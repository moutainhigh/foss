/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.smsitf.ws.server.service.impl;

import java.util.List;

import javax.jws.WebService;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.common.api.shared.domain.SendResultInfo;
import com.deppon.foss.module.base.smsitf.ws.server.service.ISMSSendResultNotifincationService;
import com.deppon.foss.module.base.smsitf.ws.server.service.ISendSmsResultService;

/**
 * 获取发送短信结果服务端接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-6-9 上午10:42:43
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-6-9 上午10:42:43
 * @since
 * @version
 */
@WebService(endpointInterface = "com.deppon.foss.module.base.smsitf.ws.server.service.ISendSmsResultService")
public class SendSmsResultService implements ISendSmsResultService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendSmsResultService.class);
	
	private ISMSSendResultNotifincationService sMSSendResultNotifincationService;

	/**
	 * <p>
	 * 短信平台返回结果方法
	 * </p>
	 * @author 094463-foss-xieyantao
	 * @date 2013-6-9 上午10:42:43
	 * @param jsonString
	 * @return
	 * @see com.deppon.foss.module.base.smsitf.esb.server.ISendSmsResult#sendSmsResultInfos(java.lang.String)
	 */
	@Override
	public void sendSmsResultInfos(String jsonArrayString) {
		if (StringUtils.isEmpty(jsonArrayString)) {
			return;
		}
		try {
			LOGGER.debug("Return JsonArrayString=>"+jsonArrayString);
			JSONArray jsonArray= JSONArray.fromObject(jsonArrayString);
			@SuppressWarnings({ "unchecked", "deprecation" })
			List<SendResultInfo> smsReturnList=JSONArray.toList(jsonArray, SendResultInfo.class);
			sMSSendResultNotifincationService.smsSendResult(smsReturnList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("SMS调用FOSS短信WS处理错误");
		}
	}
	
	public ISMSSendResultNotifincationService getsMSSendResultNotifincationService() {
		return sMSSendResultNotifincationService;
	}

	
	public void setsMSSendResultNotifincationService(
			ISMSSendResultNotifincationService sMSSendResultNotifincationService) {
		this.sMSSendResultNotifincationService = sMSSendResultNotifincationService;
	}

	
	 

}
