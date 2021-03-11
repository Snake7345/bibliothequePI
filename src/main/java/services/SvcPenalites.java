package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Penalites;

@PersistenceUnit (unitName="bibliotheque")
public class SvcPenalites {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcPenalites()
		{
			super();
		}


		public SvcPenalites(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Penalites pena)
		{
			em.persist(pena);
		}
		
		public void update(Penalites pena)
		{
			em.merge(pena);
		}
		public void remove(Penalites pena)
		{
			em.remove(pena);
		}



}
