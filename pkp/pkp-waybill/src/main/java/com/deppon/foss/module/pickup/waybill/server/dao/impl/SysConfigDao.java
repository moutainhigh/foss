/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/SysConfigDao.java
 * 
 * FILE NAME        	: SysConfigDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.waybill.shared.dto.SysConfigDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 系统配置数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-23 下午4:06:37, </p>
 * @author foss-sunrui
 * @date 2012-10-23 下午4:06:37
 * @since
 * @version
 */
public class SysConfigDao extends iBatis3DaoImpl implements ISysConfigDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "pkp.sysConfigEntityMapper.";

    /**
     * 
     * <p>通过主键查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:10:22
     * @param id
     * @return 
     * @see com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao#selectByPrimaryKey(java.lang.String)
     */
    public ConfigurationParamsEntity queryByPrimaryKey(String id) {
    	return (ConfigurationParamsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectSysConfigByPrimaryKey", id);
    }

    /**
     * 
     * <p>通过配置项标示查询系统配置,根据confCode和orgCode一起查询</p> 
     * @author foss-sunrui
     * @date 2012-10-23 下午4:10:25
     * @param confCode
     * @param orgCode
     * @return 
     */
    public ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCode(confCode);
		if(orgCode!=null&&!orgCode.equals("")){
			sysConfig.setOrgCode(orgCode);
		}else{
			sysConfig.setOrgCode(FossConstants.ROOT_ORG_CODE);
		}
		sysConfig.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		sysConfig.setActive(FossConstants.ACTIVE);
		return (ConfigurationParamsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectSysConfigByConfCode", sysConfig);
    }
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,只根据confCode来查询</p> 
     * @author niujian
     * @date 2012-10-23 下午4:10:25
     * @param confCode
     * @return 
     */
    public ConfigurationParamsEntity queryByConfCode(String confCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCode(confCode);
		sysConfig.setActive(FossConstants.ACTIVE);
		return (ConfigurationParamsEntity) this.getSqlSession().selectOne(NAMESPACE + "selectSysConfigByConfCode", sysConfig);
    }
    
    /**
     * 
     * <p>通过配置项集合查询系统配置</p> 
     * @author foss-sunrui
     * @date 2012-10-24 下午2:36:42
     * @param confCodes
     * @param orgCode
     * @return 
     * @see com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao#queryByConfCodeArray(java.lang.String[])
     */
	public List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes, String orgCode) {
		SysConfigDto sysConfig = new SysConfigDto();
		sysConfig.setConfCodes(confCodes);
		sysConfig.setOrgCode(orgCode);
		sysConfig.setConfType(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP);
		sysConfig.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "selectSysConfigByConfCodeArray", sysConfig);
	}
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addConfig(ConfigurationParamsEntity config) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", config.getId());
		String id = (String) this.getSqlSession().selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			this.getSqlSession().insert(NAMESPACE + "insertSysConfig", config);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateConfig(ConfigurationParamsEntity config) {
		this.getSqlSession().update(NAMESPACE + "updateSysConfigByPrimaryKey", config);
	}


	/**
	 * 删除
	 * @param config
	 */
	public void delete(ConfigurationParamsEntity config) {
		this.getSqlSession().delete(NAMESPACE + "delete", config);
	}
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	public List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity){		
		return this.getSqlSession().selectList(NAMESPACE + "queryConfigurationParamsByEntity", entity);
	}
	
	/**
	 * 查询整车价格参数波动方案
	 * author PanGuoYang
	 */
	@Override
	public CarloadPricePlanDto queryByConfCode(Date date, String invoceType,
			String code) {
		CarloadPricePlanDto carloadPlan = new CarloadPricePlanDto();
		carloadPlan.setActive(FossConstants.ACTIVE);
		carloadPlan.setOrganizationCode(code);
		carloadPlan.setInvoiceType(invoceType);
		carloadPlan.setBeginTime(date);
		return  (CarloadPricePlanDto) this.getSqlSession().selectOne(NAMESPACE + "searchCarloadPlan", carloadPlan);
	}
	

}