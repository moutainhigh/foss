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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ProvinceCityInfoService.java
 * 
 * FILE NAME        	: ProvinceCityInfoService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IProvinceCityInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity;
 import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 银行省市信息Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-19 下午5:55:58 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 下午5:55:58
 * @since
 * @version
 */
public class ProvinceCityInfoService implements IProvinceCityInfoService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceCityInfoService.class);
    
    /**
     * 银行省市信息Service接口.
     */
    private IProvinceCityInfoDao provinceCityInfoDao;
    
    /**
     * 设置 银行省市信息Service接口.
     *
     * @param provinceCityInfoDao the new 银行省市信息Service接口
     */
    public void setProvinceCityInfoDao(IProvinceCityInfoDao provinceCityInfoDao) {
        this.provinceCityInfoDao = provinceCityInfoDao;
    }

    /**
     * <p>新增银行省市信息</p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午5:55:58
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService#addProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)
     */
    @Override
    public int addProvinceCity(ProvinceCityEntity entity) {
	
	if(null == entity){
	    
	    return FossConstants.FAILURE;
	}
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	//新增时修改时间与创建时间相同
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	//新增时数据为有效状态
	entity.setActive(FossConstants.ACTIVE);
	
	return provinceCityInfoDao.addProvinceCity(entity);
    }

    /**
     * <p>根据code作废银行省市信息</p>.
     *
     * @param code 
     * @param modifyUser 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午5:56:00
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService#deleteProvinceCity(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteProvinceCity(String code, String modifyUser) {
	
	if(StringUtil.isBlank(code)){
	    return FossConstants.FAILURE;
	}
	LOGGER.debug("code: " + code);
	return provinceCityInfoDao.deleteProvinceCity(code, modifyUser);
    }

    /**
     * <p>修改银行省市信息</p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 下午5:56:00
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService#updateProvinceCity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ProvinceCityEntity)
     */
    @Override
    public int updateProvinceCity(ProvinceCityEntity entity) {
	
	if(null == entity){
	    return FossConstants.FAILURE;
	}
	if(StringUtil.isBlank(entity.getDistrictCode())){
	    return FossConstants.FAILURE;
	}
	return provinceCityInfoDao.updateProvinceCity(entity);
    }
    
    /**
     * <p>根据省市编码查询省市信息</p>.
     *
     * @param code 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-1 下午5:07:39
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IProvinceCityInfoService#queryCityEntityByCode(java.lang.String)
     */
    @Override
    public ProvinceCityEntity queryCityEntityByCode(String code){
	if(StringUtil.isBlank(code)){
	    return null;
	}
	LOGGER.debug("code: " + code);
	
	return provinceCityInfoDao.queryCityEntityByCode(code);
    }

}
