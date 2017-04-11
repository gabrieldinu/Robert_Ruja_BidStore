package ro.fortech.application.bidstore.persisetence.dao;

import org.hibernate.Session;
import ro.fortech.application.bidstore.persisetence.entity.User;
import ro.fortech.application.bidstore.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.persisetence.provider.HibernateProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
//@Transactional

public class UserDAOImpl implements UserDAO {

//    @Inject
//    EntityManager entityManager;

    @Override
    public User getUser(String username) {
//        TypedQuery<User> query = entityManager.createNamedQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
//        query.setParameter("username", username);

        return new User();
    }

    public UserAuth getUserAuthentication(String username, String password){

        return new UserAuth("admin","password", UUID.randomUUID());
    }
}
