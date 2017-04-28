package ro.fortech.application.bidstore.frontend.mvc.managed.category;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by robert.ruja on 28-Apr-17.
 */

@ManagedBean(name = "categoryTree")
@ViewScoped
public class TreeView  implements Serializable {

    private TreeNode root;

    private String someData = "Initial";

    @PostConstruct
    public void init(){
        root = new DefaultTreeNode("Root", null);
        root.getChildren().add(new DefaultTreeNode("Node 2"));
        root.getChildren().add(new DefaultTreeNode("Node 3"));
    }
    public TreeNode getRoot() {
        return root;
    }
    public void updateTree(){
        root.getChildren().add(new DefaultTreeNode("Some other node"));
        this.someData = "Updated";
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }
}
