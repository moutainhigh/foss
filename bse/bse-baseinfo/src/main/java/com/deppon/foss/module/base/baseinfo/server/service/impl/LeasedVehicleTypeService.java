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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/LeasedVehicleTypeService.java
 * 
 * FILE NAME        	: LeasedVehicleTypeService.java
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“车辆车型”的数据库对应数据访问Service接口实现类：SUC-109
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-24 上午10:46:27</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-24 上午10:46:27
 * @since
 * @version
 */
public class LeasedVehicleTypeService implements ILeasedVehicleTypeService {

    // "外请车车型"接口DAO
    private ILeasedVehicleTypeDao leasedVehicleTypeDao;

    /**
     * <p>新增一个“车辆车型”实体入库（忽略实体中是否存在空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:55:59
     * @param vehicleType “车辆车型”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleTypeException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#addLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addLeasedVehicleType(VehicleTypeEntity vehicleType, 
	    String createUser, boolean ignoreNull) throws LeasedVehicleTypeException {
	if (null == vehicleType) {
	    throw new LeasedVehicleTypeException("", "车辆车型为空");
	}
	if (StringUtils.isBlank(vehicleType.getVehicleLengthCode())) {
	    throw new LeasedVehicleTypeException("", "车辆车型编码为空");
	}
	if (StringUtils.isBlank(createUser)) {
	    throw new LeasedVehicleTypeException("", "创建人为空");
	}
	// 验证数据
	VehicleTypeEntity tempVehicleType = queryLeasedVehicleTypeByVehicleLengthCode(vehicleType.getVehicleLengthCode());
	if (null != tempVehicleType) {
	    throw new LeasedVehicleTypeException("", "车辆车型编码已经存在");
	} else {
	    vehicleType.setCreateUser(createUser);
	    if (ignoreNull) {
		leasedVehicleTypeDao.addLeasedVehicleTypeBySelective(vehicleType);
	    } else {
		leasedVehicleTypeDao.addLeasedVehicleType(vehicleType);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“车辆车型”记录唯一标识作废一条“车辆车型”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:55:58
     * @param id 记录唯一标识
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#deleteLeasedVehicleType(java.lang.String)
     */
    @Override
    @Transactional
    public int deleteLeasedVehicleType(String id, String modifyUser)
	    throws LeasedVehicleTypeException {
	if (StringUtils.isBlank(id)) {
	    throw new LeasedVehicleTypeException("", "ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new LeasedVehicleTypeException("", "修改人为空");
	}
	// 验证数据
	VehicleTypeEntity vehicleType = queryLeasedVehicleType(id);
	if (null == vehicleType) {
	    throw new LeasedVehicleTypeException("", "车辆车型编码不经存在");
	} else {
	    // 逻辑删除，直接“停用”
	    vehicleType.setModifyUser(modifyUser);
	    vehicleType.setActive(FossConstants.INACTIVE);
	    leasedVehicleTypeDao.updateLeasedVehicleTypeBySelective(vehicleType);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“车辆车型”实体入库（忽略实体中是否存在空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:56:02
     * @param vehicleType “车辆车型”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#updateLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    @Transactional
    public int updateLeasedVehicleType(VehicleTypeEntity vehicleType,
	    String modifyUser, boolean ignoreNull) throws LeasedVehicleTypeException {
    	if (null == vehicleType) {
    	    throw new LeasedVehicleTypeException("车辆车型为空","");
    	}
    	if (StringUtils.isBlank(vehicleType.getId())) {
    	    throw new LeasedVehicleTypeException("ID 为空","");
    	}
    	if (StringUtils.isBlank(modifyUser)) {
    	    throw new LeasedVehicleTypeException("修改人为空","");
    	}
    	/*if (null==vehicleType.getSeq()) {
    	    throw new LeasedVehicleTypeException("序号为空","");
    	}*/
    	// 验证数据 前端车型编码该属性不可输,是否有必要再校验
    	//VehicleTypeEntity tempVehicleType = queryLeasedVehicleTypeByVehicleLengthCode(vehicleType.getVehicleLengthCode());
    	/*if (null == tempVehicleType) {
    	    throw new LeasedVehicleTypeException("车辆车型编码不存在");
    	}*/
    	/*long i=leasedVehicleTypeDao.queryLeasedVehicleSeqBySeq(vehicleType.getSeq());
    	if (i != 0) {
    	    throw new LeasedVehicleTypeException("序号已存在","");
    	} else {
    	    vehicleType.setModifyUser(modifyUser);
    	    if (ignoreNull) {
    		leasedVehicleTypeDao.updateLeasedVehicleTypeBySelective(vehicleType);
    	    } else {
    		leasedVehicleTypeDao.updateLeasedVehicleType(vehicleType);
    	    }
    	}*/
    	
    	vehicleType.setModifyUser(modifyUser);
	if (ignoreNull) {
	    leasedVehicleTypeDao
		    .updateLeasedVehicleTypeBySelective(vehicleType);
	} else {
	    leasedVehicleTypeDao.updateLeasedVehicleType(vehicleType);
	}
    	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”唯一实体（条件做自动判断，只选择实体中非空值）</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:46:29
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @return 符合条件的“车辆车型”实体列表
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleTypeEntity queryLeasedVehicleType(
	    VehicleTypeEntity vehicleType) throws LeasedVehicleTypeException {
	if (null == vehicleType) {
	    return null;
	}
	return leasedVehicleTypeDao.queryLeasedVehicleTypeBySelective(vehicleType);
    }
	
    /**
     * <p>根据“车辆车型”记录唯一标识查询出一条“车辆车型”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:56:03
     * @param id 记录唯一标识	
     * @return “车辆车型”实体
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleType(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleTypeEntity queryLeasedVehicleType(String id)
	    throws LeasedVehicleTypeException {
	if (StringUtils.isBlank(id)) {
	    return null;
	}
	VehicleTypeEntity vehicleType = new VehicleTypeEntity();
	vehicleType.setId(id);
	return queryLeasedVehicleType(vehicleType);
    }

    
	
    /**
     * <p>根据“车辆车型”编码查询出一条“车辆车型”记录</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:46:28
     * @param vehicleLengthCode 车辆车长编码
     * @return “车辆车型”实体
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleTypeByVehicleLengthCode(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleTypeEntity queryLeasedVehicleTypeByVehicleLengthCode(
	    String vehicleLengthCode) throws LeasedVehicleTypeException {
	if (StringUtils.isBlank(vehicleLengthCode)) {
	    throw new LeasedVehicleTypeException("", "车辆车长编码为空");
	}
	VehicleTypeEntity vehicleType = new VehicleTypeEntity();
	vehicleType.setVehicleLengthCode(vehicleLengthCode);
	return queryLeasedVehicleType(vehicleType);
    }
	
    /**
     * <p>获取当前系统中此对象最大的序列号</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-12 上午11:10:24
     * @return 当前最大的序列号
     * @throws LeasedVehicleTypeException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleTypeMaxSeq()
     */
    @Override
    public BigDecimal queryLeasedVehicleTypeMaxSeq()
	    throws LeasedVehicleTypeException {
	return leasedVehicleTypeDao.queryLeasedVehicleTypeMaxSeq(null, null);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-18 上午8:56:04
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆车型”实体列表
     * @throws LeasedVehicleTypeException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleTypeListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryLeasedVehicleTypeListBySelectiveCondition(
	    VehicleTypeEntity vehicleType, int offset, int limit) throws LeasedVehicleTypeException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if(null == vehicleType){
	    vehicleType = new VehicleTypeEntity();
	}
	List<VehicleTypeEntity> vehicleTypeList = leasedVehicleTypeDao.queryLeasedVehicleTypeListBySelectiveCondition(vehicleType, offset, limit);
	if (CollectionUtils.isNotEmpty(vehicleTypeList)) {
	    Long totalCount = leasedVehicleTypeDao.queryLeasedVehicleTypeRecordCountBySelectiveCondition(vehicleType);
	    paginationDto.setPaginationDtos(vehicleTypeList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }

    /**
     * 查询车新信息，并对车型长度相同的信息做去重操作
     * @author 088933-foss-zhangjiheng
     * @date 2013-3-6 下午3:00:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryLeasedVehicleTypeListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity,
     *      int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VehicleTypeEntity> queryDistinctLeasedVehicleTypeListBySelectiveCondition(
			VehicleTypeEntity vehicleType, int offset, int limit)
			throws LeasedVehicleTypeException {
		List<VehicleTypeEntity> vehicleTypeList = null;
		if (offset < 0 || limit < 0) {
			return new ArrayList<VehicleTypeEntity>();
		}
		if (null == vehicleType) {
			vehicleType = new VehicleTypeEntity();
		}
		vehicleTypeList = leasedVehicleTypeDao
				.queryLeasedVehicleTypeListBySelectiveCondition(vehicleType,
						offset, limit);
		List<VehicleTypeEntity> distinctVehicleTypeList = new ArrayList<VehicleTypeEntity>();
		// 去掉车型长度相同的信息
		for (VehicleTypeEntity vehicleTypeEntity : vehicleTypeList) {

			if (distinctVehicleTypeList.size() == 0) {
				distinctVehicleTypeList.add(vehicleTypeEntity);
			} else {
				boolean str=true;
				for (VehicleTypeEntity disVehicleTypeEntity : distinctVehicleTypeList) {
					if (disVehicleTypeEntity.getVehicleLength().compareTo(
							vehicleTypeEntity.getVehicleLength()) == 0) {
						str=false;
					}
				}
				//如果没有重复的则添加入新的集合中
				if(str){
					distinctVehicleTypeList.add(vehicleTypeEntity);
				}
			}
		}
		return distinctVehicleTypeList;
    }

    /**
     * <p>查询车型编码和序号的对应关系</p>
     * @author foss-zhujunyong
     * @date Feb 27, 2013 4:17:39 PM
     * @return
     * @throws LeasedVehicleTypeException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService#queryVehicleCodeSeqMapping()
     */
    @Override
    public Map<String, Integer> queryVehicleCodeSeqMapping()
	    throws LeasedVehicleTypeException {
	Map<String, Integer> result = new HashMap<String, Integer>();
	List<VehicleTypeEntity> list = leasedVehicleTypeDao.queryLeasedVehicleTypeListBySelective(null);
	if (CollectionUtils.isEmpty(list)) {
	    return result;
	}
	for (VehicleTypeEntity entity : list) {
	    if (entity != null
		    && StringUtils.isNotBlank(entity.getVehicleLengthCode())
		    && entity.getSeq() != null) {
		result.put(entity.getVehicleLengthCode(), entity.getSeq().intValue());
	    }
	}
	return result;
    }

    /**
     * @param leasedVehicleTypeDao
     *            the leasedVehicleTypeDao to set
     */
    public void setLeasedVehicleTypeDao(
	    ILeasedVehicleTypeDao leasedVehicleTypeDao) {
	this.leasedVehicleTypeDao = leasedVehicleTypeDao;
    }
}
