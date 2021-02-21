package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Utilisateurs {
    private int idUtilisateurs;
    private String nom;
    private String prenom;
    private Object sexe;
    private String courriel;
    private String login;
    private String mdp;
    private byte actif;
    private int rolesIdRoles;
    private Collection<Facture> facturesByIdUtilisateurs;
    private Roles rolesByRolesIdRoles;
    private Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs;

    @Id
    @Column(name = "IdUtilisateurs")
    public int getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    @Basic
    @Column(name = "Nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "Sexe")
    public Object getSexe() {
        return sexe;
    }

    public void setSexe(Object sexe) {
        this.sexe = sexe;
    }

    @Basic
    @Column(name = "Courriel")
    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    @Basic
    @Column(name = "Login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "Mdp")
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Basic
    @Column(name = "Actif")
    public byte getActif() {
        return actif;
    }

    public void setActif(byte actif) {
        this.actif = actif;
    }

    @Basic
    @Column(name = "RolesIdRoles")
    public int getRolesIdRoles() {
        return rolesIdRoles;
    }

    public void setRolesIdRoles(int rolesIdRoles) {
        this.rolesIdRoles = rolesIdRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateurs that = (Utilisateurs) o;
        return idUtilisateurs == that.idUtilisateurs &&
                actif == that.actif &&
                rolesIdRoles == that.rolesIdRoles &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(sexe, that.sexe) &&
                Objects.equals(courriel, that.courriel) &&
                Objects.equals(login, that.login) &&
                Objects.equals(mdp, that.mdp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateurs, nom, prenom, sexe, courriel, login, mdp, actif, rolesIdRoles);
    }

    @OneToMany(mappedBy = "utilisateursByUtilisateursIdUtilisateurs")
    public Collection<Facture> getFacturesByIdUtilisateurs() {
        return facturesByIdUtilisateurs;
    }

    public void setFacturesByIdUtilisateurs(Collection<Facture> facturesByIdUtilisateurs) {
        this.facturesByIdUtilisateurs = facturesByIdUtilisateurs;
    }

    @ManyToOne
    @JoinColumn(name = "RolesIdRoles", referencedColumnName = "IdRoles", nullable = false)
    public Roles getRolesByRolesIdRoles() {
        return rolesByRolesIdRoles;
    }

    public void setRolesByRolesIdRoles(Roles rolesByRolesIdRoles) {
        this.rolesByRolesIdRoles = rolesByRolesIdRoles;
    }

    @OneToMany(mappedBy = "utilisateursByUtilisateursIdUtilisateurs")
    public Collection<UtilisateursAdresses> getUtilisateursAdressesByIdUtilisateurs() {
        return utilisateursAdressesByIdUtilisateurs;
    }

    public void setUtilisateursAdressesByIdUtilisateurs(Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs) {
        this.utilisateursAdressesByIdUtilisateurs = utilisateursAdressesByIdUtilisateurs;
    }
}
