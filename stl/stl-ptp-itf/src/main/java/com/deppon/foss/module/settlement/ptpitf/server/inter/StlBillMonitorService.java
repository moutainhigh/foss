package com.deppon.foss.module.settlement.ptpitf.server.inter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * ptp监控结算应收、应付、预收等单据接口
 */
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
public interface StlBillMonitorService {

	/**
	 * 获取结算各单据总记录数和总金额数
	 * @author gpz
	 * @date 2016年8月5日
	 * @param data 传入json数据
	 */
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Path("/monitorSettlementBill")
	public String monitorSettlementBill(String data);
}
