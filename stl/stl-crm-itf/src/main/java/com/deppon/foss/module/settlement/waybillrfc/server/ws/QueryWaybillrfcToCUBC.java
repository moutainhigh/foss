package com.deppon.foss.module.settlement.waybillrfc.server.ws;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignRfcEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillRfcRequest;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.waybillrfc.server.inter.IQueryWaybillrfcToCUBC;
import com.deppon.foss.util.CollectionUtils;

public class QueryWaybillrfcToCUBC implements IQueryWaybillrfcToCUBC {
	/**
	 * 日志
	 */
	private static final Logger logger = LogManager.getLogger(QueryWaybillrfcToCUBC.class);
	
	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	/**
	 * 注入签收变更SERVICE
	 */
	private ISignChangeService signChangeService;
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}

	@Override
	public Response selectWaybillrfc(String param) {
		logger.info("CUBC请求的参数为："+param);
		WaybillRfcRequest request = JSONObject.parseObject(param, WaybillRfcRequest.class);
		List<String> waybillnos = request.getWaybillNos();
		//响应
		WaybillRfcRequest response = new WaybillRfcRequest();
		//反签收请求参数
		SignRfcEntity entity = new SignRfcEntity();
		if(CollectionUtils.isEmpty(waybillnos)){
			throw new SettlementException("传入单号为空！");
		}
		entity.setWaybillNo(waybillnos.get(0));
		List<String> waybillList = null;
		int count = 0;
		try {
			//查询是否有未处理的更改单
			waybillList = waybillRfcService.isExsitsWayBillRfcs(waybillnos);
			//查询是否有审批中的签收变更单
			count = signChangeService.getWaybillApprovalCountling(entity);
		} catch (Exception e) {
			logger.error("查询更改单或签收变更发生异常！"+e.getMessage());
		}
		response.setWaybillNos(waybillList);
		if(count > 0){
			response.setResultMark("Y");
		}else {
			response.setResultMark("N");
		}
		logger.info("返回给CUBC的响应参数为："+JSONObject.toJSONString(response));
		return  Response.ok(JSONObject.toJSONString(response)).header("ESB-ResultCode", 1).build();
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void setResp(HttpServletResponse resp) {
		this.resp = resp;
	}
	
	
}
