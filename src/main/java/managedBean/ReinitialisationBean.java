package managedBean;

import entities.Utilisateurs;
import org.apache.log4j.Logger;
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
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Named
@SessionScoped

public class ReinitialisationBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(ReinitialisationBean.class);
    private String login;
    private String courriel;

    @PostConstruct
    public void init() {
    login="";
    courriel="";
    }

    public String reinitia()
    {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        List<Utilisateurs> results = serviceU.reinitialisation(login,courriel);
        EntityTransaction transaction = serviceU.getTransaction();
        log.debug("je passe la dedans");

        try
        {
            if (results.isEmpty())
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));
                log.debug("jerome est passé la dedans");
                return "/envoiInfo?faces-redirect=true";
            }
            else
            {
                Utilisateurs utilisateur = results.get(0);
                String mdp = randomMdp();
                transaction.begin();
                utilisateur.setMdp(mdp);
                serviceU.save(utilisateur);
                transaction.commit();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                log.debug("axel est passé la dedans");
                sendMessage(utilisateur.getCourriel(),"Vous avez demandé une réinitialisation du mot de passe, désormais votre nouveau mot de passe sera : " + mdp,"Réinitialisation du mot de passe");
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));
            }
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas réussie", null));
            }
            serviceU.close();
        }

        return "/envoiInfo?faces-redirect=true";
    }

    /*TODO : Penser a mettre les fonctions d'envoi de mail dans une même classe*/
    public static void sendMessage( String mailDest, String Texte, String Titre)  {
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

    /**
     *
     * candidateChars
     *            the candidate chars
     * length
     *            the number of random chars to be generated
     *
     *
     */
    public String randomMdp()
    {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int length = 10;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }

    public String flushLogin()
    {
        init();
        return "/login?faces-redirect=true";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

}
