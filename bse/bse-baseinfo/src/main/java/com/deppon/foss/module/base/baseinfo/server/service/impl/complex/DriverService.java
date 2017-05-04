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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/complex/DriverService.java
 * 
 * FILE NAME        	: DriverService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.complex;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnDriverService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IDriverService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
/**
 * 用来提供交互所有关于“司机（公司、外请）”的数据库对应数据访问复杂的Service接口实现类：无SUC
 * <p>注意：由于数据建模设计中，公司车不直接关系到司机，外请车关系到司机，公司司机直接关系的是工号</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-30 下午5:23:09</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-30 下午5:23:09
 * @since
 * @version
 */
public class DriverService implements IDriverService {
   
    //"公司司机"Service接口
    private IOwnDriverService ownDriverService;
    
    //"外请车司机"Service接口
    private ILeasedDriverService leasedDriverService;

    /**
     * <p>提供给"中转"模块使用，根据"司机姓名"模糊匹配获取"司机"相关的数据实体信息的DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-5 下午6:46:51
     * @param driverName 司机姓名
     * @return DriverBaseDto封装的对象集合
     * @throws BusinessException
     * @throws BusinessException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.IDriverService#queryAutoCompleteDriverBaseDtoByDriverName(java.lang.String)
     */
    @Override
    public List<DriverBaseDto> queryAutoCompleteDriverBaseDtoByDriverName(
            String driverName) throws BusinessException {

	List<DriverBaseDto> allDriverBaseDtos = new ArrayList<DriverBaseDto>();
	//公司司机模糊匹配
	List<DriverBaseDto> ownDriverBaseDtos = ownDriverService.queryAutoCompleteDriverBaseDtoByDriverName(driverName);
	if (CollectionUtils.isNotEmpty(ownDriverBaseDtos)) {
	    allDriverBaseDtos.addAll(ownDriverBaseDtos);
	}
	//外请司机模糊匹配
	List<DriverBaseDto> leasedDriverBaseDtos = leasedDriverService.queryAutoCompleteDriverBaseDtoByDriverName(driverName);
	if (CollectionUtils.isNotEmpty(leasedDriverBaseDtos)) {
	    allDriverBaseDtos.addAll(leasedDriverBaseDtos);
	}
        return allDriverBaseDtos;
    }
    
    /**
     * @param ownDriverService the ownDriverService to set
     */
    public void setOwnDriverService(IOwnDriverService ownDriverService) {
        this.ownDriverService = ownDriverService;
    }
    
    /**
     * @param leasedDriverService the leasedDriverService to set
     */
    public void setLeasedDriverService(ILeasedDriverService leasedDriverService) {
        this.leasedDriverService = leasedDriverService;
    }
}
