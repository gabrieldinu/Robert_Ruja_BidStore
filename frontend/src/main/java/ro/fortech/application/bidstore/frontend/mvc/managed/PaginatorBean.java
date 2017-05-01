package ro.fortech.application.bidstore.frontend.mvc.managed;

import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.ContentView;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by coco on 01-May-17.
 */

@ManagedBean
@ViewScoped
public class PaginatorBean implements Serializable {

    private int pageSize = 10;
    private int pageCount;
    private int page = 1;
    private String sortBy;
    private boolean ascending;

    @ManagedProperty(value = "#{contentView}")
    private ContentView contentView;

    public void updateContent() {
        contentView.updateItemList(page,pageSize,sortBy,ascending);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        updateContent();
    }

    public int getPageCount() {
        int size = contentView.getAllItems().size();
        int count = size / pageSize;
        int remainder = size % pageSize;
        return remainder == 0 ? count:count+1;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        updateContent();
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
        updateContent();
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
        updateContent();
    }

    public ContentView getContentView() {
        return contentView;
    }

    public void setContentView(ContentView contentView) {
        this.contentView = contentView;
    }
}
