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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/VehicleBrandDao.java
 * 
 * FILE NAME        	: VehicleBrandDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“车辆品牌”的数据库对应数据访问DAO接口实现类：无SUC,由LMS调用同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-29 上午9:25:40</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-29 上午9:25:40
 * @since
 * @version
 */
public class VehicleBrandDao extends SqlSessionDaoSupport implements IVehicleBrandDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".vehiclebrand";
    
    /**
     * <p>新增一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:48
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#addVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    public int addVehicleBrand(VehicleBrandEntity vehicleBrand) {
	vehicleBrand.setId(UUIDUtils.getUUID());
	vehicleBrand.setCreateDate(new Date());
	vehicleBrand.setActive(FossConstants.ACTIVE);
	vehicleBrand.setModifyDate(vehicleBrand.getCreateDate());
	vehicleBrand.setModifyUser(vehicleBrand.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addVehicleBrand", vehicleBrand);
    }

    /**
     * <p>新增一个“车辆品牌”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:45
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#addVehicleBrandBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    public int addVehicleBrandBySelective(VehicleBrandEntity vehicleBrand) {
	vehicleBrand.setId(UUIDUtils.getUUID());
	vehicleBrand.setCreateDate(new Date());
	vehicleBrand.setActive(FossConstants.ACTIVE);
	vehicleBrand.setModifyDate(vehicleBrand.getCreateDate());
	vehicleBrand.setModifyUser(vehicleBrand.getCreateUser());
	return getSqlSession().insert(NAMESPACE + ".addVehicleBrandBySelective", vehicleBrand);
    }

    /**
     * <p>根据“车辆品牌”记录唯一标识作废一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:50
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#deleteVehicleBrand(java.lang.String)
     */
    @Override
    public int deleteVehicleBrand(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteVehicleBrand", id);
    }

    /**
     * <p>修改一个“车辆品牌”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:39
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#updateVehicleBrandBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    public int updateVehicleBrandBySelective(VehicleBrandEntity vehicleBrand) {
	vehicleBrand.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateVehicleBrandBySelective", vehicleBrand);
    }

    /**
     * <p>修改一个“车辆品牌”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:37
     * @param vehicleBrand “车辆品牌”实体
     * @return 影响记录数
     * @see
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#updateVehicleBrand(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    public int updateVehicleBrand(VehicleBrandEntity vehicleBrand) {
	vehicleBrand.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateVehicleBrand", vehicleBrand);
    }

    /**
     * <p>根据“车辆品牌”记录唯一标识查询出一条“车辆品牌”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:54:41
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @return “车辆品牌”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleBrandDao#queryVehicleBrandBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleBrandEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public VehicleBrandEntity queryVehicleBrandBySelective(VehicleBrandEntity vehicleBrand) {
	if(StringUtils.isBlank(vehicleBrand.getId())){
	    //强制设置为只查询“启用”的记录
	    vehicleBrand.setActive(FossConstants.ACTIVE);
	}

	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<VehicleBrandEntity> vehicleBrandList = getSqlSession().selectList(NAMESPACE + ".queryVehicleBrandListBySelective", vehicleBrand, rowBounds);
	if (CollectionUtils.isEmpty(vehicleBrandList)) {
	    return null;
	}
	return vehicleBrandList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:22:42
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @return 符合条件的“车辆品牌”实体列表
     * @see
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleBrandEntity> queryVehicleBrandListBySelective(
            VehicleBrandEntity vehicleBrand) {
	//强制设置为只查询“启用”的记录
	vehicleBrand.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryVehicleBrandListBySelective", vehicleBrand);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆品牌”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:22:42
     * @param vehicleBrand 以“车辆品牌”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆品牌”实体列表
     * @see
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleBrandEntity> queryVehicleBrandBySelectiveCondition(
	    VehicleBrandEntity vehicleBrand, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	vehicleBrand.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryVehicleBrandListBySelective", vehicleBrand, new RowBounds(offset, limit));
    }
}
