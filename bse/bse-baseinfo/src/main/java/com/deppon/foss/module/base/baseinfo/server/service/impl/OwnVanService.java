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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/OwnVanService.java
 * 
 * FILE NAME        	: OwnVanService.java
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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVanService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnTractorsException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVanException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwnVehicleException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“公司车辆-厢式车”的数据库对应数据访问Service接口：SUC-785
 * <p>需要版本控制：已实现</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-19 上午11:07:16</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-19 上午11:07:16
 * @since
 * @version
 */
public class OwnVanService extends OwnVehicleService implements IOwnVanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnVanService.class);
    
    /**
     * <p>新增一个“公司厢式车”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:37
     * @param ownTruck “公司厢式车”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws OwnVanException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVanService#addOwnVan(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addOwnVan(OwnTruckEntity ownTruck, String createUser, boolean ignoreNull) throws OwnVanException{
	if (null == ownTruck) {
	    throw new OwnVanException("", "厢式车信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnTractorsException("", "车牌号为空");
	}
	try {
	    addOwnVehicle(ownTruck, createUser, ignoreNull, DictionaryValueConstants.VEHICLE_TYPE_VAN);
	} catch (OwnVehicleException e) {
	    LOGGER.error("新增一个公司厢式车信息失败", e);
	    throw new OwnVanException("", e.getMessage(), e);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“公司厢式车”记录唯一标识作废（逻辑删除）一条“公司厢式车”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:40
     * @param id 记录唯一标识
     * @param modifyUser 修改人
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws OwnVanException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVanService#deleteOwnVan(java.lang.String)
     */
    @Override
    @Transactional
    public int deleteOwnVan(OwnTruckEntity ownTruck, String modifyUser) throws OwnVanException{
	if (null == ownTruck) {
	    throw new OwnVanException("", "厢式车信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnVanException("", "车牌号为空");
	}
	if (StringUtils.isBlank(ownTruck.getId())) {
	    throw new OwnTractorsException("", "ID为空");
	}
	try {
	    deleteOwnVehicle(ownTruck, modifyUser, DictionaryValueConstants.VEHICLE_TYPE_VAN);
	} catch (OwnVehicleException e) {
	    LOGGER.error("作废一个公司厢式车信息失败", e);
	    throw new OwnVanException("", e.getMessage(), e);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“公司厢式车”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-19 下午2:32:43
     * @param ownTruck “公司厢式车”实体
     * @param modifyUser 修改人
     * @return 1：成功；-1：失败
     * @throws OwnVanException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVanService#updateOwnVan(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity)
     */
    @Override
    @Transactional
    public int updateOwnVan(OwnTruckEntity ownTruck, String modifyUser) throws OwnVanException{
	if (null == ownTruck) {
	    throw new OwnVanException("", "厢式车信息为空");
	}
	if (StringUtils.isBlank(ownTruck.getVehicleNo())) {
	    throw new OwnVanException("", "车牌号为空");
	}
	try {
	    updateOwnVehicle(ownTruck, modifyUser, DictionaryValueConstants.VEHICLE_TYPE_VAN);
	} catch (OwnVehicleException e) {
	    LOGGER.error("修改一个公司厢式车信息失败", e);
	    throw new OwnVanException("", e.getMessage(), e);
	}
	return FossConstants.SUCCESS;
    }
}
