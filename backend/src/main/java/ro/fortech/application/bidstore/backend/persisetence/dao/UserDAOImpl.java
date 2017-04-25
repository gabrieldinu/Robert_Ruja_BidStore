package ro.fortech.application.bidstore.backend.persisetence.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import ro.fortech.application.bidstore.backend.model.AddressType;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAddressPk;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.persisetence.provider.HibernateSessionProvider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
public class UserDAOImpl implements UserDAO {

    @Inject
    EntityManager em;

    @Inject
    HibernateSessionProvider hibernateProvider;



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
    @Transactional
    public boolean saveUserInfo(User user) {
        try {
            em.merge(user);
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
    public boolean saveUserAuthentication(UserAuth userAuth) {
        try {
            em.merge(userAuth);
            return true;
        } catch (Exception ex) {
            return false;
        }
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

    @Override
    @Transactional
    public boolean setUserEnabled(String managedUsername, boolean b) {
        try {
            User user = getUserDetails(new User(managedUsername, null, null, null, null,null));
            user.setEnabled(b);
            em.merge(user);
        }catch(Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public boolean saveUserAddress(Map<String,UserAddress> userAddressMap) {
        try {
            for(Map.Entry<String,UserAddress> entry : userAddressMap.entrySet()) {
                em.merge(entry.getValue());
            }
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    @Override
    public Map<String, UserAddress> getUserAddressDetails(User user) {

            Map<String, UserAddress> addressMap = new HashMap<>();
        try {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserAddress.class)
                    .add(Restrictions.or(
                            Restrictions.eq("primaryKey", new UserAddressPk(user.getUsername(), AddressType.HOME)),
                            Restrictions.eq("primaryKey", new UserAddressPk(user.getUsername(), AddressType.BILLING)),
                            Restrictions.eq("primaryKey", new UserAddressPk(user.getUsername(), AddressType.SHIPPING))
                            )
                    );
            for (Object o : detachedCriteria.getExecutableCriteria(hibernateProvider.getSession()).list()) {
                UserAddress address = (UserAddress) o;
                addressMap.put(address.getPrimaryKey().getAddressType().getMapKey(), address);
            }

        }catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return addressMap;
    }
}
