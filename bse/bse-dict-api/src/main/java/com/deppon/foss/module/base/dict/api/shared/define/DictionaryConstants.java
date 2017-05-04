/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/define/DictionaryConstants.java
 * 
 * FILE NAME        	: DictionaryConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.shared.define;

/**
 * 数据字典-词 的常量类。 词的常均从此类获取。当需要新添加一个数据字典的词的时候，需要在此类新添加一个常量的变量。
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-22 下午4:04:57
 */
public class DictionaryConstants {

	public static final String FOSS_ACTIVE = "FOSS_ACTIVE";
	public static final String FOSS_ACTIVE__DESC= "是否有效";
	
	
	/**
	 * 是否初始化
	 */
	public static final String FOSS_BOOLEAN = "FOSS_BOOLEAN";
	public static final String FOSS_BOOLEAN__DESC= "是否有效";
	
	
	/**
	 * 结算--还款单来源单据类型
	 */
	public static final String BILL_REPAYMENT_SOURCE_BILL_TYPE = "BILL_REPAYMENT__SOURCE_BILL_TYPE";
	public static final String BILL_REPAYMENT_SOURCE_BILL_TYPE__DESC= "还款单来源单据类型";
	
	
	
	/**
	 * 结算--客户类型
	 */
	public static final String SETTLEMENT__CUSTOMER_TYPE = "SETTLEMENT__CUSTOMER_TYPE";
	public static final String SETTLEMENT__CUSTOMER_TYPE__DESC= "客户类型";
	
	/**
	 * 结算--付款对接系统为财务自助还是对接费控标志   其中Y--代表财务共享  N--代表费控
	 */
	public static final String SETTLEMENT__PAYTOSYSTEM_TYPE = "SETTLEMENT__PAYTOSYSTEM_TYPE";
	public static final String SETTLEMENT__PAYTOSYSTEM_TYPE__DESC= "付款工作流对接系统类型";
	
	/**
	 * 结算--共享域名
	 */
	public static final String SETTLEMENT__FSSC_TYPE = "SETTLEMENT__FSSC_TYPE";
	public static final String SETTLEMENT__FSSC_TYPE__DESC= "共享域名";
	
	/**
	 * 结算--共享域名
	 */
	public static final String SETTLEMENT__CUSTOMER_MAX_DEBIT_DAYS_TYPE = "SETTLEMENT__CUSTOMER_MAX_DEBIT_DAYS_TYPE";
	public static final String SETTLEMENT__CUSTOMER_MAX_DEBIT_DAYS_TYPE__DESC= "客户临欠最大天数";
	
	/**
	 * 折扣、增值优惠渠道
	 * 
	 * @author IBMDP sz
	 * @date 2013-1-22 下午4:04:57
	 */
	public static final String PKP_PRICE_CHANNAL = "PKP_PRICE_CHANNEL";
	public static final String PKP_PRICE_CHANNAL__DESC = "渠道";
	
	/**
	 * 折扣、增值优惠所属行业
	 * 
	 * @author IBMDP sz
	 * @date 2013-1-22 下午4:04:57
	 */
	public static final String PKP_PRICE_INDUSTRY = "PKP_PRICE_INDUSTRY";
	public static final String PKP_PRICE_INDUSTRY__DESC = "所属行业";
	
	/**
	 * 接送货—价格模型-渠道
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 下午4:04:57
	 */
	public static final String PKP_PRODUCT_SPEED = "PKP_PRODUCT_SPEED";
	public static final String PKP_PRODUCT_SPEED__DESC = "产品优先级";
	
	/**
	 * 出车任务字段描述
	 * 	（1）送+接
		（2）送+转
		（3）带+送+接
		（4）带+送+转
		（5）二次派送
		（6）带+二次派送
	 * @author gongjp
	 * @date 2015-06-18
	 */
	public static final String DISPATCH_VEHICLE_TASK = "DISPATCH_VEHICLE_TASK";
	public static final String DISPATCH_VEHICLE_TASK__DESC = "出车任务";
	

	/**
	 * 接送货—订单受理时的状态
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-22 下午4:04:57
	 */
	public static final String PKP_PROCESS_STATUS = "PKP_PROCESS_STATUS";
	public static final String PKP_PROCESS_STATUS__DESC = "受理状态";
	
	/**
	 * 未排单备注
	 */
	public static final String BSE_UNSINGLE_ROWREMARKS = "BSE_UNSINGLE_ROWREMARKS";
	public static final String BSE_UNSINGLE_ROWREMARKS__DESC = "未排单备注";
	
	/**
	 * 行业货源类别
	 */
	public static final String BSE_SOURCE_CATEGORY = "BSE_SOURCE_CATEGORY";
	public static final String BSE_SOURCE_CATEGORY__DESC = "行业货源品类";
	
	/**
	 * GIS使用特殊地址
	 */
	public static final String BSE_SPECIAL_ADDRESS_TYPE = "BSE_SPECIAL_ADDRESS_TYPE";
	public static final String BSE_SPECIAL_ADDRESS_TYPE__DESC= "特殊地址类型";
	/**
	 * 综合管理-线路-线路类别
	 * @author foss-zhujunyong
	 */
	public static final String BSE_LINE_SORT = "BSE_LINE_SORT"; 
	public static final String BSE_LINE_SORT__DESC = "线路类别"; 
	
	/**
	 * 综合管理-月台库区-库区类型
	 * 
	 * @author foss-zhujunyong
	 */
	public static final String BSE_GOODSAREA_TYPE = "BSE_GOODSAREA_TYPE";
	public static final String BSE_GOODSAREA_TYPE__DESC = "库区类型";
	
	
	/**
	 * 外场库型
	 */
	public static final String BSE_TRANSFER_CENTER_STOCK_TYPE = "BSE_TRANSFER_CENTER_STOCK_TYPE";
	
	public static final String BSE_TRANSFER_CENTER_STOCK_TYPE__DESC = "外场库型";
	
	
	
	/**
	 * 综合管理-长短途（月台库区-库区类别、增值服务）
	 * 
	 * @author foss-zhujunyong
	 */
	public static final String BSE_GOODSAREA_USAGE = "BSE_GOODSAREA_USAGE";
	public static final String BSE_GOODSAREA_USAGE__DESC = "库区类别";
	
	/**
	 * 综合管理-展馆关键字信息-货物类型
	 * 
	 * @author foss-zhangXu189284
	 */
	public static final String BSE_EXHIBITIONKEYWORD_TYPE = "BSE_EXHIBITIONKEYWORD_TYPE";
	public static final String BSE_EXHIBITIONKEYWORD_TYPE__DESC = "展会货-货物类型";
	/**
	 * 清仓差异原因
	***/
	
	public static final String TFR_ST_TASK_DIFFERENCEREASONS = "TFR_ST_TASK_DIFFERENCEREASONS";
	public static final String TFR_ST_TASK_DIFFERENCEREASONS__DESC = "差异原因";

	/**
	 * 站内消息-消息类型
	 * 
	 * @author 101911-foss-zhouChunlai
	 */
	public static final String MSG_TYPE = "MSG_TYPE";
	public static final String MSG_TYPE__DESC = "站内消息类型";

	/**
	 * 站内消息-消息读取状态
	 * 
	 * @author 101911-foss-zhouChunlai
	 */
	public static final String MSG__READ_STATUS = "MSG__READ_STATUS";
	public static final String MSG__READ_STATUS__DESC = "消息读取状态";

	/**
	 * 待办事项-业务类型
	 * 
	 * @author 101911-foss-zhouChunlai
	 */
	public static final String TODOMSG__BUSINESS_TYPE = "TODOMSG__BUSINESS_TYPE";
	public static final String TODOMSG__BUSINESS_TYPE__DESC = "待办事项业务类型";

	/**
	 * 待办事项-待办状态
	 * 
	 * @author 101911-foss-zhouChunlai
	 */
	public static final String TODOMSG__STATUS_TYPE = "TODOMSG__STATUS_TYPE";
	public static final String TODOMSG__STATUS_TYPE__DESC = "待办事项状态类型";
	
	/**
	 * 返货类型
	 */
	public static final String RETURN_GOODS_TYPE = "RETURN_GOODS_TYPE";
	public static final String RETURN_GOODS_TYPE__DESC = "返货类型";

	/**
	 * 综合管理-月台库区-货物类型
	 * 
	 * @author foss-zhujunyong
	 */
	public static final String BSE_GOODS_TYPE = "BSE_GOODS_TYPE";
	public static final String BSE_GOODS_TYPE__DESC = "货物类型";

	/**
	 * "集中接送货大小区区域类型"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String BSE_REGION_TYPE = "BSE_REGION_TYPE";
	public static final String BSE_REGION_TYPE__DESC = "区域类型";

	/**
	 * "客户类型"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String BSE_CUSTOMER_TYPE = "BSE_CUSTOMER_TYPE";
	public static final String BSE_CUSTOMER_TYPE__DESC = "客户类型";

	/**
	 * CRM-"结算方式"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String BSE_CLEARING_TYPE = "CLEARING_TYPE";
	public static final String BSE_CLEARING_TYPE__DESC = "结算方式";
	
	/**
	 * CRM-"客户属性"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_CUSTOMER_ATTRIBUTE = "CRM_CUSTOMER_ATTRIBUTE";
	public static final String CRM_CUSTOMER_ATTRIBUTE__DESC = "CRM-客户属性";
	
	/**
	 * CRM-"客户类型"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_CUSTOMER_TYPE = "CRM_CUSTOMER_TYPE";
	public static final String CRM_CUSTOMER_TYPE__DESC = "CRM-客户类型";
	
	/**
	 * CRM-"散客类型"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_NONFIXED_CUSTOMER_TYPE = "CRM_NONFIXED_CUSTOMER_TYPE";
	public static final String CRM_NONFIXED_CUSTOMER_TYPE__DESC = "CRM-散客类型";
	
	/**
	 * CRM-"客户等级"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_CUSTOMER_GRADE = "CRM_CUSTOMER_GRADE";
	public static final String CRM_CUSTOMER_GRADE__DESC = "CRM-客户等级";
	
	/**
	 * CRM-"客户分群"的"词代码"
	 * 
	 * @author 232607-foss-杨在强
	 */
	public static final String CRM_CUSTOMER_GROUP = "CRM_CUSTOMER_GROUP";
	public static final String CRM_CUSTOMER_GROUP__DESC = "CRM-客户分群";
	
	/**
	 * CRM-"地址类型"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_ADDRESS_TYPE = "CRM_ADDRESS_TYPE";
	public static final String CRM_ADDRESS_TYPE__DESC = "CRM-地址类型";
	
	/**
	 * CRM-"付款方式"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_PAY_WAY = "CRM_PAY_WAY";
	public static final String CRM_PAY_WAY__DESC = "CRM-付款方式";
	
	/**
	 * CRM-"账户用途"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_ACCOUNT_USE = "CRM_ACCOUNT_USE";
	public static final String CRM_ACCOUNT_USE__DESC = "CRM-账户用途";
	
	/**
	 * CRM-"账户性质"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_ACCOUNT_NATURE = "CRM_ACCOUNT_NATURE";
	public static final String CRM_ACCOUNT_NATURE__DESC = "CRM-账户性质";
	
	/**
	 * CRM-"性别"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String CRM_SEX = "CRM_SEX";
	public static final String CRM_SEX__DESC = "CRM-性别";
	
	/**
	 * CRM-"订单类型"的"词条代码"
	 */
	public static final String CRM_ORDER_TYPE = "ORDERTYPE";
	public static final String CRM_ORDER_TYPE__DESC = "CRM-订单类型";
	
