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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/GridHeaderDto.java
 * 
 *  FILE NAME     :GridHeaderDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: GridHeaderDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

/**
 * 短途排班表头
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-25 上午9:19:21
 */
public class GridHeaderDto implements java.io.Serializable {

	private static final long serialVersionUID = -2122208569476110148L;

	/**
	 * 表头日期（1-31）
	 */
	private int headerDate;
	/**
	 * 星期（大写）
	 */
	private String dayText;
	/**
	 * 列头的name(根据日期1-31)
	 */
	private String dataIndex;
	/**
	 * 数据类型（用于model定义数据类型）
	 */
	private String dataType;
	/**
	 * render的数据
	 */
	private String rendererData;

	/**
	 * 获取 表头日期（1-31）.
	 * 
	 * @return the 表头日期（1-31）
	 */
	public int getHeaderDate() {
		return headerDate;
	}

	/**
	 * 设置 表头日期（1-31）.
	 * 
	 * @param headerDate
	 *            the new 表头日期（1-31）
	 */
	public void setHeaderDate(int headerDate) {
		this.headerDate = headerDate;
	}

	/**
	 * 获取 星期（大写）.
	 * 
	 * @return the 星期（大写）
	 */
	public String getDayText() {
		return dayText;
	}

	/**
	 * 设置 星期（大写）.
	 * 
	 * @param dayText
	 *            the new 星期（大写）
	 */
	public void setDayText(String dayText) {
		this.dayText = dayText;
	}

	/**
	 * 获取 列头的name(根据日期1-31).
	 * 
	 * @return the 列头的name(根据日期1-31)
	 */
	public String getDataIndex() {
		return dataIndex;
	}

	/**
	 * 设置 列头的name(根据日期1-31).
	 * 
	 * @param dataIndex
	 *            the new 列头的name(根据日期1-31)
	 */
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	/**
	 * 获取 数据类型（用于model定义数据类型）.
	 * 
	 * @return the 数据类型（用于model定义数据类型）
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * 设置 数据类型（用于model定义数据类型）.
	 * 
	 * @param dataType
	 *            the new 数据类型（用于model定义数据类型）
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 获取 render的数据.
	 * 
	 * @return the render的数据
	 */
	public String getRendererData() {
		return rendererData;
	}

	/**
	 * 设置 render的数据.
	 * 
	 * @param rendererData
	 *            the new render的数据
	 */
	public void setRendererData(String rendererData) {
		this.rendererData = rendererData;
	}
}