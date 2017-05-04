package com.deppon.foss.module.transfer.partialline.server.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class LdpExternalBillTrackServiceTest extends BaseTestCase{

	@Autowired
	private ILdpExternalBillTrackService ldpExternalBillTrackService;
	@Test
	public void testAddLdpExternalBillTrack() {
		LdpExternalBillTrackDto record = new LdpExternalBillTrackDto();
		ldpExternalBillTrackService.addLdpExternalBillTrack(record);
	}
	@Test
	public void testQueryLdpExternalBillTrackList(){
		LdpExternalBillTrackDto ldpExternalBillTrackDto = new LdpExternalBillTrackDto();
		ldpExternalBillTrackDto.setWaybillNo("6665551063");
		ldpExternalBillTrackService.queryLdpExternalBillTrackList(ldpExternalBillTrackDto);
	}

}