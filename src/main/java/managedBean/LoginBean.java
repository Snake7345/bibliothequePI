package managedBean;

import entities.PermissionsRoles;
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


    Utilisateurs utilisateurAuth = new Utilisateurs();

    //---------------------------------------------------------
    /*
     * Méthode qui permet l'authentification de l'utilisateur,
     * on vérifie que l'utilisateur existe dans la base de données, mais également qu'il a la permission de se connecter
     *
     *
     * */
    public void auth()
    {
        log.debug("---------------------------------debut--------------------------");
        FacesMessage m = new FacesMessage("Login ou/et mot de passe incorrect");
        SvcUtilisateurs service= new SvcUtilisateurs();
        RolesBean RB = new RolesBean();

        try {
            log.debug(utilisateurAuth.getLogin() + " + " + utilisateurAuth.getMdp());
            log.debug("1");
            List<Utilisateurs> results = service.authentify(utilisateurAuth.getLogin(),utilisateurAuth.getMdp());
            log.debug("2");
            if (SecurityManager.processToLogin(utilisateurAuth.getLogin(), utilisateurAuth.getMdp(), false)){

                log.debug("OKAY");
                log.debug("TEST PERMISSION");
                log.debug(SecurityUtils.getSubject().isPermitted("Roles:Lire"));
                log.debug(SecurityUtils.getSubject().getSession().getAttribute(utilisateurAuth.getLogin()));
                log.debug("FIN TESTT");
                utilisateurAuth = results.get(0);
                SecurityUtils.getSubject().getSession().setAttribute("role", utilisateurAuth.getRoles());
                SecurityUtils.getSubject().getSession().setAttribute("user", utilisateurAuth);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userAuth", utilisateurAuth);
                FacesContext.getCurrentInstance().getExternalContext().redirect("bienvenue.xhtml");

            }

            //TODO : Faire comprendre a Shiro qu'il doit intégrer cette permission
            /*else if(!RB.checkPermission(48,results.get(0).getRoles().getIdRoles())){
                m = new FacesMessage("Connexion refuse: utilisateur non autorise");
                FacesContext.getCurrentInstance().addMessage(null, m);

            }*/


        } catch (IOException e) {
            e.printStackTrace();
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
