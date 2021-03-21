package enumeration;

import java.io.BufferedReader;

public enum FactureEtatEnum
{
    ENCOURS("en cours"),
    TERMINER("terminer"),
    ANNULE("annule");

    private String label;

    FactureEtatEnum(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }

}
