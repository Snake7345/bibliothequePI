package converter;

import entities.Localites;
import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcLocalites;
import services.SvcRoles;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;


@Named
@FacesConverter(value = "localiteConverter")
public class LocaliteConverter implements Converter {
    private static final Logger log = Logger.getLogger(LocaliteConverter.class);
    private final SvcLocalites service = new SvcLocalites();

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
            return String.valueOf(((Localites) o).getIdLocalites());
        }
        else
            return null;
    }
}