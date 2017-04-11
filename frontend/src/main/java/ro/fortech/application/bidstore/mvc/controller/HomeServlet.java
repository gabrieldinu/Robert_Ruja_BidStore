package ro.fortech.application.bidstore.mvc.controller;

import ro.fortech.application.bidstore.managed.AccountBean;

import javax.faces.bean.ManagedProperty;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Created by robert.ruja on 10-Apr-17.
 */
@WebServlet("/faces")
public class HomeServlet extends HttpServlet{

    @ManagedProperty("#{accountBean}")
    private AccountBean accountBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
//        if(!accountBean.isLoggedIn()){
//            return "account/singnin";
//        }
    }
}
