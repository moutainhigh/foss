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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/UploadVoucherDto.java
 * 
 * FILE NAME        	: UploadVoucherDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/**
 * 
 * 上传凭证DTO
 * @author 102246-foss-shaohongliang
 * @date 2012-12-25 上午9:05:22
 */
public class UploadVoucherDto implements Serializable{

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -2968771070264509400L;

	/**
	 * 凭证类型
	 */
	private String proofType;

	/**
	 * 是否默认
	 */
	private boolean isDefault;

	/**
	 * 凭证名称
	 */
	private String proofName;

	/**
	 * 凭证内容
	 */
	private String proofBytes;

	
	/**
	 * @return the proofType
	 */
	public String getProofType() {
		return proofType;
	}

	
	/**
	 * @param proofType the proofType to set
	 */
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	
	/**
	 * @return the isDefault
	 */
	public boolean getIsDefault() {
		return isDefault;
	}

	
	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
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
	 * @return the proofBytes
	 */
	public String getProofBytes() {
		return proofBytes;
	}

	
	/**
	 * @param proofBytes the proofBytes to set
	 */
	public void setProofBytes(String proofBytes) {
		this.proofBytes = proofBytes;
	}


}