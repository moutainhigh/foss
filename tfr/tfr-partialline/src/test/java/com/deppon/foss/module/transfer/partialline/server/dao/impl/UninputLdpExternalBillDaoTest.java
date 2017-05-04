package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputLdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class UninputLdpExternalBillDaoTest extends BaseTestCase{

	@Autowired
	private IUninputLdpExternalBillDao uninputLdpExternalBillDao;
	@Test
	public void testQueryUninputLdpExternalBill() {
		LdpHandoverBillDetailDto detailDto = new LdpHandoverBillDetailDto();
		uninputLdpExternalBillDao.queryUninputLdpExternalBill(detailDto, 10, 0);
	}

	@Test
	public void testQueryUninputLdpExternalBillCount() {
		LdpHandoverBillDetailDto detailDto = new LdpHandoverBillDetailDto();
		uninputLdpExternalBillDao.queryUninputLdpExternalBillCount(detailDto);
	}

	@Test
	public void testQueryLastHandoverBillDetail() {
		LdpHandoverBillDetailDto detailDto = new LdpHandoverBillDetailDto();
		uninputLdpExternalBillDao.queryLastHandoverBillDetail(detailDto);
	}

}
