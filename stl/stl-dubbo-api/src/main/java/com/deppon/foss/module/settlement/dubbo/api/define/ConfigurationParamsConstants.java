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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/shared/define/ConfigurationParamsConstants.java
 * 
 * FILE NAME        	: ConfigurationParamsConstants.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.settlement.dubbo.api.define;

public class ConfigurationParamsConstants {

	/**
	 * 是否递归查找父组织机构
	 */
	public static final String NEED_SEARCH_PARENT_ORG = "Y";

	
	/**
	 * 递归查找父组织机构最大层级
	 */
	public static final int SEARCH_MAX_ORG_LEVEL = 10;

	// 综合模块使用

	/**
	 * 临欠最大欠款天数 对应jira编号：ISSUE-249
	 */
	public static final String BAS_PARM__MAX_TEMP_ARREARAGE_DAYS = "MAX_TEMP_ARREARAGE_DAYS";

	/**
	 * 月结最大欠款天数 对应jira编号：ISSUE-249
	 */
	public static final String BAS_PARM__MAX_MONTHLYPAY_ARREARAGE_DAY = "MAX_MONTHLYPAY_ARREARAGE_DAY";
	
	/**
	 * 定人定区系统管理员部门 对应jira编号：BUG-28179
	 */
	public static final String BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR = "BAS_REGIONAL_VEHICLE_SYSTEM_ADMINSTRATOR";
	
	
	/**
	 * 快递自提，直接减免费用
	 */
	public static final String BAS_PARM__EXPRESS_SELF_PICKUP_SUBSTRACT = "BAS_PARM__EXPRESS_SELF_PICKUP_SUBSTRACT";
	
	
	/**
	 * 用组织业务规则限制装卸费的上限(实收运费的比例，不是固定金额) 对应jira编号：ISSUE-731
	 * public static final String BAS_PARM__MAX_SERVICE_FEE_RATE = "MAX_SERVICE_FEE_RATE";
	 */
	
	
	/**
	 * 整车限制最大重量（单位：KG）
	 */
	public static final String BAS_PARM__MAX_WEIGHT_VEHICLE="MAX_WEIGHT_VEHICLE";
	
	/**
	 * 整车需要提示的重量（单位：KG）
	 */
	public static final String BAS_PARM__MIN_WEIGHT_VEHICLE="MIN_WEIGHT_VEHICLE";
	/**
	 * 整车限制最大体积（单位：m³）
	 */
	public static final String BAS_PARM__MAX_VOLUME_VEHICLE="MAX_VOLUME_VEHICLE";
	
	/**
	 * 整车需要提示的体积（单位：m³）
	 */
	public static final String BAS_PARM__MIN_VOLUME_VEHICLE="MIN_VOLUME_VEHICLE";
	
	// 接送货模块使用
	/**
	 * 对应jira编号：ISSUE-661 派送排单中的排单规则： 运单的到付金额必须小于XX阈值（如10000元，可由综合管理配置）
	 */
	public static final String PKP_PARM__WAYBILL_DISPATCH_SCHEDULE_MAX_COD = "WAYBILL_DISPATCH_SCHEDULE_MAX_COD";
	
	/**
	 * 确认派送单时，车辆重量/体积装载率阈值(下限)
	 */
	public static final String PKP_PARM__DELIVER_WEIGHT_LOWER_THRESHOLD = "DELIVER_WEIGHT_LOWER_THRESHOLD";
	public static final String PKP_PARM__DELIVER_VOLUME_LOWER_THRESHOLD = "DELIVER_VOLUME_LOWER_THRESHOLD";

	/**
	 * 对应jira编号：ISSUE-564 保价申明价值默认为3000
	 */
	public static final String PKP_PARM__DEFAULT_INSURANCE_AMOUNT = "DEFAULT_INSURANCE_AMOUNT";
	
	/**
	 * 异常转弃货JOB扫描天数(默认90天)
	 */
	public static final String PKP_PARM__ABANDOONGOODS_APP_DAYS = "ABANDOONGOODS_APP_DAYS";
	
	/**
	 * 运单库存件数
	 */
	
	public static final String PKP_WAYBILL_STOCK_NUMBER = "WAYBILL_STOCK_NUMBER";
	
	/**
	 * 运单库存是否异步
	 */
	
	public static final String PKP_WAYBILL_STOCK_SYN = "WAYBILL_STOCK_SYN";
	
	/**
	 * 香港市 编码
	 */
	public static final String PKP_CITY_HK_CODE = "WAYBILL_CITY_HK_CODE";
	
