package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.module.pickup.waybill.shared.response.EscWayPDABillResponseEntity;

/**
 * FOSS 提供给悟空查询快递总运费
 * @author Foss-308595-GELL
 *
 */
public interface IEcsPDABillPriceService {
	/**
	 * 悟空PDA计算运费
	 * @param requestJson
	 * @return
	 */
	@POST
    @Path("/queryPDABillPrice")
	//请求数据格式为json
	@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
	//响应数据格式为json
	@Produces("application/json;charset=UTF-8") 
	String queryPDABillPrice(String requestJson);
	//http://localhost:8080/foss/services/ecsPDABillPriceUrl/queryPDABillPrice
}
