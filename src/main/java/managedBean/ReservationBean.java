package managedBean;

import entities.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import services.SvcLivres;
import services.SvcReservations;
import services.SvcUtilisateurs;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped

public class ReservationBean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Livres livre;
    private static final Logger log = Logger.getLogger(ReservationBean.class);
    private String numMembre;
    private final Bibliotheques bibliactuel = (Bibliotheques) SecurityUtils.getSubject().getSession().getAttribute("biblio");

    @PostConstruct
    public void init()
    {
        livre = new Livres();
        numMembre = "";
    }

    public void reservation() {
        SvcUtilisateurs serviceU = new SvcUtilisateurs();
        SvcReservations serviceR = new SvcReservations();
        Utilisateurs u = serviceU.getByNumMembre(numMembre).get(0);
        Reservation reservation = serviceR.createReservation(u, bibliactuel, livre);
        if(serviceR.findOneActiv(reservation).size() == 0) {
            EntityTransaction transaction = serviceR.getTransaction();
            transaction.begin();
            try {

                serviceR.save(reservation);
                transaction.commit();
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().setKeepMessages(true);
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation a reussie", null));
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.getExternalContext().getFlash().setKeepMessages(true);
                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "L'operation n'a pas reussie", null));
                }
                init();
                serviceR.close();
            }
        }
        else {
            init();
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().getFlash().setKeepMessages(true);
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le client a déjà une réservation pour ce livre dans une bibliotheque", null));
        }
    }

    public Bibliotheques getBibliactuel() {
        return bibliactuel;
    }

    public String getNumMembre() {
        return numMembre;
    }

    public void setNumMembre(String numMembre) {
        this.numMembre = numMembre;
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }
}
