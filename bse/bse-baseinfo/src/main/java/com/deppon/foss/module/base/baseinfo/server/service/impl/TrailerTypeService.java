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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/TrailerTypeService.java
 * 
 * FILE NAME        	: TrailerTypeService.java
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

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITrailerTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleForTrailerTypeDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.TrailerTypeException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“挂车类型”的数据库对应“数据字典”数据访问Service接口实现类：无SUC，提供给LMS同步
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-5 上午10:47:03</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-5 上午10:47:03
 * @since
 * @version
 */
public class TrailerTypeService extends iBatis3DaoImpl implements
	ITrailerTypeService {
    
    //"数据字典值"Service接口
    private IDataDictionaryValueService dataDictionaryValueService;
    
    /**
     * <p>新增一个“LMS挂车类型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:32:53
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
     * @throws TrailerTypeException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITrailerTypeService#addTrailerType(com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleForTrailerTypeDto)
     */
    @Override
    public int addTrailerType(VehicleForTrailerTypeDto vehicleForTrailerTypeDto)
	    throws TrailerTypeException {
	if (null == vehicleForTrailerTypeDto) {
	    throw new TrailerTypeException("", "挂车类型为空");
	}
	DataDictionaryValueEntity dataDictionaryValueEntity = new DataDictionaryValueEntity();
	dataDictionaryValueEntity.setValueCode(vehicleForTrailerTypeDto.getTrailerTypeCode());
	dataDictionaryValueEntity.setValueName(vehicleForTrailerTypeDto.getTrailerTypeName());
	dataDictionaryValueEntity.setTermsCode(DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE);
	dataDictionaryValueService.addDataDictionaryValue(dataDictionaryValueEntity);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“LMS挂车类型”记录唯一标识作废（逻辑删除）一条“LMS挂车类型”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:32:57
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
     * @throws TrailerTypeException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITrailerTypeService#deleteTrailerType(com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleForTrailerTypeDto)
     */
    @Override
    public int deleteTrailerType(
	    VehicleForTrailerTypeDto vehicleForTrailerTypeDto)
	    throws TrailerTypeException {
	if (null == vehicleForTrailerTypeDto) {
	    throw new TrailerTypeException("", "挂车类型为空");
	}
	DataDictionaryValueEntity dataDictionaryValueEntity = queryTrailerType(vehicleForTrailerTypeDto);
	dataDictionaryValueService.deleteDataDictionaryValue(dataDictionaryValueEntity);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“LMS挂车类型”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 下午2:33:01
     * @param ownTruck “LMS挂车类型”实体
     * @return 1：成功；-1：失败
      * @throws TrailerTypeException 
      * @see com.deppon.foss.module.base.baseinfo.api.server.service.ITrailerTypeService#updateTrailerType(com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleForTrailerTypeDto)
      */
    @Override
    public int updateTrailerType(
	    VehicleForTrailerTypeDto vehicleForTrailerTypeDto)
	    throws TrailerTypeException {
	if (null == vehicleForTrailerTypeDto) {
	    throw new TrailerTypeException("", "挂车类型为空");
	}
	DataDictionaryValueEntity dataDictionaryValueEntity = queryTrailerType(vehicleForTrailerTypeDto);
	dataDictionaryValueService.updateDataDictionaryValue(dataDictionaryValueEntity);
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据条件有选择的检索符合条件的“LMS挂车类型”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 上午11:16:26
     * @param vehicleForTrailerTypeDto 以“LMS挂车类型”实体承载的条件参数实体
     * @return 符合条件的“LMS挂车类型”实体
     * @throws TrailerTypeException
     */
    private DataDictionaryValueEntity queryTrailerType(
	    VehicleForTrailerTypeDto vehicleForTrailerTypeDto)
            throws TrailerTypeException {
	if (null == vehicleForTrailerTypeDto) {
	    throw new TrailerTypeException("", "挂车类型为空");
	}
	if (StringUtils.isBlank(vehicleForTrailerTypeDto.getTrailerTypeCode())) {
	    throw new TrailerTypeException("", "挂车类型编码为空");
	}
	String termsCode = DictionaryConstants.LMS_TRAILER_TYPE_TERMSCODE;
	String valueCode = vehicleForTrailerTypeDto.getTrailerTypeCode();
	return dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(termsCode, valueCode);
    } 
    
    /**
     * @param dataDictionaryValueService the dataDictionaryValueService to set
     */
    public void setDataDictionaryValueService(
    	IDataDictionaryValueService dataDictionaryValueService) {
        this.dataDictionaryValueService = dataDictionaryValueService;
    }
}
