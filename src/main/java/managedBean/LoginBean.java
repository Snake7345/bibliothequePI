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
            log.debug(login + " + " + mdp);
            log.debug("1");
            List<Utilisateurs> results = service.findByLogin(login);
            log.debug("2");
            if(bibliactuel.isActif()) {
                if (SecurityManager.processToLogin(login, mdp, false)) {

                    log.debug("OKAY");
                    utilisateurAuth = results.get(0);
                    log.debug(utilisateurAuth.getIdUtilisateurs());
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
    public String deconnexion(){
        /*log.debug("test deco " + utilisateurAuth.getNom());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        utilisateurAuth = new Utilisateurs();
        log.debug("test deco2 " + utilisateurAuth.getNom());
        return "login";*/
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
