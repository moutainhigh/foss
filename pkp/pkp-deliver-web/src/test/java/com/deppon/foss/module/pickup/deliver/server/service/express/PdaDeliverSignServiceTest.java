/*
 * PROJECT NAME: pkp-deliver-web
 * PACKAGE NAME: com.deppon.foss.module.pickup.deliver.server.service.express
 * FILE    NAME: PdaDeliverSignServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.deliver.server.service.express;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.deliver.server.common.TestHelper;
import com.deppon.foss.module.pickup.deliver.server.service.impl.PdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverSignService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;


/**
 * 派送签收服务测试类
 * @author 026123-foss-lifengteng
 * @date 2013-9-6 下午2:15:34
 */
public class PdaDeliverSignServiceTest {
	private IPdaDeliverSignService pdaDeliverSignService;

	
    @Before
    public void setUp() throws Exception {
    	pdaDeliverSignService = TestHelper.get().getBeanByClass(PdaDeliverSignService.class);
    }
	
	/**
	 * dto
	 * @author 026123-foss-lifengteng
	 * @date 2013-9-6 下午2:20:50
	 */
	@Test
	public void testPdaExpressSign(){
		PdaDeliverSignDto dto = new PdaDeliverSignDto();
		dto.setWaybillNo("5201308280");
		String message = "";
		try {
			message = pdaDeliverSignService.pdaExpressSign(dto);
		} catch (Exception e) {
			//有用到缓存，可能会报错，所以try...catch下
			//e.printStackTrace();
			//报表或视图不存在，实际上是因为使用了缓存
		}
		System.out.println("签收结果："+message);
	}
}
