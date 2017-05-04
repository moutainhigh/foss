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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/pickup/predeliver/server/dao/DeliverbillDaoTest.java
 * 
 * FILE NAME        	: DeliverbillDaoTest.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.server.dao.impl.DeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.server.util.SpringTestUtil;

public class DeliverbillDaoTest {
	private IDeliverbillDao deliverbillDao = null;

	@Before
	public void init() throws Exception {
		this.deliverbillDao = SpringTestUtil.getInstance()
				.getApplicationContext().getBean(DeliverbillDao.class);
	}

	@Test
	public void queryPdaDownloadedDeliverbillList() {
		String vehicleNo = "沪A082R9";
		String driverCode = "094425";

		DeliverbillDto deliverbillDto = new DeliverbillDto();
		deliverbillDto.setStatus(DeliverbillConstants.STATUS_CONFIRMED);
		deliverbillDto.setVehicleNo(vehicleNo);
		deliverbillDto.setDriverCode(driverCode);

		List<DeliverbillDto> deliverbillDtoList = this.deliverbillDao
				.queryByCondition(deliverbillDto);

		Assert.assertTrue(deliverbillDtoList != null
				&& deliverbillDtoList.size() > 0L);
	}
}