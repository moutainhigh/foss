package com.deppon.foss.module.settlement.ecsitf.server.rs;


import com.deppon.foss.module.settlement.common.api.shared.dto.EcsLineSignRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EcsPaymentSettlementRequestDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 快递签收业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-04-14 上午11:09:06
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsSignForSettlement {
	
	/**
	 * 查询财务单据信息，获取应收与已收
	 * @param jsonStr
	 */
	@POST
	@Path("/queryReceivableAmount")
	public String queryReceivableAmount(String jsonStr);
	
	/**
	 * 结清货款，确认付款 实收货款 / 到付运单结转临欠月结
	 * @param jsonStr
	 */
	@POST
	@Path("/confirmToPayment")
	public String confirmToPayment(String jsonStr);
	
	/**
	 * 签收  确认收入 调用结算出库接口
	 * @param jsonStr
	 */
	@POST
	@Path("/confirmTaking")
	public String confirmTaking(String jsonStr);
	
	/**
	 * 反签收 财务出库 调用结算接口
	 * @param jsonStr
	 */
	@POST
	@Path("/reverseConfirmTaking")
	public String reverseConfirmTaking(String jsonStr);
	
	/**
	 * 反结清 反到付运费结转临欠/月结  反确认付款
	 * @param jsonStr
	 * @param jsonStr
	 */
	@POST
	@Path("/reversConfirmToPayment")
	public String reversConfirmToPayment(String jsonStr);

	/**
	 * 添加事务控制
	 */
	//结清货款
	public void confirmToPayment(EcsPaymentSettlementRequestDto dto);

	//签收确认
	public void confirmTaking(EcsLineSignRequestDto dto);

	//反签收确认
	public void reverseConfirmTaking(EcsLineSignRequestDto dto);

	//反结清
	public void reversConfirmToPayment(EcsPaymentSettlementRequestDto dto);
}
