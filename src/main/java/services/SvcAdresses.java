package services;

import entities.Adresses;
import entities.Bibliotheques;
import entities.Utilisateurs;
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

	//Méthode qui permet via une requete de retourner une liste avec une adresse
	public List<Adresses> findOneAdresse(Adresses ad)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("boite", ad.getBoite());
		param.put("localite", ad.getLocalites());
		param.put("numero", ad.getNumero());
		param.put("rue", ad.getRue());
		return finder.findByNamedQuery("Adresses.findOne",param);
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

	public List<Adresses> getfindAllNotAdBibli() {

		return finder.findByNamedQuery("Adresses.findAllNotAdBibli", null);
	}


	public List<Adresses> getfindAllNotAdBibliNotUtiForMofif (Bibliotheques bib) {
		Map<String, Object> param = new HashMap<>();
		param.put("bibliotheque", bib);
		return finder.findByNamedQuery("Adresses.findAllNotAdBibliNotUtiForMofif", param);
	}
	public List<Adresses> getfindAllNotAdBibliNotUti () {
		return finder.findByNamedQuery("Adresses.findAllNotAdBibliNotUti", null);
	}
}
