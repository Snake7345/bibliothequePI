package converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter extends DateTimeConverter
{
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String regex = getMandatoryAttribute(component, "validateRegex");
        String pattern = getMandatoryAttribute(component, "convertPattern");

        if (value != null && !value.matches(regex)) {
            throw new ConverterException(new FacesMessage(String.format("Date invalide, la mettre sous le format %s", pattern)));
        }

        setPattern(pattern);
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        setPattern(getMandatoryAttribute(component, "convertPattern"));
        return super.getAsString(context, component, value);
    }

    private String getMandatoryAttribute(UIComponent component, String name) {
        String value = (String) component.getAttributes().get(name);

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(String.format("<f:attribute name=\"%s\"> is missing.", name));
        }

        return value;
    }

}
