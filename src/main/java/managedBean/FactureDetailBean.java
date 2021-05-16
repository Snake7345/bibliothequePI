package managedBean;

import entities.FacturesDetail;
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
    private FacturesDetail facturesDetail;

    private static final Logger log = Logger.getLogger(FactureDetailBean.class);

    public List<FacturesDetail> getReadAll()
    {
        SvcFactureDetail service = new SvcFactureDetail();
        List<FacturesDetail> listFactD = new ArrayList<FacturesDetail>();
        listFactD= service.findAllFactureDetail();

        service.close();
        return listFactD;
    }

    //-------------------------------Getter & Setter--------------------------------------------

    public FacturesDetail getFactureDetail() {
        return facturesDetail;
    }

    public void setFactureDetail(FacturesDetail facturesDetail) {
        this.facturesDetail = facturesDetail;
    }







}
