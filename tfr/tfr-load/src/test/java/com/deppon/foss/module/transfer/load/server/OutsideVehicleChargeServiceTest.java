package com.deppon.foss.module.transfer.load.server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
/**
 * 
 * @author liangfuxiang
 * @date 2013-03-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class OutsideVehicleChargeServiceTest {
	final Logger logger = LoggerFactory.getLogger(OutsideVehicleChargeServiceTest.class);
	
	@Autowired
	private IOutsideVehicleChargeService outsideVehicleChargeService;
	@BeforeTransaction
	public void beforeTransaction() {
		
	}
	
	@Rule
	public   ExpectedException excepiton=  ExpectedException.none();
	
	//@Test
	public final void testIsBillPayable() {
		
		try {
			//数据：GZWHZYC13022102---配载单号(存在费用调整)   GZWHZYC13022102Y---配载单号(不存在费用调整)   "   "-----配载单号为空
			assertEquals(true,outsideVehicleChargeService.isBillPayable("GZWHZYC13022102"));
		}
		catch (Exception e) {
			excepiton.expect(TfrBusinessException.class);
			excepiton.expectMessage(e.getMessage());
		}
		
	}

	@Test
	public final void testQueryOutVehicleAssembleBillAndFeeVoList(){
		try {
			List<String> vehicleAssembleNoList=new ArrayList<String>();
			//vehicleAssembleNoList.add("SHBJ130415Z04");
			vehicleAssembleNoList.add("GZWHZYC13022102");
			//vehicleAssembleNoList.add("SHBJ13041903");
		    List<OutVehicleAssembleBillAndFeeVo> list=outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(vehicleAssembleNoList);
			System.out.println("0--------------------------------------0:"+list.size());
		}
		catch (Exception e) {
			excepiton.expect(TfrBusinessException.class);
			excepiton.expectMessage(e.getMessage());
		}
	}
}
