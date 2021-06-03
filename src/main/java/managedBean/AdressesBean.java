package managedBean;

import entities.Adresses;
import org.apache.log4j.Logger;
import services.SvcAdresses;

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
public class AdressesBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Adresses adresse;
    private static final Logger log = Logger.getLogger(AdressesBean.class);

    @PostConstruct
    public void init()
    {
        adresse = new Adresses();
    }


    public String newAdress()
    {
        save();
        return "/tableAdresses.xhtml?faces-redirect=true";
    }
    public void save()
    {
        SvcAdresses service = new SvcAdresses();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(adresse);
            transaction.commit();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("ModifRe", new FacesMessage("Modification réussie"));
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("Erreur", new FacesMessage("une erreur est survenue"));
            }
            else
            {
                init();
            }
            service.close();
        }
    }
    /*
     * Méthode qui permet de vider les variables et de revenir sur le table des Adresses .
     * */
    public String flushAdd()
    {
        init();
        return "/tableAdresses?faces-redirect=true";
    }


    /*
     * Méthode qui permet via le service de retourner
     * la liste des adresses
     */
    public List<Adresses> getReadAll()
    {
        SvcAdresses service = new SvcAdresses();
        List<Adresses> listAd = new ArrayList<Adresses>();
        listAd= service.findAllAdresses();

        service.close();
        return listAd;
    }


//-------------------------------Getter & Setter--------------------------------------------

    public Adresses getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresses adresse) {
        this.adresse = adresse;
    }



}
