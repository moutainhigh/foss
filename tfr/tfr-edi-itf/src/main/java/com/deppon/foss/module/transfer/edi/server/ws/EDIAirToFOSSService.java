/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-edi-itf
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/edi/server/ws/EDIAirToFOSSService.java
 *  
 *  FILE NAME          :EDIAirToFOSSService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.edi.server.ws;

import java.util.List;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.airservice.AirService;
import com.deppon.foss.airservice.CommonException;
import com.deppon.foss.esb.edi.server.air.AirArriveCountRequest;
import com.deppon.foss.esb.edi.server.air.AirArriveCountResponse;
import com.deppon.foss.esb.edi.server.air.AirArriveInfo;
import com.deppon.foss.esb.edi.server.air.AirInfo;
import com.deppon.foss.esb.edi.server.air.AirInfoQueryRequest;
import com.deppon.foss.esb.edi.server.air.AirInfoQueryResponse;
import com.deppon.foss.esb.edi.server.air.BatchSignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.BatchSignInfoSendResponse;
import com.deppon.foss.esb.edi.server.air.SignInfoSendRequest;
import com.deppon.foss.esb.edi.server.air.SignInfoSendResponse;
import com.deppon.foss.esb.edi.server.air.StockCountInfo;
import com.deppon.foss.esb.edi.server.air.StockCountRequest;
import com.deppon.foss.esb.edi.server.air.StockCountResponse;
import com.deppon.foss.esb.edi.server.air.WaybillInfoRequest;
import com.deppon.foss.esb.edi.server.air.WaybillInfoResponse;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IUploadingEdiService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirArriveSendInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirStockInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.QueryAirArriveInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.UploadingEdiDto;

/**
 * EDI 与Foss-tfr服务端实现
 * @author 104306-foss-wangLong
 * @date 2012-12-27 上午10:54:44
 */
