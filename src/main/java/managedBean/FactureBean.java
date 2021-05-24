package managedBean;

import entities.*;
import enumeration.FactureEtatEnum;
import org.apache.log4j.Logger;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import services.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
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
    private List<String> listCB;
    private String numMembre;
    private Bibliotheques Bibli;


    public void save()
    {
        SvcFacture service = new SvcFacture();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(factures);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifEd", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            service.close();
        }

    }


    public Factures newFact(List<FacturesDetail> factDet)
    {
        //TODO finaliser la méthode
        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcBibliotheques serviceB = new SvcBibliotheques();
        SvcTarifs serviceT = new SvcTarifs();
        serviceFD.setEm(service.getEm());
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date();
        Factures fact = new Factures();
        Tarifs T = serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0);
        double prixTVAC = 0;

        try {

            fact.setDateDebut(timestamp);
            for (String CB: listCB){
                ExemplairesLivres el = serviceEL.findOneByCodeBarre(CB).get(0);
                FacturesDetail Factdet = serviceFD.newRent(el,fact,T,)
            }
            serviceEL.close();
            String numFact = createNumFact();
            fact.setNumeroFacture(numFact);
            String path = "Factures\\" + numFact + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.ENCOURS);
            fact.setUtilisateurs(serviceU.getByNumMembre(numMembre).get(0));
            for(FacturesDetail FD: factDet){
                prixTVAC= prixTVAC + FD.getPrix();
            }
        }
        catch(NullPointerException npe){

        }
        return fact;
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

    public Factures getFacture() {
        return factures;
    }

    public void setFacture(Factures factures) {
        this.factures = factures;
    }

    public String getCB() {
        return CB;
    }

    public void setCB(String CB) {
        this.CB = CB;
    }

    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
    }
}
