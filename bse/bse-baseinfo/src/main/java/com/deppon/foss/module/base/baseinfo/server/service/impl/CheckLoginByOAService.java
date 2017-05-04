package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.deppon.dip.remote.dkeytms.checkloginservice.header.ESBHeader;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICheckLoginByOAService;

import dip.integrateorg.datasync.isysbbsservice.CheckLoginService;

public class CheckLoginByOAService implements ICheckLoginByOAService {
	
	private CheckLoginService checkLoginService;

	  /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoginByOAService.class);
    
	@Override
	public boolean checkLoginByOA(String empCode, String passWord) {
		LOGGER.info("同步OA工号和密码:send info start.............");
		
//		ESB_DKEYTMS2ESB_CHECK_LOGIN_SERVICE
//		DIP_ESB2DIP_CHECK_LOGIN_SERVICE
//http://192.168.67.12:8580/esb2/ws/foss2dip/retrievePasswordService?wsdl
		//ESB_FOSS2ESB_RETRIEVE_PASSWORD

			ESBHeader header = new ESBHeader();
			// 设置服务编码
			header.setEsbServiceCode("ESB_FOSS2ESB_RETRIEVE_PASSWORD");
			header.setMessageFormat("SOAP");
			header.setSourceSystem("FOSS");
			header.setExchangePattern(1);
			header.setVersion("1.0");
			header.setBusinessId(empCode);
			header.setRequestId(UUID.randomUUID().toString());
		
			JSONObject jsonObject = new JSONObject();      
	        jsonObject.put("jobNumber", empCode);
	        jsonObject.put("password", passWord); 
	        String requestStr = jsonObject.toString();
			String resultJson = checkLoginService.checkLoginService(requestStr, header);
			
		    JSONObject obj = JSONObject.parseObject(resultJson);
			String result = obj.getString("result");
			String bank = obj.getString("bank");
			boolean flag = false;
			if(!StringUtils.isEmpty(bank)){
				flag = true;
			}
			
			LOGGER.info("同步OA工号和密码:send info end.............");
			LOGGER.debug("校验结果: "+ result);
			return flag;
			   
	}

	public CheckLoginService getCheckLoginService() {
		return checkLoginService;
	}

	public void setCheckLoginService(CheckLoginService checkLoginService) {
		this.checkLoginService = checkLoginService;
	}

}
