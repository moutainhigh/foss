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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/RegionalVehicleDao.java
 * 
 * FILE NAME        	: RegionalVehicleDao.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定人定区Dao实现类：对定人定区提供增删改查的操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-12 下午2:27:18 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-12 下午2:27:18
 * @since
 * @version
 */
public class RegionalVehicleDao extends SqlSessionDaoSupport implements
	IRegionalVehicleDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.regionvehicle.";
    
    /**
     * 新增定人定区 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:27:18
     * @param entity 车辆定区实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int addRegionalVehicle(RegionVehicleEntity entity) {
	
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废定人定区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:27:18
     * @param codes
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @see
     */
    //update by 092020-lipengfei 由于区分一二级定区车，无法确定需要作废数据的条数，故将String[]修改为List<String>
    @Override
    public int deleteRegionalVehicleByCode(List<String> codes,String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }
    
    /**
     * 修改定人定区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:27:18
     * @param entity 车辆定区实体
     * @return 1：成功；-1：失败
     * @see
     */
    @Override
    public int updateRegionalVehicle(RegionVehicleEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有定人定区信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:27:18
     * @param entity 车辆定区实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleInfoDto> queryRegionalVehicles(
	    RegionVehicleEntity entity, int limit, int start) {
        
	RowBounds rowBounds = new RowBounds(start, limit);
	/*if(StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())
			||StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, entity.getVehicleType())){
		entity.setVehicleType(DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR);
	}*/
	return this.getSqlSession().selectList(NAMESPACE + "getAllInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-12 下午2:27:18
     * @param entity 车辆定区实体
     * @return
     * @see
     */
    @Override
    public Long queryRecordCount(RegionVehicleEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount", entity);
    }
    
    /**
     * 根据区域编码查询定车定区绑定的车辆信息
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 上午11:10:08
     * @param regionCode 区域编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehiclesByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehiclesByCode(String regionCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("regionCode", regionCode);
	map.put("active", FossConstants.ACTIVE);
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehiclesByCode", map);
    }
    
    /**
     * <p>根据传入的参数查询车辆定区信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-7 下午2:08:20
     * @param vehicleNoList 车牌号列表
     * @param regionType 接送货类型：接货区、送货区
     * @param regionNature 区域类型:大区、小区
     * @return null 或 车辆定区信息列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehiclesByParam(java.util.List, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehiclesByParam(
	    List<String> vehicleNoList, String regionType, String regionNature) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("vehicleNoList", vehicleNoList);
	map.put("regionType", regionType);
	map.put("regionNature", regionNature);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehiclesByParam", map);
    }
    
    /**
     * <p>根据gis的区域id匹配接货小区id及车辆车牌号</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-15 下午6:16:29
     * @param gisId GIS系统小区范围ID
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#querySmallZoneInfoByParam(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<RegionVehicleEntity> querySmallZoneInfoByGisId(String gisId) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("gisId", gisId);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "querySmallZoneInfoByGisId", map);
    }
    
    /**
     * <p>通过传入一个车队或车队下调度组的code，查询出车队下的所有接货小区</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 上午11:58:23
     * @param vehicleOrgCode 车队code或车队下调度组的code
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehiclesByOrgCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehiclesByOrgCode(
	    List<String> vehicleOrgCodes) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("codeList",vehicleOrgCodes);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehiclesByOrgCode", map);
    }
    
    /**
     * <p>根据多个区域的虚拟编码查询定车定区信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午8:30:34
     * @param regionCodes 区域的虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehiclesByRegionCodes(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehiclesByRegionCodes(
	    List<String> regionCodes) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("regionCodes",regionCodes);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehiclesByRegionCodes", map);
    }
    
    /**
     * <p>验证车牌号与区域的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-14 上午9:19:39
     * @param vehicleNo 车牌号
     * @param regionCode 区域虚拟编码
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehicleByVehicleAndRegionCode(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehicleByVehicleAndRegionCode(
	    String vehicleNo, String regionCode) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("vehicleNo",vehicleNo);
	map.put("regionCode",regionCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehicleByVehicleAndRegionCode", map);
    }
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-1-23 下午2:31:22
     * @param vehicleNo 车牌号
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryRegionVehicleByVehicleAndVehicleType(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryRegionVehicleByVehicleAndVehicleType(
	    String vehicleNo) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("vehicleNo",vehicleNo);
	//机动车
	map.put("vehicleMotor",DictionaryValueConstants.MOTOR_VEHICLE);
	//定区车
	//update by 092020-lipengfei. 定区车分为一级定区车和二级定区车，SQL查询时通过like来统一判断
	map.put("vehicleCar", DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryRegionVehicleByVehicleAndVehicleType", map);
    }
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午3:30:31
     * @param vehicleNo 车牌号
     * @param vehicleType 车辆职责类型
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryInfosVehicleAndVehicleType(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryInfosVehicleAndVehicleType(
	    String vehicleNo, String vehicleType) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("vehicleNo",vehicleNo);
	map.put("vehicleType", vehicleType);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryInfosVehicleAndVehicleType", map);
    }
    
    /**
     * <p>根据车牌号、接送货类型、车辆职责类型验证定车定区是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-15 下午5:02:17
     * @param vehicleNo 车牌号
     * @param regionType 接送货类型：接货区、送货区
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao#queryInfoByVehicleNoAndRegionType(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegionVehicleEntity> queryInfoByVehicleNoAndRegionType(
	    String vehicleNo, String regionType,String vehicleType) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active",FossConstants.ACTIVE);
	map.put("vehicleNo",vehicleNo);
	map.put("regionType", regionType);
	map.put("vehicleType", vehicleType);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryInfoByVehicleNoAndRegionType", map);
    }

	/**
	 * @author 092020-lipengfei
	 * @Description 根据区域编码和车辆职责类型查询定车定区数量
	 * @Time 2014-4-18
	 * @param regionCode
	 * @param vehicleType
	 * @return
	 */
	@Override
	public int queryNumByRegionCodeAndVehicleType(String regionCode,
			String vehicleType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active",FossConstants.ACTIVE);
		map.put("regionCode", regionCode);
		map.put("vehicleType", vehicleType);
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryNumByRegionCodeAndVehicleType", map);
	}

    /**
     * @author 092020-lipengfei
     * @Description 根据大区编码查询定人定区
     * @Time 2014-4-22
     * @param codes
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<RegionVehicleEntity> getRegionVehicleEntityByBigZoonCode(String regionCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active0",FossConstants.ACTIVE);
		map.put("regionCode",regionCode);
		return this.getSqlSession().selectList(NAMESPACE + "getRegionVehicleEntityByBigZoonCode", map);
	}
    
	/**
     * 根据大区或者小区的虚拟编码去作废定人定区的数据
     * code对应定人定区表里region_code
     * auther:wangpeng_078816
     * date:2014-6-9
     *
     */
	@Override
	public int deleteRegionalVehicleByRegionCode(List<String> codes,
			String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("active", FossConstants.INACTIVE);
		map.put("active0", FossConstants.ACTIVE);
		
		return this.getSqlSession().update(NAMESPACE + "deleteByRegionCode", map);
	}
	/**
	 * 
	 *<p>Title: queryInfosByRegionCodeAndVihicleTypeAndRegionType</p>
	 *<p>根据区域名称，区域类型，和车辆职责类型查询唯一定区车记录</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-7-9上午10:05:50
	 * @param regionCode
	 * @param regionType
	 * @param vehicleType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RegionVehicleEntity queryInfosByRegionCodeAndVihicleTypeAndRegionType(
			String regionCode, String regionType, String vehicleType) {

		RegionVehicleEntity entity =new RegionVehicleEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setVehicleType(vehicleType);
		entity.setRegionCode(regionCode);
		entity.setRegionType(regionType);
		List<RegionVehicleEntity> list =getSqlSession().selectList(NAMESPACE+"queryInfosByRegionCodeAndVihicleTypeAndRegionType",entity);
		//非空校验
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	 /**
     * @author dujunuhi-187862
     * @Description 自动排单项目：根据小区编码查询对应车牌号
     * @Time 2014-4-22
     * @param codes
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
    public List<RegionVehicleEntity> getRegionVehicleNoBySmallZoneCode(String regionCode){
		RegionVehicleEntity entity = new RegionVehicleEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setRegionCode(regionCode);
		entity.setRegionNature(DictionaryValueConstants.REGION_NATURE_SQ);//小区
		List<RegionVehicleEntity> list=this.getSqlSession().
				selectList(NAMESPACE + "getRegionVehicleNOBySmallZoneCode", entity);
		if(CollectionUtils.isNotEmpty(list)){//如果有，就返回小区的车辆
			return list;
		}else{//如果没有，返回空
			return null;
		}
	}
    
}
