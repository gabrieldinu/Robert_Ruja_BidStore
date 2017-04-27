package ro.fortech.application.bidstore.frontend.mvc.managed.account;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import ro.fortech.application.bidstore.backend.exception.account.AccountException;
import ro.fortech.application.bidstore.backend.persistence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.service.account.UserAccountService;
import ro.fortech.application.bidstore.backend.service.bidding.UserBiddingService;
import ro.fortech.application.bidstore.frontend.mvc.model.LazyUserDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@ManagedBean(name = "userMgt")
@ViewScoped
public class UserManagement implements Serializable {

    @Inject
    UserBiddingService service;

    @Inject
    UserAccountService userAccountService;

    private LazyDataModel lazyModel;

    private BiddingUser selectedUser;

    private String managedUsername;

    @Inject
    FacesContext context;

    @PostConstruct
    public void init() {
        lazyModel = new LazyUserDataModel(service);
    }

    public void enable(){
        try {
            userAccountService.enableUser(managedUsername);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "The user was enabled"));
        }catch(AccountException ex){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "There was a problem updating the user!"));
            }
    }

    public void disable(){
        try {
            userAccountService.disableUser(managedUsername);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "The user was disabled"));
        }catch(AccountException ex){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "There was a problem updating the user!"));

        }
    }

    public LazyDataModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel lazyModel) {
        this.lazyModel = lazyModel;
    }

    public UserBiddingService getService() {
        return service;
    }

    public void setService(UserBiddingService service) {
        this.service = service;
    }

    public BiddingUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(BiddingUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void onRowSelect(SelectEvent event) {
        this.selectedUser = ((BiddingUser) event.getObject());
    }

    public String getManagedUsername() {
        return managedUsername;
    }

    public void setManagedUsername(String managedUsername) {
        this.managedUsername = managedUsername;
    }
}
