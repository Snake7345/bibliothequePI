package entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries
        ({
                @NamedQuery(name = "PermissionsRoles.findAll", query = "SELECT p FROM PermissionsRoles p"),
        })
@Entity
@Table(name = "permissions_roles", schema = "bibliotheque")
public class PermissionsRoles {
    private int permissionsIdPermissions;
    private int rolesIdRoles;
    private int idPermissionsRoles;
    private Permissions permissionsByPermissionsIdPermissions;
    private Roles rolesByRolesIdRoles;

    @Basic
    @Column(name = "PermissionsIdPermissions", nullable = false)
    public int getPermissionsIdPermissions() {
        return permissionsIdPermissions;
    }

    public void setPermissionsIdPermissions(int permissionsIdPermissions) {
        this.permissionsIdPermissions = permissionsIdPermissions;
    }

    @Basic
    @Column(name = "RolesIdRoles", nullable = false)
    public int getRolesIdRoles() {
        return rolesIdRoles;
    }

    public void setRolesIdRoles(int rolesIdRoles) {
        this.rolesIdRoles = rolesIdRoles;
    }

    @Id
    @Column(name = "IdPermissionsRoles", nullable = false)
    public int getIdPermissionsRoles() {
        return idPermissionsRoles;
    }

    public void setIdPermissionsRoles(int idPermissionsRoles) {
        this.idPermissionsRoles = idPermissionsRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionsRoles that = (PermissionsRoles) o;
        return permissionsIdPermissions == that.permissionsIdPermissions &&
                rolesIdRoles == that.rolesIdRoles &&
                idPermissionsRoles == that.idPermissionsRoles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionsIdPermissions, rolesIdRoles, idPermissionsRoles);
    }

    @ManyToOne
    @JoinColumn(name = "PermissionsIdPermissions", referencedColumnName = "IdPermissions", nullable = false)
    public Permissions getPermissionsByPermissionsIdPermissions() {
        return permissionsByPermissionsIdPermissions;
    }

    public void setPermissionsByPermissionsIdPermissions(Permissions permissionsByPermissionsIdPermissions) {
        this.permissionsByPermissionsIdPermissions = permissionsByPermissionsIdPermissions;
    }

    @ManyToOne
    @JoinColumn(name = "RolesIdRoles", referencedColumnName = "IdRoles", nullable = false)
    public Roles getRolesByRolesIdRoles() {
        return rolesByRolesIdRoles;
    }

    public void setRolesByRolesIdRoles(Roles rolesByRolesIdRoles) {
        this.rolesByRolesIdRoles = rolesByRolesIdRoles;
    }
}
