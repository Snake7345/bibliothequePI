package services;

import entities.Editeurs;
import entities.Permissions;
import entities.PermissionsRoles;
import entities.Roles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPermissionRoles extends Service<PermissionsRoles> implements Serializable
{

    public SvcPermissionRoles()
    {
        super();
    }

    // Méthode qui permet de sauver une permissionsRoles et de le mettre en DB
    @Override
    public PermissionsRoles save(PermissionsRoles permissionsRoles)
    {
        if (permissionsRoles.getIdPermissionsRoles() == 0) {
            em.persist(permissionsRoles);
        } else {
            permissionsRoles = em.merge(permissionsRoles);
        }

        return permissionsRoles;
    }


    public PermissionsRoles createPermissionRoles(Permissions p, Roles r)
    {
        PermissionsRoles pr = new PermissionsRoles();
        pr.setPermissions(p);
        pr.setRole(r);

        return pr;
    }

    public List<PermissionsRoles> findPermissionsAndRoles(int permission, int role) {
        Map<String, Integer> param = new HashMap<>();
        param.put("permission", permission);
        param.put("role", role);
        return finder.findByNamedQuery("PermissionsRoles.findPermissionsRoles", param);
    }


}
