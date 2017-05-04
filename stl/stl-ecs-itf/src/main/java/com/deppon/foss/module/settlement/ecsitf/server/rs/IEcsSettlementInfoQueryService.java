package com.deppon.foss.module.settlement.ecsitf.server.rs;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 快递综合查询财务信息接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-22 16:07
 */
public interface IEcsSettlementInfoQueryService {
	
	/**
	 * 快递综合查询运单的财务情况
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-22 16:07
	 */
	@POST
	@Path("/queryFinanceInfo")
	String queryWaybillFinanceInfo(String jsonStr);

}