	/**
	 * 香港 省 编码
	 */
	public static final String PKP_PROV_HK_CODE = "WAYBILL_PROV_HK_CODE";
	/**
	 * 送货（含不上楼），最大送货费设置
	 */
	public static final String PKP_DELIVER_NOUP_MAX_FEE="DELIVER_NOUP_MAX_FEE";
	
	/**
	 * 运单变更处理待办处理超时分钟数
	 */
	public static final String PKP_PARM__WAYBILLRFC_MINUTES = "WAYBILLRFC_MINUTES";
	
	/**
	 * 更改单必须需要的受理部门
	 */
	public static final String PKP_PARM__WAYBILLRFC_DEPT="WAYBILLRFC_DEPT";
	
	/**
	 * 系统上线日期参数
	 */
	public static final String PKP_FOSS_GOLIVE_DATE = "PKP_FOSS_GOLIVE_DATE";
	
	/**
	 * 锁定初始化时间倍数，以分钟算
	 */
	public static final String PKP_FOSS_LOCK_INIT="PKP_FOSS_LOCK_INIT";
	
	/**
	 * 锁定初始化
	 */
	public static final String PKP_FOSS_LOCK_REFRESH="PKP_FOSS_LOCK_REFRESH";
	
	
	/**
	 * 是否需要锁定
	 */
	
	public static final String PKP_FOSS_IS_LOCK="PKP_FOSS_IS_LOCK";
	
	/**
	 * 锁定是否可以解锁
	 */
	public static final String PKP_FOSS_CAN_UNLOCK="PKP_FOSS_CAN_UNLOCK";
	
	/**
	 * 是否开启离线开单
	 */
	public static final String PKP_FOSS_IS_OFFLINE_OPEN = "PKP_FOSS_IS_OFFLINE_OPEN";
	
	/**
	 * 代收手续费 折扣
	 */
	public static final String PKP_FOSS_COD_FEE_DISCOUNT = "PKP_FOSS_COD_FEE_DISCOUNT";
	
	/**
	 * 保价费 折扣
	 */
	public static final String PKP_FOSS_INSURANCE_FEE_DISCOUNT = "PKP_FOSS_INSURANCE_FEE_DISCOUNT";
	
	/**
	* 快递伙伴开单折扣
	*/
	public static final String CREATINGEXP_PARTNER_ORDER_DISCOUNT="CREATINGEXP_PARTNER_ORDER_DISCOUNT";
	
	
	/**
	* 快递伙伴开单最大折扣折扣
	*/
	public static final String MAX_TRANSPORTFEE_EXP="MAX_TRANSPORTFEE_EXP";
	/**
	/**
	 * 
	* 零担伙伴开单折扣
	*/
	public static final String CREATING_PARTNER_ORDER_DISCOUNT="CREATING_PARTNER_ORDER_DISCOUNT";

	/**
	* 高兑现率值
	*/
	public static final String EARLTWARING_INFO_HIGH_VALUE="EARLTWARING_INFO_HIGH_VALUE";
	/**
	* 低兑现率值
	*/
	public static final String EARLTWARING_INFO_LOW_VALUE="EARLTWARING_INFO_LOW_VALUE";
	/**
	* 高兑现线路提示语
	*/
	public static final String EARLTWARING_INFO_HIGH="EARLTWARING_INFO_HIGH";
	/**
	* 低兑现线路提示语
	*/
	public static final String EARLTWARING_INFO_LOW="EARLTWARING_INFO_LOW";

	/**
	* 预警线路提示语
	*/
	public static final String EARLYWARNING_INFO="EARLYWARNING_INFO";
	
	/**
	 * 合伙人2016.04.10号切分初始化数据配置参数
	 */
	public static final String PKP_PTP_SIGN_INIT_410 = "PKP_PTP_SIGN_INIT_410";

	// 允许离线登陆的最大日期数CODE
	public static final String MAX_OFFLINE_DAYS_CONFIG_CODE = "MAXOFFDAYS";
	// 允许离线登陆的最大日期数
	public static final int MAX_OFFLINE_DAYS = 10;
	//快递公斤段分割点
	public static final String PKP_EXPRESS_WEIGHT_CUT="EXPRESS_WEIGHT_CUT";
	//快递公斤段分割点时间节点(时间节点前，不采用公斤段分割点的校验；时间节点后采用此校验)
	public static final String PKP_EXPRESS_WEIGHT_CUT_TIME = "EXPRESS_WEIGHT_CUT_TIME";
	
