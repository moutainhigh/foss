package com.deppon.foss.module.settlement.ecsitf.server.rs;

import com.deppon.foss.module.settlement.agency.api.shared.dto.EcsSettlementLdpExternalBillRequestDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 快递 外发单业务调用相关结算接口
 * @author 243921-zhangtingting
 * @date 2016-04-21 上午9:40:24
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface IEcsLdpExternalBillForSettlement {

	/**
	 * 新增外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/addExternalBil")
	public String addExternalBill(String jsonStr);
	
	/**
	 * 修改外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/modifyExternalBill")
	public String modifyExternalBill(String jsonStr);
	
	/**
	 * 审核外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/auditExternalBill")
	public String auditExternalBill(String jsonStr);
	
	/**
	 * 作废外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/disableExternalBill")
	public String disableExternalBill(String jsonStr);
	
	/**
	 * 反审核外发单服务 结算接口
	 * @param jsonStr
	 * @return
	 */
	@POST
	@Path("/reverseAuditExternalBill")
	public String reverseAuditExternalBill(String jsonStr);

	/**
	 * 添加事务控制
	 */

	//新增外发单服务
	public void addExternalBill(EcsSettlementLdpExternalBillRequestDto dto);

	//修改外发单服务
	public void modifyExternalBill(EcsSettlementLdpExternalBillRequestDto dto);

	//审核外发单服务
	public void auditExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list);

	//作废外发单服务
	public void disableExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list);

	//反审核外发单服务
	public void reverseAuditExternalBill(List<EcsSettlementLdpExternalBillRequestDto> list);
}
