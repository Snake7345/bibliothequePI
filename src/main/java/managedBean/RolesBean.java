package managedBean;

import entities.Permissions;
import entities.PermissionsRoles;
import entities.Roles;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcPermissionRoles;
import services.SvcPermissions;
import services.SvcRoles;
import services.SvcUtilisateurs;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Named
@SessionScoped
public class RolesBean implements Serializable {

    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Roles role;
    private Roles roleArchetype;
    private static final Logger log = Logger.getLogger(RolesBean.class);
    private List<Permissions> listPerm;
    private Permissions pe;
    private Permissions permissions;

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        role = new Roles();
        listPerm=new ArrayList<>();
        pe = new Permissions();
        permissions = new Permissions();
    }


    /*Méthode qui permet d'ajouter une liste de permissions liées a un archétype*/
    public void addArchetype()
    {
        SvcPermissions serviceP = new SvcPermissions();
        listPerm.clear();
        listPerm = serviceP.findPermissionsFromRoles(roleArchetype.getIdRoles());
        serviceP.close();
    }

    /*Méthode qui permet de vider le tableau des permissions*/
    public void clearListPermissions()
    {
        if(listPerm.size()>0)
        {
            listPerm.clear();
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La liste des permissions est vide", null));
        }
    }

    /*Cette méthode permet d'ajouter une permission a un rôle, analyse les permissions et ajoute si nécéssaire les autres permissions.
    * Si les permissions ne sont pas rafraichies alors affichage d'un message d'erreur*/
    public void addPermission()
    {
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        log.debug("1");
        /*On vérifie si l'utilisateur nous a pas encodé une action/un type vide*/
        if(pe.getAction() == null || pe.getAction().equals(""))
        {
            log.debug("2");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez rafrachir l'action pour ajouter une permission", null));
        }
        else
        {
            log.debug("4");
            for (Permissions p : listPerm) {
                /*Si une permission choisie se trouve déjà dans le tableau, alors on marque un message d'erreur*/
                if ((p.getType().equals(pe.getType()) && p.getAction().equals(pe.getAction()))) {
                    flag = true;
                    log.debug("5");
                }
                /*Si Lire est présent dans le tableau, on en prend compte*/
                if (p.getType().equals(pe.getType()) && p.getAction().equals("Lire")) {
                    flag2 = true;
                    log.debug("6");
                }
                /*Si Lire est présent dans le tableau, on en prend compte*/
                if (p.getType().equals(pe.getType()) && p.getAction().equals("Creer")) {
                    flag3 = true;
                    log.debug("7");
                }

            }
            if (!flag) {
                log.debug("8");
                permissions.setAction(pe.getAction());
                permissions.setType(pe.getType());
                listPerm.add(permissions);
                permissions = new Permissions();
                /*Si lire n'est pas dans le tableau et que l'action choisie est créer, modificiation ou activeDesactive, alors on ajoute lire*/
                if (!flag2 && (pe.getAction().equals("Creer") || pe.getAction().equals("Modification") || pe.getAction().equals("ActiveDesactive"))) {log.debug("9");
                    /*Si créer n'est pas dans le tableau et que l'action choisie est "modification" alors on ajoute créer*/
                    if (!flag3 && pe.getAction().equals("Modification")) {
                        log.debug("10");
                        pe.setAction("Creer");
                        permissions.setAction(pe.getAction());
                        permissions.setType(pe.getType());
                        listPerm.add(permissions);
                        permissions = new Permissions();
                    }
                    pe.setAction("Lire");
                    permissions.setAction(pe.getAction());
                    permissions.setType(pe.getType());
                    listPerm.add(permissions);
                    permissions = new Permissions();
                    log.debug("11");

                }
                /*Si créer n'est pas dans le tableau mais que lire y est, et que l'action choisie est "modification" alors on ajoute créer*/
                if (!flag3 && flag2 && pe.getAction().equals("Modification")) {
                    pe.setAction("Creer");
                    permissions.setAction(pe.getAction());
                    permissions.setType(pe.getType());
                    listPerm.add(permissions);
                    permissions = new Permissions();
                    log.debug("12");
                }
            } else {
                log.debug("13");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La valeur est déjà dans le tableau", null));
            }
        }
    }
    /*Cette méthode permet de supprimer une permission a un objet "rôle", cette méthode affichera un message d'erreur si l'utilisateur supprime une permission liées à une autre permission*/
    public void supPermission() {

        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        for (Permissions p : listPerm) {
            if (p.getType().equals(pe.getType()) && p.getAction().equals("Creer")) {
                flag = true;
            }
            if (p.getType().equals(pe.getType()) && p.getAction().equals("Modification")) {
                flag2 = true;
            }
            if (p.getType().equals(pe.getType()) && p.getAction().equals("ActiveDesactive")) {
                flag3 = true;
            }
        }
        if(listPerm.contains(pe))
        {
            /*Pour pouvoir supprimer la permission Lire il faut supprimer toutes les autres actions, Créer, ActivDesactiv, Modifier*/
            if (pe.getAction().equals("Lire")) {
                if (!flag && !flag2 && !flag3) {
                    listPerm.remove(pe);
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez supprimer les autres actions liées a ce type de permission avant", null));
                }
                /*Pour pouvoir supprimer la permission Lire il faut supprimer l'action modifier */
            } else if (pe.getAction().equals("Creer")) {
                if (!flag2) {
                    listPerm.remove(pe);
                } else {
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Veuillez supprimer l'action \"modifier\" avant de supprimer cette action", null));
                }
            } else {
                listPerm.remove(pe);
            }
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Une erreur est survenue",null));
        }
        pe = new Permissions();
    }


    /*Cette méthode permet de vérifier si un objet "rôle" avec une même dénomination existe (avec la méthode verifRoleExist) et permet d'appeler la méthode save qui va sauvegarder l'objet "rôle" et les permissions liées */
    public String newRoles()
    {
        log.debug("role " + role.getPermissionsRoles());
        log.debug("role " + role.getDenomination());
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
    /*Méthode qui permet de vérifier si le rôle existe*/
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
    /*Cette méthode permet la désactivation d'un objet "rôle" TANT que ce n'est pas l'archétype de base du programme et permet également la réactivation d'un rôle*/
    public String activdesactivRol()
    {
        listPerm=new ArrayList<>();
        if(role.getPermissionsRoles().size()>0) {
            for (PermissionsRoles pr : role.getPermissionsRoles()) {
                listPerm.add(pr.getPermissions());
            }
        }

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
                log.debug("desactivation role " + role.getDenomination());
                role.setActif(false);
            }

        }
        else
        {
            role.setActif(true);
        }
        save();
        listPerm=new ArrayList<>();
        return "/tableRoles.xhtml?faces-redirect=true";
    }
    /*méthode utiliser pour vérifier si un utilisateur a une permission dans le cadre d'un rendered ou d'un c:if*/
    public boolean checkPermission(String permission)
    {
        try
        {
            return SecurityUtils.getSubject().isPermitted(permission);

        }
        catch(Error nre)
        {
            log.debug("une erreur est survenue...");
        }
        return false;

    }

    /*Cette méthode permet de charger les permissions d'un rôle ainsi que ces informations et de retourner le formulaire de modification d'un rôle*/
    public String redirectModifRole(){
        for (PermissionsRoles pr: role.getPermissionsRoles()) {
            listPerm.add(pr.getPermissions());
        }
        return "/formEditRole.xhtml?faces-redirect=true";
    }
    /*Cette méthode permet la modification d'un rôle et de retourner sur la table des rôles*/
    public String modifRole()
    {
        save();
        init();
        return "/tableRoles.xhtml?faces-redirect=true";
    }
    /*Cette méthode permet la sauvegarde d'un objet "rôle" en db*/
    public void save()
    {
        log.debug("entree save");
        SvcRoles serviceR = new SvcRoles();
        SvcPermissions serviceP = new SvcPermissions();
        SvcPermissionRoles servicePR = new SvcPermissionRoles();
        servicePR.setEm(serviceR.getEm());
        EntityTransaction transaction = serviceR.getTransaction();
        transaction.begin();
        try {
            serviceR.save(role);
            if(role.getPermissionsRoles() != null) {
                if (role.getPermissionsRoles().size() > 0) {
                    for (PermissionsRoles pr : role.getPermissionsRoles()) {
                        servicePR.delete(pr.getIdPermissionsRoles());
                    }
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
            serviceR.close();
        }

    }
    /*Cette méthode permet de vider les variables et retourne la table des rôles*/
    public String flushRol()
    {
        init();
        return "/tableRoles?faces-redirect=true";
    }
    /*Cette méthode permet de vider les variables et retourne le formulaire de création de rôle*/
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
     * Méthode qui permet via le service de retourner la liste de tous les roles sauf le role "Client"
     */
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

    public Roles getRoleArchetype() {
        return roleArchetype;
    }

    public void setRoleArchetype(Roles roleArchetype) {
        this.roleArchetype = roleArchetype;
    }
}