	//包装类型为打木托 2016年1月14日 15:05:08 葛亮亮
	public static final String PKP_FOSS_PACKAGE_SALVER_DISCOUNT = "PKP_FOSS_PACKAGE_SALVER_DISCOUNT";
	//包装类型为打木架 2016年1月14日 15:05:08 葛亮亮
	public static final String PKP_FOSS_PACKAGE_STAND_DISCOUNT = "PKP_FOSS_PACKAGE_STAND_DISCOUNT";
	//包装类型为打木箱 2016年1月14日 15:05:08 葛亮亮
	public static final String PKP_FOSS_PACKAGE_BOX_DISCOUNT = "PKP_FOSS_PACKAGE_BOX_DISCOUNT";
	//最低一票比例 2016年1月14日 15:05:08 葛亮亮
	public static final String PKP_FOSS_MIX_INSURANCE_DISCOUNT = "PKP_FOSS_MIX_INSURANCE_DISCOUNT";
	

	// 中转模块使用

	/**
	 * 租车标记有效期天数
	 **/
	public static final String TFR_PARM_TEMPRENTALMARK_VALID_DAYS = "TFR_PARM_TEMPRENTALMARK_VALID_DAYS";
	
	/**
	/**
	 * 正式交接单最大可修改的运单数量 ：整数
	 */
	public static final String TFR_PARM_HANDOVERBILL_CANBEMODIFIED_WAYBILL_COUNT = "HANDOVERBILL_CANBEMODIFIED_WAYBILL_COUNT";
	/**
	 * 交接单漂移待办的起始日期
	 */
	public static final String TFR_PARM_HANDOVERBILL_DRIFTED_TODO_DATE = "HANDOVERBILL_DRIFTED_TODO_DATE";
	/**
	 * pda装车是否忽略走货路径
	 */
	public static final String TFR_PARM_HANDOVER_IGNORE_TRANSPORT_PATH_PDA = "HANDOVER_IGNORE_TRANSPORT_PATH_PDA";
	/**
	 * pc交接是否忽略走货路径
	 */
	public static final String TFR_PARM_HANDOVER_IGNORE_TRANSPORT_PATH_PC = "HANDOVER_IGNORE_TRANSPORT_PATH_PC";
	/**
	 * 上报卸车少货找到差错之少货差错最晚上报时间
	 */
	public static final String TFR_PARM_REPORT_UNLOAD_LACK_GOODS_FOUND_LATEST_REPORT_TIME = "REPORT_UNLOAD_LGF__LATEST_REPORT_TIME";
	/**
	 * 上报清仓少货找到差错之少货差错最晚上报时间
	 */
	public static final String TFR_PARM_REPORT_ST_LACK_GOODS_FOUND_LATEST_REPORT_TIME = "REPORT_ST_LGF_LATEST_REPORT_TIME";
	/**
	 * pda卸车两地装车校验
	 */
	public static final String TFR_PARM_UNLOAD_IGNORE_SEPARATE_UNLOAD = "TFR_PARM_UNLOAD_IGNORE_SEPARATE_UNLOAD";
	/**
	 * 货量预测起始时间 ：字符串
	 */
	public static final String TFR_PARM__FORECAST_START = "FORECAST_START";
	/**
	 * 货量预测外场集中接货重量配置
	 */
	public static final String TFR_PARM__FORECAST_WEIGHT = "FORECAST_WEIGHT";
	/**
	 * 货量预测外场集中接货体积配置
	 */
	public static final String TFR_PARM__FORECAST_VOLUME = "FORECAST_VOLUME";
	/**
	 * 货量预测持续时间： 整数
	 */
	public static final String TFR_PARM__FORECAST_DURATION = "FORECAST_DURATION";
	/**
	 * 货量预测执行间隔时间： 整数
	 */
	public static final String TFR_PARM__FORECAST_GAP = "FORECAST_GAP";
	/**
	 * 货量预测预警重量： 小数
	 */
	public static final String TFR_PARM__WARN_WEIGHT = "WARN_WEIGHT";
	/**
	 * 货量预测预警体积： 小数
	 */
	public static final String TFR_PARM__WARN_VOLUME = "WARN_VOLUME";
	
	/**
	 * 查询货量统计配载方案最大可配限制
	 */
	public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH_TEMP = "STOWAGE_PLANS_DEFAULT_LENGTH_TEMP";
	
	/**
	 * 配载方案配置条数限制
	 */
	public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH = "STOWAGE_PLANS_DEFAULT_LENGTH";

