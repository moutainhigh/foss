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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/define/FuelConsonptionConstants.java
 *  
 *  FILE NAME          :FuelConsonptionConstants.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.define;


public class FuelConsonptionConstants {
	/**
	 * 导出Excel的起始编号，默认为0
	 */
	public static final int EXPORT_EXCEL_START_NUMBER=0;
	
	
	/**
	 * 导出Excel的最大条数
	 */
	public static final int EXPORT_EXCEL_MAX_COUNTS=100000; 
	
	/**
	 * 导出Excel 使用
	 */
    public static final String EXCEL_SHEET_NAME="sheet";//导出Excel  sheet
    
    
	/**
	 * GBK编码
	 */
	public static final String UNICODE_GBK="GBK";

	/**
	 *ISO8859-1编码
	 */
	public static final String UNICODE_ISO="ISO8859_1";
	
	
	/**
	 * UTF-8编码
	 */
	public static final String UNICODE_UTF="UTF-8";
	
	/**
	 * 导入excel最大列数
	 */
	public static final Integer EXCEL_COLUMN_MAX_SIZE = 3;
	
	/**
	 * 第一列
	 */
	public static final Integer EXCEL_COLUMN_FIRST = 0;
	/**
	 * 第二列
	 */
	public static final Integer EXCEL_COLUMN_SECOND = 1;
	/**
	 * 第三列
	 */
	public static final Integer EXCEL_COLUMN_THREE = 2;
	
	/**
	 * 发车类型
	 */ 
	public static final String DEPARTURE_TYPE_FOR_LONG_DISTANCE="LONG_DISTANCE";//长途车辆
	
	public static final String DEPARTURE_TYPE_FOR_SHORT_DISTANCE="SHORT_DISTANCE";//短途接送货
	
	public static final String DEPARTURE_TYPE_FOR_SHORT_BUS="SHORT_BUS";//短途班车
	
	public static final String DEPARTURE_TYPE_FOR_LOGISTICS_VEHICLE="LOGISTICS_VEHICLE"; //后勤车
	
	public static final String DEPARTURE_TYPE_FOR_EXPRESS="EXPRESS"; //快递
	
	/**
	 * 油耗Excel名称
	 */
	public static final String FUEL_CONSUMPTION_EXCEL_NAME_FOR_LONG="长途油耗";//长途油耗
	
	public static final String FUEL_CONSUMPTION_EXCEL_NAME_FOR_SHORT="短途油耗";//短途油耗
	
	public static final String FUEL_CONSUMPTION_EXCEL_NAME_FOR_LOGISTICS="后勤车油耗";//后勤车油耗
	
	public static final String FUEL_CONSUMPTION_EXCEL_NAME_FOR_EXPRESS="快递车油耗";//后勤车油耗
	/**
	 * 操作类型,1新增，2修改，3作废
	 */
	public static final int OPERATOR_STATE_ADD = 1;
	public static final int OPERATOR_STATE_UPDATE = 2;
	public static final int OPERATOR_STATE_DELETE = 3;
	
	/**
	 * 是，否
	 */
	public static final String YES = "Y";
	public static final String NO = "N";
	public static final String YES_AND_NO = "Y, N";
	
	/**
	 * 共通新增、修改成功返回code
	 */
	public static final int MANAGEMENT_SUCCESS = 0;
	
	/**
	 * .XLS
	 */
	public static final String EXCEL_FILE_TAIL_XLS_UP = ".XLS";
	/**
	 * .xls
	 */
	public static final String EXCEL_FILE_TAIL_XLS_DOWN = ".xls";
	/**
	 * .xlsx
	 */
	public static final String EXCEL_FILE_TAIL_XLSX_DOWN = ".xlsx";
	/**
	 * .XLSX
	 */
	public static final String EXCEL_FILE_TAIL_XLSX_UP = ".XLSX";
	
	/**
	 * 第一
	 */
	public static final Integer EXCEL_DEFAULT_SHEET_ONE = 0;
	/**
	 * NAN
	 */
	public static final String NAN = "NaN";
}