package services;

import entities.*;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;


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

    public FacturesDetail newRent(ExemplairesLivres el, Factures fa, Tarifs t, int J, Timestamp d)
    {
        double prix=0.0;
        int nbrjours=0;
        int index=0;
        SvcTarifsJours serviceTJ = new SvcTarifsJours();
        SvcJours serviceJ = new SvcJours();
        List<Jours> jours = serviceJ.findByNbrJ(J);
        FacturesDetail facturesDetail = new FacturesDetail();
        facturesDetail.setExemplairesLivre(el);
        facturesDetail.setFacture(fa);
        List<TarifsJours> tj = new ArrayList<>();
        for(Jours jours1 : jours)
        {
            tj.add(serviceTJ.findByJours(t,jours1).get(0));
        }
        boolean flag=false;
        //try catch nécéssaire
        try {
            while (!flag) {
                prix = prix + tj.get(index).getPrix();
                nbrjours = nbrjours + tj.get(index).getJours().getNbrJour();
                if (nbrjours > J) {
                    prix = prix - tj.get(index).getPrix();
                    nbrjours = nbrjours - tj.get(index).getJours().getNbrJour();
                    index++;
                } else if (nbrjours == J) {
                    flag = true;
                }
            }
        }
        catch (IndexOutOfBoundsException ignored){}
        facturesDetail.setPrix(prix);
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
    public FacturesDetail newPenaretard(ExemplairesLivres el, Factures fa, Tarifs t, Penalites p,double nbjour, Date dp, Timestamp df)
    {
        SvcTarifsPenalites serviceTP = new SvcTarifsPenalites();
        FacturesDetail facturesDetail = new FacturesDetail();
        facturesDetail.setExemplairesLivre(el);
        facturesDetail.setFacture(fa);
        facturesDetail.setPrix(serviceTP.findByPena(t,p,dp).get(0).getPrix()*nbjour);
        facturesDetail.setDateFin(df);
        facturesDetail.setDateRetour(df);
        serviceTP.close();
        return facturesDetail;
    }


    public List<FacturesDetail> findAllFactureDetail()
    {
        return finder.findByNamedQuery("FactureDetail.findAll",null);
    }



}