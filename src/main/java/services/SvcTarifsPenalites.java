package services;


import entities.Penalites;
import entities.Tarifs;
import entities.TarifsPenalites;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<TarifsPenalites> findByPena(Tarifs t, Penalites p, Date d) {
		Map<String, String> param = new HashMap<>();
		param.put("dateDebut", d.toString());
		param.put("denominationPena", p.getDenomination());
		param.put("denominationTarif", t.getDenomination());
		param.put("dateFin", d.toString());
		return finder.findByNamedQuery("TarifsPenalites.findByPenalites", param);
	}



	public TarifsPenalites createTarifsPenalites(Tarifs t, Penalites pe, Double pr, Date db, Date df)
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