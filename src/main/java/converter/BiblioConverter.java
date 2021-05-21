package converter;

import entities.Bibliotheques;
import entities.Editeurs;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcEditeurs;

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

            return service.getById(id);
        } else
            return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null) {
            log.debug("test vallues " + o.toString());
            return String.valueOf(((Bibliotheques) o).getIdBibliotheques());
        }
        else
            return null;
    }
}