package managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

import entities.Utilisateurs;

@Named
@SessionScoped

public class LoginBean implements Serializable {
    /*Déclaration des variables*/
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LoginBean.class);
    @PersistenceUnit  (unitName = "bibliotheque")
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotheque");
    public static EntityManager em;


    Utilisateurs utilisateurAuth = new Utilisateurs();

    //---------------------------------------------------------
    /*
     * Méthode qui permet l'authentification de l'utilisateur,
     * on vérifie que l'utilisateur existe dans la base de données, si il existe
     * on vérifie son rôle et on affiche la page d'accueil correspondant à son role
     *
     * */
    public String auth()
    {
        log.debug("---------------------------------debut--------------------------");
        em = emf.createEntityManager();
        FacesMessage m = new FacesMessage("Login ou mot de passe incorrect");

        try {
            List<Utilisateurs> results = em.createNamedQuery("Utilisateurs.authentify", Utilisateurs.class)
                    .setParameter("login", utilisateurAuth.getCourriel())
                    .setParameter("mdp", utilisateurAuth.getMdp())
                    .getResultList();

            if (results.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, m);
                return "login";

            }
            else {
                utilisateurAuth = results.get(0);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userAuth", utilisateurAuth);
                return "bienvenue";
            }

        }

        catch(NoResultException nre)
        {
            FacesMessage errorMessage = new FacesMessage("Login ou mot de passe incorrect");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            return "login";
        }

    }

    /*Cette méthode permet la deconnexion de l'utilisateur*/
    public String deconnexion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        return null;
    }

    //-------------------------Getter & Setter--------------------------------------------------------------------------------

    public Utilisateurs getUtilisateurAuth() {
        return utilisateurAuth;
    }
    public void setUtilisateurAuth(Utilisateurs utilisateurAuth) {
        this.utilisateurAuth = utilisateurAuth;
    }

}
