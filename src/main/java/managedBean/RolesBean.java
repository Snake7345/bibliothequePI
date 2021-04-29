package managedBean;

import entities.Roles;
import services.SvcRoles;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class RolesBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private final SvcRoles service = new SvcRoles();

    public List<Roles> getReadAll()
    {
        List<Roles> listRole = new ArrayList<Roles>();
        listRole = service.findAllRolesActiv();


        return listRole;
    }

}
