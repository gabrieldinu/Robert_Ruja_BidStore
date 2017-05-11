package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common;

import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.TabViewBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by robert.ruja on 11-May-17.
 */
@ManagedBean
@ViewScoped
public class Breadcrumbs implements Serializable {

    private List<Category> categories;

    private List<Category> bidding;

    private List<Category> allCategories;

    @ManagedProperty(value = "#{tabViewBean}")
    private TabViewBean tabViewBean;

    @ManagedProperty(value = "#{treeBean}")
    private TreeBean treeBean;

    @Inject
    private BiddingService biddingService;

    @PostConstruct
    public void init(){
        allCategories = biddingService.getAllCategories();
    }

    public List<Category> getBreadcrumbs() {
        List<Category> breadcrumbs  = new ArrayList<>();
        Category category = treeBean.getSelectedCategory();
        breadcrumbs.add(category);
        Category current = category;
        for(int i = allCategories.size() - 1; i >= 0; i--){
            Category searchCategory = allCategories.get(i);
            if(searchCategory.getParentId() != null && searchCategory.getId().equals(current.getParentId())){
                breadcrumbs.add(searchCategory);
                current = searchCategory;
            }
        }
        Collections.reverse(breadcrumbs);
        return breadcrumbs;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getBidding() {
        return bidding;
    }

    public void setBidding(List<Category> bidding) {
        this.bidding = bidding;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public TabViewBean getTabViewBean() {
        return tabViewBean;
    }

    public void setTabViewBean(TabViewBean tabViewBean) {
        this.tabViewBean = tabViewBean;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }

    public BiddingService getBiddingService() {
        return biddingService;
    }

    public void setBiddingService(BiddingService biddingService) {
        this.biddingService = biddingService;
    }
}
