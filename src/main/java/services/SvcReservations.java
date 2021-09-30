package services;

import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcReservations extends Service<Reservation> implements Serializable {
    private static final Logger log = Logger.getLogger(services.SvcReservations.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public SvcReservations() {
        super();
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == 0) {
            em.persist(reservation);
        } else {
            reservation = em.merge(reservation);
        }

        return reservation;
    }

    public Reservation createReservation(Utilisateurs u, Bibliotheques b, Livres l)
    {
        Reservation r = new Reservation();
        r.setBibliotheques(b);
        r.setUtilisateur(u);
        r.setLivre(l);

        return r;
    }

    public List<Reservation> findOneActiv(Reservation re)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("utilisateur", re.getUtilisateur());
        param.put("livre", re.getLivre());
        return finder.findByNamedQuery("Reservation.findOneActiv",param);
    }

    public List<Reservation> findAllActivbyLivre(Bibliotheques b, Livres l)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("bibliotheque", b);
        param.put("livre", l);
        return finder.findByNamedQuery("Reservation.findAllActivByLivre",param);
    }
}
