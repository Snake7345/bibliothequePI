package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import entities.Editeurs;
import entities.FactureDetail;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcFactureDetail extends Service<FactureDetail> implements Serializable
{
    private static final Logger log = Logger.getLogger(SvcFactureDetail.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public SvcFactureDetail()
    {
        super();
    }

    @Override
    public FactureDetail save(FactureDetail factureDetail) {
        return null;
    }


    public List<FactureDetail> findAllFactureDetail()
    {
        return finder.findByNamedQuery("FactureDetail.findAll",null);
    }



}