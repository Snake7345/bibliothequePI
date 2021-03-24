package services;

import entities.Tarifs;
import org.apache.log4j.Logger;

import java.io.Serializable;
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
		return null;
	}


	public List<Tarifs> findAllTarifs() {
		return finder.findByNamedQuery("Tarifs.findAll", null);
	}


}