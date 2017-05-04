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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcAgentPersonEntity.java
 * 
 * FILE NAME        	: WaybillRfcAgentPersonEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 运单变更代理人
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-shaohongliang,date:2012-10-11 下午5:09:25</p>
  * @date 2012-10-11 下午5:09:25
 * @since
 * @version
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:45:13
 */
public class WaybillRfcAgentPersonEntity extends BaseEntity {
	/**
     * 序列化版本号
     */
	private static final long serialVersionUID = -1176847928590514518L;
	// 主记录id
	private String parentId;
	// 代理人code
	private String agentEmpCode;
	// 代理人name
	private String agentEmpName;
	//有效性
	private String active;
	/**
	 * @return parentId : set the property parentId.
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId : return the property parentId.
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return agentEmpCode : set the property agentEmpCode.
	 */
	public String getAgentEmpCode() {
		return agentEmpCode;
	}
	/**
	 * @param agentEmpCode : return the property agentEmpCode.
	 */
	public void setAgentEmpCode(String agentEmpCode) {
		this.agentEmpCode = agentEmpCode;
	}
	/**
	 * @return agentEmpName : set the property agentEmpName.
	 */
	public String getAgentEmpName() {
		return agentEmpName;
	}
	/**
	 * @param agentEmpName : return the property agentEmpName.
	 */
	public void setAgentEmpName(String agentEmpName) {
		this.agentEmpName = agentEmpName;
	}
	/**
	 * @return active : set the property active.
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active : return the property active.
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
}