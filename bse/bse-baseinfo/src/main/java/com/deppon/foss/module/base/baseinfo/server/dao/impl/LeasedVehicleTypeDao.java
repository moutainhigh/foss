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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LeasedVehicleTypeDao.java
 * 
 * FILE NAME        	: LeasedVehicleTypeDao.java
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

import java.math.BigDecimal;
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“车辆车型”的数据库对应数据访问DAO接口的实现类：SUC-109
 * <p style="display:none">modifyvehicleType</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-13 上午10:46:20</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-13 上午10:46:20
 * @since
 * @version
 */
public class LeasedVehicleTypeDao extends SqlSessionDaoSupport implements ILeasedVehicleTypeDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".vehicletype";
    
    private ISyncVehicleTypeService syncVehicleTypeService;
    
    public void setSyncVehicleTypeService(
			ISyncVehicleTypeService syncVehicleTypeService) {
		this.syncVehicleTypeService = syncVehicleTypeService;
	}
    
    /**
     * 车型每公里费用表基础资料同步 
     * @author 310854
     * @date 2016-4-20
     */
    private void syncVehicleType(VehicleTypeEntity vehicleType){
    	List<VehicleTypeEntity> entitys = new ArrayList<VehicleTypeEntity>();
    	entitys.add(vehicleType);
    	syncVehicleTypeService.syncVehicleType(entitys);
    }
    
    /** 
     * <p>新增一个“车辆车型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#addLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    public int addLeasedVehicleType(VehicleTypeEntity vehicleType) {
	vehicleType.setId(UUIDUtils.getUUID());
	vehicleType.setCreateDate(new Date());
	vehicleType.setActive(FossConstants.ACTIVE);
	vehicleType.setModifyUser(vehicleType.getCreateUser());
	vehicleType.setModifyDate(vehicleType.getCreateDate());
	
	int result = getSqlSession().insert(NAMESPACE + ".addLeasedVehicleType", vehicleType);
	if(result > 0){
		syncVehicleType(vehicleType);
	}
	return result;
    }

    /** 
     * <p>新增一个“车辆车型”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#addLeasedVehicleTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    public int addLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType) {
	vehicleType.setId(UUIDUtils.getUUID());
	vehicleType.setCreateDate(new Date());
	vehicleType.setActive(FossConstants.ACTIVE);
	vehicleType.setModifyUser(vehicleType.getCreateUser());
	vehicleType.setModifyDate(vehicleType.getCreateDate());
	
	int result = getSqlSession().insert(NAMESPACE + ".addLeasedVehicleTypeBySelective", vehicleType);
	//  车型每公里费用表基础资料同步 
    if(result > 0){
    	syncVehicleType(vehicleType);
    }
	return result;
    }
    
    /** 
     * <p>根据“车辆车型”记录唯一标识删除（物理删除）一条“车辆车型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#deleteLeasedVehicleType(java.lang.String)
     */
    @Override
    public int deleteLeasedVehicleType(String id) {
    	int result = getSqlSession().delete(NAMESPACE + ".deleteLeasedVehicleType", id);
    	if(result > 0){
    		VehicleTypeEntity vehicleType = new VehicleTypeEntity();
    		vehicleType.setId(id);
    		vehicleType.setModifyDate(new Date());
    		vehicleType.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
    		vehicleType.setActive("N");
    		
    		syncVehicleType(vehicleType);
    	}
	return result;
    }

    /** 
     * <p>修改一个“车辆车型”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#updateLeasedVehicleTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    public int updateLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType) {
	vehicleType.setModifyDate(new Date());
	
	int result = getSqlSession().update(NAMESPACE + ".updateLeasedVehicleTypeBySelective", vehicleType);
	if(result > 0){
		syncVehicleType(vehicleType);
	}
	return result;
    }

    /** 
     * <p>修改一个“车辆车型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param vehicleType “车辆车型”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#updateLeasedVehicleType(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    public int updateLeasedVehicleType(VehicleTypeEntity vehicleType) {
	vehicleType.setModifyDate(new Date());
	
	int result = getSqlSession().update(NAMESPACE + ".updateLeasedVehicleType", vehicleType);
	if(result > 0){
		syncVehicleType(vehicleType);
	}
	return result;
    }
    
    /**
     * <p>根据“车辆车型”记录ID集合作废（逻辑删除）多条“车辆车型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-23 下午5:52:27
     * @param modifyUser 修改人
     * @param active 启用或者禁用
     * @param ids 唯一标识集合
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#deleteLeasedVehicleTypeByBatchIds(java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    public int updateLeasedVehicleTypeActiveStatusByBatchIds(String modifyUser, String active, List<String> ids) {
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("ids", ids);
	parameters.put("modifyUser", modifyUser);
	parameters.put("modifyDate", new Date());
	parameters.put("active", active);
        return getSqlSession().update(NAMESPACE + ".updateLeasedVehicleTypeActiveStatusByBatchIds", parameters);
    }

    /**
     * <p>获取当前系统中此对象最大的序列号</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-12 上午11:10:24
     * @param vehicleType 车辆类型（厢式车/挂车）
     * @param vehicleSort 公司车或外请车
     * @return 当前最大的序列号
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#queryLeasedVehicleTypeMaxSeq(java.lang.String, java.lang.String)
     */
    @Override
    public BigDecimal queryLeasedVehicleTypeMaxSeq(String vehicleType, String vehicleSort) {
	VehicleTypeEntity vehicleTypeParameter = new VehicleTypeEntity();
	vehicleTypeParameter.setVehicleType(vehicleType);
	vehicleTypeParameter.setVehicleSort(vehicleSort);
	Object maxSeq = getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleTypeMaxSeq", vehicleTypeParameter);
	if (null == maxSeq) {
	    maxSeq = NumberConstants.NUMERAL_ZORE;
	}
        return NumberUtils.createBigDecimal(maxSeq.toString());
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午3:55:45
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @return “车辆车型”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#queryLeasedVehicleTypeBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public VehicleTypeEntity queryLeasedVehicleTypeBySelective(VehicleTypeEntity vehicleType) {
	if(StringUtils.isBlank(vehicleType.getId())){
	    //强制设置为只查询“启用”的记录
	    vehicleType.setActive(FossConstants.ACTIVE);
	}
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<VehicleTypeEntity> vehicleTypeList = getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleTypeListBySelective", vehicleType, rowBounds);
	if (CollectionUtils.isEmpty(vehicleTypeList)) {
	    return null;
	}
	return vehicleTypeList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:37:46
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @return 符合条件的“车辆车型”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#queryLeasedVehicleTypeListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleTypeEntity> queryLeasedVehicleTypeListBySelective(VehicleTypeEntity vehicleType) {
	//强制设置为只查询“启用”的记录
	vehicleType = vehicleType == null ? new VehicleTypeEntity() : vehicleType;
	vehicleType.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleTypeListBySelective", vehicleType);
    }

    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“车辆车型”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param leasedDriver 以“车辆车型”实体承载的条件参数实体
     * @return 符合条件的“车辆车型”实体记录条数 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#queryLeasedVehicleTypeRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    public long queryLeasedVehicleTypeRecordCountBySelectiveCondition(
            VehicleTypeEntity vehicleType) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	vehicleType.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleTypeRecordCountBySelectiveCondition", vehicleType);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    /** 
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“车辆车型”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-13 上午10:46:20
     * @param vehicleType 以“车辆车型”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“车辆车型”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleTypeDao#queryLeasedVehicleTypeListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleTypeEntity> queryLeasedVehicleTypeListBySelectiveCondition(
	    VehicleTypeEntity vehicleType,int offset,int limit) {
	//强制设置为只查询“启用”的记录
	vehicleType.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleTypeListBySelectiveCondition", vehicleType, new RowBounds(offset, limit));
    }
 
    /** 
     * 查找是否存在序列号
     * @author 101911-foss-zhouChunlai
     * @date 2013-4-20 上午9:37:15
     */
    @Override
    public long queryLeasedVehicleSeqBySeq(BigDecimal seq) {
	long recordRount = 0;
	Map<String,String> map=new HashMap<String, String>();
	map.put("active",FossConstants.ACTIVE);
	map.put("seq",seq.toString());
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleSeqBySeq", map);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
}
