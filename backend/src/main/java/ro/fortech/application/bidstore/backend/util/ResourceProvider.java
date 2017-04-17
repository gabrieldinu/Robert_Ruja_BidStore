package ro.fortech.application.bidstore.backend.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class ResourceProvider {

    @PersistenceContext(unitName = "applicationBidStorePU")
    private EntityManager entityManager;

    @Produces
    @ApplicationScoped
    private EntityManager produceEntityManager(){
        return this.entityManager;
    }
}
