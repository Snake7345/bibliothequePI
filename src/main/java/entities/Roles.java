package entities;

import javax.management.relation.Role;
import javax.persistence.*;
import java.io.Serializable;
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
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRoles;
    private String denomination;
    private boolean actif = true;
    private Collection<PermissionsRoles> permissionsRoles;
    private Collection<Utilisateurs> utilisateurs;

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
                Objects.equals(denomination, roles.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoles, denomination, actif);
    }

    @OneToMany(mappedBy = "RolesIdRoles")
    public Collection<PermissionsRoles> getPermissionsRoles() {
        return permissionsRoles;
    }

    public void setPermissionsRoles(Collection<PermissionsRoles> pr) {
        this.permissionsRoles = pr;
    }

    @OneToMany(mappedBy = "RolesIdRoles")
    public Collection<Utilisateurs> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Collection<Utilisateurs> user) {
        this.utilisateurs = user;
    }

    @Override
    public Roles clone(){
        Roles role = null;
        try{
            role = (Roles) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return role;
    }

    public void setFields(Roles role) {
        this.denomination = role.denomination;
        this.actif = role.actif;
        this.permissionsRoles = role.permissionsRoles;
        this.utilisateurs = role.utilisateurs;
    }
}