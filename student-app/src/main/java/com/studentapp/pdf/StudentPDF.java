package com.studentapp.pdf;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.studentapp.response.StudentResponse;

public class StudentPDF {

	 private List<StudentResponse> studentList;
     
	    public StudentPDF(List<StudentResponse> studentList) {
	        this.studentList = studentList;
	    }
	 
	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	      
	        cell.setPadding(5);
	        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
	         
	        Font font = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC);
	       
	         
	        cell.setPhrase(new Phrase("Student ID", font));
	        cell.setVerticalAlignment(Element.ALIGN_LEFT);
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Name", font));
	        cell.setVerticalAlignment(Element.ALIGN_LEFT);
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Age", font));
	        cell.setVerticalAlignment(Element.ALIGN_LEFT);
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Branch", font));
	        cell.setVerticalAlignment(Element.ALIGN_LEFT);
	        table.addCell(cell);
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (StudentResponse student : studentList) {
	            table.addCell(String.valueOf(student.getStudentId()));
	            table.addCell(student.getName());
	            table.addCell(student.getAge());
	            table.addCell(student.getBranch());
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
	        
	        font.setSize(18);
	 
	         
	        Paragraph p = new Paragraph("List of Students", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.0f, 2.5f, 2.5f, 1.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }
}
