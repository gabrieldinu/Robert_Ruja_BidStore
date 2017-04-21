package ro.fortech.application.bidstore.backend.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


public class ResourceProvider {

    @PersistenceContext(unitName = "applicationBidStorePU")
    private EntityManager entityManager;

    @Produces
    @ApplicationScoped
    private EntityManager produceEntityManager(){
        return this.entityManager;
    }

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
