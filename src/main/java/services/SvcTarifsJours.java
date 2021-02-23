package services;

import entities.Penalites;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcTarifsJours {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcTarifsJours()
		{
			super();
		}


		public SvcTarifsJours(EntityManager em1)
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
