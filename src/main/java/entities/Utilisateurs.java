package entities;

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
    private int idUtilisateurs;
    private String nom;
    private String prenom;
    private Object sexe;
    private String courriel;
    private String login;
    private String mdp;
    private boolean actif;
    private int rolesIdRoles;
    private Collection<Facture> facturesByIdUtilisateurs;
    private Roles rolesByRolesIdRoles;
    private Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateurs", nullable = false)
    public int getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 150)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "Prenom", nullable = false, length = 150)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "Sexe", nullable = false)
    public Object getSexe() {
        return sexe;
    }

    public void setSexe(Object sexe) {
        this.sexe = sexe;
    }

    @Basic
    @Column(name = "Courriel", nullable = false, length = 255)
    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    @Basic
    @Column(name = "Login", nullable = true, length = 255)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "Mdp", nullable = true, length = 255)
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return this.actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Basic
    @Column(name = "RolesIdRoles", nullable = false)
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
