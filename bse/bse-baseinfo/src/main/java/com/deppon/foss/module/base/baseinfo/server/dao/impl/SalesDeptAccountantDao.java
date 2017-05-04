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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/SalesDeptAccountantDao.java
 * 
 * FILE NAME        	: SalesDeptAccountantDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity;
import com.deppon.foss.util.UUIDUtils;


public class SalesDeptAccountantDao extends iBatis3DaoImpl implements ISalesDeptAccountantDao {

    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.salesDeptAccountant.";
    
    /**
     * 
     * 新增区域会计
     * @author 027443-foss-zhaopeng
     * @date 2012-10-30 下午5:18:19
     * @param entity 区域会计实体
     * @return 1 成功，-1 失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao#addSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public int addSalesDeptAccountant(SalesDeptAccountantEntity entity) {
	
	//根据小组规范，设置ID在DAO层
	entity.setId(UUIDUtils.getUUID());
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /**
     * 
     * 更新区域会计信息
     * @author 027443-foss-zhaopeng
     * @date 2012-10-31 上午9:31:08
     * @param entity 最新的区域会计实体
     * @return 1 成功，-1 失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao#updateSalesDeptAccountant(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public int updateSalesDeptAccountant(SalesDeptAccountantEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /**
     * 
     * 根据传入的code删除对应的区域会计信息
     * @author 027443-foss-zhaopeng
     * @date 2012-10-31 上午10:18:03
     * @param codes 区域会计code数组，modifyUser 修改人工号
     * @return 1 成功,-1 失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao#deleteSalesDeptAccountantByCodes(java.lang.String[], java.lang.String)
     */
    @Override
    public int deleteSalesDeptAccountantByCodes(String[] ids,
	    String modifyUser) {
	//把前台参数传入ibatis配置文件中
	Map<String,Object> map = new HashMap<String, Object>();
	map.put("ids", ids);
	//区域会计不需要时间建模，因此直接删掉
//	map.put("modifyUser", modifyUser);
//	map.put("modifyDate", new Date());
//	map.put("active", FossConstants.INACTIVE);
//	map.put("active0", FossConstants.ACTIVE);
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }


    /**
     * 
     * 根据查询条件查询总记录行数
     * @author 027443-foss-zhaopeng
     * @date 2012-10-31 上午11:12:11
     * @param entity 把界面查询条件封装为一个区域会计实体
     * @return 总行数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity)
     */
    @Override
    public Long queryRecordCount(SalesDeptAccountantEntity entity) {
	
	return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryRecordCount", entity);
    }

    /**
     * 
     * 分页查询
     * @author 027443-foss-zhaopeng
     * @date 2012-10-31 上午11:24:45
     * @param entity 查询条件
     * @param limit 每页最大显示条数
     * @param start 开始条数
     * @return 结果集
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDeptAccountantDao#querySalesDeptAccountantGroup(com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDeptAccountantEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SalesDeptAccountantEntity> querySalesDeptAccountantGroup(
	    SalesDeptAccountantEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryALlInfos", entity, rowBounds);
    }
    
    

}
