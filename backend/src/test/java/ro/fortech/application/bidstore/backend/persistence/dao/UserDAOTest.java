package ro.fortech.application.bidstore.backend.persistence.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import ro.fortech.application.bidstore.backend.model.AddressType;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddressPk;

import static org.junit.Assert.*;

import java.util.Map;

/**
 * Created by robert.ruja on 26-Apr-17.
 */

public class UserDAOTest extends TransactionalDAOTest {

    private static UserDAOImpl userDAO;

    @BeforeClass
    public static void load(){
        userDAO = new UserDAOImpl();
        userDAO.setHibernateProvider(
                sessionProvider
        );
    }


    @Test
    public void TestGetUserAddressDetails() {
        User user = new User("gigi");
        Map<String,UserAddress> userAddressMap = userDAO.getUserAddressDetails(user);
        assertFalse(userAddressMap.isEmpty());
        assertEquals(userAddressMap.get("shippingAddress"),
                new UserAddress(new UserAddressPk("gigi", AddressType.SHIPPING),"Nicolina","Iasi",123));
        assertEquals(userAddressMap.get("homeAddress"),
                new UserAddress(new UserAddressPk("gigi", AddressType.HOME),"Palat","Iasi",213));
    }

}
