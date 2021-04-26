package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
@Table(name = "permissions")
@NamedQueries
        ({
                @NamedQuery(name = "Permissions.findAllTri", query = "SELECT p FROM Permissions p ORDER BY p.denomination ASC"),
        })

public class Permissions {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPermissions", nullable = false)
    private int idPermissions;
    public int getIdPermissions() {
        return idPermissions;
    }

    public void setIdPermissions(int idPermissions) {
        this.idPermissions = idPermissions;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 150)
    private String denomination;
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
    private Collection<PermissionsRoles> permissionsRolesByIdPermissions;
    public Collection<PermissionsRoles> getPermissionsRolesByIdPermissions() {
        return permissionsRolesByIdPermissions;
    }

    public void setPermissionsRolesByIdPermissions(Collection<PermissionsRoles> permissionsRolesByIdPermissions) {
        this.permissionsRolesByIdPermissions = permissionsRolesByIdPermissions;
    }
}
