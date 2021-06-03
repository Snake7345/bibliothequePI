package validators;

import entities.Adresses;
import services.SvcAdresses;
import services.SvcExemplairesLivres;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("adressValidator")
public class AdressValidator implements Validator
{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        SvcAdresses serviceA = new SvcAdresses();

        if (serviceA.findOneAdresse((Adresses) o).size() == 0)
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"L'adresse existe déjà",null));
        }

        serviceA.close();
    }

}

