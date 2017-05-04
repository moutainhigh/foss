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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LeasedVehicleDao.java
 * 
 * FILE NAME        	: LeasedVehicleDao.java
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
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“外请车（厢式车、挂车、拖头）”的数据库对应数据访问DAO接口实现类：SUC-44、SUC-103、SUC-608
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 下午6:06:25</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 下午6:06:25
 * @since
 * @version
 */
public class LeasedVehicleDao extends SqlSessionDaoSupport implements ILeasedVehicleDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".leasedtruck";
    
    /**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:34
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#addLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    public int addLeasedVehicle(LeasedTruckEntity leasedTruck) {
	leasedTruck.setId(UUIDUtils.getUUID());
	leasedTruck.setCreateDate(new Date());
	leasedTruck.setActive(FossConstants.ACTIVE);
	//todo
	leasedTruck.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addLeasedVehicle", leasedTruck);
    }

    /**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:35
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#addLeasedVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    public int addLeasedVehicleBySelective(LeasedTruckEntity leasedTruck) {
	leasedTruck.setId(UUIDUtils.getUUID());
	leasedTruck.setCreateDate(new Date());
	leasedTruck.setActive(FossConstants.ACTIVE);
	leasedTruck.setModifyDate(new Date(NumberConstants.ENDTIME));
	return getSqlSession().insert(NAMESPACE + ".addLeasedVehicleBySelective", leasedTruck);
    }
    
    /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录唯一标识删除（物理删除）一条“外请车（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:33
     * @param id 记录唯一标识
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#deleteLeasedVehicle(java.lang.String)
     */
    @Override
    public int deleteLeasedVehicle(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteLeasedVehicle", id);
    }

    /**
     * <p>根据ID集合批量作废（逻辑删除）“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-23 下午3:37:52
     * @param ids ID集合
     * @param active 启用或禁用
     * @param modifyUser 修改人
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#deleteLeasedVehicleByBatchIds(java.lang.String, java.lang.String[])
     */
    public int deleteLeasedVehicleActiveStatusByBatchIds(String[] ids, String active, String modifyUser){
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("ids", ids);
	parameters.put("modifyUser", modifyUser);
	parameters.put("modifyDate", new Date());
	parameters.put("active", active);
	return getSqlSession().update(NAMESPACE + ".updateLeasedVehicleActiveStatusByBatchIds", parameters);
    }
    
    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:38
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#updateLeasedVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    public int updateLeasedVehicleBySelective(LeasedTruckEntity leasedTruck) {
	leasedTruck.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedVehicleBySelective",  leasedTruck);
    }

    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:39
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @return 影响记录数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#updateLeasedVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    public int updateLeasedVehicle(LeasedTruckEntity leasedTruck) {
	leasedTruck.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateLeasedVehicle", leasedTruck);
    }
    
    /**
     * <p>根据记录唯一标识检索出符合条件的“外请车（厢式车、挂车、拖头）”唯一实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:58:51
     * @param id 记录唯一标识
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleBySelective(java.lang.String)
     */
    @Override
    public LeasedTruckEntity queryLeasedVehicleBySelective(String id) {
	LeasedTruckEntity leasedTruck = new LeasedTruckEntity();
	leasedTruck.setId(id);
        return queryLeasedVehicleBySelective(leasedTruck);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:58:51
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public LeasedTruckEntity queryLeasedVehicleBySelective(LeasedTruckEntity leasedTruck) {
	if(StringUtils.isBlank(leasedTruck.getId())){
	    //强制设置为只查询“启用”的记录
	    leasedTruck.setActive(FossConstants.ACTIVE);
	}
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<LeasedTruckEntity> leasedTruckList = getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleListBySelective", leasedTruck, rowBounds);
	if (CollectionUtils.isEmpty(leasedTruckList)) {
	    return null;
	}
	return leasedTruckList.get(NumberConstants.NUMERAL_ZORE);
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:31:24
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedTruckEntity> queryLeasedVehicleListBySelective(LeasedTruckEntity leasedTruck) {
	//强制设置为只查询“启用”的记录
	leasedTruck.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleListBySelective", leasedTruck);
    }
    
    /**
     * <p>根据条件（分页模糊）有选择的统计出符合条件的“外请车（厢式车、挂车、拖头）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午10:15:40
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleRecordCountBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity)
     */
    @Override
    public long queryLeasedVehicleRecordCountBySelectiveCondition(
            LeasedTruckEntity leasedTruck) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	//leasedTruck.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleRecordCountBySelectiveCondition", leasedTruck);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午9:04:37
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedTruckEntity> queryLeasedVehicleListBySelectiveCondition(
	    LeasedTruckEntity leasedTruck, int offset, int limit) {
	//强制设置为只查询“启用”的记录,由于外请厢式车要查询无效的数据，这个不可用
	//leasedTruck.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE +".queryLeasedVehicleListBySelectiveCondition", leasedTruck, new RowBounds(offset, limit));
    }

    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 189284-ZhangXu
     * @Date 2015-3-5  下午3:59:11
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedTruckEntity> queryLeasedVehicleListBySelectiveCondition(
	    LeasedTruckEntity leasedTruck) {
	return getSqlSession().selectList(NAMESPACE +".queryLeasedVehicleListBySelectiveCondition", leasedTruck);
    }
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午3:56:03
     * @param vehicleNos 车牌号集合
     * @return VehicleAssociationDto封装了传送对象集合
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleBaseDtosByVehicleNos(java.lang.String[])
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleBaseDto> queryLeasedVehicleBaseDtosByVehicleNos(String[] vehicleNos) {
	List<VehicleBaseDto> vehicleBaseDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNos", vehicleNos);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	vehicleBaseDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleBaseDtosByVehicleNos", parameters);
        return vehicleBaseDtos;
    }
    
    /**
     * <p>提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param openVehicle 是否敞篷车（Y/N）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(
            String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status) {
	List<LeasedVehicleWithDriverDto> vehicleWithDriverDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNo", vehicleNo);
	parameters.put("vehicleLengthCode", vehicleTypeCode);
	parameters.put("driverName", driverName);
	parameters.put("driverPhone", driverPhone);
	parameters.put("openVehicle", openVehicle);
	parameters.put("status", status);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	vehicleWithDriverDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleWithDriverDtosByCondition", parameters);
        return vehicleWithDriverDtos;
    }
    
    /**
     * <p>（分页）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param openVehicle 是否敞篷车（Y/N）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(
            String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status, int offset, int limit) {
	List<LeasedVehicleWithDriverDto> vehicleWithDriverDtos = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNo", vehicleNo);
	parameters.put("vehicleLengthCode", vehicleTypeCode);
	parameters.put("driverName", driverName);
	parameters.put("driverPhone", driverPhone);
	parameters.put("openVehicle", openVehicle);
	parameters.put("status", status);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
	vehicleWithDriverDtos = getSqlSession().selectList(NAMESPACE + ".queryLeasedVehicleWithDriverDtosByCondition", parameters, new RowBounds(offset, limit));
        return vehicleWithDriverDtos;
    }
    
    /**
     * <p>（查询分页总条数）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleType 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param openVehicle 是否敞篷车（Y/N）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILeasedVehicleDao#queryLeasedVehicleWithDriverDtosByCondition(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public long countQueryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, String openVehicle, String status) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	Map<String, Object> parameters = new HashMap<String, Object>();
	parameters.put("vehicleNo", vehicleNo);
	parameters.put("vehicleLengthCode", vehicleTypeCode);
	parameters.put("driverName", driverName);
	parameters.put("driverPhone", driverPhone);
	parameters.put("openVehicle", openVehicle);
	parameters.put("status", status);
	//强制设置为只查询“启用”的记录
	parameters.put("active", FossConstants.ACTIVE);
    Object result = getSqlSession().selectOne(NAMESPACE + ".countQueryLeasedVehicleRecordCountBySelectiveCondition", parameters);
    if (null != result) {
    recordRount = Long.parseLong(result.toString());
	}
    return recordRount;
    }
    
    /**
     * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
     * 查询结果字段有：勾选框、车牌号、所属司机、司机电话、载重（吨）、自重（吨）、净空（方）、车长（米）、所属车队、操作人
     * @author 310854
     * @date 2016-5-14
     */
    @Override
    @SuppressWarnings("unchecked")
	public List<LeasedTruckEntity> queryLeasedServiceDateList(
			LeasedTruckEntity leasedTruck, int offset, int limit) {
		// 强制设置为只查询“启用”的记录,由于外请厢式车要查询无效的数据，这个不可用
		// leasedTruck.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(
				NAMESPACE + ".queryLeasedVehicleTeamList",
				leasedTruck, new RowBounds(offset, limit));
	}
    
    /**
     * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆总数
     * @author 310854
     * @date 2016-5-14
     */
    public long queryLeasedServiceDateList(
            LeasedTruckEntity leasedTruck) {
	long recordRount = 0;
	//强制设置为只查询“启用”的记录
	//leasedTruck.setActive(FossConstants.ACTIVE);
        Object result = getSqlSession().selectOne(NAMESPACE + ".queryqueryLeasedVehicleTeamListTotal", leasedTruck);
        if (null != result) {
            recordRount = Long.parseLong(result.toString());
	}
        return recordRount;
    }
    
    
    /**
     * 外清车【外请厢式车】绑定车队
     * @author 310854
     * @date 2016-5-16
     */
    public int addLeasedServiceDateTream(List<LeasedTruckEntity> list){
    	
    	return getSqlSession().insert(NAMESPACE+ ".insert_leasedTruck_tream", list);
    }
    
    /**
     * 外清车【外请厢式车】释放车队
     * @author 310854
     * @date 2016-5-16
     */
    public int deleteLeasedServiceDateTream(Map<String,Object> map){
    	
    	return getSqlSession().update(NAMESPACE+ ".update_leasedTruck_tream", map);
    }
    
    /**
     * 结算使用，通过车牌号获取司机电话
     * @author 310854
     * @date 2016-7-14
     */
    public  String queryLeasedVehicleDriverByVehicleNo(String vehicleNo){
    	return  (String)getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleDriverByVehicleNo", vehicleNo);
    }
    
    /**
	 * 通过外请车车牌号获取服务车队
	 * @author 310854
	 * @param vehicleNo
	 */
    public LeasedTruckEntity queryLeasedVehicleTeam (String vehicleNo){
    	return  (LeasedTruckEntity)getSqlSession().selectOne(NAMESPACE + ".queryLeasedVehicleTeam", vehicleNo);
    }
}
