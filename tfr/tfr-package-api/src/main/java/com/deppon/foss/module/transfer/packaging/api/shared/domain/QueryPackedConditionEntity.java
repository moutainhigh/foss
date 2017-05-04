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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/QueryPackedConditionEntity.java
 *  
 *  FILE NAME          :QueryPackedConditionEntity.java
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
 * FILE    NAME: QueryPackedConditionEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 查询条件
 * @author 046130-foss-xuduowei
 * @date 2012-10-17 上午8:28:59
 */
public class QueryPackedConditionEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2640247026353078493L;
	/**
	 * 开单部门
	 */
	private String waybillCreateDept;
	/**
	 * 包装部门
	 */
	private String packedDept;
	/**
	 * 包装人
	 */
	private String packedPerson;
	/**
	 * 包装开始时间
	 */
	private Date packedBeginDate;
	/**
	 * 包装结束时间
	 */
	private Date packedEndDate;
	/**
	 * 运单号
	 */
	private String wayBillNumber;
	/**
	 * 货区编号
	 */
	private String areaCode;
	
	/**
	 * 包装类型
	 */
	private String packageType;
	
	/**
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getWaybillCreateDept() {
		return waybillCreateDept;
	}
	
	/**
	 * 设置 开单部门.
	 *
	 * @param waybillCreateDept the new 开单部门
	 */
	public void setWaybillCreateDept(String waybillCreateDept) {
		this.waybillCreateDept = waybillCreateDept;
	}
	
	/**
	 * 获取 包装部门.
	 *
	 * @return the 包装部门
	 */
	public String getPackedDept() {
		return packedDept;
	}
	
	/**
	 * 设置 包装部门.
	 *
	 * @param packedDept the new 包装部门
	 */
	public void setPackedDept(String packedDept) {
		this.packedDept = packedDept;
	}
	
	/**
	 * 获取 包装人.
	 *
	 * @return the 包装人
	 */
	public String getPackedPerson() {
		return packedPerson;
	}
	
	/**
	 * 设置 包装人.
	 *
	 * @param packedPerson the new 包装人
	 */
	public void setPackedPerson(String packedPerson) {
		this.packedPerson = packedPerson;
	}
	
	/**
	 * 获取 包装开始时间.
	 *
	 * @return the 包装开始时间
	 */
	public Date getPackedBeginDate() {
		return packedBeginDate;
	}
	
	/**
	 * 设置 包装开始时间.
	 *
	 * @param packedBeginDate the new 包装开始时间
	 */
	public void setPackedBeginDate(Date packedBeginDate) {
		this.packedBeginDate = packedBeginDate;
	}
	
	/**
	 * 获取 包装结束时间.
	 *
	 * @return the 包装结束时间
	 */
	public Date getPackedEndDate() {
		return packedEndDate;
	}
	
	/**
	 * 设置 包装结束时间.
	 *
	 * @param packedEndDate the new 包装结束时间
	 */
	public void setPackedEndDate(Date packedEndDate) {
		this.packedEndDate = packedEndDate;
	}
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param areaCode the new 货区编号
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	
	
	
}