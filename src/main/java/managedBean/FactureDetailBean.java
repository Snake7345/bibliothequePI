package managedBean;

import entities.FactureDetail;
import org.apache.log4j.Logger;
import services.SvcFactureDetail;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FactureDetailBean implements Serializable {
    // Déclaration des variables globales
    private static final long serialVersionUID = 1L;
    private FactureDetail factureDetail;

    private static final Logger log = Logger.getLogger(FactureDetailBean.class);

    public List<FactureDetail> getReadAll()
    {
        SvcFactureDetail service = new SvcFactureDetail();
        List<FactureDetail> listFactD = new ArrayList<FactureDetail>();
        listFactD= service.findAllFactureDetail();

        service.close();
        return listFactD;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public FactureDetail getFactureDetail() {
        return factureDetail;
    }

    public void setFactureDetail(FactureDetail factureDetail) {
        this.factureDetail = factureDetail;
    }







}
