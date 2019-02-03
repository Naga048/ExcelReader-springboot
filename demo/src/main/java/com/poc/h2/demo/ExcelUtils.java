package com.poc.h2.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	

	public static List<KRIList> getKriNames(InputStream in){
		List<KRIList> kriList= new ArrayList<KRIList>();
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(in);
			XSSFSheet  sheet= workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();	
				KRIList kriNames = new KRIList();
				int i=0;
				kriNames.setKriName(getCellValue(row.getCell(i)).toString());i++;
				kriNames.setKriType(getCellValue(row.getCell(i)).toString());i++;
				kriNames.setKriAliasName(getCellValue(row.getCell(i)).toString());i++;
				kriNames.setSheetNo(getCellValue(row.getCell(i)).toString());
				kriList.add(kriNames);
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kriList;
		
	}
	
	private static Object getCellValue(Cell cell)
	{
		Object obj = null;
		if(cell.CELL_TYPE_STRING==cell.getCellType())
			obj = cell.getStringCellValue();
		if(cell.CELL_TYPE_NUMERIC==cell.getCellType())
			obj = cell.getNumericCellValue();
		
		return obj;
		
	}
	
	public static CellStyle customCellStyle(CellStyle cellStyle)
	{
		
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}
	
	public static <T> List<List<T>> split(List<T> list, int numberOfParts) {
	      List<List<T>> numberOfPartss = new ArrayList<>(numberOfParts);
	      int size = list.size();
	      int sizePernumberOfParts = (int) Math.ceil(((double) size) / numberOfParts);
	      int leftElements = size;
	      int i = 0;
	      while (i < size && numberOfParts != 0) {
	          numberOfPartss.add(list.subList(i, i + sizePernumberOfParts));
	          i = i + sizePernumberOfParts;
	          leftElements = leftElements - sizePernumberOfParts;
	          sizePernumberOfParts = (int) Math.ceil(((double) leftElements) / --numberOfParts);
	       }
	       return numberOfPartss;
	   }

	public static void workSheetHeaders(Row newRow,Row sourceRow,XSSFCellStyle  newCellStyle)
	{

    	for(int j=0;j<sourceRow.getLastCellNum();j++)
    	{
    		Cell oldCell = sourceRow.getCell(j);
    		Cell newCell = newRow.createCell(j);
    		if(oldCell==null)
    			newCell.setCellValue("");
    		else {
    		ExcelUtils.customCellStyle(newCellStyle);
    		if(Cell.CELL_TYPE_STRING==oldCell.getCellType())
    			newCell.setCellValue(oldCell.getStringCellValue());
    		newCell.setCellStyle(newCellStyle);
    		}
    		   	    		
    	}
	}

}
