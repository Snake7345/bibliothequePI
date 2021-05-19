package services;



import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcTarifsJours extends Service<TarifsJours> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcTarifsJours.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcTarifsJours() {
		super();
	}

	@Override
	public TarifsJours save(TarifsJours tarifsJours) {
		if (tarifsJours.getIdTarifsJours() == 0) {
			em.persist(tarifsJours);
		} else {
			tarifsJours = em.merge(tarifsJours);
		}

		return tarifsJours;
	}

	public TarifsJours createTarifsJours(Tarifs t, Jours j, Double p, Timestamp db, Timestamp df)
	{
		log.debug("truc a la con numeroshlag");
		TarifsJours tj = new TarifsJours();
		tj.setJours(j);
		tj.setTarifs(t);
		tj.setPrix(p);
		tj.setDateDebut(db);
		tj.setDateFin(df);
		return tj;
	}


	public List<TarifsJours> findAllTarifsJours() {
		return finder.findByNamedQuery("TarifsJours.findAll", null);
	}


}