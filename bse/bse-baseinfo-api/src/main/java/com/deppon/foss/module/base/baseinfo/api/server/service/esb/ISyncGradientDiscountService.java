package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGradientDiscountInfoEntity;

/**
 * 零担梯度折扣需求 服务端
 * @author 218392
 * @date 2015-06-03 15:13:20
 *
 */
public interface ISyncGradientDiscountService {
	@POST
	@Path("/addGradientDiscount")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	ReturnGradientDiscountInfoEntity addGradientDiscount(String gradientDiscount);
}
