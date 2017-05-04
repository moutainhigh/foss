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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AircraftTypeDao.java
 * 
 * FILE NAME        	: AircraftTypeDao.java
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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 用来操作交互“机型信息”的数据库对应数据访问DAO接口的实现类：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-13 下午5:10:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-13 下午5:10:03
 * @since
 * @version
 */
public class AircraftTypeDao extends SqlSessionDaoSupport implements IAircraftTypeDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".aircrafttype";
    
    /** 
     * <p>新增一个“机型信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#addAircraftType(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public int addAircraftType(AircraftTypeEntity aircraftType) {
	aircraftType.setId(UUIDUtils.getUUID());
	aircraftType.setCreateDate(new Date());
	aircraftType.setActive(FossConstants.ACTIVE);
	aircraftType.setModifyDate(aircraftType.getCreateDate());
	aircraftType.setModifyUser(aircraftType.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAircraftType", aircraftType);
    }

    /** 
     * <p>新增一个“机型信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#addAircraftTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public int addAircraftTypeBySelective(AircraftTypeEntity aircraftType) {
	aircraftType.setId(UUIDUtils.getUUID());
	aircraftType.setCreateDate(new Date());
	aircraftType.setActive(FossConstants.ACTIVE);
	aircraftType.setModifyDate(aircraftType.getCreateDate());
	aircraftType.setModifyUser(aircraftType.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAircraftTypeBySelective", aircraftType);
    }
    
    /** 
     * <p>根据“机型信息”记录唯一标识作废一条“机型信息”记录（不做记录版本控制，直接删除）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#deleteAircraftType(java.lang.String)
     */
    @Override
    public int deleteAircraftType(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteAircraftType", id);
    }

    /** 
     * <p>修改一个“机型信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#updateAircraftTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public int updateAircraftTypeBySelective(AircraftTypeEntity aircraftType) {
	aircraftType.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAircraftTypeBySelective", aircraftType);
    }

    /** 
     * <p>修改一个“机型信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param aircraftType “机型信息”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#updateAircraftType(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public int updateAircraftType(AircraftTypeEntity aircraftType) {
	aircraftType.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAircraftType", aircraftType);
    }
    
    /** 
     * <p>根据条件有选择的检索出符合条件的“机型信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 下午5:10:03
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return “机型信息”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#queryAircraftTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public AircraftTypeEntity queryAircraftTypeBySelective(AircraftTypeEntity aircraftType) {
	if(StringUtils.isBlank(aircraftType.getId())){
	    //强制设置为只查询“启用”的记录
	    aircraftType.setActive(FossConstants.ACTIVE);
	}
	//ID存在，则记录肯定唯一，忽略其他参数
	
	List<AircraftTypeEntity> aircraftTypeList = null;
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	aircraftTypeList = getSqlSession().selectList(NAMESPACE + ".queryAircraftTypeListBySelective", aircraftType, rowBounds);
	if (CollectionUtils.isEmpty(aircraftTypeList)) {
	    return null;
	}
	return aircraftTypeList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 上午8:46:55
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return 符合条件的“机型信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#queryAircraftTypeListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AircraftTypeEntity> queryAircraftTypeListBySelective(AircraftTypeEntity aircraftType) {
	//强制设置为只查询“启用”的记录
	aircraftType.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAircraftTypeListBySelective", aircraftType);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“机型信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @return 符合条件的“机型信息”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#queryAircraftTypeCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity)
     */
    @Override
    public long queryAircraftTypeCountBySelectiveCondition(AircraftTypeEntity aircraftType) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录s
	aircraftType.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryAircraftTypeCountBySelectiveCondition", aircraftType);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * 
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-15 上午8:48:56
     * @param aircraftType 以“机型信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“机型信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAircraftTypeDao#queryAircraftTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AircraftTypeEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AircraftTypeEntity> queryAircraftTypeListBySelectiveCondition(
            AircraftTypeEntity aircraftType, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	aircraftType.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAircraftTypeListBySelectiveCondition", aircraftType, new RowBounds(offset, limit));
    }
}
