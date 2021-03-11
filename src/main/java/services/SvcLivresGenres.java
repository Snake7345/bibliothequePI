package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.LivresGenres;

@PersistenceUnit (unitName="bibliotheque")
public class SvcLivresGenres {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcLivresGenres()
		{
			super();
		}


		public SvcLivresGenres(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(LivresGenres livresg)
		{
			em.persist(livresg);
		}
		
		public void update(LivresGenres livresg)
		{
			em.merge(livresg);
		}
		public void remove(LivresGenres livresg)
		{
			em.remove(livresg);
		}



}
