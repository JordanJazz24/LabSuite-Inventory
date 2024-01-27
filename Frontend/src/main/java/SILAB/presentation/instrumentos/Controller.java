package SILAB.presentation.instrumentos;


import SILAB.Application;
import SILAB.logic.Instrumento;
import SILAB.logic.Service;
import SILAB.logic.TipoInstrumento;

import java.io.IOException;
import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) throws Exception {
        model.init(Service.instance().search(new Instrumento()));
       model.setTipos(Service.instance().search(new TipoInstrumento()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void clear() {
        model.setCurrent(new Instrumento());
        model.setMode(1);
        model.commit();
    }

    public void search(Instrumento filter) throws  Exception{
    List<Instrumento> rows = Service.instance().search(filter);
//    if (rows.isEmpty()){
//        model.setList( new ArrayList<>());
//
//    }
    model.setList(rows);
    model.setCurrent(new Instrumento());
    model.commit();
    }

    public void update(Instrumento nuevo) throws Exception{
       Service.instance().update(nuevo);
        this.search(new Instrumento());
    }



    public void guardar(Instrumento nuevo) throws Exception{
       Service.instance().create(nuevo);
       this.search(new Instrumento());
    }
    public void delete() throws  Exception{
      Service.instance().delete(model.getCurrent());
      this.search(new Instrumento());
        Application.tiposController.search(new TipoInstrumento()); //actualiza la lista de tipos
    }



    public void edit(int row){
       Instrumento e = model.getList().get(row);
       try {
           model.setMode(2);
           model.setCurrent(Service.instance().read(e));
           model.commit();
           Application.calibracionesController.setInstrumento(model.getCurrent());
       } catch (Exception ex) {}
    }

    public void show() throws Exception {
        model.setTipos(Service.instance().search(new TipoInstrumento()));
        model.commit();
    }

    public void report() throws IOException {
      //  PDFGenerator.generatePDFInstru("Instrumentos.pdf");

    }
}
