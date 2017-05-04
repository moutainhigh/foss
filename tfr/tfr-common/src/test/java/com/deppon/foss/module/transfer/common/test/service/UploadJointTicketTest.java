/*
 * PROJECT NAME: tfr-common
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.test.service
 * FILE    NAME: UpdateVehicleStateTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.test.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBInitializationException;
import com.deppon.esb.inteface.domain.air.SumBillRequest;
import com.deppon.esb.inteface.domain.vehicle.CarRunInfo;
import com.deppon.esb.inteface.domain.vehicle.CarRuncationRequest;

/**
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-12-7 下午6:17:33
 */
public class UploadJointTicketTest {
	
	@Before
	public void init() {
		try {
			ESBJMSAccessor.launch();
		} catch (ESBInitializationException e) {
			e.printStackTrace();
		}
	}
	@Test
 	public void testUpdateVehicleData() throws Exception {
		
		SumBillRequest sumBillRequest = new SumBillRequest();
	    sumBillRequest.setCreatorNumber("046130");
	    sumBillRequest.setSenderName("046130");
	    sumBillRequest.setSendDate(new Date());
	    sumBillRequest.setSubject("046130");
	    sumBillRequest.setMailFolderName("046130");
	    sumBillRequest.setNoticeFlag("1");
	    sumBillRequest.setReadFlag("");
	    sumBillRequest.setMailFlag("1");
	    sumBillRequest.setPriorityLevel("1");
	    
	    // 字节
	/*    int byteCount = 0;
	    InputStream inputStream = uploadJointTicketDto.getInputStream();
	    int temp = 0;
	    while ((temp = inputStream.read()) != -1) {
	    	byteCount += temp;
		}*/
	    sumBillRequest.setMailSize(12345);
	    sumBillRequest.setAttachmentName("046130");
	    sumBillRequest.setAttachmentLink(sumBillRequest.getAttachmentName());
		
		//设置头信息
		//获取头信息
		AccessHeader header = new AccessHeader();
		header.setEsbServiceCode("ESB_FOSS2ESB_EDI_FILEUP_SUMBILL");
		header.setVersion("1.0");
		header.setBusinessId(UUID.randomUUID().toString());
		header.setBusinessDesc1("upload");
		System.err.println("发送开始");
		try {
			//发送请求
			ESBJMSAccessor.asynReqeust(header, sumBillRequest);
			System.err.println("发送完成");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	} 
}