	/**
	 * 计算平均重量体积起始时间 ：字符串
	 */
	public static final String TFR_PARM__AVERAGE_CALCULATE_START = "AVERAGE_CALCULATE_START";
	/**
	 * 计算平均重量体积持续时间： 整数
	 */
	public static final String TFR_PARM__AVERAGE_CALCULATE_DURATION = "AVERAGE_CALCULATE_DURATION";
	/**
	 * 外场人员装车效率： 小数
	 */
	public static final String TFR_PARM__LOAD_EFFICIENCY = "LOAD_EFFICIENCY";
	/**
	 * 外场人员卸车效率： 小数
	 */
	public static final String TFR_PARM__UNLOAD_EFFICIENCY = "UNLOAD_EFFICIENCY";
	/**
	 * 外场阀值时间 超过该时间则派送时间推迟一天： 字符串
	 */
	public static final String TFR_PARM__PICKUP_THRESHOLD = "PICKUP_THRESHOLD";
	/** 
	 * 清仓差异上报时间上限：整数 (小时) 
	 */
	public static final String TFR_PARM__ST_REPORT_OA_ERROR = "ST_REPORT_OA_ERROR";
	/** 
	 * 卸车少货差异上报时间上限：小数 (小时) 
	 */
	public static final String TFR_PARM__UNLOAD_EXC_TIME_LIMIT_REPORT_OA_ERROR = "UNLOAD_EXC_TIME_LIMIT_REPORT_OA_ERROR";
	/**
	 * 卸车差异报告生成时间限制：整数（天）
	 */
	public static final String TFR_PARM_UNLOAD_CREATE_REPORT_DAY = "TFR_PARM_UNLOAD_CREATE_REPORT_DAY";
	/**
	 * 时间开关，控制长短途卸车多货少货是否上报oa：字符串(表示时间)
	 */
	public static final String TFR_PARM_UNLOAD_EXC_LATEST_TIME = "TFR_PARM_UNLOAD_EXC_LATEST_TIME";
	/**
	 * 时间开关，控制接货卸车少货是否上报oa：字符串(表示时间)
	 */
	public static final String TFR_PARM_DELIVER_UNLOAD_EXC_LATEST_TIME = "TFR_PARM_DELIVER_UNLOAD_EXC_LATEST_TIME";
	/**
	 * 时间开关，控制装车多货是否上报oa：字符串(表示时间)
	 */
	public static final String TFR_PARM_LOAD_EXC_LATEST_TIME = "TFR_PARM_LOAD_EXC_LATEST_TIME";
	/** 
	 * 放行超时时间：整数（分钟）
	 */
	public static final String TFR_PARM__CANCEL_DEPARTURE = "CANCEL_DEPARTURE";
	/**
	 * 返回最优月台个数
	 */
	public static final String TFR_PARM__OPTIMAL_PLATFORM_RESULT_COUNT = "OPTIMAL_PLATFORM_RESULT_COUNT";
	
	/**
	 * 计算最优月台 货物总体积取值百分比
	 */
	public static final String TFR_PARM__OPTIMAL_PLATFORM_GOODS_PERCENT = "OPTIMAL_PLATFORM_GOODS_PERCENT";
	
	/**
	 * 航空早班截至时间(默认值12:00)
	 */
	public static final String TFR_PARM__MORNING_FLIGHT_TIME = "ORNING_FLIGHT_TIME";
	/**
	 * 航空中班截至时间(默认值16:00)
	 */
	public static final String TFR_PARM__MIDDEL_FLIGHT_TIME = "MIDDEL_FLIGHT_TIME";
	/**
	 * 处理少货特殊组织
	 */
	public static final String TFR_PARM__LOSE_GOODS_SPECIAL_ORG = "LOSE_GOODS_SPECIAL_ORG";
	/**
	 * 清仓差异明细上报OA 时间开关
	 */
	public static final String TFR_PARM__ST_REPORT_OA_BEGINE_TIME = "ST_REPORT_OA_BEGINE_TIME";
	
	/**
	 * 封签异常上报OA 时间开关
	 */
	public static final String TFR_PARM__TFR_SEAL_EXC_LATEST_TIME = "TFR_SEAL_EXC_LATEST_TIME";

	/**
	 * 调整外请车费用-是否奖励
	 */
	public static final String TFR_OUTSIDEVEHICLECHARGE_BE_REWARD = "TFR_OUTSIDEVEHICLECHARGE_BE_REWARD";
	
