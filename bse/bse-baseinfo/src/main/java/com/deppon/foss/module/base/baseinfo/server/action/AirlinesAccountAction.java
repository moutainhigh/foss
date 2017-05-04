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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirlinesAccountAction.java
 * 
 * FILE NAME        	: AirlinesAccountAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirlinesAccountVo;

/**
 * 月台ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class AirlinesAccountAction extends AbstractAction {
	private static final long serialVersionUID = 2883644272419312426L;
	//vo
    private AirlinesAccountVo airlinesAccountVo  = new AirlinesAccountVo();
	public AirlinesAccountVo getAirlinesAccountVo() {
		return airlinesAccountVo;
	}
	public void setAirlinesAccountVo(AirlinesAccountVo airlinesAccountVo) {
		this.airlinesAccountVo = airlinesAccountVo;
	}
    //航空公司账户service
	private IAirlinesAccountService airlinesAccountService;
	public void setAirlinesAccountService(
			IAirlinesAccountService airlinesAccountService) {
		this.airlinesAccountService = airlinesAccountService;
	}
	/**
	 * .
	 * <p>
	 * 查询所有的航空公司账户根据条件<br/>
	 * 方法名：queryAirlinesAccountListBySelectiveCondition
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryAirlinesAccountListBySelectiveCondition() {
		try {
			PaginationDto paginationDto = airlinesAccountService.queryAirlinesAccountListBySelectiveCondition(airlinesAccountVo.getAirlinesAccountEntity(), start, limit);
			airlinesAccountVo.setAirlinesAccountDtoList(paginationDto.getPaginationDtos());
			this.setTotalCount(paginationDto.getTotalCount());
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	/**
	 * .
	 * <p>
	 * 作废训中的航空公司账户<br/>
	 * 方法名：deleteAirlinesAccount
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String deleteAirlinesAccount() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAccountService.deleteAirlinesAccount(airlinesAccountVo.getIds(), userCode);
			return returnSuccess(MessageType.DELETE_AIRLINESACCOUNT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 新增航空公司账户根据条件<br/>
	 * 方法名：addAirlinesAccount
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String addAirlinesAccount() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAccountService.addAirlinesAccount(airlinesAccountVo.getAirlinesAccountEntity(), userCode, false);
			return returnSuccess(MessageType.SAVE_AIRLINESACCOUNT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 修改航空公司账户根据条件<br/>
	 * 方法名：updateAirlinesAccount
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String updateAirlinesAccount() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			airlinesAccountService.updateAirlinesAccount(airlinesAccountVo.getAirlinesAccountEntity(), userCode, true);
			return returnSuccess(MessageType.UPDATE_AIRLINESACCOUNT_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * .
	 * <p>
	 * 根据CODE查询航空公司账户<br/>
	 * 方法名：queryAirlinesAccount
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-12-03
	 * @since JDK1.6
	 */
	@JSON
	public String queryAirlinesAccount() {
		try {
			AirlinesAccountEntity airlinesAccountEntity = airlinesAccountService.queryAirlinesAccount(airlinesAccountVo.getAirlinesAccountEntity().getId());
			airlinesAccountVo.setAirlinesAccountEntity(airlinesAccountEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
}
