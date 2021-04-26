package services;


import entities.Utilisateurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcUtilisateurs extends Service<Utilisateurs> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcUtilisateurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcUtilisateurs() {
		super();
	}

	@Override
	public Utilisateurs save(Utilisateurs utilisateurs) {
		if (utilisateurs.getIdUtilisateurs() == 0) {
			em.persist(utilisateurs);
		} else {
			utilisateurs = em.merge(utilisateurs);
		}

		return utilisateurs;
	}

	public List<Utilisateurs> findAllUtilisateurs() {
		return finder.findByNamedQuery("Utilisateurs.findAll", null);
	}
	public List<Utilisateurs> findAllUtilisateursActiv() {
		return finder.findByNamedQuery("Utilisateurs.findActiv", null);
	}
	public List<Utilisateurs> findAllUtilisateursInactiv() {
		return finder.findByNamedQuery("Utilisateurs.findInactiv", null);
	}



}