	/**
	 * 清仓差异明细自动处理 时间开关
	 */
	public static final String TFR_PARM__ST_REPORT_GAP_REPAIR_BEGINE_TIME = "ST_REPORT_GAP_REPAIR_BEGINE_TIME";
	
	/**
	 * 清仓差异明细上报OA 每批处理数量上限
	 */
	public static final String TFR_PARM__ST_REPORT_OA_BUNDLE_NUM = "ST_REPORT_OA_BUNDLE_NUM";
	
	/**
	 * 清仓任务新建调整时间差
	 */
	public static final String TFR_PARM__ST_TASK_BEFORE_CREATE_TIME = "ST_TASK_BEFORE_CREATE_TIME";
	/**
	 *是否奖励时间区间
	 * */
	public static final String TFR_PARM_REWARDORPUNISH_TIME_INTERVAL="TFR_PARM_REWARDORPUNISH_TIME_INTERVAL";

	/**
	 * 配载单办单时间
	 * */
	public static final String TFR_PARM_ADDVECHICLEAESSBILL_TIME="TFR_PARM_ADDVECHICLEAESSBILL_TIME";
	/**
	 * 快递叉车票统计参数
	 * */
	public static final String TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS="TFR_PARM_EXPRESS_FORKLIFTTICKET_PARAMETERS";
	

	/**
	 * PDA查询清仓差异报告时长
	 */
	public static final String TFR_PARM_PDA_ST_DIFFER_TIME = "TFR_PARM_PDA_ST_DIFFER_TIME";
	
	/**
	 * PDA查询卸车差异报告时长
	 */
	public static final String TFR_PARM_PDA_UNLOAD_DIFFER_TIME = "TFR_PARM_PDA_UNLOAD_DIFFER_TIME";
	
	/**
	 * 自动取消清仓任务时差
	 */
	public static final String TFR_PARM_CANCEL_ST_TASK_TIME = "TFR_PARM_CANCEL_ST_TASK_TIME";
	/**
	 * 快递体积转换常量
	 * */
	public static final String 	TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS="TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS";
	/**
	 * 快递代理外发上报OA差错时间开关
	 */
	public static final String TFR_PARM_LDP_REPORT_OA_ERROR_TIME_SWITCH = "TFR_PARM_LDP_REPORT_OA_ERROR_TIME_SWITCH";
/**
	 * PDA卸车入库job线程数
	 * */
	public static final String TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT="TFR_PARM_PDA_UNLOAD_INSTOCK_JOB_COUNT";

	/**
	 * 时间开关，推送代理单号给快递100的绑定开始时间：字符串(表示时间)
	 */
	public static final String TFR_PARM_PUSH_AGENTWAYBILLNO_BEGIN_TIME = "TFR_PARM_PUSH_AGENTWAYBILLNO_BEGIN_TIME";
/**
	 * PDA卸车入库线程查询的数据限制条数
	 * */
	public static final String TFR_PARM_PDA_UNLOAD_INSTOCK_THREAD_COUNT="TFR_PARM_PDA_UNLOAD_INSTOCK_THREAD_COUNT";
	
	/**
	 * PDA卸车入库线程休眠时间
	 */
	public static final String TFR_PARM_PDA_UNLOAD_INSTOCK_SLEEP_TIME = "TFR_PARM_PDA_UNLOAD_INSTOCK_SLEEP_TIME";
	/**
	 * 智能分拣柜来取货物重量上限
	 */
	public static final String TFR_PARM_BCM_QUERY_WAYBILL_WEIGHT_LIMIT = "TFR_PARM_BCM_QUERY_WAYBILL_WEIGHT_LIMIT";
	
	/**
	 * 自动上报无标签多货至QMS的起始时间
	 */
	public static final String TFR_PARM_REPORT_NOLABEL_CREATE_TIME = "TFR_PARM_REPORT_NOLABEL_CREATE_TIME";
	
