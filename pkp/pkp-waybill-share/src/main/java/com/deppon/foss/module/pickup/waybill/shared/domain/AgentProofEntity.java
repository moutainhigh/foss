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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/AgentProofEntity.java
 * 
 * FILE NAME        	: AgentProofEntity.java
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
 * 代理凭证
 * @author 102246-foss-shaohongliang
 * @date 2012-10-31 下午2:42:00
 */
public class AgentProofEntity extends BaseEntity {


    /**
     * 生成序列化标识
     * （用一句话描述这个变量表示什么）
     */
    private static final long serialVersionUID = 2558892759924782178L;

    // 收货人代理ID
    private String consigneeAgentId;

    // 凭证名称
    private String proofName;

    // 凭证路径
    private String proofPath;

    // 凭证大小
    private Integer proofSize;

    

	/**
	 * @return the consigneeAgentId
	 */
	public String getConsigneeAgentId() {
		return consigneeAgentId;
	}

	/**
	 * @param consigneeAgentId the consigneeAgentId to set
	 */
	public void setConsigneeAgentId(String consigneeAgentId) {
		this.consigneeAgentId = consigneeAgentId;
	}
	/**
	 * @return the proofName
	 */
	public String getProofName() {
		return proofName;
	}
	
	/**
	 * @param proofName
	 *            the proofName to set
	 */
	public void setProofName(String proofName) {
		this.proofName = proofName;
	}
	
	/**
	 * @return the proofPath
	 */
	public String getProofPath() {
		return proofPath;
	}
	
	/**
	 * @param proofPath
	 *            the proofPath to set
	 */
	public void setProofPath(String proofPath) {
		this.proofPath = proofPath;
	}
	
	/**
	 * @return the proofSize
	 */
	public Integer getProofSize() {
		return proofSize;
	}
	
	/**
	 * @param proofSize
	 *            the proofSize to set
	 */
	public void setProofSize(Integer proofSize) {
		this.proofSize = proofSize;
	}


}