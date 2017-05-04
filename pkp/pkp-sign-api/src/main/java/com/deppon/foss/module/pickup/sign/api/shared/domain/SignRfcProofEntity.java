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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SignRfcProofEntity.java
 * 
 * FILE NAME        	: SignRfcProofEntity.java
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
 * 
 * <p>签收变更附件凭证<br />
 * </p>
 * @title SignRfcProof.java
 * @package com.deppon.foss.module.pickup.sign.api.shared.domain 
 * @author ibm-lizhiguo
 * @version 0.1 2013-1-5
 */
public class SignRfcProofEntity implements Serializable{
    
    /** 
	 * @fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 1L;

	/** The id. */
    private String id;

    /** The rfcproof size. */
    private String rfcproofSize;

    /** The srv sign rfc id. */
    private String srvSignRfcId;

    /** The rfcproof name. */
    private String rfcproofName;

    /** The rfcproof path. */
    private String rfcproofPath;

	/**
	 * @return id : return the property id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id : set the property id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return rfcproofSize : return the property rfcproofSize.
	 */
	public String getRfcproofSize() {
		return rfcproofSize;
	}

	/**
	 * @param rfcproofSize : set the property rfcproofSize.
	 */
	public void setRfcproofSize(String rfcproofSize) {
		this.rfcproofSize = rfcproofSize;
	}

	/**
	 * @return srvSignRfcId : return the property srvSignRfcId.
	 */
	public String getSrvSignRfcId() {
		return srvSignRfcId;
	}

	/**
	 * @param srvSignRfcId : set the property srvSignRfcId.
	 */
	public void setSrvSignRfcId(String srvSignRfcId) {
		this.srvSignRfcId = srvSignRfcId;
	}

	/**
	 * @return rfcproofName : return the property rfcproofName.
	 */
	public String getRfcproofName() {
		return rfcproofName;
	}

	/**
	 * @param rfcproofName : set the property rfcproofName.
	 */
	public void setRfcproofName(String rfcproofName) {
		this.rfcproofName = rfcproofName;
	}

	/**
	 * @return rfcproofPath : return the property rfcproofPath.
	 */
	public String getRfcproofPath() {
		return rfcproofPath;
	}

	/**
	 * @param rfcproofPath : set the property rfcproofPath.
	 */
	public void setRfcproofPath(String rfcproofPath) {
		this.rfcproofPath = rfcproofPath;
	}
}