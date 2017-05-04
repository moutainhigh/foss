package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsWaybillFinanceInfoResponse;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISettlementInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsSettlementInfoQueryService;

/**
 * 快递综合查询财务信息接口
 * @author foss-231434-bieyexiong
 * @date 2016-4-22 16:07
 */
public class EcsSettlementInfoQueryService implements IEcsSettlementInfoQueryService{

	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";

	@Context
	HttpServletResponse res;
	
	private ISettlementInfoQueryService settlementInfoQueryService;

	/**
	 * 快递综合查询运单的财务情况
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-22 16:07
	 */
	@Override
	public @ResponseBody String queryWaybillFinanceInfo(@RequestBody String jsonStr) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			EcsWaybillFinanceInfoResponse finRes = new EcsWaybillFinanceInfoResponse();
			
			JSONObject obj = JSONObject.parseObject(jsonStr);
			
			if(obj == null){
				return this.getResponse(FAILURE, "财务情况查询失败：参数为空");
			}
			
			String waybillNo = obj.getString("waybillNo");
			
			WaybillFinanceInfoDto finDto = settlementInfoQueryService.queryWaybillFinanceInfo(waybillNo);
			
			if(finDto == null){
				return this.getResponse(FAILURE, "财务情况查询失败：该运单无财务信息");
			}
			
			finRes.setWaybillNo(waybillNo);
			finRes.setOrigFeeInfo(finDto.getOrigFeeInfo());
			finRes.setDestFeeInfo(finDto.getDestFeeInfo());
			finRes.setCodFeeInfo(finDto.getCodFeeInfo());
			finRes.setOtherFeeInfos(finDto.getOtherFeeInfos());
			finRes.setInvoiceFeeInfos(finDto.getInvoiceFeeInfos());
			finRes.setResult(SUCCESS);
			
			return JSONObject.toJSONString(finRes);
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "财务情况查询失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "财务情况查询失败：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
		}
	}
	
	/**
	 * 获取响应信息
	 * @author foss-231434-bieyexiong
	 * @date 2016-4-20 17:27
	 */
	private String getResponse(String result,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("result", result);
		map.put("message", message);
		String response = JSONObject.toJSONString(map);
		return response;
	}

	public void setSettlementInfoQueryService(
			ISettlementInfoQueryService settlementInfoQueryService) {
		this.settlementInfoQueryService = settlementInfoQueryService;
	}

}
