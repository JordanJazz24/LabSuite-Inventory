package SILAB.logic;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class Service implements IService, IListener {  // PROXY
    private static Service theInstance;
    ObjectSocket os = null; //sincronico
    ObjectSocket as = null; //asincronico
    ITarget target;

    public static IService instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    public static IListener instanceListener() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    public Service() {
        try {
            this.connect();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    private void connect() throws Exception {
        os = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));
        os.out.writeInt(Protocol.SYNC);
        os.out.flush();
        os.sid = (String) os.in.readObject();

    }

    private void disconnect() throws Exception {
        os.out.writeInt(Protocol.DISCONNET);
        os.out.flush();
        os.skt.shutdownOutput();
        os.skt.close();

    }

    //-------------------tipo instrumento----------------------
    @Override
    public void create(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.createTipoInstrumento);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Tipo de instrumento creado");
        } else throw new Exception("Tipo ya existe O ERROR ");

    }

    @Override
    public TipoInstrumento read(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.readTipoInstrumento);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            return (TipoInstrumento) os.in.readObject();
        } else throw new Exception("Tipo no exite");
    }

    @Override
    public void update(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.updateTipoInstrumento);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Tipo de instrumento actualizado");
        } else throw new Exception("Nose pudo actualizar");
    }

    @Override
    public void delete(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.deleteTipoInstrumento);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Tipo de instrumento eliminado");
        } else throw new Exception("Nose pudo eliminar");
    }

    @Override
    public List<TipoInstrumento> search(TipoInstrumento tipoInstrumento) throws Exception {
        os.out.writeInt(Protocol.searchTipoInstrumento);
        os.out.writeObject(tipoInstrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR ) { //no hay errores
            System.out.println("Tipo de instrumento buscado");
            return (List<TipoInstrumento>) os.in.readObject();
        } else throw new Exception("Nose pudo buscar");
    }


    //--------------------------instrumento---------------------

    @Override
    public void create(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.createInstrumento);
        os.out.writeObject(instrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Instrumento creado");
        } else throw new Exception("Instrumento ya existe O ERROR ");
    }

    @Override
    public Instrumento read(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.readInstrumento);
        os.out.writeObject(instrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            return (Instrumento) os.in.readObject();
        } else throw new Exception("Instrumento no exite");
    }

    @Override
    public void update(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.updateInstrumento);
        os.out.writeObject(instrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Instrumento actualizado");
        } else throw new Exception("Nose pudo actualizar");
    }

    @Override
    public void delete(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.deleteInstrumento);
        os.out.writeObject(instrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Instrumento eliminado");
        } else throw new Exception("Nose pudo eliminar");
    }

    @Override
    public List<Instrumento> search(Instrumento instrumento) throws Exception {
        os.out.writeInt(Protocol.searchInstrumento);
        os.out.writeObject(instrumento);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Instrumento buscado");
            return (List<Instrumento>) os.in.readObject();
        } else throw new Exception("Nose pudo buscar");
    }


    //calibracion
    @Override
    public void create(Calibracion calibracion) throws Exception {
        os.out.writeInt(Protocol.createCalibracion);
        os.out.writeObject(calibracion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Calibracion creada");
        } else throw new Exception("Calibracion ya existe O ERROR ");
    }

    @Override
    public Calibracion read(Calibracion calibracion) throws Exception {
        os.out.writeInt(Protocol.readCalibracion);
        os.out.writeObject(calibracion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            return (Calibracion) os.in.readObject();
        } else throw new Exception("Calibracion no exite");
    }

    @Override
    public void update(Calibracion calibracion) throws Exception {
        os.out.writeInt(Protocol.updateCalibracion);
        os.out.writeObject(calibracion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Calibracion actualizada");
        } else throw new Exception("Nose pudo actualizar");
    }

    @Override
    public void delete(Calibracion calibracion) throws Exception {
        os.out.writeInt(Protocol.deleteCalibracion);
        os.out.writeObject(calibracion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Calibracion eliminada");
        } else throw new Exception("Nose pudo eliminar");
    }

    @Override
    public List<Calibracion> search(Calibracion calibracion) throws Exception {
        os.out.writeInt(Protocol.searchCalibracion);
        os.out.writeObject(calibracion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Calibracion buscada");
            return (List<Calibracion>) os.in.readObject();
        } else throw new Exception("Nose pudo buscar");
    }

    @Override
    public List<Calibracion> findCalibrationsForInstrument(Instrumento instrumento) {
        try {
            os.out.writeInt(Protocol.findCalibrationsForInstrument);
            os.out.writeObject(instrumento);
            os.out.flush();
            if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
                System.out.println("Calibraciones buscadas");
                return (List<Calibracion>) os.in.readObject();
            } else throw new Exception("Nose pudo buscar");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //medicion
    @Override
    public void deleteMedicionForCalibracion(Medicion medicion) throws Exception {
        os.out.writeInt(Protocol.deleteMedicionForCalibracion);
        os.out.writeObject(medicion);
        os.out.flush();
        if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
            System.out.println("Medicion eliminada");
        } else throw new Exception("Nose pudo eliminar");
    }

    @Override
    public List<Medicion> getMedicionesForCalibracion(String s) {
        try {
            os.out.writeInt(Protocol.getMedicionesForCalibracion);
            os.out.writeObject(s);
            os.out.flush();
            if (os.in.readInt() == Protocol.ERROR_NO_ERROR) { //no hay errores
                System.out.println("Mediciones buscadas");
                return (List<Medicion>) os.in.readObject();
            } else throw new Exception("Nose pudo buscar");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //------------------------ LISTENING FUNCTIONS
    boolean continuar = true;

    public void startListening() {
        try {
            as = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));
            as.sid = os.sid;
            as.out.writeInt(Protocol.ASYNC);
            System.out.println("escuchando asicnronicnamente : " + as.sid);
            as.out.writeObject(as.sid);
            as.out.flush();
        } catch (Exception e) { System.out.println("Error al conectar");       }

        Thread t = new Thread(new Runnable() {
            public void run() {
                listen();
                System.out.println("Escuchando");
            }
        });
        continuar = true;
        t.start();
    }

    public void stopListening() {
        continuar = false;
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = as.in.readInt();
                switch (method) {
                    case Protocol.DELIVER:
                        try {
                            Message message = (Message) as.in.readObject();
                            deliver(message);
                        } catch (ClassNotFoundException ex) {
                            System.out.println("Error al recibir mensaje");
                        }
                        break;
                }

             //   as.out.flush();//quitar talvez

            } catch (IOException ex) {
                continuar = false;
            }
        }
        //cerrar conexion
        try {
            as.skt.shutdownOutput();
            as.skt.close();
        }catch (Exception e){System.out.println("Error al cerrar conexion");}

    }

    private void deliver(final Message message) {
        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {
                                           target.deliver(message);
                                       }
                                   }
        );
    }

    @Override
    public void setTarget(ITarget target) {
        this.target = target;
    }

    @Override
    public void start() {
        this.startListening();
    }

    @Override
    public void stop() {
        this.stopListening();
        try {
            this.disconnect();
            System.out.println("Desconectado");
        } catch (Exception e) {
            System.out.println("Error al desconectar");
        }
    }
}
