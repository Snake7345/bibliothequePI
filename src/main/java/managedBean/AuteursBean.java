package managedBean;

import entities.Adresses;
import entities.Auteurs;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcAuteurs;
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
public class AuteursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Auteurs auteur;
    private static final Logger log = Logger.getLogger(AuteursBean.class);
    private Auteurs autTemp;

    @PostConstruct
    public void init()
    {
        auteur = new Auteurs();
    }

    public String newAuteur()
    {
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver l'auteur");
        transaction.begin();

        try {

            service.save(auteur);

            transaction.commit();
            log.debug("J'ai sauvé l'adresse");
            return "/tableAuteurs.xhtml?faces-redirect=true";
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
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            autTemp.setFields(auteur);
            service.save(autTemp);
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

    public String activdesactivAut()
    {
        SvcAuteurs service = new SvcAuteurs();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        try
        {
            transaction.begin();
            /*Si la voiture est active alors on la désactive*/
            if(auteur.isActif())
            {
                log.debug("je passe le if de désactive");
                auteur.setActif(false);
            }

            else
            {
                auteur.setActif(true);
            }


            service.save(auteur);

            transaction.commit();
            log.debug("J'ai modifié l'auteur");
            return "/tableAuteurs.xhtml?faces-redirect=true";
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

    public String flushAut()
    {
        init();
        return "tableAuteurs?faces-redirect=true";
    }

    public String SearchAuteur(String nom)
    {

        try
        {
            listVoiture2 =
                    .setParameter("plaque", plaque2)
                    .getResultList();
            log.debug("list voiture2 " + listVoiture2.size());
            if(listVoiture2.isEmpty())
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("voitRech", new FacesMessage("la voiture n'a pas été trouvé"));
                return null;
            }

        }
        finally{
            em.clear();
            em.close();
        }
        plaque2 = "";
        return "formSearchVoiture?faces-redirect=true";
    }




    public void edit()
    {
        this.autTemp = auteur.clone();
    }


    public List<Auteurs> getReadAll()
    {
        SvcAuteurs service = new SvcAuteurs();
        List<Auteurs> listAut = new ArrayList<Auteurs>();
        listAut= service.findAllAuteurs();

        service.close();
        return listAut;
    }

    public Auteurs getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
    }

    public Auteurs getAutTemp() {
        return autTemp;
    }

    public void setAutTemp(Auteurs autTemp) {
        this.autTemp = autTemp;
    }

}
