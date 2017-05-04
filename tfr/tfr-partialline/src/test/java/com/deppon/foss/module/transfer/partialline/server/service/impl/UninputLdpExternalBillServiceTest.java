package com.deppon.foss.module.transfer.partialline.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.service.IUninputLdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;
 
public class UninputLdpExternalBillServiceTest extends BaseTestCase{

	@Autowired
	private IUninputLdpExternalBillService uninputLdpExternalBillService;

	@Test
	public void testAddLdpExternalBill(){
		//Map<String, List<String>> waybillNoSerialNoList=new HashMap<String,List<String>>();
		//@218392 NCI项目sonar扫描报错
		List<LdpHandoverBillDetailDto> ldpHandoverBillDetailDtoList = new ArrayList<LdpHandoverBillDetailDto>();
		LdpHandoverBillDetailDto ldpHandoverBillDetailDto = new LdpHandoverBillDetailDto();
		ldpHandoverBillDetailDto.setSerialNo("001");
//		List<String> serialNoList=new ArrayList<String>();
//		serialNoList.add("0001");
//		serialNoList.add("0002");
//		serialNoList.add("0003");
//		waybillNoSerialNoList.put("123456", serialNoList);
//		List<String> waybillNoList = new ArrayList<String>();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		uninputLdpExternalBillService.addLdpExternalBill(ldpHandoverBillDetailDtoList, currentInfo);
	}

	@Test
	public void testQueryUninputLdpExternalBill(){
		LdpHandoverBillDetailDto dto = new LdpHandoverBillDetailDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		uninputLdpExternalBillService.queryUninputLdpExternalBill(dto, 10, 0);
	}
	
	@Test
	public void testQueryUninputLdpExternalBillCount(){
		LdpHandoverBillDetailDto dto = new LdpHandoverBillDetailDto();
		CurrentInfo currentInfo = new CurrentInfo(new UserEntity());
		uninputLdpExternalBillService.queryUninputLdpExternalBillCount(dto);
	}
}