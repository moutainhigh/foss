/**
 *  initial comments.
 */
/*******************************************************************************
 * 
 * 
 * 
 * 
 * 
 * 
 * 
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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/server/service/impl/UploadingEdiService.java
 *  
 *  FILE NAME          :UploadingEdiService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  SR-1	打开界面：
 *  
1、	航空公司默认为CZ，信息来源航空公司二字代码基础信息；
2、	航班号默认为空；
3、	如果当前部门为空运总调，则空运总调为当前部门，根据用户数据权限可做修改，如果当前部门为非空运总调，则空运总调默认为当前部门对应的空运总调，可修改，不能为空；
4、	目的站默认为空，输入限制为空运线路信息中的城市名称；
5、	航班日期默认为当天，可修改，不能为空；
6、	跟踪状态默认为待跟踪；
7、	正单号默认为空；
8、	默认不执行查询操作；

SR-2	起飞跟踪、过程跟踪和到达跟踪按钮只有当前操作人为空运操作员才可使用； 

SR-3	空运操作员记录跟踪信息时，起飞跟踪和到达跟踪的实飞时间和实到时间不能为空；

SR-4	1、	当前状态为继续跟踪时，不能做起飞跟踪，当前状态为已完成跟踪，不能再做其他跟踪；
2、	当前状态为"待跟踪"时，只能起飞跟踪，不能做过程跟踪与到达跟踪

SR-5	可以批量跟踪航班信息；

SR-6	保存跟踪信息时：
1、	同时将当前操作人姓名和操作时间记录在跟踪信息中，已“姓名+时间+跟踪记录”形式保存，保存时追加在上次跟踪信息后；
2、	起飞跟踪或到达跟踪的跟踪信息为空时，后台默认跟踪信息为起飞跟踪或到达跟踪；
3、	过程跟踪的跟踪信息不能为空；

SR-7	当鼠标移动至显示列表中的跟踪信息记录时，界面弹出跟踪信息，跟踪信息以分行的形式显示每次跟踪信息；

 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossBillReceivableResponseDO;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IUploadingEdiService;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirArriveSendInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirStockInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.QueryAirArriveInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.UploadingEdiDto;
import com.deppon.foss.module.transfer.common.api.cubcgray.model.VestResponse;
import com.deppon.foss.module.transfer.common.api.server.service.IFossToCubcService;
import com.deppon.foss.module.transfer.common.api.shared.define.CUBCGrayContants;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcBillReceivableResponse;
import com.deppon.foss.module.transfer.common.api.shared.dto.GrayParameterDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.common.server.utils.CUBCGrayUtil;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * edi实现类 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-26 下午1:34:04
 */
public class UploadingEdiService implements IUploadingEdiService {
	
	
	/**
	 * 调用cubc常用同步接口
	 */
	private IFossToCubcService fossToCubcService;
	
	public void setFossToCubcService(IFossToCubcService fossToCubcService) {
		this.fossToCubcService = fossToCubcService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadingEdiService.class);
	/**
	 * 查询航空正单dao
	 */
	private IAirWaybillDao airWayBillDao;
	
	/**
	 * 结算应收已收服务
	 */
	private IBillReceivableService billReceivableService;
	
	private CUBCGrayUtil cubcUtil;
	
	public void setCubcUtil(CUBCGrayUtil cubcUtil) {
		this.cubcUtil = cubcUtil;
	}


