package ro.fortech.application.bidstore.backend.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.*;
import ro.fortech.application.bidstore.backend.persistence.provider.HibernateSessionProvider;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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

    @Inject
    private EntityManager em;

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
        return DetachedCriteria.forClass(Category.class)
            .setResultTransformer (Criteria.DISTINCT_ROOT_ENTITY)
        .getExecutableCriteria(hibernateProvider.getSession()).list();
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
                                .setResultTransformer (Criteria.DISTINCT_ROOT_ENTITY)
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
    public List getItems(List<Long> categoryIds, String sortBy, boolean ascending, String searchFilter, Map<String, Object> filters) {
        Criteria criteria = hibernateProvider.getSession().createCriteria(Item.class)
                .createAlias("categories","cat")
                .createAlias("bids", "b", JoinType.LEFT_OUTER_JOIN)
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("id"))
                        .add(Projections.groupProperty("name"))
                        .add(Projections.groupProperty("description"))
                        .add(Projections.groupProperty("openingDate"))
                        .add(Projections.groupProperty("closingDate"))
                        .add(Projections.groupProperty("status"))
                        .add(Projections.groupProperty("initialPrice"))
                        .add(Projections.count("b.itemId"),"bidCount")
                        .add(Projections.max("b.bidValue"),"bestBid")
                )
                .add(Restrictions.in("cat.id", categoryIds));
        //filter
        for(Map.Entry<String,Object> entry: filters.entrySet()){
            criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        if(searchFilter != null && !searchFilter.isEmpty()) {
            criteria.add(Restrictions.like("name", searchFilter, MatchMode.ANYWHERE));
        }

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
            if (em.contains(bid)) {
                em.remove(bid);
            } else {
                bid = em.getReference(bid.getClass(), bid.getId());
                em.remove(bid);
            }
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Item getItemWithId(Long itemId) {
        return null;
    }

    @Override
    public Bid getBidForItem(Long itemId, String username) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Bid.class)
                .add(Restrictions.eq("itemId",itemId))
                .add(Restrictions.eq("bidUserId",username));
        List list = criteria.getExecutableCriteria(hibernateProvider.getSession()).list();
        return list.isEmpty() ? null : (Bid) list.get(0);
    }

    @Override
    public List<Category> getCategories(String sortBy, boolean ascending, String searchText) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
        criteria.add(Restrictions.isNotNull("parentId"));
        //filtering
        if(searchText != null && !searchText.isEmpty()) {
            criteria.add(Restrictions.like("name", searchText, MatchMode.ANYWHERE));
        }

        //sorting
        if(sortBy != null && !sortBy.isEmpty())
            criteria.addOrder(ascending?Order.asc(sortBy):Order.desc(sortBy));

        return criteria.getExecutableCriteria(hibernateProvider.getSession()).list();
    }
}
