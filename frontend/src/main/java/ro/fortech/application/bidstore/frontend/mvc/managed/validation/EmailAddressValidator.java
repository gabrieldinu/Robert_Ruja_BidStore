package ro.fortech.application.bidstore.frontend.mvc.managed.validation;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FacesValidator(value = "emailValidator")
@ViewScoped
public class EmailAddressValidator implements Validator {

    private static final String REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private static final Pattern PATTERN = Pattern.compile(REGEX,Pattern.CASE_INSENSITIVE);

    private Matcher matcher;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String emailText = (String) o;
        matcher = PATTERN.matcher(emailText);
        FacesMessage facesMessage;
        if(!matcher.find()){
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid email address !",null);
            throw new ValidatorException(facesMessage);
        }

    }
}
