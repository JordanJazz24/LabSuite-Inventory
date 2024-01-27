package SILAB.presentation.calibraciones;

import SILAB.logic.Calibracion;
import SILAB.logic.Instrumento;
import SILAB.logic.Medicion;

import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Calibracion> list;

    List<Medicion> listMediciones;
    Calibracion current;

    Medicion medicionActual;

    public Medicion getMedicionActual() {
        return medicionActual;
    }

    public void setMedicionActual(Medicion medicionActual) {
        this.medicionActual = medicionActual;
    }

    public List<Medicion> getListMediciones() {
        return listMediciones;
    }

    public void setListMediciones(List<Medicion> listMediciones) {
        changedProps +=LISTAMEDICION;
        this.listMediciones = listMediciones;
    }

    Instrumento currentInstrumento;

    public Instrumento getCurrentInstrumento() {
        return currentInstrumento;
    }

    public void setCurrentInstrumento(Instrumento currentInstrumento) {

        this.currentInstrumento = currentInstrumento;
        changedProps +=CURRENT;
    }

    int mode;
    int changedProps = NONE;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }



    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void     init(List<Calibracion> list){
        setList(list);
        setCurrent(new Calibracion());
    }

    public List<Calibracion> getList() {
        return list;
    }
    public void setList(List<Calibracion> list){
        this.list = list;
        changedProps +=LIST;
    }

    public Calibracion getCurrent() {
        return current;
    }
    public void setCurrent(Calibracion current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;

    public static int LISTAMEDICION=4;
}
