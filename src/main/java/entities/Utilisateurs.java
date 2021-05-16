package entities;

import enumeration.UtilisateurSexeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;



@Entity
@Table(name="utilisateurs")
@NamedQueries
        ({
                @NamedQuery(name = "Utilisateurs.findAll", query = "SELECT u FROM Utilisateurs u"),
                @NamedQuery(name = "Utilisateurs.findActiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=TRUE"),
                @NamedQuery(name = "Utilisateurs.findInactiv", query = "SELECT u FROM Utilisateurs u WHERE u.actif=FALSE"),
                @NamedQuery(name=  "Utilisateurs.authentify", query="SELECT u FROM Utilisateurs u where u.login=:login and u.mdp=:mdp and u.actif=TRUE"),
                @NamedQuery(name="Utilisateurs.searchName", query="SELECT u FROM Utilisateurs u WHERE u.nom=:nom"),
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
    private boolean actif = true;
    @OneToMany(mappedBy = "utilisateurs")
    private Collection<Factures> factures;
    @OneToMany(mappedBy = "utilisateur")
    private Collection<UtilisateursAdresses> utilisateursAdresses;

    @Id
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
        this.mdp = mdp;
    }


    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RolesIdRoles")
    private Roles roles;
    public Roles getRoles() {
        return roles;
    }
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateurs that = (Utilisateurs) o;
        return idUtilisateurs == that.idUtilisateurs &&
                actif == that.actif &&
                roles == that.roles &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(sexe, that.sexe) &&
                Objects.equals(courriel, that.courriel) &&
                Objects.equals(login, that.login) &&
                Objects.equals(mdp, that.mdp) &&
                Objects.equals(factures, that.factures) &&
                Objects.equals(utilisateursAdresses, that.utilisateursAdresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateurs, nom, prenom, sexe, courriel, login, mdp, actif, roles, factures, utilisateursAdresses);
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