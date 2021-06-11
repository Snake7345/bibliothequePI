package services;


import entities.Adresses;
import entities.Auteurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcAuteurs extends Service<Auteurs> implements Serializable
{
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcAuteurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcAuteurs()
		{
			super();
		}
	//Méthode qui permet via une requete de retourner la liste entière des Auteurs
	public List<Auteurs> findAllAuteurs()
	{
		return finder.findByNamedQuery("Auteurs.findAllTri",null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des auteurs actifs
	public List<Auteurs> findAllAuteursActiv()
	{
		return finder.findByNamedQuery("Auteurs.findAllActiv",null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des auteurs inactifs
	public List<Auteurs> findAllAuteursInactiv()
	{
		return finder.findByNamedQuery("Auteurs.findAllInactiv",null);
	}


	// Méthode qui permet de sauver un auteur et de le mettre en DB
	@Override
	public Auteurs save(Auteurs auteurs) {
		if (auteurs.getIdAuteurs() == 0) {
			em.persist(auteurs);
		} else {
			auteurs = em.merge(auteurs);
		}

		return auteurs;
	}

	//Méthode qui permet via une requete de retourner une liste avec un auteur
	public List<Auteurs> findOneAuteur(Auteurs aut)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("nom", aut.getNom());
		param.put("prenom", aut.getPrenom());
		return finder.findByNamedQuery("Auteurs.findOne",param);
	}

	//Méthode qui permet via une requete de retourner une liste avec un nom d'auteur
	public List<Auteurs> getByName(String nom) {
		Map<String, String> param = new HashMap<>();
		param.put("nom", nom);

		return finder.findByNamedQuery("Auteurs.searchName", param);
	}



}
