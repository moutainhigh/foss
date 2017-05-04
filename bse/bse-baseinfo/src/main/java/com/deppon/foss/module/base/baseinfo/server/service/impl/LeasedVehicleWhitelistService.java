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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedVehicleWhitelistService.java
 * 
 * FILE NAME        	: LeasedVehicleWhitelistService.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车白名单”申请（入库、可用、不可用、撤回）的数据库对应数据访问Service接口实现类：SUC-104
 * <p>需要版本控制：已实现</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-26 上午11:41:58</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-26 上午11:41:58
 * @since
 * @version
 */
public class LeasedVehicleWhitelistService implements ILeasedVehicleWhitelistService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(LeasedVehicleWhitelistService.class);
	
	//同步给ECS的操作类型
	private static final String OPTION_ADD = "1";
	
	//同步给ECS的操作类型
	private static final String OPTION_UPDATE = "2";

	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";
	
    //"外请白名单"审核DAO
    /**
     * 
     */
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;
    
    /**
     * 人员 Service接口.
     */
    private IEmployeeService employeeService;
    
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
     * 设置 人员 Service接口.
     *
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
	/**
     * <p>修改一个“外请白名单车”实体入库（忽略实体中是否存在空值）</p>.
     *
     * @param whitelistAuditParameter “外请白名单车”实体
     * @param isVersion true：需要版本控制，false：不需要版本控制
     * @return 1：成功；0：失败
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:44
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#updateLeasedVehicleWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, boolean)
     */	
    @Override
    @Transactional
    public int updateLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, boolean isVersion) {
	if (null == whitelistAuditParameter) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_PARAMETER_ISNULL, "外请车白名单参数信息为空");
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getId())) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.ID_ISNULL, "ID为空");
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getModifyUser())) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.MODIFIER_ISNULL, "修改人为空");
	}
	
	WhitelistAuditEntity whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditParameter.getId());
	if(null == whitelistAudit){
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_WHITELIST_ISNULL, "外请车白名单不存在");
	}
	
	if (isVersion) {
	    //版本控制：禁用当前记录
	    whitelistAudit.setActive(FossConstants.INACTIVE);
	    leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(whitelistAudit);
	    //版本控制：新增一条记录
	    leasedWhitelistAuditDao.addLeasedWhitelistAudit(whitelistAuditParameter);
	    
	    //同步数据到ECS
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
	    
	    //同步数据到ECS
	    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
	    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(whitelistAudit, OPTION_UPDATE);
	    entityList.add(updateInfo);
	    this.syncWhiteListAudiToECS(entityList);
	}
        return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车白名单”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * <p>包括已审核过和未审核的所有状态（可用、不可用、未入库）</p>.
     *
     * @param whitelistAudit 以“外请车白名单”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:52
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#queryLeasedVehicleWhitelistsListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryLeasedVehicleWhitelistsListBySelectiveCondition(
    		WhitelistAuditQueryDto whitelistAuditQueryDto, int offset, int limit)
	    throws LeasedVehicleWhitelistException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	//强制设置白名单类型为“外请车”
	if (null == whitelistAuditQueryDto) {
		whitelistAuditQueryDto = new WhitelistAuditQueryDto();
	}
	whitelistAuditQueryDto.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
	
	//如果输入部门查询条件为空
	if(StringUtils.isEmpty(whitelistAuditQueryDto.getApplyOrg())){
		List<WhitelistAuditEntity> whitelistAuditList = leasedWhitelistAuditDao.queryLeasedWhitelistApplyListBySelectiveCondition(whitelistAuditQueryDto, offset, limit);
		if(CollectionUtils.isNotEmpty(whitelistAuditList)){
		    Long totalRecord = leasedWhitelistAuditDao.queryLeasedWhitelistApplyRecordCountBySelectiveCondition(whitelistAuditQueryDto);
		    paginationDto.setPaginationDtos(convertInfoList(whitelistAuditList));
		    paginationDto.setTotalCount(totalRecord);
		}
	//否则按如下查询	
	}else{
		List<WhitelistAuditEntity> whitelistAuditList = leasedWhitelistAuditDao.queryLeasedWhitelistApplyListBySelectiveConditionAndApplyOrg(whitelistAuditQueryDto, offset, limit);
		if(CollectionUtils.isNotEmpty(whitelistAuditList)){
		    Long totalRecord = leasedWhitelistAuditDao.queryLeasedWhitelistApplyRecordCountBySelectiveConditionAndApplyOrg(whitelistAuditQueryDto);
		    paginationDto.setPaginationDtos(convertInfoList(whitelistAuditList));
		    paginationDto.setTotalCount(totalRecord);
		}
	}
	
	return paginationDto;
    }

    /**
     * <p>根据“外请车白名单”记录唯一标识查询出一条“外请车白名单”记录</p>.
     *
     * @param whitelistAudit 
     * @return “外请车白名单”实体
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#queryLeasedVehicleWhitelistsBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public WhitelistAuditEntity queryLeasedVehicleWhitelistsBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit) throws LeasedVehicleWhitelistException {
	if (null == whitelistAudit) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_PARAMETER_ISNULL, "外请车白名单参数信息为空");
	}
	//强制设置白名单类型为“外请车”
	whitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
	whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAudit);
	return whitelistAudit;
    }

    /**
     * <p>申请一个“外请车白名单”实体入库</p>.
     *
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:54
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#applyLeasedVehicleWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int applyLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, 
	    String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, true);
	//首次对白名单申请入库的，强制设置白名单状态为"未入库"
	whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY);
	executeApplyWhitelists(whitelistAudit, modifyUser, DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY, applyNotes, true);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>申请一个“外请车白名单”为“可用”状态</p>.
     *
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#availableLeasedVehicleWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int availableLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, 
	    String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	executeApplyWhitelists(whitelistAudit, modifyUser,
		DictionaryValueConstants.LEASEDWHITELISTS_APPLY_AVAILABLE, applyNotes, false);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>申请一个“外请车白名单”为“不可用”状态</p>.
     *
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param applyNotes 申请意见备注
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:56
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#unavailableLeasedVehicleWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int unavailableLeasedVehicleWhitelists(WhitelistAuditEntity whitelistAuditParameter, 
	    String applyNotes, String modifyUser) throws LeasedVehicleWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	executeApplyWhitelists(whitelistAudit, modifyUser,
		DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UNAVAILABLE, applyNotes, false);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>撤销对一个“外请车白名单”的当前申请（申请入库、申请可用、申请不可用）</p>.
     *
     * @param whitelistAuditParameter “外请车白名单”实体
     * @param modifyUser 申请人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午5:12:57
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService#withdrawLeasedVehicleWhitelists(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity)
     */
    @Override
    @Transactional
    public int withdrawLeasedVehicleWhitelists(
	    WhitelistAuditEntity whitelistAuditParameter, String modifyUser) throws LeasedVehicleWhitelistException {
	//验证白名单是否存在
	WhitelistAuditEntity whitelistAudit = executeValidationParameters(whitelistAuditParameter, modifyUser, false);
	
	//如果当前状态是未入库（申请入库），手动撤销，则白名单变为不可用。
	if (StringUtils.equals(whitelistAudit.getStatus(), DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY)) {
	    whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
	}
	//如果当前状态是未审核（申请修改净空），手动撤销，则白名单变为不可用。
	if (StringUtils.equals(whitelistAudit.getStatus(), DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY_ING)){
		whitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
	}
	
	if (StringUtils.equals(FossConstants.INACTIVE, whitelistAudit.getActive())) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_AUDITED, "白名单已经被审核");
	}else{
	    executeApplyWhitelists(whitelistAudit, modifyUser, null, null, false);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>业务数据异常检测</p>.
     *
     * @param whitelistAuditParameter “外请车白名单”
     * @param modifyUser 修改人
     * @param isNew 是否新入库申请
     * @return 
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午7:10:59
     * @see
     */
    private WhitelistAuditEntity executeValidationParameters(
	    WhitelistAuditEntity whitelistAuditParameter, String modifyUser, boolean isNew) throws LeasedVehicleWhitelistException {
	if (null == whitelistAuditParameter) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_PARAMETER_ISNULL, "外请车白名单参数信息为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_PROPOSER_ISNULL, "审核人为空");
	}
	
	WhitelistAuditEntity whitelistAudit = null;
	if (isNew) {
	    whitelistAudit = whitelistAuditParameter;
	}else{
	    if (StringUtils.isBlank(whitelistAuditParameter.getId())) {
		throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.ID_ISNULL, "ID 为空");
	    }
	    whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditParameter.getId());
	    if (null == whitelistAudit) {
		throw new LeasedVehicleWhitelistException(LeasedVehicleWhitelistException.APPLY_WHITELIST_ISNULL, "外请车白名单信息不存在");
	    }
	}
	return whitelistAudit;
    }
    
    /**
     * <p>统一执行申请处理</p>.
     *
     * @param sourceWhitelistAudit “外请车白名单”
     * @param modifyUser 
     * @param currentApply 修改人
     * @param applyNotes 
     * @param isNew true：新白名单；fasle：旧白名单
     * @throws LeasedVehicleWhitelistException 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-3 上午11:08:33
     * @see
     */
    private void executeApplyWhitelists(WhitelistAuditEntity sourceWhitelistAudit, String modifyUser,
	    String currentApply, String applyNotes, boolean isNew) throws LeasedVehicleWhitelistException {
	if (!isNew) {
	    sourceWhitelistAudit.setActive(FossConstants.INACTIVE);
	    sourceWhitelistAudit.setModifyUser(modifyUser);
	    leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(sourceWhitelistAudit);
	    
	    //同步到ECS
	    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
	    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(sourceWhitelistAudit, OPTION_DELETE);
	    entityList.add(updateInfo);
	    this.syncWhiteListAudiToECS(entityList);
	}
	WhitelistAuditEntity targetWwhitelistAudit = sourceWhitelistAudit;
	targetWwhitelistAudit.setApplyUser(modifyUser);
	targetWwhitelistAudit.setApplyTime(new Date());
	targetWwhitelistAudit.setCreateUser(modifyUser);
	targetWwhitelistAudit.setApplyNotes(applyNotes);
	targetWwhitelistAudit.setCurrentApplication(currentApply);
	//强制设置为“外请车”的白名单
	sourceWhitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
	leasedWhitelistAuditDao.addLeasedWhitelistAudit(targetWwhitelistAudit);
    
	//同步到ECS
    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
    WhiteListAuditInfo addInfo = this.toWhiteListAuditInfo(targetWwhitelistAudit, OPTION_ADD);
    entityList.add(addInfo);
    this.syncWhiteListAudiToECS(entityList);
    }
    
    /**
     * <p>封装车辆白名单申请人工号转化成名字</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-26 下午3:11:22
     * @param entity
     * @return
     * @see
     */
    private WhitelistAuditEntity convertInfo(WhitelistAuditEntity entity){
	if(null != entity){
	    //根据编码查询员工信息
	    EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(entity.getApplyUser());
	    if(employeeEntity!=null){
	    	entity.setApplyUserName(employeeEntity.getEmpName());
	    }
	}
	return entity;
    }
    
    /**
     * <p>批量封装信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-26 下午3:18:38
     * @param list
     * @return
     * @see
     */
    private List<WhitelistAuditEntity> convertInfoList(List<WhitelistAuditEntity> list){
	if(CollectionUtils.isNotEmpty(list)){
	    List<WhitelistAuditEntity> entityList = new ArrayList<WhitelistAuditEntity>();
	    for(WhitelistAuditEntity entity : list){
		entityList.add(convertInfo(entity));
	    }
	    
	    return entityList;
	}
	
	return list;
    }
    
    
    
    /**
     * <p>根据车牌号修改一个“外请白名单车”实体入库</p>.
     *
     * @param whitelistAuditParameter “外请白名单车”实体 
     * @return 1：成功；0：失败
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 下午4:07:44
     */	
    @Override
    public int updateLeasedWhitelistAuditByVehicleNo(WhitelistAuditEntity whitelistAuditParameter) {
	if (null == whitelistAuditParameter) {
	    return 0;
	}
	if (StringUtils.isBlank(whitelistAuditParameter.getVehicleNo())) {
		return 0;
	}
	WhitelistAuditEntity whitelistAuditsearchParam = new WhitelistAuditEntity();
	whitelistAuditsearchParam.setVehicleNo(whitelistAuditParameter.getVehicleNo()); 
	WhitelistAuditEntity whitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(whitelistAuditsearchParam);
	if(null == whitelistAudit){
	    return 0;
	}
	int result = leasedWhitelistAuditDao.updateLeasedWhitelistAuditByCardIdOrVehicleNo(whitelistAuditParameter);
	
		// 同步到ECS
		if (result > 0) {
			List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo>();
			whitelistAuditParameter.setId(whitelistAudit.getId());
			WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(
					whitelistAuditParameter, OPTION_UPDATE);
			entityList.add(updateInfo);
			this.syncWhiteListAudiToECS(entityList);
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
