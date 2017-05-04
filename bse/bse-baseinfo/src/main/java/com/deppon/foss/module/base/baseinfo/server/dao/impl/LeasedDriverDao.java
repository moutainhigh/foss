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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LeasedDriverDao.java
 * 
 * FILE NAME        	: LeasedDriverDao.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车司机”的数据库对应数据访问DAO接口实现类：SUC-211 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午10:53:14</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午10:53:14
 * @since
 * @version
 */
public class LeasedDriverDao extends SqlSessionDaoSupport implements ILeasedDriverDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".leaseddriver";
    
    /**
     * <p>新增一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:16
     * @param leasedDriver “外请车司机”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#addLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public int addLeasedDriver(LeasedDriverEntity leasedDriver) {
	leasedDriver.setId(UUIDUtils.getUUID());
	leasedDriver.setCreateDate(new Date());
	leasedDriver.setActive(FossConstants.ACTIVE);
	leasedDriver.setModifyUser(leasedDriver.getCreateUser());
	leasedDriver.setModifyDate(leasedDriver.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addLeasedDriver", leasedDriver);
    }

    /**
     * <p>新增一个“外请车司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:17
     * @param leasedDriver “外请车司机”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#addLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public int addLeasedDriverBySelective(LeasedDriverEntity leasedDriver) {
	leasedDriver.setId(UUIDUtils.getUUID());
	leasedDriver.setCreateDate(new Date());
	leasedDriver.setActive(FossConstants.ACTIVE);
	leasedDriver.setModifyUser(leasedDriver.getCreateUser());
	leasedDriver.setModifyDate(leasedDriver.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addLeasedDriverBySelective", leasedDriver);
    }
    
    /**
     * <p>根据“外请车司机”记录唯一标识作废一条“外请车司机”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:15
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#deleteLeasedDriver(java.lang.String)
     */
    @Override
    public int deleteLeasedDriver(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteLeasedDriver", id);
    }

    /**
     * <p>修改一个“外请车司机”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:20
     * @param leasedDriver “外请车司机”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#updateLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public int updateLeasedDriverBySelective(LeasedDriverEntity leasedDriver) {
	leasedDriver.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedDriverBySelective", leasedDriver);
    }

    /**
     * <p>修改一个“外请车司机”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:21
     * @param leasedDriver “外请车司机”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#updateLeasedDriver(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public int updateLeasedDriver(LeasedDriverEntity leasedDriver) {
	leasedDriver.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedDriver", leasedDriver);
    }
    
    /**
     * <p>根据记录唯一标识查询“外请车司机”唯一激活可用状态实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-10 上午10:17:42
     * @param id 记录唯一标识
     * @return 符合条件的“外请车司机”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverBySelective(java.lang.String)
     */
    @Override
    public LeasedDriverEntity queryLeasedDriverBySelective(String id) {
	LeasedDriverEntity leasedDriver = new LeasedDriverEntity();
	leasedDriver.setId(id);
        return queryLeasedDriverBySelective(leasedDriver);
    }
    
    /**
     * <p>根据条件有选择的查询“外请车司机”唯一激活可用状态实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-30 下午8:32:32
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public LeasedDriverEntity queryLeasedDriverBySelective(LeasedDriverEntity leasedDriver) {
	if(StringUtils.isBlank(leasedDriver.getId())){
	    //强制设置为只查询“启用”的记录
	    leasedDriver.setActive(FossConstants.ACTIVE);
	}
	//ID存在，则记录肯定唯一，忽略其他参数
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	String statement = NAMESPACE + ".queryLeasedDriverListBySelective";
	List<LeasedDriverEntity> leasedDriverList = getSqlSession().selectList(statement, leasedDriver, rowBounds);
	if (CollectionUtils.isEmpty(leasedDriverList)) {
	    return null;
	}
        return leasedDriverList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的统计出符合条件的“外请车司机”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedDriverEntity> queryLeasedDriverListBySelective(LeasedDriverEntity leasedDriver) {
	//强制设置为只查询“启用”的记录
	leasedDriver.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryLeasedDriverListBySelective", leasedDriver);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“外请车司机”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:23:18
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @return 符合条件的“外请车司机”实体记录条数 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity)
     */
    @Override
    public long queryLeasedDriverRecordCountBySelectiveCondition(LeasedDriverEntity leasedDriver) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	leasedDriver.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedDriverRecordCountBySelectiveCondition", leasedDriver);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“外请车司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:53:19
     * @param leasedDriver 以“外请车司机”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车司机”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedDriverEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedDriverEntity> queryLeasedDriverListBySelectiveCondition(
	    LeasedDriverEntity leasedDriver, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	leasedDriver.setActive(FossConstants.ACTIVE);
	List<LeasedDriverEntity> leasedDriverList = getSqlSession().selectList(NAMESPACE + ".queryLeasedDriverListBySelectiveCondition", leasedDriver, new RowBounds(offset, limit));
	if (CollectionUtils.isEmpty(leasedDriverList)) {
	    leasedDriverList = new ArrayList<LeasedDriverEntity>();
	}
	return leasedDriverList;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午5:40:33
     * @param vehicleNo 外请车车牌号
     * @return DriverBaseDto封装了的传送对象集合
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverBaseDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DriverBaseDto> queryLeasedDriverBaseDtosByVehicleNos(String[] vehicleNos) {
	List<DriverBaseDto> driverBaseDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNos", vehicleNos);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	driverBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedDriverBaseDtosByVehicleNos", parameters);
        return driverBaseDtos;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午7:04:42
     * @param driverNames 司机姓名集合 
     * @return DriverBaseDto封装的对象集合
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverBaseDtosByDriverNames(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DriverBaseDto> queryLeasedDriverBaseDtosByDriverNames(String[] driverNames) {
	List<DriverBaseDto> driverBaseDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("driverNames", driverNames);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	driverBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedDriverBaseDtosByDriverNames", parameters);
        return driverBaseDtos;
    }

    /**
     * 提供给"中转"模块使用，根据"外请车牌号"来获取公司司机相关的数据实体和其相关联的其他信息的DTO
     *
     * @author foss-qiaolifeng
     * @date 2013-7-8 下午3:03:52
     * @param vehicleNo 外请车车牌号
     * @return DriverBaseDto封装了的传送对象集合
     * @throws LeasedDriverException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedDriverDao#queryLeasedDriverBaseDtosByTruckVehicleNos(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverBaseDto> queryLeasedDriverBaseDtosByTruckVehicleNos(
			String[] vehicleNos) {
		List<DriverBaseDto> driverBaseDtos = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("vehicleNos", vehicleNos);
		//强制设置为只查询“启用”的记录
		parameters.put("active", FossConstants.ACTIVE);
		driverBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedDriverBaseDtosByTrukcVehicleNos", parameters);
	        return driverBaseDtos;
	}
}
