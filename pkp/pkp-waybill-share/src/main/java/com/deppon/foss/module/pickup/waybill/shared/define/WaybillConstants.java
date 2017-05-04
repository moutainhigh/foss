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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/define/WaybillConstants.java
 * 
 * FILE NAME        	: WaybillConstants.java
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
package com.deppon.foss.module.pickup.waybill.shared.define;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;

/**
 * 词条代码:WAYBILL_STATUS
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2013-2-25 下午4:11:40, </p>
 * @author foss-sunrui
 * @date 2013-2-25 下午4:11:40
 * @since
 * @version
 */
public class WaybillConstants {
	
	//角色：400客服的角色ID
	public final static String PKP_400_CS_REP="PKP_400_CS_REP";
	/**
	 * 返货管理界面常量：受理状态、返货类型
	 * @author 201287
	 * @date 2015年2月5日 15:27:15
	 */
	/****************开始**************************/
	/*受理状态*/
	//受理状态:已受理
	public final static String ACCEPTSTATUS_HANDLED="HANDLED";
	//受理状态:未受理
	public final static String ACCEPTSTATUS_REFUSED="REFUSED";
	//受理状态：已退回
	public final static String ACCEPTSTATUS_RETURNED="RETURNED";
											
	/*返货类型*/
	//返货类型：同城转寄
	public final static String RETURNTYPE_SAME_CITY="SAME_CITY";
	//返货类型：非同城转寄
	public final static String RETURNTYPE_OTHER_CITY="OTHER_CITY";
	//返货类型：客户拒收
	public final static String RETURNTYPE_CUSTORMER_REFUSE="CUSTORMER_REFUSE";
	//返货类型：继续派送
	public final static String RETURNTYPE_CONTINUE_SENDING="CONTINUE_SENDING";
	//返货类型：滞留件
	public final static String RETURNTYPE_DETAINED_GOODS="DETAINED_GOODS";
	//返货类型：7天返货
	public final static String RETURNTYPE_SEVEN_DAYS_RETURN="SEVEN_DAYS_RETURN";
	//返货类型：外发 
	public final static String RETURNTYPE_SEND_OUT="SEND_OUT";
	//返货类型：外发3天返货 
	public final static String RETURNTYPE_SEND_OUT_THREE_DAYS_RETURN="OUTBOUND_THREE_DAYS_RETURN";
	//返货类型:退回
	public final static String RETURN_BACK="RETURN_BACK";
	//返货原因：我司原因  
	public final static String RETURNREASON_COMPANY_REASON="COMPANY_REASON";
	//返货原因：客户原因
	public final static String RETURNREASON_CUSTOMER_REASON="CUSTOMER_REASON";
	//原因明细:转寄退回件原因内新增原因明细：发货人原因，收货人原因，贴错单和其他Detaile
	//发货人原因
	public final static String REASON_DETAIL_SEND_REASON="SEND_REASON";
    //收货人原因
	public final static String REASON_DETAIL_REVICE_REASON="COLLECT_REASON";
	//贴错单
	public final static String REASON_DETAIL_ERROR_REASON="STICK_WRONG";
	//其他
	public final static String REASON_DETAIL_OTHER_REASON="ORTHER_WRONG";
	
	/****************结束*************************/
	
	
	//发票标记
	public final static String INVOICE_TYPE_02="02—非运输专票6%";
	public final static String INVOICE_TYPE_01="01—运输专票11%";
	public final static String INVOICE_02="INVOICE_TYPE_02";
	public final static String INVOICE_01="INVOICE_TYPE_01";
	public final static String INVOICE_TYPE = "invoiceTab";	//zxy 20131220 DEFECT-987 新增:发票标记
	public final static String BIGTICKET = "bigTicket";	//zxy 20131220 DEFECT-987 新增:发票标记
	public final static String DELIVERY_CUSTOMER_CODE = "deliveryCustomerCode"; //wutao 
	public final static String RECEIVE_CUSTOMER_CODE = "receiveCustomerCode";//wutao
	
	public final static String DELIVERY_CENTRALIZED_SETTLEMENT ="startCentralizedSettlement";
	public final static String RECEIVE_CENTRALIZED_SETTLEMENT = "arriveCentralizedSettlement";
	
	//人民币对港币汇率
	public final static  String RMB_AGAINST_HK = "RMB_AGAINST_HK";
	
	public final static  String HK_UNIT ="HK$:";
	public final static  String RMB = "￥";
	
	//==============begin配合综合基础数据里定义的专线偏线用于查询网点==================
	public final static  String BRANCH_TYPE_QYPX="汽运偏线";
	public final static  String BRANCH_TYPE_KYPX="空运";
	public final static  String BRANCH_TYPE_QYZX="汽运专线";
	//汽运偏线
	public final static  String PX = "PX";
	//空运
	public final static  String KY = "KY";
	//汽运专线
	public final static  String ZX = "ZX";
	//==============end配合综合基础数据里定义的专线偏线用于查询网点==================
	
	//四位自动补零
	public final static  String FORMATSTR = "%04d";
	
	public final static  String COMMA = ",";//逗号
	
	//运单打印大陆版1 默认全打
	public final static String PRINTTEMPLATES_ONE = "printWaybill";
	
	public final static String PRINTTEMPLATES_VALUE_ONE ="运单打印大陆版";
	
	//运单打印香港版1 默认全打
	public final static String PRINTTEMPLATES_HONGKONG_ONE = "printwaybillhongkong";
	
	public final static String PRINTTEMPLATES_VALUE_HONGKONG_ONE ="运单打印香港版";
	
	//pda已补录
	public final static String PDA_ACTIVE = "PDA_ACTIVE";
	
	//pda代办
	public final static String PKP_PDA_WAYBILL = "PKP_PDA_WAYBILL";
		
	//成功标识
	public final static String SUCCESS = "success";
	
	//失败标识
	public final static String FAIL = "fail";
	
	//体积上限
	public final static String VOLUME_UPLIMIT = "9999999.99";
	
	//运单常量(标示是开单)
	public final static String WAYBILL = "WAYBILL";
	
	//运单号
	public final static String WAYBILL_NO = "WAYBILL_NO";
	
	//集中开单部门编码
	public final static String FOCUS_DEPT_CODE = "FOCUS_DEPT_CODE";
	
	//集中开单部门名称
	public final static String FOCUS_DEPT_NAME = "FOCUS_DEPT_NAME";
	
	//新开过的的单子
	public final static String NEW_WAYBILL_NOS = "NEW_WAYBILL_NOS";
	
	public final static String INTERVAL = "100";//运单号连续间隔绝对值
	
	public final static int PHONE_LENGTH = 80;//运单号连续间隔绝对值
	
	//运单在线提交状态
	public final static String SUBMIT_STATE = "SUBMIT";
	
	//费用明细-手动输入ID
	public final static String HAND_INPUT = "shoudongshuruid";
	
	//运单离线暂存状态
	public final static String OFF_LINE_TEMP_SAVE = "TEMP_SAVE";
	
	//保险声明价值上限
	public final static int INSURANCE_LIMIT = 5000;
	
	//金额上限
	public final static int AMOUNT_LIMIT = 10000;
	
	
	//送货进仓
	public final static String DELIVER_STORAGE_NAME = "送货进仓;";
	/**
	 * 更改单时调用中转参数方法配置
	 */
	public static final String WAYBILLRFC_TRF_METHOD="SD";
	
	//贵重物品
	public final static String VALUE_GOODS_NAME = "贵重物品;";
	
	//外请车司机默认工号
	public final static String OUTER_DRIVER_CODE = "000000";
	
	//汽运
	public final static String HIGHWAYS = "HIGHWAYS";
	
	/**
	 * 长度
	 */
	public final static int LENGTH_FIFTY = 50;
	
	public final static String ONLINE_LOGIN = "ONLINE_LOGIN";//在线
	
	public final static String LOGIN_TYPE = "user_loginType";//登录类型
	
	//待推送
	public final static String SYNC_PENDING = "sync_pending";
	
	//已推送
	public final static String SYNCED = "synced";
	
	
	/**
	 * 常量
	 */
	public final static  String MOBILEPHONE_LENGTH = "11";//移动号码长度
	
	public final static  String MOBILEPHONE_FIRST = "1";//手机第一位的值
	
	public final static  String BILLINGWAY_WEIGHT = "WEIGHT";//按重量计费
	
	public final static  String BILLINGWAY_VOLUME = "VOLUME";//按体积计费
	
	public final static  String DEPARTURE_TYPE = "1";//出发网点组
			
	public final static  String ARRIVAL_TYPE =  "2";//到达网点组
	
	public final static  String PRINT_INFO_WAYBILL = "WAYBILL";//打印信息表开单打印
	
