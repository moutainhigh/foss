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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/StowagePlansAction.java
 * 
 *  FILE NAME     :StowagePlansAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 2013年7月30日 16:35:53
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.StowagePlansEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.StowagePlansVO;

/**
 * 配载方案 配置
 * 
 * @author 134019-foss-yuyongxiang
 * @date 2013-7-31 下午4:06:29
 */
@SuppressWarnings("serial")
public class StowagePlansAction extends AbstractAction {
	
	@SuppressWarnings("unused")
	private static long serialVersionUID = 4087294908525422087L;
	/** 日志管理器 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StowagePlansAction.class);

	/**
	 * 配载方案配置
	 */
	private IStowagePlansService stowagePlansService;	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 部门 复杂查询 service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 车队 Service接口
	 */
	private IMotorcadeService motorcadeService;
	/**
	 *系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	

	private StowagePlansVO vo = new StowagePlansVO();

	/**
	 * 查询 配载方案配置项list
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-7-31 下午4:16:43
	 * @return
	 */
	public String selectStowagePlansList() {
		try {
			//判定查询条件是否正确，不正确则抛出异常
			if(null == vo.getStowagePlansDto())
			{
				throw new TfrBusinessException("查询条件不正确!");
			}
			
			//这地方由于 未vo.getStowagePlansDto() 做判定直接赋值的所以这边需要确保在调用this.findTransforCenter之前这个对象是不为空的
			//把当前员工所属的外场赋值到查询条件里面
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgName()赋值
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgCode()赋值 所以这边的返回值用不到
			this.findTransforCenter(FossUserContext.getCurrentDept().getCode());
			
			// list
			List<StowagePlansEntity> stowagePlansList = stowagePlansService
					.selectStowagePlansList(vo.getStowagePlansDto(),
							super.start, super.limit);
			// set list
			vo.setStowagePlansList(stowagePlansList);
			// 赋值list总条数
			super.totalCount = stowagePlansService
					.selectStowagePlansListCount(vo.getStowagePlansDto());
		} catch (TfrBusinessException e) {
			// return Exception
			return super.returnError(e);
		}

		this.stowagePlansDefaultLength();
		return super.returnSuccess();
	}
	/**
	 * 新增配载方案
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午5:25:10
	 * @return
	 */
	public String insertStowagePlans(){
		try{
			if(null == vo.getStowagePlansDto() || null == vo.getStowagePlansDetailList()){
				throw new TfrBusinessException("输入的参数不正确,请重新输入!");
			}
			
			//这地方由于 未vo.getStowagePlansDto() 做判定直接赋值的所以这边需要确保在调用this.findTransforCenter之前这个对象是不为空的
			//把当前员工所属的外场赋值到查询条件里面
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgName()赋值
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgCode()赋值 所以这边的返回值用不到
			this.findTransforCenter(FossUserContext.getCurrentDept().getCode());
			
			stowagePlansService.insertStowagePlans(vo.getStowagePlansDto(),vo.getStowagePlansDetailList());
			
		}catch (TfrBusinessException e) {
			return super.returnError(e);
		}
		
		return super.returnSuccess();
	}
	
	/**
	 * 更新配载方案
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午6:33:20
	 * @return
	 */
	public String updateStowagePlans(){
		try{
			if(null == vo.getStowagePlansDto() || null == vo.getStowagePlansDetailList() || StringUtils.isBlank(vo.getStowagePlansDto().getId())){
				throw new TfrBusinessException("输入的参数不正确,请重新输入!");
			}
			
			//这地方由于 未vo.getStowagePlansDto() 做判定直接赋值的所以这边需要确保在调用this.findTransforCenter之前这个对象是不为空的
			//把当前员工所属的外场赋值到查询条件里面
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgName()赋值
			//这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgCode()赋值 所以这边的返回值用不到
			this.findTransforCenter(FossUserContext.getCurrentDept().getCode());
			
			stowagePlansService.updateStowagePlans(vo.getStowagePlansDto(),vo.getStowagePlansDetailList());
			
		}catch (TfrBusinessException e) {
			return super.returnError(e);
		}
		
		return super.returnSuccess();
	}
	
	
	public String selectStowagePlansDetail(){
		try{
			if(null == vo.getStowagePlansDto() || StringUtils.isBlank(vo.getStowagePlansDto().getStowagePlansId())){
				throw new TfrBusinessException("输入的参数不正确,请重新输入!");
			}
			vo.setStowagePlansDetailList(stowagePlansService.selectStowagePlansDetailList(vo.getStowagePlansDto()));
		}catch (TfrBusinessException e) {
			return super.returnError(e);
		}
		return super.returnSuccess();
	}
	
	/**
	 * 删除 配载方案
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-1 下午7:09:27
	 * @return
	 */
	public String deleteStowagePlans(){
		try{
			if(null == vo.getStowagePlansDto() || StringUtils.isBlank(vo.getStowagePlansDto().getId())){
				throw new TfrBusinessException("输入的参数不正确,请重新输入!");
			}
			stowagePlansService.deleteStowagePlansWithId(vo.getStowagePlansDto());
		}catch (TfrBusinessException e) {
			return super.returnError(e);
		}
		return super.returnSuccess();
	}
	
