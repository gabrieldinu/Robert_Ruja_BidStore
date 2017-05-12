package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.category;

import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.TreeBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by robert.ruja on 11-May-17.
 */
@ManagedBean(name = "categoryEdit")
@ViewScoped
public class CategoryEditBean implements Serializable {

    @ManagedProperty(value = "treeBean")
    private TreeBean treeBean;

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }
}
