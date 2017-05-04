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
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/AirTrackFlightAction.java
 * 
 *  FILE NAME          :AirTrackFlightAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirDispatchUtilService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTrackFlightService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTrackFlightVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 空运跟踪action
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午8:06:23
 */
public class AirTrackFlightAction extends AbstractAction {

	private static final long serialVersionUID = 8821401033062209193L;
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AirTrackFlightAction.class);
	/**
	 * 注入空运跟踪service
	 */
	private IAirTrackFlightService airTrackFlightService; 
	/**
	 * 空运跟踪vo
	 */
	private AirTrackFlightVo vo = new AirTrackFlightVo();
	
	/**
	 * 获取上级空运总调
	 */
	private IAirDispatchUtilService airDispatchUtilService;
	
	
	/**
	 * 设置 获取上级空运总调.
	 *
	 * @param airDispatchUtilService the new 获取上级空运总调
	 */
	public void setAirDispatchUtilService(
			IAirDispatchUtilService airDispatchUtilService) {
		this.airDispatchUtilService = airDispatchUtilService;
	}
	
	/**
	 * 根据条件查询航空正单
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-17 下午5:40:59
	 */
	public String queryAirTrackFlight(){
		//定义dto
		AirTrackFlightDto dto = new AirTrackFlightDto();
		//异常处理
		try {
			//转换日期
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//vo与dto的属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//取值赋值
			vo.setDtoList(airTrackFlightService.queryAirTrackFlight(dto, this.getStart(), this.getLimit()));
			//得到总数
			this.setTotalCount(airTrackFlightService.queryTrackFlightCount(dto));
		} catch (IllegalAccessException e) {
			//抛异常写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			//抛异常写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return super.returnError(e.getMessage());
		}
		return super.returnSuccess();
		//调用父类方法
		//返回成功信息
	}
	/**
	 * 起飞跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-20 下午5:24:19
	 */
	public String takeOffTrack(){
		//过程跟踪dto
		AirTrackFlightDto dto = new AirTrackFlightDto();
		try {
			//注册日期类型转换器
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//vo与dto的属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//设置用户信息
			fillCurrentUserInfo(dto);
			//调用起飞跟踪service
			airTrackFlightService.takeOffTrack(dto);
		} catch (IllegalAccessException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (BusinessException e){
			LOGGER.error(e.getMessage(), e);
			//返回错误信息
			return super.returnError(e);
		}
		//调用父类方法
		//返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 过程跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 上午11:50:45
	 */
	public String processTrack(){
		AirTrackFlightDto dto = new AirTrackFlightDto();
		try {
			//vo与dto的属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//设置用户信息
			fillCurrentUserInfo(dto);
			//调用过程跟踪service
			airTrackFlightService.processTrack(dto);
		} catch (IllegalAccessException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (BusinessException e){
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e);
		}
		//调用父类方法
		//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 到达跟踪
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 上午11:50:58
	 */
	public String arriveTrack(){
		AirTrackFlightDto dto = new AirTrackFlightDto();
		try {
			//注册日期类型转换器
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//vo与dto的属性拷贝
			BeanUtils.copyProperties(dto, vo);
			//设置用户信息
			fillCurrentUserInfo(dto);
			//调用到达跟踪service
			airTrackFlightService.arriveTrack(dto);
		} catch (IllegalAccessException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (InvocationTargetException e) {
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e.getMessage());
		} catch (BusinessException e){
			//抛异常并写入日志
			LOGGER.error(e.getMessage(), e);
			//调用父类方法
			//返回错误信息
			return super.returnError(e);
		}
		//调用父类方法
				//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 设置dto中的当前用户信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-21 上午11:46:23
	 */
	public void fillCurrentUserInfo(AirTrackFlightDto dto){
		//从context中获取用户信息
		CurrentInfo userInfo  = FossUserContext.getCurrentInfo();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = airDispatchUtilService.queryAirDispatchDept(FossUserContext.getCurrentDeptCode());
		//如果用户信息不为空则进行填充至dto,否则抛出异常
		if(userInfo != null){
			//填充部门编码
			dto.setCreateUserCode(userInfo.getEmpCode());
			//填充部门名称
			dto.setCreateUserName(userInfo.getEmpName());
			//填充当前部门编码
			dto.setCreateOrgCode(orgAdministrativeInfoEntity.getCode());
			//填充当前部门名称
			dto.setCreateOrgName(orgAdministrativeInfoEntity.getName());
		}else{
			//抛异常
			//取当前用户信息出错
			throw new TfrBusinessException(TfrBusinessException.GET_CURRENTUSERINFO_FAILURE);
		}
	}
	
	/**
	 * 获取 空运跟踪vo.
	 *
	 * @return the 空运跟踪vo
	 */
	public AirTrackFlightVo getVo() {
		//get方法
		return vo;
	}

	/**
	 * 设置 空运跟踪vo.
	 *
	 * @param vo the new 空运跟踪vo
	 */
	public void setVo(AirTrackFlightVo vo) {
		//set方法
		this.vo = vo;
	}

	/**
	 * 设置 注入空运跟踪service.
	 *
	 * @param airTrackFlightService the new 注入空运跟踪service
	 */
	public void setAirTrackFlightService(
			//空运跟踪service
			IAirTrackFlightService airTrackFlightService) {
		//注入service
		this.airTrackFlightService = airTrackFlightService;
	}
	
}