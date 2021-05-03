package managedBean;

import entities.Pays;
import entities.Penalites;
import entities.Permissions;
import org.apache.log4j.Logger;
import services.SvcPays;
import services.SvcPermissions;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PermissionsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Permissions permission;
    private final SvcPermissions service = new SvcPermissions();
    private static final Logger log = Logger.getLogger(PermissionsBean.class);

    public List<Permissions> getReadAll()
    {
        List<Permissions> listPermissions = new ArrayList<Permissions>();
        listPermissions = service.findAllPermissions();


        return listPermissions;
    }

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }
}
