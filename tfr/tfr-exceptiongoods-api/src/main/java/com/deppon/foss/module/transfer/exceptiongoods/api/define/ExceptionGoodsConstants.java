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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/ExceptionGoodsConstants.java
 * 
 *  FILE NAME          :ExceptionGoodsConstants.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.define;

import java.util.HashMap;
import java.util.Map;


/**
 * 定义了异常货相关常量
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:04:46
 */
public class ExceptionGoodsConstants {
	
	/**
	 * 库存状态   已入库
	 */
	public static final String STOCK_STATUS_IN = "IN_STOCK";
	/**
	 * 库存状态   已出库
	 */
	public static final String STOCK_STATUS_OUT = "OUT_STOCK";
	/**
	 * 库存状态   未入库
	 */
	public static final String STOCK_STATUS_NO_IN = "NO_STOCK";
	
	/** 
	 * 找货状态   找到
	 */
	public static final String NO_LABEL_GOODS_FIND = "FIND";
	/**
	 * 找货状态   未找到
	 */
	public static final String NO_LABEL_GOODS_NO_FIND = "NO_FIND";
	
	/**
	 * 找货状态 转弃货
	 */
	public static final String NO_LABEL_GOODS_TO_ABANDON="TO_ABANDON";
	
	/**
	 * OA调用FOSS违禁品接口 操作类型   标记违禁品
	 */
	public static final int MARK_CONTRABAND = 1;
	/**
	 * OA调用FOSS违禁品接口 操作类型   标记违禁品处理结果
	 */
	public static final int MARK_CONTRABAND_RESULT = 2;
	
	/**
	 * 违禁品移交状态  未移交
	 */
	public static final String NO_HANDOVER_STATUS = "NO_HANDOVER";
	/**
	 * 违禁品移交状态  移交
	 */
	public static final String HANDOVER_STATUS = "HANDOVER";
	
	/**
	 * 违禁处理结果  疑似违禁品
	 */
	public static final String SUSPICION_CONTRABAND_PROCESS_RESULT = "SUSPICION_CONTRABAND";
	/**
	 * 违禁处理结果  违禁品
	 */
	public static final String CONTRABAND_PROCESS_RESULT = "CONTRABAND";
	/**
	 * 违禁处理结果  非违禁品
	 */
	public static final String NO_CONTRABAND_PROCESS_RESULT = "NO_CONTRABAND";
	/**
	 * 未上报过违禁品
	 */
	public static final String NO_REPORT_CONTRABAND = "NO_REPORT_CONTRABAND";
	
	/**
	 * 上传文件目录
	 */
	public static final String UPLOAD_PATH = "nolabel//";
	
	/**
	 * 流水号格式
	 */
	public static final String SERIAL_NO_STYLE = "0000";

	/**
	 * 无标签运单号格式
	 */
	public static final String NOLABEL_GOODS_BILL_NO_STYLE = "w00000000";
	
	/**
	 * 部门类型  营业部
	 */
	public static final String SALE_ORG = "SALE";
	/**
	 * 部门类型  驻地派送部
	 */
	public static final String STATION_ORG = "STATION";
	/**
	 * 部门类型  外场
	 */
	public static final String TRANSFER_CENTER_ORG = "TRANSFER_CENTER";
	
	/** 货物位置*/
	public static final String SIGN_GOODS_POSITION = "已签收";
	
	/**
	 * 巴枪扫描打印
	* @fields PRINT_TYPE
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:54:37
	* @version V1.0
	*/
	public static final int PRINT_TYPE = 1;
	
	/** 查询上分拣或标签打印 导出Excel文件的表头*/
	public static final String[] ROW_HEADS = {"运单号","流水号","操作人","操作部门","操作时间"};
	
	public static final String SHEET_NAME = "上分拣或标签打印";
	
	/** 查询上分拣或标签打印 导出Excel文件的sheet最大行数*/
	public static final int SHEET_SIZE = 5000;
	/**
	 * @author nly
	 * @date 2015年6月23日 下午4:37:08
	 * @function 转换品类为QMS对应值
	 *  服装纺织类	CLOTHING_SPINNING	1-服装纺织类
		工业电子类	INDUSTRIAL_ELEC		2-工业电子类
		生活电子类	LIFE_ELEC			3-生活电子类
		生活电器类	ELECTRICAL_APPLIANCE	4-生活电器类
		家具类	FURNITURE			5-家具类
		生活用品化妆类	COSMETIC		6-化妆品类
		食品药品类	FOOD_MEDICAMENTS	7-食品奶品类
		塑胶建材类	PLASTIC_BUILDING	8-塑胶建材类
		五金仪器类	HARDWARE_INSTR		9-五金仪器类
		易碎易损类	FRAGILE				10-易碎易损类
		其他类	OTHER				11-其他类
	 */
	public static Map<String,String> goodsTypeMap = new HashMap<String, String>();
	
