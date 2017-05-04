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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/GoodsAreaDao.java
 * 
 * FILE NAME        	: GoodsAreaDao.java
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

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 库区Dao
 * @author foss-zhujunyong
 * @date Oct 22, 2012 11:56:57 AMR
 * @version 1.0
 */
public class GoodsAreaDao extends SqlSessionDaoSupport implements IGoodsAreaDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".goodsArea.";

    /** 
     * <p>添加库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#addGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
    @Override
    public GoodsAreaEntity addGoodsArea(GoodsAreaEntity goodsArea) {
	Date now = new Date();
	goodsArea.setId(UUIDUtils.getUUID());
	goodsArea.setVirtualCode(goodsArea.getId());
	goodsArea.setCreateDate(now);
	goodsArea.setModifyDate(new Date(NumberConstants.ENDTIME));
	goodsArea.setModifyUser(goodsArea.getCreateUser());
	goodsArea.setActive(FossConstants.ACTIVE);
	goodsArea.setVersion(now.getTime());
	double volume;
	//计算体积
//	if(StringUtil.isNotEmpty(goodsArea.getGoodsAreaWidth())
//			&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaHeight())
//			&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaLength())){
//		volume = Double.parseDouble(goodsArea.getGoodsAreaWidth())*Double.parseDouble(goodsArea.getGoodsAreaHeight())*Double.parseDouble(goodsArea.getGoodsAreaLength());
//	}else{
//		volume=0;
//	}
	/**
	 * 2015.5.13  
	 *   2.1  库区面积=库区所包含所有库位面积（库位长度*库位宽度）之和，库区面积允许手动修改；    
     *   2.2  修改库区体积计算方法，库区体积=库区面积*高度
	 */
	if(StringUtil.isNotEmpty(goodsArea.getArea())
			&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaHeight())
			){
		volume = Double.parseDouble(goodsArea.getArea())*Double.parseDouble(goodsArea.getGoodsAreaHeight());
	}else{
		volume=0;
	}
	goodsArea.setVolume(volume+"");
	int result =  getSqlSession().insert(NAMESPACE + "addGoodsArea", goodsArea);
	return result > 0 ? goodsArea : null;
    }

    /** 
     * <p>作废库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#deleteGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
    @Override
    public GoodsAreaEntity deleteGoodsArea(GoodsAreaEntity goodsArea) {
	Date now = new Date();
	goodsArea.setActive(FossConstants.INACTIVE);
	goodsArea.setModifyDate(now);
	goodsArea.setVersion(now.getTime());
	int result = getSqlSession().update(NAMESPACE + "deleteGoodsArea", goodsArea); 
	return result > 0 ? goodsArea : null;
    }

    /** 
     * <p>更新库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#updateGoodsArea(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
    @Override
    public GoodsAreaEntity updateGoodsArea(GoodsAreaEntity goodsArea) {
	GoodsAreaEntity entity = deleteGoodsArea(goodsArea);
	if (entity == null) {
	    return null;
	}
	
	goodsArea.setId(UUIDUtils.getUUID());
	goodsArea.setCreateDate(entity.getModifyDate());
	goodsArea.setVersion(entity.getModifyDate().getTime());
	goodsArea.setModifyDate(new Date(NumberConstants.ENDTIME));
	goodsArea.setCreateUser(entity.getModifyUser());
	goodsArea.setActive(FossConstants.ACTIVE);
	double volume;
	//计算体积
//	if(StringUtil.isNotEmpty(goodsArea.getGoodsAreaWidth())&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaHeight())&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaLength())){
//		volume = Double.parseDouble(goodsArea.getGoodsAreaWidth())*Double.parseDouble(goodsArea.getGoodsAreaHeight())*Double.parseDouble(goodsArea.getGoodsAreaLength());
//	}else{
//		volume=0;
//	}
	/**
	 * 2015.5.13  
	 *   2.1  库区面积=库区所包含所有库位面积（库位长度*库位宽度）之和，库区面积允许手动修改；    
     *   2.2  修改库区体积计算方法，库区体积=库区面积*高度
	 */
	if(StringUtil.isNotEmpty(goodsArea.getArea())
			&&StringUtil.isNotEmpty(goodsArea.getGoodsAreaHeight())
			){
		volume = Double.parseDouble(goodsArea.getArea())*Double.parseDouble(goodsArea.getGoodsAreaHeight());
	}else{
		volume=0;
	}
	goodsArea.setVolume(volume+"");
	int result = getSqlSession().insert(NAMESPACE + "addGoodsArea", goodsArea);
	return result > 0 ? entity : null;
    }


    /** 
     * <p>根据虚拟代码查询库区</p> 
     * @author 232607 
     * @date 2015-8-5 下午5:23:03
     * @param virtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaByVirtualCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@Override
    public GoodsAreaEntity queryGoodsAreaByVirtualCode(String virtualCode) {
		Map<String, String> map = new HashMap<String, String> ();
		map.put("active", FossConstants.ACTIVE);
		map.put("virtualCode", virtualCode);
		List<GoodsAreaEntity> entitys=getSqlSession().selectList(NAMESPACE + "queryGoodsAreaByVirtualCode", map);
		if(entitys.size()>0){
			return entitys.get(0);
		}else{
			return null;
		}
    }

    /** 
     * <p>查询指定条件的库区列表</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param goodsArea
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaByCondition(GoodsAreaEntity goodsArea, int start, int limit) {
	GoodsAreaEntity entity = goodsArea == null ? new GoodsAreaEntity() : goodsArea;
	entity.setActive(FossConstants.ACTIVE);
	return (List<GoodsAreaEntity>)getSqlSession().selectList(NAMESPACE + "queryGoodsAreaListByCondition", entity, new RowBounds(start, limit));
    }

    /**  
     * <p>查询指定条件的库区数量</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#countGoodsAreaByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
    @Override
    public long countGoodsAreaByCondition(GoodsAreaEntity goodsArea) {
	goodsArea.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "countGoodsAreaListByCondition", goodsArea);
    }

    /** 
     * <p>查询指定外场下的所有库区</p> 
     * @author foss-zhujunyong
     * @date Oct 22, 2012 11:56:58 AM
     * @param organizationCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaListByOrganizationCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaListByOrganizationCode(
	    String organizationCode, String goodsAreaType) {
	Map<String, String> map = new HashMap<String, String> ();
	map.put("active", FossConstants.ACTIVE);
	map.put("organizationCode", organizationCode);
	map.put("goodsAreaType", goodsAreaType);
	return (List<GoodsAreaEntity>) getSqlSession().selectList(NAMESPACE + "queryGoodsAreaListByOrganizationCode", map);
    }
    /**
     * 根据外场编码查询有计票标志的库区
     * 
     * @author  lifanghong
     * @Date    2014-02-13
     * @param   orgCode
     * @return  List<GoodsAreaEntity>
     *  
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryCountingModeGoodsAreaListByOrganizationCode(
	    String organizationCode) {
	Map<String, String> map = new HashMap<String, String> ();
	map.put("active", FossConstants.ACTIVE);
	map.put("organizationCode", organizationCode);
	return (List<GoodsAreaEntity>) getSqlSession().selectList(NAMESPACE + "queryCountingModeGoodsAreaListByOrganizationCode", map);
    }

    /** 
     * <p>下载货区</p> 
     * @author foss-zhujunyong
     * @date Oct 31, 2012 10:46:38 AM
     * @param goodsArea
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaListForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaListForDownload(
	    GoodsAreaEntity goodsArea) {
	return (List<GoodsAreaEntity>) getSqlSession().selectList(NAMESPACE + "queryGoodsAreaListForDownload", goodsArea);
    }

    /**
    * <p>下载货区</p> 
    * @author foss-zhujunyong
    * @date Oct 31, 2012 10:46:38 AM
    * @param goodsArea
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaListForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity)
    */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaListForDownloadByPage(GoodsAreaEntity goodsArea, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return (List<GoodsAreaEntity>) getSqlSession().selectList(NAMESPACE + "queryGoodsAreaListForDownload", goodsArea, rowBounds);
    }

    @Override
    public int deleteGoodsAreas(List<String> virtualCodes, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	Date now = new Date();
	map.put("active", FossConstants.ACTIVE);
	map.put("inactive", FossConstants.INACTIVE);
	map.put("modifyDate", now);
	map.put("modifyUser", modifyUser);
	map.put("version", now.getTime());
	map.put("virtualCodes", virtualCodes);
	return getSqlSession().update(NAMESPACE + "deleteGoodsAreas", map);
    }

    @Override
    public Date queryLastModifyTime() {
	Long version = (Long) getSqlSession().selectOne(NAMESPACE + "queryLastVersion");
	if (version == null) {
	    return null;
	}
	return new Date(version);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsAreaEntity> queryGoodsAreaListForCache() {
	String active = FossConstants.ACTIVE;
	return (List<GoodsAreaEntity>)getSqlSession().selectList(NAMESPACE + "queryGoodsAreaListForCache", active);
    }

    /**
     * 根据外场编码查询快递货区
     * 
     * @author  WangPeng
     * @Date    2013-8-12 下午2:23:39
     * @param   orgCode
     * @return  GoodsAreaEntity
     *  
     */
    @SuppressWarnings("unchecked")
	public List<GoodsAreaEntity> queryExpressGoodsAreaByTransCenterCode(String orgCode) {
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressGoodsAreaByTransCenterCode", orgCode);
	}
    /** 
	 * <p>作为接口给中转调用
	 *    	  查询参数：驻地营业部编码
	 *    	  返回参数：快递驻地库区和驻地派送库区，这两个库区的实体（包含code和name）</p> 
     * @author 232607 
     * @date 2015-6-8 上午11:11:47
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IGoodsAreaDao#queryGoodsAreaByStationSalesDept(java.lang.String)
     */
    @SuppressWarnings("unchecked")
	public List<GoodsAreaEntity> queryGoodsAreaByStationSalesDept(String code){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code", code);
		return getSqlSession().selectList(NAMESPACE + "queryGoodsAreaByStationSalesDept", map);
	}

}
