package ro.fortech.application.bidstore.backend.persisetence.provider;

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

    public Session getSession(){

        try {

            return manager.unwrap(Session.class);

        }catch(Exception ex){

            throw new RuntimeException(ex);
        }
    }
}
