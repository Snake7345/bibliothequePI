package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return actif;
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
                Objects.equals(denomination, roles.denomination) &&
                Objects.equals(permissionsRolesByIdRoles, roles.permissionsRolesByIdRoles) &&
                Objects.equals(utilisateursByIdRoles, roles.utilisateursByIdRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoles, denomination, actif, permissionsRolesByIdRoles, utilisateursByIdRoles);
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
