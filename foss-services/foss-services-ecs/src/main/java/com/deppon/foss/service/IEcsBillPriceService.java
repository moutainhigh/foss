package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;

/**
 * FOSS 提供给悟空查询快递总运费
 * @author Foss-308595-GELL
 *
 */
public interface IEcsBillPriceService {
	/**
	 * 悟空查询总运费
	 * 2016年5月6日 17:54:35
	 * @param requestJson
	 * @return
	 */
	@POST
    @Path("/queryBillPrice")
	//请求数据格式为json
	@Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN })
	//响应数据格式为json
	@Produces("application/json;charset=UTF-8") 
	EscWayBillResponseEntity queryBillPrice(String requestJson);
	//http://localhost:8080/foss/services/ecsBillPriceUrl/queryBillPrice
	
}
