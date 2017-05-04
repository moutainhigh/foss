package com.deppon.foss.module.settlement.agency.test.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.IBillLandRecAndPayImportService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.BillRecAndPayImportDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;

public class BillLandRecAndPayImportServiceTest extends BaseTestCase {
	
	@Autowired
	private IBillLandRecAndPayImportService billLandRecAndPayImportService;
	
	/**
	 * 保存应收、应付信息
	 * @author 045738-foss-maojianqiang
	 * @date 2013-9-4 下午5:42:49
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void saveAirImportPayAndRec(){
		//声明dto
		BillRecAndPayImportDto dto = new BillRecAndPayImportDto();
		//声明list
		List<BillRecAndPayImportDto> list = new ArrayList<BillRecAndPayImportDto>();
		BillRecAndPayImportDto dto1 = new BillRecAndPayImportDto();
		dto1.setWaybillNo("5013090403");
		dto1.setOrigOrgCode("W011302020515");
		dto1.setOrigOrgName("上海闵行区浦江镇营业部");
		dto1.setCustomerCode("LDP130814");
		dto1.setCustomerName("捷达速递");
		dto1.setRecAmount(new BigDecimal("10000"));
		dto1.setNotes("test");
		
		
		dto.setBillRecAndPayImportDtoList(list);
		billLandRecAndPayImportService.saveLandImportPayAndRec(dto, AgencyTestUtil.getCurrentInfo());
	}
}
