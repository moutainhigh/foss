/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/CrmWaybillService.java
 * 
 * FILE NAME        	: CrmWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
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
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm._interface.crmservice.CrmService;
import com.deppon.esb.header.ESBHeader;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.OaQueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailForOfficialResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailRequest;
import com.deppon.esb.inteface.domain.waybillservice.QueryOneYearDetailResponse;
import com.deppon.esb.inteface.domain.waybillservice.WayBillDetail;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.waybillservice.CommonException;
import com.deppon.foss.waybillservice.WaybillService;

/**
 * 
 * CRM WAYBILL SERVICE 
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午6:43:03
 */
public class CrmWaybillService implements WaybillService {
	/**
	 * 日志
	 */
	protected static final Logger LOG = LoggerFactory
			.getLogger(CrmWaybillService.class.getName());
	/**
	 * 版本
	 */
	private static final String VERSION = "0.1";
	/**
	 * 业务id
	 */
	private static final String BUSINESS_ID = "ORDER";
	/**
	 * esb头信息
	 */
	private Holder<ESBHeader> esbHeader;
	/**
	 * 运单管理 service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * crm调用service
	 */
	private CrmOrderService crmService;
	/**
	 * 设置 运单管理 service
	 * @param waybillManagerService
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * 创建远程服务
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @return void
	 * @see
	 */
	protected void createService() {
		if (this.crmService == null) {
			/**
			 * 创建crm service
			 */
			CrmService port = new CrmService();
			//得到crm service
			WaybillService service = (WaybillService) port.getCrmService();
			if (service != null) {
				this.crmService = (CrmOrderService) service;
			}
		}
	}
	


	/**
	 * 创建头文件
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @return void
	 */
	protected void createHeader() {
		if (this.esbHeader == null) {//如果 esbHeader为 null
			esbHeader = new Holder<ESBHeader>();
			ESBHeader header = new ESBHeader();//创建头
			header.setVersion(VERSION);//设置版本
			header.setBusinessId(BUSINESS_ID);//设置业务id
			header.setSourceSystem("FOSS");//设置系统为foss
			header.setTargetSystem("CRM");//设置目标系统为crm
			header.setMessageFormat("SOAP");//消息格式为soap
			header.setExchangePattern(1);//交互模式
			esbHeader.value = header;//最后的值重新给esbheader赋值
		}
	}
	
	/**
	 * queryWaybillInfo
	 * @param waybillNo
	 * @return
	 */
	public List<WayBillDetail> queryWaybillInfo(List<String> waybillNo) {
		return null;
	}



	/**
	 * 接口查询方法
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param esbHeader
	 * @param payload
	 * @throws CommonException
	 * @see com.deppon.foss.waybillservice.WaybillService#queryDetail(javax.xml.ws.Holder, com.deppon.esb.inteface.domain.waybillservice.QueryDetailRequest)
	 */
	@Override
	public QueryDetailResponse queryDetail(Holder<ESBHeader> esbHeader,
			QueryDetailRequest payload) throws CommonException {
		List<String> waybillNoList = payload.getWaybillNo();
		List<CrmWaybillServiceDto> crmServiceDto = waybillManagerService.queryWaybillDetail(waybillNoList);
		return convertResultDto2WaybillDetail(crmServiceDto);
	}



	/**
	 * 转换Dto到接口需要的类型
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param crmServiceDto
	 * @return QueryDetailResponse
	 * @see
	 */
	private QueryDetailResponse convertResultDto2WaybillDetail(List<CrmWaybillServiceDto> crmServiceDto) {
		QueryDetailResponse response = new QueryDetailResponse();
		
		return response;
	}
	
	@Override
	public QueryOneYearDetailResponse queryOneYearDetail(
			Holder<ESBHeader> esbHeader,
			QueryOneYearDetailRequest queryOneYearDetailRequest)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryDetailForOfficialResponse queryDetailForOfficial(
			Holder<ESBHeader> esbHeader,
			QueryDetailForOfficialRequest queryDetailForOfficialRequest)
			throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public OaQueryDetailResponse queryOADetail(Holder<ESBHeader> esbHeader,
			OaQueryDetailRequest oAQueryDetailRequest) throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

}