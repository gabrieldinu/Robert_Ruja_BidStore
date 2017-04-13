package ro.fortech.application.bidstore.mvc.controller;

import ro.fortech.application.bidstore.backend.exception.AccountActivationException;
import ro.fortech.application.bidstore.backend.service.UserAccountService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by robert.ruja on 10-Apr-17.
 */
@WebServlet("/activate")
public class ActivationServlet extends HttpServlet{

    @Inject
    private UserAccountService service;

    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {

        String sUUID = request.getParameter("activationId");

        if(sUUID != null){

            try {

                service.activateAccount(sUUID);
                response.sendRedirect("/BidStore/view/account/confirmActivation.xhtml");

            }catch(AccountActivationException ex){
                response.sendRedirect("/BidStore/view/account/signin.xhtml");
            }
        } else {
            response.sendRedirect("/BidStore/view/account/signin.xhtml");
        }
    }
}
