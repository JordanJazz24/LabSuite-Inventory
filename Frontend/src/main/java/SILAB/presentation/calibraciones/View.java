package SILAB.presentation.calibraciones;

import SILAB.Application;
import SILAB.logic.Calibracion;
import SILAB.logic.Instrumento;
import SILAB.logic.Medicion;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchNombre;
    private JButton search;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchNombreLbl;
    private JButton report;
    private JTextField numero;
    private JTextField mediciones;
    private JTextField fechas;
    private JLabel numeroLb;
    private JLabel medicionesLb;
    private JLabel fechasLB;
    private JButton clear;
    private JLabel instrumentoActual;
    private JPanel panelMediciones;
    private JTable listaMediciones;
    private JScrollPane scroollMediciones;

    public View() {

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Calibracion filter = new Calibracion();
                    filter.setFecha(searchNombre.getText());
                    filter.setInstrumento(model.current.getInstrumento());
                    controller.search2(filter);
                    // controller.searchPorunidddad(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (model.getMode() == Application.modeMedicion) {
                        controller.deleteMedicion();
                    } else {
                        controller.delete();
                    }

                    // controller.searchPorunidddad(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                panelMediciones.setVisible(true);
                controller.edit(row);
            }
        });

        listaMediciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listaMediciones.getSelectedRow();
                controller.editMedicion(row);
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.clear();
                panelMediciones.setVisible(false);
            }
        });


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Calibracion calibracionActual = new Calibracion(numero.getText(), model.getCurrent().getInstrumento(), fechas.getText());
                    if ( controller.model.getMode() == Application.modeMedicion) {

                        controller.actualizarMediciones(getMedicionesFromTable());

                        calibracionActual.setMediciones(model.getListMediciones());
                        controller.update(calibracionActual);

                    } else {
                        List<Medicion> listaMedi = new ArrayList<>();
                        for (int i = 0; i < (Integer.parseInt(mediciones.getText())); i++) {
                            listaMedi.add(new Medicion());
                        }

                        calibracionActual.setMediciones(listaMedi); // setea la lista de mediciones al objeto calibracion
                        controller.guardar(calibracionActual);
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controller.report();
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new java.io.File("Calibraciones.pdf"));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public List<Medicion> getMedicionesFromTable() {
        List<Medicion> mediciones = new ArrayList<>();
        TableMediciones tableModel = (TableMediciones) listaMediciones.getModel();
        int rowCount = tableModel.getRowCount();
        int colCount = tableModel.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            Medicion medicion = new Medicion();
            for (int col = 0; col < colCount; col++) {
                Object value = tableModel.getValueAt(row, col);
                if (col == TableMediciones.MEDICION) {
                    medicion.setNumero((String) value);
                } else if (col == TableMediciones.REFERENCIA) {
                    medicion.setReferencia((String) value);
                } else if (col == TableMediciones.LECTURA) {
                    medicion.setLectura((String) value);
                }
            }
            mediciones.add(medicion);
        }

        return mediciones;
    }


    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) {
            int[] cols = {TableModel.NUMERO, TableModel.FECHA, TableModel.MEDICIONES};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }

        if ((changedProps & Model.LISTAMEDICION) == Model.LISTAMEDICION) { // si se cambia la lista de mediciones
            int[] cols = {TableMediciones.MEDICION, TableMediciones.REFERENCIA, TableMediciones.LECTURA};
            listaMediciones.setModel(new TableMediciones(cols, model.getListMediciones()));
            listaMediciones.setRowHeight(30);
            TableColumnModel columnModel = listaMediciones.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }

        if ((changedProps & Model.CURRENT) == Model.CURRENT) {
            Instrumento instrumento = model.getCurrent().getInstrumento();
            if (instrumento.getSerie() == "") {
                instrumentoActual.setText("Ninguno");
            } else {
                instrumentoActual.setText(instrumento.getSerie() + " - " + instrumento.getDescripcion() +
                        " (" + instrumento.getMinimo() + "-" + instrumento.getMaximo() + " " + instrumento.getTipoInstrumento().getUnidad() + ")");
            }

            instrumentoActual.setForeground(Color.RED);
            numero.setText(model.getCurrent().getNumCalibra());
            mediciones.setText(String.valueOf(model.getCurrent().getMediciones().size()));
            fechas.setText(model.getCurrent().getFecha());
        }

        if (model.getMode() == Application.modeEdit || model.getMode() == Application.modeMedicion) {
            numero.setEnabled(false);
            fechas.setEnabled(false);
            mediciones.setEnabled(false);
            delete.setEnabled(true);

        } else {
            numero.setEnabled(false);
            fechas.setEnabled(true);
            mediciones.setEnabled(true);
            delete.setEnabled(false);

        }
        //if para dejar text en blanco si no esta en ningun modo de los dos


        this.panel.revalidate();
    }
}
