package ro.fortech.application.bidstore.backend.persistence.provider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;


/**
 * Created by robert.ruja on 29-Mar-17.
 */
@Named
public class HibernateSessionProvider {

    @Inject
    private EntityManager manager;

    private SessionFactory factory;

    public Session getSession(){

        try {
            if(manager != null)
                return manager.unwrap(Session.class);
            else
                return factory.getCurrentSession();
        }catch(Exception ex){

            throw new RuntimeException(ex);
        }
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }
}
