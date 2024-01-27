package SILAB.logic;

import java.io.Serializable;

public class Medicion implements Serializable {
    private String numero;
    private String referencia;
    private String lectura;
    private Calibracion calibracion;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLectura() {
        return lectura;
    }

    public void setLectura(String lectura) {
        this.lectura = lectura;
    }

    public Calibracion getCalibracion() {
        return calibracion;
    }

    public void setCalibracion(Calibracion calibracion) {
        this.calibracion = calibracion;
    }

    public Medicion() {
        this.numero = "";
        this.referencia = "";
        this.lectura = "";
        this.calibracion = new Calibracion();
    }

    public Medicion(String numero, String referencia, String lectura, Calibracion calibracion) {
        this.numero = numero;
        this.referencia = referencia;
        this.lectura = lectura;
        this.calibracion = calibracion;
    }
}