	public final static  String PRINT_INFO_WAYBILLRFC = "WAYBILLRFC";//打印信息表更改单打印
	
	public final static  String WAYBILL_FOCUS = "FOCUS";//集中开单
	
	public final static  String SEARCH_PICTURE = "SEARCH_PICTURE";//图片开单查询
	public final static  String WAYBILL_SALE_DEPARTMENT = "SALE_DEPARTMENT";//营业部开单
	public final static  String WAYBILL_BATCHWAYBILL = "BATCHWAYBILL";//批量开单
	public final static  String WAYBILL_PICTURE = "PICTURE";//图片开单
	
	public final static  String VOLUME_M = "1000000";//尺寸计算体积单位换算
	public final static  String EC_GOODS = "PCP";//精准包裹
	public final static  String TRUCK_FLIGHT = "FLF";//精准卡航
	public final static  String AIR_FLIGHT = "AF";//精准空运
	public final static  String DEAP = "DEAP";//商务专递
	public final static  String FSF_FLIGHT="FSF";//精准城运
	public final static  String LRF_FLIGHT="LRF" ;//精准汽运(长途)
	public final static  String SRF_FLIGHT="SRF" ;//精准汽运(短途)
	public final static  String AIR_FREIGHT="AIR_FREIGHT" ;//空运
	public final static  String HIGHWAYS_REFERRALS = "PLF";//汽运偏线
	public final static  String TRANS_VEHICLE = "TRANS_VEHICLE";//汽运
	
	public final static String BIG_DOOR_TO_DOOR = "DTD" ; //精准大票.经济件
	public final static String BIG_YARD_TO_YARD = "YTY" ; //精准大票.标准件
	
	/**
	 * 设置最大日期(Tue Dec 31 23:59:59 CST 2999)
	 */
	public final static  Date MAX_DATE = new Date(32503651199343l);
	
	
	public final static  String AIR_FREIGHT_PRICE = "AIR_FREIGHT";//查询价格时候返回的价格类型product item code
	
	public final static  String  PERMILLAGE = "1000";//千分比
	
	public final static  String  TON_KILO = "1000";//吨换算成公斤
	
	public final static  String  AIR_GOODSTYPE_NAME = "全部";//空运货物类型名称
	
	public final static  String  AIR_GOODSTYPE_CODE = "H00001";//空运货物类型编码
	
	
	/**
	 * WAYBILL_STATUS
	 * 运单生效状态
	 */
	public final static  String EFFECTIVE = "EFFECTIVE"; // 已生效
	public final static  String OBSOLETE = "OBSOLETE"; // 已作废
	public final static  String ABORTED = "ABORTED"; // 已中止
	public final static  String UNACTIVE = "UNACTIVE"; //电子运单待激活
	
	/**

	 * 送货费
	 */
	public final static  String SHAZ = "SHAZ"; // 送货安装送货费
	public final static  String SHSL = "SHSL_JZ"; // 送货上楼（家装）送货费
	public final static  String SHBSL = "SHBSL_JZ"; //送货不上楼（家装）送货费
	/**
	 * WAYBILL_PENDING_STATUS
	 */
	
	/**
	 * 
	 */
	public final static  String WAYBILL_STATUS_OFFLINE_PENDING="OFFLINE_PENDING";//离线运单，待提交
	/**
	 * 
	 */
	public final static  String WAYBILL_STATUS_PENDING_ACTIVE="PDA_ACTIVE";//PDA已补录
	/**
	 * 
	 */
	public final static  String WAYBILL_STATUS_PC_ACTIVE="PC_ACTIVE";//暂存已开单
	
	
	/************* 运单来源 ******************/
	/**
	 * 运单暂存
	 */
	public final static  String WAYBILL_STATUS_PC_PENDING="PC_PENDING";
	/**
	 * PDA补录
	 */
	public final static  String WAYBILL_STATUS_PDA_PENDING="PDA_PENDING";
	/**
	 * serviceType
	 */
	public final static  String WAYBILL_SERVICE_TYPE="12";
	/**
	 * 弃货处理
	 */
	public final static  String WAYBILL_STATUS_GOODS_PENDING="GOODS_PENDING";
	/**
	 * 更改暂存
	 */
	public final static  String WAYBILL_STATUS_RFC_PENDING="RFC_PENDING";
	/**
	 * 更改生效
	 */
	public final static  String WAYBILL_STATUS_RFC_ACTIVE="RFC_ACTIVE";
	
	/**
	 * 电子运单-待激活
	 */
	public final static String WAYBILL_STATUS_EWAYBILL_PENDING = "EWAYBILL_PRE_ACTIVE";
	/**
	 * 电子运单-激活
	 */
	public final static String WAYBILL_STATUS_EWAYBILL_ACTIVE = "EWAYBILL_ACTIVE";
	
	/**
	 * 零担电子运单
	 */
	public final static String WAYBILL_STATUS_LTLEWAYBILL = "LTLEWAYBILL" ;
	
	/**
	 * 字符串处理正则表达式 BEGIN
	 */
	public final static String GOOD_PACKAGE_SLIPT = 
			"(\\d*)(?=纸)|(\\d*)(?=纤)|(\\d*)(?=托)|(\\d*)(?=膜)|(\\d*)(?=木)|([\u4e00-\u9fa5]*)";//货物包装拆分正则表达式，[\u4e00-\u9fa5]表示任意汉字
	/**
	 * 字符串处理正则表达式 end
	 */
	
	/**
	 * 数据字典码terms_code BEGIN
	 */
	public final static  String PICKUP_GOODS = "PICKUPGOODS";//提货方式
	
	public final static  String OUTER_REMARK = "OUTERREMARK";//对外备注
	
	public final static  String PAYMENT_MODE = "SETTLEMENT__PAYMENT_TYPE";//付款方式	注意：设置成结算的付款方式，已经商讨过
	
	public final static  String REFUND_TYPE = "REFUNDTYPE";//退款类型
	
	public final static  String RETURN_BILL_TYPE = "RETURNBILLTYPE";//返单类型
	
	public final static  String PREDICT_FLIGHT = "PREDICTFLIGHT";
			
	public final static  String CUSTOMER_CHANGE = "WAYBILL_RFC_TYPE_CUSTOMER";
					
	public final static  String INSIDE_CHANGE = "WAYBILL_RFC_TYPE_INSIDE";
					
	public final static  String ORDER_TYPE = "ORDERTYPE";//订单类型
			
	public final static  String ORDER_STATUS = "ORDERSTATUS";//订单状态
					
	public final static  String PENDING_TYPE = "PENDINGTYPE";
					
	public final static  String OFFLINE_ACTIVE = "OFFLINEACTIVE";
	
	/**
	 * 返单类别  无需返单
	 */
	public final static  String NOT_RETURN_BILL = "NONE";
	
	/**
	 * 返单类别  原件签收单返回
	 */
	public final static  String RETURNBILLTYPE_ORIGINAL = "ORIGINAL";
	
	/**
	 * 返单类别  传真件签收单返回
	 */
	public final static  String RETURNBILLTYPE_FAX = "FAX";
	
	/**
	 * 返单类别  扫描件返回
	 */
	public final static  String RETURNBILLTYPE_SCANNED = "SCANNED";
	
	/**
	 * 运单到达联传真返回
	 */
	public final static  String RETURNBILLTYPE_ARRIVE = "ARRIVESHEET_FAX";
	
	
	
	public final static  String BILLING_WAY = "BILLINGWAY";//计费方式
	
	public final static  String AIR_GOODS_TYPE = "AIRGOODSTYPE";//空运货物类型
	
	public final static  String PICKUP_GOODS_AIR = "PICKUPGOODSAIR";//空运运输
	
	public final static  String PICKUP_GOODS_HIGHWAYS = "PICKUPGOODSHIGHWAYS";//汽运运输
	
	public final static  String AIR_FLIGHT_TYPE = "AIR_FLIGHT_TYPE"; //航班类别
	
	public final static  String MAKE_WAYBILL_WAY = "MAKE_WAYBILL_WAY"; //合票类型
	
	public final static  String VALUE_GOODS = "VALUABLE_GOODS";//贵重物品
	public final static  String INTERNAL_DELIVERY_TYPE = "INTERNAL_DELIVERY_TYPE";//内部发货类型	
	public final static  String BSE_SOURCE_CATEGORY = "BSE_SOURCE_CATEGORY";//行业货源品类
	public final static  String CRM_CUSTOMER_GROUP = "CRM_CUSTOMER_GROUP";//客户分群
	public final static  String SPECIAL_VALUE_ADDED_SERVICE_TYPE="SPECIAL_VALUE_ADDED_SERVICE_TYPE";//特殊增值服务
	public final static  String SPECIAL_DELIVERY_TYPE="PICKUPGOODSSPECIALDELIVERYTYPE";//特殊提货方式
	public final static  String INSTALLATION_CODE="RGBL";//人工搬楼费code
	/**
	 * 数据字典码terms_code END
	 */
	
