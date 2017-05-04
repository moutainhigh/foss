package com.deppon.foss.esb.common;

import javax.xml.ws.Holder;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.esb.server.SampleDto;


@ContextConfiguration(locations = { 
		"/com/deppon/foss/esb/common/jaxws-client.xml",
		"/com/deppon/foss/esb/common//jaxws-server.xml"}
)
public class CommonServiceTest extends AbstractJUnit4SpringContextTests {
	public static final String TEST_SERVICE_CODE = "TEST_CODE";
	
	@Autowired
	private ICommonService service;
	
	@Test
	public void testESBCommonWebservice() throws Exception {
		SampleDto sample = new SampleDto();
		
		sample.setId("id");
		sample.setCode("ID_CODE");

		// 实例化ESBHeader，并设置到Holder对象中
		ESBHeader header = new ESBHeader();
		header.setEsbServiceCode(TEST_SERVICE_CODE);
		Holder<ESBHeader> holder = new Holder<ESBHeader>(header);
		
		String response = service.process(sample, holder);
		
		Assert.assertEquals("id", response);
	}
}
