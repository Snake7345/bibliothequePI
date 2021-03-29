package services;


import entities.Auteurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcAuteurs extends Service<Auteurs> implements Serializable
{
	private static final Logger log = Logger.getLogger(SvcAuteurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcAuteurs()
		{
			super();
		}

	public List<Auteurs> findAllAuteurs()
	{
		return finder.findByNamedQuery("Auteurs.findAll",null);
	}

	@Override
	public Auteurs save(Auteurs auteurs) {
		if (auteurs.getIdAuteurs() == 0) {
			em.persist(auteurs);
		} else {
			auteurs = em.merge(auteurs);
		}

		return auteurs;
	}




}
