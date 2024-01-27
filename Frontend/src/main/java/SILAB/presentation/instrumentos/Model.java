package SILAB.presentation.instrumentos;


import SILAB.logic.Instrumento;
import SILAB.logic.TipoInstrumento;

import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Instrumento> list;
    Instrumento current;
    List<TipoInstrumento> tipoInstrumentoNombres;


    int mode;
    int changedProps = NONE; // changedProps se usa para saber que propiedad se cambio en el modelo
                            // cada set es una actulizacion que se realiza en el modelo

    public List<TipoInstrumento> getTipos() {
        return tipoInstrumentoNombres;
    }

    public void setTipos(List<TipoInstrumento> tipoInstrumentoNombres) {
        this.tipoInstrumentoNombres = tipoInstrumentoNombres;
        changedProps +=TYPES;
    }

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

    public void init(List<Instrumento> list){
        setList(list);
        setCurrent(new Instrumento());
    }

    public List<Instrumento> getList() {
        return list;
    }
    public void setList(List<Instrumento> list){
        this.list = list;
        changedProps +=LIST;
    }

    public Instrumento getCurrent() {
        return current;
    }
    public void setCurrent(Instrumento current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public static int NONE=0;
    public static int LIST=1;
    public static int CURRENT=2;
    public static int TYPES=4;




}
