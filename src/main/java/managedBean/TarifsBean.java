package managedBean;

import entities.Bibliotheques;
import entities.Jours;
import entities.Penalites;
import entities.Tarifs;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
/*TODO :
 *
 * -Verifier les dates au niveau des tarifs pénalités / tarifs jours car aucune ligne ou date ne doivent se chevaucher
 * - Et si sa se passe, vérifier les dates pour pas qu'il y ait de chevauchement
 *
 * */
public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final Logger log = Logger.getLogger(TarifsBean.class);
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;

    private List<PenaCustom> grillePena = new ArrayList<>();
    private List<JourCustom> grilleJour = new ArrayList<>();

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
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
            }
            service.close();
        }
    }
   public String newTarif()
    {

        //si tarif demom exist ou que dans jourcustom il ny as pas le 1 jour => erreur;
        boolean flagJ=false;
        boolean flagD1=false;
        boolean flagD2=false;
        SvcTarifs service = new SvcTarifs();
        for (JourCustom j: grilleJour){
            if (j.getNbrJours() == 1) {
                flagJ = true;
            }
            if (j.getDateDebut().getTime()<tarif.getDateDebut().getTime()){
                flagD1 = true;
            }
            if (j.getDateFin().getTime()<tarif.getDateDebut().getTime()){
                flagD2 = true;
            }
        }
        for (PenaCustom p:grillePena) {
            if (p.getDateDebut().getTime()<tarif.getDateDebut().getTime()){
                flagD1 = true;
            }
            if (p.getDateFin().getTime()<p.getDateDebut().getTime()){
                flagD2 = true;
            }
        }

        if (flagJ && !flagD1 && !flagD2 && service.findOneTarif(tarif).size()==0) {

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
                tarif = service.save(tarif);
                for (PenaCustom p : grillePena) {
                    penalites = serviceP.addPena(p.getName());
                    serviceTP.save(serviceTP.createTarifsPenalites(tarif, penalites, p.getPrix(), p.getDateDebut(), p.getDateFin()));
                }
                for (JourCustom j : grilleJour) {
                    jours = serviceJ.addJours(j.getNbrJours());
                    serviceTJ.save(serviceTJ.createTarifsJours(tarif, jours, j.getPrix(), j.getDateDebut(), j.getDateFin()));
                }
                transaction.commit();
                log.debug("J'ai sauvé le tarif");
                return "/tableTarifs.xhtml?faces-redirect=true";
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a reussie", null));
                } else {

                    log.debug("je suis censé avoir réussi");
                    init();
                }

                service.close();
            }
        }
        else {
            service.close();
            if(!flagJ){
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "la valeur tarifaire pour 1 jours est requise, veuillez l'ajouter", null));
                return "";
            }
            else if (flagD1){
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "les dates encodées dans les tableaux ne peuvent être antérieure à la date de début du tarif, veuillez corriger", null));
                return "";
            }
            else if (flagD2){
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "les dates de fin encodées dans les tableaux ne peuvent être antérieure à leur date de début correspondante, veuillez corriger", null));
                return "";
            }
            else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ce nom de tarif existe déjà; veuillez en choisir un autre", null));
                return "";
            }

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

    public String flushTarifs() {
        init();
        return "/tableTarifs?faces-redirect=true";
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
}
