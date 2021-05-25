package enumeration;

public enum FactureEtatEnum
{
    encours("encours"),
    terminer("terminer"),
    annule("annule");

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
