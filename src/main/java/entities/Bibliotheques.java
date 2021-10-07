package entities;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="bibliotheques")
@NamedQueries
        ({
                @NamedQuery(name = "Bibliotheques.findAll", query = "SELECT b FROM Bibliotheques b"),
                @NamedQuery(name = "Bibliotheques.findAllActiv", query = "SELECT b FROM Bibliotheques b WHERE b.actif=TRUE"),
                @NamedQuery(name = "Bibliotheques.findById", query = "SELECT b FROM Bibliotheques b WHERE b.idBibliotheques=:idBibliotheques"),

        })
public class Bibliotheques implements Serializable {
    private static final Logger log = Logger.getLogger(Bibliotheques.class);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBibliotheques;
    private String nom;
    private boolean actif = true;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Adresses> adresses;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<ExemplairesLivres> exemplairesLivres;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Tarifs> tarifs;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Factures> factures;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<UtilisateursBibliotheques> utilisateurs;
    @OneToMany(mappedBy = "bibliotheques")
    private Collection<Reservation> reservations;


    @Id
    @Column(name = "IdBibliotheques", nullable = false)
    public int getIdBibliotheques() {
        return idBibliotheques;
    }

    public void setIdBibliotheques(int idBibliotheques) {
        this.idBibliotheques = idBibliotheques;
    }

    @Basic
    @Column(name = "Actif", nullable = false)
    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Basic
    @Column(name = "Nom", nullable = false, length = 255)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Factures> getFactures() {
        return factures;
    }

    public void setFactures(Collection<Factures> factures) {
        this.factures = factures;
    }


    public Collection<UtilisateursBibliotheques> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(Collection<UtilisateursBibliotheques> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bibliotheques that = (Bibliotheques) o;
        log.debug("test equals bib");
        log.debug(idBibliotheques == that.idBibliotheques && actif == that.actif && nom.equals(that.nom) && adresses.equals(that.adresses));
        return idBibliotheques == that.idBibliotheques && actif == that.actif && nom.equals(that.nom) && adresses.equals(that.adresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBibliotheques, actif, nom, adresses, exemplairesLivres, tarifs, factures,utilisateurs);
    }

    public Collection<Adresses> getAdresses() {
        return adresses;
    }

    public void setAdresses(Collection<Adresses> adresses) {
        this.adresses = adresses;
    }

    public Collection<ExemplairesLivres> getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(Collection<ExemplairesLivres> exemplairesLivres) {
        this.exemplairesLivres = exemplairesLivres;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Collection<Tarifs> getTarifs() {
        return tarifs;
    }

    public void setTarifs(Collection<Tarifs> tarifs) {
        this.tarifs = tarifs;
    }

    @Override
    public Bibliotheques clone(){
        Bibliotheques biblio = null;
        try{
            biblio = (Bibliotheques) super.clone();
        }catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        return biblio;
    }

    public void setFields(Bibliotheques biblio) {
        this.nom = biblio.nom;
        this.adresses = biblio.adresses;
        this.exemplairesLivres = biblio.exemplairesLivres;
        this.tarifs = biblio.tarifs;
        this.actif = biblio.actif;
    }
}
