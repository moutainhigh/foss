package com.deppon.foss.module.settlement.common.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;


public class CUBCCommonService {
	
	protected IFossConfigEntityService fossConfigEntityService;
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}
	protected String esbAddress;//直连使用
	public void setEsbAddress(String esbAddress) {
		this.esbAddress = esbAddress;
	}
	protected final static Logger logger;
	protected final static String APPLICATION_JSON;
	protected final static String CODE_FORMAT;
	protected final static String CONTENT_TYPE;
	protected final static String REQUEST_HEADER;
	static{
		logger = LoggerFactory.getLogger(CUBCGrayService.class.getName());
		APPLICATION_JSON = "application/json";
		CODE_FORMAT = "UTF-8";
		CONTENT_TYPE = "Content-Type";
		REQUEST_HEADER = "application/json;charset=UTF-8";
	}
	
}
