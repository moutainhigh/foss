package com.deppon.foss.module.settlement.vtsitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** 
 * foss对接整车运单更改、作废财务规则校验
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-18 下午3:39:19    
 */
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface IValidateRfcAndCancelToVts {

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/validateRfcAndCancel")
	public String validateRfcAndCancel(String validateRfcAndCancelDto);
}
