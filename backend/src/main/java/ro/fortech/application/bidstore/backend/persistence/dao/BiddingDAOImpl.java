package ro.fortech.application.bidstore.backend.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ro.fortech.application.bidstore.backend.persistence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.persistence.provider.HibernateSessionProvider;
import ro.fortech.application.bidstore.backend.util.HibernateUtil;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 20-Apr-17.
 */
public class BiddingDAOImpl implements BiddingDAO {

    @Inject
    private UserDAO userDAO;

    @Inject
    private HibernateSessionProvider hibernateProvider;

    private List<BiddingUser> temporaryBiddingUserList;

    @Override
    public List<BiddingUser> queryForBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {

        temporaryBiddingUserList = new LinkedList<>();

        Criteria criteria = hibernateProvider.getSession().createCriteria(BiddingUser.class);

        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);


        //sorting
        if(sortField != null) {
            criteria.addOrder(
                    sortOrder.equals("ASC") ?
                            Order.asc(sortField) :
                            Order.desc(sortField)
            );
        }
        //TODO: solve the filtering bug
        //filtering
        if(filters!=null && !filters.isEmpty()){
            for(Map.Entry<String,Object> entry: filters.entrySet()) {
                criteria.add(Restrictions.sqlRestriction(
                        //"{alias}." +
                        HibernateUtil.getColumNameFromField(BiddingUser.class,entry.getKey()) + " LIKE '%" + entry.getValue() + "%' "
                ));
            }
        }

        for(Object u: criteria.list()) {
            temporaryBiddingUserList.add((BiddingUser) u);
        }

        return temporaryBiddingUserList;
    }

    @Override
    public BiddingUser getSingleBiddingUser(String username) {
        Criteria criteria = hibernateProvider.getSession().createCriteria(BiddingUser.class);
        BiddingUser user;
        try {
             user = (BiddingUser)criteria.add(Restrictions.eq("username",username))
                     .uniqueResult();

             return user;

        } catch(Exception ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void saveBiddingUser(BiddingUser biddingUser) {
        hibernateProvider.getSession().merge(biddingUser);
    }

    @Override
    @Transactional
    public boolean enableBiddingUser(UserAuth auth) {
        try {
            userDAO.saveUserAuthentication(auth);
            User user = userDAO.getUserDetails(new User(auth.getUsername()));
            hibernateProvider.getSession().delete(user);
            saveBiddingUser(new BiddingUser(user,0,0,0));
            return true;
        }catch(Exception ex) {
            return false;
        }
    }

}
