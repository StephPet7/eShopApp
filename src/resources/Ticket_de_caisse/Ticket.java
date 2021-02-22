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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.*;
import java.io.*;  
import javax.print.*; 

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
import entity.Lignefacture;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author rodriguenana
 */
public class Ticket {
    
      private final String titre = "eShop Ticket";
      public  ArrayList<Lignefacture> listAchats;
      private String TicketEtCaisse = null;
      private String dateTicket = null;
      private String total = " -Total: ";
      private String montantRemi = " -Montant Remis:  ";
      private String reliquat = "- Reliquat: ";
      private final String urlQRCode  = "qrcode.PNG";
      private final int width = 315;
      private final int height = 850;
      private final int dim = 25;
      public String fichierTicket = "ticket_";
      private String infoQRCode = "eShop";
      private final String logo= "E:/Academie/personal_documents/codes/Java/eShop/src/resources/img/defaultProduct.png";
      
      private void setFichierTicket(String url){ this.fichierTicket = url;}
      
      public Ticket(ArrayList<Lignefacture> list, String achatDate, String caisseId,double montantR)
      { 
            super();
            this.listAchats = list;
            dateTicket = "-"+achatDate;
            TicketEtCaisse = "-"+"Caisse"+caisseId;
            double tmp = 0.0;
            for(Lignefacture t:listAchats){
                tmp += t.getPrixT();
            }
            total+= ""+tmp+" XAF";       
            montantRemi +=""+montantR+" XAF";
            reliquat +=""+(montantR - tmp)+" XAF";
            infoQRCode += caisseId+achatDate.replace("/", "").replace(":", "");
            this.fichierTicket += this.TicketEtCaisse + ".pdf";
      }
      public boolean buildTicket(){
        Document document = new Document(new Rectangle(width, height)); 
        document.addTitle(this.titre);
        document.setMargins(10,10, 0, 0);
        System.out.println("QRcode " + this.infoQRCode);
        boolean res = QRcode.Create(this.infoQRCode, this.urlQRCode);
        if(!res) return false;
   
        try {
            PdfWriter writer=PdfWriter.getInstance(document,  new FileOutputStream(this.fichierTicket));
            document.open();

           // Image image1 = Image.getInstance("file:src/utile/logo_ballon_couronne_ticket.png");
            Image image1 = Image.getInstance(logo);
            image1.setAlignment(Image.ALIGN_TOP);
            image1.setAlignment(Image.ALIGN_CENTER);
            image1.scalePercent(40, 40);
            document.add(image1);
            System.out.println(" Impression interne:logo");
            // creation de la table pour inserer les paris 
            PdfPTable table = new PdfPTable(4); // 1 column.
            table.setWidthPercentage(100);
            table.setSpacingBefore(6f);
            table.setSpacingAfter(6f);
            
            // definition du style des caracteres
            Font font1 = new Font(Font.FontFamily.UNDEFINED, 12);
            Font fontInfo = new Font(Font.FontFamily.UNDEFINED, 12);
            Font fontLois = new Font(Font.FontFamily.UNDEFINED, 11);
            Font fontValider = new Font(Font.FontFamily.UNDEFINED, 12);
           document.add(new Paragraph(" "+this.TicketEtCaisse, font1));
           document.add(new Paragraph(" "+this.dateTicket, font1));   
           document.add(new Phrase("  "));
           Paragraph p =new Paragraph("  Id:"+this.infoQRCode, fontValider);
           p.setAlignment(Element.ALIGN_CENTER);
           document.add(p);
           
           
           PdfPCell name = new PdfPCell(new Paragraph("Code",font1));
           name.setHorizontalAlignment(Element.ALIGN_CENTER);
           name.setVerticalAlignment(Element.ALIGN_MIDDLE);
           table.addCell(name);
           
           name = new PdfPCell(new Paragraph("Quantite",font1));
           name.setHorizontalAlignment(Element.ALIGN_CENTER);
           name.setVerticalAlignment(Element.ALIGN_MIDDLE);
           table.addCell(name);
           
           name = new PdfPCell(new Paragraph("Prix Unitaire",font1));
           name.setHorizontalAlignment(Element.ALIGN_CENTER);
           name.setVerticalAlignment(Element.ALIGN_MIDDLE);
           table.addCell(name);
           
           name = new PdfPCell(new Paragraph("Prix Total",font1));
           name.setHorizontalAlignment(Element.ALIGN_CENTER);
           name.setVerticalAlignment(Element.ALIGN_MIDDLE);
           table.addCell(name);
           
           PdfPCell cel = null;
           int count = listAchats.size();
           Lignefacture a;
           for(int i= 0; i < count; i++){
            a= listAchats.get(i);
            
            cel = new PdfPCell(new Paragraph(a.getCode(),font1));
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cel);
            
            cel = new PdfPCell(new Paragraph(""+a.getQte(),font1));
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cel);
            
            cel = new PdfPCell(new Paragraph(""+a.getPrixU(),font1));
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cel);
            
            cel = new PdfPCell(new Paragraph(""+a.getPrixT(),font1));
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            cel.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cel);
          }
           
          document.add(table);
         
          System.out.println(" Impression interne : Table imprimee");
          
            document.add(new Paragraph(" "+this.total, font1));
            document.add(new Paragraph(" "+this.montantRemi, font1));
            document.add(new Paragraph(" "+this.reliquat, font1));
            System.out.println(" Impression interne: prix totaux");
            Image image2 = Image.getInstance(this.urlQRCode);
            image2.setAlignment(Image.ALIGN_CENTER);
           // image2.scalePercent(50, 50);
            document.add(image2);       
            
            p = new Paragraph("---------------------------------");            
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);
            
            p = new Paragraph("@Eshop Team");            
            p.setAlignment(Element.ALIGN_RIGHT);
            document.add(p);

            document.close();
            writer.close();          
            
              }catch(Exception e){
                  System.out.println(e);
                  return false;
              }
    
    
         return true;
         } 
   
      
      public void deleteTMP(){
          Ticket.deleteFile(this.fichierTicket);
      }
      
      public void printTicket(){
          try {
              Ticket.prinTicketPDF(this.fichierTicket, "JOBName"+this.infoQRCode);
              Ticket.deleteFile(this.fichierTicket);
          } catch (IOException ex) {
              Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
          } catch (PrinterException ex) {
              Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
          }
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

public static void printAffichePDF(String jobName)

                  throws IOException, PrinterException {

             String filePath = "C:/ROYALGAME/AFFICHE/"+jobName;
             
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

                  if (name.equalsIgnoreCase("PDFCreator")) {

                        printJob.setPrintService(printServices[count]);

                        break;

                  } 

            }  

            printJob.print();

      }
     
      
 // public static void main(String[] args) {
    
  
 //   }
    
    public static boolean deleteFile(String fichier)
    {	
    	try{
 
    		File file = new File(fichier);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
                        return true;
    		}else{
    			System.out.println("Delete operation is failed.");
                        return false;
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
                 return false;
    	}
 
    }
}
      
