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
 * PROJECT NAME	: stl-pay-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/pay/api/shared/domain/NewBillPayablePtpEntity.java
 * 
 * FILE NAME        	: NewBillPayablePtpEntity.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

/**
 * 
 * 新增应付信息实体
 * 
 * 属性：
	应付单实体BillPayableEntity ;

	应付单明细列表 List<BillPayableDEntity> ;
 * @author 蒋迅
 * @date 2016-01-23 下午05:02:00
 * @since
 * @version
 */
public class NewBillPayablePtpEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 458621163100842072L;

	/**
	 * 应付单实体
	 */
	private BillPayableEntity billPayableEntity;

	/**
	 * 应付单明细列表
	 */
	private List<BillPayableDEntity> billPayableDList;

	public BillPayableEntity getBillPayableEntity() {
		return billPayableEntity;
	}

	public void setBillPayableEntity(BillPayableEntity billPayableEntity) {
		this.billPayableEntity = billPayableEntity;
	}

	public List<BillPayableDEntity> getBillPayableDList() {
		return billPayableDList;
	}

	public void setBillPayableDList(List<BillPayableDEntity> billPayableDList) {
		this.billPayableDList = billPayableDList;
	}
	
}
