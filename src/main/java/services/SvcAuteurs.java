package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Auteurs;

@PersistenceUnit (unitName="bibliotheque")
public class SvcAuteurs {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcAuteurs()
		{
			super();
		}


		public SvcAuteurs(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Auteurs auteur)
		{
			em.persist(auteur);
		}
		
		public void update(Auteurs auteur)
		{
			em.merge(auteur);
		}
		public void remove(Auteurs auteur)
		{
			em.remove(auteur);
		}



}