	static {
		goodsTypeMap.put("CLOTHING_SPINNING", "1");
		goodsTypeMap.put("INDUSTRIAL_ELEC", "2");
		goodsTypeMap.put("LIFE_ELEC", "3");
		goodsTypeMap.put("ELECTRICAL_APPLIANCE", "4");
		goodsTypeMap.put("FURNITURE", "5");
		goodsTypeMap.put("COSMETIC", "6");
		goodsTypeMap.put("FOOD_MEDICAMENTS", "7");
		goodsTypeMap.put("PLASTIC_BUILDING", "8");
		goodsTypeMap.put("HARDWARE_INSTR", "9");
		goodsTypeMap.put("FRAGILE", "10");
		goodsTypeMap.put("OTHER", "11");
	}
	/**
	 * @author nly
	 * @date 2015年6月15日 下午5:27:24
	 * @function  数据字典行业货源品类转为无标签品类
	 *  生活电子类	BSE_LIFE_ELECTRONIC				生活电子类	LIFE_ELEC
		五金仪器类	BSE_HARDWARE_INSTRUMENT			五金仪器类	HARDWARE_INSTR
		塑胶建材类	BSE_PLASTICBUILDING_MATERIALS	塑胶建材类	PLASTIC_BUILDING
		生活电器类	BSE_ELECTRICAL_LIFE				生活电器类	ELECTRICAL_APPLIANCE
		家具类	BSE_FURNITURE					家具类	FURNITURE
		易损易碎类	BSE_DELICATE					易碎易损类	FRAGILE
		食品药品类	BSE_FOOD_DRUG					食品药品类	FOOD_MEDICAMENTS
		其他类	BSE_OTHER						其他类	OTHER
		生活日化类	BSE_LIFE_DAILY					生活用品化妆类	COSMETIC
		服装纺织类	BSE_TEXTILE_GARMENT				服装纺织类	CLOTHING_SPINNING
		工业电子类	BSE_INDUSTRIAL_ELECTRONICS		工业电子类	INDUSTRIAL_ELEC

	 */
	public static Map<String,String> goodsTypesMap = new HashMap<String, String>();
	
	static {
		goodsTypeMap.put("BSE_TEXTILE_GARMENT","CLOTHING_SPINNING");
		goodsTypeMap.put("BSE_INDUSTRIAL_ELECTRONICS","INDUSTRIAL_ELEC");
		goodsTypeMap.put("BSE_LIFE_ELECTRONIC","LIFE_ELEC");
		goodsTypeMap.put("BSE_ELECTRICAL_LIFE","ELECTRICAL_APPLIANCE");
		goodsTypeMap.put("BSE_FURNITURE","FURNITURE");
		goodsTypeMap.put("BSE_LIFE_DAILY","COSMETIC");
		goodsTypeMap.put("BSE_FOOD_DRUG","FOOD_MEDICAMENTS");
		goodsTypeMap.put("BSE_PLASTICBUILDING_MATERIALS","PLASTIC_BUILDING");
		goodsTypeMap.put("BSE_HARDWARE_INSTRUMENT","HARDWARE_INSTR");
		goodsTypeMap.put("BSE_DELICATE","FRAGILE");
		goodsTypeMap.put("BSE_OTHER","OTHER");
	}
	/**
	 * @author nly
	 * @date 2015年6月13日 下午3:05:59
	 * @function 转换包装 1-纸 2-纤 3-木 4-膜 5-裸 6-其他    木	WOOD  膜	FILM 纤	FIBER   其它	OTHER 纸	PAPER 裸	NAKED
	 */
	public static Map<String,String> packageTypeMap = new HashMap<String, String>();
	
	static {
		packageTypeMap.put("PAPER", "1");
		packageTypeMap.put("FIBER", "2");
		packageTypeMap.put("WOOD", "3");
		packageTypeMap.put("FILM", "4");
		packageTypeMap.put("NAKED", "5");
		packageTypeMap.put("OTHER", "6");
	}
	
	/**
	 * @author nly
	 * @date 2015年6月24日 上午8:48:59
	 * @function 转换无标签发现类型 1-装车、2-卸车、3-清仓  UNLOAD-卸车 STOCKTAKING-清仓 LOAD-装车
	 */
	public static Map<String,String> findTypeMap = new HashMap<String, String>();
	
	static {
		findTypeMap.put("LOAD", "1");
		findTypeMap.put("UNLOAD", "2");
		findTypeMap.put("STOCKTAKING", "3");
	}
	
	/**
	 * 无标签多货状态
	 */
	//未确认：认领前状态
	public static final String GOODS_STATUS_UNCONFIRM = "UNCONFIRM";
	//已确认：认领时
	public static final String GOODS_STATUS_CONFIRM = "CONFIRM";
	//异常货处理已确认
	public static final String GOODS_STATUS_EXCEPTIONCONFIRM = "EXCEPTIONCONFIRM";
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_14 = 14;
	public final static int SONAR_NUMBER_18 = 18;
	public static final int SONAR_NUMBER_24 = 24;
	public static final int SONAR_NUMBER_60 = 60;
	public final static int SONAR_NUMBER_1000 = 1000;
}