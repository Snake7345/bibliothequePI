package enumeration;

public enum ExemplairesLivresEtatEnum
{
        Bon("Bon"),
        Moyen("Moyen"),
        Mauvais("Mauvais");

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