	/**
	 * CRM-"订单状态"的"词条代码"
	 */
	public static final String CRM_ORDER_STATUS = "ORDERSTATUS";
	public static final String CRM_ORDER_STATUS__DESC = "CRM-订单状态";
	
	/**
	 * 银企-"行政区域等级"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String BSE_DISTRICT_DEGREE = "BSE_DISTRICT_DEGREE";
	public static final String BSE_DISTRICT_DEGREE__DESC = "行政区域等级";

	/**
	 * 综合管理-广告语-广告语类型 "词条代码"
	 * 
	 * @author 100847-foss-xieyantao
	 */
	public static final String BSE_SLOGAN_TYPE = "BSE_SLOGAN_TYPE";
	public static final String BSE_SLOGAN_TYPE__DESC = "广告语类型";

	/**
	 * 综合管理-线路-"运输类型"的"词条代码"
	 * 
	 * @author 100847-foss-xieyantao
	 */
	public static final String BSE_TRANS_TYPE = "TRANS_TYPE";
	public static final String BSE_TRANS_TYPE__DESC = "运输类型";
    
	/**
	 * 综合管理-线路-"线路类型"的"词条代码"
	 */
	public static final String BSE_TRANS_LINE_TYPE="LINE_TYPE";
	public static final String BSE_TRANS_LINE_TYPE__DESC="线路类型";
	/**
	 * "中转-货量预测-货量预测类型"的"词条代码"
	 * 
	 * @author huyue
	 */
	public static final String TFR_FORECAST_TYPE = "TFR_FORECAST_TYPE";
	public static final String TFR_FORECAST_TYPE__DESC = "货量预测类型";

	/** BEGIN：******************************************* "空运"相关的词条代码 *********************************************/


	/**
	 * "飞机类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String AIRPLANE_TYPE_TERMSCODE = "AIRPLANE_TYPE";
	public static final String AIRPLANE_TYPE_TERMSCODE__DESC = "飞机类型";

	/** END：******************************************* "空运"相关的词条代码 *********************************************/

	/** BEGIN：******************************************* "车辆"相关的词条代码 *********************************************/

	/**
	 * "车辆类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String VEHICLE_TYPE_TERMSCODE = "BES_VEHICLE_TYPE";
	public static final String VEHICLE_TYPE_TERMSCODE__DESC = "车辆类型";

	/**
	 * "外请白名单"的白名单类型（司机、车）的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String LEASEDWHITELISTS_TYPE_TERMSCODE = "BES_WHITELISTS_TPYE";
	public static final String LEASEDWHITELISTS_TYPE_TERMSCODE__DESC = "白名单类型";

	/**
	 * "外请白名单（司机、车）"白名单状态的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String LEASEDWHITELISTS_AUDIT_TERMSCODE = "BES_WHITELISTS_AUDIT";
	public static final String LEASEDWHITELISTS_AUDIT_TERMSCODE__DESC = "外请司白名单状态的白名单";

	/**
	 * "外请白名单（司机、车）"当前申请的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String LEASEDWHITELISTS_APPLY_TERMSCODE = "BES_WHITELISTS_APPLY";
	public static final String LEASEDWHITELISTS_APPLY_TERMSCODE__DESC = "当前申请的";

	/**
	 * "车辆使用类型（接送货车、物流班车、长途车、后勤车）"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String VEHICLE_USED_TYPE_TERMSCODE = "BES_VEHICLE_USED_TYPE";
	public static final String VEHICLE_USED_TYPE_TERMSCODE__DESC = "车辆使用类型";

	/**
	 * "LMS同步的挂车类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String LMS_TRAILER_TYPE_TERMSCODE = "LMS_TRAILER_TYPE";
	public static final String LMS_TRAILER_TYPE_TERMSCODE__DESC = "挂车类型";

	/**
	 * "数据"的所属类型（公司的、外请的）"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String DATA_OWNERSHIP_TYPE_TERMSCODE = "DATA_OWNERSHIP_TYPE";
	public static final String DATA_OWNERSHIP_TYPE_TERMSCODE__DESC = "数据所属类型";

	/**
	 * "单双桥类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String VEHICLE_BRIGE_TYPE_TERMSCODE = "VEHICLE_BRIGE_TYPE";
	public static final String VEHICLE_BRIGE_TYPE_TERMSCODE__DESC = "单双桥类型";

	/**
	 * "停车原因"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String VEHICLE_STOP_REASON_TERMSCODE = "VEHICLE_STOP_REASON";
	public static final String VEHICLE_STOP_REASON_TERMSCODE__DESC = "停车原因";

	/**
	 * "车辆品牌"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String VEHICLE_BRAND_TYPE_TERMSCODE = "VEHICLE_BRAND_TYPE";
	public static final String VEHICLE_BRAND_TYPE_TERMSCODE__DESC = "车辆品牌";

	/** END：******************************************* "车辆"相关的词条代码 *********************************************/

	/** BEGIN：******************************************* "UUMS"相关的词条代码 *********************************************/

	/**
	 * "职等信息"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String UUMS_DEGREE_TERMSCODE = "UUMS_DEGREE";
	public static final String UUMS_DEGREE_TERMSCODE__DESC = "职等信息";

	/**
	 * "职位信息"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String UUMS_POSITION_TERMSCODE = "UUMS_POSITION";
	public static final String UUMS_POSITION_TERMSCODE__DESC = "职位信息";

	/** END：******************************************* "UUMS"相关的词条代码 *********************************************/

	/**
	 * "包装材料"的"词条代码"
	 * 
	 * @author 046130-foss-xuduowei
	 */
	public static final String PACKAGING_PACKED_MATERIAL = "PACKAGING_MATE";
	public static final String PACKAGING_PACKED_MATERIAL__DESC = "包装材料";
	/**
	 * "货物状态（包装）"的"词条代码"
	 * 
	 * @author 046130-foss-xuduowei
	 */
	public static final String PACKAGING_GOODS_STATUS = "TFR_GOODS_STATUS";
	public static final String PACKAGING_GOODS_STATUS__DESC = "货物状态";
	
	/**
	 * "货物状态（包装）"的"词条代码",用与后台查询，前台显示
	 * 
	 * @author 046130-foss-xuduowei
	 */
	public static final String PACKAGING_GOODS_STATUS_QUERY = "TFR_GOODS_STATUS_QUERY";
	public static final String PACKAGING_GOODS_STATUS_QUERY__DESC = "货物状态";

	public static final String BAS_DEST_NET_TYPE = "DEST_NET_TYPE";
	public static final String BAS_DEST_NET_TYPE__DESC = "提货网点类型";

	public static final String BAS_PRODUCT_FLAG = "PRODUCT_FLAG";
	public static final String BAS_PRODUCT_FLAG__DESC = "产品快慢车标识";

	public static final String BAS_COD_TYPE = "COD_TYPE";
	public static final String BAS_COD_TYPE__DESC = "代收货款类型";

	public static final String BAS_CURRENCY_CODE = "CURRENCY_CODE";
	public static final String BAS_CURRENCY_CODE__DESC = "币种";

	public static final String BAS_SIGN_RETURN_TYPE = "SIGN_RETURN_TYPE";
	public static final String BAS_SIGN_RETURN_TYPE__DESC = "签收回单类型";

	public static final String BAS_PACKAGE_TYPE = "PACKAGE_TYPE";
	public static final String BAS_PACKAGE_TYPE__DESC = "包装类型";

	/**
	 * 组织配置参数-综合模块使用
	 */
	public static final String SYSTEM_CONFIG_PARM__BAS = "SYSTEM_CONFIG_PARM__BAS";
	public static final String SYSTEM_CONFIG_PARM__BAS__DESC = "组织配置参数-综合模块";

	/**
	 * 组织配置参数-接送货模块使用
	 */
	public static final String SYSTEM_CONFIG_PARM__PKP = "SYSTEM_CONFIG_PARM__PKP";
	public static final String SYSTEM_CONFIG_PARM__PKP__DESC = "组织配置参数-接送货模块";

	/**
	 * 组织配置参数-中转模块使用
	 */
	public static final String SYSTEM_CONFIG_PARM__TFR = "SYSTEM_CONFIG_PARM__TFR";
	public static final String SYSTEM_CONFIG_PARM__TFR__DESC = "组织配置参数-中转模块";

	/**
	 * 组织配置参数-结算模块使用
	 */
	public static final String SYSTEM_CONFIG_PARM__STL = "SYSTEM_CONFIG_PARM__STL";
	public static final String SYSTEM_CONFIG_PARM__STL__DESC = "组织配置参数-结算模块";


	/**
	 * 组织级别（组织级别，取自数据字典，包括但不限于事业部，大区，小区，部门等）
	 */
	public static final String ORG_LEVEL = "ORG_LEVEL";
	public static final String ORG_LEVEL__DESC = "组织级别";

	
	/**
	 * "班次类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String TFR_FREQUENCY_TYPE = "TFR_FREQUENCY_TYPE";
	public static final String TFR_FREQUENCY_TYPE__DESC = "班次类型";

	/**
	 * "车辆状态"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String TFR_VECHILE_STATUS = "TFR_VECHILE_STATUS";
	public static final String TFR_VECHILE_STATUS__DESC = "中转-车辆状态";
	/**
	 * /** "计划状态"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String TFR_PLAN_STATUS = "TFR_PLAN_STATUS";
	public static final String TFR_PLAN_STATUS__DESC = "计划状态";
	/**
	 * "车辆归属类型"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String TFR_TRUCK_TYPE = "TFR_TRUCK_TYPE";
	public static final String TFR_TRUCK_TYPE__DESC = "车辆归属类型";
	/**
	 * "是否正班车"的"词条代码"
	 * 
	 * @author 100847-foss-GaoPeng
	 */
	public static final String TFR_IS_ON_SCHEDULING = "TFR_IS_ON_SCHEDULING";
	public static final String TFR_IS_ON_SCHEDULING__DESC = "是否正班车";

	/** BEGIN：******************************* 中转相关的词条代码 *********************************/

	/**
	 * 无标签货物库存状态
	 * 
	 * @author 097457-foss-wangqiang
	 */
	public static final String NO_LABEL_GOODS_STOCK_STATE = "NO_LABEL_GOODS_STOCK_STATE";
	public static final String NO_LABEL_GOODS_STOCK_STATE__DESC = "无标签货物库存状态";
	/**
	 * 入库类型
	 * 
	 * @author 097457-foss-wangqiang
	 */
	public static final String STOCK_TYPE_IN = "STOCK_TYPE_IN";
	public static final String STOCK_TYPE_IN__DESC = "入库类型";
	/**
	 * 违禁品移交状态
	 * 
	 * @author 097457-foss-wangqiang
	 */
	public static final String CONTRABAND_HANDOVER_STATUS = "CONTRABAND_HANDOVER_STATUS";
	public static final String CONTRABAND_HANDOVER_STATUS__DESC = "违禁品移交状态";

	/** 违禁品处理结果 */
	public static final String CONTRABAND_PROCESS_RESULT = "CONTRABAND_PROCESS_RESULT";
	public static final String CONTRABAND_PROCESS_RESULT__DESC = "违禁品处理结果";

	/** 违禁品返回方式 */
	public static final String CONTRABAND_RETURN_TYPE = "CONTRABAND_RETURN_TYPE";
	public static final String CONTRABAND_RETURN_TYPE__DESC = "违禁品返回方式";

	/** 无标签发现类型 */
	public static final String NO_LABEL_GOODS_DISCOVER_STATE = "NO_LABEL_GOODS_DISCOVER_STATE";
	public static final String NO_LABEL_GOODS_DISCOVER_STATE__DESC = "无标签发现类型";

