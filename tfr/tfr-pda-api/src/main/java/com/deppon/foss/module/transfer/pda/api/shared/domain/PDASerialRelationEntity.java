/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/PDASerialRelationEntity.java
 *  
 *  FILE NAME          :PDASerialRelationEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.domain
 * FILE    NAME: PDASerialRelationEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PDA包装录入的流水号关系
 * @author 046130-foss-xuduowei
 * @date 2012-11-3 上午9:24:00
 */
public class PDASerialRelationEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1208085546479982684L;
	/**
	 * 旧流水号
	 */
	private String oldSerialNo;
	/**
	 * 新流水号
	 */
	private String newSerialNo;

	/**
	 * 获取 旧流水号.
	 *
	 * @return the 旧流水号
	 */
	public String getOldSerialNo() {
		return oldSerialNo;
	}

	/**
	 * 设置 旧流水号.
	 *
	 * @param oldSerialNo the new 旧流水号
	 */
	public void setOldSerialNo(String oldSerialNo) {
		this.oldSerialNo = oldSerialNo;
	}

	/**
	 * 获取 新流水号.
	 *
	 * @return the 新流水号
	 */
	public String getNewSerialNo() {
		return newSerialNo;
	}

	/**
	 * 设置 新流水号.
	 *
	 * @param newSerialNo the new 新流水号
	 */
	public void setNewSerialNo(String newSerialNo) {
		this.newSerialNo = newSerialNo;
	}
	
	
}