	/**
	 * 系统配置参数CODE BEGIN
	 */
	public final static  String VEHICLE_DIRECT_WEIGHT = "1001";//大车直送重量
	
	public final static  String VEHICLE_DIRECT_VOLUME = "1002";//大车直送体积
	
	public final static  String VALUEGOODS_SINGLE_VOLUME = "1003";//单件货物贵重物品按体积判断
	
	public final static  String VALUEGOODS_SINGLE_PRICE = "1004";//单件货物贵重物品声明价值判断
	
	public final static  String VALUEGOODS_MUTI_VOLUME = "1005";//运单货物贵重物品按总体积判断
	
	public final static  String VALUEGOODS_MUTI_PRICE = "1006";//运单货物贵重物品按总声明价值判断
	
	public final static  String WEIGHT_BY_VOLUME_BEGIN = "1007";//货物重量体积比区间起始值
	
	public final static  String WEIGHT_BY_VOLUME_END = "1008";//货物重量体积比区间终止值
	
	public final static  String GOODS_PACKING_VOLUME = "1009";//打木架体积计算
	
	public final static  String VALUEGOODS_WAYBILL_PRICE = "1010";//一票贵重物品平均单件保价判断
	
	public final static  String WHOLEVEHICLE_FEE_RANGE_UP = "1011";//整车约车报价区间上限
	
	public final static  String WHOLEVEHICLE_FEE_RANGE_LOW = "1012";//整车约车报价区间下限
	
	public static final String EXP_MULTI_PIECE_DEPTCODE = "EXP_MULTI_PIECE_DEPTCODE";  //快递可开多件货部门
	
	public static final String GOODS_VOLUME_TOTAL = "goodsVolumeTotal";//打木架总体积

	public static final String CUBC_LIMT_TIMEOUT = "CUBC_LIMT_TIMEOUT";//CUBC接口超时时间

	public static final String CHANGE = "change";

	public static final String CANCEL = "cancel";

	public static final String CUBC_CAN_CANCEL = "能否作废校验，传入描述为订单号";

	public static final String CUBC_CAN_CHANGE = "能否更改校验，传入描述为订单号";

	public static final String CUBC_BE_BEBT = "信用额度校验，传入描述为客户编码";
	/**
	 * 系统配置参数CODE END
	 */


	/**
	 * 付款类型CODE BEGIN
	 */
	
    public final static  String PAYMENT_PRE_PAY = "PAYMENT_PRE_PAY";//预付
	
	public final static  String PAYMENT_TO_PAY = "PAYMENT_TO_PAY";//到付
	
	public final static  String PAYMENT_REAL_PAY = "PAYMENT_REAL_PAY";//手写现付金额
	
	public final static  String PAYMENT_PDA_TOTAL_PAY = "PAYMENT_PDA_TOTAL_PAY";//pda总运费
	/**
	 * 付款类型CODE END
	 */
	
	/**
	 * 扫描类型CODE BEGIN
	 */
    public final static  String SCAN_TYPE_SACN = "SCAN";//正扫
    
    public final static  String SCAN_TYPE_CANCEL = "CANCEL";//反扫
    
	/**
	 * 扫描类型CODE END
	 */	
	
	/**
	 * 货物类型CODE BEGIN
	 */
	
    public final static  String GOODS_TYPE_A = "A";
    
    public final static  String GOODS_TYPE_B = "B";
	/**
	 * 货物类型CODE END
	 */
	

	/**
	 * 空运货物类型 BEGIN
	 */
	
    public final static  String GOODS_TYPE_NORMAL = "NORMAL";//普货
	/**
	 * 空运货物类型 END
	 */
	
	
	
	/**
	 * 提货方式 BEGIND
	 */
    
    public final static  String DELIVER_NOUP = "DELIVER_NOUP";//汽运送货(不含上楼)
    
	public final static  String SELF_PICKUP = "SELF_PICKUP";//汽运自提
	
	public final static  String DELIVER_FREE = "DELIVER";//汽运免费派送
	
	public final static  String DELIVER_STORAGE = "DELIVER_INGA";//汽运送货进仓
	
	public final static  String DELIVER_UP = "DELIVER_UP";//汽运送货（上楼）
	
	public final static  String INNER_PICKUP = "INNER_PICKUP";//汽运内部自提
	
	public final static  String DELIVER_INNER_PICKUP = "DELIVER_INNER_PICKUP";//内部带货送货
	public final static  String DELIVER_FLOOR = "DELIVER_FLOOR";//送货上楼安装（家居）
	
	/**
	 * 大件上楼费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-15下午15:12
	 */
	public final static  String LARGE_DELIVER_UP="LARGE_DELIVER_UP";//大件上楼费
	
	//---------------------------------------------------------------------
	
	public final static  String AIR_PICKUP_FREE = "SELF_PICKUP_FREE_AIR";//空运免费自提
	
	public final static  String AIR_SELF_PICKUP = "SELF_PICKUP_AIR";//空运自提(不含机场提货费)
	
	public final static  String DELIVER_FREE_AIR = "DELIVER_AIR";//空运免费送货
	
	public final static  String AIRPORT_PICKUP = "AIRPORT_PICKUP";//空运机场自提
	
	public final static  String DELIVER_NOUP_AIR = "DELIVER_NOUP_AIR";//送货(不含上楼)
	
	public final static  String DELIVER_UP_AIR = "DELIVER_UP_AIR";//空运送货上楼
	
	public final static  String DELIVER_INGA_AIR = "DELIVER_INGA_AIR";//空运送货进仓
	
	/**
	 * 大件上楼费
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-15下午15:12
	 */
	public final static  String LARGE_DELIVER_UP_AIR="LARGE_DELIVER_UP_AIR";//大件上楼费
	
	/**
	 * 家装送装
	 * @author:262036-foss-huangwei
	 * @date:2015-10-13下午15:12
	 */
	public final static  String EQUIP="EQUIP";//家装送装
	
	
	/**
	 * 其他费用
	 */
	public final static  String OTHERFEE_ZHXX = "ZHXX";//综合服务费
	public final static  String OTHERFEE_RYFJ = "RYFJ";//燃油附加费 - RYFJ
	
	
	/**
	 * 提货方式 END
	 */
	
	/**
	 * 运单价格计算公式 BEGIN
	 */
	public final static  String SERVICE_FEE = "M";
			
	public final static  String TRANS_FEE = "C";
	
	public final static  String WEIGHT_VOLUME = "Z";
	
	public final static  String DISCOUNT = "D";
	
	public final static  String RATE = "R";
	
	public final static  String UNITPRICE = "Q";
	
	public final static  String SERVICE_FEE_AGAIN = "M2";

	
	public final static  String CODE_EXP_ONE = "PR004";
	
	public final static  String CODE_EXP_TWO = "PR005";

	public final static  String CODE_EXP_THREE = "PR006";
	/**
	 * 运单价格计算公式 END
	 */
	
	/**
	 * 付费类型 BEGIN
	 */
	//临时欠款
	public final static  String TEMPORARY_DEBT = "DT";
			
	//到付
	public final static  String ARRIVE_PAYMENT = "FC";
	
	//月结
	public final static  String MONTH_PAYMENT = "CT";

	//现金
	public final static  String CASH_PAYMENT = "CH";
	
	//网上支付
	public final static  String ONLINE_PAYMENT = "OL";
	
	//银行卡
	public final static  String CREDIT_CARD_PAYMENT = "CD";
	
	//电汇telegraphic transfer (TT)
	public final static  String TELEGRAPHIC_TRANSFER = "TT";
	
	//支票
	public final static  String CHECK = "NT";
	
	//付款方式月结
	public final static  String MONTH_END = "MONTH_END";
	
	/**
	 * 付费类型 END
	 */
	
	/**
	 * 退款类型 BEGIN
	 * 
	 */
	public final static  String REFUND_TYPE_THREE_DAY = "R3";//三日退
	
	public final static  String REFUND_TYPE_ONE_DAY = "R1";//即日退
	
	public final static  String REFUND_TYPE_VALUE = "RA";//审核退
	/**
	 * 退款类型 END
	 */
	
	public final static  String REFUND_TYPE_ONE_DAY_CMB = "招商";
	
	public final static  String REFUND_TYPE_ONE_DAY_CCB = "建设";
	
	public final static  String REFUND_TYPE_ONE_DAY_ABC = "农业";
	
