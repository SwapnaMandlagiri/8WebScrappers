package com.tarladala.utilities;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	
		public String path;
		FileInputStream file;
		XSSFWorkbook book ;
		XSSFSheet sheet;
		Row row;
		Cell cell;
		public ExcelReader(String path) {
			this.path=path;
		}
		
		public int getrowcount(String Sheetname) throws IOException {
				 file = new FileInputStream(path);
			      book = new XSSFWorkbook(file);
				sheet = book.getSheet(Sheetname);
				int rowcount= sheet.getLastRowNum();
				book.close();
				file.close();
				return rowcount;
		}
		
		public int getcellcount(String Sheetname, int rownum) throws IOException {
			file = new FileInputStream(path);
			  book = new XSSFWorkbook(file);
				sheet = book.getSheet(Sheetname);
				Row row = sheet.getRow(rownum);
				int cellcount= row.getLastCellNum();
				book.close();
				file.close();
			return cellcount;
			
		}
		
		public String getdata(String Sheetname, int rownum, int cellnum) throws IOException {
			file = new FileInputStream(path);
			  book = new XSSFWorkbook(file);
				sheet = book.getSheet(Sheetname);
				 row = sheet.getRow(rownum);
				 cell = row.getCell(cellnum);
				 DataFormatter dataf = new DataFormatter();
				String data =dataf.formatCellValue(cell);
			return data;
			
		}
		
		public String setcelldata(String Sheetname, int rownum, int cellnum, String data) throws IOException {
			file = new FileInputStream(path);
			 book = new XSSFWorkbook(file);
			 sheet = book.getSheet(Sheetname);
			 if(sheet.getRow(rownum)==null) 
				 sheet.createRow(rownum);
			  row = sheet.getRow(rownum);
			 cell = row.createCell(cellnum);
			 cell.setCellValue(data);
			 FileOutputStream fo = new FileOutputStream(path);
			 book.write(fo);
			 book.close();
			 file.close();
			 fo.close();
			return data;
			
		}

}