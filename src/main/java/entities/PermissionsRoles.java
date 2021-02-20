package entities;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Named
@SessionScoped
public class PermissionsRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "PermissionsIdPermissions", nullable = false)
    private Integer permissionsIdPermissions;

    @Column(name = "RolesIdRoles", nullable = false)
    private Integer rolesIdRoles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPermissionsRoles", nullable = false)
    private Integer idPermissionsRoles;

    public void setPermissionsIdPermissions(Integer permissionsIdPermissions) {
        this.permissionsIdPermissions = permissionsIdPermissions;
    }

    public Integer getPermissionsIdPermissions() {
        return permissionsIdPermissions;
    }

    public void setRolesIdRoles(Integer rolesIdRoles) {
        this.rolesIdRoles = rolesIdRoles;
    }

    public Integer getRolesIdRoles() {
        return rolesIdRoles;
    }

    public void setIdPermissionsRoles(Integer idPermissionsRoles) {
        this.idPermissionsRoles = idPermissionsRoles;
    }

    public Integer getIdPermissionsRoles() {
        return idPermissionsRoles;
    }

    @Override
    public String toString() {
        return "PermissionsRoles{" +
                "permissionsIdPermissions=" + permissionsIdPermissions + '\'' +
                "rolesIdRoles=" + rolesIdRoles + '\'' +
                "idPermissionsRoles=" + idPermissionsRoles + '\'' +
                '}';
    }
}
