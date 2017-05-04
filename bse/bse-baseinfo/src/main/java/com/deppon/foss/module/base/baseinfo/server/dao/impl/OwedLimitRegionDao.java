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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/OwedLimitRegionDao.java
 * 
 * FILE NAME        	: OwedLimitRegionDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 临欠额度区间范围信息DAO接口实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-25 上午9:43:51 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-25 上午9:43:51
 * @since
 * @version
 */
public class OwedLimitRegionDao extends SqlSessionDaoSupport implements
	IOwedLimitRegionDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.owedLimitRegion.";
    
    /** 
     * <p>添加临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:43:52
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#addInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public int addInfo(OwedLimitRegionEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * <p>作废临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:43:52
     * @param ids
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#deleteInfos(java.util.List, java.lang.String)
     */
    @Override
    public int deleteInfos(List<String> ids, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	Date date = new Date();
	map.put("ids", ids);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", date);
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteById", map);
    }

    /** 
     * <p>修改临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:43:52
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#updateInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public int updateInfo(OwedLimitRegionEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * <p>分页查询临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:43:52
     * @param entity
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#queryAllInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OwedLimitRegionEntity> queryAllInfos(
	    OwedLimitRegionEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * <p>查询总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 上午9:43:52
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public Long queryRecordCount(OwedLimitRegionEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    /**
     * <p>根据传入的营业部收入查询营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午10:12:51
     * @param taking 营业部收入
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#queryInfoByTaking(java.math.BigDecimal)
     */
    @Override
    public OwedLimitRegionEntity queryInfoByTaking(BigDecimal taking,String id) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("taking", taking);
	map.put("id", id);
	
	return (OwedLimitRegionEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoByTaking", map);
    }
    
    /**
     * <p>验证额度是否在额度区间范围内</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-27 上午8:48:19
     * @param taking
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#queryInfoByRegionValue(java.math.BigDecimal, java.lang.String)
     */
    @Override
    public OwedLimitRegionEntity queryInfoByRegionValue(BigDecimal taking,String id) {
   	Map<String, Object> map = new HashMap<String, Object>();
   	map.put("active", FossConstants.ACTIVE);
   	map.put("taking", taking);
   	map.put("id", id);
   	
   	return (OwedLimitRegionEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoByRegionValue", map);
    }
    
    /**
     * <p>更新营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午11:00:55
     * @param dto 封装有部门编码和最大临欠额度
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#updateDepartmentAmountOwed(com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto)
     */
    @Override
    public int updateDepartmentAmountOwed(OwedLimitDto dto) {
	// 如果最大临欠额度为null，则没有必要执行sql更新
	if (dto.getOwedLimit() == null) {
	    return 0;
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("dto", dto);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "updateDepartmentAmountOwed", map);
    }
    
    /**
     * <p>批量更新营业部最大临欠额度</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-1 上午8:47:10
     * @param list
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#batchUpdateDeptAmountOwed(java.util.List)
     */
    @Override
    public int batchUpdateDeptAmountOwed(List<OwedLimitDto> list) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("list", list);
	map.put("active", FossConstants.ACTIVE);
	
	// 该批量更新存在性能问题，现在改为在Service中循环修改，在mapper文件中该方法已注释掉
	return this.getSqlSession().update(NAMESPACE + "batchUpdateDeptAmountOwed", map);
    }
    
    /**
     * <p>根据最小额度、最大额度查询额度信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-27 下午6:49:19
     * @param minValue
     * @param maxValue
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao#queryInfoByRegionValues(java.math.BigDecimal, java.math.BigDecimal, java.lang.String)
     */
    @Override
    public OwedLimitRegionEntity queryInfoByRegionValues(BigDecimal minValue,
	    BigDecimal maxValue, String id) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("minValue", minValue);
	map.put("maxValue", maxValue);
	map.put("id", id);
	
	return (OwedLimitRegionEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoByRegionValues", map);
    }

}
