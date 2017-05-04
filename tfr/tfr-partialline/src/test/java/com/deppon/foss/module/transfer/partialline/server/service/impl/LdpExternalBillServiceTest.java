package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class LdpExternalBillServiceTest extends BaseTestCase{

	@Autowired
	private ILdpExternalBillService ldpExternalBillService;
	@Test
	public void testSelectByParams() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.selectByParams(dto, 10, 0, currentInfo);
	}

	@Test
	public void testQueryExternalBillInfoList() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillService.queryExternalBillInfoList(dto, false);
	}

	@Test
	public void testQueryCount() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.queryCount(dto, currentInfo);
	}

	@Test
	public void testAddOneLdpExternalBill() {
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.addOneLdpExternalBill("0001","12345", currentInfo);
	}

	@Test
	public void testUpdateLdpExternalBill() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.updateLdpExternalBill(dto, currentInfo);
	}

	@Test
	public void testQueryWaybillInfo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.queryWaybillInfo(dto, "12345", currentInfo);
	}

	@Test
	public void testAuditLdpExternalBill() throws IllegalAccessException, InvocationTargetException {
		List<String> auditIds = new ArrayList<String>();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.auditLdpExternalBill(auditIds, currentInfo);
	}

	@Test
	public void testDeAuditLdpExternalBill() throws IllegalAccessException, InvocationTargetException {
		List<String> auditIds = new ArrayList<String>();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.deAuditLdpExternalBill(auditIds, currentInfo);
	}

	@Test
	public void testInvalideLdpExternalBill() throws IllegalAccessException, InvocationTargetException {
		List<String> auditIds = new ArrayList<String>();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		ldpExternalBillService.invalideLdpExternalBill(auditIds, currentInfo);
	}

	@Test
	public void testQueryLdpExternalBillByWaybillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillService.queryLdpExternalBillByWaybillNo(dto);
	}

	@Test
	public void testQueryLdpExternalBillByWaybillNoForChange() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillService.queryLdpExternalBillByWaybillNoForChange(dto);
	}

	@Test
	public void testQueryExternalBillByWaybillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillService.queryExternalBillByWaybillNo(dto);
	}

	@Test
	public void testCheckExistLdpExternalBillByWaybillNo() {
		ldpExternalBillService.checkExistLdpExternalBillByWaybillNo("123");
	}

	@Test
	public void testQueryLdpExternalBillDetail() {
		ldpExternalBillService.queryLdpExternalBillDetail("13214");
	}

}