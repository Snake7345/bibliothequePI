package managedBean;

import entities.Permissions;
import org.apache.log4j.Logger;
import services.SvcPermissions;

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
public class PermissionsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Permissions permission;
    private static final Logger log = Logger.getLogger(PermissionsBean.class);

    @PostConstruct
    public void init()
    {
        permission = new Permissions();
    }

    public String newPermission()
    {
        SvcPermissions service = new SvcPermissions();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver la permission");
        transaction.begin();

        try {

            service.save(permission);

            transaction.commit();
            log.debug("J'ai sauvé le permission");
            return "/tablePermissions.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));

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



    public String flushPerm()
    {
        init();
        return "/tablePermissions?faces-redirect=true";
    }

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les permissions
     */
    public List<Permissions> getReadAll()
    {
        SvcPermissions service = new SvcPermissions();
        List<Permissions> listPermissions = new ArrayList<Permissions>();
        listPermissions = service.findAllPermissions();
        service.close();
        return listPermissions;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }
}