	/** 无标签品类 */
	public static final String NO_LABEL_GOODS_CATEGORY = "NO_LABEL_GOODS_CATEGORY";
	public static final String NO_LABEL_GOODS_CATEGORY__DESC = "无标签品类";

	/** 无标签货物属性 */
	public static final String NO_LABEL_GOODS_MATERIAL_PROPERTY = "NO_LABEL_GOODS_MATERIAL_PROPERTY";
	public static final String NO_LABEL_GOODS_MATERIAL_PROPERTY__DESC = "无标签货物属性";

	/** 无标签找货状态 */
	public static final String NO_LABEL_GOODS_FIND_STATE = "NO_LABEL_GOODS_FIND_STATE";
	public static final String NO_LABEL_GOODS_FIND_STATE__DESC = "无标签找货状态";

	/** 无标签OA上报状态 */
	public static final String REPORT_OA_STATUS = "REPORT_OA_STATUS";
	public static final String REPORT_OA_STATUS__DESC = "无标签OA上报状态";

	/** 无标签包装类型 */
	public static final String NO_LABEL_GOODS_PACKAGE_TYPE = "NO_LABEL_GOODS_PACKAGE_TYPE";
	public static final String NO_LABEL_GOODS_PACKAGE_TYPE__DESC = "无标签包装类型";

	/**
	 * 借车状态
	 * 
	 * @author 104306-foss-wangLong
	 */
	public static final String TFR_BORROWVEHICLE_STATUS = "BORROWVEHICLE_STATUS";
	public static final String TFR_BORROWVEHICLE_STATUS__DESC = "借车状态";

	/** 借车使用类型 */
	public static final String ORROWVEHICLE_USE_TYPE = "ORROWVEHICLE_USE_TYPE";
	public static final String ORROWVEHICLE_USE_TYPE__DESC = "借车使用类型";

	/** 约车状态 */
	public static final String ORDERVEHICLE_STATUS = "ORDERVEHICLE_STATUS";
	public static final String ORDERVEHICLE_STATUS__DESC = "约车状态";

	/** 预约类型 */
	public static final String ORDERVEHICLE_ORDERTYPE = "ORDERVEHICLE_ORDERTYPE";
	public static final String ORDERVEHICLE_ORDERTYPE__DESC = "预约类型";

	/** 装车任务类型 */
	public static final String LOAD_TASK_TYPE = "TASK_TYPE";
	public static final String LOAD_TASK_TYPE__DESC = "装车类型";

	/** 月台任务状态 */
	public static final String PLATFORMDISPATCH_STATUS = "PLATFORMDISPATCH_STATUS";
	public static final String PLATFORMDISPATCH_STATUS__DESC = "月台任务状态";

	/** 月台停靠类型 */
	public static final String PLATFORMDISPATCH_TYPE = "PLATFORMDISPATCH_TYPE";
	public static final String PLATFORMDISPATCH_TYPE__DESC = "月台停靠类型";

	/** 月台任务来源 */
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE = "PLATFORMDISPATCH_SCHEDULESOURCE";
	public static final String PLATFORMDISPATCH_SCHEDULESOURCE__DESC = "月台任务来源";

	/** 扫描状态 */
	public static final String SCAN_STATE = "SCAN_STATE";
	public static final String SCAN_STATE__DESC = "扫描状态";

	/** 装车任务状态 */
	public static final String LOAD_TASK_STATE = "TASK_STATE";
	public static final String LOAD_TASK_STATE__DESC = "装车状态";

	/** 装车货物状态 */
	public static final String LOAD_GOODS_STATE = "LOAD_GOODS_STATE";
	public static final String LOAD_GOODS_STATE__DESC = "装车货物状态";

	/** 派送装车差异报告状态 */
	public static final String DELIVER_LOAD_GAP_REPORT_STATE = "GAPREP_STATUS";
	public static final String DELIVER_LOAD_GAP_REPORT_STATE__DESC = "派送装车差异报告状态";

	/** 已分配派送装车任务状态 */
	public static final String ASSIGN_DELIVER_LOAD_TASK_STATE = "ASSIGN_DELIVER_LOAD_TASK_STATE";
	public static final String ASSIGN_DELIVER_LOAD_TASK_STATE__DESC = "已分配派送装车任务状态";

	/** 派送单状态 */
	public static final String DELIVER_BILL_STATE_STATE = "DELIVER_BILL_STATE_STATE";
	public static final String DELIVER_BILL_STATE_STATE__DESC = "派送单状态";

	/** 理货员参与情况类型 */
	public static final String LOADER_PARTICIPATION = "DELIVER_BILL_STATE_STATE";
	public static final String LOADER_PARTICIPATION__DESC = "理货员参与情况类型";

	/** 装卸车车工作量操作类型 */
	public static final String LOADER_WORKLOAD_HANDLE_TYPE = "LOADER_WORKLOAD_HANDLE_TYPE";
	public static final String LOADER_WORKLOAD_HANDLE_TYPE__DESC = "装卸车车工作量操作类型";

	/** 装卸车车工作量任务类型 */
	public static final String LOADER_WORKLOAD_TASK_TYPE = "LOADER_WORKLOAD_TASK_TYPE";
	public static final String LOADER_WORKLOAD_TASK_TYPE__DESC = "装卸车车工作量任务类型";

	/** 配载类型 */
	public static final String ASSEMBLE_TYPE = "ASSEMBLE_TYPE";
	public static final String ASSEMBLE_TYPE__DESC = "配载类型";

	/**配载单付款方式*/
	public static final String ASSEMBLE_PAYMENT_TYPE = "ASSEMBLE_PAYMENT_TYPE";
	public static final String ASSEMBLE_PAYMENT_TYPE_DESC = "配载单付款方式";
	
	/** 配载单运输性质 */
	public static final String ASSEMBLE_TRANSPROPERTY = "ASSEMBLE_TYPE";
	public static final String ASSEMBLE_TRANSPROPERTY__DESC = "配载单运输性质";

	/** 奖罚类型 */
	public static final String AWARD_TYPE = "AWARD_TYPE";
	public static final String AWARD_TYPE__DESC = "奖罚类型";

	/** 增减类型 */
	public static final String ADJUST_TYPE = "ADJUST_TYPE";
	public static final String ADJUST_TYPE__DESC = "增减类型";

	/** 费用增加原因 */
	public static final String FEE_AUGMENT_REASON = "FEE_AUGMENT_REASON";
	public static final String FEE_AUGMENT_REASON__DESC = "费用增加原因";

	/** 费用减少原因 */
	public static final String FEE_REDUCE_REASON = "FEE_REDUCE_REASON";
	public static final String FEE_REDUCE_REASON__DESC = "费用减少原因";

	/** 调整原因 */
	public static final String ADJUST_REASON = "ADJUST_REASON";
	public static final String ADJUST_REASON__DESC = "调整原因";

	/** 时效提前原因 */
	public static final String CAUSE_AGINGINADVANCE_REASON = "CAUSE_AGINGINADVANCE_REASON";
	public static final String CAUSE_AGINGINADVANCE_REASON__DESC = "时效提前原因";

	/** 时效延误原因 */
	public static final String CAUSE_AGINGDELAY_REASON = "CAUSE_AGINGDELAY_REASON";
	public static final String CAUSE_AGINGDELAY_REASON__DESC = "时效延误原因";

	/** 调整审核状态 */
	public static final String ADJUST_AUDIT_STATE = "ADJUST_AUDIT_STATE";
	public static final String ADJUST_AUDIT_STATE__DESC = "调整审核状态";

	/** 签单类型 */
	public static final String SIGN_BILL_TYPE = "SIGN_BILL_TYPE";
	public static final String SIGN_BILL_TYPE__DESC = "签单类型";

	/** 用车类型 */
	public static final String USE_TRUCK_TYPE = "USE_TRUCK_TYPE";
	public static final String USE_TRUCK_TYPE__DESC = "用车类型";

	/** 空运配载类型 */
	public static final String AIR_ASSEMBLE_TYPE = "AIR_ASSEMBLE_TYPE";
	public static final String AIR_ASSEMBLE_TYPE__DESC = "空运配载类型";

	/** 航班类型 */
	public static final String AIR_FLIGHT_TYPE = "AIR_FLIGHT_TYPE";
	public static final String AIR_FLIGHT_TYPE__DESC = "航班类型";

	/** 运价种类 */
	public static final String AIR_RATE_CLASS = "AIR_RATE_CLASS";
	public static final String AIR_RATE_CLASS__DESC = "运价种类";

	/** 正单付款方式 */
	public static final String AIR_PAYMENT_TYPE = "AIR_PAYMENT_TYPE";
	public static final String AIR_PAYMENT_TYPE__DESC = "运正单付款方式";

	/** 正单提货方式 */
	public static final String AIR_PICKUP_TYPE = "AIR_PICKUP_TYPE";
	public static final String AIR_PICKUP_TYPE__DESC = "正单提货方式";

	/** 正单跟踪状态 */
	public static final String AIR_TRACK_STATE = "AIR_TRACK_STATE";
	public static final String AIR_TRACK_STATE__DESC = "正单提货方式";

	/** 订舱受理状态 */
	public static final String AIR_ACCEPT_STATUS = "AIR_ACCEPT_STATUS";
	public static final String AIR_ACCEPT_STATUS__DESC = "订舱受理状态";

	/** 偏线外发单状态 */
	public static final String PARTIALLINE_AUDIT_STATUS = "PARTIALLINE_AUDIT_STATUS";
	public static final String PARTIALLINE_AUDIT_STATUS__DESC = "偏线外发单状态";

	/** 车头证件类型 */
	public static final String VEHICLEHEAD_CARD_TYPE = "VEHICLEHEAD_CARD_TYPE";
	public static final String VEHICLEHEAD_CARD_TYPE__DESC = "车头证件类型";

	/** 车柜证件类型 */
	public static final String CONTAINER_CARD_TYPE = "CONTAINER_CARD_TYPE";
	public static final String CONTAINER_CARD_TYPE__DESC = "车柜证件类型";

	/** 车辆证件类型 */
	public static final String VEHICLE_CARD_TYPE = "VEHICLE_CARD_TYPE";
	public static final String VEHICLE_CARD_TYPE__DESC = "车辆证件类型";

	/** 放行类型 */
	public static final String DEPART_TYPE = "DEPART_TYPE";
	public static final String DEPART_TYPE__DESC = "放行类型";

	/** 车辆放行明细 */
	public static final String TRUCK_DEPART_DETAIL = "TRUCK_DEPART_DETAIL";
	public static final String TRUCK_DEPART_DETAIL__DESC = "车辆放行明细";

	/** 车辆追踪状态 */
	public static final String JOB_TRUCK_STATUS = "JOB_TRUCK_STATUS";
	public static final String JOB_TRUCK_STATUS__DESC = "车辆追踪状态";

	/** 车辆状态追踪类型 */
	public static final String JOB_TRUCK_TYPE = "JOB_TRUCK_TYPE";
	public static final String JOB_TRUCK_TYPE__DESC = "车辆状态追踪类型";

	/** 车辆当前状态 */
	public static final String TRUCK_CURRENT_STATUS = "TRUCK_CURRENT_STATUS";
	public static final String TRUCK_CURRENT_STATUS__DESC = "车辆状态追踪类型";

	/** 放行申请方式 */
	public static final String APPLY_DEPART_TYPE = "APPLY_DEPART_TYPE";
	public static final String APPLY_DEPART_TYPE__DESC = "车辆状态追踪类型";

