/**
 * 
 */
package com.churchclerk.contactapi.entity;

import com.churchclerk.contactapi.model.Contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 
 * @author dongp
 *
 */
@Entity
@Table(name="contact")
public class ContactEntity extends Contact {

	@Column(name="active")
	@Override
	public boolean isActive() {
		return super.isActive();
	}

	@Id
	@Column(name="id")
	@Override
	public String getId() {
		return super.getId();
	}

	@Column(name="phone")
	@Override
	public String getPhone() {
		return super.getPhone();
	}

	@Column(name="email")
	@Override
	public String getEmail() {
		return super.getEmail();
	}

	@Column(name="web")
	@Override
	public String getWeb() {
		return super.getWeb();
	}

	@Column(name="street")
	@Override
	public String getStreet() {
		return super.getStreet();
	}

	@Column(name="city")
	@Override
	public String getCity() {
		return super.getCity();
	}

	@Column(name="state")
	@Override
	public String getState() {
		return super.getState();
	}

	@Column(name="zip")
	@Override
	public String getZip() {
		return super.getZip();
	}

	@Column(name="country")
	@Override
	public String getCountry() {
		return super.getCountry();
	}

	@Column(name="created_date")
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Column(name="created_by")
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Column(name="updated_date")
	@Override
	public Date getUpdatedDate() {
		return super.getUpdatedDate();
	}

	@Column(name="updated_by")
	@Override
	public String getUpdatedBy() {
		return super.getUpdatedBy();
	}
}
