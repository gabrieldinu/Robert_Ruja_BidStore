package ro.fortech.application.bidstore.backend.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    public UserAddress(UserAddressPk primaryKey, String street, String city, int zipcode) {
        this.primaryKey = primaryKey;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAddress that = (UserAddress) o;

        if (zipcode != that.zipcode) return false;
        if (!primaryKey.equals(that.primaryKey)) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        return city != null ? city.equals(that.city) : that.city == null;
    }

    @Override
    public int hashCode() {
        int result = primaryKey.hashCode();
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + zipcode;
        return result;
    }
}
