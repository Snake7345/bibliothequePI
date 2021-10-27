package objectCustom;

import managedBean.RolesBean;
import org.apache.log4j.Logger;

import java.util.Date;

public class PenaCustom {

    private String name;
    private Double prix;
    private Date dateDebut, dateFin;
    private static final Logger log = Logger.getLogger(PenaCustom.class);

    public PenaCustom(String name, Double prix, Date dateDebut, Date dateFin) {
        log.debug("1 custom remplie");
        this.name = name;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;

    }

    public PenaCustom() {
        this.name = "";
        log.debug("2");
        this.prix = 0.0;
        this.dateDebut = new Date();
        this.dateFin = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
