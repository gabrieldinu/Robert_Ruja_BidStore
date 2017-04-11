package ro.fortech.application.bidstore.service;

import ro.fortech.application.bidstore.persisetence.dao.UserDAO;
import ro.fortech.application.bidstore.persisetence.entity.User;
import ro.fortech.application.bidstore.persisetence.entity.UserAuth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;


/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
@Stateless
public class AccountBeanServiceImpl implements AccountBeanService {

    @Inject
    private UserDAO userDAO;

//    @Inject
//    HibernateProvider provider;

    @Override
    public String showUser() {
        String message = "";

//        message = provider.getSession().get(User.class,"admin").toString();
        message = userDAO.toString();
        return message;
    }

    public UserAuth getAutenticatedUser(String user, String password){
//        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
//        query.setParameter("login", user.getLogin());
//        query.setParameter("password", PasswordUtils.digestPassword(user.getPassword()));
        try {
//            user = query.getSingleResult();
//            // If the user is an administrator
//            if (user.getRole().equals(UserRole.ADMIN))
//                admin = true;
//            // If the user has clicked on remember me
//            if (rememberMe) {
//                String uuid = UUID.randomUUID().toString();
//                user.setUuid(uuid);
//                addCookie(uuid);
//            } else {
//                user.setUuid(null);
//                removeCookie();
//            }
            return userDAO.getUserAuthentication(user,password);
        } catch(NoResultException ex){
            return null;
        }
    }

}