	public final static  String REFUND_TYPE_ONE_DAY_ICBC = "工商";
	
	/**
	 * 行政区域级别 BEGIN
	 */
	public final static  String COUNTRY_DEGREE = "1";
	
	public final static  String PROVINCE_DEGREE = "2";
	
	public final static  String CITY_DEGREE = "3";
	
	public final static  String DISTRICT_DEGREE = "4";
	
	public final static  int CUSTOMER_COUNT=100;
	
	/**
	 *   265041 - zhoupengyu
	 *   开单一票多件参数配置(最大件数，体积，重量)
	 * */
	public final static String  ONEGOODSQTYTOTAL_GOODS = "ONEGOODSQTYTOTAL_GOODS";
	
	public final static String  ONEGOODSQTYTOTAL_WEIGHT = "ONEGOODSQTYTOTAL_WEIGHT";
	
	public final static String  ONEGOODSQTYTOTAL_VOLUME = "ONEGOODSQTYTOTAL_VOLUME";
	/**
	 * 默认初始的流水号
	 */
	public final static  String SERIAL_NUMBER="0001";
	
	/**
	 * 行政区域级别 END
	 */
	
	/********* CRM订单状态  (开单后调用CRM订单状态修改接口时使用)********/
	
	
	/**
	 * 查询公布价综合页面地址
	 */
	public final static  String PRICE_SEARCH_URL = PropertiesUtil.getKeyValue("price.query");
	
	
	/**
	 * FOSS URI 地址
	 */
	public final static  String  FOSS_URI= PropertiesUtil.getKeyValue("foss.uri");

	/**
	 * CRM订单  begin
	 */
	public final static  String CRM_ORDER_WAIT_ACCEPT = "WAIT_ACCEPT";//待受理
	
	public final static  String CRM_ORDER_ACCEPT = "ACCEPT";//已受理
	/********* CRM订单状态  (开单后调用CRM订单状态修改接口时使用)********/
	public final static  String CRM_ORDER_WAYBILL = "GOT";//已开单
	
	public final static  String CRM_ORDER_RECEIPTING = "RECEIPTING";//接货中
	
	public final static  String CRM_ORDER_GOBACK = "GOBACK";//已退回
	
	public final static  String CRM_ORDER_CHANNEL_ONLINE = "ONLINE";//订单来源网上营业厅
	
	public final static  String CRM_ORDER_PAYMENT_ONLINE = "PAY_ONLINE";//订单支付方式在线支付
	
	public final static  String CRM_ORDER_CHANNEL_ALIBABA = "ALIBABA";//订单来源阿里巴巴
	
	public final static  String CRM_ORDER_CHANNEL_ALIBABACXT = "ALIBABACXT";//订单来源阿里巴巴诚信通
	  
	public final static String CRM_ORDER_CHANNEL_CALLCENTER = "CALLCENTER";//400 呼叫中心
	
	public final static String CRM_ORDER_CHANNEL_BUSINESS_DEPT = "BUSINESS_DEPT";//营业部
	
	public final static String CRM_ORDER_CHANNEL_JIUXIANWANG = "JIUXIANWANG";//酒仙网
	
	public final static String CRM_ORDER_CHANNEL_YOUSHANG = "YOUSHANG";//友商网
	
	public final static String CRM_ORDER_CHANNEL_SHANGCHENG = "SHANGCHENG";//商城
	
	public final static String CRM_ORDER_CHANNEL_QQSUDI = "QQSUDI";//QQ速递
	
	public final static  String CRM_ORDER_PAYMENT_MONTH_PAY = "MONTH_PAY";//月结
	/**
	 * CRM订单  end
	 */
	
	/**
	 * 电子运单   begin
	 */
	public final static String EWAYBILL = "EWAYBILL";//运单类型为电子运单；
	
	public final static  String PREFIX_NINE = "9";//电子运单的第一个数字为9
	
	public final static  String CREATOR_SYSTEM = "000000"; //电子运单的默认创建人工号为系统工号000000
	
	public final static  String UNACTIVE_EWAYBILL_GENERATE_SUCCESS = "success";//待激活电子运单生成成功
	
	public final static  String UNACTIVE_EWAYBILL_GENERATE_FAILURE = "failure";//待激活电子运单生成失败
	
	/**
	 * 电子运单  end
	 */
	public final static  String UNACTIVE_PENDINGCOUPON_SEND_FAILURE = "failure";//待返券短信发送失败
	
	public final static  String UNACTIVE_PENDINGCOUPON_SEND_SUCCESS = "success";//待返券短信发送成功
	
	/**
	 * 官网查询我的发货单 begin IN_TRANSIT在途运输 NORMAL_SIGN-正常签收 UNNORMAL_SIGN-异常签收
	 */
	/**
	 * 在途运输
	 */
	public final static  String ACCOUNT_IN_TRANSIT = "IN_TRANSIT";// 在途运输
	/**
	 * 正常签收
	 */
	public final static  String ACCOUNT_NORMAL_SIGN = "NORMAL_SIGN";// 正常签收
	/**
	 * 异常签收
	 */
	public final static  String ACCOUNT_UNNORMAL_SIGN = "UNNORMAL_SIGN";// 异常签收
	/**
	 * 异常签收
	 */
	public final static  String UNNORMAL= "UNNORMAL";// 异常
	/**
	 * 已开单
	 */
	public final static  String ACCOUNT_EFFECTIVE = "EFFECTIVE";// 已开单
	/**
	 * 营业部已出发
	 */
	public final static  String ACCOUNT_STATION_OUT = "STATION_OUT";// 营业部已出发
	/**
	 * 已到达中转场
	 */
	public final static  String ACCOUNT_TFR_IN = "TFR_IN";// 已到达中转场
	/**
	 * 中转库存
	 */
	public final static  String ACCOUNT_TFR_STOCK = "TFR_STOCK";// 中转库存
	/**
	 * 中转运输
	 */
	public final static  String ACCOUNT_TFR_OUT = "TFR_OUT";// 中转运输
	/**
	 * 已到达营业部
	 */
	public final static  String ACCOUNT_STATION_IN = "STATION_IN";// 已到达营业部
	/**
	 * 营业部库存
	 */
	public final static  String ACCOUNT_STATION_STOCK = "STATION_STOCK";// 营业部库存
	/**
	 * 送货中
	 */
	public final static  String ACCOUNT_DELIVERING = "DELIVERING";// 送货中
		

	/**
	 *  官网查询我的发货单  end
	 */
	
	/**
	 *  运单入库操作类型   begin
	 */
	public final static  String WAYBILL_STOCK_TYPE_PRE ="pre";
	
	public final static  String WAYBILL_STOCK_TYPE_FAILURE="failure";
	
	public final static  String WAYBILL_STOCK_TYPE_SUCCESS="success";
	
	/**
	 *  运单入库操作类型   end
	 */
	
	/**================ 运单客户类型   start =============*/
	/**
	 * CRM 正式客户
	 */
	public final static  String CUSTOMER_TYPE_FORMAL="formal";
	
	/**
	 * CRM 的临客、散客
	 */
	public final static  String CUSTOMER_TYPE_TEMP="temp";
	
	/**
	 * 发货历史客户
	 */
	public final static  String CUSTOMER_TYPE_HISTORY="history";
	/**================ 运单客户类型   end =============*/
	
	/**
	 * 待补录
	 */
	public final static  String TO_MAKEUP = "待补录";
	/**
	 * 未知
	 */
	public final static  String UNKNOWN = "N/A";
	
	/**================ 标示首次新增(运单变更审核权限)或修改已生效时新增  =============*/
	public final static  String FIRST_ADD="first";
	public final static  String UPDATE_ADD="update";
	
	/**
	 * 锁定业务对象描述,提交运单信息
	 */
	public final static  String WAYBILL_SUBMIT = "WAYBILL_SUBMIT";
	/**
	 * 锁定业务对象描述,受理通过
	 */
	public final static  String WAYBILL_ACCEPT_AGREE = "WAYBILL_ACCEPT_AGREE";
	/**
	 * 锁定业务对象描述,受理拒绝
	 */
	public final static  String WAYBILL_ACCEPT_REJECT = "WAYBILL_ACCEPT_REJECT";
	/**
	 * 锁定业务对象描述,审核通过
	 */
	public final static  String WAYBILL_AUDIT_AGREE = "WAYBILL_AUDIT_AGREE";
	/**
	 * 锁定业务对象描述,审核拒绝
	 */
	public final static  String WAYBILL_AUDIT_REJECT = "WAYBILL_AUDIT_REJECT";
	
