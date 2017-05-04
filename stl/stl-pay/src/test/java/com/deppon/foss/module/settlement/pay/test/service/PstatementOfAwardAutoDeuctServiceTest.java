package com.deppon.foss.module.settlement.pay.test.service;

import com.deppon.foss.module.settlement.pay.test.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardAutoDeductService;
import org.springframework.test.annotation.Rollback;

public class PstatementOfAwardAutoDeuctServiceTest extends BaseTestCase{

	@Autowired
	private static IPartnerStatementOfAwardAutoDeductService partnerStatementOfAwardAutoDeductService;

	@Test
	@Rollback(false)
	public void test() {
		partnerStatementOfAwardAutoDeductService.autoDeductPartnerStatementOfAward();
	}

//
//	public static void main(String[] args) {
//		PartnerStatementOfAwardAutoDeductService s= new PartnerStatementOfAwardAutoDeductService();
//		s.autoDeductPartnerStatementOfAward();
//	}

	public void setPartnerStatementOfAwardAutoDeductService(
			IPartnerStatementOfAwardAutoDeductService partnerStatementOfAwardAutoDeductService) {
		this.partnerStatementOfAwardAutoDeductService = partnerStatementOfAwardAutoDeductService;
	}

}
