package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import entities.Tarifs;

@PersistenceUnit (unitName="bibliotheque")
public class SvcTarifs {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcTarifs()
		{
			super();
		}


		public SvcTarifs(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Tarifs tarif)
		{
			em.persist(tarif);
		}
		
		public void update(Tarifs tarif)
		{
			em.merge(tarif);
		}
		public void remove(Tarifs tarif)
		{
			em.remove(tarif);
		}



}
