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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AirportDao.java
 * 
 * FILE NAME        	: AirportDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“机场信息”的数据库对应数据访问DAO接口实现类：SUC-52
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-2 下午5:30:00</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-2 下午5:30:00
 * @since
 * @version
 */
public class AirportDao extends SqlSessionDaoSupport implements IAirportDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".airport";

    /**
     * <p>新增一个“机场信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:10
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#addAirport(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public int addAirport(AirportEntity airport) {
	airport.setId(UUIDUtils.getUUID());
	airport.setActive(FossConstants.ACTIVE);
	airport.setCreateDate(new Date());
	airport.setModifyDate(airport.getCreateDate());
	airport.setModifyUser(airport.getCreateUser());
	return getSqlSession().insert(NAMESPACE  + ".addAirport", airport);
    }

    /**
     * <p>新增一个“机场信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:12
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#addAirportBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public int addAirportBySelective(AirportEntity airport) {
	airport.setId(UUIDUtils.getUUID());
	airport.setActive(FossConstants.ACTIVE);
	airport.setCreateDate(new Date());
	airport.setModifyDate(airport.getCreateDate());
	airport.setModifyUser(airport.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addAirportBySelective", airport);
    }
    
    /**
     * <p>根据“机场信息”记录唯一标识作废（物理删除）一条“机场信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:08
     * @param id id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#deleteAirport(java.lang.String)
     */
    @Override
    public int deleteAirport(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteAirport", id);
    }
    
    /**
     * <p>修改一个“机场信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:17
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#updateAirportBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public int updateAirportBySelective(AirportEntity airport) {
	airport.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirportBySelective", airport);
    }

    /**
     * <p>修改一个“机场信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:19
     * @param airport “机场信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#updateAirport(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public int updateAirport(AirportEntity airport) {
	airport.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateAirport", airport);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:53:14
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return “机场信息”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#queryAirportBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public AirportEntity queryAirportBySelective(AirportEntity airport) {
	if(StringUtils.isBlank(airport.getId())){
	    //强制设置为只查询“启用”的记录
	    airport.setActive(FossConstants.ACTIVE);
	}
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<AirportEntity> airportList = getSqlSession().selectList(NAMESPACE + ".queryAirportListBySelective", airport, rowBounds);
	if (CollectionUtils.isEmpty(airportList)) {
	    return null;
	}
	return airportList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return 符合条件的“机场信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#queryAirportListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirportEntity> queryAirportListBySelective(AirportEntity airport) {
	//强制设置为只查询“启用”的记录
	airport.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryAirportListBySelective", airport);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“机场信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @return 符合条件的“机场信息”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#queryAirportCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public long queryAirportCountBySelectiveCondition(AirportEntity airport) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录s
	airport.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryAirportCountBySelectiveCondition", airport);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param airport 以“机场信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“机场信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao#queryAirportListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AirportEntity> queryAirportListBySelectiveCondition(
            AirportEntity airport, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	airport.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryAirportListBySelectiveCondition", airport, new RowBounds(offset, limit));
    }
}
