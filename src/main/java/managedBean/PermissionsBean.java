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

@Named
@SessionScoped
public class PermissionsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Permissions permission;
    private static final Logger log = Logger.getLogger(PermissionsBean.class);

    private String type;
    private String action;
    private final List<Permissions> listPer = getReadAll();

    private List<String> listAction = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        listAction = getPermissionsAction();
        permission = new Permissions();
    }

    public String flushPerm()
    {
        init();
        return "/tablePermissions?faces-redirect=true";
    }

    private String middlename;
    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
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
        log.debug("type : " + type);
        log.debug("listAction : " + listAction.size());
        return listAction;
    }
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
        log.debug("test type" + type);
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        log.debug("test action" + action);
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
}
