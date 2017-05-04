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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/domain/QueryPDAUnpackConEntity.java
 *  
 *  FILE NAME          :QueryPDAUnpackConEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;



/**
 * PDA查询营业部代打包装的查询条件实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public class QueryPDAUnpackConEntity extends BaseEntity {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9017361422008123954L;
	
	/**
	 * 代包装部门
	 */
	private String packDept;
	
	/**
	 * 走货路径状态名称
	 */
	private String statusName;
	/**
	 * 包装获取编码
	 */
	private String areaCode;
	
	/**
	 * 获取 代包装部门.
	 *
	 * @return the 代包装部门
	 */
	public String getPackDept() {
		return packDept;
	}
	
	/**
	 * 设置 代包装部门.
	 *
	 * @param packDept the new 代包装部门
	 */
	public void setPackDept(String packDept) {
		this.packDept = packDept;
	}
	
	/**
	 * 获取 走货路径状态名称.
	 *
	 * @return the 走货路径状态名称
	 */
	public String getStatusName() {
		return statusName;
	}
	
	/**
	 * 设置 走货路径状态名称.
	 *
	 * @param statusName the new 走货路径状态名称
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	/**
	 * 获取 包装获取编码.
	 *
	 * @return the 包装获取编码
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * 设置 包装获取编码.
	 *
	 * @param areaCode the new 包装获取编码
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
	
	
}