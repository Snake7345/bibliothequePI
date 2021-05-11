package managedBean;

import entities.Auteurs;
import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcAuteurs;
import services.SvcPermissions;
import services.SvcRoles;

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
public class RolesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Roles role;
    private static final Logger log = Logger.getLogger(RolesBean.class);
    private Roles rolTemp;

    @PostConstruct
    public void init()
    {
        role = new Roles();
    }

    public String newRoles()
    {
        SvcRoles service = new SvcRoles();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver le role");
        transaction.begin();

        try {

            service.save(role);

            transaction.commit();
            log.debug("J'ai sauvé le role");
            return "/tableRoles.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }

            service.close();
        }

    }

    public String activdesactivRol()
    {
        SvcRoles service = new SvcRoles();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        try
        {
            transaction.begin();
            /*Si la voiture est active alors on la désactive*/
            if(role.isActif())
            {
                log.debug("je passe le if de désactive");
                role.setActif(false);
            }

            else
            {
                role.setActif(true);
            }


            service.save(role);

            transaction.commit();
            log.debug("J'ai modifié le role");
            return "/tableAuteurs.xhtml?faces-redirect=true";
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }
            service.close();
        }
    }

    public void save()
    {
        SvcRoles service = new SvcRoles();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            rolTemp.setFields(role);
            service.save(rolTemp);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("Erreur inconnue"));
            }
            service.close();
        }

    }

    public String flushRol()
    {
        init();
        return "tableRoles?faces-redirect=true";
    }


    public void edit()
    {
        this.rolTemp = role.clone();
    }

    public Roles getRolTemp() {
        return rolTemp;
    }

    public void setRolTemp(Roles rolTemp) {
        this.rolTemp = rolTemp;
    }

    public List<Roles> getReadAll()
    {
        SvcRoles service = new SvcRoles();
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRoles();


        return listRole;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
