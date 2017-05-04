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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/dao/DeliverbillDetailDaoTest.java
 * 
 * FILE NAME        	: DeliverbillDetailDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.DeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.server.util.SpringTestUtil;

/**
 * 
 * DeliverbillDetailDao 测试类
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-28 下午5:53:22
 */
public class DeliverbillDetailDaoTest {
	private DeliverbillDetailDao deliverbillDetailDao;

	@Before
	public void init() throws Exception {
		this.deliverbillDetailDao = SpringTestUtil.getInstance()
				.getApplicationContext().getBean(DeliverbillDetailDao.class);
	}

	//@Test
	//@Rollback(true)
	public void testAdd() {
		DeliverbillDetailEntity deliverbillDetail = new DeliverbillDetailEntity();
		deliverbillDetail.setArrangeGoodsQty(10);
		deliverbillDetail.setArrangeStatus("ing");
		deliverbillDetail.setArrivesheetNo("");
		deliverbillDetail.setArriveTime(new Date());
		deliverbillDetail.setConsignee("mol");
		deliverbillDetail.setConsigneeAddress("");
		deliverbillDetail.setConsigneeContact("13760751908");
		deliverbillDetail.setDeliverRequire("0");
		deliverbillDetail.setDeliverType("deliver");
		deliverbillDetail.setDimension("6*1*1");
		deliverbillDetail.setEstimatedDeliverTime(new Date());
		deliverbillDetail.setGoodsName("图书");
		deliverbillDetail.setGoodsStatus("normal");
		deliverbillDetail.setGoodsVolumeTotal(new BigDecimal(6));
		deliverbillDetail.setIsAlreadyContact("0");
		deliverbillDetail.setIsException("0");
		deliverbillDetail.setIsNeedInvoice("1");
		deliverbillDetail.setNotes("");
		deliverbillDetail.setPaymentType("CASH");
		deliverbillDetail.setPreArrangeGoodsQty(5);
		deliverbillDetail.setSerialNo(2);
		deliverbillDetail.setTransportType("VEHICLE");
		deliverbillDetail.settSrvDeliverbillId("test_deliver_no_001");
		deliverbillDetail.setWaybillGoodsQty(10);
		deliverbillDetail.setWaybillNo("2012105");
		deliverbillDetail.setWeight(new BigDecimal(3.86));
//		deliverbillDetail.setPayAmount(10L);
		deliverbillDetail.setFastWaybillFlag((short) 1);

		Assert.assertNotNull(this.deliverbillDetailDao.add(deliverbillDetail));
	}
	
	//@Test
	//@Rollback(true)
	public void testQueryWaybillArrangedFlag() {
		String waybillNo = "2012116";
		String deptCode = "GS00002";

		DeliverbillDetailDto deliverbillDetailDto = new DeliverbillDetailDto();
		deliverbillDetailDto.setStatus(DeliverbillConstants.STATUS_CANCELED);
		deliverbillDetailDto.setWaybillNo(waybillNo);
		deliverbillDetailDto.setEndStockOrgCode(deptCode);

		Long cnt = this.deliverbillDetailDao
				.queryWaybillArrangedFlag(deliverbillDetailDto);

		Assert.assertTrue(cnt != null && cnt > 0L);
	}

	// @Test
	// public void testDelete() {
	// String deliverbillDetailId = "test_deliverbilldetail_id_001";
	// int result = this.deliverbillDetailDao.delete(deliverbillDetailId);
	// Assert.assertTrue(result > 0);
	// }

	// @Test
	// public void testQueryById() {
	// String deliverbillDetailId = "1bd0a9cd-ee70-4cd5-ac59-f554da708ed2";
	// DeliverbillDetailEntity deliverbillDetail = this.deliverbillDetailDao
	// .queryById(deliverbillDetailId);
	// Assert.assertNotNull(deliverbillDetail);
	// }
	@Test
	public void testQueryDetailList(){
		String tSrvDeliverBillId = "82b7f75e-002c-45aa-a6b5-e4b7fcfb163e";
/*		List<DeliverbillDetailEntity> list = this.deliverbillDetailDao.
				queryByDeliverbillId(tSrvDeliverBillId);
		for(DeliverbillDetailEntity entity:list){
			System.out.println(entity.getWaybillNo());
		}*/
	} 
}