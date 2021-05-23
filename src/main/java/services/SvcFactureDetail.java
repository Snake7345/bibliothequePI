package services;

import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
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
    public FacturesDetail save(FacturesDetail facturesDetail) {
        if (facturesDetail.getIdFactureDetail() == 0) {
            em.persist(facturesDetail);
        } else {
            facturesDetail = em.merge(facturesDetail);
        }

        return facturesDetail;
    }

    public FacturesDetail newRent(ExemplairesLivres el, Factures fa, Tarifs t, Jours j, Timestamp d)
    {
        SvcTarifsJours serviceTJ = new SvcTarifsJours();
        FacturesDetail facturesDetail = new FacturesDetail();
        facturesDetail.setExemplairesLivre(el);
        facturesDetail.setFacture(fa);
        facturesDetail.setPrix(serviceTJ.findByNbrJours(t,j).get(0).getPrix());
        facturesDetail.setDateFin(d);
        serviceTJ.close();
        return facturesDetail;
    }
    public FacturesDetail newPena(ExemplairesLivres el, Factures fa, Tarifs t, Penalites p, Date dp, Timestamp df)
    {
        SvcTarifsPenalites serviceTP = new SvcTarifsPenalites();
        FacturesDetail facturesDetail = new FacturesDetail();
        facturesDetail.setExemplairesLivre(el);
        facturesDetail.setFacture(fa);
        facturesDetail.setPrix(serviceTP.findByPena(t,p,dp).get(0).getPrix());
        facturesDetail.setDateFin(df);
        serviceTP.close();
        return facturesDetail;
    }


    public List<FacturesDetail> findAllFactureDetail()
    {
        return finder.findByNamedQuery("FactureDetail.findAll",null);
    }



}