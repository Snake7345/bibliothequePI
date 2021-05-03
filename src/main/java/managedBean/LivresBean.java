package managedBean;

import entities.Auteurs;
import entities.Facture;
import entities.Jours;
import entities.Livres;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcLivres;

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
public class LivresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private final SvcLivres service = new SvcLivres();
    private static final Logger log = Logger.getLogger(LivresBean.class);
    private Livres livTemp;

    @PostConstruct
    public void init()
    {
        livre = new Livres();
    }

    public String newLivre()
    {
        EntityTransaction transaction = service.getTransaction();
        //Todo mettre/faire une verification de l'objet utilisateur,
        log.debug("J'vais essayer d'sauver le livre");
        transaction.begin();

        try {

            service.save(livre);

            transaction.commit();
            log.debug("J'ai sauvé l'adresse");
            return "/tableLivres.xhtml?faces-redirect=true";
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
            livTemp.setFields(livre);
            service.save(livTemp);
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

    public void edit()
    {
        this.livTemp = livre.clone();
    }


    public List<Livres> getReadAll()
    {
        List<Livres> listLiv = new ArrayList<Livres>();
        listLiv= service.findAllLivres();


        return listLiv;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }




}
