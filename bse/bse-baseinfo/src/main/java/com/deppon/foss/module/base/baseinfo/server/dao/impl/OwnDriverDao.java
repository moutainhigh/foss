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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/OwnDriverDao.java
 * 
 * FILE NAME        	: OwnDriverDao.java
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

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司司机”的数据库对应数据访问DAO接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-29 下午4:26:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-29 下午4:26:16
 * @since
 * @version
 */
public class OwnDriverDao extends iBatis3DaoImpl implements IOwnDriverDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".owndriver";
    
    /**
     * <p>新增一个“公司司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:32:42
     * @param ownDriver “公司司机”实体
     * @return 影响记录数 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#addOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
     */
    @Override
    public int addOwnDriver(OwnDriverEntity ownDriver) {
	ownDriver.setId(UUIDUtils.getUUID());
	ownDriver.setCreateDate(new Date());
	ownDriver.setActive(FossConstants.ACTIVE);
	ownDriver.setModifyUser(ownDriver.getCreateUser());
	ownDriver.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addOwnDriver", ownDriver);
    }

    /**
     * <p>新增一个“公司司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:33:24
     * @param ownDriver “公司司机”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#addOwnDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
     */
    @Override
    public int addOwnDriverBySelective(OwnDriverEntity ownDriver) {
	ownDriver.setId(UUIDUtils.getUUID());
	ownDriver.setCreateDate(new Date());
	ownDriver.setActive(FossConstants.ACTIVE);
	ownDriver.setModifyUser(ownDriver.getCreateUser());
	ownDriver.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addOwnDriverBySelective", ownDriver);
    }

    /**
     * <p>根据“公司司机”记录唯一标识删除（物理删除）一条“公司司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:34:08
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#deleteOwnDriver(java.lang.String)
     */
    @Override
    public int deleteOwnDriver(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteOwnDriver", id);
    }

    /**
     * <p>修改一个“公司司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:50:24
     * @param ownDriver “公司司机”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#updateOwnDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
     */
    @Override
    public int updateOwnDriverBySelective(OwnDriverEntity ownDriver) {
	ownDriver.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateOwnDriverBySelective", ownDriver);
    }

    /**
     * <p>修改一个“公司司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:50:46
     * @param ownDriver “公司司机”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#updateOwnDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
     */
    @Override
    public int updateOwnDriver(OwnDriverEntity ownDriver) {
	ownDriver.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateOwnDriver", ownDriver);
    }

    /**
     * <p>根据“公司司机”记录唯一标识查询出一条“公司司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:51:06
     * @param id 记录唯一标识
     * @return “公司司机”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#queryOwnDriver(java.lang.String)
     */
    @Override
    public OwnDriverEntity queryOwnDriver(String id) {
	return (OwnDriverEntity) getSqlSession().selectOne(NAMESPACE + ".queryOwnDriver", id);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“公司司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 下午4:51:24
     * @param ownDriver 以“公司司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司司机”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#queryOwnDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<OwnDriverEntity> queryOwnDriverListBySelectiveCondition(
	    OwnDriverEntity ownDriver, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	ownDriver.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryOwnDriverListBySelectiveCondition", ownDriver, new RowBounds(offset, limit));
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午6:46:51
     * @param driverName 司机姓名集合
     * @return DriverBaseDto封装的对象集合
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#queryOwnDriverBaseDtosByDriverNames(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DriverBaseDto> queryOwnDriverBaseDtosByDriverNames(
	    String[] driverNames) {
	List<DriverBaseDto> driverBaseDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("driverNames", driverNames);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	driverBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryOwnDriverBaseDtosByDriverNames", parameters);
        return driverBaseDtos;
    }

	/** 
	 * 根据条件查询总条数
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:02:46
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnDriverDao#queryOwnDriverRecordByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnDriverEntity)
	 */
	@Override
	public long queryOwnDriverRecordByCondition(OwnDriverEntity ownDriver) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE+".queryOwnDriverRecordByCondition", ownDriver);
	}
}
