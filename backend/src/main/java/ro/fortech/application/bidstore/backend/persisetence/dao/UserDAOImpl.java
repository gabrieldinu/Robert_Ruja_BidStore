package ro.fortech.application.bidstore.backend.persisetence.dao;

import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
public class UserDAOImpl implements UserDAO {

    @Inject
    EntityManager em;

    public UserAuth getUserAuthentication(String username){

        UserAuth userAuth;
        TypedQuery<UserAuth> query = em.createNamedQuery(UserAuth.FIND_BY_USERNAME, UserAuth.class);
        query.setParameter("username", username);

        try{
            userAuth = query.getSingleResult();
            return userAuth;
        }catch(NoResultException ex){
            return null;
        }
    }

    public User getUserDetails(User userInput){
        User user;
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_USERNAME, User.class);
        query.setParameter("username", userInput.getUsername());

        try{
            user = query.getSingleResult();
            return user;
        }catch(NoResultException ex){
            return null;
        }
    }

    @Override
    @Transactional
    public void saveUserInfo(UserAuth userAuth, User user) {
        em.persist(userAuth);
        em.persist(user);
    }

    @Override
    public UserAuth getUserAuthenticationByUUID(String uuid) {
        UserAuth userAuth;
        TypedQuery<UserAuth> query = em.createNamedQuery(UserAuth.FIND_BY_UUID, UserAuth.class);
        query.setParameter("uuid", uuid);

        try {
            userAuth = query.getSingleResult();
            return userAuth;
        }catch(NoResultException ex){
            return null;
        }
    }

    @Override
    @Transactional
    public void saveUserAuthentication(UserAuth userAuth) {

        em.persist(userAuth);
    }

    @Override
    @Transactional
    public void deleteUserWithExpiredDate(Date date) {
        Query query = em.createNamedQuery(UserAuth.DELETE_BY_EXPIRING_DATE);
        query.setParameter("date",date);
        query.executeUpdate();
    }
}
