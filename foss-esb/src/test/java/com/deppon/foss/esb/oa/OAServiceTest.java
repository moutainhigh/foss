package com.deppon.foss.esb.oa;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.deppon.foss.esb.interceptor.ClientHeader;
import com.deppon.foss.esb.interceptor.WSHeaderHelper;
import com.deppon.foss.esb.server.SampleDto;


@ContextConfiguration(locations = { 
		"/com/deppon/foss/esb/oa/jaxws-client.xml",
		"/com/deppon/foss/esb/oa/jaxws-server.xml"}
)
public class OAServiceTest extends AbstractJUnit4SpringContextTests {
	public static final String TEST_SERVICE_CODE = "TEST_CODE";
	
	@Autowired
	private IOAService service;
	
	@Test
	public void testESBOaWebservice() throws Exception {
		SampleDto sample = new SampleDto();
		
		sample.setId("id");
		sample.setCode("ID_CODE");
		
		// 通过WSHeaderHelper帮助类设置esb所需要的header信息；
		ClientHeader config = new ClientHeader();
		config.setEsbServiceCode(TEST_SERVICE_CODE);
		config.setBusinessId("order10001234");
		WSHeaderHelper.setClientHeader(config);

		String response = service.process(sample);
		
		Assert.assertEquals("id", response);
	}
}
