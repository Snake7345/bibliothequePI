package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permissions_roles", schema = "bibliotheque")
@NamedQueries
        ({
                @NamedQuery(name="PermissionsRoles.findPermissionsRoles",  query ="SELECT pr FROM PermissionsRoles pr WHERE pr.permissions.idPermissions=:permission AND pr.role.idRoles=:role"),
        })
public class PermissionsRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPermissionsRoles", nullable = false)
    private int idPermissionsRoles;
    @ManyToOne
    @JoinColumn(name = "PermissionsIdPermissions", nullable = false)
    private Permissions permissions;
    @ManyToOne
    @JoinColumn(name = "RolesIdRoles", nullable = false)
    private Roles role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionsRoles that = (PermissionsRoles) o;
        return idPermissionsRoles == that.idPermissionsRoles &&
                Objects.equals(permissions, that.permissions) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermissionsRoles, permissions, role);
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public int getIdPermissionsRoles() {
        return idPermissionsRoles;
    }

    public void setIdPermissionsRoles(int idPermissionsRoles) {
        this.idPermissionsRoles = idPermissionsRoles;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }


}