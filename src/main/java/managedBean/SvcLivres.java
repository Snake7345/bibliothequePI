package managedBean;

import entities.Livres;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcLivres {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcLivres()
		{
			super();
		}


		public SvcLivres(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Livres livres)
		{
			em.persist(livres);
		}
		
		public void update(Livres livres)
		{
			em.merge(livres);
		}
		public void remove(Livres livres)
		{
			em.remove(livres);
		}



}
