package entities;

import enumeration.FactureEtatEnum;
import enumeration.UtilisateurSexeEnum;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
@Table(name="utilisateurs")
@NamedQueries
        ({
                @NamedQuery(name = "Utilisateurs.findAll", query = "SELECT u FROM Utilisateurs u"),
                @NamedQuery(name = "Utilisateurs.findActiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=TRUE"),
                @NamedQuery(name = "Utilisateurs.findInactiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=FALSE"),
                @NamedQuery(name=  "Utilisateurs.authentify", query="SELECT u FROM Utilisateurs u where u.login=:login and u.mdp=:mdp"),
        })
public class Utilisateurs {












    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateurs", nullable = false)
    private int idUtilisateurs;

    public int getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 150)
    private String nom;
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom", nullable = false, length = 150)
    private String prenom;
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(2) default 'FR'", name = "Sexe")
    private UtilisateurSexeEnum Sexe;

    public UtilisateurSexeEnum getSexe() {
        return Sexe;
    }

    public void setSexe(UtilisateurSexeEnum sexe) {
        Sexe = sexe;
    }

    @Basic
    @Column(name = "Courriel", nullable = false, length = 255)
    private String courriel;
    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    @Basic
    @Column(name = "Login", nullable = true, length = 255)
    private String login;
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "Mdp", nullable = true, length = 255)
    private String mdp;
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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

    @Basic
    @Column(name = "RolesIdRoles", nullable = false)
    private int rolesIdRoles;
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
                Objects.equals(Sexe, that.Sexe) &&
                Objects.equals(courriel, that.courriel) &&
                Objects.equals(login, that.login) &&
                Objects.equals(mdp, that.mdp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateurs, nom, prenom, Sexe, courriel, login, mdp, actif, rolesIdRoles);
    }

    @OneToMany(mappedBy = "utilisateursByUtilisateursIdUtilisateurs")
    private Collection<Facture> facturesByIdUtilisateurs;
    public Collection<Facture> getFacturesByIdUtilisateurs() {
        return facturesByIdUtilisateurs;
    }

    public void setFacturesByIdUtilisateurs(Collection<Facture> facturesByIdUtilisateurs) {
        this.facturesByIdUtilisateurs = facturesByIdUtilisateurs;
    }

    @ManyToOne
    @JoinColumn(name = "RolesIdRoles", referencedColumnName = "IdRoles", nullable = false)
    private Roles rolesByRolesIdRoles;
    public Roles getRolesByRolesIdRoles() {
        return rolesByRolesIdRoles;
    }

    public void setRolesByRolesIdRoles(Roles rolesByRolesIdRoles) {
        this.rolesByRolesIdRoles = rolesByRolesIdRoles;
    }

    @OneToMany(mappedBy = "utilisateursByUtilisateursIdUtilisateurs")
    private Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs;
    public Collection<UtilisateursAdresses> getUtilisateursAdressesByIdUtilisateurs() {
        return utilisateursAdressesByIdUtilisateurs;
    }

    public void setUtilisateursAdressesByIdUtilisateurs(Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs) {
        this.utilisateursAdressesByIdUtilisateurs = utilisateursAdressesByIdUtilisateurs;
    }
}
