package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.Utilisateurs;
import entities.UtilisateursAdresses;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcUtilisateursAdresses extends Service<UtilisateursAdresses> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcUtilisateursAdresses.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcUtilisateursAdresses() {
		super();
	}

	@Override
	public UtilisateursAdresses save(UtilisateursAdresses utilisateursAdresses) {
		return null;
	}


	public List<UtilisateursAdresses> findAllUtilisateursAdresses() {
		return finder.findByNamedQuery("UtilisateursAdresses.findAll", null);
	}


}

