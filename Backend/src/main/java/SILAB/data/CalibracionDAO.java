package SILAB.data;

import SILAB.logic.Calibracion;
import SILAB.logic.Instrumento;
import SILAB.logic.Medicion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CalibracionDAO {
    Database db;

    public CalibracionDAO() {
        db = Database.instance();
    }

    public void create(Calibracion e) throws Exception {
        String sql = "INSERT INTO " +
                "Calibracion " +
              "(instrumento_serie, fecha) " +
                "VALUES (?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getInstrumento().getSerie());
        stm.setString(2, e.getFecha());
        ResultSet keys= db.executeUpdateWithKeys(stm);

        int key = -1; // valor por defecto
        if (keys.next()){
           key = keys.getInt(1); // recuperando el id del registro creado
        }

        MedicionDAO m = new MedicionDAO();
        List<Medicion> mediciones = m.createMediciones(e.getInstrumento(), e.getMediciones().size());
        for (Medicion medicion : mediciones) {
            m.create(medicion, key);
        }

    }

    public Calibracion read(String numCalibra) throws Exception {
        String sql = "SELECT * from " +
                "Calibracion c inner join Instrumento i on c.instrumento_serie=i.serie " +
                "WHERE c.numCalibra=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numCalibra);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "c");
        } else {
            throw new Exception("Calibracion NO EXISTE");
        }
    }

    public void update(Calibracion e) throws Exception {
        MedicionDAO m = new MedicionDAO();

        for (Medicion medicion : e.getMediciones()) {
            m.updateMedicion(medicion);
        }
    }

    public void delete(Calibracion calibracion) throws Exception {
        String sql = "DELETE FROM Calibracion WHERE numCalibra=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, calibracion.getNumCalibra());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("CALIBRACION NO EXISTE");
        }
    }

    public List<Calibracion> search(Calibracion e) throws Exception {
        List<Calibracion> resultado = new ArrayList<Calibracion>();
        String sql = "SELECT * " +
                "FROM Calibracion c " +
                "WHERE c.fecha LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getFecha() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "c"));
        }
        return resultado;
    }

    public Calibracion from(ResultSet rs, String alias) throws Exception {
        Calibracion e = new Calibracion();
        MedicionDAO m =new MedicionDAO();
        InstrumentoDAO i = new InstrumentoDAO();

        e.setNumCalibra(rs.getString(alias + ".numCalibra"));
        e.setFecha(rs.getString(alias + ".fecha"));
        e.setInstrumento(i.read(rs.getString(alias + ".instrumento_serie")));
        e.setMediciones(m.getMedicionesForCalibracion(e.getNumCalibra()));
        return e;
    }

    public List<Calibracion> findCalibrationsForInstrument(Instrumento current) {
        List<Calibracion> resultado = new ArrayList<Calibracion>();
        String sql = "SELECT * " +
                "FROM Calibracion c " +
                "WHERE c.instrumento_serie = ?";
        try {

            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, current.getSerie());
            ResultSet rs = db.executeQuery(stm);

            while (rs.next())
                resultado.add(from(rs, "c"));

        } catch (Exception e) {
            System.out.println("Error al buscar calibraciones para instrumento");
        }

        return resultado; // retorna la lista de calibraciones para el instrumento seleccionado
    }

}
