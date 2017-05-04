
/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.dao;

import static org.junit.Assert.fail;

import java.util.Date;

import org.jgroups.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author ibm-foss-sxw
 *
 */
public class WaybillExpressDaoTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillExpressDaoTest.class);
	private IWaybillExpressDao waybillExpressDao = null;
	AdjustPlanSearcherDto condition;
	private static ApplicationContext ctx = null;
	String[] xmls = new String[] { "com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml", "com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml" };
	private boolean testadd= false;
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillExpressDao == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillExpressDao = ctx.getBean(WaybillExpressDao.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#addWaybillExpressEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
	 */
	@Test
	public void testAddWaybillExpressEntity() {
		try {
			WaybillExpressEntity e = new WaybillExpressEntity();
			e.setId(UUID.randomUUID().toString());
			e.setAddCodeTime(new Date());
			e.setBankTradeSerail("123");
			e.setBillTime(new Date());
			e.setCanReturnCargo(FossConstants.ACTIVE);
			e.setChangeVolume(FossConstants.ACTIVE);
			e.setCreateDate(new Date());
			e.setCreateOrgCode("123");
			e.setCreateUser("092444");
			e.setCustomerPickupOrgCode("234");
			e.setCustomerPickupOrgName("123");;
			e.setDeliveryCustomerCityCode("123");
			e.setDeliveryCustomerDistCode("123");
			e.setWaybillNo("2323");
			e.setWaybillId("2");
			e.setReceiveOrgCode("233");
			e.setReceiveCustomerProvCode("123");
			if(testadd){
				waybillExpressDao.addWaybillExpressEntity(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#updateWaybillExpressByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
	 */
	@Test
	public void testUpdateWaybillExpressByWaybillNo() {
		try{
			WaybillExpressEntity e = new WaybillExpressEntity();
			e.setId(UUID.randomUUID().toString());
			e.setAddCodeTime(new Date());
			e.setBankTradeSerail("123");
			e.setBillTime(new Date());
			e.setCanReturnCargo(FossConstants.ACTIVE);
			e.setChangeVolume(FossConstants.ACTIVE);
			e.setCreateDate(new Date());
			e.setCreateOrgCode("123");
			e.setCreateUser("092444");
			e.setCustomerPickupOrgCode("234");
			e.setCustomerPickupOrgName("123");;
			e.setDeliveryCustomerCityCode("123");
			e.setDeliveryCustomerDistCode("123");
			e.setWaybillNo("2323");
			e.setWaybillId("2");
			e.setReceiveOrgCode("233");
			e.setReceiveCustomerProvCode("123");
			
			waybillExpressDao.updateWaybillExpressByWaybillNo(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#updateWaybillExpressById(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
	 */
	@Test
	public void testUpdateWaybillExpressById() {
		try{	WaybillExpressEntity e = new WaybillExpressEntity();
		e.setId(UUID.randomUUID().toString());
		e.setAddCodeTime(new Date());
		e.setBankTradeSerail("123");
		e.setBillTime(new Date());
		e.setCanReturnCargo(FossConstants.ACTIVE);
		e.setChangeVolume(FossConstants.ACTIVE);
		e.setCreateDate(new Date());
		e.setCreateOrgCode("123");
		e.setCreateUser("092444");
		e.setCustomerPickupOrgCode("234");
		e.setCustomerPickupOrgName("123");;
		e.setDeliveryCustomerCityCode("123");
		e.setDeliveryCustomerDistCode("123");
		e.setWaybillNo("2323");
		e.setWaybillId("2");
		e.setReceiveOrgCode("233");
		e.setReceiveCustomerProvCode("123");
		waybillExpressDao.updateWaybillExpressById(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#queryWaybillExpressByNo(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressByNo() {
		try{	waybillExpressDao.queryWaybillExpressByNo("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#queryWaybillExpressByWaybillId(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressByWaybillId() {
		try{	waybillExpressDao.queryWaybillExpressByWaybillId("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#queryWaybillExpressById(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressById() {
		try{	waybillExpressDao.queryWaybillExpressById("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.dao.impl.WaybillExpressDao#queryCityProvince(java.lang.String)}.
	 */
	@Test
	public void testQueryCityProvince() {
		try{	waybillExpressDao.queryCityProvince("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}