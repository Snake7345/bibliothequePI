package managedBean;

import entities.Permissions;
import org.apache.log4j.Logger;
import services.SvcPermissions;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@SessionScoped
public class PermissionsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Permissions permission;
    private static final Logger log = Logger.getLogger(PermissionsBean.class);

    private String type;
    private String middlename;
    private String action;
    private final List<Permissions> listPer = getReadAll();
    private List<String> listAction = new ArrayList<>();


    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        listAction = getPermissionsAction();
        permission = new Permissions();
        type ="";
        action="";

    }
    //Méthode qui permet de vider les variables et de revenir sur le table des permissions
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

    /*
     * Méthode qui permet de retourner la liste de toutes les types de permissions
     */
    public List<String> getPermissionsType()
    {

        List<String> listType = new ArrayList<>();
        for(Permissions p : listPer)
        {
            if(!listType.contains(p.getType()))
            {
                listType.add(p.getType());
            }
        }
        return listType;
    }
    /*
     * Méthode qui permet de retourner la liste de toutes les types de permissions si l'utilisateur connecté est un manager
     */
    public List<String> getPermissionsTypeManager()
    {
        List<String> listType = new ArrayList<>();
        for(Permissions p : listPer)
        {
            if(!listType.contains(p.getType()))
            {
                if(!Objects.equals(p.getType(), "Permissions"))
                listType.add(p.getType());
            }
        }
        return listType;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de toutes les actions de permissions
     */
    public List<String> getPermissionsAction()
    {

        if (listAction.size()>0){
            listAction.clear();
        }
        for(Permissions p : listPer)
        {
            if (p.getType().equals(type)) {
                listAction.add(p.getAction());
            }
        }
        return listAction;
    }
    /*il faut un commentaire*/
    public void stateChangeListener(ValueChangeEvent event) {
        if (event.getNewValue() != type) {
            getPermissionsAction();
        }
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

    public List<Permissions> getListPer() {
        return listPer;
    }

    public List<String> getListAction() {
        return listAction;
    }

    public void setListAction(List<String> listAction) {
        this.listAction = listAction;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }
}
