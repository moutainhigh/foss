package com.deppon.foss.module.settlement.agency.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandPayableAgencyService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillPayableAgencyDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.define.FossConstants;

public class BillLandPayableAgencyServiceTest extends BaseTestCase {
	
	@Autowired
	private IBillLandPayableAgencyService billLandPayableAgencyService;
	/**
	 * 查询快递代理其他管理应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:57:36
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testQueryLandPayablePage(){
		BillPayableAgencyDto dto = billLandPayableAgencyService.queryLandPayablePage(getDto(), 1, 20, AgencyTestUtil.getCurrentInfo());
		Assert.assertTrue(dto!=null);
	}
	
	/**
	 * 快递代理其他应付单导出
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:59:05
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testLandPayableListExport(){
		HSSFWorkbook hsb = billLandPayableAgencyService.landPayableListExport(getDto(), AgencyTestUtil.getCurrentInfo());
		Assert.assertTrue(hsb!=null);
	}
	
	/**
	 * 快递代理其他应付单导出
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:59:05
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testWriteBackLandOtherBillPayable(){
		List<BillPayableAgencyDto> billPayableAgencyDtos = new ArrayList<BillPayableAgencyDto>();
		BillPayableAgencyDto dto1 = new BillPayableAgencyDto();
		dto1.setWaybillNo("9909021301");
		billPayableAgencyDtos.add(dto1);
		BillPayableAgencyDto billPayableAgencyDto = new BillPayableAgencyDto();
		billLandPayableAgencyService.reverseAuditLandOtherBillPayable(billPayableAgencyDtos, billPayableAgencyDto, AgencyTestUtil.getCurrentInfo());

		billLandPayableAgencyService.writeBackLandOtherBillPayable(billPayableAgencyDtos, billPayableAgencyDto, AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他应付单导出
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:59:05
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAuditLandOtherBillPayable(){
		List<BillPayableAgencyDto> billPayableAgencyDtos = new ArrayList<BillPayableAgencyDto>();
		BillPayableAgencyDto dto1 = new BillPayableAgencyDto();
		dto1.setWaybillNo("9909021301");
		billPayableAgencyDtos.add(dto1);
		BillPayableAgencyDto billPayableAgencyDto = new BillPayableAgencyDto();
		billLandPayableAgencyService.reverseAuditLandOtherBillPayable(billPayableAgencyDtos, billPayableAgencyDto, AgencyTestUtil.getCurrentInfo());

		billLandPayableAgencyService.auditLandOtherBillPayable(billPayableAgencyDtos, billPayableAgencyDto, AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他应付单导出
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:59:05
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testReverseAuditLandOtherBillPayable(){
		List<BillPayableAgencyDto> billPayableAgencyDtos = new ArrayList<BillPayableAgencyDto>();
		BillPayableAgencyDto dto1 = new BillPayableAgencyDto();
		dto1.setWaybillNo("9909021301");
		billPayableAgencyDtos.add(dto1);
		BillPayableAgencyDto billPayableAgencyDto = new BillPayableAgencyDto();
		billLandPayableAgencyService.reverseAuditLandOtherBillPayable(billPayableAgencyDtos, billPayableAgencyDto, AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 快递代理其他应付单导出
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 上午10:59:05
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAddBillPayable(){
		billLandPayableAgencyService.addBillPayable(getAddDto(), AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 
	 * 获取查询参数
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午3:56:09
	 * @return
	 */
	private BillPayableAgencyDto getDto(){
		BillPayableAgencyDto dto = new BillPayableAgencyDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setIsRedBack(FossConstants.NO);
		dto.setCustomerCode("LDP00009");
		dto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__LAND_STOWAGE_OTHER);
		dto.setApproveStatus(SettlementDictionaryConstants.BILL_RECEIVABLE__APPROVE_STATUS__NOT_AUDIT);
		List<String> reNos = new ArrayList<String>();
		reNos.add("YF9300000124");
		reNos.add("YF9300000126");
		dto.setPayableNos(reNos);
		
		List<String> sources = new ArrayList<String>();
		sources.add("9909021301");
		dto.setSourceBillNos(sources);
		return dto;
	}
	
	/**
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-5 下午4:35:52
	 * @return
	 */
	private BillPayableAgencyDto getAddDto(){
		BillPayableAgencyDto dto = new BillPayableAgencyDto();
		dto.setWaybillNo("9909021301");
		dto.setAmount(new BigDecimal("10000"));
		dto.setCustomerCode("LDP00009");
		dto.setCustomerName("圆通速递");
		dto.setNotes("111");
		return dto;
	}
}
