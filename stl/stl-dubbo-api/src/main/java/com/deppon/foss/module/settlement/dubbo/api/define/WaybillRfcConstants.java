/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/WaybillRfcConstants.java
 * 
 * FILE NAME        	: WaybillRfcConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.util.Date;

/**
 * 
 * 更改单用到的常量
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @since
 * @version
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:30:15
 */
public class WaybillRfcConstants {
	
	/**
	 * 代办处理中
	 */
	public final static String TODO_STATUS = "PROCESSING";
	/**
	 * 代办处理超时
	 */
	public final static String TODO_STATUS_OUTHANDLE = "OUTHANDLE";
	
	/**
	 * 验证运单号中全是数字的正则表达式
	 */
	public static final String WAYBILLNO_REG_EXPRSSION = "[0-9]*";
	/**
	 * 定义运单长度
	 */
	public static final int WAYBILLNO_LENGTH_8 = 8;
//	public static final int WAYBILLNO_LENGTH_9 = 9;
	public static final int WAYBILLNO_LENGTH_10 = 10;
	/**
	 * 更改单受理类型 CHECK 审核  PROCESS 处理
	 */
	public static final String WAYBILL_RFC_CHECK = "CHECK";
	public static final String WAYBILL_RFC_PROCESS = "PROCESS";
	/**
	 * 执行节点分隔字符
	 */
	public static final String ACTUATING_NODE_SPLIT_CHARACTER = ",";
	/**
	 * 设置最大日期(Tue Dec 31 23:59:59 CST 2999)
	 */
	public static final Date MAX_DATE_RFC = new Date(32503651199343l);
	
	/**
	 * 设置时间 （时）
	 */
	public static final int TIME_TWENTY_THREE = 23;
	
	/**
	 * 设置时间 （分、秒）
	 */
	public static final int TIME_FIFTY_NINE = 59;
	
	/**
	 * 默认的待办事项扫描天数
	 */
	public static final String DEFAULT_DAYS = "3";
	
	/**
	 * 运单变更
	 */
	public static final String WAYBILL_RFC = "WAYBILL_RFC";
	
	/**
	 * 变更来源
	 */
	public static final String WAYBILL_RFC_SOURCE = "WAYBILL_RFC_SOURCE";  
	
	/**
	 * 变更类型
	 */
	public static final String WAYBILL_RFC_TYPE = "WAYBILL_RFC_TYPE";  
	
	/**
	 * 更改单状态
	 */
	public static final String WAYBILL_RFC_STATUS = "WAYBILL_RFC_STATUS";  
	
	/**
	 * 更改单核销状态
	 */
	public static final String WAYBILL_RFC_WRITE_OFF_STATUS = "WAYBILL_RFC_WRITE_OFF_STATUS";  
	
	/**
	 * <br><B>货物库存状态</B><br>
	 * <br>开单部门库存 		RECEIVE_STOCK
	 * <br>开单部门已出库	RECEIVE_STOCK_OUT
	 * <br>中转部门库存		TRANSFER_STOCK
	 * <br>中转部门已库存	TRANSFER_STOCK_OUT
	 * <br>收货部门库存		DELIVERY_STOCK
	 * <br>收货部门已出库	DELIVERY_STOCK_OUT
	 */
	public static final String STOCK_STATUS = "WAYBILL_RFC_STOCK_STATUS";
	/**
	 * 变更来源 -- 客户要求
	 */
	public static final String CUSTOMER_REQUIRE = "CUSTOMER_REQUIRE";  

	/**
	 * 变更来源 -- 内部要求
	 */
	public static final String INSIDE_REQUIRE = "INSIDE_REQUIRE";  

	/**
	 * 更改单状态 -- 待审核、起草
	 */
	public static final String PRE_AUDIT = "PRE_AUDIT";  

	/**
	 * 待受理、审核同意
	 */
	public static final String PRE_ACCECPT = "PRE_ACCECPT";  
	
	/**
	 * 已同意
	 */
	public static final String ACCECPT = "ACCECPT";  

