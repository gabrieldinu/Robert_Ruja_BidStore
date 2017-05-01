package ro.fortech.application.bidstore.frontend.mvc.managed.tabview;

import ro.fortech.application.bidstore.backend.model.BidStatus;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coco on 30-Apr-17.
 */

@ManagedBean(name = "contentView")
@ViewScoped
public class ContentView implements Serializable {

    private String content;

    private List<Item> itemList;

    private List<Item> allItems;

    @PostConstruct
    public void init(){

       itemList = new ArrayList<>();
       allItems = new ArrayList<>();
       for(int i=1;i<20;i++){
           allItems.add(new Item((long)i, "intel i"+i, "some long long long description here, just writitng something to fill this up",
                   223.7*i, 130.2*i, 3L*i, new Date(System.currentTimeMillis()/i),new Date(System.currentTimeMillis()/i), i%2==0?BidStatus.OPEN:BidStatus.CLOSED, null));
       }
       updateItemList(1,10,null,false);
        renderItemList();

    }

    public void updateItemList(int page, int pageSize, String sortBy, boolean ascending) {
        this.itemList = allItems.subList(--page*pageSize, page*pageSize + pageSize > allItems.size()-1?allItems.size()-1:page*pageSize+pageSize);
    }

    public void renderItemList(){
        this.content = "item_list";
    }

    public void renderSingleItem(){
        this.content = "single_item";
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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
}
