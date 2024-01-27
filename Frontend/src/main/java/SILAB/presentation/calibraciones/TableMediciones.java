package SILAB.presentation.calibraciones;

import SILAB.logic.Medicion;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableMediciones extends AbstractTableModel implements javax.swing.table.TableModel {
    List<Medicion> rows;
    int[] cols;

    public TableMediciones(int[] cols, List<Medicion> rows){
        this.cols=cols;
        this.rows=rows;
        initColNames();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        switch (cols[col]){
            case  LECTURA: return Integer.class;
            default: return super.getColumnClass(col);
        }
    }


    public int getRowCount() {
        return rows.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex==LECTURA){
            return true;
        } else {
            return false;
        }
    }

@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Medicion medicion = rows.get(rowIndex);
        switch (cols[columnIndex]){
            case LECTURA: medicion.setLectura(String.valueOf(aValue));
        }
    }


    public Object getValueAt(int row, int col) {
        Medicion medicion = rows.get(row);
        switch (cols[col]){
            case MEDICION: return medicion.getNumero();
            case REFERENCIA: return medicion.getReferencia();
            case LECTURA: return medicion.getLectura();
            default: return "";
        }
    }

    public Medicion getRowAt(int row) {
        return rows.get(row);
    }

    public static final int MEDICION=0;
    public static final int REFERENCIA  =1;
    public static final int LECTURA=2;

    String[] colNames = new String[6];
    private void initColNames(){
        colNames[MEDICION]= "Medicion";
        colNames[REFERENCIA]= "Referencia";
        colNames[LECTURA]= "Lectura";
    }

}
