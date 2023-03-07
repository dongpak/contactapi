/*

 */
package com.churchclerk.contactapi.model;

import com.churchclerk.baseapi.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 *
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Contact extends BaseModel {

    private String phone;
    private String email;
    private String web;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        if (!super.equals(o)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(phone, contact.phone) &&
                Objects.equals(email, contact.email) &&
                Objects.equals(web, contact.web) &&
                Objects.equals(street, contact.street) &&
                Objects.equals(city, contact.city) &&
                Objects.equals(state, contact.state) &&
                Objects.equals(zip, contact.zip) &&
                Objects.equals(country, contact.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, email, web, street, city, state, zip, country);
    }

    /**
     *
     * @param source
     */
    public void copy(Contact source) {
        super.copy(source);
        setPhone(source.getPhone());
        setEmail(source.getEmail());
        setWeb(source.getWeb());
        setStreet(source.getStreet());
        setCity(source.getCity());
        setState(source.getState());
        setZip(source.getZip());
        setCountry(source.getCountry());
    }

    /**
     *
     * @param source
     */
    public void copyNonNulls(Contact source) {
        super.copyNonNulls(source);
        copy(source.getPhone(), this::setPhone);
        copy(source.getEmail(), this::setEmail);
        copy(source.getWeb(), this::setWeb);
        copy(source.getStreet(), this::setStreet);
        copy(source.getCity(), this::setCity);
        copy(source.getState(), this::setState);
        copy(source.getZip(), this::setZip);
        copy(source.getCountry(), this::setCountry);
    }
}
