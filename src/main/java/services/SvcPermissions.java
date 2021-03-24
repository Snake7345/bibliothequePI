package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Livres;
import entities.Permissions;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPermissions extends Service<Permissions> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcPermissions.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPermissions() {
		super();
	}

	@Override
	public Permissions save(Permissions permissions) {
		return null;
	}


	public List<Permissions> findAllPermissions() {
		return finder.findByNamedQuery("Permissions.findAll", null);
	}


}

