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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/util/CompareUtils.java
 * 
 *  FILE NAME     :CompareUtils.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.util
 * FILE    NAME: CompareUtils.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.transfer.scheduling.api.define.TruckDepartPlanConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.TruckDepartPlanDetailDto;
import com.deppon.foss.util.DateUtils;

/**
 * 用于比对发车计划bean的属性值，给出结果
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-4 上午9:34:20
 */
public class CompareUtils {
	/**
	 * 调整为
	 */
	public static final String CHANGE_DESC = "[调整为]";
	public static final String CHANGE_DESC_KEY = "CHANGE_DESC_KEY";
	/**
	 * 新增计划
	 */
	public static final String CHANGE_ADD_VECHILE_DESC = "新增计划:";
	public static final String CHANGE_ADD_VECHILE_DESC_KEY = "CHANGE_ADD_VECHILE_DESC_KEY";
	/**
	 * 日期
	 */
	public static final String FIELD_NAME_SCHEDULE_DATE = "日期:";
	public static final String FIELD_NAME_SCHEDULE_DATE_OLD = "DATE_OLD";
	public static final String FIELD_NAME_SCHEDULE_DATE_NEW = "DATE_NEW";
	/**
	 * 线路
	 */
	public static final String FIELD_NAME_LINE_NO = "线路:";
	public static final String FIELD_NAME_LINE_NO_OLD = "LINE_NO_OLD";
	public static final String FIELD_NAME_LINE_NO_NEW = "LINE_NO_NEW";
	/**
	 * 班次
	 */
	public static final String FIELD_NAME_FREQUENCY_NO = "班次:";
	public static final String FIELD_NAME_FREQUENCY_NO_OLD = "FREQUENCY_NO_OLD";
	public static final String FIELD_NAME_FREQUENCY_NO_NEW = "FREQUENCY_NO_NEW";
	/**
	 * 车牌
	 */
	public static final String FIELD_NAME_VEHICLE_NO = "车牌:";
	public static final String FIELD_NAME_VEHICLE_NO_OLD = "VEHICLE_NO_OLD";
	public static final String FIELD_NAME_VEHICLE_NO_NEW = "VEHICLE_NO_NEW";
	/**
	 * 司机
	 */
	public static final String FIELD_NAME_DRIVER_NAME = "司机:";
	public static final String FIELD_NAME_DRIVER_NAME_OLD = "DRIVER_NAME_OLD";
	public static final String FIELD_NAME_DRIVER_NAME_NEW = "DRIVER_NAME_NEW";
	/**
	 * 前缀
	 */
	public static final String TMP_PREFIX = "$";
	/**
	 * 调整前缀模板
	 */
	public static final String NOTICE_TMP_HEADER = "发车计划中：日期:$DATE_OLD$,线路:$LINE_NO_OLD$，班次:$FREQUENCY_NO_OLD$，车牌:$VEHICLE_NO_OLD$，司机:$DRIVER_NAME_OLD$";
	public static final String TFR_PLAN_NOTICE_TMP_HEADER = "TFR_PLAN_NOTICE_TMP_HEADER";
	/**
	 * 调整或新增末尾模板
	 */
	public static final String NOTICE_TMP_TAIL = "日期:$DATE_NEW$,线路:$LINE_NO_NEW$，班次:$FREQUENCY_NO_NEW$，车牌:$VEHICLE_NO_NEW$，司机:$DRIVER_NAME_NEW$";
	public static final String TFR_PLAN_NOTICE_TMP_TAIL = "TFR_PLAN_NOTICE_TMP_TAIL";
	/**
	 * 字符串为空
	 */
	public static final int STRINGBUFFER_LENGTH_NULL = 0;
	/**
	 * 冒号分割
	 */
	public static String SPERATOR_RISK = ":";
	/**
	 * 逗号
	 */
	public static String SPERATOR_COMMA = ",";
	/**
	 * 标记-是
	 */
	public static String FLAG_Y = "Y";
	/**
	 * 标记-否
	 */
	public static String FLAG_N = "N";
	/**
	 * 车牌号码
	 */
	public static final String FIELD_NAME_VECHILE_NO = "车牌号码:";
	/**
	 * 司机姓名
	 */
	public static final String FIELD_NAME_DRIVERNAME = "司机姓名:";

