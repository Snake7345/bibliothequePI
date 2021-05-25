package services;

import entities.Roles;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcRoles extends Service<Roles> implements Serializable {
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcRoles.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcRoles() {
		super();
	}

	// Méthode qui permet de sauver un role et de le mettre en DB
	@Override
	public Roles save(Roles roles) {
		if (roles.getIdRoles() == 0) {
			em.persist(roles);
		} else {
			roles = em.merge(roles);
		}

		return roles;
	}

	//Méthode qui permet via une requete de retourner la liste entière des roles
	public List<Roles> findAllRoles() {
		return finder.findByNamedQuery("Roles.findAll", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des roles actifs
	public List<Roles> findAllRolesActiv() {
		return finder.findByNamedQuery("Roles.findActiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des roles inactifs
	public List<Roles> findAllRolesInactiv() {
		return finder.findByNamedQuery("Roles.findInactiv", null);
	}

	public List<Roles> findRole(String denomination) {
		Map<String, String> param = new HashMap<>();
		param.put("denomination", denomination);

		return finder.findByNamedQuery("Roles.findRole", param);
	}

	public Roles deleteRole(Roles rol){
		System.out.println("Je delete le role :" + rol.getDenomination());
		rol.setActif(false);
		return rol;
	}


}

