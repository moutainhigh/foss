package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPushFoss2EcsWaybillNoLogService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISysWaybillInfoToOmsService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 
 * 推送运单相关信息至OMS的公共方法 实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:272311,date:2016-8-13 上午9:32:14,content:TODO </p>
 * @author 272311-sangwenhao 
 * @date 2016-8-13 上午9:32:14
 * @since
 * @version
 */
public class SysWaybillInfoToOmsService implements ISysWaybillInfoToOmsService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	 //FOSS推送运单号至OMS 记录日志服务
    @Resource
    private IPushFoss2EcsWaybillNoLogService pushFoss2EcsWaybillNoLogService;
	
	//推送信息url路径
	private String pushWaybillNoToOMS_URL ;

	public String getPushWaybillNoToOMS_URL() {
		return pushWaybillNoToOMS_URL;
	}

	public void setPushWaybillNoToOMS_URL(String pushWaybillNoToOMSURL) {
		this.pushWaybillNoToOMS_URL = pushWaybillNoToOMSURL;
	}

	/**
	 * 
	 * <p>推送运单号至OMS</p> 
	 * @author 272311-sangwenhao 
	 * @date 2016-8-13 上午9:32:36
	 * @param waybillNo
	 * @return 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW,noRollbackFor=BusinessException.class)
	@Override
	public boolean pushWaybillNoToOMS(WaybillDto waybillDto) {

		boolean flag = false ;
//		http://192.168.68.127/grayrule-web/gray/noGrayRuleService/noGrayRule.dp
//		入参：{"reqSystemCode":"FOSS","waybillNo":"50000001","belongToSysCode":"FOSS"}
		if(waybillDto.getWaybillEntity()!=null
				&& StringUtils.isNotBlank(waybillDto.getWaybillEntity().getWaybillNo())){
			Map<String, String> map = new HashMap<String, String>();
			map.put("reqSystemCode", WaybillConstants.FOSS);
			map.put("waybillNo", waybillDto.getWaybillEntity().getWaybillNo());
			map.put("belongToSysCode", WaybillConstants.FOSS);
			String mapJson = JSONObject.toJSONString(map);
			logger.info("推送运单号至OMS,请求字符串 json："+mapJson);
			int status = 0 ;
			String response = "" ;
			long begin = System.nanoTime();//毫微秒
			long times = 0 ;
			String exceptionMsg = "" ;
			
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(pushWaybillNoToOMS_URL);
			postMethod.getParams().setContentCharset("UTF-8");
			postMethod.getParams().setHttpElementCharset("UTF-8");

			try {
				
				StringRequestEntity requestEntity = new StringRequestEntity(mapJson,"application/json", "UTF-8");
				postMethod.setRequestEntity(requestEntity);
				status = httpClient.executeMethod(postMethod);
				times = (System.nanoTime()-begin)/(NumberConstants.NUMBER_1000*NumberConstants.NUMBER_1000) ;
//				logger.info("推送运单号至OMS，请求返回状态：" + status);
				if (HttpStatus.SC_OK == status) {
					response = postMethod.getResponseBodyAsString();
					// {"result": "TRUE", "desc": "成功"}
//					logger.info("请求返回内容 response:" + response);
					@SuppressWarnings("unchecked")
					Map<String, Object> resMap = JSONObject.parseObject(response,Map.class);
					if (StringUtils.equalsIgnoreCase(resMap.get("result").toString(), "TRUE")) {
						flag = true;
					}//if end
				}//if end

			}  catch (Exception e) {
				exceptionMsg = "推送运单号至OMS 异常："+e ;
				logger.error("推送运单号至OMS 异常：" + e);
				throw new BusinessException("推送运单号至OMS异常：" + e);
			}finally{
				if(postMethod != null){
					try {
						postMethod.releaseConnection();
					} catch (Exception e) {
						exceptionMsg = "method.releaseConnection exception"+e ;
//						logger.info("method.releaseConnection exception"+e);
					}
				}
				packLogInfo(waybillDto, mapJson, flag, response, times,
						exceptionMsg, status);
			}
		}
		return flag ;
	}

	
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 272311-sangwenhao 
	 * @date 2016-8-13 下午12:00:45
	 */
	private void packLogInfo(WaybillDto waybillDto, String mapJson,
			boolean flag, String response, long times, String exceptionMsg,int status) {
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("waybillDto", waybillDto);
		logMap.put("mapJson", mapJson);
		logMap.put("flag", flag);
		logMap.put("response", response);
		logMap.put("times", times);
		logMap.put("status", status) ;
		logMap.put("exceptionMsg", exceptionMsg);
		addLogInfo(logMap);
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
				if(map.get("waybillDto") instanceof WaybillDto){
					WaybillDto waybillDto = (WaybillDto) map.get("waybillDto");
					//推送日志值foss日志表
					//记录接口交易日志
					PushFoss2EcsWaybillNoLogEntity logEntity = new PushFoss2EcsWaybillNoLogEntity();
					//运单ID
					logEntity.setWaybillID(waybillDto.getWaybillEntity().getId());
					//运单编号
					logEntity.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
					//开单时间
					logEntity.setBillCreateTime(waybillDto.getWaybillEntity().getCreateTime());
					//操作人
					if(waybillDto.getCurrentInfo()!=null){
						logEntity.setOperator(waybillDto.getCurrentInfo().getEmpCode());
					}
					//传递内容
					logEntity.setSendMsg(map.get("mapJson")+"");
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
					logEntity.setSysRelation("pushWaybillNoToOMS");
					logger.info("推送运单号至OMS,插入日志表,推送内容："+JSONObject.toJSONString(logEntity));
					pushFoss2EcsWaybillNoLogService.insertWaybillNoLog(logEntity);
					
				}//if end
			}//if end
		} catch (Exception e) {
			//不作处理
		}
		
	}

}
