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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirRevisebillDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;


import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 合大票清单明细
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午12:16:27
 */
public class AirRevisebillDetailEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1642414057767622440L;
	/**
	 * 合票清单明细ID
	 */
	private String airPickupbillDetailId;
	
	/**
	 * 中转合票清单明细ID
	 */
	private String airTransPickupbillDetailId;
		
	/**
	 * 变更内容
	 */
	private String reviseContent;
		
	/**
	 * 创建时期
	 */
	private Date createTime;
		
	/**
	 * 操作人编码
	 */
	private String operatorCode;
		
	/**
	 * 操作人
	 */
	private String operatorName;
		
	/**
	 * 操作时间
	 */
	private Date operationTime;
		
	/**
	 * 操作部门
	 */
	private String operationOrgName;
		
	/**
	 * 操作部门编码
	 */
	private String operationOrgCode;
	
	/**
	 * 获取 iD.
	 *
	 * @return the iD
	 */
	public String getAirPickupbillDetailId() {
		return airPickupbillDetailId;
	}

	/**
	 * 设置 iD.
	 *
	 * @param airPickupbillDetailId the new iD
	 */
	public void setAirPickupbillDetailId(String airPickupbillDetailId) {
		this.airPickupbillDetailId = airPickupbillDetailId;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getAirTransPickupbillDetailId() {
		return airTransPickupbillDetailId;
	}

	/**
	 * 
	 *
	 * @param airTransPickupbillDetailId 
	 */
	public void setAirTransPickupbillDetailId(String airTransPickupbillDetailId) {
		this.airTransPickupbillDetailId = airTransPickupbillDetailId;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getReviseContent() {
		return reviseContent;
	}

	/**
	 * 
	 *
	 * @param reviseContent 
	 */
	public void setReviseContent(String reviseContent) {
		this.reviseContent = reviseContent;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 
	 *
	 * @param createTime 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * 
	 *
	 * @param operatorCode 
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 
	 *
	 * @param operatorName 
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public Date getOperationTime() {
		return operationTime;
	}

	/**
	 * 
	 *
	 * @param operationTime 
	 */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getOperationOrgName() {
		return operationOrgName;
	}

	/**
	 * 
	 *
	 * @param operationOrgName 
	 */
	public void setOperationOrgName(String operationOrgName) {
		this.operationOrgName = operationOrgName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getOperationOrgCode() {
		return operationOrgCode;
	}

	/**
	 * 
	 *
	 * @param operationOrgCode 
	 */
	public void setOperationOrgCode(String operationOrgCode) {
		this.operationOrgCode = operationOrgCode;
	}
}