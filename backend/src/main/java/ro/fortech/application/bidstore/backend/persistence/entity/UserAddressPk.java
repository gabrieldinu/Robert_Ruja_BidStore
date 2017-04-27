package ro.fortech.application.bidstore.backend.persistence.entity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAddressPk that = (UserAddressPk) o;

        if (!username.equals(that.username)) return false;
        return addressType == that.addressType;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + addressType.hashCode();
        return result;
    }
}