	public static final Log LOGGER = LogFactory.getLog(DateUtils.class);
	/**
	 * 线路标题
	 */
	public static String LINETILE = "新增发车计划 线路";
	/**
	 * 计划发车时间
	 */
	public static String DEPARTTIMETITLE = ",计划发车时间:";

	/**
	 * 变动描述符
	 */
	public static String SPERATOR_OF_CHANGE = "=>";
	/**
	 * 标记-是（中文）
	 */
	public static final String FLAG_Y_DESC = "是";
	/**
	 * 标记-否（中文）
	 */
	public static final String FLAG_N_DESC = "否";

	/**
	 * 发车日期
	 */
	public static final String FIELD_NAME_DEPARTDATE = "发车日期:";
	/**
	 * 计划发车时间
	 */
	public static final String FIELD_NAME_PLANDEPARTTIME = "计划发车时间:";
	/**
	 * 班次
	 */
	public static final String FIELD_NAME_FREQUENCYNO = "班次:";
	/**
	 * 月台号
	 */
	public static final String FIELD_NAME_PLATFORMNOCODE = "月台号:";
	/**
	 * 是否正班车
	 */
	public static final String FIELD_NAME_ISONSCHEDULING = "是否正班车:";
	/**
	 * 班次类型
	 */
	public static final String FIELD_NAME_FREQUENCYTYPE = "班次类型:";
	/**
	 * 车型
	 */
	public static final String FIELD_NAME_TRUCKMODEL = "车型:";
	/**
	 * 归属类型
	 */
	public static final String FIELD_NAME_TRUCKTYPE = "归属类型:";
	/**
	 * 货柜号
	 */
	public static final String FIELD_NAME_CONTAINERNO = "货柜号:";
	/**
	 * 最大载重
	 */
	public static final String FIELD_NAME_MAXLOADWEIGHT = "最大载重:";
	/**
	 * 实际最大载重
	 */
	public static final String FIELD_NAME_ACTUALMAXLOADWEIGHT = "实际最大载重:";
	/**
	 * 车辆净空
	 */
	public static final String FIELD_NAME_TRUCKVOLUME = "车辆净空:";
	/**
	 * 预计装载载重
	 */
	public static final String FIELD_NAME_PLANLOADWEIGHT = "预计装载载重:";
	/**
	 * 预计装载体积
	 */
	public static final String FIELD_NAME_PLANLOADVOLUME = "预计装载体积:";
	/**
	 * 备注
	 */
	public static final String FIELD_NAME_TRUCKINFONOTES = "备注:";
	/**
	 * 司机姓名1
	 */
	public static final String FIELD_NAME_DRIVERNAME1 = "司机姓名1:";
	/**
	 * 联系方式1
	 */
	public static final String FIELD_NAME_DRIVERPHONE1 = "联系方式1:";
	/**
	 * 司机姓名2
	 */
	public static final String FIELD_NAME_DRIVERNAME2 = "司机姓名2:";
	/**
	 * 联系方式2
	 */
	public static final String FIELD_NAME_DRIVERPHONE2 = "联系方式2:";

	/**
	 * 联系方式
	 */
	public static final String FIELD_NAME_DRIVERPHONE = "联系方式:";
	/**
	 * 线路名称
	 */
	public static final String FIELD_NAME_LINENAME = "线路名称:";
	/**
	 * 计划到达时间
	 */
	public static final String FIELD_NAME_PLANARRIVETIME = "计划到达时间:";
	/**
	 * 月台使用时间
	 */
	public static final String FIELD_NAME_PLATFORMTIMESTART = "月台使用时间:";
	/**
	 * 月台结束时间
	 */
	public static final String FIELD_NAME_PLATFORMTIMEEND = "月台结束时间:";
	/**
	 * 车队
	 */
	public static final String FIELD_NAME_CARGROUPNAME = "车队:";
	/**
	 * 对应排班的修改
	 */
	public static final String SCHEDULING_MODIDY = "SCHEDULING_MODIDY";
	/**
	 * 针对排班新增的
	 */
	public static final String PLAN_ADD_NEW = "PLAN_ADD_NEW";

