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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirlinesAgentAction.java
 * 
 * FILE NAME        	: AirlinesAgentAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirlinesAgentVo;

/**
 * 航空公司代理人ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class AirlinesAgentAction extends AbstractAction {
	private static final long serialVersionUID = 2883644272419312426L;
    //vo
	private AirlinesAgentVo airlinesAgentVo = new AirlinesAgentVo();
	public AirlinesAgentVo getAirlinesAgentVo() {
		return airlinesAgentVo;
	}
	public void setAirlinesAgentVo(AirlinesAgentVo airlinesAgentVo) {
		this.airlinesAgentVo = airlinesAgentVo;
	}
	//航空代理人service
	private IAirlinesAgentService airlinesAgentService;
	public void setAirlinesAgentService(IAirlinesAgentService airlinesAgentService) {
		this.airlinesAgentService = airlinesAgentService;
	}
	/**
	 * .
	 * <p>
	 * 查询所有的航空公司代理人根据条件<br/>
	 * 方法名：queryAirlinesAgentListBySelectiveCondition
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryAirlinesAgentListBySelectiveCondition() {
		try {
			PaginationDto paginationDto = airlinesAgentService.queryAirlinesAgentListBySelectiveCondition(airlinesAgentVo.getAirlinesAgentEntity(), start, limit);
			airlinesAgentVo.setAirlinesAgentDtoList(paginationDto.getPaginationDtos());
			this.setTotalCount(paginationDto.getTotalCount());
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 作废训中的航空公司代理人<br/>
	 * 方法名：deleteAirlinesAgent
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String deleteAirlinesAgent() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAgentService.deleteAirlinesAgent(airlinesAgentVo.getIds(), userCode);
			return returnSuccess(MessageType.DELETE_AIRLINESAGENT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 新增航空公司代理人根据条件<br/>
	 * 方法名：addAirlinesAgent
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String addAirlinesAgent() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAgentService.addAirlinesAgent(airlinesAgentVo.getAirlinesAgentEntity(), userCode, false);
			return returnSuccess(MessageType.SAVE_AIRLINESAGENT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改航空公司代理人根据条件<br/>
	 * 方法名：updateAirlinesAgent
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String updateAirlinesAgent() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAgentService.updateAirlinesAgent(airlinesAgentVo.getAirlinesAgentEntity(), userCode, true);
			return returnSuccess(MessageType.UPDATE_AIRLINESAGENT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 根据CODE查询航空公司代理人<br/>
	 * 方法名：queryAirlinesAgent
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String queryAirlinesAgent() {
		try {
			AirlinesAgentEntity airlinesAgentEntity = airlinesAgentService.queryAirlinesAgentBySelective(airlinesAgentVo.getAirlinesAgentEntity());
			airlinesAgentVo.setAirlinesAgentEntity(airlinesAgentEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
