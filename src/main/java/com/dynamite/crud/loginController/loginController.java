package com.dynamite.crud.loginController;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            String src = "D:/ArihantCapital/write-to-pdf/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf";  // Input PDF
            String dest = "D:/ArihantCapital/write-to-pdf/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF

            PdfReader reader = new PdfReader(src);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            int page = 1; // Process only page 1
            Map<String, List<Map<String, Object>>> fieldsToUpdate = new HashMap<>();
            
            addField(fieldsToUpdate, "Bid cum", Map.of("id", 1, "value", "30833057", "x", 55, "y", 10, "font_size", 12));
            addField(fieldsToUpdate, "Mr", Map.of("id", 1, "value", addSpaces("ANKIT JAIN"), "x", 80, "y", -2, "font_size", 8));
            
           

            // Function to add multiple values to the same key
            addField(fieldsToUpdate, "Bid Lot as advertised", Map.of("id", 1, "value", addSpaces("33"), "x", 55, "y", 30, "font_size", 8));
            addField(fieldsToUpdate, "Bid Lot as advertised", Map.of("id", 2, "value", "428", "x", 120, "y", 30, "font_size", 8));

            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("7666321805"), "x", 62, "y", -0, "font_size", 8));
            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("AYCPV8215G"), "x", 330, "y", -60, "font_size", 8));// BOTTOM PAN NUMBER
            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("IN30198311315434"), "x", 25, "y", -60, "font_size", 12));// DP ID /CL ID
            addField(fieldsToUpdate, "STD", Map.of("id", 1, "value", addSpaces("7666321805"), "x", 60, "y", -0, "font_size", 8));
            addField(fieldsToUpdate, "STD", Map.of("id", 1, "value", addSpaces("AYCPV8215G"), "x", 10, "y", 25, "font_size", 8));//PAN NUMBER TOP
            addField(fieldsToUpdate, "STD", Map.of("id", 1, "value", "Fourteen Thousand One Hundred Twenty Four", "x", -30, "y", 200, "font_size", 8));//PRICE IN WORD
            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("IN30198311315434"), "x", 5, "y", -418, "font_size", 8));//3. BIDDER’S DEPOSITORY ACCOUNT DETAILS 
            addField(fieldsToUpdate, "Blocked", Map.of("id", 1, "value", addSpaces("14124"), "x", 70, "y", 0, "font_size", 8));
            addField(fieldsToUpdate, "Blocked", Map.of("id", 1, "value", addSpaces("38"), "x", 35, "y", -23, "font_size", 8));
            addField(fieldsToUpdate, "Blocked", Map.of("id", 1, "value", addSpaces("428"), "x", 35, "y", -11, "font_size", 8));
            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("ANKIT JAIN"), "x", 340, "y", 40, "font_size", 8));// Name of Sole / First Bidder
            addField(fieldsToUpdate, "Mobile", Map.of("id", 1, "value", addSpaces("14124"), "x", 85, "y", -40, "font_size", 8));
            
            addField(fieldsToUpdate, "SCSB BRANCH", Map.of("id", 1, "value", addSpaces("14124"), "x", -50, "y", 235, "font_size", 8));//Amount blocked TOP   
            List<String> addressLines = splitTextIntoLines("303 OM RESIDENCY SECTOR 5 PLOT 10 ULWE NAVI MUMBAI 410205", 30);
            int yOffset = 0;
            for (String line : addressLines) {
                addField(fieldsToUpdate, "Address", Map.of("id", 1, "value", line, "x", 30, "y", 0 - yOffset, "font_size", 7));
                yOffset -= 10;  // Move down for the next line
            }
            
            Map<String, float[]> fieldCoordinates = new HashMap<>();

            parser.processContent(page, new RenderListener() {
                @Override
                public void beginTextBlock() {}

                @Override
                public void renderText(TextRenderInfo renderInfo) {
                    String text = renderInfo.getText();
                    if (text != null) {
                        for (String field : fieldsToUpdate.keySet()) {
                            if (text.contains(field)) {
                                float x = renderInfo.getBaseline().getStartPoint().get(0);
                                float y = renderInfo.getBaseline().getStartPoint().get(1);
                                fieldCoordinates.put(field, new float[]{x, y});
                                System.out.println("Found '" + field + "' at (X: " + x + ", Y: " + y + ")");
                            }
                        }
                    }
                }

                @Override
                public void endTextBlock() {
                    for (String field : fieldsToUpdate.keySet()) {
                        if (fieldCoordinates.containsKey(field)) {
                            try {
                                float[] coords = fieldCoordinates.get(field);
                                PdfContentByte over = stamper.getOverContent(page);
                                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

                                for (Map<String, Object> fieldValue : fieldsToUpdate.get(field)) {
                                    float x = coords[0] + Integer.parseInt(fieldValue.get("x").toString());
                                    float y = coords[1] - Integer.parseInt(fieldValue.get("y").toString());

                                    over.beginText();
                                    over.setFontAndSize(bf, Integer.parseInt(fieldValue.get("font_size").toString()));
                                    over.setTextMatrix(x, y);
                                    over.showText(fieldValue.get("value").toString());
                                    over.endText();

                                    System.out.println("Updated '" + field + "' with '" + fieldValue.get("value") +
                                            "' at (X: " + x + ", Y: " + y + ")");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void renderImage(ImageRenderInfo renderInfo) {}
            });

            stamper.close();
            reader.close();
            System.out.println("✅ PDF updated successfully: " + dest);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return "Hello";
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

 
	
	@RequestMapping(value = "/pageoness", method = RequestMethod.POST)
    public String pageOnesss() throws DocumentException, IOException {
        try {
            String src = "D:/ArihantCapital/write-to-pdf/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf";  // Input PDF
            String dest = "D:/ArihantCapital/write-to-pdf/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF

            PdfReader reader = new PdfReader(src);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            int page = 1; // Process only page 1
            Map<String, List<Map<String, Object>>> fieldsToUpdate = new HashMap<>();

            // Storing values as list of maps
            fieldsToUpdate.put("Bid cum", Arrays.asList(
                    Map.of("id", 1, "value", "30833057", "x", 55, "y", 2, "font_size", 18)
            ));

            fieldsToUpdate.put("SCSB BRANCH", Arrays.asList(
                    Map.of("id", 1, "value", "12345", "x", 10, "y", 20, "font_size", 8)
            ));


            fieldsToUpdate.put("Mr", Arrays.asList(
                    Map.of("id", 1, "value", addSpaces("ANSH JAIN"), "x", 80, "y", -10, "font_size", 8)
            ));
            
        
            fieldsToUpdate.put("Address", Arrays.asList(
                    Map.of("id", 1, "value", "MADHYA PRADESH RAT L AM 4 5 7 0 0 1", "x", 40, "y", -12, "font_size", 8)
            ));

            fieldsToUpdate.put("Mobile", Arrays.asList(
                    Map.of("id", 1, "value", addSpaces("7666321805"), "x", 62, "y", -10, "font_size", 8)
            ));

            fieldsToUpdate.put("STD", Arrays.asList(
                    Map.of("id", 1, "value", addSpaces("7666321805"), "x", 62, "y", -11, "font_size", 12)
            ));

            fieldsToUpdate.put("Amount blocked", Arrays.asList(
                    Map.of("id", 1, "value", addSpaces("14124"), "x", 95, "y", -8, "font_size", 8)
            ));
            


            fieldsToUpdate.put("No. of Equity Shares", Arrays.asList(
                    Map.of("id", 1, "value", "33", "x", 55, "y", -9, "font_size", 8)
            ));
            
            fieldsToUpdate.put("Bid Price", Arrays.asList(
                    Map.of("id", 1, "value", "428", "x", 55, "y", -9, "font_size", 8)
            ));

            fieldsToUpdate.put("Blocked", Arrays.asList(
                    Map.of("id", 1, "value", addSpaces("14124"), "x", 70, "y", -9, "font_size", 8)
            ));

            fieldsToUpdate.put("in words", Arrays.asList(
                    Map.of("id", 1, "value", "Fourteen Thousand One Hundred Twenty Four", "x", 35, "y", -9, "font_size", 8)
            ));
            
            
            fieldsToUpdate.put("Acknowledgement", Arrays.asList(
                    Map.of("id", 1, "value", "ANSH JAIN", "x", -25, "y", -40, "font_size", 8)
            ));

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
                                
                                 // Offset for better alignment
//                                over.setTextMatrix(x + 50, y);
                                // Extract and print the correct value
                                List<Map<String, Object>> fieldValues = (List<Map<String, Object>>) fieldsToUpdate.get(field);
                                if (fieldValues != null && !fieldValues.isEmpty()) {
                                	over.setFontAndSize(bf, Integer.parseInt(fieldValues.get(0).get("font_size").toString()));
                                	over.setTextMatrix(x + Integer.parseInt(fieldValues.get(0).get("x").toString()), y - Integer.parseInt(fieldValues.get(0).get("y").toString()));
                                    String valueToPrint = fieldValues.get(0).get("value").toString();
                                    over.showText(valueToPrint);
                                    System.out.println("Updated '" + field + "' with '" + valueToPrint + "' at (X: " + x + ", Y: " + y + ")");
                                }

                                over.endText();
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
        return "Hello";
    }
	
	  public static String addSpaces(String input) {
	        return String.join(" ", input.split(""));
	  }
	  
	// Function to allow multiple values for the same key
	    public void addField(Map<String, List<Map<String, Object>>> fieldsToUpdate, String key, Map<String, Object> value) {
	        fieldsToUpdate.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	    }

	
	@RequestMapping(value = "/pageoneWorking", method = RequestMethod.POST)
	public String pageOneWorking() throws DocumentException, IOException {
	    try {
	        String src = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM.pdf";  // Input PDF
	        String dest = "D:/Pdf/HEXAWARE_TECHNOLOGIES_LIMITED_SyndicateASBA Form_R_FullForm_JM1.pdf"; // Output PDF

	        PdfReader reader = new PdfReader(src);
	        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
	        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

	        int page = 1; // Process only page 1
	        Map<String, Object> fieldsToUpdate = new HashMap<>(); // Store multiple fields

	        fieldsToUpdate.put("Bid cum", Arrays.asList(
	        	    Map.of("id", 1, "value", "30833057", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("SCSB BRANCH", Arrays.asList(
	        	    Map.of("id", 1, "value", "12345", "x", 10, "y", 20)
	        	));
	        fieldsToUpdate.put("Bank Name", Arrays.asList(
	        	    Map.of("id", 1, "value", "XYZ Bank Ltd.", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Mr", Arrays.asList(
	        	    Map.of("id", 1, "value", "ANSH JAIN", "x", 45, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Address", Arrays.asList(
	        	    Map.of("id", 1, "value",  "MADHYA PRADESH RAT L AM 4 5 7 0 0 1", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Mobile", Arrays.asList(
	        	    Map.of("id", 1, "value",  "7666321805", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("STD", Arrays.asList(
	        	    Map.of("id", 1, "value",  "7666321805", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Amount blocked", Arrays.asList(
	        	    Map.of("id", 1, "value",  "14124", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("No. of Equity Shares", Arrays.asList(
	        	    Map.of("id", 1, "value",  "33", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Bid Price", Arrays.asList(
	        	    Map.of("id", 1, "value",  "428", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("Blocked", Arrays.asList(
	        	    Map.of("id", 1, "value",  "14124", "x", 10, "y", 20)
	        	));
	        
	        fieldsToUpdate.put("in words", Arrays.asList(
	        	    Map.of("id", 1, "value",  "Fourteen Thousand One Hundred Twenty Four", "x", 10, "y", 20)
	        	));
	        
//	        fieldsToUpdate.put("DEPOSITORY ACCOUNT", "I N 3 0 1 9 8 3 1 1 3 1 5 4 3 4");
	         
	        
	        
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
	                            over.setFontAndSize(bf, 8);
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
