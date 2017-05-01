package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by robert.ruja on 28-Apr-17.
 */

@ManagedBean(name = "categoryTree")
@ViewScoped
public class CategoryView implements Serializable {

    private TreeNode root;

    private String someData = "Initial";

    private String description = "";

    @PostConstruct
    public void init(){
        updateTree();
    }

    public void updateTree(){
        root = new DefaultTreeNode("");
        root.getChildren().add(populateNew("Desktop PC"));
        root.getChildren().add(populateNew("PC Components"));
        root.getChildren().add(populateNew("Laptops"));
        root.getChildren().add(populateNew("Laptop Accessiories"));
        this.someData = "Updated";
    }

    private TreeNode populateNew(String name){
        TreeNode root = new DefaultTreeNode(name);
        root.getChildren().add(new DefaultTreeNode("Node 2"));
        root.getChildren().add(new DefaultTreeNode("Node 3"));
        root.getChildren().add(new DefaultTreeNode("Some other node"));
        return root;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