	/** 车辆业务类型 */
	public static final String ARRIVE_TYPE_TFR = "ARRIVE_TYPE_TFR";
	public static final String ARRIVE_TYPE_TFR__DESC = "车辆业务类型";

	/** 放行申请状态 */
	public static final String APPLY_DEPART_STATUS = "APPLY_DEPART_STATUS";
	public static final String APPLY_DEPART_STATUS__DESC = "放行申请状态";

	/** 证件包状态 */
	public static final String CERTIFICATE_BAG_STATUS = "CERTIFICATE_BAG_STATUS";
	public static final String CERTIFICATE_BAG_STATUS__DESC = "放行申请状态";

	/** 清仓任务状态 */
	public static final String TFR_ST_TASK_STATUS = "TFR_ST_TASK_STATUS";
	public static final String TFR_ST_TASK_STATUS__DESC = "清仓任务状态";

	/** 清仓差异报告处理状态 */
	public static final String TFR_ST_TASK_REPORT_STATUS = "TFR_ST_TASK_STATUS";
	public static final String TFR_ST_TASK_REPORT_STATUS__DESC = "清仓差异报告处理状态";

	/** 清仓状态（货区） */
	public static final String TFR_ST_TASK_GOODS_AREA_STATUS = "TFR_ST_TASK_GOODS_AREA_STATUS";
	public static final String TFR_ST_TASK_GOODS_AREA_STATUS__DESC = "清仓状态（货区）";

	/** 货物状态 */
	public static final String GOODS_STATUS = "GOODS_STATUS";
	public static final String GOODS_STATUS__DESC = "货物状态";

	/** 外请约车状态 */
	public static final String TFR_INVITEVEHICLE_STATUS = "INVITEVEHICLE_STATUS";
	public static final String TFR_INVITEVEHICLE_STATUS__DESC = "外请约车状态";


	/** 车队/车队组的“车队类型” 
	 * 对应类BizTypeConstants的“中转-装车”，“中转-卸车”，“中转-装卸车”，“派送装卸车”：
	 */
	public static final String TFR_LOAD_UNLOAD_TYPE = "TFR_LOAD_UNLOAD_TYPE";
	public static final String TFR_LOAD_UNLOAD_TYPE__DESC = "装卸车类型";
	/** END：******************************* 中转相关的词条代码 *********************************/

	
	/** BEGIN：******************************* 车队车队组相关的词条代码 *********************************/

	/** 车队/车队组的“车队类型” */
	public static final String BSE_FLEET_TYPE = "BSE_FLEET_TYPE";
	public static final String BSE_FLEET_TYPE__DESC = "车队类型";
	/** END：******************************* 车队车队组相关的词条代码 *********************************/

	
	/** BEGIN：******************************* 接送货相关的词条代码 *********************************/

	/** 计费类型 */
	public static final String BILLING_WAY = "BILLINGWAY";
	public static final String BILLING_WAY__DESC = "计费类型";

	/** 提货方式（汽运） */
	public static final String PICKUP_GOODS = "PICKUPGOODSHIGHWAYS";
	public static final String PICKUP_GOODS__DESC = "提货方式";

	/** 提货方式（空运） */
	public static final String PICKUP_GOODS_AIR = "PICKUPGOODSAIR";
	public static final String PICKUP_GOODS_AIR__DESC = "提货方式";
	
	/** 特殊增值服务       */
	public static final String SPECIAL_VALUE_ADDED_SERVICE_TYPE="SPECIAL_VALUE_ADDED_SERVICE_TYPE";
	public static final String SPECIAL_VALUE_ADDED_SERVICE_TYPE__DESC = "特殊增值服务";
	
	/** 特殊增值服务相对应的提货方式*/
	public static final String SPECIAL_DELIVERY_TYPE="PICKUPGOODSSPECIALDELIVERYTYPE";
	public static final String SPECIAL_DELIVERY_TYPE__DESC="特殊增值服务相对应的提货方式";
	
	/** 对外备注 */
	public static final String OUTER_REMARK = "OUTERREMARK";
	public static final String OUTER_REMARK__DESC = "对外备注";

	/** 付款方式 */
	public static final String PAYMENT_MODE = "PAYMENTMODE";
	public static final String PAYMENT_MODE__DESC = "付款方式";

	/** 退款类型 */
	public static final String REFUND_TYPE = "REFUNDTYPE";
	public static final String REFUND_TYPE__DESC = "退款类型";
	
	/** 空运货物类型 */
	public static final String AIR_GOODS_TYPE = "AIRGOODSTYPE";
	public static final String AIR_GOODS_TYPE__DESC = "空运货物类型";

	/** 返单类型 */
	public static final String RETURN_BILL_TYPE = "RETURNBILLTYPE";
	public static final String RETURN_BILL_TYPE__DESC = "返单类型";

	/** 合票类型 */
	public static final String MAKE_WAYBILL_WAY = "MAKE_WAYBILL_WAY";
	public static final String MAKE_WAYBILL_WAY__DESC = "合票类型";

	/** 订单状态 */
	public static final String PKP_ORDER_STATUS = "PKP_ORDER_STATUS";
	public static final String PKP_ORDER_STATUS__DESC = "订单状态";

	/** 订单发送状态 */
	public static final String PKP_ORDER_SEND_STATUS = "PKP_ORDER_SEND_STATUS";
	public static final String PKP_ORDER_SEND_STATUS__DESC = "订单发送状态";

	/** 订单类型 */
	public static final String PKP_ORDER_TYPE = "PKP_ORDER_TYPE";
	public static final String PKP_ORDER_TYPE__DESC = "订单类型";

	/** 订单拒绝原因 */
	public static final String PKP_ORDER_REJECT_REASON = "PKP_ORDER_REJECT_REASON";
	public static final String PKP_ORDER_REJECT_REASON__DESC = "订单拒绝原因";

	/** PDA签到状态 */
	public static final String PKP_PDA_SIGN_STATUS = "PKP_PDA_SIGN_STATUS";
	public static final String PKP_PDA_SIGN_STATUS__DESC = "PDA签到状态";

	/** 通知状态 */
	public static final String PKP_NOTIFY_CUSTOMER_STATUS = "PKP_NOTIFY_CUSTOMER_STATUS";
	public static final String PKP_NOTIFY_CUSTOMER_STATUS__DESC = "通知状态";

	/** 通知类型 */
	public static final String PKP_NOTIFY_CUSTOMER_TYPE = "PKP_NOTIFY_CUSTOMER_TYPE";
	public static final String PKP_NOTIFY_CUSTOMER_TYPE__DESC = "通知类型";

	/** 派送单状态 */
	public static final String PKP_DELIVERBILL_STATUS = "PKP_DELIVERBILL_STATUS";
	public static final String PKP_DELIVERBILL_STATUS__DESC = "派送单状态";

	/** 到达联状态 */
	public static final String PKP_ARRIVESHEET_STATUS = "PKP_ARRIVESHEET_STATUS";
	public static final String PKP_ARRIVESHEET_STATUS__DESC = "到达联状态";

	/** 异常类型 */
	public static final String PKP_EXCEPTION_TYPE = "PKP_EXCEPTION_TYPE";
	public static final String PKP_EXCEPTION_TYPE__DESC = "异常类型";

	/** 异常环节 */
	public static final String PKP_EXCEPTION_PHASE = "PKP_EXCEPTION_PHASE";
	public static final String PKP_EXCEPTION_PHASE__DESC = "异常环节";

	/** 异常状态 */
	public static final String PKP_EXCEPTION_STATE = "PKP_EXCEPTION_STATE";
	public static final String PKP_EXCEPTION_STATE__DESC = "异常状态";
	
	/** 异常操作 */
	public static final String PKP_EXCEPTION_OPERATE = "PKP_EXCEPTION_OPERATE";
	public static final String PKP_EXCEPTION_OPERATE__DESC = "异常操作";

	/** 异常转弃货状态 */
	public static final String PKP_ABANDONGOODS_STATUS = "PKP_ABANDONGOODS_STATUS";
	public static final String PKP_ABANDONGOODS_STATUS__DESC = "异常转弃货状态";

	/** 弃货类型 */
	public static final String PKP_ABANDONGOODS_TYPE = "PKP_ABANDONGOODS_TYPE";
	public static final String PKP_ABANDONGOODS_TYPE__DESC = "弃货类型";

	/** OA工作流弃货状态 */
	public static final String PKP_ABANDONGOODS_WORKFLOW_TYPE = "PKP_ABANDONGOODS_WORKFLOW_TYPE";
	public static final String PKP_ABANDONGOODS_WORKFLOW_TYPE__DESC = "OA工作流弃货状态";

	/** 签收变更审批状态 */
	public static final String PKP_SIGN_RFC_APPROVE_STATUS = "PKP_SIGN_RFC_APPROVE_STATUS";
	public static final String PKP_SIGN_RFC_APPROVE_STATUS__DESC = "签收变更审批状态";

	/** 签收变更类型 */
	public static final String PKP_SIGN_RFC_TYPE = "PKP_SIGN_RFC_TYPE";
	public static final String PKP_SIGN_RFC_TYPE__DESC = "签收变更类型";

	/** 签收变更明细类型 */
	public static final String PKP_SIGN_RFC_DETAIL_TYPE = "PKP_SIGN_RFC_DETAIL_TYPE";
	public static final String PKP_SIGN_RFC_DETAIL_TYPE__DESC = "签收变更明细类型";

	/** 反签收明细类型 */
	public static final String PKP_SIGN_REVERSE_DETAIL_TYPE = "PKP_SIGN_REVERSE_DETAIL_TYPE";
	public static final String PKP_SIGN_REVERSE_DETAIL_TYPE__DESC = "反签收明细类型";

	/** 证件类型 */
	public static final String PKP_CREDENTIAL_TYPE = "PKP_CREDENTIAL_TYPE";
	public static final String PKP_CREDENTIAL_TYPE__DESC = "证件类型";

	/** 签收情况 */
	public static final String PKP_SIGN_SITUATION = "PKP_SIGN_SITUATION";
	public static final String PKP_SIGN_SITUATION__DESC = "签收情况";

	/** 拉回原因 */
	public static final String PKP_PULLBACK_REASON = "PKP_PULLBACK_REASON";
	public static final String PKP_PULLBACK_REASON__DESC = "拉回原因";

	/** 返单状态 */
	public static final String PKP_RETURNBILL_STATUS = "PKP_RETURNBILL_STATUS";
	public static final String PKP_RETURNBILL_STATUS__DESC = "返单状态";
	
	/** 跟踪类别 */
	public static final String PKP_TRACK_TYPE = "PKP_TRACK_TYPE";
	public static final String PKP_TRACK_TYPE__DESC = "跟踪类别";
	
	/** 跟踪方式  */
	public static final String PKP_TRACK_METHOD = "PKP_TRACK_METHOD";
	public static final String PKP_TRACK_METHOD__DESC = "跟踪方式 ";
	
	/** 公司拒绝原因  */
	public static final String PKP_COMPANY_REJECT_REASON = "PKP_COMPANY_REJECT_REASON";
	public static final String PKP_COMPANY_REJECT_REASON__DESC = "公司拒绝原因 ";
	
	/** 客户拒绝原因  */
	public static final String PKP_CUSTOMER_REJECT_REASON = "PKP_CUSTOMER_REJECT_REASON";
	public static final String PKP_CUSTOMER_REJECT_REASON__DESC = "客户拒绝原因 ";
	
	
	/** 变更来源  */
	public static final String WAYBILL_RFC_SOURCE = "WAYBILL_RFC_SOURCE";
	public static final String WAYBILL_RFC_SOURCE__DESC = "变更来源";

