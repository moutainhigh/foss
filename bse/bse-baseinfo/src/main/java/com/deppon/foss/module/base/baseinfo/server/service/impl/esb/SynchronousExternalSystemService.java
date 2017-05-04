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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SynchronousExternalSystemService.java
 * 
 * FILE NAME        	: SynchronousExternalSystemService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.inteface.domain.vehicle.SiteInfo;
import com.deppon.esb.inteface.domain.vehicle.SiteInfocationRequest;
import com.deppon.esb.pojo.transformer.jaxb.SiteInfocationRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.ISynchronousExternalSystemService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 用来操作交互“同步FOSS数据信息”给“外围系统”的应数据Service接口实现类：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-26 上午10:34:58</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-26 上午10:34:58
 * @since
 * @version
 */
public class SynchronousExternalSystemService implements ISynchronousExternalSystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronousExternalSystemService.class);
    private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECEIVE_TRANSFERSITEINFO";
    private static final String VERSION = "0.1";
    
    /**
     * <p>同步FOSS的“外场信息”给LMS系统</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-26 上午10:32:07
     * @param siteInfoList “外场信息”封装实体列表
     * @throws SynchronousExternalSystemException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.complex.ISynchronousExternalSystemService#synchronizeOutfieldDataToLms(java.util.List)
     */
    @Override
    public void synchronizeOutfieldDataToLms(List<SiteInfo> siteInfoList) throws SynchronousExternalSystemException {
	if (CollectionUtils.isNotEmpty(siteInfoList)) {
	    //执行数据发送封装
	    try {
		
		SiteInfocationRequest siteInfocationRequest = new SiteInfocationRequest();
		siteInfocationRequest.getSiteInfo().addAll(siteInfoList);
		AccessHeader accessHeader = new AccessHeader(); 
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUIDUtils.getUUID());
		accessHeader.setVersion(VERSION);
		
		LOGGER.info("start to send outfield info to other platform 开始 同步外场信息 到其它平台：\n"
			+ new SiteInfocationRequestTrans()
				.fromMessage(siteInfocationRequest));
		
		ESBJMSAccessor.asynReqeust(accessHeader, siteInfocationRequest);
		
		LOGGER.info("start to send outfield info to other platform 开始 同步外场信息 到其它平台：\n"
			+ new SiteInfocationRequestTrans()
				.fromMessage(siteInfocationRequest));

	    } catch (Exception e) {
		LOGGER.error("start to send outfield info to other platform exception", e);
		throw new SynchronousExternalSystemException(
			"start to send outfield info to other platform exception",
			"start to send outfield info to other platform exception 发送数据到ESB错误");
	    }
	}
    }
}
