package com.deppon.foss.module.base.baseinfo.server.service.impl;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDelayDeductRecordService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DelayDeductRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordCheckRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordCheckResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SyncDelayDeductRecordResponseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DelayDeductRecordQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DelayDeduRecordException;
/**
 * 
 * 延时款记录申请审核服务
 * @author 308861 
 * @date 2016-10-29 下午2:19:34
 * @since
 * @version
 */
public class DelayDeductRecordService implements IDelayDeductRecordService {
	 /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DelayDeductRecordService.class);

	/**
	 * 系统监控，FOSS查询PTP代收延迟扣款记录的esb地址
	 **/
	private String fossToptpRecordAddress;
	
	public String getFossToptpRecordAddress() {
		return fossToptpRecordAddress;
	}
	public void setFossToptpRecordAddress(String fossToptpRecordAddress) {
		this.fossToptpRecordAddress = fossToptpRecordAddress;
	}
	
	/**
	 * 系统监控，FOSS审核PTP代收延迟扣款记录的esb地址
	 */
	private String fossToptpRecordCheckAddress;
	
	public String getFossToptpRecordCheckAddress() {
		return fossToptpRecordCheckAddress;
	}
	public void setFossToptpRecordCheckAddress(String fossToptpRecordCheckAddress) {
		this.fossToptpRecordCheckAddress = fossToptpRecordCheckAddress;
	}
	
	/**
	 * 条件查询代收延时扣款记录
	 */
	@Override
	public SyncDelayDeductRecordResponseEntity queryDelayDeductRecordForPage(DelayDeductRecordQueryDto delayDeductRecordQueryDto) {
		SyncDelayDeductRecordResponseEntity  entity=synDelayDeductRecordToPTP(delayDeductRecordQueryDto);	
		if (entity == null) {
			throw new BusinessException("【条件查询代收延时扣款记录】接口异常，操作失败，请稍后重试[异常信息:拒绝连接]！！！");
		}
		return entity;
	}
	/**
	 * 
	 * 同步【 代收延迟扣款】查询
	 * @author 308861 
	 * @date 2016-10-31 下午3:56:02
	 * @param delayDeductRecordQueryDto
	 * @return
	 * @see
	 */
	private SyncDelayDeductRecordResponseEntity synDelayDeductRecordToPTP(DelayDeductRecordQueryDto delayDeductRecordQueryDto){
		LOGGER.info("同步【代收延迟扣款】到【PTP】开始----------------------------");
		//实例化客户端
		HttpClient httpClient=new HttpClient();
		//设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		//构造PostMethod的实例
		PostMethod postMethod = new PostMethod(fossToptpRecordAddress);
		//设置响应超时时间
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.NUMBER_86400);
	    //设置连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_86400);
		SyncDelayDeductRecordRequestEntity request =null;
		SyncDelayDeductRecordResponseEntity responseEntity = null;
		ObjectMapper jsonObject=null;
		try {
			//封装请求参数
			request=new SyncDelayDeductRecordRequestEntity();
			//申请部门编码
			request.setApplyOrgCode(delayDeductRecordQueryDto.getApplyOrgCode());
			//审核部门编码
			request.setCheckOrgCode(delayDeductRecordQueryDto.getCheckOrgCode());
			//申请开始时间
			request.setApplyStartTime(delayDeductRecordQueryDto.getApplyStartTime());
			//申请开始时间long型
			long start=0;
			if(delayDeductRecordQueryDto.getApplyStartTime() == null){
				start=0;
			}else{
				start = delayDeductRecordQueryDto.getApplyStartTime().getTime();
			}
			request.setApplyStartTimeLong(start);
			//申请结束时间
			request.setApplyEndTime(delayDeductRecordQueryDto.getApplyEndTime());
			//申请结束时间long型
			long end=0;
			if(delayDeductRecordQueryDto.getApplyEndTime() == null){
				end=0;
			}else{
				end = delayDeductRecordQueryDto.getApplyEndTime().getTime();
			}
			request.setApplyEndTimeLong(end);
			//审核状态
			request.setCheckStatus(delayDeductRecordQueryDto.getCheckStatus());
			//运单号
			request.setWaybillNo(delayDeductRecordQueryDto.getWaybillNo());
			//分页---开始
			request.setStart(delayDeductRecordQueryDto.getStart());
			//分页---总条数
			request.setLimit(delayDeductRecordQueryDto.getLimit());
			//转换成JSON
			Object object = JSONObject.fromObject(request);
			String json = object.toString();
			LOGGER.info("同步【代收延迟扣款】to PTP url:" + this.fossToptpRecordAddress);
			LOGGER.info("同步【代收延迟扣款】to PTP json:" + json);
			StringRequestEntity entity = new StringRequestEntity(json,"application/json", "UTF-8");
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			postMethod.setRequestEntity(entity);
			LOGGER.info("请求参数："+
					delayDeductRecordQueryDto.getApplyOrgCode() + "," + 
					delayDeductRecordQueryDto.getApplyStartTime() +  "," + 
					delayDeductRecordQueryDto.getApplyEndTime()+ "," +
					delayDeductRecordQueryDto.getCheckOrgCode()+ "," +
					delayDeductRecordQueryDto.getCheckStatus()+ "," +
					delayDeductRecordQueryDto.getWaybillNo()+ "向PTP推送代收延迟扣款参数,开始................");
			//判断响应状态
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == NumberConstants.NUMBER_200){
				//相应信息
				String jsonResponse = postMethod.getResponseBodyAsString();
				LOGGER.info("同步代收延迟扣款返回JSON:"+jsonResponse);
				//转换
				jsonObject= new ObjectMapper();
				responseEntity=jsonObject.readValue(jsonResponse, SyncDelayDeductRecordResponseEntity.class);
			}else{//如果返回状态码不是200，那么默认为异常
				if(responseEntity==null){
		    		responseEntity = new SyncDelayDeductRecordResponseEntity();
		    	}
				responseEntity.setIsSuccess(MessageConstants.FAILED);
				responseEntity.setExptionMSG("Server Error");
				throw new DelayDeduRecordException("发送PTP代收延迟扣款失败:返回响应码:"+statusCode);
			}
			LOGGER.info("同步【代收延迟扣款】到【PTP】结束----------------------------------");
		} catch (Exception e) {
			if(responseEntity==null){
	    		responseEntity = new SyncDelayDeductRecordResponseEntity();
	    	}
			responseEntity.setIsSuccess(MessageConstants.FAILED);
			responseEntity.setExptionMSG(e.getClass().getSimpleName());
			//如果是服务端PTP异常，那么ESB会返回相应的错误信息，这会导致上面JSONObject.toBean转换异常抛出JSONException
			if(e instanceof JSONException){
				responseEntity.setExptionMSG("Server Error");
			}
			LOGGER.info("同步【代收延迟扣款】到【PTP】系统失败:"+e.getMessage());
		}
		
		
		return responseEntity;
	}
	/**
	 * 
	 * 通过唯一标识id审核代收延迟货款记录
	 * @author 308861 
	 * @date 2016-10-29 下午2:42:04
	 * @param delayDeductRecordEntity
	 * @return 
	 */
	@Override
	public SyncDelayDeductRecordCheckResponseEntity updateCheckStatusById(DelayDeductRecordEntity delayDeductRecordEntity) {
		SyncDelayDeductRecordCheckResponseEntity response=synDelayDeductRecordCheckToPTP(delayDeductRecordEntity);
		if (response == null) {
			throw new BusinessException("【审核代收延时扣款记录】接口异常，操作失败，请稍后重试[异常信息:拒绝连接]！！！");
		}
		return response;
	}
	/**
	 * 
	 * 同步【审核代收延迟货款记录】接口
	 * @author 308861 
	 * @date 2016-11-1 上午11:24:37
	 * @param delayDeductRecordEntity
	 * @return
	 * @see
	 */
	private SyncDelayDeductRecordCheckResponseEntity synDelayDeductRecordCheckToPTP(DelayDeductRecordEntity recordEntity){
		LOGGER.info("同步【审核代收延迟货款记录】到【PTP】开始----------------------------");
		//实例化客户端
		HttpClient httpClient=new HttpClient();
		//设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		//构造PostMethod的实例
		PostMethod postMethod = new PostMethod(fossToptpRecordCheckAddress);
		//设置响应超时时间
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, NumberConstants.NUMBER_86400);
		//设置连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(NumberConstants.NUMBER_86400);
		SyncDelayDeductRecordCheckRequestEntity request=null;
		SyncDelayDeductRecordCheckResponseEntity response=null;
		ObjectMapper jsonObject=null;
		try {
			//封装请求参数
			request=new SyncDelayDeductRecordCheckRequestEntity();
			//id
			request.setId(recordEntity.getId());
			//审核状态
			request.setCheckStatus(recordEntity.getCheckStatus());
			//审核人名称/修改人名称
			request.setCheckPersonName(recordEntity.getCheckPersonName());
			//审核人编码/修改人编码
			request.setCheckPersonCode(recordEntity.getCheckPersonCode());
			//审核时间/修改时间
			request.setCheckTime(recordEntity.getCheckTime());
			//审核意见
			request.setCheckOpinion(recordEntity.getCheckOpinion());
			//审核部门名称
			request.setCheckOrgName(recordEntity.getCheckOrgName());
			//审核部门编码
			request.setCheckOrgCode(recordEntity.getCheckOrgCode());
			//转换成JSON
			Object object = JSONObject.fromObject(request);
			String json = object.toString();
			LOGGER.info("同步【审核代收延迟货款记录】to PTP url:" + this.fossToptpRecordCheckAddress);
			LOGGER.info("同步【审核代收延迟货款记录】to PTP json:" + json);
			StringRequestEntity entity = new StringRequestEntity(json,"application/json", "UTF-8");
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			postMethod.setRequestEntity(entity);
			LOGGER.info(
					recordEntity.getCheckStatus() + "(审核状态)," + 
					recordEntity.getCheckPersonName() +  "(审核人名字)," + 
					recordEntity.getCheckPersonCode()+ "(审核人工号)," +
					recordEntity.getCheckTime()+ "(审核时间)," +
					recordEntity.getCheckOrgName()+ "(审核部门)," +
					recordEntity.getCheckOrgCode()+ "(审核部门编码),向PTP推送审核代收延迟扣款,开始......");
			//判断响应状态
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == NumberConstants.NUMBER_200){
				String jsonResponse = postMethod.getResponseBodyAsString();
				LOGGER.info("同步代收延迟扣款返回JSON:"+jsonResponse);
				//转换
				jsonObject= new ObjectMapper();
				response=jsonObject.readValue(jsonResponse, SyncDelayDeductRecordCheckResponseEntity.class);
			}else{//如果返回状态码不是200，那么默认为异常
				if(response==null){
		    		response = new SyncDelayDeductRecordCheckResponseEntity();
		    	}
				response.setIsSuccess(MessageConstants.FAILED);
				response.setExptionMSG("Server Error");
				throw new DelayDeduRecordException("发送PTP审核代收延迟扣款失败:返回响应码:"+statusCode);
			}
			LOGGER.info("同步【审核代收延迟货款记录】到【PTP】结束----------------------------------");
		} catch (Exception e) {
			if(response==null){
	    		response = new SyncDelayDeductRecordCheckResponseEntity();
	    	}
			response.setIsSuccess("1");
			response.setExptionMSG(e.getClass().getSimpleName());
			//如果是服务端PTP异常，那么ESB会返回相应的错误信息，这会导致上面JSONObject.toBean转换异常抛出JSONException
			if(e instanceof JSONException){
				response.setExptionMSG("Server Error");
			}
			LOGGER.info("同步【审核代收延迟货款记录】到【PTP】系统失败:"+e.getMessage());
			throw new DelayDeduRecordException("审核系统异常");
		}
		return response;
	}
}