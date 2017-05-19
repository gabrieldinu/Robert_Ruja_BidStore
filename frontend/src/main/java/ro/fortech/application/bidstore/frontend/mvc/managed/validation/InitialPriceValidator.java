package ro.fortech.application.bidstore.frontend.mvc.managed.validation;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by robert.ruja on 18-May-17.
 */

@FacesValidator(value = "initialPriceValidator")
@ViewScoped
public class InitialPriceValidator implements Validator {

    private static final String REGEX = "^[1-9]\\d*(\\.\\d+)?$";

    private static final Pattern PATTERN = Pattern.compile(REGEX,Pattern.CASE_INSENSITIVE);

    private Matcher matcher;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String initialPriceText = (String) o;
        matcher = PATTERN.matcher(initialPriceText);
        FacesMessage facesMessage = null;
        if(!matcher.find()){
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Please enter a valid decimal value!",null);
            throw new ValidatorException(facesMessage);
        }
    }
}
