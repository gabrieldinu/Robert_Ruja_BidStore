package ro.fortech.application.bidstore.frontend.mvc.controller.filter;

import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by robert.ruja on 12-Apr-17.
 */
public class AuthSecurityFilter implements Filter{

    @Inject
    UserAccount userAccount;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        boolean allowedPages = path.contains("/view/public/") || path.contains("/activate?");

        if(userAccount.isLoggedIn()){
            if(allowedPages) {
                response.sendRedirect("/BidStore/view/main.xhtml");
                return;
            }
        }else{
            if(!allowedPages) {
                response.sendRedirect("/BidStore/view/public/account/signin.xhtml");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
