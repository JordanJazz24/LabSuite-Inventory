/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SILAB.logic;

import SILAB.data.CalibracionDAO;
import SILAB.data.InstrumentoDAO;
import SILAB.data.MedicionDAO;
import SILAB.data.TipoInstrumentoDao;

import java.util.List;

public class Service implements IService {
    private static Service theInstance;
    public static Service instance(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }

    private TipoInstrumentoDao tipoInstrumentoDao;
    private InstrumentoDAO instrumentoDAO;

    private CalibracionDAO calibracionDAO;
    private MedicionDAO medicionDAO;

    public Service() {
        try{
            tipoInstrumentoDao = new TipoInstrumentoDao();
            instrumentoDAO = new InstrumentoDAO();
            calibracionDAO = new CalibracionDAO();
            medicionDAO = new MedicionDAO();
        }
        catch(Exception e){
        }
    }

    // ------------ TIPOS DE INTRUMENTO -------------
    public void create(TipoInstrumento e)throws Exception{
        tipoInstrumentoDao.create(e);
    }

    public TipoInstrumento read(TipoInstrumento e) throws Exception{
        return tipoInstrumentoDao.read(e.getCodigo());
    }

    public void update(TipoInstrumento e)throws Exception{
        tipoInstrumentoDao.update(e);
    }

    public void delete(TipoInstrumento e)throws Exception{
        tipoInstrumentoDao.delete(e);
    }

    public List<TipoInstrumento> search(TipoInstrumento e) throws Exception {
        return tipoInstrumentoDao.search(e);
    }

   // ------------ INSTRUMENTOS -------------
   public void create(Instrumento e) throws Exception{
       instrumentoDAO.create(e);
   }

   public Instrumento read(Instrumento e) throws Exception{
       return instrumentoDAO.read(e.getSerie());
   }

   public void update(Instrumento e)throws Exception{

       instrumentoDAO.update(e);
   }

   public void delete(Instrumento e)throws Exception{
       instrumentoDAO.delete(e);
   }

   public List<Instrumento> search(Instrumento v) throws Exception {
       return instrumentoDAO.search(v);
   }

    // ------------ CALIBRACIONES -------------

    public void create(Calibracion e) throws Exception{
        calibracionDAO.create(e);
    }

    public Calibracion read(Calibracion e) throws Exception{
        return calibracionDAO.read(e.getNumCalibra());
    }

    public void update(Calibracion e)throws Exception{
        calibracionDAO.update(e);
    }

    public void delete(Calibracion e)throws Exception{
        calibracionDAO.delete(e);
    }

    public List<Calibracion> search(Calibracion v) throws Exception {
        return calibracionDAO.search(v);
    }

    public List<Calibracion> findCalibrationsForInstrument(Instrumento current) {
        return calibracionDAO.findCalibrationsForInstrument(current);
    }

// ------------ MEDICIONES -------------
    public void deleteMedicionForCalibracion( Medicion medicionActual) throws Exception {
        medicionDAO.delete(medicionActual);
    }

    public List<Medicion> getMedicionesForCalibracion(String numCalibra) {
        return medicionDAO.getMedicionesForCalibracion(numCalibra);
    }
}
