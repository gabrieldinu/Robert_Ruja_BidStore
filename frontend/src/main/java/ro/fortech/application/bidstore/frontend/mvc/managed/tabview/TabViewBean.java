package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import org.primefaces.event.TabChangeEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by coco on 10-May-17.
 */

@ManagedBean(name = "tabViewBean")
@SessionScoped
public class TabViewBean implements Serializable {

    private String content = "bidding";

    public void onTabChange(TabChangeEvent event) {
        this.content = (String) event.getTab().getAttributes().get("content");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
