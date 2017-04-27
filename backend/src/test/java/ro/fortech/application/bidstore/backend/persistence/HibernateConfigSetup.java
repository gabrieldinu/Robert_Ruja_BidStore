package ro.fortech.application.bidstore.backend.persistence;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by robert.ruja on 26-Apr-17.
 */
public class HibernateConfigSetup {

    private ServiceRegistry serviceRegistry;
    private SessionFactory factory;

    public void configure(){
        try {
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            factory = config.buildSessionFactory();
        } catch (HibernateException ex){
            throw new RuntimeException(ex);
        }
    }
    public SessionFactory getFactory(){

        if(this.factory == null) {

            this.configure();
        }
        return this.factory;
    }
}
