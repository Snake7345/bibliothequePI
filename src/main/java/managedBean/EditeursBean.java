package managedBean;

import entities.Auteurs;
import entities.Bibliotheques;
import entities.Editeurs;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcEditeurs;

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
public class EditeursBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Editeurs editeur;
    private static final Logger log = Logger.getLogger(EditeursBean.class);

    @PostConstruct
    public void init()
    {
        editeur = new Editeurs();
    }

    public String newEditeur()
    {
        SvcEditeurs service = new SvcEditeurs();
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver l'editeur");
        transaction.begin();

        try {

            service.save(editeur);

            transaction.commit();
            log.debug("J'ai sauvé l'editeur");
            return "/tableEditeurs.xhtml?faces-redirect=true";
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
        SvcEditeurs service = new SvcEditeurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(editeur);
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

    public String flushEd()
    {
        init();
        return "tableEditeurs?faces-redirect=true";
    }

    public List<Editeurs> getReadAll()
    {
        SvcEditeurs service = new SvcEditeurs();
        List<Editeurs> listEditeurs = new ArrayList<Editeurs>();
        listEditeurs= service.findAllEditeurs();

        service.close();
        return listEditeurs;
    }


    public Editeurs getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeurs editeur) {
        this.editeur = editeur;
    }

}
