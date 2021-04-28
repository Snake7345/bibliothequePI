package managedBean;

import entities.Utilisateurs;
import enumeration.UtilisateurSexeEnum;
import services.SvcUtilisateurs;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
public class UtilisateursBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Utilisateurs utilisateur;
    private Object utilisateursexe;


    private UtilisateurSexeEnum[] getUtilisateurSexeEnum()
    {
        return UtilisateurSexeEnum.values();
    }


    public UtilisateursBean()
    {
        super();
    }

    @PostConstruct
    public void init()
    {
        utilisateursexe = UtilisateurSexeEnum.A;
    }

    public String newUtil()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        transaction.begin();

        try {
            Utilisateurs u = this.utilisateur;
            service.save(u);

            transaction.commit();

            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }

            service.close();
        }
    }


//-------------------------------Getter & Setter--------------------------------------------
    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object getutilisateursexe() {
        return utilisateursexe;
    }

    public void setutilisateursexe(Object utilisateursexe) {
        this.utilisateursexe = utilisateursexe;
    }

}

