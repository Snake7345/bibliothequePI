package managedBean;

import entities.*;
import objectCustom.JourCustom;
import objectCustom.PenaCustom;
import org.apache.log4j.Logger;
import services.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final Logger log = Logger.getLogger(TarifsBean.class);
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;

    private List<PenaCustom> grillePena = new ArrayList<>();
    private List<JourCustom> grilleJour = new ArrayList<>();
    private Bibliotheques bibli;
    private String denominationTarif;
    private Date dateDebut;

    @PostConstruct
    public void init()
    {
        tarif=new Tarifs();
        grillePena.add(new PenaCustom());
        grilleJour.add(new JourCustom());
    }

    public void save()
    {
        SvcTarifs service = new SvcTarifs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(tarif);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
            }
            service.close();
        }
    }
   public String newTarif()
    {
        SvcTarifs service = new SvcTarifs();
        SvcTarifsJours serviceTJ = new SvcTarifsJours();
        SvcTarifsPenalites serviceTP = new SvcTarifsPenalites();
        SvcJours serviceJ = new SvcJours();
        SvcPenalites serviceP = new SvcPenalites();
        EntityTransaction transaction = service.getTransaction();
        serviceTJ.setEm(service.getEm());
        serviceTP.setEm(service.getEm());
        serviceJ.setEm(service.getEm());
        serviceP.setEm(service.getEm());

        Penalites penalites;
        Jours jours;
        //Todo mettre/faire une verification de l'objet Livre, ainsi que des auteurs et du genre
        log.debug("J'vais essayer d'sauver le tarif");

        transaction.begin();
        try {
            tarif.setBibliotheques(bibli);
            tarif.setDenomination(denominationTarif);
            tarif.setDateDebut(new Timestamp(dateDebut.getTime()));
            tarif = service.save(tarif);
            for (PenaCustom p: grillePena) {
                penalites= serviceP.addPena(p.getName());
                serviceTP.save(serviceTP.createTarifsPenalites( tarif, penalites,p.getPrix(), new Timestamp( p.getDateDebut().getTime()),new Timestamp(p.getDateFin().getTime())));
            }
            for (JourCustom j: grilleJour) {
                jours= serviceJ.addJours(j.getNbrJours());
                serviceTJ.save(serviceTJ.createTarifsJours( tarif, jours,j.getPrix(), new Timestamp( j.getDateDebut().getTime()),new Timestamp(j.getDateFin().getTime())));
            }
            transaction.commit();
            log.debug("J'ai sauvé le tarif");
            return "/tableTarifs.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur dans tarif et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));
            }
            else
            {

                log.debug("je suis censé avoir réussi");
                init();
            }

            service.close();
        }
    }

    public void addNewPenaRow() {
        grillePena.add(new PenaCustom());
    }

    public void addNewJourRow() {
        grilleJour.add(new JourCustom());
    }

    public void delPenaRow() {
        if (grillePena.size() >1)
        {
            grillePena.remove(grillePena.size()-1);
        }

    }

    public void delJourRow() {
        if (grilleJour.size() >1)
        {
            grilleJour.remove(grilleJour.size()-1);
        }
    }

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les grilles tarifaires
     */

    public List<Tarifs> getReadAll()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs;
        listTarifs = service.findAllTarifs();

        service.close();
        return listTarifs;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }

    public List<PenaCustom> getGrillePena() {
        return grillePena;
    }

    public void setGrillePena(List<PenaCustom> grillePena) {
        this.grillePena = grillePena;
    }

    public List<JourCustom> getGrilleJour() {
        return grilleJour;
    }

    public void setGrilleJour(List<JourCustom> grilleJour) {
        this.grilleJour = grilleJour;
    }

    public Bibliotheques getBibli() {
        return bibli;
    }

    public void setBibli(Bibliotheques bibli) {
        this.bibli = bibli;
    }

    public String getDenominationTarif() {
        return denominationTarif;
    }

    public void setDenominationTarif(String denominationTarif) {
        this.denominationTarif = denominationTarif;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
}
