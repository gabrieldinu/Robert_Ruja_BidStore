package ro.fortech.application.bidstore.backend.util;

import ro.fortech.application.bidstore.backend.util.sql.UserManagementSql;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Properties;
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

    @Produces @UserManagementSql
    private String produceUserManagementSql() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("sql_queries.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("ro.fortech.application.bidstore.backend.persistence.dao.managingUsers.sql");
    }
}