	/**
	 * 香港CODE
	 */
	public final static  String ORG_CODE_HONGKONG = "810000";
	/**
	 * 台湾CODE
	 */
	public final static  String ORG_CODE_TAIWAN = "710000";
	/**
	 * 澳门CODE
	 */
	public final static  String ORG_CODE_MACAO = "820000";
	
	/**
	 * 锁定业务对象描述,更改单导入
	 */
	public final static  String IMPORT_WAYBILL_NUMBER = "IMPORT_WAYBILL_NUMBER";
	
	/**
	 * 锁定业务对象描述,提交更改单
	 */
	public final static  String WAYBILL_RFC = "WAYBILL_RFC";

	/**
	 * 系统上线时间
	 * 2013- 6 -1
	 */
	public final static  Date SYSTEM_ONBOARD_TIME = new Date(2013 - 1900, 5 ,1);
	
	/**
	 * 空运合票方式  合大票
	 */
	public final static String FREIGHT_METHOD_HDP="HDP";
	
	/**
	 * 开单类型 返单
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_WAYBILL ="RETURN_WAYBILL";
	
	/**
	 * 开单类型 返货(按票返)
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_CARGO ="RETURN_CARGO";
	
	public final static String WAYBILL_EXPRESSTYPE_RETURN_CARGO_NAME="按票返";
	
	/**
	 * 开单类型 按件返
	 */
	public final static String WAYBILL_EXPRESSTYPE_RETURN_PIECE ="RETURN_PIECE";
	
	public final static String WAYBILL_EXPRESSTYPE_RETURN_PIECE_NAME="按件返";

	/**
	 *  城市类型  落配载
	 */
	public final static String CITY_TYPE_PEIZAI=DictionaryValueConstants.EXPRESS_CITY_TYPE_LDP;
	
	/**
	 * 城市类型  试点
	 */
	public final static String CITY_TYPE_SHIDIAN=DictionaryValueConstants.EXPRESS_CITY_TYPE_PILOT;

	
	/**
	 * 体积上限
	 */
	public static final String EXPRESS_VOLUME_RANGE_UP = "1101";
	
	/**
	 * 体积下限
	 */
	public static final String EXPRESS_VOLUME_RANGE_LOW = "1102";
	
	/**
	 * 体积
	 */
	public static final String EXPRESS_VOLUME_RANGE_RATE = "1103";

	/**
	 * 快递保价申明价值上限
	 */
	public static final String EXPRESS_INSURRANCE_UP = "1104";
	
	/**
	 * 包裹
	 */
	public static final String PACKAGE = "PACKAGE";

	/**
	 * 3.60特惠件
	 */
	public static final String ROUND_COUPON_PACKAGE ="RCP";
	
	/**
	 * 三级产品-电商尊享件Enjoy
	 */
	public static final String ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE ="EPEP";
	
	/**
	 * 三级产品-快递商务件
	 */
	public static final String PRICING_PRODUCT_EXPRESS_AIR_FREIGHT="DEAP";
	
	/**
	 * 淘宝
	 */
	public static final String CRM_ORDER_CHANNEL_TAOBAO ="TAOBAO";
	
	/**
	 * 送货进仓
	 */
	public static final String OTHERFEE_SHJCF ="SHJCF";
	
	/**
	 * 包装类型:打木托
	 */
	public static final String PACKAGE_TYPE_SALVER ="SALVER";
	/**
	 *  德邦内部带货客户编码 --
	 */
	public static final String DEPPON_CUSTOMER="4008305555";
	
	/**
	 * 配置参数-IT服务台一键上报是否开启
	 */
	public final static String ITSERVICE_UPLOAD_START = "ITSERVICE_UPLOAD_START";
	
	/**
	 * 配置参数-更改单接货费验证是否开启
	 */
	public final static String RFC_PICKFEE_VAL_START = "RFC_PICKFEE_VAL_START";
	
	/**
	 * 配置参数-接送货——微信功能是否开启
	 */
	public final static String WEIXIN_PULL_START = "WEIXIN_PULL_START";
	
	/**
	 * 配置参数-接送货-CRM推广活动是否开启
	 */
	public final static String CRM_ACTIVE_START = "CRM_ACTIVE_START";
	/**
	 * 配置参数-接送货-规则引擎是否开启
	 */
	public final static String BRMS_ACTIVE_START = "BRMS_ACTIVE_START";
	
	/**
	 * 配置参数-接送货-优惠券是否开启
	 */
	public final static String PROMOTIONS_START = "PROMOTIONS_START";
	
	/**
	 * 配置参数-接送货-签收单生效时间
	 */
	public final static String ORIGINAL_START_TIME = "ORIGINAL_START_TIME";
	
	
	/**
	 * foss系统
	 */
	public final static String SYSTEM_NAME = "Foss系统";
	
	/**
	 * 图片开单
	 */
	public static final String GUITITLENAME_PICTURE = "图片开单";
	
	/**
	 * 失败标志
	 */
	public final static int FAIL_TYPE = 0;
	
	/**
	 * foss系统
	 */
	public final static String YES = "Y";
	
	public final static String NO = "N";
	
	/**
	 *  客户端下载最大并发数
	 */
	public static final String DOWLOAD_MAX_THREAD = "DOWLOAD_MAX_THREAD";
	
	/**
	 *  令牌存活时间
	 */
	public static final String DOWNLOAD_TOKEN_SURVIVAL_TIME = "DOWNLOAD_TOKEN_SURVIVAL_TIME";
	
	/**
	 *  令牌池数量
	 */
	public static final String DOWNLOAD_TOKEN_TOTAL = "DOWNLOAD_TOKEN_TOTAL";
	
	/**
	 *  令牌下载开关
	 */
	public static final String FOSS_DOWNLOAD_SWITCH = "FOSS_DOWNLOAD_SWITCH";
	
	/**
	 *  令牌最大下载页数
	 */
	public static final String DOWNLOAD_MAX_PAGE_SIZE = "DOWNLOAD_MAX_PAGE_SIZE";
	
	/**
	 *  令牌请求间隔
	 */
	public static final String DOWNLOAD_TOKEN_INTERVAL = "DOWNLOAD_TOKEN_INTERVAL";
	
	/**
	 *  令牌心跳间隔
	 */
	public static final String DOWNLOAD_BEAT_TOKEN_INTERVAL = "DOWNLOAD_BEAT_TOKEN_INTERVAL";
	
	/**
	 * 配置参数-接送货——偏线时效方案校验是否开启
	 */
	public final static String OUTER_EFFECTIVE_VERIFIED_CODE = "OUTER_EFFECTIVE_VERIFIED";

	/**
	 * 配置参数-接送货-发票系统推送消息开关
	 */
	public static final String QUOTA_INVOICE_SWITCH_CODE = "ESB_FOSS2ESB_QUOTA_MARK";
	
	/**
	 * APP客户-发货人
	 */
	public final static  String APP_DELIVER = "DELIVER";
	
	/**
	 * APP客户-收货人
	 */
	public final static  String APP_RECEIVE = "RECEIVE";
	
	//************@Atuhor wutao 
	//************@Date 2014-08-26
	/**
	 * 始发区域
	 */
	public final static String START_REGION_CODE = "START_REGION";
	/**
	 * 到达区域
	 */
	public final static String ARRIVE_REGION_CODE = "ARRIVE_REGION";
	
	/**
	 * 省级别
	 */
	public final static String DISTRICT_PROVINCE_CODE = "DISTRICT_PROVINCE";
	/**
	 * 市级别
	 */
	public final static String DISTRICT_CITY_CODE = "DISTRICT_CITY";
	/**
	 * 区域级别
	 */
	public final static String DISTRICT_COUNTY_CODE = "DISTRICT_COUNTY";
	
	/**
	 * wutao
	 * 添加需求字段
	 */
	public final static  String SELF_PICKUP_REGION = "自提区";//自提区 

	/**
	 * 电子运单生成待激活运单JOB开关配置
	 */
	public static final String EWAYBILL_GENERATE_JOB_SWITCH = "EWAYBILL_GENERATE_JOB_SWITCH";
	
	/**
	 * 电子运单每个周期处理的订单数量
	 */
	public static final String EWAYBILL_GENERATE_NUMBER_PER_CYCLE = "EWAYBILL_GENERATE_NUMBER_PER_CYCLE";
	
	/**
	 * 电子运单超期需处理天数配置
	 */
	public static final String EWAYBILL_OVER_DAYS_INVALID_CODE = "EWAYBILL_OVER_DAYS_INVALID";
	
	/**
	 * 电子运单上线时间配置
	 */
	public static final String EWAYBILL_ONLINE_DAYS_CODE = "EWAYBILL_ONLINE_DAYS";

	/**
	 * 电子运单类型
	 */
	public static final String WAYBILL_EWAYBILL_TYPE = "EWAYBILL";
	
