package services;

import entities.Auteurs;
import entities.Penalites;
import entities.Permissions;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcPermissions extends Service<Permissions> implements Serializable {
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcPermissions.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcPermissions() {
		super();
	}

	// Méthode qui permet de sauver une permission et de la mettre en DB
	@Override
	public Permissions save(Permissions permissions) {
		if (permissions.getIdPermissions() == 0) {
			em.persist(permissions);
		} else {
			permissions = em.merge(permissions);
		}

		return permissions;
	}

	public List<Permissions> findOnePermission(Permissions perm)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("type", perm.getType());
		param.put("action", perm.getAction());
		return finder.findByNamedQuery("Permissions.findOne",param);
	}

	//Méthode qui permet via une requete de retourner la liste entière des permissions
	public List<Permissions> findAllPermissions() {
		return finder.findByNamedQuery("Permissions.findAllTri", null);
	}


}

