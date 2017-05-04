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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/define/ArriveSheetConstants.java
 * 
 * FILE NAME        	: ArriveSheetConstants.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

import java.util.HashMap;
import java.util.Map;

/**
 * : 到达联模块常量
 * 
 * @author dp-dengtingting
 * 
 */
public class ArriveSheetConstants {

	/**
	 *  生成 
	 */
	public static final String STATUS_GENERATE = "NEW";
	/**
	 *  派送中
	 */
	public static final String STATUS_DELIVER = "DELIVER";
	/**
	 *  签收
	 */
	public static final String STATUS_SIGN = "SIGN";
	/**
	 *  到达联状态--拒收
	 */
	public static final String STATUS_REFUSE = "REFUSE";
	/** 
	 * 签收情况--异常
	 */
	public static final String SITUATION_UNNORMAL = "UNNORMAL";
	/**
	 * 到达联广告值
	 */
	public static final String ARRIVESHEET_ADCODE ="arriveSheetAdCode";
	
	/**
	 * 逗号
	 */
	public static final String COMMA = ",";
	/**
	 * 反斜杠
	 */
	public static final String BACKSLASH = "/";
	/**
	 * 常量Y
	 */
	public static final String Y = "Y";
	/**
	 * 零
	 */
	public static final Integer ZERO = 0;	
	public static final String SPAN = "　　";
	public static final String SPAN1 = "　";
	
	
	//---------------------------------签收情况
	/** 
	 * 签收情况--正常签收
	 */
	public static final String SITUATION_NORMAL_SIGN = "NORMAL_SIGN";
	/**
	 *  签收情况---异常签收
	 */
	public static final String SITUATION_UNNORMAL_SIGN = "UNNORMAL_SIGN";
	/** 
	 * 签收情况--异常-破损
	 */
	public static final String SITUATION_UNNORMAL_BREAK = "UNNORMAL_BREAK";
	/**
	 *  签收情况---异常-潮湿
	 */
	public static final String SITUATION_UNNORMAL_DAMP = "UNNORMAL_DAMP";
	/** 
	 * 签收情况---异常-污染
	 */
	public static final String SITUATION_UNNORMAL_POLLUTION = "UNNORMAL_POLLUTION";
	/** 
	 * 签收情况---异常内物短少
	 */
	public static final String SITUATION_UNNORMAL_GOODSHORT= "UNNORMAL_GOODSHORT";
	/** 
	 * 签收情况--异常-其他
	 */
	public static final String SITUATION_UNNORMAL_ELSE = "UNNORMAL_ELSE";
	/**
	 *  签收情况---货物拉回
	 */
	public static final String SITUATION_GOODS_BACK = "GOODS_BACK";
	/** 
	 * 签收情况---部分签收
	 */
	public static final String SITUATION_PARTIAL_SIGN = "PARTIAL_SIGN";
	/**
	 *  签收情况---异常-违禁品
	 */
	public static final String SITUATION_UNNORMAL_CONTRABAND = "UNNORMAL_CONTRABAND";
	/** 
	 *  签收情况---异常-弃货
	 */
	public static final String SITUATION_UNNORMAL_ABANDONGOODS = "UNNORMAL_ABANDONGOODS";
	/** 
	 *  签收情况---同票多类异常
	 */
	public static final String SITUATION_UNNORMAL_SAMEVOTEODD = "UNNORMAL_SAMEVOTEODD";
	/**
	 * 签收情况---异常-丢货
	 */
	public static final String SITUATION_UNNORMAL_LOSTCARGO = "UNNORMAL_LOSTCARGO";
	
	/**
	 * 拉回原因---客户要求其他时间送货（客户）
	 */
	public static final String PULLBACK_REASON_OTHER_TIME_DELIVERY = "OTHER_TIME_DELIVERY";
	/**
	 * 拉回原因---其他原因306566
	 */
	 
	public static final String PULLBACK_REASON_OTHER = "OTHER";


	public static final Map<String, String> NO_PRINT_FEE = new HashMap<String, String>();
	static {
		// 费用明细中不需要的费用项目
		NO_PRINT_FEE.put("JH", "JH");// 接货费
//		NO_PRINT_FEE.put("SH", "SH");// 送货费
		NO_PRINT_FEE.put("BF", "BF");// 保费
		NO_PRINT_FEE.put("HK", "HK");// 代收货款手续费
		NO_PRINT_FEE.put("BZ", "BZ");// 包装费
//		NO_PRINT_FEE.put("QS", "QS");// 签收回单
		NO_PRINT_FEE.put("FRT", "FRT");// 运费
		NO_PRINT_FEE.put("QT", "QT");// 其他费用
	}
	// 重量
	public static final String WEIGHT_UNIT = "kg ";
	// 体积
	public static final String VOLUME_UNIT = "m3 ";
	public static final String XINGHAO = " ***";
	
