package managedBean;

import entities.Facture;
import entities.FactureDetail;
import entities.Genres;
import org.apache.log4j.Logger;
import services.SvcFacture;
import services.SvcGenres;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class GenresBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Genres genre;
    private final SvcGenres service = new SvcGenres();
    private static final Logger log = Logger.getLogger(GenresBean.class);

    public List<Genres> getReadAll()
    {
        List<Genres> listGenres = new ArrayList<Genres>();
        listGenres= service.findAllGenres();


        return listGenres;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

}
