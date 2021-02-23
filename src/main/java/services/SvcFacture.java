package services;

import entities.Facture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcFacture {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcFacture()
		{
			super();
		}


		public SvcFacture(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(Facture facture)
		{
			em.persist(facture);
		}
		
		public void update(Facture facture)
		{
			em.merge(facture);
		}
		public void remove(Facture facture)
		{
			em.remove(facture);
		}



}
