/**   
* @Title: ExcelUtil.java 
* @Package com.deppon.foss.module.pickup.creatingexp.client.common 
* @Description: 封装table数据导出到excel工具类
* @author 270293   
* @date 2015-7-16 上午9:22:16 
* @version V1.0   
*/
package com.deppon.foss.module.pickup.creatingexp.client.common;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.deppon.foss.base.util.define.NumberConstants;
  
/** 
 * @ClassName: ExcelUtil 
 * @Description: 封装table数据导出到excel工具类 
 * @author 270293-foss-zhangfeng 
 * @date 2015-7-16 上午9:22:16 
 *  
 */
public class ExcelUtil {
	
    /** 
     * 导出Excel的方法 
     *  
     * @param object 可以是servlet中的httpsession
     *  
     * @param excelContent 
     *            数据体集合，集合内放置String数组 
     * @param columnNames 
     *            数据列的头，使用数组 
     * @param titleName 
     *            要导出的文件名称 
     * @param out 
     *            导出流，Web中使用response得到 
     * @throws IOException 
     *            
     */  
  
	 public static void create(Object obj, List<?> excelContent,  String[] columnNames, String titleName, OutputStream out) throws IOException {  
	        //1.建立新HSSFWorkbook对象  
	    	HSSFWorkbook wb = new HSSFWorkbook();
	    	//2.建立新的sheet对象
	        HSSFSheet sheet = wb.createSheet(new SimpleDateFormat("yyyy-MM-dd").format(new Date())); 
	        //3.设置标题格式 
	        Region region = new Region();  
	        region.setColumnFrom((short) 0);  
	        region.setColumnTo((short) (columnNames.length - 1));  
	        region.setRowFrom((short) 0);  
	        region.setRowTo((short) 0);  
	        sheet.addMergedRegion(region);  
	        //4.标题列列的字体样式  
	        HSSFFont titleFont = wb.createFont();  
	        titleFont.setColor(HSSFFont.COLOR_RED);  
	        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        titleFont.setFontHeight((short) NumberConstants.NUMBER_300);  
	  
	        // 5.选择列的字体样式  
	        HSSFFont headerFont = wb.createFont();  
	        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        headerFont.setColor(HSSFFont.BOLDWEIGHT_NORMAL);  
	        HSSFPrintSetup printSetup = sheet.getPrintSetup();  
	        printSetup.setLandscape(true);  
	        sheet.setFitToPage(true);  
	        sheet.setHorizontallyCenter(true);  
	        sheet.createFreezePane(0, 1);  
	        sheet.setAutobreaks(true);  
	        sheet.setDefaultColumnWidth((short) 13.5);  
	        printSetup.setFitHeight((short) NumberConstants.NUMBER_100);  
	        printSetup.setFitWidth((short) NumberConstants.NUMBER_180);  
	        // 6.标题列样式  
	        HSSFCellStyle titlestyle = wb.createCellStyle();  
	        titlestyle.setFont(titleFont);  
	        titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        /*titlestyle.setFillForegroundColor(HSSFColor.WHITE.index);  
	        titlestyle.setFillPattern(HSSFCellStyle.SQUARES);  
	        titlestyle.setLeftBorderColor(HSSFColor.BLACK.index);  
	        titlestyle.setRightBorderColor(HSSFColor.BLACK.index);  
	        titlestyle.setTopBorderColor(HSSFColor.BLACK.index);  
	        titlestyle.setBottomBorderColor(HSSFColor.BLACK.index);  */
	  
	        titlestyle.setWrapText(true);  
	        // 7.选择列样式  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setFont(headerFont);  
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	        style.setHidden(true);  
	        // 8.内容列的样式  
	        HSSFCellStyle style2 = wb.createCellStyle();  
	        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	        style2.setDataFormat(wb.createDataFormat().getFormat("0.00"));  
	        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	        // 9.写入标题  
	        HSSFRow titleRow = null;  
	  
	        if (!titleName.equals("")) {  
	            titleRow = sheet.createRow((short) 0);  
	            titleRow.setHeightInPoints(30.120f);  
	            HSSFCell titlecell = titleRow.createCell((short) 0);// 标题  
	            titlecell.setCellStyle(titlestyle);  
	            titlecell.setCellValue(titleName);  
	        }  
	  
	        // 10.创建列名称  
	        HSSFRow headRow = sheet.createRow((short) 1);//  
	        headRow.setHeightInPoints(20.120f);  
	  
	        for (int i = 0; i < columnNames.length; i++) {  
	            HSSFCell cell = headRow.createCell((short) i);  
	            cell.setCellStyle(style);  
	            cell.setCellValue(columnNames[i]);  
	        }  
	        // 11.创建内容  
	        for (int i = 0; i < excelContent.size(); i++) {  
	            // 建立新行  
	            HSSFRow row = sheet.createRow((short) i + 2);  
	            for (int j = 0; j < ((Object[]) excelContent.get(i)).length; j++) {  
	                // 新建一列  
	                HSSFCell cell = row.createCell((short) j);  
	                cell.setCellStyle(style2);  
	                if (j == 0) {  
	                    cell.setCellValue((i + 1) + "");  
	                    continue;  
	                }  
	                Object t = ((Object[])excelContent.get(i))[j];  
	                if(t!=null){
	                if (t instanceof Integer) {  
	                    cell.setCellValue(((BigDecimal) ((Object[]) excelContent.get(i))[j]).toString());  
	                    continue;  
	                }  
	                if (t instanceof java.sql.Date) {  
	                    cell.setCellValue(((java.sql.Date) ((Object[]) excelContent.get(i))[j]).toString());  
	                    continue;  
	                }  
	                if (t instanceof java.util.Date) {  
	                    cell.setCellValue(((java.util.Date) ((Object[]) excelContent.get(i))[j]).toString());  
	                    continue;  
	                }  
	                String strValue= (String) ((Object[]) excelContent.get(i))[j];
	                cell.setCellValue(strValue.equals("null")?"":strValue);
	                }
	            }  
	        }  
	        wb.write(out);
	        //12.关闭数据流
	        out.flush();  
	        out.close();  
	    }  
	  
	}  
