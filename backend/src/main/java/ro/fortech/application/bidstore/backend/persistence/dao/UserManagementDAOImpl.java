package ro.fortech.application.bidstore.backend.persistence.dao;

import org.hibernate.SQLQuery;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.provider.HibernateSessionProvider;
import ro.fortech.application.bidstore.backend.util.sql.UserManagementSql;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robert.ruja on 23-May-17.
 */
@Named
public class UserManagementDAOImpl implements UserManagementDAO {

    @Inject
    private HibernateSessionProvider hibernateProvider;

    @Inject @UserManagementSql
    private String userManagemntSql;

    @Override
    public List<BiddingUser> getUserList() {
        SQLQuery query = hibernateProvider.getSession().createSQLQuery(userManagemntSql);
        List<BiddingUser> userList = new ArrayList<>();

        for(Object o : query.list()) {
            BiddingUser user = new BiddingUser();
            Object[] cols = (Object[])o;
            user.setFirstName((String)cols[0]);
            user.setLastName((String)cols[1]);
            user.setUsername((String)cols[2]);
            user.setEmail((String)cols[3]);
            int admin = (Integer)cols[4];
            int enabled = (Integer)cols[5];
            user.setAdmin(admin == 0);
            user.setEnabled(enabled == 0);
            user.setItemsPlaced(((BigInteger)cols[6]).longValue());
            user.setItemsSold(((BigInteger)cols[7]).longValue());
            user.setItemsBought(((BigInteger)cols[8]).longValue());
            userList.add(user);
        }
        return userList;
    }
}
