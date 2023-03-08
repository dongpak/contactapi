/*

 */
package com.churchclerk.contactapi.model;

import com.churchclerk.baseapi.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
