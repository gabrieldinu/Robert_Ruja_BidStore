package ro.fortech.application.bidstore.frontend.mvc.controller;

import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.service.account.UserAccountService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by robert.ruja on 18-Apr-17.
 */
@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet{

    @Inject
    private UserAccountService service;

    @Inject
    private UserAccount userAccount;

    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {

        String token = request.getParameter("token");

        if(token != null){

                UserAuth userAuth =  service.getRequestChangePassowrd(token);

                if(userAuth != null) {

                    userAccount.setUserAuth(userAuth);
                    userAccount.setLoggedIn(true);
                    response.sendRedirect("/BidStore/view/account/resetPassword.xhtml");

                } else {

                    response.sendRedirect("/BidStore/view/public/error.xhtml");
                }

        } else {
            response.sendRedirect("/BidStore/view/public/account/signin.xhtml");
        }
    }
}
