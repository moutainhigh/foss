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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/service/impl/AbandonGoodsApplicationService.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArriveDeliverDao;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveDeliverManagerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverTotalDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveDeliverVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.*;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 *到达派送管理查询系统 
 * 
 * @author ibm-meiying
 * @version 0.1 2013-6-24
 */
public class ArriveDeliverManagerService implements IArriveDeliverManagerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArriveDeliverManagerService.class);
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 到达派送dao
	 */
	private IArriveDeliverDao arriveDeliverDao;
	/**
	 * 营业部车队对应
	 */
	private ISalesMotorcadeService salesMotorcadeService;
	
	/**
	 * 组织信息 Service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 应收单service
	 */
	private IBillReceivableService billReceivableService;

	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.predeliver.server.service.impl.ArriveDeliverManagerService";
	private String queryTradeListUrl;
	public void setQueryTradeListUrl(String queryTradeListUrl) {
		this.queryTradeListUrl = queryTradeListUrl;
	}
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}


	/**
	 * 导出到达派送信息
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	public InputStream exportArriveDeliverInfo(ArriveDeliverQueryDto dto,int start, int limit)
	{
		
		this.refreshArriveDeliverQueryDto(dto);
		List<ArriveDeliverDto> arriveDeliverDtoList=arriveDeliverDao.queryArriveDeliverByParams(dto,start,limit);
		//379106 Start
		if(null!=arriveDeliverDtoList&&CollectionUtils.isNotEmpty(arriveDeliverDtoList)) {
			List<String> waybillNos = new ArrayList<String>();
			for (ArriveDeliverDto arriveDeliverDto : arriveDeliverDtoList) {
				waybillNos.add(arriveDeliverDto.getWaybillNo());
			}
			//调用灰度接口
			String vestSystemCode = null;
			try {
				LOGGER.info("查询灰度接口开始");
				CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
						SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".exportArriveDeliverInfo",
						SettlementConstants.TYPE_FOSS);
				CubcGrayResponseEntity response = (CubcGrayResponseEntity) HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
				List<VestBatchResult> list = response.getVestBatchResult();
				vestSystemCode = list.get(0).getVestSystemCode();
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".exportArriveDeliverInfo");
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)) {
				//调用结算接口根据运单号查询应收单已核销金额（如有多个应收单返回金额之和）
				Map<String, BigDecimal> map = billReceivableService.queryReceivableVeryfyAmountsByWaybill(waybillNos);
				if (null != map && map.size() > 0) {
					for (ArriveDeliverDto arriveDto : arriveDeliverDtoList) {
						if (map.containsKey(arriveDto.getWaybillNo())) {
							arriveDto.setRepaymentCodeAmount(map.get(arriveDto.getWaybillNo()));
						} else {
							arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
						}
					}
				} else {
					for (ArriveDeliverDto arriveDto : arriveDeliverDtoList) {
						arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
					}
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
					LOGGER.info("调用CUBC接口查询开始");
					//CUBC
					FossSearchTradeRequestDO requestDto = new FossSearchTradeRequestDO();
					requestDto.setWaybillNos(waybillNos);
					FossSearchTradeResponseDO responseDto = null;
					try {
						responseDto = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto, new FossSearchTradeResponseDO(), queryTradeListUrl);
					} catch (Exception e) {
						LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
						throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
					}
					if(null!=requestDto){
						if(StringUtils.isNotBlank(responseDto.getMsg()) && StringUtils.equals("N", responseDto.getActive())){
							LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto.getMsg());
							throw new SettlementException(responseDto.getMsg());
						}
						if(null!=responseDto.getDataMap()&&responseDto.getDataMap().size()>0){
							Map<String, List<TradeDO>> map = responseDto.getDataMap();
								for (ArriveDeliverDto arriveDto : arriveDeliverDtoList) {
									BigDecimal veryfyAmount=BigDecimal.ZERO;
									if (map.containsKey(arriveDto.getWaybillNo())) {
										List<TradeDO> tradeDOs = map.get(arriveDto.getWaybillNo());
											for (TradeDO tradeDO : tradeDOs ){
												//判断是不是应收单
												if(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED.equals(tradeDO.getOrderType())) {
												//判断是否是到达应收跟代收货款应收
												if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(tradeDO.getOrderSubType()) ||
														SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(tradeDO.getOrderSubType())) {
													veryfyAmount.add(tradeDO.getVerifyAmount());
												}
											}
										}
									arriveDto.setRepaymentCodeAmount(veryfyAmount);
								} else {
									arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
								}
							}
						}
					}
			}
			//end
		}
		List<List<String>> rowList = new ArrayList<List<String>>();
		//如果传入list不为空
		if(CollectionUtils.isNotEmpty(arriveDeliverDtoList))
		{
			for (ArriveDeliverDto arriveDeliverDto : arriveDeliverDtoList) {
				List<String> columnList = new ArrayList<String>();
				columnList.add(arriveDeliverDto.getWaybillNo());
				columnList.add(arriveDeliverDto.getArrivesheetNo());
				if(StringUtils.isNotBlank(arriveDeliverDto.getTransportType())){
					if(arriveDeliverDto.getTransportType().equals("TRANS_VEHICLE"))
					{
						//提货方式
						columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
					}else{
						//提货方式
						columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS_AIR));
					}
				}else {
					//提货方式
					columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getReceiveMethod(), DictionaryConstants.PICKUP_GOODS));
				
				}
				columnList.add(DateUtils.convert(arriveDeliverDto.getArriveTime(), DateUtils.DATE_TIME_FORMAT));
				columnList.add(DateUtils.convert(arriveDeliverDto.getInStockTime(), DateUtils.DATE_TIME_FORMAT));
				columnList.add(arriveDeliverDto.getReceiveCustomerContact());
				columnList.add(arriveDeliverDto.getGoodsQtyTotal()== null ? BigDecimal.ZERO.toString() :arriveDeliverDto.getGoodsQtyTotal().toString());
				//重量(公斤)
				BigDecimal goodsWeight = arriveDeliverDto.getGoodsWeightTotal() == null ? BigDecimal.ZERO : arriveDeliverDto.getGoodsWeightTotal();
				DecimalFormat df = new DecimalFormat("0.000");
				columnList.add(df.format(goodsWeight));
				//体积(方)
				BigDecimal goodsVolumeTotal = arriveDeliverDto.getGoodsVolumeTotal() == null ? BigDecimal.ZERO : arriveDeliverDto.getGoodsVolumeTotal();
				columnList.add(df.format(goodsVolumeTotal));
				columnList.add(arriveDeliverDto.getCodAmount() == null ? BigDecimal.ZERO.toString() :arriveDeliverDto.getCodAmount().toString());
				columnList.add(arriveDeliverDto.getToPayAmount()== null ? BigDecimal.ZERO.toString() :arriveDeliverDto.getToPayAmount().toString());
				columnList.add(arriveDeliverDto.getRepaymentCodeAmount()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getRepaymentCodeAmount().toString());//已付金额
				columnList.add(arriveDeliverDto.getPrePayAmount()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getPrePayAmount().toString());//预付金额
				columnList.add(arriveDeliverDto.getCodToPayAmount()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getCodToPayAmount().toString());//收款总额
				if(FossConstants.YES.equals(arriveDeliverDto.getSettleStatus())){
					columnList.add("已结清");
				}else{
					columnList.add("未结清");
				}
				columnList.add(arriveDeliverDto.getReceiveOrgCode());
				columnList.add(arriveDeliverDto.getDriverName());
				columnList.add(DateUtils.convert(arriveDeliverDto.getBillTime(), DateUtils.DATE_TIME_FORMAT));//收货日期
				//拼接得到客户地址 省-市-区县-具体地址
				String custAddr = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(arriveDeliverDto.getReceiveCustomerProvCode(), arriveDeliverDto.getReceiveCustomerCityCode(), arriveDeliverDto.getReceiveCustomerDistCode(), arriveDeliverDto.getReceiveCustomerAddress(), arriveDeliverDto.getReceiveCustomerAddressNote());
				columnList.add(custAddr);//收货人具体地址
				columnList.add(arriveDeliverDto.getReceiveCustomerPhone());//收货人电话
				columnList.add(arriveDeliverDto.getReceiveCustomerMobilePhone());//收货人手机
				columnList.add(arriveDeliverDto.getArriveGoodsQty()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getArriveGoodsQty().toString());//到达件数
				columnList.add(arriveDeliverDto.getStockType());//库存状态
				columnList.add(arriveDeliverDto.getOrgCode());// 库存部门
				columnList.add(arriveDeliverDto.getStockGoodsQty()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getStockGoodsQty().toString());//库存件数
				
				if("SUCCESS".equals(arriveDeliverDto.getNotificationResult())){//是否通知成功(最新通知状态)
					columnList.add("是");
				}else{
					columnList.add("否");
				}
				columnList.add(arriveDeliverDto.getDeliveryMan());//取货人
				//返单类型
				columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getReturnBillType(), DictionaryConstants.RETURN_BILL_TYPE));
				//返单情况
				columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getReturnBillStatus(), DictionaryConstants.PKP_RETURNBILL_STATUS));
				columnList.add(arriveDeliverDto.getIsArrange());//是否已排单
				
				columnList.add(arriveDeliverDto.getDeliverbillNo());//派送单单号
				columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getDeliverbillStatus(), DictionaryConstants.PKP_DELIVERBILL_STATUS));//派送情况
				columnList.add(arriveDeliverDto.getIsSign());//是否签收
				columnList.add(DateUtils.convert(arriveDeliverDto.getSignTime(), DateUtils.DATE_TIME_FORMAT));//签收时间
				//签收情况
				columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getSituation(), DictionaryConstants.PKP_SIGN_SITUATION));
				if(ArriveSheetConstants.SITUATION_GOODS_BACK.equals(arriveDeliverDto.getSituation())){
					columnList.add(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));//拉回备注
				}else{
					columnList.add(arriveDeliverDto.getSignNote());//签收备注
				}
				columnList.add(arriveDeliverDto.getCargoName());//货物品名
				
				columnList.add(arriveDeliverDto.getDeliverGoodsFee()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getDeliverGoodsFee().toString());//送货费

				columnList.add(arriveDeliverDto.getInsuranceValue());//保价声明价值
				columnList.add(arriveDeliverDto.getInsuranceFee());//保价费
				
				columnList.add(arriveDeliverDto.getOtherFee()== null ? BigDecimal.ZERO.toString():arriveDeliverDto.getOtherFee().toString());//其他费用
				columnList.add(arriveDeliverDto.getDeliverRequire());//送货要求
				columnList.add(DateUtils.convert(arriveDeliverDto.getOperateTime(), DateUtils.DATE_TIME_FORMAT));//送货时间

				columnList.add(arriveDeliverDto.getProductCode());//运输性质
				columnList.add(arriveDeliverDto.getTargetOrgCode());//目的站
				rowList.add(columnList);
			}
		} 
		String[] rowHeads = {"单号","到达联编号","开单提货方式","到达时间",
				"入库时间","收货人","开单件数","开单重量","开单体积","代收货款",
				"到付金额","已付金额","预付金额","收款总额","是否已结清","出发部门","送货人或代理",
				"收货日期","收货人地址","收货人电话","收货人手机","到达件数",
				"库存状态","库存部门","库存件数","是否通知成功(最新通知状态)","取货人",
				"返单类型","返单情况","是否已排单","派送单单号","派送情况","是否签收",
				"签收时间","签收情况","签收备注","货物品名","送货费","保价声明价值","保价费",
				"其他费用","送货要求","送货时间","运输性质","目的站"};
	    ExportResource exportResource = new ExportResource();
	    exportResource.setHeads(rowHeads);
	    exportResource.setRowList(rowList);
	    ExportSetting exportSetting = new ExportSetting();
	    exportSetting.setSheetName("综合派送信息");
	    exportSetting.setSize(NUMBER);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
        return objExporterExecutor.exportSync(exportResource, exportSetting);		
	}
	/**
	 * 根据条件查询到达派送信息,合计信息
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	@Override
	public ArriveDeliverVo queryArriveDeliverByParams(ArriveDeliverQueryDto dto ,int start, int limit){
		
		ArriveDeliverVo vo = new ArriveDeliverVo();
		List<ArriveDeliverDto> lists=arriveDeliverDao.queryArriveDeliverByParams(dto,start,limit);
		//379106  Start
		if(null!=lists&&CollectionUtils.isNotEmpty(lists)) {
			List<String> waybillNos = new ArrayList<String>();
			for (ArriveDeliverDto arriveDto : lists) {
				waybillNos.add(arriveDto.getWaybillNo());
			}
			String vestSystemCode = null;
			//调用灰度层传递运单号 根据返回值进行判断走FOSS还是CUBC
			try {
				LOGGER.info("运单号个数为：" + waybillNos.size());
				CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(waybillNos,
						SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".queryArriveDeliverByParams",
						SettlementConstants.TYPE_FOSS);
				LOGGER.info("调用灰度方法" + requestDto.getSourceBillNos().size());
				CubcGrayResponseEntity response = (CubcGrayResponseEntity) HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
				List<VestBatchResult> list = response.getVestBatchResult();
				vestSystemCode = list.get(0).getVestSystemCode();
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"错误方法位置："+SERVICE_CODE+".queryArriveDeliverByParams" + e.getMessage());
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)) {
				//查询运单号已核销金额（如运单有多个应收单取其和）
				Map<String, BigDecimal> map = billReceivableService.queryReceivableVeryfyAmountsByWaybill(waybillNos);
				if (null != map && map.size() > 0) {
					for (ArriveDeliverDto arriveDto : lists) {
						if (map.containsKey(arriveDto.getWaybillNo())) {
							arriveDto.setRepaymentCodeAmount(map.get(arriveDto.getWaybillNo()));
						} else {
							arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
						}
					}
				} else {
					for (ArriveDeliverDto arriveDto : lists) {
						arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
					}
				}
			}
			if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
				LOGGER.info("调用CUBC接口查询开始");
					//CUBC
					FossSearchTradeRequestDO requestDto = new FossSearchTradeRequestDO();
					requestDto.setWaybillNos(waybillNos);
					FossSearchTradeResponseDO responseDto = null;
					try {
						responseDto = (FossSearchTradeResponseDO)HttpClientUtils.postMethod(requestDto, new FossSearchTradeResponseDO(), queryTradeListUrl);
					} catch (Exception e) {
						LOGGER.error("调用CUBC接口出现异常,异常信息为："+e);
						throw new SettlementException("服务器正忙,CUBC查询物流交易单异常,请稍候重试");
					}
					if(null!=requestDto){
						if(StringUtils.isNotBlank(responseDto.getMsg()) && StringUtils.equals("N", responseDto.getActive())){
							LOGGER.error("调用CUBC查询物流交易单接口失败，异常信息：" + responseDto.getMsg());
							throw new SettlementException(responseDto.getMsg());
						}
						if(null!=responseDto.getDataMap()&&responseDto.getDataMap().size()>0){
							Map<String, List<TradeDO>> map = responseDto.getDataMap();
							for (ArriveDeliverDto arriveDto : lists) {
								BigDecimal veryfyAmount=BigDecimal.ZERO;
								if (map.containsKey(arriveDto.getWaybillNo())) {
									List<TradeDO> tradeDOs = map.get(arriveDto.getWaybillNo());
									for (TradeDO tradeDO : tradeDOs ){
										//判断是不是应收单
										if(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED.equals(tradeDO.getOrderType())) {
											//判断是否是到达应收跟代收货款应收
											if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(tradeDO.getOrderSubType()) ||
													SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(tradeDO.getOrderSubType())) {
												veryfyAmount.add(tradeDO.getVerifyAmount());
											}
										}
									}
									arriveDto.setRepaymentCodeAmount(veryfyAmount);
								} else {
									arriveDto.setRepaymentCodeAmount(BigDecimal.ZERO);
								}
							}
						}
					}
			}
		}//end
		if(CollectionUtils.isNotEmpty(lists)){
			for (ArriveDeliverDto arriveDeliverDto : lists) {
				//拼接得到客户地址 省-市-区县-具体地址
				String custAddr = handleQueryOutfieldService.getCompleteAddressAttachAddrNote(arriveDeliverDto.getReceiveCustomerProvCode(), arriveDeliverDto.getReceiveCustomerCityCode(), arriveDeliverDto.getReceiveCustomerDistCode(), arriveDeliverDto.getReceiveCustomerAddress(), arriveDeliverDto.getReceiveCustomerAddressNote());
				arriveDeliverDto.setReceivePCDAddress(custAddr);
				if(ArriveSheetConstants.SITUATION_GOODS_BACK.equals(arriveDeliverDto.getSituation())){
					arriveDeliverDto.setSignNote(DictUtil.rendererSubmitToDisplay(arriveDeliverDto.getSignNote(), DictionaryConstants.PKP_PULLBACK_REASON));//拉回备注
				}
			}
			vo.setArriveDeliverDtoList(lists);
			ArriveDeliverTotalDto  totalDto = arriveDeliverDao.queryArriveDeliverTotalByParams(dto);
			if(totalDto != null){
				if(totalDto.getToPayAmountTotal() == null){
					totalDto.setToPayAmountTotal(BigDecimal.ZERO);
				}
				if(totalDto.getPrePayAmountTotal() == null){
					totalDto.setPrePayAmountTotal(BigDecimal.ZERO);
				}
				totalDto.setAllAmountTotal(BigDecimal.ZERO.add(totalDto.getToPayAmountTotal().add(totalDto.getPrePayAmountTotal())));
				vo.setArriveDeliverTotalDto(totalDto);//得到汇总信息
			}
		}
		return vo;
	}
	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}
	public void setArriveDeliverDao(IArriveDeliverDao arriveDeliverDao) {
		this.arriveDeliverDao = arriveDeliverDao;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 根据条件查询到达派送信息合计
	 * 
	 * @author foss-meiying
	 * @date 2013-6-25 下午4:59:27
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryArriveDeliverCountByParams(ArriveDeliverQueryDto dto){	
		return arriveDeliverDao.queryArriveDeliverCountByParams(dto);
	}
	
	@Override
	public ArriveDeliverQueryDto refreshArriveDeliverQueryDto(
			ArriveDeliverQueryDto dto) {
		dto.setLastLoadOrgCode(FossUserContext.getCurrentDeptCode());
		List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(dto.getLastLoadOrgCode());
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> ld=new ArrayList<String>();
			ld.add(list.get(1));
			ld.add(list.get(2));
			dto.setEndStockOrgCode(list.get(0)); //最终库存部门编码
			dto.setGoodsAreaCodes(ld);// 获取库区
			
			// 最终库存部门名称
			dto.setEndStockOrgName(this.orgAdministrativeInfoService.queryCommonNameByCommonCode(dto.getEndStockOrgCode()));
		}else{
			return null;
		}
		if(StringUtils.isBlank(dto.getWaybillNo())){//如果运单号为空
			if(dto.getSignTimeStart() != null &&dto.getSignTimeEnd() != null ){
				dto.setSettleStatus(FossConstants.YES);//已结清
				//如果运输性质选择整车  是否经过到达部门 没勾选
				if(StringUtils.isBlank(dto.getIsPassOwnDepartment()) && 
						StringUtils.isNotBlank(dto.getProductCode()) && ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(dto.getProductCode())){
				dto.setIsPassOwnDepartment(ArriveSheetConstants.DEFAULT_NO);//不经过到达部门
				}else {
					dto.setIsPassOwnDepartment(null); //设置是否经过到达部门为NULL
					OrgAdministrativeInfoEntity org = FossUserContext.getCurrentDept();//当前操作部门
					String isSale = org.getSalesDepartment();
					List<String> orgIdList = new ArrayList<String>();
					if (FossConstants.YES.equals(isSale))//如果是营业部派送部 则通过营业部-车队对应关系表获取车队code
					{
						SalesMotorcadeEntity entity = new SalesMotorcadeEntity();
						entity.setSalesdeptCode(FossUserContextHelper.getOrgCode());
						List<SalesMotorcadeEntity> salesMotorcadeList = salesMotorcadeService.querySalesMotorcadeExactByEntity(entity,ArriveSheetConstants.BEGIN_NUM, ArriveSheetConstants.PAGE_NUM);
						if (!CollectionUtils.isEmpty(salesMotorcadeList))
						{
							for (SalesMotorcadeEntity salesMotorcadeEntity : salesMotorcadeList) //循环得到对应的车队信息
							{
								orgIdList.add(salesMotorcadeEntity.getMotorcadeCode());
							}
						}else
						{	
							orgIdList.add(FossUserContextHelper.getOrgCode());//得到当前部门
						}
					}
					List<String> subOrgCodeList = new ArrayList<String>();
					subOrgCodeList.add(dto.getLastLoadOrgCode()); //添加最终配载部门
					subOrgCodeList.add(dto.getEndStockOrgCode());//添加最终库存部门
					if(orgIdList.size()>0 && orgIdList.contains(dto.getEndStockOrgCode())){
						
					}else {
						orgIdList.add(dto.getEndStockOrgCode()); 
					}
					//根据部门编码获取所属及下属部门信息
					for (String string : orgIdList) {
						// 找顶级车队下所有子组织code
						List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(string);
						if (CollectionUtils.isNotEmpty(orgList)) {
							for (OrgAdministrativeInfoEntity orgEntity : orgList) {
								if(!subOrgCodeList.contains(orgEntity.getCode())){
									subOrgCodeList.add(orgEntity.getCode());
								}
							}
						}
					}
					dto.setSignOperateOrgCodes(subOrgCodeList);  //MANA-204到达派送管理查询优化需求
				}
			}
		}
		// 派送单状态为 已确认  已下拉   已签收确认
		List<String> deliverStatusList = new ArrayList<String>();
		//已确认
		deliverStatusList.add(DeliverbillConstants.STATUS_CONFIRMED);
		// 派送单状态为"已签收确认"
		deliverStatusList.add(DeliverbillConstants.STATUS_SIGNINFO_CONFIRMED);
		//已下拉 
		deliverStatusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
		dto.setDeliverStatusList(deliverStatusList);//派送单状态集合	
		dto.setActive(FossConstants.YES);
		return dto;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}

	public void setBillReceivableService(IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
}