package services;

import entities.Permissions;
import entities.PermissionsRoles;
import entities.Roles;

import java.util.List;

public class SvcPermissionRoles extends Service<PermissionsRoles>
{

    public SvcPermissionRoles()
    {
        super();
    }

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





}
