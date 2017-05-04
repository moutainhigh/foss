/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
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
 * PROJECT NAME	: stl-consumer-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/api/shared/domain/InvoiceEntity.java
 * 
 * FILE NAME        	: InvoiceEntity.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 
 * 应收信息实体
 * 
 * 属性：
	应收单实体BillReceivableEntity ;

	应收单明细列表 List<BillReceivableDEntity> ;
 * @author 蒋迅
 * @date 2016-01-22 下午5:53:34
 * @since
 * @version
 */
public class NewReceivablePtpEntity extends BaseEntity {


	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7699686382718388144L;


	/**
	 * 应收单实体
	 */
	private BillReceivableEntity billReceivableEntity;

	/**
	 * 应收单明细列表
	 */
	private List<BillReceivableDEntity> billReceivableDList;

	public BillReceivableEntity getBillReceivableEntity() {
		return billReceivableEntity;
	}

	public void setBillReceivableEntity(BillReceivableEntity billReceivableEntity) {
		this.billReceivableEntity = billReceivableEntity;
	}

	public List<BillReceivableDEntity> getBillReceivableDList() {
		return billReceivableDList;
	}

	public void setBillReceivableDList(
			List<BillReceivableDEntity> billReceivableDList) {
		this.billReceivableDList = billReceivableDList;
	}

	
	
}
