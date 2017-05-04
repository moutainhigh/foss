package com.deppon.foss.module.transfer.common.server.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillReceivableResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcCommonResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcModifyAirChangeResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcTruckConfirmArrivalResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationAirJoinTicketResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcValidationSourceBillNoResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseExternalBillDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseToCubcCallBack;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.PropertiesUtil;

public class FOSSToCUBCService implements IFossToCubcService{
	private static final Logger LOGGER = LogManager.getLogger(FOSSToCUBCService.class);

	/**
	 * 王瑞鹏写的
	 * @param requestStr
	 * @param url
	 * @return
	 */
	private ResponseToCubcCallBack toCubc(String requestStr, String url) {
		ResponseToCubcCallBack responseDto = new ResponseToCubcCallBack();
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);	
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			
			LOGGER.error("cubc返回结果："+responseStr);

			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}

			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}

			responseDto = JSONObject.parseObject(responseStr, ResponseToCubcCallBack.class);
			LOGGER.info("toCubc responseDto " + responseDto);
		} catch (Exception e) {
			throw new TfrBusinessException("CUBC接口异常", e);
		}
		
		return responseDto;
	}

	/**
	 * 修改航空正单信息
	 * 
	 * @param requestStr
	 * @return ResponseAirWaybill result（0失败，1成功）
	 */
	@Override
	public ResponseToCubcCallBack pushUpdateAirWaybill(String requestStr) {
		String code = "ESB_FOSS2ESB_FOSS_AIRWAYBILL_UPDATE";
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		LOGGER.error("修改航空正单信息:"+url);
		return this.toCubc(requestStr, url);
	}

	/**
	 * 修改打木架信息
	 * 
	 * @param requestStr
	 * @return ResponseToCubcCallBack result（0失败，1成功）
	 */
	@Override
	public ResponseToCubcCallBack pushUpdatePacking(String requestStr) {
		String code = "ESB_FOSS2ESB_FOSS_PACKING_UPDATE";
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		LOGGER.error("修改打木架信息参数:"+requestStr);
		LOGGER.error("修改打木架信息:"+url);
		return this.toCubc(requestStr, url);
	}

	/**
	 * 审核/反审核/作废 审核：0，反审核：1，作废：2
	 * 
	 * @param requestStr
	 * @return ResponseToCubcCallBack result（0失败，1成功）
	 */
	@Override
	public ResponseToCubcCallBack pushAuditReverseAuditInvalid(String requestStr) {
		String code = "ESB_FOSS2ESB_FOSS_FORPACKING_AUDIT";
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		LOGGER.error("审核/反审核/作废 审核参数:"+requestStr);
		LOGGER.error("审核/反审核/作废 审核:"+url);
		return this.toCubc(requestStr, url);
	}
		
	/**
	 * @author 365455
	 * 修改合大票清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	public ResponseExternalBillDto pushupdateSaveAirPickupAnd(String requestStr) {
		// esb服务编码待确定
		String code ="ESB_FOSS2ESB_FOSS_AIRPICKUP_UPDATE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod postMethod = new PostMethod(url);
		LOGGER.info("pushupdateSaveAirPickupAnd url = " + url);
		ResponseExternalBillDto responseDto = new ResponseExternalBillDto();
		postMethod.addRequestHeader("Content-type",
				"application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.info("pushupdateSaveAirPickupAnd responseStr=" + responseStr);
			 if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			if (responseStr.contains("exceptionCode")
					&& responseStr.contains("S000099")) {
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr,
					ResponseExternalBillDto.class);
		} catch (Exception e) {
			responseDto.setResult("0");
			responseDto.setReason(e.getMessage());
			throw new TfrBusinessException("CUBC接口异常", e);
		}
		return responseDto;
	}
	
	/**
	 * @author 365455
	 * 删除合大票清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	@Override
	public ResponseExternalBillDto pushdeleteAirPickupAnd(String requestStr) {
		String code = "ESB_FOSS2ESB_FOSS_AIRPICKUP_UPDATE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod postMethod = new PostMethod(url);
		LOGGER.info("pushdeleteAirPickupAnd url = " + url);
		ResponseExternalBillDto responseDto = new ResponseExternalBillDto();
		postMethod.addRequestHeader("Content-type",
				"application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.info("pushdeleteAirPickupAnd responseStr = " + responseStr);
			 if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			if (responseStr.contains("exceptionCode")
					&& responseStr.contains("S000099")) {
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr,
					ResponseExternalBillDto.class);
		} catch (Exception e) {
			responseDto.setResult("0");
			responseDto.setReason(e.getMessage());
			throw new TfrBusinessException("CUBC接口异常", e);
		}
		return responseDto;
	}
	
	/**
	 * @author 365455
	 * 修改中转提货清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	@Override
	public ResponseExternalBillDto pushupdateairTransfer(String requestStr) {
		String code = "ESB_FOSS2ESB_FOSS_AIRTRANSPICKUP_UPDATE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod postMethod = new PostMethod(url);
		LOGGER.info("pushupdateairTransfer url = " + url);
		ResponseExternalBillDto responseDto = new ResponseExternalBillDto();
		postMethod.addRequestHeader("Content-type",
				"application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.info("pushupdateairTransfer responseStr = " + responseStr);
			 if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			if (responseStr.contains("exceptionCode")
					&& responseStr.contains("S000099")) {
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr,
					ResponseExternalBillDto.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			responseDto.setResult("0");
			responseDto.setReason(e.getMessage());
			throw new TfrBusinessException("CUBC接口异常", e);
		}
		return responseDto;
	}
	/**
	 * @author 365455
	 * 删除中转提货清单
	 * @param requestStr
	 * @return ResponseToCubcCallBack
	 * 			result（0失败，1成功）
	 * 调用CUBC接口
	 */
	@Override
	public ResponseExternalBillDto pushdeleteairTransfer(String requestStr) {
		LOGGER.info("pushdeleteairTransfer param = " + requestStr);
		String code = "ESB_FOSS2ESB_FOSS_AIRTRANSPICKUP_DELETE";
		HttpClient httpClient = new HttpClient();
		String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		PostMethod postMethod = new PostMethod(url);
		LOGGER.info("pushdeleteairTransfer url = " + url);
		ResponseExternalBillDto responseDto = new ResponseExternalBillDto();
		postMethod.addRequestHeader("Content-type",
				"application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		try {
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.info("pushdeleteairTransfer responseStr = " + responseStr);
			 if(null == responseStr||"".equals(responseStr)){ 
			    	return null;	
			    }
			if (responseStr.contains("exceptionCode")
					&& responseStr.contains("S000099")) {
				return null;
			}
			responseDto = JSONObject.parseObject(responseStr,
					ResponseExternalBillDto.class);
			
		} catch (Exception e) {
			responseDto.setResult("0");
			responseDto.setReason(e.getMessage());
			throw new TfrBusinessException("CUBC接口异常", e);
		}
		return responseDto;
	}
	

	@Override
	public String querysmallTicketNumFromCUBC(String requestStr) {
		LOGGER.info("querysmallTicketNumFromCUBC param=" + requestStr);
		String code = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossTransferService/checkReceiptIsLegal";
		String responseStr = getResponseStringByReqString(requestStr, code);
		LOGGER.info("querysmallTicketNumFromCUBC result=" + responseStr);
		return responseStr;
	}

	/**
	 * @author 335284
	 * @param requestStr
	 * @param code
	 * @return
	 */
	private String getResponseStringByReqString(String requestStr, String code) {
		String url;
		if (code.startsWith("http")) {
			url = code;
		} else {
			url = PropertiesUtil.getKeyValue("esb.rs") + "/" + code;
		}
		LOGGER.info("getResponseStringByReqString url=" + url+">>>>param=" + requestStr);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("Content-type",	"application/json; charset=utf-8");
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);
		String responseStr;
		try {
			httpClient.executeMethod(postMethod);
			responseStr = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			LOGGER.error("cubc异常", e);
			responseStr = "cubc-" + e.getMessage();
			throw new TfrBusinessException(responseStr);
		}
		LOGGER.info("getResponseStringByReqString========>" + responseStr);
		return responseStr;
	}

	/**
	 * Foss通过esb（或者http）调用cubc接口的通用方法<br>
	 * @since 2017年1月4日
	 * @author 328768
	 */
	@Override
	public CubcCommonResponse fossToCubc(String esbCodeUrl, String msg, Object param) {

		HttpClient httpClient = new HttpClient();
		
		 //设置编码格式
        httpClient.getParams().setContentCharset("UTF-8");
        //设置链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
        //设置读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
        
		String url=null;
		if(esbCodeUrl.startsWith("http")){
			 url =esbCodeUrl;
		}else{
			 url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbCodeUrl;
		}
		
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcCommonResponse cubcCommonResponse = new CubcCommonResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = stringBuffer.toString();
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcCommonResponse = JSONObject.parseObject(responseStr, CubcCommonResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcCommonResponse.setResult("0");
			cubcCommonResponse.setReason(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}

		return cubcCommonResponse;
	}

	@Override
	public String querySmallTicketForWayBillFromCUBC(String requestStr) {
		String code = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossTransferService/querySmallTicCodes";
		String responseStr = getResponseStringByReqString(requestStr, code);
		LOGGER.info("querySmallTicketForWayBillFromCUBC result=" + responseStr);
		return responseStr;
	}
	
	
	/**
	 * @description 根据来源单号调用cubc查询应付单是否已经核销/已付款/已审核
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService#queryBillPayableIsWriteOff(java.lang.Object)
	 * @author 328768-foss-gaojianfu
	 * @update 2017年1月5日 下午5:00:50
	 * @version V1.0
	 */
	public CubcValidationSourceBillNoResponse queryBillPayableIsWriteOff(Object param) { //String esbCode = "";
		String msg = "根据来源单号调用cubc查询应付单是否已经核销/已付款/已审核";
		
		HttpClient httpClient = new HttpClient();

		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		String url = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossValidationVerificationRecService/validationVerificationRec";
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcValidationSourceBillNoResponse cubcValidationVerificationResponse = new CubcValidationSourceBillNoResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity=null;
		try {
			requestEntity = new StringRequestEntity(requestStr, "application/json", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = new String(stringBuffer.toString().getBytes(),"utf-8");
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcValidationVerificationResponse = JSONObject.parseObject(responseStr, CubcValidationSourceBillNoResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcValidationVerificationResponse.setSuccess(false);
			cubcValidationVerificationResponse.setExceptionMsg(e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return cubcValidationVerificationResponse;
	}
	
	/**
	 * @description 验证空运合大票明细
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService#queryBillPayableIsWriteOff(java.lang.Object)
	 * @author 328768-foss-gaojianfu
	 * @update 2017年1月5日 下午5:00:50
	 * @version V1.0
	 */
	public CubcValidationAirJoinTicketResponse validateAirJointTicketDetail(Object param) {
		// String esbCode = "";
		String msg = "验证空运合大票明细";

		HttpClient httpClient = new HttpClient();

		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		// String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbCode;
		String url = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossValidationVerificationService/validationTradeIsVerification";
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcValidationAirJoinTicketResponse cubcValidationVerificationRecResponse = new CubcValidationAirJoinTicketResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = new String(stringBuffer.toString().getBytes(),"utf-8");
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcValidationVerificationRecResponse = JSONObject.parseObject(responseStr, CubcValidationAirJoinTicketResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcValidationVerificationRecResponse.setSuccess(false);
			cubcValidationVerificationRecResponse.setExceptionMsg(e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return cubcValidationVerificationRecResponse;
	}
	
	
	/**
	 * @description 推送车辆到达确认信息到cubc
	 * @return
	 * @version 1.0
	 * @author 328768-foss-gaojianfu
	 * @update 2017年1月6日 下午3:15:40
	 */
	public CubcTruckConfirmArrivalResponse truckConfirm(Object param){
		// String esbCode = "";
		String msg = "推送车辆到达确认信息到cubc";

		HttpClient httpClient = new HttpClient();

		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		// String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbCode;
		String url = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "fossTransferRequestServiceImpl/confirmCarArrive";
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcTruckConfirmArrivalResponse cubcTruckConfirmArrivalResponse = new CubcTruckConfirmArrivalResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity=null;
		try {
			requestEntity = new StringRequestEntity(requestStr, "application/json", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = new String(stringBuffer.toString().getBytes(),"utf-8");
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcTruckConfirmArrivalResponse = JSONObject.parseObject(responseStr, CubcTruckConfirmArrivalResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcTruckConfirmArrivalResponse.setResult(0);;
			cubcTruckConfirmArrivalResponse.setReason(e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return cubcTruckConfirmArrivalResponse;
	}
	
	/**
	* @description 调用cubc接口变更清单
	* @param param
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2017年1月7日 上午10:45:16
	 */
	public CubcModifyAirChangeResponse modifyAirChangeDetail(Object param){
		// String esbCode = "";
		String msg = "调用cubc接口变更清单";

		HttpClient httpClient = new HttpClient();

		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		// String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbCode;
		String url = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "fossChangeAirBillRequestService/changeAirBill";
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcModifyAirChangeResponse cubcModifyAirChangeResponse = new CubcModifyAirChangeResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = new String(stringBuffer.toString().getBytes(),"utf-8");
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcModifyAirChangeResponse = JSONObject.parseObject(responseStr, CubcModifyAirChangeResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcModifyAirChangeResponse.setResult(0);;
			cubcModifyAirChangeResponse.setReason(e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return cubcModifyAirChangeResponse;
	}
	@Override
	public String queryArrivalUnverifyFeeFromCUBC(String requestStr) {
		String code = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossTransferService/queryArriveData";
		String responseStr = getResponseStringByReqString(requestStr, code);
		LOGGER.info("queryArrivalUnverifyFeeFromCUBC result=" + responseStr);
		return responseStr;
	}
	
	@Override
	public String queryLeasedTruckTotalFee(String jsonString) {
		String code = PropertiesUtil.getKeyValue("cubc.service.address.prefix")
				+ "v1/cubc/fossTransferService/queryOutsideCar";
		String responseStr = getResponseStringByReqString(jsonString, code);
		LOGGER.info("queryLeasedTruckTotalFee result=" + responseStr);
		return responseStr;
	}

	/**
	* @description FOSS调用CUBC查询财务单据服务
	* @param param
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2017年1月7日 上午10:45:16
	 */
	@Override
	public CubcBillReceivableResponse queryReceivableAmount(Object param){
		// String esbCode = "";
		String msg = "FOSS调用CUBC查询财务单据服务";

		HttpClient httpClient = new HttpClient();

		// 设置编码格式
		httpClient.getParams().setContentCharset("UTF-8");
		// 设置链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		// String url = PropertiesUtil.getKeyValue("esb.rs") + "/" + esbCode;
		String url = PropertiesUtil.getKeyValue("cubc.service.address.prefix") + "fossSettleSearch/settleSearch";
		LOGGER.error(msg + ":" + url);
		PostMethod postMethod = new PostMethod(url);
		CubcBillReceivableResponse cubcBillReceivableResponse = new CubcBillReceivableResponse();
		postMethod.addRequestHeader("Content-type", "application/json; charset=utf-8");
		String requestStr = JSONObject.toJSONString(param);
		LOGGER.error(msg + ":" + requestStr);
		RequestEntity requestEntity = new StringRequestEntity(requestStr);
		postMethod.setRequestEntity(requestEntity);

		try {

			httpClient.executeMethod(postMethod);

			BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = reader.readLine()) != null) {
				stringBuffer.append(str);
			}
			String responseStr = new String(stringBuffer.toString().getBytes(),"utf-8");
			if (null == responseStr || "".equals(responseStr)) {
				return null;
			}
			LOGGER.error(msg + ":" + responseStr);
			if (responseStr.contains("exceptionCode") && responseStr.contains("S000099")) {
				return null;
			}
			cubcBillReceivableResponse = JSONObject.parseObject(responseStr, CubcBillReceivableResponse.class);
		} catch (Exception e) {
			LOGGER.error(msg + ":" + e.getMessage());
			cubcBillReceivableResponse.setResult("N");
			cubcBillReceivableResponse.setMessage(e.getMessage());
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}

		return cubcBillReceivableResponse;
	}
	
}
