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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/FlightDao.java
 * 
 * FILE NAME        	: FlightDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“航班信息”的数据库对应数据访问DAO接口实现类：SUC-785 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-2 下午5:28:06</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-2 下午5:28:06
 * @since
 * @version
 */
public class FlightDao extends SqlSessionDaoSupport implements IFlightDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".flight";

    /**
     * <p>新增一个“航班信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:39
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#addFlight(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public int addFlight(FlightEntity flight) {
	flight.setId(UUIDUtils.getUUID());
	flight.setActive(FossConstants.ACTIVE);
	flight.setCreateDate(new Date());
	flight.setModifyDate(flight.getCreateDate());
	flight.setModifyUser(flight.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addFlight", flight);
    }

    /**
     * <p>新增一个“航班信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:42
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#addFlightBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public int addFlightBySelective(FlightEntity flight) {
	flight.setId(UUIDUtils.getUUID());
	flight.setActive(FossConstants.ACTIVE);
	flight.setCreateDate(new Date());
	flight.setModifyDate(flight.getCreateDate());
	flight.setModifyUser(flight.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addFlightBySelective", flight);
    }
    
    /**
     * <p>根据“航班信息”记录唯一标识作废（物理删除）一条“航班信息”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:36
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#deleteFlight(java.lang.String)
     */
    @Override
    public int deleteFlight(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteFlight", id);
    }

    /**
     * <p>修改一个“航班信息”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:47
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#updateFlightBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public int updateFlightBySelective(FlightEntity flight) {
	flight.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateFlightBySelective", flight);
    }

    /**
     * <p>修改一个“航班信息”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:49
     * @param flight “航班信息”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#updateFlight(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public int updateFlight(FlightEntity flight) {
	flight.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateFlight", flight);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“航班信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:52:45
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return “航班信息”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#queryFlightBySelective(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public FlightEntity queryFlightBySelective(FlightEntity flight) {
	if(StringUtils.isBlank(flight.getId())){
	    //强制设置为只查询“启用”的记录
	    flight.setActive(FossConstants.ACTIVE);
	}//ID存在，则记录肯定唯一，忽略其他参数
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<FlightEntity> flightList = getSqlSession().selectList(NAMESPACE + ".queryFlightListBySelective", flight, rowBounds);
	if (CollectionUtils.isEmpty(flightList)) {
	    return null;
	}
	return flightList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航班信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return 符合条件的“航班信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#queryFlightListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FlightEntity> queryFlightListBySelective(FlightEntity flight) {
	//强制设置为只查询“启用”的记录
	flight.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryFlightListBySelective", flight);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“航班信息”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @return 符合条件的“航班信息”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#queryFlightCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity)
     */
    @Override
    public long queryFlightCountBySelectiveCondition(FlightEntity flight) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录s
	flight.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryFlightCountBySelectiveCondition", flight);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“航班信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @param flight 以“航班信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“航班信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IFlightDao#queryFlightListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<FlightEntity> queryFlightListBySelectiveCondition(
            FlightEntity flight, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	flight.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryFlightListBySelectiveCondition", flight, new RowBounds(offset, limit));
    }
}
