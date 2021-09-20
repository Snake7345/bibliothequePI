package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@NamedQueries
        ({
                //TODO : Faire attention aux permissions car elles doivent être adaptées
                @NamedQuery(name = "Permissions.findAllTri", query = "SELECT p FROM Permissions p ORDER BY p.type ASC"),
                @NamedQuery(name = "Permissions.findOne", query ="SELECT p FROM Permissions p WHERE p.type=:type AND p.action=:action"),
                @NamedQuery(name = "Permissions.findByType", query ="SELECT p FROM Permissions p WHERE p.type=:type AND p.action=:action"),
        })
public class Permissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermissions;
    private String type;
    private String action;
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
    @Column(name = "Type", nullable = false, length = 100)
    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    @Basic
    @Column(name = "Action", nullable = false, length = 150)
    public String getAction() {
        return action;
    }

    public void setAction(String Action) {
        this.action = Action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissions that = (Permissions) o;
        return idPermissions == that.idPermissions && type.equals(that.type) && action.equals(that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPermissions, type, action);
    }

    public Collection<PermissionsRoles> getPermissionsRoles() {
        return permissionsRoles;
    }

    public void setPermissionsRoles(Collection<PermissionsRoles> permissionsRolesByIdPermissions) {
        this.permissionsRoles = permissionsRolesByIdPermissions;
    }
}
