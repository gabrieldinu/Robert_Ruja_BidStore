package ro.fortech.application.bidstore.backend.persisetence.entity;

import ro.fortech.application.bidstore.backend.model.AddressType;

import javax.persistence.*;

/**
 * Created by robert.ruja on 25-Apr-17.
 */

@Entity
@Table(name = "t_user_address")
public class UserAddress {


    @EmbeddedId
    private UserAddressPk primaryKey;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private int zipcode;

    public UserAddress() {
    }

    public UserAddress(UserAddressPk primaryKey) {
        this.primaryKey = primaryKey;
    }

    public UserAddressPk getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UserAddressPk primaryKey) {
        this.primaryKey = primaryKey;
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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
