package converter;

import entities.Bibliotheques;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "biblioConverter")
public class BiblioConverter implements Converter {
    private static final Logger log = Logger.getLogger(BiblioConverter.class);
    private final SvcBibliotheques service = new SvcBibliotheques();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            int id = Integer.parseInt(s);
            log.debug(service.getById(id));
            return service.getById(id);
        } else
            return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            return String.valueOf(((Bibliotheques) o).getIdBibliotheques());
        }
        else
            return null;
    }
}