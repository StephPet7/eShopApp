/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.Ticket_de_caisse;  

/**
 *
 * @author rodriguenana
 */
// ajout ici



import java.io.*;
import javax.print.*;

    
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;




//import java.awt.Rectangle;

import java.awt.print.Book;

import java.awt.print.PageFormat;

import java.awt.print.Paper;



import java.awt.print.PrinterException;

import java.awt.print.PrinterJob;

import java.io.FileInputStream;

import java.io.IOException;

import java.nio.ByteBuffer;

 

import javax.print.PrintService;

 

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPrintPage;



 

public class PrintestBis {

    /*
       public static void main(String[] args) throws IOException, PrinterException {
    
               String fileName = "C:/logoTicket/aaaa-Ticket-V01333.pdf";

    	  // String fileName = "C:/TicketOpenShop/AATicket-Caisse--A7-font10--version-222-490.pdf";
    	 //  String printerName = "Zonerich AB-80K";
    	   String jobName = "PDF Print Job";

       Printest t =     new Printest();
       t.COM(fileName);
       t.printPDF(fileName,jobName);
    
      }     
  */

      public void printPDF(String filePath, String jobName)

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

   
  public  void COM(String fileName) {
        //dimension validé pour la largeur de 210, la hauteur pour 8 macthes 600, 300 pour 5 matches sans les info du bas
      // pour chaque pari il faut ajourter 25 de hauteur, il faut trouver un moyen d'estimer les info du bas.
        // pour 9 pari de match on a 650, pour 8 par on a 625, pour 3 on devrait avoir
        Document document = new Document(new Rectangle(300, 800)); 
     //   Document document = new Document(PageSize.A7, 5, 5, 0, 0);
       // Document document = new Document(PageSize.A7);
      //  Ticket tic = new Ticket(0,null,null,null,0,0);
     //   tic.setFichierTicket("C:/logoTicket/AATicket-Caisse--A7-font10--version-222-450b.pdf");
       
      document.addTitle("ROYAL GAME");
       document.setMargins(10,10, 0, 0);
       
  try {
            PdfWriter.getInstance(document,
  new FileOutputStream(fileName));

            document.open();
            
            //  document.setMargins(1, 2, 1, 1);
            // Insertion du logo de la companie
        Image image1 = Image.getInstance("C:/logoTicket/logo_ballon02.png");
       // image1.setAlignment(Image.MIDDLE);
        image1.scalePercent(40, 40);
        image1.setAlignment(Image.ALIGN_CENTER);
         //document.isInline();
         
        document.add(image1);
     /*       Font FontTitre = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
       Paragraph preface = new Paragraph("ROYAL GAME", FontTitre);
      
     // preface.add(new Paragraph("Europa-Game", catFont));
     preface.setAlignment(Element.ALIGN_CENTER);
  //  addEmptyLine(preface, 1);
         //document.add(new Phrase("  "));
         //  document.add(new Paragraph("EUROPA-GAME", fontTitre));
          document.add(preface);
          document.add(new Phrase("  "));*/

         //   Paragraph titre = new Paragraph("Europa-Game", catFont);
          //  titre.setAlignment(Element.ALIGN_CENTER);
       // document.add(titre);
            
            PdfPTable table = new PdfPTable(1); // 3 columns.
            
           table.setWidthPercentage(100);
           table.setSpacingBefore(6f);
           table.setSpacingAfter(6f);
           
                   
           
         //  table.setSpacingBefore(10f);
           //table.setSpacingAfter(10f);
         Font font5 = new Font(Font.FontFamily.TIMES_ROMAN   , 11);
         Font font2 = new Font(Font.FontFamily.TIMES_ROMAN   , 12);
         Font fontInfo = new Font(Font.FontFamily.TIMES_ROMAN, 11);
         Font fontTicket = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        // Font fontTitre = new Font(Font.FontFamily.TIMES_ROMAN, 14);
        // fontTitre.setStyle("bold");
         
                document.add(new Paragraph(" -Date. 23/05/2015 19:22:21 ", font2));
               document.add(new Paragraph(" -Caisse. 25803  Ticket. 478-523-690 ", fontTicket));

          //  Font catFont = new Font(Font.FontFamily.HELVETICA, 16);        
        //    PdfPCell cell1 = new PdfPCell(new Paragraph("25/05/2015 18:45 - Cote 3.15", font3));
         //   PdfPCell cell2 = new PdfPCell(new Paragraph("Manchester Chelsea – Queens Park Rangers", font2));
           // PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));

           
          //  table.addCell(cell1);
          //  table.addCell(cell2);
            //table.addCell(cell3);

           // document.add(table);
          
             //Paragraph preface = new Paragraph();
    // We add one empty line
   // addEmptyLine(preface, 1);
    // Lets write a big header

       //   document.add(new Paragraph(" Caisse Nr. 25803  Pari. 478-523-690 ", fontTicket));
       
          
           PdfPCell cell1 = new PdfPCell( new Paragraph(" Jeu: 10256 - 24/05/2015 16:30\n Manchester – Queens Park Rangers\n Score(1-2) Mi-Temps, cote: 1.55", font2));
           PdfPCell cell2 = new PdfPCell(new Paragraph(" Jeu: 11200 - 24/05/2015 18:45\n Chievo Verone – Hellas Verone\n V1 Fin Match, cote: 2.55", font2)); 
           PdfPCell cell3 = new PdfPCell(new Paragraph(" Jeu: 14381 - 24/05/2015 19:00\n FC Porto  -  Gil Vicente\n TB(02) Mi-Temps, cote: 1.25", font2));
           PdfPCell cell11 = new PdfPCell( new Paragraph(" Jeu: 10256 - 24/05/2015 16:30\n Manchester – Queens Park Rangers\n S(1-2) Mi-Temps, cote: 1.55", font2));
           PdfPCell cell12 = new PdfPCell(new Paragraph(" Jeu: 11200 - 24/05/2015 18:45\n Chievo Verone – Hellas Verone\n V1 Fin Match, cote: 2.55", font2)); 
           PdfPCell cell13 = new PdfPCell(new Paragraph(" Jeu: 14381 - 24/05/2015 19:00\n FC Porto  -  Gil Vicente\n TB(02) Mi-Temps, cote: 1.25", font2));
           PdfPCell cell14 = new PdfPCell(new Paragraph(" Jeu: 11200 - 24/05/2015 18:45\n Chievo Verone – Hellas Verone\n V1 Fin Match, cote: 2.55", font2)); 
           PdfPCell cell15 = new PdfPCell(new Paragraph(" Jeu:14381 - 24/05/2015 19:00\n FC Porto  -  Gil Vicente\n TB(02) Mi-Temps, cote: 1.25", font2));
           PdfPCell cell16 = new PdfPCell(new Paragraph(" Jeu: 14381 - 24/05/2015 19:00\n FC Porto  -  Gil Vicente\n TB(02) Mi-Temps, cote: 1.25", font2));
             PdfPCell cell17 = new PdfPCell(new Paragraph(" Jeu: 14381 - 24/05/2015 19:00\n FC Porto  -  Gil Vicente\n TB(02) Mi-Temps, cote: 1.25", font2));
          //Paragraph paragraph7 = new Paragraph("En cas de gain, vous serez payé au point de vente où le ticket a été délivré", font2);
          Phrase paragraph7 = new Phrase(" Ce pari confirme que le joueur accepte nos règles de paris \n sans restriction.", fontInfo);
          //document.add(preface);
          document.add(new Phrase("  "));
          Phrase phrase = new Phrase(2);
          phrase.setFont(fontInfo);
          phrase.add("Paris Simple - Partenaire Nro. 24563");
       //   document.add(new Phrase(" "));
          document.add(phrase);
             
          table.addCell(cell1);
          table.addCell(cell2);
          table.addCell(cell3);
      /*   table.addCell(cell11);
         table.addCell(cell12);
          table.addCell(cell13);
          table.addCell(cell14);
          table.addCell(cell15);
          table.addCell(cell16);
          table.addCell(cell17);  */
          
          document.add(table);
         // document.add(paragraph7);
        //  document.add(new Paragraph("---------------------------------"));
          document.add(new Paragraph(" -Mise: 1500 XAF, Cote: 4.94", font2));
          document.add(new Paragraph(" -Gain Estimé: 7410 XAF", font2));
          
        Image image2 = Image.getInstance("");
      
       // image2.scalePercent(90, 75);
        image2.setAlignment(Image.ALIGN_CENTER);
        document.add(image2);
        document.add(paragraph7);        
        document.add(new Phrase(" Les paris sont interdits au moins de 18 ans.",fontInfo));
          //document.add(paragraph7);
         // document.add(paragraph2);
           document.add(new Paragraph("---------------------------------"));
          document.add(new Paragraph("---------------------------------"));
            document.close();
            //tic.prinTicketPDF();
        } catch(Exception e){ System.out.println(e.toString());

        }
  
    }
}
 
    
    

