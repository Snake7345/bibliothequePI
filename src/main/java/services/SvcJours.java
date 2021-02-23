package services;

import entities.Jours;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcJours {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcJours()
		{
			super();
		}


		public SvcJours(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Jours jours)
		{
			em.persist(jours);
		}
		
		public void update(Jours jours)
		{
			em.merge(jours);
		}
		public void remove(Jours jours)
		{
			em.remove(jours);
		}



}
