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


import java.util.List;

import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.inteface.domain.ecs.LoadUnloadTeam;
import com.deppon.esb.inteface.domain.ecs.Porter;
import com.deppon.esb.inteface.domain.ecs.SyncLoadUnloadTeamRequest;
import com.deppon.esb.pojo.transformer.jaxb.LoadUnloadTeamSyncRequestTrans;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncLoadUnloadTeamService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadSquadEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PorterEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.util.CollectionUtils;

/**
 * 
 * 同步装卸车小队信息给ECS
 * 
 * @author
 * @date 
 */
public class SyncLoadUnloadTeamService implements ISyncLoadUnloadTeamService {

	private static final Logger log = LoggerFactory
			.getLogger(SyncLoadUnloadTeamService.class);

	private static final String ESB_SERVICE_CODE = "ESB_FOSS2ESB_RECEIVE_ZXCARTEAM";
	private static final String VERSION = "1.0";
	
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
	 * 
	 * 同步装卸车小队信息给ECS
	 * 
	 * @author 
	 * @date 
	 */

	@Override
	public void syncLoadUnloadTeamToEcs(List<LoadAndUnloadSquadEntity> entitys, int operateType) {

		//开关
		if(!configurationParamsService.queryBseSwitch4Ecs()){
			return;
		}
		
		if (CollectionUtils.isEmpty(entitys)) {
			throw new BusinessException("传入的对象为空");
		}
		SyncLoadUnloadTeamRequest request = ConvertEsbEntity(entitys,operateType);
		
		// 创建服务头信息
		AccessHeader accessHeader = new AccessHeader();
		accessHeader.setEsbServiceCode(ESB_SERVICE_CODE);
		accessHeader.setBusinessId(UUID.randomUUID().toString());
		accessHeader.setBusinessDesc1("同步装卸车小队信息给ECS");
		accessHeader.setVersion(VERSION);
		try {
			log.info("开始调用 同步装卸车小队信息给ECS：\n"
					+ new LoadUnloadTeamSyncRequestTrans().fromMessage(request));
			ESBJMSAccessor.asynReqeust(accessHeader, request);
			log.info("结束调用 同步装卸车小队信息给ECS：\n"+ new LoadUnloadTeamSyncRequestTrans().fromMessage(request));
		} catch (ESBException e) {
			throw new BusinessException("同步装卸车小队信息失败。");
		}
		log.info("send ExpressDeliveryRegions to GW System ：同步数据到ECS \n");
		
	}
	private SyncLoadUnloadTeamRequest ConvertEsbEntity(List<LoadAndUnloadSquadEntity> list, int operateType) {
		SyncLoadUnloadTeamRequest request = new SyncLoadUnloadTeamRequest();
		
		List<LoadUnloadTeam> loadUnloadTeams = request.getLoadUnloadTeam();
		LoadUnloadTeam loadUnloadTeam = null;
		for(LoadAndUnloadSquadEntity entity : list){
			loadUnloadTeam = new LoadUnloadTeam();
			loadUnloadTeam.setActive(entity.getActive());
			loadUnloadTeam.setCreateTime(entity.getCreateDate());
			loadUnloadTeam.setCreateUserCode(entity.getCreateUser());
			loadUnloadTeam.setId(entity.getId());
			loadUnloadTeam.setModifyTime(entity.getModifyDate());
			loadUnloadTeam.setModifyUserCode(entity.getModifyUser());
			loadUnloadTeam.setOperatorSign(operateType);
			loadUnloadTeam.setArrangeBizType(entity.getArrangeBizType());
			loadUnloadTeam.setCode(entity.getCode());
			loadUnloadTeam.setName(entity.getName());
			loadUnloadTeam.setParentOrgCode(entity.getParentOrgCode());
			
			Porter porter = null;
			if(entity.getPorters() != null && entity.getPorters().size()>0){
			for(PorterEntity porterEntity : entity.getPorters()){
				porter = new Porter();
				porter.setActive(porterEntity.getActive());
				porter.setCreateTime(porterEntity.getCreateDate());
				porter.setCreateUserCode(porterEntity.getCreateUser());
				porter.setEmpCode(porterEntity.getEmpCode());
				porter.setEmpName(porterEntity.getEmpName());
				porter.setId(porterEntity.getId());
				porter.setModifyTime(porterEntity.getModifyDate());
				porter.setModifyUserCode(porterEntity.getModifyUser());
				porter.setParentOrgCode(porterEntity.getParentOrgCode());
				porter.setPinyin(porterEntity.getPinyin());
				porter.setType(porterEntity.getType());
				loadUnloadTeam.getPorter().add(porter);
			}
			}
			loadUnloadTeams.add(loadUnloadTeam);
			}
		
		return request;
	}



	}
	

	
	
	
	

