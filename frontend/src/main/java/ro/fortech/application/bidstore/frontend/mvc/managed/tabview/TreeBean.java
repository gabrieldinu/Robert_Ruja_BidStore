package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

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
@SessionScoped
public class TreeBean implements Serializable {

    private TreeNode root;

    private TreeNode selectedNode;

    private Category selectedCategory;

    @Inject
    private BiddingService biddingService;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode();
        buildTree(biddingService.getRoot(), root);
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

    private void expand(TreeNode treeNode){
        if (treeNode.getParent()!=null){
            treeNode.getParent().setExpanded(true);
            expand(treeNode.getParent());
        }
    }

    private TreeNode searchNodeForCateogory(Category category, TreeNode node) {

        if(node.getData().equals(category)){
            return node;
        }
        for(TreeNode searchNode: node.getChildren()){
            if((searchNode.getData()).equals(category)){
                return searchNode;
            }
            searchNodeForCateogory(category,searchNode);
        }
        return node;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        selectedCategory = (Category)selectedNode.getData();

    }

    public void onNodeExpand(NodeSelectEvent event){
        TreeNode expandedNode = event.getTreeNode();
        Category category = (Category)expandedNode.getData();
        addChildren(expandedNode, category.getId());
    }

    public void addChildren(TreeNode node, Long parentId){

        List<TreeNode> children = node.getChildren();
        children.clear();
        for(Category fetchCategory: biddingService.getCategoriesWithParentId(parentId)){
            children.add(new DefaultTreeNode(fetchCategory));
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
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        expand(searchNodeForCateogory(selectedCategory, root));
        this.selectedCategory = selectedCategory;
    }

    public BiddingService getBiddingService() {
        return biddingService;
    }

    public void setBiddingService(BiddingService biddingService) {
        this.biddingService = biddingService;
    }
}
