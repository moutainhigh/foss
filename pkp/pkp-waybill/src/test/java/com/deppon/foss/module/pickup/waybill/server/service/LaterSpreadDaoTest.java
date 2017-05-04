package com.deppon.foss.module.pickup.waybill.server.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.api.server.dao.ILaterSpreadDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ICustomerCouponService;
import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.LaterSpreadDao;
import com.deppon.foss.module.pickup.waybill.server.service.impl.CustomerCouponService;
import com.deppon.foss.module.pickup.waybill.shared.domain.CustomerCouponEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LaterSpreadEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LaterSpreadDto;
import com.deppon.foss.util.UUIDUtils;

public class LaterSpreadDaoTest {

	ILaterSpreadDao laterSpreadDao;

	private ICustomerCouponService customerCouponService;

	@Before
	public void setUpBeforeClass() throws Exception {
		laterSpreadDao = TestHelper.get().getBeanByClass(LaterSpreadDao.class);
		customerCouponService = TestHelper.get().getBeanByClass(
				CustomerCouponService.class);

	}

	@Test
	public void save() {
		LaterSpreadEntity e = new LaterSpreadEntity();
		e.setId(UUIDUtils.getUUID());
		e.setBillTime(new Date());
		e.setBillAmount(new BigDecimal(100));
		e.setCartageFee(new BigDecimal(80));
		e.setCreateDate(new Date());
		e.setCustomerCode("hbhk");
		e.setIsSend("Y");
		e.setOrdinaryFee(new BigDecimal(60));
		e.setPhone("15821999948");
		e.setProductCode("FLF");
		e.setSmsContent("哈哈哈");
		e.setSpread(new BigDecimal(10));
		e.setWaybillNo("11111111111111");
		for (int i = 0; i < 12; i++) {
			e.setId(UUIDUtils.getUUID() + 1);
			laterSpreadDao.save(e);
		}

	}

	@Test
	public void updateLaterSpreadForJobTopNum() {
		LaterSpreadDto lsd = laterSpreadDao.updateLaterSpreadForJobTopNum(
				"1111111", 10);
		System.out.println(lsd.getResultNum());
	}

	@Test
	public void queryLaterSpreadByJobID() {
		List<LaterSpreadEntity> list = laterSpreadDao
				.queryLaterSpreadByJobID("1111111");
		System.out.println(list);

	}

	@Test
	public void executeSendSMSFail() {
		//
		LaterSpreadEntity e = new LaterSpreadEntity();
		e.setId("36e16631-fad6-43b2-bed6-be3c5d655e13");
		e.setJobId("N/A");
		laterSpreadDao.executeSendSMSFail(e);
	}

	@Test
	public void testinsert() throws Exception {
		for (int i = 0; i < 10; i++) {
			CustomerCouponEntity entity = new CustomerCouponEntity();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String d = format.format(System.currentTimeMillis() + 101000000);
			Date date = format.parse(d);
			System.out.println(format.format(date));
			entity.setActiveDate(date);
			entity.setCouponCode("cc111" + i);
			entity.setCustomerCode("400481106");
			entity.setPhone("15821999948");
			entity.setAmount(new BigDecimal(10 + i));
			entity.setWaybillNo("807711");
			customerCouponService.insert(entity);
		}
	}

	@Test
	public void testuseCustomerCoupon() throws Exception {
		customerCouponService.useCustomerCoupon("hbhk1", "cc111");

	}

	@Test
	public void testqueryCustomerCouponList() throws Exception {
		CustomerCouponEntity entity = new CustomerCouponEntity();
		entity.setActiveDate(new Date());
		entity.setCouponCode("cc111");
		entity.setCustomerCode("hbhk1");
		entity.setPhone("15821999948");
		entity.setAmount(new BigDecimal(10));
		entity.setWaybillNo("807711");
		customerCouponService.queryCustomerCouponList(entity, 0, 10);

	}
	
	@Test
	public void testqueryWaybillChangeDestination() throws Exception {
		Long c = laterSpreadDao.queryWaybillChangeDestinationAndReceiveMethod("14111301");
		System.out.println("ssssssssss:"+c);
	}
}
