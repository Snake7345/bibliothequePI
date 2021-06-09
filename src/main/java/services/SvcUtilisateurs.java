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

	public List<Utilisateurs> findOneUtilisateur(Utilisateurs util)
	{
		Map<String, Object> param = new HashMap<>();
		param.put("nom", util.getNom());
		param.put("prenom", util.getPrenom());
		param.put("sexe", util.getSexe());
		param.put("courriel", util.getCourriel());
		//param.put("login", util.getLogin());
		//param.put("mdp", util.getMdp());
		param.put("role", util.getRoles());
		return finder.findByNamedQuery("Utilisateurs.findOne",param);
	}

	//Méthode qui permet via une requete de retourner la liste entière des utilisateurs
	public List<Utilisateurs> findAllUtilisateurs() {
		return finder.findByNamedQuery("Utilisateurs.findAll", null);
	}

	public List<Utilisateurs> findAllUtilisateursUtil() {
		return finder.findByNamedQuery("Utilisateurs.findAllUtil", null);
	}
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

	public List<Utilisateurs> findAllUtilisateursCliActiv() {
		return finder.findByNamedQuery("Utilisateurs.findCliActiv", null);
	}

	public List<Utilisateurs> findAllUtilisateursCliInactiv() {
		return finder.findByNamedQuery("Utilisateurs.findCliInactiv", null);
	}

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

	public List<Utilisateurs> authentify(String login, String mdp) {
		Map<String, String> param = new HashMap<>();
		param.put("login", login);
		param.put("mdp", mdp);

		return finder.findByNamedQuery("Utilisateurs.authentify", param);
	}



}

