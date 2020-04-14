/**
 * 
 */
package com.churchclerk.contactapi.service;

import com.churchclerk.contactapi.entity.ContactEntity;
import com.churchclerk.contactapi.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;


/**
 * 
 * @author dongp
 *
 */
@Service
public class ContactService {

	private static Logger logger	= LoggerFactory.getLogger(ContactService.class);

	@Autowired
	private ContactStorage storage;


	/**
	 *
	 * @return
	 */
	public Page<? extends Contact> getResources(Pageable pageable, Contact criteria) {

		Page<ContactEntity> page = storage.findAll(new ContactResourceSpec(criteria), pageable);

		return page;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Contact getResource(String id) {

		Optional<ContactEntity> entity = storage.findById(id);

		checkResourceNotFound(id, entity);
		return entity.get();
	}

	private void checkResourceNotFound(String id, Optional<ContactEntity> optional) {
		if (optional.isPresent() == false) {
			throw new NotFoundException("No such Contact resource with id: " + id);
		}
	}

	/**
	 *
	 * @param resource
	 * @return
	 */
	public Contact createResource(Contact resource) {
		ContactEntity entity = new ContactEntity();

		entity.copy(resource);

		return storage.save(entity);
	}


	/**
	 *
	 * @param resource
	 * @return
	 */
	public Contact updateResource(Contact resource) {
		Optional<ContactEntity> optional = storage.findById(resource.getId());

		checkResourceNotFound(resource.getId(), optional);

		ContactEntity entity = optional.get();

		entity.copyNonNulls(resource);
		return storage.save(entity);
	}



	/**
	 *
	 * @param id
	 * @return
	 */
	public Contact deleteResource(String id) {
		Optional<ContactEntity> optional = storage.findById(id);

		checkResourceNotFound(id, optional);

		storage.deleteById(id);
		return optional.get();
	}
}
