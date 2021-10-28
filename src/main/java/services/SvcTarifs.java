package services;

import entities.Bibliotheques;
import entities.Genres;
import entities.Tarifs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcTarifs extends Service<Tarifs> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcTarifs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcTarifs() {
		super();
	}

	@Override
	public Tarifs save(Tarifs tarifs) {
		if (tarifs.getIdTarifs() == 0) {
			em.persist(tarifs);
		} else {
			tarifs = em.merge(tarifs);
		}

		return tarifs;
	}


	public List<Tarifs> findOneTarifByDenom(Tarifs tar)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("denomination", tar.getDenomination());
		return finder.findByNamedQuery("Tarifs.findOneByDenom",param);
	}
	public List<Tarifs> findOneTarifByDateDebut(Tarifs tar)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("date", tar.getDateDebut());
		return finder.findByNamedQuery("Tarifs.findOneByDateDebut",param);
	}


	public List<Tarifs> findAllTarifs() {
		return finder.findByNamedQuery("Tarifs.findAll", null);
	}

	public List<Tarifs> getTarifByBiblio(Date d, String biblio) {
		Map<String, Object> param = new HashMap<>();
		param.put("date", d);
		param.put("bibliotheque",biblio);

		return finder.findByNamedQuery("Tarifs.findByBiblio", param);
	}

	public List<Tarifs> getTarifByBiblioActuelle(int biblio) {
		Map<String, Object> param = new HashMap<>();
		param.put("bibliotheque", biblio);

		return finder.findByNamedQuery("Tarifs.findTarifsByBiblio", param);
	}



}