	/**
	 * 比对发车计划修改内容
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-3 下午8:30:33
	 */
	public static StringBuffer compareModifyContent(TruckDepartPlanDetailDto oldDetail,
			TruckDepartPlanDetailDto detailDto) {
		// 比对结果
		StringBuffer content = new StringBuffer();
		// 更新比对
		if (oldDetail != null) {
			compareDesc(oldDetail, detailDto, content);
		}// 直接生成新增比对结果
		else {
			content.append(LINETILE).append(SPERATOR_RISK).append(detailDto.getLineName()).append(DEPARTTIMETITLE)
					.append(DateUtils.convert(detailDto.getPlanDepartTime(), DateUtils.DATE_TIME_FORMAT));
		}
		return content;
	}

	/**
	 * 修改描述
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午12:34:40
	 */
	private static void compareDesc(TruckDepartPlanDetailDto oldDetail, TruckDepartPlanDetailDto detailDto,
			StringBuffer content) {

		// 车牌号
		apendStringType(FIELD_NAME_VECHILE_NO, oldDetail.getVehicleNo(), detailDto.getVehicleNo(), content);

		// 发车日期

		apendDateType(FIELD_NAME_DEPARTDATE, oldDetail.getDepartDate(), detailDto.getDepartDate(), content);

		// 计划发车时间

		apendDateType(FIELD_NAME_PLANDEPARTTIME, oldDetail.getPlanDepartTime(), detailDto.getPlanDepartTime(), content);

		// 班次号

		apendStringType(FIELD_NAME_FREQUENCYNO, oldDetail.getFrequencyNo(), detailDto.getFrequencyNo(), content);

		// 月台

		apendStringType(FIELD_NAME_PLATFORMNOCODE, oldDetail.getPlatformNoCode(), detailDto.getPlatformNoCode(),
				content);

		// 是否正班车

		apendBoolType(FIELD_NAME_ISONSCHEDULING, oldDetail.getIsOnScheduling(), detailDto.getIsOnScheduling(), content);

		// 班次类型
		apendStringType(FIELD_NAME_FREQUENCYTYPE,
				DictUtil.rendererSubmitToDisplay(oldDetail.getFrequencyType(), DictionaryConstants.TFR_FREQUENCY_TYPE),
				DictUtil.rendererSubmitToDisplay(detailDto.getFrequencyType(), DictionaryConstants.TFR_FREQUENCY_TYPE),
				content);

		// 车型
		if(StringUtils.isNotBlank(detailDto.getTruckModelValue())){
			apendStringType(FIELD_NAME_TRUCKMODEL, oldDetail.getTruckModelValue(), detailDto.getTruckModelValue(), content);
		}else{
			apendStringType(FIELD_NAME_TRUCKMODEL, oldDetail.getTruckModel(), detailDto.getTruckModel(), content);
		}
		

		// 车辆归属类型

		apendStringType(FIELD_NAME_TRUCKTYPE, oldDetail.getTruckType(), detailDto.getTruckType(), content);

		// 货柜号

		apendStringType(FIELD_NAME_CONTAINERNO, oldDetail.getContainerNo(), detailDto.getContainerNo(), content);

		// 最大载重

		apendBigDecimalType(FIELD_NAME_MAXLOADWEIGHT, oldDetail.getMaxLoadWeight(), detailDto.getMaxLoadWeight(),
				content);

		// 实际最大载重

		apendBigDecimalType(FIELD_NAME_ACTUALMAXLOADWEIGHT, oldDetail.getActualMaxLoadWeight(),
				detailDto.getActualMaxLoadWeight(), content);

		// 车辆净空

		apendBigDecimalType(FIELD_NAME_TRUCKVOLUME, oldDetail.getTruckVolume(), detailDto.getTruckVolume(), content);

		// 预计装载载重

		apendBigDecimalType(FIELD_NAME_PLANLOADWEIGHT, oldDetail.getPlanLoadWeight(), detailDto.getPlanLoadWeight(),
				content);

		// 预计装载体积

		apendBigDecimalType(FIELD_NAME_PLANLOADVOLUME, oldDetail.getPlanLoadVolume(), detailDto.getPlanLoadVolume(),
				content);

		// 备注

		apendStringType(FIELD_NAME_TRUCKINFONOTES, oldDetail.getTruckInfoNotes(), detailDto.getTruckInfoNotes(),
				content);
		// 长途
		if (TruckDepartPlanConstants.PLAN_TYPE_LONG.equals(oldDetail.getPlanType())) {
			// 司机姓名1

			apendStringType(FIELD_NAME_DRIVERNAME1, oldDetail.getDriverName1(), detailDto.getDriverName1(), content);

			// 司机电话1

			apendStringType(FIELD_NAME_DRIVERPHONE1, oldDetail.getDriverPhone1(), detailDto.getDriverPhone1(), content);

		} else {
			// 司机姓名1

			apendStringType(FIELD_NAME_DRIVERNAME, oldDetail.getDriverName1(), detailDto.getDriverName1(), content);

			// 司机电话1

			apendStringType(FIELD_NAME_DRIVERPHONE, oldDetail.getDriverPhone1(), detailDto.getDriverPhone1(), content);
		}
		// 司机2

		apendStringType(FIELD_NAME_DRIVERNAME2, oldDetail.getDriverName2(), detailDto.getDriverName2(), content);

		// 司机电话2

		apendStringType(FIELD_NAME_DRIVERPHONE2, oldDetail.getDriverPhone2(), detailDto.getDriverPhone2(), content);

		// 线路名称

		apendStringType(FIELD_NAME_LINENAME, oldDetail.getLineName(), detailDto.getLineName(), content);

		// 计划到达时间

		apendDateType(FIELD_NAME_PLANARRIVETIME, oldDetail.getPlanArriveTime(), detailDto.getPlanArriveTime(), content);

		// 月台使用时间

		apendDateType(FIELD_NAME_PLATFORMTIMESTART, oldDetail.getPlatformTimeStart(), detailDto.getPlatformTimeStart(),
				content);

		// 月台结束时间

		apendDateType(FIELD_NAME_PLATFORMTIMEEND, oldDetail.getPlatformTimeEnd(), detailDto.getPlatformTimeEnd(),
				content);

		// 车队

		apendStringType(FIELD_NAME_CARGROUPNAME, oldDetail.getCarGroupName(), detailDto.getCarGroupName(), content);

	}

