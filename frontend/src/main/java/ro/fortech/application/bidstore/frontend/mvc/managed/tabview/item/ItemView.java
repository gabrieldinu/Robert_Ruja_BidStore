package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.item;

import ro.fortech.application.bidstore.backend.model.BidStatus;
import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by coco on 14-May-17.
 */

@ManagedBean
@ViewScoped
public class ItemView implements Serializable {

    @Inject
    private BiddingService service;

    private String content = "sell";

    public BiddingService getService() {
        return service;
    }

    public List<ItemDetails> getItems() {
        return new ArrayList<ItemDetails>(){{
            ItemDetails details = new ItemDetails();
            details.setBestBid(20.0);
            details.setBidCount(100L);
            details.setCategories(new ArrayList<String>(){{add("CPUs");add("Socket");}});
            details.setClosingDate(new Date(System.currentTimeMillis()));
            details.setDescription("some description");
            details.setItemName("First Item");
            details.setOpeningDate(new Date(System.currentTimeMillis()));
            details.setWinner("Gigi");
            details.setStatus(BidStatus.CLOSED);
            add(details);

            details = new ItemDetails();
            details.setBestBid(30.0);
            details.setBidCount(103L);
            details.setCategories(new ArrayList<String>(){{add("Motherboards");add("Sockets");}});
            details.setClosingDate(new Date(System.currentTimeMillis()));
            details.setDescription("some another description");
            details.setItemName("Second Item");
            details.setOpeningDate(new Date(System.currentTimeMillis()));
            details.setWinner("Chelu");
            details.setStatus(BidStatus.OPEN);
            add(details);
        }};
    }

    public void setService(BiddingService service) {
        this.service = service;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
