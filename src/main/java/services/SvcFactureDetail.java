package services;

import entities.FacturesDetail;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SvcFactureDetail extends Service<FacturesDetail> implements Serializable
{
    private static final Logger log = Logger.getLogger(SvcFactureDetail.class);
    private static final long serialVersionUID = 1L;
    Map<String, Object> params = new HashMap<String, Object>();

    public SvcFactureDetail()
    {
        super();
    }

    @Override
    public FacturesDetail save(FacturesDetail factureDetail) {
        if (factureDetail.getIdFactureDetail() == 0) {
            em.persist(factureDetail);
        } else {
            factureDetail = em.merge(factureDetail);
        }

        return factureDetail;
    }


    public List<FacturesDetail> findAllFactureDetail()
    {
        return finder.findByNamedQuery("FacturesDetail.findAll",null);
    }



}