package ro.fortech.application.bidstore.backend.persisetence.dao;

import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
@Transactional
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

    public User getUser(User userInput){
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
    public void saveUser(UserAuth userAuth, User user) {

            em.persist(user);
            em.persist(userAuth);
    }

}
