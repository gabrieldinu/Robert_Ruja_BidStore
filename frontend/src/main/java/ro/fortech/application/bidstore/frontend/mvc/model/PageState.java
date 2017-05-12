package ro.fortech.application.bidstore.frontend.mvc.model;

import org.primefaces.model.TreeNode;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;


/**
 * Created by robert.ruja on 12-May-17.
 */
public class PageState {

    private Category selectedCategory;

    private TreeNode selectedTreeNode;

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public TreeNode getSelectedTreeNode() {
        return selectedTreeNode;
    }

    public void setSelectedTreeNode(TreeNode selectedTreeNode) {
        this.selectedTreeNode = selectedTreeNode;
    }
}
