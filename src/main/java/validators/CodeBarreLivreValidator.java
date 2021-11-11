package validators;

import entities.ExemplairesLivres;
import services.SvcExemplairesLivres;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.List;

@FacesValidator("codeBarreLivreValidator")
public class CodeBarreLivreValidator implements Validator
{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String CB = (String) o;


        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        List<ExemplairesLivres> L = serviceEL.findOneByCodeBarre(CB);
        if (L.size() == 0)
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le code barre n'existe pas",null));
        }
        else if (L.get(0).isLoue())
        {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le livre est déjà loué",null));
        }
        else if(!L.get(0).isActif()){throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Le livre ne peut être loué ou transferé",null));}

        serviceEL.close();
    }

}
