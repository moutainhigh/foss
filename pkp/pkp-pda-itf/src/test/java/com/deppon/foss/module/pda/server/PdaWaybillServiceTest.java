/**
 * Project Name:pkp-pda-itf
 * File Name:PdaWaybillServiceTest.java
 * Package Name:com.deppon.foss.module.pda.server
 * Date:2014-10-11下午1:41:10
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.deppon.foss.module.pda.server;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPicturePdaDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * ClassName:PdaWaybillServiceTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * 
 * Date:     2014-10-11 下午1:41:10 <br/>
 * @author   157229-zxy
 * @version  
 * @since    JDK 1.6
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{ 
		"classpath*:com/deppon/foss/module/pda/test/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfo.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/administrativeRegions.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizations.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionary.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryCache.xml",
		"classpath*:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",
		"classpath*:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryValue.xml",
		"classpath*:com/deppon/foss/module/pickup/pricing/server/META-INF/baseProduct.xml"
})
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class PdaWaybillServiceTest {
	private static ApplicationContext ctx = null;
	
	@Autowired
	private IPdaWaybillService pdaWaybillService;
	

	@Test
	public void testSubmitWaybillPictureByPDA(){
		WaybillPicturePdaDto waybillPicturePdaDto = new WaybillPicturePdaDto();
		waybillPicturePdaDto.setBigGoodsFlag("Y");
		waybillPicturePdaDto.setBillOrgCode("W060002070313");
		waybillPicturePdaDto.setCashPayFlag("Y");
		waybillPicturePdaDto.setDriverCode("068003");
		waybillPicturePdaDto.setFilePath("/filepath/a.txt");
		waybillPicturePdaDto.setOrderNo("");
		waybillPicturePdaDto.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
		waybillPicturePdaDto.setRemark("");
		waybillPicturePdaDto.setWaybillNo("214102400");
		waybillPicturePdaDto.setEquipmentNo("214102400");
		waybillPicturePdaDto.setWaybillUuid(UUIDUtils.getUUID());
		pdaWaybillService.submitWaybillPictureByPDA(waybillPicturePdaDto);
	}
	
	@Test
	public void testCancelWaybillPictureByPDA(){
		pdaWaybillService.cancelWaybillPictureByPDA("92014101101", "0000");
	}
	
	@Test
	public void testQueryWaybillPictureByPDA(){
		WaybillPicturePdaDto waybillPicturePdaDto = new WaybillPicturePdaDto();
		waybillPicturePdaDto.setDriverCode("124920");
		try {
			pdaWaybillService.queryWaybillPictureByPDA(waybillPicturePdaDto);
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	
}

