package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import services.SvcExemplairesLivres;
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
/*
* TODO :
*  - Formulaire confirmation
* */
public class LivresBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private static final Logger log = Logger.getLogger(LivresBean.class);
    private LivresGenres livresGenre;
    private LivresAuteurs livresAuteur;
    private List<Genres> genre;
    private List<Auteurs> auteur;
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
        aut = new Auteurs();
        edit = new Editeurs();
        gen = new Genres();
    }

    public String newLivre()
    {
        SvcLivres service = new SvcLivres();
        SvcLivresGenres serviceLG = new SvcLivresGenres();
        SvcLivresAuteurs serviceLA = new SvcLivresAuteurs();
        EntityTransaction transaction = service.getTransaction();
        serviceLA.setEm(service.getEm());
        serviceLG.setEm(service.getEm());


        transaction.begin();
        try {
            livre.setIsbn(livre.getIsbn().replace("-", ""));
            livre = service.save(livre);
            for (Auteurs auteurs : auteur) {
                serviceLA.save(serviceLA.createLivresAuteurs(livre, auteurs));
            }
            for (Genres genres : genre) {
                serviceLG.save(serviceLG.createLivresGenres(livre, genres));
            }

            transaction.commit();
            return "/tableLivres.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'est pas reussie",null));
            }
            else
            {

                init();
            }

            auteur.clear();
            genre.clear();
            service.close();
        }
    }

    public void save()
    {
        SvcLivres service = new SvcLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            livre.setIsbn(livre.getIsbn().replace("-", ""));
            service.save(livre);

            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
            }
            else
            {
                init();
            }
            auteur.clear();
            genre.clear();
            service.close();
        }

    }



    public String activdesactivLiv()
    {
        SvcLivres service = new SvcLivres();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        serviceEL.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            /*Si le livre est actif alors on le désactive; sinon on l'active*/
            if(livre.isActif())
            {
                livre.setActif(false);
                for (ExemplairesLivres el:livre.getExemplairesLivres()) {
                    el.setActif(false);
                    serviceEL.save(el);
                }
            }
            else
            {
                livre.setActif(true);
            }
            service.save(livre);

            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation a échoué",null));
            }
            service.close();
        }
            return "/tableLivres.xhtml?faces-redirect=true";
    }

    public String searchLivre()
    {

        SvcLivres service = new SvcLivres();

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
        return "/formSearchLivre.xhtml?faces-redirect=true";
    }

    public String flushLiv()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/tableLivres.xhtml?faces-redirect=true";
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
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    public String readByEditeurs()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByEditeurs(edit);

        service.close();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    public String readByGenres()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByGenres(gen);

        service.close();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    public String flushBienv()
    {
        init();
        return "/bienvenue.xhtml?faces-redirect=true";
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
