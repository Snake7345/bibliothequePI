package managedBean;

import entities.Roles;
import org.apache.log4j.Logger;
import services.SvcPermissionRoles;
import services.SvcRoles;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class RolesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Roles role;
    private static final Logger log = Logger.getLogger(RolesBean.class);

    @PostConstruct
    public void init()
    {
        role = new Roles();
    }

    public String newRoles()
    {
        //Todo mettre/faire une verification de l'objet role
        save();
        return "/tableRoles.xhtml?faces-redirect=true";
    }

    public String activdesactivRol()
    {
        if(role.isActif())
        {
            if(role.getDenomination().equals("Client") || role.getDenomination().equals("Employe") || role.getDenomination().equals("Manager") || role.getDenomination().equals("Administrateur"))
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"les roles Administrateur, manager, Employe et Client, ne peuvent être désactivés",null));
            }
            else
            {
                log.debug("je passe le if de désactive");
                role.setActif(false);
            }

        }
        else
        {
            role.setActif(true);
        }
        save();
        return "/tableRoles.xhtml?faces-redirect=true";
    }

    //TODO a optimiser
    public boolean checkPermission(int permission, int role)
    {
        boolean flag = false;
        SvcPermissionRoles service = new SvcPermissionRoles();
        try
        {
            log.debug("je passe dans le check permission" + service.findPermissionsAndRoles(permission, role));
            if(!service.findPermissionsAndRoles(permission,role).isEmpty())
            {
                log.debug("mon flag est true");
                flag = true;
            }
        }
        catch(NoResultException nre)
        {
            log.debug("pas de résultat trouvé");
        }
        return flag;
    }

    public void save()
    {
        SvcRoles service = new SvcRoles();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(role);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
            }
            service.close();
        }

    }

    public String flushRol()
    {
        init();
        return "/tableRoles?faces-redirect=true";
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les roles
     */
    public List<Roles> getReadAll()
    {
        SvcRoles service = new SvcRoles();
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRoles();


        return listRole;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les roles actifs
     */

    public List<Roles> getReadActiv()
    {
        SvcRoles service = new SvcRoles();
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRolesActiv();


        return listRole;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les roles inactifs
     */
    public List<Roles> getReadInactiv()
    {
        SvcRoles service = new SvcRoles();
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRolesInactiv();


        return listRole;
    }

    public List<Roles> getReadActivUtil()
    {
        SvcRoles service = new SvcRoles();
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRolesActivUtil();


        return listRole;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
