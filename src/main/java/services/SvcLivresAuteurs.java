package services;

import entities.LivresAuteurs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcLivresAuteurs {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcLivresAuteurs()
		{
			super();
		}


		public SvcLivresAuteurs(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(LivresAuteurs livresa)
		{
			em.persist(livresa);
		}
		
		public void update(LivresAuteurs livresa)
		{
			em.merge(livresa);
		}
		public void remove(LivresAuteurs livresa)
		{
			em.remove(livresa);
		}



}
