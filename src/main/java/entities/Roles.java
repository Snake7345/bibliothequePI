package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@NamedQueries
        ({
                @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
                @NamedQuery(name = "Roles.findActiv", query="SELECT r FROM Roles r WHERE r.actif='true'"),
        })
@Entity
public class Roles {
    private int idRoles;
    private String denomination;
    private boolean actif;
    private Collection<PermissionsRoles> permissionsRolesByIdRoles;
    private Collection<Utilisateurs> utilisateursByIdRoles;

    @Id
    @Column(name = "IdRoles", nullable = false)
    public int getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    @Basic
    @Column(name = "Denomination", nullable = false, length = 150)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
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
    public Collection<PermissionsRoles> getPermissionsRolesByIdRoles() {
        return permissionsRolesByIdRoles;
    }

    public void setPermissionsRolesByIdRoles(Collection<PermissionsRoles> permissionsRolesByIdRoles) {
        this.permissionsRolesByIdRoles = permissionsRolesByIdRoles;
    }

    @OneToMany(mappedBy = "rolesByRolesIdRoles")
    public Collection<Utilisateurs> getUtilisateursByIdRoles() {
        return utilisateursByIdRoles;
    }

    public void setUtilisateursByIdRoles(Collection<Utilisateurs> utilisateursByIdRoles) {
        this.utilisateursByIdRoles = utilisateursByIdRoles;
    }
}
