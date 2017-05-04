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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/esb/SyncSalesDepartmentService.java
 * 
 * FILE NAME        	: SyncSalesDepartmentService.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.inteface.domain.MotorcadeInfo;
import com.deppon.crm.inteface.domain.SyncMotorcadeRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.pojo.transformer.jaxb.SyncMotorcadeRequestTrans;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;

/**
 * 
 * 同步车队信息给CRM
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2013-7-29 上午9:57:32
 */
public class SyncMotorcadeService implements ISyncMotorcadeService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncMotorcadeService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_SYNC_MOTORCADEINFO";
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 
	 * 同步车队信息给CRM
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-7-29 上午9:59:35
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncMotorcadeService#syncMotorcadeToCrm(java.util.List)
	 */
	@Override
	public void syncMotorcadeToCrm(List<MotorcadeEntity> list) {

		// 判断传入的集合是否为空
		if (CollectionUtils.isNotEmpty(list)) {
			SyncMotorcadeRequest motorcadeInfoRequest = new SyncMotorcadeRequest();
			List<MotorcadeInfo> motorCadeInfoList = new ArrayList<MotorcadeInfo>();
			StringBuffer versionNos = new StringBuffer();
			StringBuffer codes = new StringBuffer();
			// 循环取出同步的车队信息
			for (MotorcadeEntity motorcadeEntity : list) {
				MotorcadeInfo motorcadeInfo = new MotorcadeInfo();
				// 根据车队编码获取车队组织信息
				OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCodeClean(motorcadeEntity
								.getCode());
				if (orgEntity != null) {
					// 设置ID，用标杆编码代替
					motorcadeInfo.setId(orgEntity.getUnifiedCode());
					// 设置部门标杆编码
					motorcadeInfo.setUnifiedCode(orgEntity.getUnifiedCode());
					// 是否顶级车队标示
					motorcadeInfo.setIsTopMotorcade(motorcadeEntity
							.getIsTopFleet());
					// 同步时间
					motorcadeInfo.setModifyTime(new Date());
					versionNos.append(SymbolConstants.EN_COMMA);
					versionNos.append(orgEntity.getVersionNo());
					codes.append(SymbolConstants.EN_COMMA);
					codes.append(orgEntity.getCode());

					motorCadeInfoList.add(motorcadeInfo);

				}
			}
			motorcadeInfoRequest.getMotorcadeInfo().addAll(motorCadeInfoList);
			AccessHeader accessHeader = new AccessHeader();
			accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
			accessHeader.setBusinessId(codes.toString());
			accessHeader.setBusinessDesc1("同步车队信息给CRM");
			accessHeader.setBusinessDesc2(versionNos.toString());
			accessHeader.setVersion("0.1");

			try {
				log.info("开始调用 同步车队信息：\n"
						+ new SyncMotorcadeRequestTrans()
								.fromMessage(motorcadeInfoRequest));

				ESBJMSAccessor.asynReqeust(accessHeader, motorcadeInfoRequest);

				log.info("结束调用 同步车队信息：\n"
						+ new SyncMotorcadeRequestTrans()
								.fromMessage(motorcadeInfoRequest));

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new SynchronousExternalSystemException(" 同步车队信息",
						" 同步车队信息 发送数据到ESB错误");
			}
		}

	}
}
