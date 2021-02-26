package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "utilisateurs")
public class Utilisateurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUtilisateurs", nullable = false)
    private Integer idUtilisateurs;

    @Column(name = "Nom", nullable = false)
    private String nom;

    @Column(name = "Prenom", nullable = false)
    private String prenom;

    @Column(name = "Sexe", nullable = false)
    private String sexe;

    @Column(name = "Courriel", nullable = false)
    private String courriel;

    @Column(name = "Login")
    private String login;

    @Column(name = "Mdp")
    private String mdp;

    @Column(name = "Actif", nullable = false)
    private Integer actif = 1;

    @Column(name = "RolesIdRoles", nullable = false)
    private Integer rolesIdRoles;

    public void setIdUtilisateurs(Integer idUtilisateurs) {
        this.idUtilisateurs = idUtilisateurs;
    }

    public Integer getIdUtilisateurs() {
        return idUtilisateurs;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSexe() {
        return sexe;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMdp() {
        return mdp;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public Integer getActif() {
        return actif;
    }

    public void setRolesIdRoles(Integer rolesIdRoles) {
        this.rolesIdRoles = rolesIdRoles;
    }

    public Integer getRolesIdRoles() {
        return rolesIdRoles;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" +
                "idUtilisateurs=" + idUtilisateurs + '\'' +
                "nom=" + nom + '\'' +
                "prenom=" + prenom + '\'' +
                "sexe=" + sexe + '\'' +
                "courriel=" + courriel + '\'' +
                "login=" + login + '\'' +
                "mdp=" + mdp + '\'' +
                "actif=" + actif + '\'' +
                "rolesIdRoles=" + rolesIdRoles + '\'' +
                '}';
    }
}
