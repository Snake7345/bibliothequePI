package managedBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Utilisateurs;
@PersistenceUnit (unitName="bibliotheque")
public class SvcUtilisateurs {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcUtilisateurs()
		{
			super();
		}


		public SvcUtilisateurs(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Utilisateurs uti)
		{
			em.persist(uti);
		}
		
		public void update(Utilisateurs uti)
		{
			em.merge(uti);
		}
		public void remove(Utilisateurs uti)
		{
			em.remove(uti);
		}



}
