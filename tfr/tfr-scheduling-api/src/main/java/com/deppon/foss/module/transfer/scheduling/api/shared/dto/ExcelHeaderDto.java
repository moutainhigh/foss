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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ExcelHeaderDto.java
 * 
 *  FILE NAME     :ExcelHeaderDto.java
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
 * FILE    NAME: ExeclHeader.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

/**
 * Excel表头数据
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-1-26 上午10:09:32
 */
public class ExcelHeaderDto implements java.io.Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4212370391505018244L;
	/**
	 * 单元日期数据（1-31）
	 */
	private String headerCellDateTxet;
	/**
	 * 单元星期数据（1-7）
	 */
	private String headerCellDayTxet;
	/**
	 * 列下标
	 */
	private int columnIndex;

	/**
	 * 表体单元数据
	 */
	private String bodyCellText;

	/**
	 * 带参数的构造器
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-26 上午10:20:33
	 */
	public ExcelHeaderDto(String headerCellDateTxet, String headerCellDayTxet) {
		this.headerCellDateTxet = headerCellDateTxet;
		this.headerCellDayTxet = headerCellDayTxet;
	}

	/**
	 * 无参数构造器
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-1-26 上午10:26:12
	 */
	public ExcelHeaderDto() {
	}

	/**
	 * 获取 单元日期数据（1-31）.
	 * 
	 * @return the 单元日期数据（1-31）
	 */
	public String getHeaderCellDateTxet() {
		return headerCellDateTxet;
	}

	/**
	 * 设置 单元日期数据（1-31）.
	 * 
	 * @param headerCellDateTxet
	 *            the new 单元日期数据（1-31）
	 */
	public void setHeaderCellDateTxet(String headerCellDateTxet) {
		this.headerCellDateTxet = headerCellDateTxet;
	}

	/**
	 * 获取 单元星期数据（1-7）.
	 * 
	 * @return the 单元星期数据（1-7）
	 */
	public String getHeaderCellDayTxet() {
		return headerCellDayTxet;
	}

	/**
	 * 设置 单元星期数据（1-7）.
	 * 
	 * @param headerCellDayTxet
	 *            the new 单元星期数据（1-7）
	 */
	public void setHeaderCellDayTxet(String headerCellDayTxet) {
		this.headerCellDayTxet = headerCellDayTxet;
	}

	/**
	 * 获取 列下标.
	 *
	 * @return the 列下标
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * 设置 列下标.
	 *
	 * @param columnIndex the new 列下标
	 */
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * 获取 表体单元数据.
	 *
	 * @return the 表体单元数据
	 */
	public String getBodyCellText() {
		return bodyCellText;
	}

	/**
	 * 设置 表体单元数据.
	 *
	 * @param bodyCellText the new 表体单元数据
	 */
	public void setBodyCellText(String bodyCellText) {
		this.bodyCellText = bodyCellText;
	}

}