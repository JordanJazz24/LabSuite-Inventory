    package SILAB.logic;
    
    import java.io.Serializable;
    
    public class Protocol   {
    
        public static final String SERVER = "localhost";
        public static final int PORT = 1234;
    
        //TipoInsstrumento
        public static final int createTipoInstrumento =100;
        public static final int readTipoInstrumento =101;
        public static final int updateTipoInstrumento =102;
        public static final int deleteTipoInstrumento =103;
        public static final int searchTipoInstrumento =104;
    
        //Instrumentos
        public static final int createInstrumento =200;
        public static final int readInstrumento =  201;
        public static final int updateInstrumento =202;
        public static final int deleteInstrumento =203;
        public static final int searchInstrumento =204;
    
        //Calibraciones
    
        public static final int createCalibracion =300;
        public static final int readCalibracion =  301;
        public static final int updateCalibracion =302;
        public static final int deleteCalibracion =303;
        public static final int searchCalibracion =304;
        public static final int findCalibrationsForInstrument =305;
    
        //Mediciones
        public static final int deleteMedicionForCalibracion =400;
        public static final int getMedicionesForCalibracion =401;
    
    
    
    
        public static final int SYNC=10;
        public static final int ASYNC =11;
        public static final int DELIVER =12;
        public static final int DISCONNET =13;
    
        public static final int ERROR_NO_ERROR=0;
        public static final int ERROR_ERROR=1;
    }