package com.deppon.foss.module.settlement.qms.server.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 异常的账单金额计算
 *
 * @author 347069-foss-zhangzhiqiang
 * @date 2016-9-12 上午10:19:22
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IAbnormalBillAmountCalculated {
	/**
	 * 小票单号查询小票金额
	 * 
	 * @param jsonStr
	 * @return 1返回成功 Other返回异常信息
	 */
	@POST
	@Path("/abnormalBillAmountCalculated")
	public String queryReceiptsSumByBill(String jsonStr);
}