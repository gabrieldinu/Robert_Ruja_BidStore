package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.Paginator;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

/**
 * Created by robert.ruja on 28-Apr-17.
 */

@ManagedBean(name = "categoryView")
@ViewScoped
public class CategoryView implements Serializable {

    private List<Category> breadcrumbs;

    private List<Category> allCategories;

    private String searchText;

    private Paginator paginator = new Paginator();

    @ManagedProperty(value = "#{contentView}")
    private ContentView contentView;

    @ManagedProperty(value = "#{treeBean}")
    private TreeBean treeBean;

    @Inject
    private BiddingService biddingService;

    @PostConstruct
    public void init(){
        allCategories = biddingService.getAllCategories();
    }

    public void updateCategoryView(Category category){
        treeBean.setSelectedCategory(category);
        contentView.renderItemList();
        updateBreadcrumbs(category);
    }

    private void updateBreadcrumbs(Category category) {
        breadcrumbs = new ArrayList<>();
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
    }

    public List<String> completeText(String query) {

        return biddingService.getCategoriesNameCointains(query);
    }

    public List<Category> getCategoryList() {

        List<Category> results = new ArrayList<>();
        //todo: sorting and filtering for category search
        if(searchText != null && !searchText.isEmpty()){
            for(Category searchCategory: allCategories){
                if(searchCategory.getName().toLowerCase().contains(searchText.toLowerCase()))
                    results.add(searchCategory);
            }
        }
        paginator.setItemCount(results.size());
        paginator.calculate();
        return results.subList(paginator.getStartIndex(),paginator.getEndIndex());
    }

    public List<Category> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Category> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public ContentView getContentView() {
        return contentView;
    }

    public void setContentView(ContentView contentView) {
        this.contentView = contentView;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }
}
