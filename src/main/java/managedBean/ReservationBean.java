package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcExemplairesLivres;
import services.SvcReservations;
import services.SvcUtilisateurs;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Named
@SessionScoped

public class ReservationBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private Reservation reserv;
    private ExemplairesLivres exemplairesLivres;
    private static final Logger log = Logger.getLogger(ReservationBean.class);
    private String numMembre;
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        reserv = new Reservation();
        livre = new Livres();
        exemplairesLivres = new ExemplairesLivres();
        numMembre = "";
    }

    /*Cette méthode permet la reservation d'un livre en se basant sur le numéro de membre du client et la bibliothèque actuel connecté pour effectuer la reservation*/
    public String reservation() {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcReservations serviceR = new SvcReservations();
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);
        Reservation reservation = serviceR.createReservation(u, bibliothequeActuelle, livre);
        if(serviceR.findOneActiv(reservation).size() == 0) {
            EntityTransaction transaction = serviceR.getTransaction();
            transaction.begin();
            try {

                serviceR.save(reservation);
                transaction.commit();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas reussie", null));
                }
                init();
                serviceR.close();
            }
        }
        else {
            init();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le client a déjà une réservation pour ce livre dans une bibliotheque", null));
        }
        return "/tableReservation.xhtml?faces-redirect=true";
    }
    /*Cette méthode permet d'envoyer un mail au client qui a effectué une reservation concernant un livre rentré de location*/
    public static void sendMessagereserv( String mailDest, String Texte, String Titre)  {
        //Création de la session
        String mail = "bibliolibatc@gmail.com";
        String password = "porte7345";


        Properties properties = new Properties();


        Session session = Session.getInstance(properties);
        MimeMessage message = new MimeMessage(session);
        try {

            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart.setText(Texte);
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
    /*Cette méthode permet d'envoyer un mail au client si le livre est retourné en bibliothèque et est donc disponible pour le client qui a fait la réservation*/
    public String confirmReservation()
    {
        SvcReservations serviceR = new SvcReservations();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        serviceEL.setEm(serviceR.getEm());
        List<Reservation> r= new ArrayList<>();
        if(serviceR.findAllActivbyLivre(bibliothequeActuelle, exemplairesLivres.getLivres()).size()>0)
        {
            r = serviceR.findAllActivbyLivre(bibliothequeActuelle, exemplairesLivres.getLivres());
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas réussie", null));
            return "/tableFactures.xhtml?faces-redirect=true";
        }

        EntityTransaction transaction = serviceR.getTransaction();
        transaction.begin();
        try
        {
            for(Reservation reservation : r){
                if(!reservation.isMailEnvoye())
                {
                    sendMessagereserv(reservation.getUtilisateur().getCourriel(),"Votre réservation concernant le livre : "
                            + reservation.getLivre().getTitre() + " est disponible, veuillez venir le chercher a la bibliotheque " + reservation.getBibliotheques().getNom(),
                            "Votre réservation");
                    reservation.setMailEnvoye(true);
                    exemplairesLivres.setReserve(true);
                    serviceR.save(reservation);
                    serviceEL.save(exemplairesLivres);
                    break;
                }
            }
            transaction.commit();


        }
        finally
        {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas réussie", null));
            }
            serviceR.close();
        }


        return "/tableFactures.xhtml?faces-redirect=true";
    }

    /*Cette méthode permet de retourner la liste de toutes les réservations actives*/
    public List<Reservation> getReadAllReservActiv()
    {
        SvcReservations service = new SvcReservations();
        List<Reservation> listReserv = new ArrayList<>();
        listReserv = service.findAllActivbyLivre(bibliothequeActuelle, livre);

        service.close();
        return listReserv;
    }
    /*Cette méthode permet de vider les variables et retourner sur la tables des livres*/
    public String flushReserv()
    {
        init();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    public void flushNewReserv()
    {
        exemplairesLivres = new ExemplairesLivres();
        numMembre = "";
        reserv = new Reservation();
    }
    public void flushReservPopup()
    {
        exemplairesLivres = new ExemplairesLivres();
        reserv = new Reservation();
    }
/*TODO : Optimisation possible : envoyer un mail a une autre reservation si mail déjà envoyé a celle supprimée*/
    /*Cette méthode permet de désactiver une reservation*/
    public String suppReserv()
    {
        SvcReservations serviceR = new SvcReservations();
        EntityTransaction transaction = serviceR.getTransaction();
        transaction.begin();
        try{
            reserv.setActif(false);
            serviceR.save(reserv);
            transaction.commit();
        }
        finally
        {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas réussie", null));
            }
        }
        serviceR.close();
        flushNewReserv();
        return "/tableReservation.xhtml?faces-redirect=true";
    }

    /*Cette méthode permet de désactiver une reservation via le popup des réservations d'un client*/
    public void suppReservPopup()
    {
        SvcReservations serviceR = new SvcReservations();
        EntityTransaction transaction = serviceR.getTransaction();
        transaction.begin();
        try{
            reserv.setActif(false);
            serviceR.save(reserv);
            transaction.commit();
        }
        finally
        {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas réussie", null));
            }
        }
        serviceR.close();
        flushReservPopup();
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Bibliotheques getBibliactuel() {
        return bibliothequeActuelle;
    }

    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public ExemplairesLivres getExemplairesLivres() {
        return exemplairesLivres;
    }

    public void setExemplairesLivres(ExemplairesLivres exemplairesLivres) {
        this.exemplairesLivres = exemplairesLivres;
    }

    public Reservation getReserv() {
        return reserv;
    }

    public void setReserv(Reservation reserv) {
        this.reserv = reserv;
    }
}
