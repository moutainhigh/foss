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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SalesMotorcadeService.java
 * 
 * FILE NAME        	: SalesMotorcadeService.java
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部车队关系 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-14 上午12:38:58
 */
public class SalesMotorcadeService implements ISalesMotorcadeService {

    /**
     * 营业部车队关系 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService#addSalesMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity)
     */
    @Override
    @Transactional
    public SalesMotorcadeEntity addSalesMotorcade(SalesMotorcadeEntity entity) {
	// 检查参数的合法性
	if (null == entity) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getSalesdeptCode()) || StringUtils.isBlank(entity.getMotorcadeCode())) {
	    return null;
	}
	return salesMotorcadeDao.addSalesMotorcade(entity);
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeDao#deleteSalesMotorcade(java.lang.String)
     */
    @Override
    @Transactional
    public SalesMotorcadeEntity deleteSalesMotorcade(SalesMotorcadeEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	return salesMotorcadeDao.deleteSalesMotorcade(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeDao#deleteSalesMotorcadeMore(java.lang.String[])
     */
    @Override
    @Transactional
    public SalesMotorcadeEntity deleteSalesMotorcadeMore(String[] codes , String deleteUser) {
	return salesMotorcadeDao.deleteSalesMotorcadeMore(codes, deleteUser);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeDao#updateSalesMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity)
     */
    @Override
    @Transactional
    public SalesMotorcadeEntity updateSalesMotorcade(SalesMotorcadeEntity entity) {
	// 检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	if (StringUtils.isBlank(entity.getSalesdeptCode()) || StringUtils.isBlank(entity.getMotorcadeCode())) {
	    return null;
	}
	return salesMotorcadeDao.updateSalesMotorcade(entity);
    }


    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeService#querySalesMotorcadeByCode(java.lang.String)
     */
    @Override
    public SalesMotorcadeEntity querySalesMotorcadeByVirtualCode(String code) {
	if (null == code) {
	    return null;
	}
	return salesMotorcadeDao.querySalesMotorcadeByVirtualCode(code);
    }


    /**
     * 精确查询 
     * 根据多个编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeService#querySalesMotorcadeByCode(java.lang.String)
     */
    @Override
    public List<SalesMotorcadeEntity> querySalesMotorcadeBatchByVirtualCode(
	    String[] codes) {
	if (codes==null||codes.length==0){
	    return null;
	}
	
	return salesMotorcadeDao.querySalesMotorcadeBatchByVirtualCode(codes);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeService#querySalesMotorcadeExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity, int, int)
     */
    @Override
    public List<SalesMotorcadeEntity> querySalesMotorcadeExactByEntity(
	    SalesMotorcadeEntity entity, int start, int limit) {
	return salesMotorcadeDao.querySalesMotorcadeExactByEntity(
		entity, start, limit);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeService#querySalesMotorcadeExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity)
     */
    @Override
    public long querySalesMotorcadeExactByEntityCount(SalesMotorcadeEntity entity) {
	return salesMotorcadeDao.querySalesMotorcadeExactByEntityCount(entity);
    }
 
    /**
     * 模糊查询 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesMotorcadeService#deleteSalesMotorcadeMore(java.lang.String[])
     */
    @Override
    public List<SalesMotorcadeEntity> querySalesMotorcadeByEntity(
	    SalesMotorcadeEntity entity, int start, int limit) {
	return salesMotorcadeDao.querySalesMotorcadeByEntity(entity, start, limit);
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:38:58
     * @see com.deppon.foss.module.baseinfo.server.service.ISalesMotorcadeService#querySalesMotorcadeCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.SalesMotorcadeEntity)
     */
    @Override
    public long querySalesMotorcadeByEntityCount(SalesMotorcadeEntity entity) {
	return salesMotorcadeDao.querySalesMotorcadeByEntityCount(entity);
    }
    
		
    /**
     * 下面是特殊方法
     */

    /**
     * 通过salesdeptCode营业部编码 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    public SalesMotorcadeEntity deleteSalesMotorcadeBySalesdeptCode(SalesMotorcadeEntity entity){
	return salesMotorcadeDao.deleteSalesMotorcadeBySalesdeptCode(entity);
    }
    /**
     * 
     *<p>通过motorcadeCode车队编码来废除</p>
     *@author 130566-zengJunfan
     *@date   2013-7-26上午9:04:41
     * @param entity
     * @return
     */
    @Override
	public SalesMotorcadeEntity deleteSalesMotorcadeEntityByMotorcadeCode(
			SalesMotorcadeEntity entity) {
    	
		return salesMotorcadeDao.deleteSalesMotorcadeEntityByMotorcadeCode(entity);
	}
    /**
     * 批量插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 上午12:51:18
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesProductDao#deleteSalesProduct(java.lang.String)
     */
    @Override
    public List<SalesMotorcadeEntity> addSalesMotorcadeMore(List<SalesMotorcadeEntity> entitys){
    	List<SalesMotorcadeEntity> salesMotorcadeEntitys = new ArrayList<SalesMotorcadeEntity>();
	for(SalesMotorcadeEntity entity : entitys){
		//库中查询数据
		SalesMotorcadeEntity entity2 =
				salesMotorcadeDao.querySalesMotorcadeBySalesCodeAndMotorCode(entity.getSalesdeptCode(), entity.getMotorcadeCode());
	    //若库区不存在
		if(entity2==null){
			SalesMotorcadeEntity salesMotorcadeEntity = this.addSalesMotorcade(entity);
			if(salesMotorcadeEntity != null){
				salesMotorcadeEntitys.add(salesMotorcadeEntity);
			}
	    }
	}
	return salesMotorcadeEntitys;
    }
    
    /**
     * 
     * <p>通过营业部查询关联的车队部门编码</p> 
     * @author foss-zhujunyong
     * @date Apr 10, 2013 3:31:31 PM
     * @param salesCode
     * @return
     * @see
     */
    @Override
    public List<String> querySalesMotorcadeListBySales(String salesCode){
	List<String> result = new ArrayList<String> ();
	if (StringUtils.isBlank(salesCode)) {
	    return result;
	}
	SalesMotorcadeEntity c = new SalesMotorcadeEntity();
	c.setActive(FossConstants.ACTIVE);
	c.setSalesdeptCode(salesCode);
	List<SalesMotorcadeEntity> list = querySalesMotorcadeByEntity(c, 0, Integer.MAX_VALUE);
	if (CollectionUtils.isEmpty(list)) {
	    return result;
	}
	for (SalesMotorcadeEntity entity : list) {
	    result.add(entity.getMotorcadeCode());
	}
	return result;
    }

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private ISalesMotorcadeDao salesMotorcadeDao;

    public void setSalesMotorcadeDao(ISalesMotorcadeDao salesMotorcadeDao) {
	this.salesMotorcadeDao = salesMotorcadeDao;
    }

	
}
