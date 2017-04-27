package ro.fortech.application.bidstore.frontend.util;

import ro.fortech.application.bidstore.backend.service.mail.ConfiguredMailService;
import ro.fortech.application.bidstore.backend.service.mail.MailService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by robert.ruja on 12-Apr-17.
 */
public class ResourceProvider {

    @Produces
    @RequestScoped
        private FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @RequestScoped
    private HttpServletResponse produceHttpServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @ApplicationScoped
    private ExternalContext produceExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    @Produces
    @ApplicationScoped
    private Properties getConfigProperties(){
        Properties properties = new Properties();
        InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("config/config.properties");
        try {
            properties.load(is);
            return properties;
        } catch (IOException e) {
            return null;
        }
    }

    @Produces
    @ViewScoped
    @ConfiguredMailService
    MailService getMailService() {

        return new MailService(getConfigProperties());

    }
}
