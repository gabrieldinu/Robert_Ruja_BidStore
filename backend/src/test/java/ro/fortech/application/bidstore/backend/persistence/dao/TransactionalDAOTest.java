package ro.fortech.application.bidstore.backend.persistence.dao;

import org.junit.After;
import org.junit.Before;

/**
 * Created by robert.ruja on 26-Apr-17.
 */
public abstract class TransactionalDAOTest extends AbstractDAOTest{
    @Before
    public void begin() {
        sessionProvider.getSession().beginTransaction();
    }
    @After
    public void commit(){
        sessionProvider.getSession().getTransaction().commit();
    }
}
