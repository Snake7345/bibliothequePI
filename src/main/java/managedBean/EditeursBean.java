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

    @PostConstruct
    public void init()
    {
        editeur = new Editeurs();
    }

    public String newEditeur()
    {
        //Todo mettre/faire une verification de l'objet editeur,
        save();
        return "/tableEditeurs.xhtml?faces-redirect=true";


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
            fc.addMessage("ModifEd", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
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
        return "tableEditeurs?faces-redirect=true";
    }

    /*
     * Méthode qui permet via le service de retourner
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
