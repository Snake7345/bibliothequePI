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

	public ExemplairesLivres loueExemplaire(ExemplairesLivres exemliv){
		System.out.println("Je loue l'exemplaire livre :" + exemliv.isLoue());
		exemliv.setLoue(!exemliv.isLoue());
		return exemliv;
	}

	public List<ExemplairesLivres> findAllExemplairesLivres()
	{
		return finder.findByNamedQuery("ExemplairesLivres.findAll",null);
	}
	public List<ExemplairesLivres> findlastExemplairesLivres()
	{
		return finder.findByNamedQuery("ExemplairesLivres.findLastExemplaire",null);
	}

	public List<ExemplairesLivres> findOneByCodeBarre(String CB)
	{
		Map<String, String> param = new HashMap<>();
		param.put("codeBarre", CB);
		return finder.findByNamedQuery("ExemplairesLivres.findOneCB",param);
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