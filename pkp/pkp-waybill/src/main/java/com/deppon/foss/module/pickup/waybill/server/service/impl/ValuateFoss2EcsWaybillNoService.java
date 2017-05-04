package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IValuateFoss2EcsWaybillNoService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.vo.ValidateESCWaybillNoVo;

public class ValuateFoss2EcsWaybillNoService implements
		IValuateFoss2EcsWaybillNoService {
	//日志
    private Logger logger = LoggerFactory.getLogger(ValuateFoss2EcsWaybillNoService.class);
	//FOSS将运单号传给ECS处理的ESB地址
    private String valuateFoss2EcsWaybillNoUrl;
    //FOSS推送运单号给ECS记录日志
    @Resource
    private IPushFoss2EcsWaybillNoLogService pushFoss2EcsWaybillNoLogService;
    
    /**
     * 
     * @Title: valuateFoss2EcsWaybillNo 
     * @author: 351326
     * @time: 2016-8-3下午4:42:39
     * @Description: 运单号校验查询
     * @param waybillNo
     * @param operator 操作人
     * @return 存在“true”  不存在“false”
     * @throws
     */
	@Override
	public boolean valuateFoss2EcsWaybillNo(
			String waybillNo,String operator) {
		String jsonStr = JSON.toJSONString(waybillNo);
		logger.info("FOSS将运单号传给ECS校验是否存在URl:"+valuateFoss2EcsWaybillNoUrl+" json:"+jsonStr);
	    HttpClient client = new HttpClient();
	    PostMethod method = new PostMethod(valuateFoss2EcsWaybillNoUrl);
	    //设置响应超时时间100ms,超时不影响开单
	    //method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 100);
	    //设置连接超时时间100ms,超时不影响开单
	    //client.getHttpConnectionManager().getParams().setConnectionTimeout(100);
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
				isSuccess = JSONObject.parseObject(responseString,Boolean.class);
				logger.info("FOSS将运单号传给ECS返回JSON:"+responseString);
			}else{
				//异常时不影响开单，不抛出异常
				logger.info("FOSS将运单号传给ECS校验是否存在服务端异常"+statuCode);
			}
		}catch(SocketTimeoutException e){
			exceptionInfo = "FOSS将运单号传给ECS校验是否存在请求响应超时"+e;
			logger.info("FOSS将运单号传给ECS校验是否存在请求响应超时"+e);
		}catch(ConnectTimeoutException e){
			exceptionInfo = "FOSS将运单号传给ECS校验是否存在请求连接超时"+e;
			logger.info("FOSS将运单号传给ECS校验是否存在请求连接超时"+e);
		}catch (Exception e) {
			exceptionInfo = "FOSS将运单号传给ECS校验是否存在服务端异常"+e;
			//异常时不影响开单，不抛出异常
			logger.info("FOSS将运单号传给ECS校验是否存在服务端异常"+e);
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
			//logEntity.setWaybillID(waybillID);
			//运单编号
			logEntity.setWaybillNo(waybillNo);
			//开单时间
			//logEntity.setBillCreateTime(foss2EcsWaybillNoRequest.getBillCreateTime());
			//传递内容
			logEntity.setSendMsg(jsonStr);
			//接口返回参数异常信息为空表示成功，插入Y，否则N
			logEntity.setSuccessFlag(StringUtils.isEmpty(exceptionInfo) ? "Y" : "N");
			//日志记录时间
			logEntity.setCreateTime(new Date());
			//返回信息
			logEntity.setResponeMsg(responseString);
			//响应时间
			logEntity.setResponseTime(responseTime);
			//异常信息
			logEntity.setExceptionInfo(exceptionInfo);
			//系统调用关系
			logEntity.setSysRelation("valuateFoss2EcsWaybillNo");
			//操作人
			logEntity.setOperator(operator);
			pushFoss2EcsWaybillNoLogService.insertWaybillNoLog(logEntity);
		}
		return isSuccess;
	}
	public String getValuateFoss2EcsWaybillNoUrl() {
		return valuateFoss2EcsWaybillNoUrl;
	}
	public void setValuateFoss2EcsWaybillNoUrl(String valuateFoss2EcsWaybillNoUrl) {
		this.valuateFoss2EcsWaybillNoUrl = valuateFoss2EcsWaybillNoUrl;
	}

	//FOSS校验悟空快递单号地址
	private String valuateWaybillNoURL ; 
	
	public String getValuateWaybillNoURL() {
		return valuateWaybillNoURL;
	}
	
	public void setValuateWaybillNoURL(String valuateWaybillNoURL) {
		this.valuateWaybillNoURL = valuateWaybillNoURL;
	}
		
	/**
	 * FOSS校验悟空快递单号地址
	 * @param map
	 * @return true:校验正常 可以开单，false：校验异常，不可开单
	 * @author 272311- sangwenhao
	 */
	@Override
	public boolean validateWaybillNoIsCorrect(Map<String, String> map) {
		boolean flag = true;
		ValidateESCWaybillNoVo validateESCWaybillNoVo = new ValidateESCWaybillNoVo();
		validateESCWaybillNoVo.setMap(map);
		validateESCWaybillNoVo.setSystemName(WaybillConstants.FOSS);
		// {"map":{"networkNo":"","empCode":"","wayBillno":"123456"},"systemName":"FOSS"}
		String json = JSONObject.toJSONString(validateESCWaybillNoVo);

		logger.info("校验快递运单号，请求字符串 json：" + json);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(valuateWaybillNoURL);
		postMethod.getParams().setContentCharset("UTF-8");
		postMethod.getParams().setHttpElementCharset("UTF-8");
		int status = 0 ;
		String response = "" ;
		long times = System.nanoTime();//毫微秒
		String exceptionMsg = "" ;
		Map<String, Object> logMap = new HashMap<String, Object>();
		try {
			
			StringRequestEntity requestEntity = new StringRequestEntity(json,"application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			status = httpClient.executeMethod(postMethod);
			times = (System.nanoTime()-times)/(NumberConstants.NUMERAL_1000*NumberConstants.NUMERAL_1000) ;
//			logger.info("校验快递运单号，请求返回状态：" + status+" ; 耗时："+times);
			if (HttpStatus.SC_OK == status) {
				response = postMethod.getResponseBodyAsString();
				// {"errMsg":"","shuntSystem":"FOSS"}
//				logger.info("请求返回内容 response:" + response);
				@SuppressWarnings("unchecked")
				Map<String, Object> resMap = JSONObject.parseObject(response,Map.class);
				if (!StringUtils.equals(resMap.get("shuntSystem").toString(), WaybillConstants.FOSS)) {
					flag = false;
				}//if end
			}

		} catch (SocketTimeoutException e){
			exceptionMsg = "校验快递运单号规则，请求响应超时 "+e ;
			logger.info("校验快递运单号规则，请求响应超时"+e);
		} catch (ConnectTimeoutException e){
			exceptionMsg = "校验快递运单号规则，请求连接超时"+e ;
			logger.info("校验快递运单号规则，请求连接超时"+e);
		} catch (Exception e) {
			exceptionMsg = "校验快递运单号异常："+e ;
			logger.error("校验快递运单号异常：" + e);
			throw new BusinessException("校验快递运单号异常：" + e);
		}finally{
			if(postMethod != null){
				try {
					postMethod.releaseConnection();
				} catch (Exception e) {
					exceptionMsg = "method.releaseConnection exception:"+e ;
					logger.info("method.releaseConnection exception"+e);
				}
			}
			logMap.put("requestJson", json);
			logMap.put("waybillNo", map.get("wayBillno"));
			logMap.put("flag", flag);
			logMap.put("response", response);
			logMap.put("times", times);
			logMap.put("exceptionMsg", exceptionMsg);
			logMap.put("status", status);
			addLogInfo(logMap);
		}
		return flag;
	}
	
	/**
	 * <p>推送日志信息，可公用，map中的内容自行添加，pkp.t_srv_push_ecs_waybillno_log</p> 
	 * @param map 封装的日志信息
	 * @author 272311-sangwenhao 
	 * @date 2016-8-13 上午11:59:05
	 * @param map
	 */
	private void addLogInfo(Map<String,Object> map) {
		try {
			if(MapUtils.isNotEmpty(map)){
				//推送日志值foss日志表
				//记录接口交易日志
				PushFoss2EcsWaybillNoLogEntity logEntity = new PushFoss2EcsWaybillNoLogEntity();
				//运单编号
				logEntity.setWaybillNo(map.get("waybillNo")+"");
				//传递内容
				logEntity.setSendMsg(map.get("requestJson")+"");
				//接口返回参数异常信息为空表示成功，插入Y，否则N
				logEntity.setSuccessFlag(StringUtils.isEmpty(map.get("exceptionMsg")+"") ? "Y" : "N");
				//日志记录时间
				logEntity.setCreateTime(new Date());
				//返回信息
				logEntity.setResponeMsg(map.get("response")+"");
				//响应时间
				logEntity.setResponseTime((Long) map.get("times"));
				//返回状态
				logEntity.setColumn1(map.get("status")+"");
				//异常信息
				logEntity.setExceptionInfo(map.get("exceptionMsg")+"");
				//系统调用关系
				logEntity.setSysRelation("validateWaybillNoIsCorrect");
				logger.info("校验快递运单号规则,插入日志表,推送内容："+JSONObject.toJSONString(logEntity));
				pushFoss2EcsWaybillNoLogService.insertWaybillNoLog(logEntity);
				
			}//if end
		} catch (Exception e) {
			//
		}
		
	}

}
