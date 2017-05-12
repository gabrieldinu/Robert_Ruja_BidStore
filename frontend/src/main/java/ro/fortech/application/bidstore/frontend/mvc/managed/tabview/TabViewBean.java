package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultTreeNode;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.Breadcrumbs;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.TreeBean;
import ro.fortech.application.bidstore.frontend.mvc.model.PageState;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by coco on 10-May-17.
 */

@ManagedBean(name = "tabViewBean")
@ViewScoped
public class TabViewBean implements Serializable {

    private String content = "bidding";

    private Map<String, PageState> stateMap = new HashMap<>();

    @ManagedProperty(value = "#{treeBean}")
    private TreeBean treeBean;

    public void onTabChange(TabChangeEvent event) {
        //save page state
        PageState pageState = stateMap.get(content);
        if(pageState == null) {
            pageState = new PageState();
        }
        pageState.setSelectedCategory(treeBean.getSelectedCategory());
        //pageState.setSelectedTreeNode(treeBean.getSelectedNode());
        stateMap.put(content,pageState);

        this.content = (String) event.getTab().getAttributes().get("content");

        //get new state for next page
        pageState = stateMap.get(content);
        if(pageState != null) {
            treeBean.setSelectedCategory(pageState.getSelectedCategory());
            //treeBean.setSelectedNode(pageState.getSelectedTreeNode());
        } else {
            //reinit the tree for first time access of the tab
            treeBean.initTree();
            treeBean.setSelectedCategory(treeBean.getRootCategory());
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, PageState> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, PageState> stateMap) {
        this.stateMap = stateMap;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }
}
