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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/define/ScheduleConstants.java
 * 
 *  FILE NAME     :ScheduleConstants.java
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
 * FILE    NAME: OrderVehicleConstants.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.define;

/**
 * 短途发车计划常量类
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-3-26 下午2:36:25
 */
public final class ScheduleConstants {

	/**
	 * 计划状态-可用
	 */
	public static final String SCHEDULE_STATUS_ACTIVE = "Y";
	/**
	 * NAN
	 */
	public static final String NAN = "NaN";
	/**
	 * 逗号
	 */
	public static final String COMMA = ",";
	/**
	 * 计划状态-删除
	 */
	public static final String SCHEDULE_STATUS_DELETED = "DELETED";
	/**
	 * 计划类型-短途排班
	 */
	public static final String SCHEDULE_TYPE_SHORTSCHEDULE = "TFR";
	/**
	 * 计划类型-接送货
	 */
	public static final String SCHEDULE_TYPE_DELIVERY = "PKP";
	/**
	 * 工作类别-休息
	 */
	public static final String PLAN_TYPE_REST = "REST";
	/**
	 * 工作类别-出车
	 */
	public static final String PLAN_TYPE_WORK = "WORK";
	/**
	 * 工作类别-值班
	 */
	public static final String PLAN_TYPE_ON_DUTY = "DUTY";
	/**
	 * 工作类别-培训
	 */
	public static final String PLAN_TYPE_TRAIN = "TRAINING";
	/**
	 * 工作类别-离岗
	 */
	public static final String PLAN_TYPE_UNDERGO = "LEAVE";
	/**
	 * 工作类别-未知
	 */
	public static final String PLAN_TYPE_UNKNOWN = "UNKNOWN";
	/**
	 * 增加1
	 */
	public static final Integer ADD_INT_VALUE = 1;
	/**
	 * 结果0
	 */
	public static final Long RESULT_INT_VALUE_ZERO = 0L;
	/**
	 * 接送货导入列数
	 */
	public static final Integer PKP_EXCEL_LAST_COLUMN_NUM = 10;
	/**
	 * 短途导入列数
	 */
	public static final Integer TFR_EXCEL_LAST_COLUMN_NUM = 13;
	/**
	 * 第一
	 */
	public static final Integer EXCEL_DEFAULT_SHEET_ONE = 0;
	/**
	 * 列数初始值
	 */
	public static final Integer EXCEL_COLUMN_INIT_SIZE = 0;
	/**
	 * 第一列
	 */
	public static final Integer EXCEL_COLUMN_FIRST = 0;
	/**
	 * 第二列
	 */
	public static final Integer EXCEL_COLUMN_SECOND = 1;
	/**
	 * 下标0
	 */
	public static final Integer IDX_NUM = 0;
	/**
	 * 0
	 */
	public static final Integer ZERO = 0;
	/**
	 * 时间截取位置0
	 */
	public static final Integer TIME_SUBSTRING_ZERO = 0;
	/**
	 * 时间截取位置2
	 */
	public static final Integer TIME_SUBSTRING_TWO = 2;
	/**
	 * 时间截取位置4
	 */
	public static final Integer TIME_SUBSTRING_FOUR = 4;
	/**
	 * 时间截取位置7
	 */
	public static final Integer TIME_SUBSTRING_SEVEN = 7;
	/**
	 * 时间截取位置8
	 */
	public static final Integer TIME_SUBSTRING_EIGHT = 8;
	/**
	 * 时间截取位置10
	 */
	public static final Integer TIME_SUBSTRING_TEN = 10;
	/**
	 * 行数计算因子
	 */
	public static final Integer EXCEL_ROW_ADD_ONE = 1;
	/**
	 * 表格每页行数
	 */
	public static final Integer EXCEL_DEFAULT_SHEET_SIZE = 5000;
	/**
	 * 车辆可用状态
	 */
	public static final String VEHICLENO_ACTIVE = "Y";
	/**
	 * 更新操作
	 */
	public static final String ACTION_TYPE_UPDATE = "UPDATE";
	/**
	 * 新增操作
	 */
	public static final String ACTION_TYPE_INSERT = "INSERT";
	/**
	 * 排班异常
	 */
	public static final String SCHEDULE_EXCEPTION = "SCHEDULE_EXCEPTION";
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
	 * 排班表信息
	 */
	public static final String EXPORT_EXCEL_FILE_NAME = "排班表信息";

