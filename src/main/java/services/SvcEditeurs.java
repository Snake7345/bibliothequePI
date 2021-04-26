package services;

import entities.Editeurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcEditeurs extends Service<Editeurs> implements Serializable
{
	private static final Logger log = Logger.getLogger(SvcEditeurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcEditeurs()
	{
		super();
	}

	@Override
	public Editeurs save(Editeurs editeurs) {
		if (editeurs.getIdEditeurs() == 0) {
			em.persist(editeurs);
		} else {
			editeurs = em.merge(editeurs);
		}

		return editeurs;
	}


	public List<Editeurs> findAllEditeurs()
	{
		return finder.findByNamedQuery("Editeurs.findAll",null);
	}



}

