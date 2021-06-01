package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import services.SvcLivres;
import services.SvcLivresAuteurs;
import services.SvcLivresGenres;

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
/*TODO :          Parcourir la chaine de caractère et éliminer tous les tirets. (ISBN)*/
public class LivresBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private static final Logger log = Logger.getLogger(LivresBean.class);

    private LivresAuteurs livresAuteur;
    private List<Auteurs> auteur;
    private LivresGenres livresGenre;
    private List<Genres> genre;

    private Auteurs aut;
    private Editeurs edit;
    private Genres gen;

    private List<Livres> listLiv = new ArrayList<Livres>();

    private List<Livres> searchResults;

    @PostConstruct
    public void init()
    {
        listLiv = getReadAll();
        livre = new Livres();
    }

    public String newLivre()
    {
        SvcLivres service = new SvcLivres();
        SvcLivresGenres serviceLG = new SvcLivresGenres();
        SvcLivresAuteurs serviceLA = new SvcLivresAuteurs();
        EntityTransaction transaction = service.getTransaction();
        serviceLA.setEm(service.getEm());
        serviceLG.setEm(service.getEm());

        //Todo mettre/faire une verification de l'objet Livre, ainsi que des auteurs et du genre
        log.debug("J'vais essayer d'sauver le livre");

        transaction.begin();
        try {
            //ISBN.replace("-","");
            livre = service.save(livre);
            for (Auteurs auteurs : auteur) {
                serviceLA.save(serviceLA.createLivresAuteurs(livre, auteurs));
            }
            for (Genres genres : genre) {
                serviceLG.save(serviceLG.createLivresGenres(livre, genres));
            }

            transaction.commit();
            log.debug("J'ai sauvé le livre");
            return "/tableLivres.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                log.debug("J'ai fait une erreur dans livre et je suis con");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("erreur", new FacesMessage("le rollback a pris le relais"));
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
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(livre);
            //
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("le rollback a pris le relais"));
            }
            service.close();
        }

    }

    public String activdesactivLiv()
    {
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        log.debug("je débute la méthode activdésactive");
        /*Si le livre est actif alors on le désactive; sinon on l'active*/
            if(livre.isActif())
            {
                log.debug("je passe le if de désactive");
                livre.setActif(false);
            }
            else
            {
                livre.setActif(true);
            }
            save();
            log.debug("J'ai modifié le livre");
            return "/tableLivres.xhtml?faces-redirect=true";
    }

    public String searchLivre()
    {

        SvcLivres service = new SvcLivres();
        //try
        //{

        log.debug("list livres " + service.getByTitre(livre.getTitre()).size());
        if(service.getByTitre(livre.getTitre()).isEmpty())
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("livRech", new FacesMessage("le livre n'a pas été trouvé"));
            return null;
        }
        else
        {
            searchResults = service.getByTitre(livre.getTitre());
        }

        //}
        //catch
        //{

        //}
        return "formSearchLivre?faces-redirect=true";
    }

    public String flushLiv()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "tableLivres?faces-redirect=true";
    }


    /*
     * Méthode qui permet via le service de retourner la liste de tous les livres
     */
    public List<Livres> getReadAll()
    {
        SvcLivres service = new SvcLivres();

        listLiv= service.findAllLivres();

        service.close();
        return listLiv;
    }
    /*
     * Méthode qui permet via le service de retourner sous une liste le livre actuel
     */
    public List<Livres> getReadlivre()
    {
        listLiv.clear();
        listLiv.add(livre);

        return listLiv;
    }

    /*
     * Méthode qui permet via le service de retourner la liste de tous les livres actifs
     */
    public List<Livres> getReadActiv()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.findAllLivresActiv();

        service.close();
        return listLiv;
    }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les inactifs
     */
    public List<Livres> getReadInactiv()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.findAllLivresInactiv();

        service.close();
        return listLiv;
    }

    public String readByAuteurs()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByAuteurs(aut);

        service.close();
        return "tableLivres?faces-redirect=true";
    }

    public String readByEditeurs()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByEditeurs(edit);

        service.close();
        return "tableLivres?faces-redirect=true";
    }

    public String readByGenres()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByGenres(gen);

        service.close();
        return "tableLivres?faces-redirect=true";
    }

    public String flushBienv()
    {
        init();
        return "bienvenue?faces-redirect=true";
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public List<Livres> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Livres> searchResults) {
        this.searchResults = searchResults;
    }

    public LivresAuteurs getLivresAuteur() {
        return livresAuteur;
    }

    public void setLivresAuteur(LivresAuteurs livresAuteur) {
        this.livresAuteur = livresAuteur;
    }

    public LivresGenres getLivresGenre() {
        return livresGenre;
    }

    public void setLivresGenre(LivresGenres livresGenre) {
        this.livresGenre = livresGenre;
    }

    public List<Genres> getGenre() {
        return genre;
    }

    public void setGenre(List<Genres> genre) {
        this.genre = genre;
    }

    public List<Auteurs> getAuteur() {
        return auteur;
    }

    public void setAuteur(List<Auteurs> auteur) {
        this.auteur = auteur;
    }

    public List<Livres> getListLiv() {
        return listLiv;
    }

    public void setListLiv(List<Livres> listLiv) {
        this.listLiv = listLiv;
    }

    public Auteurs getAut() {
        return aut;
    }

    public void setAut(Auteurs aut) {
        this.aut = aut;
    }

    public Editeurs getEdit() {
        return edit;
    }

    public void setEdit(Editeurs edit) {
        this.edit = edit;
    }

    public Genres getGen() {
        return gen;
    }

    public void setGen(Genres gen) {
        this.gen = gen;
    }
}
