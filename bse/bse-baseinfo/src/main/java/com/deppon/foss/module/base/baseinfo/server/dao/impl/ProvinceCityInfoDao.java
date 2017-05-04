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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/ProvinceCityInfoDao.java
 * 
 * FILE NAME        	: ProvinceCityInfoDao.java
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
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 银行省市信息DAO接口实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-19 下午7:20:39 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 下午7:20:39
 * @since
 * @version
 */
public class ProvinceCityInfoDao extends SqlSessionDaoSupport implements
	IProvinceCityInfoDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.provinceCity.";

    /** 
     * <p>新增银行省市信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午7:20:39
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao#addProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)
     */
    @Override
    public int addProvinceCity(ProvinceCityEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * <p>根据code作废银行省市信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午7:20:41
     * @param code
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao#deleteProvinceCity(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteProvinceCity(String code, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("districtCode", code);
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	
	return this.getSqlSession().delete(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * <p>修改银行省市信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午7:20:41
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao#updateProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)
     */
    @Override
    public int updateProvinceCity(ProvinceCityEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * <p>根据省市编码查询省市信息</p>  
     * @author 094463-foss-xieyantao
     * @date 2012-12-1 下午5:10:39
     * @param code
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao#queryCityEntityByCode(java.lang.String)
     */
    @Override
    public ProvinceCityEntity queryCityEntityByCode(String code) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("code", code);
	map.put("active", FossConstants.ACTIVE);
	
	return (ProvinceCityEntity)this.getSqlSession().selectOne(NAMESPACE + "queryCityEntityByCode", map);
    }

}
