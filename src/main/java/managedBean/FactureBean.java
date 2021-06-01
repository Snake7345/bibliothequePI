package managedBean;

import entities.*;
import enumeration.FactureEtatEnum;
import objectCustom.locationCustom;
import org.apache.log4j.Logger;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import pdfTools.ModelFactBiblio;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
/*TODO :
 *
 * -REFUSER LA CREATION DE LA FACTURE SI UN DES ELEMENTS DE LA FACTURE SONT MANQUANTS
 * (adresse, tarifs, livres,...)
 * - Quand une facture est crée, il faut passer l'exemplaire livre a loué, et quand la facture est cloturé, il faut inspecter le livre et décrire son état et mettre son état
 * - la redirection quand la facture et crée
 * - NE PAS OUBLIER la cloture de la facture
 * - le code barre doit être vérifier par validation (ou autre chose) : Mauvais code barre, code barre inexistant,utilisateurcli inactif,... + l'exemplaire livre ne peut pas être déjà loué
 *
 * - Si l'état de l'exemplaire livre est mauvais, alors retirer le livre de la location = Desactiver
 *
 * - NE PAS OUBLIER D'ENVOYEZ LE MAIL APRES CREATION DE LA FACTURE
 * */
public class FactureBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures factures;
    private static final Logger log = Logger.getLogger(FactureBean.class);
    private List<locationCustom> listLC = new ArrayList<>();
    private List<TarifsPenalites> tarifsPenalites;
    private String numMembre;
    private String CB;
    private boolean choixetat;
    private Bibliotheques Bibli;
    private ExemplairesLivres exemplairesLivres;
    @PostConstruct
    public void init(){
        listLC = new ArrayList<>();
        addNewListRow();
        factures= new Factures();
        numMembre= "";
        CB= "";



    }
    public void addNewListRow() {
        listLC.add(new locationCustom());
    }

    public void delListRow() {
        if (listLC.size() >1)
        {
            listLC.remove(listLC.size()-1);
        }
    }

    public String newFact()
    {
        //TODO finaliser la méthode

        //initialisation des services requis
        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcTarifs serviceT = new SvcTarifs();
        SvcJours serviceJ = new SvcJours();

        //rassemblement des entity managers pour la transaction
        serviceFD.setEm(service.getEm());
        serviceEL.setEm(service.getEm());

        //initialisation des object et variables
        double prixTVAC = 0;
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampdebut = new Timestamp(rounded);
        Date date = new Date();
        Factures fact = new Factures();
        ModelFactBiblio MFB =new ModelFactBiblio();
        Tarifs T = serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);
        boolean flag = false;
        //vérif si livre non loué
        for (locationCustom lc: listLC) {
            if (serviceEL.findOneByCodeBarre(lc.getCB()).get(0).isLoue()){
                flag=true;
                break;
            }
        }

        if (!flag) {
            //initialisation de la transaction
            EntityTransaction transaction = service.getTransaction();
            transaction.begin();
            try {
                //création de la facture
                fact.setDateDebut(timestampdebut);
                fact.setNumeroFacture(createNumFact());
                String path = "Factures\\" + fact.getNumeroFacture() + ".pdf";
                fact.setLienPdf(path);
                fact.setEtat(FactureEtatEnum.en_cours);
                fact.setUtilisateurs(u);
                // parcour de la liste des location a inscrire dans la facture
                for (locationCustom lc : listLC) {
                    //création des détails de la facture
                    ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
                    serviceEL.loueExemplaire(el);
                    Timestamp timestampretour = new Timestamp(rounded + (lc.getNbrJours() * 24 * 3600 * 1000));
                    FacturesDetail Factdet = serviceFD.newRent(el, fact, T, lc.getNbrJours(), timestampretour);
                    serviceFD.save(Factdet);
                    serviceEL.save(el);
                    prixTVAC = prixTVAC + Factdet.getPrix();
                }

                fact.setPrixTvac(prixTVAC);

                // sauvegarde de la facture et commit de transaction
                service.save(fact);
                transaction.commit();
                service.refreshEntity(fact);
                MFB.creation(fact);
                return "TableFactures.xhtml?faces-redirect=true";
            } finally {
                //bloc pour gérer les erreurs lors de la transactions
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
                    return "";
                }
                //fermeture des service
                service.close();
                serviceJ.close();
                serviceU.close();
                serviceT.close();
            }
        }
        else {
            //todo facemessage pour signaler que l'on ne peut louer un livre déjà loué
            return "formNewFact.xhtml?faces-redirect=true";
        }

    }

    public void newFactPena(FacturesDetail facturesDetail)
    {

        //TODO finaliser la méthode
        //initialisation des services requis

        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcTarifs serviceT = new SvcTarifs();
        SvcPenalites serviceP = new SvcPenalites();
        SvcTarifsPenalites serviceTP = new SvcTarifsPenalites();

        //rassemblement des entity managers pour la transaction

        serviceFD.setEm(service.getEm());
        serviceEL.setEm(service.getEm());

        //initialisation des object et variables
        double prixTVAC = 0;
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampfacture = new Timestamp(rounded);

        Date date = new Date();

        Factures fact = new Factures();
        FacturesDetail factdet= new FacturesDetail();
        ModelFactBiblio MFB =new ModelFactBiblio();
        Tarifs T = serviceT.getTarifByBiblio(Date.from(facturesDetail.getFacture().getDateDebut().toInstant()), Bibli.getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);



        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //création de la facture
            fact.setDateDebut(timestampfacture);
            fact.setNumeroFacture(createNumFact());
            String path = "Factures\\" + fact.getNumeroFacture() + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.terminer);
            fact.setUtilisateurs(u);

            //création des facture détails
            if (facturesDetail.getDateRetour().after(facturesDetail.getDateFin())){
                int nbjour = (int)((facturesDetail.getDateRetour().getTime() - facturesDetail.getDateFin().getTime())/(1000*60*60*24));
                log.debug(nbjour);

                if (serviceTP.findByPena(T,serviceP.findByName("Retard").get(0),Date.from(facturesDetail.getFacture().getDateDebut().toInstant())).size() >= 1){
                    factdet= serviceFD.newPenaretard(facturesDetail.getExemplairesLivre() , fact , T , serviceP.findByName("Retard").get(0) , nbjour , Date.from(facturesDetail.getFacture().getDateDebut().toInstant()),timestampfacture);
                    prixTVAC=prixTVAC+factdet.getPrix();
                    serviceFD.save(factdet);
                }

            }
            if (tarifsPenalites.size() >= 1){
                for (TarifsPenalites tp: tarifsPenalites){
                    factdet=serviceFD.newPena(facturesDetail.getExemplairesLivre(),fact,T, tp.getPenalite(), Date.from(facturesDetail.getFacture().getDateDebut().toInstant()),timestampfacture);
                    prixTVAC=prixTVAC+factdet.getPrix();
                    serviceFD.save(factdet);
                }
            }
            fact.setPrixTvac(prixTVAC);
            // sauvegarde de la facture et commit de transaction
            service.save(fact);
            transaction.commit();
            //refresh pour récupérer les collections associées
            service.refreshEntity(fact);
            MFB.creation(fact);
        }
        finally {
            //bloc pour gérer les erreurs lors de la transactions
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            //fermeture des service
            service.close();
            serviceP.close();
            serviceU.close();
            serviceT.close();
            serviceTP.close();
        }
    }

    public String redirectChoix(){
        if (choixetat){
            SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
            exemplairesLivres = serviceEL.findOneByCodeBarre(CB).get(0);
            Date date = new Date();
            SvcTarifs serviceT = new SvcTarifs();
            tarifsPenalites= (List<TarifsPenalites>) serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0).getTarifsPenalites();
            return "formEtatLivre.xhtml?faces-redirect=true";
        }
        else {
            return retourLivre();
        }
    }


    public String retourLivre(){
        FacturesDetail facturesDetail = new FacturesDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        List<ExemplairesLivres> listEL= serviceEL.findOneByCodeBarre(CB);
        Factures fact = new Factures();
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampretour = new Timestamp(rounded);
        boolean flag =false;
        if (listEL.get(0).isLoue())
        {
            for (FacturesDetail fd : listEL.get(0).getFactureDetails()){
                if (fd.getDateRetour() == null) {
                    facturesDetail = fd;
                    numMembre=fd.getFacture().getUtilisateurs().getNumMembre();
                    flag=true;
                }
            }
            if (flag=true)
            {
                flag=false;
                facturesDetail.setDateRetour(timestampretour);
                //savefd();
                if (facturesDetail.getDateRetour().after(facturesDetail.getDateFin()) || tarifsPenalites.size()>=1) // ajouter  ou péna dégradation
                {
                    newFactPena(facturesDetail);
                }
                for (FacturesDetail fd: facturesDetail.getFacture().getFactureDetails())
                {
                    if (fd.getDateRetour() == null) {
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    fact = facturesDetail.getFacture();
                    fact.setEtat(FactureEtatEnum.terminer);
                }
                SvcFacture service = new SvcFacture();
                SvcFactureDetail serviceFD = new SvcFactureDetail();
                serviceEL.setEm(service.getEm());
                serviceFD.setEm(service.getEm());
                EntityTransaction transaction = service.getTransaction();
                transaction.begin();
                try {
                    ExemplairesLivres el =listEL.get(0);
                    el.setLoue(false);
                    serviceEL.save(el);
                    serviceFD.save(facturesDetail);
                    if (fact.getEtat()==FactureEtatEnum.terminer){
                        service.save(fact);
                    }
                    transaction.commit();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage("ModifEd", new FacesMessage("retour confirmé"));
                } finally {
                    if (transaction.isActive()) {
                        transaction.rollback();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
                    }
                    service.close();
                }
            }
        }
        else
        {
            //todo add facemessage pour signaler que le livre n'est pas loué
        }
        // todo réfléchir au retour
        return "tableFactures.xhtml?faces-redirect=true";
    }

    /*Méthode permettant de créer un numéro de facture avec FB(FactureBiblio) suivi de l'année, le mois et un nombre a 4 chiffres*/
    public String createNumFact()
    {

        String numFact="";
        Calendar cal = Calendar.getInstance();
        int annee = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH) +1;
        SvcFacture serviceF = new SvcFacture();
        List<Factures> fact;

        //tester si l'année en cours = année de la dernière facture
        try
        {
            fact = serviceF.findAllFactureDesc();
            if (fact.size() != 0){
                String text = fact.get(0).getNumeroFacture();
                int anneelastFact = Integer.parseInt(text.substring(2, 6));
                log.debug("année last fact = "+anneelastFact);

                if(annee == anneelastFact)
                {
                    int nb = Integer.parseInt(text.substring(text.length() - 5, text.length()));
                    numFact = "FB" + annee + String.format("%02d", mois) + String.format("%05d",nb+1);
                }
                else
                {
                    numFact = "FB" + annee + String.format("%02d", mois) + "00001";
                }
                log.debug("numero facture = "+ numFact);
            }
            else{
                numFact = "FB" + annee + String.format("%02d", mois) + "00001";
            }
        }
        catch(NullPointerException npe) {
            log.debug("Problème: pas trouvé de facture");
        }

        return numFact;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de toutes les factures
     */
    public List<Factures> getReadAll()
    {
        SvcFacture service = new SvcFacture();
        List<Factures> listFact = new ArrayList<Factures>();
        listFact= service.findAllFacture();

        service.close();
        return listFact;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Factures getFactures() {
        return factures;
    }

    public void setFactures(Factures factures) {
        this.factures = factures;
    }

    public List<locationCustom> getListLC() {
        return listLC;
    }

    public void setListLC(List<locationCustom> listLC) {
        this.listLC = listLC;
    }

    public Bibliotheques getBibli() {
        return Bibli;
    }

    public void setBibli(Bibliotheques bibli) {
        Bibli = bibli;
    }

    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
    }

    public String getCB() {
        return CB;
    }

    public void setCB(String CB) {
        this.CB = CB;
    }

    public List<TarifsPenalites> getTarifsPenalites() {
        return tarifsPenalites;
    }

    public void setTarifsPenalites(List<TarifsPenalites> tarifsPenalites) {
        this.tarifsPenalites = tarifsPenalites;
    }

    public boolean isChoixetat() {
        return choixetat;
    }

    public void setChoixetat(boolean choixetat) {
        this.choixetat = choixetat;
    }

    public ExemplairesLivres getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(ExemplairesLivres exemplairesLivres) {
        this.exemplairesLivres = exemplairesLivres;
    }
}
