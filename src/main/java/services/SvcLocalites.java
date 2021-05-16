package services;

import entities.Localites;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLocalites extends Service<Localites> implements Serializable {
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcLocalites.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLocalites() {
		super();
	}

	// Méthode qui permet de sauver une localité et de le mettre en DB
	@Override
	public Localites save(Localites localites) {
		if (localites.getIdLocalites() == 0) {
			em.persist(localites);
		} else {
			localites = em.merge(localites);
		}

		return localites;
	}

	//Méthode qui permet via une requete de retourner la liste entière des localités
	public List<Localites> findAllLocalites() {
		return finder.findByNamedQuery("Localites.findAll", null);
	}


}

