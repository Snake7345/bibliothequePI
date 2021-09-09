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

    private String type;
    private String action;

    @PostConstruct
    public void init()
    {
        type="";
        action="";
        permission = new Permissions();
    }

/*
Méthode désactivée pour raison de stabilité

    public String newPermission()
    {
        /*SvcPermissions service = new SvcPermissions();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        if (checkPermExist()) {
            try {

                service.save(permission);

                transaction.commit();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));
                return "/tablePermissions.xhtml?faces-redirect=true";
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas reussie", null));

                    return "";
                } else {
                    init();
                }

                service.close();
            }
        }
        else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cette permission existe déjà en base de donnée", null));
            return "/tablePermissions.xhtml?faces-redirect=true";
        }

        return "";
    }

    // Méthode qui vérifie si une permission est déjà existante dans la base de donnée ou non
    public boolean checkPermExist(){
        SvcPermissions serviceP = new SvcPermissions();
        return (serviceP.findOnePermission(permission.getDenomination()).size() == 0);
}*/

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

    public List<String> getPermissionsType()
    {
        List<String> listType = new ArrayList<>();
        List<Permissions> listPer= getReadAll();
        for(Permissions p : listPer)
        {
            if(!listType.contains(p.getType()))
            {
                listType.add(p.getType());
            }
        }
        log.debug("listType : " + listType.size());
        return listType;
    }
    public List<String> getPermissionsAction(Object ty)
    {
        List<String> listAction = new ArrayList<>();
        List<Permissions> listPer= getReadAll();
        for(Permissions p : listPer)
        {
            if(p.getType().equals(ty.toString()))
            {

                listAction.add(p.getAction());
            }
        }
        log.debug("type : " + type);
        log.debug("listAction : " + listAction.size());
        return listAction;
    }


    //-------------------------------Getter & Setter--------------------------------------------

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
