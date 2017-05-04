package com.deppon.foss.module.pickup.common.client.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.client.widget.excel.ExcelImporter;
/**
*******************************************
* <b style="font-family:寰蒋闆呴粦"><small>Description:excel鏂囦欢瀵煎叆瀵煎嚭宸ュ叿绫�/small></b>   </br>
* <b style="font-family:寰蒋闆呴粦"><small>HISTORY</small></b></br>
* <b style="font-family:寰蒋闆呴粦"><small>
*  ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:寰蒋闆呴粦,font-size:70%"> 
* 1   2011-6-11   璋紶鍥�      鏂板
* </div>  
********************************************
 */
public final class ExcelUtils {
	
	private ExcelUtils(){
		
	}
	
	/**
	 * 
	 * @Title:exportCSV
	 * @Description:鎶奐Table鐨勫唴瀹瑰鍑哄埌鏂囦欢涓�
	 * @param @param table
	 * @param @param file
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void exportCSV(JTable table, File file) throws IOException {
		if (table == null){
			throw new IllegalArgumentException("table == null");
		}
		if (file == null){
			throw new IllegalArgumentException("file == null");
		}
			
		exportCSV((DefaultTableModel) table.getModel(), new FileWriter(file));
	}
	/**
	 * 
	 * @Title:exportCSV
	 * @Description:鎶奃efaultTableModel涓殑鍐呭瀵煎嚭鍒癢riter涓�
	 * @param @param model
	 * @param @param writer
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void exportCSV(DefaultTableModel model, Writer writer) throws IOException {
		
		try {
			
			// 鎬昏鏁帮紝涓嶅寘鎷垪澶�
			int rows = model.getRowCount();
			// 鍒楁暟
			int columns = model.getColumnCount();
			// 瀵煎嚭鏍囬
			for (int column = 0; column < columns; column ++) {
				if (column > 0){
					writer.write(",");
				}
				writer.write(filterComma(model.getColumnName(column)));
			}
			// 瀵煎嚭鎵�湁琛�
			for (int row = 0; row < rows; row ++) {
				writer.write("\n");
				for (int column = 0; column < columns; column ++) {
					if (column > 0){
						writer.write(",");
					}
					writer.write(filterComma(model.getValueAt(row, column)));
				}
			}
		} finally {
			writer.close();
		}
	}
	/**
	 * 
	 * @Title:importCSV
	 * @Description:瑙ｆ瀽鏂囦欢鍐呭锛屾妸鏂囦欢鍐呭瀵煎叆鍒癑Table涓�
	 * @param @param table
	 * @param @param file
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void importCSV(JTable table, File file) throws IOException {
		if (table == null){
			throw new IllegalArgumentException("table is not allowed null");
		}
			
		if (file == null){
			throw new IllegalArgumentException("file is not allowed null");
		}
		importCSV((DefaultTableModel) table.getModel(), new FileReader(file));
	}
	/**
	 * 
	 * @Title:importCSV
	 * @Description:璇诲彇Reader涓殑鍐呭锛屾妸鍐呭瀵煎叆鍒癉efaultTableModel涓�
	 * @param @param model
	 * @param @param reader
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void importCSV(DefaultTableModel model, Reader reader) throws IOException {
		if (model == null){
			throw new IllegalArgumentException("model == null");
		}
		if (reader == null){
			throw new IllegalArgumentException("reader == null");
		}
		try {
			
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			if (line == null) {
				return;
			}
			String[] headers = line.split(",");
			int columns = model.getColumnCount();
			int len = Math.min(columns, headers.length);
			for (int column = 0; column < len; column ++) {
				if (! model.getColumnName(column).equals(headers[column])) {
					throw new IllegalStateException("Csv.columnHeaders != Table.columnHeaders");
				}
			}
			while ((line = br.readLine()) != null) {
				String[] cells = line.split(",");
				if (cells.length != columns) {
					String[] dest = new String[columns];
					System.arraycopy(cells, 0, dest, 0, Math.min(columns, cells.length));
					cells = dest;
				}
				model.addRow(cells);
			}
		} finally {
			reader.close();
		}
	}
	/**
	 * 
	 * @Title:filterComma
	 * @Description:杩囨护鎺塁SV鏂囦欢涓殑閫楀彿
	 * @param @param value
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String filterComma(Object value) {
		if (value == null) {
			return "";
		}
		String str = value.toString();
		if (str.contains(",")) {
			return "\"" + value + "\"";
		}
		return str;
	}
	/**
	 * 
	 * @Title:exportExcel
	 * @Description:鎶奐Table涓殑鍐呭浠xcel鏍煎紡瀵煎嚭鍒版枃浠朵腑
	 * @param @param table
	 * @param @param file
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void exportExcel(JTable table, File file,Integer[] notExportCol) throws IOException {
		if (table == null){
			throw new IllegalArgumentException("table does not allow null");
		}
			
		if (file == null){
			throw new IllegalArgumentException("file does not allow null");
		}
		exportExcel((DefaultTableModel) table.getModel(), new FileOutputStream(file), notExportCol);
	}
	/**
	 * 
	 * @Title:exportExcel
	 * @Description:鎶奃efaultTableModel涓殑鍐呭浠xcel鏍煎紡瀵煎嚭鍒拌緭鍑烘祦涓�
	 * @param @param model
	 * @param @param output
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void exportExcel(DefaultTableModel model, OutputStream output, Integer... notExportCol) throws IOException {
		
		if (model == null){
			throw new IllegalArgumentException("model does not allow  null");
		}
		if (output == null){
			throw new IllegalArgumentException("outputstream does not allow null");
		}
		if(notExportCol == null){
			notExportCol =new Integer[]{};
		}
		try {
			
			ExcelExporter exporter = new ExcelExporter(output);
			// 鎬昏鏁帮紝涓嶅寘鎷垪澶�
			int rows = model.getRowCount();
			// 鍒楁暟
			int columns = model.getColumnCount();
			// 瀵煎嚭鏍囬
			exporter.createRow(0);
			List<Integer> notColList =Arrays.asList(notExportCol);
			int colcount = 0;
			for (int column = 0; column < columns; column ++) {
				//此列不导出
				if(notColList.contains((Integer)column)){
					++colcount;
					continue;
				}
				exporter.setCellhead(column-colcount, model.getColumnName(column), NumberConstants.NUMBER_20);
			}
			// 瀵煎嚭鎵�湁琛�
			for (int row = 1; row <= rows; row ++) {
				exporter.createRow(row);
				colcount = 0;
				for (int column = 0; column < columns; column ++) {
					//此列不导出
					if(notColList.contains((Integer)column)){
						++colcount;
						continue;
					}
					Object value = model.getValueAt(row - 1, column);
					int columnIndex = column-colcount;
					if (value == null) {
						exporter.setCell(columnIndex, "");
					} else if (value instanceof Double || value instanceof Float) {
						exporter.setCell(columnIndex, ((Number) value).doubleValue());
					} else if (value instanceof Number) {
						exporter.setCell(columnIndex, ((Number) value).intValue());
					} else if (value instanceof Calendar) {
						exporter.setCell(columnIndex, (Calendar) value);
					} else if (value instanceof Date) {
						Calendar calendar =  Calendar.getInstance();
						calendar.setTime((Date) value);
						exporter.setCell(columnIndex, calendar);
					} else {
						exporter.setCell(columnIndex, value.toString());
					}
				}
			}
			exporter.export(columns);
		} finally {
			output.close();
		}
	}
	/**
	 * 
	 * @Title:importExcel
	 * @Description:瑙ｆ瀽excel鏂囦欢涓殑鍐呭锛屽鍏ュ埌JTable瀵硅薄涓�
	 * @param @param table
	 * @param @param file
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void importExcel(JTable table, File file) throws IOException {
		if (table == null){
			throw new IllegalArgumentException("table does not allow  null");
		}
			
		if (file == null){
			throw new IllegalArgumentException("file does not allow null");
		}
		importExcel((DefaultTableModel) table.getModel(), new FileInputStream(file));
	}
	/**
	 * 
	 * @Title:importExcel
	 * @Description:璇诲彇杈撳叆娴佷腑鐨勬暟鎹紝瀵煎叆鍒癉efaultTableModel瀵硅薄涓�
	 * @param @param model
	 * @param @param input
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	public static void importExcel(DefaultTableModel model, InputStream input) throws IOException {
		if (model == null){
			throw new IllegalArgumentException("model does not allow  null");
		}
			
		if (input == null){
			throw new IllegalArgumentException("file does not allow null");
		}
			
		try {
			ExcelImporter importer = new ExcelImporter(input);
			// 鎬昏鏁帮紝鍖呮嫭鍒楀ご
			int rows = importer.getRowCount();
			if (rows == 0){
				return;
			}
			int columns = model.getColumnCount();
			// 妫�煡绗竴琛屽垪澶存槸鍚︿竴鑷�
			String[] headers = importer.readExcelLine(0);
			if (headers == null){
				return;
			}
			int len = Math.min(columns, headers.length);
			for (int column = 0; column < len; column ++) {
				if (! model.getColumnName(column).equals(headers[column])) {
					throw new IllegalStateException("Excel.columnHeaders != Table.columnHeaders");
				}
			}
			// 璇诲彇鎵�湁琛�
			for (int row = 1; row < rows; row ++) {
				String[] cells = importer.readExcelLine(row);
				if (cells.length != columns) {
					String[] dest = new String[columns];
					System.arraycopy(cells, 0, dest, 0, Math.min(columns, cells.length));
					cells = dest;
				}
				model.addRow(cells);
			}
		} finally {
			input.close();
		}
	}

}

