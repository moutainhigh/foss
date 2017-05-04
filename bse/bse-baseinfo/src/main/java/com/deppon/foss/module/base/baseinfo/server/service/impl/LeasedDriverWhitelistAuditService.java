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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedDriverWhitelistAuditService.java
 * 
 * FILE NAME        	: LeasedDriverWhitelistAuditService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistAuditService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverWhitelistAuditException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请司机白名单”审核（同意、拒绝）的数据库对应数据访问Service接口实现类：SUC-750
 * <p>需要版本控制：已实现</p>  
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 上午9:32:07</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 上午9:32:07
 * @since
 * @version
 */
public class LeasedDriverWhitelistAuditService implements
	ILeasedDriverWhitelistAuditService {
	
	//同步给ECS的操作类型
	private static final String OPTION_ADD = "1";
	
	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";
    
    //"外请白名单"审核DAO
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;

    //"外请司机"服务
    private ILeasedDriverService leasedDriverService;
    
    //同步信息到ECS系统的service
    private ISyncLeasedInformationService syncLeasedInformationService;
    
    /**
     * 同步信息到ECS系统的service
     * @param syncLeasedInformationService
     */
    public void setSyncLeasedInformationService(
			ISyncLeasedInformationService syncLeasedInformationService) {
		this.syncLeasedInformationService = syncLeasedInformationService;
	}
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请司机白名单”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * <p>只包括当前申请不为NULL的数据（申请入库、申请可用、申请不可用）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 下午6:23:07
     * @param whitelistAudit 以“外请司机白名单”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws LeasedDriverWhitelistAuditException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistAuditService#queryUnauditedLeasedDriverWhitelistsBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryUnauditedLeasedDriverWhitelistsBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit, int offset, int limit)
	    throws LeasedDriverWhitelistAuditException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if (null == whitelistAudit) {
	    whitelistAudit = new WhitelistAuditEntity();
	}
	
	//强制设置白名单类型为“外请司机”
	whitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_DRIVER);
	if (StringUtils.isBlank(whitelistAudit.getCurrentApplication())) {
	    //强制设置“当前申请”状态不为NULL，表示查询全部待审核的白名单列表
	    whitelistAudit.setCurrentApplication(null);
	}
	
	List<WhitelistAuditEntity> whitelistAuditList = leasedWhitelistAuditDao.queryLeasedWhitelistAuditListBySelectiveCondition(whitelistAudit, offset, limit);
	Long totalRecord = leasedWhitelistAuditDao.queryLeasedWhitelistAuditRecordCountBySelectiveCondition(whitelistAudit);
	paginationDto.setPaginationDtos(whitelistAuditList);
	paginationDto.setTotalCount(totalRecord);
	return paginationDto;
    }

    /**
    * <p>审核一个“外请司机白名单”的申请</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 上午9:32:09
     * @param ids 记录唯一标识集合
     * @param auditNotes 审核意见备注
     * @param modifyUser 审核人
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistAuditException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistAuditService#auditArgeeLeasedDriverWhitelists(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int auditArgeeLeasedDriverWhitelists(List<String> ids, String auditNotes, String modifyUser)
	    throws LeasedDriverWhitelistAuditException {
	//待优化实现方式
	executeValidationParameters(ids, modifyUser);
	for (String id : ids) {
	    executeAuditWhitelists(id, modifyUser, auditNotes, true);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>拒绝一个“外请司机白名单”的申请</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-26 上午10:20:05
     * @param ids 记录唯一标识集合
     * @param auditNotes 审核意见备注
     * @param modifyUser 审核人
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistAuditException  
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistAuditService#auditDeclineLeasedDriverWhitelists(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int auditDeclineLeasedDriverWhitelists(List<String> ids, String auditNotes, String modifyUser)
            throws LeasedDriverWhitelistAuditException {
	//待优化实现方式
	executeValidationParameters(ids, modifyUser);
	for (String id : ids) {
	    executeAuditWhitelists(id, modifyUser, auditNotes, false);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>方法参数异常检测</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午7:35:00
     * @param ids 白名单ID集合
     * @param modifyUser 当前修改人
     * @see
     */
    private void executeValidationParameters(List<String> ids, String modifyUser)
	    throws LeasedDriverWhitelistAuditException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new LeasedDriverWhitelistAuditException(LeasedDriverWhitelistAuditException.AUDIT_ID_ISNULL, "ID为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedDriverWhitelistAuditException(LeasedDriverWhitelistAuditException.AUDIT_AUDITOR_ISNULL, "审核人为空");
	}
    }
    
    /**
     * <p>统一执行审核处理意见</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-3 上午11:08:33
     * @param sourceWhitelistAudit “外请司机白名单”
     * @param modifyUser 修改人
     * @param auditNotes 审核备注
     * @param doArgee true：同意；false：拒绝
     * @see
     */
    private void executeAuditWhitelists(String id, String modifyUser,
	    String auditNotes, boolean doArgee) throws LeasedDriverWhitelistAuditException {
	//白名单数据验证
	WhitelistAuditEntity sourceWhitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(id);
	if (null == sourceWhitelistAudit) {
	    throw new LeasedDriverWhitelistAuditException(LeasedDriverWhitelistAuditException.AUDIT_WHITELIST_ISNULL, "外请司机白名单不存在");
	}else{
	    if (StringUtils.equals(FossConstants.INACTIVE, sourceWhitelistAudit.getActive())) {
		throw new LeasedDriverWhitelistAuditException(LeasedDriverWhitelistAuditException.AUDIT_REVOCATORY, "白名单已经被撤回");
	    }
	}
	
	//白名单对应的司机信息验证
	LeasedDriverEntity leasedDriverParameter = new LeasedDriverEntity();
	leasedDriverParameter.setIdCard(sourceWhitelistAudit.getDriverIdCard());
	LeasedDriverEntity leasedDriver = leasedDriverService.queryLeasedDriverBySelective(leasedDriverParameter);
	if (null == leasedDriver) {
	    throw new LeasedDriverWhitelistAuditException(LeasedDriverWhitelistAuditException.AUDIT_DRIVER_ISNULL, "外请司机信息不存在");
	}
	
	//禁用当前白名单记录
	sourceWhitelistAudit.setActive(FossConstants.INACTIVE);
	sourceWhitelistAudit.setModifyUser(modifyUser);
	leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(sourceWhitelistAudit);
	
	//同步到ECS
    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(sourceWhitelistAudit, OPTION_DELETE);
    entityList.add(updateInfo);
    this.syncWhiteListAudiToECS(entityList);
	
	//新增一条白名单记录
	WhitelistAuditEntity targetWhitelistAudit = sourceWhitelistAudit;
	sourceWhitelistAudit.setApproveUser(modifyUser);
	sourceWhitelistAudit.setApproveTime(new Date());
	sourceWhitelistAudit.setAuditNotes(auditNotes);
	targetWhitelistAudit.setCreateUser(modifyUser);
	
	boolean updateDriver = true;
	//根据同意或者拒绝，修改对应的"白名单状态"
	if (doArgee) {
	    //修改当前白名单的"白名单状态"
	    if (StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UNAVAILABLE)) {
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
		leasedDriver.setStatus(FossConstants.INACTIVE);
	    }else if(StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_AVAILABLE)||StringUtils.equals(
				    sourceWhitelistAudit.getCurrentApplication(), 
				    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY)){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
		leasedDriver.setStatus(FossConstants.ACTIVE);
	    }/*else if(StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY)){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
		leasedDriver.setStatus(FossConstants.ACTIVE);
	    }*/else{
		updateDriver = false;
	    }
	}else{
	    //如果"当前申请为申请入库"被拒绝，则当前白名单的"白名单状态为不可用"
	    if(StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY)){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
		leasedDriver.setStatus(FossConstants.INACTIVE);
	    }else{
		updateDriver = false;
	    }
	}
	if (updateDriver) {
	    //同步修改对应的外请司机的"外请司机状态"
	    leasedDriverService.updateLeasedDriver(leasedDriver, modifyUser, false);	    
	}
	targetWhitelistAudit.setCurrentApplication(null);
	leasedWhitelistAuditDao.addLeasedWhitelistAudit(targetWhitelistAudit);
    
	//同步到ECS
    List<WhiteListAuditInfo> addList = new ArrayList<WhiteListAuditInfo> ();
    WhiteListAuditInfo addInfo = this.toWhiteListAuditInfo(targetWhitelistAudit, OPTION_ADD);
    addList.add(addInfo);
    this.syncWhiteListAudiToECS(addList);
    }
    
    /**
     * @param leasedWhitelistAuditDao the leasedWhitelistAuditDao to set
     */
    public void setLeasedWhitelistAuditDao(
    	ILeasedWhitelistAuditDao leasedWhitelistAuditDao) {
        this.leasedWhitelistAuditDao = leasedWhitelistAuditDao;
    }
    
    /**
     * @param leasedDriverService the leasedDriverService to set
     */
    public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
        this.leasedDriverService = leasedDriverService;
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
}

