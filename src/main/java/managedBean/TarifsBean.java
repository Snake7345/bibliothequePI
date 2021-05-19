package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import services.*;

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
public class TarifsBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private Tarifs tarif;
    private static final Logger log = Logger.getLogger(TarifsBean.class);

    private List<Double> prix;

    public void save()
    {
        SvcTarifs service = new SvcTarifs();
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            service.save(tarif);
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
    public String newTarif()
    {
        SvcTarifs service = new SvcTarifs();
        SvcTarifsJours serviceTJ = new SvcTarifsJours();
        SvcTarifsPenalites serviceTP = new SvcTarifsPenalites();
        EntityTransaction transaction = service.getTransaction();
        serviceTJ.setEm(service.getEm());
        serviceTP.setEm(service.getEm());
        //Todo mettre/faire une verification de l'objet Livre, ainsi que des auteurs et du genre
        log.debug("J'vais essayer d'sauver le tarif");

        transaction.begin();
        try {
            tarif = service.save(tarif);
            for (int i = 0;i < prix.size();i++) {
                serviceTJ.save(serviceTJ.createLivresAuteurs(tarif,));
            }
            transaction.commit();
            log.debug("J'ai sauvé le livre");
            return "/tableTarifs.xhtml?faces-redirect=true";
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
            }

            service.close();
        }
    }

    /*
     * Méthode qui permet via le service de retourner la liste de toutes les grilles tarifaires
     */

    public List<Tarifs> getReadAll()
    {
        SvcTarifs service = new SvcTarifs();
        List<Tarifs> listTarifs = new ArrayList<Tarifs>();
        listTarifs = service.findAllTarifs();

        service.close();
        return listTarifs;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
    }

    public List<Double> getPrix() {
        return prix;
    }

    public void setPrix(List<Double> prix) {
        this.prix = prix;
    }
}
