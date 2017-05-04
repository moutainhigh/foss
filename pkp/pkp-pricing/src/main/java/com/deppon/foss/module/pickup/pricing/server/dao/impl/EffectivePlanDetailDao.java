/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/EffectivePlanDetailDao.java
 * 
 * FILE NAME        	: EffectivePlanDetailDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 时效明细信息dao
 * @author DP-Foss-YueHongJie
 * @date 2012-12-25 下午2:54:27
 * @version 1.0
 */
public class EffectivePlanDetailDao extends SqlSessionDaoSupport implements IEffectivePlanDetailDao{
    
    //mybatis 
    private static final String NAME_SPACE = "foss.pricing.EffectivePlanDetailEntityMapper.";
    //下载信息
    private static final String SELECT_DETAIL_FOR_DOWNLOAD = "selectDetailForDownload";
    //根据条件查询时效
    private static final String SELECT_DETAIL_BY_CONDITION = "queryEffectivePlanDetailByCondition";
    //添加
    private static final String INSERT = "insert";
    //修改
    private static final String UPDATE = "updateByPrimaryKeySelective";
    //删除
    private static final String DELETE = "deleteByPrimaryKey";
    //查询单个实体
    private static final String SELECT_BY_PRIMARY_KEY = "selectByPrimaryKey";
    //批量删除时效明细信息
    private static final String DELETE_EFFECTIVE_DETAIL_PLAN_BY_IDS = "deleteEffectivePlanDetailByIds";
    //批量删除时效明细信息
    private static final String ACTIVE_EFFECTIVE_DETAIL_PLAN_BY_IDS = "activeEffectivePlanDetailByIds";
    
    /**
     * 
     * <p>删除单个时效信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:56:16
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#deleteByPrimaryKey(java.lang.String)
     */
    @Override
    public int deleteByPrimaryKey(String id) {
	return this.getSqlSession().delete(NAME_SPACE+DELETE, id);
    }

    /**
     * 
     * <p>添加时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:56:29
     * @param record
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#insertSelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @Override
    public int insertSelective(EffectivePlanDetailEntity record) {
	this.setEffectivePlanDetailValuesAttribute(record);
	return this.getSqlSession().insert(NAME_SPACE+INSERT, record);
    }

    /**
     * 
     * <p>查询单个时效明细实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:56:44
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#selectByPrimaryKey(java.lang.String)
     */
    @Override
    public EffectivePlanDetailEntity selectByPrimaryKey(String id) {
	return (EffectivePlanDetailEntity) this.getSqlSession().selectOne(NAME_SPACE + SELECT_BY_PRIMARY_KEY ,id);
    }

    /**
     * 
     * <p>修改时效明细实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:57:03
     * @param record
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @Override
    public int updateByPrimaryKeySelective(EffectivePlanDetailEntity record) {
	this.setEffectivePlanDetailValuesAttribute(record);
	return this.getSqlSession().update(NAME_SPACE + UPDATE, record);
    }

    /**
     * 
     * <p>查询时效明细下载数据</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:57:37
     * @param deptRegionId
     * @param lastModifyTime
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailInfoForDownload(java.lang.String, java.util.Date)
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfoForDownload(String deptRegionId, Date lastModifyTime) {
		Map<String,Object> map  = new HashMap<String,Object>();
		map.put("deptRegionId", deptRegionId);
		map.put("lastModifyTime", lastModifyTime);
		map.put("active", FossConstants.ACTIVE);//设置有效数据标志
		map.put("billDate", new Date());//
		return getSqlSession().selectList(NAME_SPACE + SELECT_DETAIL_FOR_DOWNLOAD, map);
    }

    /**
     * 
     * <p>查询时效</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:58:11
     * @param deptRegionId
     * @param arrvRegionId
     * @param productCode
     * @param businessDate
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailListByCondition(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByCondition(String deptRegionId, String arrvRegionId, String productCode,
	   Date businessDate) {
	   EffectivePlanDto effectivePlanDto = new EffectivePlanDto();
	   effectivePlanDto.setActive(FossConstants.ACTIVE);
	   effectivePlanDto.setDeptRegionId(deptRegionId);
	   effectivePlanDto.setArrvRegionId(arrvRegionId);
	   effectivePlanDto.setProductCode(productCode);
	   effectivePlanDto.setBillDate(businessDate); 
	   return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailListByCondition", effectivePlanDto);
    }

    
    
    /**
     * 
     * 按照始发区域ID,到达区域ID,Level3 产品-精准卡航...,营业日期 查询唯一时效明细
     * @author DP-Foss-YueHongJie
     * @date 2012-11-6 下午1:41:57
     * @param deptRegionId 始发区域ID
     * @param arrvieRegionId 到达区域ID
     * @param productCode 产品编码
     * @param businessDate 营业日期
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity) 
     * @return EffectivePlanDetailEntity  时效明细信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfo(
	    EffectivePlanDetailEntity effectivePlanDetailEntity) {
	return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailInfo", effectivePlanDetailEntity);
    }

    /**
     * 
     * <p>查询时效明细分页信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:00:32
     * @param effectivePlanDetailEntity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailInfoPagging(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfoPagging(
	    EffectivePlanDetailEntity effectivePlanDetailEntity, int start,
	    int limit) {
	RowBounds rowBounds = new RowBounds(start,limit);
	return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailInfo", effectivePlanDetailEntity,rowBounds);
    }

    /**
     * 
     * <p>查询总记录数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:01:29
     * @param effectivePlanDetailEntity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailInfoPaggingCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @Override
    public Long queryEffectivePlanDetailInfoPaggingCount(
	    EffectivePlanDetailEntity effectivePlanDetailEntity) {
    	return (Long) getSqlSession().selectOne(NAME_SPACE+"queryEffectivePlanDetailInfoPaggingCount", effectivePlanDetailEntity);
    }

    /**
     * 
     * <p>查询时效方案明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:47:41
     * @param effectivePlanDetailEntity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailByCondition(
	EffectivePlanDetailEntity effectivePlanDetailEntity) {
	return getSqlSession().selectList(NAME_SPACE+SELECT_DETAIL_BY_CONDITION, effectivePlanDetailEntity);
    }
    
    
    /**
     * 
     * <p>分页查询时效方案明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:47:41
     * @param effectivePlanDetailEntity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#queryEffectivePlanDetailByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailByConditionByPage(
    		EffectivePlanDetailEntity effectivePlanDetailEntity, int started, int limited) {
    	RowBounds rowBounds = new RowBounds(started, limited);
    	return getSqlSession().selectList(NAME_SPACE+SELECT_DETAIL_BY_CONDITION, effectivePlanDetailEntity, rowBounds);
    }
    
    

    /**
     * 
     * <p>批量删除方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:46:49
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#deleteEffectivePlanDetailByIds(java.util.List)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void deleteEffectivePlanDetailByIds(List<String> ids) {
	String sql = NAME_SPACE + DELETE_EFFECTIVE_DETAIL_PLAN_BY_IDS;
	HashMap parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	getSqlSession().delete(sql,parameterMap);
    }
	
    /**
     * 
     * <p>批量激活时效方案</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午3:46:31
     * @param ids 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#activeEffectivePlanDetailByIds(java.util.List)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void activeEffectivePlanDetailByIds(List<String> ids) {
	String sql = NAME_SPACE + ACTIVE_EFFECTIVE_DETAIL_PLAN_BY_IDS;
	HashMap parameterMap = new HashMap();
	parameterMap.put("ids", ids);
	parameterMap.put("active", FossConstants.ACTIVE);
	parameterMap.put("versionNo", new Date().getTime());
	getSqlSession().update(sql,parameterMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EffectivePlanDetailEntity> searchEffectivePlanDetailEntityByCondition(
	    EffectivePlanDetailEntity effectivePlanEntity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	String sql = NAME_SPACE + "searchEffectivePlanDetailEntityByCondition";
	return  getSqlSession().selectList(sql,effectivePlanEntity,rowBounds);
    }

    @Override
    public Long searchEffectivePlanDetailEntityByConditionCount(
	    EffectivePlanDetailEntity effectivePlanEntity) {
	String sql = NAME_SPACE + "searchEffectivePlanDetailEntityByConditionCount";
	return  (Long) getSqlSession().selectOne(sql,effectivePlanEntity);
    }
    
    
    /**
     * 
     * <p>设置必要参数</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-22 下午3:51:26
     * @param entity
     * @see
     */
    private void setEffectivePlanDetailValuesAttribute(EffectivePlanDetailEntity entity){
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setCreateDate(new Date());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	entity.setVersionNo(new Date().getTime());
    }

