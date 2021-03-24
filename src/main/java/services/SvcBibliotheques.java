package services;

import entities.Auteurs;
import entities.Bibliotheques;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcBibliotheques extends Service<Bibliotheques> implements Serializable
{
	private static final Logger log = Logger.getLogger(SvcBibliotheques.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcBibliotheques()
	{
		super();
	}



	public List<Bibliotheques> findAllBibliotheques()
	{
		return finder.findByNamedQuery("Bibliotheques.findAll",null);
	}

	@Override
	public Bibliotheques save(Bibliotheques bibliotheques) {
		return null;
	}

}