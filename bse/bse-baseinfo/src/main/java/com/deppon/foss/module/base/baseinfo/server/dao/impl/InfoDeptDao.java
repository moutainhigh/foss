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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/InfoDeptDao.java
 * 
 * FILE NAME        	: InfoDeptDao.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“信息部”的数据库对应数据访问DAO接口实现类：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 上午10:24:07</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 上午10:24:07
 * @since
 * @version
 */
public class InfoDeptDao extends SqlSessionDaoSupport implements IInfoDeptDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".infodept";
    
    /**
     * <p>新增一个“信息部”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param infoDept “信息部”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#addInfoDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    public int addInfoDept(InfoDeptEntity infoDept) {
	infoDept.setId(UUIDUtils.getUUID());
	infoDept.setCreateDate(new Date());
	infoDept.setActive(FossConstants.ACTIVE);
	infoDept.setModifyUser(infoDept.getCreateUser());
	infoDept.setModifyDate(infoDept.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addInfoDept", infoDept);
    }

    /**
     * <p>新增一个“信息部”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param infoDept “信息部”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#addInfoDeptBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    public int addInfoDeptBySelective(InfoDeptEntity infoDept) {
	infoDept.setId(UUIDUtils.getUUID());
	infoDept.setCreateDate(new Date());
	infoDept.setActive(FossConstants.ACTIVE);
	infoDept.setModifyUser(infoDept.getCreateUser());
	infoDept.setModifyDate(infoDept.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addInfoDeptBySelective", infoDept);
    }

    /**
     * <p>根据“信息部”记录唯一标识删除（物理删除）一条“信息部”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#deleteInfoDept(java.lang.String)
     */
    @Override
    public int deleteInfoDept(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteInfoDept", id);
    }

    /**
     * <p>修改一个“信息部”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param infoDept “信息部”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#updateInfoDeptBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    public int updateInfoDeptBySelective(InfoDeptEntity infoDept) {
	infoDept.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateInfoDeptBySelective", infoDept);
    }

    /**
     * <p>修改一个“信息部”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param infoDept “信息部”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#updateInfoDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    public int updateInfoDept(InfoDeptEntity infoDept) {
	infoDept.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateInfoDept", infoDept);
    }

    /**
     * <p>根据条件有选择的查询“信息部”唯一激活可用状态实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-30 下午8:32:32
     * @param infoDept 以“信息部”实体承载的条件参数实体
     * @return 符合条件的“信息部”实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#queryInfoDeptBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public InfoDeptEntity queryInfoDeptBySelective(InfoDeptEntity infoDept) {
	if(StringUtils.isBlank(infoDept.getId())){
	    //强制设置为只查询“启用”的记录
	    infoDept.setActive(FossConstants.ACTIVE);
	}
	//ID存在，则记录肯定唯一，忽略其他参数
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	String statement = NAMESPACE + ".queryInfoDeptListBySelective";
	List<InfoDeptEntity> infoDeptList = getSqlSession().selectList(statement, infoDept, rowBounds);
	if (CollectionUtils.isEmpty(infoDeptList)) {
	    return null;
	}
        return infoDeptList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的统计出符合条件的“信息部”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDept 以“信息部”实体承载的条件参数实体
     * @return 符合条件的“信息部”实体记录条数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#queryInfoDeptListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<InfoDeptEntity> queryInfoDeptListBySelective(
	    InfoDeptEntity infoDept) {
	//强制设置为只查询“启用”的记录
	infoDept.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryInfoDeptListBySelective", infoDept);
    }

    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“信息部”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDept 以“信息部”实体承载的条件参数实体
     * @return 符合条件的“信息部”实体记录条数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#queryInfoDeptRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity)
     */
    @Override
    public long queryInfoDeptRecordCountBySelectiveCondition(
	    InfoDeptEntity infoDept) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	infoDept.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryInfoDeptRecordCountBySelectiveCondition", infoDept);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“信息部”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param infoDept 以“信息部”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“信息部”实体列表
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptDao#queryInfoDeptListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<InfoDeptEntity> queryInfoDeptListBySelectiveCondition(
	    InfoDeptEntity infoDept, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	infoDept.setActive(FossConstants.ACTIVE);
	List<InfoDeptEntity> infoDeptList = getSqlSession().selectList(NAMESPACE + ".queryInfoDeptListBySelectiveCondition", infoDept, new RowBounds(offset, limit));
	if (CollectionUtils.isEmpty(infoDeptList)) {
	    infoDeptList = new ArrayList<InfoDeptEntity>();
	}
	return infoDeptList;
    }
}
