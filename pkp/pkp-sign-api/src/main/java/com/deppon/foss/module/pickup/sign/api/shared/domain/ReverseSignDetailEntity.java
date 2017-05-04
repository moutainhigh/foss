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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/ReverseSignDetailEntity.java
 * 
 * FILE NAME        	: ReverseSignDetailEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 反签收明细
 * @author ibm-lizhiguo
 * @date 2012-11-20 上午9:25:20
 */
public class ReverseSignDetailEntity  extends BaseEntity{
    /** 
	 * @fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 1L;
	/**
	 * 关联ID
	 */
    private String mappingId;
    /**
     * 变更签收ID
     */
    private String tSrvSignRfcId;
    /**
     * 类型(0：到达联，1：付款，2：运单签收结果)
     */
    private String type;
	/**
	 * @return the mappingId
	 */
	public String getMappingId() {
		return mappingId;
	}
	/**
	 * @param mappingId the mappingId to set
	 */
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	/**
	 * @return the tSrvSignRfcId
	 */
	public String gettSrvSignRfcId() {
		return tSrvSignRfcId;
	}
	/**
	 * @param tSrvSignRfcId the tSrvSignRfcId to set
	 */
	public void settSrvSignRfcId(String tSrvSignRfcId) {
		this.tSrvSignRfcId = tSrvSignRfcId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
    
}