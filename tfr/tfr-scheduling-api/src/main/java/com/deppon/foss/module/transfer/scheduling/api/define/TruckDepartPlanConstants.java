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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/TruckDepartPlanConstants.java
 * 
 *  FILE NAME     :TruckDepartPlanConstants.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.define
 * FILE    NAME: TruckDepartPlanConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.define;

import java.math.BigDecimal;

/**
 * 发车计划常量
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-22 上午10:02:33
 */
public class TruckDepartPlanConstants {

	/**
	 * 状态-可用
	 */
	public static final String STATUS_ACTIVE = "Y";
	/**
	 * 状态-不可用
	 */
	public static final String STATUS_NOT_ACTIVE = "N";
	/**
	 * 全部
	 */
	public static final String CAR_STATUS_ALL = "A";
	/**
	 * 计划状态-新建
	 */
	public static final String PLAN_STATUS_NEW = "NEW";
	/**
	 * 计划状态-下发
	 */
	public static final String PLAN_STATUS_RELEASE = "RELEASE";
	/**
	 * 计划类型-短途
	 */
	public static final String PLAN_TYPE_SHORT = "SHORT";
	/**
	 * 计划类型-长途
	 */
	public static final String PLAN_TYPE_LONG = "LONG";
	/**
	 * 是否异常-是
	 */
	public static final String IS_ISSUE_YES = "Y";
	/**
	 * 是否异常-否
	 */
	public static final String IS_ISSUE_NO = "N";
	/**
	 * 是否正班车-是
	 */
	public static final String IS_ON_SCHEDULING_YES = "Y";
	/**
	 * 是否正班车-否
	 */
	public static final String IS_ON_SCHEDULING_NO = "N";
	/**
	 * 字符串-空值
	 */
	public static final String COLUMN_NULL_REPLACER = "N/A";
	/**
	 * 班次类型-正常
	 */
	public static final String FREQUENCY_TYPE_NORMAL = "NORMAL";
	/**
	 * 班次类型-停发
	 */
	public static final String FREQUENCY_TYPE_SUSPEND = "SUSPEND";
	/**
	 * 班次类型-加发
	 */
	public static final String FREQUENCY_TYPE_ADD = "ADD";
	/**
	 * 归属类型-自有
	 */
	public static final String TRUCK_TYPE_MYSELF_OWN = "OWN";
	/**
	 * 归属类型-外请
	 */
	public static final String TRUCK_TYPE_OUTER = "OUTER";
	/**
	 * 事务成功
	 */
	public static final int SERVICE_TRANSACTION_SUCCESS = 1;
	/**
	 * 事务成功
	 */
	public static final int SERVICE_TRANSACTION_HAS_ADDED = 2;
	/**
	 * 事务失败
	 */
	public static final int SERVICE_TRANSACTION_FAILURE = 0;
	/**
	 * 查询结果总数0
	 */
	public static final Long RESULT_ZERO = 0L;
	/**
	 * 车辆状态-可用
	 */
	public static final String CAR_STATUS_ENABLE = "Y";
	/**
	 * 车辆状态-不可用
	 */
	public static final String CAR_STATUS_DISABLE = "N";
	/**
	 * 营业部
	 */
	public static final String IS_SALES_DEPARTMENT = "Y";
	/**
	 * 外场
	 */
	public static final String IS_TRANSFER_CENTER = "Y";
	/**
	 * 由时效标准导入
	 */
	public static final String INIT_FLAG_Y = "Y";
	/**
	 * 由时效标准导入
	 */
	public static final String INIT_FLAG_N = "N";
	/**
	 * 发车计划对应车辆已出发
	 */
	public static final String LEFT_FLAG_Y = "Y";
	/**
	 * 已经出发
	 */
	public static final String LEFT_FLAG_Y_DESC = "已出发";
	/**
	 * 发车计划对应车辆未出发
	 */
	public static final String LEFT_FLAG_N = "N";
	/**
	 * 操作类型- 新增
	 */
	public static final String OPERATION_TYPE_INSERT = "INSERT";
	/**
	 * 操作类型- 更新
	 */
	public static final String OPERATION_TYPE_UPDATE = "UPDATE";
	/**
	 * 货量预测-分隔符
	 */
	public static final String QUANTITY_DESC_SEPERATOR = "/";
	/**
	 * 备注分隔符
	 */
	public static final String NOTES_SEPERATOR = "|";
	/**
	 * 空
	 */
	public static final String EMPTY = "-";
	/**
	 * 精度
	 */
	public static final int SCALE_NUM = 2;
	/**
	 * 重量单位
	 */
	public static final String WEIGHT_UNIT = "T";
	/**
	 * 重量单位-吨
	 */
	public static final String WEIGHT_UNIT_DESC = "吨";
	/**
	 * 体积单位
	 */
	public static final String VOLUMN_UNIT = "F";
	/**
	 * 体积单位-方
	 */
	public static final String VOLUMN_UNIT_DESC = "方";
	/**
	 * 合入
	 */
	public static final String MERGE_TYPE_IN = "IN";
	/**
	 * 合入中文描述
	 */
	public static final String MERGE_TYPE_IN_DESC = "合入";
	/**
	 * 合出
	 */
	public static final String MERGE_TYPE_OUT = "OUT";
	/**
	 * 合出中文描述
	 */
	public static final String MERGE_TYPE_OUT_DESC = "合出";
	/**
	 * 双桥
	 */
	public static final String BRIDGE_DOUBLE = "DOUBLE";
	/**
	 * 双桥默认最大载重=33
	 */
	public static final BigDecimal BRIDGE_DOUBLE_DEFAULT_MAX_WEIGHT = BigDecimal.valueOf(33);
	/**
	 * 双桥实际最大载重计算因子
	 */
	public static final BigDecimal BRIDGE_DOUBLE_ACT_MAX_WEIGHT = BigDecimal.valueOf(55);
	/**
	 * 单桥
	 */
	public static final String BRIDGE_SINGLE = "SINGLE";
	/**
	 * 单桥默认最大载重=30
	 */
	public static final BigDecimal BRIDGE_SINGLE_DEFAULT_MAX_WEIGHT = BigDecimal.valueOf(30);
	/**
	 * 单桥实际最大载重计算因子
	 */
	public static final BigDecimal BRIDGE_SINGLE_ACT_DEFAULT_MAX_WEIGHT = BigDecimal.valueOf(50);
	/**
	 * 分页开头
	 */
	public static final int PAGE_START = 0;
	/**
	 * 分页条数限制
	 */
	public static final int PAGE_LIMIT = 1;
	/**
	 * 自有车查询开始
	 */
	public static final int OWN_VEHICLE_START = 0;
	/**
	 * 限制
	 */
	public static final int OWN_VEHICLE_LIMIT = 0;

