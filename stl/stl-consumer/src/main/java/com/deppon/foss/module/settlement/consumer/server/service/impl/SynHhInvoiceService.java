package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.consumer.api.server.service.IStatementCreatService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISynHhInvoiceService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.UpdateInvoiceState;
import com.deppon.foss.util.ESBHeaderConstant;

public class SynHhInvoiceService implements ISynHhInvoiceService{
	/**
	 * 日志
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(SynHhInvoiceService.class);

	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;
	
	IStatementCreatService statementCreatService;
	
	@Override
	public String updateHhInvoiceService(String invoiceState) {
		// TODO Auto-generated method stub
		resp.setHeader(ESBHeaderConstant.VERSION, req.getHeader(ESBHeaderConstant.VERSION));
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE, "FOSS_ESB2FOSS_HH_UPDATE_INVOICE");
		resp.setHeader(ESBHeaderConstant.REQUESTID, req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID, req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT, req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE, req.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		UpdateInvoiceState request = new UpdateInvoiceState();
		try {
			JSONObject object = JSONObject.fromObject(invoiceState);
			request = (UpdateInvoiceState) JSONObject.toBean(object, UpdateInvoiceState.class);
		} catch (Exception e) {
			// TODO: handle exception
			request.setErrorReason("数据异常");
			request.setUpdateResult("0");
			resp.setHeader("ESB-ResultCode", "1");
			String responseJson = JSONObject.fromObject(request).toString();
			return responseJson;
		}
		try {
			String[] nos=request.getStatementBillNo().split(",");
			if(nos==null||nos.length==0){
				request.setErrorReason("null");
				request.setUpdateResult("1");
				resp.setHeader("ESB-ResultCode", "1");
				String responseJson = JSONObject.fromObject(request).toString();
				return responseJson;
			}else{
				List<String> numbers=new ArrayList<String>();
				for(String temp:nos){
					numbers.add(temp);
				}
				Map<String,Object> map=new HashMap<String,Object>();
				String updateState=request.getInvoiceStatus();
				//将状态修改
				if("0".equals(updateState)){
					updateState="notAccept";
				}else if("1".equals(updateState)){
					updateState="inIssue";
				}else if("2".equals(updateState)||"4".equals(updateState)){
					updateState="reback";
				}else if("3".equals(updateState)){
					updateState="issued";
				}else if("5".equals(updateState)){
					updateState="notApply";
				}else{
					throw new RuntimeException("对账单状态有问题");
				}
				map.put("invoiceStatus", updateState);
				map.put("list", numbers);
				int updateNumber=statementCreatService.updateInvoiceState(map);
				if(updateNumber==nos.length){
					resp.setHeader("ESB-ResultCode", "1");
					request.setUpdateResult("1");
					request.setErrorReason("");
					String responseJson = JSONObject.fromObject(request).toString();
					return responseJson;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.setErrorReason("修改状态出现异常...");
			request.setUpdateResult("0");
			resp.setHeader("ESB-ResultCode", "1");
			String responseJson = JSONObject.fromObject(request).toString();
			return responseJson;
		}
		return null;
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

	public IStatementCreatService getStatementCreatService() {
		return statementCreatService;
	}

	public void setStatementCreatService(
			IStatementCreatService statementCreatService) {
		this.statementCreatService = statementCreatService;
	}
}
