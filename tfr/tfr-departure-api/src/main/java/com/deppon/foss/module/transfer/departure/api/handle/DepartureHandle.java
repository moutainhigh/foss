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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/handle/DepartureHandle.java
 *  
 *  FILE NAME          :DepartureHandle.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.handle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.TruckDepartEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.AutoDepartDTO;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;

/**
 * 车辆放行助手类
 *
 * @author foss-liubinbin(for IBM)
 * @date 2013-3-18 下午3:38:47
 */
public class DepartureHandle{
	
	/** *********** 日志 **************. */
	private static final Logger LOGGER = LogManager
			.getLogger(DepartureHandle.class);

	/** *****时间格式1********. */
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
	
	/** *****时间格式2********. */
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd";

	private static Calendar fromCal = Calendar.getInstance();
	/**
	 * 字符串类型的转成date类型的.
	 *
	 * @param strDate the str date
	 * @return the date
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:01:50
	 */
	public static Date getDate(String strDate){
		if ("".equals(strDate) || null == strDate)
			return null;

		return DepartureHandle.getDate(strDate, DepartureHandle.DATE_FORMAT_1);
	}

	/**
	 * 转换日期为指定的模版类型.
	 *
	 * @param strDate the str date
	 * @param format the format
	 * @return the date
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:02:56
	 */
	public static Date getDate(String strDate, String format){

		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat(format);
		Date objDate = null;

		try{
			objDate = (Date) objSimpleDateFormat.parse(strDate);
		} catch (java.text.ParseException e){
			throw new TfrBusinessException("日期比较不正确");
		}
		return objDate;

	}

	/**
	 * date类型的转成String类型的.
	 *
	 * @param date the date
	 * @return the date str
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:04:20
	 */
	public static String getDateStr(Date date){
		if (null != date){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
			return format.format(date);
		}
		return "";
	}
	
	/**
	 * date类型的转成String类型的.
	 *
	 * @param date the date
	 * @param flag the flag
	 * @return the date str
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午3:04:20
	 */
	public static String getDateStr(Date date,String flag){
		if (null != date){
			SimpleDateFormat format = new SimpleDateFormat(flag);
			return format.format(date);
		}
		return "";
	}

	/**
	 * 取得当前日期.
	 *
	 * @return the current date
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static Date getCurrentDate(){
		return new Date();
	}

	/**
	 * 取得当前用户.
	 *
	 * @return the current user code
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static String getCurrentUserCode(){
		return FossUserContext.getCurrentUser().getEmployee().getEmpCode();
	}

	/**
	 * 取得当前用户姓名.
	 *
	 * @return the current user name
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static String getCurrentUserName(){
		return FossUserContext.getCurrentUser().getEmployee().getEmpName();
	}

	/**
	 * 放行时更新任务车辆明细.
	 *
	 * @return the current org code
	 * @author dp-liubinbin
	 * @date 2012-10-24 下午2:23:53
	 */
	public static String getCurrentOrgCode(){
		return FossUserContext.getCurrentDeptCode();
	}

	/**
	 * 将DTO转成entity，用于数据库的保存.
	 *
	 * @param autoDto the auto dto
	 * @return the truck depart entity
	 * @author IBM-liubinbin
	 * @date 2012-11-5 下午5:18:36
	 */
	public static TruckDepartEntity convertDtoToEntityForDepart(
			AutoDepartDTO autoDto){
		TruckDepartEntity entity = new TruckDepartEntity();
		//ID
		entity.setId(autoDto.getId());
		//车牌号
		entity.setVehicleNo(autoDto.getVehicleNo());
		//司机编码
		entity.setDriverCode(autoDto.getDriverCode());
		//司机姓名
		entity.setDriverName(autoDto.getDriverName());
		//司机电话
		entity.setDriverPhone(autoDto.getDriverPhone());
		//申请类型
		entity.setApplyType(autoDto.getApplyType());
		//申请时间
		entity.setApplyDepartTime(autoDto.getApplyDepartTime());
		//申请用户编码
		entity.setApplyUserCode(autoDto.getApplyUserCode());
		//申请部门编码
		entity.setApplyOrgCode(autoDto.getApplyOrgCode());
		//创建时间
		entity.setCreateTime(autoDto.getCreateTime());
		//创建部门编码
		entity.setCreateOrgCode(autoDto.getCreateOrgCode());
		//创建用户编码
		entity.setCreateUserCode(autoDto.getCreateUserCode());
		//创建用户姓名
		entity.setCreateUserName(autoDto.getCreateUserName());
		//更新部门编码
		entity.setUpdateOrgCode(autoDto.getUpdateOrgCode());
		//更新时间
		entity.setUpdateTime(autoDto.getUpdateTime());
		//更新用户编码
		entity.setUpdateUserCode(autoDto.getUpdateUserCode());
		//更新用户名称
		entity.setUpdateUserName(autoDto.getUpdateUserName());
		//状态
		entity.setStatus(autoDto.getStatus());
		//放行类型
		entity.setDepartType(autoDto.getDepartType());
		//放行事项
		entity.setDepartItems(autoDto.getDepartItems());
		//任务车辆ID
		entity.setTruckTaskId(autoDto.getTruckTaskId());
		return entity;
	}

