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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedDriverWhitelistService.java
 * 
 * FILE NAME        	: LeasedDriverWhitelistService.java
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
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverWhitelistException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请司机白名单”申请（入库、可用、不可用、撤回）的数据库对应数据访问Service接口实现类：SUC-750
 * <p>需要版本控制：已实现</p> 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 下午5:09:30</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 下午5:09:30
 * @since
 * @version
 */
public class LeasedDriverWhitelistService implements ILeasedDriverWhitelistService {
    
//	private static final Logger LOGGER = LoggerFactory
//			.getLogger(LeasedDriverWhitelistService.class);
	
	//同步给ECS的操作类型
	private static final String OPTION_ADD = "1";
	
	//同步给ECS的操作类型
	private static final String OPTION_UPDATE = "2";

	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";
	
    //"外请白名单"审核DAO
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;

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
     * <p>修改一个“外请白名单司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:44
     * @param whitelistAuditParameter “外请白名单司机”实体
     * @param isVersion true：需要版本控制，false：不需要版本控制
     * @return 1：成功；0：失败
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#updateLeasedDriverWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, boolean)
     */
    @Override
    @Transactional
    public int updateLeasedDriverWhitelists(
	    WhitelistAuditEntity whitelistAuditParameter, boolean isVersion)throws LeasedDriverWhitelistException {
	if (null == whitelistAuditParameter) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_PARAMETER_ISNULL, "外请司机白名单参数信息为空");
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getId())) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.ID_ISNULL, "ID为空");
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getModifyUser())) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.MODIFIER_ISNULL, "修改人为空");
	}
	
	WhitelistAuditEntity whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditParameter.getId());
	if(null == whitelistAudit){
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_WHITELISTS_ISNULL, "外请司机白名单不存在");
	}
	
	if (isVersion) {
	    //版本控制：禁用当前记录
	    whitelistAudit.setActive(FossConstants.INACTIVE);
	    leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(whitelistAudit);
	    //版本控制：新增一条记录
	    leasedWhitelistAuditDao.addLeasedWhitelistAudit(whitelistAuditParameter);
	    
	    //同步到ECS
	    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
	    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(whitelistAudit, OPTION_DELETE);
	    WhiteListAuditInfo addInfo = this.toWhiteListAuditInfo(whitelistAuditParameter, OPTION_ADD);
	    entityList.add(updateInfo);
	    entityList.add(addInfo);
	    this.syncWhiteListAudiToECS(entityList);
	}else{
		whitelistAudit.setStatus(whitelistAuditParameter.getStatus());
		whitelistAudit.setCurrentApplication(whitelistAuditParameter.getCurrentApplication());
		whitelistAudit.setActive(whitelistAuditParameter.getActive());
	    leasedWhitelistAuditDao.updateLeasedWhitelistAudit(whitelistAudit);
	    
	    //同步到ECS
	    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(whitelistAudit, OPTION_UPDATE);
	    this.syncWhiteListAudiToECS(updateInfo);
	}
        return FossConstants.SUCCESS;
    }
   
    /**
     * <p>根据条件有选择的检索出符合条件的“外请司机白名单”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * <p>包括已审核过和未审核的所有状态（可用、不可用、未入库）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午3:49:42
     * @param whitelistAudit 以“外请司机白名单”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#queryLeasedDriverWhitelistsListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryLeasedDriverWhitelistsListBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit, int offset, int limit)
	    throws LeasedDriverWhitelistException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if (null == whitelistAudit) {
	    whitelistAudit = new WhitelistAuditEntity();
	}
	//强制设置白名单类型为“外请司机”
	whitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_DRIVER);
	
	WhitelistAuditQueryDto whitelistAuditQueryDto = new WhitelistAuditQueryDto();
	BeanUtils.copyProperties(whitelistAudit, whitelistAuditQueryDto);
	
	List<WhitelistAuditEntity> whitelistAuditList = leasedWhitelistAuditDao.queryLeasedWhitelistApplyListBySelectiveCondition(whitelistAuditQueryDto, offset, limit);
	if(CollectionUtils.isNotEmpty(whitelistAuditList)){
	    Long totalRecord = leasedWhitelistAuditDao.queryLeasedWhitelistApplyRecordCountBySelectiveCondition(whitelistAuditQueryDto);
	    paginationDto.setPaginationDtos(whitelistAuditList);
	    paginationDto.setTotalCount(totalRecord);
	}
	return paginationDto;
    }

    /**
     * <p>根据“外请司机白名单”记录唯一标识查询出一条“外请司机白名单”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:09:32
     * @param id 记录唯一标识
     * @return “外请司机白名单”实体
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#queryLeasedDriverWhitelistsBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public WhitelistAuditEntity queryLeasedDriverWhitelistsBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit) throws LeasedDriverWhitelistException {
	if (null == whitelistAudit) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_PARAMETER_ISNULL, "外请司机白名单参数信息为空");
	}
	//强制设置为“外请司机”的白名单
	whitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_DRIVER);
	whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit);
	return whitelistAudit;
    }

    /**
     * <p>申请一个“外请司机白名单”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:09:33
     * @param whitelistAuditParameter “外请司机白名单”实体
     * @param applyNotes 申请意见备注
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#applyLeasedDriverWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int applyLeasedDriverWhitelists(WhitelistAuditEntity whitelistAuditParameter,
	    String applyNotes, String modifyUser) throws LeasedDriverWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, true);
	
	//首次对白名单申请入库的，强制设置白名单状态为"未入库"
	whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY);
	executeApplyWhitelists(whitelistAudit, modifyUser, 
		DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY, applyNotes, true);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>申请一个“外请司机白名单”为“可用”状态</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:09:34
     * @param whitelistAuditParameter “外请司机白名单”实体
     * @param applyNotes 申请意见备注
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#availableLeasedDriverWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int availableLeasedDriverWhitelists(WhitelistAuditEntity whitelistAuditParameter, 
	    String applyNotes, String modifyUser) throws LeasedDriverWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	executeApplyWhitelists(whitelistAudit, modifyUser, 
		DictionaryValueConstants.LEASEDWHITELISTS_APPLY_AVAILABLE, applyNotes, false);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>申请一个“外请司机白名单”为“不可用”状态</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:09:35
     * @param whitelistAuditParameter “外请司机白名单”实体
     * @param applyNotes 申请意见备注
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#unAvailableLeasedDriverWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int unAvailableLeasedDriverWhitelists(WhitelistAuditEntity whitelistAuditParameter, 
	    String applyNotes, String modifyUser) throws LeasedDriverWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	executeApplyWhitelists(whitelistAudit, modifyUser, 
		DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UNAVAILABLE, applyNotes, false);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>撤销对一个“外请司机白名单”的当前申请（申请入库、申请可用、申请不可用）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:09:36
     * @param whitelistAuditParameter “外请司机白名单”实体
     * @return 1：成功；-1：失败
     * @throws LeasedDriverWhitelistException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService#withdrawLeasedDriverWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @Transactional
    public int withdrawLeasedDriverWhitelists(
	    WhitelistAuditEntity whitelistAuditParameter, String modifyUser) throws LeasedDriverWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	
	//如果是首次申请入库未审核后被撤销，那么白名单状态强制设置为“不可用”
	if (StringUtils.equalsIgnoreCase(whitelistAudit.getStatus(), DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY)) {
	    whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
	}
	
	if (StringUtils.equals(FossConstants.INACTIVE, whitelistAudit.getActive())) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_AUDITED, "白名单已经被审核");
	}else{
	    executeApplyWhitelists(whitelistAudit, modifyUser, null, null, false);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
    * <p>业务数据异常检测</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午7:10:59
     * @param whitelistAuditParameter “外请司机白名单”
     * @param modifyUser 修改人
     * @param isNew 是否新入库申请
     * @return 存在的"外请司机白名单"实体
     * @throws LeasedDriverWhitelistException
     * @see
     */
    private WhitelistAuditEntity executeValidationParameters(
	    WhitelistAuditEntity whitelistAuditParameter, String modifyUser, boolean isNew) throws LeasedDriverWhitelistException {
	if (null == whitelistAuditParameter) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_PARAMETER_ISNULL, "外请司机白名单参数信息为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_PROPOSER_ISNULL, "申请人为空");
	}
	
	WhitelistAuditEntity whitelistAudit = null;
	if (isNew) {
	    whitelistAudit = whitelistAuditParameter;
	}else{
	    if (StringUtils.isBlank(whitelistAuditParameter.getId())) {
		throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.ID_ISNULL, "ID 为空");
	    }
	    whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditParameter.getId());
	    if (null == whitelistAudit) {
		throw new LeasedDriverWhitelistException(LeasedDriverWhitelistException.APPLY_WHITELISTS_ISNULL, "外请司机白名单信息不存在");
	    }
	}
	return whitelistAudit;
    }
    
    /**
     * <p>统一执行申请处理</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-3 上午11:08:33
     * @param sourceWhitelistAudit “外请司机白名单”
     * @param modifyUser 修改人
     * @param currentApply 申请事由
     * @param isNew 是否新入库申请
     * @see
     */
    private void executeApplyWhitelists( WhitelistAuditEntity sourceWhitelistAudit, String modifyUser,
	    String currentApply, String applyNotes, boolean isNew) throws LeasedDriverWhitelistException {
	if (!isNew) {
	    sourceWhitelistAudit.setActive(FossConstants.INACTIVE);
	    sourceWhitelistAudit.setModifyUser(modifyUser);
	    leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(sourceWhitelistAudit);  
	    
	    //同步信息到ECS
	    WhiteListAuditInfo whiteListAuditInfo = this.toWhiteListAuditInfo(sourceWhitelistAudit, OPTION_DELETE);
	    this.syncWhiteListAudiToECS(whiteListAuditInfo);
	}
	WhitelistAuditEntity targetWwhitelistAudit = sourceWhitelistAudit;
	targetWwhitelistAudit.setApplyUser(modifyUser);
	targetWwhitelistAudit.setApplyTime(new Date());
	targetWwhitelistAudit.setCreateUser(modifyUser);
	targetWwhitelistAudit.setApplyNotes(applyNotes);
	targetWwhitelistAudit.setCurrentApplication(currentApply);
	//强制设置为“外请司机”的白名单
	sourceWhitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_DRIVER);
	leasedWhitelistAuditDao.addLeasedWhitelistAudit(targetWwhitelistAudit);
	
	//同步信息到ECS
	WhiteListAuditInfo whiteListAuditInfo = this.toWhiteListAuditInfo(targetWwhitelistAudit, OPTION_ADD);
	this.syncWhiteListAudiToECS(whiteListAuditInfo);
    }
    
    /**
     * <p>根据身份证ID修改一个“外请白名单司机”实体入库</p>.
     *
     * @param whitelistAuditParameter “外请白名单车”实体
     * @param isVersion true：需要版本控制，false：不需要版本控制
     * @return 1：成功；0：失败
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:44
     */	
    @Override
    public int updateLeasedDriverWhitelistsByCardId(WhitelistAuditEntity whitelistAuditParameter) {
	if (null == whitelistAuditParameter) {
	    return 0;
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getDriverIdCard())) {
		return 0;
	}
	WhitelistAuditEntity whitelistAuditsearchParam = new WhitelistAuditEntity(); 
	whitelistAuditsearchParam.setDriverIdCard(whitelistAuditParameter.getDriverIdCard());
	WhitelistAuditEntity whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditsearchParam);
	if(null == whitelistAudit){
	    return 0;
	}
	int result = leasedWhitelistAuditDao.updateLeasedWhitelistAuditByCardIdOrVehicleNo(whitelistAuditParameter);
    
	//同步信息到ECS
	if(result > 0){
		whitelistAuditParameter.setId(whitelistAudit.getId());
		WhiteListAuditInfo whiteListAuditInfo = this.toWhiteListAuditInfo(whitelistAuditParameter, OPTION_UPDATE);
		this.syncWhiteListAudiToECS(whiteListAuditInfo);
	}
	return FossConstants.SUCCESS;
    }
    /**
     * @param leasedWhitelistAuditDao the leasedWhitelistAuditDao to set
     */
    public void setLeasedWhitelistAuditDao(
    	ILeasedWhitelistAuditDao leasedWhitelistAuditDao) {
        this.leasedWhitelistAuditDao = leasedWhitelistAuditDao;
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