	/** 客户变更类型  */
	public static final String WAYBILL_RFC_TYPE_CUSTOMER = "WAYBILL_RFC_TYPE_CUSTOMER";
	public static final String WAYBILL_RFC_TYPE_CUSTOMER__DESC = "客户变更类型";

	/** 内部变更类型  */
	public static final String WAYBILL_RFC_TYPE_INSIDE = "WAYBILL_RFC_TYPE_INSIDE";
	public static final String WAYBILL_RFC_TYPE_INSIDE__DESC = "内部变更类型";

	/** 更改单状态  */
	public static final String WAYBILL_RFC_STATUS = "WAYBILL_RFC_STATUS";
	public static final String WAYBILL_RFC_STATUS__DESC = "更改单状态";

	/** 货物库存状态  */
	public static final String WAYBILL_RFC_STOCK_STATUS = "WAYBILL_RFC_STOCK_STATUS";
	public static final String WAYBILL_RFC_STOCK_STATUS__DESC = "货物库存状态";

	/** 更改单核销状态  */
	public static final String BILL_WAYLLBAY_WRITEOFF_STATUS = "BILL_WAYLLBAY_WRITEOFF_STATUS";
	public static final String BILL_WAYLLBAY_WRITEOFF_STATUS__DESC = "更改单核销状态";
    /**
     * 内部发货类型
     */
	public static final String INTERNAL_DELIVERY_TYPE = "INTERNAL_DELIVERY_TYPE";
	public static final String INTERNAL_DELIVERY_TYPE__DESC = "内部发货类型";
	/**
	 * 客户更改原因类型
	 * RFC_REASON
	 */
	public static final String RFC_REASON = "RFC_REASON";
	public static final String RFC_REASON__DESC = "客户更改原因类型";
	/** END：******************************* 接送货相关的词条代码 *********************************/

	/** BEGIN：******************************* 结算相关的词条代码 *********************************/

	/* 是否红单 */
	public static final String SETTLEMENT__IS_RED_BACK = "SETTLEMENT__IS_RED_BACK";
	public static final String SETTLEMENT__IS_RED_BACK__DESC = "是否红单";

	/* 单据生成方式 */
	public static final String SETTLEMENT__CREATE_TYPE = "SETTLEMENT__CREATE_TYPE";
	public static final String SETTLEMENT__CREATE_TYPE__DESC = "单据生成方式";

	/* 币种 */
	public static final String SETTLEMENT__CURRENCY_CODE = "SETTLEMENT__CURRENCY_CODE";
	public static final String SETTLEMENT__CURRENCY_CODE__DESC = "币种";

	/* 支付方式 */
	public static final String SETTLEMENT__PAYMENT_TYPE = "SETTLEMENT__PAYMENT_TYPE";
	public static final String SETTLEMENT__PAYMENT_TYPE__DESC = "支付方式";
	
	/* 单据类型 */
	public static final String BILL_PARENT_TYPE = "BILL_PARENT_TYPE";
	public static final String BILL_PARENT_TYPE__DESC = "单据类型";

	/* 应收单单据子类型 */
	public static final String BILL_RECEIVABLE__BILL_TYPE = "BILL_RECEIVABLE__BILL_TYPE";
	public static final String BILL_RECEIVABLE__BILL_TYPE__DESC = "应收单单据子类型";

	/* 应收单来源单据子类型 */
	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE = "BILL_RECEIVABLE__SOURCE_BILL_TYPE";
	public static final String BILL_RECEIVABLE__SOURCE_BILL_TYPE__DESC = "应收单来源单据子类型";

	/* 应收单付款方式 */
	public static final String BILL_RECEIVABLE__PAYMENT_TYPE = "BILL_RECEIVABLE__PAYMENT_TYPE";
	public static final String BILL_RECEIVABLE__PAYMENT_TYPE__DESC = "应收单付款方式";

	/* 应收单审核状态 */
	public static final String BILL_RECEIVABLE__APPROVE_STATUS = "BILL_RECEIVABLE__APPROVE_STATUS";
	public static final String BILL_RECEIVABLE__APPROVE_STATUS__DESC = "应收单审核状态";

	/* 应收单收款类别 */
	public static final String BILL_RECEIVABLE__COLLECTION_TYPE = "BILL_RECEIVABLE__COLLECTION_TYPE";
	public static final String BILL_RECEIVABLE__COLLECTION_TYPE__DESC = "收款类别";

	/* 应付单单据子类型 */
	public static final String BILL_PAYABLE__BILL_TYPE = "BILL_PAYABLE__BILL_TYPE";
	public static final String BILL_PAYABLE__BILL_TYPE__DESC = "应付单单据子类型";

	/* 应付单来源单据子类型 */
	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE = "BILL_PAYABLE__SOURCE_BILL_TYPE";
	public static final String BILL_PAYABLE__SOURCE_BILL_TYPE__DESC = "应付单来源单据子类型";

	/* 应付单生效状态 */
	public static final String BILL_PAYABLE__EFFECTIVE_STATUS = "BILL_PAYABLE__EFFECTIVE_STATUS";
	public static final String BILL_PAYABLE__EFFECTIVE_STATUS__DESC = "应付单生效状态";

	/* 应付单支付状态 */
	public static final String BILL_PAYABLE__PAY_STATUS = "BILL_PAYABLE__PAY_STATUS";
	public static final String BILL_PAYABLE__PAY_STATUS__DESC = "应付单支付状态";

	/* 应付单冻结状态 */
	public static final String BILL_PAYABLE__FROZEN_STATUS = "BILL_PAYABLE__FROZEN_STATUS";
	public static final String BILL_PAYABLE__FROZEN_STATUS__DESC = "应付单冻结状态";

	/* 应付单付款状态 */
	public static final String BILL_PAYABLE__PAYMENT_STATUS = "BILL_PAYABLE__PAYMENT_STATUS";
	public static final String BILL_PAYABLE__PAYMENT_STATUS__DESC = "应付单付款状态";

	/* 应付单审核状态 */
	public static final String BILL_PAYABLE__APPROVE_STATUS = "BILL_PAYABLE__APPROVE_STATUS";
	public static final String BILL_PAYABLE__APPROVE_STATUS__DESC = "应付单审核状态";

	/* 应付单付款方 */
	public static final String BILL_PAYABLE__PAYER_TYPE = "BILL_PAYABLE__PAYER_TYPE";
	public static final String BILL_PAYABLE__PAYER_TYPE__DESC = "应付单付款方";

	/* 应付单应付类型 */
	public static final String BILL_PAYABLE__PAYABLE_TYPE = "BILL_PAYABLE__PAYABLE_TYPE";
	public static final String BILL_PAYABLE__PAYABLE_TYPE__DESC = "应付单应付类型";

	/* 应付单支付类别 */
	public static final String BILL_PAYABLE__BILL_PAYABLE__PAYMENT_CATEGORIES = "BILL_PAYABLE__PAYMENT_CATEGORIES";
	public static final String BILL_PAYABLE__BILL_PAYABLE__PAYMENT_CATEGORIES__DESC = "应付单支付类别";

	/* 现金收款单单据类型 */
	public static final String BILL_CASH_COLLECTION__BILL_TYPE = "BILL_CASH_COLLECTION__BILL_TYPE";
	public static final String BILL_CASH_COLLECTION__BILL_TYPE__DESC = "现金收款单单据类型";

	/* 现金收款单来源单据子类型 */
	public static final String BILL_CASH_COLLECTION__SOURCE_BILL_TYPE = "BILL_CASH_COLLECTION__SOURCE_BILL_TYPE";
	public static final String BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__DESC = "现金收款单来源单据子类型";

	/* 现金收款单单据状态 */
	public static final String BILL_CASH_COLLECTION__STATUS = "BILL_CASH_COLLECTION__STATUS";
	public static final String BILL_CASH_COLLECTION__STATUS__DESC = "现金收款单单据状态";

	/* 预收单单据子类型 */
	public static final String BILL_DEPOSIT_RECEIVED__BILL_TYPE = "BILL_DEPOSIT_RECEIVED__BILL_TYPE";
	public static final String BILL_DEPOSIT_RECEIVED__BILL_TYPE__DESC = "预收单单据子类型";

	/* 预收单退款状态 */
	public static final String BILL_DEPOSIT_RECEIVED__REFUND_STATUS = "BILL_DEPOSIT_RECEIVED__REFUND_STATUS";
	public static final String BILL_DEPOSIT_RECEIVED__REFUND_STATUS__DESC = "预收单退款状态";

	/* 预收单单据状态 */
	public static final String BILL_DEPOSIT_RECEIVED__STATUS = "BILL_DEPOSIT_RECEIVED__STATUS";
	public static final String BILL_DEPOSIT_RECEIVED__STATUS__DESC = "预收单单据状态";

	/* 预收单运输类型 */
	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE = "BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE";
	public static final String BILL_DEPOSIT_RECEIVED__TRANSPORT_TYPE__DESC = "预收单运输类型";

	/* 预付单单据子类型 */
	public static final String BILL_ADVANCED_PAYMENT__BILL_TYPE = "BILL_ADVANCED_PAYMENT__BILL_TYPE";
	public static final String BILL_ADVANCED_PAYMENT__BILL_TYPE__DESC = "预付单单据子类型";

	/* 预付单审批状态 */
	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS = "BILL_ADVANCED_PAYMENT__AUDIT_STATUS";
	public static final String BILL_ADVANCED_PAYMENT__AUDIT_STATUS__DESC = "预付单审批状态";

	/* 还款单单据状态 */
	public static final String BILL_REPAYMENT__STATUS = "BILL_REPAYMENT__STATUS";
	public static final String BILL_REPAYMENT__STATUS__DESC = "还款单单据状态";

	/* 还款单审核状态 */
	public static final String BILL_REPAYMENT__AUDIT_STATUS = "BILL_REPAYMENT__AUDIT_STATUS";
	public static final String BILL_REPAYMENT__AUDIT_STATUS__DESC = "还款单审核状态";

	/* 还款单单据类型 */
	public static final String BILL_REPAYMENT__BILL_TYPE = "BILL_REPAYMENT__BILL_TYPE";
	public static final String BILL_REPAYMENT__BILL_TYPE__DESC = "还款单单据类型";

	/* 付款单汇款状态 */
	public static final String BILL_PAYMENT__REMIT_STATUS = "BILL_PAYMENT__REMIT_STATUS";
	public static final String BILL_PAYMENT__REMIT_STATUS__DESC = "付款单汇款状态";

	/* 付款单审核状态 */
	public static final String BILL_PAYMENT__AUDIT_STATUS = "BILL_PAYMENT__AUDIT_STATUS";
	public static final String BILL_PAYMENT__AUDIT_STATUS__DESC = "付款单审核状态";

	/* 付款单来源单据子类型 */
	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE = "BILL_PAYMENT__SOURCE_BILL_TYPE";
	public static final String BILL_PAYMENT__SOURCE_BILL_TYPE__DESC = "付款单来源单据子类型";

	/* 付款单单据类型 */
	public static final String BILL_PAYMENT__BILL_TYPE = "BILL_PAYMENT__BILL_TYPE";
	public static final String BILL_PAYMENT__BILL_TYPE__DESC = "付款单单据类型";

	/* 对账单确认状态 */
	public static final String STATEMENT_OF_ACCOUNT__CONFIRM_STATUS = "STATEMENT_OF_ACCOUNT__CONFIRM_STATUS";
	public static final String STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__DESC = "对账单确认状态";

	/* 对账单单据类型 */
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE = "STATEMENT_OF_ACCOUNT__BILL_TYPE";
	public static final String STATEMENT_OF_ACCOUNT__BILL_TYPE__DESC = "对账单单据类型";

