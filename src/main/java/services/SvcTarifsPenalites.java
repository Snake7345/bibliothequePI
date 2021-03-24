package services;


import entities.TarifsPenalites;
import org.apache.log4j.Logger;

import java.io.Serializable;
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
		return null;
	}


	public List<TarifsPenalites> findAllTarifsPenalites() {
		return finder.findByNamedQuery("TarifsPenalites.findAll", null);
	}


}