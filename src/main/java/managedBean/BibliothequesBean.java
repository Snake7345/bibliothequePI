package managedBean;

import entities.Adresses;
import entities.Bibliotheques;
import entities.Utilisateurs;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import security.SecurityManager;
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
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Named
@SessionScoped
public class BibliothequesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Bibliotheques bibliotheque;
    private String nomBiblio;

    private final int idBiblio = recupIdBiblio();
    private Adresses adresses;
    private static final Logger log = Logger.getLogger(BibliothequesBean.class);
    private List<Bibliotheques> listBiblioActiv = new ArrayList<>();
    private final List<Bibliotheques> listBibactuel = getFindBiblio();


    @PostConstruct
    public void init()
    {

        bibliotheque = new Bibliotheques();
        listBiblioActiv = getReadBiblioActiv();
    }

    public int recupIdBiblio()
    {
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";
        String result = "";
        int result2;
        try
        {
            // Le fichier d'entrée
            File file = new File(userdir + "bibliotheque.txt");
            // Créer l'objet File Reader
            FileReader fr = new FileReader(file);
            // Créer l'objet BufferedReader
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null)
            {
                // ajoute la ligne au buffer
                sb.append(line);
            }
            fr.close();
            System.out.println("Contenu du fichier: ");
            System.out.println(sb.toString());
            result = sb.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        result2 = Integer.parseInt(result);
        log.debug("La valeur de la bibliotheque est de : " + result2);
        return result2;
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

    public String flushAccueil()
    {
        init();
        return "/bienvenue?faces-redirect=true";
    }

    public void reinitialisation()
    {
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";

        try{

            File f = new File(userdir + "bibliotheque.txt");

            if(f.delete())
            {
                log.debug(f.getName() + " est supprimé.");
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reinitialisation du programme",null));

                SecurityManager.processToLogout();
                beforePageLoad();
            }
            else
            {
                log.debug("Opération de suppression echouée");
                /*TODO : ajouter une erreur 404 serait une bonne idée*/
            }

        }catch(Exception e){
            e.printStackTrace();
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

    public void beforePageLoad() throws IOException {
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";
        File f = new File(userdir + "bibliotheque.txt");
        log.debug("entree debut programme");
        if(!f.isFile())
        {
            log.debug("Fichier biblio non trouve");
            FacesContext.getCurrentInstance().getExternalContext().redirect("formSelectBiblio.xhtml");
        }
        else
        {
            log.debug("Fichier biblio trouve");
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }


    public String createFichier()
    {
        String userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";
        try {

            // Recevoir le fichier
            File f = new File(userdir + "bibliotheque.txt");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile()) {
                FileWriter fw = new FileWriter(userdir + "bibliotheque.txt");
                fw.write(nomBiblio);
                log.debug("File created");
                fw.close();
            }
            else {
                log.debug("File already exists");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "/login?faces-redirect=true";

    }

    public List<Bibliotheques> getFindBiblio()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        List<Bibliotheques> listBib = new ArrayList<Bibliotheques>();
        listBib= service.findById(idBiblio);

        service.close();
        return listBib;
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

    public int getIdBiblio() {
        return idBiblio;
    }

    public List<Bibliotheques> getListBibactuel() {
        return listBibactuel;
    }
}
