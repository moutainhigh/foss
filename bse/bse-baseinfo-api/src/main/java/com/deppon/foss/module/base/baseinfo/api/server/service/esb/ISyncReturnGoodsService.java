package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsResponseEntity;

public interface ISyncReturnGoodsService {
	@POST
	@Path("/addReturnGoods")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	ReturnGoodsResponseEntity addReturnGoods(String returnBill);
	
	
}
