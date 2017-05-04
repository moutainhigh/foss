package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.EcsOutWarehouseRequest;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOutWarehouseExceptionService;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsOutWarehouseExceptionService;

public class EcsOutWarehouseExceptionService implements IEcsOutWarehouseExceptionService{

	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";

	@Context
	HttpServletResponse res;

	private IEcsOutWarehouseExceptionService ecsOutWarehouseExceptionService;
	
	//弃货红冲接口
	private IOutWarehouseExceptionService outWarehouseExceptionService;
	
	@Override
	public @ResponseBody String outWarehouseException(@RequestBody String jsonReq) {
		try{
			res.setHeader("ESB-ResultCode" , "1");
			
			if(StringUtils.isBlank(jsonReq)){
				return this.getResponse(FAILURE, "异常出库红冲失败：参数为空!");
			}
			
			EcsOutWarehouseRequest req = JSONObject.parseObject(jsonReq,EcsOutWarehouseRequest.class);
			
			if(req == null){
				return this.getResponse(FAILURE, "异常出库红冲失败：参数为空!");
			}
			
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				return this.getResponse(FAILURE, "异常出库红冲失败：签收人信息为空！");
			}
			
			CurrentInfo cInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(),req.getEmpName(),req.getCurrentDeptCode(),req.getCurrentDeptName());
			
			ecsOutWarehouseExceptionService.outWarehouseException(req.getWaybillNo(),req.getExpType(),cInfo);
			
			return this.getResponse(SUCCESS, "异常出库红冲成功!");
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "异常出库红冲失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "异常出库红冲失败：系统异常，请重新操作，以校验财务单据！："+e.getMessage());
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

	@Transactional
	@Override
	public void outWarehouseException(String waybillNo, String expType,CurrentInfo cInfo) {
		//异常红冲出库
		outWarehouseExceptionService.outWarehouseException(waybillNo, expType, cInfo);
	}

	public void setEcsOutWarehouseExceptionService(
			IEcsOutWarehouseExceptionService ecsOutWarehouseExceptionService) {
		this.ecsOutWarehouseExceptionService = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsOutWarehouseExceptionService",IEcsOutWarehouseExceptionService.class);
	}

	public void setOutWarehouseExceptionService(
			IOutWarehouseExceptionService outWarehouseExceptionService) {
		this.outWarehouseExceptionService = outWarehouseExceptionService;
	}

}