	/**
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-16 下午5:16:04
	 * @return
	 */
	public String stowagePlansDefaultLength(){
		/**
		 * 查询货量统计配载方案最大可配限制
			public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH_TEMP = "STOWAGE_PLANS_DEFAULT_LENGTH_TEMP";
		 * 配载方案配置条数限制
			public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH = "STOWAGE_PLANS_DEFAULT_LENGTH";
		 */
		try{
			//配载方案配置条数限制
		ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH, FossUserContext.getCurrentDeptCode());
		//默认值
		if(null == entityStart){
			vo.setStowagePlanDefault("3");
		}else{
			vo.setStowagePlanDefault(entityStart.getConfValue());
		}
		
		}catch (TfrBusinessException e) {
			//设置默认值
			vo.setStowagePlanDefault("3");
			LOGGER.error("未取出配置参数直接设置默认值->部门: " +FossUserContext.getCurrentDeptName() +" : "+FossUserContext.getCurrentDeptCode());
		}

		return super.returnSuccess();
	}
	/**
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-8-16 下午5:15:59
	 * @return
	 */
	public String stowagePlansDefaultTempLength(){
		/**
		 * 查询货量统计配载方案最大可配限制
			public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH_TEMP = "STOWAGE_PLANS_DEFAULT_LENGTH_TEMP";
		 * 配载方案配置条数限制
			public static final String TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH = "STOWAGE_PLANS_DEFAULT_LENGTH";
		 */
		try{
			//配载方案配置条数限制
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__STOWAGE_PLANS_DEFAULT_LENGTH_TEMP, FossUserContext.getCurrentDeptCode());
			//默认值
			if(null == entityStart){
				vo.setStowagePlanDefault("3");
			}else{
				vo.setStowagePlanDefault(entityStart.getConfValue());
			}
			
		}catch (TfrBusinessException e) {
			//设置默认值
			vo.setStowagePlanDefault("3");
			Log.error("未取出配置参数直接设置默认值->部门: " +FossUserContext.getCurrentDeptName() +" : "+FossUserContext.getCurrentDeptCode());
		}
		
		return super.returnSuccess();
	}
	
	
	/**************************** private *******************************/
	 
	/**
	 * 根据当前的员工的CODE查找所对应的外场
	 * 
	 *这地方由于 未vo.getStowagePlansDto() 做判定直接赋值的所以这边需要确保在调用this.findTransforCenter之前这个对象是不为空的
	 *把当前员工所属的外场赋值到查询条件里面
	 *这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgName()赋值
	 *这个方法里面直接调用vo.getStowagePlansDto().setOrigOrgCode()赋值 所以这边的返回值用不到
	 * @param org
	 * @return
	 */
	private String findTransforCenter(String org){
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity parentOrg=null;
		
		//查找当前员工的部门实体信息
		OrgAdministrativeInfoEntity orgs = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(org);
		//如果当前员工属于调度组
		if(null != orgs && StringUtils.equals("Y", orgs.getDispatchTeam())){
			//根据当前员工所在部门CODE查找顶级车队
			parentOrg = orgAdministrativeInfoComplexService.getTopFleetByCode(orgs.getCode());
			if(null != parentOrg && StringUtils.isNotEmpty(parentOrg.getCode())){
				//根据顶级车队CODE查找所对应的外场
				MotorcadeEntity motorcade = motorcadeService.queryMotorcadeByCode(parentOrg.getCode());
				if(null != motorcade && StringUtils.isNotEmpty(motorcade.getTransferCenter()))
				//赋值外场CODE 和 name
				vo.getStowagePlansDto().setOrigOrgName(motorcade.getTransferCenterName());
				vo.getStowagePlansDto().setOrigOrgCode(motorcade.getTransferCenter());
				return motorcade.getTransferCenter();
			}
				throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场");
		}
		//当前员工不属于调度组的的时候
		parentOrg = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(org, bizTypes);
		if(null == parentOrg || StringUtils.isEmpty(parentOrg.getCode())){
			throw new TfrBusinessException("未找到此部门：" + org + " 所对应的的上级 外场");
		}else{
			//赋值外场CODE 和 name
			vo.getStowagePlansDto().setOrigOrgName(parentOrg.getName());
			vo.getStowagePlansDto().setOrigOrgCode( parentOrg.getCode());
			return parentOrg.getCode();
		}
	}
	
	/**************************** getter and setter *******************************/
	public StowagePlansVO getVo() {
		return vo;
	}

	public void setVo(StowagePlansVO vo) {
		this.vo = vo;
	}

	/**
	 * @param stowagePlansService
	 *            the stowagePlansService to set
	 */
	public void setStowagePlansService(IStowagePlansService stowagePlansService) {
		this.stowagePlansService = stowagePlansService;
	}

	/**
	 * @param serialversionuid the serialversionuid to set
	 */
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param motorcadeService the motorcadeService to set
	 */
	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

}