    /**
     * 
     * @Description: 查询时效明细信息总数
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-9 下午4:52:52
     * @param deptRegionId
     * @param arrvieRegionIds
     * @param productCode
     * @param businessDate
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByArrvIds(
			String deptRegionId, List<String> arrvieRegionIds, String productCode,
			String active, Date billDate) {
		Map map = new HashMap();
		map.put("deptRegionId", deptRegionId);
		map.put("arrvRegionIds", arrvieRegionIds);
		map.put("productCode", productCode);
		map.put("active", active);
		map.put("billDate", billDate);
		return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailListByArrvIds", map);
	}
	
	/**
     * 
     * @Description: 根据出发和到达区域ID集合查询时效明细信息
     * @author FOSSDP-sz
     * @date 2013-1-25 下午4:08:33
     * @param deptRegionIds
     * @param arrvieRegionIds
     * @param productCode
     * @param active
     * @param businessDate
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByRegionIds (
    		Set<String> deptRegionIds, Set<String> arrvieRegionIds, String productCode,String active, Date billDate) {
		Map map = new HashMap();
		map.put("deptRegionIds", deptRegionIds);
		map.put("arrvRegionIds", arrvieRegionIds);
		map.put("productCode", productCode);
		map.put("active", active);
		map.put("billDate", billDate);
		return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailListByRegionIds", map);
	}
	
	/**
     * 
     * @Description: 根据出发和到达区域ID集合查询时效明细信息
     * @author FOSSDP-sz
     * @date 2013-1-25 下午4:08:33
     * @param deptRegionIds
     * @param arrvieRegionIds
     * @param productCode
     * @param active
     * @param businessDate
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailListByRegionIds (
    		List<String> deptRegionIds, List<String> arrvieRegionIds, String productCode,String active, Date billDate) {
		Map map = new HashMap();
		map.put("deptRegionIds", deptRegionIds);
		map.put("arrvRegionIds", arrvieRegionIds);
		map.put("productCode", productCode);
		map.put("active", active);
		map.put("billDate", billDate);
		return getSqlSession().selectList(NAME_SPACE+"queryEffectivePlanDetailListByRegionIds", map);
	}

    /**
     * 
     * <p>时效批量复制信息新增明细,只适合特殊场景复制而做</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-24 下午4:40:31
     * @param oldId
     * @param newId 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao#insertBatchEffectivePlanDetail(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int insertBatchEffectivePlanDetail(String oldId, String newId) {
	Map map = new HashMap();
	map.put("oldId", oldId);
	map.put("newId", newId);
	map.put("active", FossConstants.INACTIVE);//批量新增默认为不激活的数据
	return getSqlSession().insert(NAME_SPACE+"batchInsert", map);
    }
}