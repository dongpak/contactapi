/**
 * 
 */
package com.churchclerk.contactapi.service;

import com.churchclerk.contactapi.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 
 * @author dongp
 *
 */
public interface ContactStorage extends JpaRepository<ContactEntity, String>, JpaSpecificationExecutor<ContactEntity> {

}
