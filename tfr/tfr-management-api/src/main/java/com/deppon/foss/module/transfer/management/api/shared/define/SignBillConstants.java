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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/define/SignBillConstants.java
 *  
 *  FILE NAME          :SignBillConstants.java
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

public class SignBillConstants {

	/**  导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	/** 查询转货车 导出Excel文件的表头*/
	public static final String[] TRANSFER_ROW_HEADS = {"签单编号","用车部门","司机姓名","日期","车牌号码","车型","转货里程","体积","重量","用车时间","是否第一个部门","转货提成","用车费用划分"};
	
	/** 名称*/
	public static final String TRANSFER_SHEET_NAME = "转货车签单";
	
	/** 查询其他签单 导出Excel文件的表头*/
	public static final String[] OTHER_ROW_HEADS ={"签单编号","用车部门","司机姓名","日期","车牌号码","车型","签单类型","目的站","线路里程","票数","体积","重量","用车时间","其他金额"} ;
	
	/** 名称*/
	public static final String OTHER_SHEET_NAME = "其他签单";
	
	
	/** 查询派送签单 导出Excel文件的表头*/
	public static final String[] SEND_ROW_HEADS = {"签单编号","用车部门","司机姓名","日期","车牌号码","车型","派送票数","体积","重量","进仓票数","上楼票数","上楼费","里程","拉回票数","是否单独配送"};
	
	/** 名称*/
	public static final String SEND_SHEET_NAME = "派送签单";
	
	/** 查询专线对发签单 导出Excel文件的表头*/
	public static final String[] REGULARTRUCK_ROW_HEADS = {"签单编号","用车部门","司机姓名","日期","车牌号码","车型","线路","线路里程","体积","重量"};
	
	/** 名称*/
	public static final String REGULARTRUCK_SHEET_NAME = "专线对发签单";
	
	
	/** 查询专线对发签单 导出Excel文件的表头*/
	public static final String[] FOCUS_ROW_HEADS = {"签单编号","用车部门","司机","司机姓名","日期","车牌号码","车型","行驶公里","接货票数","体积","重量","上楼接货票数","单独接货票数"};
	
	/** 名称*/
	public static final String FOCUS_SHEET_NAME = "集中接货签单";
	
	/** 是 code  */
	public static final String  IS_SING_BILL_CODE="Y";
	
	/** 是 显示名字 */
	public static final String  IS_SING_BILL_Name="是";
	
	/** 否  code  */
	public static final String  IS_NOT_SING_BILL_CODE="N";
	/** 否  显示名字 */
	public static final String  IS_NOT_SING_BILL_Name="否";	
	
	/** 内部签单 */
	public static final String  INTERIOR_SIGN_BILL_NAME="内部签单";
	
	/** 专车签单 */
	public static final String  SPECIAL_SIGN_BILL_NAME="专车签单";
	
	/** 其他签单 */
	public static final String  OTHER_SIGN_BILL_NAME="其他签单";
	
	
	/** 内部签单 */
	public static final String  INTERIOR_SIGN_BILL="INTERIOR_SIGN_BILL";
	
	/** 专车签单 */
	public static final String  SPECIAL_SIGN_BILL="SPECIAL_SIGN_BILL";
	
	/** 其他签单 */
	public static final String  OTHER_SIGN_BILL="OTHER_SIGN_BILL";
	
	
}