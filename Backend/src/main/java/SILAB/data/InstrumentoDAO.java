package SILAB.data;

import SILAB.logic.Calibracion;
import SILAB.logic.Instrumento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstrumentoDAO {
    Database db;

    public InstrumentoDAO() {
        db = Database.instance();
    }

    public void create(Instrumento e) throws Exception {
        String sql = "INSERT INTO " +
                "Instrumento " +
                "(serie, tipoInstrumento, minimo, maximo, tolerancia, descripcion)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getSerie());
        stm.setString(2, e.getTipoInstrumento().getCodigo());
        stm.setInt(3, e.getMinimo());
        stm.setInt(4, e.getMaximo());
        stm.setInt(5, e.getTolerancia());
        stm.setString(6, e.getDescripcion());

        db.executeUpdate(stm);
    }

    public Instrumento read(String serie) throws Exception {
        String sql = "SELECT " +
                "* from " +
                "Instrumento i inner join TipoInstrumento t on i.tipoInstrumento=t.codigo " +
                "WHERE i.serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, serie);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "i");
        } else {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }
    }

    public void update(Instrumento e) throws Exception {
        CalibracionDAO calibracionDAO = new CalibracionDAO();
        List<Calibracion> calibraciones = calibracionDAO.findCalibrationsForInstrument(e);
        if (calibraciones.size() > 0) {
            throw new Exception("INSTRUMENTO TIENE CALIBRACIONES ASOCIADAS");
        }

        String sql = "UPDATE " +
                "Instrumento " +
                "SET tipoInstrumento=?, minimo=?, maximo=?, tolerancia=?, descripcion=? " +
                "WHERE serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getTipoInstrumento().getCodigo()); // Assuming TipoInstrumento has a reasonable toString() method
        stm.setInt(2, e.getMinimo());
        stm.setInt(3, e.getMaximo());
        stm.setInt(4, e.getTolerancia());
        stm.setString(5, e.getDescripcion());
        stm.setString(6, e.getSerie());

        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }
    }

    public void delete(Instrumento e) throws Exception {
        String sql = "DELETE " +
                "FROM Instrumento " +
                "WHERE serie=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getSerie());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("INSTRUMENTO NO EXISTE");
        }
    }

    public List<Instrumento> search(Instrumento e) throws Exception {
        List<Instrumento> resultado = new ArrayList<Instrumento>();
        String sql = "SELECT * " +
                "FROM " +
                "Instrumento i inner join TipoInstrumento t on i.tipoInstrumento=t.codigo " +
                "WHERE i.descripcion LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getDescripcion() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "i"));
        }
        return resultado;
    }

    public Instrumento from(ResultSet rs, String alias) throws Exception {
        Instrumento e = new Instrumento();
        TipoInstrumentoDao tipo= new TipoInstrumentoDao();

        e.setSerie(rs.getString(alias + ".serie"));
        e.setTipoInstrumento(tipo.from(rs, "t"));
        e.setMinimo(rs.getInt(alias + ".minimo"));
        e.setMaximo(rs.getInt(alias + ".maximo"));
        e.setTolerancia(rs.getInt(alias + ".tolerancia"));
        e.setDescripcion(rs.getString(alias + ".descripcion"));
        return e;
    }
}
