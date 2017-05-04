/**
 * 
 */
package com.deppon.foss.module.transfer.platform.server.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.service.IQueryPDAonLineService;
import com.deppon.foss.module.transfer.platform.api.server.service.IReturnRateService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TransferCenterDto;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

/**
 * @author 105795
 *
 */
public class QueryPDAonLineServiceTest extends BaseTestCase {

	@Autowired IQueryPDAonLineService queryPDAonLineService;
	/**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#calculatePDAonline()}.
	 */
	@Test
	public void testCalculatePDAonline() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryAllTransferCenter()}.
	 */
	@Test
	public void testQueryAllTransferCenter() {
		//fail("Not yet implemented");
		//queryPDAonLineService.queryHqAndTransferCenter();
		
		
	}

	/**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryHqAndTransferCenter()}.
	 */
	@Test
	public void testQueryHqAndTransferCenter() {
		//fail("Not yet implemented");
		
		TransferCenterDto dto=	queryPDAonLineService.queryOrgByOrgCode("W01140402");
		System.out.println(dto.getOrgCode());
		System.out.println(dto.getOrgName());

	}

	/**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryPDAOnlineUsing(com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity)}.
	 *//*
	@Test
	public void testQueryPDAOnlineUsing() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryAllCategoryPDAUsing(com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity)}.
	 *//*
	@Test
	public void testQueryAllCategoryPDAUsingPDAOnlineUsingEntity() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryAllCategoryPDAUsing(com.deppon.foss.module.transfer.platform.api.shared.domain.PDAOnlineUsingEntity, int, int)}.
	 *//*
	@Test
	public void testQueryAllCategoryPDAUsingPDAOnlineUsingEntityIntInt() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryAllCategoryPDAUsingDetail(java.lang.String, java.util.Date, java.util.Date, int, int)}.
	 *//*
	@Test
	public void testQueryAllCategoryPDAUsingDetail() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#queryOrgByOrgCode(java.lang.String)}.
	 *//*
	@Test
	public void testQueryOrgByOrgCode() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.deppon.foss.module.transfer.platform.server.service.impl.QueryPDAonLineService#exportPDAUsingDetail(java.lang.String, java.util.Date, java.util.Date)}.
	 *//*
	@Test
	public void testExportPDAUsingDetail() {
		fail("Not yet implemented");
	}*/

}
