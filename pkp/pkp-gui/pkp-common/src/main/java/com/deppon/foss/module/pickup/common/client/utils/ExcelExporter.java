package com.deppon.foss.module.pickup.common.client.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.base.util.define.NumberConstants;

/**
 * 瀵煎嚭Excel鏂囦欢
 */
public class ExcelExporter {
	
	private static final int NUM_175 = 175;

	private static final int NINE = 9;

	// 璁剧疆cell缂栫爜瑙ｅ喅涓枃楂樹綅瀛楄妭鎴柇
	// private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;

	// 瀹氬埗娴偣鏁版牸寮�
	private static String NUMBER_FORMAT = "#,##0.00";

	// 瀹氬埗鏃ユ湡鏍煎紡
	private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

	private OutputStream out = null;

	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;

	private HSSFRow row = null;

	/**
	 * 鍒濆鍖朎xcel
	 * 
	 */
	public ExcelExporter(OutputStream out) {
		this.out = out;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * 瀵煎嚭Excel鏂囦欢
	 * 
	 * @throws IOException
	 */
	public void export(int columns) throws IOException {
		//设置列宽
		for (int i = 0; i < columns; i++) {
			sheet.autoSizeColumn((short)i); 
		}
		workbook.write(out);
		out.flush();
	}

	/**
	 * 澧炲姞涓�
	 * 
	 * @param index
	 *            琛屽彿
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 鑾峰彇鍗曞厓鏍肩殑鍊�
	 * 
	 * @param index
	 *            鍒楀彿
	 */
	public String getCell(int index) {
		HSSFCell cell = this.row.getCell(index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_FORMULA:
				strExcelCell = "FORMULA ";
				break;
			case HSSFCell.CELL_TYPE_NUMERIC: {
				strExcelCell = String.valueOf(cell.getNumericCellValue());
			}
				break;
			case HSSFCell.CELL_TYPE_STRING:
				strExcelCell = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
			default:
				strExcelCell = "";
				break;
			}
		}
		return strExcelCell;
	}

	/**
	 * 璁剧疆鍗曞厓鏍�
	 * 
	 * @param index
	 *            鍒楀彿
	 * @param value
	 *            鍗曞厓鏍煎～鍏呭�
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * 璁剧疆鍗曞厓鏍�
	 * 
	 * @param index
	 *            鍒楀彿
	 * @param value
	 *            鍗曞厓鏍煎～鍏呭�
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 寤虹珛鏂扮殑cell鏍峰紡
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 璁剧疆cell鏍峰紡涓哄畾鍒剁殑娴偣鏁版牸寮�
		cell.setCellStyle(cellStyle); // 璁剧疆璇ell娴偣鏁扮殑鏄剧ず鏍煎紡
	}

	/**
	 * 璁剧疆鍗曞厓鏍�
	 * 
	 * @param index
	 *            鍒楀彿
	 * @param value
	 *            鍗曞厓鏍煎～鍏呭�
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell(index);
		//cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle= workbook.createCellStyle();    
		cellStyle.setWrapText(true);    
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	}
	
	public void setCellhead(int index, String value,int headHeight) {
		this.row.setHeightInPoints(headHeight);
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle= workbook.createCellStyle();
	    HSSFPalette palette = workbook.getCustomPalette();  //wb HSSFWorkbook对象
	    palette.setColorAtIndex((short) NINE, (byte) NumberConstants.NUMBER_250, (byte) NUM_175, (byte) NumberConstants.NUMBER_25);
		cellStyle.setFillBackgroundColor((short) NINE);// 设置背景色
		cellStyle.setFillForegroundColor((short) NINE);// 设置前景色
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();    
		font.setFontName("微软雅黑");    
		font.setFontHeightInPoints((short) NumberConstants.NUMBER_12);//设置字体大小    
		cellStyle.setFont(font);
		
		cell.setCellStyle(cellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	}

	/**
	 * 璁剧疆鍗曞厓鏍�
	 * 
	 * @param index
	 *            鍒楀彿
	 * @param value
	 *            鍗曞厓鏍煎～鍏呭�
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell(index);
		//cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 寤虹珛鏂扮殑cell鏍峰紡
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 璁剧疆cell鏍峰紡涓哄畾鍒剁殑鏃ユ湡鏍煎紡
		cell.setCellStyle(cellStyle); // 璁剧疆璇ell鏃ユ湡鐨勬樉绀烘牸寮�
	}

}