package managedBean;

import entities.Bibliotheques;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import security.SecurityManager;
import services.SvcUtilisateurs;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    private String login;
    private String mdp;
    private final Bibliotheques bibliactuel = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");



    Utilisateurs utilisateurAuth = new Utilisateurs();

    //---------------------------------------------------------
    /*
     * Méthode qui permet l'authentification de l'utilisateur,
     * on vérifie que l'utilisateur existe dans la base de données, et on lui attribue des variables de session comme le role par exemple.
     *
     *
     * */
    public void auth()
    {

        FacesMessage m = new FacesMessage("Login ou/et mot de passe incorrect");
        SvcUtilisateurs service= new SvcUtilisateurs();
        RolesBean RB = new RolesBean();

        try {
            List<Utilisateurs> results = service.findByLogin(login);
            if(bibliactuel.isActif()) {
                if (SecurityManager.processToLogin(login, mdp, false)) {

                    utilisateurAuth = results.get(0);
                    SecurityUtils.getSubject().getSession().setAttribute("role", utilisateurAuth.getRoles());
                    SecurityUtils.getSubject().getSession().setAttribute("user", utilisateurAuth);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userAuth", utilisateurAuth);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("bienvenue.xhtml");

                }
            }
            else
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"la bibliotheque n'est pas active",null));
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*Cette méthode permet la deconnexion de l'utilisateur*/
    public String deconnexion(){
        utilisateurAuth = new Utilisateurs();
        if(SecurityManager.processToLogout())
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Votre session est bien déconnectée",null));
            return "/index.xhtml?faces-redirect=true";
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Une erreur s'est produite",null));
            return "/index.xhtml?faces-redirect=true";
        }
    }

    //-------------------------Getter & Setter--------------------------------------------------------------------------------

    public Utilisateurs getUtilisateurAuth() {
        return utilisateurAuth;
    }
    public void setUtilisateurAuth(Utilisateurs utilisateurAuth) {
        this.utilisateurAuth = utilisateurAuth;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Bibliotheques getBibliactuel() {
        return bibliactuel;
    }
}
