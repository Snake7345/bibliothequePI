package managedBean;

import entities.*;
import enumeration.ExemplairesLivresEtatEnum;
import enumeration.FactureEtatEnum;
import objectCustom.locationCustom;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.primefaces.event.UnselectEvent;
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
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Named
@SessionScoped

public class FactureBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;

    private Factures factures;
    private FacturesDetail facturesDetails;
    private static final Logger log = Logger.getLogger(FactureBean.class);
    private List<locationCustom> listLC = new ArrayList<>();
    private List<TarifsPenalites> tarifsPenalitesChoisie;
    private List<TarifsPenalites> listTarifsPenalites;
    private String numMembre;
    private String CB;
    private boolean choixetat;
    private ExemplairesLivres exemplairesLivres;
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init(){
        listLC = new ArrayList<>();
        addNewListRow();
        factures= new Factures();
        numMembre= "";
        CB= "";
        facturesDetails= new FacturesDetail();
    }

    /*Cette méthode permet d'ajouter une nouvelle ligne dans un formulaire concernant une location */
    public void addNewListRow() {
        listLC.add(new locationCustom());
    }
    /*Cette méthode permet de retirer une ligne dans un formulaire concernant une location */
    public void delListRow() {
        if (listLC.size() >1)
        {
            listLC.remove(listLC.size()-1);
        }
    }

    /*Méthode qui permet d'envoyer un mail sur le email de l'utilisateur avec la facture */
    public static void sendMessage(String filename, String mailDest, String Texte, String Titre)  {
        //Création de la session
        String mail = "bibliolibatc@gmail.com";
        String password = "porte7345";

        /*On prend la facture dans le dossier ci-dessus*/
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\Factures\\" + filename;


        Properties properties = new Properties();

        /*On crée ensuite le mail ainsi que la facture ci-jointe avec celui-ci*/
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

    public String redirectConfirmLocation()
    {
        //initialisation des services requis
        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcTarifs serviceT = new SvcTarifs();
        SvcReservations serviceR = new SvcReservations();


        //initialisation des object et variables
        double prixTVAC = 0;
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampdebut = new Timestamp(rounded);
        Date date = new Date();
        Tarifs T= new Tarifs();

        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);
        boolean flag = false;
        boolean flag2 = false;

        if (serviceT.getTarifByBiblio(date, bibliothequeActuelle.getNom()).size()==0){
            flag=true;
        }else {
            T = serviceT.getTarifByBiblio(date, bibliothequeActuelle.getNom()).get(0);
        }
        //vérification si le livre est loué
        for (locationCustom lc: listLC) {
            ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
            if (el.isLoue() || !(el.getBibliotheques().getIdBibliotheques() == (bibliothequeActuelle.getIdBibliotheques())))
            {
                flag=true;

            }
            List<Reservation> reservations = serviceR.findAllActivbyLivre(bibliothequeActuelle, el.getLivres());

            for (Reservation r : reservations)
            {
                if (r.getUtilisateur().getIdUtilisateurs()==u.getIdUtilisateurs())
                {
                    flag2=true;
                    break;
                }
            }
            if(reservations.size()>0){
                if(!flag2 && el.isReserve()){
                    flag=true;
                    break;
                }
                else{
                    flag2=false;
                }
            }

        }

        if (!flag) {

            try {

                //création de la facture
                factures.setBibliotheques(bibliothequeActuelle);
                factures.setDateDebut(timestampdebut);
                factures.setEtat(FactureEtatEnum.en_cours);
                factures.setUtilisateurs(u);
                // parcour de la liste des location a inscrire dans la facture
                for (locationCustom lc : listLC) {

                    //création des détails de la facture
                    ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);

                    Timestamp timestampretour = new Timestamp(rounded + (lc.getNbrJours() * 24 * 3600 * 1000));
                    FacturesDetail Factdet = serviceFD.newRent(el, factures, T, lc.getNbrJours(), timestampretour);

                    prixTVAC = prixTVAC + Factdet.getPrix();
                }
                factures.setPrixTvac(prixTVAC);
                return "/formConfirmationPaiementLoca.xhtml?faces-redirect=true";
            } finally {
                //fermeture des service
                service.close();
                serviceU.close();
                serviceT.close();
                serviceFD.close();
                serviceEL.close();
                serviceR.close();
            }
        }
        else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Une erreur est survenue",null));
            factures=new Factures();
            return "/formNewFact.xhtml?faces-redirect=true";
        }
    }

    // Méthode qui permet de créer une facture
    public String newFact()
    {
        //initialisation des services requis
        SvcFacture service =new SvcFacture();
        SvcFactureDetail serviceFD = new SvcFactureDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcTarifs serviceT = new SvcTarifs();
        SvcJours serviceJ = new SvcJours();
        SvcReservations serviceR = new SvcReservations();

        //rassemblement des entity managers pour la transaction
        serviceFD.setEm(service.getEm());
        serviceEL.setEm(service.getEm());
        serviceR.setEm(service.getEm());
        //initialisation des object et variables
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Date date = new Date();
        ModelFactBiblio MFB =new ModelFactBiblio();
        Tarifs T= new Tarifs();

        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);

        if (!(serviceT.getTarifByBiblio(date, bibliothequeActuelle.getNom()).size()==0))
        {
            T = serviceT.getTarifByBiblio(date, bibliothequeActuelle.getNom()).get(0);
        }

        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //création de la facture
            factures.setNumeroFacture(createNumFact());
            String path = "Factures\\" + factures.getNumeroFacture() + ".pdf";
            factures.setLienPdf(path);
            // parcour de la liste des location a inscrire dans la facture
            for (locationCustom lc : listLC) {

                //création des détails de la facture
                ExemplairesLivres el = serviceEL.findOneByCodeBarre(lc.getCB()).get(0);
                serviceEL.loueExemplaire(el);
                if(el.isReserve()){
                    serviceEL.reservExemplaire(el);
                    Reservation r= serviceR.findOneActiv(serviceR.createReservation(u,bibliothequeActuelle,el.getLivres())).get(0);
                    r.setActif(false);
                    serviceR.save(r);
                }

                Timestamp timestampretour = new Timestamp(rounded + (lc.getNbrJours() * 24 * 3600 * 1000));
                FacturesDetail Factdet = serviceFD.newRent(el, factures, T, lc.getNbrJours(), timestampretour);

                serviceFD.save(Factdet);
                serviceEL.save(el);
            }

            // sauvegarde de la facture et commit de transaction
            service.save(factures);
            transaction.commit();
            service.refreshEntity(factures);
            MFB.creation(factures, bibliothequeActuelle);
            sendMessage(factures.getNumeroFacture()+".pdf",factures.getUtilisateurs().getCourriel(),"vous trouverez la facture concernant votre location en piece jointe","Facture de location");
            factures=new Factures();
            return "/tableFactures.xhtml?faces-redirect=true";
        }
        finally
        {
            //bloc pour gérer les erreurs lors de la transactions
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation n'a pas reussie",null));
                //return "";
            }
            //fermeture des service
            service.close();
            serviceJ.close();
            serviceU.close();
            serviceT.close();
        }
    }


    // Méthode qui permet de créer une facture de pénalité
    public void newFactPena(FacturesDetail facturesDetail)
    {


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
        Tarifs T = serviceT.getTarifByBiblio(Date.from(facturesDetail.getFacture().getDateDebut().toInstant()), facturesDetail.getFacture().getBibliotheques().getNom()).get(0);
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);



        //initialisation de la transaction
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            //création de la facture
            fact.setBibliotheques(bibliothequeActuelle);
            fact.setDateDebut(timestampfacture);
            fact.setNumeroFacture(createNumFact());
            String path = "Factures\\" + fact.getNumeroFacture() + ".pdf";
            fact.setLienPdf(path);
            fact.setEtat(FactureEtatEnum.terminer);
            fact.setUtilisateurs(u);

            //création des facture détails
            if (tarifsPenalitesChoisie.size() >= 1){
                for (TarifsPenalites tp: tarifsPenalitesChoisie)
                {
                    factdet=serviceFD.newPena(facturesDetail.getExemplairesLivre(),fact,T, tp.getPenalite(), Date.from(facturesDetail.getFacture().getDateDebut().toInstant()),timestampfacture);
                    prixTVAC=prixTVAC+factdet.getPrix();
                    serviceFD.save(factdet);
                }
            }
            /*Calcul des pénalités par jour de retard ainsi que des frais de pénalités divers*/
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
            //refresh pour récupérer les collections associÃ©es
            service.refreshEntity(fact);
            MFB.creation(fact, tarifsPenalitesChoisie,factdetretard, bibliothequeActuelle);
            sendMessage(fact.getNumeroFacture()+".pdf",fact.getUtilisateurs().getCourriel(),"vous trouverez la facture concernant les pénalités suite a votre location en piece jointe","Facture de pénalité");
        }
        finally {
            //bloc pour gérer les erreurs lors de la transactions
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

    /*Méthode qui permet de récupérer les infos de l'objet "exemplaire livre" et de renvoyer sur le formulaire pour constater l'état du livre quand il est rentré de location.
    On appliquera d'appliquer éventuellement des pénalités*/
    public String redirectChoix(){
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        exemplairesLivres = serviceEL.findOneByCodeBarre(CB).get(0);
        tarifsPenalitesChoisie = new ArrayList<>();
        listTarifsPenalites = new ArrayList<>();
        if (choixetat){
            Date date = new Date();
            SvcTarifs serviceT = new SvcTarifs();
            listTarifsPenalites = (List<TarifsPenalites>) serviceT.getTarifByBiblio(date, bibliothequeActuelle.getNom()).get(0).getTarifsPenalites();
            for(TarifsPenalites tp : listTarifsPenalites)
            {
                if(tp.getPenalite().getDenomination().equals("Retard"))
                {
                    listTarifsPenalites.remove(tp);
                    break;
                }
            }
            return "/formEtatLivre.xhtml?faces-redirect=true";
        }
        else
        {
            return retourLivre();
        }
    }

    /*
     * Méthode qui permet de travailler sur le selectCheckboxMenu et de voir les éléments qui ne sont pas selectionné
     * */
    public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        context.addMessage(null, msg);
    }

    // Méthode qui permet le retour d'un exemplaire de livre et vérifie si il est loué
    public String retourLivre(){
        /*Déclaration des valeurs */
        FacturesDetail facturesDetail = new FacturesDetail();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        Factures fact = new Factures();
        long now =  System.currentTimeMillis();
        long rounded = now - now % 60000;
        Timestamp timestampretour = new Timestamp(rounded);
        boolean flag =false;
        boolean reserve = false;
        /*On vérifie si le livre est bien loué*/
        if (exemplairesLivres.isLoue())
        {
            log.debug("1");
            for (FacturesDetail fd : exemplairesLivres.getFactureDetails()){
                log.debug("2");
                if (fd.getDateRetour() == null) {
                    log.debug("3");
                    facturesDetail = fd;
                    numMembre=fd.getFacture().getUtilisateurs().getNumMembre();
                    flag=true;
                }
            }
            if (flag)
            {
                log.debug("4");
                flag=false;
                facturesDetail.setDateRetour(timestampretour);
                if (facturesDetail.getDateRetour().after(facturesDetail.getDateFin()) || tarifsPenalitesChoisie.size()>=1)
                {
                    log.debug("5");
                    newFactPena(facturesDetail);
                }
                for (FacturesDetail fd: facturesDetail.getFacture().getFactureDetails())
                {
                    log.debug("6");
                    if (fd.getDateRetour() == null) {
                        log.debug("7");
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    log.debug("8");
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
                    log.debug("9");
                    exemplairesLivres.setLoue(false);
                    if (exemplairesLivres.getEtat()==ExemplairesLivresEtatEnum.Mauvais)
                    {
                        exemplairesLivres.setActif(false);
                    }
                    exemplairesLivres.setBibliotheques(bibliothequeActuelle);
                    serviceEL.save(exemplairesLivres);
                    serviceFD.save(facturesDetail);
                    if (fact.getEtat()==FactureEtatEnum.terminer){
                        log.debug("10");
                        service.save(fact);
                    }

                    transaction.commit();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"retour ok",null));
                } finally {
                    log.debug("11");
                    if (transaction.isActive()) {
                        log.debug("12");
                        transaction.rollback();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage("Erreur", new FacesMessage("l'operation n'est pas reussie"));
                    }
                    service.close();
                    SvcReservations serviceR = new SvcReservations();

                    if(serviceR.findAllActivbyLivre(bibliothequeActuelle, exemplairesLivres.getLivres()).size()>0)
                    {
                        log.debug("13");
                        reserve = true;
                    }
                    serviceR.close();
                }
            }
        }
        else
        {
            log.debug("14");
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le livre n'est pas loue",null));
        }
        if(reserve)
        {
            log.debug("15");
            return "/formConfirmationReservation.xhtml?faces-redirect=true";
        }
        else {
            log.debug("16");
            return "/tableFactures.xhtml?faces-redirect=true";
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

        //On teste si l'année en cours est égale à l'année de la dernière facture
        try
        {
            fact = serviceF.findAllFactureDesc();
            if (fact.size() != 0){
                String text = fact.get(0).getNumeroFacture();
                int anneelastFact = Integer.parseInt(text.substring(2, 6));

                if(annee == anneelastFact)
                {
                    int nb = Integer.parseInt(text.substring(text.length() - 6, text.length()));
                    numFact = "FB" + annee + String.format("%02d", mois) + String.format("%06d",nb+1);
                }
                else
                {
                    numFact = "FB" + annee + String.format("%02d", mois) + "000001";
                }
            }
            else{
                numFact = "FB" + annee + String.format("%02d", mois) + "000001";
            }
        }
        catch(NullPointerException ignored) {
        }
        serviceF.close();
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

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les factures en fonction de la bibliothèque où on est connecté
     */
    public List<Factures> getReadAllByBiblio()
    {
        SvcFacture service = new SvcFacture();
        List<Factures> listFact = new ArrayList<Factures>();
        listFact= service.findAllByBibliotheque(bibliothequeActuelle.getIdBibliotheques());

        service.close();
        return listFact;
    }

    /*
     * Méthode qui permet de vider les variables et retourne sur la table des factures
     * */
    public String flushFact()
    {
        init();
        return "/tableFactures.xhtml?faces-redirect=true";
    }

    public String flushFactNew()
    {
        init();
        return "/formNewFact.xhtml?faces-redirect=true";
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
        return bibliothequeActuelle;
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

    public List<TarifsPenalites> getTarifsPenalitesChoisie() {
        return tarifsPenalitesChoisie;
    }

    public void setTarifsPenalitesChoisie(List<TarifsPenalites> tarifsPenalitesChoisie) {
        this.tarifsPenalitesChoisie = tarifsPenalitesChoisie;
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

    public List<TarifsPenalites> getListTarifsPenalites() {
        return listTarifsPenalites;
    }

    public void setListTarifsPenalites(List<TarifsPenalites> listTarifsPenalites) {
        this.listTarifsPenalites = listTarifsPenalites;
    }
}
