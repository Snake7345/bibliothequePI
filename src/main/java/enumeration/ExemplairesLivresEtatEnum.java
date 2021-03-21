package enumeration;

public enum ExemplairesLivresEtatEnum
{
        BON("Bon"),
        MOYEN("Moyen"),
        MAUVAIS("Mauvais");

        private String label;

    ExemplairesLivresEtatEnum(String label)
    {
            this.label = label;
    }

    public String getLabel()
    {
            return label;
    }
}
