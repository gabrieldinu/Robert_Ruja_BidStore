package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.category;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.Breadcrumbs;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.TreeBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by robert.ruja on 11-May-17.
 */
@ManagedBean(name = "categoryEdit")
@ViewScoped
public class CategoryEditBean implements Serializable {

    @ManagedProperty(value = "#{treeBean}")
    private TreeBean treeBean;

    @ManagedProperty(value = "#{breadcrumbs}")
    private Breadcrumbs breadcrumbs;

    @Inject
    private BiddingService service;

    @Inject
    private FacesContext context;

    public void saveCategory() {
        Category category = treeBean.getSelectedCategory();
        if(category != null){
            try {
                service.saveCategory(category);
                treeBean.mergeNode(category);

                //set the new category as selected
                treeBean.setSelectedCategory(category);
                //update breadcrumbs
                breadcrumbs.updateBreadCrumbs();

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Save category",
                        "The category was successfully saved"));
            } catch (BiddingException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save category",
                        "An error occured while trying to save current category. Please try again later!"));
            }
        }
    }

    public void createNew() {
        Category parent = treeBean.getSelectedCategory();
        Category category = new Category();
        category.setId(0L);
        category.setParentId(parent.getId());
        category.setCreationDate(new Date(System.currentTimeMillis()));
        category.setDescription("");
        category.setName("New category");
        treeBean.setSelectedCategory(category);
    }

    public void remove() {
        Category category = service.getCategoryById(treeBean.getSelectedCategory().getId());
        if(category.getChildren() == null || category.getChildren().isEmpty()) {
            try {
                service.remove(category);
                treeBean.removeNode(category);
                //update breadcrumbs
                breadcrumbs.updateBreadCrumbs();

            } catch (BiddingException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove",
                        "An error occured while trying to remove current category. Please try again later!"));
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove",
                    "The category has other subcategories, remove subcategories first!"));
        }
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }

    public Breadcrumbs getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(Breadcrumbs breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
