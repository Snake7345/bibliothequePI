package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Roles;

@PersistenceUnit (unitName="bibliotheque")
public class SvcRoles {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcRoles()
		{
			super();
		}


		public SvcRoles(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Roles role)
		{
			em.persist(role);
		}
		
		public void update(Roles role)
		{
			em.merge(role);
		}
		public void remove(Roles role)
		{
			em.remove(role);
		}



}
