package com.deppon.foss.module.settlement.vtsitf.server.inter;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 根据单号查询应付单付款状态接口
 * @author 331556 fanjingwei
 * @date 2016-05-18
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IBillPayableQueryForVTS {
	@POST
	@Path("/billPayableQueryForVTS")
 public String billPayableQueryForVTS(String wayBillNo);
}
