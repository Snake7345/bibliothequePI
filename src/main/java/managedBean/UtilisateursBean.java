package managedBean;

import entities.Utilisateurs;
import enumeration.UtilisateurSexeEnum;
import services.SvcUtilisateurs;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UtilisateursBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Utilisateurs utilisateur;
    private final SvcUtilisateurs service = new SvcUtilisateurs();

    public UtilisateursBean()
    {
        super();
    }

    @PostConstruct
    public void init()
    {
        utilisateur = new Utilisateurs();
    }

    public String newUtil()
    {
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        transaction.begin();

        try {
            Utilisateurs u = this.utilisateur;
            service.save(u);

            transaction.commit();
            utilisateur = new Utilisateurs();
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();

                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));
                utilisateur = new Utilisateurs();
                return "";
            }

            service.close();
        }

    }

    public List<Utilisateurs> getReadAll()
    {
        List<Utilisateurs> listUtil = new ArrayList<Utilisateurs>();
             listUtil = service.findAllUtilisateurs();


        return listUtil;
    }


//-------------------------------Getter & Setter--------------------------------------------
    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }



}

