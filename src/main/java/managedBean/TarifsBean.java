package managedBean;

import entities.*;
import objectCustom.JourCustom;
import objectCustom.PenaCustom;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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

public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final Logger log = Logger.getLogger(TarifsBean.class);
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;

    private List<PenaCustom> grillePena = new ArrayList<>();
    private List<JourCustom> grilleJour = new ArrayList<>();
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        tarif=new Tarifs();
        grilleJour.clear();
        grillePena.clear();
        grillePena.add(new PenaCustom());
        grilleJour.add(new JourCustom());
    }

    /*Cette méthode permet de sauvegarder un objet "tarifs"*/
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
    /*Cette méthode permet de retrouver les infos concernant la grille tarifaire qu'on veut modifier et nous envoit ensuite sur le formulaire d'édition de tarif
    * sauf dans le cas ou la grille tarfiaire est déjà appliquée alors elle nous renvoit sur la table des tarifs avec un message d'erreur*/
    public String redirectEdit(){
        long dn = new Date().getTime();
        if(tarif.getDateDebut().getTime() > dn){
            grillePena.clear();
            grilleJour.clear();
            if (tarif.getTarifsPenalites().size()!=0){
                for (TarifsPenalites TP: tarif.getTarifsPenalites()) {
                    grillePena.add(new PenaCustom(TP.getPenalite().getDenomination(), TP.getPrix(), TP.getDateDebut(),TP.getDateFin()));
                }
            }
            for (TarifsJours TJ:tarif.getTarifsJours()){
                grilleJour.add(new JourCustom(TJ.getJours().getNbrJour(), TJ.getPrix(),TJ.getDateDebut(),TJ.getDateFin()));
            }
            return "/formEditTarif.xhtml?faces-redirect=true";
        }
        else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"ce tarif ne peut être modifié",null));
            init();
            return "/tableTarifs.xhtml?faces-redirect=true";
        }

    }
    /*Cette méthode permet d'ajouter une grille tarifaire et vérifier si les données sont correctes (date de début et de fin de tarif cohérente,...)*/
    public String newTarif()
    {

        //si tarif demom exist ou que dans jourcustom il ny as pas le 1 jour => erreur;
        boolean flagJ=false;
        boolean flagD1=false;
        boolean flagD2=false;
        boolean flagD3;
        boolean flagV1;
        SvcTarifs service = new SvcTarifs();

        if(tarif.getIdTarifs()!=0){
            flagV1= (service.getById(tarif.getIdTarifs()).getDenomination().equals(tarif.getDenomination())|| service.findOneTarifByDenom(tarif).size()==0);}
        else {
            flagV1=service.findOneTarifByDenom(tarif).size()==0;
        }

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
        if(tarif.getIdTarifs()!=0){
            flagD3=false;
        }
        else {flagD3=service.findOneTarifByDateDebut(tarif).size()!=0;}


        if (flagJ && !flagD1 && !flagD2 && flagV1 && !flagD3) {

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

            transaction.begin();
            try {
                tarif.setBibliotheques(bibliothequeActuelle);
                tarif = service.save(tarif);
                if(tarif.getIdTarifs()!=0){
                    for (TarifsJours tarifsJours:tarif.getTarifsJours())
                    {
                        serviceTJ.delete(tarifsJours.getIdTarifsJours());
                    }
                    for (TarifsPenalites tp:tarif.getTarifsPenalites())
                    {
                        serviceTP.delete(tp.getIdTarifsPenalites());
                    }
                }
                for (PenaCustom p : grillePena) {
                    penalites = serviceP.addPena(p.getName());
                    serviceTP.save(serviceTP.createTarifsPenalites(tarif, penalites, ((int)((p.getPrix()*100)+0.5)/100.0), p.getDateDebut(), p.getDateFin()));
                }
                for (JourCustom j : grilleJour) {


                    jours = serviceJ.addJours(j.getNbrJours());
                    serviceTJ.save(serviceTJ.createTarifsJours(tarif, jours, ((int)((j.getPrix()*100)+0.5)/100.0), j.getDateDebut(), j.getDateFin()));
                }
                transaction.commit();
                return "/tableTarifs.xhtml?faces-redirect=true";

            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas reussie", null));
                }

                init();
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
            else if (flagD3){
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "la dates de debut du tarif doit être unique, veuillez corriger", null));
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
    /*Cette méthode permet d'ajouter une nouvelle ligne concernant la grille des pénalités*/
    public void addNewPenaRow() {
        grillePena.add(new PenaCustom());
    }
    /*Cette méthode permet d'ajouter une nouvelle ligne concernant la grille des jours */
    public void addNewJourRow() {
        grilleJour.add(new JourCustom());
    }
    /*Cette méthode permet de retirer une ligne concernant la grille des pénalités */
    public void delPenaRow() {
        if (grillePena.size() >1)
        {
            grillePena.remove(grillePena.size()-1);
        }

    }
    /*Cette méthode permet de retirer une ligne concernant la grille des jours */
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

    public List<Tarifs> getTarifByBiblioActuelle()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs;
        listTarifs = service.getTarifByBiblioActuelle(bibliothequeActuelle.getIdBibliotheques());
        service.close();
        return listTarifs;
    }
    /*
     * Méthode qui permet de vider les variables et nous renvoit sur la table des tarifs
     */
    public String flushTarifs() {
        init();
        return "/tableTarifs?faces-redirect=true";
    }
    /*
     * Méthode qui permet de vider les variables et nous renvoit sur le formulaire de création d'une grille tarifaire
     */
    public String flushTarifsNew() {
        init();
        return "/formNewTarif?faces-redirect=true";
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

    public Bibliotheques getBib() {
        return bibliothequeActuelle;
    }
}
