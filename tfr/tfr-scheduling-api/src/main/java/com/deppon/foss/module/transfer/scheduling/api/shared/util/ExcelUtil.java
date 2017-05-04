/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/util/ExcelUtil.java
 * 
 *  FILE NAME     :ExcelUtil.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.util
 * FILE    NAME: ExcelUtil.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExcelHeaderDto;

/**
 * 导出EXCEL工具
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-1-25 下午5:32:10
 */
public class ExcelUtil {
	/**
	 * 日志管理器
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 标题行高
	 */
	public static final Short TITLE_ROW_HEIGHT = 16 * 35;
	/**
	 * 标题字体大小
	 */
	public static final Short TITLE_FONT_SIZE = 20;
	/**
	 * 标题字体名称
	 */
	public static final String TITLE_FONT_NAME = "隶书";
	/**
	 * 表头行高
	 */
	public static short HEADER_ROW_HEIGHT = ConstantsNumberSonar.SONAR_NUMBER_16 * ConstantsNumberSonar.SONAR_NUMBER_28;
	/**
	 * 表头字体大小
	 */
	public static final Short HEADER_FONT_SIZE = 11;
	/**
	 * 表头字体名称
	 */
	public static final String HEADER_FONT_NAME = "黑体";
	/**
	 * 默认李宽
	 */
	public static final int DEFAULT_COLUMN_WIDTH = 15;
	/**
	 * 日期列宽
	 */
	public static final Short DAY_COLUMN_WIDTH = 36 * 32;

	/**
	 * 表头行高
	 */
	public static short BODY_ROW_HEIGHT = ConstantsNumberSonar.SONAR_NUMBER_16 * ConstantsNumberSonar.SONAR_NUMBER_10;

	/**
	 * 表头行数
	 */
	public static int HEADER_ROW_NUM = 2;
	/**
	 * 表头第一行
	 */
	public static int HEADER_FIRST_ROW_INDEX = 0;

	/**
	 * 姓名
	 */
	private static String DRIVER_NAME = "姓名";
	/**
	 * 姓名列宽
	 */
	public static final Short DRIVERNAME_COLUMN_WIDTH = 36 * 60;
	/**
	 * 线路
	 */
	private static String LINE_NAME = "线路";
	/**
	 * 姓名列宽
	 */
	public static final Short LINENAME_COLUMN_WIDTH = 36 * 80;
	/**
	 * 电话
	 */
	private static String TELEPHONE = "电话";
	/**
	 * 电话列宽
	 */
	public static final Short TELEPHONE_COLUMN_WIDTH = 36 * 80;
	/**
	 * 车牌号
	 */
	private static String VEHICLE_NO = "车牌号";
	/**
	 * 车牌号列宽
	 */
	public static final Short VEHICLE_NO_COLUMN_WIDTH = 36 * 80;
	/**
	 * 车队名称
	 */
	private static String DRIVER_ORG_NAME = "车队名称";
	/**
	 * 车队名称列宽
	 */
	public static final Short DRIVER_ORG_NAME_COLUMN_WIDTH = 36 * 80;
	/**
	 * 上班天数
	 */
	private static String WORK_TOTAL = "上班天数";
	/**
	 * 上班天数列宽
	 */
	public static final Short WORK_TOTAL_COLUMN_WIDTH = 36 * 80;
	/**
	 * 表体其实数字
	 */
	public static final int START_BODY_ROW_NO = 2;
	/**
	 * 未配置
	 */
	public static final String PLAN_TYPE_UNKNOWN_TEXT = "x";
	/**
	 * 休息
	 */
	public static final String PLAN_TYPE_REST_TEXT = "休";
	/**
	 * 离岗
	 */
	public static final String PLAN_TYPE_UNDERGO_TEXT = "离";
	/**
	 * 未知
	 */
	public static final String UNKNOWN_TEXT = "-";
	/**
	 * 方法起始
	 */
	public static final String METHOD_START = "getDate";
	/**
	 * 值班
	 */
	public static final String PLAN_TYPE_ON_DUTY_TEXT = "值";
	/**
	 * 培训
	 */
	public static final String PLAN_TYPE_TRAIN_TEXT = "训";
	/**
	 * 出车
	 */
	public static final String PLAN_TYPE_WORK_TEXT = "车";
	/**
	 * 第一行
	 */
	private static final int FIRST_ROW_IDX = 0;
	/**
	 * 第二行
	 */
	private static final int SECOND_ROW_IDX = 1;
	/**
	 * 列1
	 */
	public static final int CELL_0 = 0;
	/**
	 * 列2
	 */
	public static final int CELL_1 = 1;
	/**
	 * 列3
	 */
	public static final int CELL_2 = 2;
	/**
	 * 列4
	 */
	public static final int CELL_3 = 3;
	/**
	 * 列5
	 */
	public static final int CELL_4 = 4;

