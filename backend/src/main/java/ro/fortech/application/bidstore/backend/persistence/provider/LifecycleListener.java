package ro.fortech.application.bidstore.backend.persistence.provider;

import javax.persistence.PrePersist;

/**
 * Created by robert.ruja on 29-Mar-17.
 */
public class LifecycleListener<T> {

    @PrePersist
    void prePersist(T object){
        System.out.println("+++++++ Pre Persist");
    }
}
