package services;

import entities.Auteurs;
import entities.Editeurs;
import entities.Genres;
import entities.Livres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcLivres extends Service<Livres> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcLivres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcLivres() {
		super();
	}

	@Override
	public Livres save(Livres livres) {
		if (livres.getIdLivres() == 0) {
			em.persist(livres);
		} else {
			livres = em.merge(livres);
		}

		return livres;
	}

	public List<Livres> findAllLivres() {
		return finder.findByNamedQuery("Livres.findAllTri", null);
	}
	public List<Livres> findAllLivresActiv() {
		return finder.findByNamedQuery("Livres.findAllActiv", null);
	}
	public List<Livres> findAllLivresInactiv() {
		return finder.findByNamedQuery("Livres.findAllInactiv", null);
	}

	public List<Livres> getByAuteurs(Auteurs auteur) {
		Map<String, Auteurs> param = new HashMap<>();
		param.put("auteur", auteur);

		return finder.findByNamedQuery("Livres.findByAuteur", param);
	}

	public List<Livres> getByEditeurs(Editeurs editeur) {
		Map<String, Editeurs> param = new HashMap<>();
		param.put("editeur", editeur);

		return finder.findByNamedQuery("Livres.findByEditeurs", param);
	}
	public List<Livres> getByGenres(Genres genre) {
		Map<String, Genres> param = new HashMap<>();
		param.put("genre", genre);

		return finder.findByNamedQuery("Livres.findByGenres", param);
	}

	public Livres deleteLivres(Livres liv){
		System.out.println("Je delete le livre :" + liv.getTitre());
		liv.setActif(false);
		return liv;
	}

}

