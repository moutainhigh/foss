package com.deppon.foss.module.settlement.crmitf.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 生成理赔、服务补救、退运费应付单.
 *
 * @author 231434-foss-bieyexiong
 * @date 2015-12-20 上午10:19:22
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8") 
public interface IGeneratePayableBill {
	
	@POST
	@Path("/claimsPayBillGenerate")
	public String claimsPayBillGenerate(String jsonStr);

}
