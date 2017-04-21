package ro.fortech.application.bidstore.backend.persisetence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import ro.fortech.application.bidstore.backend.model.UserEnabled;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persisetence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persisetence.provider.HibernateSessionProvider;
import ro.fortech.application.bidstore.backend.util.HibernateUtil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 20-Apr-17.
 */
public class BiddingDAOImpl implements BiddingDAO{

    @Inject
    private UserDAO userDAO;

    @Inject
    private HibernateSessionProvider hibernateProvider;

    private List<BiddingUser> temporaryBiddingUserList;

    @Override
    public List<BiddingUser> queryForBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {

        temporaryBiddingUserList = new LinkedList<>();

        Criteria criteria = buildCriteriaForBiddingUser();
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);


        //sorting
        if(sortField != null) {
            sortField = "bu." + sortField;
            criteria.addOrder(
                    sortOrder.equals("ASC") ?
                            Order.asc(sortField) :
                            Order.desc(sortField)
            );
        }
        //filtering
        if(filters!=null && !filters.isEmpty()){
            for(Map.Entry<String,Object> entry: filters.entrySet()) {
                criteria.add(Restrictions.sqlRestriction("{alias}." +
                        HibernateUtil.getColumNameFromField(BiddingUser.class,entry.getKey()) + " LIKE '%" + entry.getValue() + "%' "
                        ));
                //criteria.add(Restrictions.like(entry.getKey(),"%" + entry.getValue() + "%"));
            }
        }

        for(Object o:criteria.list()) {
            Object[] elements = (Object[])o;
            temporaryBiddingUserList.add(
                    buildBiddingUserFromProjections(elements)
            );
        }

        return temporaryBiddingUserList;
    }

    @Override
    public BiddingUser getSingleBiddingUser(String rowKey) {
        Criteria criteria = buildCriteriaForBiddingUser();
        BiddingUser user;
        try {
             Object[] elements = (Object[])criteria.add(Restrictions.eq("username",rowKey))
                     .uniqueResult();

             return buildBiddingUserFromProjections(elements);

        } catch(Exception ex) {
            return null;
        }
    }

    private Criteria buildCriteriaForBiddingUser() {
        Session session = hibernateProvider.getSession();
        return session.createCriteria(BiddingUser.class,"bu")
                .setProjection(Projections.projectionList()
                .add(Projections.groupProperty("firstName"))
                .add(Projections.groupProperty("lastName"))
                .add(Projections.groupProperty("username"))
                .add(Projections.groupProperty("email"))
                .add(Projections.groupProperty("role"))
                .add(Projections.groupProperty("userEnabled"))
                .add(Projections.sum("itemsPlaced"),"bu.itemsPlaced")
                .add(Projections.sum("itemsSold"),"bu.itemsSold")
                .add(Projections.sum("itemsBought"),"bu.itemsBought")
        );
    }

    private BiddingUser buildBiddingUserFromProjections(Object[] elements){
        return new BiddingUser(
                (String)elements[2],
                (String)elements[0],
                (String)elements[1],
                (String)elements[3],
                (UserRole) elements[4],
                (UserEnabled) elements[5],
                (Long)elements[6],
                (Long)elements[7],
                (Long)elements[8]);
    }
}