	/**
	 * 根据正单号查询运单list
	 * @param  airWaybillNo 正单号
	 * @return List<UploadingEdiDto> 返回list<dto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午1:45:31
	 */
	@Override
	public List<UploadingEdiDto> queryWayBillByAirWaybillNo(String airWaybillNo){
		//根据正单号查询运单list
		List<UploadingEdiDto> dtoList = airWayBillDao.queryWayBillByAirWaybillNo(airWaybillNo);
		//返回结果列表
		List<UploadingEdiDto> resultDdiDtoList = new ArrayList<UploadingEdiDto>();
		//如果查询结果不为空
		if(!CollectionUtils.isEmpty(dtoList)){
			//遍历查询结果
			for (UploadingEdiDto dto : dtoList) {
				if (dto != null) {
					if (!ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dto.getProductCode())) {
						LOGGER.error("--该运单的运输性质不为精准空运，不能签收！");
						//退出本次循环，不加入返回结果
						continue;
					}
					LOGGER.info("--运单号" + dto.getWayBillNo());
					LOGGER.info("--调用结算接口开始 ：" + ReflectionToStringBuilder.toString(dto));
					BillReceivableConditionDto billReceiveable = new BillReceivableConditionDto(dto.getWayBillNo());
					// 财务单据查询，灰度改造 328768 ---------------------------start
					List<BillReceivableEntity> billReceivableEntities = null;

					// 封装灰度实体，类型是运单
					GrayParameterDto parDto = new GrayParameterDto();
					parDto.setSourceBillType(CUBCGrayUtil.SBType.W.getName());
					parDto.setSourceBillNos(new String[] { dto.getWayBillNo() });
					VestResponse vestResponse = cubcUtil.getUcbcGrayData(parDto, new Throwable());

					if (CUBCGrayContants.SYSTEM_CODE_FOSS
							.equals(vestResponse.getVestBatchResult().get(0).getVestSystemCode())) {
						try {
							billReceivableEntities = billReceivableService
									.queryReceivableAmountByCondition(billReceiveable);
						} catch (Exception e) {
							LOGGER.error("调用FOSS结清查询财务单据异常信息为：" + e.getMessage());
						}
					} else {
						try {
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("sourceNo", dto.getWayBillNo());
							CubcBillReceivableResponse cubcBillReceivableResponse = fossToCubcService
									.queryReceivableAmount(paramMap);
							if (null != cubcBillReceivableResponse) {
								if (FossConstants.YES.equals(cubcBillReceivableResponse.getResult())) {
									billReceivableEntities = JSONObject
											.parseObject(JSONObject.toJSONString(cubcBillReceivableResponse),
													FossBillReceivableResponseDO.class)
											.getList();
								} else {
									throw new TfrBusinessException(
											"FOSS调用CUBC查询财务单据服务失败:cubc" + cubcBillReceivableResponse.getMessage());
								}
							} else {
								throw new TfrBusinessException("FOSS调用CUBC查询财务单据服务失败");
							}
						} catch (Exception e) {
							LOGGER.error("调用FOSS结清查询财务单据异常信息为：" + e.getMessage());
						}
					}
					// 财务单据查询，灰度改造 328768 ---------------------------end
					//实收到付款
					BigDecimal realArriveMoney = BigDecimal.valueOf(0);
					if(!CollectionUtils.isEmpty(billReceivableEntities)){
						for (BillReceivableEntity billReceivableEntity : billReceivableEntities){
							// 空运到达应收单
							if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(billReceivableEntity.getBillType())){
								// 实收到付运费
								dto.setArriveCharge(billReceivableEntity.getVerifyAmount());
								//加入到付总额中
								realArriveMoney = realArriveMoney.add(billReceivableEntity.getVerifyAmount());
							} // 代收货款应收单
							else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(billReceivableEntity.getBillType())){
								// 实收代收货款
								dto.setRealRefund(billReceivableEntity.getVerifyAmount());
								//加入到付总额中
								realArriveMoney = realArriveMoney.add(billReceivableEntity.getVerifyAmount());
							}
						}			
					}
					//实收到付款
					dto.setRealArriveMoney(realArriveMoney);
					LOGGER.info("--调用结算接口结束, 实收到付款：" + realArriveMoney);
				}
				resultDdiDtoList.add(dto);
			} 
		}
		return resultDdiDtoList;
	}


	/**
	 * 查询空运到达派送信息录入情况统计
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-26 下午2:44:53
	 */
	@Override
	public List<AirArriveSendInfoDto> queryFlightArriveSendInfo(
			QueryAirArriveInfoDto queryAirArriveInfoDto) {
		//如果代理网点为all
		if(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME.equals(queryAirArriveInfoDto.getLadingStationNumber())){
			queryAirArriveInfoDto.setLadingStationNumber("");
		}
		//edi传过来的日期是当天零点，需要增加一天
		//Date endDate = queryAirArriveInfoDto.getDepartureEndTime();
		//加一天
		//queryAirArriveInfoDto.setDepartureEndTime(DateUtils.addDayToDate(endDate, 1));
		//查询结果
		List<AirArriveSendInfoDto> airArriveSendInfoDtoList = airWayBillDao.queryFlightArriveSendInfo(queryAirArriveInfoDto);
		//返回结果
		List<AirArriveSendInfoDto> resultDto =  new ArrayList<AirArriveSendInfoDto>();
		//如果查询结果不为空
		if(!CollectionUtils.isEmpty(airArriveSendInfoDtoList)){
			LOGGER.info("录入状态:"+ queryAirArriveInfoDto.getRecordState() +"[end]");
			for (AirArriveSendInfoDto airArriveSendInfoDto : airArriveSendInfoDtoList) {
				//录入状态-已签收
				if(AirfreightConstants.AIRFREIGHT_EDI_SIGNED.equals(queryAirArriveInfoDto.getRecordState())){
					//如果签收情况不为空，加入到返回结果中
					if(airArriveSendInfoDto.getSignTime() != null){
						resultDto.add(airArriveSendInfoDto);
					}
				//录入状态-未签收
				}else if(AirfreightConstants.AIRFREIGHT_EDI_NOT_SIGN.equals(queryAirArriveInfoDto.getRecordState())){
					//如果签收情况为空，加入到返回果中
					if(airArriveSendInfoDto.getSignTime() == null){
						//加入返回结果列表
						resultDto.add(airArriveSendInfoDto);
					}
				//全部
				}else{
					//所有的都加入返回结果列表
					resultDto.add(airArriveSendInfoDto);
				}
				//代理网点名称
				LOGGER.info("queryFlightArriveSendInfo-searchName:",airArriveSendInfoDto.getLadingStation());
			}
		}
		return resultDto;
	}
	
	/**
	 * 查询空运库存信息
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	@Override
	public List<AirStockInfoDto> queryAirStockInfo(
			QueryAirArriveInfoDto queryAirArriveInfoDto) {
		//如果代理网点为all
		if(AirfreightConstants.AIRFREIGHT_DEST_ORG_NAME.equals(queryAirArriveInfoDto.getLadingStationNumber())){
			queryAirArriveInfoDto.setLadingStationNumber("");
		}
		//edi传过来的日期是当天零点，需要增加一天
		Date endDate = queryAirArriveInfoDto.getDepartureEndTime();
		//加一天
		queryAirArriveInfoDto.setDepartureEndTime(DateUtils.addDayToDate(endDate, 1));
		//查询结果
		List<AirStockInfoDto> airStockInfoDtoList = airWayBillDao.queryAirStockInfo(queryAirArriveInfoDto);
		return airStockInfoDtoList;
	}

	
	public void setAirWayBillDao(IAirWaybillDao airWayBillDao) {
		this.airWayBillDao = airWayBillDao;
	}


	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}


	
}