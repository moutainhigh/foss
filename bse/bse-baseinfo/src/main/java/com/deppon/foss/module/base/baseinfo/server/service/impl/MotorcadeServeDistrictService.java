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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/MotorcadeServeDistrictService.java
 * 
 * FILE NAME        	: MotorcadeServeDistrictService.java
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity;

/**
 * 车队负责的行政区域 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-17 下午8:11:15
 */
public class MotorcadeServeDistrictService implements IMotorcadeServeDistrictService {

    /**
     * dao对象的声明
     */
    private IMotorcadeServeDistrictDao motorcadeServeDistrictDao;
	
    /**
     * 车队负责的行政区域 新增 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeDistrictService#addMotorcadeServeDistrict(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity)
     */
    @Override
    @Transactional
	public MotorcadeServeDistrictEntity addMotorcadeServeDistrict(
			MotorcadeServeDistrictEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		//执行插入操作
		return motorcadeServeDistrictDao.addMotorcadeServeDistrict(entity);
	}

    /**
     * 通过VIRTUAL_CODE标识来删除 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao#deleteMotorcadeServeDistrict(java.lang.String)
     */
    @Override
    @Transactional
    public MotorcadeServeDistrictEntity deleteMotorcadeServeDistrict(MotorcadeServeDistrictEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			//返回为空
			return null;
		}
		//执行删除操作
		return motorcadeServeDistrictDao.deleteMotorcadeServeDistrict(entity);
    }

    /**
     * 通过VIRTUAL_CODE标识来批量删除 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao#deleteMotorcadeServeDistrictMore(java.lang.String[])
     */
    @Override
    @Transactional
    public MotorcadeServeDistrictEntity deleteMotorcadeServeDistrictMore(String[] codes , String deleteUser) {
    	//执行批量删除操作
    	return motorcadeServeDistrictDao.deleteMotorcadeServeDistrictMore(codes, deleteUser);
    }

    /**
     * 更新 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictDao#updateMotorcadeServeDistrict(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity)
     */
    @Override
    @Transactional
    public MotorcadeServeDistrictEntity updateMotorcadeServeDistrict(MotorcadeServeDistrictEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			//返回为空
			return null;
		}
		//执行更新操作
		return motorcadeServeDistrictDao.updateMotorcadeServeDistrict(entity);
    }
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictService#queryMotorcadeServeDistrictByCode(java.lang.String)
     */
    @Override
    public MotorcadeServeDistrictEntity queryMotorcadeServeDistrictByVirtualCode(String code) {
    	//对code进行非空判断
		if (null == code) {
			//返回为空
			return null;
		}
		//执行查询并返回结果
		return motorcadeServeDistrictDao.queryMotorcadeServeDistrictByVirtualCode(code);
    }

    /**
     * 精确查询 
     * 根据多个编码批量查询 车队负责的行政区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictService#queryMotorcadeServeDistrictByCode(java.lang.String)
     */
    @Override
    public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictBatchByVirtualCode(
	    String[] codes) {
    	//数组非空判断
		if (ArrayUtils.isEmpty(codes)){
			//返回为空
			return null;
		}
		//执行查询并返回
		return motorcadeServeDistrictDao
				.queryMotorcadeServeDistrictBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询 车队负责的行政区域
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictService#queryMotorcadeServeDistrictExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity, int, int)
     */
    @Override
    public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictExactByEntity(
	    MotorcadeServeDistrictEntity entity, int start, int limit) {
	return motorcadeServeDistrictDao.queryMotorcadeServeDistrictExactByEntity(
		entity, start, limit);
    }

    /**
     * 精确查询 车队负责的行政区域
     * 查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictService#queryMotorcadeServeDistrictExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeDistrictEntity)
     */
    @Override
    public long queryMotorcadeServeDistrictExactByEntityCount(MotorcadeServeDistrictEntity entity) {
    	//执行查询操作
    	return motorcadeServeDistrictDao.queryMotorcadeServeDistrictExactByEntityCount(entity);
    }
	
    /**
     * 精确查询 车队负责的行政区域,返回所有的结果，
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     */
    @Override
    public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictExactByEntityAll(
	    MotorcadeServeDistrictEntity entity) {
    	//执行查询操作
    	return this.queryMotorcadeServeDistrictExactByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
 
    /**
     * 模糊查询 车队负责的行政区域
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeDistrictService#deleteMotorcadeServeDistrictMore(java.lang.String[])
     */
    @Override
    public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictByEntity(
	    MotorcadeServeDistrictEntity entity, int start, int limit) {
    	//执行分页查询
    	return motorcadeServeDistrictDao.queryMotorcadeServeDistrictByEntity(entity, start, limit);
    }

    /**
     * 模糊查询 车队负责的行政区域
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     * @see com.deppon.foss.module.baseinfo.server.service.IMotorcadeServeDistrictService#queryMotorcadeServeDistrictCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.MotorcadeServeDistrictEntity)
     */
    @Override
    public long queryMotorcadeServeDistrictByEntityCount(MotorcadeServeDistrictEntity entity) {
    	//执行查询操作
    	return motorcadeServeDistrictDao.queryMotorcadeServeDistrictByEntityCount(entity);
    }

    /**
     * 根据entity模糊查询 车队负责的行政区域,返回所有的结果，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:11:15
     */
    @Override
    public List<MotorcadeServeDistrictEntity> queryMotorcadeServeDistrictByEntityAll(
	    MotorcadeServeDistrictEntity entity) {
    	//执行查询操作
    	return this.queryMotorcadeServeDistrictByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
    
    /**
     * 通过MotorcadeCode车队编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */	
    @Override
    public MotorcadeServeDistrictEntity deleteMotorcadeServeDistrictByMotorcadeCode(MotorcadeServeDistrictEntity entity){
    	//执行删除操作
    	return motorcadeServeDistrictDao.deleteMotorcadeServeDistrictByMotorcadeCode(entity);
    }

    /** 
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-18 上午2:21:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeDistrictService#addMotorcadeServeDistrictMore(java.util.List)
     */
    @Override
    public List<MotorcadeServeDistrictEntity> addMotorcadeServeDistrictMore(
	    List<MotorcadeServeDistrictEntity> entitys) {
    	List<MotorcadeServeDistrictEntity> motorcadeServeDistrictEntitys = new ArrayList<MotorcadeServeDistrictEntity>();
    	//遍历集合,一次插入一条
		for (MotorcadeServeDistrictEntity entity : entitys) {
			MotorcadeServeDistrictEntity oldEntity =motorcadeServeDistrictDao.queryMotorcadeServeDistrictByMotorCodeAndDistrictCode(entity.getMotorcadeCode(), entity.getDistrictCode());
			//若该实体在库中不存在，就执行插入
			if(oldEntity ==null){
				MotorcadeServeDistrictEntity motorcadeServeDistrictEntity = this.addMotorcadeServeDistrict(entity);
				if(motorcadeServeDistrictEntity != null){
					motorcadeServeDistrictEntitys.add(motorcadeServeDistrictEntity);
				}
			}//执行插入从阿做
		}
		//返回遍历后的对象
		return motorcadeServeDistrictEntitys;
    }

	/**
	 * @param  motorcadeServeDistrictDao  
	 */
	public void setMotorcadeServeDistrictDao(
			IMotorcadeServeDistrictDao motorcadeServeDistrictDao) {
		this.motorcadeServeDistrictDao = motorcadeServeDistrictDao;
	}
}