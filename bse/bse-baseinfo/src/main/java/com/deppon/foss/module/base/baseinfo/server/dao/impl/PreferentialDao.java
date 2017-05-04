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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PreferentialDao.java
 * 
 * FILE NAME        	: PreferentialDao.java
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户优惠信息Dao接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-22 上午10:31:10 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-22 上午10:31:10
 * @since
 * @version
 */
public class PreferentialDao extends SqlSessionDaoSupport implements IPreferentialDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.preferential.";

    /** 
     * 新增客户优惠信息
     * @author 094463-foss-xieyantao
     * @date 2012-11-22 上午10:27:19
     * @param entity 客户优惠信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao#addPreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)
     */
    @Override
    public int addPreferential(PreferentialEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废客户优惠信息
     * @author dp-xieyantao
     * @date 2012-11-22 上午10:27:19
     * @param crmId
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao#deletePreferentialByCode(java.lang.String, java.lang.String)
     */
    @Override
    public int deletePreferentialByCode(BigDecimal crmId, String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("code", crmId);
	map.put("modifyUser", modifyUser);
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	
	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 修改客户优惠信息
     * @author dp-xieyantao
     * @date 2012-11-22 上午10:27:19
     * @param entity 客户优惠信息实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao#updatePreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)
     */
    @Override
    public int updatePreferential(PreferentialEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户优惠信息是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午2:43:03
     * @param crmId
     * @param lastupdatetime
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao#queryPreferentialByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean queryPreferentialByCrmId(BigDecimal crmId,
	    Date lastupdatetime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("modifyDate", lastupdatetime);
	map.put("crmId", crmId);
	//客户优惠信息表是否启用没有用，因为CRM系统那边客户优惠信息没有状态
	map.put("active", null);
	
	List<PreferentialEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryPreferentialByCrmId", map);
	return CollectionUtils.isNotEmpty(list);
    }
    
    /**
     * <p>根据客户编码、时间查询客户当前时间内的客户优惠信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 上午10:20:09
     * @param customerCode 客户编码
     * @param date 查询时间
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao#queryPreferentialInfo(java.lang.String, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public PreferentialEntity queryPreferentialInfo(String customerCode,
	    Date date) {
	Map<String,Object> map = new HashMap<String, Object>();
	map.put("customerCode", customerCode);
	map.put("date", date);
	
	List<PreferentialEntity> list = this.getSqlSession().selectList(NAMESPACE + "queryPreferentialInfo", map);
	if(CollectionUtils.isEmpty(list)){
	    return null;
	}else {
	    return list.get(0);
	}
    }
    
    /**
     * <p>根据客户编码查询客户合同优惠信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-22 下午3:55:21
     * @param customerCode 客户编码
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PreferentialInfoDto> queryPreferentialInfoDtosByCustCode(String customerCode){
	Map<String,Object> map = new HashMap<String, Object>();
	map.put("customerCode", customerCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryPreferentialInfoDtosByCustCode", map);
    }
    
    /**
     * <p>根据客户编码查询客户合同信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-8-29 下午7:15:21
     * @param customerCode 客户编码
     * @return
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<PreferentialInfoDto> queryPriceVersionInfoDtosByCustCode(
			String customerCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		
		return this.getSqlSession().selectList(NAMESPACE + "queryPriceVsrsionInfoDtosByCustCode", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PreferentialInfoDto> queryCusBargainByCodeAndTime(
			String customerCode, Date date) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("customerCode", customerCode);
		map.put("date", date);
		map.put("active", "Y");
		List<PreferentialInfoDto> list= this.getSqlSession().selectList(NAMESPACE+"queryCusBargainByCodeAndTime", map);
		return list;
	}
}
