package managedBean;

import entities.Auteurs;
import entities.Roles;
import org.apache.log4j.Logger;
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
    private final SvcRoles service = new SvcRoles();

    @PostConstruct
    public void init()
    {
        role = new Roles();
    }

    public String newRoles()
    {
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

    public List<Roles> getReadAll()
    {
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRolesActiv();


        return listRole;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