	/**
	 * 排班表信息
	 */
	public static final String EXPORT_EXCEL_FILE_NAME_COMPLEX = "排班表视图";
	/**
	 * 年月默认日为01
	 */
	public static final String YEAR_MONTH_TAIL = "-01";
	/**
	 * 列位数
	 */
	public static final String COLUMN_TAIL = ".0";
	/**
	 * 时间结尾
	 */
	public static final String TIME_TAIL = ":00";
	/**
	 * 冒号
	 */
	public static final String COLON = ":";
	/**
	 * 短途排班表头
	 */
	public static final String[] TFR_ROW_HEADERS = { "排班类型", "部门编号", "部门名称", "司机编号", "司机姓名", "日期", "任务类型编号", "任务类型",
			"任务序号", "车牌号", "线路简码", "线路", "班次" };
	/**
	 * 接送货排班表头
	 */
	public static final String[] PKP_ROW_HEADERS = { "排班类型", "部门编号", "部门名称", "司机编号", "司机姓名", "日期", "任务类型编号", "任务类型",
			"任务序号", "车牌号" };

	/**
	 * 列0
	 */
	public static final int COL_0 = 0;
	/**
	 * 列1
	 */
	public static final int COL_1 = 1;
	/**
	 * 列2
	 */
	public static final int COL_2 = 2;
	/**
	 * 列3
	 */
	public static final int COL_3 = 3;
	/**
	 * 列4
	 */
	public static final int COL_4 = 4;
	/**
	 * 列5
	 */
	public static final int COL_5 = 5;
	/**
	 * 列6
	 */
	public static final int COL_6 = 6;
	/**
	 * 列7
	 */
	public static final int COL_7 = 7;
	/**
	 * 列8
	 */
	public static final int COL_8 = 8;
	/**
	 * 列9
	 */
	public static final int COL_9 = 9;
	/**
	 * 列10
	 */
	public static final int COL_10 = 10;
	/**
	 * 列11
	 */
	public static final int COL_11 = 11;
	/**
	 * 列12
	 */
	public static final int COL_12 = 12;
	/**
	 * 列13
	 */
	public static final int COL_13 = 13;
	/**
	 * 列14
	 */
	public static final int COL_14 = 14;
	/**
	 * 列15
	 */
	public static final int COL_15 = 15;
	/**
	 * 值班
	 */
	public static final String PLAN_TYPE_ON_DUTY_TXT = "值班";
	/**
	 * 休息
	 */
	public static final String PLAN_TYPE_REST_TXT = "休息";
	/**
	 * 培训
	 */
	public static final String PLAN_TYPE_TRAIN_TXT = "培训";
	/**
	 * 离岗
	 */
	public static final String PLAN_TYPE_UNDERGO_TXT = "离岗";
	/**
	 * 未知
	 */
	public static final String PLAN_TYPE_UNKNOWN_TXT = "未知";
	/**
	 * 出车
	 */
	public static final String PLAN_TYPE_WORK_TXT = "出车";
	/**
	 * 车队调度组
	 */
	public static final String DISPATCH_TEAM_Y = "Y";
	
	/**sonar_constants_number*/
	public final static int SONAR_NUMBER_3 = 3;
	public final static int SONAR_NUMBER_4 = 4;
	public final static int SONAR_NUMBER_5 = 5;
	public final static int SONAR_NUMBER_6 = 6;
	public final static int SONAR_NUMBER_7 = 7;
	public final static int SONAR_NUMBER_8 = 8;
	public final static int SONAR_NUMBER_9 = 9;
	public final static int SONAR_NUMBER_10 = 10;
	public final static int SONAR_NUMBER_11 = 11;
	public final static int SONAR_NUMBER_12 = 12;
	public final static int SONAR_NUMBER_13 = 13;
	public static final int SONAR_NUMBER_36 = 36;

}