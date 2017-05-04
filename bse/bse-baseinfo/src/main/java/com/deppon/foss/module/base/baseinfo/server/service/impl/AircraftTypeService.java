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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AircraftTypeService.java
 * 
 * FILE NAME        	: AircraftTypeService.java
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AircraftTypeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AircraftTypeException;
import com.deppon.foss.util.define.FossConstants;


/**
 * 用来操作交互“机型信息”的数据库对应数据访问的Service接口实现类：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-16 上午8:48:56</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-16 上午8:48:56
 * @since
 * @version
 */
public class AircraftTypeService implements IAircraftTypeService {
    
    /**
     * "机型信息"Dao.
     */
    private IAircraftTypeDao aircraftTypeDao;
    
    /**
     * <p>新增一个“机型信息”实体入库（忽略实体中是否存在空值）</p>.
     *
     * @param aircraftType “机型信息”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午8:46:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#addAircraftType(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addAircraftType(AircraftTypeEntity aircraftType, String createUser, boolean ignoreNull) throws AircraftTypeException{
	//参数验证
	if (null == aircraftType) {
	    throw new AircraftTypeException("", "机型信息为空");
	}
	//机型代码验证
	if (StringUtils.isBlank(aircraftType.getCode())) {
	    throw new AircraftTypeException("", "机型代码为空");
	}
	//创建人信息验证
	if (StringUtils.isBlank(createUser)) {
	    throw new AircraftTypeException("", "创建人信息为空");
	}
	
	//验证对应"机型信息"是否已经存在
	AircraftTypeEntity oldAircraftType, tempAircraftType = new AircraftTypeEntity();
	//设置编码
	tempAircraftType.setCode(aircraftType.getCode());
	//根据条件有选择的检索出符合条件一个的“机型信息”实体（条件做自动判断，只选择实体中非空值）
	oldAircraftType = queryAircraftTypeBySelective(tempAircraftType);
	//机型信息重复性验证
	if (null != oldAircraftType) {
	    throw new AircraftTypeException("", "机型信息已经存在");
	}else{
	    //设置创建用户
	    aircraftType.setCreateUser(createUser);
	    if (ignoreNull) {
		//新增一个“机型信息”实体入库 （只选择实体中非空值）
		aircraftTypeDao.addAircraftTypeBySelective(aircraftType);
	    }else{
		//新增一个“机型信息”实体入库（忽略实体中是否存在空值）
		aircraftTypeDao.addAircraftType(aircraftType);
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“机型信息”记录唯一标识作废（逻辑删除）一条“机型信息”记录</p>.
     *
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午8:46:52
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#deleteAircraftType(java.util.List, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteAircraftType(List<String> ids, String modifyUser) throws AircraftTypeException{
	//参数验证
	if (CollectionUtils.isEmpty(ids)) {
	    throw new AircraftTypeException("", "ID为空");
	}
	//修改人信息验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AircraftTypeException("", "修改人信息为空");
	}
	//待优化实现
	for (String id : ids) {
	    //验证对应"机型信息"是否已经存在
	    AircraftTypeEntity aircraftType = queryAircraftTypeBySelective(id);
	    if (null == aircraftType) {
		throw new AircraftTypeException("", "机型信息不存在");
	    }else{
		//设置修改人
		aircraftType.setModifyUser(modifyUser);
		//设置状态：有效
		aircraftType.setActive(FossConstants.INACTIVE);
		//修改一个“机型信息”实体入库 （只选择实体中非空值）
		aircraftTypeDao.updateAircraftTypeBySelective(aircraftType);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“机型信息”实体入库（忽略实体中是否存在空值）</p>.
     *
     * @param aircraftType “机型信息”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午8:46:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#updateAircraftType(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateAircraftType(AircraftTypeEntity aircraftType, String modifyUser,  boolean ignoreNull) throws AircraftTypeException{
	//参数验证
	if (null == aircraftType) {
	    throw new AircraftTypeException("", "机型信息为空");
	}
	//ID 为空验证
	if (StringUtils.isBlank(aircraftType.getId())) {
	    throw new AircraftTypeException("", "ID 为空");
	}
	//机型代码验证
	if (StringUtils.isBlank(aircraftType.getCode())) {
	    throw new AircraftTypeException("", "机型代码为空");
	}
	//修改人信息验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AircraftTypeException("", "修改人信息为空");
	}
	
	//验证对应"机型信息"是否已经存在
	AircraftTypeEntity oldAircraftType, tempAircraftType = new AircraftTypeEntity();
	//设置编码
	tempAircraftType.setCode(aircraftType.getCode());
	//根据条件有选择的检索出符合条件一个的“机型信息”实体（条件做自动判断，只选择实体中非空值）
	oldAircraftType = queryAircraftTypeBySelective(tempAircraftType);
	//机型信息验证
	if (null == oldAircraftType) {
	    throw new AircraftTypeException("", "机型信息不存在");
	}else{
	    //设置修改人
	    aircraftType.setModifyUser(modifyUser);
	    if (ignoreNull) {
		//修改一个“机型信息”实体入库 （只选择实体中非空值）
		aircraftTypeDao.updateAircraftTypeBySelective(aircraftType);
	    }else{
		//修改一个“机型信息”实体入库（忽略实体中是否存在空值）
		aircraftTypeDao.updateAircraftType(aircraftType);
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“机型信息”记录唯一标识查询出一条“机型信息”记录</p>.
     *
     * @param id 记录唯一标识
     * @return “机型信息”实体
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午8:46:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#queryAircraftTypeBySelective(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public AircraftTypeEntity queryAircraftTypeBySelective(String id) throws AircraftTypeException{
	//参数验证
	if(StringUtils.isBlank(id)){
	    return null;
	}
	//创建一个机型信息实体
	AircraftTypeEntity aircraftType = new AircraftTypeEntity();
	//设置ＩＤ
	aircraftType.setId(id);
	//根据条件有选择的检索出符合条件的“机型信息”唯一实体（条件做自动判断，只选择实体中非空值）
	return aircraftTypeDao.queryAircraftTypeBySelective(aircraftType);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件一个的“机型信息”实体（条件做自动判断，只选择实体中非空值）</p>.
     *
     * @param aircraftType 
     * @return 符合条件的“机型信息”实体
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:23:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#queryAircraftTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public AircraftTypeEntity queryAircraftTypeBySelective(
            AircraftTypeEntity aircraftType) throws AircraftTypeException {
        //参数验证
	if (null == aircraftType) {
	    throw new AircraftTypeException("", "机型参数信息为空");
	}
	//根据条件有选择的检索出符合条件的“机型信息”唯一实体（条件做自动判断，只选择实体中非空值）
        return aircraftTypeDao.queryAircraftTypeBySelective(aircraftType);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     *
     * @param aircraftType 
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws AircraftTypeException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAircraftTypeService#queryAircraftTypeListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryAircraftTypeListBySelectiveCondition(
	    AircraftTypeEntity aircraftType, int offset, int limit) throws AircraftTypeException{
	//分页的Action和Service通讯封装对象 
	PaginationDto paginationDto = new PaginationDto();
	if (limit < 0 || offset < 0) {
	    return  paginationDto;
	}
	//参数验证
	if(null == aircraftType){
	    //创建一个实体
	    aircraftType = new AircraftTypeEntity();
	}
	//根据条件（分页模糊）有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）
	List<AircraftTypeEntity> aircraftTypeList = aircraftTypeDao.queryAircraftTypeListBySelectiveCondition(aircraftType, offset, limit);
	//集合不为空判断
	if (CollectionUtils.isNotEmpty(aircraftTypeList)) {
	    //定义一个集合
	    List<AircraftTypeDto> aircraftTypeDtoList = new ArrayList<AircraftTypeDto>(aircraftTypeList.size());
	    AircraftTypeDto aircraftTypeDto = null;
	    //循环迭代
	    for (AircraftTypeEntity tempAircraftType : aircraftTypeList) {
		try {
		    //创建对象
		    aircraftTypeDto = new AircraftTypeDto(tempAircraftType);
		} catch (AircraftTypeException e) {
		    throw new AircraftTypeException("", "机型信息Entity数据复制到Dto失败", e);
		}
		//把信息存放到集合中
		aircraftTypeDtoList.add(aircraftTypeDto);
	    }
	    //查询总的记录数
	    Long totalCount = aircraftTypeDao.queryAircraftTypeCountBySelectiveCondition(aircraftType);
	    paginationDto.setPaginationDtos(aircraftTypeDtoList);
	    //设置总记录数
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }

    /**
     * 设置 "机型信息"Dao.
     *
     * @param aircraftTypeDao the aircraftTypeDao to set
     */
    public void setAircraftTypeDao(IAircraftTypeDao aircraftTypeDao) {
        this.aircraftTypeDao = aircraftTypeDao;
    }
}
