package managedBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.UtilisateursAdresses;
@PersistenceUnit (unitName="bibliotheque")
public class SvcUtilisateursAdresses {
	protected EntityManager em;
	
	//---------------------------------------------------------------------------
		public SvcUtilisateursAdresses()
		{
			super();
		}

		
		public SvcUtilisateursAdresses(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(UtilisateursAdresses utilad)
		{
			em.persist(utilad);
		}
		
		public void update(UtilisateursAdresses utilad)
		{
			em.merge(utilad);
		}
		public void remove(UtilisateursAdresses utilad)
		{
			em.remove(utilad);
		}



}
