package services;

import entities.Adresses;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcAdresses extends Service<Adresses> implements Serializable {

	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcAdresses.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcAdresses()
	{
		super();
	}

	//Méthode qui permet via une requete de retourner la liste entière des adresses
	public List<Adresses> findAllAdresses()
	{
		return finder.findByNamedQuery("Adresses.findAll",null);
	}

	// Méthode qui permet de sauver une adresse et de la mettre en DB
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
