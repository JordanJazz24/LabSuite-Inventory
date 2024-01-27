package SILAB.presentation.tipos;

//import SILAB.logic.PDFGenerator;
import SILAB.Application;
import SILAB.logic.ITarget;
import SILAB.logic.Message;
import SILAB.logic.Service;
import SILAB.logic.TipoInstrumento;

import java.util.List;

public class Controller  {
    View view;
    Model model;

    public Controller(View view, Model model) throws Exception {
        model.init(Service.instance().search(new TipoInstrumento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void clear() {
        model.setCurrent(new TipoInstrumento());
        model.setMode(1);
        model.commit();
    }

    public void search(TipoInstrumento filter) throws  Exception{
        List<TipoInstrumento> rows = Service.instance().search(filter);
//        if (rows.isEmpty()){
//            throw new Exception("NINGUN REGISTRO COINCIDE");
//        }
        model.setList(rows);
        model.setCurrent(new TipoInstrumento());
        model.commit();
    }

    public void update(TipoInstrumento nuevo) throws Exception{
        Service.instance().update(nuevo);
        this.search(new TipoInstrumento());
    }

    public void guardar(TipoInstrumento nuevo) throws Exception{
        Service.instance().create(nuevo);
        this.search(new TipoInstrumento());
       // Application.mensajesController.actualizar();

    }
    public void delete() throws  Exception{
         Service.instance().delete(model.getCurrent());
         this.search(new TipoInstrumento());
    }



    public void edit(int row){
        TipoInstrumento e = model.getList().get(row);
        try {

            model.setMode(2);
            model.setCurrent(Service.instance().read(e));
            model.commit();
        } catch (Exception ex) {}
    }


    public void report()throws Exception{
  //      PDFGenerator.generatePDF("TiposDeInstrumentos.pdf");
    }


}
