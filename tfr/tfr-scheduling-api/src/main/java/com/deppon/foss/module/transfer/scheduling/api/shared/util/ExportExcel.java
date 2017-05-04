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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/util/ExportExcel.java
 * 
 *  FILE NAME     :ExportExcel.java
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
 * FILE    NAME: ExportExcel.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.scheduling.api.define.ScheduleConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.ExcelHeaderDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckScheduleGridDto;

/**
 * 导出EXCEL
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-1-25 下午6:22:12
 */
public class ExportExcel {

	/**
	 * 日志管理器
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);

	/**
	 * 导出排班
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-28 上午9:29:29
	 */
	public void exportExcel(List<ExcelHeaderDto> headers, List<TruckScheduleGridDto> dataset, OutputStream out,
			String title) {
		exportExcel(title, headers, dataset, out);
	}

	/**
	 * 利用JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void exportExcel(String title, List<ExcelHeaderDto> headers, List<TruckScheduleGridDto> dataset,
			OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度
		sheet.setDefaultColumnWidth(ExcelUtil.DEFAULT_COLUMN_WIDTH);
		// 设置表格默认行高
		sheet.setDefaultRowHeight(ExcelUtil.HEADER_ROW_HEIGHT);
		// 颜色板,重新设置颜色
		HSSFPalette palette = workbook.getCustomPalette();
		// 表头颜色 HSSFColor.LIGHT_ORANGE.index
		palette.setColorAtIndex(HSSFColor.LIGHT_ORANGE.index, (byte) (0xff & 255), (byte) (0xff & 192),
				(byte) (0xff & 0));
		// 休息颜色 HSSFColor.BLUE.index
		palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) (0xff & 255), (byte) (0xff & 199), (byte) (0xff & 206));

		// 表体字体
		HSSFFont bodyFont = ExcelUtil.createBodyDefaultFont(workbook);
		// 休息的样式
		CellStyle restStyle = ExcelUtil.createBorderBodyCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.BLUE.index, CellStyle.ALIGN_CENTER, bodyFont);
		// 上班的样式
		CellStyle workStyle = ExcelUtil.createBorderBodyCellStyle(workbook, HSSFColor.WHITE.index,
				HSSFColor.WHITE.index, CellStyle.ALIGN_CENTER, bodyFont);
		// 单元格默认样式
		CellStyle defautStyle = workStyle;

		// 产生表格标题行
		HSSFRow row = null;
		HSSFCell cell = null;
		// 取消标题
		// 表头样式
		CellStyle styleHeader = ExcelUtil.createStyleHeader(workbook, bodyFont);
		// 列数
		int headerSize = headers.size();
		// 表头数据
		ExcelUtil.createHeader(sheet, row, cell, styleHeader, headers, headerSize);

		// 遍历集合数据，产生数据行
		int rowTotal = dataset.size();
		// 司机排班数据
		TruckScheduleGridDto bodyCellData = null;
		// 方法
		Method getMethod = null;
		// 单元格值
		String textValue = StringUtils.EMPTY;
		// 遍历所有司机
		for (int colIdx = 0; colIdx < rowTotal; colIdx++) {
			// 起始行下表为2
			row = sheet.createRow(ExcelUtil.START_BODY_ROW_NO + colIdx);
			// 获取司机排班信息
			bodyCellData = dataset.get(colIdx);
			// 姓名
			cell = row.createCell(ExcelUtil.CELL_0);
			ExcelUtil.cellValue(cell, bodyCellData.getDriverName(), defautStyle, StringUtils.EMPTY);

			// 线路
			cell = row.createCell(ExcelUtil.CELL_1);
			ExcelUtil.cellValue(cell, bodyCellData.getLineNameOrArea(), defautStyle, StringUtils.EMPTY);

			// 电话
			cell = row.createCell(ExcelUtil.CELL_2);
			ExcelUtil.cellValue(cell, bodyCellData.getDriverPhone(), defautStyle, StringUtils.EMPTY);

			// 车牌
			cell = row.createCell(ExcelUtil.CELL_3);
			ExcelUtil.cellValue(cell, bodyCellData.getVehicleNo(), defautStyle, StringUtils.EMPTY);

			// 车队名称
			cell = row.createCell(ExcelUtil.CELL_4);
			ExcelUtil.cellValue(cell, bodyCellData.getDriverOrgName(), defautStyle, StringUtils.EMPTY);

			// 第6列开始
			int cellIdx = 5;
			// 1-31日数据
			for (int i = 0; i < headerSize - 6; i++) {
				// 创建单元格
				cell = row.createCell(cellIdx + i);
				// 数据不为空
				if (bodyCellData != null) {
					// 构造方法名
					String getMethodName = ExcelUtil.METHOD_START + (i + 1);
					try {
						// 反射方式获取数据
						getMethod = TruckScheduleGridDto.class.getMethod(getMethodName, new Class[] {});
						// 反射回调值
						Object value = getMethod.invoke(bodyCellData, new Object[] {});
						// 将值进行填充
						if (value != null) {
							textValue = value.toString();
							// 如果未配置-x
							if (ScheduleConstants.PLAN_TYPE_UNKNOWN.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_UNKNOWN_TEXT, restStyle,
										StringUtils.EMPTY);
								// 如果是休息-休
							} else if (ScheduleConstants.PLAN_TYPE_REST.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_REST_TEXT, restStyle, StringUtils.EMPTY);
								// 如果是离岗-离
							} else if (ScheduleConstants.PLAN_TYPE_UNDERGO.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_UNDERGO_TEXT, restStyle,
										StringUtils.EMPTY);
								// 值班
							} else if (ScheduleConstants.PLAN_TYPE_ON_DUTY.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_ON_DUTY_TEXT, workStyle,
										StringUtils.EMPTY);
								// 培训
							} else if (ScheduleConstants.PLAN_TYPE_TRAIN.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_TRAIN_TEXT, workStyle, StringUtils.EMPTY);
								// 出车
							} else if (ScheduleConstants.PLAN_TYPE_WORK.equals(textValue)) {
								ExcelUtil.cellValue(cell, ExcelUtil.PLAN_TYPE_WORK_TEXT, workStyle, StringUtils.EMPTY);
							}

						}
					} catch (Exception e) {

					}
					// 无数据的情况
				} else {
					ExcelUtil.cellValue(cell, ExcelUtil.UNKNOWN_TEXT, defautStyle, StringUtils.EMPTY);
				}

			}
			// 上班天数
			cell = row.createCell(headerSize - 1);
			ExcelUtil.cellValue(cell, String.valueOf(bodyCellData.getWorkTotal()), defautStyle, StringUtils.EMPTY);

		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook = null;
				try {
					out.close();
				} catch (IOException e) {
					LOGGER.error("IOException", e);
				}
			}
		}

	}

}