	/**
	 * 自动上报分批配载差错至QMS的起始时间
	 */
	public static final String TFR_PARM_REPORT_BATCHLOADING_CREATE_TIME = "TFR_PARM_REPORT_BATCHLOADING_CREATE_TIME";
	
/**
	 * 自动补码job开关
	* @fields TFR_PARM_AUTO_ADDCODE_POWER
	* @author 14022-foss-songjie
	* @update 2015年7月24日 下午4:56:52
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_ADDCODE_POWER="TFR_PARM_AUTO_ADDCODE_POWER";
	
	/**
	* @fields 自动补码job的线程数量
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:17:36
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_ADDCODE_THREAD_NUM="TFR_PARM_AUTO_ADDCODE_THREAD_NUM";
	
	
	
	/**
	 * 自动补码job的线程执行间隔时间
	* @fields TFR_PARM_AUTO_ADDCODE_THREAD_EXE_TIME
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:17:58
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_ADDCODE_THREAD_EXE_TIME="TFR_PARM_AUTO_ADDCODE_THREAD_EXE_TIME";
	
	
	/**
	 * 自动补码job的一次处理总量
	* @fields TFR_PARM_AUTO_ADDCODE_EXE_NUM
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:18:21
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_ADDCODE_EXE_NUM="TFR_PARM_AUTO_ADDCODE_EXE_NUM";
	
	
	/**
	 * 自动补码gis的一次处理总量
	* @fields TFR_PARM_AUTO_GIS_EXE_NUM
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:18:21
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_GIS_EXE_NUM="TFR_PARM_AUTO_GIS_EXE_NUM";
	/**
	 * 自动补码gis的线程数量
	* @fields TFR_PARM_AUTO_GIS_THREAD_NUM
	* @author 14022-foss-songjie
	* @update 2015年7月25日 下午4:18:21
	* @version V1.0
	*/
	public static final String TFR_PARM_AUTO_GIS_THREAD_NUM="TFR_PARM_AUTO_GIS_THREAD_NUM";
	
	// ------------------------- Start 结算模块配置的参数 -------------------------
	
	/**
	 * 装卸费百分比：汽运
	 */
	public static final String STL_SERVICE_FEE_RATIO = "STL_SERVICE_FEE_RATIO";
	
	/**
	 * 装卸费百分比：空运
	 */
	public static final String STL_SERVICE_AIR_FEE_RATIO = "STL_SERVICE_AIR_FEE_RATIO";
	
	/**
	 * 装卸费百分比：快递
	 */
	public static final String STL_SERVICE_EXPRESS_FEE_RATIO = "STL_SERVICE_EXPRESS_FEE_RATIO";
	/**
	 * 代收货款上限：整数
	 */
	public static final String STL_COD_MAX_AMOUNT = "STL_COD_MAX_AMOUNT";

	/**
	 * 代收货款下限：整数
	 */
	public static final String STL_COD_MIN_AMOUNT = "STL_COD_MIN_AMOUNT";
	
	/**
	 * 快递代收货款上限：整数
	 */
	public static final String STL_PACKAGE_COD_MAX_AMOUNT = "STL_PACKAGE_COD_MAX_AMOUNT";

	/**
	 * 快递代收货款下限：整数
	 */
	public static final String STL_PACKAGE_COD_MIN_AMOUNT = "STL_PACKAGE_COD_MIN_AMOUNT";
	
	/**
	 * PDA代收货款确认金额：整数
	 */
	public static final String STL_COD_PDA_CONFIRM_AMOUNT = "STL_COD_PDA_CONFIRM_AMOUNT";

	/**
	 * 签收延迟时间：整数
	 */
	public static final String STL_POD_DELAY_MINUTE = "STL_POD_DELAY_MINUTE";

	/**
	 * 偏线运费比率：小数
	 */
	public static final String STL_PL_FEE_RATIO = "STL_PL_FEE_RATIO";
	
	/**
	 * 快递代理运费比率：小数
	 */
	public static final String STL_LDP_FEE_RATIO = "STL_LDP_FEE_RATIO";

	/**
	 * 月结欠款期限：整数
	 */
	public static final String STL_CREDIT_LIMIT_DAY = "STL_CREDIT_LIMIT_DAY";

	/**
	 * 临时欠款期限：整数
	 */
	public static final String STL_DEBT_LIMIT_DAY = "STL_DEBT_LIMIT_DAY";
	
	/**
	 * 临时欠款期限预警值：整数
	 */
	public static final String STL_DEBT_LIMIT_ALARM_DAY = "STL_DEBT_LIMIT_ALARM_DAY";
	
	/**
	 * 信用额度预警值：小数
	 */
	public static final String STL_CREDIT_LIMT_ALRAM_AMOUNT = "STL_CREDIT_LIMT_ALRAM_AMOUNT";

	/**
	 * 对账单锁定时间：整数
	 */
	public static final String STL_STATEMENT_LOCK_MINUTE = "STL_STATEMENT_LOCK_MINUTE";

	/**
	 * 应收单锁定时间：整数
	 */
	public static final String STL_RECEIVABLE_LOCK_MINUTE = "STL_RECEIVABLE_LOCK_MINUTE";

