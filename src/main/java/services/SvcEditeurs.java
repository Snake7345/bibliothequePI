package services;

import entities.Auteurs;
import entities.Editeurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcEditeurs extends Service<Editeurs> implements Serializable
{
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcEditeurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcEditeurs()
	{
		super();
	}

	// Méthode qui permet de sauver un éditeur et de le mettre en DB
	@Override
	public Editeurs save(Editeurs editeurs) {
		if (editeurs.getIdEditeurs() == 0) {
			em.persist(editeurs);
		} else {
			editeurs = em.merge(editeurs);
		}

		return editeurs;
	}

	//Méthode qui permet via une requete de retourner une liste avec un editeur
	public List<Editeurs> findOneEditeur(Editeurs ed)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("nom", ed.getNom());
		return finder.findByNamedQuery("Editeurs.findOne",param);
	}


	//Méthode qui permet via une requete de retourner la liste entière des éditeurs
	public List<Editeurs> findAllEditeurs()
	{
		return finder.findByNamedQuery("Editeurs.findAllTri",null);
	}



}

