package managedBean;

import entities.Adresses;
import entities.Utilisateurs;
import enumeration.UtilisateurSexeEnum;
import org.apache.log4j.Logger;
import services.SvcUtilisateurs;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class UtilisateursBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Utilisateurs utilisateur;
    private Adresses adresse;
    private static final Logger log = Logger.getLogger(UtilisateursBean.class);
    private Utilisateurs utiliTemp;

    public UtilisateursBean()
    {
        super();
    }

    @PostConstruct
    public void init()
    {
        utilisateur = new Utilisateurs();
    }

    public String newUtil()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver l'utilisateur");
        transaction.begin();

        try {

            utilisateur= service.save(utilisateur);

            transaction.commit();
            log.debug("J'ai sauvé l'utilisateur " + utilisateur.getIdUtilisateurs());
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }
            service.close();
        }

    }

    public void save()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            utiliTemp.setFields(utilisateur);
            service.save(utiliTemp);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("Erreur inconnue"));
            }
            service.close();
        }

    }

    public String activdesactivUtil()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        try
        {
            transaction.begin();
            /*Si la voiture est active alors on la désactive*/
            if(utilisateur.isActif())
            {
                log.debug("je passe le if de désactive");
                utilisateur.setActif(false);
            }

            else
            {
                utilisateur.setActif(true);
            }


            service.save(utilisateur);

            transaction.commit();
            log.debug("J'ai modifié l'utilisateur");
            return "/tableUtilisateurs.xhtml?faces-redirect=true";
        }
        finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("Erreur inconnue"));

                return "";
            }
            else
            {
                log.debug("je suis censé avoir réussi");
                init();
            }
            service.close();
        }
    }

    public String flushUtil()
    {
        init();
        return "tableUtilisateurs?faces-redirect=true";
    }


    public List<Utilisateurs> getReadAll()
    {
        SvcUtilisateurs service = new SvcUtilisateurs();
        List<Utilisateurs> listUtil = new ArrayList<Utilisateurs>();
             listUtil = service.findAllUtilisateurs();

        service.close();
        return listUtil;
    }


//-------------------------------Getter & Setter--------------------------------------------
    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Utilisateurs getUtiliTemp() {
        return utiliTemp;
    }

    public void setUtiliTemp(Utilisateurs utiliTemp) {
        this.utiliTemp = utiliTemp;
    }



}

