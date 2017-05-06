package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import ro.fortech.application.bidstore.backend.model.BidStatus;
import ro.fortech.application.bidstore.backend.persistence.entity.*;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.Paginator;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@ManagedBean(name = "contentView")
@ViewScoped
public class ContentView implements Serializable {

    private String content;

    private List<Item> allItems;

    private String searchText = "";

    private Item selectedItem;

    private Bid currentItemBid;


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


    public List<Item> getItemList() {
        List<Item> tempList = new ArrayList<>();
        Item item;
        //filter
        //todo:db search
        for(int i = 0; i < allItems.size(); i++){
            item = allItems.get(i);
            if(treeBean.getSelectedCategory() != null && !item.hasCategory(treeBean.getSelectedCategory()))
                continue;
            if(searchText !=null && !item.getName().toLowerCase().contains(searchText.toLowerCase()))
                continue;
            tempList.add(item);
        }

        paginator.setItemCount(tempList.size());
        paginator.calculate();
        if(paginator.getSortBy() != null) {
            Collections.sort(tempList, (o1 ,o2) -> {
                   if(paginator.isAscending())
                       return o1.getName().compareToIgnoreCase(o2.getName());
                   else
                       return o2.getName().compareToIgnoreCase(o1.getName());
            });
        }
        return tempList.subList(paginator.getStartIndex(), paginator.getEndIndex());
    }

    public List<String> completeText(String query) {
        //todo: db search should be done
        List<String> results = new ArrayList<String>();
        for(Item item: getItemList()){
            if(item.getName().contains(query))
                results.add(item.getName());
        }
        return results;
    }

    public List<Bid> getBid(Item item, User user) {
        //todo: db search
        return new ArrayList<>();
    }


    public void renderItemList(){
        this.content = "item_list";
    }

    public void renderSingleItem(Item item){

       this.selectedItem = item;
       this.content = "single_item";
       //db search
//        this.currentItemBid = new Bid();
//        currentItemBid.setBidDate(new Timestamp(System.currentTimeMillis()));
//        currentItemBid.setBidValue(123.3);
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

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public Bid getCurrentItemBid() {
        return currentItemBid;
    }

    public void setCurrentItemBid(Bid currentItemBid) {
        this.currentItemBid = currentItemBid;
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
