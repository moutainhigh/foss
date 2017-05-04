package com.deppon.foss.module.settlement.agency.test.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;

public class ComplementFunctionServiceTest extends BaseTestCase {

	@Autowired
	private IComplementFunctionService complementFunctionService;
	
	/**
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。.
	 *
	 * @param dto the dto
	 * @param currentInfo the current info
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午2:40:03
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void testComplementFunctionWriteBackAndCreateReceivable(){
		SettlementComplementBillDto dto = new SettlementComplementBillDto();
		complementFunctionService.complementFunctionWriteBackAndCreateReceivable(dto, AgencyTestUtil.getCurrentInfo());
	}
	
}
