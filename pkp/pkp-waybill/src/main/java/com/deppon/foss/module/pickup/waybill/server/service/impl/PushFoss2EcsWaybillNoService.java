package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoRequest;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillSubmitException;

/**
 * 
 * @ClassName: PushFoss2EcsWaybillNoService 
 * @Description: FOSS将运单号传给ECS处理 
 * @author 351326
 * @date 2016-8-3 上午9:53:58 
 *
 */
public class PushFoss2EcsWaybillNoService implements IPushFoss2EcsWaybillNoService{
	//日志
    private Logger logger = LoggerFactory.getLogger(PushFoss2EcsWaybillNoService.class);
    //FOSS将运单号传给ECS处理的ESB地址
    private String pushFoss2EcsWaybillNoUrl;
    //FOSS推送运单号给ECS记录日志
    @Resource
    private IPushFoss2EcsWaybillNoLogService pushFoss2EcsWaybillNoLogService;
    
    /**
     * FOSS将运单号传给ECS处理
     * @Title: PushFoss2EcsWaybillNo 
     * @author: 351326
     * @time: 2016-8-3下午8:25:35
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param waybillID
     * @param waybillNo
     * @param billCreateTime
     * @param operator
     * @return void    返回类型 
     * @throws
     */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,noRollbackFor=WaybillSubmitException.class)
	public void pushFoss2EcsWaybillNo(
			String waybillID,String waybillNo,Date billCreateTime,String operator) {
		PushFoss2EcsWaybillNoRequest foss2EcsWaybillNoRequest = new PushFoss2EcsWaybillNoRequest();
		foss2EcsWaybillNoRequest.setWaybillNo(waybillNo);
		foss2EcsWaybillNoRequest.setBillCreateTime(billCreateTime);
		String jsonStr = JSON.toJSONString(foss2EcsWaybillNoRequest);
		logger.info("FOSS将运单号传给ECS处理URl:"+pushFoss2EcsWaybillNoUrl+" json:"+jsonStr);
	    HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod(pushFoss2EcsWaybillNoUrl);
	    //设置响应超时时间
	    //method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
	    //设置连接超时时间
	    //client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	    boolean isSuccess = false;
	    String exceptionInfo = "";
	    long responseTime = 0;
	    String responseString = "";
	    try {
			StringRequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
			//设置字符集编码
			method.getParams().setContentCharset("UTF-8");
			method.getParams().setHttpElementCharset("UTF-8");
			method.setRequestEntity(requestEntity);
			long beginTime = System.currentTimeMillis();
			int statuCode = client.executeMethod(method);
			responseTime = System.currentTimeMillis() - beginTime;
			//返回状态如果是200为正常
			if(statuCode==HttpStatus.SC_OK){
				responseString = method.getResponseBodyAsString();
				responseString = responseString.substring(1, responseString.length()-1);
				responseString = responseString.replaceAll("\\\\", "");
				logger.info("FOSS将运单号传给ECS返回JSON:"+responseString);
				
				Map<String, Object> handleResultMap=JSON.parseObject(responseString, HashMap.class);
				isSuccess = (Boolean) handleResultMap.get("isSuccess");
				//pushFoss2EcsWaybillNoResponse = JSONObject.parseObject(responseString,PushFoss2EcsWaybillNoResponse.class); //获取反回类
				if(!isSuccess){
					throw new WaybillSubmitException("同步运单号异常:"+ handleResultMap.get("message"));
				}
				
			}else{
				logger.info("FOSS将运单号传给ECS服务端异常"+statuCode);
				throw new WaybillSubmitException("FOSS将运单号传给ECS服务端异常"+statuCode);
			}
		}catch (Exception e) {
			exceptionInfo = "FOSS将运单号传给ECS服务端异常"+e;
			logger.info("FOSS将运单号传给ECS服务端异常"+e);
			throw new WaybillSubmitException("FOSS将运单号传给ECS接口内部异常"+e);
		}finally{
			if(method != null){
				try {
					method.releaseConnection();
				} catch (Exception e) {
					exceptionInfo = "method.releaseConnection exception"+e;
					logger.info("method.releaseConnection exception"+e);
				}
			}
			//记录接口交易日志
			PushFoss2EcsWaybillNoLogEntity logEntity = new PushFoss2EcsWaybillNoLogEntity();
			//运单ID
			logEntity.setWaybillID(waybillID);
			//运单编号
			logEntity.setWaybillNo(foss2EcsWaybillNoRequest.getWaybillNo());
			//开单时间
			logEntity.setBillCreateTime(foss2EcsWaybillNoRequest.getBillCreateTime());
			//传递内容
			logEntity.setSendMsg(jsonStr);
			//接口返回参数（true运单插入成功，false插入失败）
			logEntity.setSuccessFlag(isSuccess ? "Y" : "N");
			//日志记录时间
			logEntity.setCreateTime(new Date());
			//返回信息
			logEntity.setResponeMsg(responseString);
			//响应时间
			logEntity.setResponseTime(responseTime);
			//异常信息
			logEntity.setExceptionInfo(exceptionInfo);
			//系统调用关系
			logEntity.setSysRelation("pushFoss2EcsWaybillNo");
			//操作人
			logEntity.setOperator(operator);
			pushFoss2EcsWaybillNoLogService.insertWaybillNoLog(logEntity);
		}
	    
	}
	
	public String getPushFoss2EcsWaybillNoUrl() {
		return pushFoss2EcsWaybillNoUrl;
	}
	public void setPushFoss2EcsWaybillNoUrl(String pushFoss2EcsWaybillNoUrl) {
		this.pushFoss2EcsWaybillNoUrl = pushFoss2EcsWaybillNoUrl;
	}
	public IPushFoss2EcsWaybillNoLogService getPushFoss2EcsWaybillNoLogService() {
		return pushFoss2EcsWaybillNoLogService;
	}
	public void setPushFoss2EcsWaybillNoLogService(
			IPushFoss2EcsWaybillNoLogService pushFoss2EcsWaybillNoLogService) {
		this.pushFoss2EcsWaybillNoLogService = pushFoss2EcsWaybillNoLogService;
	}
	
}
