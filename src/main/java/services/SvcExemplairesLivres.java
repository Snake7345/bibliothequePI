package services;

import entities.ExemplairesLivres;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcExemplairesLivres extends Service<ExemplairesLivres> implements Serializable
{
	private static final Logger log = Logger.getLogger(SvcExemplairesLivres.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcExemplairesLivres()
	{
		super();
	}



	public List<ExemplairesLivres> findAllExemplairesLivres()
	{
		return finder.findByNamedQuery("ExemplairesLivres.findAll",null);
	}
	public List<ExemplairesLivres> findlastExemplairesLivres()
	{
		return finder.findByNamedQuery("ExemplairesLivres.findLastExemplaire",null);
	}

	@Override
	public ExemplairesLivres save(ExemplairesLivres exemplairesLivres) {
		if (exemplairesLivres.getIdExemplairesLivres() == 0) {
			em.persist(exemplairesLivres);
		} else {
			exemplairesLivres = em.merge(exemplairesLivres);
		}

		return exemplairesLivres;
	}

	public ExemplairesLivres desactivExemplaireLivre(ExemplairesLivres exemliv){
		System.out.println("Je desactive l'exemplaire livre :" + exemliv.getCodeBarre());
		exemliv.setActif(false);
		return exemliv;
	}
}