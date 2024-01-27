package SILAB.presentation.calibraciones;

import SILAB.Application;
import SILAB.logic.Calibracion;
import SILAB.logic.Instrumento;
import SILAB.logic.Medicion;
import SILAB.logic.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller{
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(new ArrayList<>()); // la calibracion inicialmente esta vacia, se llena con el metodo search
        model.setCurrentInstrumento(new Instrumento());
        model.setListMediciones(new ArrayList<>());
        model.setMedicionActual(new Medicion());
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void clear() {
        model.setCurrent(new Calibracion());
        model.setList(new ArrayList<>()); // se limpia la lista de calibraciones
        model.setMode(1);
        model.commit();
    }


    public void    search2(Calibracion filter) throws  Exception{
        List<Calibracion> rows = Service.instance().search(filter);

        model.setList(rows);
        model.commit();
    }

    public void update(Calibracion nuevo) throws Exception{
        Service.instance().update(nuevo);
        setInstrumento(nuevo.getInstrumento());
       // this.search(new Calibracion());
    }

    public void guardar(Calibracion nuevo) throws Exception{
        Service.instance().create(nuevo);
        setInstrumento(nuevo.getInstrumento());
        //this.search(new Calibracion());
    }
    public void delete() throws  Exception{
        Service.instance().delete(model.getCurrent());
        setInstrumento(model.getCurrent().getInstrumento());
        //this.search(new Calibracion());
    }

    public void edit(int row){
        Calibracion e = model.getList().get(row);
        try {

            model.setMode(2); // modo de edicion
            model.setCurrent(Service.instance().read(e));
            model.setListMediciones(model.getCurrent().getMediciones());
            model.commit();
        } catch (Exception ex) {}

    }

    public void setInstrumento(Instrumento current) {

        model.setList(Service.instance().findCalibrationsForInstrument(current)); //carga las calibraciones del instrumento seleccionado

        // Obtener la cantidad de calibraciones existentes para ese instrumento
        int cantidadCalibraciones = model.getList().size(); //
        String numeroCalibracion = String.valueOf(cantidadCalibraciones + 1);
        Calibracion calibracion = new Calibracion();

        calibracion.setNumCalibra(numeroCalibracion); // asignar el numero de calibracion
        calibracion.setInstrumento(current);    // asignar el instrumento seleccionado anteriormente en el view de instrumentos
        Date fechaActual = new Date();
        // Formatear la fecha en el formato deseado (por ejemplo, "yyyy-MM-dd")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormateada = dateFormat.format(fechaActual); // asignar la fecha actual a la calibracion
        calibracion.setFecha(fechaFormateada);


        model.setCurrent(calibracion);
        model.commit();

    }

    public void editMedicion(int row) {
        Medicion e = model.getListMediciones().get(row);
        try {

            model.setMode(Application.modeMedicion); // modo de edicion
            model.setMedicionActual(e);
        } catch (Exception ex) {}
    }

    public void deleteMedicion() throws Exception {
       Service.instance().deleteMedicionForCalibracion(model.getMedicionActual());
        actualizarMediciones(Service.instance().getMedicionesForCalibracion(model.getCurrent().getNumCalibra()));
    }

    public void actualizarMediciones( List <Medicion> listaMediciones) {

        model.setListMediciones(listaMediciones);
        model.commit();
    }

    public void report() throws IOException {

     //    PDFGenerator.generatePDFCalibraciones("Calibraciones.pdf");
    }
}
