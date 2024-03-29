package entities;

import enumeration.UtilisateurSexeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import security.SecurityManager;


// AND u.login=:login AND u.mdp=:mdp
//
@Entity
@Table(name="utilisateurs")
@NamedQueries
        ({
                @NamedQuery(name = "Utilisateurs.findAll", query = "SELECT u FROM Utilisateurs u"),
                @NamedQuery(name = "Utilisateurs.findOne", query = "SELECT u FROM Utilisateurs u WHERE  u.nom=:nom AND u.prenom=:prenom AND u.courriel=:courriel AND u.sexe=:sexe AND u.roles=:role"),
                @NamedQuery(name = "Utilisateurs.findAllUtil", query = "SELECT u FROM Utilisateurs u WHERE u.numMembre IS NULL"),
                @NamedQuery(name = "Utilisateurs.findActiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=TRUE AND u.numMembre IS NULL"),
                @NamedQuery(name = "Utilisateurs.findInactiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=FALSE AND u.numMembre IS NULL"),
                @NamedQuery(name=  "Utilisateurs.searchName", query="SELECT u FROM Utilisateurs u WHERE u.nom=:nom AND u.numMembre IS NOT NULL"),
                @NamedQuery(name = "Utilisateurs.findLastMembre", query = "SELECT u FROM Utilisateurs u WHERE u.numMembre IS NOT NULL ORDER BY u.numMembre DESC"),
                @NamedQuery(name = "Utilisateurs.searchMembre", query = "SELECT u FROM Utilisateurs u WHERE u.numMembre=:numMembre"),
                @NamedQuery(name = "Utilisateurs.findByLogin", query = "SELECT u FROM Utilisateurs u WHERE u.login=:login"),
                @NamedQuery(name = "Utilisateurs.findByLoginMail", query = "SELECT u FROM Utilisateurs u WHERE u.login=:login AND u.courriel=:courriel"),
                @NamedQuery(name = "Utilisateurs.findAllCli", query = "SELECT u FROM Utilisateurs u WHERE u.numMembre IS NOT NULL"),
                @NamedQuery(name = "Utilisateurs.findCliActiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=TRUE AND u.numMembre IS NOT NULL"),
                @NamedQuery(name = "Utilisateurs.findCliInactiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=FALSE AND u.numMembre IS NOT NULL"),
        })
public class Utilisateurs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilisateurs;
    private String nom;
    private String prenom;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "sexe")
    private UtilisateurSexeEnum sexe;
    private String courriel;
    private String login;
    private String mdp;
    private String numMembre;
    private boolean actif = true;
    @OneToMany(mappedBy = "utilisateurs")
    private Collection<Factures> factures;
    @OneToMany(mappedBy = "utilisateur")
    private Collection<UtilisateursAdresses> utilisateursAdresses;
    @OneToMany(mappedBy = "utilisateur")
    private Collection<UtilisateursBibliotheques> utilisateursBibliotheques;
    @ManyToOne
    @JoinColumn(name = "RolesIdRoles")
    private Roles roles;

    @Id
    @Column(name = "IdUtilisateurs", nullable = false)
    public int getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setIdUtilisateurs(int idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    @Basic
    @Column(name = "NumMembre", nullable = true, length = 10)
    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
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


    public UtilisateurSexeEnum getSexe() {
        return sexe;
    }

    public void setSexe(UtilisateurSexeEnum sexe) {
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

        this.mdp = SecurityManager.encryptPassword(mdp);
    }


    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }


    public Roles getRoles() {
        return roles;
    }
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Collection<UtilisateursBibliotheques> getUtilisateursBibliotheques() {
        return utilisateursBibliotheques;
    }

    public void setUtilisateursBibliotheques(Collection<UtilisateursBibliotheques> utilisateursBibliotheques) {
        this.utilisateursBibliotheques = utilisateursBibliotheques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateurs that = (Utilisateurs) o;
        return idUtilisateurs == that.idUtilisateurs && actif == that.actif && nom.equals(that.nom) && prenom.equals(that.prenom) && sexe == that.sexe && courriel.equals(that.courriel) && Objects.equals(login, that.login) && Objects.equals(mdp, that.mdp) && Objects.equals(numMembre, that.numMembre) &&  roles.equals(that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateurs, nom, prenom, sexe, courriel, login, mdp, numMembre, actif, roles);
    }

    public Collection<Factures> getFactures() {
        return factures;
    }

    public void setFactures(Collection<Factures> facturesByIdUtilisateurs) {
        this.factures = facturesByIdUtilisateurs;
    }


    public Collection<UtilisateursAdresses> getUtilisateursAdresses() {
        return utilisateursAdresses;
    }

    public void setUtilisateursAdresses(Collection<UtilisateursAdresses> utilisateursAdressesByIdUtilisateurs) {
        this.utilisateursAdresses = utilisateursAdressesByIdUtilisateurs;
    }

    @Override
    public Utilisateurs clone(){
        Utilisateurs utili = null;
        try{
            utili = (Utilisateurs) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return utili;
    }

    public void setFields(Utilisateurs utilisateur) {
        this.nom = utilisateur.nom;
        this.prenom = utilisateur.prenom;
        this.sexe = utilisateur.sexe;
        this.courriel = utilisateur.courriel;
        this.login = utilisateur.login;
        this.mdp = utilisateur.mdp;
        this.actif = utilisateur.actif;
        this.factures = utilisateur.factures;
        this.utilisateursAdresses = utilisateur.utilisateursAdresses;
    }

}