	/* 对账单明细单据父类型 */
	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE = "STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE";
	public static final String STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DESC = "对账单明细单据父类型 ";

	/* 核销单核销方式 */
	public static final String BILL_WRITEOFF__WRITEOFF_TYPE = "BILL_WRITEOFF__WRITEOFF_TYPE";
	public static final String BILL_WRITEOFF__WRITEOFF_TYPE__DESC = "核销单核销方式";

	/* 代收货款类型 */
	public static final String COD__COD_TYPE = "COD__COD_TYPE";
	public static final String COD__COD_TYPE__DESC = "代收货款类型";

	/* 代收货款退款路径 */
	public static final String COD__REFUND_PATH = "COD__REFUND_PATH";
	public static final String COD__REFUND_PATH__DESC = "代收货款退款路径";

	/* 对公对私标志 */
	public static final String COD__PUBLIC_PRIVATE_FLAG = "COD__PUBLIC_PRIVATE_FLAG";
	public static final String COD__PUBLIC_PRIVATE_FLAG__DESC = "对公对私标志";

	/* 空运代收货款状态 */
	public static final String COD__AIR_STATUS = "COD__AIR_STATUS";
	public static final String COD__AIR_STATUS__DESC = "空运代收货款状态";

	/* 代收货款状态 */
	public static final String COD__STATUS = "COD__STATUS";
	public static final String COD__STATUS__DESC = "代收货款状态";

	/* 代收货款操作类型 */
	public static final String COD_LOG__OPERATE_TYPE = "COD_LOG__OPERATE_TYPE";
	public static final String COD_LOG__OPERATE_TYPE__DESC = "代收货款操作类型";

	/* 代收批次状态 */
	public static final String COD_BATCH__STATUS = "COD_BATCH__STATUS";
	public static final String COD_BATCH__STATUS__DESC = "代收批次状态";
	
	/* 小票申请单据状态 */
	public static final String NOTE_APPLICATION__STATUS = "NOTE_APPLICATION__STATUS";
	public static final String NOTE_APPLICATION__STATUS__DESC = "小票申请单据状态";

	/* 小票申请审批状态 */
	public static final String NOTE_APPLICATION__APPROVE_STATUS = "NOTE_APPLICATION__APPROVE_STATUS";
	public static final String NOTE_APPLICATION__APPROVE_STATUS__DESC = "小票申请审批状态";

	/* 小票申请核销状态 */
	public static final String NOTE_APPLICATION__WRITE_OFF_STATUS = "NOTE_APPLICATION__WRITE_OFF_STATUS";
	public static final String NOTE_APPLICATION__WRITE_OFF_STATUS__DESC = "小票申请核销状态";

	/* 小票发放下发方式 */
	public static final String NOTE_STOCK_IN__ISSUED_TYPE = "NOTE_STOCK_IN__ISSUED_TYPE";
	public static final String NOTE_STOCK_IN__ISSUED_TYPE__DESC = "小票发放下发方式";

	/* 小票明细单据状态 */
	public static final String NOTE_DETAILS__STATUS = "NOTE_DETAILS__STATUS";
	public static final String NOTE_DETAILS__STATUS__DESC = "小票明细单据状态";

	/* 小票付款方式 */
	public static final String OTHER_REVENUE__PAYMENT_TYPE = "OTHER_REVENUE__PAYMENT_TYPE";
	public static final String OTHER_REVENUE__PAYMENT_TYPE__DESC = "小票付款方式";

	/* 发票来源单据子类型 */
	public static final String INVOICE__SOURCE_BILL_TYPE = "INVOICE__SOURCE_BILL_TYPE";
	public static final String INVOICE__SOURCE_BILL_TYPE__DESC = "来源单据子类型";

	/* 发票类型 */
	public static final String INVOICE__INVOICE_TYPE = "INVOICE__INVOICE_TYPE";
	public static final String INVOICE__INVOICE_TYPE__DESC = "发票类型";
	
	/* 发票面值类型 */
	public static final String INVOICE__INVOICE_VALUE_TYPE = "INVOICE__INVOICE_VALUE_TYPE";
	public static final String INVOICE__INVOICE_VALUE_TYPE__DESC = "发票面值类型";

	/* 异常出库异常类型 */
	public static final String OUT_STOCK_EXCEPTION__EXCEPTION_TYPE = "OUT_STOCK_EXCEPTION__EXCEPTION_TYPE";
	public static final String OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__DESC = "异常出库异常类型";

	/* 现金收入报表明细来源单据子类型 */
	public static final String CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE = "CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE";
	public static final String CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DESC = "现金收入报表明细来源单据子类型";

	/* 司机收款报表明细类型 */
	public static final String DRIVER_COLLECTION_RPT_D__TYPE = "DRIVER_COLLECTION_RPT_D__TYPE";
	public static final String DRIVER_COLLECTION_RPT_D__TYPE__DESC = "司机收款报表明细类型";
	
	/* 操作日志操作单据类型 */
	public static final String OPERATING_LOG__OPERATE_BILL_TYPE = "OPERATING_LOG__OPERATE_BILL_TYPE";
	public static final String OPERATING_LOG__OPERATE_BILL_TYPE__DESC = "操作日志单据类型";
	
	/* 操作日志操作类型 */
	public static final String OPERATING_LOG__OPERATE_TYPE = "OPERATING_LOG__OPERATE_TYPE";
	public static final String OPERATING_LOG__OPERATE_TYPE__DESC = "操作日志操作类型";
	
	/* 理赔单类型 */
	public static final String BILL_CLAIM__TYPE = "BILL_CLAIM__TYPE";
	public static final String BILL_CLAIM__TYPE__DESC = "理赔单类型";
	
	/* 理赔单支付类别类型 */
	public static final String BILL_CLAIM__PAYMENT_CATEGORIES = "BILL_CLAIM__PAYMENT_CATEGORIES";
	public static final String BILL_CLAIM__PAYMENT_CATEGORIES__DESC = "理赔单支付类别";
	
	/*          报表数据字典                       */
	public static final String MVRRFI_VOUCHER__ORG_TYPE = "VOUCHER__ORG_TYPE";
	public static final String MVRRFI_VOUCHER__ORG_TYPE__DESC = "始发到达往来月报表出发/到达";
	
	/* 发票标记  */
	public static final String SETTLEMENT_INVOICE_MARK = "SETTLEMENT_INVOICE_MARK";
	public static final String SETTLEMENT_INVOICE_MARK__DESC = "发票标记";
	
	/* 应收明细费用类型  */
	public static final String RECEIVABLE_DETAIL__FEE_TYPE = "RECEIVABLE_DETAIL__FEE_TYPE";
	public static final String RECEIVABLE_DETAIL__FEE_TYPE__DESC = "应收明细费用类型";
	
	/* 应付明细费用类型  */
	public static final String PAYABLE_DETAIL__FEE_TYPE = "PAYABLE_DETAIL__FEE_TYPE";
	public static final String PAYABLE_DETAIL__FEE_TYPE__DESC = "应付明细费用类型";
	
	/* 扣款状态  */
	public static final String WITHHOLD_STATUS = "WITHHOLD_STATUS";
	public static final String WITHHOLD_STATUS__DESC = "扣款状态";
	
	
	/** END：******************************* 结算相关的词条代码 *********************************/

	/** START：*******************************站点组的词条代码 *********************************/
	/**
	 * 站点组类型
	 * @author 094463-foss-xieyantao
	 */
	public static final String BSE_SITE_GROUP_TYPE = "BSE_SITE_GROUP_TYPE";
	public static final String BSE_SITE_GROUP_TYPE__DESC = "站点组类型";
	/** END：******************************* 站点组的词条代码 *********************************/

	/** BEGIN：******************************************* 费控系统(FIN)相关的词条代码 *********************************************/

	/**
	 * FIN-"账户性质"的"词条代码"
	 * 
	 * @author 094463-foss-xieyantao
	 */
	public static final String FIN_ACCOUNT_TYPE = "FIN_ACCOUNT_TYPE";
	public static final String FIN_ACCOUNT_TYPE__DESC = "FIN-账户性质";

	/** END：******************************************* 费控系统(FIN)相关的词条代码 *********************************************/

	/**
	 * 行政区域级别 对应 DictionaryValueConstants 类的 DISTRICT_NATION="NATION";
	 * DISTRICT_PROVINCE="DISTRICT_PROVINCE"; DISTRICT_CITY="CITY";
	 * DISTRICT_COUNTY="DISTRICT_COUNTY";
	 */
	public static final String DISTRICT_DEGREE = "DISTRICT_DEGREE";
	public static final String DISTRICT_DEGREE__DESC = "行政区域级别";

	/**
	 * 人员（在职、离职状态）的常量
	 */
	public static final String EMPLOYEE_STATUS = "EMPLOYEE_STATUS";
	public static final String EMPLOYEE_STATUS__DESC = "人员在/离职状态";

	/**
	 * 权限类型的常量
	 */
	public static final String RESOURCE_TYPE = "RESOURCE_TYPE";
	public static final String RESOURCE_TYPE__DESC = "权限类型";
    
    /**
     * 权限所属系统类型
     */
	public static final String RESOURCE_BELONG_SYSTEM_TYPE="RESOURCE_BELONG_SYSTEM_TYPE";
	public static final String RESOURCE_BELONG_SYSTEM_TYPE__DESC="权限所属系统类型";
	
	/**
	 * 违禁品类型
	 * FOSS-073586-LIXUEXING
	 */
	public static final String CONTRABAND_TYPE = "CONTRABAND_TYPE";
	public static final String CONTRABAND_TYPE__DESC = "违禁品类型";
	/**
	 * 违禁品级别
	 * FOSS-073586-LIXUEXING
	 */
	public static final String CONTRABAND_LEVEL = "CONTRABAND_LEVEL";
	public static final String CONTRABAND_LEVEL__DESC = "违禁品级别";
	/**
	 * 违禁品类别
	 * FOSS-073586-LIXUEXING
	 */
	public static final String CONTRABAND_GOODS_CATEGORY = "CONTRABAND_GOODS_CATEGORY";
	public static final String CONTRABAND_GOODS_CATEGORY__DESC = "违禁品类别";
	/**
	 * 增值服务
	 * FOSS-073586-LIXUEXING
	 */
	public static final String VALUE_ADDED_SERVICES = "VALUE_ADDED_SERVICES";
	public static final String VALUE_ADDED_SERVICES__DESC = "增值服务";
	/**
	 * 承运业务
	 * FOSS-073586-LIXUEXING
	 */
	public static final String OPERATING_SERVICES = "OPERATING_SERVICES";
	public static final String OPERATING_SERVICES__DESC = "承运业务";
	/**
	 * 信息部性质
	 * FOSS-073586-LIXUEXING
	 */
	public static final String INFO_DEPT_PROPERTIES = "INFO_DEPT_PROPERTIES";
	public static final String INFO_DEPT_PROPERTIES__DESC = "信息部性质";
	/**
	 * 收货类型
	 * FOSS-073586-LIXUEXING
	 */
	public static final String RECEIVING_TYPE = "RECEIVING_TYPE";
	public static final String RECEIVING_TYPE__DESC = "收货类型";
	/**
	 * 子系统
	 * FOSS-073586-LIXUEXING
	 */
	public static final String SUB_SYSTEM = "SUB_SYSTEM";
	public static final String SUB_SYSTEM__DESC = "子系统";
	/**
	 * 车辆职责类别
	 * FOSS-073586-LIXUEXING
	 */
	public static final String VEHICLE_RESPONSIBILITY_CATEGORY = "VEHICLE_RESPONSIBILITY_CATEGORY";
	public static final String VEHICLE_RESPONSIBILITY_CATEGORY__DESC = "车辆职责类别";
	/**
	 * 比较条件
	 * FOSS-073586-LIXUEXING
	 */
	public static final String COMPARE_CONDITION = "COMPARE_CONDITION";
	public static final String COMPARE_CONDITION__DESC = "比较条件";
	/**
	 * 比较符
	 * FOSS-073586-LIXUEXING
	 */
	public static final String COMPARE_SYMBOL = "COMPARE_SYMBOL";
	public static final String COMPARE_SYMBOL__DESC = "比较符";
	/**
	 * 逻辑符
	 * FOSS-073586-LIXUEXING
	 */
	public static final String LOGIC_SYMBOL = "LOGIC_SYMBOL";
	public static final String LOGIC_SYMBOL__DESC = "逻辑符";
	/**
	 * 短信模板
	 * FOSS-073586-LIXUEXING
	 */
	public static final String SMS_TEMPLETE = "SMS_TEMPLETE";
	public static final String SMS_TEMPLETE__DESC = "短信模板";
	/**
	 * 短信广告
	 * FOSS-073586-LIXUEXING
	 */
	public static final String SMS_ADVERTISEMENT = "SMS_ADVERTISEMENT";
	public static final String SMS_ADVERTISEMENT__DESC = "短信广告";
	/**
	 * 单据广告
	 * FOSS-073586-LIXUEXING
	 */
	public static final String BILL_ADVERTISEMENT = "BILL_ADVERTISEMENT";
	public static final String BILL_ADVERTISEMENT__DESC = "单据广告";
	/**
	 * 客户 合同 优惠类型
	 * FOSS-073586-LIXUEXING
	 */
	public static final String CRM_PREFERENTIAL_TYPE = "CRM_PREFERENTIAL_TYPE";
	public static final String CRM_PREFERENTIAL_TYPE__DESC = "CRM-优惠类型";
	
