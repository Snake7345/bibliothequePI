package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
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
    private final Bibliotheques bibliactuel = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");




    @PostConstruct
    public void init()
    {
        listLiv = getReadAll();
        livre = new Livres();
        aut = new Auteurs();
        edit = new Editeurs();
        gen = new Genres();
    }
    /* Cette méthode permet la création d'un nouveau livre avec le bon format d'ISBN avec l'auteur, le genre
    * */
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
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation est reussie",null));
            return "/tableLivres.xhtml?faces-redirect=true";
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
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

    /* Cette méthode permet la sauvegarde d'un nouveau livre
     *
     */
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
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation n'a pas reussie",null));
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


    /* Cette méthode permet de désactiver un livre ainsi que les exemplaires liées.
     * Cette méthode permet aussi de réactiver un livre (mais pas les exemplaires livres associés)
     */
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
            init();
            service.close();
        }
            return "/tableLivres.xhtml?faces-redirect=true";
    }


    /* Cette méthode permet de rechercher en fonction de ce qu'encode l'utilisateur un livre
     */
    public String searchLivre()
    {
        log.debug("titre recherché: " + livre.getTitre());
        SvcLivres service = new SvcLivres();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        if(service.getByTitre(livre.getTitre()).isEmpty())
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"le livre n'a pas été trouvé",null));
            return "/formSearchLivre.xhtml?faces-redirect=true";
        }
        else
        {
            searchResults = service.getByTitre(livre.getTitre());
        }

        return "/formSearchLivre.xhtml?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur le table des Livres
    public String flushLiv()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/tableLivres.xhtml?faces-redirect=true";
    }
    //Méthode qui permet de vider les variables et de revenir sur le formulaire de création de livre
    public String flushLivNew()
    {
        init();
        if(searchResults!= null)
        {
            searchResults.clear();
        }
        return "/formNewLivre.xhtml?faces-redirect=true";
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
     * Méthode qui permet via le service de retourner le livre actuel
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
    /*
     * Méthode qui permet via le service de retourner la liste des livres en fonction de l'auteur
     */
    public String readByAuteurs()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByAuteurs(aut);

        service.close();
        return "/tableLivres.xhtml?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner la liste des livres en fonction de l'éditeur
     */
    public String readByEditeurs()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByEditeurs(edit);

        service.close();
        return "/tableLivres.xhtml?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner la liste des livres en fonction du genre
     */
    public String readByGenres()
    {
        SvcLivres service = new SvcLivres();
        listLiv = service.getByGenres(gen);

        service.close();
        return "/tableLivres.xhtml?faces-redirect=true";
    }

    //Méthode qui permet de vider les variables et de revenir sur le formulaire de recherche de livres
    public String flushLivSearch() {
        init();
        if (searchResults != null) {
            searchResults.clear();
        }
        return "/formSearchLivre?faces-redirect=true";
    }

    /*
     * Méthode qui permet via une pastille de décrire la disponibilité d'un livre
     */
    public String verifDispo(Livres liv)
    {

        boolean flag1=false;
        boolean flag2=false;
        boolean flag3=false;
        if(liv.getExemplairesLivres().size() >0)
        {
            for (ExemplairesLivres el : liv.getExemplairesLivres())
            {
                if (!el.isLoue() && (el.getBibliotheques().getIdBibliotheques() ==bibliactuel.getIdBibliotheques()) && el.isActif())
                {
                    // Le livre est disponible
                    flag1 = true;
                }
                else if (!el.isLoue() && el.isActif())
                {
                    // le livre est disponible mais pas dans la bibliothèque actuelle.
                    flag2 = true;
                }
                else if (el.isLoue())
                {
                    // Le livre est loué
                    flag3 = true;
                }
            }
        }
        if(flag1)
        {

            return "Images/vert2.png";
        }
        else if(flag2)
        {
            return "Images/orange2.png";
        }
        else if(flag3)
        {
            return "Images/rouge2.png";
        }
        else
        {
            return "Images/noir4.png";
        }
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
