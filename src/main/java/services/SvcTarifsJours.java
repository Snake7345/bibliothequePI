package services;



import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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

	public List<TarifsJours> findByNbrJours(Tarifs t, Jours j) {
		Map<String, String> param = new HashMap<>();
		Date date = new Date();
		param.put("dateDebut", date.toString());
		param.put("nbrJour", String.valueOf(j.getNbrJour()));
		param.put("denominationTarif", t.getDenomination());
		param.put("dateFin", date.toString());
		return finder.findByNamedQuery("TarifsJours.findByNbrJours", param);
	}

	public TarifsJours createTarifsJours(Tarifs t, Jours j, Double p, Date db, Date df)
	{
		log.debug("truc a la con numeroshlag");
		TarifsJours tj = new TarifsJours();
		tj.setJours(j);
		tj.setTarif(t);
		tj.setPrix(p);
		tj.setDateDebut(db);
		tj.setDateFin(df);
		return tj;
	}


	public List<TarifsJours> findAllTarifsJours() {
		return finder.findByNamedQuery("TarifsJours.findAll", null);
	}


}