
/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.service;

import static org.junit.Assert.fail;

import java.util.Date;

import org.jgroups.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.server.dao.WaybillExpressDaoTest;
import com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressArrivalSheetDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author ibm-foss-sxw
 *
 */
public class WaybillExpressServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillExpressDaoTest.class);
	private WaybillExpressService waybillExpressService = null;
	AdjustPlanSearcherDto condition;
	private static ApplicationContext ctx = null;
	String[] xmls = new String[] { "com/deppon/foss/module/pickup/waybill/server/META-INF/springTest.xml", "com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml" };
	private boolean testadd= false;
	
	@Before
	public void init() throws Exception {
		try {
			if (ctx == null || waybillExpressService == null) {
				ctx = new ClassPathXmlApplicationContext(xmls);
				waybillExpressService = ctx.getBean(WaybillExpressService.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#getWaybillExpressDao()}.
	 */
	@Test
	public void testGetWaybillExpressDao() {
		waybillExpressService.getWaybillExpressDao();
	}
	
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#addWaybillExpressEntity(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
	 */
	@Test
	public void testAddWaybillExpressEntity() {
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
		if(testadd){
			waybillExpressService.addWaybillExpressEntity(e);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#updateWaybillExpressByWaybillNo(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
	 */
	@Test
	public void testUpdateWaybillExpressByWaybillNo() {
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
		
		waybillExpressService.updateWaybillExpressByWaybillNo(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#updateWaybillExpressById(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity)}.
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
		
		waybillExpressService.updateWaybillExpressById(e);
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#queryWaybillExpressByNo(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressByNo() {
		try{	waybillExpressService.queryWaybillExpressByNo("2");
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#queryWaybillExpressByWaybillId(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressByWaybillId() {
		try{	waybillExpressService.queryWaybillExpressByWaybillId("2");
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#queryWaybillExpressById(java.lang.String)}.
	 */
	@Test
	public void testQueryWaybillExpressById() {
		try{	waybillExpressService.queryWaybillExpressById("2");
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#addWaybillExpressCode(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressDto)}.
	 */
	@Test
	public void testAddWaybillExpressCode() {
		try{	WaybillExpressDto dto = new WaybillExpressDto();
		dto.setWaybillNo("2361361278631278");
		waybillExpressService.addWaybillExpressCode(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#queryPublishPriceDetail(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testQueryPublishPriceDetail() {
		try{	waybillExpressService.queryPublishPriceDetail("232323",	 "22783");
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#createExpressArrivalSheetAndDeliveryBill(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressArrivalSheetDto)}.
	 */
	@Test
	public void testCreateExpressArrivalSheetAndDeliveryBill() {
		try{	WaybillExpressArrivalSheetDto dto = new WaybillExpressArrivalSheetDto();
		waybillExpressService.createExpressArrivalSheetAndDeliveryBill(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}}
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillExpressService#generateArriveSheetId(java.lang.String)}.
	 */
	@Test
	public void testGenerateArriveSheetId() {
		try{	waybillExpressService.generateArriveSheetId("23232");
		} catch (Exception e) {
			
		}
	}
	
	
	
}