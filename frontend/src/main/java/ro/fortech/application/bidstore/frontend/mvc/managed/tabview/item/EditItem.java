package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.item;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;
import ro.fortech.application.bidstore.frontend.util.ConfigProperties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by robert.ruja on 17-May-17.
 */

@ManagedBean
@ViewScoped
public class EditItem implements Serializable {

    @Inject
    private BiddingService service;

    @Inject
    private FacesContext context;

    @ManagedProperty(value = "#{userAccount}")
    private UserAccount userAccount;

    @Inject
    @ConfigProperties
    private Properties config;

    private Part part;

    private File file;

    private String content = "first";

    private String imagesPath;

    private List<String> selectedCategories = new ArrayList<>();

    private Item item;

    private String initialPrice;

    private Date opening;

    private Date closing;

    @PostConstruct
    public void init() {
        imagesPath = config.getProperty("application.external.resource.path");
    }

    public List<Category> getCategories() {
        return service.getCategories(null,false,null);
    }

    public void createNew() {
        this.item = new Item();
        this.item.setOwner(userAccount.getUser().getUsername());
    }

    public void cancel() {
        this.item = null;
        this.content = "first";
        this.selectedCategories = new ArrayList<>();
        this.initialPrice = null;
        this.opening = null;
        this.closing = null;
    }

    public void save() {
        setItemCategories(selectedCategories);
        if(this.file != null) {
            item.setUrl(file.getName());
        }
        try {
            service.saveItem(item);
            context.addMessage("itemMessages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Save item",
                    "The item was successfully saved"));
            cancel();
        }catch(BiddingException ex) {
            context.addMessage("itemMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save item",
                    "There was a problem while trying to save the item into db. Please try again later!"));
            cancel();
        }
    }

    public void upload() {
        File dir = new File(imagesPath + File.separator + item.getOwner());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        try (InputStream input = part.getInputStream()) {
            this.file = new File( imagesPath + File.separator + item.getOwner() + File.separator + "item_" + UUID.randomUUID() + part.getSubmittedFileName());
            Files.copy(input, file.toPath());
        }
        catch (IOException e) {
            context.addMessage("itemMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload picture",
                    "There was a problem while trying to upload the item. Please try again later!"));
        }
    }

    public void cancelUpload() {
        this.file = null;
    }

    public void edit(ItemDetails item) {
        this.item = service.getItemWithId(item.getItemId());
        this.initialPrice = item.getInitialPrice()+"";
        this.opening = item.getOpeningDate();
        this.closing = item.getClosingDate();
        this.selectedCategories = new ArrayList<>();
        for(Category category: this.item.getCategories()){
            this.selectedCategories.add(category.getId().toString());
        }
    }

    private void setItemCategories(List<String> selectedCategories) {
        Map<Long, Category> categoryMap = service.getCategoriesAsMap();
        Set<Category> categorySet = new HashSet<>();
        for(String categoryId : selectedCategories) {
            Category category = categoryMap.get(Long.parseLong(categoryId));
            if(category != null) {
                categorySet.add(category);
                //add all parents
                while(category.getParentId() != null) {
                    category = categoryMap.get(category.getParentId());
                    categorySet.add(category);
                }
            }
        }
        item.setCategories(categorySet);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<String> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.item.setInitialPrice(Double.parseDouble(initialPrice));
        this.initialPrice = initialPrice;
    }

    public Date getOpening() {
        return opening;
    }

    public void setOpening(Date opening) {
        this.item.setOpeningDate(new java.sql.Date(opening.getTime()));
        this.opening = opening;
    }

    public Date getClosing() {
        return closing;
    }

    public void setClosing(Date closing) {
        this.item.setClosingDate(new java.sql.Date(closing.getTime()));
        this.closing = closing;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Part getFile() {
        return part;
    }

    public void setFile(Part file) {
        this.part = file;
    }

}