public class EDIAirToFOSSService implements AirService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EDIAirToFOSSService.class);

	private IUploadingEdiService uploadingEdiService;

	/**
	 * 查询空运到达派送信息录入情况统计
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:17:05
	 * @param payload 参数
	 * @param esbHeader esb头
	 * @see AirService#queryAirArriveCount(AirArriveCountRequest, Holder)
	 * 
	 */
	public AirArriveCountResponse queryAirArriveCount(AirArriveCountRequest payload, Holder<ESBHeader> esbHeader)
			throws CommonException {
		//记录日志
		LOGGER.info("查询空运到达派送信息录入情况统计  传入参数:{}", payload);
		//查询条件dto
		QueryAirArriveInfoDto queryAirArriveInfoDto = new QueryAirArriveInfoDto();
		//赋值查询条件
		BeanUtils.copyProperties(payload, queryAirArriveInfoDto);
		 //查询空运到达派送信息录入情况统计
		List<AirArriveSendInfoDto> list = uploadingEdiService.queryFlightArriveSendInfo(queryAirArriveInfoDto);
		//空运到达统计
		AirArriveCountResponse airArriveCountResponse = new AirArriveCountResponse();
		//如果查询结果为空
		if (CollectionUtils.isEmpty(list)) {
			//返回结果
			return airArriveCountResponse;
		}
		//遍历结果
		for (AirArriveSendInfoDto airArriveSendInfoDto : list) {
			//空运到达信息
			AirArriveInfo airArriveInfo = new AirArriveInfo();
			//属性拷贝
			BeanUtils.copyProperties(airArriveSendInfoDto, airArriveInfo);
			//添加返回的结果
			airArriveCountResponse.getAirArriveInfo().add(airArriveInfo);
		}
		//返回响应信息
		return airArriveCountResponse;
	}

	/**
	 * 查询空运库存信息
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:17:29
	 * @param payload 参数
	 * @param esbHeader esb头
	 * @see AirService#queryStockCount(StockCountRequest, Holder)
	 * @return StockCountResponse
	 */
	public StockCountResponse queryStockCount(StockCountRequest payload, Holder<ESBHeader> esbHeader) 
			throws CommonException {
		//记录日志
		LOGGER.info("edi查询空运库存信息请求开始:");
		//查询条件
		QueryAirArriveInfoDto queryAirArriveInfoDto = new QueryAirArriveInfoDto();
		//代理名称
		queryAirArriveInfoDto.setLadingStationNumber(payload.getLadingStationNumber());
		//出港时间开始
		queryAirArriveInfoDto.setDepartureStartTime(payload.getDepartureStartTime());
		//出港时间结束
		queryAirArriveInfoDto.setDepartureEndTime(payload.getDepartureEndTime());
		//返回结果
		StockCountResponse stockCountResponse = new StockCountResponse();
		//查询库存列表
		List<AirStockInfoDto> list = uploadingEdiService.queryAirStockInfo(queryAirArriveInfoDto);
		//如果为空就返回
		if (CollectionUtils.isEmpty(list)) {
			return stockCountResponse;
		}
		//遍历查询到的结果
		for (AirStockInfoDto airStockInfoDto : list) {
			StockCountInfo stockCountInfo = new StockCountInfo();
			//代理编码
			stockCountInfo.setLadingStationNumber(airStockInfoDto.getLadingStationNumber());
			//代理名称
			stockCountInfo.setLadingStation(airStockInfoDto.getLadingStation());
			//运单号
			stockCountInfo.setWayBillNumber(airStockInfoDto.getWayBillNumber());
			//航班日期
			stockCountInfo.setOutBoundFlight(airStockInfoDto.getOutBoundFlight());
			//出港时间
			stockCountInfo.setDepartureTime(airStockInfoDto.getDepartureTime());
			//加入结果列表
			stockCountResponse.getStockCountInfo().add(stockCountInfo);
		}
		//返回结果
		return stockCountResponse;
	}

	/***
	 * 通过正单号查询 运单
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:16:34
	 * @param payload
	 * @param esbHeader
	 * @return AirInfoQueryResponse
	 */
	public AirInfoQueryResponse queryAirInfo(AirInfoQueryRequest payload, Holder<ESBHeader> esbHeader) 
			throws CommonException {
		//记录日志
		LOGGER.info("edi根据正单号查询运单请求,正单号为:"+payload.getBillNo());
		//查询参数
		AirInfoQueryResponse airInfoQueryResponse = new AirInfoQueryResponse();
		//查询list
		List<UploadingEdiDto> list = null;
		//记录日志
		LOGGER.info("uploadingEdiService#queryWayBillByAirWaybillNo开始查询!");
		//查询结果赋值
		list = uploadingEdiService.queryWayBillByAirWaybillNo(payload.getBillNo());
		//如果查询结果为空
		if (CollectionUtils.isEmpty(list)) {
			return airInfoQueryResponse;
		}
		//遍历查询结果
		for (UploadingEdiDto airArriveInfo : list) {
			//到达信息
			AirInfo airInfo = new AirInfo();
			//属性拷贝
			BeanUtils.copyProperties(airArriveInfo, airInfo);
			//加入到返回结果中
			airInfoQueryResponse.getWaybills().add(airInfo);
		}
		//记录日志
		LOGGER.info("edi根据正单号查询运单请求结束:");
		//返回
		return airInfoQueryResponse;
	}

	/**
	 * 发送签收信息-接送货实现
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:16:34
	 * @param payload
	 * @param esbHeader
	 */
	public SignInfoSendResponse sendSignInfo(SignInfoSendRequest payload,
			Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}
	
	/**
	 * 根据运单查询-接送货实现
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:16:34
	 * @param payload
	 * @param esbHeader
	 */
	public WaybillInfoResponse queryWaybill(WaybillInfoRequest payload,	Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}

	/**
	 * 设置uploadingEdiService
	 * 
	 * @param uploadingEdiService
	 *            the uploadingEdiService to set
	 */
	public void setUploadingEdiService(IUploadingEdiService uploadingEdiService) {
		this.uploadingEdiService = uploadingEdiService;
	}
	/**
	 * 批量签收-接送货实现
	 * @author 104306-foss-wangLong
	 * @date 2012-12-24 上午11:16:34
	 * @param payload
	 * @param esbHeader
	 */
	@Override
	public BatchSignInfoSendResponse batchSignInfo(BatchSignInfoSendRequest payload, Holder<ESBHeader> esbHeader) throws CommonException {
		return null;
	}
}