	/**
	 * 查看日期是不是当天的，如果是，返回true，否则返回false.
	 *
	 * @param date the date
	 * @return true, if successful
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static boolean compare_date(Date date){
		// 只根据日期来比较
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_2);
		Date currentDay = getCurrentDate();
		try{
			Date dt1 = df.parse(getDateStr(date));
			Date dt2 = df.parse(getDateStr(currentDay));
			if (dt1.getTime() == dt2.getTime()){// 是当当天的记录
				return true;
			} else{// 不是当天的记录
				return false;
			}
		} catch (Exception exception){
			throw new TfrBusinessException("日期比较不正确");
		}
	}

	/**
	 * 获得两个时间的小时差.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the gap by hour
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午3:55:05
	 */
	public static int getGapByHour(Date startDate, Date endDate){
		if (startDate == null || endDate == null){//如果时间为空，直接返回0
			return 0;
		}
		//返回时间差的小时数
		return (int) ((endDate.getTime() - startDate.getTime())/(LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_1000));
	}

	/**
	 * 获得当前时间的前30分钟.
	 *
	 * @return the half hour time before
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午3:55:05
	 */
	public static Date getHalfHourTimeBefore(){
		try{
			//时间格式化
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			//转成数值型
			long time = date.getTime() - LoadConstants.SONAR_NUMBER_30 * LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_1000;
			Date date1 = new Date(time);
			String time_before = format.format(date1);
			return getDate(time_before);
		} catch (Exception e){//报错，打出日志，不抛异常，不影响业务流程
			LOGGER.error("日期转换不正确");
		}
		return null;

	}
	/**
	 * 获得当前时间的前一分.
	 *
	 * @return the half hour time before
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午3:55:05
	 */
	public static Date getOneMinuteTimeBefore(Date date){
		try{
			//时间格式化
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//转成数值型
			long time = date.getTime() - LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_1000;
			Date date1 = new Date(time);
			String time_before = format.format(date1);
			return getDate(time_before);
		} catch (Exception e){//报错，打出日志，不抛异常，不影响业务流程
			LOGGER.error("日期转换不正确");
		}
		return null;

	}
	
	/**
	 * 获得当前时间的前七天.
	 *
	 * @return the seven days before
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午3:55:05
	 */
	public static Date getSevenDaysBefore(){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			long time = date.getTime() - LoadConstants.SONAR_NUMBER_7*LoadConstants.SONAR_NUMBER_60*LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_1000;
			Date date1 = new Date(time);
			String time_before = format.format(date1);
			return getDate(time_before);
		} catch (Exception e){//报错，打出日志，不抛异常，不影响业务流程
			LOGGER.error("日期转换不正确");
		}
		return null;

	}
	
	/**
	 * 获得当前时间的前7天.
	 *
	 * @param date the date
	 * @return the seven day time before
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-27 下午3:55:05
	 */
	public static Date getSevenDayTimeBefore(Date date){
		try{
			if(date==null){//判断是否为空
				return null;
			}
			//转换日期格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time = date.getTime() - LoadConstants.SONAR_NUMBER_7*LoadConstants.SONAR_NUMBER_24*LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_60 * LoadConstants.SONAR_NUMBER_1000;
			Date date1 = new Date(time);
			String time_before = format.format(date1);
			return getDate(time_before);
		} catch (Exception e){//报错，打出日志，不抛异常，不影响业务流程
			LOGGER.error("日期转换不正确");
		}
		return null;

	}
	
	/**
	 * 
	 * 预计到达时间跟当前日期比较，如果大于当前日期，则准时到达，
	 * 返回true
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static boolean isOnTime(Date planArrivalTime) {
		// 只根据日期来比较
		if (null == planArrivalTime) {
			return false;
		}
		if (planArrivalTime.after(new Date())) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 预计到达时间跟到达时间比较，如果大于到达时间，则准时到达，
	 * 返回true
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static boolean isOnTime(Date operaTime,Date planArrivalTime) {
		// 只根据日期来比较
		if (null == planArrivalTime) {
			return false;
		}
		if(operaTime==null){
			return false;
		}
		if (planArrivalTime.after(operaTime)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 给日期增加天数
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static Date adddate(Date date, int addDay) {
		// 只根据日期来比较
		if (date == null) {
			return null;
		}
		fromCal.setTime(date);
		fromCal.add(Calendar.DAY_OF_YEAR, addDay);
		System.out.println(date);
		return fromCal.getTime();
	}
	/**
	 * 
	 * 给日期增加小时数
	 * 
	 * @author IBM-liubinbin
	 * @date 2012-11-8 下午2:44:08
	 */
	public static Date adddate(Date date, int addHour, int min) {
		// 只根据日期来比较
		if (date == null) {
			return null;
		}
		fromCal.setTime(date);
		fromCal.add(Calendar.HOUR_OF_DAY, addHour);
		fromCal.add(Calendar.MINUTE, min);
		System.out.println(date);
		return fromCal.getTime();
	}


}