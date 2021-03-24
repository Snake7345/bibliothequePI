package services;



import entities.TarifsJours;
import org.apache.log4j.Logger;

import java.io.Serializable;
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
		return null;
	}


	public List<TarifsJours> findAllTarifsJours() {
		return finder.findByNamedQuery("TarifsJours.findAll", null);
	}


}