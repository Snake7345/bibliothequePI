package connexion;

import java.io.Serializable;
import java.util.*;

//import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

//import com.og.connection.EMF;

/** 
 * Class to perform entity CRUD with the database
 * 
 * @author Renaud DIANA
 */
public class EntityFinderImpl<T> implements EntityFinder<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;
	private Class<?> ec;

	/**
     * Default constructor
     */
	public EntityFinderImpl(Class<?> ec){
		super();
		this.ec = ec;
	}

	public EntityFinderImpl(Class<?> ec, EntityManager em){
		super();
		this.ec = ec;
		this.em = em;
	}

	// Log4j	 
	private static final Logger log = Logger.getLogger(EntityFinderImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public T findOne(int id) {

		T t = (T)em.find(ec, id);

		log.debug("Bean " + ec.getSimpleName() + " find from database: Ok");

		return t;
	}
//---------------------------------------------------------------------------------------------------------	
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<T> findByNamedQuery(String namedQuery, T t, Map<K, V> param) {
		
		List<T> listT = new ArrayList<T>();
		Class<? extends Object> ec = t.getClass();
		
		EntityManager em = EMF.getEM();
		try {
		    Query query = em.createNamedQuery(namedQuery, ec);
		    
	    	if(param != null) {
	    		
	    		setParameters(query, param);		
	    	}
	    	listT = (List<T>) query.getResultList();
	    	
	    	log.debug("List " + t + " size: " + listT.size());       
	    	log.debug("Named query " + namedQuery + " find from database: Ok");	    
		}
		finally {
			
			em.clear();
	        em.close();
	    }
		return listT;
	}
//----------------------------------------------------------------------------------------------------------	
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> List<T> findByCustomQuery(String customQuery, T t, Map<K, V> param) {
		
		List<T> listT = new ArrayList<T>();
		Class<? extends Object> ec = t.getClass();
	    
		EntityManager em = EMF.getEM();
		try {
	    	Query query = em.createQuery(customQuery, ec);
	    	if(param != null) {
	    		
	    		setParameters(query, param);
	    	}
	    	listT = (List<T>) query.getResultList();
	    	    	
	    	log.debug("List " + t + " size: " + listT.size());       
	    	log.debug("Custom query " + customQuery + " find from database: Ok");
		}
		finally {
			
			em.clear();
	        em.close();
	    }
		return listT;
	}

	/**  
	 * @param query
	 * @param param
	 * @return
	 * 			the query with parameters
	 */
	private <K, V> void setParameters(Query query, Map<K, V> param) {
		
		Set<Map.Entry<K, V>> entries = param.entrySet();
		Iterator<Map.Entry<K, V>> itr = entries.iterator();
		while(itr.hasNext()){
			Map.Entry<K, V> entry = itr.next();
			if((boolean) entry.getKey().toString().toLowerCase().contains("date"))
				query.setParameter((String) entry.getKey(),(Date) entry.getValue(), TemporalType.DATE);
			else
				query.setParameter((String) entry.getKey(),entry.getValue());
			//log.debug("entry.getValue: " + entry.getValue());
		}
	}
	
}