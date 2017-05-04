/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author ibm-foss-sxw
 *
 */
public class WaybillPriceExpressServiceTest {    

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService#WaybillPriceExpressService()}.
	 */
	@Test
	public void testWaybillPriceExpressService() {
		try{
		WaybillPriceExpressService s = new WaybillPriceExpressService();
		s.queryPublishPriceDetailOnline("1", "2");
		}catch(Throwable e){
			
		}
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService#queryPublishPriceDetailOnline(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testQueryPublishPriceDetailOnline() {
		try{
			WaybillPriceExpressService s = new WaybillPriceExpressService();
			s.queryPublishPriceDetailOnline("1", "2");
			}catch(Throwable e){
				
			}
	}

}
