package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Genres;

@PersistenceUnit (unitName="bibliotheque")
public class SvcGenres {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcGenres()
		{
			super();
		}


		public SvcGenres(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Genres genres)
		{
			em.persist(genres);
		}
		
		public void update(Genres genres)
		{
			em.merge(genres);
		}
		public void remove(Genres genres)
		{
			em.remove(genres);
		}



}
