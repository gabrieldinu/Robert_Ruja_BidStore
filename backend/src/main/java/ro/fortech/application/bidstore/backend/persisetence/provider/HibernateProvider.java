package ro.fortech.application.bidstore.backend.persisetence.provider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Created by robert.ruja on 29-Mar-17.
 */
//@Named
public class HibernateProvider {

    private SessionFactory sessionFactory;

    //@Inject
    private HibernateConfig config;

    //@PostConstruct
    public void checkSessionFactory(){

        try {

            sessionFactory = config.getFactory();

        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
        System.out.println("SessionFactory Ok");
    }

    public Session getSession(){

        try{

            return sessionFactory.openSession();

        }catch(Exception ex){

            throw new RuntimeException(ex);
        }
    }
}
