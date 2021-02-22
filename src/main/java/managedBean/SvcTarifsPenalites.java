package managedBean;

import entities.TarifsPenalites;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

@PersistenceUnit (unitName="bibliotheque")
public class SvcTarifsPenalites {
	protected EntityManager em;

	//---------------------------------------------------------------------------
		public SvcTarifsPenalites()
		{
			super();
		}


		public SvcTarifsPenalites(EntityManager em1)
		{
			
			this.em = em1;
			
		}
	//---------------------------------------------------------------------------

		public void create(TarifsPenalites tpena)
		{
			em.persist(tpena);
		}
		
		public void update(TarifsPenalites tpena)
		{
			em.merge(tpena);
		}
		public void remove(TarifsPenalites tpena)
		{
			em.remove(tpena);
		}



}
