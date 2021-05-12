package converter;

import entities.Editeurs;
import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcEditeurs;
import services.SvcRoles;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "editeurConverter")
public class EditeurConverter implements Converter {
    private static final Logger log = Logger.getLogger(EditeurConverter.class);
    private final SvcEditeurs service = new SvcEditeurs();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            int id = Integer.parseInt(s);

            return service.getById(id);
        } else
            return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            log.debug("test vallues " + o.toString());
            return String.valueOf(((Editeurs) o).getIdEditeurs());
        }
        else
            return null;
    }
}