package managedBean;

import entities.PermissionsRoles;
import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcPermissionRoles;
import services.SvcRoles;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class PermissionsRolesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private PermissionsRoles permissionsRoles;
    private static final Logger log = Logger.getLogger(PermissionsRolesBean.class);

    public void save()
    {
        SvcPermissionRoles service = new SvcPermissionRoles();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(permissionsRoles);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
            }
            service.close();
        }
    }

    //-------------------------------Getter & Setter--------------------------------------------

}
