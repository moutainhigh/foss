package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IPartnersWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;

public class PartnersWaybillDaoTest {
	
	private IPartnersWaybillDao partnersWaybillDao ;
	private ApplicationContext cx = null;
	
	@Before
	public void setUp() throws Exception {
		if (cx == null || partnersWaybillDao == null) {
			cx = new ClassPathXmlApplicationContext(new String[]{"com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml"});
			partnersWaybillDao = (IPartnersWaybillDao) cx.getBean(IPartnersWaybillDao.class);
		}
	}

	@Test
	public void testAddPartnersWaybillEntity() {
		PartnersWaybillEntity entity = new PartnersWaybillEntity() ;
		entity.setWaybillNo("123456");
		entity.setPickupFee(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(100)));
		entity.setServiceFee(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(100)));
		entity.setTotalFee(entity.getPickupFee().add(entity.getServiceFee()));
		
		partnersWaybillDao.addPartnersWaybillEntity(entity);
	}

	/**
	 * 
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	@Test
	public void testUpdatePartnersWaybill() {
		PartnersWaybillEntity entity = new PartnersWaybillEntity() ;
		entity.setWaybillNo("123456");
		entity.setBoxCharge(BigDecimal.valueOf(5));
		entity.setDeliveryGoodsFee(BigDecimal.valueOf(8));
		partnersWaybillDao.updatePartnersWaybill(entity);
	}

	@Test
	public void testUpdateActiveByWaybillNo() {
		Map<String, String> map = new HashMap<String, String>() ;
		map.put("waybillNo", "123456");
		map.put("active", "N");
		partnersWaybillDao.updateActiveByWaybillNo(map);
	}

}
