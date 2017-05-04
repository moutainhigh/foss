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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/OwnVehicleDao.java
 * 
 * FILE NAME        	: OwnVehicleDao.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司车辆”的数据库对应数据访问DAO接口实现类：SUC-785
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-16 下午2:07:01</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-16 下午2:07:01
 * @since
 * @version
 */
public class OwnVehicleDao extends SqlSessionDaoSupport implements IOwnVehicleDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".owntruck";
    
    /**
     * <p>新增一个“公司车辆”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:03
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#addOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public int addOwnVehicle(OwnTruckEntity ownTruck) {
	ownTruck.setId(UUIDUtils.getUUID());
	ownTruck.setCreateDate(new Date());
	ownTruck.setActive(FossConstants.ACTIVE);
	ownTruck.setModifyUser(ownTruck.getCreateUser());
	ownTruck.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addOwnVehicle", ownTruck);
    }

    /**
     * <p>新增一个“公司车辆”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:04
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#addOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public int addOwnVehicleBySelective(OwnTruckEntity ownTruck) {
	ownTruck.setId(UUIDUtils.getUUID());
	ownTruck.setCreateDate(new Date());
	ownTruck.setActive(FossConstants.ACTIVE);
	ownTruck.setModifyUser(ownTruck.getCreateUser());
	ownTruck.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addOwnVehicleBySelective", ownTruck);
    }
    
    /**
     * <p>根据“公司车辆”记录唯一标识删除（物理删除）一条“公司车辆”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:02
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#deleteOwnVehicle(java.lang.String)
     */
    @Override
    public int deleteOwnVehicle(String id) {
	return getSqlSession().delete(NAMESPACE +".deleteOwnVehicle", id);
    }
    
    /**
     * <p>修改一个“公司车辆”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:06
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#updateOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public int updateOwnVehicleBySelective(OwnTruckEntity ownTruck) {
	ownTruck.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateOwnVehicleBySelective", ownTruck);
    }

    /**
     * <p>修改一个“公司车辆”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:07
     * @param ownTruck “公司车辆”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#updateOwnVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public int updateOwnVehicle(OwnTruckEntity ownTruck) {
	ownTruck.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateOwnVehicle", ownTruck);
    }
    
    /**
     * <p>作废公司车辆</p> 
      * @author 094463-foss-xieyantao
      * @date 2013-5-29 下午8:46:52
      * @param ownTruck “公司车辆”实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#deleteOwnVehicleByVehicleNo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public int deleteOwnVehicleByVehicleNo(OwnTruckEntity ownTruck) {
	ownTruck.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".deleteOwnVehicleByVehicleNo", ownTruck);
    }
    
    /**
     * <p>根据“公司车辆”记录唯一标识查询出一条“公司车辆”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:05
     * @param id 记录唯一标识
     * @return “公司车辆”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleBySelective(java.lang.String)
     */
    @Override
    public OwnTruckEntity queryOwnVehicleBySelective(String id) {
	OwnTruckEntity ownTruck = new OwnTruckEntity();
	ownTruck.setId(id);
	return queryOwnVehicleBySelective(ownTruck);
    }
    
    /**
     * <p>根据条件有选择的查询一个“公司车辆”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-26 下午4:41:07
     * @param ownTruck 以“公司车辆”实体为参数
     * @return “公司车辆”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public OwnTruckEntity queryOwnVehicleBySelective(OwnTruckEntity ownTruck) {
	if(StringUtils.isBlank(ownTruck.getId())){
	    //强制设置为只查询“启用”的记录
	    ownTruck.setActive(FossConstants.ACTIVE);
	}//ID存在，则记录肯定唯一，忽略其他参数

	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<OwnTruckEntity> ownTruckList = getSqlSession().selectList(NAMESPACE + ".queryOwnVehicleListBySelective", ownTruck, rowBounds);
	if (CollectionUtils.isEmpty(ownTruckList)) {
	    return null;
	}
	return ownTruckList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车厢式车”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param ownTruck 以“外请车厢式车”实体承载的条件参数实体
     * @return 符合条件的“外请车厢式车”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<OwnTruckEntity> queryOwnVehicleListBySelective(
            OwnTruckEntity ownTruck) {
	//强制设置为只查询“启用”的记录
	ownTruck.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryOwnVehicleListBySelective", ownTruck);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“公司车辆”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午11:45:13
     * @param ownTruck 以“公司车辆”实体承载的条件参数实体
     * @return 符合条件的“公司车辆”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    public long queryOwnVehicleCountBySelectiveCondition(
            OwnTruckEntity ownTruck) {
	long recordCount = 0;
	//强制设置为只查询“启用”的记录
	ownTruck.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryOwnVehicleCountBySelectiveCondition", ownTruck);
        if (null != result) {
            recordCount = Long.parseLong(result.toString());
	}
        return recordCount;
    }

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“公司车辆”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:07:05
     * @param ownTruck 以“公司车辆”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“公司车辆”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<OwnTruckEntity> queryOwnVehicleListBySelectiveCondition(
	    OwnTruckEntity ownTruck, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	ownTruck.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryOwnVehicleListBySelectiveCondition", ownTruck, new RowBounds(offset, limit));
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:44:23
     * @param vehicleNos 车牌号集合
     * @param orgId 部门编号
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @param excludeVehicleType 排除的车辆类型
     * @return VehicleBaseDto封装了传送对象集合
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleBaseDtoListByVehicleNos(java.util.List, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleBaseDto> queryOwnVehicleBaseDtoListByVehicleNos(
	    List<String> vehicleNos, String orgId, String vehicleTypeCode, String status, int offset, int limit, String excludeVehicleType) {
	List<VehicleBaseDto> vehicleBaseDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNos", vehicleNos.size() == NumberConstants.NUMERAL_ZORE ? null : vehicleNos);
	parameters.put("orgId", orgId);
	parameters.put("vehicleTypeCode", vehicleTypeCode);
	parameters.put("status", status);
	parameters.put("vehicleType", excludeVehicleType);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	vehicleBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryOwnVehicleBaseDtoListByVehicleNos", parameters, new RowBounds(offset, limit));
        return vehicleBaseDtos;
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合记录总数</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-29 上午9:44:23
     * @param vehicleNos 车牌号集合
     * @param orgId 部门编号
     * @param vehicleTypeCode 车型编号
     * @param status 车辆状态
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOwnVehicleDao#queryOwnVehicleBaseDtoCountByVehicleNos(java.util.List, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Long queryOwnVehicleBaseDtoCountByVehicleNos(List<String> vehicleNos,
            String orgId, String vehicleTypeCode, String status) {
	long recordRount = 0;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNos", vehicleNos.size() == NumberConstants.NUMERAL_ZORE ? null : vehicleNos);
	parameters.put("orgId", orgId);
	parameters.put("vehicleTypeCode", vehicleTypeCode);
	parameters.put("status", status);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryOwnVehicleBaseDtoCountByVehicleNos", parameters);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
}
