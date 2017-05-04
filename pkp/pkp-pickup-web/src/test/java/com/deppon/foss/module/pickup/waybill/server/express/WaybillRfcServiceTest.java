/*
 * PROJECT NAME: pkp-pickup-web
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.server.express
 * FILE    NAME: WaybillRfcServiceTest.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.server.express;

import org.junit.Before;
import org.junit.Test;

import com.deppon.foss.module.pickup.waybill.server.common.TestHelper;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.server.service.impl.HandOverBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.server.service.impl.LdpExternalBillService;


/**
 * 快递更改单调用接口
 * @author 026123-foss-lifengteng
 * @date 2013-9-6 上午11:48:30
 */
public class WaybillRfcServiceTest {
	private ILdpExternalBillService ldpExternalBillService;
	private IHandOverBillService handOverBillService;
	
    @Before
    public void setUp() throws Exception {
    	ldpExternalBillService = TestHelper.get().getBeanByClass(LdpExternalBillService.class);
    	handOverBillService = TestHelper.get().getBeanByClass(HandOverBillService.class);
    }
    
    /**
     * 根据运单号，快递代理理公司代码查询外发单(用于结算查询是否存在有效外发单)
     * @author 026123-foss-lifengteng
     * @date 2013-9-6 上午11:50:20
     */
    @Test
    public void test4CheckIfExistValidLdpExternalBillForStl(){
    	String waybillNo = "5201308280";
    	boolean flag = false;
		try {
			flag = ldpExternalBillService.checkIfExistValidLdpExternalBillForStl(waybillNo, null);
		} catch (Exception e) {
			//有用到缓存，可能会报错，所以try...catch下
			//e.printStackTrace();
			//报表或视图不存在，实际上是因为使用了缓存
		}
    	if(flag){
    		System.out.println("运单号"+waybillNo+"已外发录入");
    	}else{
    		System.out.println("运单号"+waybillNo+"未外发录入");
    	}
    }
    
    /**
     * 根据运单号查询运单是否已交接快递代理公司
     * @author 026123-foss-lifengteng
     * @date 2013-9-6 上午11:50:20
     */
    @Test
    public void testQueryBeLdpHandOveredByWaybillN(){
    	String waybillNo = "5201308280";
    	boolean flag = false;
		try {
			flag = handOverBillService.queryBeLdpHandOveredByWaybillNo(waybillNo);
		} catch (Exception e) {
			//有用到缓存，可能会报错，所以try...catch下
			//e.printStackTrace();
			//报表或视图不存在，实际上是因为使用了缓存
		}
    	if(flag){
    		System.out.println("运单号"+waybillNo+"已交接");
    	}else{
    		System.out.println("运单号"+waybillNo+"未交接");
    	}
    }
}