	/**
	 * 功能：将HSSFWorkbook写入Excel文件
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param absPath
	 *            写入文件的相对路径
	 * @param wbName
	 *            文件名
	 */
	public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			LOGGER.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()).toString());
		} catch (IOException e) {
			LOGGER.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()).toString());
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				LOGGER.error(new StringBuffer("[").append(e.getMessage()).append("]").append(e.getCause()).toString());
			}
		}
	}

	/**
	 * 功能：创建HSSFSheet工作簿
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param sheetName
	 *            String
	 * @return HSSFSheet
	 */
	public static HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(ScheduleConstants.SONAR_NUMBER_12);
		sheet.setGridsPrinted(false);
		sheet.setDisplayGridlines(false);
		return sheet;
	}

	/**
	 * 功能：创建HSSFRow
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * @param rowNum
	 *            int
	 * @param height
	 *            int
	 * @return HSSFRow
	 */
	public static HSSFRow createRow(HSSFSheet sheet, int rowNum, int height) {
		HSSFRow row = sheet.createRow(rowNum);
		row.setHeight((short) height);
		return row;
	}

	/**
	 * 功能：创建CellStyle样式
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param backgroundColor
	 *            背景色
	 * @param foregroundColor
	 *            前置色
	 * @param font
	 *            字体
	 * @return CellStyle
	 */
	public static CellStyle createCellStyle(HSSFWorkbook wb, short backgroundColor, short foregroundColor,
			short halign, Font font) {
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(halign);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cs.setFillBackgroundColor(backgroundColor);
		cs.setFillForegroundColor(foregroundColor);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cs.setFont(font);
		return cs;
	}

	/**
	 * 功能：创建带边框的CellStyle样式
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param backgroundColor
	 *            背景色
	 * @param foregroundColor
	 *            前置色
	 * @param font
	 *            字体
	 * @return CellStyle
	 */
	public static CellStyle createBorderCellStyle(HSSFWorkbook wb, short backgroundColor, short foregroundColor,
			short halign, Font font) {
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(halign);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cs.setFillBackgroundColor(backgroundColor);
		cs.setFillForegroundColor(foregroundColor);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cs.setFont(font);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_MEDIUM);
		cs.setBorderBottom(CellStyle.BORDER_MEDIUM);
		return cs;
	}

	/**
	 * 功能：创建带边框的CellStyle样式
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param backgroundColor
	 *            背景色
	 * @param foregroundColor
	 *            前置色
	 * @param font
	 *            字体
	 * @return CellStyle
	 */
	public static CellStyle createBorderBodyCellStyle(HSSFWorkbook wb, short backgroundColor, short foregroundColor,
			short halign, Font font) {
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(halign);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cs.setFillBackgroundColor(backgroundColor);
		cs.setFillForegroundColor(foregroundColor);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cs.setFont(font);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		return cs;
	}

	/**
	 * 表头样式
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午9:00:13
	 */
	public static CellStyle createStyleHeader(HSSFWorkbook wb, HSSFFont bodyFont) {
		// 表头样式
		CellStyle styleHeader = ExcelUtil.createBorderCellStyle(wb, HSSFColor.WHITE.index,
				HSSFColor.LIGHT_ORANGE.index, CellStyle.ALIGN_CENTER, bodyFont);
		// 表头字体
		Font fontHeader = ExcelUtil.createFont(wb, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index,
				ExcelUtil.HEADER_FONT_SIZE);
		fontHeader.setFontName(ExcelUtil.HEADER_FONT_NAME);
		styleHeader.setFont(fontHeader);
		return styleHeader;
	}

	/**
	 * 功能：创建CELL
	 * 
	 * @param row
	 *            HSSFRow
	 * @param cellNum
	 *            int
	 * @param style
	 *            HSSFStyle
	 * @return HSSFCell
	 */
	public static HSSFCell createCell(HSSFRow row, int cellNum, CellStyle style) {
		HSSFCell cell = row.createCell(cellNum);
		cell.setCellStyle(style);
		return cell;
	}

	/**
	 * 功能：合并单元格
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * @param firstRow
	 *            int
	 * @param lastRow
	 *            int
	 * @param firstColumn
	 *            int
	 * @param lastColumn
	 *            int
	 * @return int 合并区域号码
	 */
	public static int mergeCell(HSSFSheet sheet, int firstRow, int lastRow, int firstColumn, int lastColumn) {
		return sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
	}

	/**
	 * 功能：创建字体
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param boldweight
	 *            short
	 * @param color
	 *            short
	 * @return Font
	 */
	public static Font createFont(HSSFWorkbook wb, short boldweight, short color, short size) {
		Font font = wb.createFont();
		font.setBoldweight(boldweight);
		font.setColor(color);
		font.setFontHeightInPoints(size);
		return font;
	}

	/**
	 * 创建默认的表体字体
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午8:53:13
	 */
	public static HSSFFont createBodyDefaultFont(HSSFWorkbook wb) {
		HSSFFont bodyFont = wb.createFont();
		bodyFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		return bodyFont;
	}

	/**
	 * 设置合并单元格的边框样式
	 * 
	 * @param sheet
	 *            HSSFSheet
	 * @param ca
	 *            CellRangAddress
	 * @param style
	 *            CellStyle
	 */
	public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca, CellStyle style) {
		for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
			HSSFRow row = HSSFCellUtil.getRow(i, sheet);
			for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
				HSSFCell cell = HSSFCellUtil.getCell(row, j);
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * 获取表头数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-26 上午10:28:56
	 */
	public static List<ExcelHeaderDto> calcHeader(String ymd) {
		List<ExcelHeaderDto> gridHeaderFields = new ArrayList<ExcelHeaderDto>();
		// 获取本月最后一天的数值
		Calendar cdr = Calendar.getInstance();
		if (org.apache.commons.lang.StringUtils.isNotBlank(ymd)) {
			cdr.setTime(com.deppon.foss.util.DateUtils.convert(ymd, com.deppon.foss.util.DateUtils.DATE_FORMAT));
		}
		int actualMaximum = cdr.getActualMaximum(Calendar.DATE);
		// 循环取日期
		ExcelHeaderDto ghDto = null;
		// 姓名
		ghDto = new ExcelHeaderDto(DRIVER_NAME, DRIVER_NAME);
		gridHeaderFields.add(ghDto);
		// 线路
		ghDto = new ExcelHeaderDto(LINE_NAME, LINE_NAME);
		gridHeaderFields.add(ghDto);
		// 电话
		ghDto = new ExcelHeaderDto(TELEPHONE, TELEPHONE);
		gridHeaderFields.add(ghDto);
		// 车牌号
		ghDto = new ExcelHeaderDto(VEHICLE_NO, VEHICLE_NO);
		gridHeaderFields.add(ghDto);
		// 车队名称
		ghDto = new ExcelHeaderDto(DRIVER_ORG_NAME, DRIVER_ORG_NAME);
		gridHeaderFields.add(ghDto);

		for (int day = 1; day <= actualMaximum; day++) {
			// 设置当天日期
			cdr.set(cdr.get(Calendar.YEAR), cdr.get(Calendar.MONTH), day);
			// 获取日期，星期，等数据
			ghDto = new ExcelHeaderDto();
			// 日期
			ghDto.setHeaderCellDateTxet(String.valueOf(cdr.get(Calendar.DAY_OF_MONTH)));
			// 星期
			ghDto.setHeaderCellDayTxet(DateUtils.convertDayOfWeek(cdr.get(Calendar.DAY_OF_WEEK)));
			gridHeaderFields.add(ghDto);
		}
		// 上班天数
		ghDto = new ExcelHeaderDto(WORK_TOTAL, WORK_TOTAL);
		gridHeaderFields.add(ghDto);

		return gridHeaderFields;
	}

	/**
	 * 创建表体
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午8:50:17
	 */
	public static void creatSheetTitle(HSSFWorkbook workbook, HSSFSheet sheet, HSSFCell cell, String title,
			List<ExcelHeaderDto> headers) {
		cell.setCellValue(title);
		// 标题样式
		CellStyle styleTitle = ExcelUtil.createBorderCellStyle(workbook, HSSFColor.DARK_YELLOW.index,
				HSSFColor.WHITE.index, CellStyle.ALIGN_CENTER, ExcelUtil.createBodyDefaultFont(workbook));
		// 标题字体
		Font fontTitle = ExcelUtil.createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLACK.index,
				ExcelUtil.TITLE_FONT_SIZE);
		fontTitle.setFontName(ExcelUtil.TITLE_FONT_NAME);
		styleTitle.setFont(fontTitle);
		cell.setCellStyle(styleTitle);
		// 合并
		ExcelUtil.mergeCell(sheet, 0, 0, 0, headers.size() - 1);

	}

	/**
	 * 创建表头
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午9:05:16
	 */
	public static void createHeader(HSSFSheet sheet, HSSFRow row, HSSFCell cell, CellStyle styleHeader,
			List<ExcelHeaderDto> headers, int headerSize) {
		// 表头
		for (int i = ExcelUtil.HEADER_FIRST_ROW_INDEX; i < ExcelUtil.HEADER_ROW_NUM; i++) {
			row = sheet.createRow(i);
			// 表头行高
			row.setHeight(ExcelUtil.HEADER_ROW_HEIGHT);
			// 表头数据
			ExcelHeaderDto headerDto = null;
			if (CollectionUtils.isNotEmpty(headers)) {
				HSSFRichTextString text = null;

				for (int j = 0; j < headerSize; j++) {
					headerDto = headers.get(j);
					cell = row.createCell(j);
					if (j == 0) {
						sheet.setColumnWidth(j, ExcelUtil.DRIVERNAME_COLUMN_WIDTH);
					} else if (j == 1) {
						sheet.setColumnWidth(j, ExcelUtil.LINENAME_COLUMN_WIDTH);
					} else if (j == 2) {
						sheet.setColumnWidth(j, ExcelUtil.TELEPHONE_COLUMN_WIDTH);
					} else if (j == ScheduleConstants.SONAR_NUMBER_3) {
						sheet.setColumnWidth(j, ExcelUtil.VEHICLE_NO_COLUMN_WIDTH);
					} else if (j == ScheduleConstants.SONAR_NUMBER_4) {
						sheet.setColumnWidth(j, ExcelUtil.DRIVER_ORG_NAME_COLUMN_WIDTH);
					} else if (j > ScheduleConstants.SONAR_NUMBER_4 || j < ScheduleConstants.SONAR_NUMBER_36) {
						sheet.setColumnWidth(j, ExcelUtil.DAY_COLUMN_WIDTH);
					}
					if (j == headerSize - 1) {
						sheet.setColumnWidth(j, ExcelUtil.WORK_TOTAL_COLUMN_WIDTH);
						cell.setCellStyle(styleHeader);
					}
					cell.setCellStyle(styleHeader);
					if (i == ExcelUtil.HEADER_FIRST_ROW_INDEX) {
						text = new HSSFRichTextString(headerDto.getHeaderCellDateTxet());
					} else {
						text = new HSSFRichTextString(headerDto.getHeaderCellDayTxet());
					}
					cell.setCellValue(text);
				}
			}
		}
		// 合并姓名
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, 0, 0);
		// 合并线路
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, 1, 1);
		// 合并电话
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, 2, 2);
		// 合并车牌
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, ScheduleConstants.SONAR_NUMBER_3, ScheduleConstants.SONAR_NUMBER_3);
		// 合并车队名称
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, ScheduleConstants.SONAR_NUMBER_4, ScheduleConstants.SONAR_NUMBER_4);
		// 合并上班天数
		ExcelUtil.mergeCell(sheet, ExcelUtil.FIRST_ROW_IDX, ExcelUtil.SECOND_ROW_IDX, headerSize - 1, headerSize - 1);

	}

	/**
	 * 设置单元格值及样式
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午9:11:41
	 */
	public static void cellValue(HSSFCell cell, String textValue, CellStyle cellStyle, String commont) {
		cell.setCellValue(textValue);
		cell.setCellStyle(cellStyle);
	}

}