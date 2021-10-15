package managedBean;

import entities.Adresses;
import entities.Bibliotheques;
import entities.ExemplairesLivres;
import entities.Reservation;
import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import security.SecurityManager;
import services.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class BibliothequesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Bibliotheques bibliotheque = new Bibliotheques();
    private String nomBiblio;

    private int idBiblio;
    private final Bibliotheques bibliothequeActuelle = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");
    private Adresses adresses;
    private static final Logger log = Logger.getLogger(BibliothequesBean.class);
    private List<Bibliotheques> listBiblioActiv = new ArrayList<>();
    private List<Bibliotheques> listBibactuel;
    private String userdir;
    private String nom;

    @PostConstruct
    public void init()
    {
        userdir = System.getProperty("user.dir");
        userdir = userdir.substring(0,userdir.length()-24) + "\\src\\main\\webapp\\";
        File f = new File(userdir + "bibliotheque.txt");
        if(f.isFile())
        {
            idBiblio = recupIdBiblio();
        }
        bibliotheque = new Bibliotheques();
        listBiblioActiv = getReadBiblioActiv();
        listBibactuel = getFindBiblio();
        if(listBibactuel.size() > 0) {
            SecurityUtils.getSubject().getSession().setAttribute("biblio", listBibactuel.get(0));
        }

    }

    /*Méthode qui permet de récupérer l'id de la bibliothèque contenu dans le fichier bibliotheque.txt*/
    public int recupIdBiblio()
    {
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
        return result2;
    }

    // Méthode qui va appellé la méthode save() pour créer/modifier une bibliotheque en DB et nous renvoi sur la table des bibliothèques
    public String newBiblio()
    {
        save();
        return "/tableBibliotheques.xhtml?faces-redirect=true";
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

    //Méthode qui permet de vider les variables et de revenir sur la page d'accueil
    public String flushAccueil()
    {
        init();
        return "/bienvenue?faces-redirect=true";
    }

    /*Méthode permettant la réinitialisation de la bibliothèque liée au programme.
    * En supprimant le fichier bibliotheque.txt, l'utilisateur sera déconnecté de la session
    * et renvoyer sur la page de selection de la bibliothèque*/
    public void reinitialisation()
    {
        try{

            File f = new File(userdir + "bibliotheque.txt");

            if(f.delete())
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reinitialisation du programme",null));
                SecurityManager.processToLogout();
                beforePageLoad();
            }
            else
            {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Reinitialisation du programme échouée",null));
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
     * Méthode qui permet de vider les variables et de revenir sur la création d'une nouvelle bibliothèque .
     *
     */
    public String flushBiblioNew()
    {
        init();
        return "/formNewBibliotheque?faces-redirect=true";
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
    /*
     * Méthode qui permet via le service de retourner
     * la liste de toutes les bibliothèques actives
     */
    public List<Bibliotheques> getReadBiblioActiv()
    {

        SvcBibliotheques serviceB = new SvcBibliotheques();
        listBiblioActiv = serviceB.findAllBiblioActiv();

        serviceB.close();
        return listBiblioActiv;
    }


    /*
    * cette méthode va choisir la page de démarrage du programme en fonction si bibliotheque.txt existe ou non
     */
    public void beforePageLoad() throws IOException {


        File f = new File(userdir + "bibliotheque.txt");
        if(!f.isFile())
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("formSelectBiblio.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }

    /*
     * cette méthode va créer le fichier bibliotheque.txt si il n'existe pas en fonction de la bibliothèque selectionné dans le formulaire de démarrage de programme
     */
    public String createFichier()
    {

        try {

            // Recevoir le fichier
            File f = new File(userdir + "bibliotheque.txt");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile()) {
                FileWriter fw = new FileWriter(userdir + "bibliotheque.txt");
                fw.write(nomBiblio);
                fw.close();
            }
            else {
                log.debug("File already exists");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        init();
        return "/login?faces-redirect=true";

    }
    /*
     * cette méthode va rechercher une bibliotheque en fonction de la bibliothèque selectionnée
     */
    public List<Bibliotheques> getFindBiblio()
    {
        SvcBibliotheques service = new SvcBibliotheques();
        List<Bibliotheques> listBib = service.findById(idBiblio);
        service.close();
        return listBib;
    }
    /*
     * cette méthode va permettre de désactiver la bibliothèque ainsi que les exemplaires des livres sauf si ils sont loué ou reservé.
     * Cette méthode permet aussi la réactivation de la bibliothèque
     */
    public String ActivDesactivBiblio()
    {
        SvcBibliotheques serviceB = new SvcBibliotheques();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        serviceEL.setEm(serviceB.getEm());
        EntityTransaction transaction = serviceB.getTransaction();

        transaction.begin();
        try
        {
            if(bibliotheque.isActif())
            {
                bibliotheque.setActif(false);
                for (ExemplairesLivres el : bibliotheque.getExemplairesLivres()) {
                    if(el.isActif() && !el.isLoue() && !el.isReserve())
                    {
                        el.setActif(false);
                        serviceEL.save(el);
                    }
                    else if (el.isLoue())
                    {
                        transaction.rollback();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"toutes les locations doivent être cloturer avant la fermeture de la bibliothèque ",null));
                        return "/tableBibliotheques.xhtml?faces-redirect=true";
                    }
                    else if (el.isReserve())
                    {
                        transaction.rollback();
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.getExternalContext().getFlash().setKeepMessages(true);
                        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tout les livres doivent être disponible (pas de transfert pas de réservation) avant la fermeture de la bibliothèque ",null));
                        return "/tableBibliotheques.xhtml?faces-redirect=true";
                    }
                }
            }
            else
            {
                bibliotheque.setActif(true);
            }
            serviceB.save(bibliotheque);

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
            serviceB.close();
        }
        return "/tableBibliotheques.xhtml?faces-redirect=true";
    }

    /*
     * cette méthode va permettre de désactiver la bibliothèque et de transférer les exemplaires des livres dans la bibliothèque connecté
     * et de nous renvoyer sur la table des bibliothèques
     */
    public String DesactivBiblioTransfert()
    {
        SvcBibliotheques serviceB = new SvcBibliotheques();
        SvcExemplairesLivres serviceEL = new SvcExemplairesLivres();
        SvcReservations serviceR = new SvcReservations();
        serviceEL.setEm(serviceB.getEm());
        serviceR.setEm(serviceB.getEm());
        EntityTransaction transaction = serviceB.getTransaction();

        transaction.begin();
        try
        {
            if(bibliotheque.isActif())
            {
                bibliotheque.setActif(false);
                for (ExemplairesLivres el : bibliotheque.getExemplairesLivres()) {
                    if(el.isActif())
                    {
                        el.setBibliotheques(bibliothequeActuelle);
                        serviceEL.save(el);
                    }
                }
                for (Reservation r : bibliotheque.getReservations())
                {
                    if(r.isActif())
                    {
                        r.setBibliotheques(bibliothequeActuelle);
                    }
                }
            }
            else
            {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"La bibliotheque est déjà désactivé ",null));
                return "/tableBibliotheques.xhtml?faces-redirect=true";
            }
            serviceB.save(bibliotheque);

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
            serviceB.close();
        }
        return "/tableBibliotheques.xhtml?faces-redirect=true";
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

    public void setIdBiblio(int idBiblio) {
        this.idBiblio = idBiblio;
    }

    public void setListBibactuel(List<Bibliotheques> listBibactuel) {
        this.listBibactuel = listBibactuel;
    }

    public String getUserdir() {
        return userdir;
    }

    public void setUserdir(String userdir) {
        this.userdir = userdir;
    }

    public Bibliotheques getBibli() {
        return bibliothequeActuelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
