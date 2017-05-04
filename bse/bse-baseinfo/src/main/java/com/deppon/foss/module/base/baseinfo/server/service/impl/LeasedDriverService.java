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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedDriverService.java
 * 
 * FILE NAME        	: LeasedDriverService.java
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
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.ecs.inteface.domain.LeasedDriverInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLeasedInformationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车司机”的数据库对应数据访问Service接口实现类：SUC-211 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午11:06:01</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午11:06:01
 * @since
 * @version
 */
public class LeasedDriverService implements ILeasedDriverService {
	
	//同步给ECS的操作类型
	private static final String OPTION_ADD = "1";
	//同步给ECS的操作类型
	private static final String OPTION_UPDATE = "2";
	//同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";

	//"外请司机"DAO
    private ILeasedDriverDao leasedDriverDao;
    
    //"外请司机白名单"Service
    private ILeasedDriverWhitelistService leasedDriverWhitelistService;
    
    //同步信息到ECS的service
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
     * <p>新增一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param leasedDriver “外请车司机”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#addLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    @Transactional
    public int addLeasedDriver(LeasedDriverEntity leasedDriver,
	    String createUser, boolean ignoreNull) throws LeasedDriverException {
	if (null == leasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.PARAMETER_ISNULL, "外请司机为空");
	}
	if (StringUtils.isBlank(leasedDriver.getIdCard())) {
	    throw new LeasedDriverException(LeasedDriverException.ID_CARD_ISNULL, "外请司机身份证号为空");
	}else{
		leasedDriver.setIdCard(StringUtils.trim(leasedDriver.getIdCard()));
	}
	if (StringUtils.isBlank(createUser)) {
	    throw new LeasedDriverException(LeasedDriverException.CREATER_ISNULL, "创建人信息为空");
	}
	if(StringUtils.isNotBlank(leasedDriver.getDriverName())){
		leasedDriver.setDriverName(StringUtils.trim(leasedDriver.getDriverName()));
	}
	updateLeasedDriverWhitelistsByCardId(leasedDriver,createUser);
	//验证对应身份证号"外请司机"是否已经存在
	LeasedDriverEntity oldLeasedDriver, tempLeasedDriver = new LeasedDriverEntity();
	tempLeasedDriver.setIdCard(leasedDriver.getIdCard());
	oldLeasedDriver = queryLeasedDriverBySelective(tempLeasedDriver);
	if (null != oldLeasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.DRIVER_IS_NOT_NULL, "外请司机已经存在");
	}else{
	    leasedDriver.setCreateUser(createUser);
	    //默认新增的外请司机为"不可用"状态
	    leasedDriver.setStatus(FossConstants.INACTIVE);
			if (ignoreNull) {
				int result = leasedDriverDao.addLeasedDriverBySelective(leasedDriver);

				// 同步信息到ECS
				if (result > 0) {
					LeasedDriverInfo addInfo = this.toLeasedDriverInfo(leasedDriver, OPTION_ADD);
					this.syncLeasedDriverToECS(addInfo);
				}
			} else {
				int result = leasedDriverDao.addLeasedDriver(leasedDriver);

				// 同步信息到ECS
				if (result > 0) {
					LeasedDriverInfo addInfo = this.toLeasedDriverInfo(leasedDriver, OPTION_ADD);
					this.syncLeasedDriverToECS(addInfo);
				}
			}
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * 修改司机白名单信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-6-6 下午6:56:37
     */
    private void updateLeasedDriverWhitelistsByCardId(LeasedDriverEntity leasedDriver,String modifyUser){
    	WhitelistAuditEntity whitelistAuditEntity = new WhitelistAuditEntity();
    	whitelistAuditEntity.setDriverIdCard(leasedDriver.getIdCard());
    	whitelistAuditEntity.setQualification(leasedDriver.getQualification());
    	whitelistAuditEntity.setDriverName(leasedDriver.getDriverName());
    	whitelistAuditEntity.setDriverPhone(leasedDriver.getDriverPhone());
    	whitelistAuditEntity.setDriverLicense(leasedDriver.getDriverLicense());
    	whitelistAuditEntity.setModifyUser(modifyUser);
    	leasedDriverWhitelistService.updateLeasedDriverWhitelistsByCardId(whitelistAuditEntity);
    }
    /**
     * <p>根据“外请车司机”记录标识集合删除（物理删除）多条“外请车司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:42
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#deleteLeasedDriver(java.uitl.List)
     */
    @Override
    @Transactional
	public int deleteLeasedDriver(List<String> ids, String modifyUser)
			throws LeasedDriverException {
		if (CollectionUtils.isEmpty(ids)) {
			throw new LeasedDriverException(LeasedDriverException.ID_ISNULL,
					"ID 为空");
		}
		if (StringUtils.isBlank(modifyUser)) {
			throw new LeasedDriverException(
					LeasedDriverException.MODIFIER_ISNULL, "修改人信息为空");
		}

		// 待优化实现
		for (String id : ids) {
			// 验证对应"身份证号外请司机"是否已经存在
			LeasedDriverEntity leasedDriver = leasedDriverDao
					.queryLeasedDriverBySelective(id);
			if (null == leasedDriver) {
				throw new LeasedDriverException(
						LeasedDriverException.DRIVER_ISNULL, "外请司机不存在");
			} else {
				updateLeasedDriverWhitelists(modifyUser, leasedDriver);
			}
		}
		return FossConstants.SUCCESS;
	}

	private void updateLeasedDriverWhitelists(String modifyUser,
			LeasedDriverEntity leasedDriver) {
		// 检测对应"外请司机白名单"是否存在，存在则同步修改为"不可用"，记录"禁用"
		WhitelistAuditEntity whitelistAuditParameter = new WhitelistAuditEntity();
		whitelistAuditParameter.setDriverIdCard(leasedDriver.getIdCard());
		WhitelistAuditEntity whitelistAudit = leasedDriverWhitelistService
				.queryLeasedDriverWhitelistsBySelectiveCondition(whitelistAuditParameter);

		boolean flage = true;// 默认 “外请挂车”、“外请拖头”、“外请厢式车” 可以操作“作废”功能
		if (null != whitelistAudit) {
			/**
			 * 1、“外请司机在“入库”后(即 不是未入库的状态)，不能操作“作废”功能 2、如果登陆人员角色为“运输管理员”，可以操作作废功能
			 */

			if (StringUtils.equals(
					DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_APPLY,
					whitelistAudit.getStatus())) {
				whitelistAudit.setCurrentApplication(null);
				whitelistAudit
						.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
				whitelistAudit.setActive(FossConstants.INACTIVE);
				leasedDriverWhitelistService.updateLeasedDriverWhitelists(
						whitelistAudit, false);
			} else {
				flage = false;
				Set<String> roleIds = FossUserContext.getCurrentUser()
						.getRoleids();
				for (String roleId : roleIds) {
					// TRANS_ASSISTANT“运输管理员”角色编码
					if ("TRANS_ASSISTANT".equals(roleId)) {
						flage = true;
						whitelistAudit.setCurrentApplication(null);
						whitelistAudit
								.setStatus(DictionaryValueConstants.LEASEDWHITELISTS_AUDIT_UNAVAILABLE);
						whitelistAudit.setActive(FossConstants.INACTIVE);
						leasedDriverWhitelistService
								.updateLeasedDriverWhitelists(whitelistAudit,
										false);
					}
				}
			}
		}
		/**
		 * “外请司机在“入库”后，不能操作“作废”功能
		 */
		if(flage){
			//逻辑删除，直接“停用”
			leasedDriver.setModifyUser(modifyUser);
			leasedDriver.setActive(FossConstants.INACTIVE);
			leasedDriver.setStatus(FossConstants.INACTIVE);
			int result = leasedDriverDao.updateLeasedDriverBySelective(leasedDriver);
			
		    //同步信息到ECS
			if(result > 0){
				LeasedDriverInfo updateInfo = this.toLeasedDriverInfo(leasedDriver, OPTION_DELETE);
				this.syncLeasedDriverToECS(updateInfo);
			}
		 }else{
			 throw new LeasedDriverException(LeasedDriverException.IS_NOT_DELETE, "该信息已入库，无法作废！");
		 }
	}
    
    /**
     * <p>修改一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param leasedDriver “外请车司机”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#updateLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    @Transactional
    public int updateLeasedDriver(LeasedDriverEntity leasedDriver,
	    String modifyUser, boolean ignoreNull) throws LeasedDriverException {
	
	if (null == leasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.PARAMETER_ISNULL, "外请司机为空");
	}
	if (StringUtils.isBlank(leasedDriver.getId())) {
	    throw new LeasedDriverException(LeasedDriverException.ID_ISNULL, "ID 为空");
	}
	if (StringUtils.isBlank(leasedDriver.getIdCard())) {
	    throw new LeasedDriverException(LeasedDriverException.ID_CARD_ISNULL, "外请司机身份证号为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedDriverException(LeasedDriverException.MODIFIER_ISNULL, "修改人信息为空");
	}
	
	//验证对应"身份证号外请司机"是否已经存在
	LeasedDriverEntity oldLeasedDriver, tempLeasedDriver = new LeasedDriverEntity();
	tempLeasedDriver.setIdCard(leasedDriver.getIdCard());
	oldLeasedDriver = queryLeasedDriverBySelective(tempLeasedDriver);
	updateLeasedDriverWhitelistsByCardId(leasedDriver,modifyUser);
	if (null == oldLeasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.DRIVER_ISNULL, "外请司机不存在");
	}else{
	    leasedDriver.setModifyUser(modifyUser);
	    if (ignoreNull) {
				int result = leasedDriverDao.updateLeasedDriverBySelective(leasedDriver);
				// 同步信息到ECS
				if (result > 0) {
					LeasedDriverInfo updateInfo = this.toLeasedDriverInfo(leasedDriver, OPTION_UPDATE);
					this.syncLeasedDriverToECS(updateInfo);
				}
			} else {
				int result = leasedDriverDao.updateLeasedDriver(leasedDriver);
				// 同步信息到ECS
				if (result > 0) {
					LeasedDriverInfo updateInfo = this.toLeasedDriverInfo(leasedDriver, OPTION_UPDATE);
					this.syncLeasedDriverToECS(updateInfo);
				}
			}
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“外请车司机”记录唯一标识查询出一条“外请车司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:45
     * @param id 记录唯一标识
     * @return “外请车司机”实体
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLeasedDriverBySelective(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedDriverEntity queryLeasedDriverBySelective(String id) throws LeasedDriverException{
	if (StringUtils.isBlank(id)) {
	    throw new LeasedDriverException(LeasedDriverException.ID_ISNULL, "ID 为空");
	}
	LeasedDriverEntity leasedDriver = null;
	leasedDriver = leasedDriverDao.queryLeasedDriverBySelective(id);
	return leasedDriver;
    }

    /**
     * <p>根据条件有选择的检索出符合条件一个的“外请车司机”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:23:53
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体
     * @throws LeasedDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public LeasedDriverEntity queryLeasedDriverBySelective(
            LeasedDriverEntity leasedDriver) throws LeasedDriverException {
	if (null == leasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.PARAMETER_ISNULL, "司机参数信息为空");
	}
	return leasedDriverDao.queryLeasedDriverBySelective(leasedDriver);
    }
    
    /**
     * <p>
     * 根据条件有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param leasedDriver
     *            以“外请车司机”实体承载的条件参数实体
     * @return 分页的Action和Service通讯封装对象
     * @throws LeasedDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLeasedDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public List<LeasedDriverEntity> queryLeasedDriverListBySelectiveCondition(
            LeasedDriverEntity leasedDriver) throws LeasedDriverException {
	if (null == leasedDriver) {
	    throw new LeasedDriverException(LeasedDriverException.PARAMETER_ISNULL, "司机参数信息为空");
	}
	int offset = NumberConstants.NUMERAL_ZORE;
	int limit = Integer.MAX_VALUE;
        return leasedDriverDao.queryLeasedDriverListBySelectiveCondition(leasedDriver, offset, limit);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param ownTruck 以“外请车司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象 
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLeasedDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryLeasedDriverListBySelectiveCondition(
	    LeasedDriverEntity leasedDriver, int offset, int limit) throws LeasedDriverException{
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if(null == leasedDriver){
	    leasedDriver = new LeasedDriverEntity();
	}
	List<LeasedDriverEntity> leasedDriverList = leasedDriverDao.queryLeasedDriverListBySelectiveCondition(leasedDriver, offset, limit);
	if (CollectionUtils.isNotEmpty(leasedDriverList)) {
	    Long totalCount = leasedDriverDao.queryLeasedDriverRecordCountBySelectiveCondition(leasedDriver);
	    paginationDto.setPaginationDtos(leasedDriverList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto; 
    }

    /**
     * <p>提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午5:40:33
     * @param vehicleNo 外请车车牌号
     * @return DriverAssociationDto封装了的传送对象集合
     * @throws LeasedDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLesasedDriverAssociationDtoByVehicleNo(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<DriverAssociationDto> queryLesasedDriverAssociationDtoByVehicleNo(
            String vehicleNo) throws LeasedDriverException {

	if (StringUtils.isBlank(vehicleNo)) {
	    throw new LeasedDriverException(LeasedDriverException.DTO_VEHICLENO_ISNULL, "车牌号为空");
	}
	List<DriverBaseDto> driverBaseDtos = null;
	List<DriverAssociationDto> driverAssociationDtos = new ArrayList<DriverAssociationDto>();
	driverBaseDtos = leasedDriverDao.queryLeasedDriverBaseDtosByVehicleNos(new String[]{vehicleNo});

	for (DriverBaseDto driverBaseDto : driverBaseDtos) {
	    DriverAssociationDto driverAssociationDto = new DriverAssociationDto();
	    try {
		//"外请司机"不具有的部门和部门负责人相关信息
		BeanUtils.copyProperties(driverAssociationDto, driverBaseDto);
	    } catch (Exception e) {
		throw new OwnVehicleException(LeasedDriverException.DTO_COPY_PROPERTIES, "复制司机数据失败");
	    }
	    driverAssociationDtos.add(driverAssociationDto);
	}
        return driverAssociationDtos;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午7:04:42
     * @param driverName 司机姓名
     * @return DriverBaseDto封装的对象集合
     * @throws LeasedDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryAutoCompleteDriverBaseDtoByDriverName(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(
            String driverName) throws LeasedDriverException {
	if (StringUtils.isBlank(driverName)) {
	    throw new LeasedDriverException(LeasedDriverException.DTO_DRIVER_ISNULL, "司机姓名为空");
	}
	List<DriverBaseDto> driverBaseDtos = null;
	driverBaseDtos = leasedDriverDao.queryLeasedDriverBaseDtosByDriverNames(new String[]{driverName});
        return driverBaseDtos;
    }
    
    /**
     * @param leasedDriverDao the leasedDriverDao to set
     */
    public void setLeasedDriverDao(ILeasedDriverDao leasedDriverDao) {
        this.leasedDriverDao = leasedDriverDao;
    }

    /**
     * @param leasedDriverWhitelistService the leasedDriverWhitelistService to set
     */
    public void setLeasedDriverWhitelistService(
    	ILeasedDriverWhitelistService leasedDriverWhitelistService) {
        this.leasedDriverWhitelistService = leasedDriverWhitelistService;
    }

	/** 
	 * 提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
	 *
	 * @author foss-qiaolifeng
	 * @date 2013-7-8 下午3:00:25
	 * @param vehicleNo
	 *            外请车车牌号
	 * @return DriverAssociationDto封装了的传送对象集合
	 * @throws LeasedDriverException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService#queryLesasedDriverAssociationDtoByTruckVehicleNo(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DriverAssociationDto> queryLesasedDriverAssociationDtoByTruckVehicleNo(
			String vehicleNo) throws LeasedDriverException {
		if (StringUtils.isBlank(vehicleNo)) {
		    throw new LeasedDriverException(LeasedDriverException.DTO_VEHICLENO_ISNULL, "车牌号为空");
		}
		List<DriverBaseDto> driverBaseDtos = null;
		List<DriverAssociationDto> driverAssociationDtos = new ArrayList<DriverAssociationDto>();
		driverBaseDtos = leasedDriverDao.queryLeasedDriverBaseDtosByTruckVehicleNos(new String[]{vehicleNo});

		for (DriverBaseDto driverBaseDto : driverBaseDtos) {
		    DriverAssociationDto driverAssociationDto = new DriverAssociationDto();
		    try {
			//"外请司机"不具有的部门和部门负责人相关信息
			BeanUtils.copyProperties(driverAssociationDto, driverBaseDto);
		    } catch (Exception e) {
			throw new OwnVehicleException(LeasedDriverException.DTO_COPY_PROPERTIES, "复制司机数据失败");
		    }
		    driverAssociationDtos.add(driverAssociationDto);
		}
	        return driverAssociationDtos;
	}
	
	/**
	 * 同步信息到ECS
	 * @author 313353
	 * @param leasedDriver
	 */
	private void syncLeasedDriverToECS(LeasedDriverInfo leasedDriver){
		if(null == leasedDriver){
			return;
		}
		List<LeasedDriverInfo> entitys = new ArrayList<LeasedDriverInfo>();
		entitys.add(leasedDriver);
		this.syncLeasedDriverToECS(entitys);
	}
	
	/**
	 * 同步信息到ECS
	 * @author 313353
	 * @param leasedDriver
	 */
	private void syncLeasedDriverToECS(List<LeasedDriverInfo> entitys){
		syncLeasedInformationService.syncLeasedDriverToECS(entitys);
	}
	
	/**
	 * foss实体类转化为esb实体类 外请司机
	 * 
	 * @param fossEntity
	 * @return
	 */
	private LeasedDriverInfo toLeasedDriverInfo(LeasedDriverEntity fossEntity, String optionType) {
		if (fossEntity == null) {
			return null;
		}
		LeasedDriverInfo esbInfo = new LeasedDriverInfo();
		esbInfo.setId(fossEntity.getId());
		esbInfo.setDriverName(fossEntity.getDriverName());
		esbInfo.setAddress(fossEntity.getAddress());
		esbInfo.setDriverPhone(fossEntity.getDriverPhone());
		esbInfo.setVehicleNo(fossEntity.getVehicleNo());
		esbInfo.setIdCard(fossEntity.getIdCard());
		esbInfo.setRelativeName(fossEntity.getRelativeName());
		esbInfo.setRelativePhone(fossEntity.getRelativePhone());
		esbInfo.setDriverLicense(fossEntity.getDriverLicense());
		esbInfo.setQualification(fossEntity.getQualification());
		esbInfo.setLicenseType(fossEntity.getLicenseType());
		esbInfo.setBeginTime(fossEntity.getBeginTime());
		esbInfo.setExpirationDate(fossEntity.getExpirationDate());
		esbInfo.setNotes(fossEntity.getNotes());
		esbInfo.setStatus(fossEntity.getStatus());
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		esbInfo.setModifyTime(fossEntity.getModifyDate());
		esbInfo.setActive(fossEntity.getActive());
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());
		esbInfo.setOptionType(optionType);
		return esbInfo;
	}

}

