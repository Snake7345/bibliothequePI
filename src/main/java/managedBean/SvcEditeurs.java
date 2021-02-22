package managedBean;

import entities.Editeurs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcEditeurs {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcEditeurs()
		{
			super();
		}


		public SvcEditeurs(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Editeurs editeur)
		{
			em.persist(editeur);
		}
		
		public void update(Editeurs editeur)
		{
			em.merge(editeur);
		}
		public void remove(Editeurs editeur)
		{
			em.remove(editeur);
		}



}
