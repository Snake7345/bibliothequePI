package validators;

import services.SvcExemplairesLivres;
import services.SvcTarifs;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("tarifValidator")
public class TarifValidator implements Validator
{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String CB = (String) o;


        SvcTarifs serviceT = new SvcTarifs();
        //manque
        /*if (serviceT)
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"le tarif existe déjà",null));
        }
*/

        serviceT.close();
    }

}