	/**
	 * 到达联打印类型 -- 针式
	 */
	public static final String ARRIVE_SHEET_PRINT_TYPE_PIN = "1";
	/**
	 * 到达联打印类型 -- 激光原版
	 */
	public static final String ARRIVE_SHEET_PRINT_TYPE_LASER_OLD = "2";
	/**
	 * 到达联打印类型 -- 激光新版
	 */
	public static final String ARRIVE_SHEET_PRINT_TYPE_LASER_NEW = "3";
	
	/*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** *** 到达联变量  *** *** *** *** *** *** *** *****/ 
	/*
	 * 条码
	 */
	public static final String ARRIVE_SHEET_PRINT_barcode = "barcode";
	/*
	 * 到达联编号
	 */
	public static final String ARRIVE_SHEET_PRINT_arriveSheetNo = "arriveSheetNo";
	/*
	 * 发货客户名称(托运人公司)
	 */
	public static final String ARRIVE_SHEET_PRINT_deliveryCustomerName = "deliveryCustomerName";
	/*
	 * 发货客户编码(联系人编码)
	 */
	public static final String ARRIVE_SHEET_PRINT_deliveryCustomerCode = "deliveryCustomerCode";
	/*
	 * 收货客户名称(收货人/公司)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveCustomerName = "receiveCustomerName";
	/*
	 * 收货客户名称(收货人/公司)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveCustomerNameA = "receiveCustomerNameA";
	/*
	 * 收货客户手机(联系电话)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveCustomerMobilephone = "receiveCustomerMobilephone";
	/*
	 * 收货具体地址(地址)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveCustomerAddress = "receiveCustomerAddress";
	/*
	 * 货物名称(货物品名)
	 */
	public static final String ARRIVE_SHEET_PRINT_goodsName = "goodsName";
	/*
	 * 交货方式
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveMethod = "receiveMethod";
	/*
	 * 备注(其他服务)
	 */
	public static final String ARRIVE_SHEET_PRINT_outerNotes = "outerNotes";
	/*
	 * 运单号
	 */
	public static final String ARRIVE_SHEET_PRINT_waybillNo = "waybillNo";
	/*
	 * 流水号
	 */
	public static final String ARRIVE_SHEET_PRINT_serial = "serial";
	/*
	 * 货物包装(货物基本信息-包装)
	 */
	public static final String ARRIVE_SHEET_PRINT_goodsPackage = "goodsPackage";
	/*
	 * (件数)
	 */
	public static final String ARRIVE_SHEET_PRINT_goodsQty = "goodsQty";
	public static final String ARRIVE_SHEET_PRINT_goodsQty1 = "goodsQty1";
	/*
	 * (件数)货物总重量
	 */
	public static final String ARRIVE_SHEET_PRINT_goodsWeightTotal = "goodsWeightTotal";
	/*
	 * 货物总体积
	 */
	public static final String ARRIVE_SHEET_PRINT_goodsVolumeTotal = "goodsVolumeTotal";
	/*
	 * 运费计费类型
	 */
	public static final String ARRIVE_SHEET_PRINT_billingType = "billingType";
	/*
	 * 计费重量
	 */
	public static final String ARRIVE_SHEET_PRINT_billWeight = "billWeight";
	/*
	 * 收货市(始发站FROM)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveCustomerCityCode = "receiveCustomerCityCode";
	/*
	 * 目的站(目的站TO)
	 */
	public static final String ARRIVE_SHEET_PRINT_targetOrgCode = "targetOrgCode";
	/*
	 * 收货市
	 */
	public static final String ARRIVE_SHEET_PRINT_deliveryCustomerCityCode = "deliveryCustomerCityCode";
	/*
	 * 运输方式
	 */
	public static final String ARRIVE_SHEET_PRINT_transportType = "transportType";
	/*
	 * 【收货部门(出发部门)+地址+发货网点电话】(发货网点电话)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveOrgCodeAndPhone = "receiveOrgCodeAndPhone";
	/*
	 * 【提货网点+提货网点电话】（提货网点电话）
	 */
	public static final String ARRIVE_SHEET_PRINT_deliveryOrgCodeAndPhone = "deliveryOrgCodeAndPhone";
	/*
	 * 提货网点(制单人/时间)
	 */
	public static final String ARRIVE_SHEET_PRINT_createAndPrintTime = "createAndPrintTime";
	
	/*
	 * 开单人和开单时间
	 */
	public static final String ARRIVE_SHEET_PRINT_billUserAndTime = "billUserAndTime";
	/*
	 * 打印次数
	 */
	public static final String ARRIVE_SHEET_PRINT_printTime = "printNum";	
	
