package managedBean;

import entities.ExemplairesLivres;
import entities.Livres;
import org.apache.log4j.Logger;
import services.SvcExemplairesLivres;

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
    private Livres livre;
    private String LastBarCode;
    public void init()
    {
        nombreExemplaire=0;
        SvcExemplairesLivres service = new SvcExemplairesLivres();

        if (service.findlastExemplairesLivres().size()==0){
            LastBarCode = "0";
        }
        else {

            LastBarCode=service.findlastExemplairesLivres().get(0).getCodeBarre();
        }
                livre = new Livres();
    }
    public String addExemplaireLivre(int nombreExemplaire){

        SvcExemplairesLivres service = new SvcExemplairesLivres();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            for (int i = 0; i < nombreExemplaire; i++) {
                exemplairesLivre.setCodeBarre(generateBarCode());
                service.save(exemplairesLivre);
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

        return "";
    }
    public String generateBarCode(){
        String barCode;
        if (LastBarCode=="0"){
            barCode=livre.getIsbn()+"00000";
        }
        else{
            barCode=String.valueOf(Integer.parseInt(LastBarCode)+1);
        }

        return barCode;
    }

    //méthode save probablement inutile dans ce bean puisque appel custom
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

    /*
     * Méthode qui permet via le service de retourner la liste de tous les exemplaires livres
     */
    public List<ExemplairesLivres> getReadAll()
    {
        SvcExemplairesLivres service = new SvcExemplairesLivres();
        List<ExemplairesLivres> listExemplaires = new ArrayList<ExemplairesLivres>();
        listExemplaires= service.findAllExemplairesLivres();

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
