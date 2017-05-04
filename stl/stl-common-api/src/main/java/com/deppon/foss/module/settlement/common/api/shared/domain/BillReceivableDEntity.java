/**
 * Copyright 2016 STL TEAM
 */
/*******************************************************************************
 * Copyright 2016 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-common-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/common/api/shared/domain/BillReceivableDEntity.java
 * 
 * FILE NAME        	: BillReceivableDEntity.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 应收单明细实体
 * 
 * @author dp-蒋迅
 * @date 2016-01-06 下午18:01:30
 * @since
 * @version
 */
public class BillReceivableDEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 来源单据单号
	 */
	private String sourceBillNo;

	/**
	 * 应收单号
	 */
	private String receivableNo;
	
	/**
	 * 金额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal amount;
	
	/**
	 * 是否有效
	 */
	private String active;
	
	/**
	 * 制单人编码
	 */
	private String createUserCode;

	/**
	 * 制单人名称
	 */
	private String createUserName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 应收类型
	 */
	private String receivableType;

	public String getSourceBillNo() {
		return sourceBillNo;
	}

	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	public String getReceivableNo() {
		return receivableNo;
	}

	public void setReceivableNo(String receivableNo) {
		this.receivableNo = receivableNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReceivableType() {
		return receivableType;
	}

	public void setReceivableType(String receivableType) {
		this.receivableType = receivableType;
	}
}
