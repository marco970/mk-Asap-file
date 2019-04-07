package aSap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class Excell {
	
	public Excell()	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("hejYou");
		
		CalendarTest ct = new CalendarTest(2018, 2);
		
		
		//String date = ct.getDate(i);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("hejYou");
		//HSSFRow row = sheet.createRow(0);
		//HSSFCell cell = row.createCell(0);
		//cell.setCellValue("siemandero");
		
		HSSFRow row0 = sheet.createRow(0); 
		HSSFRow row = sheet.createRow(1);  
		HSSFCell cell = row.createCell(1);  
		HSSFCell cell2 = row.createCell(2);
		HSSFCell cell0 = row0.createCell(0);
		cell0.setCellValue("klmn");
		 HSSFCellStyle cs = workbook.createCellStyle();
		 cs.setWrapText(true);
		 cell.setCellStyle(cs);
         cell.setCellValue("Two cells have merged \nadding new line of text \nthis is the third line");  
         sheet.autoSizeColumn(2);  
         cell2.setCellValue("abc");
           //Merging cells by providing cell index  
         sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));  
         row.setHeightInPoints((3*sheet.getDefaultRowHeightInPoints())); 
         
         HSSFCell hssfCell = row.createCell(6);
         
         
         HSSFRichTextString richString = new HSSFRichTextString( "Hello, World!" );
         HSSFFont font = workbook.createFont();
         font.setFontHeightInPoints((short) 30);
         font.setFontName("IMPACT");
         font.setItalic(true);
         font.setColor(HSSFFont.COLOR_RED);

         richString.applyFont( 9, 13, font );
         hssfCell.setCellValue( richString );
		
		
			workbook.write(new FileOutputStream("test_xls.xls"));
			workbook.close(); 
		
		
		
	}

}
