package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentApplyDisableService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableApplicationEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.RepaymentDisableDetailEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

/**
 * 还款单作废申请test
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午8:36:09
 */
public class BillRepaymentApplyDisableServiceTest extends BaseTestCase{
	@Autowired
	private IBillRepaymentApplyDisableService billRepaymentApplyDisableService;
	
	@Test
	@Rollback(false)
	public void addDisableApplicationTest() {
		BillRepaymentDisableDto dto = new BillRepaymentDisableDto();
		dto.setAmount(new BigDecimal(5600));
		dto.setApplyOrgCode("W0113080203");
		dto.setApplyOrgName("上海黄浦赛格营业部");
		dto.setApplyUserCode("092036");
		dto.setApplyUserName("薄辰龙");
		dto.setDisableReason("测试插入");
		dto.setPaymentType("CH");
		dto.setRepaymentNo("HK2000027963");
		dto.setIsAllDisable("Y");
		
		RepaymentDisableDetailEntity entity = new RepaymentDisableDetailEntity();
		entity.setAmount(new BigDecimal(5600));
		entity.setReceviceNo("YS6000049243");
		entity.setReceviceWaybillNo("201250701");
		entity.setRepaymentNo("HK2000027963");
		entity.setOrgCode("W0113080203");
		entity.setOrgName("上海黄浦赛格营业部");
		entity.setWriteOffBillNo("HX000058885");
		entity.setWriteoffType("RR");
		entity.setWriteoffTime(new Date());
		
		dto.getDetails().add(entity);
		
		billRepaymentApplyDisableService.addDisableApplication(dto);
	} 
	
	@Test
	@Rollback(true)
	public void queryRepaymentAndWriteoffTest() {
		BillRepaymentDisableDto dto = new BillRepaymentDisableDto();
		dto.setRepaymentNo("HK2000027963");
		dto.setApplyUserCode("088933");
		BillRepaymentDisableResultDto billRepaymentDisableResultDto = billRepaymentApplyDisableService.queryRepayment(dto);
		System.out.println(billRepaymentDisableResultDto.getTotalCount());
		billRepaymentDisableResultDto = billRepaymentApplyDisableService.queryRepaymentWriteoff(dto, 0, 10);
		System.out.println(billRepaymentDisableResultDto.getTotalCount());
	}
	
	@Test
	@Rollback(true)
	public void queryDisableApplicationByDtoTest() {
		BillRepaymentDisableDto dto = new BillRepaymentDisableDto();
		dto.setApplyOrgCode("W0113080203");
		List<String> r = new ArrayList<String>();
		r.add("HK2000027963");
		dto.setRepaymentNos(r);
		BillRepaymentDisableResultDto result = billRepaymentApplyDisableService.queryDisableApplicationByDto(dto, 0, 10);
		System.out.println(result.getApplys().get(0).getRepaymentNo());
	}
}
