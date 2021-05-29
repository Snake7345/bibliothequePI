/*package validators;


import services.SvcUtilisateurs;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.validation.Validator;

@FacesValidator("utilisateursExists")
public class UtilisateursExists implements Validator
{

    public UtilisateursExists()
    {

    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String login = (String) o;

        SvcUtilisateurs serviceU = new SvcUtilisateurs();

        try {
            if (serviceU.findByLogin(login) != null)
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message.translate(login + " est dÃ©jÃ  utilisÃ©"), null));

        } finally {
            serviceU.close();
        }
    }


}
*/