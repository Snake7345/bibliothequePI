package services;

import entities.Bibliotheques;
import entities.Utilisateurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcBibliotheques extends Service<Bibliotheques> implements Serializable
{
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcBibliotheques.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcBibliotheques()
	{
		super();
	}


	//Méthode qui permet via une requete de retourner la liste entière des bibliothèques
	public List<Bibliotheques> findAllBibliotheques()
	{
		return finder.findByNamedQuery("Bibliotheques.findAll",null);
	}

	// Méthode qui permet de sauver une bibliothèque et de la mettre en DB
	@Override
	public Bibliotheques save(Bibliotheques bibliotheques) {
		if (bibliotheques.getIdBibliotheques() == 0) {
			em.persist(bibliotheques);
		} else {
			bibliotheques = em.merge(bibliotheques);
		}

		return bibliotheques;
	}

	public List<Bibliotheques> findById(int idBibliotheques) {
		Map<String, Integer> param = new HashMap<>();
		param.put("idBibliotheques", idBibliotheques);
		return finder.findByNamedQuery("Bibliotheques.findById", param);
	}

	public List<Bibliotheques> findAllBiblioActiv() {
		return finder.findByNamedQuery("Bibliotheques.findAllActiv", null);
	}

}