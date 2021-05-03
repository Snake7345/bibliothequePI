package managedBean;

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
    private final SvcUtilisateurs service = new SvcUtilisateurs();
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
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver l'utilisateur");
        transaction.begin();

        try {

            service.save(utilisateur);

            transaction.commit();
            log.debug("J'ai sauvé l'utilisateur");
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
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            utiliTemp.setFields(utili);
            service.save(utiliTemp);
            transaction.commit();
            message.display(FacesMessage.SEVERITY_INFO, "Modifications rÃ©ussies");
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                message.display(FacesMessage.SEVERITY_ERROR, "Unknown error");
            }
            service.close();
        }

    }


    public void edit()
    {
        this.utiliTemp = utilisateur.clone();
    }


    public List<Utilisateurs> getReadAll()
    {
        List<Utilisateurs> listUtil = new ArrayList<Utilisateurs>();
             listUtil = service.findAllUtilisateurs();


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

