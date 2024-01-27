package SILAB.logic;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Worker {
    Server srv;
    ObjectSocket os;//sincronico
    ObjectSocket as;//asincronico
    IService service;

    public Worker(Server srv, ObjectSocket os,  IService service) {
        this.srv = srv;
        this.os = os;
        this.service = service;
    }

    boolean continuar;

    public void start() {
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable() {
                public void run() {
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setAsincronico(ObjectSocket as) {
        this.as = as;
    }

    public void stop() {
        continuar = false;
        System.out.println("Conexion cerrada...");
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = os.in.readInt();
                System.out.println("Operacion: " + method);

                switch (method) {
                    //-------------------tipo instrumento----------------------
                    case Protocol.createTipoInstrumento:
                        try {
                            TipoInstrumento tipo = (TipoInstrumento) os.in.readObject();
                            service.create(tipo);
                            os.out.writeInt(0);//envia mensaje de error
                            os.out.flush();
                            srv.deliver(new Message(Message.CREATE,"TI",tipo.getNombre()));
                       System.out.println("Tipo: (+)" + "Entidad: TI "+ "Texto: "+ tipo.getNombre());
                            System.out.println("Tipo de instrumento creado");
                        } catch (Exception ex) {
                            System.out.println(ex);
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;

                    case Protocol.readTipoInstrumento:
                        try {
                            TipoInstrumento e = service.read((TipoInstrumento) os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                            os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.updateTipoInstrumento:
                        try {
                            TipoInstrumento tipo = (TipoInstrumento) os.in.readObject();
                            service.update(tipo);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.UPDATE,"TI",tipo.getNombre()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.deleteTipoInstrumento:
                        try {
                            TipoInstrumento tipo = (TipoInstrumento) os.in.readObject();
                            service.delete(tipo);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.DELETE,"TI",tipo.getNombre()));

                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.searchTipoInstrumento:
                        try {
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(service.search((TipoInstrumento) os.in.readObject()));
                           // os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    //--------------------------instrumento---------------------
                    case Protocol.createInstrumento:
                        try {
                            Instrumento instrumento = (Instrumento) os.in.readObject();
                            service.create(instrumento);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.CREATE,"I",instrumento.getDescripcion()));

                            System.out.println("Instrumento creado");
                        } catch (Exception ex) {
                            System.out.println(ex);
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.readInstrumento:
                        try {
                            Instrumento e = service.read((Instrumento) os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                            os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.updateInstrumento:
                        try {
                            Instrumento instrumento = (Instrumento) os.in.readObject();
                            service.update(instrumento);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.UPDATE,"I",instrumento.getDescripcion()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.deleteInstrumento:
                        try {
                            Instrumento instrumento = (Instrumento) os.in.readObject();
                            service.delete(instrumento);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.DELETE,"I",instrumento.getDescripcion()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.searchInstrumento:
                        try {
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(service.search((Instrumento) os.in.readObject()));
                           // os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    // -------------------calibracion----------------------
                    case Protocol.createCalibracion:
                        try {
                            Calibracion calibracion = (Calibracion) os.in.readObject();
                            service.create(calibracion);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.CREATE,"C",calibracion.getInstrumento().getDescripcion()));
                            System.out.println("Calibracion creada");
                        } catch (Exception ex) {
                            System.out.println(ex);
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.readCalibracion:
                        try {
                            Calibracion e = service.read((Calibracion) os.in.readObject());
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(e);
                            os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.updateCalibracion:
                        try {
                            Calibracion calibracion = (Calibracion) os.in.readObject();
                            service.update(calibracion);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.UPDATE,"C",calibracion.getInstrumento().getDescripcion()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.deleteCalibracion:
                        try {
                            Calibracion calibracion = (Calibracion) os.in.readObject();
                            service.delete(calibracion);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.DELETE,"C",calibracion.getInstrumento().getDescripcion()));
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.searchCalibracion:
                        try {
                            Calibracion calibracion = (Calibracion) os.in.readObject();
                            List<Calibracion> calibraciones = service.findCalibrationsForInstrument(calibracion.getInstrumento());
                           //filtrar por fecha
                            List<Calibracion> resultado =calibraciones.stream()
                                    .filter(i -> i.getFecha().contains(calibracion.getFecha()))
                                    .sorted(Comparator.comparing(Calibracion::getFecha))
                                    .collect(Collectors.toList());

                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(resultado);
                            os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.findCalibrationsForInstrument:
                        try {
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(service.findCalibrationsForInstrument((Instrumento) os.in.readObject()));
                            os.out.flush();
                        } catch (Exception ex) {
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    //-------------------Medicion----------------------
                    case Protocol.deleteMedicionForCalibracion:
                        try {
                            Medicion medicion = (Medicion) os.in.readObject();
                            service.deleteMedicionForCalibracion(medicion);
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.flush();
                            srv.deliver(new Message(Message.DELETE,"M",medicion.getCalibracion().getNumCalibra()));
                            System.out.println("Medicion eliminada");
                        } catch (Exception ex) {
                            System.out.println(ex);
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.getMedicionesForCalibracion:
                        try {
                            os.out.writeInt(Protocol.ERROR_NO_ERROR);
                            os.out.writeObject(service.getMedicionesForCalibracion((String) os.in.readObject()));
                            os.out.flush();
                        } catch (Exception ex) {
                            System.out.println(ex);
                            os.out.writeInt(Protocol.ERROR_ERROR);
                            os.out.flush();
                        }
                        break;
                    case Protocol.DISCONNET:
                        try {
                            this.stop();
                            srv.remove(this);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;


                }
            } catch (IOException ex) {
                System.out.println(ex);
                srv.remove(this);
                continuar = false;
            }
        }
    }


   public void deliver(Message message){
       if (as != null) {
           try {
               as.out.writeInt(Protocol.DELIVER);
               as.out.writeObject(message);
               as.out.flush();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
}
