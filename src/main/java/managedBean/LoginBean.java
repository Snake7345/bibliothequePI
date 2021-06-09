package managedBean;

import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcUtilisateurs;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped

public class LoginBean implements Serializable {
    /*Déclaration des variables*/
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LoginBean.class);
    @PersistenceUnit  (unitName = "bibliotheque")


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
        FacesMessage m = new FacesMessage("Login ou/et mot de passe incorrect");
        SvcUtilisateurs service= new SvcUtilisateurs();
        RolesBean RB = new RolesBean();

        try {
            log.debug(utilisateurAuth.getLogin() + " + " + utilisateurAuth.getMdp());
            List<Utilisateurs> results = service.authentify(utilisateurAuth.getLogin(),utilisateurAuth.getMdp());

            if (results.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, m);
                return "login";

            }
            else if(!RB.checkPermission(48,results.get(0).getRoles().getIdRoles())){
                m = new FacesMessage("Connexion refuse: utilisateur non autorise");
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
        log.debug("test deco " + utilisateurAuth.getNom());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        utilisateurAuth = new Utilisateurs();
        log.debug("test deco2 " + utilisateurAuth.getNom());
        return "login";
    }

    //-------------------------Getter & Setter--------------------------------------------------------------------------------

    public Utilisateurs getUtilisateurAuth() {
        return utilisateurAuth;
    }
    public void setUtilisateurAuth(Utilisateurs utilisateurAuth) {
        this.utilisateurAuth = utilisateurAuth;
    }

}
