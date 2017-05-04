package com.deppon.foss.module.settlement.common.test.service;

import java.util.Date;

import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.settlement.common.test.BaseTestCase;
import com.deppon.foss.util.UUIDUtils;

/**
 * 付款单明细Service 测试类
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午3:38:11
 * @since
 * @version
 */
public class BillPaymentDServiceTest extends BaseTestCase{
	@Autowired
	IBillPaymentDService billPaymentDService;
	
	
	private BillPaymentDEntity getBillPaymentDEntity(){
		BillPaymentDEntity entity=new BillPaymentDEntity();
		entity.setId(UUIDUtils.getUUID());//ID
		entity.setPaymentNo("FK00001");
		Date date=new Date();
		entity.setPaymentAccountDate(date);
		entity.setSourceBillNo("YF001");
		entity.setWaybillNo("90900000");
		entity.setSourceBillType("YF");
		entity.setSourceAccountDate(date);
		entity.setCreateTime(date);
		entity.setSrcSourceBillNo("90900000");
		return entity;
		
		
	}
	
	/**
	 * 付款单明细Service
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:39:48
	 */
	@Test
	public void testAddBillPaymentD(){
		BillPaymentDEntity entity=this.getBillPaymentDEntity();
		this.billPaymentDService.addBillPaymentD(entity);
	}
}
