package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Adresses;

@PersistenceUnit (unitName="bibliotheque")
public class SvcAdresses {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcAdresses()
		{
			super();
		}


		public SvcAdresses(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Adresses adresse)
		{
			em.persist(adresse);
		}
		
		public void update(Adresses adresse)
		{
			em.merge(adresse);
		}
		public void remove(Adresses adresse)
		{
			em.remove(adresse);
		}



}
