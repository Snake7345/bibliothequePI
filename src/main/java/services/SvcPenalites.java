package services;

import entities.Penalites;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPenalites extends Service<Penalites> implements Serializable {
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcPenalites.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPenalites() {
		super();
	}
	// Méthode qui permet de sauver une pénalité et de le mettre en DB
	@Override
	public Penalites save(Penalites penalites) {
		if (penalites.getIdPenalites() == 0) {
			em.persist(penalites);
		} else {
			penalites = em.merge(penalites);
		}

		return penalites;
	}

	public Penalites addPena(String d)
	{

		if(findByName(d).size() == 1)
		{
			return findByName(d).get(0);
		}
		else
		{
			Penalites penalites = new Penalites();
			penalites.setDenomination(d);
			save(penalites);
			return penalites;
		}
	}

	//Méthode qui permet via une requete de retourner la liste entière des pénalités
	public List<Penalites> findAllPenalites() {
		return finder.findByNamedQuery("Penalites.findAll", null);
	}

	public List<Penalites> findByName(String denom) {
		Map<String, String> param = new HashMap<>();
		param.put("denomination", denom);

		return finder.findByNamedQuery("Penalites.findByName", param);
	}


}

