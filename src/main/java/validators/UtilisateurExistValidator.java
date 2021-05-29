package validators;


import services.SvcUtilisateurs;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("utilisateurExistValidator")
public class UtilisateurExistValidator implements Validator
{

    public UtilisateurExistValidator()
    {

    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String login = (String) o;
        FacesMessage msg = new FacesMessage("Le pseudo est deja utilise, veuillez en choisir un autre");

        SvcUtilisateurs serviceU = new SvcUtilisateurs();

        try {
            if (!serviceU.findByLogin(login).isEmpty())
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);

        } finally {
            serviceU.close();
        }
    }


}