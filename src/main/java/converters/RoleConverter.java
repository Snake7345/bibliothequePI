package converters;

import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcRoles;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;


@Named
@FacesConverter(value = "roleConverter")
public class RoleConverter implements Converter {
    private static final Logger log = Logger.getLogger(RoleConverter.class);
    private final SvcRoles service = new SvcRoles();

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
            return String.valueOf(((Roles) o).getIdRoles());
        }
        else
            return null;
    }
}
