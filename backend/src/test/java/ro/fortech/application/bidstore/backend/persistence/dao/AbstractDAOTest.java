package ro.fortech.application.bidstore.backend.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import ro.fortech.application.bidstore.backend.persistence.HibernateConfigSetup;
import ro.fortech.application.bidstore.backend.persistence.provider.HibernateSessionProvider;

/**
 * Created by robert.ruja on 26-Apr-17.
 */
public abstract class AbstractDAOTest {

    public static HibernateSessionProvider sessionProvider;

    public static HibernateConfigSetup hibernateConfigSetup;

    @BeforeClass
    public static void setUp() {
        sessionProvider = new HibernateSessionProvider();
        hibernateConfigSetup = new HibernateConfigSetup();
        hibernateConfigSetup.configure();
        sessionProvider.setFactory(
                hibernateConfigSetup.getFactory()
        );
    }

}
