package managedBean;

import entities.Bibliotheques;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcBibliotheques {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcBibliotheques()
		{
			super();
		}


		public SvcBibliotheques(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Bibliotheques bibliotheque)
		{
			em.persist(bibliotheque);
		}
		
		public void update(Bibliotheques bibliotheque)
		{
			em.merge(bibliotheque);
		}
		public void remove(Bibliotheques bibliotheque)
		{
			em.remove(bibliotheque);
		}



}
