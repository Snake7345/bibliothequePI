package services;


import entities.Utilisateurs;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcUtilisateurs extends Service<Utilisateurs> implements Serializable {
	// Déclaration des variables
	private static final Logger log = Logger.getLogger(SvcUtilisateurs.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcUtilisateurs() {
		super();
	}

	// Méthode qui permet de sauver un utilisateur et de le mettre en DB
	@Override
	public Utilisateurs save(Utilisateurs utilisateurs) {
		log.debug("Valeur utilisateur" + utilisateurs.getPrenom() +' '+utilisateurs.getNom()+' '+utilisateurs.getMdp()+' '+ utilisateurs.getCourriel()+' '+ utilisateurs.getIdUtilisateurs()+' '+ utilisateurs.getRoles().getDenomination()+' '+ utilisateurs.isActif());
		if (utilisateurs.getIdUtilisateurs() == 0) {
			em.persist(utilisateurs);
		} else {
			utilisateurs = em.merge(utilisateurs);
		}

		return utilisateurs;
	}

	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs
	public List<Utilisateurs> findAllUtilisateurs() {
		return finder.findByNamedQuery("Utilisateurs.findAll", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utiisateurs actifs
	public List<Utilisateurs> findAllUtilisateursActiv() {
		return finder.findByNamedQuery("Utilisateurs.findActiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs inactifs
	public List<Utilisateurs> findAllUtilisateursInactiv() {
		return finder.findByNamedQuery("Utilisateurs.findInactiv", null);
	}

	public Utilisateurs deleteUtilisateur(Utilisateurs util){
		System.out.println("Je delete l'utilisateur :" + util.getNom() + util.getPrenom());
		util.setActif(false);
		return util;
	}

	public List<Utilisateurs> getByName(String nom) {
		Map<String, String> param = new HashMap<>();
		param.put("nom", nom);

		return finder.findByNamedQuery("Utilisateurs.searchName", param);
	}



}

