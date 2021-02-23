package services;

import entities.Localites;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcLocalites {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcLocalites()
		{
			super();
		}


		public SvcLocalites(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Localites loca)
		{
			em.persist(loca);
		}
		
		public void update(Localites loca)
		{
			em.merge(loca);
		}
		public void remove(Localites loca)
		{
			em.remove(loca);
		}



}
