package com.deppon.foss.module.settlement.agency.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalLdpService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementLdpExternalBillDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;

public class VehicleAgencyExternalLdpServiceTest extends BaseTestCase{
	@Autowired
	private IVehicleAgencyExternalLdpService vehicleAgencyExternalLdpService;
	
	/**
	 * 新增外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:11:34
	 * @param dto    有中转传入数据【运单号、外发单号、外发代理编码/名称、金额等信息】
	 * @param currentInfo
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAddExternalBill(){
		//声明dto
		SettlementLdpExternalBillDto dto = new SettlementLdpExternalBillDto();
		vehicleAgencyExternalLdpService.addExternalBill(dto,  AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 
	 * 修改外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:17:43
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testModifyExternalBill(){
		//声明dto
		SettlementLdpExternalBillDto dto = new SettlementLdpExternalBillDto();
		vehicleAgencyExternalLdpService.modifyExternalBill(dto, AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 审核外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:18:38
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testAuditExternalBill(){
		List<SettlementLdpExternalBillDto> externalBills = new ArrayList<SettlementLdpExternalBillDto>();
		vehicleAgencyExternalLdpService.auditExternalBill(externalBills,  AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 * 作废外发单服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:19:20
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void disableExternalBill(){
		List<SettlementLdpExternalBillDto> externalBills = new ArrayList<SettlementLdpExternalBillDto>();
		vehicleAgencyExternalLdpService.disableExternalBill(externalBills,  AgencyTestUtil.getCurrentInfo());
	}
	
	/**
	 *反审核外发服务
	 * @author guxinhua
	 * @date 2013-7-24 下午6:19:54
	 * @param externalBills
	 * @param currentInfo
	 * @see
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void reverseAuditExternalBill(){
		List<SettlementLdpExternalBillDto> externalBills = new ArrayList<SettlementLdpExternalBillDto>();
		vehicleAgencyExternalLdpService.reverseAuditExternalBill(externalBills,  AgencyTestUtil.getCurrentInfo());
	}
}
