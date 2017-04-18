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
    public boolean saveUserInfo(UserAuth userAuth, User user) {
        try {
            em.persist(userAuth);
            em.persist(user);
            return true;
        } catch(Exception ex){
            return false;
        }
    }

    @Override
    public UserAuth getUserAuthenticationByActivationToken(String activationTokenUUID) {
        UserAuth userAuth;
        TypedQuery<UserAuth> query = em.createNamedQuery(UserAuth.FIND_BY_ACTIVATION_TOKEN, UserAuth.class);
        query.setParameter("activationToken", activationTokenUUID);

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
        em.merge(userAuth);
    }

    @Override
    @Transactional
    public void deleteUserWithExpiredDate(Date date) {
        Query query = em.createNamedQuery(UserAuth.DELETE_BY_EXPIRING_DATE);
        query.setParameter("date",date);
        query.executeUpdate();
    }

    @Override
    public User getByEmail(User user) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_EMAIL, User.class);
        query.setParameter("email", user.getEmail());
        try {
         return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserAuth getUserAuthenticationByResetToken(String token) {
        TypedQuery<UserAuth> query = em.createNamedQuery(UserAuth.FIND_BY_RESET_TOKEN, UserAuth.class);
        query.setParameter("token", token);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserAuth getUserAuthenticationByUUID(String cookieValue) {
        TypedQuery<UserAuth> query = em.createNamedQuery(UserAuth.FIND_BY_UUID, UserAuth.class);
        query.setParameter("uuid", cookieValue);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
