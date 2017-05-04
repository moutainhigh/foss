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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/domain/AbandonGoodsProofEntity.java
 * 
 * FILE NAME        	: AbandonGoodsProofEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
* @ClassName: 	AbandonGoodsProofEntity
* @Description: 上传凭证附件文件信息保存表
* @author 
* @date 		2012-10-25 上午10:06:27
*
*/
public class AbandonGoodsProofEntity extends BaseEntity{
    /**
	* @Fields serialVersionUID : 序列化版本号
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 转弃货_ID
	 */
    private String tSrvAbandonApplicationId;

    /**
     * 变更凭证名称
     */
    private String rfcproofName;

    /**
     * 变更凭证路径
     */
    private String rfcproofPath;

    /**
     * 变更凭证大小
     */
    private Integer rfcproofSize;

	/**
	 * @return the tSrvAbandonApplicationId
	 */
	public String gettSrvAbandonApplicationId() {
		return tSrvAbandonApplicationId;
	}

	/**
	 * @param tSrvAbandonApplicationId the tSrvAbandonApplicationId to set
	 */
	public void settSrvAbandonApplicationId(String tSrvAbandonApplicationId) {
		this.tSrvAbandonApplicationId = tSrvAbandonApplicationId;
	}

	/**
	 * @return the rfcproofName
	 */
	public String getRfcproofName() {
		return rfcproofName;
	}

	/**
	 * @param rfcproofName the rfcproofName to set
	 */
	public void setRfcproofName(String rfcproofName) {
		this.rfcproofName = rfcproofName;
	}

	/**
	 * @return the rfcproofPath
	 */
	public String getRfcproofPath() {
		return rfcproofPath;
	}

	/**
	 * @param rfcproofPath the rfcproofPath to set
	 */
	public void setRfcproofPath(String rfcproofPath) {
		this.rfcproofPath = rfcproofPath;
	}

	/**
	 * @return the rfcproofSize
	 */
	public Integer getRfcproofSize() {
		return rfcproofSize;
	}

	/**
	 * @param rfcproofSize the rfcproofSize to set
	 */
	public void setRfcproofSize(Integer rfcproofSize) {
		this.rfcproofSize = rfcproofSize;
	}

  
}