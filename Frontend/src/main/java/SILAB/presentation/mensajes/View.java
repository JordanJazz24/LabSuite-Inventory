package SILAB.presentation.mensajes;


import SILAB.logic.Message;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class View implements Observer {
    private JPanel panel;
    private JPanel bodyPanel;
    private JTextPane messages;
    private JButton limpiarButton;

    Model model;
    Controller controller;

    public View() {
        messages.setEditable(false);
    //    bodyPanel.setVisible(false);

        ///DefaultCaret caret = (DefaultCaret) messages.getCaret();
       // caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.limpiar();
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanel() {
        return panel;
    }


    public void update(Observable updatedModel, Object properties) {

        int prop = (int) properties;
           // bodyPanel.setVisible(true);
            if ((prop & Model.Message) == Model.Message) {
                // Crear un documento para el JTextPane
                StyledDocument doc = messages.getStyledDocument();

                // Crear un estilo para el texto (puedes personalizar esto según tus necesidades)
                Style style = messages.addStyle("DefaultStyle", null);
                StyleConstants.setFontFamily(style, "SansSerif");
                StyleConstants.setFontSize(style, 12);
                StyleConstants.setForeground(style, Color.BLACK);

                this.messages.setText("");

                for (Message message : model.getMessages()) {
                    // Agregar un salto de línea si ya hay texto en el JTextPane
                    if (doc.getLength() > 0) {
                        try {
                            doc.insertString(doc.getLength(), "\n", style);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Agregar el mensaje al JTextPane
                    try {
                        String tipo = "";
                        switch (message.getTipo()){
                            case 1:
                                StyleConstants.setBackground(style, Color.GREEN);
                                tipo = "(+) ";
                                break;
                            case 2:
                                StyleConstants.setBackground(style, Color.CYAN);
                                tipo = "(=) ";
                                break;
                            case 3:
                                StyleConstants.setBackground(style, Color.RED);
                                tipo = "(-) ";
                                break;
                            default:
                                StyleConstants.setBackground(style, Color.BLACK);
                        }


                        String mensaje =  tipo + message.getEntidad()+ " " + message.getTexto() ;
                        doc.insertString(doc.getLength(), mensaje, style);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            panel.validate();
    }

}
