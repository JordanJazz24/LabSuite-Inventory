package SILAB.logic;//package SILAB.logic;
//
//
//import com.itextpdf.kernel.colors.ColorConstants;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.*;
//import com.itextpdf.layout.properties.TextAlignment;
//import com.itextpdf.layout.properties.UnitValue;
//
//import java.io.IOException;
//
//public class PDFGenerator implements Serializable  {
//    public static void generatePDF(String dest) throws IOException {
//        PdfWriter writer = new PdfWriter(dest);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        // Agregar título
//        Paragraph title = new Paragraph("Lista de Tipos de Instrumentos");
//        title.setTextAlignment(TextAlignment.CENTER);
//        title.setFontSize(20);
//        document.add(title);
//
//        // Crear tabla para mostrar los datos
//        Table table = new Table(new float[]{1, 2, 2}); // 3 columnas
//        table.setWidth(UnitValue.createPercentValue(100));
//
//        // Encabezados de columna
//        table.addHeaderCell(new Cell().add(new Paragraph("Código").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Unidad"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//
//        // Agregar datos de tipos de instrumentos
//        // Reemplaza esto con tu propia lógica para obtener los tipos de instrumentos
//        for ( TipoInstrumento tipoInstrumento : Service.instance().search(new TipoInstrumento()) ) {
//            table.addCell(new Cell().add(new Paragraph(tipoInstrumento.getCodigo())));
//            table.addCell(new Cell().add(new Paragraph(tipoInstrumento.getNombre())));
//            table.addCell(new Cell().add(new Paragraph(tipoInstrumento.getUnidad())));
//        }
//
//        document.add(table);
//
//        // Cerrar el documento
//        document.close();
//
//
//
//    }
//    public static void generatePDFInstru(String dest) throws IOException {
//        PdfWriter writer = new PdfWriter(dest);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        // Agregar título
//        Paragraph title = new Paragraph("Lista de Instrumentos");
//        title.setTextAlignment(TextAlignment.CENTER);
//        title.setFontSize(20);
//        document.add(title);
//
//        // Crear tabla para mostrar los datos
//        Table table = new Table(new float[]{1, 2, 2, 2, 2, 2}); // 6 columnas
//        table.setWidth(UnitValue.createPercentValue(100));
//
//        // Encabezados de columna
//        table.addHeaderCell(new Cell().add(new Paragraph("Num.Serie").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Tipo").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Descripcion"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//        table.addHeaderCell(new Cell().add(new Paragraph("Minimo"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//        table.addHeaderCell(new Cell().add(new Paragraph("Maximo"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//        table.addHeaderCell(new Cell().add(new Paragraph("Tolerancia"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//
//
//        // Agregar datos de tipos de instrumentos
//        // Reemplaza esto con tu propia lógica para obtener los tipos de instrumentos
//        for ( Instrumento instrumento : Service.instance().search(new Instrumento()) ) {
//            table.addCell(new Cell().add(new Paragraph(instrumento.getSerie())));
//            table.addCell(new Cell().add(new Paragraph(instrumento.getDescripcion())));
//            table.addCell(new Cell().add(new Paragraph(instrumento.getTipoInstrumento().getNombre())));
//            table.addCell(new Cell().add(new Paragraph(instrumento.getMinimo()+"")));
//            table.addCell(new Cell().add(new Paragraph(instrumento.getMaximo()+"")));
//            table.addCell(new Cell().add(new Paragraph(instrumento.getTolerancia()+"")));
//        }
//
//        document.add(table);
//
//        // Cerrar el documento
//        document.close();
//    }
//
//    public static void generatePDFCalibraciones(String dest) throws IOException {
//        PdfWriter writer = new PdfWriter(dest);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//
//        // Agregar título
//        Paragraph title = new Paragraph("Lista de Calibraciones");
//        title.setTextAlignment(TextAlignment.CENTER);
//        title.setFontSize(20);
//        document.add(title);
//
//        // Crear tabla para mostrar los datos
//        Table table = new Table(new float[]{1, 2, 2, 2}); // 4 columnas
//        table.setWidth(UnitValue.createPercentValue(100));
//
//        // Encabezados de columna
//        table.addHeaderCell(new Cell().add(new Paragraph("Num.Calibracion").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Instrumento").setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE)));
//        table.addHeaderCell(new Cell().add(new Paragraph("Fecha"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//        table.addHeaderCell(new Cell().add(new Paragraph("Mediciones"). setBackgroundColor(ColorConstants.RED).setFontColor(ColorConstants.WHITE) ));
//
//        // Agregar datos de tipos de instrumentos
//        // Reemplaza esto con tu propia lógica para obtener los tipos de instrumentos
//        for (   Calibracion calibracion : Service.instance().search(new Calibracion()) ) {
//            table.addCell(new Cell().add(new Paragraph(calibracion.getNumCalibra())));
//            table.addCell(new Cell().add(new Paragraph(calibracion.getInstrumento().getSerie())));
//            table.addCell(new Cell().add(new Paragraph(calibracion.getFecha())));
//            table.addCell(new Cell().add(new Paragraph(calibracion.getMediciones().size()+"")));
//        }
//
//        document.add(table);
//
//        // Cerrar el documento
//        document.close();
//    }
//
//
//}