	/**
	 * 外发单红冲期限：整数
	 */
	public static final String STL_PL_WRITEBACK_DAY = "STL_PL_WRITEBACK_DAY";
	
	/**
	 * 快递代理外发单红冲期限：整数
	 */
	public static final String STL_LDP_WRITEBACK_DAY = "STL_LDP_WRITEBACK_DAY";

	/**
	 * 现金收款单红冲期限：整数
	 */
	public static final String STL_CASH_COLLECTION_WRITEBACK_DAY = "STL_CASH_COLLECTION_WRITEBACK_DAY";

	/**
	 * 还款单作废期限：整数
	 */
	public static final String STL_REPAYMENT_CANCELABLE_DAY = "STL_REPAYMENT_CANCELABLE_DAY";

	/**
	 * 外发单反审核期限：整数
	 */
	public static final String STL_PL_REVERSE_AUDIT_DAY = "STL_PL_REVERSE_AUDIT_DAY";
	
	/**
	 * 外发单反审核期限：整数
	 */
	public static final String STL_LDP_REVERSE_AUDIT_DAY = "STL_LDP_REVERSE_AUDIT_DAY";

	/**
	 * 反核销期限：整数
	 */
	public static final String STL_WRITEOFF_REVERSE_WRITEOFF_DAY = "STL_WRITEOFF_REVERSE_WRITEOFF_DAY";

	/**
	 * 小票作废期限：整数
	 */
	public static final String STL_INVOICE_CANCELABLE_DAY = "STL_INVOICE_CANCELABLE_DAY";

	/**
	 * 申请备用金最大额度：整数
	 */
	public static final String STL_PETTY_CASH_MAX_AMOUNT = "STL_PETTY_CASH_MAX_AMOUNT";

	/**
	 * 申请备用金最小额度：整数
	 */
	public static final String STL_PETTY_CASH_MIN_AMOUNT = "STL_PETTY_CASH_MIN_AMOUNT";

	/**
	 * 空运其他应收应付最大金额：整数
	 */
	public static final String STL_AIR_ADD_MAX_AMOUNT = "STL_AIR_ADD_MAX_AMOUNT";
	
	/**
	 * 快递代理其他应收应付最大金额：整数
	 */
	public static final String STL_LDP_ADD_MAX_AMOUNT = "STL_LDP_ADD_MAX_AMOUNT";
	
	/**
	 * 偏线其他应收应付最大金额：整数
	 */
	public static final String STL_PA_ADD_MAX_AMOUNT = "STL_PA_ADD_MAX_AMOUNT";
	
	/**
	 * 装卸费超时天数限制: 整数
	 */
	public static final String STL_OVERDUE_SF_PAYABLE_TIMEOUT_LIMIT = "STL_OVERDUE_SF_PAYABLE_TIMEOUT_LIMIT";
	
	/**
	 * 包装供应商破损奖罚最大金额: 整数
	 */
	public static final String STL_PACKING_DAMAGE_MAX_AMOUNT = "STL_PACKING_DAMAGE_MAX_AMOUNT";
	
	/**
	 * 更改单核销报表的变更金额
	 */
	public static final String STL_WRITEOFF_CHANGE_AMOUNT = "STL_WRITEOFF_CHANGE_AMOUNT";
	
	/**
	 * 应付单付款金额
	 */
	public static final String STL_PAYABLE_PAY_AMOUNT = "STL_PAYABLE_PAY_AMOUNT";
	 
	// ------------------------- End 结算模块配置的参数 -------------------------

	/**
	 * 087584-foss-lijun start:
	 */
	/**
	 * 权限-图标的CSS样式 iconCls foss_icons_emp
	 */
	public static final String RESOURCE_ICONCLS = "RESOURCE_ICONCLS";

	/**
	 * 权限-节点的CSS样式-0级
	 */
	public static final String RESOURCE_NODE_CLS0 = "RESOURCE_NODE_CLS0";
	/**
	 * 权限-节点的CSS样式-1级
	 */
	public static final String RESOURCE_NODE_CLS1 = "RESOURCE_NODE_CLS1";
	/**
	 * 权限-节点的CSS样式-2级
	 */
	public static final String RESOURCE_NODE_CLS2 = "RESOURCE_NODE_CLS2";
	/**
	 * 权限-节点的CSS样式-3级
	 */
	public static final String RESOURCE_NODE_CLS3 = "RESOURCE_NODE_CLS3";
	/**
	 * 权限-节点的CSS样式-4级
	 */
	public static final String RESOURCE_NODE_CLS4 = "RESOURCE_NODE_CLS4";
	/**
	 * 权限-节点的CSS样式-5级
	 */
	public static final String RESOURCE_NODE_CLS5 = "RESOURCE_NODE_CLS5";

