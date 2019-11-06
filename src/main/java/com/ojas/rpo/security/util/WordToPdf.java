package com.ojas.rpo.security.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class WordToPdf {

	public static void convertWordtoPdf(String srcfileName, String targetFilePath) throws IOException {

		/*try {
			InputStream is = new FileInputStream(new File(srcfileName));
			XWPFDocument document = new XWPFDocument(is);
			OutputStream out = new FileOutputStream(targetFilePath);
			PdfOptions options = null;
			PdfConverter.getInstance().convert(document, out, options);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		try {
			if(srcfileName.endsWith(".doc")) {
				convertDocToPDF(srcfileName, targetFilePath);
			}
			if(srcfileName.endsWith(".docx")) {
				convertDocxToPDF(srcfileName, targetFilePath);
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void convertDocxToPDF(String srcfileName, String targetFilePath) throws Exception {
	      
        try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(srcfileName));
			document.createNumbering();
			PdfOptions options = PdfOptions.create();
			OutputStream out = new FileOutputStream(targetFilePath);
			System.out.println("PDF Converter Object : "+PdfConverter.getInstance());
			PdfConverter.getInstance().convert(document, out, options);
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
        
	}
	
	public static void convertDocToPDF(String srcfileName, String targetFilePath) throws Exception{

		try {
			WordprocessingMLPackage wordMLPackage = getMLPackage(new FileInputStream(srcfileName));
			Docx4J.toPDF(wordMLPackage, new FileOutputStream(targetFilePath));
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

	protected static WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception{
		WordprocessingMLPackage mlPackage = null;
		try {
			PrintStream originalStdout = System.out;
			
			//Disable stdout temporarily as Doc convert produces alot of output
			System.setOut(new PrintStream(new OutputStream() {
				public void write(int b) {
					//DO NOTHING
				}
			}));

			mlPackage = Doc.convert(iStream);
			
			System.setOut(originalStdout);
			
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return mlPackage;
	}

}
