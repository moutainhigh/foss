package com.deppon.foss.module;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//import com.deppon.foss.module.base.querying.server.dao.INewbirdinfoDao;
import com.deppon.foss.module.base.querying.server.service.IIntegrativeQueryService;
import com.deppon.foss.module.base.querying.server.service.ISpecialValueAddedService;
import com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService;

public class Test2 extends BaseTestCase{
//	@Autowired
//	private INewbirdinfoDao newbirdinfoDao;
	@Autowired
	private ISpecialValueAddedService specialValueAddedService;
	@Test
	public void TTE(){
//		System.out.println(newbirdinfoDao.toString());
		System.out.println(specialValueAddedService.queryDeliveryInfo("6007003009"));
}

}
