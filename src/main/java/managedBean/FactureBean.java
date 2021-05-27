package managedBean;

import entities.*;
import enumeration.FactureEtatEnum;
import objectCustom.locationCustom;
import org.apache.log4j.Logger;
import pdfTools.ModelFactBiblio;
import services.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
 * - Il faut que la facture accepte les autres jours qui ne sont pas mis en DB
 * - Quand une facture est crée, il faut passer l'exemplaire livre a loué, et quand la facture est cloturé, il faut inspecter le livre et décrire son état et mettre son état
 * - la redirection quand la facture et crée, ne se fait pas Erreur a corriger : le tableau n'est pas non plus correctement initialisé
 * - NE PAS OUBLIER la cloture de la facture
 *
 * */
public class FactureBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures factures;
    private static final Logger log = Logger.getLogger(FactureBean.class);
    private List<locationCustom> listLC = new ArrayList<>();
    private String numMembre;
    private Bibliotheques Bibli;

    public void init(){
        listLC.add(new locationCustom());
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

        //initialisation des object et variables
        double prixTVAC = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date();

        Factures fact = new Factures();
        log.debug("bibli" + Bibli.getNom());
        ModelFactBiblio MFB =new ModelFactBiblio();
        Tarifs T = serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);

        Calendar cal = Calendar.getInstance();

        //fermeture des services utilisé lors de l'initialisation


        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //création de la facture
            fact.setDateDebut(timestamp);
            String numFact = createNumFact();
            fact.setNumeroFacture(numFact);
            String path = "Factures\\" + numFact + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.en_cours);
            fact.setUtilisateurs(u);
            // parcour de la liste des location a inscrire dans la facture
            log.debug("debut for listlc");
            for (locationCustom lc: listLC){
                //création des détails de la facture
                ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
                log.debug("livre "+el.getLivres().getTitre());
                Jours j = serviceJ.findByNbrJ(lc.getNbrJours()).get(0);
                cal.setTime(timestamp);// w ww.  j ava  2  s  .co m
                cal.add(Calendar.DATE, j.getNbrJour());
                log.debug("jours "+j.getNbrJour());
                FacturesDetail Factdet = serviceFD.newRent(el,fact,T,j, new Timestamp(cal.getTime().getTime()));
                serviceFD.save(Factdet);
                prixTVAC = prixTVAC + Factdet.getPrix();
            }
            log.debug("fin du for listlc");

            fact.setPrixTvac(prixTVAC);
            log.debug("prix calcule "+prixTVAC);

            // sauvegarde de la facture et commit de transaction
            service.save(fact);
            log.debug("test1");
            log.debug(fact.getEtat());

            transaction.commit();
            log.debug("test 2");
            log.debug(fact.getFactureDetails().size());
            log.debug("test 3");
            log.debug(fact.getIdFactures());
            log.debug("test 4");
            service.refreshEntity(fact);
            log.debug(fact.getFactureDetails().size());
            MFB.creation(fact);
            return "TableFactures";
        }
        finally {
            //bloc pour gérer les erreurs lors de la transactions
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
                return "";
            }
            //fermeture finale de service
            service.close();// fermeture des services utilisé pour la créations de détails
            serviceEL.close();
            serviceJ.close();
            serviceU.close();
            serviceT.close();
        }
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
}
