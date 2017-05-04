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
 *  FILE PATH          :/AirSpaceAction.java
 * 
 *  FILE NAME          :AirSpaceAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-airfreight
 * PACKAGE NAME: com.deppon.foss.module.transfer.airfreight.server.action
 * FILE    NAME: AirSpaceAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirSpaceService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.exception.AirSpaceException;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirSpaceVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * 舱位管理相关操作
 * @author dp-pengzhen
 * @date 2012-10-13 上午11:30:52
 */
public class AirSpaceAction extends AbstractAction {
	//空运舱位action
	
	private static final long serialVersionUID = -4856197161786985126L;
	/**
	 * service
	 * 注入
	 */
	private IAirSpaceService airSpaceService;
	/**
	 * 前端
	 * 交互
	 * 数据
	 * Vo
	 */
	private AirSpaceVo vo = new AirSpaceVo();
	
	/**
	 * TODO 
	 * 保存录入的
	 * 舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-29 下午4:01:40
	 */
	@JSON
	public String addAirSpace(){
		//增加保存舱位
		try{
			//获取dto
			AirSpaceDto dto = vo.getDto();
			//设置当前用户信息
			fillCurrentUserInfo(dto);
			//调用service
			//新增新增舱位方法
			airSpaceService.addAirSpace(dto);
			//调用父类成功信息
			return super.returnSuccess();
		}catch(AirSpaceException e){
			//抛出异常
			//调用父类方法
			//返回错误信息
			return super.returnError(e);
			//成功
		}
	}
	
	/**
	 * 查询航空公司舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-29 下午3:39:22
	 */
	@JSON
	public String  queryAirSpace(){
		//查询舱位
		//异常处理
		try {
			AirSpaceDto dto = vo.getDto();
			//设置当前用户信息
			fillCurrentUserInfo(dto);
			//舱位信息
			vo.setAirSpaceDtoList(airSpaceService.queryAirSpace(dto,
					this.getLimit(), this.getStart()));
			//总舱位
			this.setTotalCount(airSpaceService.queryAirSpaceCount(dto));
			//返回成功信息
			return super.returnSuccess();
		} catch (AirSpaceException e) {
			//抛出异常
			//返回错误信息
			return super.returnError(e);
		}
	}
	/**
	 * 通过id查询舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午3:04:14
	 */
	@JSON
	public String queryAirSpaceById(){
		//通过id查询舱位信息
		//异常处理
		try{
			//调用service注入dto
			//通过id查询
			//从vo中取值
			AirSpaceDto dto = airSpaceService.queryAirSpaceById(vo.getDto().getId());
			//舱位信息明细实体
			//定义list
			//从dto获值
			List<AirSpaceDetailEntity> list = dto.getDetailEntityList();
			//遍历列表，按航班类型转换前台显示字段
			for (int i=0; i<list.size(); i++){ 
				//早班
				//与list中的对比
				if(AirfreightConstants.MORNING_FLIGHT.equals(list.get(i).getFlightType())){
					//判断类型是否为早班
					//设置早班
					//判断类型是否为即日达
					//设置即日达
					dto.setMorningShift(list.get(i).getSpaceTotal());
				//中班
					//与list中的对比
				}else if(AirfreightConstants.MIDDLE_FLIGHT.equals(list.get(i).getFlightType())){
					//判断类型是否为中班
					//设置中班
					//判断类型是否为次日达
					//设置次日达
					dto.setMiddleShift(list.get(i).getSpaceTotal());
				//晚班
					//与list中的对比
				}/*else if(AirfreightConstants.NIGHT_FLIGHT.equals(list.get(i).getFlightType())){
					//判断类型是否为晚班
					//设置晚班
					//判断类型是否为次日达
					//设置次日达
					dto.setNightShift(list.get(i).getSpaceTotal());
				//中转
					//与list中的对比
				}*/else if(AirfreightConstants.TRANSFER_FLIGHT.equals(list.get(i).getFlightType())){
					//判断类型是否为中转
					//设置中转
					//判断类型是否为航空普运
					//设置航空普运
					dto.setTransitShift(list.get(i).getSpaceTotal());
				}
			}
			//航班分为即日达、次日达、航空普运三种航班类型
			//设置dto
			vo.setDto(dto);
			//调用父类
			//返回成功信息
			return super.returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//返回错误信息
			return super.returnError(e);
		}
		
	}
	
