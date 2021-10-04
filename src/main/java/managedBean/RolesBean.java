package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcPermissionRoles;
import services.SvcPermissions;
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
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class RolesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Roles role;
    private static final Logger log = Logger.getLogger(RolesBean.class);
    private List<Permissions> listPerm;
    private Permissions pe;
    private Permissions permissions;

    @PostConstruct
    public void init()
    {
        role = new Roles();
        listPerm=new ArrayList<>();
        pe = new Permissions();
        permissions = new Permissions();
    }


    public void addPermission()
    {
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        log.debug("l'action est " + pe.getAction());
        if(pe.getAction() == null || pe.getAction().equals(""))
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez rafrachir l'action pour ajouter une permission", null));
        }
        else
        {
            log.debug("je passe la dedans : " + pe.getAction());
            for (Permissions p : listPerm) {
                if (p.getType().equals(pe.getType()) && p.getAction().equals(pe.getAction())) {
                    flag = true;
                }
                if (p.getType().equals(pe.getType()) && p.getAction().equals("Lire")) {
                    flag2 = true;
                }
                if (p.getType().equals(pe.getType()) && p.getAction().equals("Creer")) {
                    flag3 = true;
                }

            }

            if (!flag) {
                log.debug("1");
                log.debug(pe.getAction() + " " + pe.getType());
                permissions.setAction(pe.getAction());
                permissions.setType(pe.getType());
                listPerm.add(permissions);
                permissions = new Permissions();
                if (!flag2 && (pe.getAction().equals("Creer") || pe.getAction().equals("Modification") || pe.getAction().equals("ActivDesactiv"))) {
                    if (!flag3 && pe.getAction().equals("Modification")) {
                        log.debug("2");
                        pe.setAction("Creer");
                        permissions.setAction(pe.getAction());
                        permissions.setType(pe.getType());
                        listPerm.add(permissions);
                        permissions = new Permissions();
                        log.debug(pe.getAction() + " " + pe.getType());
                    }
                    log.debug("3");
                    pe.setAction("Lire");
                    log.debug(pe.getAction() + " " + pe.getType());
                    permissions.setAction(pe.getAction());
                    permissions.setType(pe.getType());
                    listPerm.add(permissions);
                    permissions = new Permissions();
                }
                if (!flag3 && flag2 && pe.getAction().equals("Modification")) {
                    log.debug("4");
                    pe.setAction("Creer");
                    log.debug(pe.getAction() + " " + pe.getType());
                    permissions.setAction(pe.getAction());
                    permissions.setType(pe.getType());
                    listPerm.add(permissions);
                    permissions = new Permissions();
                }
                log.debug(Arrays.toString(listPerm.toArray()));
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La valeur est déjà dans le tableau", null));
            }
            pe = new Permissions();
        }
    }

    public void supPermission()
    {

        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        for(Permissions p : listPerm)
        {
            if(p.getType().equals(pe.getType()) && p.getAction().equals("Creer"))
            {
                flag = true;
            }
            if(p.getType().equals(pe.getType()) && p.getAction().equals("Modification"))
            {
                flag2 = true;
            }
            if(p.getType().equals(pe.getType()) && p.getAction().equals("ActivDesactiv"))
            {
                flag3 = true;
            }
        }



        if(pe.getAction().equals("Lire"))
        {
            if(!flag && !flag2 && !flag3){
                listPerm.remove(pe);
            }
            else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez supprimer les autres actions liées a ce type de permission avant", null));
            }
        }
        else if(pe.getAction().equals("Creer"))
        {
            if(!flag2){
                listPerm.remove(pe);
            }
            else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez supprimer l'action \"modifier\" avant de supprimer cette action", null));
            }
        }
        else if(pe.getAction().equals("Modification") || pe.getAction().equals("ActivDesactiv"))
        {
                listPerm.remove(pe);
        }

        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La valeur est déjà dans le tableau",null));
        }
        pe = new Permissions();
    }



    public String newRoles()
    {
        if(verifRoleExist(role))
        {
            save();
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
        }
        init();

        return "/tableRoles.xhtml?faces-redirect=true";
    }

    public boolean verifRoleExist(Roles rol)
    {
        SvcRoles serviceR = new SvcRoles();
        if(serviceR.findRole(rol.getDenomination()).size() > 0)
        {
            serviceR.close();
            return false;
        }
        else {
            serviceR.close();
            return true;
        }

    }

    public String activdesactivRol()
    {
        if(role.isActif())
        {
            if(role.getDenomination().equals("Client") || role.getDenomination().equals("Employe") || role.getDenomination().equals("Manager") || role.getDenomination().equals("Administrateur"))
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ce role ne peut être désactivé",null));
            }
            else
            {
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

    public boolean checkPermission(String permission)
    {
        try
        {
            log.debug("test permissions de checkPermission ");
            log.debug(SecurityUtils.getSubject().isPermitted(permission));
            return SecurityUtils.getSubject().isPermitted(permission);

        }
        catch(Error nre)
        {
            log.debug("une erreur est survenue...");
        }
        return false;

    }

    public String redirectModifRole(){
        for (PermissionsRoles pr: role.getPermissionsRoles()) {
            listPerm.add(pr.getPermissions());
        }
        return "/formEditRole.xhtml?faces-redirect=true";
    }

    public String modifRole()
    {
        save();
        init();
        return "/tableRoles.xhtml?faces-redirect=true";
    }

    public void save()
    {
        SvcRoles service = new SvcRoles();
        SvcPermissions serviceP = new SvcPermissions();
        SvcPermissionRoles servicePR = new SvcPermissionRoles();
        EntityTransaction transaction = service.getTransaction();
        servicePR.setEm(service.getEm());
        transaction.begin();
        try {
            service.save(role);
            service.refreshEntity(role);
            if(role.getPermissionsRoles().size()>0){
                for(PermissionsRoles pr : role.getPermissionsRoles()){
                    servicePR.delete(pr.getIdPermissionsRoles());
                }
            }
            for(Permissions p : listPerm)
            {
                if(serviceP.findOnePermission(p).size()>0){
                    servicePR.save(servicePR.createPermissionRoles(serviceP.findOnePermission(p).get(0),role));
                }
                else{
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
                    break;
                }
            }
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
    public String flushRolNew()
    {
        init();
        return "/formNewRole?faces-redirect=true";
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

    public List<Permissions> getListPerm() {
        return listPerm;
    }

    public void setListPerm(List<Permissions> listPerm) {
        this.listPerm = listPerm;
    }

    public Permissions getPe() {
        return pe;
    }

    public void setPe(Permissions pe) {
        this.pe = pe;
    }
}
