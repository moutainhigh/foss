/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/QueryGoodsAction.java
 * 
 * FILE NAME        	: QueryGoodsAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WorkFlowApproveDto;
import com.opensymphony.xwork2.ActionContext;

/**
 * 工作流图示action
 * 
 * @author 136892
 * 
 */
public class WorkFlowTrackAction extends AbstractAction {

	/** 序列化. */
	private static final long serialVersionUID = 1118011519921068369L;
	private WorkFlowApproveDto workFlowApproveDto;

	public WorkFlowApproveDto getWorkFlowApproveDto() {
		return workFlowApproveDto;
	}

	public void setWorkFlowApproveDto(WorkFlowApproveDto workFlowApproveDto) {
		this.workFlowApproveDto = workFlowApproveDto;
	}

	/**
	 * 工作流图示
	 * 
	 * @author 136892
	 */
	@JSON
	public String workFlowTrack() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String processInstId = (String) request.getParameter("processInstId");
		ActionContext.getContext().put("processInstId",processInstId);
		return SUCCESS;
	}

}