	/**
	 * 审核拒绝
	 */
	public static final String AUDIT_DENY  = "AUDIT_DENY";  
	
	/**
	 * 受理拒绝
	 */
	public static final String ACCECPT_DENY = "ACCECPT_DENY";  
	

	/**
	 * 更改单类型-- 更改
	 */
	public static final String CUSTOMER_CHANGE = "CUSTOMER_CHANGE";  
	
	/**
	 * 转运
	 */
	public static final String TRANSFER = "TRANSFER";  

	/**
	 * 返货
	 */
	public static final String RETURN = "RETURN";  

	/**
	 * 作废
	 */
	public static final String INVALID = "INVALID";  
	
	/**
	 * 更改
	 */
	public static final String INSIDE_CHANGE = "INSIDE_CHANGE";  
	
	/**
	 * 中止
	 */
	public static final String ABORT = "ABORT";  
	

	/**
	 * 核销状态
	 */
	public static final String BILL_WAYLLBAY_WRITEOFF_STATUS = "BILL_WAYLLBAY_WRITEOFF_STATUS";
	
	/**
	 * 已核销
	 */
	public static final String WRITE_OFF_SUCCESS = "WRITE_OFF_SUCCESS";  
	/**
	 * 核销不通过
	 */
	public static final String WRITE_OFF_FAILURE = "WRITE_OFF_FAILURE";  
	/**
	 * 未核销
	 */
	public static final String NO_WRITE_OFF = "NO_WRITE_OFF";  

	
	/**
	 * 货物库存状态 -- 收货部门库存
	 */
	public static final String RECEIVE_STOCK = "RECEIVE_STOCK";  
	/**
	 * 收货部门已出库
	 */
	public static final String RECEIVE_STOCK_OUT = "RECEIVE_STOCK_OUT";  
	/**
	 * 中转部门库存
	 */
	public static final String TRANSFER_STOCK = "TRANSFER_STOCK";  
	/**
	 * 中转部门已出库
	 */
	public static final String TRANSFER_STOCK_OUT = "TRANSFER_STOCK_OUT";  
	/**
	 * 到达部门库存
	 */
	public static final String DELIVERY_STOCK = "DELIVERY_STOCK";  
	/**
	 * 到达部门已出库
	 */
	public static final String DELIVERY_STOCK_OUT = "DELIVERY_STOCK_OUT";  
	

	/**
	 * 生成待办时库存状态--始发配载部门
	 */
	public static final String FIRST_LOAD_STOCK = "FIRST_LOAD_STOCK";
	/**
	 * 已出始发配载部门
	 */
	public static final String FIRST_LOAD_STOCK_OUT = "FIRST_LOAD_STOCK_OUT";
	
	/**
	 * 查询条件(全部),便于使用
	 */
	public static final String SEARCH_TYPE_ALL = "ALL";
	
	/**
	 * 审核代理状态-已中止
	 */
	public static final String RFC_AGENT_STATUS_ABORT = "ABORT";
	
	/**
	 * 审核代理状态-已生效
	 */
	public static final String RFC_AGENT_STATUS_AVALIABLE = "AVALIABLE";
	
	/**
	 * 审核代理状态-未生效
	 */
	public static final String RFC_AGENT_STATUS_INVALID = "INVALID";
	
	/**
	 * 审核代理状态-已删除
	 */
	public static final String RFC_AGENT_STATUS_DELETED = "DELETED";

	/**
	 * 处理待办URL
	 */
	public static String TODO_ACTION_URL = "T_waybill-todoActionIndex|处理待办事项|/pkp-deliver-web/waybill/todoActionIndex.action";
	
	/**
	 * 提供给结算---财务类更改单打印的差额的常量	
	 */
	public static final String RFCITEMS_TOTAL_FEE="totalFee";
		
	/**
	 * 提供给结算--财务类更改单打印时的类型
	 */
	public  static final String MODIFYBILL_PRINT_WAYBILLRFC="WAYBILLRFC";
	
	/**
	 * 更改单变更项为空默认值
	 */
	public static final String WAYBILLRFC_NULL_DEFAULT="-";
	
