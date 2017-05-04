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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcProofEntity.java
 * 
 * FILE NAME        	: WaybillRfcProofEntity.java
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
 * (运单变更凭证)
 * 在更改单界面的凭证附件信息
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:邵宏亮,date:2012-10-10 下午6:54:49, </p>
 * @author 邵宏亮
 * @date 2012-10-10 下午6:54:49
 * @since
 * @version
 */
public class WaybillRfcProofEntity extends BaseEntity {

    /**
	 * 序列化版本
	 */
    private static final long serialVersionUID = -8852244838319049264L;

    /**
     *  运单变更ID
     */
    private String waybillRfcId;

    /**
     *  变更凭证名称
     */
    private String rfcProofName;

    /**
     *  变更凭证路径
     */
    private String rfcProofPath;

    /**
     *  变更凭证大小
     */
    private Long rfcProofSize;

	
	/**
	 * @return the waybillRfcId
	 */
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	
	/**
	 * @param waybillRfcId the waybillRfcId to set
	 */
	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	
	/**
	 * @return the rfcProofName
	 */
	public String getRfcProofName() {
		return rfcProofName;
	}

	
	/**
	 * @param rfcProofName the rfcProofName to set
	 */
	public void setRfcProofName(String rfcProofName) {
		this.rfcProofName = rfcProofName;
	}

	
	/**
	 * @return the rfcProofPath
	 */
	public String getRfcProofPath() {
		return rfcProofPath;
	}

	
	/**
	 * @param rfcProofPath the rfcProofPath to set
	 */
	public void setRfcProofPath(String rfcProofPath) {
		this.rfcProofPath = rfcProofPath;
	}

	
	/**
	 * @return the rfcProofSize
	 */
	public Long getRfcProofSize() {
		return rfcProofSize;
	}

	
	/**
	 * @param rfcProofSize the rfcProofSize to set
	 */
	public void setRfcProofSize(Long rfcProofSize) {
		this.rfcProofSize = rfcProofSize;
	}



}