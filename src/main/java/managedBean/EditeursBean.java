package managedBean;

import entities.Editeurs;
import org.apache.log4j.Logger;
import services.SvcEditeurs;

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
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Editeurs editeur;
    private static final Logger log = Logger.getLogger(EditeursBean.class);

    /*Permet d'attribuer et/ou vider les variables au démarrage du bean*/
    @PostConstruct
    public void init()
    {
        editeur = new Editeurs();
    }

    // Méthode qui fait appel a la méthode save() qui permet la création de l'objet "Editeur" et renvoi un message si jamais l'éditeur existe déjà en DB
    // la méthode nous renverra sur la table des éditeurs
    public String saveEditeur()
    {
        if(verifEditeurExist(editeur))
        {
            save();
        }
        else{
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La donnée est déjà existante en DB",null));
            }
        init();
        return "/tableEditeurs.xhtml?faces-redirect=true";


    }

    // Méthode qui vérifie si un éditeur existe déjà en base de donnée
    public boolean verifEditeurExist(Editeurs ed)
    {
        SvcEditeurs serviceE = new SvcEditeurs();
        if(serviceE.findOneEditeur(ed).size() > 0)
        {
            serviceE.close();
            return false;
        }
        else {
            serviceE.close();
            return true;
        }

    }

    // Méthode qui permet la sauvegarde d'un objet "éditeur" en base de donnée
    public void save()
    {
        SvcEditeurs service = new SvcEditeurs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(editeur);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation n'est pas reussie",null));
            }
            service.close();
        }

    }

    /*
     * Méthode qui permet de vider les variables et de revenir sur le table Editeurs .
     */
    public String flushEd()
    {
        init();
        return "/tableEditeurs?faces-redirect=true";
    }
    /*
     * Méthode qui permet de vider les variables et de revenir sur la création d'un nouvel éditeur .
     */
    public String flushEdNew()
    {
        init();
        return "/formNewEditeur?faces-redirect=true";
    }

    /*
     * Méthode qui permet de retourner
     * la liste de tous les éditeurs
     */
    public List<Editeurs> getReadAll()
    {
        SvcEditeurs service = new SvcEditeurs();
        List<Editeurs> listEditeurs = new ArrayList<Editeurs>();
        listEditeurs= service.findAllEditeurs();

        service.close();
        return listEditeurs;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Editeurs getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeurs editeur) {
        this.editeur = editeur;
    }

}
