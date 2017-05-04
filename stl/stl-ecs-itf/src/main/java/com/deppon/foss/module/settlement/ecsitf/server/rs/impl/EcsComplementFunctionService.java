package com.deppon.foss.module.settlement.ecsitf.server.rs.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.ecs.EcsFossComplementRequest;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.ecsitf.server.rs.IEcsComplementFunctionService;

public class EcsComplementFunctionService implements IEcsComplementFunctionService{

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	//成功返回1
	private static final String SUCCESS = "1";

	//失败返回0
	private static final String FAILURE = "0";
	
	@Context
	HttpServletResponse res;
	
	//快递补码接口
	private IEcsComplementFunctionService ecsComplementFunctionService;

	//补码功能财务接口
	private IComplementFunctionService complementFunctionService;
	
	//同步运单信息接口
	private IEcsSendWaybillService ecsSendWaybillService;
	
	private ILogEcsFossService logEcsFossService;
	
	@Override
	public @ResponseBody String complementFunctionWriteBackAndCreateReceivable(@RequestBody String jsonReq) {
		Date date = new Date();
		String response = null, waybillNo = "null";
		//是否同步运单成功
		boolean flag = false;
		try{
			res.setHeader("ESB-ResultCode" , "1");
			EcsFossComplementRequest req = JSONObject.parseObject(jsonReq,EcsFossComplementRequest.class);
			
			if(req == null){
				response = this.getResponse(FAILURE, "结算补码失败：补码参数为空！");
			}
			
			if(StringUtils.isBlank(req.getEmpCode())
					|| StringUtils.isBlank(req.getEmpName())
					|| StringUtils.isBlank(req.getCurrentDeptCode())
					|| StringUtils.isBlank(req.getCurrentDeptName())){
				response = this.getResponse(FAILURE, "结算补码失败：补码员信息为空！");
			}
			waybillNo = req.getWaybillNo();
			//响应为空 说明校验通过
			if (StringUtils.isBlank(response)) {
				SettlementComplementBillDto dto = this.getComplementDto(req);
				CurrentInfo currentInfo = SettlementUtil.getECSCurrentInfo(req.getEmpCode(), req.getEmpName(), req.getCurrentDeptCode(), req.getCurrentDeptName());
				
				ecsComplementFunctionService.complementFunctionWriteBackAndCreateReceivable(dto, currentInfo, req);
				
				flag = true;
				response = this.getResponse(SUCCESS, "结算补码成功！");
			}
		}catch(BusinessException e){
			response = this.getResponse(FAILURE, "结算补码失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			response = this.getResponse(FAILURE, "结算补码失败：系统异常，请重新操作，以校验财务单据！："+e);
		}
		//记录异步接口日志
		try {
			//保存日志
			logEcsFossService.setLog(jsonReq,  response, 
					SettlementDictionaryConstants.FOSS_ESB2FOSS_COMPLEMENT_FUNCTION, waybillNo, flag, date);
		} catch (Exception e) {
			logger.info("结算补码接口插入失败："+e.toString());
		}
		return response;
	}
	
	/**
	 * 校验能否补码
	 * 1、存在多条相同类型应收、应付单，不允许补码
	 * 2、应收、应付已核销金额大于0，不允许补码（始发应收除外）
	 * 3、应收、应付已确认对账单不允许补码（始发应收除外）
	 * 4、应付单已审核，不允许补码
	 * 5、应收单已锁定，不允许补码
	 * 6、现金收款单记账日期大于30天，不允许补码
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-01 14:05
	 */
	@Override
	public @ResponseBody String checkComplementFunction(@RequestBody String jsonReq) {
		res.setHeader("ESB-ResultCode" , "1");
		
		EcsFossComplementRequest req = JSONObject.parseObject(jsonReq,EcsFossComplementRequest.class);
		
		if(req == null){
			return this.getResponse(FAILURE, "结算补码校验不通过：参数为空！");
		}
		
		if(StringUtils.isBlank(req.getWaybillNo())
				|| req.getBillTime()==null
				|| StringUtils.isBlank(req.getReceiveOrgCode())){
			return this.getResponse(FAILURE, "结算补码校验不通过：参数为空！");
		}
		
		SettlementComplementBillDto dto = this.getComplementDto(req);
		try{
			complementFunctionService.checkComplementFunction(dto);	
			return this.getResponse(SUCCESS,"结算补码校验通过！");
		}catch(BusinessException e){
			return this.getResponse(FAILURE, "结算补码校验不通过：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()));
		}catch(Exception e){
			return this.getResponse(FAILURE, "结算补码不通过：系统异常，请重新操作，以校验财务单据！：" + e);
		}
	}

	@Transactional
	@Override
	public void complementFunctionWriteBackAndCreateReceivable(SettlementComplementBillDto dto, CurrentInfo currentInfo,EcsFossComplementRequest req) {
		complementFunctionService.complementFunctionWriteBackAndCreateReceivable(dto, currentInfo);
		//修改运单到达部门信息
		ecsSendWaybillService.updateWaybillByComplement(req);
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
	
	private SettlementComplementBillDto getComplementDto(EcsFossComplementRequest req){
		SettlementComplementBillDto dto = new SettlementComplementBillDto();
		
		//运单号
		dto.setWaybillNo(req.getWaybillNo());
		//最新提货网点的到达部门编码
		dto.setDestOrgCode(req.getDestOrgCode());
		//最新提货网点的到达部门名称
		dto.setDestOrgName(req.getDestOrgName());
		//用于区分自由网点补码，还是快递代理补码 自有网点 Y 快递代理 N
		dto.setIsFreeSite(req.getIsFreeSite());
		//开单时间
		dto.setBillTime(req.getBillTime());
		//收货部门
		dto.setReceiveOrgCode(req.getReceiveOrgCode());
		//来源系统ECS
		dto.setSourceSystem(SettlementDictionaryConstants.SOURCE_SYSTEM_ECS);
		return dto;
	}

	public void setEcsComplementFunctionService(
			IEcsComplementFunctionService ecsComplementFunctionService) {
		this.ecsComplementFunctionService = WebApplicationContextHolder.getWebApplicationContext().
				getBean("ecsComplementFunctionService",IEcsComplementFunctionService.class);
	}

	public void setComplementFunctionService(
			IComplementFunctionService complementFunctionService) {
		this.complementFunctionService = complementFunctionService;
	}

	public void setEcsSendWaybillService(
			IEcsSendWaybillService ecsSendWaybillService) {
		this.ecsSendWaybillService = ecsSendWaybillService;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

}
