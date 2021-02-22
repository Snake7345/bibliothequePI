package managedBean;

import entities.Permissions;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcPermissions {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcPermissions()
		{
			super();
		}


		public SvcPermissions(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Permissions perm)
		{
			em.persist(perm);
		}
		
		public void update(Permissions perm)
		{
			em.merge(perm);
		}
		public void remove(Permissions perm)
		{
			em.remove(perm);
		}



}
