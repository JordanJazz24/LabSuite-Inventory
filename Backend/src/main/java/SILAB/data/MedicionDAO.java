package SILAB.data;

import SILAB.logic.Instrumento;
import SILAB.logic.Medicion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicionDAO {
    Database db;

    public MedicionDAO() {
        db = Database.instance();
    }

    public void create(Medicion medicion, int id) throws Exception {
        String sql = "INSERT INTO Medicion (referencia, lectura, calibracion) VALUES (?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, medicion.getReferencia());
        stm.setString(2, medicion.getLectura());
        stm.setInt(3, id);
        db.executeUpdateWithKeys(stm);
    }

    public Medicion read(String numCalibra) throws Exception {
        String sql = "SELECT " +
                "* from " +
                "Medicion i inner join Calibracion t on i.calibracion=t.numCalibra " +
                "WHERE i.numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numCalibra);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "i");
        } else {
            throw new Exception("Medicion NO EXISTE");
        }
    }

    public void updateMedicion(Medicion medicion) throws Exception {
        String sql = "UPDATE Medicion SET lectura=? WHERE numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, medicion.getLectura());
        stm.setInt(2, Integer.parseInt(medicion.getNumero()));

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("MEDICION NO EXISTE");
        }
    }

    public void delete(Medicion medicion) throws Exception {
        String sql = "DELETE FROM Medicion WHERE numero=? ";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, medicion.getNumero());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("MEDICION NO EXISTE");
        }
    }

    public List<Medicion> search(Medicion e) throws Exception {
        List<Medicion> resultado = new ArrayList<Medicion>();
        String sql = "SELECT * " +
                "FROM " +
                "Medicion i inner join Calibracion t on i.calibracion=t.numCalibra " +
                "WHERE i.lectura LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getLectura() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "i"));
        }
        return resultado;
    }

    public Medicion from(ResultSet rs, String alias) throws Exception {
        Medicion e = new Medicion();
        CalibracionDAO  calibracionDAO = new CalibracionDAO();

        e.setNumero(rs.getString(alias + ".numero"));
        e.setReferencia(rs.getString(alias + ".referencia"));
        e.setLectura(rs.getString(alias + ".lectura"));
       // e.setCalibracion(calibracionDAO.from(rs, "t"));
        return e;
    }



    public Medicion from(ResultSet rs) {
        try {
            Medicion e = new Medicion();
            e.setNumero(rs.getString("numero"));
            e.setReferencia(rs.getString("referencia"));
            e.setLectura(rs.getString("lectura"));
            return e;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Medicion> getMedicionesForCalibracion(String numCalibra) {
        List<Medicion> mediciones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Medicion WHERE calibracion=?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, numCalibra);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                mediciones.add(from(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediciones;
    }

    public List<Medicion> createMediciones(Instrumento instrumento, int cantidadMediciones) {
        int minimo = instrumento.getMinimo();
        int maximo = instrumento.getMaximo();

        List<Medicion> mediciones = new ArrayList<>(cantidadMediciones);
        if (cantidadMediciones > 0) {
            int paso = (maximo - minimo) / cantidadMediciones;

            for (int i = 0; i < cantidadMediciones; i++) {
                int valor = minimo + i * paso;
                Medicion medicion = new Medicion();
                medicion.setNumero(String.valueOf(i+1));
                medicion.setReferencia(String.valueOf(valor));
                medicion.setLectura("");
                mediciones.add(medicion);
            }
        }

        return mediciones;
    }
}
