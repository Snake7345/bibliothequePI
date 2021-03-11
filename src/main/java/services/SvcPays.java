package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Pays;

@PersistenceUnit (unitName="bibliotheque")
public class SvcPays {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcPays()
		{
			super();
		}


		public SvcPays(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Pays pays)
		{
			em.persist(pays);
		}
		
		public void update(Pays pays)
		{
			em.merge(pays);
		}
		public void remove(Pays pays)
		{
			em.remove(pays);
		}



}
