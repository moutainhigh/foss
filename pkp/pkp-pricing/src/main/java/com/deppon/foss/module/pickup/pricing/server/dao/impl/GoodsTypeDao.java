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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/GoodsTypeDao.java
 * 
 * FILE NAME        	: GoodsTypeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GoodsTypeDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 货物类型定义
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 上午8:19:44
 */
public class GoodsTypeDao extends SqlSessionDaoSupport implements IGoodsTypeDao{

    /**
     * mybatis namespace
     */
    private static final String NAMESPACE =  "foss.pkp.pkp-pricing.pricingGoodsType.";
    
    /**
     * 
     * <p>新增</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:04:51
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#insert(com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity)
     */
    @Override
    public int insert(GoodsTypeEntity entity) {
	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	UserEntity currentUser = FossUserContext.getCurrentUser();
	entity.setCreateUser(currentUser.getEmployee().getEmpCode());
	entity.setCreateOrgCode(currentDept.getCode());
	entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	return this.getSqlSession().insert(NAMESPACE + "insert",entity);
    }

    /**
     * 
     * <p>查询单个实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:05:05
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#selectByPrimaryKey(java.lang.String)
     */
    @Override
    public GoodsTypeEntity selectByPrimaryKey(String id) {
    	return (GoodsTypeEntity)getSqlSession().selectOne(NAMESPACE + "selectByPrimaryKey",id);
    }

    /**
     * 
     * <p>修改实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:05:16
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#updateByPrimaryKey(com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity)
     */
    @Override
    public int updateByPrimaryKey(GoodsTypeEntity entity) {
	 entity.setModifyDate(new Date());
	 entity.setVersionNo(System.currentTimeMillis());
	 OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
	 UserEntity currentUser = FossUserContext.getCurrentUser();
	 entity.setModifyUser(currentUser.getEmployee().getEmpCode());
	 entity.setModifyOrgCode(currentDept.getCode());
	 return getSqlSession().update(NAMESPACE + "updateByPrimaryKey",entity);
    }

    /**
     * 
     * <p>批量激活实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:05:30
     * @param goodsTypeIds
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#activationGoodsType(java.util.List)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int activationGoodsType(List<String> goodsTypeIds) {
	Map p = new HashMap();
	p.put("goodsTypeIds", goodsTypeIds);
	return getSqlSession().update(NAMESPACE + "activationGoodsType",p);
    }

    /**
     * 
     * <p>根据不同条件查询实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:05:43
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#findGoodsTypeByCondiction(com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsTypeEntity> findGoodsTypeByCondiction(
	    GoodsTypeEntity entity) {
	return getSqlSession().selectList(NAMESPACE + "findGoodsTypeByCondiction", entity);
    }

    /**
     * 
     * <p>分页查询</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:06:30
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#findGoodsTypePagingByCondiction(com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsTypeEntity> findGoodsTypePagingByCondiction(
	    GoodsTypeEntity entity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "findGoodsTypePagingByCondiction", entity, rowBounds);
    }

    /**
     * 
     * <p>查询总记录</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:06:45
     * @param entity
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#countGoodsTypePagingByCondiction(com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity)
     */
    @Override
    public Long countGoodsTypePagingByCondiction(
	    GoodsTypeEntity entity) {
	return (Long) getSqlSession().selectOne(NAMESPACE + "countGoodsTypePagingByCondiction", entity);
    }

    /**
     * 
     * <p>根据货物编码加业务日期，查询当前货物信息/p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:06:58
     * @param goodsCode
     * @param billDate
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#queryGoodsTypeByGoodTypeCode(java.lang.String, java.util.Date)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public GoodsTypeEntity queryGoodsTypeByGoodTypeCode(String goodsCode,Date billDate) {
	Map  p = new HashMap();
	p.put("active", FossConstants.ACTIVE);
	p.put("code", goodsCode);
	p.put("billDate", billDate);
	GoodsTypeEntity goodsTypeEntity = null;
	List<GoodsTypeEntity> list = getSqlSession().selectList(NAMESPACE + "queryGoodsTypeByGoodTypeCode", p);
	if(CollectionUtils.isNotEmpty(list) ){
	    goodsTypeEntity = list.get(0);
	}
	return goodsTypeEntity;
    }

    /**
     * 
     * <p>查询货物信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:07:27
     * @param entity
     * @param billDate
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IGoodsTypeDao#findGoodsTypeByCache(com.deppon.foss.module.pickup.pricing.api.shared.dto.GoodsTypeDto, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GoodsTypeEntity> findGoodsTypeByCache(GoodsTypeDto entity,
	    Date billDate) {
	entity.setBillDate(billDate);
	entity.setActive(FossConstants.ACTIVE);
	String sql = NAMESPACE +"findGoodsTypeByCache";
	return (ArrayList<GoodsTypeEntity>) getSqlSession().selectList(sql, entity); 
    }

    /**
     * @Description: 查询缓存中货物集合信息
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-28 上午11:36:07
     * @param goodsCode
     * @param billDate
     * @return
     * @version V1.0
     */
    @SuppressWarnings("unchecked")
    public GoodsTypeEntity queryGoodsTypeByCache(String goodsCode,Date billDate) {
    	GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
    	goodsTypeDto.setActive(FossConstants.ACTIVE);
    	goodsTypeDto.setBillDate(billDate);
    	String sql = NAMESPACE +"findGoodsTypeByCache";
    	List<GoodsTypeEntity> list = getSqlSession().selectList(sql, goodsTypeDto); 
    	GoodsTypeEntity goodsTypeEntity = null;
    	if(CollectionUtils.isNotEmpty(list)) {
    		goodsTypeEntity = list.get(0); 
    	} 
    	return goodsTypeEntity;
    }

    /**
     * 
     * @Description: 查询货物类型编号最大值
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-5 下午2:51:01
     * @return
     * @version V1.0
     */
	@Override
	public String getMaxGoodsTypeCode() {
		String sql = NAMESPACE +"getMaxGoodsTypeCode";
    	return (String)getSqlSession().selectOne(sql); 
	}
	
	/**
     * 
     * @Description: 根据货物类型编号和适用时间查询货物类型集合
     * @author FOSSDP-sz
     * @date 2013-1-31 上午11:07:58
     * @param entity
     * @param billDate
     * @return
     * @version V1.0
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<GoodsTypeEntity> queryGoodsTypeByCode(String goodsCode) {
		String sql = NAMESPACE +"queryGoodsTypeByCode";
		Map map = new HashMap();
		map.put("code", goodsCode);
		map.put("active", FossConstants.ACTIVE);
    	return getSqlSession().selectList(sql, map); 
    }

	/**
	 * 查询与当前name相同的记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsTypeEntity> isTheSameGoodsTypeName(String name) {
		String sql = NAMESPACE + "isTheSameGoodsTypeName";
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		return getSqlSession().selectList(sql, map);
	}
	
}