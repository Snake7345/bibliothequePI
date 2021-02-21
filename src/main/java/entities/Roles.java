package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Roles {
    private int idRoles;
    private String denomination;
    private byte actif;
    private Collection<PermissionsRoles> permissionsRolesByIdRoles;
    private Collection<Utilisateurs> utilisateursByIdRoles;

    @Id
    @Column(name = "IdRoles")
    public int getIdRoles() {
        return idRoles;
    }

    public void setIdRoles(int idRoles) {
        this.idRoles = idRoles;
    }

    @Basic
    @Column(name = "Denomination")
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Basic
    @Column(name = "Actif")
    public byte getActif() {
        return actif;
    }

    public void setActif(byte actif) {
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
