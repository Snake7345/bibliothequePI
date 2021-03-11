package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.FactureDetail;

@PersistenceUnit (unitName="bibliotheque")
public class SvcFactureDetail {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcFactureDetail()
		{
			super();
		}


		public SvcFactureDetail(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(FactureDetail factured)
		{
			em.persist(factured);
		}
		
		public void update(FactureDetail factured)
		{
			em.merge(factured);
		}
		public void remove(FactureDetail factured)
		{
			em.remove(factured);
		}



}