	/**
	 * 可用
	 */
	public static final String CAR_STATUS_DESC_Y = "可用";
	/**
	 * 不可用
	 */
	public static final String CAR_STATUS_DESC_N = "[不可用]";
	/**
	 * 在用
	 */
	public static final String CAR_STATUS_DESC_USING = "在用";
	/**
	 * 在用（已计划）
	 */
	public static final String CAR_STATUS_DESC_PLAN = "在用（已计划）";
	/**
	 * 空闲
	 */
	public static final String CAR_STATUS_DESC_FREE = "空闲";
	/**
	 * 是否敞篷车-是
	 */
	public static final String OPEN_VEHICLE_Y = "Y";
	/**
	 * 是否敞篷车-否
	 */
	public static final String OPEN_VEHICLE_N = "N";
	/**
	 * 是否高栏-是
	 */
	public static final String RAIL_VEHICLE_Y = "Y";
	/**
	 * 是否高栏-否
	 */
	public static final String RAIL_VEHICLE_N = "N";
	/**
	 * 时间计算因子
	 */
	public static final int TIME_ZERO = 0;

	/**
	 * 日志类型-自动记录日志
	 */
	public static final String LOG_TYPE_AUTO = "AUTO";
	/**
	 * 用户备注
	 */
	public static final String LOG_TYPE_REMARK = "REMARK";
	/**
	 * 系统异常
	 */
	public static final String EXCEPTION_CODE = "系统异常";
	/**
	 * 外场
	 */
	public static final String TRANSFER_CENTER_Y = "Y";
	/**
	 * 车队
	 */
	public static final String TRANS_DEPARTMENT_Y = "Y";
	/**
	 * 普车时效除数因子
	 */
	public static final int COMMON_AGING_DIVISOR = 1000;
	/**
	 * 60分
	 */
	public static final BigDecimal MIN_60 = BigDecimal.valueOf(60);
	/**
	 * 24小时
	 */
	public static final BigDecimal HOUR_24 = BigDecimal.valueOf(24);
	/**
	 * 60秒
	 */
	public static final BigDecimal SEC_60 = BigDecimal.valueOf(60);
	/**
	 * 普车时效初始化0
	 */
	public static final int COMMON_AGING_DEFAULT = 0;
	/**
	 * 短途发车计划
	 */
	public static final String SHORT_PLAN_EXCEL_FILE_NAME = "短途发车计划";
	/**
	 * 长途发车计划
	 */
	public static final String LONG_PLAN_EXCEL_FILE_NAME = "长途发车计划";
	/**
	 * 发车计划导出Excel表头
	 */
	public static final String[] EXCEL_HEADER_ROWS = { "出发部门", "到达部门", "发车日期", "线路", "发车时间", "班次", "正班车", "班次类型",
			"司机1", "联系方式1", "司机2", "联系方式2", "车牌号", "车型", "归属类型", "月台号", "货柜号", "车队", "创建人", "创建时间", "状态" };
	/**
	 * 发车计划导出Excel表头
	 */
	public static final String[] EXCEL_LONG_HEADER_ROWS = { "出发部门", "到达部门", "发车日期", "线路", "发车时间", "班次", "正班车", "班次类型",
			"司机1", "联系方式1", "司机2", "联系方式2", "车牌号", "车型", "归属类型", "月台号", "货柜号","挂牌号", "车队", "创建人", "创建时间", "状态" };
	/**
	 * 已经存在
	 */
	public static final String HAS_ADDED = "exist";
}