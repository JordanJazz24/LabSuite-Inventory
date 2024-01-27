package SILAB.presentation.tipos;

import SILAB.Application;
import SILAB.logic.TipoInstrumento;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
    private JTextField codigo;
    private JTextField nombre;
    private JTextField unidad;
    private JLabel codigoLbl;
    private JLabel nombreLbl;
    private JLabel unidadLbl;
    private JButton clear;

    public View() {
        codigo.setToolTipText("Ingrese un número(caracteres numericos)");
        nombre.setToolTipText("Ingrese un nombre (caracteres alfabéticos)");
        unidad.setToolTipText("Ingrese un número (caracteres alfabéticos)");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TipoInstrumento filter= new TipoInstrumento();
                    filter.setNombre(searchNombre.getText());
                    controller.search(filter);
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
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "TIPO DE INSTRUMENTO ELIMINADO", "", JOptionPane.INFORMATION_MESSAGE);

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
                controller.edit(row);
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             setLabelColor(codigoLbl, Color.BLACK);
             setLabelColor(nombreLbl, Color.BLACK);
                setLabelColor(unidadLbl, Color.BLACK);
                controller.clear();

            }
        });


// Dentro del ActionListener del botón save
        // Dentro del ActionListener del botón save
        // Dentro del ActionListener del botón save
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String codigoText = codigo.getText();
                String nombreText = nombre.getText();
                String unidadText = unidad.getText();
                boolean isValid = false;
                // Validar código
                if (!isValidInput(codigoText, "[a-zA-Z]+")) {
                    setLabelColor(codigoLbl, Color.RED);
                    isValid = true;
                } else {
                    setLabelColor(codigoLbl, Color.BLACK);
                }

                // Validar nombre
                if (!isValidInput(nombreText, "[a-zA-Z]+")) {
                    setLabelColor(nombreLbl, Color.RED);
                    isValid = true;
                } else {
                    setLabelColor(nombreLbl, Color.BLACK);
                }

                // Validar unidad
                if (!isValidInput(unidadText, "[a-zA-Z]+")) {
                    setLabelColor(unidadLbl, Color.RED);
                    isValid = true;
                } else {
                    setLabelColor(unidadLbl, Color.BLACK);
                }

                if (isValid) {
                    return;
                }

                TipoInstrumento instrumentoActual = new TipoInstrumento(codigoText, nombreText, unidadText);
                try {
                    if (controller.model.getMode() == Application.modeEdit) {
                        controller.update(instrumentoActual);
                    } else {
                        controller.guardar(instrumentoActual);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(panel, e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);

                }
            }


        });


        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controller.report();
                    if(Desktop.isDesktopSupported()){
                        File myFile = new File("TiposDeInstrumentos.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(panel, e.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
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
    private void setLabelColor(JLabel label, Color color) {
        label.setForeground(color);
    }

    private boolean isValidInput(String input, String regex) {
        return input.matches(regex);
    }
    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) {
            int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.UNIDAD};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {
            codigo.setText(model.getCurrent().getCodigo());
            nombre.setText(model.getCurrent().getNombre());
            unidad.setText(model.getCurrent().getUnidad());
        }

        if (model.getMode()==Application.modeEdit){
            codigo.setEnabled(false);
            delete.setEnabled(true );

        }else{
            codigo.setEnabled(true);
            delete.setEnabled(false);

        }
        //if para dejar text en blanco si no esta en ningun modo de los dos



        this.panel.revalidate();
    }
}
