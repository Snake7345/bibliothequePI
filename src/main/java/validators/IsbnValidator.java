package validators;

import org.apache.log4j.Logger;
import services.SvcLivres;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("IsbnValidator")
public class IsbnValidator implements Validator {

    private static final String isbnReg = "^[\\d-]+$";

    private Pattern pattern;
    private Matcher matcher;

    public IsbnValidator(){
        pattern = Pattern.compile(isbnReg);
    }

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException
    {
        final Logger log = Logger.getLogger(IsbnValidator.class);
        String ISBN = String.valueOf(value);
        SvcLivres serviceL = new SvcLivres();
        matcher = pattern.matcher(value.toString());

        int count = 0;
        for (int i = 0; i < ISBN.length(); i++) {
            if (Character.isDigit(ISBN.charAt(i))){
                count++;
            }
        }

        if (!matcher.matches())
        {
            FacesMessage msg = new FacesMessage("La donnee ne doit contenir que des tirets (-) et des chiffres (de 0 à 9)");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        else if((ISBN.length() != 10) && (ISBN.length() != 13) &&(ISBN.length() != 17))
        {
            FacesMessage msg = new FacesMessage("Pour encoder l'isbn vous devez mettre 10,13 ou 17 caracteres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        /*TODO : Compter le nombre de chiffre sans les tirets doit être = a 10 ou 13 */
        else if(serviceL.findByIsbn(ISBN).size() != 0)
        {
            FacesMessage msg = new FacesMessage("Cette ISBN existe déjà en DB");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        else if (count!=10 && count!=13){
            FacesMessage msg = new FacesMessage("Pour encoder l'isbn vous devez mettre 10 ou 13 Chiffres");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        serviceL.close();


    }
}