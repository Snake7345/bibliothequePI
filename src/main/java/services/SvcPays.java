package services;

import entities.Pays;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPays extends Service<Pays> implements Serializable {
	//Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcPays.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPays() {
		super();
	}

	// Méthode qui permet de sauver un pays et de le mettre en DB
	@Override
	public Pays save(Pays pays) {
		if (pays.getIdPays() == 0) {
			em.persist(pays);
		} else {
			pays = em.merge(pays);
		}

		return pays;
	}

	//Méthode qui permet via une requete de retourner la liste entière des Pays
	public List<Pays> findAllPays() {
		return finder.findByNamedQuery("Pays.findAllTri", null);
	}


}

