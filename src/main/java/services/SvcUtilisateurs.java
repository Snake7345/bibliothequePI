package services;


import entities.Auteurs;
import entities.Roles;
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
		if (utilisateurs.getIdUtilisateurs() == 0) {
			em.persist(utilisateurs);
		} else {
			utilisateurs = em.merge(utilisateurs);
		}

		return utilisateurs;
	}

	//Méthode qui permet via une requete de retourner une liste avec un utilisateur
	public List<Utilisateurs> findOneUtilisateur(Utilisateurs util)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("nom", util.getNom());
		param.put("prenom", util.getPrenom());
		param.put("sexe", util.getSexe());
		param.put("courriel", util.getCourriel());
		param.put("role", util.getRoles());
		return finder.findByNamedQuery("Utilisateurs.findOne",param);
	}

	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs
	public List<Utilisateurs> findAllUtilisateursUtil() {
		return finder.findByNamedQuery("Utilisateurs.findAllUtil", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs (client)
	public List<Utilisateurs> findAllUtilisateursCli() {
		return finder.findByNamedQuery("Utilisateurs.findAllCli", null);
	}

	//Méthode qui permet via une requete de retourner la liste entière des utiisateurs actifs
	public List<Utilisateurs> findAllUtilisateursActiv() {
		return finder.findByNamedQuery("Utilisateurs.findActiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs inactifs
	public List<Utilisateurs> findAllUtilisateursInactiv() {
		return finder.findByNamedQuery("Utilisateurs.findInactiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs (client) actifs
	public List<Utilisateurs> findAllUtilisateursCliActiv() {
		return finder.findByNamedQuery("Utilisateurs.findCliActiv", null);
	}
	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs (client) inactif
	public List<Utilisateurs> findAllUtilisateursCliInactiv() {
		return finder.findByNamedQuery("Utilisateurs.findCliInactiv", null);
	}

	//Méthode qui permet via une requete de retourner une avec le dernier numero d'utilisateur (client)
	public List<Utilisateurs> findlastMembre()
	{
		return finder.findByNamedQuery("Utilisateurs.findLastMembre",null);
	}



	public List<Utilisateurs> getByName(String nom) {
		Map<String, String> param = new HashMap<>();
		param.put("nom", nom);

		return finder.findByNamedQuery("Utilisateurs.searchName", param);
	}

	public List<Utilisateurs> getByNumMembre(String numMembre) {
		Map<String, String> param = new HashMap<>();
		param.put("numMembre", numMembre);

		return finder.findByNamedQuery("Utilisateurs.searchMembre", param);
	}
	public List<Utilisateurs> findByLogin(String login) {
		Map<String, String> param = new HashMap<>();
		param.put("login", login);

		return finder.findByNamedQuery("Utilisateurs.findByLogin", param);
	}

	public List<Utilisateurs> reinitialisation(String login, String courriel) {
		Map<String, String> param = new HashMap<>();
		param.put("login", login);
		param.put("courriel", courriel);

		return finder.findByNamedQuery("Utilisateurs.findByLoginMail", param);
	}


}

