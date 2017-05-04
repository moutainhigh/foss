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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedVehicleWhitelistAuditService.java
 * 
 * FILE NAME        	: LeasedVehicleWhitelistAuditService.java
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
import com.deppon.ecs.inteface.domain.LeasedTruckInfo;
import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedWhitelistAuditDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistAuditService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistAuditException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车白名单”审核（同意、拒绝）的数据库对应数据访问Service接口实现类：SUC-104
 * <p>需要版本控制：已实现</p>   
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 上午9:33:39</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 上午9:33:39
 * @since
 * @version
 */
public class LeasedVehicleWhitelistAuditService implements
	ILeasedVehicleWhitelistAuditService {

//	private static final Logger LOGGER = LoggerFactory.getLogger(LeasedVehicleWhitelistService.class);
	
	//同步给ECS的操作类型
	private static final String OPTION_ADD = "1";

	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";
    
    //"外请白名单"审核DAO
    private ILeasedWhitelistAuditDao leasedWhitelistAuditDao;
    
    //"外请车"服务
    private ILeasedVehicleService leasedVehicleService;
    /**
     * 外请车dao
     */
    private ILeasedVehicleDao leasedVehicleDao;
    
    
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
      * 
     * @Title: setLeasedVehicleDao 
     * @Description: set 外请车Dao
     * @param @param leasedVehicleDao    设定文件 
     * @return void    返回类型 
     * @author 189284-ZhangXu
     * @Date 2015-3-5  下午3:27:58
     * @throws
      */
    public void setLeasedVehicleDao(ILeasedVehicleDao leasedVehicleDao) {
		this.leasedVehicleDao = leasedVehicleDao;
	}

    /**
     * <p>根据条件有选择的检索出符合条件的“外请车白名单”实体列表（条件做自动判断，只选择实体中非空值）</p>
     * <p>只包括当前申请不为NULL的数据（申请入库、申请可用、申请不可用）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 上午9:33:40
     * @param whitelistAudit 以“外请车白名单”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws LeasedVehicleWhitelistAuditException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistAuditService#queryUnauditedLeasedVehicleWhitelistsBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryUnauditedLeasedVehicleWhitelistsBySelectiveCondition(
	    WhitelistAuditEntity whitelistAudit, int offset, int limit)
	    throws LeasedVehicleWhitelistAuditException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if (null == whitelistAudit) {
	    whitelistAudit = new WhitelistAuditEntity();
	}
	//强制设置白名单类型为“外请车”
	whitelistAudit.setWhitelistType(DictionaryValueConstants.LEASEDWHITELISTS_TYPE_VEHICLE);
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
     * <p>同意一个“外请车白名单”的申请</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 上午9:33:41
     * @param ids 记录唯一标识集合
     * @param auditNotes 审核意见备注
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistAuditException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistAuditService#auditArgeeLeasedVehicleWhitelists(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int auditArgeeLeasedVehicleWhitelists(List<String> ids, String auditNotes, CurrentInfo modifyUser)
	    throws LeasedVehicleWhitelistAuditException {
	//待优化实现方式
	executeValidationParameters(ids, modifyUser.getEmpCode());
	for (String id : ids) {
	    executeAuditWhitelists(id, modifyUser, auditNotes, true);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>拒绝一个“外请车白名单”的申请</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 下午5:53:26
     * @param ids 记录唯一标识集合
     * @param auditNotes 审核意见备注
     * @param modifyUser 审核人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleWhitelistAuditException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistAuditService#auditDeclineLeasedVehicleWhitelists(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int auditDeclineLeasedVehicleWhitelists(List<String> ids, String auditNotes, CurrentInfo modifyUser)
            throws LeasedVehicleWhitelistAuditException {
	//待优化实现方式
	executeValidationParameters(ids, modifyUser.getEmpCode());
	for (String id : ids) {
	    executeAuditWhitelists(id, modifyUser, auditNotes, false);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>方法参数异常检测</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午7:35:00
     * @param ids 
     * @param modifyUser
     * @see
     */
    private void executeValidationParameters(List<String> ids, String modifyUser)
	    throws LeasedVehicleWhitelistAuditException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_ID_ISNULL, "ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_AUDITOR_ISNULL, "审核人为空");
	}
    }
    
    /**
     * <p>统一执行审核处理意见</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-3 上午11:08:33
     * @param sourceWhitelistAudit “外请车白名单”
     * @param modifyUser 修改人
     * @param auditNotes 审核备注
     * @param doArgee true：同意；false：拒绝
     * @see
     */
    private void executeAuditWhitelists(String id, CurrentInfo modifyUser,
	    String auditNotes, boolean doArgee) throws LeasedVehicleWhitelistAuditException{
	//白名单数据验证
	WhitelistAuditEntity sourceWhitelistAudit = leasedWhitelistAuditDao.queryLeasedWhitelistAuditBySelective(id);
	if (null == sourceWhitelistAudit) {
	    throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_WHITELIST_ISNULL, "外请车白名单不存在");
	}else{
	    if (StringUtils.equals(FossConstants.INACTIVE, sourceWhitelistAudit.getActive())) {
		throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_REVOCATORY, "外请车白名单已撤回");
	    }
	}
	
	//白名单对应的车辆信息验证
	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
	leasedTruck.setVehicleNo(sourceWhitelistAudit.getVehicleNo());
	leasedTruck = leasedVehicleService.queryLeasedVehicleBySelectiveCondition(leasedTruck);
	if (null == leasedTruck) {
	    throw new LeasedVehicleWhitelistAuditException(LeasedVehicleWhitelistAuditException.AUDIT_VEHICLE_ISNULL, "外请车辆信息不存在");
	}
	    
	//禁用当前白名单记录
	sourceWhitelistAudit.setActive(FossConstants.INACTIVE);
	sourceWhitelistAudit.setModifyUser(modifyUser.getEmpCode());
	leasedWhitelistAuditDao.updateLeasedWhitelistAuditBySelective(sourceWhitelistAudit);
	
	//同步数据到ECS
    List<WhiteListAuditInfo> entityList = new ArrayList<WhiteListAuditInfo> ();
    WhiteListAuditInfo updateInfo = this.toWhiteListAuditInfo(sourceWhitelistAudit, OPTION_DELETE);
    entityList.add(updateInfo);
    this.syncWhiteListAudiToECS(entityList);
    
	//新增一条白名单记录
	WhitelistAuditEntity targetWhitelistAudit = sourceWhitelistAudit;
	sourceWhitelistAudit.setApproveUser(modifyUser.getEmpCode());
	sourceWhitelistAudit.setApproveTime(new Date());
	sourceWhitelistAudit.setAuditNotes(auditNotes);
	targetWhitelistAudit.setCreateUser(modifyUser.getEmpCode());
	targetWhitelistAudit.setCreateUser(modifyUser.getEmpCode());
	
	boolean updateVehicle = true;
	if (doArgee) {
	  //修改当前白名单的"白名单状态"
	    if (StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UNAVAILABLE)) {
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
		leasedTruck.setStatus(FossConstants.INACTIVE);
	    }else if((StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_AVAILABLE))||(StringUtils.equals(
				    sourceWhitelistAudit.getCurrentApplication(), 
				    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY))||(StringUtils.equals(
						    sourceWhitelistAudit.getCurrentApplication(), 
						    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME))){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
		leasedTruck.setStatus(FossConstants.ACTIVE);
	    }/*else if(StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY)){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
		leasedTruck.setStatus(FossConstants.ACTIVE);
	    }else if (StringUtils.equals(
			    sourceWhitelistAudit.getCurrentApplication(), 
			    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME)){
	    	   targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
			    leasedTruck.setStatus(FossConstants.ACTIVE);
	    }*/else{
		updateVehicle = false;
	    }
	}else{
	  //如果"当前申请为申请入库"被拒绝，则当前白名单的"白名单状态为不可用"
	    if(StringUtils.equals(
		    sourceWhitelistAudit.getCurrentApplication(), 
		    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_APPLY)){
		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
		leasedTruck.setStatus(FossConstants.INACTIVE);
	    }//如果"当前申请为   申请修改净空"被拒绝，则当前白名单的"白名单状态为可用" 外请车信息改回审核之前的信息
	    else if(StringUtils.equals(
			    sourceWhitelistAudit.getCurrentApplication(), 
			    DictionaryValueConstants.LEASEDWHITELISTS_APPLY_UPDARE_SELFVOLUME)){
	    	LeasedTruckEntity leasedTruckEntity =new LeasedTruckEntity();
	    	leasedTruckEntity.setVehicleNo(sourceWhitelistAudit.getVehicleNo());
	    	//根据车牌号查询出当前有效的外请车
	    	List<LeasedTruckEntity> leasedTruckList=
	    			leasedVehicleDao.queryLeasedVehicleListBySelective(leasedTruckEntity);
	    	//更改前的外请车信息list
	    	List<LeasedTruckEntity> leasedTruckLists=null;
	    	if(leasedTruckList!=null&&leasedTruckList.size()>0){
	    		leasedTruckEntity.setActive(FossConstants.INACTIVE);
	    		//查询出上一条作废的外请车记录，用于恢复之前的数据（利用上一条记录的修改时间=当前记录的创建时间  车牌号相等做判断）
	    		 leasedTruckLists=
	    				leasedVehicleDao.queryLeasedVehicleListBySelectiveCondition(leasedTruckEntity);	  
	    			//1、禁用当前实体记录 
		    		leasedTruckList.get(0).setModifyUser(modifyUser.getEmpCode());
		    		leasedTruckList.get(0).setModifyUserDept(modifyUser.getCurrentDeptCode());
		    		leasedTruckList.get(0).setActive(FossConstants.INACTIVE);
		    		leasedVehicleDao.updateLeasedVehicleBySelective(leasedTruckList.get(0));
		    		
		    		//同步数据到ECS
		    		List<LeasedTruckInfo> updateList = new ArrayList<LeasedTruckInfo>();
		    		LeasedTruckInfo updateTruckInfo = this.toLeasedTruckInfo(leasedTruckList.get(0), OPTION_DELETE);
		    		updateList.add(updateTruckInfo);
		    		this.syncLeasedDriverToECS(updateList);
	    	}
	    	if(leasedTruckLists!=null&&leasedTruckLists.size()>2){	    	
				//2、产生一条最新启用记录
	    		leasedTruckLists.get(0).setCreateUser(modifyUser.getEmpCode());
	    		leasedTruckLists.get(0).setCreateUserDept(modifyUser.getCurrentDeptCode());
			    //新增时修改人的信息置空
	    		leasedTruckLists.get(0).setModifyUserDept(null);
	    		leasedTruckLists.get(0).setStatus(FossConstants.ACTIVE);
	    		leasedTruckLists.get(0).setModifyUser(null);
	    		leasedVehicleDao.addLeasedVehicle(leasedTruckLists.get(0));
	    		
	    		//同步数据到ECS
	    		List<LeasedTruckInfo> addList = new ArrayList<LeasedTruckInfo>();
	    		LeasedTruckInfo addInfo = this.toLeasedTruckInfo(leasedTruckLists.get(0), OPTION_ADD);
	    		addList.add(addInfo);
	    		this.syncLeasedDriverToECS(addList);
	    		
	    		updateVehicle = false;
	    		targetWhitelistAudit.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_AVAILABLE);
	    		targetWhitelistAudit.setApplyNotes("申请修改净空，未通过");
	    	}
	    	
	    }else{
		updateVehicle = false;
	    }
	}
	if (updateVehicle) {
	    //同步更新对应外请车的"外请车状态"
	    leasedVehicleService.updateLeasedVehicle(leasedTruck, modifyUser, false);
	}
	targetWhitelistAudit.setCurrentApplication(null);
	leasedWhitelistAuditDao.addLeasedWhitelistAudit(targetWhitelistAudit);
	
	//同步数据到ECS
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
     * @param leasedVehicleService the leasedVehicleService to set
     */
    public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
        this.leasedVehicleService = leasedVehicleService;
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
	 * 同步信息到ECS
	 * @author 313353
	 * @param leasedDriver
	 */
	private void syncLeasedDriverToECS(List<LeasedTruckInfo> entitys){
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
		syncLeasedInformationService.syncLeasedTruckToECS(entitys);
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
}
