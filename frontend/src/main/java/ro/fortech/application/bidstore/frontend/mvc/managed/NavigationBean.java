package ro.fortech.application.bidstore.frontend.mvc.managed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by robert.ruja on 27-Apr-17.
 */

@SessionScoped
@ManagedBean(name = "naviBean")
public class NavigationBean implements Serializable {

    private String page;

    @PostConstruct
    public void init() {
        page = "content";
    }

    public String loadContent() {
        return "/view/public/content";
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