	/**
	 * 运单状态
	 */
	public static final String PENDINGTYPE = "PENDINGTYPE";
	public static final String PENDINGTYPE__DESC = "运单状态";
	
	/**
	 * 离线状态
	 */
	public static final String OFFLINEACTIVE = "OFFLINEACTIVE";
	public static final String OFFLINEACTIVE__DESC = "离线状态";
	
	/**
	 * 卸车类型-中转
	 */
	public static final String UNLOAD_TYPE = "UNLOAD_TYPE";
	public static final String UNLOAD_TYPE__DESC = "卸车类型";
	
	/**
	 * 卸车差异报告处理状态-中转
	 */
	public static final String UNLOAD_DIFF_REPORT_HANDLE_STATUS = "UNLOAD_DIFF_REPORT_HANDLE_STATUS";
	public static final String UNLOAD_DIFF_REPORT_HANDLE_STATUS__DESC = "卸车差异报告处理状态";

	/**
	 * 卸车差异类型-中转
	 */
	public static final String UNLOAD_EXCEPTION_TYPE = "UNLOAD_EXCEPTION_TYPE";
	public static final String UNLOAD_EXCEPTION_TYPE__DESC = "卸车差异类型";

	/**
	 * PDA生成的交接单，流水号的装车异常类型-中转
	 */
	public static final String LOAD_EXCEPTION_TYPE = "LOAD_EXCEPTION_TYPE";
	public static final String LOAD_EXCEPTION_TYPE__DESC = "装车异常类型";
	
	// 人员数据字典
	
	/**
	 * 人员性别
	 */
	public static final String GENDER = "GENDER";
	public static final String GENDER__DESC = "人员性别（男女）";
	
	
	/**
	 * 贵重物品
	 */
	public static final String VALUABLE_GOODS = "VALUABLE_GOODS";
	public static final String VALUABLE_GOODS__DESC = "贵重物品";
	
	/**
	 * 空运单号类型
	 */
	public static final String BILLNO_TYPE = "BILLNO_TYPE";
	public static final String BILLNO_TYPE__DESC = "空运单号类型";
	
	/**
	 * 发车模式
	 */
	public static final String DEPARTURE_MODEL = "DEPARTURE_MODEL";
	public static final String DEPARTURE_MODEL__DESC = "发车模式";
	
	/**
	 * 加油方式
	 */
	public static final String FUEL_TYPE = "FUEL_TYPE";
	public static final String FUEL_TYPE__DESC = "加油方式";
	
	/**
	 * 加油标号
	 */
	public static final String FUEL_LABEL = "FUEL_LABEL";
	public static final String FUEL_LABEL__DESC = "加油标号";
	
	/**
	 * 加油付款类型
	 */
	public static final String PAY_TYPE = "PAY_TYPE";
	public static final String PAY_TYPE__DESC = "加油付款类型";

	/**
	 * 封签类型
	 */
	public static final String SEAL_TYPE = "SEAL_TYPE";
	public static final String SEAL_TYPE__DESC = "封签类型";

	/**
	 * 封签状态
	 */
	public static final String SEAL_STATE = "SEAL_STATE";
	public static final String SEAL_STATE__DESC = "封签状态";
	
	/** 发车类型 */
	public static final String ARRIVE_TYPE = "ARRIVE_TYPE";
	public static final String ARRIVE_TYPE__DESC = "发车类型";

	/** 月台状态 */
	public static final String PLATFORM_STATUS_TFR = "PLATFORM_STATUS_TFR";
	public static final String PLATFORM_STATUS_TFR__DESC = "月台状态";
	
	/** 星标类型  ISSUE-2764（全国到达青岛区域的部分目的营业部需要加星标）*/
	public static final String ASTERISK_TYPE = "ASTERISK_TYPE";
	public static final String ASTERISK_TYPE__DESC = "星标类型";

	/** 时效类型 **/
	public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
	public static final String PRODUCT_TYPE__DESC = "时效类型";

	/** 理货业务类型 **/
	public static final String PKP_ARRANGRDOODS_SPEED = "PKP_ARRANGRDOODS_SPEED";
	public static final String PKP_ARRANGRDOODS_SPEED__DESC = "理货业务类型 ";
	/**返货原因**/
	public static final String  RETURN_REASON = "RETURN_REASON";
	public static final String  RETURN_REASON__DESC ="返货原因";
	/**返货处理状态**/
	public static final String  RETURN_STATUS = "RETURN_STATUS" ;
	public static final String RETURN_STATUS__DESC ="处理状态";
	/**运单开单类型*/
	public static final String PKP_WAYBILLEXPRESS_TYPE ="PKP_WAYBILLEXPRESS_TYPE";
	public static final String PKP_WAYBILLEXPRESS_TYPE__DESC ="运单开单类型";
	
	/**
	 * "(LDP)核销方式"的"词条代码"
	 * 
	 * @author WangPeng
	 */
	public static final String LDP_WRITE_OFF_TYPE = "LDP_WRITE_OFF_TYPE";
	public static final String LDP_WRITE_OFF_TYPE__DESC = "快递代理公司核销方式";
	/**
	 * "快递100屏蔽敏感词汇"的"数据字典"
	 * 
	 * @author zhangpeng
	 */
	public static final String SHIELDING_SENSITIVE_KD100 = "SHIELDING_SENSITIVE_KD100";
	public static final String SHIELDING_SENSITIVE_KD100__DESC = "快递100敏感词汇";
	
	/**
	 * "快递PDA提货方式"的"词条代码"
	 * 
	 * @author WangPeng
	 */
	public static final String KD_PICKUPGOODSHIGHWAYS_TYPE = "KD_PICKUPGOODSHIGHWAYS";
	public static final String KD_PICKUPGOODSHIGHWAYS__DESC = "快递PDA提货方式";
	
	/**订单受理状态*/
	public static final String PKP_PROCESS_STATUS_TYPE ="PKP_PROCESS_STATUS";
	public static final String PKP_PROCESS_STATUS_TYPE__DESC ="订单受理状态";
	/**
	 * “税务凭证报表”发票标记
	 */
//	public static final String SETTLEMENT__INVOICE_MARK = "SETTLEMENT__INVOICE_MARK";
//	public static final String SETTLEMENT__INVOICE_MARK__DESC = "发票标记";
	/**
	 * 派送属性的词条代码（快递派送区域）
	 */
	public static final String DELIVERY_NATURE ="DELIVERY_NATURE";
	public static final String DELIVERY_NATURE__DESC ="派送属性";
	/**
	 * 派送网点类型的词条代码（零担大客户派送地址库）
	 */
	public static final String DELIVERY_TYPE ="DELIVERY_TYPE";
	public static final String DELIVERY_TYPE__DESC ="派送网点类型";
	/**
	 * 非标准派送时效
	 */
	public static final String NO_STANDARD_DELIVERY_TIME ="NO_STANDARD_DELIVERY_TIME";
	public static final String NO_STANDARD_DELIVERY_TIME__DESC ="非标准派送时效";
	/**
	 * 快递收派小区类型
	 */
	public static final String EXPRESS_SMALL_TYPE = "EXPRESS_SMALL_TYPE";
	public static final String EXPRESS_SMALL_TYPE__DESC= "快递小区类型";
	
	/**
	 * 快递员工作类别
	 */
	public static final String EXPRESS_PLAN_TYPE ="EXPRESS_PLAN_TYPE";
	public static final String EXPRESS_PLAN_TYPE__DESC ="快递员工作类别";
	
	/**
	 * 快递员属性
	 */
	public static final String COURIER_NATURE ="COURIER_NATURE";
	public static final String COURIER_NATURE__DESC ="快递员属性";
	
	/**
	 * 特殊接货地址类型
	 */
	public static final String SPECIAL_ADDRESS_TYPE ="SPECIAL_ADDRESS_TYPE";
	public static final String SPECIAL_ADDRESS_TYPE__DESC ="特殊接货地址类型";
	
	/**
	 * 到达联签收状态
	 */
	public static final String PKP_SIGN_STATUS ="PKP_SIGN_STATUS";
	public static final String PKP_SIGN_STATUS__DESC ="到达联签收状态";

	/**
	 * 超时装卸费审核状态
	 */ 
	public static final String SETTLEMENT__OVERDUE_SF_AUDIT_STATUS = "OVERDUE_SF_AUDIT_STATUS";
	public static final String SETTLEMENT__OVERDUE_SF_AUDIT_STATUS__DESC = "超时装卸费审批状态";
	/**
	 * 库区密度查询时间点(0-23)
	 */
	public static final String TFR_TWENTY_FOUR_OCLOCK ="TFR_TWENTY_FOUR_OCLOCK";
	public static final String TFR_TWENTY_FOUR_OCLOCK__DESC ="库区密度查询时间点";
	
	/**
	 * 预提状态
	 */
	public static final String WITHHOLDING_STATUS = "WITHHOLDING_STATUS";
	public static final String WITHHOLDING_STATUS__DESC= "预提状态";

	/**
	 * 费用类型
	 */
	public static final String RENTCAR_COST_TYPE = "RENTCAR_COST_TYPE";
	public static final String RENTCAR_COST_TYPE__DESC= "费用类型";

	/**
	 * 租车用途
	 */
	public static final String RENTCAR_USE_TYPE = "RENTCAR_USE_TYPE";
	public static final String RENTCAR_USE_TYPE__DESC= "租车用途";
	
