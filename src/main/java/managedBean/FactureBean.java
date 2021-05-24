package managedBean;

import entities.*;
import enumeration.FactureEtatEnum;
import objectCustom.locationCustom;
import org.apache.log4j.Logger;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import services.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.transaction.Transaction;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class FactureBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures factures;
    private static final Logger log = Logger.getLogger(FactureBean.class);
    private List<locationCustom> listLC;
    private String numMembre;
    private Bibliotheques Bibli;

    public String newFact()
    {
        //TODO finaliser la méthode

        //initialisation des servicees requis
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
        Tarifs T = serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);

        //fermeture des services utilisé lors de l'initialisation
        serviceU.close();
        serviceT.close();

        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        try {
            //création de la facture
            fact.setDateDebut(timestamp);
            String numFact = createNumFact();
            fact.setNumeroFacture(numFact);
            String path = "Factures\\" + numFact + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.ENCOURS);
            fact.setUtilisateurs(u);
            // parcour de la liste des location a inscrire dans la facture
            for (locationCustom lc: listLC){
                //création des détails de la facture
                ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
                Jours j = serviceJ.findByNbrJ(lc.getNbrJours()).get(0);
                FacturesDetail Factdet = serviceFD.newRent(el,fact,T,j,timestamp);
                serviceFD.save(Factdet);
                prixTVAC = prixTVAC + Factdet.getPrix();
            }
            // fermeture des services utilisé pour la créations de détails
            serviceEL.close();
            serviceJ.close();

            fact.setPrixTvac(prixTVAC);
            // sauvegarde de la facture et commit de transaction
            service.save(fact);
            transaction.commit();
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
            service.close();
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
        catch(NullPointerException npe) {
            log.debug("Problème: pas trouvé de facture");
        }
        catch(NoResultException nre) {
            numFact = "FB" + annee + String.format("%02d", mois) + "00001";
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
