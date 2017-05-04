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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/AgentProofEntity.java
 * 
 * FILE NAME        	: AgentProofEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import java.io.Serializable;

/**
 * 凭证的附件文件信息
 * 
 * @date 2012-10-16 上午10:13:42
 * @since
 * @version
 *
 */
public class AgentProofEntity implements Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
    private String id;

    /**
     * 修改他人签收id
     */
    private String tSrvConsigneeAgentId;

    /**
     * 真实文件名
     */
    private String proofName;

    /**
     * 上传后文件路径
     */
    private String proofPath;

    /**
     * 文件大小
     */
    private String proofSize;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the tSrvConsigneeAgentId
	 */
	public String gettSrvConsigneeAgentId() {
		return tSrvConsigneeAgentId;
	}

	/**
	 * @param tSrvConsigneeAgentId the tSrvConsigneeAgentId to set
	 */
	public void settSrvConsigneeAgentId(String tSrvConsigneeAgentId) {
		this.tSrvConsigneeAgentId = tSrvConsigneeAgentId;
	}

	/**
	 * @return the proofName
	 */
	public String getProofName() {
		return proofName;
	}

	/**
	 * @param proofName the proofName to set
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
	 * @param proofPath the proofPath to set
	 */
	public void setProofPath(String proofPath) {
		this.proofPath = proofPath;
	}

	/**
	 * @return the proofSize
	 */
	public String getProofSize() {
		return proofSize;
	}

	/**
	 * @param proofSize the proofSize to set
	 */
	public void setProofSize(String proofSize) {
		this.proofSize = proofSize;
	}

  
}