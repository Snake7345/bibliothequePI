package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
@Table(name = "roles")
@NamedQueries
        ({
                @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
                @NamedQuery(name = "Roles.findActiv", query="SELECT r FROM Roles r WHERE r.actif=TRUE"),
                @NamedQuery(name = "Roles.findInactiv", query="SELECT r FROM Roles r WHERE r.actif=FALSE"),
        })
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRoles", nullable = false)
    private int idRoles;
    public int getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
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

    @Basic
    @Column(name = "Actif", nullable = false)
    private boolean actif;
    public boolean isActif() {
        return this.actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return idRoles == roles.idRoles &&
                actif == roles.actif &&
                Objects.equals(denomination, roles.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoles, denomination, actif);
    }

    @OneToMany(mappedBy = "rolesByRolesIdRoles")
    private Collection<PermissionsRoles> permissionsRolesByIdRoles;
    public Collection<PermissionsRoles> getPermissionsRolesByIdRoles() {
        return permissionsRolesByIdRoles;
    }

    public void setPermissionsRolesByIdRoles(Collection<PermissionsRoles> permissionsRolesByIdRoles) {
        this.permissionsRolesByIdRoles = permissionsRolesByIdRoles;
    }

    @OneToMany(mappedBy = "rolesByRolesIdRoles")
    private Collection<Utilisateurs> utilisateursByIdRoles;
    public Collection<Utilisateurs> getUtilisateursByIdRoles() {
        return utilisateursByIdRoles;
    }

    public void setUtilisateursByIdRoles(Collection<Utilisateurs> utilisateursByIdRoles) {
        this.utilisateursByIdRoles = utilisateursByIdRoles;
    }
}
