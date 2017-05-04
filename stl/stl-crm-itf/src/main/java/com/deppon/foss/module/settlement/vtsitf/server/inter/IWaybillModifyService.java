
package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author 339073
 * @date 2016-06-15 19:10:20
 * pkp运单核对后运单状态修改restful接口
 *
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface IWaybillModifyService {
	
	/**
	 * 
	 * @author 339073
	 * @date 2016-06-15 19:10:20
	 */
	@POST
	@Path("/updateWaybillStatus")
	@Consumes({MediaType.APPLICATION_JSON})
	String updateWaybillStatus(String param);
	
}
