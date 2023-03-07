/*

 */
package com.churchclerk.contactapi;

import com.churchclerk.baseapi.BaseApi;
import com.churchclerk.baseapi.model.ApiCaller;
import com.churchclerk.contactapi.model.Contact;
import com.churchclerk.contactapi.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
@Component
@Slf4j
@Path("/contact")
public class ContactApi extends BaseApi<Contact> {

    @QueryParam("phone")
    protected String phoneLike;

    @QueryParam("email")
    private String emailLike;

    @QueryParam("web")
    private String webLike;

    @QueryParam("street")
    private String streetLike;

    @QueryParam("city")
    private String cityLike;

    @QueryParam("state")
    private String stateLike;

    @QueryParam("zip")
    private String zipLike;

    @QueryParam("country")
    private String countryLike;


    @Autowired
    private ContactService service;

    /**
     *
     */
    public ContactApi() {
        super(Contact.class);
        setReadRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK, ApiCaller.Role.OFFICIAL, ApiCaller.Role.MEMBER, ApiCaller.Role.NONMEMBER);
        setUpdateRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK, ApiCaller.Role.OFFICIAL, ApiCaller.Role.MEMBER, ApiCaller.Role.NONMEMBER);
        setCreateRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK);
        setDeleteRoles(ApiCaller.Role.ADMIN, ApiCaller.Role.CLERK);
    }

    @Override
    protected Page<? extends Contact> doGet(Pageable pageable) {

        return service.getResources(pageable, createCriteria());
    }

    /**
     *
     * @return
     */
    protected Contact createCriteria() {
        Contact criteria	= new Contact();

        addBaseCriteria(criteria);

        criteria.setPhone(phoneLike);
        criteria.setEmail(emailLike);
        criteria.setWeb(webLike);
        criteria.setStreet(streetLike);
        criteria.setCity(cityLike);
        criteria.setState(stateLike);
        criteria.setZip(zipLike);
        criteria.setCountry(countryLike);

        if (id == null) {
            apiCaller.getMemberOf().forEach(churchId -> {
                if (id == null) {
                    id = churchId;
                }
            });
        }

        if (readAllowed(id, this::hasSuperRole)) {
            if (id != null) {
                criteria.setId(UUID.fromString(id));
            }
        }
        else {
            // force return of empty array
            criteria.setId(null);
        }

        log.info("id="+criteria.getId());
        return criteria;
    }

    @Override
    protected Contact doGet() {
        if ((id == null) || (id.trim().isEmpty())) {
            throw new BadRequestException("Contact id cannot be empty");
        }

        Contact resource = service.getResource(id);

        if ((resource == null) || (readAllowed(resource.getId().toString()) == false)) {
            throw new NotFoundException();
        }

        return resource;
    }

    @Override
    protected Contact doCreate(Contact resource) {
        if (resource.getId() != null) {
            throw new BadRequestException("Contact id should not be present");
        }

        checkRequiredFields(resource);

        if (createAllowed(null, this::hasSuperRole) == false) {
            throw new ForbiddenException();
        }

        resource.setId(UUID.randomUUID());
        return service.createResource(resource);
    }

    @Override
    protected Contact doUpdate(Contact resource) {
        if ((id == null) || (id.isEmpty()) || (resource.getId() == null)) {
            throw new BadRequestException("Contact id cannot be empty");
        }

        if (resource.getId().toString().equals(id) == false) {
            throw new BadRequestException("Contact id does not match");
        }

        checkRequiredFields(resource);

        if (updateAllowed(id) == false) {
            throw new ForbiddenException();
        }

        resource.setUpdatedBy(apiCaller.getUserid());
        resource.setUpdatedDate(new Date());

        return service.updateResource(resource);
    }

    private void checkRequiredFields(Contact resource) {
        if ((resource.getStreet() == null) || (resource.getStreet().trim().isEmpty())) {
            throw new BadRequestException("Contact's street cannot be empty");
        }

        if ((resource.getCity() == null) || (resource.getCity().trim().isEmpty())) {
            throw new BadRequestException("Contact's city cannot be empty");
        }

        if ((resource.getState() == null) || (resource.getState().trim().isEmpty())) {
            throw new BadRequestException("Contact's state cannot be empty");
        }

        if ((resource.getZip() == null) || (resource.getZip().trim().isEmpty())) {
            throw new BadRequestException("Contact's zip cannot be empty");
        }

        if ((resource.getCountry() == null) || (resource.getCountry().trim().isEmpty())) {
            throw new BadRequestException("Contact's country cannot be empty");
        }
    }

    @Override
    protected Contact doDelete() {
        if ((id == null) || id.isEmpty()) {
            throw new BadRequestException("Contact id cannot be empty");
        }

        if (deleteAllowed(id) == false) {
            throw new ForbiddenException();
        }

        return service.deleteResource(id);
    }
}
