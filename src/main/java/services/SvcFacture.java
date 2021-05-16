package services;

import entities.Factures;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SvcFacture extends Service<Factures> implements Serializable {
	private static final Logger log = Logger.getLogger(SvcFacture.class);
	private static final long serialVersionUID = 1L;
	Map<String, Object> params = new HashMap<String, Object>();

	public SvcFacture() {
		super();
	}

	@Override
	public Factures save(Factures factures) {
		if (factures.getIdFactures() == 0) {
			em.persist(factures);
		} else {
			factures = em.merge(factures);
		}

		return factures;
	}


	public List<Factures> findAllFacture() {
		return finder.findByNamedQuery("Facture.findAll", null);
	}


}