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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AirAgencyDeptDao.java
 * 
 * FILE NAME        	: AirAgencyDeptDao.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运代理网点Dao实现：提供对空运代理网点的增删改查基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 上午11:33:03 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 上午11:33:03
 * @since
 * @version
 */
public class AirAgencyDeptDao extends SqlSessionDaoSupport implements
	IAirAgencyDeptDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.outerBranch.";
    
    /**
     * 新增空运代理网点 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:34:08
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#addAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public int addAirAgencyDept(OuterBranchEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废空运代理网点  
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:34:15
     * @param codes 偏线代理网点虚拟编码数组
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#deleteAirAgencyDeptByCode(java.lang.String[])
     */
    @Override
    public int deleteAirAgencyDeptByCode(String[] codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	Date date = new Date();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", date);
	map.put("versionNo", date.getTime());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改空运代理网点  
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:34:20
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#udpateAirAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public int updateAirAgencyDept(OuterBranchEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有空运代理网点信息
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:34:26
     * @param entity 空运/偏线代理网点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#queryAirAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryAirAgencyDepts(
	    OuterBranchEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryAirInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 上午11:34:32
     * @param entity 空运/偏线代理网点实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public Long queryRecordCount(OuterBranchEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "queryAirCount", entity);
    }
    
    /**
     * <p>根据代理公司虚拟编码查询所属代理网点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-29 下午2:42:43
     * @param comVirtualCode 代理公司虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#queryAirAgencyDeptsByComVirtualCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryAirAgencyDeptsByComVirtualCode(
	    String comVirtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("comVirtualCode", comVirtualCode);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchsByComCode", map);
    }
    
    /**
     * 根据传入对象查询符合条件所有空运代理网点信息
     * @author 313353-foss-qiupeng
     * @date 2016-05-19 上午11:34:26
     * @param entity 空运/偏线代理网点实体
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyDeptDao#queryAirAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryAgencyBranchByAgentDeptName(OuterBranchEntity entity) {
	return this.getSqlSession().selectList(NAMESPACE + "queryAgencyBranchByAgentDeptName", entity);
    }

}
