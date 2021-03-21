package enumeration;

public enum UtilisateurSexeEnum
{
    MADAME("F"),
    MONSIEUR("M"),
    AUTRE("A");

    private String label;

    UtilisateurSexeEnum(String label)
    {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

