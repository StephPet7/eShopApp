/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.Ticket_de_caisse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
/**
 *
 * @author rodriguenana
 */
public class TicketOpenShop {
   
    
     
    
    
    public TicketOpenShop(){
       
         super();
    
         }
    
    
    public static void PrintPDF(ArrayList<String> ligneFac){
    
         //ici on doit deja inserer l'entete des données.
        int height = 300;
        int width = 230;
        int dim = 20;
        
        String nomMaga = (String)ligneFac.get(1);     
        String NrFacture = (String)ligneFac.get(0);
        String nomFichier = "C:/TicketOpenShop/AATicket" + NrFacture + ".pdf";
       
   
        
     // preparation ticket
        Document document = new Document(new Rectangle(300, 700)); 
        document.addTitle(nomMaga);
        document.setMargins(10,10, 0, 0); 
 try {
            PdfWriter.getInstance(document,
            new FileOutputStream(nomFichier));
            document.open();
            // Insertion du logo de la companie
            Image image1 = Image.getInstance("/resources/Ticket_de_caisse/logo_opensolution.png");
            image1.setAlignment(Image.ALIGN_TOP);
            image1.setAlignment(Image.ALIGN_CENTER);
            image1.scalePercent(50, 50);
            document.add(image1);
         
            // definition du style des caracteres
            Font font1 = new Font(Font.FontFamily.TIMES_ROMAN   , 10);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN    , 10);
           // Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            //Font fontTicket = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font fontTitre = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            fontTitre.setStyle("bold");
       
           Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
           Paragraph GrandTitre = new Paragraph(nomMaga, catFont);
    
           GrandTitre.setAlignment(Element.ALIGN_CENTER);

           document.add(GrandTitre);
           document.add(new Phrase("  "));       
           document.add(new Paragraph((String)ligneFac.get(2), font1));
           document.add(new Paragraph(NrFacture, font2));
           // date ici
           document.add(new Paragraph((String)ligneFac.get(3), font2)); 
           document.add(new Paragraph((String)ligneFac.get(4), font2));  
           document.add(new Phrase("  "));
           
          String ma = null;
             //String affichageMatches[] = new String[this.listPariMatches.size()];
             for(int i= 5; i < ligneFac.size()-1; i++){
             //  Jeu: 10256 - 24/05/2015 16:30\n Manchester – Queens Park Rangers\n Score(1-2) Mi-Temps, cote: 1.55"
            ma = (String)ligneFac.get(i);
            document.add( new Paragraph(" - " + ma,font1)); 
              
                      
                               }
            document.add(new Paragraph("---------------------------------------------------"));
             // arriver à  ce niveau signifie que tous les jeux ont ete insere dans la table
            document.add(new Paragraph((String)ligneFac.get(ligneFac.size()-1), font1));
            document.add(new Phrase("  "));
            document.add(new Paragraph(" Merci et gardez votre ticket pour toute reclamation", font2));
            document.add(new Paragraph("----------------------------------------"));          
            document.add(new Paragraph(" Logiciel fourni par Open Solution - Tel 676892402", font2));

            document.close();
            prinTicketPDF(nomFichier,"PDF Ticket");
              }catch(Exception e){;}
    
    
         } 
      
 
public static void prinTicketPDF(String filePath, String jobName)

                  throws IOException, PrinterException {

             FileInputStream fileInputStream = new FileInputStream(filePath);

            byte[] pdfContent = new byte[fileInputStream.available()];

            fileInputStream.read(pdfContent, 0, fileInputStream.available());

            ByteBuffer buffer = ByteBuffer.wrap(pdfContent);

            final PDFFile pdfFile = new PDFFile(buffer);

            PDFPrintPage pages = new PDFPrintPage(pdfFile);


            PrinterJob printJob = PrinterJob.getPrinterJob();
            
            PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();

            printJob.setJobName(jobName);

            Book book = new Book();
            




            book.append(pages, pageFormat, pdfFile.getNumPages());

            printJob.setPageable(book);

            Paper paper = new Paper();

            paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
          
            pageFormat.setPaper(paper);
            PrintService Nservices = PrintServiceLookup.lookupDefaultPrintService();
            String name = Nservices.getName();
          //  printJob.setPrintService(Nservices);
            PrintService[] printServices = PrinterJob.lookupPrintServices();

            for (int count = 0; count < printServices.length; ++count) {

                  if (name.equalsIgnoreCase(printServices[count].getName())) {

                        printJob.setPrintService(printServices[count]);

                        break;

                  } 

            }  

            printJob.print();

      }
  /*
public static void main(String args[]){


   ArrayList<String> testFac = new ArrayList<String>(7);
   
   for(int i= 0; i < 9; i++){
   
   testFac.add(new String("moniqueShopping_"+i));
   }
   
   TicketOpenShop.PrintPDF(testFac);



   } */
    
    
}
