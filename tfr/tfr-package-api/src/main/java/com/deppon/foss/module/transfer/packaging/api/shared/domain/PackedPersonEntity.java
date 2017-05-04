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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/PackedPersonEntity.java
 *  
 *  FILE NAME          :PackedPersonEntity.java
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
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.domain
 * FILE    NAME: PackedPersonEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 包装录入的包装人实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-20 上午9:46:53
 */
public class PackedPersonEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262929461471161346L;
	/**
	 * 包装人
	 */
	private String empName;
	/**
	 * 包装人工号
	 */
	private String empCode;
	/**
	 * 包装主信息id
	 */
	private String packedId;
	
	/**
	 * 获取 包装人.
	 *
	 * @return the 包装人
	 */
	public String getEmpName() {
		return empName;
	}
	
	/**
	 * 设置 包装人.
	 *
	 * @param empName the new 包装人
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	/**
	 * 获取 包装人工号.
	 *
	 * @return the 包装人工号
	 */
	public String getEmpCode() {
		return empCode;
	}
	
	/**
	 * 设置 包装人工号.
	 *
	 * @param empCode the new 包装人工号
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	/**
	 * 获取 包装主信息id.
	 *
	 * @return the 包装主信息id
	 */
	public String getPackedId() {
		return packedId;
	}
	
	/**
	 * 设置 包装主信息id.
	 *
	 * @param packedId the new 包装主信息id
	 */
	public void setPackedId(String packedId) {
		this.packedId = packedId;
	}
	
	
	
}