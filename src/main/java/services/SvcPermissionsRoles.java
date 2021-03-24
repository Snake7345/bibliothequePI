package services;

import entities.PermissionsRoles;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPermissionsRoles extends Service<PermissionsRoles> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcPermissionsRoles.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPermissionsRoles() {
		super();
	}

	@Override
	public PermissionsRoles save(PermissionsRoles permissionsRoles) {
		return null;
	}


	public List<PermissionsRoles> findAllPermissionsRoles() {
		return finder.findByNamedQuery("PermissionsRoles.findAll", null);
	}


}

