package com.dynamite.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;

@RestController
public class pdfParser {

	@RequestMapping(value = "/pdfParser", method = RequestMethod.GET)
	public ResponseEntity<?> pdfParser() throws DocumentException, IOException {
	    try {
	        UUID uuid = UUID.randomUUID();
	        String src = "D:/ArihantCapital/write-to-pdf/Pdf/read/Laxmi Dental Limited_ ASBA_R Form_Nuvama.pdf";  // Input PDF
	        String dest = "D:/ArihantCapital/write-to-pdf/Pdf/" + uuid + "Modified_Form.pdf";
	        String finalDest = "D:/ArihantCapital/write-to-pdf/Pdf/" + uuid + "Modified_Form1.pdf";
	        PdfReader reader = new PdfReader(src);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

	        int page = 1; // Process only page 1
	        Map<String, List<Map<String, Object>>> fieldsToUpdate = new HashMap<>();

	        
	        addField(fieldsToUpdate, "2. PAN OF SOLE / FIRST BIDDER", Map.of("id", 1, "value", addSpaces("AYCPV8215G"), "position", "top-right", "offsetX", 250, "offsetY", 200, "font_size", 15));// TOP PAN NUMBER
	        addField(fieldsToUpdate, "2. PAN OF SOLE / FIRST BIDDER", Map.of("id", 1, "value", addSpaces("AYCPV8215G"), "position", "bottom-right", "offsetX", 210, "offsetY", 190, "font_size", 15));// BOTTOM PAN NUMBER
	        
	        addField(fieldsToUpdate, "Bid cum1", Map.of("id", 1, "value", "30833057", "position", "top-right", "offsetX", 150, "offsetY", 80, "font_size", 15));
	        addField(fieldsToUpdate, "Bid cum2", Map.of("id", 2, "value", "30833057", "position", "right-center", "offsetX", 150, "offsetY", 315, "font_size", 15));
	        addField(fieldsToUpdate, "Bid cum3", Map.of("id", 2, "value", "30833057", "position", "bottom-right", "offsetX", 150, "offsetY", 38, "font_size", 15));
	        
	        addField(fieldsToUpdate, "3. BIDDER’S DEPOSITORY ACCOUNT DETAILS", Map.of("id", 1, "value", addSpaces("IN30198311315434"), "position", "top-left", "offsetX", 70, "offsetY", 230, "font_size", 15));
	        addField(fieldsToUpdate, "DP ID/CL ID", Map.of("id", 1, "value", addSpaces("IN30198311315434"), "position", "bottom-left", "offsetX", 70, "offsetY", 190, "font_size", 15));
	        
	        
	      //Mobile Number Top Right
	        addField(fieldsToUpdate, "Telephone / Mobile", Map.of("id", 2, "value", addSpaces("7666321805"), "position", "top-right", "offsetX", 180, "offsetY", 170, "font_size", 12));
	        
	        //Mobile Number Botton left
	        addField(fieldsToUpdate, "Telephone / Mobile", Map.of("id", 2, "value", addSpaces("7666321805"), "position", "bottom-left", "offsetX", 115, "offsetY", 125, "font_size", 12));
  
	        List<String> amountinWordBreak = splitTextIntoLines(addSpaces("Fourteen Thousand One Hundred Twenty Four"), 50);
	        int amountinWordyOffset = 10;
	        for (String line : amountinWordBreak) {
	          addField(fieldsToUpdate, "Price in word", Map.of("id", 2, "value", line, "position", "right-center", "offsetX", 280, "offsetY", -30 - amountinWordyOffset, "font_size", 12));//PRICE IN WORD
	          amountinWordyOffset -= 20;  // Move down for the next line
	        }
	        
	        List<String> bidderName = splitTextIntoLines("Suraj Lalman Vishwakarma", 18);
            int NameyOffset = 0;
            for (String line : bidderName) {
                addField(fieldsToUpdate, "1.NAME & CONTACT DETAILS OF SOLE/FIRST BIDDER", Map.of("id", 2, "value", line, "position", "top-right", "offsetX", 230, "offsetY", 110 - NameyOffset, "font_size", 15));// Name of Sole / First Bidder
                NameyOffset -= 17;  
            }
            
            //NAME BOTTOM LEFT
            List<String> bidderName1 = splitTextIntoLines("Suraj Lalman Vishwakarma", 25);
            int NameyOffset1 = 0;
            for (String line : bidderName1) {
                addField(fieldsToUpdate, "1.NAME & CONTACT DETAILS OF SOLE/FIRST BIDDER", Map.of("id", 2, "value", line, "position", "bottom-left", "offsetX", 140, "offsetY", 145 - NameyOffset1, "font_size", 15));// Name of Sole / First Bidder
                NameyOffset1 -= 17;  
            }
            
            
            //NAME BOTTOM RIGHT
            List<String> bidderName2 = splitTextIntoLines("Suraj", 25);
            int NameyOffset2 = 0;
            for (String line : bidderName2) {
                addField(fieldsToUpdate, "1.NAME & CONTACT DETAILS OF SOLE/FIRST BIDDER", Map.of("id", 2, "value", line, "position", "bottom-right", "offsetX", 200, "offsetY", 90 - NameyOffset2, "font_size", 12));// Name of Sole / First Bidder
                NameyOffset1 -= 15;  
            }
            
            List<String> addressLines = splitTextIntoLines("303 OM RESIDENCY SECTOR 5 PLOT 10 ULWE NAVI MUMBAI 410205", 50);
            int yOffset = 0;
            for (String line : addressLines) {
                addField(fieldsToUpdate, "Address", Map.of("id", 2, "value", line, "position", "top-right", "offsetX", 250, "offsetY", 140 - yOffset, "font_size", 8));
                yOffset -= 15;  // Move down for the next line
            }
            
          //TOP LEFT
            addField(fieldsToUpdate, "Amount blocked (in figures)", Map.of("id", 1, "value", "14124", "position", "left-center", "offsetX", 140, "offsetY", -35, "font_size", 15));
            //Bottom left
            addField(fieldsToUpdate, "Amount blocked (in figures)", Map.of("id", 1, "value", "14124", "position", "bottom-left", "offsetX", 140, "offsetY", 170, "font_size", 15));
            
            
            //SET CENTER BID PRICE . BID OPTIONS
            addField(fieldsToUpdate, "BID OPTIONS Bid", Map.of("id", 1, "value", "33", "position", "top-center", "offsetX", 240, "offsetY", 315, "font_size", 15));
            addField(fieldsToUpdate, "BID OPTIONS Bid Price", Map.of("id", 1, "value", "428", "position", "top-center", "offsetX", 160, "offsetY", 315, "font_size", 15));
            
            
            //Bottom bid price set
            addField(fieldsToUpdate, "Bid Price", Map.of("id", 1, "value", "33", "position", "bottom-left", "offsetX", 125, "offsetY", 85, "font_size", 15));
            addField(fieldsToUpdate, "Bid Price", Map.of("id", 1, "value", "428", "position", "bottom-left", "offsetX", 125, "offsetY", 70, "font_size", 15));
            addField(fieldsToUpdate, "Amount blocked (in figures)", Map.of("id", 1, "value", "14124", "position", "bottom-left", "offsetX", 145, "offsetY", 55, "font_size", 15));

	        parser.processContent(page, new RenderListener() {
	            @Override
	            public void beginTextBlock() {}

	            @Override
	            public void renderText(TextRenderInfo renderInfo) {}

	            @Override
	            public void endTextBlock() {
	                for (String field : fieldsToUpdate.keySet()) {
	                    try {
	                        PdfContentByte over = stamper.getOverContent(page);
//	                        String font_path = "resources/fonts/RubikMonoOne-Regular.ttf";
	                        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
//	                        BaseFont bf = BaseFont.createFont(font_path, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

	                        for (Map<String, Object> fieldValue : fieldsToUpdate.get(field)) {
	                        	   float[] coordinates = getCoordinates(reader, page, fieldValue);
	                               float x = coordinates[0];
	                               float y = coordinates[1];

	                               over.saveState();

	                               // Enable anti-aliasing
	                               PdfGState gState = new PdfGState();
	                               gState.setRenderingIntent(PdfName.ANTIALIAS);
	                               over.setGState(gState);

	                               over.beginText();
	                               over.setFontAndSize(bf, Integer.parseInt(fieldValue.get("font_size").toString()));
	                               over.setTextMatrix(x, y);
	                               over.showText(fieldValue.get("value").toString());
	                               over.endText();
	                               over.restoreState();
	                               System.err.println("Updated '" + field + "' with '" + fieldValue.get("value") +
	                                       "' at (X: " + x + ", Y: " + y + ")");
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }

	            @Override
	            public void renderImage(ImageRenderInfo renderInfo) {}
	        });

	        stamper.close();
	        reader.close();

	        // Extract only the first page
	        PdfReader modifiedReader = new PdfReader(dest);
	        Document document = new Document();
	        PdfCopy copy = new PdfCopy(document, new FileOutputStream(finalDest));
	        document.open();
	        // Copy only the first page
	        copy.addPage(copy.getImportedPage(modifiedReader, 1));

	        document.close();
	        modifiedReader.close();

	        // Return the modified PDF for download
	        File file = new File(finalDest);

	        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=e_Form.pdf")
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource);

	    } catch (IOException | DocumentException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	private float[] getCoordinates(PdfReader reader, int page, Map<String, Object> fieldValue) {
	    float pageWidth = reader.getPageSize(page).getWidth();
	    float pageHeight = reader.getPageSize(page).getHeight();
	    String position = fieldValue.get("position").toString();
	    float offsetX = Float.parseFloat(fieldValue.get("offsetX").toString());
	    float offsetY = Float.parseFloat(fieldValue.get("offsetY").toString());

	    float x = 0;
	    float y = 0;

	    switch (position) {
	        case "top-right":
	            x = pageWidth - offsetX;
	            y = pageHeight - offsetY;
	            break;
	        case "top-left":
	            x = offsetX;
	            y = pageHeight - offsetY;
	            break;
	        case "bottom-right":
	            x = pageWidth - offsetX;
	            y = offsetY;
	            break;
	        case "bottom-left":
	            x = offsetX;
	            y = offsetY;
	            break;
	        case "top-center":
	            x = (pageWidth / 2) - (offsetX / 2);
	            y = pageHeight - offsetY;
	            break;
	        case "bottom-center":
	            x = (pageWidth / 2) - (offsetX / 2);
	            y = offsetY;
	            break;
	        case "center":
	            x = (pageWidth / 2) - (offsetX / 2);
	            y = (pageHeight / 2) - (offsetY / 2);
	            break;
	        case "left-center":
	            x = offsetX;
	            y = (pageHeight / 2) - (offsetY / 2);
	            break;
	        case "right-center":
	            x = pageWidth - offsetX;
	            y = (pageHeight / 2) - (offsetY / 2);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid position: " + position);
	    }

	    return new float[]{x, y};
	}

	public static List<String> splitTextIntoLines(String text, int maxCharsPerLine) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > maxCharsPerLine) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder();
            }
            currentLine.append(word).append(" ");
        }
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString().trim());
        }
        return lines;
    }
	
	  public static String addSpaces(String input) {
	        return String.join(" ", input.split(""));
	  }
	  
	// Function to allow multiple values for the same key
	    public void addField(Map<String, List<Map<String, Object>>> fieldsToUpdate, String key, Map<String, Object> value) {
	        fieldsToUpdate.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	    }

}
