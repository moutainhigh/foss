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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/OwnVehicleService.java
 * 
 * FILE NAME        	: OwnVehicleService.java
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
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleRegionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnTractorsException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVanException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司车辆（厢式车、挂车、拖头、骨架车）”的数据库对应数据访问Service接口实现类：SUC-785
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-10-16 下午3:30:22
 * @since
 * @version
 */
public class OwnVehicleService implements IOwnVehicleService {
    
	/**
	 * 声明日志对象
	 */
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnVehicleService.class);
    
	/**
	 * 公司车辆接口DAO
	 */
    protected IOwnVehicleDao ownVehicleDao;
    
	/**
	 * 数据字典值Service接口
	 */
    private IDataDictionaryValueService dataDictionaryValueService;
    
	/**
	 * 车辆车型Service接口
	 */
    private ILeasedVehicleTypeService leasedVehicleTypeService;
    
	/**
	 * 定人定区Service接口
	 */
    private IRegionalVehicleService regionalVehicleService;
    
	/**
	 * 车队组织机构Servic接口
	 */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

	/**
	 * 部门组织机构Servic接口
	 */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService; 

	/**
	 * 员工Servic接口
	 */
    private IEmployeeService employeeService;
    
    /**
     * <p>从LMS同步一个“公司车辆和停车计划信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:15
     * @param ownTruck “公司车辆”实体
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#synchronousOwnVehiclePlanByLMS(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String)
     */
    @Override
    @Transactional
    public int synchronousOwnVehiclePlanByLMS(OwnTruckEntity ownTruck, String modifyUser) throws OwnVehicleException {
	if (null == ownTruck) {
	    throw new OwnTractorsException("", "车辆信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnTractorsException("", "车牌号为空");
	}
	if (StringUtils.isBlank(ownTruck.getId())) {
	    throw new OwnTractorsException("", "ID为空");
	}
	
	//验证数据
	OwnTruckEntity oldOwnTruck, tempOwnTruck = new OwnTruckEntity();
	tempOwnTruck.setVehicleNo(ownTruck.getVehicleNo());
	oldOwnTruck = queryOwnVehicleBySelective(tempOwnTruck, null);
	if (null == oldOwnTruck) {
	    throw new OwnTractorsException("", "公司车辆信息不存在");
	}else{
	    //1、禁用当前实体记录 
	    oldOwnTruck.setModifyUser(modifyUser);
	    oldOwnTruck.setActive(FossConstants.INACTIVE);
	    ownVehicleDao.updateOwnVehicleBySelective(oldOwnTruck);
	    
	    //2、产生一条最新启用记录
	    ownTruck.setCreateUser(modifyUser);
	    ownTruck.setVehicleType(oldOwnTruck.getVehicleType());
	    ownVehicleDao.addOwnVehicle(ownTruck);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>新增一个“公司车辆（厢式车、挂车、拖头、骨架车）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:37
     * @param ownTruck “公司车辆（厢式车、挂车、拖头、骨架车）”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @param vehicleType 厢式车/挂车/拖头/骨架车/null
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#addOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String, boolean, java.lang.String)
     */
    @Override
    @Transactional
    public int addOwnVehicle(OwnTruckEntity ownTruck, String createUser,
            boolean ignoreNull, String vehicleType) throws OwnVehicleException {
	if (null == ownTruck) {
	    throw new OwnVanException("", "车辆信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnTractorsException("", "车牌号为空");
	}
	if (StringUtils.isBlank(vehicleType)) {
	    throw new OwnTractorsException("", "车辆类型不存在");
	}
	//验证数据
	OwnTruckEntity oldOwnTruck, tempOwnTruck = new OwnTruckEntity();
	tempOwnTruck.setVehicleNo(ownTruck.getVehicleNo());
	oldOwnTruck = queryOwnVehicleBySelective(tempOwnTruck, vehicleType);
	if (null != oldOwnTruck) {
	    throw new OwnTractorsException("", "公司车辆信息已经存在");
	}else{
	    ownTruck.setCreateUser(createUser);
	    //强制设置“车辆类型”为拖头值代码 
	    ownTruck.setVehicleType(vehicleType);
	    if (ignoreNull) {
		 ownVehicleDao.addOwnVehicleBySelective(ownTruck);
	    }else{
		 ownVehicleDao.addOwnVehicle(ownTruck);
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“公司车辆（厢式车、挂车、拖头、骨架车）”记录唯一标识作废（逻辑删除）一条“公司车辆（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:40
     * @param ownTruck “公司车辆（厢式车、挂车、拖头、骨架车）”实体
     * @param modifyUser 修改人
     * @param vehicleType 厢式车/挂车/拖头/骨架车/null
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#deleteOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteOwnVehicle(OwnTruckEntity ownTruck, String modifyUser,
            String vehicleType) throws OwnVehicleException {
	if (null == ownTruck) {
	    throw new OwnVanException("", "车辆信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnVanException("", "车牌号为空");
	}
	if (StringUtils.isBlank(ownTruck.getId())) {
	    throw new OwnTractorsException("", "ID为空");
	}
	if (StringUtils.isBlank(vehicleType)) {
	    throw new OwnTractorsException("", "车辆类型不存在");
	}
	//验证数据
	OwnTruckEntity oldOwnTruck, tempOwnTruck = new OwnTruckEntity();
	tempOwnTruck.setVehicleNo(ownTruck.getVehicleNo());
	oldOwnTruck = queryOwnVehicleBySelective(tempOwnTruck, vehicleType);
	if (null == oldOwnTruck) {
	    throw new OwnTractorsException("", "公司车辆信息不存在");
	}else{
	    oldOwnTruck.setModifyUser(modifyUser);
	    oldOwnTruck.setActive(FossConstants.INACTIVE);
	    ownVehicleDao.updateOwnVehicleBySelective(oldOwnTruck);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>修改一个“公司车辆（厢式车、挂车、拖头、骨架车）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:43
     * @param ownTruck “公司车辆（厢式车、挂车、拖头、骨架车）”实体
     * @param modifyUser 修改人
     * @param vehicleType 厢式车/挂车/拖头/骨架车/null
     * @return 1：成功；-1：失败
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#updateOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int updateOwnVehicle(OwnTruckEntity ownTruck, String modifyUser,
            String vehicleType) throws OwnVehicleException {
	if (null == ownTruck) {
	    throw new OwnVanException("", "厢式车信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnVanException("", "车牌号为空");
	}
	if (StringUtils.isBlank(vehicleType)) {
	    throw new OwnTractorsException("", "车辆类型不存在");
	}
	//验证数据
	OwnTruckEntity oldOwnTruck, tempOwnTruck = new OwnTruckEntity();
	tempOwnTruck.setVehicleNo(ownTruck.getVehicleNo());
	oldOwnTruck = queryOwnVehicleBySelective(tempOwnTruck, vehicleType);
	if (null == oldOwnTruck) {
//	    throw new OwnTractorsException("", "公司拖头信息不存在");
	    //如果信息不存在，就新增
	    addOwnVehicle(ownTruck,modifyUser,false,vehicleType);
	}else{
	    //1、禁用当前实体记录 
	    oldOwnTruck.setModifyUser(modifyUser);
	    oldOwnTruck.setActive(FossConstants.INACTIVE);
//	    ownVehicleDao.updateOwnVehicleBySelective(oldOwnTruck);
	    ownVehicleDao.deleteOwnVehicleByVehicleNo(oldOwnTruck);
	    //2、产生一条最新启用记录
	    ownTruck.setCreateUser(modifyUser);
	    ownTruck.setVehicleType(vehicleType);
	    ownVehicleDao.addOwnVehicle(ownTruck);
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“公司车辆（厢式车、挂车、拖头、骨架车）”记录唯一标识查询出一条“公司车辆（厢式车、挂车、拖头、骨架车）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:47
     * @param id 记录唯一标识
     * @return “公司车辆（厢式车、挂车、拖头、骨架车）”实体
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleBySelective(java.lang.String)
     */
    @Override
    public OwnTruckEntity queryOwnVehicleBySelective(String id)
            throws OwnVehicleException {
	if (StringUtils.isBlank(id)) {
	    throw new OwnVehicleException("", "ID 为空");
	}
        return ownVehicleDao.queryOwnVehicleBySelective(id);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头、骨架车）”单个实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-22 上午9:28:20
     * @param ownTruck 以“公司车辆（厢式车、挂车、拖头、骨架车）”实体承载的条件参数实体
     * @param vehicleType 车辆类型数据字典对应值代码（厢式车、挂车、拖头、骨架车）
     * @return 符合条件的“公司车辆（厢式车、挂车、拖头、骨架车）”实体
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public OwnTruckEntity queryOwnVehicleBySelective(
            OwnTruckEntity ownTruck, String vehicleType) throws OwnVehicleException {
	if (null == ownTruck) {
	    throw new OwnVehicleException("", "公司车辆为空");
	}/*else{
	    //设置“车辆类型”
//	    executeValidationVehicleType(ownTruck, vehicleType);
	}*/
        return ownVehicleDao.queryOwnVehicleBySelective(ownTruck);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“公司车辆（厢式车、挂车、拖头、骨架车）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param ownTruck 以“公司车辆（厢式车、挂车、拖头、骨架车）”实体承载的条件参数实体
     * @param vehicleType 车辆类型数据字典对应值代码（厢式车、挂车、拖头、骨架车）
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象 
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryOwnVehicleListBySelectiveCondition(
	    OwnTruckEntity ownTruck, String vehicleType, int offset, int limit)
	    throws OwnVehicleException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}else{
	    if(null == ownTruck){
		ownTruck = new OwnTruckEntity();
	    }
	    //设置“车辆类型”
	    executeValidationVehicleType(ownTruck, vehicleType);
	}
	List<OwnTruckEntity> ownTruckList = ownVehicleDao.queryOwnVehicleListBySelectiveCondition(ownTruck, offset, limit);
	if (CollectionUtils.isNotEmpty(ownTruckList)) {
	    List<OwnVehicleDto> ownVehicleList = new ArrayList<OwnVehicleDto>();
	    OrgAdministrativeInfoEntity orgAdministrativeInfo = null;
	    OwnVehicleDto ownVehicleDto = null;
	    for (OwnTruckEntity truck : ownTruckList) {
		try {
		    ownVehicleDto = new OwnVehicleDto(truck);
		} catch (OwnVehicleException e) {
		    throw new OwnVehicleException("", "公司车辆Entity数据复制到Dto失败", e);
		}
		//所属部门(编码、名称）走缓存
		orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(truck.getOrgId());
		if(null != orgAdministrativeInfo){
		    ownVehicleDto.setOrgName(orgAdministrativeInfo.getName());
		}
		//车辆不可用原因代码
		if(StringUtils.isNotBlank(truck.getUnavilableCode())){
		    DataDictionaryValueEntity dictionaryValue = null;
		    dictionaryValue = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.VEHICLE_STOP_REASON_TERMSCODE, truck.getUnavilableCode());
		    if(null != dictionaryValue){
			ownVehicleDto.setUnavilableCode(dictionaryValue.getValueCode());
			ownVehicleDto.setUnavilableName(dictionaryValue.getValueName());
		    }
		}
		ownVehicleList.add(ownVehicleDto);
	    }
	    Long totalCount = ownVehicleDao.queryOwnVehicleCountBySelectiveCondition(ownTruck);
	    paginationDto.setPaginationDtos(ownVehicleList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }

    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午4:28:11
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleAssociationDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<VehicleAssociationDto> queryOwnVehicleAssociationDtosByVehicleNos(
            String[] vehicleNos) throws OwnVehicleException {
	if (ArrayUtils.isEmpty(vehicleNos)) {
	    throw new OwnVehicleException("", "车牌号为空");
	}
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<String> vehicleNoList = new ArrayList<String>(vehicleNos.length);
	for (String vehicleNo : vehicleNos) {
	    vehicleNoList.add(vehicleNo);
	}
	PaginationDto paginationDto = queryVehicleAssociationDtoListPaginationByCondition(
		null, vehicleNoList, null, null, NumberConstants.NUMERAL_ZORE,
		NumberConstants.NUMERAL_ONE, null);
	if(null != paginationDto){
	    vehicleAssociationDtos = paginationDto.getPaginationDtos();
	}
        return vehicleAssociationDtos;
    }
    
    /**
     * <p>提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆、所属定人定区的相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 上午11:34:30
     * @param vehicleNo 车牌号
     * @param vehicleTypeCode 车型编码
     * @param orgCode 组织编码
     * @param regionType 大区（ComnConst.REGION_NATURE_BQ）/小区（ComnConst.REGION_NATURE_SQ）
     * @param goodsType 接货（DictionaryValueConstants.REGION_TYPE_PK）/送货（DictionaryValueConstants.REGION_TYPE_DE）
     * @return OwnVehicleRegionDto封装了传送对象集合
     * @throws OwnVehicleException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleRegionDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<OwnVehicleRegionDto> queryOwnVehicleRegionDtosByCondition(
            String vehicleNo, String vehicleTypeCode, String orgCode,
            String regionType, String goodsType) throws OwnVehicleException {
	if (StringUtils.isEmpty(vehicleNo) && StringUtils.isEmpty(vehicleTypeCode) && StringUtils.isEmpty(orgCode)) {
	    throw new OwnVehicleException("", "车牌号、车型编码和组织编码不能全部为空");
	}
	List<OwnVehicleRegionDto> vehicleRegionDtos = new ArrayList<OwnVehicleRegionDto>();
	OwnTruckEntity ownTruckParameter = new OwnTruckEntity();
	ownTruckParameter.setVehicleNo(vehicleNo);
	ownTruckParameter.setOrgId(orgCode);
	ownTruckParameter.setVehcleLengthCode(vehicleTypeCode);
	List<OwnTruckEntity> ownTruckList = ownVehicleDao.queryOwnVehicleListBySelectiveCondition(ownTruckParameter, 0, Integer.MAX_VALUE);
	
		if (CollectionUtils.isNotEmpty(ownTruckList)) {
			for (OwnTruckEntity ownTruck : ownTruckList) {
				// 车辆基础数据
				OwnVehicleRegionDto vehicleRegionDto = new OwnVehicleRegionDto();
				vehicleRegionDto.setVehicleNo(ownTruck.getVehicleNo());
				vehicleRegionDto.setVehicleSelfVolume(ownTruck.getSelfVolume());
				vehicleRegionDto.setVehicleDeadLoad(ownTruck.getDeadLoad());

				String vehicleType = ownTruck.getVehicleType();
				if (StringUtils.isNotBlank(vehicleType)) {
					getOwnVehicleRegionDto(ownTruck, vehicleRegionDto,
							vehicleType);
				} else {
					if (LOGGER.isWarnEnabled()) {
						LOGGER.warn("警告：数据不完整错误，对应的车辆类型不存在");
					}
				}

				// 查询车辆定人定区走缓存
				List<String> regionParameter = Arrays.asList(ownTruck
						.getVehicleNo());
				List<RegionVehicleEntity> regionList = regionalVehicleService
						.queryRegionVehiclesByParam(regionParameter, goodsType,
								regionType);
				if (CollectionUtils.isNotEmpty(regionList)) {
					for (RegionVehicleEntity region : regionList) {
						vehicleRegionDto.setVehicleRegionCode(region
								.getRegionCode());
						vehicleRegionDto.setVehicleRegionName(region
								.getRegionName());
						vehicleRegionDto.setVehicleGoodsType(region
								.getRegionType());
						vehicleRegionDto.setVehicleGoodsType(region
								.getRegionNature());
					}
				}
				vehicleRegionDtos.add(vehicleRegionDto);
			}
		}
        return vehicleRegionDtos;
    }

	private void getOwnVehicleRegionDto(OwnTruckEntity ownTruck,
			OwnVehicleRegionDto vehicleRegionDto, String vehicleType) {
		if (StringUtils.equals(vehicleType,
				DictionaryValueConstants.VEHICLE_TYPE_TRACTORS)) {
			if (LOGGER.isWarnEnabled()) {
				LOGGER.debug("调试：拖头无车型数据，不需要获取车型资料");
			}
		} else {
			// 需要车辆车型走缓存
			String vehicleLengthCode = ownTruck
					.getVehcleLengthCode();
			VehicleTypeEntity vehicleLengthType = leasedVehicleTypeService
					.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
			if (null != vehicleLengthType) {
				vehicleRegionDto
						.setVehicleLengthCode(vehicleLengthType
								.getVehicleLengthCode());
				vehicleRegionDto.setVehicleLength(vehicleLengthType
						.getVehicleLength());
			} else {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.debug("调试：拖头无车型数据，不需要获取车型资料");
				}
			}
		}
	}
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号、部门编号、车型编号、车辆状态"来获取车辆相关信息DTO集合（分页模糊）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-17 下午3:13:22
     * @param orgId 部门编号
     * @param vehicleNos 车牌号集合
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态（Y：可用；N：不可用）
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @param excludeVehicleType 排除挂车类型数据（数据字典常量值）
     * @return 封装了数据集合和记录数的对象
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryVehicleAssociationDtoListPaginationByCondition(java.lang.String, java.util.List, java.lang.String, java.lang.String, int, int, boolean)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryVehicleAssociationDtoListPaginationByCondition(
            String orgId, List<String> vehicleNos, String vehicleTypeCode,
            String status, int offset, int limit, String excludeVehicleType) throws OwnVehicleException {
	PaginationDto paginationDto = new PaginationDto();
	//分页数据异常
	if (limit < 0 || offset < 0) {
	    return paginationDto;
	}
        List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<VehicleBaseDto> vehicleBaseDtos = ownVehicleDao.queryOwnVehicleBaseDtoListByVehicleNos(vehicleNos, orgId, vehicleTypeCode, status, offset, limit, excludeVehicleType);
	if(CollectionUtils.isNotEmpty(vehicleBaseDtos)){
			for (VehicleBaseDto vehicleBaseDto : vehicleBaseDtos) {
				// 车辆关联属性对象
				VehicleAssociationDto vehicleAssociationDto = new VehicleAssociationDto();
				try {
					BeanUtils.copyProperties(vehicleBaseDto,
							vehicleAssociationDto, new String[] {
									"vehicleDeadLoad", "vehicleSelfVolume",
									"vehicleLength", "vehicleWidth",
									"vehicleHeight" });
					vehicleAssociationDto.setVehicleDeadLoad(vehicleBaseDto
							.getVehicleDeadLoad());
					vehicleAssociationDto.setVehicleSelfVolume(vehicleBaseDto
							.getVehicleSelfVolume());
					vehicleAssociationDto.setVehicleLength(vehicleBaseDto
							.getVehicleLength());
					vehicleAssociationDto.setVehicleWidth(vehicleBaseDto
							.getVehicleWidth());
					vehicleAssociationDto.setVehicleHeight(vehicleBaseDto
							.getVehicleHeight());
				} catch (Exception e) {
					throw new OwnVehicleException("", "复制车辆数据失败");
				}

				// 车辆业务编（编码、名称）走缓存
				DataDictionaryValueEntity dataDictionaryValue = null;
				String termsCode = DictionaryConstants.VEHICLE_USED_TYPE_TERMSCODE;
				String valueCode = vehicleBaseDto.getVehicleUsedTypeCode();
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								termsCode, valueCode);
				if (null != dataDictionaryValue) {
					vehicleAssociationDto
							.setVehicleUsedTypeName(dataDictionaryValue
									.getValueName());
				}

				// 车辆品牌（编码、名称）走缓存
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								DictionaryConstants.VEHICLE_BRAND_TYPE_TERMSCODE,
								vehicleBaseDto.getVehicleBrandCode());
				if (null != dataDictionaryValue) {
					vehicleAssociationDto
							.setVehicleBrandName(dataDictionaryValue
									.getValueName());
				}

				// VehicleBrandEntity vehicleBrand =
				// vehicleBrandService.queryVehicleBrandByBrandCode(vehicleBaseDto.getVehicleBrandCode());
				// if (null != vehicleBrand) {
				// vehicleAssociationDto.setVehicleBrandName(vehicleBrand.getBrandName());
				// }

				String vehicleType = vehicleBaseDto.getVehicleType();
				if (StringUtils.isNotBlank(vehicleType)) {
					getVehicleAssociationDto(vehicleBaseDto,
							vehicleAssociationDto, vehicleType);
				} else {
					if (LOGGER.isWarnEnabled()) {
						LOGGER.warn("警告：数据不完整错误，对应的车辆类型不存在");
					}
				}

				// 所属直属部门(编码、名称）走缓存
				String organizationCode = vehicleBaseDto
						.getVehicleOrganizationCode();
				OrgAdministrativeInfoEntity organization = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(organizationCode);
				if (null != organization) {
					vehicleAssociationDto
							.setVehicleOrganizationCode(organization.getCode());
					vehicleAssociationDto
							.setVehicleOrganizationName(organization.getName());

					// 所属直属部门负责人信息走缓存
					String organizationPprincipalCode = organization
							.getPrincipalNo();
					EmployeeEntity organizationEmployee = employeeService
							.querySimpleEmployeeByEmpCode(organizationPprincipalCode);
					if (null != organizationEmployee) {
						vehicleAssociationDto
								.setVehicleOrganizationLeaderCode(organizationEmployee
										.getEmpCode());
						vehicleAssociationDto
								.setVehicleOrganizationLeaderName(organizationEmployee
										.getEmpName());
						vehicleAssociationDto
								.setVehicleOrganizationLeaderPhone(organizationEmployee
										.getPhone());
					}

					// 所属车队信息走缓存
					OrgAdministrativeInfoEntity motorcade = orgAdministrativeInfoComplexService
							.getOrgAdministrativeInfoMotorcadeByCode(organizationCode);
					if (null != motorcade) {
						vehicleAssociationDto.setVehicleMotorcadeCode(motorcade
								.getCode());
						vehicleAssociationDto.setVehicleMotorcadeName(motorcade
								.getName());

						// 所属车队负责人信息走缓存
						String motorcadePrincipalCode = motorcade
								.getPrincipalNo();
						EmployeeEntity motorcadeEmployee = employeeService
								.querySimpleEmployeeByEmpCode(motorcadePrincipalCode);
						if (null != motorcadeEmployee) {
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderCode(motorcadeEmployee
											.getEmpCode());
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderName(motorcadeEmployee
											.getEmpName());
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderPhone(motorcadeEmployee
											.getPhone());
						}
					}
				}

				// 使用已有对象，但是需要制NULL
				dataDictionaryValue = null;
				// 停车原因和代码
				String unavilableTermsCode = DictionaryConstants.VEHICLE_STOP_REASON_TERMSCODE;
				String unavilableValueCode = vehicleBaseDto.getUnavilableCode();
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								unavilableTermsCode, unavilableValueCode);
				if (null != dataDictionaryValue) {
					vehicleAssociationDto.setUnavilableCode(dataDictionaryValue
							.getValueCode());
					vehicleAssociationDto
							.setUnavilableReason(dataDictionaryValue
									.getValueName());
				}
				vehicleAssociationDtos.add(vehicleAssociationDto);
			}
	    //获取数据总数
	    Long totalCount = ownVehicleDao.queryOwnVehicleBaseDtoCountByVehicleNos(vehicleNos, orgId, vehicleTypeCode, status);
	    paginationDto.setPaginationDtos(vehicleAssociationDtos);
	    paginationDto.setTotalCount(totalCount);
	}
        return paginationDto;
    }

	private void getVehicleAssociationDto(VehicleBaseDto vehicleBaseDto,
			VehicleAssociationDto vehicleAssociationDto, String vehicleType) {
		if(StringUtils.equals(vehicleType, DictionaryValueConstants.VEHICLE_TYPE_TRACTORS)){
		if(LOGGER.isWarnEnabled()){
		    LOGGER.debug("调试：拖头无车型数据，不需要获取车型资料");
		}
		//对于不是拖头的，不需要判断车型，但要提供一个默认值，每公里费用，业务暂定为7.75，Bug号码6003
		 vehicleAssociationDto.setEachKilometersFees(NumberConstants.EACHKILOMETERSFES);
		
		}else{
		//需要车辆车型走缓存
		String vehicleLengthCode = vehicleBaseDto.getVehicleLengthCode();
		VehicleTypeEntity vehicleLengthType = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
		if (null != vehicleLengthType) {
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
	}
	

    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合(包括拖头)</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 313353-foss-qiupeng
     * @date 2016-07-07 上午09:28:11
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryOwnVehicleAssociationDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<VehicleAssociationDto> queryOwnVehicleByVehicleNosIncludeTractors(
            String[] vehicleNos) throws OwnVehicleException {
	if (ArrayUtils.isEmpty(vehicleNos)) {
	    throw new OwnVehicleException("", "车牌号为空");
	}
	List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<String> vehicleNoList = new ArrayList<String>(vehicleNos.length);
	for (String vehicleNo : vehicleNos) {
	    vehicleNoList.add(vehicleNo);
	}
	PaginationDto paginationDto = queryVehicleByConditionIncludeTractors(
		null, vehicleNoList, null, null, NumberConstants.NUMERAL_ZORE,
		NumberConstants.NUMERAL_ONE, null);
	if(null != paginationDto){
	    vehicleAssociationDtos = paginationDto.getPaginationDtos();
	}
        return vehicleAssociationDtos;
    }
    
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号、部门编号、车型编号、车辆状态"来获取车辆相关信息DTO集合（分页模糊）(包含拖头)</p> 
     * @author 313353-foss-qiupeng
     * @date 2016-07-07 上午08:13:22
     * @param orgId 部门编号
     * @param vehicleNos 车牌号集合
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态（Y：可用；N：不可用）
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @param excludeVehicleType 排除挂车类型数据（数据字典常量值）
     * @return 封装了数据集合和记录数的对象
     * @throws OwnVehicleException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService#queryVehicleAssociationDtoListPaginationByCondition(java.lang.String, java.util.List, java.lang.String, java.lang.String, int, int, boolean)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryVehicleByConditionIncludeTractors(
            String orgId, List<String> vehicleNos, String vehicleTypeCode,
            String status, int offset, int limit, String excludeVehicleType) throws OwnVehicleException {
	PaginationDto paginationDto = new PaginationDto();
	//分页数据异常
	if (limit < 0 || offset < 0) {
	    return paginationDto;
	}
        List<VehicleAssociationDto> vehicleAssociationDtos = new ArrayList<VehicleAssociationDto>();
	List<VehicleBaseDto> vehicleBaseDtos = ownVehicleDao.queryOwnVehicleBaseDtoListByVehicleNos(vehicleNos, orgId, vehicleTypeCode, status, offset, limit, excludeVehicleType);
	if(CollectionUtils.isNotEmpty(vehicleBaseDtos)){
			for (VehicleBaseDto vehicleBaseDto : vehicleBaseDtos) {
				// 车辆关联属性对象
				VehicleAssociationDto vehicleAssociationDto = new VehicleAssociationDto();
				try {
					BeanUtils.copyProperties(vehicleBaseDto,
							vehicleAssociationDto, new String[] {
									"vehicleDeadLoad", "vehicleSelfVolume",
									"vehicleLength", "vehicleWidth",
									"vehicleHeight" });
					vehicleAssociationDto.setVehicleDeadLoad(vehicleBaseDto
							.getVehicleDeadLoad());
					vehicleAssociationDto.setVehicleSelfVolume(vehicleBaseDto
							.getVehicleSelfVolume());
					vehicleAssociationDto.setVehicleLength(vehicleBaseDto
							.getVehicleLength());
					vehicleAssociationDto.setVehicleWidth(vehicleBaseDto
							.getVehicleWidth());
					vehicleAssociationDto.setVehicleHeight(vehicleBaseDto
							.getVehicleHeight());
				} catch (Exception e) {
					throw new OwnVehicleException("", "复制车辆数据失败");
				}

				// 车辆业务编（编码、名称）走缓存
				DataDictionaryValueEntity dataDictionaryValue = null;
				String termsCode = DictionaryConstants.VEHICLE_USED_TYPE_TERMSCODE;
				String valueCode = vehicleBaseDto.getVehicleUsedTypeCode();
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								termsCode, valueCode);
				if (null != dataDictionaryValue) {
					vehicleAssociationDto
							.setVehicleUsedTypeName(dataDictionaryValue
									.getValueName());
				}

				// 车辆品牌（编码、名称）走缓存
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								DictionaryConstants.VEHICLE_BRAND_TYPE_TERMSCODE,
								vehicleBaseDto.getVehicleBrandCode());
				if (null != dataDictionaryValue) {
					vehicleAssociationDto
							.setVehicleBrandName(dataDictionaryValue
									.getValueName());
				}

				// VehicleBrandEntity vehicleBrand =
				// vehicleBrandService.queryVehicleBrandByBrandCode(vehicleBaseDto.getVehicleBrandCode());
				// if (null != vehicleBrand) {
				// vehicleAssociationDto.setVehicleBrandName(vehicleBrand.getBrandName());
				// }

				String vehicleType = vehicleBaseDto.getVehicleType();
				if (StringUtils.isNotBlank(vehicleType)) {
					getVehicleAssociationDtoIncludeTractors(vehicleBaseDto,
							vehicleAssociationDto, vehicleType);
				} else {
					if (LOGGER.isWarnEnabled()) {
						LOGGER.warn("警告：数据不完整错误，对应的车辆类型不存在");
					}
				}

				// 所属直属部门(编码、名称）走缓存
				String organizationCode = vehicleBaseDto
						.getVehicleOrganizationCode();
				OrgAdministrativeInfoEntity organization = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(organizationCode);
				if (null != organization) {
					vehicleAssociationDto
							.setVehicleOrganizationCode(organization.getCode());
					vehicleAssociationDto
							.setVehicleOrganizationName(organization.getName());

					// 所属直属部门负责人信息走缓存
					String organizationPprincipalCode = organization
							.getPrincipalNo();
					EmployeeEntity organizationEmployee = employeeService
							.querySimpleEmployeeByEmpCode(organizationPprincipalCode);
					if (null != organizationEmployee) {
						vehicleAssociationDto
								.setVehicleOrganizationLeaderCode(organizationEmployee
										.getEmpCode());
						vehicleAssociationDto
								.setVehicleOrganizationLeaderName(organizationEmployee
										.getEmpName());
						vehicleAssociationDto
								.setVehicleOrganizationLeaderPhone(organizationEmployee
										.getPhone());
					}

					// 所属车队信息走缓存
					OrgAdministrativeInfoEntity motorcade = orgAdministrativeInfoComplexService
							.getOrgAdministrativeInfoMotorcadeByCode(organizationCode);
					if (null != motorcade) {
						vehicleAssociationDto.setVehicleMotorcadeCode(motorcade
								.getCode());
						vehicleAssociationDto.setVehicleMotorcadeName(motorcade
								.getName());

						// 所属车队负责人信息走缓存
						String motorcadePrincipalCode = motorcade
								.getPrincipalNo();
						EmployeeEntity motorcadeEmployee = employeeService
								.querySimpleEmployeeByEmpCode(motorcadePrincipalCode);
						if (null != motorcadeEmployee) {
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderCode(motorcadeEmployee
											.getEmpCode());
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderName(motorcadeEmployee
											.getEmpName());
							vehicleAssociationDto
									.setVehicleMotorcadeLeaderPhone(motorcadeEmployee
											.getPhone());
						}
					}
				}

				// 使用已有对象，但是需要制NULL
				dataDictionaryValue = null;
				// 停车原因和代码
				String unavilableTermsCode = DictionaryConstants.VEHICLE_STOP_REASON_TERMSCODE;
				String unavilableValueCode = vehicleBaseDto.getUnavilableCode();
				dataDictionaryValue = dataDictionaryValueService
						.queryDataDictionaryValueByTermsCodeValueCode(
								unavilableTermsCode, unavilableValueCode);
				if (null != dataDictionaryValue) {
					vehicleAssociationDto.setUnavilableCode(dataDictionaryValue
							.getValueCode());
					vehicleAssociationDto
							.setUnavilableReason(dataDictionaryValue
									.getValueName());
				}
				vehicleAssociationDtos.add(vehicleAssociationDto);
			}
	    //获取数据总数
	    Long totalCount = ownVehicleDao.queryOwnVehicleBaseDtoCountByVehicleNos(vehicleNos, orgId, vehicleTypeCode, status);
	    paginationDto.setPaginationDtos(vehicleAssociationDtos);
	    paginationDto.setTotalCount(totalCount);
	}
        return paginationDto;
    }

	private void getVehicleAssociationDtoIncludeTractors(VehicleBaseDto vehicleBaseDto,
			VehicleAssociationDto vehicleAssociationDto, String vehicleType) {

		//需要车辆车型走缓存
		String vehicleLengthCode = vehicleBaseDto.getVehicleLengthCode();
		VehicleTypeEntity vehicleLengthType = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
		if (null != vehicleLengthType) {
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
    
    private void executeValidationVehicleType(OwnTruckEntity ownTruck,
	    String vehicleType) throws OwnVehicleException {
	if(StringUtils.isEmpty(vehicleType)){
	    //设置“车辆类型”为NULL，表示查询所有类型的公司车 
	    ownTruck.setVehicleType(null);
	}else {
	    if(StringUtils.equals(DictionaryValueConstants.VEHICLE_TYPE_TRACTORS, vehicleType)
		    || StringUtils.equals(DictionaryValueConstants.VEHICLE_TYPE_TRAILER, vehicleType)
		    || StringUtils.equals(DictionaryValueConstants.VEHICLE_TYPE_VAN, vehicleType)
		    || StringUtils.equals(DictionaryValueConstants.VEHICLE_TYPE_RQSVC, vehicleType)){
		ownTruck.setVehicleType(vehicleType);
	    }else{
		throw new OwnVehicleException("", "指定的车辆类型不存在");
	    }
	}
    }
    
    /**
     * @param ownVehicleDao the ownVehicleDao to set
     */
    public void setOwnVehicleDao(IOwnVehicleDao ownVehicleDao) {
        this.ownVehicleDao = ownVehicleDao;
    }

    /**
     * @param dataDictionaryValueService the dataDictionaryValueService to set
     */
    public void setDataDictionaryValueService(
    	IDataDictionaryValueService dataDictionaryValueService) {
        this.dataDictionaryValueService = dataDictionaryValueService;
    }

    /**
     * @param leasedVehicleTypeService the leasedVehicleTypeService to set
     */
    public void setLeasedVehicleTypeService(
    	ILeasedVehicleTypeService leasedVehicleTypeService) {
        this.leasedVehicleTypeService = leasedVehicleTypeService;
    }
    
    /**
     * @param regionalVehicleService the regionalVehicleService to set
     */
    public void setRegionalVehicleService(
    	IRegionalVehicleService regionalVehicleService) {
        this.regionalVehicleService = regionalVehicleService;
    }
    
    /**
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
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
