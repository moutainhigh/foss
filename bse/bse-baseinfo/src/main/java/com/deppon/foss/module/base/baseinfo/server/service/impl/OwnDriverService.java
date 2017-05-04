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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/OwnDriverService.java
 * 
 * FILE NAME        	: OwnDriverService.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnDriverException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司司机”的数据库对应数据访问Service接口实现类：无SUC
 * <p>需要版本控制：已实现</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午6:40:55</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午6:40:55
 * @since
 * @version
 */
public class OwnDriverService implements IOwnDriverService {

    //"公司司机"DAO
    private IOwnDriverDao ownDriverDao;
    
    //"部门组织机构"Servic接口
    private IOrgAdministrativeInfoService orgAdministrativeInfoService; 
    
    //"员工"Servic接口
    private IEmployeeService employeeService;
    
    /**
     * <p>新增一个“公司司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:13:32
     * @param ownDriver “公司司机”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws OwnDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#addOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addOwnDriver(OwnDriverEntity ownDriver, String createUser, boolean ignoreNull) throws OwnDriverException{
	if (null == ownDriver) {
	    throw new OwnDriverException("", "公司司机为空");
	}
	if (StringUtils.isBlank(ownDriver.getEmpCode())) {
	    throw new OwnDriverException("", "工号为空");
	}
	if (StringUtils.isBlank(ownDriver.getEmpName())) {
	    throw new OwnDriverException("", "姓名为空");
	}
	//验证数据
	OwnDriverEntity tempOwnDriver = new OwnDriverEntity();
	tempOwnDriver.setEmpCode(ownDriver.getEmpCode());
	if(CollectionUtils.isNotEmpty(queryOwnDriverListBySelectiveCondition(tempOwnDriver, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE))){
	    throw new OwnDriverException("", "公司司机信息已经存在");
	}else{
	    ownDriver.setCreateUser(createUser);
	}
	if (ignoreNull) {
	    ownDriverDao.addOwnDriverBySelective(ownDriver);
	}else{
	    ownDriverDao.addOwnDriver(ownDriver);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司司机”记录唯一标识删除（物理删除）一条“公司司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:13:37
     * @param ownDriver “公司司机”实体
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws OwnDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#deleteOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteOwnDriver(OwnDriverEntity ownDriver, String modifyUser) throws OwnDriverException{
	if (null == ownDriver) {
	    throw new OwnDriverException("", "公司司机为空");
	}
	if (StringUtils.isBlank(ownDriver.getEmpCode())) {
	    throw new OwnDriverException("", "工号为空");
	}
	//验证数据
	OwnDriverEntity tempOwnDriver = new OwnDriverEntity();
	tempOwnDriver.setEmpCode(ownDriver.getEmpCode());
	List<OwnDriverEntity> ownDriverEntities = queryOwnDriverListBySelectiveCondition(tempOwnDriver, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	if(CollectionUtils.isEmpty(ownDriverEntities)){
	    throw new OwnDriverException("", "公司司机信息不存在");
	}else{
	    ownDriver = ownDriverEntities.get(NumberConstants.NUMERAL_ZORE);
	    ownDriver.setModifyUser(modifyUser);
	    ownDriver.setActive(FossConstants.INACTIVE);
	    ownDriverDao.updateOwnDriverBySelective(ownDriver);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“公司司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:17:50
     * @param ownDriver “公司司机”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws OwnDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#updateOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateOwnDriver(OwnDriverEntity ownDriver, String modifyUser, boolean ignoreNull) throws OwnDriverException{
	if (null == ownDriver) {
	    throw new OwnDriverException("", "公司司机为空");
	}
	if (StringUtils.isBlank(ownDriver.getEmpCode())) {
	    throw new OwnDriverException("", "工号为空");
	}
	if (StringUtils.isBlank(ownDriver.getEmpName())) {
	    throw new OwnDriverException("", "姓名为空");
	}
	//验证数据
	OwnDriverEntity tempOwnDriver = new OwnDriverEntity();
	tempOwnDriver.setEmpCode(ownDriver.getEmpCode());
	List<OwnDriverEntity> ownDriverEntities = queryOwnDriverListBySelectiveCondition(tempOwnDriver, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	if(CollectionUtils.isEmpty(ownDriverEntities)){
//	    throw new OwnDriverException("", "公司司机信息不存在");
	    addOwnDriver(ownDriver, modifyUser, false);
	}else{
	    //1、禁用当前实体记录 
	    tempOwnDriver = ownDriverEntities.get(NumberConstants.NUMERAL_ZORE);
	    tempOwnDriver.setModifyUser(modifyUser);
	    tempOwnDriver.setActive(FossConstants.INACTIVE);
	    ownDriverDao.updateOwnDriverBySelective(tempOwnDriver);
	    //2、产生一条最新启用记录
	    ownDriver.setCreateUser(modifyUser);
	    ownDriverDao.addOwnDriver(ownDriver);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司司机”记录唯一标识查询出一条“公司司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:18:17
     * @param id 记录唯一标识
     * @return “公司司机”实体
     * @throws OwnDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#queryOwnDriver(java.lang.String)
     */
    @Override
    public OwnDriverEntity queryOwnDriver(String id) throws OwnDriverException{
	if (StringUtils.isBlank(id)) {
	    throw new OwnDriverException("", "ID 为空");
	}
	OwnDriverEntity ownDriver = null;
	ownDriver = ownDriverDao.queryOwnDriver(id);
	return ownDriver;
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:24:23
     * @param ownDriver 以“公司司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司司机”实体列表
     * @throws OwnDriverException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#queryOwnDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)
     */
    @Override
    public List<OwnDriverEntity> queryOwnDriverListBySelectiveCondition(
	    OwnDriverEntity ownDriver, int offset, int limit) throws OwnDriverException{
	if (offset < 0 || limit < 0) {
	    return new ArrayList<OwnDriverEntity>();
	}
	if(null == ownDriver){
	    ownDriver = new OwnDriverEntity();
	}
	List<OwnDriverEntity> ownDriverList;
	ownDriverList = ownDriverDao.queryOwnDriverListBySelectiveCondition(ownDriver, offset, limit);
	return ownDriverList;
    }

    /**
     * <p>提供给"中转"模块使用，根据"公司员工工号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午5:20:05
     * @param driverCode 公司员工工号
     * @return DriverAssociationDto封装了的传送对象
     * @throws OwnDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#queryOwnDriverAssociationDtoByEmployeeCode(java.lang.String)
     */
    @Override
    public DriverAssociationDto queryOwnDriverAssociationDtoByDriverCode(
            String driverCode) throws OwnDriverException {
	if (StringUtils.isBlank(driverCode)) {
	    throw new OwnDriverException("", "员工工号为空");
	}
	List<OwnDriverEntity> ownDriverTempList = null;
	DriverAssociationDto driverAssociationDto = null;
	OwnDriverEntity ownDriverParameter = new OwnDriverEntity();
	ownDriverParameter.setEmpCode(driverCode);
	ownDriverTempList = ownDriverDao.queryOwnDriverListBySelectiveCondition(ownDriverParameter, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);

	if (CollectionUtils.isNotEmpty(ownDriverTempList)) {
	    driverAssociationDto = new DriverAssociationDto();
	    OwnDriverEntity ownDriver = ownDriverTempList.get(NumberConstants.NUMERAL_ZORE);
	    
	    //公司司机基础信息
	    driverAssociationDto.setDriverCode(ownDriver.getEmpCode());
	    driverAssociationDto.setDriverName(ownDriver.getEmpName());
	    driverAssociationDto.setDriverPhone(ownDriver.getEmpPhone());
	    
	    //所属部门(编码、名称）
	    String organizationCode = ownDriver.getOrgId();
	    OrgAdministrativeInfoEntity organization = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(organizationCode);
	    if (null != organization) {
		driverAssociationDto.setDriverOrganizationCode(organization.getCode());
		driverAssociationDto.setDriverOrganizationName(organization.getName());
		
		//所属部门负责人信息
		String organizationPprincipalCode = organization.getPrincipalNo();
		EmployeeEntity organizationEmployee = employeeService.queryEmployeeByEmpCode(organizationPprincipalCode);
		if (null != organizationEmployee) {
		    driverAssociationDto.setDriverOrganizationLeaderCode(organizationEmployee.getEmpCode());
		    driverAssociationDto.setDriverOrganizationLeaderName(organizationEmployee.getEmpName());
		    driverAssociationDto.setDriverOrganizationLeaderPhone(organizationEmployee.getPhone());
		}
	    }
	}
	return driverAssociationDto;
    }

    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午7:04:42
     * @param driverName 司机姓名集合
     * @return DriverBaseDto封装的对象集合
     * @throws OwnDriverException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService#queryAutoCompleteDriverBaseDtoByDriverName(java.lang.String)
     */
    @Override
    public List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(
            String driverName) throws OwnDriverException {
	if (StringUtils.isBlank(driverName)) {
	    throw new OwnDriverException("", "员工姓名为空");
	}
	List<DriverBaseDto> driverBaseDtos = null;
	driverBaseDtos = ownDriverDao.queryOwnDriverBaseDtosByDriverNames(new String[]{driverName});
        return driverBaseDtos;
    }
    
    /**
     * @param ownDriverDao the ownDriverDao to set
     */
    public void setOwnDriverDao(IOwnDriverDao ownDriverDao) {
        this.ownDriverDao = ownDriverDao;
    }
    
    /**
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
