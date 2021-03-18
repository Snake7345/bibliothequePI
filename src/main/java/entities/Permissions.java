package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@NamedQueries
        ({
                @NamedQuery(name = "Permissions.findAll", query = "SELECT p FROM Permissions p"),
        })

@Entity
public class Permissions {
    private int idPermissions;
    private String denomination;
    private Collection<PermissionsRoles> permissionsRolesByIdPermissions;

    @Id
    @Column(name = "IdPermissions", nullable = false)
    public int getIdPermissions() {
        return idPermissions;
    }

    public void setIdPermissions(int idPermissions) {
        this.idPermissions = idPermissions;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 150)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissions that = (Permissions) o;
        return idPermissions == that.idPermissions &&
                Objects.equals(denomination, that.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermissions, denomination);
    }

    @OneToMany(mappedBy = "permissionsByPermissionsIdPermissions")
    public Collection<PermissionsRoles> getPermissionsRolesByIdPermissions() {
        return permissionsRolesByIdPermissions;
    }

    public void setPermissionsRolesByIdPermissions(Collection<PermissionsRoles> permissionsRolesByIdPermissions) {
        this.permissionsRolesByIdPermissions = permissionsRolesByIdPermissions;
    }
}
