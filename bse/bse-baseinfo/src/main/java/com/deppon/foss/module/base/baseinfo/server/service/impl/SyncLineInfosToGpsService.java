/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SyncLineInfosToGpsService.java
 * 
 * FILE NAME        	: SyncLineInfosToGpsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import javax.xml.ws.Holder;

import org.example.deppon_gps_service.CommonException;
import org.example.deppon_gps_service.DepponGpsService;
import org.example.deppon_gps_service.domain.QueryTransmitLineRequest;
import org.example.deppon_gps_service.domain.QueryTransmitLineResponse;
import org.jgroups.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.inteface.domain.lineinfo.LineInfo;
import com.deppon.foss.inteface.domain.lineinfo.SyncLineInfoRequest;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncLineInfosToGpsService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LineInfoDto;

/**
 * Foss向GPS传送班线数据信息Web Service客户端服务接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-12-8 下午1:58:33,content:
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-12-8 下午1:58:33
 * @since
 * @version
 */
public class SyncLineInfosToGpsService implements ISyncLineInfosToGpsService {
    
    private DepponGpsService depponGpsService;
    
    /**
     * @return  the depponGpsService
     */
    public DepponGpsService getDepponGpsService() {
        return depponGpsService;
    }
    
    /**
     * @param depponGpsService the depponGpsService to set
     */
    public void setDepponGpsService(DepponGpsService depponGpsService) {
        this.depponGpsService = depponGpsService;
    }

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncLineInfosToGpsService.class);

    /**
     * <p>
     * 向GPS同步线路信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-8 下午1:58:33
     * @param entity
     * @return
     * @throws CommonException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncLineInfosToGpsService#syncLineInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public boolean syncLineInfos(LineInfoDto dto) {
	LOGGER.info("syncLine:send info start.............");

	ESBHeader header = new ESBHeader();
	// 设置服务编码
	header.setEsbServiceCode("ESB_FOSS2ESB_TRANSMIT_LINE");
	header.setMessageFormat("SOAP");
	header.setSourceSystem("FOSS");
	header.setExchangePattern(1);
	header.setVersion("1.0");
	header.setRequestId(UUID.randomUUID().toString());

	Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

	if (null != dto) {
	    //线路ID作为唯一标识
	    header.setBusinessId(dto.getLineId());
	    QueryTransmitLineRequest request = new QueryTransmitLineRequest();
	    //到达站标识
	    request.setArrivalsiteId(dto.getArrivalsiteId());
	    //操作标识
	    request.setIsDeleted(dto.getIsDeleted());
	    //线路标识
	    request.setLineId(dto.getLineId());
	    //线路名称
	    request.setLineName(dto.getLineName());
	    //线路距离(公里)
	    request.setMile(dto.getMile());
	    //卡车时效（千分之小时）
	    request.setRuntime(dto.getRuntime());
	    //始发站标识
	    request.setStartsiteId(dto.getStartsiteId());	

	    try {
		QueryTransmitLineResponse response = depponGpsService.queryTransmitLine(
			request, holder);
		LOGGER.info("syncLine:send info end.............");
		LOGGER.debug("syncLine:result: "+response.isResult());
		return response.isResult();
	    } catch (CommonException e) {
		return false;
	    }
	} else {
	    return false;
	}
    }
    /**
     * <p>
     * 向GPS同步线路信息
     * </p>
     * 
     * @author 130346-foss-lifanghong
     * @param entity
     * @return
     * @throws CommonException
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncLineInfosToGpsService#syncLineInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity)
     */
    @Override
    public void syncLineInfosToGps(LineInfoDto dto) {
	LOGGER.info("syncLine:send info start.............");

	LineInfo  lineInfo = new LineInfo();
	// 设置服务编码
	AccessHeader hea = new AccessHeader();
	hea.setEsbServiceCode("ESB_FOSS2ESB_SYNC_LINEINFO");
	if(null!=dto){
		hea.setBusinessId(dto.getArrivalsiteId());
	}else{
		hea.setBusinessId(UUID.randomUUID().toString());	
	}
	hea.setVersion("1.0");
	hea.setBusinessDesc1("");

	if (null != dto) {
	    SyncLineInfoRequest request = new SyncLineInfoRequest();
	    //线路虚拟编码
	    lineInfo.setLineCode(dto.getLineId());
	    //线路名称
	    lineInfo.setLineName(dto.getLineName());
	    //出发部门
	    lineInfo.setOrigDeptCode(dto.getStartsiteId());
	    //到达部门
	    lineInfo.setDestDeptCode(dto.getArrivalsiteId());
	    //时效
	    if(dto.getRuntime()!=null){
			  lineInfo.setAging(dto.getRuntime());
		}
//	    //时效
//	    lineInfo.setAging(dto.getRuntime());
	    //标准里程数
	    lineInfo.setDistance(dto.getMile());
	    //操作标示
	    lineInfo.setOperationFlag(dto.getIsDeleted());
	 
	    request.getLineInfos().add(lineInfo);
	    try {
			ESBJMSAccessor.asynReqeust(hea, request);
			LOGGER.info("syncLine:send info end.............");
		} catch (ESBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//	    try {
//		QueryTransmitLineResponse response = depponGpsService.queryTransmitLine(
//			request, holder);
//		LOGGER.info("syncLine:send info end.............");
//		LOGGER.debug("syncLine:result: "+response.isResult());
//		return response.isResult();
//	    } catch (CommonException e) {
//		return false;
//	    }
	}
    }
}