	/**
	 * 开箱验货区域
	 */
	public static final String BILL_INSPECTION_REGION ="BILL_INSPECTION_REGION";
	public static final String BILL_INSPECTION_REGION__DESC ="开箱验货区域";
	/**
	 * 开箱验货区域级别
	 */
	public static final String BILL_DISTRICT_DEGREE = "BILL_DISTRICT_DEGREE";
	public static final String BILL_DISTRICT_DEGREE__DESC = "开箱验货区域级别";
	/**
	 * 包装类型
	 */
	public static final String VALUEADDED_PACKAGE_TYPE = "VALUEADDED_PACKAGE_TYPE";
	public static final String VALUEADDED_PACKAGE_TYPE__DESC = "增值服务包装类型";
	/**
	 * 返单类型
	 */
	public static final String VALUEADDED_RETURN_TYPE = "VALUEADDED_RETURN_TYPE";
	public static final String VALUEADDED_RETURN_TYPE__DESC = "增值服务返单类型";
	/**
	 * 签收类型
	 */
	public static final String POD_ENTITY_POD_TYPE = "POD_ENTITY__POD_TYPE";
	public static final String POD_ENTITY_POD_TYPE__DESC= "签收类型";
	
	/**
	 * 提前找货状态
	 */
	public static final String PKP_INADVANCE_GOODS_STATE="PKP_INADVANCE_GOODS_STATE";
	public static final String PKP_INADVANCE_GOODS_STATE__DESC="提前找货状态";
	
	/**
	 * 行政区清仓试点外场
	 */
	public static final String TFR_ST_TASK_TEST_TRANS = "TFR_ST_TASK_TEST_TRANS";
	public static final String TFR_ST_TASK_TEST_TRANS__DESC = "行政区清仓试点外场";

	/**
	 * 签收人类型
	 */
	public static final String PKP_SIGN_PERSON_TYPE="PKP_SIGN_PERSON_TYPE";
	public static final String PKP_SIGN_PERSON_TYPE__DESC="签收人类型";
	
	/**
	 * 城市级别
	 */
	public static final String BSE_CITY_LEVEL ="BSE_CITY_LEVEL";
	public static final String BSE_CITY_LEVEL__DESC ="城市级别";


	/**

	 * FOSS 综合开发组
	 * 临时租车标记时间管理页面中--部门属性 
	 * @author 218392 张永雪
	 * 2014-12-24 AM 11:18:23
	 */
	public static final String DEPARTMENT_OF_ATTRIBUTES = "DEPARTMENT_OF_ATTRIBUTES";
	public static final String DEPARTMENT_OF_ATTRIBUTES__DESC = "部门属性";


	/**
	 * 外场业务渠道
	 */
	public static final String TRANSFER_SERVICE_CHANNEL ="TRANSFER_SERVICE_CHANNEL";
	public static final String TRANSFER_SERVICE_CHANNEL__DESC ="外场业务渠道";
	
	/**
	 * 网点模式-308861
	 */
	public static final String NETWORK_MODEL ="NETWORK_MODEL";
	public static final String NETWORK_MODEL__DESC ="网点模式";
	
	/** 
	 *  收货发票类型
	 */ 
	public static final String PKP_RECEIPT_INVOICE_TYPE = "PKP_RECEIPT_INVOICE_TYPE";
	public static final String PKP_RECEIPT_INVOICE_TYPE__DESC = "收货发票类型";
	
	/**
	 * 特殊送货地址类型
	 */
	public static final String PKP_SPECIAL_DELIVERYADDRESS_TYPE = "PKP_SPECIAL_DELIVERYADDRESS_TYPE";
	public static final String PKP_SPECIAL_DELIVERYADDRESS_TYPE__DESC = "特殊送货地址类型";
	
	/**
	 * 出发分拣方案
	 */
	public static final String SORTING_SCHEMENAME_TYPE ="SORTING_SCHEMENAME_TYPE";
	public static final String SORTING_SCHEMENAME_TYPE__DESC ="出发分拣方案";
	
	/**
	 * 出发分拣包类型
	 */
	public static final String SORTING_PACKAGE_TYPE ="SORTING_PACKAGE_TYPE";
	public static final String SORTING_PACKAGE_TYPE__DESC ="出发分拣包类型";
	
	/**
	 * 空运到达类型 
	 * @author zwd 200968 2015-06-05
	 */
	public static final String FLIGHT_ARRIVE_TYPE ="FLIGHT_ARRIVE_TYPE";
	public static final String FLIGHT_ARRIVE_TYPE__DESC ="空运到达类型";
	
	/**
	 * 空运通知情况
	 * @author zwd 200968 2015-09-15 t_opt_air_notify_customers
	 */
	public static final String AIR_NOTIFY_CUSTOMERS_TYPE ="AIR_NOTIFY_CUSTOMERS_TYPE";
	public static final String AIR_NOTIFY_CUSTOMERS_TYPE__DESC ="空运通知情况";
	
	/**
	 * 派送通知异常类型
	 */
	public static final String PKP_NOTICE_EXCEPTION_REASON ="PKP_NOTICE_EXCEPTION_REASON";
	public static final String PKP_NOTICE_EXCEPTION_REASON__DESC="派送通知异常类型";
	
	/**
	 * 可视化排单-运单退回类型
	 */
	public static final String PKP_VISIBLE_WAYBILL_RETURN ="PKP_VISIBLE_WAYBILL_RETURN";
	public static final String PKP_VISIBLE_WAYBILL_RETURN__DESC="排单运单退回类型";
	
	/**
	 * 黑名单类别
	*/ 
	public static final String CUST_BLACKLIST_CATEGORY ="CUST_BLACKLIST_CATEGORY";
	public static final String CUST_BLACKLIST_CATEGORY__DESC="黑名单类别";
	
	/**
	 * 丢货预警业务逻辑处理时间限制
	 */
	public static final String TFR_LOSTGOODS_TIMELIMIT_TYPE ="TFR_LOSTGOODS_TIMELIMIT_TYPE";
	public static final String TFR_LOSTGOODS_TIMELIMIT_TYPE__DESC="丢货预警时间限制";
	
	
	/**
	 * 代理公司简称
	 */
	public static final String ABBREVIATION_OF_AGENCY_COMPANY ="ABBREVIATION_OF_AGENCY_COMPANY";
	public static final String ABBREVIATION_OF_AGENCY_COMPANY__DESC="代理公司简称";
	/**
	 * 不执行快递100自动签收字段
	 */
	public static final String NONEED_AUTOSIGN_FROM_KD100 = "NONEED_AUTOSIGN_FROM_KD100";
	public static final String NONEED_AUTOSIGN_FROM_KD100__DESC = "不执行快递100自动签收字段";
	/**
	 * 子母件签收状态
	 */
	public static final String CZM_SIGN_STATUS = "CZM_SIGN_STATUS";
	public static final String CZM_SIGN_STATUS__DESC = "子母件签收状态";
		
	/**
	* 一次执行创建卸车差异报告时间限制
	*/
	public static final String TFR_PARAM_CREATE_UNLOAD_ONE_TIME = "TFR_PARAM_CREATE_UNLOAD_ONE_TIME";
	public static final String TFR_PARAM_CREATE_UNLOAD_ONE_TIME__DESC = "一次执行创建卸车差异报告JOB时间限制";

	/**
	* 一次执行创建清仓差异报告时间限制
	*/
	public static final String TFR_PARAM_CREATE_STOCKCHECKING_ONE_TIME = "TFR_PARAM_CREATE_STOCKCHECKING_ONE_TIME";
	public static final String TFR_PARAM_CREATE_STOCKCHECKING_ONE_TIME__DESC = "一次执行创建清仓差异报告JOB时间限制";

	/**
	 * 超方上报的比例参数
	 */
	public static final String TFR_PARAM_OVERVOLUMNANDWEIGHT_TOQMS_CONDITION = "TFR_PARAM_OVERVOLUMN_TOQMS_CONDITION";
	public static final String TFR_PARAM_OVERVOLUMNANDWEIGHT_TOQMS_CONDITION__DESC = "上报超方超重的比例参数";
	
	
	
	public static final String TFR_UNTASK_SMSDEPT ="TFR_UNTASK_SMSDEPT";
	public static final String TFR_UNTASK_SMSDEPT__DESC ="卸车发短信部门";
	
	/**
	 * 卸车类型 
	 * @author 272681 2015-10-10
	 */
	public static final String ASSIGNUNLOADTASK_UNLOADTYPE ="ASSIGNUNLOADTASK_UNLOADTYPE";
	public static final String ASSIGNUNLOADTASK_UNLOADTYPE__DESC ="分配卸车任务类型";
	/**
	 * 红背心运单状态
	 */
	public static final String SALESDEPT_DELIVERY_PROC = "SALESDEPT_DELIVERY_PROC";
	public static final String SALESDEPT_DELIVERY_PROC__DESC = "家装运单状态";
	
	
	/**
	 * 快递装卸费业务类型
	 */
	public static final String EXP_HAND_CHARGE_BUSI_TYPE = "EXP_HAND_CHARGE_BUSI_TYPE";
	public static final String EXP_HAND_CHARGE_BUSI_TYPE__DESC = "快递装卸费业务方式";
	/**
	 * 生成分批配载上报数据参数约束
	 */
	public static final String CREATE_BATCHLOADING_REPORT_PARAMS = "CREATE_BATCHLOADING_REPORT_PARAMS";
	public static final String CREATE_BATCHLOADING_REPORT_PARAMS__DESC = "生成分批配载上报数据参数约束";
	
	/**
	 * 自动上报分批配载参数约束
	 */
	public static final String AUTO_BATCHLOADING_REPORT_PARAMS = "AUTO_BATCHLOADING_REPORT_PARAMS";
	public static final String AUTO_BATCHLOADING_REPORT_PARAMS__DESC = "自动上报分批配载参数约束";	

	/**
	 * @项目：智能开单项目
	 * @功能：增加智能开单词名称
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-27
	 */
	public static final String INTELLIGENCE_BILLING_PARAMS = "INTELLIGENCE_BILLING";
	public static final String INTELLIGENCE_BILLING_PARAMS__DESC = "智能开单";
	/**
	 * @功能：走货路径，运输性质（三级产品）
	 * @author:308865
	 * @date:2016-08-03
	 */
	public static final String BSE_FREIGHT_TRANS_TYPE = "BSE_FREIGHT_TRANS_TYPE";
	public static final String BSE_FREIGHT_TRANS_TYPE__DESC = "运输性质";

	
	/**
	 * 调度订单服务标志
	 */
	public static final String ORDER_SERVICE_FLAG="ORDER_SERVICE_FLAG";
	public static final String ORDER_SERVICE_FLAG__DESC="调度订单服务标志";
	/**
	 * 调度订单计划类型
	 */
	public static final String ORDER_SCHEDULE_TYPE="ORDER_SCHEDULE_TYPE";
	public static final String ORDER_SCHEDULE_TYPE__DESC="调度订单计划类型";

	/**
	 * @功能：中转模块判断是否需要晚到补价
	 * @author:332153-foss-zm
	 * @date:2016年12月27日10:33:47
	 */
	public static final String IS_ARRIVE_LATE_PARAMS = "IS_ARRIVE_LATE";
	public static final String IS_ARRIVE_LATE__DESC = "判断是否需要晚到补差价时间点";
	
	/**
	 * @项目：OCR系统
	 * @功能：增加OCR系统开关
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-27
	 */
	public static final String OCR_PARAMS = "OCR";
	public static final String OCR_PARAMS__DESC = "OCR系统图像智能识别开关";
	
	/**
	 * @功能：结清货款页面添加付款方式选项
	 * @author 243921-foss-zhangtingting
	 * @date 2017-04-18
	 */
	public static final String REPAYMENT_PAYMENT_TYPE = "REPAYMENT_PAYMENT_TYPE";
	public static final String REPAYMENT_PAYMENT_TYPE__DESC = "结清货款付款方式";
}