	/**
	 * 087584-foss-lijun end.
	 */
	
	public static final String VEHICLE_DIRECT_WEIGHT = "1001";//大车直送重量
	
	public static final String VEHICLE_DIRECT_VOLUME = "1002";//大车直送体积
	
	public static final String VALUEGOODS_SINGLE_VOLUME = "1003";//单件货物贵重物品按体积判断
	
	public static final String VALUEGOODS_SINGLE_PRICE = "1004";//单件货物贵重物品声明价值判断
	
	public static final String VALUEGOODS_MUTI_VOLUME = "1005";//运单货物贵重物品按总体积判断
	
	public static final String VALUEGOODS_MUTI_PRICE = "1006";//运单货物贵重物品按总声明价值判断
	
	public static final String WEIGHT_BY_VOLUME_BEGIN = "1007";//货物重量体积比区间起始值
	
	public static final String WEIGHT_BY_VOLUME_END = "1008";//货物重量体积比区间终止值
	
	public static final String GOODS_PACKING_VOLUME = "1009";//打木架体积计算
	
	public static final String VALUEGOODS_WAYBILL_PRICE = "1010";//运单一票声明价值贵重物品判断
	
	public static final String WHOLEVEHICLE_FEE_RANGE_UP = "1011";//整车约车报价区间上限
	
	public static final String WHOLEVEHICLE_FEE_RANGE_LOW = "1012";//整车约车报价区间下限
	
	//零担自动调度总开关
	public static final String PKP_AUTO_SCHEDULE_MANAGE = "AUTO_SCHEDULING_MANAGE";
	
	//快递自动调度总开关
	public static final String PKP_AUTO_EXPRESS_SCHEDULE_MANAGE = "EXPRESS_AUTO_SCHEDULE";
	//快递自动调度容量 14.7.14
	public static final String PKP_EXPRESS_AUTO_SCHEDULE_COUNT = "EXPRESS_AUTO_SCHEDULE_COUNT";
	//零担自动调度容量 14.7.14
	public static final String PKP_AUTO_SCHEDULE_COUNT = "AUTO_SCHEDULE_COUNT";
	public static final String HK_ZZ_SH_0To100="HK_ZZ_SH_0To100";
	public static final String HK_SY_SH_0To100="HK_SY_SH_0To100";
	public static final String HK_SY_SH_100To300="HK_SY_SH_100To300";
	public static final String HK_ZZ_SH_100To300="HK_ZZ_SH_100To300";
	public static final String HK_SY_SH_300To500="HK_SY_SH_300To500";
	public static final String HK_ZZ_SH_300To500="HK_ZZ_SH_300To500";
	public static final String HK_SY_SH_500To1000="HK_SY_SH_500To1000";
	public static final String HK_ZZ_SH_500To1000="HK_ZZ_SH_500To1000";
	public static final String HK_GG_FEE_0To100="HK_GG_FEE_0To100";
	public static final String HK_GG_FEE_100ToBG="HK_GG_FEE_100ToBG";
	
	//电子运单校验开关
	public static final String PKP_EWAYBILL_CHECK_SWITCH = "EWAYBILL_CHECK_SWITCH";
	
	//短信开关
	public static final String STL_ORDER_MESSAGE_SWITCH = "ORDER_MESSAGE_SWITCH";
	
	/**
	 * 派送、手工放行时间段，在这个时间范围内出发的派送任务，需要给客户发短信
	 * 格式：0800-1900，指当天早八点到晚七点，不允许跨天
	 */
	public static final String TFR_PARM_DELIVER_DEPART_TIME_RANGE = "TFR_PARM_DELIVER_DEPART_TIME_RANGE";
	//空运开单31天以上运单签收短信发送开关
	public static final String STL_SIGN_MESSAGE_SWITCH = "STL_SIGN_MESSAGE_SWITCH";
	
	public static final String TFR_AUTO_CHECKDPJZ_SIGNINMSG = "TFR_AUTO_CHECKDPJZ_SIGNINMSG";
	
	/**
	 * 合伙人调用规则引擎
	 */
	public static final String PARTNER_RULE_ENGINE = "PARTNER_RULE_ENGINE";
	
	//装车明细下拉接口开关
    public static final String TFR_FOSS_LOAD_DOWNLOAD = "TFR_FOSS_LOAD_DOWNLOAD";


}
