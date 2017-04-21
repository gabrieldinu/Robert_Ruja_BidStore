package ro.fortech.application.bidstore.backend.service.account;

import ro.fortech.application.bidstore.backend.persisetence.dao.UserDAO;

import javax.ejb.*;
import javax.inject.Inject;
import java.sql.Timestamp;

@Singleton
@Startup
public class AccountCheckerService {

    @Inject
    private UserDAO userDAO;

    //every 30 minute
   @Schedule(hour="*",minute="*/30",persistent = false)
    private void checkAccounts() {
       userDAO.deleteUserWithExpiredDate(new Timestamp(System.currentTimeMillis()));
   }
}
