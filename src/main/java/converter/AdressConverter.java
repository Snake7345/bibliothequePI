package converter;

import entities.Adresses;
import org.apache.log4j.Logger;
import services.SvcAdresses;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "adressConverter")
public class AdressConverter implements Converter {
    private static final Logger log = Logger.getLogger(EditeurConverter.class);
    private final SvcAdresses service = new SvcAdresses();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            int id = Integer.parseInt(s);
            log.debug(service.getById(id).getNumero());
            log.debug(service.getById(id).getRue());
            log.debug(service.getById(id).getLocalites().getVille());
            return service.getById(id);
        } else
            return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            log.debug("test vallues " + o.toString());
            return String.valueOf(((Adresses) o).getIdAdresses());
        }
        else
            return null;
    }
}