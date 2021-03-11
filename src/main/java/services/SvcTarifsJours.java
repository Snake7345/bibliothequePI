package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.TarifsJours;

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

		public void create(TarifsJours tarifsj)
		{
			em.persist(tarifsj);
		}
		
		public void update(TarifsJours tarifsj)
		{
			em.merge(tarifsj);
		}
		public void remove(TarifsJours tarifsj)
		{
			em.remove(tarifsj);
		}



}
