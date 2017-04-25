package ro.fortech.application.bidstore.backend.persisetence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ro.fortech.application.bidstore.backend.persisetence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.persisetence.provider.HibernateSessionProvider;
import ro.fortech.application.bidstore.backend.util.HibernateUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
    EntityManager em;

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

        //filtering
        if(filters!=null && !filters.isEmpty()){
            for(Map.Entry<String,Object> entry: filters.entrySet()) {
                criteria.add(Restrictions.sqlRestriction(
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
    public BiddingUser getSingleBiddingUser(String rowKey) {
        Criteria criteria = hibernateProvider.getSession().createCriteria(BiddingUser.class);
        BiddingUser user;
        try {
             user = (BiddingUser)criteria.add(Restrictions.eq("username",rowKey))
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
