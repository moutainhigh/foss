package com.deppon.foss.module.transfer.load.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.transfer.load.api.server.service.IVehicleSealService;
import com.deppon.foss.module.transfer.load.api.shared.define.SealConstant;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaVehicleSealService;

/** 
 * @className: VehicleAssembleBillServiceTest
 * @date: 2013-1-7 上午9:42:17
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:com/deppon/foss/module/transfer/load/server/spring-test.xml" })
@TransactionConfiguration
@Transactional
public class VehicleSealServiceTest {

	@Autowired
	private IVehicleSealService vehicleSealService;
	
	@Autowired
	private IPdaVehicleSealService pdaVehicleSealService;
	
	@BeforeTransaction
	public void beforeTransaction() {

	}
	
	/**
	 * 封签差错OA上报
	 * @return 
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午9:45:36
	 */
	@Test
	public void autoReportSlipError(){
		vehicleSealService.autoReportSlipError();
	}
	
	
	/**
	 * PDA接口, 录入封签
	 * @return 
	 * @author zhangyixin
	 * @date 2013-10-30 14:49:50
	 */
	@Test
	public void insertSealOrig(){
		List<String> backSealNos = new ArrayList<String>();
		backSealNos.add("13103001");
		List<String> sideSealNos = new ArrayList<String>();
		pdaVehicleSealService.insertSealOrig("甘AHX131", "W1200030122", backSealNos, sideSealNos, "029312", "pda029312", "W1200030122", "PDA_SCAN");
	}
	
	/**
	 * PDA接口, 录入封签
	 * @return 
	 * @author zhangyixin
	 * @date 2013-10-30 14:49:50
	 */
	@Test
	public void insertSealDest(){
		List<com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity> backSealNos = new ArrayList<com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity>();
		com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity sealOrigDetail = new com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity();
		sealOrigDetail.setSealNo("13110701");
		sealOrigDetail.setSealType(SealConstant.SEAL_TYPE_DETAIL_BACK);
		sealOrigDetail.setBindType(SealConstant.SEAL_TYPE_PDA_SCAN);
		backSealNos.add(sealOrigDetail);
		pdaVehicleSealService.insertSealOrig("粤AK580", "W3100020616", backSealNos,
				"088933", "pda088933", "W3100020616", "W1200030122", 
				SealConstant.SEAL_TYPE_PDA_SCAN);
	}
}