	/**
	 * 电子运单激活失败
	 */
	public static final String EWAYBILL_ACTIVE_FAIL ="EWAYBILL_ACTIVE_FAIL";
	
	/**
	 * 
	 */
	public static final String IS_NULL_FOR_AI = "否";
	
	public static final String IS_NOT_NULL_FOR_AI = "是";
	
	/**
	 * 开单图片上传地址
	 */
	public final static  String PICTURE_WAYBILL_DIR = "upload.waybill.picture.dir";
	
	/**
	 * 获取图片的地址
	 */
	public final static  String PICTURE_WAYBILL_ADDRESS = "waybill.picture.address";
	
	/**
	 * 返货工单上报地址
	 */
	public final static  String EXP_RETURNGOODS_ADDRESS = "exp.returngoods.address";
	
	/**
	 * 查询展会货GIS地址
	 */
	public final static  String ISEXHIBITCARGO_GIS_URL = "isexhibitcargo.gis.url";
	
	/**
	 * 集中开单 监控运单号还未收到手机端反馈 间隔时间
	 */
	public final static  String PUSH_MESSAGE_WAYBILL_MINUTE = "waybill.pushmessage.minute";
	
	/**
	 * waybillPicture_pending_type
	 * 
	 * zxy 20141014 集中接货开单质量优化项目
	 * 
	 */
	/**
	 * 待录入
	 */
	public final static  String WAYBILL_PICTURE_TYPE_PENDING = "PENDING";
	//已分配
	public final static  String WAYBILL_PICTURE_TYPE_DISTRIBUTED = "DISTRIBUTED";
	/**
	 * 已录入(PDA待补录)
	 */
	public final static  String WAYBILL_PICTURE_TYPE_PDA_PENDING = "PDA_PENDING";
	/**
	 * 已撤销
	 */
	public final static  String WAYBILL_PICTURE_TYPE_CANCEL = "CANCEL";
	/**
	 * 已退回
	 */
	public final static  String WAYBILL_PICTURE_TYPE_RETURN = "RETURN";
	/**
	 * 已开单
	 */
	public final static  String WAYBILL_PICTURE_TYPE_PDA_ACTIVE = "PDA_ACTIVE";
	/**
	 * 运单异常
	 */
	public final static  String WAYBILL_PICTURE_TYPE_WAYBILL_EXCEPTION = "WAYBILL_EXCEPTION";
	
	/**
	 * 中止
	 */
	public final static  String WAYBILL_PICTURE_TYPE_DISCONTINUE = "DISCONTINUE";
	
	/**
	 * 打木架 
	 */
	public final static  String WAYBILL_WOODEN_STAND = "stand";
	/**
	 * 打木箱
	 */
	public final static  String WAYBILL_WOODEN_BOX = "box";
	/**
	 * 没打木架打木箱
	 */
	public final static  String WAYBILL_WOODEN_NOPACK = "noPack";
	
	/**
	 * 图片处理线程处理状态
	 */
	public final static  String WAYBILL_PICTURE_TYPE_THREAD = "THREAD";
	
	//推送
	
	//推送推送类型 1表示一个人 
	public final static  int PUSHTYPE_ONE = 1;
	//推送推送类型 2表示一群人
	public final static  int PUSHTYPE_TWO = 2;
	//推送推送类型 3表示所有人
	public final static  int PUSHTYPE_THREE = 3;
	
	//推送消息类型 1表示消息
	public final static  int MESSAGETYPE_ONE = 1;
	//推送消息类型 2表示通知
	public final static  int MESSAGETYPE_TWO = 2;
	
	//推送推送类型 1表示浏览器
	public final static  int DEVICETYPE_ONE = 1;
	//推送推送类型 2表示PC
	public final static  int DEVICETYPE_TWO = 2;
	//推送推送类型 3表示安卓
	public final static  int DEVICETYPE_THREE = 3;
	//推送推送类型 4表示IOS
	public final static  int DEVICETYPE_FOUR = 4;
	//推送推送类型 5表示WINDOWS PHONE 
	public final static  int DEVICETYPE_FIVE = 5;
	
	
	/**
	 * @author Foss-278328-hujinyang
	 * @TIME 20160504
	 * 图片处理线程等待处理时间
	 */
    public final static  Long PIC_THREAD_SLEEP_TIME = 1000l;
		
	/**
	 * Dmana-9885巨商汇来源标识
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-03
	 */
	public final static String GIANT_SINK = "JUSHANGHUI";
	
	/**
	 * Dmana-9885阿里巴巴MRO来源标识
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-02-03
	 */
	public final static  String ALIBABA = "ALIBABAMRO";
	
	// 重量上限
	public static final double WEIGHT_UPPER_LIMIT = 30;

	// 体积上限
	public static final double VOLUME_UPPER_LIMIT = 0.18;

	
	/**
	 * 电子面单已经扫描
	 */
	public final static  String EWAYBILL_SCANED_TYPE = "EWAYBILL_ISSCANED";
	/**
	 * 电子面单已经扫描
	 */
	public final static  String EWAYBILL_UNISSCAN_TYPE = "EWAYBILL_UNISSCAN";
	
	/**
	 * 公司微信地址配置
	 */
	public final static  String EWAYBILL_DEPPON_WEIXIN_ADDRESS = "DEPPON_WEIXIN_ADDRESS";
	
	//市辖区
	public static final String SHI_XIA_QU = "shixiaqu";
	
	//设置电子运单生成待激活线程启动开关
	public  static final String PKP_EWAYBILL_AUTO_SCHEDULE = "EWAYBILL_AUTO_SCHEDULE";
	//设置电子运单生成待激活线程一次处理的个数
	public static final String PKP_EWAYBILL_SCHEDULE_COUNT="EWAYBILL_SCHEDULE_COUNT";
	//设置电子运单生成激活线程启动开关
	public  static final String PKP_EWAYBILL_AUTO_VALID = "EWAYBILL_AUTO_VALID";
	//设置电子运单生成激活线程一次处理的个数
	public static final String PKP_EWAYBILL_AUTO_VALID_COUNT="EWAYBILL_AUTO_VALID_COUNT";
	//设置待激活电子运单线程池数量
	public static final String PKP_EWAYBILL_UNACTIVE_COUNT="EWAYBILL_UNACTIVE_COUNT";
	//设置激活电子运单线程池数量
	public static final String PKP_EWAYBILL_ACTIVE_COUNT="EWAYBILL_ACTIVE_COUNT";
	
	//设置OWS/DOP标签信息推送任务启动开关
	public  static final String PKP_OWSDOP_LABEL_PUSH_AUTO_SCHEDULE = "OWSDOP_LABEL_PUSH_AUTO_SCHEDULE";
	//设置OWS/DOP标签信息推送任务一次处理的个数
	public static final String PKP_OWSDOP_LABEL_PUSH_SCHEDULE_COUNT="OWSDOP_LABEL_PUSH_SCHEDULE_COUNT";
	//设置OWS/DOP标签信息推送任务最大线程数
	public static final String PKP_OWSDOP_LABEL_PUSH_THREAD_COUNT="OWSDOP_LABEL_PUSH_THREAD_COUNT";

	//设置电子运单子母件线程池数
	public static final String PKP_EWAYBILL_AUTO_CZMTHREAD_COUNT="EWAYBILL_AUTO_CZMTHREAD_COUNT";
	
	//设置电子运单预处理线程数
	public static final String PKP_EWAYBILL_AUTO_PREHANDLE_COUNT="EWAYBILL_AUTO_PREHANDLE_COUNT";		

	//我司原因
	public static final String COMPANY_REASON ="COMPANY_REASON";
	//客户原因
	public static final String CUSTOMER_REASON = "CUSTOMER_REASON";
	
	//零担统称
	public static final String TYPE_OF_CARGO = "CARGO";
	
	//快递统称
	public static final String TYPE_OF_EXPRESS = "EXPRESS";
	
	//快递统称
	public static final String CURRENCY_CODE = "RMB";
	