	/**
	 * PDA变更类型
	 */
	public static final String PDA_CHANGE="PDA_CHANGE";
	
	/**
	 * 货物包装件数变更
	 */
	public static final String PACK_PIECE_CHANGE = "PIECE_CHANGE";
	
	/**
	 * 零担快递代理
	 */
	public static final String AGENTTYPE_EXPRESS = "EXPRESS";
	
	/**
	 * 转寄退回-- 客户原因
	 */
	public static final Integer return_CUSTOMER_REASON = 1;  
	
	/**
	 * 转寄退回-- 我司原因
	 */
	public static final Integer return_COMPANY_REASON = 2;  
	
	
	/**
	 * 全部 liyongfei 新增运单类型
	 */
	public static final String WAYBILL_TYPE_ALL ="ALL";
	//普通运单
	public static final String WAYBILL_TYPE_NORMAL ="NORMAL";
	//电子运单
	public static final String WAYBILL_TYPE_EWAYBILL ="EWAYBILL";
	
	//快递批量发更改的默认原因为：
	public static final String WAYBILL_BATCHCHANGE_REASON = "批量导入准确重量";
	
	//快递批量发更改的更改状态
	public static final String WAYBILL_BATCH_RFC_FAIL ="RFC_FAIL";//更改失败
	public static final String WAYBILL_BATCH_RFC_SUCCESS="RFC_SUCCESS";//更改成功
	public static final String WAYBILL_BATCH_RFC_IMPORTED="RFC_IMPORTED";//已导入
	public static final String WAYBILL_BATCH_UN_CHANGE="UN_CHANGE";//未更改
	
	//快递批量发更改开关
	public static final String WAYBILL_BATCH_RFC_SWITCH = "WAYBILL_BATCH_RFC_SWITCH";
	
	/**
	 * 更改单变更项常量
	 * 提货网点、包装(纸木纤托膜其他)、件数、货物类型、运输性质、提货方式、单号
	 * customerPickupOrgName+paper+wood+fibre+salver+membrane+otherPackage+goodsQtyTotal+goodsTypeCode+productCode+receiveMethod+waybillNo
	 * @author 102246-foss-shaohongliang
	 *
	 */
	public static class WaybillRfcChangeItemsConstants{		
		
		//单号
		public static final String ITEM_WAYBILL_NO = "waybillNo";		
		//目的站
		public static final String ITEM_CUSTOMER_PICKUP_ORG_NAME = "customerPickupOrgName";		
		//包装(纸)
		public static final String ITEM_PAPER = "paper";
		//包装(木)
		public static final String ITEM_WOOD = "wood";
		//包装(纤)
		public static final String ITEM_FIBRE = "fibre";
		//包装(托)
		public static final String ITEM_SALVER = "salver";
		//包装(膜)
		public static final String ITEM_MEMBRANE = "membrane";
		//包装(其他)
		public static final String ITEM_OTHER_PACKAGE = "otherPackage";
		//件数
		public static final String ITEM_GOODS_QTY_TOTAL = "goodsQtyTotal";
		//运输性质
		public static final String ITEM_PRODUCT_CODE = "productCode";
		//货物类型（AB）
		public static final String ITEM_GOODS_TYPE_CODE = "goodsTypeCode";
		//提货方式
		public static final String ITEM_RECEIVE_METHOD = "receiveMethod";
		
		/**
		 * 涉及到标签更改的变更项
		 */
		public static final String[] LABELGOOD_ITEMS = new String[]{
			ITEM_WAYBILL_NO,
			ITEM_CUSTOMER_PICKUP_ORG_NAME,	
			ITEM_PAPER,
			ITEM_WOOD,
			ITEM_FIBRE,
			ITEM_SALVER,
			ITEM_MEMBRANE,
			ITEM_OTHER_PACKAGE,
			ITEM_GOODS_QTY_TOTAL,
			ITEM_PRODUCT_CODE,
			ITEM_GOODS_TYPE_CODE,
			ITEM_RECEIVE_METHOD,
			};
		
	}
	
	
}