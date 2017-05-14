package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by coco on 05-May-17.
 */
@ManagedBean(name = "treeBean")
@ViewScoped
public class TreeBean implements Serializable {

    private TreeNode root;

    private TreeNode selectedNode;

    private Category selectedCategory;

    private Category rootCategory;

    @Inject
    private BiddingService biddingService;

    @PostConstruct
    public void init() {
        rootCategory = biddingService.getRoot();
        root = new DefaultTreeNode(rootCategory);
        buildTree(rootCategory,root);
    }

    public void buildTree(Category category, TreeNode parent){

        for(Category child: category.getChildren()){
            TreeNode node = new DefaultTreeNode(child);
            parent.getChildren().add(node);
            if(child.getChildren() == null || child.getChildren().isEmpty())
                continue;
            buildTree(child,node);
        }

    }
    public void onBreadcrumbSelect(Category category) {
        setSelectedCategory(category);
    }

    public void onCategorySelect(Category category) {
        setSelectedCategory(category);
    }

    private void expand(TreeNode treeNode){
        if (treeNode.getParent()!=null){
            treeNode.getParent().setExpanded(true);
            expand(treeNode.getParent());
        }
    }

    private TreeNode searchNodeForCategory(Category category, TreeNode node) {
        if(node.getData() != null && node.getData().equals(category)){
            return node;
        }
        for(TreeNode searchNode: node.getChildren()){
            if((searchNode = searchNodeForCategory(category,searchNode)) != null)
                return searchNode;
        }
        return null;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        selectedCategory = (Category)selectedNode.getData();

    }

    public void onNodeUnselect() {
        selectedCategory = rootCategory;
    }

    public void mergeNode(Category category) {
        Category parent = biddingService.getCategoryById(category.getParentId());

        if(parent != null) {
            TreeNode parentNode = searchNodeForCategory(parent, root);
            if (parentNode != null) {
                //check for existing child
                TreeNode childNode = searchNodeForCategory(category, parentNode);
                if (childNode != null) {
                    //replace with new data
                    parentNode.getChildren().remove(childNode);
                    parentNode.getChildren().add(new DefaultTreeNode(category));
                } else {
                    parentNode.getChildren().add(new DefaultTreeNode(category));
                    parent.getChildren().add(category);
                }
            }
        } else {
            throw new RuntimeException("The parent id of the category was not found into db");
        }
    }

    public void removeNode(Category category) {
        TreeNode node = searchNodeForCategory(category,root);
        if(node != null) {
            setSelectedCategory((Category)node.getParent().getData());
            List<Category> children = ((Category)node.getParent().getData()).getChildren();
            if(children != null)
                children.remove(category);
            node.getParent().getChildren().remove(node);

        }else{
            throw new RuntimeException("The corresponding node for the given category was not found in the tree");
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Category getSelectedCategory() {
        if(selectedCategory == null)
            selectedCategory = rootCategory;
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {

        TreeNode node = searchNodeForCategory(selectedCategory,root);
        if(selectedNode != null)
            selectedNode.setSelected(false);
        selectedNode = node;
        if(node != null) {
            selectedNode.setSelected(true);
            expand(selectedNode);

        }
        this.selectedCategory = selectedCategory;
    }

    public BiddingService getBiddingService() {
        return biddingService;
    }

    public void setBiddingService(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    public Category getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }

}
