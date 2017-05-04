package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncExpressPartAndSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.esb.SyncExpressPartAndSalesDeptService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.define.FossConstants;

public class SyncExpressPartInfosToCrm {
	
	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;
	private ISyncExpressPartAndSalesDeptService syncExpressPartAndSalesDeptService;

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		syncExpressPartAndSalesDeptService = (ISyncExpressPartAndSalesDeptService) SpringTestHelper
				.get().getBeanByClass(SyncExpressPartAndSalesDeptService.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}


    @Test
    public void testQueryExpressPartSalesDeptBySalesCode() {
    	 List<ExpressPartSalesDeptEntity> list = new ArrayList<ExpressPartSalesDeptEntity>();
    	 ExpressPartSalesDeptEntity entity = new ExpressPartSalesDeptEntity();
//    	 entity.setActive(FossConstants.ACTIVE);
//    	 entity.setId("1dc42e02-9426-4ce2-94e5-fdf90512552b");
//    	 entity.setSalesDeptCode("W16010112");
//    	 entity.setPartCode("W31000304010510");
    	 
    	 entity.setId("84b7a5c3-074a-41c4-b7ef-303f191c25e7");
    	 entity.setSalesDeptCode("W04060901");
    	 entity.setPartCode("W31000304010510");
    	 entity.setActive(FossConstants.ACTIVE);
    	 
//    	 entity.setId("c49e2e30-c1e3-43be-b49c-a0864ea0b98d");
//    	 entity.setSalesDeptCode("W04060104");
//    	 entity.setPartCode("W31000304010510");
//    	 entity.setActive(FossConstants.ACTIVE);
    	 
    	 list.add(entity);
    	 syncExpressPartAndSalesDeptService.syncExpressPartAndDeptToCrm(list);
//	  Assert.assertNull(dto);
    }

}
