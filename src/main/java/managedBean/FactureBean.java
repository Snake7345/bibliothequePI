package managedBean;

import entities.*;
import enumeration.ExemplairesLivresEtatEnum;
import enumeration.FactureEtatEnum;
import objectCustom.locationCustom;
import org.apache.log4j.Logger;
import pdfTools.ModelFactBiblio;
import pdfTools.ModelFactBiblioPena;
import services.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Named
@SessionScoped

public class FactureBean implements Serializable {
    // DÃ©claration des variables globales
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

    public static void sendMessage( String filename, String mailDest, String Texte, String Titre)  {
        //CrÃ©ation de la session
        String mail = "bibliolibatc@gmail.com";
        String password = "porte7345";


        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\Factures\\" + filename;


        Properties properties = new Properties();


        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        try {

            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart.setText(Texte);
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(userdir);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            message.setSubject(Titre);
            message.setFrom(new InternetAddress(mail));
            message.addRecipients(Message.RecipientType.TO, mailDest);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            Transport transport = session.getTransport("smtps");
            transport.connect ("smtp.gmail.com", 465, mail, password);
            transport.sendMessage(message,new Address[] { new InternetAddress(mailDest)});
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String newFact()
    {
        //TODO finaliser la mÃ©thode

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
        Tarifs T= new Tarifs();
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);
        boolean flag = false;
        if(Bibli==null){
            flag=true;
        }
        else if (serviceT.getTarifByBiblio(date, Bibli.getNom()).size()==0){
            flag=true;
        }else {
            T = serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0);
        }
        //vÃ©rif si livre non louÃ©
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
                //crÃ©ation de la facture
                fact.setDateDebut(timestampdebut);
                fact.setNumeroFacture(createNumFact());
                String path = "Factures\\" + fact.getNumeroFacture() + ".pdf";
                fact.setLienPdf(path);
                fact.setEtat(FactureEtatEnum.en_cours);
                fact.setUtilisateurs(u);
                // parcour de la liste des location a inscrire dans la facture
                for (locationCustom lc : listLC) {
                    //crÃ©ation des dÃ©tails de la facture
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
                sendMessage(fact.getNumeroFacture()+".pdf",fact.getUtilisateurs().getCourriel(),"vous trouverez la facture concernant votre location en piece jointe","Facture de location");
                return "/tableFactures.xhtml?faces-redirect=true";
            } finally {
                //bloc pour gÃ©rer les erreurs lors de la transactions
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation n'a pas reussie",null));
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
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"une erreur est survenue, soit Le livre est déjà loué ou une information est manquante (tarif, biblotheque)",null));
            return "/formNewFact.xhtml?faces-redirect=true";
        }
    }

    public void newFactPena(FacturesDetail facturesDetail)
    {

        //TODO finaliser la mÃ©thode
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
        FacturesDetail factdetretard= null;
        ModelFactBiblioPena MFB =new ModelFactBiblioPena();
        Tarifs T = serviceT.getTarifByBiblio(Date.from(facturesDetail.getFacture().getDateDebut().toInstant()), Bibli.getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);



        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //crÃ©ation de la facture
            fact.setDateDebut(timestampfacture);
            fact.setNumeroFacture(createNumFact());
            String path = "Factures\\" + fact.getNumeroFacture() + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.terminer);
            fact.setUtilisateurs(u);

            //crÃ©ation des facture dÃ©tails
            if (tarifsPenalites.size() >= 1){
                for (TarifsPenalites tp: tarifsPenalites)
                {
                    factdet=serviceFD.newPena(facturesDetail.getExemplairesLivre(),fact,T, tp.getPenalite(), Date.from(facturesDetail.getFacture().getDateDebut().toInstant()),timestampfacture);
                    prixTVAC=prixTVAC+factdet.getPrix();
                    serviceFD.save(factdet);
                }
            }

            if (facturesDetail.getDateRetour().after(facturesDetail.getDateFin())){
                int nbjour = (int)((facturesDetail.getDateRetour().getTime() - facturesDetail.getDateFin().getTime())/(1000*60*60*24));

                if (serviceTP.findByPena(T,serviceP.findByName("Retard").get(0),Date.from(facturesDetail.getFacture().getDateDebut().toInstant())).size() >= 1){
                    factdetretard= serviceFD.newPenaretard(facturesDetail.getExemplairesLivre() , fact , T , serviceP.findByName("Retard").get(0) , nbjour , Date.from(facturesDetail.getFacture().getDateDebut().toInstant()),timestampfacture);
                    prixTVAC=prixTVAC+factdetretard.getPrix();
                    serviceFD.save(factdetretard);
                }

            }

            fact.setPrixTvac(prixTVAC);
            // sauvegarde de la facture et commit de transaction
            service.save(fact);
            transaction.commit();
            //refresh pour rÃ©cupÃ©rer les collections associÃ©es
            service.refreshEntity(fact);
            MFB.creation(fact,tarifsPenalites,factdetretard);
            sendMessage(fact.getNumeroFacture()+".pdf",fact.getUtilisateurs().getCourriel(),"vous trouverez la facture concernant les pénalités suite a votre location en piece jointe","Facture de pénalité");
        }
        finally {
            //bloc pour gÃ©rer les erreurs lors de la transactions
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Erreur fatale",null));
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
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        exemplairesLivres = serviceEL.findOneByCodeBarre(CB).get(0);
        tarifsPenalites= new ArrayList<>();
        if (choixetat){
            Date date = new Date();
            SvcTarifs serviceT = new SvcTarifs();
            tarifsPenalites= (List<TarifsPenalites>) serviceT.getTarifByBiblio(date, Bibli.getNom()).get(0).getTarifsPenalites();
            return "/formEtatLivre.xhtml?faces-redirect=true";
        }
        else {
            return retourLivre();
        }
    }


    public String retourLivre(){
        FacturesDetail facturesDetail = new FacturesDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        Factures fact = new Factures();
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampretour = new Timestamp(rounded);
        boolean flag =false;
        if (exemplairesLivres.isLoue())
        {
            for (FacturesDetail fd : exemplairesLivres.getFactureDetails()){
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
                if (facturesDetail.getDateRetour().after(facturesDetail.getDateFin()) || tarifsPenalites.size()>=1)
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
                    exemplairesLivres.setLoue(false);
                    if (exemplairesLivres.getEtat()==ExemplairesLivresEtatEnum.Mauvais)
                    {
                        exemplairesLivres.setActif(false);
                    }
                    serviceEL.save(exemplairesLivres);
                    serviceFD.save(facturesDetail);
                    if (fact.getEtat()==FactureEtatEnum.terminer){
                        service.save(fact);
                    }
                    transaction.commit();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"retour ok",null));
                } finally {
                    if (transaction.isActive()) {
                        transaction.rollback();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage("Erreur", new FacesMessage("l'operation n'est pas reussie"));
                    }
                    service.close();
                }
            }
        }
        else
        {
            //todo add facemessage pour signaler que le livre n'est pas louÃ©
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le livre n'est pas loue",null));
        }
        // todo rÃ©flÃ©chir au retour
        return "/tableFactures.xhtml?faces-redirect=true";
    }

    /*MÃ©thode permettant de crÃ©er un numÃ©ro de facture avec FB(FactureBiblio) suivi de l'annÃ©e, le mois et un nombre a 4 chiffres*/
    public String createNumFact()
    {

        String numFact="";
        Calendar cal = Calendar.getInstance();
        int annee = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH) +1;
        SvcFacture serviceF = new SvcFacture();
        List<Factures> fact;

        //tester si l'annÃ©e en cours = annÃ©e de la derniÃ¨re facture
        try
        {
            fact = serviceF.findAllFactureDesc();
            if (fact.size() != 0){
                String text = fact.get(0).getNumeroFacture();
                int anneelastFact = Integer.parseInt(text.substring(2, 6));

                if(annee == anneelastFact)
                {
                    int nb = Integer.parseInt(text.substring(text.length() - 5, text.length()));
                    numFact = "FB" + annee + String.format("%02d", mois) + String.format("%05d",nb+1);
                }
                else
                {
                    numFact = "FB" + annee + String.format("%02d", mois) + "00001";
                }
            }
            else{
                numFact = "FB" + annee + String.format("%02d", mois) + "00001";
            }
        }
        catch(NullPointerException npe) {
        }

        return numFact;
    }
    /*
     * MÃ©thode qui permet via le service de retourner la liste de toutes les factures
     */
    public List<Factures> getReadAll()
    {
        SvcFacture service = new SvcFacture();
        List<Factures> listFact = new ArrayList<Factures>();
        listFact= service.findAllFacture();

        service.close();
        return listFact;
    }

    public String flushFact()
    {
        init();
        return "/tableFactures.xhtml?faces-redirect=true";
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
