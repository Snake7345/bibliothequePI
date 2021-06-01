package services;


import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivres extends Service<Livres> implements Serializable {
	//Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcLivres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLivres() {
		super();
	}

	// Méthode qui permet de sauver un livre et de le mettre en DB
	@Override
	public Livres save(Livres livres) {
		if (livres.getIdLivres() == 0) {
			em.persist(livres);
		} else {
			livres = em.merge(livres);
		}

		return livres;
	}

	//Méthode qui permet via une requete de retourner la liste entière des livres
	public List<Livres> findAllLivres() {
		return finder.findByNamedQuery("Livres.findAllTri", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des livres actifs
	public List<Livres> findAllLivresActiv() {
		return finder.findByNamedQuery("Livres.findActiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des livres inactifs
	public List<Livres> findAllLivresInactiv() {
		return finder.findByNamedQuery("Livres.findInactiv", null);
	}

	public List<Livres> getByAuteurs(Auteurs auteur) {
		Map<String, Auteurs> param = new HashMap<>();
		param.put("auteur", auteur);

		return finder.findByNamedQuery("Livres.findLivreByAuteurs", param);
	}

	public List<Livres> getByEditeurs(Editeurs editeur) {
		Map<String, Editeurs> param = new HashMap<>();
		param.put("editeur", editeur);

		return finder.findByNamedQuery("Livres.findByEditeurs", param);
	}
	public List<Livres> getByGenres(Genres genre) {
		Map<String, Genres> param = new HashMap<>();
		param.put("genre", genre);

		return finder.findByNamedQuery("Livres.findLivreByGenres", param);
	}

	public List<Livres> findByIsbn(String isbn) {
		Map<String, String> param = new HashMap<>();
		param.put("isbn", isbn);

		return finder.findByNamedQuery("Utilisateurs.findByIsbn", param);
	}

	public Livres deleteLivres(Livres liv){
		System.out.println("Je delete le livre :" + liv.getTitre());
		liv.setActif(false);
		return liv;
	}

	public List<Livres> getByTitre(String titre) {
		Map<String, String> param = new HashMap<>();
		param.put("titre", titre);

		return finder.findByNamedQuery("Livres.searchTitre", param);
	}

}

