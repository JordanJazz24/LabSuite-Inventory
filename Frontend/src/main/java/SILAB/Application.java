package SILAB;

import SILAB.presentation.tipos.Controller;
import SILAB.presentation.tipos.Model;
import SILAB.presentation.tipos.View;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};


//Creacion de los controladores de los modulos
        Model tiposModel= new Model();
        View tiposView = new View();
        tiposController = new Controller(tiposView,tiposModel);

        SILAB.presentation.calibraciones.Model calibraModel= new SILAB.presentation.calibraciones.Model();
        SILAB.presentation.calibraciones.View calibraView = new SILAB.presentation.calibraciones.View();
         calibracionesController = new SILAB.presentation.calibraciones.Controller(calibraView,calibraModel);

         SILAB.presentation.instrumentos.Model instrumentosModel= new SILAB.presentation.instrumentos.Model();
        SILAB.presentation.instrumentos.View instrumentosView2 = new SILAB.presentation.instrumentos.View();
        instrumentosController = new SILAB.presentation.instrumentos.Controller(instrumentosView2,instrumentosModel);

        SILAB.presentation.mensajes.Model mensajesModel= new SILAB.presentation.mensajes.Model();
        SILAB.presentation.mensajes.View mensajesView = new SILAB.presentation.mensajes.View();
        mensajesController = new SILAB.presentation.mensajes.Controller(mensajesView,mensajesModel);

//----------------acerca de------------------
        JLabel aboutLabel = new JLabel("<html><center><h1>Integrantes:</h1></center><br>" +
                                "<li>Jordan Alvarez Gonzalez</li>" +
                "<li>Cristopher Castro Aguilar</li>" +
                "<li>Alejandro Araya Mendez</li></ul></html>");
// Establecer el tamaño de la fuente para el texto
        aboutLabel.setFont(new Font("Arial", Font.PLAIN, 14));
// Alinear el texto al centro horizontalmente
        aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);
// Agregar el JLabel a un JPanel
        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.add(aboutLabel, BorderLayout.CENTER);

        JFrame window = new JFrame();
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(BorderFactory.createTitledBorder("SILAB: Sistema de Laboratorio Industrial"));

        tabs.add("Tipos de Instrumento", tiposView.getPanel());
        tabs.add("Instrumento", instrumentosView2.getPanel());
        tabs.add("Calibraciones", calibraView.getPanel());
        tabs.add("Acerca de", aboutPanel);

        JPanel mensajes = mensajesView.getPanel();
        mensajes.setBorder(BorderFactory.createTitledBorder("Mensajes"));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(tabs, BorderLayout.CENTER);
        contentPanel.add(mensajes, BorderLayout.EAST);

        window.add(contentPanel);

        window.setSize(1200, 450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setVisible(true);
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Application.mensajesController.stop();
            }
        });
    }

    public static Controller tiposController;
    public static SILAB.presentation.instrumentos.Controller instrumentosController;
    public static SILAB.presentation.calibraciones.Controller calibracionesController;
    public static SILAB.presentation.mensajes.Controller mensajesController;

    public final static int modeCreation=1;
    public final static int modeEdit=2;
    public final static int modeMedicion=3;


    public static JFrame window;
}
