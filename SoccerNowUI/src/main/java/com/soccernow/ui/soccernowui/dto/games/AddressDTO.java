package com.soccernow.ui.soccernowui.dto.games;

import java.io.Serializable;
import java.util.Objects;

public class AddressDTO implements Serializable {
    private String country;
    private String city;
    private String street;
    private String postalCode;

    public AddressDTO() {
    }

    public AddressDTO(String country, String city, String street, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public AddressDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressDTO setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public AddressDTO setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDTO entity = (AddressDTO) o;
        return Objects.equals(this.country, entity.country) &&
                Objects.equals(this.city, entity.city) &&
                Objects.equals(this.street, entity.street) &&
                Objects.equals(this.postalCode, entity.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, postalCode);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "country = " + country + ", " +
                "city = " + city + ", " +
                "street = " + street + ", " +
                "postalCode = " + postalCode + ")";
    }
}
