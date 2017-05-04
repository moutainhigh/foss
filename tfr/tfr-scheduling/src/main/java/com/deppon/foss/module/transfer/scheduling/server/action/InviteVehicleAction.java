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
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/InviteVehicleAction.java
 * 
 *  FILE NAME     :InviteVehicleAction.java
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
 * InviteVehicleAction.java
 * Copyright (c) 2004-2012, 上海德邦物流有限公司. All rights reserved.
 * Please contact 上海德邦物流有限公司 to get license distributed with this file.
 * 
 */
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.InviteVehicleVo;

/**
 * 外请约车Action.
 *
 * @author 104306-foss-wangLong
 * @date 2012-12-15 上午11:08:37
 */
public class InviteVehicleAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 72539694186080928L;
	
	/** The invite vehicle vo. */
	private InviteVehicleVo inviteVehicleVo = new InviteVehicleVo();
	
	/** The invite vehicle service. */
	private IInviteVehicleService inviteVehicleService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	//269701--lln--foss begin
	//调用综合接口 查询到达部门对应省市区code
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	//269701--lln--foss end
	/**获取配置参数 SERVICE*/
	private IConfigurationParamsService configurationParamsService;

	
	/**
	 * 加载配置参数
	 * 
	 * @return {@link java.lang.String}
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年6月15日 15:36:21
	 */
	@JSON
	public String inviteVehicleConfigurationParams() {
		// 最大重量综合模块配置参数："MAX_WEIGHT_VEHICLE";
		String maxWeightVehicle = null;
		// 提示重量综合模块配置参数："MIN_WEIGHT_VEHICLE";
		String minWeightVehicle = null;
		// 最大体积综合模块配置参数："MAX_VOLUME_VEHICLE";
		String maxVolumeVehicle = null;
		// 提示体积综合模块配置参数："MIN_VOLUME_VEHICLE";
		String minVolumeVehicle = null;
		
		// 设置 最大重量综合模块配置参数："MAX_WEIGHT_VEHICLE";
		try {
			maxWeightVehicle = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.BAS_PARM__MAX_WEIGHT_VEHICLE);
		} catch (RuntimeException e) {
			maxWeightVehicle = "99999999";
		}
		
		// 设置 提示重量综合模块配置参数："MIN_WEIGHT_VEHICLE";
		try {
			minWeightVehicle = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.BAS_PARM__MIN_WEIGHT_VEHICLE);
		} catch (RuntimeException e) {
			minWeightVehicle = "99999999";
		}

		// 设置 最大体积综合模块配置参数："MAX_VOLUME_VEHICLE";
		try {
			maxVolumeVehicle = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.BAS_PARM__MAX_VOLUME_VEHICLE);
		} catch (RuntimeException e) {
			maxVolumeVehicle = "99999.999";
		}
		// 设置 提示体积综合模块配置参数："MIN_VOLUME_VEHICLE";
		try {
			minVolumeVehicle = configurationParamsService
					.queryConfValueByCode(ConfigurationParamsConstants.BAS_PARM__MIN_VOLUME_VEHICLE);
		} catch (RuntimeException e) {
			minVolumeVehicle = "99999.999";
		}
				
		// 最大重量综合模块配置参数："MAX_WEIGHT_VEHICLE";
		if (StringUtils.isBlank(maxWeightVehicle)) {
			maxWeightVehicle = "99999999";
		}
		// 提示重量综合模块配置参数："MIN_WEIGHT_VEHICLE";
		if (StringUtils.isBlank(minWeightVehicle)) {
			minWeightVehicle = "99999999";
		}
		// 最大体积综合模块配置参数："MAX_VOLUME_VEHICLE";
		if (StringUtils.isBlank(maxVolumeVehicle)) {
			maxVolumeVehicle = "99999.999";
		}
		// 提示体积综合模块配置参数："MIN_VOLUME_VEHICLE";
		if (StringUtils.isBlank(minVolumeVehicle)) {
			minVolumeVehicle = "99999.999";
		}
		
		inviteVehicleVo.setMaxWeightVehicle(maxWeightVehicle);
		inviteVehicleVo.setMinWeightVehicle(minWeightVehicle);
		inviteVehicleVo.setMaxVolumeVehicle(maxVolumeVehicle);
		inviteVehicleVo.setMinVolumeVehicle(minVolumeVehicle);
		

		return returnSuccess();
	}
	
	/**
	 * 查询外请约车申请. 申请部门以及派车车队(包括此顶级车队及以下的班、组等)都可以查看到约车记录
	 * @return {@link java.lang.String}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @see IInviteVehicleService#queryInviteVehicleForPage
	 * @see IInviteVehicleService#queryCount
	 */
	@JSON
	public String queryInviteVehicleApply() {
		if(inviteVehicleVo.getInviteVehicleDto() ==null){
			throw new ParameterException("传入外请车信息为空"); 
		}
		List<InviteVehicleDto> inviteVehicleList = inviteVehicleService.queryInviteVehicleForPage(inviteVehicleVo.getInviteVehicleDto(), start, limit);
		Long count = inviteVehicleService.queryCount(inviteVehicleVo.getInviteVehicleDto());
	
		inviteVehicleVo.setInviteVehicleList(inviteVehicleList);
		setTotalCount(count);
		
		return returnSuccess();
	}
	
	/**
	 * 根据当前部门查询所属外场或营业部
	 * @author huyue
	 * @date 2013-5-3 上午11:14:00
	 */
	@JSON
	public String findOrderBelongDept() {
		// 尝试找登陆部门所属的顶级车队，如果能找到
		String belongTransforCenter = findTransforCenter(inviteVehicleVo.getBelongTransforCenter());
		if (null == belongTransforCenter) {
			throw new ParameterException("此登陆部门未找到对应的顶级车队，无法审核受理！");
		} else {
			inviteVehicleVo.setBelongTransforCenter(belongTransforCenter);
			return returnSuccess();
		}
	}
	
	/**
	 * 外请约车申请  提交按钮 </br>
	 * 1.如果id不为空  为修改操作</br>
	 * 2.其他为新增操作
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#saveInviteVehicleApply()
	 */
	@JSON
	public String saveInviteVehicleApply() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		try {
			inviteVehicleDto = inviteVehicleService.saveInviteVehicleApply(inviteVehicleDto);
			inviteVehicleVo.setInviteVehicleDto(inviteVehicleDto);
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 查询外请车明细.
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午3:32:19
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#saveInviteVehicleApply()
	 */
	@JSON
	public String queryInviteVehicleApplyDetail() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		InviteVehicleDto queryInviteVehicleDto = inviteVehicleService.queryInviteVehicleApplyDetail(inviteVehicleDto.getInviteNo());
		inviteVehicleVo.setInviteVehicleDto(queryInviteVehicleDto);
		
		return returnSuccess();
	}
	
	/**
	 * 根据用车部门& 用车类型 查询用车地址.
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 下午1:37:59
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#querySalesDepartmentAddressOrTransitAddress()
	 */
	@JSON
	public String queryDepartmentAddress() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		String address = null;
		try {
			address = inviteVehicleService.querySalesDepartmentAddressOrTransitAddress(inviteVehicleDto.getUseType(), inviteVehicleDto.getApplyOrgCode());
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		inviteVehicleVo.setUseVehicleAddress(address);
		return returnSuccess();	
	}
	/**
	 * 根据到达部门查询该到达部门对应的省市区code.
	 * @author 269701-foss-lln
	 * @date 2015-08-27 下午11:37:59
	 * @return {@link java.lang.String}
	 * @see IOrgAdministrativeInfoService#queryOrgAdministrativeInfoByCodeNoCache(String code)
	 */
	@JSON
	public String queryDepartmentCityCode() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		OrgAdministrativeInfoEntity arrivedDeptDetail = null;
		try{
			//调用综合接口
			arrivedDeptDetail = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(inviteVehicleDto.getArrivedDeptCode());
			if(arrivedDeptDetail!=null){
				//到达部门对应-省code
				inviteVehicleVo.getInviteVehicleDto().setProvinceCode(arrivedDeptDetail.getProvCode());
				//到达部门对应-省名称
				inviteVehicleVo.getInviteVehicleDto().setProvinceName(arrivedDeptDetail.getProvName());
				//到达部门对应-市code
				inviteVehicleVo.getInviteVehicleDto().setCityCode(arrivedDeptDetail.getCityCode());
				//到达部门对应-市名称
				inviteVehicleVo.getInviteVehicleDto().setCityName(arrivedDeptDetail.getCityName());
				//到达部门对应-区/县code
				inviteVehicleVo.getInviteVehicleDto().setAreaCode(arrivedDeptDetail.getAreaCode());
				//到达部门对应-区/县名称
				inviteVehicleVo.getInviteVehicleDto().setAreaName(arrivedDeptDetail.getAreaCode());
			}
		}catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();	
	}
	/**
	 * 撤销外请约车申请.
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:03:00
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#doUndoInviteVehicleApply(List)
	 */
	@JSON
	public String doUndoInviteVehicleApply() {
		List<String> inviteNoList = inviteVehicleVo.getInviteNoList();
		try {
			inviteVehicleService.doUndoInviteVehicleApply(inviteNoList);
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 报到.
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#doVerifyArriveInviteVehicleApply(InviteVehicleDto)
	 */
	@JSON
	public String doVerifyArriveInviteVehicleApply() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		try {
			//269701-2016/03/15--begin
			//0327版本上线 恢复FOSS整车约车 约车数据以及FOSS报到不同步给TPS
			//如果 询价编码为空--说明是历史数据（约车没有转移到TPS之前的，在FOSS进行约车的数据）以及是在FOSS进行整车约车和同城约车的数据
			if(StringUtils.isEmpty(inviteVehicleVo.getInviteVehicleDto().getInquiryNo())){
				inviteVehicleService.doVerifyArriveInviteVehicleApplyForWholecar(inviteVehicleDto);
			}else{
				inviteVehicleService.doVerifyArriveInviteVehicleApply(inviteVehicleDto);
			}
			
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * <pre>
	 * 必须确保某一辆车的状态"已报道"、"未使用"的外请车约车记录只能出现一条
	 * 车辆报到前，判断是否存在此车辆、"已报到"、"未使用"状态的，若存在，抛出异常，并且异常信息提示用户
	 *  存在以下外请车约车信息，已报道但未使用，请先对这些业务数据进行"释放"处理。
	 *    编号:001，用车部门：XXX
	 *    编号:002，用车部门：XXX
	 * </pre>
	 * @return: 
	 *
	 * @author foss-wuyingjie
	 * @date 2013-3-7 下午2:00:05
	 */
	public String checkVehicleArriveRule(){
		try {
			inviteVehicleService.checkVehicleArriveRule(inviteVehicleVo.getInviteVehicleDto().getInviteNo());
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 释放.
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @return {@link java.lang.String}
	 * @see IInviteVehicleService#doReleaseInviteVehicleApply(List)
	 */
	@JSON
	public String doReleaseInviteVehicleApply() {
		List<String> inviteNoList = inviteVehicleVo.getInviteNoList();
		try {
			inviteVehicleService.doReleaseInviteVehicleApply(inviteNoList);
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据用车部门查询费用承担部门，
	 * 如果未查询出结果就是用车部门。
	 * @author 310248
	 * @return inviteVehicleVo.comboList
	 */
	@JSON  
	public String queryBearFeesDept() {
	
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		List<OrgEntity>comboList = null;
		OrgEntity orgEntity = new OrgEntity();
		 try {
			 comboList = inviteVehicleService.queryBearFeesDept(inviteVehicleDto.getApplyOrgCode(),inviteVehicleDto.getDispatchTransDept()); 
			 if(comboList == null || comboList.size() == 0){
				 orgEntity.setApplyPath(inviteVehicleDto.getApplyOrgName());
				 orgEntity.setMinistryinformationCode(inviteVehicleDto.getApplyOrgCode());
				 List<OrgEntity>comboList1  = new ArrayList<OrgEntity>();
				 comboList1.add(orgEntity);
				 comboList= comboList1;
			 }
		} catch (InviteVehicleException e) {
			return returnError(e);
		}
		inviteVehicleVo.setComboList(comboList);
		return returnSuccess();
	}
	/**
	 * 查询合同线路
	 * @author 104306-foss-wangLong
	 * @date 2012-12-28 上午11:30:21
	 */
	@JSON
	public String queryBargainLine() {
		InviteVehicleDto inviteVehicleDto = inviteVehicleVo.getInviteVehicleDto();
		LineEntity lineEntity =  inviteVehicleService.queryBargainLine(inviteVehicleDto.getInviteNo());
		inviteVehicleVo.setLineEntity(lineEntity);
		return returnSuccess(); 
	}
	
	public String findTransforCenter(String org){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org, bizTypes);
		if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
			throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场 或 营业部");
		}else{
			return parentOrg.getCode();
		}
	}
	
	/**
	 * 获得inviteVehicleVo.
	 * @return the inviteVehicleVo
	 */
	public InviteVehicleVo getInviteVehicleVo() {
		return inviteVehicleVo;
	}
	
	/**
	 * 设置inviteVehicleVo.
	 * @param inviteVehicleVo the inviteVehicleVo to set
	 */
	public void setInviteVehicleVo(InviteVehicleVo inviteVehicleVo) {
		this.inviteVehicleVo = inviteVehicleVo;
	}
	
	/**
	 * 设置inviteVehicleService.
	 * @param inviteVehicleService the inviteVehicleService to set
	 */
	public void setInviteVehicleService(IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}

	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
}