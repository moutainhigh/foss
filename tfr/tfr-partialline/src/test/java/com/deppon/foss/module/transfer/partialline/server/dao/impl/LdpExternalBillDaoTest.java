package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class LdpExternalBillDaoTest extends BaseTestCase{

	@Autowired
	private ILdpExternalBillDao ldpExternalBillDao;
	@Test
	public void testQueryLdpExternalBillList() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryLdpExternalBillList(dto);
	}

	@Test
	public void testInsert() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.insert(dto);
	}

	@Test
	public void testQueryByPrimaryKey() {
		ldpExternalBillDao.queryByPrimaryKey("123456");
	}

	@Test
	public void testUpdateByPrimaryKey() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.updateByPrimaryKey(dto);
	}

	@Test
	public void testQueryHandedUninputWaybill() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryHandedUninputWaybill(dto);
	}

	@Test
	public void testUpdateAuditStatusByPrimaryKey() {
		String auditStatus = PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED;
		List<String> auditIds = new ArrayList<String>();
		
		ldpExternalBillDao.updateAuditStatusByPrimaryKey(auditIds, auditStatus);
	}

	@Test
	public void testQueryByWaybillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryByWaybillNo(dto);
	}

	@Test
	public void testQueryByExternalBillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryByExternalBillNo(dto);
	}

	@Test
	public void testQueryByWaybillNoAndRegisterTime() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryByWaybillNoAndRegisterTime(dto);
	}

	@Test
	public void testSelectLdpExternalBillByPrimaryKeys() {
		List<String> externalBillIds = new ArrayList<String>();
		ldpExternalBillDao.selectLdpExternalBillByPrimaryKeys(externalBillIds);
	}

	@Test
	public void testSelectWayBillByWaybillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.selectWayBillByWaybillNo(dto);
	}

	@Test
	public void testQueryLdpExternalBillByWaybillNo() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryLdpExternalBillByWaybillNo(dto);
	}

	@Test
	public void testQueryLdpExternalBillByWaybillNoForChange() {
		LdpExternalBillDto dto = new LdpExternalBillDto();
		ldpExternalBillDao.queryLdpExternalBillByWaybillNoForChange(dto);
	}

	@Test
	public void testQueryLdpExternalBillCountByWaybillNo() {
		ldpExternalBillDao.queryLdpExternalBillCountByWaybillNo("1234");
	}
	
	@Test
	public void testQueryCountIfExistValidLdpExternalBillForStl(){
		LdpExternalBillDto dto = new LdpExternalBillDto();
		dto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_INVALID);
		dto.setWaybillNo("123");
		dto.setAgentCompanyCode("LP00001");
		dto.setTransferExternal("N");
		ldpExternalBillDao.queryCountIfExistValidLdpExternalBillForStl(dto);
	}

}