package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@NamedQueries
        ({
                @NamedQuery(name = "Permissions.findAllTri", query = "SELECT p FROM Permissions p ORDER BY p.denomination ASC"),
                @NamedQuery(name = "Permissions.findOne", query ="SELECT p FROM Permissions p WHERE p.denomination=:denomination")

        })
public class Permissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermissions;
    private String denomination;
    @OneToMany(mappedBy = "permissions")
    private Collection<PermissionsRoles> permissionsRoles;

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


    public Collection<PermissionsRoles> getPermissionsRoles() {
        return permissionsRoles;
    }

    public void setPermissionsRoles(Collection<PermissionsRoles> permissionsRolesByIdPermissions) {
        this.permissionsRoles = permissionsRolesByIdPermissions;
    }
}
