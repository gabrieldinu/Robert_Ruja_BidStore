package ro.fortech.application.bidstore.backend.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.*;
import ro.fortech.application.bidstore.backend.model.BidStatus;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.*;
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
    public List<Category> getCategoriesWithParentId(Long id) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);

        Criterion criterion;
        if(id != null)
            criterion = Restrictions.eq("parentId", id);
        else
            criterion = Restrictions.isNull("parentId");

        detachedCriteria.add(criterion);

        return detachedCriteria.getExecutableCriteria(hibernateProvider.getSession()).list();
    }

    @Override
    public List<Category> getAllCategories() {
        return hibernateProvider.getSession().createCriteria(Category.class).list();
    }

    public Category getRoot() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Category.class);
        detachedCriteria.add(Restrictions.idEq(0L));
        return (Category)detachedCriteria.getExecutableCriteria(hibernateProvider.getSession()).list().get(0);
    }

    @Override
    public long getBoughtItemCount(String username) {
        //todo:
        return 0;
    }

    @Override
    public long getSoldItemCount(String username) {
        //todo:
        return 0;
    }

    @Override
    public long getPlacedItemCount(String username) {
        //todo:
        return 0;
    }

    @Override
    public List<Item> getFullItemList() {
        return hibernateProvider.getSession().createCriteria(Item.class).list();
    }

    @Override
    public List<String> getCategoriesNameContains(String query) {
        return hibernateProvider.getSession().createCriteria(Category.class)
                                .add(Restrictions.like("name",query, MatchMode.ANYWHERE))
                                .setMaxResults(6)
                                .setProjection(Projections.property("name"))
                                .list();
    }

    @Override
    @Transactional
    public boolean enableBiddingUser(UserAuth auth) {
        try {
            userDAO.saveUserAuthentication(auth);
            User user = userDAO.getUserDetails(new User(auth.getUsername()));
            hibernateProvider.getSession().delete(user);
            userDAO.saveUserInfo(user);
            return true;
        }catch(Exception ex) {
            return false;
        }
    }

    @Override
    public List<Item> getItems(List<Long> categoryIds, int maxResults, String sortBy, boolean ascending, String searchFilter) {
        Criteria criteria = hibernateProvider.getSession().createCriteria(Item.class)
                //.setMaxResults(maxResults)
                .createAlias("categories","categoriesAlias")
                .add(Restrictions.in("categoriesAlias.id", categoryIds))
                .add(Restrictions.eq("status", BidStatus.OPEN))
                .setResultTransformer (Criteria.DISTINCT_ROOT_ENTITY);

        //filter
        if(searchFilter != null && !searchFilter.isEmpty())
            criteria.add(Restrictions.like("name", searchFilter, MatchMode.ANYWHERE));
        //sort
        if(sortBy != null && !sortBy.isEmpty())
            criteria.addOrder(ascending?Order.asc(sortBy):Order.desc(sortBy));

        return criteria.list();
    }

    @Override
    public List<String> getItemsNameContains(String query) {
        return hibernateProvider.getSession().createCriteria(Item.class)
                .add(Restrictions.like("name",query, MatchMode.ANYWHERE))
                .setMaxResults(6)
                .setProjection(Projections.property("name"))
                .setResultTransformer (Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    @Transactional
    public boolean saveItem(Item item) {

        try {
            hibernateProvider.getSession().merge(item);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveBid(Bid bid) {
        try {
            hibernateProvider.getSession().merge(bid);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removeBid(Bid bid) {
        try {
            hibernateProvider.getSession().delete(bid);
            return true;
        }catch (Exception ex) {
            return false;
        }
    }
}
