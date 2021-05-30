package managedBean;

import entities.Bibliotheques;
import entities.ExemplairesLivres;
import entities.Livres;
import enumeration.ExemplairesLivresEtatEnum;
import org.apache.log4j.Logger;
import services.SvcBibliotheques;
import services.SvcExemplairesLivres;
import services.SvcLivres;

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
public class ExemplairesLivresBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private ExemplairesLivres exemplairesLivre;
    private static final Logger log = Logger.getLogger(ExemplairesLivresBean.class);
    private int nombreExemplaire;
    private Bibliotheques bibli;
    private Livres livre;
    private String LastBarCode;


    public void init()
    {
        exemplairesLivre = new ExemplairesLivres();
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        SvcBibliotheques serviceB = new SvcBibliotheques();
        //puisque ce programme n'aura jamais qu'une seulle bibliotheque; on se permet de la prendre dirrectement depuis la db
        if (serviceB.findAllBibliotheques().size()==1)
        {
            bibli= serviceB.getById(1);
        }
        if (service.findlastExemplairesLivres().size()==0){
            LastBarCode = "0";
        }
        else {
            LastBarCode=service.findlastExemplairesLivres().get(0).getCodeBarre();
        }
        service.close();
        serviceB.close();
    }
    public String addExemplaireLivre(){
        init();
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            for (int i = 0; i < nombreExemplaire; i++) {

                exemplairesLivre.setBibliotheques(bibli);
                exemplairesLivre.setEtat(ExemplairesLivresEtatEnum.Bon);
                exemplairesLivre.setLivres(livre);
                exemplairesLivre.setCodeBarre(generateBarCode());
                service.save(exemplairesLivre);
                exemplairesLivre = new ExemplairesLivres();
            }
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifEd", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            else{
                init();
            }
            service.close();
        }

        return "/tableExemplaireLivres.xhtml?faces-redirect=true";
    }
    public String generateBarCode(){

        if (LastBarCode.equals("0")){
            LastBarCode="100000001";
            return LastBarCode;
        }
        else{
            LastBarCode=String.valueOf(Integer.parseInt(LastBarCode)+1);
            if (LastBarCode.equals("400000000")){
                LastBarCode="500000000";
            }
            return LastBarCode;
        }
    }

    public void save()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(exemplairesLivre);
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
    public String activdesactivExLiv()
    {
        //TODO - vérifier si état livre permet la réactivation de l'exemplaire
        /*Si le livre est actif alors on le désactive; sinon on l'active*/
        if(exemplairesLivre.isActif())
        {
            exemplairesLivre.setActif(false);
        }
        else
        {
            exemplairesLivre.setActif(true);
        }
        save();
        return "/tableLivres.xhtml?faces-redirect=true";
        }
    /*
     * Méthode qui permet via le service de retourner la liste de tous les exemplaires livres
     */
    public List<ExemplairesLivres> getReadAll()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findExemplairesLivresByLivre(livre);
        service.close();
        return listExemplaires;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public ExemplairesLivres getExemplairesLivre() {
        return exemplairesLivre;
    }

    public void setExemplairesLivre(ExemplairesLivres exemplairesLivre) {
        this.exemplairesLivre = exemplairesLivre;
    }

    public int getNombreExemplaire() {
        return nombreExemplaire;
    }

    public void setNombreExemplaire(int nombreExemplaire) {
        this.nombreExemplaire = nombreExemplaire;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }
}
