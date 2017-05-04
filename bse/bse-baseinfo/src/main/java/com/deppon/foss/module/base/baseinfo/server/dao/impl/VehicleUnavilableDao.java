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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/VehicleUnavilableDao.java
 * 
 * FILE NAME        	: VehicleUnavilableDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司车停车计划”的数据库对应数据访问DAO接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-20 上午10:07:35</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-20 上午10:07:35
 * @since
 * @version
 */
public class VehicleUnavilableDao extends SqlSessionDaoSupport implements
	IVehicleUnavilableDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".vehicleunavilable";

    /**
     * <p>新增一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:37
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#addVehicleUnavilable(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity)
     */
    @Override
    public int addVehicleUnavilable(VehicleUnavilableEntity vehicleUnavilable) {
	vehicleUnavilable.setId(UUIDUtils.getUUID());
	vehicleUnavilable.setCreateDate(new Date());
	vehicleUnavilable.setActive(FossConstants.ACTIVE);
	vehicleUnavilable.setModifyDate(vehicleUnavilable.getCreateDate());
	vehicleUnavilable.setModifyUser(vehicleUnavilable.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addVehicleUnavilable", vehicleUnavilable);
    }

    /**
     * <p>新增一个“公司车停车计划”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:38
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#addVehicleUnavilableBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity)
     */
    @Override
    public int addVehicleUnavilableBySelective(VehicleUnavilableEntity vehicleUnavilable) {
	vehicleUnavilable.setId(UUIDUtils.getUUID());
	vehicleUnavilable.setCreateDate(new Date());
	vehicleUnavilable.setActive(FossConstants.ACTIVE);
	vehicleUnavilable.setModifyDate(vehicleUnavilable.getCreateDate());
	vehicleUnavilable.setModifyUser(vehicleUnavilable.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addVehicleUnavilableBySelective", vehicleUnavilable);
    }
    
    /**
     * <p>根据“公司车停车计划”记录唯一标识作废一条“公司车停车计划”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:36
     * @param id 记录唯一标识
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#deleteVehicleUnavilable(java.lang.String)
     */
    @Override
    public int deleteVehicleUnavilable(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteVehicleUnavilable", id);
    }

    /**
     * <p>修改一个“公司车停车计划”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:41
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#updateVehicleUnavilableBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity)
     */
    @Override
    public int updateVehicleUnavilableBySelective(VehicleUnavilableEntity vehicleUnavilable) {
	vehicleUnavilable.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateVehicleUnavilableBySelective", vehicleUnavilable);
    }

    /**
     * <p>修改一个“公司车停车计划”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:42
     * @param vehicleUnavilable “公司车停车计划”实体
     * @return 1：成功；0：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#updateVehicleUnavilable(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity)
     */
    @Override
    public int updateVehicleUnavilable(VehicleUnavilableEntity vehicleUnavilable) {
	vehicleUnavilable.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateVehicleUnavilable", vehicleUnavilable);
    }
    
    /**
     * <p>根据“公司车停车计划”记录唯一标识查询出一条“公司车停车计划”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:39
     * @param id 记录唯一标识
     * @return “公司车停车计划”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#queryVehicleUnavilable(java.lang.String)
     */
    @Override
    public VehicleUnavilableEntity queryVehicleUnavilable(String id) {
	return (VehicleUnavilableEntity) getSqlSession().selectOne(NAMESPACE + ".queryVehicleUnavilable", id);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:07:40
     * @param vehicleUnavilable 以“外请车厢式车”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车厢式车”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleUnavilableDao#queryVehicleUnavilableListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleUnavilableEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleUnavilableEntity> queryVehicleUnavilableListBySelectiveCondition(
	    VehicleUnavilableEntity vehicleUnavilable, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	vehicleUnavilable.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryVehicleUnavilableListBySelectiveCondition", vehicleUnavilable, new RowBounds(offset, limit));
    }
}
