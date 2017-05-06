package ro.fortech.application.bidstore.backend.persistence.provider;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.inject.Named;

/**
 * Created by robert.ruja on 31-Mar-17.
 */
@Named
public class HibernateConfig {

    private ServiceRegistry serviceRegistry;
    private SessionFactory factory;

    public void configure(){
        try {
            Configuration config = new Configuration();
            //serviceRegistry = new ServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").buildServiceRegistry();
            factory = config.buildSessionFactory(serviceRegistry);
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