	/**
	 * TODO 更新舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-8 下午3:03:54
	 */
	@JSON
	public String updateAirSpace(){
		//更改舱位
		//异常处理
		try{
			//舱位dto
			//获取dto
			AirSpaceDto dto = vo.getDto();
			//设置当前用户信息
			//dto中获取
			fillCurrentUserInfo(dto);
			//舱位service
			//更新舱位信息
			airSpaceService.updateAirSpace(dto);
			//返回成功信息
			return super.returnSuccess();	
		}catch(BusinessException e){
			//抛出异常
			//抛出错误信息
			return super.returnError(e);
		}
	}
	
	/**
	 * 删除航空公司舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-5 下午5:29:21
	 */
	@JSON
	public String deleteAirSpace(){
		//删除舱位信息
		//异常处理
		try{
			//调用service
			//删除舱位信息			
			airSpaceService.deleteAirSpace(vo.getDto());
			//返回成功信息
			return super.returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//返回错误信息
			return super.returnError(e);
		}
	}
	
	/**
	 * 获取当前日期的下一天
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-8 下午8:15:24
	 */
	@JSON
	public String getNextDay(){
		//取值
		//异常处理
		try{
			//定义nextday
			//nextday处理
			//在现在日期加1
			Date nextDay = DateUtils.addDayToDate(new Date(), 1);
			//设置下一天
			//设置值
			vo.setNextDay(nextDay);
			//返回成功信息
			return super.returnSuccess();
		}catch(BusinessException e){
			//抛出异常
			//调用父类
			//返回错误信息
			return super.returnError(e);
		}
	}
	
	/**
	 * 设置dto中的当前用户信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-7 上午11:46:23
	 */
	public void fillCurrentUserInfo(AirSpaceDto airSpaceDto){
		//从context中获取用户信息
		//为当前用户的信息
		CurrentInfo userInfo  = FossUserContext.getCurrentInfo();
		OrgAdministrativeInfoEntity currentDept = airSpaceService.getOrgAdministrative();
		//如果用户信息不为空则进行填充至dto,否则抛出异常
		if(userInfo != null){
			airSpaceDto.setCreateUserCode(userInfo.getEmpCode());//创建人编号
			//舱位信息创建人
			airSpaceDto.setCreateUserName(userInfo.getEmpName());//创建人姓名
			//舱位信息创建人
			airSpaceDto.setUpdateUserCode(userInfo.getEmpCode());//修改人编号
			//舱位信息修改人
			airSpaceDto.setUpdateUserName(userInfo.getEmpName());//修改人姓名
			//舱位信息修改人
			//如果当前部门为空运总调且查询条件为空，则设置查询当前部门
			if(currentDept.checkDoAirDispatch() 
					&& StringUtil.isBlank(airSpaceDto.getAssembleOrgCode())){
				//配载部门为空
				//设置当前配载部门编号
				airSpaceDto.setAssembleOrgCode(currentDept.getCode());//当前配载部门编号
				//设置配载部门名称
				airSpaceDto.setAssembleOrgName(currentDept.getName());//配载部门名称
			}else{
				//空
				//当前用户信息不为空
				//情况没为if情况
				//即不能获取当前配载部门
				//就不做任何处理
			}
		}else{
			//抛出异常
			//取得当前用户信息错误
			throw new TfrBusinessException(TfrBusinessException.GET_CURRENTUSERINFO_FAILURE);
		}
	}
	
	/**
	 * 设置 service注入.
	 *
	 * @param airSpaceService the new service注入
	 */
	public void setAirSpaceService(IAirSpaceService airSpaceService) {
		//设置舱位服务
		this.airSpaceService = airSpaceService;
		//成功
	}

	/**
	 * 获取 前端
	 * 交互数据Vo.
	 *
	 * @return the 前端交互数据Vo
	 */
	public AirSpaceVo getVo() {
		//get方法
		//返回vo
		return vo;
	}

	/**
	 * 设置 前端交互数据Vo.
	 *
	 * @param vo the new 前端交互数据Vo
	 */
	public void setVo(AirSpaceVo vo) {
		//设置vo
		this.vo = vo;
	}
}