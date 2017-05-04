package com.deppon.foss.module.settlement.ecsitf.server.jms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.ecs.inteface.domain.SyncSendWaybillRequest;
import com.deppon.ecs.inteface.domain.SyncSendWaybillResponse;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.settlement.closing.api.server.service.IEcsSendWaybillService;
import com.deppon.foss.module.settlement.closing.api.server.service.ILogEcsFossService;
import com.deppon.foss.module.settlement.closing.api.shared.dto.WayBillRequest;

/**
 * 同步运单信息异步接口
 * @author 326181
 */
public class EcsSendWaybillProcessor implements IProcess {

	/**
	 * 日志
	 */
	private final Logger logger = LogManager.getLogger(getClass());
	
	/**
	 * 同步运单信息接口
	 */
	private IEcsSendWaybillService ecsSendWaybillService;
	
	private ILogEcsFossService logEcsFossService;
	
	/**
	 * 成功：1
	 */
	private int SUCCESS = 1;
	
	/**
	 * 失败：0
	 */
	private int FAILURE = 0;
	
	@Override
	public Object process(Object req) throws ESBBusinessException {
		logger.warn("==============================调用FOSS_ESB2FOSS_ECS_SYNC_WAYBILL接口 begin=====================================");
		Date date = new Date();
		String response = "", waybillNo = "", resLog = "";
		//是否同步运单成功
		boolean isSuccess = false;
		//请求参数
		SyncSendWaybillRequest request = (SyncSendWaybillRequest) req;
		//响应参数
		SyncSendWaybillResponse res = new SyncSendWaybillResponse();
		WayBillRequest wayBillRequest=null;
		try{
			
			wayBillRequest = JSONObject.parseObject(request.getMessage(), WayBillRequest.class);
			waybillNo = wayBillRequest.getWaybillEntity() != null 
						? wayBillRequest.getWaybillEntity().getWaybillNo() :  wayBillRequest.getWaybillRfcEntity().getWaybillNo();
			ecsSendWaybillService.doSynchroWaybill(wayBillRequest, true);
			//设置返回成功 1
			response = this.getResponseJson(SUCCESS,"生成运单成功",request.getMessage(), waybillNo);
			isSuccess = true;
		}  catch(BusinessException e) {
			response = this.getResponseJson(FAILURE, "同步运单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()), request.getMessage(), waybillNo);
			resLog =  this.getResponseJson(FAILURE, "同步运单失败：" + (e.getErrorCode() != null ? e.getErrorCode() : e.getMessage()), null, null);
		} catch (Exception e){
			//设置返回失败0,声明返回异常信息
			response = this.getResponseJson(FAILURE, "系统异常：" + e, request.getMessage(), waybillNo);
			resLog =  this.getResponseJson(FAILURE, "系统异常：" + e, null, null);
		}
		try{
			logEcsFossService.setLog(request.getMessage(), resLog, "FOSS_ESB2FOSS_ECS_SYNC_WAYBILL", waybillNo, isSuccess, date);
		}catch(Exception e1){
			logger.info("记录同步运单异步接口日志失败："+e1);
		}
		res.setMessage(response);
		logger.warn("==============================调用FOSS_ESB2FOSS_ECS_SYNC_WAYBILL接口 end=====================================");
		return res;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// 错误日志
		logger.error(req.getClass().getName() + "同步运单信息错误！");
		return null;
	}

	public void setEcsSendWaybillService(
			IEcsSendWaybillService ecsSendWaybillService) {
		this.ecsSendWaybillService = ecsSendWaybillService;
	}
	
	/**
	 * 获取返回信息
 	 * @author 231434-FOSS-326181
 	 * @date 2015-7-14 上午10:30:35
 	 */
	public String getResponseJson(int result,String message, String requstMsg, String waybillNo){
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result",result);
		resMap.put("message",message);
		if (waybillNo != null) {
			resMap.put("waybillNo",waybillNo);
		}
		if (requstMsg != null) {
			resMap.put("data",requstMsg);
		}
		//将返回信息转成Json字符串
		String response = JSONObject.toJSONString(resMap);
		return response;
	}

	public void setLogEcsFossService(ILogEcsFossService logEcsFossService) {
		this.logEcsFossService = logEcsFossService;
	}

}
