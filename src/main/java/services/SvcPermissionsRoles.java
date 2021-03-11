package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.PermissionsRoles;

@PersistenceUnit (unitName="bibliotheque")
public class SvcPermissionsRoles {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcPermissionsRoles()
		{
			super();
		}


		public SvcPermissionsRoles(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(PermissionsRoles penar)
		{
			em.persist(penar);
		}
		
		public void update(PermissionsRoles penar)
		{
			em.merge(penar);
		}
		public void remove(PermissionsRoles penar)
		{
			em.remove(penar);
		}



}
