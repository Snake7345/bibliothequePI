package services;

import entities.Roles;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcRoles extends Service<Roles> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcRoles.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcRoles() {
		super();
	}

	@Override
	public Roles save(Roles roles) {
		if (roles.getIdRoles() == 0) {
			em.persist(roles);
		} else {
			roles = em.merge(roles);
		}

		return roles;
	}


	public List<Roles> findAllRoles() {
		return finder.findByNamedQuery("Roles.findAll", null);
	}
	public List<Roles> findAllRolesActiv() {
		return finder.findByNamedQuery("Roles.findActiv", null);
	}
	public List<Roles> findAllRolesInactiv() {
		return finder.findByNamedQuery("Roles.findInactiv", null);
	}


}

