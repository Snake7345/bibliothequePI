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
 * */
public class FactureBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures factures;
    private static final Logger log = Logger.getLogger(FactureBean.class);
    private List<locationCustom> listLC = new ArrayList<>();
    private String numMembre;
    private Bibliotheques Bibli;

    @PostConstruct
    public void init(){
        addNewListRow();
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



        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //création de la facture
            fact.setDateDebut(timestampdebut);
            String numFact = createNumFact();
            fact.setNumeroFacture(numFact);
            String path = "Factures\\" + numFact + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.en_cours);
            fact.setUtilisateurs(u);
            // parcour de la liste des location a inscrire dans la facture
            for (locationCustom lc: listLC){
                //création des détails de la facture
                ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
                serviceEL.loueExemplaire(el);
                Timestamp timestampretour = new Timestamp(rounded+(lc.getNbrJours() *24*3600*1000));
                FacturesDetail Factdet = serviceFD.newRent(el,fact,T,lc.getNbrJours(), timestampretour);
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
        }
        finally {
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

    public String newFactPena()
    {

        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcTarifs serviceT = new SvcTarifs();
        SvcJours serviceJ = new SvcJours();

        serviceFD.setEm(service.getEm());
        serviceEL.setEm(service.getEm());

        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampdebut = new Timestamp(rounded);

        Date date = new Date();

        return "TableFactures.xhtml?faces-redirect=true";
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
