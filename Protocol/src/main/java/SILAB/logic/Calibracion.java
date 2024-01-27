package SILAB.logic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Calibracion implements Serializable {

    String numCalibra;
    Instrumento instrumento;

    String fecha;
    List<Medicion> mediciones;

    public Calibracion(String numCalibra, Instrumento instrumento, String fecha) {
        this.numCalibra = numCalibra;
        this.instrumento = instrumento;
        this.fecha = fecha;
        this.mediciones = new ArrayList<>();
    }
    public String getNumCalibra() {
        return numCalibra;
    }
    public Calibracion getCalibracion(){
        return this;
    }
    public void setNumCalibra(String numCalibra) {
        this.numCalibra = numCalibra;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void     setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
        }

    public Calibracion() {
        this.numCalibra = "";
        this.instrumento = new Instrumento();
        this.fecha = "";
        this.mediciones = new ArrayList<>();
    }
}
