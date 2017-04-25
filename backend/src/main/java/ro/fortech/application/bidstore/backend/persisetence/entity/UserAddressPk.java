package ro.fortech.application.bidstore.backend.persisetence.entity;

import ro.fortech.application.bidstore.backend.model.AddressType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Created by robert.ruja on 25-Apr-17.
 */

@Embeddable
public class UserAddressPk implements Serializable {

    @Column
    private String username;

    @Enumerated
    @Column(name= "DTYPE")
    private AddressType addressType;

    public UserAddressPk() {
    }

    public UserAddressPk(String username, AddressType addressType) {
        this.username = username;
        this.addressType = addressType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}
