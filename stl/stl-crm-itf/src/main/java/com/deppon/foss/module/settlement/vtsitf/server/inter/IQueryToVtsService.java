package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** 
 * foss对接vts根据运单号查询财务单据信息itf接口
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-10  
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface IQueryToVtsService {

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/queryPaymentByWaybillNo")
	public String queryPaymentByWaybillNo(String waybillNo);
}
