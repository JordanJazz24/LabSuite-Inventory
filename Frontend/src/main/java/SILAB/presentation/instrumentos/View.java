package SILAB.presentation.instrumentos;


import SILAB.Application;
import SILAB.logic.Instrumento;
import SILAB.logic.TipoInstrumento;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
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
    private JTextField serie;
    private JTextField minimo;
    private JTextField descripcion;
    private JLabel serieLb;
    private JLabel minimoLbl;
    private JLabel descripLb;
    private JButton clear;
    private JLabel maximoLb;
    private JTextField maximo;
    private JTextField tolerancia;
    private JComboBox tipo;
    private JLabel toleranciaLb;

    public View() {
        serie.setToolTipText("Ingrese un número(caracteres numericos)");
        descripcion.setToolTipText("Ingrese un palabras  (caracteres alfabéticos)");
        minimo.setToolTipText("Ingrese un número (caracteres numericos)");
        maximo.setToolTipText("Ingrese un número (caracteres numericos)");
        tolerancia.setToolTipText("Ingrese un número (caracteres numericos)");

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Instrumento filter= new Instrumento();
                    filter.setDescripcion(searchNombre.getText());
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

                controller.clear();
            }
        });


        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Obtener los valores de los campos
                String serieText = serie.getText();
                String descripcionText = descripcion.getText();
                String minimoText = minimo.getText();
                String maximoText = maximo.getText();
                String toleranciaText = tolerancia.getText();
                TipoInstrumento tipoInstrumento = (TipoInstrumento) tipo.getSelectedItem();

                // Bandera para validar
                boolean isValid = true;

                // Validar serie: solo números
                if (!isValidInput(serieText, "\\d+")) {
                    setLabelColor(serieLb, Color.RED);
                    isValid = false;
                } else {
                    setLabelColor(serieLb, Color.BLACK);
                }

                // Validar descripción: solo letras
                if (!isValidInput(descripcionText, "[a-zA-Z\\s]+")) {
                    setLabelColor(descripLb, Color.RED);
                    isValid = false;
                } else {
                    setLabelColor(descripLb, Color.BLACK);
                }

                // Validar minimo y maximo
                int minimoValue = 0;
                int maximoValue = 0;
                try {
                    minimoValue = Integer.parseInt(minimoText);
                    maximoValue = Integer.parseInt(maximoText);
                } catch (NumberFormatException e) {
                    isValid = false;
                }

                if (minimoValue >= maximoValue) {
                    setLabelColor(minimoLbl, Color.RED);
                    setLabelColor(maximoLb, Color.RED);
                    isValid = false;
                } else {
                    setLabelColor(minimoLbl, Color.BLACK);
                    setLabelColor(maximoLb, Color.BLACK);
                }

                // Validar tolerancia: solo números
                if (!isValidInput(toleranciaText, "\\d+")) {
                    setLabelColor(toleranciaLb, Color.RED);
                    isValid = false;
                } else {
                    setLabelColor(toleranciaLb, Color.BLACK);
                }

                // Si hay algún error de validación, salimos
                if (!isValid) {
                    return;
                }

                // Crear el objeto Instrumento
                Instrumento instrumentoActual = new Instrumento(serieText, descripcionText, minimoValue, maximoValue, Integer.parseInt(toleranciaText), tipoInstrumento);

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

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) { //
                super.componentShown(e);
                try {
                    controller.show();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controller.report();
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new java.io.File("Instrumentos.pdf"));

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


    private void setLabelColor(JLabel label, Color color) {
        label.setForeground(color);
    }

    private boolean isValidInput(String input, String regex) {
        return input.matches(regex);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) { // si la propiedad que cambio es la lista actualiza la tabla
            int[] cols = {TableModel.SERIE, TableModel.DESCRIPCION, TableModel.MINIMO, TableModel.MAXIMO, TableModel.TOLERANCIA};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }
        if ((changedProps & Model.TYPES) == Model.TYPES) { //
            if (model.getTipos().isEmpty()) {
                tipo.setModel(new DefaultComboBoxModel(new String[]{"No hay tipos"}));
            }
            tipo.setModel(new DefaultComboBoxModel(model.getTipos().toArray(new TipoInstrumento[0])));
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) { // si la propiedad que cambio es el current actualiza los textfield
            serie.setText(model.getCurrent().getSerie());
            descripcion.setText(model.getCurrent().getDescripcion());
            minimo.setText(String.valueOf(model.getCurrent().getMinimo()));
            maximo.setText(String.valueOf(model.getCurrent().getMaximo()));
            tolerancia.setText(String.valueOf(model.getCurrent().getTolerancia()));
            tipo.setSelectedItem(model.getCurrent().getTipoInstrumento());
        }
        if (model.getMode()==Application.modeEdit){
            serie.setEnabled(false);
            delete.setEnabled(true );

        }else{
            serie.setEnabled(true);
            delete.setEnabled(false);

        }
        //if para dejar text en blanco si no esta en ningun modo de los dos



        this.panel.revalidate();
    }
}