	/**
	 * 类似布尔类型转换
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午1:55:34
	 */
	private static void apendBoolType(String keyName, String value1, String value2, StringBuffer content) {
		String filed1 = "";
		String filed2 = "";
		if (StringUtils.isBlank(value1)) {
			filed1 = "空";
		} else {
			if (FLAG_Y.equals(value1)) {
				filed1 = FLAG_Y_DESC;
			} else {
				filed1 = FLAG_N_DESC;
			}
		}
		if (StringUtils.isBlank(value2.toString())) {
			filed2 = "空";
		} else {
			if (FLAG_N.equals(value2)) {
				filed2 = FLAG_N_DESC;
			} else if (FLAG_Y.equals(value2)) {
				filed2 = FLAG_Y_DESC;
			}
		}
		if (!filed1.equals(filed2)) {
			content.append(keyName).append(filed1).append(SPERATOR_OF_CHANGE).append(filed2).append(SPERATOR_COMMA);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(keyName + filed1 + SPERATOR_OF_CHANGE + filed2 + SPERATOR_COMMA);
			}
		}

	}

	/**
	 * 数值类型
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午3:27:41
	 */
	private static void apendBigDecimalType(String keyName, BigDecimal value1, BigDecimal value2, StringBuffer content) {
		String filed1 = "";
		String filed2 = "";
		if (value1 == null || StringUtils.isBlank(value1.toString())) {
			filed1 = "空";
		} else {
			filed1 = value1.toString();
		}
		if (value1 == null || StringUtils.isBlank(value2.toString())) {
			filed2 = "空";
		} else {
			filed2 = value2.toString();
		}
		if (!filed1.equals(filed2)) {
			content.append(keyName).append(filed1).append(SPERATOR_OF_CHANGE).append(filed2).append(SPERATOR_COMMA);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(keyName + filed1 + SPERATOR_OF_CHANGE + filed2 + SPERATOR_COMMA);
			}
		}

	}

	/**
	 * 字符串型转换
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午1:35:35
	 */
	private static void apendStringType(String keyName, String value1, String value2, StringBuffer content) {
		String filed1 = "";
		String filed2 = "";
		if (value1 == null || StringUtils.isBlank(value1)) {
			filed1 = "空";
		} else {
			filed1 = value1;
		}
		if (value2 == null || StringUtils.isBlank(value2)) {
			filed2 = "空";
		} else {
			filed2 = value2;
		}
		if (!filed1.equals(filed2)) {
			content.append(keyName).append(filed1).append(SPERATOR_OF_CHANGE).append(filed2).append(SPERATOR_COMMA);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(keyName + filed1 + SPERATOR_OF_CHANGE + filed2 + SPERATOR_COMMA);
			}
		}
	}

	/**
	 * 时间型转换
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-4 下午1:35:35
	 */
	private static void apendDateType(String keyName, Date value1, Date value2, StringBuffer content) {
		String filed1 = "";
		String filed2 = "";
		if (value1 == null || StringUtils.isBlank(value1.toString())) {
			filed1 = "空";
		} else {
			filed1 = DateUtils.convert(value1, DateUtils.DATE_TIME_FORMAT);
		}
		if (value2 == null || StringUtils.isBlank(value2.toString())) {
			filed2 = "空";
		} else {
			filed2 = DateUtils.convert(value2, DateUtils.DATE_TIME_FORMAT);
		}
		if (!filed1.equals(filed2)) {
			content.append(keyName).append(filed1).append(SPERATOR_OF_CHANGE).append(filed2).append(SPERATOR_COMMA);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(keyName + filed1 + SPERATOR_OF_CHANGE + filed2 + SPERATOR_COMMA);
			}
		}
	}

	/**
	 * 模板数据
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午12:40:37
	 */
	public static String compareDetaiAndTask(Map<String, String> compMap, String noticeTmpHeader, String noticeTmpTail) {
		StringBuffer sb = new StringBuffer();
		if (compMap != null) {
			// 调整为
			if (compMap.get(CompareUtils.CHANGE_DESC_KEY) != null) {
				sb.append(noticeTmpHeader);
				sb.append(CompareUtils.CHANGE_DESC);
				sb.append(noticeTmpTail);
			}// 新增
			else if (compMap.get(CompareUtils.CHANGE_ADD_VECHILE_DESC_KEY) != null) {
				sb.append(CompareUtils.CHANGE_ADD_VECHILE_DESC);
				sb.append(noticeTmpTail);
			}
		}
		return sb.toString();
	}

	/**
	 * 替换字符串
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-8 下午12:58:08
	 */
	public static String replaceString(Map<String, String> compMap, String tmpString) {
		String rs = tmpString;
		if (compMap != null && rs != null) {
			for (Map.Entry<String, String> m : compMap.entrySet()) {
				// 替换
				rs = rs.replace(TMP_PREFIX + m.getKey() + TMP_PREFIX, m.getValue() == null ? "" : m.getValue());
			}
		}
		return rs;
	}
}