	//josn形式中的开单人姓名key键（图片开单退回返回给客户的字段，移动平台需要解析的）
	public static final String BILLING_PERSON_NAME = "billingPersonName"; 
	//josn形式中的开单人电话key键（图片开单退回返回给客户的字段，移动平台需要解析的）
	public static final String BILLING_PERSON_PHONE = "billingPersonPhone"; 
	//josn形式中的开单人原因key键（图片开单退回返回给客户的字段，移动平台需要解析的）
	public static final String WAYBILL_BACK_MESSAGE = "waybillBackMessage"; 
	
	
	/**
	 * Dmana-10888运单vo状态
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-03-24上午11:24
	 */
	public static final String WAYBILLINFOVO = "waybillinfovo";
	/**
	 * @author:220125-YangXiaoLong
	 * @date:2015-07-08上午11:24
	 */
	//设置pda时效
	public  static final String EXPRESS_PDA_TIME_SET = "EXPRESS_PDA_TIME_SET";
	//设置快递自动补录定时job的开关
	public  static final String PKP_EXPRESS_AUTO_MKKEUP = "EXPRESS_AUTO_MAKEUP";
	//设置快递自动补录当前周期批处理的订单数
	public  static final String PKP_EXPRESS_GENERATE_CYCLE = "EXPRESS_GENERATE_CYCLE";
	//设置快递自动补录时效推送每一批数量
	public  static final String PKP_EXPRESS_TIMESET_CYCLE = "PKP_EXPRESS_TIMESET_CYCLE";
	//定义数据字典用于查询渠道来源
	
	public static final String DICTIONARY_TERMS_CODE="PKP_PRICE_CHANNEL";
	//定义数据字典用于查询包装子服务来源
    public static final String BZ_TERMS_CODE="VALUEADDED_PACKAGE_TYPE";
	/**
	 * 内部发货类型
	 * dp-foss-dongjialing
	 * 225131
	 */
	//发货方付
	public static final String DELIVERY_PAY = "DELIVERY_PAY";
	//收货方付
	public static final String RECIVE_PAY = "RECIVE_PAY";
	//分割符
	public static final String SPLIT = "-";
	public static final String DELIVERY_PAY_CH = "发货方付";
	public static final String RECIVE_PAY_CH = "收货方付";
	public static final String CHANGER_ITEM_EMPLOYEE = "employeeNo";
	public static final String CHANGER_ITEM_DELIVERY_TYPE = "internalDeliveryType";
	//家具送装
	public static final String FURNITURE_EQUIP="FURNITURE_EQUIP";
	//建材送装
	public static final String BUILD_MATERIAL_EQUIP="BUILD_MATERIAL_EQUIP";
	//家电送装
	public static final String HOME_APPLICATION_EQUIP="HOME_APPLICATION_EQUIP";
	
	//分割符
	public static final String FURNITURE_EQUIP_CH = "家具送装";
	public static final String BUILD_MATERIAL_EQUIP_CH = "建材送装";
	public static final String HOME_APPLICATION_EQUIP_CH = "家电送装";
	public static final String CHANGER_ITEM_SPECIALVALUEADDEDSERVICE_TYPE="specialValueAddedServiceType";
	
	//分割符
	//送货上楼(家装)
	public static final String SEND_UPSTAIRS_EQUIP = "SEND_UPSTAIRS_EQUIP";
	//送货安装
	public static final String SEND_AND_EQUIP="SEND_AND_EQUIP";
	//送货不上楼(家装)
	public static final String SEND_NO_UPSTAIRS="SEND_NO_UPSTAIRS";
	//分隔线
	public static final String SEND_UPSTAIRS_EQUIP_CH = "送货上楼(家装)";
	public static final String SEND_AND_EQUIP_CH="送货安装";
	public static final String SEND_NO_UPSTAIRS_CH="送货不上楼(家装)";
	
	
	//零担梯度折扣
	public static final String FRT_GRADESREBATE = "FRTGRADESREBATE";
	//事后折
	public static final String AFTER_FRT_GRADESREBATE = "AFTERFRTGRADESREBATE";
	public static final String FRT_ZERO = "0";
	//客户变更原因类型
	public static final String RFC_REASON = "RFC_REASON";
	//德邦家装244277 编号
	public static final String instCode = "JZ";//安装code
	public static final String instsCode = "RGBL";//上楼code
	public static final String instqCode = "TS-SHF";//区域code
	public static final String INST_GOODS_CODE = "SHAZ";//送货安装code
	//PDA卸车——电子运单卸车
	public static final String STATUS_UNLOAD = "UNLOAD";
	//PDA接货任务——电子运单接货
	public static final String STATUS_PICKUP = "PICKUP";
	//电子运单待激活作废
	public static final String EWAYBILL_OPERATE_INVALID = "EWAYBILL_INVALID";
	//司机解绑
	public static final String WORK_STATUS_OPEN = "OPEN";
	//司机绑定
	public static final String WORK_STATUS_STOP = "STOP";
	//晚到补差价发优惠券地址
	public static final String  SENDCOUPONSERVICE_LATER_ADDRESS="sendCouponService.later.address";
	
	/**
	 * 子母单：正常单
	 */
	public final static String WAYBILL_NORMAL = "normal" ;
	/**
	 * 子母单：母单
	 */
	public final static String WAYBILL_PARENT = "parent" ;
	/**
	 * 子母单：子单
	 */
	public final static String WAYBILL_CHILD = "child" ;
	
	//是否子母件
	public final static String WAYBILL_ISPICPACKAGE = "Y" ;
	//2016年1月15日 15:00:22 葛亮亮
	public static final String TYPE_OF_VIPXLYX = "VIPXLYX"; //VIP/全网活跃线路营销
	public static final String TYPE_OF_JJTZXFY = "JJTZXFY"; //家居配送折扣
	public static final String TYPE_OF_XDYHJ = "XDYHJ";     //新点10元优惠券
	public static final String TYPE_OF_DLCJTH = "DLCJTH";   //城际特惠
	
	//合伙人
	//-1:什么也不做  0:成本  1：提成  2：成本和提成 3:作废 4:丢货弃货
	public static final String NEGATIVE_ONE  = "-1" ;
	public static final String ZERO = "0" ;
	public static final String ONE = "1" ;
	public static final String TWO = "2" ;
	public static final String THREE = "3" ;
	public static final String FOUR = "4" ;
	//liding add
	/**	 * 待刷卡运单数据:数据来源
	 * 1:运单开单
	 */
	public final static String WAYBILL_SOURCE_CREATE = "1" ;
	
	/**
	 * 待刷卡运单数据:数据来源
	 * 2:运单更改
	 */
	public final static String WAYBILL_SOURCE_MOD = "2" ;
	
	/**
	 * 待刷卡运单数据:支付状态
	 * Y:已支付
	 */
	public final static String WAYBILL_PAYSTATUS_PAID = "Y" ;
	
	/**
	 * 待刷卡运单数据:支付状态
	 * N:未支付
	 */
	public final static String WAYBILL_PAYSTATUS_UNPAID = "N" ;
	
	//裹裹服务编码
	public final static String SERVICE_TYPE="12";
	//裹裹重量和体积
	public final static BigDecimal GUOGUO_WIGHT=BigDecimal.valueOf(999);
	public final static BigDecimal GUOGUO_VOLUE=BigDecimal.valueOf(3);
	/**
	 * 客户分群
	 */
	//VIP
	public final static String VIP = "VIP" ;
	//全网活跃群
	public final static String OMNI_ACTIVE = "OMNI-ACTIVE" ;
	
	// 零担电子运单常量，START
	/**
	 * 零担电子运单自动激活线程开关
	 */
	public final static String PKP_LTL_EWAYBILL_AUTO_ACTIVE = "PKP_LTL_EWAYBILL_AUTO_ACTIVE";
	/**
	 * 零担电子运单自动激活执行记录条数
	 */
	public final static String PKP_LTL_EWAYBILL_AUTO_ACTIVE_COUNT = "PKP_LTL_EWAYBILL_AUTO_ACTIVE_COUNT";
	/**
	 * 零担电子运单自动激活线程数
	 */
	public final static String PKP_LTL_EWAYBILL_ACTIVE_COUNT = "PKP_LTL_EWAYBILL_ACTIVE_COUNT";
	/**
	 * 零担电子运单自动激活成功
	 */
	public final static  String LTL_EWAYBILL_ACTIVE_SUCCESS = "success";
	
	/**
	 * 零担电子运单自动激活失败
	 */
	public final static  String LTL_EWAYBILL_ACTIVE_FAILURE = "failure";
	/**
	 * 零担电子运单自动激活失败（发生业务异常）
	 */
	public final static  String LTL_EWAYBILL_ACTIVE_FAILURE_BUSI = "BUSINESS_ERROR_FAILURE";
	/**
	 * 零担电子运单自动激活失败（发生系统异常）
	 */
	public final static  String LTL_EWAYBILL_ACTIVE_FAILURE_SYS = "SYS_ERROR_FAILURE";
	
	
	/**
	 * 零担电子运单标签信息推送操作状态：未推送
	 */
	public static final String LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_UNPUSHED = "unpushed";
	/**
	 * 零担电子运单标签信息推送操作状态:推送中 
	 */
	public static final String LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHING = "pushing";
	/**
	 * 零担电子运单标签信息推送操作状态：已推送
	 */
	public static final String LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_PUSHED = "pushed";
	/**
	 * 零担电子运单标签信息推送操作状态：推送成功
	 */
	public static final String LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_SUCCESS = "success";
	/**
	 * 零担电子运单标签信息推送操作状态：推送失败
	 */
	public static final String LTL_EWAYBILL_LABELPUH_OPERATE_RESULT_FAIL = "fail";
	
