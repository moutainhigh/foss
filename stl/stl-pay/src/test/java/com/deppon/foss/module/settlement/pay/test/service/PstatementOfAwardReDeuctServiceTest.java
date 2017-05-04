package com.deppon.foss.module.settlement.pay.test.service;

import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardReDeductService;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PstatementOfAwardReDeuctServiceTest extends BaseTestCase{

	@Autowired
	private static IPartnerStatementOfAwardReDeductService partnerStatementOfAwardReDeductService;

	@Test
	public void test() {
		partnerStatementOfAwardReDeductService.reDeductPartnerStatementOfAward();
	}

//
//	public static void main(String[] args) {
//		PartnerStatementOfAwardAutoDeductService s= new PartnerStatementOfAwardAutoDeductService();
//		s.autoDeductPartnerStatementOfAward();
//	}

	public void setPartnerStatementOfAwardReDeductService(
			IPartnerStatementOfAwardReDeductService partnerStatementOfAwardReDeductService) {
		this.partnerStatementOfAwardReDeductService = partnerStatementOfAwardReDeductService;
	}

}
