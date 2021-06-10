package validators;

import services.SvcExemplairesLivres;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("auteurValidator")
public class AuteurValidator implements Validator
{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String CB = (String) o;


        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();

        if (serviceEL.findOneByCodeBarre(CB).size() == 0)
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le code barre n'existe pas",null));
        }

        serviceEL.close();
    }

}
