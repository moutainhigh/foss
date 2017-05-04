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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedVehicleService.java
 * 
 * FILE NAME        	: LeasedVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ecs.inteface.domain.LeasedTruckInfo;
import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBindingLeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.IClearanceErrorReportingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedTruckTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistAuditException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车辆（厢式车、挂车、拖头）”的数据库对应数据访问Service接口实现类：SUC-104、SUC-608、SUC-44、SUC-103
 * <p>需要版本控制：已实现</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 下午4:30:15</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 下午4:30:15
 * @since
 * @version
 */
public class LeasedVehicleService implements ILeasedVehicleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LeasedVehicleService.class);
    
    //同步给ECS的操作类型
	private static final String OPTION_ADD = "1";

	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";
    
    //"外请车厢式车"接口DAO
    private ILeasedVehicleDao leasedVehicleDao;
    //"外请白名单"审核DAO
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;
    //"车辆车型"Service接口
    private ILeasedVehicleTypeService leasedVehicleTypeService;
    
    //"外请车白名单"Service
    private ILeasedVehicleWhitelistService leasedVehicleWhitelistService;
    
    //公司车service接口 wp_078816_20140516_FOSS20140617
    private IOwnVehicleService  ownVehicleService;
    //快递车辆service
    private IExpressVehiclesService  expressVehiclesService;

    //同步信息到ECS的service
    private ISyncLeasedInformationService syncLeasedInformationService;

    private ISyncLeasedTruckTeamService leasedTruckTeamService;
    
    private IClearanceErrorReportingService clearanceErrorReportingService;
    
    
    public void setClearanceErrorReportingService(
			IClearanceErrorReportingService clearanceErrorReportingService) {
		this.clearanceErrorReportingService = clearanceErrorReportingService;
	}

	/**
     * 部门 复杂查询，查询顶级车队
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    /**
     * 员工Service
     */
    private IEmployeeService employeeService;
    
    /**
     * 数据字典Service接口
     */
   // private IDataDictionaryValueService dataDictionaryValueService;
    /**
     * 组织service
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    private IBindingLeasedVehicleDao bindingLeasedVehicleDao;
	
	public void setBindingLeasedVehicleDao(
			IBindingLeasedVehicleDao bindingLeasedVehicleDao) {
		this.bindingLeasedVehicleDao = bindingLeasedVehicleDao;
	}
    
    /**
     * @param leasedWhitelistAuditDao the leasedWhitelistAuditDao to set
     */
    public void setLeasedWhitelistAuditDao(
    	ILeasedWhitelistAuditDao leasedWhitelistAuditDao) {
        this.leasedWhitelistAuditDao = leasedWhitelistAuditDao;
    }
    
	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setLeasedTruckTeamService(
			ISyncLeasedTruckTeamService leasedTruckTeamService) {
		this.leasedTruckTeamService = leasedTruckTeamService;
	}

	/**
	 * 公司车service接口 wp_078816_20140516_FOSS20140617
	 * @param ownVehicleService the ownVehicleService to set
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}
	
	/**
     * 部门 复杂查询，查询顶级车队
     */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	

	/**
	 * 同步信息到ECS的service
	 * @param syncLeasedInformationService
	 */
	public void setSyncLeasedInformationService(
			ISyncLeasedInformationService syncLeasedInformationService) {
		this.syncLeasedInformationService = syncLeasedInformationService;
	}

	/**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:23:16
     * @param leasedTruckParameter “外请车（厢式车、挂车、拖头）”实体
     * @param vehicleType “车辆类型（厢式车、挂车、拖头）”的值代码
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#addLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @Transactional
    public int addLeasedVehicle(LeasedTruckEntity leasedTruckParameter,
	    String createUser, boolean ignoreNull) throws LeasedVehicleException {
	if (null == leasedTruckParameter) {
	    throw new LeasedVehicleException(LeasedVehicleException.PARAMETER_ISNULL, "外请车信息为空");
	}
	if (StringUtils.isBlank(leasedTruckParameter.getVehicleType())) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_TYPE_ISNULL, "未选择外请车类型");
	}
	if (StringUtils.isBlank(leasedTruckParameter.getVehicleNo())) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "外请车车牌号为空");
	}else{
		leasedTruckParameter.setVehicleNo(StringUtils.trim(leasedTruckParameter.getVehicleNo()));
	}
	if (StringUtils.isBlank(createUser)) {
	    throw new LeasedVehicleException(LeasedVehicleException.CREATER_ISNULL, "创建人为空");
	}
	updateLeasedVehicleWhitelistsByVehicleNo(leasedTruckParameter,createUser);
	//验证对应身份证号"外请车"是否已经存在
	LeasedTruckEntity tempLeasedTruckParameter = new LeasedTruckEntity();
	tempLeasedTruckParameter.setVehicleNo(leasedTruckParameter.getVehicleNo());
	LeasedTruckEntity leasedTruck = queryLeasedVehicleBySelectiveCondition(tempLeasedTruckParameter);
	if (null != leasedTruck) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_IS_NOT_NULL, "外请车已经存在");
	}else{
	    leasedTruck = leasedTruckParameter;
	    leasedTruck.setCreateUser(createUser);
	    //默认新增的外请车为"不可用"状态
	    leasedTruck.setStatus(FossConstants.INACTIVE);			
		if (ignoreNull) {
				int result = leasedVehicleDao.addLeasedVehicleBySelective(leasedTruck);

				// 同步信息到ECS
				if (result > 0) {
					LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_ADD);
					this.syncLeasedVehicleToECS(addInfo);
				}
			} else {
				int result = leasedVehicleDao.addLeasedVehicle(leasedTruck);
				
				// 同步信息到ECS
				if (result > 0) {
					LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_ADD);
					this.syncLeasedVehicleToECS(addInfo);
				}
			}
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录ID集合作废（逻辑删除）多条（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:23:15
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#deleteLeasedVehicle(java.lang.String)
     */
    @Override
    @Transactional
    public int deleteLeasedVehicle(List<String> ids, CurrentInfo modifyUser) throws LeasedVehicleException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new LeasedVehicleException(LeasedVehicleException.ID_ISNULL, "ID 为空");
	}
	if (null ==modifyUser) {
	    throw new LeasedVehicleException(LeasedVehicleException.MODIFIER_ISNULL, "修改人为空");
	}
	
	//待优化实现
		for (String id : ids) {
			// 验证对应"车牌号外请车"是否已经存在
			LeasedTruckEntity leasedTruck = leasedVehicleDao
					.queryLeasedVehicleBySelective(id);
			if (null == leasedTruck) {
				throw new LeasedVehicleException(
						LeasedVehicleException.VEHICLE_ISNULL, "外请车不存在");
			} else {
				// 检测对应"外请车白名单"是否存在，存在则同步修改为"不可用"，记录"禁用"
				WhitelistAuditEntity whitelistAuditParameter = new WhitelistAuditEntity();
				whitelistAuditParameter
						.setVehicleNo(leasedTruck.getVehicleNo());
				WhitelistAuditEntity whitelistAudit = leasedVehicleWhitelistService
						.queryLeasedVehicleWhitelistsBySelectiveCondition(whitelistAuditParameter);
				boolean flage = true;// 默认 “外请挂车”、“外请拖头”、“外请厢式车” 可以操作“作废”功能
				if (null != whitelistAudit) {
					flage = updateWhitelistAuditEntity(whitelistAudit, flage);

				} else {
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("提示：车辆没有对应白名单，不需要同步修改为（状态不可用、记录禁用）");
					}
				}
				/**
				 * “外请挂车”、“外请拖头”、“外请厢式车”在“入库”后，不能操作“作废”功能
				 */
				if (flage) {
					// 逻辑删除，直接“停用”
					// 设置当前修改人，修改部门
					leasedTruck.setModifyUser(modifyUser.getEmpCode());
					leasedTruck.setModifyUserDept(modifyUser
							.getCurrentDeptCode());
					leasedTruck.setActive(FossConstants.INACTIVE);
					leasedTruck.setStatus(FossConstants.INACTIVE);
					int result = leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruck);
					
					// 同步信息到ECS
					if (result > 0) {
					LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_DELETE);
					this.syncLeasedVehicleToECS(addInfo);
					}
				} else {
					throw new LeasedVehicleException(
							LeasedVehicleException.IS_NOT_DELETE,
							"该信息已入库，无法作废！");
				}

			}
		}
	return FossConstants.SUCCESS;
    }

	private boolean updateWhitelistAuditEntity(
			WhitelistAuditEntity whitelistAudit, boolean flage) {
		/**
		 * “外请挂车”、“外请拖头”、“外请厢式车”在“入库”后(即 不是申请入库的状态)，不能操作“作废”功能
		 */
		if(StringUtils.equals(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY, whitelistAudit.getStatus())){
			    whitelistAudit.setCurrentApplication(null);
			    whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
			    whitelistAudit.setActive(FossConstants.INACTIVE);
			    leasedVehicleWhitelistService.updateLeasedVehicleWhitelists(whitelistAudit, false);
		}else{
			flage=false;
			Set<String> roleIds=FossUserContext.getCurrentUser().getRoleids();
			//判断是否是“运输管理员”角色 如果是执行作废操作
			for (String roleId : roleIds) {
				//TRANS_ASSISTANT“运输管理员”角色编码
				if("TRANS_ASSISTANT".equals(roleId)){
					flage=true;
					whitelistAudit.setCurrentApplication(null);
				    whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
				    whitelistAudit.setActive(FossConstants.INACTIVE);
				    leasedVehicleWhitelistService.updateLeasedVehicleWhitelists(whitelistAudit, false);
				}
			}	
		}
		return flage;
	}
    
    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:23:21
     * @param leasedTruckParameter “外请车（厢式车、挂车、拖头）”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#updateLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @Transactional
    public int updateLeasedVehicle(LeasedTruckEntity leasedTruckParameter,
	    CurrentInfo modifyUser, boolean ignoreNull) throws LeasedVehicleException {
	if (null == leasedTruckParameter) {
	    throw new LeasedVehicleException(LeasedVehicleException.PARAMETER_ISNULL, "外请车信息为空");
	}
	if (StringUtils.isBlank(leasedTruckParameter.getId())) {
	    throw new LeasedVehicleException(LeasedVehicleException.ID_ISNULL, "ID 为空");
	}
	if (StringUtils.isBlank(leasedTruckParameter.getVehicleNo())) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "外请车车牌号为空");
	}
	if (StringUtils.isBlank(modifyUser.getEmpCode())) {
	    throw new LeasedVehicleException(LeasedVehicleException.MODIFIER_ISNULL, "修改人为空");
	}
	updateLeasedVehicleWhitelistsByVehicleNo(leasedTruckParameter,modifyUser.getEmpCode());
	//验证对应"车牌号外请车"是否已经存在
	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
	leasedTruck.setVehicleNo(leasedTruckParameter.getVehicleNo());
	leasedTruck = queryLeasedVehicleBySelectiveCondition(leasedTruck);
	if (null == leasedTruck) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_ISNULL, "外请车不存在");
	}else{
		BigDecimal selfVolume=leasedTruck.getSelfVolume();
		 //1、禁用当前实体记录 
	    leasedTruck.setModifyUser(modifyUser.getEmpCode());
	    leasedTruck.setModifyUserDept(modifyUser.getCurrentDeptCode());
	    leasedTruck.setActive(FossConstants.INACTIVE);
	    //leasedTruck.setStatus(FossConstants.INACTIVE);
	    int result = leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruck);
	    
	    // 同步信息到ECS
		if (result > 0) {
			LeasedTruckInfo updateInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_DELETE);
			this.syncLeasedVehicleToECS(updateInfo);
		}
	    //1、	操作人修改“净空”信息后，系统不立刻生效，需要审核人（请车员）在“车辆待审核白名单”界面审核
	    if(StringUtils.equals(leasedTruckParameter.getIsNotUpdateSelfVolume(), FossConstants.YES)){
	    	WhitelistAuditEntity resultWhitelistAudit, whitelistAudit = new WhitelistAuditEntity();
	    	// 外请车白名单信息
			whitelistAudit.setVehicleNo(leasedTruck.getVehicleNo()); // 车牌号
			resultWhitelistAudit = leasedVehicleWhitelistService
					.queryLeasedVehicleWhitelistsBySelectiveCondition(whitelistAudit);
			if (null == resultWhitelistAudit) {
				throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_WHITELIST_ISNULL, "外请车白名单不存在");
			}else{
				//禁用当前白名单记录
				resultWhitelistAudit.setActive(FossConstants.INACTIVE);
				resultWhitelistAudit.setModifyUser(modifyUser.getEmpCode());
				resultWhitelistAudit.setModifyDate(new Date());
				result = leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(resultWhitelistAudit);
				
				//同步白名单信息到ECS
				if(result > 0){
					WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(resultWhitelistAudit, OPTION_DELETE);
					this.syncWhiteListAudiToECS(updateInfo);
				}
				
				//新增一条白名单记录
				
					// 司机姓名
				resultWhitelistAudit.setDriverName(leasedTruck.getLeasedDriverName());
					// 司机电话
				resultWhitelistAudit.setDriverPhone(leasedTruck.getContactPhone());
					// 司机身份证
				resultWhitelistAudit.setDriverIdCard(leasedTruck.getDrivingLicense());
					// 从业资格证
				//resultWhitelistAudit.setQualification(leasedTruck.get);
					// 驾驶证
				//resultWhitelistAudit.setDriverLicense();
					// 车牌号
				resultWhitelistAudit.setVehicleNo(leasedTruck.getVehicleNo());
					// 车辆类型
				resultWhitelistAudit.setVehicleType(leasedTruck.getVehicleType());
					// 行驶证
				resultWhitelistAudit.setDrivingLicense(leasedTruck.getDrivingLicense());
					// 行驶证到期日?
				resultWhitelistAudit.setEndTimeDrivingLic(leasedTruck.getEndTimeDrivingLic());
					// 营运证
				resultWhitelistAudit.setOperatingLic(leasedTruck.getOperatingLic());
					// 营运证到期日期
				resultWhitelistAudit.setEndTimeOperatingLic(leasedTruck.getEndTimeOperatingLic());
					// 保险卡
				resultWhitelistAudit.setInsureCard(leasedTruck.getInsureCard());
					// 车主姓名
				resultWhitelistAudit.setContact(leasedTruck.getContact());
					// 车主联系方式
				resultWhitelistAudit.setContactPhone(leasedTruck.getContactPhone());
				//设置 "外请车"的当前申请为"申请修改净空"
				resultWhitelistAudit.setCurrentApplication(DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME);
				//设置 外请车白名单"的白名单状态为"未审核"
				resultWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY_ING);
					// 申请人
				resultWhitelistAudit.setApplyUser(modifyUser.getEmpCode());
					// 申请时间
				resultWhitelistAudit.setApplyTime(new Date());
					// 审核人
					// 审核时间
					// 审核备注
					// 创建时间
					// 修改时间
					// 1 是否启用
					// 创建人
				resultWhitelistAudit.setCreateUser(modifyUser.getEmpCode());
					// 修改人
					// 白名单类型
				//强制设置白名单类型为“外请车”
				resultWhitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
					// 申请备注
				resultWhitelistAudit.setApplyNotes("申请修改净空");				
				result = leasedWhitelistAuditDao.addLeasedWhitelistAudit(resultWhitelistAudit);
				
				//同步白名单信息到ECS
				if(result > 0){
					WhiteListAuditInfo addInfo = this.toWhiteListAuditInfo(resultWhitelistAudit, OPTION_ADD);
					this.syncWhiteListAudiToECS(addInfo);
				}
				
				//2、产生一条最新启用记录
			    leasedTruckParameter.setCreateUser(modifyUser.getEmpCode());
			    leasedTruckParameter.setCreateUserDept(modifyUser.getCurrentDeptCode());
			    //新增时修改人的信息置空
			    leasedTruckParameter.setModifyUserDept(null);
			    //修改车辆 净空 表示 车辆不可用
			    leasedTruckParameter.setStatus(FossConstants.INACTIVE);
			    leasedTruckParameter.setModifyUser(null);
			    leasedTruckParameter.setSelfVolume(selfVolume);
			    result = leasedVehicleDao.addLeasedVehicle(leasedTruckParameter);
				// 同步信息到ECS
				if (result > 0) {
					LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruckParameter, OPTION_ADD);
					this.syncLeasedVehicleToECS(addInfo);
				}
			}	    		    	
	    }else{
	    //2、产生一条最新启用记录
	    leasedTruckParameter.setCreateUser(modifyUser.getEmpCode());
	    leasedTruckParameter.setCreateUserDept(modifyUser.getCurrentDeptCode());
	    //新增时修改人的信息置空
	    leasedTruckParameter.setModifyUserDept(null);
	    leasedTruckParameter.setModifyUser(null);
	    leasedTruckParameter.setSelfVolume(selfVolume);
	    result = leasedVehicleDao.addLeasedVehicle(leasedTruckParameter);
			// 同步信息到ECS
			if (result > 0) {
				LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruckParameter, OPTION_ADD);
				this.syncLeasedVehicleToECS(addInfo);
			}
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>修改净空</p> 
     * @author 232607 
     * @date 2015-10-12 下午9:16:39
     * @param entity
     * @see
     */
    @Override
    public void updateSelfVolume(LeasedTruckEntity entity){
    	if (entity==null) {
    	    throw new BusinessException("外请车信息不存在");
    	}
    	if (StringUtils.isBlank(entity.getVehicleNo())) {
    	    throw new BusinessException("车牌号不存在");
    	}
    	if (entity.getSelfVolume()==null) {
    	    throw new BusinessException("净空不存在");
    	}
    	//查出原车辆信息
    	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
    	leasedTruck.setVehicleNo(entity.getVehicleNo());
    	leasedTruck = queryLeasedVehicleBySelectiveCondition(leasedTruck);
    	Map<String, Object> entityMap = new HashMap<String, Object>();
    	//作废车辆信息
    	CurrentInfo modifyUser = FossUserContext.getCurrentInfo();
	    leasedTruck.setActive(FossConstants.INACTIVE);
	    leasedTruck.setModifyUser(modifyUser.getEmpCode());
	    leasedTruck.setModifyUserDept(modifyUser.getCurrentDeptCode());
	    int result = leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruck);
	    //上报差错信息
	    boolean flag = false;
	    //测量人员只能同时为空或同时不为空，有初次测量人员信息才上报差错
	    if(!StringUtils.isBlank(leasedTruck.getMeasureEmployeeCode()) && result>0){
	    	LeasedTruckEntity leasedTruck1 = new LeasedTruckEntity();
	    	leasedTruck1=(LeasedTruckEntity) leasedTruck.clone();
	    	//修改前的对象
			entityMap.put("old", leasedTruck1);
			//操作人信息
		 	entityMap.put("userInfo", modifyUser);
		 	flag=true;
	    }
		// 同步信息到ECS
		if (result > 0) {
			LeasedTruckInfo updateInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_DELETE);
			this.syncLeasedVehicleToECS(updateInfo);
		}
	    //新增车辆信息
	    leasedTruck.setCreateUser(modifyUser.getEmpCode());
	    leasedTruck.setCreateUserDept(modifyUser.getCurrentDeptCode());
	    //修改车辆净空后，车辆状态变为不可用，注意这里是车辆状态不是车辆白名单状态
	    leasedTruck.setStatus(FossConstants.INACTIVE);
	    leasedTruck.setModifyUser(null);
	    leasedTruck.setModifyUserDept(null);
	    leasedTruck.setSelfVolume(entity.getSelfVolume());
	    leasedTruck.setMeasureEmployeeCode(entity.getMeasureEmployeeCode());
	    leasedTruck.setMeasureManagerCode(entity.getMeasureManagerCode());
	    leasedTruck.setMeasureSeniorManagerCode(entity.getMeasureSeniorManagerCode());
	    leasedTruck.setMeasureEmployeeName(entity.getMeasureEmployeeName());
	    leasedTruck.setMeasureManagerName(entity.getMeasureManagerName());
	    leasedTruck.setMeasureSeniorManagerName(entity.getMeasureSeniorManagerName());
	    result = leasedVehicleDao.addLeasedVehicle(leasedTruck);
	    //上报差错信息
	    if(flag && result>0){
	    	//修改后的对象
			entityMap.put("new", leasedTruck);
			clearanceErrorReportingService.syncClearanceError(entityMap);
			
	    }
		// 同步信息到ECS
		if (result > 0) {
			LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruck, OPTION_ADD);
			this.syncLeasedVehicleToECS(addInfo);
		}
	    //查出原白名单
    	WhitelistAuditEntity resultWhitelistAudit, whitelistAudit = new WhitelistAuditEntity();
		whitelistAudit.setVehicleNo(leasedTruck.getVehicleNo()); // 车牌号
		resultWhitelistAudit = leasedVehicleWhitelistService.queryLeasedVehicleWhitelistsBySelectiveCondition(whitelistAudit);
		if (null == resultWhitelistAudit) {
			throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_WHITELIST_ISNULL, "外请车白名单不存在");
		}
		//禁用白名单
		resultWhitelistAudit.setActive(FossConstants.INACTIVE);
		resultWhitelistAudit.setModifyUser(modifyUser.getEmpCode());
		resultWhitelistAudit.setModifyDate(new Date());
		result = leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(resultWhitelistAudit);
		
		//同步白名单信息到ECS
		if(result > 0){
			WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(resultWhitelistAudit, OPTION_DELETE);
			this.syncWhiteListAudiToECS(updateInfo);
		}
		//新增白名单
		//设置 "外请车"的当前申请为"申请修改净空"
		resultWhitelistAudit.setCurrentApplication(DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME);
		//设置 外请车白名单"的白名单状态为"未审核"
		resultWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY_ING);
			// 申请人
		resultWhitelistAudit.setApplyUser(modifyUser.getEmpCode());
			// 申请时间
		resultWhitelistAudit.setApplyTime(new Date());
			// 审核人
		resultWhitelistAudit.setCreateUser(modifyUser.getEmpCode());
		//强制设置白名单类型为“外请车”
		resultWhitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
			// 申请备注
		resultWhitelistAudit.setApplyNotes("申请修改净空");				
		result = leasedWhitelistAuditDao.addLeasedWhitelistAudit(resultWhitelistAudit);
		
		//同步白名单信息到ECS
		if(result > 0){
			WhiteListAuditInfo addInfo = this.toWhiteListAuditInfo(resultWhitelistAudit, OPTION_ADD);
			this.syncWhiteListAudiToECS(addInfo);
		}
    }
    
    
    /**
     * 修改车辆白名单信息D
     * @author 101911-foss-zhouChunlai
     * @date 2013-6-6 下午6:56:37
     */
    private void updateLeasedVehicleWhitelistsByVehicleNo(LeasedTruckEntity leasedTruckParameter,String modifyUser){
    	WhitelistAuditEntity whitelistAuditEntity = new WhitelistAuditEntity(); 
    	whitelistAuditEntity.setVehicleNo(leasedTruckParameter.getVehicleNo());
    	whitelistAuditEntity.setVehicleType(leasedTruckParameter.getVehicleType());
    	whitelistAuditEntity.setDrivingLicense(leasedTruckParameter.getDrivingLicense());
    	whitelistAuditEntity.setEndTimeDrivingLic(leasedTruckParameter.getEndTimeDrivingLic());
    	whitelistAuditEntity.setOperatingLic(leasedTruckParameter.getOperatingLic());
    	whitelistAuditEntity.setEndTimeOperatingLic(leasedTruckParameter.getEndTimeOperatingLic());
    	whitelistAuditEntity.setInsureCard(leasedTruckParameter.getInsureCard());
    	whitelistAuditEntity.setContact(leasedTruckParameter.getContact());
    	whitelistAuditEntity.setContactPhone(leasedTruckParameter.getContactPhone());
    	whitelistAuditEntity.setModifyUser(modifyUser);
    	leasedVehicleWhitelistService.updateLeasedWhitelistAuditByVehicleNo(whitelistAuditEntity);
    }
    /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录唯一标识查询出一条“外请车（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:23:18
     * @param id 记录唯一标识
     * @return “外请车（厢式车、挂车、拖头）”实体
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicle(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedTruckEntity queryLeasedVehicle(String id) throws LeasedVehicleException{
	if (StringUtils.isBlank(id)) {
	    throw new LeasedVehicleException(LeasedVehicleException.ID_ISNULL, "ID 为空");
	}
	return leasedVehicleDao.queryLeasedVehicleBySelective(id);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:56:48
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedTruckEntity queryLeasedVehicleBySelectiveCondition(
            LeasedTruckEntity leasedTruck) throws LeasedVehicleException {
	if (null == leasedTruck) {
	    throw new LeasedVehicleException(LeasedVehicleException.PARAMETER_ISNULL, "外请车参数信息为空");
	}
	//获取录入的车票号
	String vehicleNo = leasedTruck.getVehicleNo();
	if(StringUtils.isNotEmpty(vehicleNo)){
		
		//标记外请车车票是否符合规范
		boolean flag = false;
		flag = this.checkVehicleNoIsValid(vehicleNo);
		//LeasedVehicleException.VEHICLE_NO_ISERROR
		if(flag){
			throw new LeasedVehicleException("该外请车车牌不合法，应由字母、数字或者汉字随意组合，汉字不能超过两个");
		}
		//定义公司车的一个对象
		OwnTruckEntity entity = new OwnTruckEntity();
		entity.setVehicleNo(vehicleNo);
		//查询新增的外请车车牌在公司车信息中是否已经录入
		OwnTruckEntity  ownEntity = ownVehicleService.queryOwnVehicleBySelective(entity, null);
		if(null != ownEntity){
			throw new LeasedVehicleException("该外请车信息已经在公司车信息中录入，请核实外请车辆信息是否正确");
		}
	}
	leasedTruck = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck);
	return leasedTruck;
    }

    /**
     * <p>根据条件有选择的统计出符合条件的“外请车（厢式车、挂车、拖头）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午10:36:37
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public long queryLeasedVehicleRecordCountBySelectiveCondition(
            LeasedTruckEntity leasedTruck) {
	long recordCount = 0;
	recordCount = leasedVehicleDao.queryLeasedVehicleRecordCountBySelectiveCondition(leasedTruck);
	return recordCount;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:23:19
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象 
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryLeasedVehicleListBySelectiveCondition(
	    LeasedTruckEntity leasedTruck, int offset, int limit) throws LeasedVehicleException{
	PaginationDto paginationDto = new PaginationDto();
	if (limit < 0 || offset < 0) {
	    return paginationDto;
	}
	if(null == leasedTruck){
	    leasedTruck = new LeasedTruckEntity();
	}
	//判断，若不是外请厢式车，则查询条件的active都设置为Y,(拖头，挂车没有查询无效数据的需求)
	if(!leasedTruck.getVehicleType().equals(DictionaryValueConstants.VEHICLE_TYPE_VAN)){
		leasedTruck.setActive(FossConstants.ACTIVE);
	}
	List<LeasedTruckEntity> leasedTruckList = leasedVehicleDao.queryLeasedVehicleListBySelectiveCondition(leasedTruck, offset, limit);
	if (CollectionUtils.isNotEmpty(leasedTruckList)) {
	    Long totalCount = leasedVehicleDao.queryLeasedVehicleRecordCountBySelectiveCondition(leasedTruck);
	    paginationDto.setTotalCount(totalCount);
	    paginationDto.setPaginationDtos(leasedTruckList);
	}else{
	    if(LOGGER.isDebugEnabled()){
		LOGGER.debug("调试：根据提供的条件检索的结果集为（空）");
	    }
	}
	return paginationDto;
    }
    
    /**
      * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）和司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
      * @author 100847-foss-GaoPeng
      * @date 2012-10-16 上午10:18:58
      * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
      * @param offset 开始记录数
      * @param limit 限制记录数
      * @return 符合条件的“外请车（厢式车、挂车、拖头）和司机”实体列表
     * @throws LeasedVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleListDtosBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
	public PaginationDto queryLeasedVehicleListDtosBySelectiveCondition(
			LeasedTruckEntity leasedTruck, int offset, int limit)
			throws LeasedVehicleException {
		// 车辆信息
		PaginationDto paginationDto = queryLeasedVehicleListBySelectiveCondition(
				leasedTruck, offset, limit);
		List<LeasedTruckEntity> leasedTruckList = paginationDto
				.getPaginationDtos();
		if (CollectionUtils.isNotEmpty(leasedTruckList) && leasedTruck.getVehicleType().equals(DictionaryValueConstants.VEHICLE_TYPE_VAN)) {
			WhitelistAuditEntity resultWhitelistAudit, whitelistAudit = new WhitelistAuditEntity();
			List<LeasedTruckDto> leasedTruckDtos = new ArrayList<LeasedTruckDto>(
					leasedTruckList.size());
			for (LeasedTruckEntity lsTruck : leasedTruckList) {
				LeasedTruckDto leasedTruckDto = LeasedTruckDto
						.getInstance(lsTruck);
				if (StringUtils.isNotBlank(lsTruck.getVehicleNo())) {
					// 外请车白名单信息
					whitelistAudit.setVehicleNo(lsTruck.getVehicleNo()); // 车牌号
					resultWhitelistAudit = leasedVehicleWhitelistService
							.queryLeasedVehicleWhitelistsBySelectiveCondition(whitelistAudit);
					if (null != resultWhitelistAudit) {
						leasedTruckDto.setWhiteStatus(resultWhitelistAudit.getStatus()); //白名单审核状态
						leasedTruckDto.setApproveTime(resultWhitelistAudit.getApproveTime());//白名单审核时间
						if(StringUtils.isNotBlank(resultWhitelistAudit.getApplyUser())){
							String empName = employeeService.queryEmpNameByEmpCode(resultWhitelistAudit.getApplyUser());
							leasedTruckDto.setApproveUserName(empName);
						}
						leasedTruckDto.setApproveUser(resultWhitelistAudit.getApplyUser());//白名单审核人
						
					}
				}
				leasedTruckDtos.add(leasedTruckDto);
			}
			//给集合设置创建、更新人姓名
			paginationDto.setPaginationDtos(attachEmpName(leasedTruckDtos));
		}
		return paginationDto;
	}
    /**
     *<P>给工号设置名字列表<P>
     * @author :130566-zengJunfan
     * @date : 2014-3-17下午4:37:55
     * @param leasedTruckDtos
     */
    private List<LeasedTruckDto> attachEmpName(List<LeasedTruckDto> leasedTruckDtos) {
    	//
		if(CollectionUtils.isNotEmpty(leasedTruckDtos)){
			for (LeasedTruckDto leasedTruckDto : leasedTruckDtos) {
				attachEmpName(leasedTruckDto);
			}
		}
		return leasedTruckDtos;
	}
    /**
     *<P>给实体注入“名字”<P>
     * @author :130566-zengJunfan
     * @date : 2014-3-17下午4:37:55
     * @param leasedTruckDtos
     */
	private LeasedTruckDto attachEmpName(LeasedTruckDto leasedTruckDto) {
		if(null == leasedTruckDto){
			return null;
		}
		//设置创建人姓名
		if(StringUtils.isNotBlank(leasedTruckDto.getCreateUser())){
			leasedTruckDto.setCreateUserName(employeeService.queryEmpNameByEmpCode(leasedTruckDto.getCreateUser()));
		}
		//设置修改人姓名
		if(StringUtils.isNotBlank(leasedTruckDto.getModifyUser())){
			leasedTruckDto.setModifyUserName(employeeService.queryEmpNameByEmpCode(leasedTruckDto.getModifyUser()));
		}
		//设置创建用户部门名称
		if(StringUtils.isNotBlank(leasedTruckDto.getCreateUserDept())){
			leasedTruckDto.setCreateUserDeptName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(leasedTruckDto.getCreateUserDept()));
		}
		//设置修改用户部门名称
		if(StringUtils.isNotBlank(leasedTruckDto.getModifyUserDept())){
			leasedTruckDto.setModifyUserDeptName(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(leasedTruckDto.getModifyUserDept()));
		}
		return leasedTruckDto;
	}

	/**
     * <p>根据"车牌号"来获取"外请车辆-（厢式车、挂车、拖头）"对应的记录数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:20:57
     * @param vehicleNo 车牌号
     * @return "外请车辆-（厢式车、挂车、拖头）"实体
     * @throws LeasedVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleByVehicleNo(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedTruckEntity queryLeasedVehicleByVehicleNo(String vehicleNo) throws LeasedVehicleException {
	if (StringUtils.isBlank(vehicleNo)) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "车牌号为空");
	}
	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
	leasedTruck.setVehicleNo(vehicleNo);
	leasedTruck = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck);
	return leasedTruck;
    }

    /**
     * <p>根据"车牌号"来获取"外请车辆（厢式车、挂车、拖头）"对应的记录数据实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:21:00
     * @param vehicleNo 车牌号
     * @param vehicleType 车辆类型（厢式车、挂车、拖头）值代码
     * @return "外请车辆（厢式车、挂车、拖头）"实体
     * @throws LeasedVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleByVehicleNoAndType(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedTruckEntity queryLeasedVehicleByVehicleNoAndType(
	    String vehicleNo, String vehicleType) throws LeasedVehicleException {
	if (StringUtils.isBlank(vehicleNo)) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "车牌号为空");
	}
	if (StringUtils.isBlank(vehicleType)) {
	    throw new LeasedVehicleException(LeasedVehicleException.VEHICLE_TYPE_ISNULL, "未选择外请车类型");
	}
	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
	leasedTruck.setVehicleNo(vehicleNo);
	leasedTruck.setVehicleType(vehicleType);
	leasedTruck = leasedVehicleDao.queryLeasedVehicleBySelective(leasedTruck);
	return leasedTruck;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午4:28:53
     * @param vehicleNos 车牌号集合
     * @return 封装了VehicleAssociationDto传送对象集合
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleAssociationDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleAssociationDto> queryLeasedVehicleAssociationDtosByVehicleNos(
            String[] vehicleNos) throws LeasedVehicleException {
	if (ArrayUtils.isEmpty(vehicleNos)) {
	    throw new OwnVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "车牌号为空");
	}
	//初始化参数和结果集
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	VehicleAssociationDto vehicleAssociationDto = null;
	List<VehicleBaseDto> vehicleBaseDtos = null;
	//获取数据
	vehicleBaseDtos = leasedVehicleDao.queryLeasedVehicleBaseDtosByVehicleNos(vehicleNos);
	for (VehicleBaseDto vehicleBaseDto : vehicleBaseDtos) {
	    //车辆关联属性对象
	    vehicleAssociationDto = new VehicleAssociationDto();
	    try {
		BeanUtils.copyProperties(vehicleBaseDto, vehicleAssociationDto,
			new String[] { "vehicleDeadLoad", "vehicleSelfVolume",
				"vehicleLength", "vehicleWidth",
			"vehicleHeight" 
		});
		vehicleAssociationDto.setVehicleDeadLoad(vehicleBaseDto.getVehicleDeadLoad());
		vehicleAssociationDto.setVehicleSelfVolume(vehicleBaseDto.getVehicleSelfVolume());
		vehicleAssociationDto.setVehicleLength(vehicleBaseDto.getVehicleLength());
		vehicleAssociationDto.setVehicleWidth(vehicleBaseDto.getVehicleWidth());
		vehicleAssociationDto.setVehicleHeight(vehicleBaseDto.getVehicleHeight());
	    } catch (Exception e) {
		throw new OwnVehicleException(LeasedVehicleException.DTO_COPY_PROPERTIES, "复制车辆数据失败");
	    }
	    
	    String vehicleType = vehicleBaseDto.getVehicleType();
	    if(StringUtils.isNotBlank(vehicleType)){
		
		 // 暂时禁用，数据同步做测试不方便
		if(StringUtils.equals(vehicleType, DictionaryValueConstants.VEHICLE_TYPE_TRACTORS)){
		    if(LOGGER.isWarnEnabled()){
			LOGGER.debug("调试：拖头无车型数据，不需要获取车型资料");
		    }
		}else{
		    //需要车辆车型走缓存
		    String vehicleLengthCode = vehicleBaseDto.getVehicleLengthCode();
		    VehicleTypeEntity vehicleLengthType = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
		    if(null != vehicleLengthType){
			vehicleAssociationDto.setVehicleLengthCode(vehicleLengthType.getVehicleLengthCode());
			vehicleAssociationDto.setVehicleLengthValue(vehicleLengthType.getVehicleLength());
			vehicleAssociationDto.setVehicleLengthName(vehicleLengthType.getVehicleLengthName());
			vehicleAssociationDto.setEachKilometersFees(vehicleLengthType.getEachKilometersFees());
		    }else{
			if(LOGGER.isWarnEnabled()){
			    LOGGER.warn("警告：数据不完整错误，对应的车辆车型不存在");
			}
		    }
		}
            }else{
        	if(LOGGER.isWarnEnabled()){
		    LOGGER.warn("警告：数据不完整错误，对应的车辆类型不存在");
		}
            }
	    vehicleAssociationDtos.add(vehicleAssociationDto);
	}
        return vehicleAssociationDtos;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 313353-foss-qiupeng
     * @date 2016-07-06 上午09:28:53
     * @param vehicleNo 车牌号
     * @return 封装了VehicleAssociationDto传送对象集合
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleAssociationDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleAssociationDto> queryLeasedVehicleByVehicleNosIncludeTractors(
            String[] vehicleNos) throws LeasedVehicleException {
    if (ArrayUtils.isEmpty(vehicleNos)) {
    	throw new OwnVehicleException(LeasedVehicleException.VEHICLE_NO_ISNULL, "车牌号为空");
    }
	//初始化参数和结果集
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	VehicleAssociationDto vehicleAssociationDto = null;
	List<VehicleBaseDto> vehicleBaseDtos = null;
	//获取数据
	vehicleBaseDtos = leasedVehicleDao.queryLeasedVehicleBaseDtosByVehicleNos(vehicleNos);
	for (VehicleBaseDto vehicleBaseDto : vehicleBaseDtos) {
	    //车辆关联属性对象
	    vehicleAssociationDto = new VehicleAssociationDto();
	    try {
		BeanUtils.copyProperties(vehicleBaseDto, vehicleAssociationDto,
			new String[] { "vehicleDeadLoad", "vehicleSelfVolume",
				"vehicleLength", "vehicleWidth",
			"vehicleHeight" 
		});
		vehicleAssociationDto.setVehicleDeadLoad(vehicleBaseDto.getVehicleDeadLoad());
		vehicleAssociationDto.setVehicleSelfVolume(vehicleBaseDto.getVehicleSelfVolume());
		vehicleAssociationDto.setVehicleLength(vehicleBaseDto.getVehicleLength());
		vehicleAssociationDto.setVehicleWidth(vehicleBaseDto.getVehicleWidth());
		vehicleAssociationDto.setVehicleHeight(vehicleBaseDto.getVehicleHeight());
	    } catch (Exception e) {
		throw new OwnVehicleException(LeasedVehicleException.DTO_COPY_PROPERTIES, "复制车辆数据失败");
	    }
	    
	    String vehicleType = vehicleBaseDto.getVehicleType();
	    if(StringUtils.isNotBlank(vehicleType)){
		    //需要车辆车型走缓存
		    String vehicleLengthCode = vehicleBaseDto.getVehicleLengthCode();
		    VehicleTypeEntity vehicleLengthType = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
		    if(null != vehicleLengthType){
		    	vehicleAssociationDto.setVehicleLengthCode(vehicleLengthType.getVehicleLengthCode());
		    	vehicleAssociationDto.setVehicleLengthValue(vehicleLengthType.getVehicleLength());
		    	vehicleAssociationDto.setVehicleLengthName(vehicleLengthType.getVehicleLengthName());
		    	vehicleAssociationDto.setEachKilometersFees(vehicleLengthType.getEachKilometersFees());
		    }else{
		    	if(LOGGER.isWarnEnabled()){
		    		LOGGER.warn("警告：数据不完整错误，对应的车辆车型不存在");
		    	}
		    }
            }else{
            	if(LOGGER.isWarnEnabled()){
            		LOGGER.warn("警告：数据不完整错误，对应的车辆类型不存在");
            	}
            }
	    vehicleAssociationDtos.add(vehicleAssociationDto);
	}
        return vehicleAssociationDtos;
    }
    
    /**
     * <p>提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param isOpenVehicle 是否敞篷车（true：是，false：否）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(
            String vehicleNo, String vehicleTypeCode, String driverName,
            String driverPhone, boolean isOpenVehicle, String status) throws LeasedVehicleException {
	List<LeasedVehicleWithDriverDto> vehicleWithDriverDtos = null;
	//把Boolean值转换成数据字段常量值
	String openVehicle = isOpenVehicle ? FossConstants.ACTIVE : FossConstants.INACTIVE;
	vehicleWithDriverDtos = leasedVehicleDao
		.queryLeasedVehicleWithDriverDtosByCondition(vehicleNo,
			vehicleTypeCode, driverName, driverPhone, openVehicle, status);
	if(CollectionUtils.isEmpty(vehicleWithDriverDtos)){
	    if(LOGGER.isDebugEnabled()){
		LOGGER.debug("调试：根据提供的条件检索的结果集为（空）");
	    }
	    return new ArrayList<LeasedVehicleWithDriverDto>(NumberConstants.NUMERAL_ZORE);  
	}else{
	    return vehicleWithDriverDtos;
	}
    }
    /**
     * <p>（分页模糊）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param isOpenVehicle 是否敞篷车（true：是，false：否）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(
            String vehicleNo, String vehicleTypeCode, String driverName,
            String driverPhone, boolean isOpenVehicle, String status, int offset, int limit) throws LeasedVehicleException {
	List<LeasedVehicleWithDriverDto> vehicleWithDriverDtos = null;
	//把Boolean值转换成数据字段常量值
	String openVehicle = isOpenVehicle ? FossConstants.ACTIVE : FossConstants.INACTIVE;
	vehicleWithDriverDtos = leasedVehicleDao
		.queryLeasedVehicleWithDriverDtosByCondition(vehicleNo,
			vehicleTypeCode, driverName, driverPhone, openVehicle, status,offset,limit);
	if(CollectionUtils.isEmpty(vehicleWithDriverDtos)){
	    if(LOGGER.isDebugEnabled()){
		LOGGER.debug("调试：根据提供的条件检索的结果集为（空）");
	    }
	    return new ArrayList<LeasedVehicleWithDriverDto>(NumberConstants.NUMERAL_ZORE);  
	}else{
	    return vehicleWithDriverDtos;
	}
    }
    /**
     * <p>（分页模糊）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param isOpenVehicle 是否敞篷车（true：是，false：否）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @throws LeasedVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public long countQueryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName,
            String driverPhone, boolean isOpenVehicle, String status) throws LeasedVehicleException {
	//把Boolean值转换成数据字段常量值
	 String openVehicle = isOpenVehicle ? FossConstants.ACTIVE : FossConstants.INACTIVE;
	 return leasedVehicleDao.countQueryLeasedVehicleWithDriverDtosByCondition(vehicleNo,vehicleTypeCode, driverName, driverPhone, openVehicle, status);
    }
    /**
     * 
     *<p>导出外请车（挂车，厢式车，拖头）的excel表格</p>
     *@author 130566-zengJunfan
     *@date   2013-8-2上午10:25:38
     * @param leasedTruck
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public InputStream exportLeaseVehicle(LeasedTruckEntity leasedTruck) {
		PaginationDto dto =this.queryLeasedVehicleListDtosBySelectiveCondition(leasedTruck, 0, NumberConstants.NUMBER_2000);
		//LeasedTruckDto 是外请车实体类的子类
		List<LeasedTruckDto> leasedTruckDtos =dto.getPaginationDtos();
		//非空判断
		if(CollectionUtils.isNotEmpty(leasedTruckDtos)){
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (LeasedTruckDto leasedTruckDto : leasedTruckDtos) {
				List<String> columnList = new ArrayList<String>();
				columnList.add(leasedTruckDto.getVehicleNo()); //车牌号
				columnList.add(leasedTruckDto.getVehicleFrameNo());//车架号
				columnList.add(leasedTruckDto.getLeasedDriverName());//所属司机
				columnList.add(leasedTruckDto.getEngineNo());//发动机号
				columnList.add(String.valueOf(leasedTruckDto.getDeadLoad()));//载重（吨）
				columnList.add(String.valueOf(leasedTruckDto.getSelfWeight()));//自重（吨）
				columnList.add(DateUtils.convert(leasedTruckDto.getBeginTime(), DateUtils.DATE_FORMAT));//出厂日期
				columnList.add(DateUtils.convert(leasedTruckDto.getEndTime(), DateUtils.DATE_FORMAT));//报废日期
				columnList.add(leasedTruckDto.getBrand());//品牌
				if(FossConstants.YES.equals(leasedTruckDto.getProducingArea())){ //产地归属
					columnList.add("国产");
				}else{
					columnList.add("进口");
				}
				if(FossConstants.YES.equals(leasedTruckDto.getBargainVehicle())){//是否合同车
					columnList.add("是");
				}else{
					columnList.add("否");
				}
				columnList.add(leasedTruckDto.getBargainRoute());//合同线路
				columnList.add(leasedTruckDto.getOperatingLic());//营运证号
				columnList.add(leasedTruckDto.getDrivingLicense());//行驶证号
				columnList.add(leasedTruckDto.getInsureCard());//保险卡号
				columnList.add(leasedTruckDto.getContact());//车主
				columnList.add(leasedTruckDto.getContactPhone());//车主电话
				columnList.add(leasedTruckDto.getContactAddress());//车主联系地址
				//若是外请车类型是挂车
				if("vehicletype_trailer".equals(leasedTruck.getVehicleType())){
					if(FossConstants.YES.equals(leasedTruckDto.getPlat())){ //有无高地板
						columnList.add("有");
					}else{
						columnList.add("否");
					}
					if(FossConstants.YES.equals(leasedTruckDto.getOpenVehicle())){//是否敞篷车	
						columnList.add("是");
					}else{
						columnList.add("否");
					}
					if(FossConstants.YES.equals(leasedTruckDto.getMaterial())){//车柜材质
						columnList.add("铁皮柜");
					}else{
						columnList.add("玻璃柜");
					}
					columnList.add(String.valueOf(leasedTruckDto.getSelfVolume())); //净空（方）
					columnList.add(String.valueOf(leasedTruckDto.getVehicleLength())); //车长（米）
					columnList.add(String.valueOf(leasedTruckDto.getVehicleWidth())); //车宽（米）
					columnList.add(String.valueOf(leasedTruckDto.getVehicleHeight())); //车高（米）
					columnList.add(leasedTruckDto.getVehicleLengthCode());//车辆类型
					columnList.add(leasedTruckDto.getMeasureEmployeeName());//测量员工
					columnList.add(leasedTruckDto.getMeasureManagerName());//测量经理
					columnList.add(leasedTruckDto.getMeasureSeniorManagerName());//测量高级经理
				}
				//若是外请拖头
				if("vehicletype_tractors".equals(leasedTruck.getVehicleType())){
					if(FossConstants.YES.equals(leasedTruckDto.getGps())){ //是否装载gps
						columnList.add("是");
					}else{
						columnList.add("否");
					}
					columnList.add(leasedTruckDto.getGpsProvider());//gps供应商
					if("brige_single".equals(leasedTruckDto.getBridge())){ //单双桥
						columnList.add("单桥");
					}else if("brige_twinbridge".equals(leasedTruckDto.getBridge())){
						columnList.add("双桥");
					}
				}
				//若是外请厢式车
				//313353 sonar
				this.sonarSplitOne(leasedTruckDto, leasedTruckDto, columnList);
				
				rowList.add(columnList);
			}
			ExportResource exportResource = new ExportResource();
			ExportSetting exportSetting = new ExportSetting();
			ExporterExecutor objExporterExecutor = new ExporterExecutor();
			//若是外请挂车
			if("vehicletype_trailer".equals(leasedTruck.getVehicleType())){
				//列名
				String[] rowHeads ={"车牌号","车架号","所属司机","发动机号","载重（吨）","自重（吨）","出厂日期","报废日期",
						"品牌","产地归属","是否合同车","合同线路","营运证号","行驶证号","保险卡号","车主姓名",
						"车主电话","车主联系地址","有无高地板","是否敞篷车","车柜材质","净空（方）",
						"车长（米）","车宽（米）","车高（米）","车辆类型","测量员工","测量经理","测量高级经理"};
				exportResource.setHeads(rowHeads);
				exportSetting.setSheetName("外请挂车信息");				
			//若是外请拖头
			}else if("vehicletype_tractors".equals(leasedTruck.getVehicleType())){
				String[] rowHeads ={"车牌号","车架号","所属司机","发动机号","载重（吨）","自重（吨）","出厂日期","报废日期",
						"品牌","产地归属","是否合同车","合同线路","营运证号","行驶证号","保险卡号","车主姓名",
						"车主电话","车主联系地址","是否装载gps","gps供应商","单双桥"};
				exportResource.setHeads(rowHeads);
				exportSetting.setSheetName("外请拖头信息");
			}else{
				String[] rowHeads ={"车牌号","车架号","所属司机","发动机号","载重（吨）","自重（吨）","出厂日期","报废日期",
						"品牌","产地归属","是否合同车","合同线路","营运证号","行驶证号","保险卡号","车主姓名",
						"车主电话","车主联系地址","是否高栏车","是否有尾板","是否敞篷车","净空（方）"
						,"车长（米）","车宽（米）","车高（米）","车辆类型","司机电话","司机身份证","白名单状态","白名单审核人","白名单审核时间",
						"创建人","创建时间","创建部门","修改人","修改时间","修改部门","是否有效","测量员工","测量经理","测量高级经理"};
				exportResource.setHeads(rowHeads);
				exportSetting.setSheetName("外请厢式车信息");
			}
			exportResource.setRowList(rowList);
			exportSetting.setSize(NumberConstants.NUMBER_2000);
			return objExporterExecutor.exportSync(exportResource, exportSetting);
		}
		return null;
		
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(LeasedTruckDto leasedTruckDto, LeasedTruckEntity leasedTruck,
			List<String> columnList) {
		if("vehicletype_van".equals(leasedTruck.getVehicleType())){
			if(FossConstants.YES.equals(leasedTruckDto.getRailVehicle())){//是否高栏车	
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			if(FossConstants.YES.equals(leasedTruckDto.getTailBoard())){//是否有尾板	
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			if(FossConstants.YES.equals(leasedTruckDto.getOpenVehicle())){//是否敞篷车	
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			columnList.add(String.valueOf(leasedTruckDto.getSelfVolume())); //净空（方）
			columnList.add(String.valueOf(leasedTruckDto.getVehicleLength())); //车长（米）
			columnList.add(String.valueOf(leasedTruckDto.getVehicleWidth())); //车宽（米）
			columnList.add(String.valueOf(leasedTruckDto.getVehicleHeight())); //车高（米）
			columnList.add(leasedTruckDto.getVehicleLengthCode());//车辆类型
			columnList.add(leasedTruckDto.getDriverPhone());//司机电话
			columnList.add(leasedTruckDto.getLeasedDriverIdCard());//司机身份证
			//DataDictionaryValueEntity  dataDictionaryEntity= dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCodeNoCache("BES_WHITELISTS_AUDIT",leasedTruckDto.getWhiteStatus());
			if(FossConstants.YES.equals(leasedTruckDto.getStatus())){
				columnList.add("可用");
				//columnList.add(dataDictionaryEntity.getValueName());//白名单状态
			}else{
				columnList.add("不可用");
			}
			columnList.add(leasedTruckDto.getApproveUserName());//白名单审核人
			columnList.add(DateUtils.convert(leasedTruckDto.getApproveTime()));//白名单审核时间
			//添加创建人、创建时间、创建部门
			columnList.add(leasedTruckDto.getCreateUser());
			columnList.add(DateUtils.convert(leasedTruckDto.getCreateDate()));
			if(StringUtils.isNotBlank(leasedTruckDto.getCreateUserDept())){
				columnList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(leasedTruckDto.getCreateUserDept())); 
			}else{
				columnList.add("");
			}
			//添加修改部门、修改时间、修改部门
			columnList.add(leasedTruckDto.getModifyUser());
			columnList.add(DateUtils.convert(leasedTruckDto.getModifyDate()));
			if(StringUtils.isNotBlank(leasedTruckDto.getModifyUserDept())){
				columnList.add(orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(leasedTruckDto.getModifyUserDept())); 
			}else{
				columnList.add("");
			}
			if(FossConstants.YES.equals(leasedTruckDto.getActive())){
				columnList.add("是");
			}else{
				columnList.add("否");
			}
			columnList.add(leasedTruckDto.getMeasureEmployeeName());//测量员工
			columnList.add(leasedTruckDto.getMeasureManagerName());//测量经理
			columnList.add(leasedTruckDto.getMeasureSeniorManagerName());//测量高级经理
		}
	}
    
	/**
	 * 校验车牌号是否符合规则，由字母、数字或者汉字随意组合，汉字不能超过两个
	 *
	 * auther:wangpeng_078816
	 * date:2014-5-17
	 *
	 */
    private boolean checkVehicleNoIsValid(String vehicleNo){
    	//标记位
    	boolean flag = false;
    	if(StringUtils.isEmpty(vehicleNo)){
    		return flag;
    	}
    	int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
    	Pattern p = Pattern.compile(regEx);
    	Matcher m = p.matcher(vehicleNo);
    	while (m.find()) {
    			count = count + 1;
    			if(count > 2){
    				flag = true;
    				break;
    			}
    	}
    	return flag;
    }
    
    /**
     * <p>通过车牌号查询该车的种类，ownVehicle为公司车，leasedVehicle为外请车，
     * 		   expressVehicle为快递车辆，没查到就返回null</p> 
     * @author 232607 
     * @date 2015-9-22 下午5:26:51
     * @param vehicleNo
     * @return
     * @see
     */
    @Override
    public String queryVehicleType(String vehicleNo){
    	if(vehicleNo==null){
    		throw new BusinessException("传入的车牌号为空");
    	}
    	//由于在班车任务中，公司车使用频率>外请车>快递车辆，所以查询顺序也依次进行，以提高效率、减轻数据库负担
    	//判断是否为公司车
    	OwnTruckEntity condition=new OwnTruckEntity();
    	condition.setVehicleNo(vehicleNo);
    	OwnTruckEntity ownVehicle=ownVehicleService.queryOwnVehicleBySelective(condition,null);
    	if(ownVehicle!=null){
    		return "ownVehicle";
    	}else{
    		//判断是否为外请车
    		LeasedTruckEntity leasedVehicle=queryLeasedVehicleByVehicleNo(vehicleNo);
        	if(leasedVehicle!=null){
        		return "leasedVehicle";
        	}else{
        		//判断是否为快递车辆
        		ExpressVehiclesEntity entity=new ExpressVehiclesEntity();
        		entity.setActive("Y");
        		entity.setVehicleNo(vehicleNo);
        		List<ExpressVehiclesEntity> list=expressVehiclesService.queryExpressVehiclesByEntity(entity);
        		if(list!=null && list.size()!=0){
        			return "expressVehicle";
        		}else{
        			return null;
        		}
        	}
    	}
    }
    
    /**
     * @param leasedVehicleDao the leasedVehicleDao to set
     */
    public void setLeasedVehicleDao(ILeasedVehicleDao leasedVehicleDao) {
        this.leasedVehicleDao = leasedVehicleDao;
    }
    
    /**
     * @param leasedVehicleTypeService the leasedVehicleTypeService to set
     */
    public void setLeasedVehicleTypeService(
    	ILeasedVehicleTypeService leasedVehicleTypeService) {
        this.leasedVehicleTypeService = leasedVehicleTypeService;
    }

    /**
     * @param leasedVehicleWhitelistService the leasedVehicleWhitelistService to set
     */
    public void setLeasedVehicleWhitelistService(
    	ILeasedVehicleWhitelistService leasedVehicleWhitelistService) {
        this.leasedVehicleWhitelistService = leasedVehicleWhitelistService;
    }
    
    /**
     * 设置员工service
     * @param employeeService
     */
    public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
    
    /**
     * 设置数据字典service
     * @param dataDictionaryValueService
     */
    /*public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}*/

	
	/**
	 * 同步信息到ECS
	 * @author 313353
	 * @param leasedDriver
	 */
	private void syncLeasedVehicleToECS(LeasedTruckInfo leasedTruck){
		if(null == leasedTruck){
			return;
		}
		List<LeasedTruckInfo> entitys = new ArrayList<LeasedTruckInfo>();
		entitys.add(leasedTruck);
		this.syncLeasedDriverToECS(entitys);
	}
	
	/**
	 * 同步信息到ECS
	 * @author 313353
	 * @param leasedDriver
	 */
	private void syncLeasedDriverToECS(List<LeasedTruckInfo> entitys){
		syncLeasedInformationService.syncLeasedTruckToECS(entitys);
	}
	
	   /**
     * 同步信息到ECS
     * @param entity
     * @author 313353
     */
    private void syncWhiteListAudiToECS(WhiteListAuditInfo entity){
    	if(null == entity){
    		return;
    	}
    	List<WhiteListAuditInfo> entitys = new ArrayList<WhiteListAuditInfo> ();
    	entitys.add(entity);
    	this.syncWhiteListAudiToECS(entitys);
    }
    
    /**
     * 同步信息到ECS
     * @param entity
     * @author 313353
     */
	private void syncWhiteListAudiToECS(List<WhiteListAuditInfo> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
		syncLeasedInformationService.syncWhiteListAuditToECS(entitys);
	}
	
	/**
	 * foss实体类转化为esb实体类 外请车
	 * 
	 * @param fossEntity
	 * @return
	 */
	private LeasedTruckInfo toLeasedTruckInfo(LeasedTruckEntity fossEntity, String optionType) {
		if (fossEntity == null) {
			return null;
		}
		LeasedTruckInfo esbInfo = new LeasedTruckInfo();
		esbInfo.setActive(fossEntity.getActive());
		esbInfo.setBargainRoute(fossEntity.getBargainRoute());
		esbInfo.setBargainVehicle(fossEntity.getBargainVehicle());
		esbInfo.setBeginTime(fossEntity.getBeginTime());
		esbInfo.setBeginTimeDrivingLic(fossEntity.getBeginTimeDrivingLic());
		esbInfo.setBeginTimeInsureCard(fossEntity.getBeginTimeInsureCard());
		esbInfo.setBeginTimeOperatingLic(fossEntity.getBeginTimeOperatingLic());
		esbInfo.setBrand(fossEntity.getBrand());
		esbInfo.setBridge(fossEntity.getBridge());
		esbInfo.setContact(fossEntity.getContact());
		esbInfo.setContactAddress(fossEntity.getContactAddress());
		esbInfo.setContactPhone(fossEntity.getContactPhone());
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());
		esbInfo.setCreateUserDept(fossEntity.getCreateUserDept());
		esbInfo.setDeadLoad(fossEntity.getDeadLoad());
		esbInfo.setDrivingLicense(fossEntity.getDrivingLicense());
		esbInfo.setEndTime(fossEntity.getEndTime());
		esbInfo.setEndTimeDrivingLic(fossEntity.getEndTimeDrivingLic());
		esbInfo.setEndTimeInsureCard(fossEntity.getEndTimeInsureCard());
		esbInfo.setEndTimeOperatingLic(fossEntity.getEndTimeOperatingLic());
		esbInfo.setEngineNo(fossEntity.getEngineNo());
		esbInfo.setGPS(fossEntity.getGps());
		esbInfo.setGPSNo(fossEntity.getGpsNo());
		esbInfo.setGPSProvider(fossEntity.getGpsProvider());
		esbInfo.setId(fossEntity.getId());
		esbInfo.setInsureCard(fossEntity.getInsureCard());
		esbInfo.setLeasedDriverIdCard(fossEntity.getLeasedDriverIdCard());
		esbInfo.setMaterial(fossEntity.getMaterial());
		esbInfo.setModifyTime(fossEntity.getModifyDate());
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());
		esbInfo.setModifyUserDept(fossEntity.getModifyUserDept());
		esbInfo.setNotes(fossEntity.getNotes());
		esbInfo.setOpenVehicle(fossEntity.getOpenVehicle());
		esbInfo.setOperatingLic(fossEntity.getOperatingLic());
		esbInfo.setPlat(fossEntity.getPlat());
		esbInfo.setProducingArea(fossEntity.getProducingArea());
		esbInfo.setRailVehicle(fossEntity.getRailVehicle());
		esbInfo.setSelfVolume(fossEntity.getSelfVolume());
		esbInfo.setSelfWeight(fossEntity.getSelfWeight());
		esbInfo.setStatus(fossEntity.getStatus());
		esbInfo.setTailBoard(fossEntity.getTailBoard());
		esbInfo.setVehicleFrameNo(fossEntity.getVehicleFrameNo());
		esbInfo.setVehicleHeight(fossEntity.getVehicleHeight());
		esbInfo.setVehicleLenghtCode(fossEntity.getVehicleLengthCode());
		esbInfo.setVehicleLength(fossEntity.getVehicleLength());
		esbInfo.setVehicleNo(fossEntity.getVehicleNo());
		esbInfo.setVehicleType(fossEntity.getVehicleType());
		esbInfo.setVehicleWidth(fossEntity.getVehicleWidth());
		esbInfo.setOptionType(optionType);
		
		return esbInfo;
	}
	
	/**
	 * foss实体类转化为esb实体类 白名单
	 * 
	 * @param fossEntity
	 * @return
	 */
	private WhiteListAuditInfo toWhiteListAuditInfo(
			WhitelistAuditEntity fossEntity, String optionType) {
		if (fossEntity == null) {
			return null;
		}
	WhiteListAuditInfo esbInfo = new WhiteListAuditInfo();

		esbInfo.setId(fossEntity.getId());
		esbInfo.setDriverName(fossEntity.getDriverName());
		esbInfo.setDriverPhone(fossEntity.getDriverPhone());
		esbInfo.setDriverIdCard(fossEntity.getDriverIdCard());
		esbInfo.setQuelification(fossEntity.getQualification());
		esbInfo.setDriverLicense(fossEntity.getDriverLicense());
		esbInfo.setVehicleNo(fossEntity.getVehicleNo());
		esbInfo.setVehicleType(fossEntity.getVehicleType());
		esbInfo.setDrivingLicense(fossEntity.getDrivingLicense());
		esbInfo.setEndTimeDrivingLic(fossEntity.getEndTimeDrivingLic());
		esbInfo.setOperatingLic(fossEntity.getOperatingLic());
		esbInfo.setEndTimeOperatingLic(fossEntity.getEndTimeOperatingLic());
		esbInfo.setInsureCard(fossEntity.getInsureCard());
		esbInfo.setContact(fossEntity.getContact());
		esbInfo.setContactPhone(fossEntity.getContactPhone());
		esbInfo.setCurrentApplication(fossEntity.getCurrentApplication());
		esbInfo.setStatus(fossEntity.getStatus());
		esbInfo.setApplyUser(fossEntity.getApplyUser());
		esbInfo.setApplyTime(fossEntity.getApplyTime());
		esbInfo.setApproveUser(fossEntity.getApproveUser());
		esbInfo.setApproveTime(fossEntity.getApproveTime());
		esbInfo.setAuditNotes(fossEntity.getAuditNotes());
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		esbInfo.setModifyTime(fossEntity.getModifyDate());
		esbInfo.setActive(fossEntity.getActive());
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());
		esbInfo.setWhiteListType(fossEntity.getWhitelistType());
		esbInfo.setApplyNotes(fossEntity.getApplyNotes());
		esbInfo.setOptionType(optionType);
		return esbInfo;
	}
	

    /**
     * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
     * 查询结果字段有：勾选框、车牌号、所属司机、司机电话、载重（吨）、自重（吨）、净空（方）、车长（米）、所属车队、操作人
     * @author 310854
     * @date 2016-5-14
     */
	public PaginationDto queryLeasedServiceDateList(
			LeasedTruckEntity leasedTruck, int offset, int limit)
			throws LeasedVehicleException {
		PaginationDto paginationDto = new PaginationDto();
		if (limit < 0 || offset < 0) {
			return paginationDto;
		}
		if (null == leasedTruck) {
			leasedTruck = new LeasedTruckEntity();
		}
		/*测试
		BindingLeasedTruckDto dto = new BindingLeasedTruckDto();
		if(!StringUtils.isEmpty(leasedTruck.getVehicleNo())){
			dto.setVehicleNo(leasedTruck.getVehicleNo());
		}
		if(!StringUtils.isEmpty(leasedTruck.getDriverPhone())){
			dto.setDriverPhone(leasedTruck.getDriverPhone());
		}
		if(!StringUtils.isEmpty(leasedTruck.getLeasedDriverName())){
			dto.setDriverName(leasedTruck.getLeasedDriverName());
		}
		if(!StringUtils.isEmpty(leasedTruck.getOpenVehicle())){
			dto.setOpenVehicle(leasedTruck.getOpenVehicle());
		}
		dto.setVehicleLengthName("");*/
//		List<BindingLeasedTruckDto> bindingLeasedTruckDtoList = queryBindingLeasedVehicleList(dto,offset, limit);
		List<LeasedTruckEntity> leasedTruckList = leasedVehicleDao
				.queryLeasedServiceDateList(leasedTruck,
						offset, limit);
		if (CollectionUtils.isNotEmpty(leasedTruckList)) {
			Long totalCount = leasedVehicleDao
					.queryLeasedServiceDateList(leasedTruck);
			paginationDto.setTotalCount(totalCount);
			paginationDto.setPaginationDtos(leasedTruckList);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("调试：根据提供的条件检索的结果集为（空）");
			}
		}
		return paginationDto;
	}
	
	/**
	 * 释放车队
	 * @author 310854
	 * @date 2016-5-15
	 */
	@Transactional
	public int deleteLeasedServiceDateTream(List<String> list,
			CurrentInfo modifyInfo, List<String> bindingOgrCodes)
			throws LeasedVehicleException {
		if(CollectionUtils.isEmpty(list) || CollectionUtils.isEmpty(bindingOgrCodes)){
			throw new LeasedVehicleException("【无任何选中记录，请核实处理！】");
		}
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.getTopFleetByCode(modifyInfo.getCurrentDeptCode());
		if(null == orgAdministrativeInfoEntity){
			throw new LeasedVehicleException("【该组织不是车队，请核实处理！】");
		}
	
		for(String code : bindingOgrCodes){
			if(!StringUtils.equals(orgAdministrativeInfoEntity.getCode(), code)){
				throw new LeasedVehicleException("【存在其他车队绑定的车辆，请核实处理！】");
			}
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("active", FossConstants.INACTIVE);
		map.put("modifyDate", new Date());
		map.put("modifyUser", modifyInfo.getEmpCode());
		map.put("operatorName", modifyInfo.getEmpName());
		map.put("vehicleNos", list);
		List<LeasedTruckEntity> leasedTruckEntitys = new ArrayList<LeasedTruckEntity>();
		for(String vehicleNo : list){
			LeasedTruckEntity entity = new LeasedTruckEntity();
			entity.setActive(FossConstants.INACTIVE);
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date());
			entity.setCreateUser(modifyInfo.getEmpCode());
			entity.setModifyUser(modifyInfo.getEmpCode());
			entity.setOperatorName(modifyInfo.getEmpName());
			entity.setVehicleNo(vehicleNo);
			entity.setOrgCode(orgAdministrativeInfoEntity.getCode());
			leasedTruckEntitys.add(entity);
	//		LeasedTruckEntity enti = queryLeasedVehicleTeam(vehicleNo);
		}
		
		int result = leasedVehicleDao.deleteLeasedServiceDateTream(map);
		if(result > 0){
			leasedTruckTeamService.syncLeasedTruckTeam(leasedTruckEntitys);
		}
		return result;
	}
	
	/**
	 * 外清车绑定
	 * @author 310854
	 * @date 2016-5-15
	 */
	@Transactional
	public int addLeasedServiceDateTream(List<String> ids, CurrentInfo modifyInfo) throws LeasedVehicleException{
		if(CollectionUtils.isEmpty(ids)){
			throw new LeasedVehicleException("【无任何选中记录，请核实处理！】");
		}
		List<LeasedTruckEntity> list = new ArrayList<LeasedTruckEntity>();
		Date date = new Date();
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService
				.getTopFleetByCode(modifyInfo.getCurrentDeptCode());
		if(null == orgAdministrativeInfoEntity){
			throw new LeasedVehicleException("【该组织不是车队，请核实处理！】");
		}
		for(int i = 0;i<ids.size();i++){
			LeasedTruckEntity entity = new LeasedTruckEntity();
			entity.setVehicleNo(ids.get(i));
			entity.setOrgCode(orgAdministrativeInfoEntity.getCode());
			entity.setCreateDate(date);
			entity.setCreateUser(modifyInfo.getEmpCode());
			entity.setOperatorName(modifyInfo.getEmpName());
			entity.setModifyDate(entity.getCreateDate());
			entity.setModifyUser(entity.getCreateUser());
			entity.setActive(FossConstants.ACTIVE);
			list.add(entity);
		}
		int result = leasedVehicleDao.addLeasedServiceDateTream(list);
		if(result > 0){
			leasedTruckTeamService.syncLeasedTruckTeam(list);
		}
		return result;
	}
	
	/**
	 * 通过外请车车牌号获取服务车队
	 * @author 310854
	 * @param vehicleNo
	 * @return
	 * @throws LeasedVehicleException
	 */
	public LeasedTruckEntity queryLeasedVehicleTeam(String vehicleNo){
		if(StringUtils.isEmpty(vehicleNo)){
			throw new LeasedVehicleException("【车牌号为空，请核实处理！】");
		}
	//	String str = leasedVehicleDao.queryLeasedVehicleDriverByVehicleNo(vehicleNo);
		return leasedVehicleDao.queryLeasedVehicleTeam(vehicleNo);
	}
	
	@Override
	public List<BindingLeasedTruckDto> queryBindingLeasedVehicleList(
			BindingLeasedTruckDto leasedTruck, int offset, int limit)
			throws LeasedVehicleException {
		// TODO Auto-generated method stub
		CurrentInfo user = FossUserContext.getCurrentInfo();
		
		OrgAdministrativeInfoEntity entiyt = orgAdministrativeInfoComplexService
				.getTopFleetByCode(user.getCurrentDeptCode());
		if(null != entiyt && !StringUtils.isEmpty(entiyt.getCode())){
			leasedTruck.setOrgCode(entiyt.getCode());
		} else {
			return null;
		}
		
		return bindingLeasedVehicleDao.queryBindingLeasedVehicleList(leasedTruck, offset, limit);
	}

	@Override
	public long queryBindingLeasedVehicleListTotal(
			BindingLeasedTruckDto leasedTruck) throws LeasedVehicleException {
		// TODO Auto-generated method stub
		CurrentInfo user = FossUserContext.getCurrentInfo();
		
		OrgAdministrativeInfoEntity entiyt = orgAdministrativeInfoComplexService
				.getTopFleetByCode(user.getCurrentDeptCode());
		if(null != entiyt && !StringUtils.isEmpty(entiyt.getCode())){
			leasedTruck.setOrgCode(entiyt.getCode());
		} else {
			return 0L;
		}
		return bindingLeasedVehicleDao.queryBindingLeasedVehicleListTotal(leasedTruck);
	}
	
	@Override
	public String queryOrgNameByVehicleNo(String vehicleNo){
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(vehicleNo)){
			throw new BusinessException("【车牌号为空，请核实处理！】");
		}
		try{
			return bindingLeasedVehicleDao.queryOrgNameByVehicleNo(vehicleNo);
		}catch(BusinessException e){
			LOGGER.error("通过车牌号获取绑定的顶级车队有误："+e.getMessage());
			throw new BusinessException("通过车牌号获取绑定的顶级车队有误："+e.getMessage());
		}
	}

	@Override
	public String queryTitleByEmpCode(String empCode, String titleNum) {
		String str = "N";
		EmployeeEntity employeeEntity = employeeService.querySimpleEmployeeByEmpCode(empCode);
		if(employeeEntity!=null&&titleNum!=null){
			String str1 = employeeEntity.getDegree();
			//是否员工
			if(titleNum.equals("0")&&Integer.parseInt(str1)>6){
				str="Y";
				
			}//是否经理
			else if(titleNum.equals("1")&&str1.equals("06")){
				str="Y";
				
			}//是否高级经理
			else if(titleNum.equals("2")&&str1.equals("05")){
				str="Y";
			}
		}
		return str;
	}
}
