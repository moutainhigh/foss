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
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/waybill/StlWriteoffRepaymentAndReceibableTest.java
 * 
 * FILE NAME        	: StlWriteoffRepaymentAndReceibableTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.waybill;

import junit.framework.TestCase;

import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;

/**
 * 增加结算还款操作
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-2-2 上午11:17:20
 * @since
 * @version
 */
public class StlWriteoffRepaymentAndReceibableTest extends TestCase{
	
	/**
	 * 还款单Service
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 还款单实体
	 */
	private BillRepaymentEntity repaymentEntity;
	
	/**
	 * 冲应收对应的应收单
	 */
	private String waybillNo;
	
	/**
	 * 小票单号：应收单
	 */
	private String otherRevenueNo;
	
	/**
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午11:21:45
	 */
	public void testAddBillRepayment(){
		billRepaymentService.addBillRepayment(repaymentEntity, null);
	}

	
	/**
	 * @return  the repaymentEntity
	 */
	public BillRepaymentEntity getRepaymentEntity() {
		return repaymentEntity;
	}

	
	/**
	 * @param repaymentEntity the repaymentEntity to set
	 */
	public void setRepaymentEntity(BillRepaymentEntity repaymentEntity) {
		this.repaymentEntity = repaymentEntity;
	}


	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}


	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}


	
	/**
	 * @return  the otherRevenueNo
	 */
	public String getOtherRevenueNo() {
		return otherRevenueNo;
	}


	
	/**
	 * @param otherRevenueNo the otherRevenueNo to set
	 */
	public void setOtherRevenueNo(String otherRevenueNo) {
		this.otherRevenueNo = otherRevenueNo;
	}

}
