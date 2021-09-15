package managedBean;

import entities.Adresses;
import entities.Bibliotheques;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import services.SvcAdresses;
import services.SvcBibliotheques;
import services.SvcUtilisateurs;
import services.SvcUtilisateursAdresses;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BibliothequesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Bibliotheques bibliotheque;
    private String nomBiblio;

    private Adresses adresses;
    private static final Logger log = Logger.getLogger(BibliothequesBean.class);

    private List<Bibliotheques> listBiblioActiv = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        bibliotheque = new Bibliotheques();
    }

    // Méthode qui va appellé la méthode save() pour créer/modifier une bibliotheque en DB et qui envoi un message si jamais le nom de la biblio a pas été changé ou si l'utilisateur
    // veut créer une nouvelle bibliothèque (limité à 1 pour ce projet) et nous renvoi sur la table des bibliothèques
    public String newBiblio()
    {
        SvcBibliotheques serviceB = new SvcBibliotheques();
        boolean flag = getCheckbibli();
        boolean flagbib =false;
        if (!flag)
        {
            flagbib = serviceB.getById(bibliotheque.getIdBibliotheques()).equals(bibliotheque);
        }
        if(getCheckbibli() || !flagbib)
        {
            save();
        }
        else if (flagbib)
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Le nom de la bibliotheque n'a pas été changé",null));
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"une seule bibliotheque autorisé",null));
        }
        serviceB.close();
        return "/tableBibliotheques.xhtml?faces-redirect=true";
    }

    // méthode qui verifie si il n'y a pas de bibliothèque déjà créée en DB
    public boolean getCheckbibli(){
        SvcBibliotheques serviceB = new SvcBibliotheques();
        if(serviceB.findAllBibliotheques().size()==0)
        {
            serviceB.close();
            return true;
        }
        else
        {
            serviceB.close();
            return false;
        }
    }
    // Méthode qui permet la sauvegarde de la bibliothèque dans la base de donnée
    public void save()
    {
        SvcAdresses serviceA = new SvcAdresses();
        SvcBibliotheques service = new SvcBibliotheques();
        serviceA.setEm(service.getEm());
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            if(bibliotheque.getIdBibliotheques()!= 0)
            {
                service.save(bibliotheque);
            }
            else
            {
                bibliotheque=service.save(bibliotheque);
                adresses.setBibliotheques(bibliotheque);
                serviceA.save(adresses);
            }

            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"L'operation a reussie",null));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"l'operation n'a pas reussie",null));
            }
            else
            {
                init();
            }
            service.close();
        }
    }

    /*
     * Méthode qui permet de vider les variables et de revenir sur le table Bibliothèques .
     *
     */
    public String flushBiblio()
    {
        init();
        return "/tableBibliotheques?faces-redirect=true";
    }
    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les bibliothèques
     */
    public List<Bibliotheques> getReadAll()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        List<Bibliotheques> listBib = new ArrayList<Bibliotheques>();
        listBib= service.findAllBibliotheques();

        service.close();
        return listBib;
    }

    public List<Bibliotheques> getReadBiblioActiv()
    {

        SvcBibliotheques serviceB = new SvcBibliotheques();
        listBiblioActiv = serviceB.findAllBiblioActiv();

        serviceB.close();
        return listBiblioActiv;
    }

    public String createFichier()
    {
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";
        try {

            // Recevoir le fichier
            File f = new File(userdir + "bibliotheque.txt");
            FileWriter fw = new FileWriter(userdir + "bibliotheque.txt");
            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile()) {
                fw.write(nomBiblio);
                System.out.println("File created");
            }
            else {
                System.out.println("File already exists");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
        return "/login?faces-redirect=true";

    }
    //-------------------------------Getter & Setter--------------------------------------------

    public Bibliotheques getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheques bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public Adresses getAdresses() {
        return adresses;
    }

    public void setAdresses(Adresses adresses) {
        this.adresses = adresses;
    }


    public String getNomBiblio() {
        return nomBiblio;
    }

    public void setNomBiblio(String nomBiblio) {
        this.nomBiblio = nomBiblio;
    }

    public List<Bibliotheques> getListBiblioActiv() {
        return listBiblioActiv;
    }

    public void setListBiblioActiv(List<Bibliotheques> listBiblioActiv) {
        this.listBiblioActiv = listBiblioActiv;
    }
}
