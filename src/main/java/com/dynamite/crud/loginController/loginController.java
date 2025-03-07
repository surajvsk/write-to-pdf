package com.dynamite.crud.loginController;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.*;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;




@RestController
public class loginController {

//  String src = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf";   // Path to the PDF form
//  String dest = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output file with filled data
 

	
	@RequestMapping(value = "/pageone", method = RequestMethod.POST)
	public String pageOne() throws DocumentException, IOException {
	    try {
	        String src = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf";  // Input PDF
	        String dest = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF

	        PdfReader reader = new PdfReader(src);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

	        int page = 1; // Process only page 1
	        Map<String, Object> fieldsToUpdate = new HashMap<>(); // Store multiple fields

	        // 🎯 Define fields to search and their new values
	        fieldsToUpdate.put("Bid cum", "30833057");
	        fieldsToUpdate.put("SCSB BRANCH", "12334");
	        fieldsToUpdate.put("Applicant Name", "John Doe");
	        fieldsToUpdate.put("PAN", "ABCDE1234F");
	        fieldsToUpdate.put("Bank Name", "XYZ Bank Ltd.");
	        fieldsToUpdate.put("Mr", "ANSH JAIN");
	        fieldsToUpdate.put("Address", "MADHYA PRADESH RAT L AM 4 5 7 0 0 1");
	        fieldsToUpdate.put("Mobile", "7666321805");
	        fieldsToUpdate.put("STD", "7666321805");
	        fieldsToUpdate.put("Amount blocked", "14124");
	        fieldsToUpdate.put("No. of Equity Shares", "33");
	        fieldsToUpdate.put("Bid Price", "428");
	        fieldsToUpdate.put("Blocked", "14124");
	        
	        
	        Map<String, float[]> fieldCoordinates = new HashMap<>(); // Store positions dynamically

	        parser.processContent(page, new RenderListener() {
	            StringBuilder fullText = new StringBuilder();

	            @Override
	            public void beginTextBlock() {
	                fullText.setLength(0); // Clear text buffer for new blocks
	            }

	            @Override
	            public void renderText(TextRenderInfo renderInfo) {
	                String text = renderInfo.getText();
	                if (text != null) {
	                    fullText.append(text).append(" "); // Append text with space
	                    }

	                // Check for all fields in the PDF
	                for (String field : fieldsToUpdate.keySet()) {
	                	System.out.println("field::::::::"+text);
	                    if (text != null && text.contains(field)) {
	                        float x = renderInfo.getBaseline().getStartPoint().get(0); // X-Coordinate
	                        float y = renderInfo.getBaseline().getStartPoint().get(1); // Y-Coordinate
	                        fieldCoordinates.put(field, new float[]{x, y});
	                        System.out.println("Found '" + field + "' at (X: " + x + ", Y: " + y + ")");
	                    }
	                 
	                }
	            }

	            @Override
	            public void endTextBlock() {
	                String extractedText = fullText.toString().trim().replaceAll("\\s+", " ");
//	                System.out.println("Extracted Text: " + extractedText);

	                // Add text for all detected fields
	                for (String field : fieldsToUpdate.keySet()) {
	                    if (extractedText.contains(field) && fieldCoordinates.containsKey(field)) {
	                        try {
	                            float[] coords = fieldCoordinates.get(field);
	                            float x = coords[0];
	                            float y = coords[1] - 10; // Adjust position
	                            PdfContentByte over = stamper.getOverContent(page);
	                            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	                            over.beginText();
	                            over.setFontAndSize(bf, 12);
	                            over.setTextMatrix(x + 50, y); // Offset for better alignment
	                            over.showText(fieldsToUpdate.get(field).toString()); // Insert new value
	                            over.endText();
	                            System.out.println("Updated '" + field + "' with '" + fieldsToUpdate.get(field) + "' at (X: " + x + ", Y: " + y + ")");
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            }

	            @Override
	            public void renderImage(ImageRenderInfo renderInfo) {
	            }
	        });

	        stamper.close();
	        reader.close();
	        System.out.println("✅ PDF updated successfully: " + dest);
	    } catch (IOException | DocumentException e) {
	        e.printStackTrace();
	    }
	    return  "Hello";
	}



	
	@RequestMapping(value = "/_pageone", method = RequestMethod.POST)
	public String _pageOne() throws DocumentException, IOException {
	    try {
	        String src = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf"; // Input PDF
	        String dest = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF

	        PdfReader reader = new PdfReader(src);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

	        int page = 1; // Only process page 1

	        parser.processContent(page, new RenderListener() {
	            @Override
	            public void renderText(TextRenderInfo renderInfo) {
	                String text = renderInfo.getText();
	                System.out.println("text:::::::"+text);
	                if (text != null && text.contains("Bid cum")) { 
	                	// Target text
	                    float x = renderInfo.getBaseline().getStartPoint().get(0);
	                    float y = renderInfo.getBaseline().getStartPoint().get(1);
	                    System.out.println("Found on Page " + page + " at (X: " + x + ", Y: " + y + ")");
	                    try {
	                        PdfContentByte over = stamper.getOverContent(page);
	                        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	                        over.beginText();
	                        over.setFontAndSize(bf, 12);
	                        over.setTextMatrix(x + 50, y - 10); // Adjust position
	                        over.showText("30833057"); // Text to write Bid Application Number
	                        over.endText();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }

	            @Override
	            public void beginTextBlock() {}

	            @Override
	            public void endTextBlock() {}

	            @Override
	            public void renderImage(ImageRenderInfo renderInfo) {}
	        });

	        stamper.close();
	        reader.close();

	        System.out.println("PDF updated successfully: " + dest);
	    } catch (IOException | DocumentException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login() throws DocumentException, IOException{

        try {
            String src = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf"; // Input PDF
            String dest = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF
            
            PdfReader reader = new PdfReader(src);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                final int page = i;
                parser.processContent(i, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo renderInfo) {
                        String text = renderInfo.getText();
//                        System.err.println("text::::::"+text);
                        if (text != null && text.contains("Bid cum ")) {
                            float x = renderInfo.getBaseline().getStartPoint().get(0);
                            float y = renderInfo.getBaseline().getStartPoint().get(1);
                            System.out.println("Found on Page " + page + " at (X: " + x + ", Y: " + y + ")");

                            try {
                                PdfContentByte over = stamper.getOverContent(page);
                                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                over.beginText();
                                over.setFontAndSize(bf, 12);
                                over.setTextMatrix(x + 50, y); // Adjust position slightly right
                                over.showText("123456789"); // Text to write
                                over.endText();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void beginTextBlock() {}

                    @Override
                    public void endTextBlock() {}

                    @Override
                    public void renderImage(ImageRenderInfo renderInfo) {}
                });
            }

            stamper.close();
            reader.close();

            System.out.println("PDF updated successfully: " + dest);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
		return null;
    }
        
	
}
