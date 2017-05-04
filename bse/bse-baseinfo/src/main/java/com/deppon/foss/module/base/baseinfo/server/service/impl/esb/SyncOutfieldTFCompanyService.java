/*******************************************************************************
 * Copyright 2016 BSE TEAM
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SendOrgAdministrativeInfoService.java
 * 
 * FILE NAME        	: SendOrgAdministrativeInfoService.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.OutfieldTFCompanyInfo;
import com.deppon.ecs.inteface.domain.SyncOutfieldTFCompanyRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.domain.foss2ecs.SyncOutfieldTFCompanyRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncOutfieldTFCompanyService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;

/**
 * 
 * @date 2016/4/9
 * @version 1.0
 */
public class SyncOutfieldTFCompanyService implements
		ISyncOutfieldTFCompanyService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncOutfieldTFCompanyService.class);

	/**
	 * 服务编码
	 */
	private static final String SYNC_OUTFIELDTFCOMPANY_SERVE_CODE = "ESB_FOSS2ESB_RECEIVE_WCYSSYSCWGS";

	/**
	 * 版本编号
	 */
	private static final String version = "1.0";
	
	/**
	 * 开关
	 */
	private IConfigurationParamsService configurationParamsService;
	
	
	/**
	 * 设置开关
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
	 * 同步“外请车合同主体变更”到官网
	 * 
	 * @author 313353
	 * @date 2016/04/09
	 */
	@Override
	public void syncOutfieldTFCompanyToECS(List<OutfieldTFCompanyInfo> entitys) {
		
		//开关
		if(!configurationParamsService.queryBseSwitch4Ecs()){
			return;
		}
		
		// 声明要传递的值
		SyncOutfieldTFCompanyRequest request = new SyncOutfieldTFCompanyRequest();

		StringBuilder versionNos = new StringBuilder();
		StringBuilder codes = new StringBuilder();

		List<OutfieldTFCompanyInfo> esbInfos = request.getOutfieldTFCompanyInfos();
		for (OutfieldTFCompanyInfo fossEntity : entitys) {
			if (fossEntity == null) {
				continue;
			}

			versionNos.append(SymbolConstants.EN_COMMA);
			versionNos.append(fossEntity.getOutfieldName());
			codes.append(SymbolConstants.EN_COMMA);
			codes.append(fossEntity.getOutfieldCode());

			esbInfos.add(fossEntity);
		}

		AccessHeader header = new AccessHeader();

		header.setBusinessId(codes.toString());
		// 服务编码
		header.setEsbServiceCode(SYNC_OUTFIELDTFCOMPANY_SERVE_CODE);
		// 处理工作流审批结果
		header.setVersion(version);
		header.setBusinessDesc1("同步外请车合同主体变更 到其它平台");
		header.setBusinessDesc2(versionNos.toString());

		try {
			LOGGER.info("start send T_BAS_OUTFIELD_TFCOMPANY info to other platform.FOSS开始发送同步 外请车合同主体变更 到其它平台 的报文："
					+ new SyncOutfieldTFCompanyRequestTrans().fromMessage(request));
			// 发送预付款申请到ESB
			ESBJMSAccessor.asynReqeust(header, request);
			
			LOGGER.info("end send T_BAS_OUTFIELD_TFCOMPANY info to other platform.FOSS结束发送同步 外请车合同主体变更 到其它平台 的报文："
					+ new SyncOutfieldTFCompanyRequestTrans().fromMessage(request));
		} catch (ESBConvertException e) {
			LOGGER.error("同步外请车合同主体变更出错。", e);
			throw new BusinessException("同步外请车合同主体变更出错。");
		} catch (ESBException e) {
			LOGGER.error("同步外请车合同主体变更出错。", e);
			throw new BusinessException("同步外请车合同主体变更出错。");
		}
	}

}