	// 零担电子运单常量，END
	
	/**
	 * 根据传递过来的对应的三级产品编码判定是否快递
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-3 19:01:13
	 */
	public static boolean directDetermineIsExpressByProductCode(String productCode){
		//判定数据是否为空
		if(productCode == null || "".equals(productCode)){
			return false;
		}
		//在人为判定是否相等
		if(WaybillConstants.PACKAGE.equals(productCode)
				|| WaybillConstants.ROUND_COUPON_PACKAGE.equals(productCode)
				|| WaybillConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE.equals(productCode)
				|| WaybillConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productCode)){
			return true;
		}
		return false;
	}
	//用来表示否的信息
	public final static  boolean FALSE=false;
	/**
	 * 零担电子运单日志成功失败
	 */
	public static final String LTLEWAYBILL_PROCESS_LOG_SUCCESS = "success";
	
	public static final String LTLEWAYBILL_PROCESS_LOG_FAILURE = "failure";
	
	/**
	 * 零担电子运单日志类型(同步OMS,Request记录日志)
	 */
	public static final String LTLEWAYBILL_ASYN_OMS_ORDER_REQUEST_LOG = "requestOrderLog";
	
	/**
	 * 零担电子运单日志类型(同步OMS记录日志)
	 */
	public static final String LTLEWAYBILL_ASYN_OMS_ORDER_RESPONSE_LOG = "responseOrderLog";
	
	/**
	 * 零担电子运单日志类型(生成代补录日志)
	 */
	public static final String LTLEWAYBILL_GENERATE_WAYBILL_PENDING_LOG = "waybillPendingLog";
	
	/**
	 * 零担电子运单日志类型(生成代补录耗时日志)
	 */
	public static final String LTLEWAYBILL_GENERATE_WAYBILL_PENDING_MONITOR_LOG = "waybillPendingMonitorLog";
	
	/**
	 * 零担电子运单日志类型(PDA操作日志)
	 */
	public static final String LTLEWAYBILL_OPERATION_TYPE_PDA_LOG = "pdaLog";
	
	/**
	 * 零担电子运单日志类型（APP操作日志）
	 */
	public static final String LTLEWAYBILL_OPERATION_TYPE_APP_LOG = "appLog";
	
	/**
	 * 零担电子运单日志类型(APP扫描日志)
	 */
	public static final String LTLEWAYBILL_OPERATION_TYPE_APPSCAN_LOG = "appScanLog";
	
	/**
	 * 零担电子运单日志取消订单推送日志
	 */
	public static final String LTLEWAYBILL_OPERATION_TYPE_CANCEL_ORDER_LOG = "cancelLog";
	
	/**
	 * 零担电子运单日志类型(推送OMS状态)
	 */
	public static final String LTLEWAYBILL_PUSH_OMS_ORDER_STATUS = "PUSH_OMS_ORDER_STATUS_LOG";
	
	/**
	 * 零担电子运单日志类型(激活操作)
	 */
	public static final String LTLEWAYBILL_ACTIVATE_LOG = "waybillLog";
	
	/**
	 * 零担电子运单激活类型/待补录
	 */
	public static final String LTLEWAYBILL_PDA_PENDING_PROCESS = "pdaPendingProcess";	
	
	/**
	 * 零担电子运单激活类型/开单
	 */
	public static final String LTLEWAYBILL_PDA_ACTIVE_PROCESS = "pdaActiveProcess";
	
	/**
	 * 零担电子运单扫描标识(NOTSCAN未扫描/SCAN已扫描)
	 */
	public static final String LTLEWAYBILL_NOT_SCAN = "N";
	public static final String LTLEWAYBILL_SCAN = "Y";
	/**
	 * 零担电子运单 实际承运表的运单类型
	 */
	public static final String LTLEWAYBILL = "LTLEWAYBILL";
	/**
	 * 零担电子运单推送给开单环节推送给OMS的订单映射关系
	 * 常量值作为FOSS使用的运单状态,Map映射值，将推送至OMS
	 */
	public static final Map<String,String> OMS_ORDER_STATUS_RELATEION = new HashMap<String,String>();
	
	static {
		OMS_ORDER_STATUS_RELATEION.put(WAYBILL_PICTURE_TYPE_PDA_PENDING, "WAIT_MAKEUP");
		OMS_ORDER_STATUS_RELATEION.put(WAYBILL_PICTURE_TYPE_PDA_ACTIVE, "GOT");
		OMS_ORDER_STATUS_RELATEION.put(WAYBILL_PICTURE_TYPE_RETURN, "GOBACK");
	}
	/**
	 * 零担电子运单自动激活线程开关
	 */
	public final static String PKP_LTL_EWAYBILL_AUTO_PENDING = "PKP_LTL_EWAYBILL_AUTO_PENDING";
	/**
	 * 零担电子运单自动生成待补录线程数
	 */
	public final static String PKP_LTL_EWAYBILL_PENDING_COUNT = "PKP_LTL_EWAYBILL_PENDING_COUNT";
	/**
	 * 零担电子运单自动生成待补录执行记录条数
	 */
	public final static String PKP_LTL_EWAYBILL_AUTO_PENDING_COUNT = "PKP_LTL_EWAYBILL_AUTO_PENDING_COUNT";
	/**
	 * 零担电子运单的运单号最小值
	 */
	public final static int PKP_LTL_EWAYBILL_NO_MIN = 700000000;
	/**
	 * 零担电子运单的运单号最大值
	 */
	public final static int PKP_LTL_EWAYBILL_NO_MAX = 799999999;
	//FOSS
	public final static String FOSS = "FOSS" ;
	
	/**
	 * @author 354805 - taodongguo
	 * @date 2016-10-29 11:07:48
	 * 行政区域中国标识
	 */
	public final static String CHINA = "中国";
	//350909       郭倩云    
	public final static String CH_FLY_CASE="航空箱";
	//350909       郭倩云              
	public final static String CH_PAIL="桶";
	//350909       郭倩云              
	public final static String CH_IRON="铁";
	//350909       郭倩云              
	public final static String CH_WOOD="木";
	//350909       郭倩云              
	public final static String CH_CASE="框";
	//350909       郭倩云              
	public final static String CH_SALVER="托";
	//350909       郭倩云          运输性质
	public final static String PRODUCT_CODE="productCode";
	//350909       郭倩云          总件数
	public final static String GOODS_QTY_TOTAL="goodsQtyTotal";
	//350909       郭倩云          总重量
	public final static String GOODS_WEIGHT_TOTAL="goodsWeightTotal";
	//350909       郭倩云          木包装
	public final static String WOOD="wood";
	//350909       郭倩云          托包装
	public final static String SALVER="salver";
	//350909       郭倩云          其他包装
	public final static String OTHER_PACKAGE="otherPackage";
	//350909         郭倩云            总件数
	public final static String GOODS_QTY_TOTAL_CONF="GOODS_QTY_TOTAL_CONF";
	//350909       郭倩云              总重量
	public final static String GOODS_WEIGHT_TOTAL_CONF="GOODS_WEIGHT_TOTAL_CONF";
	//350909       郭倩云              是否开启零担轻货上分拣
	public final static String LIGHT_GOODS_SORTING="LIGHT_GOODS_SORTING";
	//350909       郭倩云              常量0
	public final static Integer NUM_ZERO=0;
	//add by 306486 变更明细-- 分拣结果
	public final static String SORTING_RESULT="sortResult";

	//大件仓配
	public final static String CNJZ = "CNJZ";	
	/**
	 *  350909   郭倩云     异步推送数据的开关,主要操作数据库表PKP.T_SRV_ADD_ASYN_WAYBILL 
	 */
	//是否往PKP.T_SRV_ADD_ASYN_WAYBILL表中插入财务单据和待刷卡数据的开关
	public static final String PKP_WSCWAYBILL_AUTO_SCHEDULE = "WSCWAYBILL_AUTO_SCHEDULE";
	//是否调用结算接口生成财务单据和待刷卡数据的job开关
	public static final String WSCWAYBILL_JOB_AUTO_SCHEDULE = "WSCWAYBILL_JOB_AUTO_SCHEDULE";
	//生成财务单据的类型
	public static final String ADD_SETTLE_BILL = "ADD_SETTLEBILL";
	//生成待刷卡数据的类型
	public static final String ADD_WAYBILL = "ADD_WAYBILL";

}