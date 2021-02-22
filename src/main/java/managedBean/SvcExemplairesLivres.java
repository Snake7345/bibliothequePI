package managedBean;

import entities.ExemplairesLivres;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcExemplairesLivres {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcExemplairesLivres()
		{
			super();
		}


		public SvcExemplairesLivres(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(ExemplairesLivres exemplairesl)
		{
			em.persist(exemplairesl);
		}
		
		public void update(ExemplairesLivres exemplairesl)
		{
			em.merge(exemplairesl);
		}
		public void remove(ExemplairesLivres exemplairesl)
		{
			em.remove(exemplairesl);
		}



}
