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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/MotorcadeServeSalesAreaService.java
 * 
 * FILE NAME        	: MotorcadeServeSalesAreaService.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeSalesAreaService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity;

/**
 * 车队负责的营业区域 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-17 下午8:59:10
 */
public class MotorcadeServeSalesAreaService implements IMotorcadeServeSalesAreaService {

    /**
     * dao对象的声明
     */
    private IMotorcadeServeSalesAreaDao motorcadeServeSalesAreaDao;
	
    /**
     * 车队负责的营业区域 新增 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeServeSalesAreaService#addMotorcadeServeSalesArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity)
     */
    @Override
    @Transactional
    public MotorcadeServeSalesAreaEntity addMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			//返回为空
			return null;
		}
		//执行插入操作
		return motorcadeServeSalesAreaDao.addMotorcadeServeSalesArea(entity);
    }

    /**
     * 通过VIRTUAL_CODE标识来删除 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaDao#deleteMotorcadeServeSalesArea(java.lang.String)
     */
    @Override
    @Transactional
    public MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			//返回为空
			return null;
		}
		//执行删除操作
		return motorcadeServeSalesAreaDao.deleteMotorcadeServeSalesArea(entity);
    }

    /**
     * 通过VIRTUAL_CODE标识来批量删除 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaDao#deleteMotorcadeServeSalesAreaMore(java.lang.String[])
     */
    @Override
    @Transactional
    public MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesAreaMore(String[] codes , String deleteUser) {
    	//执行删除操作
    	return motorcadeServeSalesAreaDao.deleteMotorcadeServeSalesAreaMore(codes, deleteUser);
    }

    /**
     * 更新 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaDao#updateMotorcadeServeSalesArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity)
     */
    @Override
    @Transactional
    public MotorcadeServeSalesAreaEntity updateMotorcadeServeSalesArea(MotorcadeServeSalesAreaEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
			//返回为空
			return null;
		}
		//执行更新操作
		return motorcadeServeSalesAreaDao.updateMotorcadeServeSalesArea(entity);
    }

    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaService#queryMotorcadeServeSalesAreaByCode(java.lang.String)
     */
    @Override
    public MotorcadeServeSalesAreaEntity queryMotorcadeServeSalesAreaByVirtualCode(String code) {
    	//判断code是否为空
		if (null == code) {
			//如果为空则返回null
			return null;
		}
		//执行查询操作
		return motorcadeServeSalesAreaDao
				.queryMotorcadeServeSalesAreaByVirtualCode(code);
    }

    /**
     * 精确查询 
     * 根据多个编码批量查询 车队负责的营业区域
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaService#queryMotorcadeServeSalesAreaByCode(java.lang.String)
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaBatchByVirtualCode(
	    String[] codes) {
    	//对数组进行非空判断
		if (ArrayUtils.isEmpty(codes)) {
			//返回为空
			return null;
		}
		//执行查询操作
		return motorcadeServeSalesAreaDao
				.queryMotorcadeServeSalesAreaBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询 车队负责的营业区域
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaService#queryMotorcadeServeSalesAreaExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity, int, int)
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaExactByEntity(
	    MotorcadeServeSalesAreaEntity entity, int start, int limit) {
    	//执行查询操作
		return motorcadeServeSalesAreaDao.queryMotorcadeServeSalesAreaExactByEntity(
			entity, start, limit);
    }

    /**
     * 精确查询 车队负责的营业区域
     * 查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaService#queryMotorcadeServeSalesAreaExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeServeSalesAreaEntity)
     */
    @Override
    public long queryMotorcadeServeSalesAreaExactByEntityCount(MotorcadeServeSalesAreaEntity entity) {
    	//执行查询操作
    	return motorcadeServeSalesAreaDao.queryMotorcadeServeSalesAreaExactByEntityCount(entity);
    }
	
    /**
     * 精确查询 车队负责的营业区域,返回所有的结果，
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaExactByEntityAll(
	    MotorcadeServeSalesAreaEntity entity) {
    	//执行查询操作
    	return this.queryMotorcadeServeSalesAreaExactByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
 
    /**
     * 模糊查询 车队负责的营业区域
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeServeSalesAreaService#deleteMotorcadeServeSalesAreaMore(java.lang.String[])
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByEntity(
	    MotorcadeServeSalesAreaEntity entity, int start, int limit) {
    	//执行分页查询
    	return motorcadeServeSalesAreaDao.queryMotorcadeServeSalesAreaByEntity(entity, start, limit);
    }

    /**
     * 模糊查询 车队负责的营业区域
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     * @see com.deppon.foss.module.baseinfo.server.service.IMotorcadeServeSalesAreaService#queryMotorcadeServeSalesAreaCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.MotorcadeServeSalesAreaEntity)
     */
    @Override
    public long queryMotorcadeServeSalesAreaByEntityCount(MotorcadeServeSalesAreaEntity entity) {
    	//执行分页查询
    	return motorcadeServeSalesAreaDao.queryMotorcadeServeSalesAreaByEntityCount(entity);
    }

    /**
     * 根据entity模糊查询 车队负责的营业区域,返回所有的结果，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午8:59:11
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> queryMotorcadeServeSalesAreaByEntityAll(
	    MotorcadeServeSalesAreaEntity entity) {
    	//执行查询操作
    	return this.queryMotorcadeServeSalesAreaByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }

    /**
     * 通过MotorcadeCode车队编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:05:26
     */	
    public MotorcadeServeSalesAreaEntity deleteMotorcadeServeSalesAreaByMotorcadeCode(MotorcadeServeSalesAreaEntity entity){
    	//执行查询操作
    	return motorcadeServeSalesAreaDao.deleteMotorcadeServeSalesAreaByMotorcadeCode(entity);
    }
    
    /**
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    public List<MotorcadeServeSalesAreaEntity> addMotorcadeServeSalesAreaMore(List<MotorcadeServeSalesAreaEntity> entitys){
    	List<MotorcadeServeSalesAreaEntity> motorcadeServeSalesAreaEntitys = new ArrayList<MotorcadeServeSalesAreaEntity>();
    	//遍历entity
    	for (MotorcadeServeSalesAreaEntity entity : entitys) {
    		//先判断该entity是否在库中存在
    		MotorcadeServeSalesAreaEntity oldAreaEntity =
    				motorcadeServeSalesAreaDao.queryMotorcadeServeSalesAreaByMotorCodeAndSalesAreaCode(entity.getMotorcadeCode(), entity.getSalesareaCode());
    		//若在库中不存在
    		if(oldAreaEntity ==null){
    			//执行插入操作
    			MotorcadeServeSalesAreaEntity motorcadeServeSalesAreaEntity = this.addMotorcadeServeSalesArea(entity);
    			if(motorcadeServeSalesAreaEntity != null){
    				motorcadeServeSalesAreaEntitys.add(motorcadeServeSalesAreaEntity);
    			}
    		}
		}
    	//完成插入操作之后返回遍历后的对象
		return motorcadeServeSalesAreaEntitys;
    }

	/**
	 * @param  motorcadeServeSalesAreaDao  
	 */
	public void setMotorcadeServeSalesAreaDao(
			IMotorcadeServeSalesAreaDao motorcadeServeSalesAreaDao) {
		this.motorcadeServeSalesAreaDao = motorcadeServeSalesAreaDao;
	}
}