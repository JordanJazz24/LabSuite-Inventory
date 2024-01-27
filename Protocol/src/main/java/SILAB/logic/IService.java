package SILAB.logic;



import java.util.List;

public interface IService {


    // ------------ TIPOS DE INTRUMENTO -------------
    public void create(TipoInstrumento e)throws Exception;

    public TipoInstrumento read(TipoInstrumento e) throws Exception;

    public void update(TipoInstrumento e)throws Exception;

    public void delete(TipoInstrumento e)throws Exception;

    public List<TipoInstrumento> search(TipoInstrumento e) throws Exception ;

    // ------------ INSTRUMENTOS -------------
    public void create(Instrumento e) throws Exception;

    public Instrumento read(Instrumento e) throws Exception;

    public void update(Instrumento e)throws Exception;


    public void delete(Instrumento e)throws Exception;

    public List<Instrumento> search(Instrumento v) throws Exception ;

    // ------------ CALIBRACIONES -------------

    public void create(Calibracion e) throws Exception;


    public Calibracion read(Calibracion e) throws Exception;


    public void update(Calibracion e)throws Exception;


    public void delete(Calibracion e)throws Exception;


    public List<Calibracion> search(Calibracion v) throws Exception ;


    public List<Calibracion> findCalibrationsForInstrument(Instrumento current) ;


    //------------ MEDICIONES -------------
    public void deleteMedicionForCalibracion( Medicion medicionActual) throws Exception ;

    public List<Medicion> getMedicionesForCalibracion(String numCalibra) ;



}
