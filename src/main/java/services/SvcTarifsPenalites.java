package services;


import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Todo: Vérifier la méthode createTarifsPenalites il faut ajouter les dates

public class SvcTarifsPenalites extends Service<TarifsPenalites> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcTarifsPenalites.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcTarifsPenalites() {
		super();
	}

	@Override
	public TarifsPenalites save(TarifsPenalites tarifsPenalites) {
		if (tarifsPenalites.getIdTarifsPenalites() == 0) {
			em.persist(tarifsPenalites);
		} else {
			tarifsPenalites = em.merge(tarifsPenalites);
		}

		return tarifsPenalites;
	}




	public List<TarifsPenalites> findAllTarifsPenalites() {
		return finder.findByNamedQuery("TarifsPenalites.findAll", null);
	}

	public TarifsPenalites createTarifsPenalites(Tarifs t, Penalites pe, Double pr, Timestamp db, Timestamp df)
	{
		TarifsPenalites tp = new TarifsPenalites();
		tp.setPenalite(pe);
		tp.setTarif(t);
		tp.setPrix(pr);
		tp.setDateDebut(db);
		tp.setDateFin(df);

		return tp;
	}


}