package com.deppon.foss.module.settlement.agency.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandReceivableAgencyService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillReceivableAgencyDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.define.FossConstants;

public class BillLandReceivableAgencyServiceTest extends BaseTestCase {
	@Autowired
	private IBillLandReceivableAgencyService billLandReceivableAgencyService;
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryLandReceivablePage(){	
		billLandReceivableAgencyService.queryLandReceivablePage(getDto(),1, 20, AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testLandReceivableListExport(){
		billLandReceivableAgencyService.landReceivableListExport(getDto(), AgencyTestUtil.getCurrentInfo());
		
	}
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testWriteBackLandOtherBillReceivable(){
		List<BillReceivableAgencyDto> billDtos = new ArrayList<BillReceivableAgencyDto>();
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setWaybillNo("9909021301");
		billDtos.add(dto);
		billLandReceivableAgencyService.writeBackLandOtherBillReceivable(billDtos, getDto(),  AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAuditLandOtherBillReceivable(){
		List<BillReceivableAgencyDto> billDtos = new ArrayList<BillReceivableAgencyDto>();
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setWaybillNo("9909021301");
		billDtos.add(dto);
		billLandReceivableAgencyService.auditLandOtherBillReceivable(billDtos, getDto(),  AgencyTestUtil.getCurrentInfo());	
	}
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testReverseAuditLandOtherBillReceivable(){
		List<BillReceivableAgencyDto> billDtos = new ArrayList<BillReceivableAgencyDto>();
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setWaybillNo("9909021301");
		billDtos.add(dto);
		billLandReceivableAgencyService.reverseAuditLandOtherBillReceivable(billDtos, getDto(), AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他管理分页查询
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:15:41
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAddBillReceivable(){
		billLandReceivableAgencyService.addBillReceivable(getAddDto(), AgencyTestUtil.getCurrentInfo());
	}

	/**
	 * 
	 * 获取查询参数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午3:56:09
	 * @return
	 */
	private BillReceivableAgencyDto getDto(){
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setIsRedBack(FossConstants.NO);
		dto.setCustomerCode("LDP00009");
		dto.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_RECEIVABLE);
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		List<String> reNos = new ArrayList<String>();
		reNos.add("YS9300000121");
		reNos.add("YS9300000123");
		dto.setReceivableNos(reNos);
		
		List<String> sources = new ArrayList<String>();
		sources.add("9909021301");
		sources.add("5013090403");
		dto.setSourceBillNos(sources);
		return dto;
	}
	
	/**
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午4:35:52
	 * @return
	 */
	private BillReceivableAgencyDto getAddDto(){
		BillReceivableAgencyDto dto = new BillReceivableAgencyDto();
		dto.setWaybillNo("9909021301");
		dto.setAmount(new BigDecimal("10000"));
		dto.setCustomerCode("LDP00009");
		dto.setCustomerName("圆通速递");
		dto.setNotes("111");
		return dto;
	}
}

