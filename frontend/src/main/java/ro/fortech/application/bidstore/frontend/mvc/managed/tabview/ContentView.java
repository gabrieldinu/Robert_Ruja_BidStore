package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.persistence.entity.*;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.common.TreeBean;
import ro.fortech.application.bidstore.frontend.util.Paginator;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;



@ManagedBean(name = "contentView")
@ViewScoped
public class ContentView implements Serializable {

    private String content;

    private List<Item> allItems;

    private String searchText = "";

    private Paginator paginator = new Paginator();

    @Inject
    private UserAccount userAccount;

    @Inject
    private BiddingService biddingService;

    @ManagedProperty(value = "#{treeBean}")
    private TreeBean treeBean;

    @PostConstruct
    public void init(){
        allItems = biddingService.getFullItemList();
        renderItemList();
    }

    public List<ItemDetails> getItemList() {
        List<ItemDetails> tempList = biddingService.getItems(
                treeBean.getSelectedCategory(),
                paginator.getSortBy(),
                paginator.isAscending(),
                searchText,
                new HashMap<String,Object>(){
                    {

                    }
                });

        //number of pages should be computed from db

        paginator.setItemCount(tempList.size());
        paginator.compute();

        return tempList.subList(paginator.getStartIndex(), paginator.getEndIndex());
    }

    public List<String> completeText(String query) {

        return biddingService.getItemsNameCointains(query);

    }

    public void renderItemList(){
        this.content = "item_list";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public BiddingService getBiddingService() {
        return biddingService;
    }

    public void setBiddingService(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }

}
