package services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Adresses;
import org.apache.log4j.Logger;


public class SvcAdresses extends Service<Adresses> implements Serializable {

	private static final Logger log = Logger.getLogger(SvcAdresses.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcAdresses()
	{
		super();
	}

	public List<Adresses> findAllAdresses()
	{
		return finder.findByNamedQuery("Adresses.findAll",null);
	}


	@Override
	public Adresses save(Adresses adresses) {
		if (adresses.getIdAdresses() == 0) {
			em.persist(adresses);
		} else {
			adresses = em.merge(adresses);
		}

		return adresses;
	}
}
