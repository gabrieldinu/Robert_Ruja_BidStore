package ro.fortech.application.bidstore.backend.persisetence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import ro.fortech.application.bidstore.backend.model.UserEnabled;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persisetence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persisetence.provider.HibernateSessionProvider;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 20-Apr-17.
 */
public class BiddingDAOImpl implements BiddingDAO{

    @Inject
    private UserDAO userDAO;

    @Inject
    HibernateSessionProvider hibernateProvider;

    List<BiddingUser> temporaryBiddingUserList;

    @Override
    public List<BiddingUser> queryForBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {
        temporaryBiddingUserList = new ArrayList<>();
        Session session = hibernateProvider.getSession();
        Criteria criteria = session.createCriteria(BiddingUser.class);
        ProjectionList pl = Projections.projectionList();
        pl.add(Projections.groupProperty("firstName"));
        pl.add(Projections.groupProperty("lastName"));
        pl.add(Projections.groupProperty("username"));
        pl.add(Projections.groupProperty("email"));
        pl.add(Projections.groupProperty("role"));
        pl.add(Projections.groupProperty("userEnabled"));
        pl.add(Projections.sum("itemsPlaced"));
        pl.add(Projections.sum("itemsSold"));
        pl.add(Projections.sum("itemsBought"));
        criteria.setProjection(pl);

        for(Object o:criteria.list()) {
            Object[] elements = (Object[])o;
            temporaryBiddingUserList.add(new BiddingUser(
                    (String)elements[0],
                    (String)elements[0],
                    (String)elements[0],
                    (String)elements[0],
                    (UserRole) elements[0],
                    (UserEnabled) elements[0],
                    (Long)elements[0],
                    (Long)elements[0],
                    (Long)elements[0]

            ));
        }

        //dummy
//        if(this.list == null) {
//            List<BiddingUser> list = new ArrayList<BiddingUser>() {{
//                BiddingUser user = new BiddingUser();
//                user.setEmail("email");
//                user.setUsername("username");
//                user.setUserEnabled(UserEnabled.ENABLED);
//                user.setFirstName("first name");
//                user.setLastName("last");
//                user.setRole(UserRole.USER);
//                user.setItemsBought(10);
//                user.setItemsPlaced(15);
//                user.setItemsSold(5);
//                add(user);
//                user = new BiddingUser();
//                user.setEmail("email1");
//                user.setUsername("username1");
//                user.setUserEnabled(UserEnabled.ENABLED);
//                user.setFirstName("first name1");
//                user.setLastName("last1");
//                user.setRole(UserRole.USER);
//                user.setItemsBought(103);
//                user.setItemsPlaced(125);
//                user.setItemsSold(53);
//                add(user);
//                user = new BiddingUser();
//                user.setEmail("email2");
//                user.setUsername("username2");
//                user.setUserEnabled(UserEnabled.ENABLED);
//                user.setFirstName("first name2");
//                user.setLastName("last2");
//                user.setRole(UserRole.USER);
//                user.setItemsBought(410);
//                user.setItemsPlaced(135);
//                user.setItemsSold(52);
//                add(user);
//                user = new BiddingUser();
//                user.setEmail("email3");
//                user.setUsername("username3");
//                user.setUserEnabled(UserEnabled.DISABLED);
//                user.setFirstName("first name3");
//                user.setLastName("last3");
//                user.setRole(UserRole.USER);
//                user.setItemsBought(2);
//                user.setItemsPlaced(15);
//                user.setItemsSold(45);
//                add(user);
//            }};
//            this.list = list;
        //       }
        //       return list;
        return temporaryBiddingUserList;
    }

    @Override
    public BiddingUser getSingleBiddingUser(String rowKey) {
        //                user = new BiddingUser();
//                user.setEmail("email3");
//                user.setUsername("username3");
//                user.setUserEnabled(UserEnabled.DISABLED);
//                user.setFirstName("first name3");
//                user.setLastName("last3");
//                user.setRole(UserRole.USER);
//                user.setItemsBought(2);
//                user.setItemsPlaced(15);
//                user.setItemsSold(45);
//                add(user);
        return null;
    }
}