	/*
	 * 保价声明价值(保价声明价值)
	 */
	public static final String ARRIVE_SHEET_PRINT_insuranceAmount = "insuranceAmount";
	/*
	 * 代收货款(代收货款)
	 */
	public static final String ARRIVE_SHEET_PRINT_codAmount = "codAmount";
	/*
	 * (货物基本信息-费率)
	 */
	public static final String ARRIVE_SHEET_PRINT_unitPrice = "unitPrice";
	/*
	 * (货物基本信息-运费)
	 */
	public static final String ARRIVE_SHEET_PRINT_transportFee = "transportFee";
	/*
	 * 现付
	 */
	public static final String ARRIVE_SHEET_PRINT_prePayAmount = "prePayAmount";
	/*
	 * 到付
	 */
	public static final String ARRIVE_SHEET_PRINT_toPayAmount = "toPayAmount";
	/*
	 * 保管费
	 */
	public static final String ARRIVE_SHEET_PRINT_storageCharge = "storageCharge";
	/*
	 * 保价费
	 */
	public static final String ARRIVE_SHEET_PRINT_insuranceFee = "insuranceFee";
	/*
	 * 其他费
	 */
	public static final String ARRIVE_SHEET_PRINT_ortherFree = "ortherFree";
	public static final String ARRIVE_SHEET_PRINT_ortherFree1 = "ortherFree1";
	
	/*
	 * 左上角广告
	 */
	public static final String ARRIVE_SHEET_PRINT_arriveAd = "arriveAd";
	/*
	 * 左下角广告
	 */
	public static final String ARRIVE_SHEET_PRINT_changeInfo = "changeInfo";
	/*
	 * 预约送货时间
	 */
	public static final String ARRIVE_SHEET_PRINT_deliverDate = "deliverDate";
	/*
	 * 送货要求
	 */
	public static final String ARRIVE_SHEET_PRINT_deliverRequire = "deliverRequire";
	/*
	 * (货物基本信息-发货网点制单人/时间)
	 */
	public static final String ARRIVE_SHEET_PRINT_receiveOrgCode = "receiveOrgCode";
	/*
	 * (货物基本信息-提货网点 制单人/时间)
	 */
	public static final String ARRIVE_SHEET_PRINT_customerPickupOrgCode = "customerPickupOrgCode";
	/*
	 * 背景图
	 */
	public static final String ARRIVE_SHEET_PRINT_imgbkg = "imgbkg";
	/*
	 * 条码
	 */
	public static final String ARRIVE_SHEET_PRINT_bcodeimg = "bcodeimg";
	public static final String ARRIVE_SHEET_PRINT_bcodeimg1 = "bcodeimg1";
	public static final String ARRIVE_SHEET_PRINT_bcodeimg2 = "bcodeimg2";
	/*
	 * 备注
	 */
	public static final String ARRIVE_SHEET_PRINT_content = "content";
	/*
	 * 签收提货方式--自提
	 */
	public static final String SIGN_RECEIVE_METHOD_PICKUP = "PICKUP";
	/*
	 * 签收提货方式--派送
	 */
	public static final String SIGN_RECEIVE_METHOD_DELIVER = "DELIVER";
	
	
	public static final String ARRIVE_SHEET_PRINT_SUFFIX_ONE = "_1";
	public static final String ARRIVE_SHEET_PRINT_SUFFIX_TWO = "_2";
	
	public static final Map<String, String> oldArriveSheetTemplate = new HashMap<String, String>();
	static {
		oldArriveSheetTemplate.put("arriveSheet1","原普通到达联模版");
		oldArriveSheetTemplate.put("arriveSheet2","原激光旧版到达联模版");
		oldArriveSheetTemplate.put("arriveSheet3","原激光新版到达联模版");
	}
	public static final Map<String, String> newArriveSheetTemplate = new HashMap<String, String>();
	static {
		newArriveSheetTemplate.put("arriveSheet11","新普通到达联模版");
		newArriveSheetTemplate.put("arriveSheet22","新激光旧版到达联模版");
		newArriveSheetTemplate.put("arriveSheet33","新激光新版到达联模版");
	}
	
	/** 
	 * 部门查询起始. 
	 */
	public static final int BEGIN_NUM = 0;
	/** 
	 * 部门查询起始. 
	 */
	public static final int PAGE_NUM = 100;
	/** 
	 * 默认值 NO. 
	 */
	public static final String DEFAULT_NO = "NO";
	/** 
	 * 大客户标记
	 */
	public static final String VIP = "VIP";
	
	/**
	 * 数据来源为阿里巴巴商城
	 */
	public static final String ORDERCHANNEL = "ALIBABAMRO";
	
	public static final String DELIVERYCUSTOMERNAMEALBB="  采购商城";
}