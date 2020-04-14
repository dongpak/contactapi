package com.churchclerk.contactapi.service;

import com.churchclerk.contactapi.entity.ContactEntity;
import com.churchclerk.contactapi.model.Contact;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ContactResourceSpec implements Specification<ContactEntity> {

    private Contact criteria = null;

    /**
     * @param criteria
     */
    public ContactResourceSpec(Contact criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<ContactEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        addPredicate(criteriaBuilder, root, "active", criteria.isActive(), predicates);
        addPredicate(criteriaBuilder, root, "id", criteria.getId(), predicates);


        if (predicates.isEmpty()) {
            return null;
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void addPredicate(CriteriaBuilder criteriaBuilder, Root<ContactEntity> root, String field, String value, List<Predicate> predicates) {
        Predicate predicate = null;

        if (value != null) {
            if (value.trim().isEmpty()) {
                predicate = criteriaBuilder.isEmpty(root.get(field));
            } else if (value.contains("%")) {
                predicate = criteriaBuilder.like(root.get(field), value);
            } else {
                predicate = criteriaBuilder.equal(root.get(field), value);
            }
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
    }

    private void addPredicate(CriteriaBuilder criteriaBuilder, Root<ContactEntity> root, String field, Boolean value, List<Predicate> predicates) {
        Predicate predicate = null;

        if (value != null) {
            predicate = criteriaBuilder.equal(root.get(field), value);
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
    }
}
