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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/ContrabandSignService.java
 * 
 * FILE NAME        	: ContrabandSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveSheetManngerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.IWaybillSignResultDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISendWaybillTrackService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignStockJobService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillTransactionService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillTransactionEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SendElectronicInvoiceSystemDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillTrackDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.AirAgencySignException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.RepaymentException;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.sign.api.shared.util.DateCompareUtil;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.api.shared.define.PickupWaybillConstants;
import com.deppon.foss.module.pickup.waybill.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICUBCSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOutWarehouseExceptionService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCSignOrRevSignResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.FOSSSignOrRevSignRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.exception.CUBCSignException;
import com.deppon.foss.module.trackings.api.shared.define.WaybillTrackingsConstants;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.exceptiongoods.api.define.ExceptionGoodsConstants;
import com.deppon.foss.module.transfer.exceptiongoods.api.server.service.IContrabandGoodsService;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 违禁品签收service实现
 * 
 * @author foss-meiying
 * @date 2012-11-28 上午11:54:33
 * @since
 * @version
 */
public class ContrabandSignService implements IContrabandSignService {
	
	private IWaybillSignResultDao waybillSignResultDao;
	public void setWaybillSignResultDao(IWaybillSignResultDao waybillSignResultDao) {
		this.waybillSignResultDao = waybillSignResultDao;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.pickup.sign.server.service.impl.ContrabandSignService";
	
	/**
	 * add by 353654  注入CUBC签收服务
	 */
	private ICUBCSignService cUBCSignService;
	
	public void setcUBCSignService(ICUBCSignService cUBCSignService) {
		this.cUBCSignService = cUBCSignService;
	}
	
	/**
	 * 记录日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContrabandSignService.class);
	
	/**
	 * 到达联Service
	 */
	private IArriveSheetManngerService arriveSheetManngerService;
	/**
	 * 运单签收结果Service 
	 */
	private IWaybillSignResultService waybillSignResultService;
	
	/**
	 *  运单状态服务接口
	 */
	private IActualFreightService actualFreightService;
	/**
	 *  中转出库接口
	 */
	private IStockService stockService;
	/**
	 *  签收明细service
	 */
	private ISignDetailService signDetailService;
	/**
	 * 运单service
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 * 异常出库service
	 */
	private IOutWarehouseExceptionService outWarehouseExceptionService;
	
	/**
	 * 运单完结状态操作Service
	 */
	private IWaybillTransactionService waybillTransactionService;
	/**
	 * 运单完结状态service
	 */
	private IContrabandGoodsService contrabandGoodsService;
	/**
	 * 计算&调整走货路径类
	 */
	private ICalculateTransportPathService calculateTransportPathService;
	/**
	 * 签收反签收同步改异步接口
	 */
	private ISignStockJobService signStockJobService;
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	/**张新
	 * 打印代理面单service
	 */
	private IPrintAgentWaybillService printAgentWaybillService;
	/**
	 * 轨迹推送接口 
	 */
	private ISendWaybillTrackService sendWaybillTrackService;
	public void setSendWaybillTrackService(
			ISendWaybillTrackService sendWaybillTrackService) {
		this.sendWaybillTrackService = sendWaybillTrackService;
	}
	/**
	 *判断是否原单号
	 * */
	private IWaybillExpressService waybillExpressService;
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	/**
	 * 货签服务类
	 */
	private ILabeledGoodService labeledGoodService;
	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}
	
	/**
	 * 子母件服务接口
	 */
	IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	public void setWaybillRelateDetailEntityService(
			IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	/**
	 *  结算签收Service
	 */
	private ILineSignService lineSignService;
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}
	/**
	 * 营业部接口
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 运单接口
	 */
	private IWaybillService waybillService;
	
	/**
	 * 查询违禁品信息
	 * @author foss-meiying
	 * @date 2013-2-26 下午6:37:10
	 * @param waybillNo
	 * 			运单号
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService
	 * #queryContrabandInfoByWaybillNo
	 * (java.lang.String)
	 */
	@Override
	public ContrabandInfoDto queryContrabandInfoByWaybillNo(String waybillNo,String currentDeptcode) {
		if(StringUtils.isBlank(waybillNo)){
			LOGGER.error("--运单号不能为空");//记录日志
			throw new SignException(SignException.WAYBILLNO_IS_NOT_NULL);
		}
		ContrabandInfoDto contrabandInfoDto = new ContrabandInfoDto();
		//根据运单号查询
		WaybillDto waybillDto = waybillSignResultService.queryWaybillActualFreightPartByWaybillNo(waybillNo);
		if(null != waybillDto){
			//调中转接口查询运单是否已上报违禁品
			String contrabandType = contrabandGoodsService.queryContrabandGoodsStatus(waybillNo);
			if(ExceptionGoodsConstants.SUSPICION_CONTRABAND_PROCESS_RESULT.equals(contrabandType)){
				LOGGER.error("--违禁品申报进行中");//记录日志
				throw new SignException(SignException.SUSPICION_CONTRABAND_PROCESS_RESULT);//违禁品申报进行中
			}else if (ExceptionGoodsConstants.NO_CONTRABAND_PROCESS_RESULT.equals(contrabandType)){
				LOGGER.error("--违禁品申报审核未通过");//记录日志
				throw new SignException(SignException.NO_CONTRABAND_PROCESS_RESULT);//违禁品申报审核未通过
			}else if (ExceptionGoodsConstants.NO_REPORT_CONTRABAND.equals(contrabandType)){
				LOGGER.error("--运单未申报违禁品");//记录日志
				throw new SignException(SignException.NO_REPORT_CONTRABAND);//运单未申报违禁品
			}else {
				//这里不作处理
			}
			// ibm-meiying 2013-2-27 上午10:11:27添加库存外场、库区默认查询条件
			ContrabandInfoDto dto = new ContrabandInfoDto();
			List<String> list = handleQueryOutfieldService.getEndStockCodeAndExpressAreaCode(currentDeptcode);
			if (CollectionUtils.isNotEmpty(list)) {
				List<String> ld=new ArrayList<String>();
				ld.add(list.get(1));
				ld.add(list.get(2));
				dto.setOrgCode(list.get(0)); //最终库存部门编码
				dto.setGoodsAreaCodes(ld);// 获取库区
			}
			dto.setWaybillNo(waybillNo);
			//查询中转库存流水号
			List<SignDetailEntity> signDetailEntitys = waybillSignResultService.queryOptStockSerinalNo(dto);
			//如果查询的流水号为空
			if(CollectionUtils.isEmpty(signDetailEntitys)){
				LOGGER.error("--该运单当前库存的件数为0");//记录日志
				throw new SignException(SignException.STOCK_GOODS_QTY_ZERO);
			}
			//得到流水号集合
			contrabandInfoDto.setSignDetailList(signDetailEntitys);
			//签收件数
			contrabandInfoDto.setSignGoodsQty(signDetailEntitys.size());
			//库存件数
			contrabandInfoDto.setStockGoodsQty(signDetailEntitys.size());
			//得到运输性质
			contrabandInfoDto.setProductCode(waybillDto.getProductCode());
			contrabandInfoDto.setGenerateGoodsQty(waybillDto.getGenerateGoodsQty());//生成件数
			//运单号
			contrabandInfoDto.setWaybillNo(waybillNo);
		}else {
			LOGGER.error("--该运单号不存在");//记录日志
			throw new SignException(SignException.WAYBILLNO_NULL);
		}
		contrabandInfoDto.setSignTime(new Date());
		return contrabandInfoDto;
	}

	/**
	 * 提交违禁品/丢货签收信息
	 * @author foss-meiying
	 * @date 2013-2-18 下午5:14:54
	 * @param currentInfo
	 * 		当前登录人信息
	 * @param contrabandInfoDto
	 * 			contrabandInfoDto.waybillNo 
	 * 				运单号
	 * 			contrabandInfoDto.productCode 
	 * 				运输性质
	 * 			contrabandInfoDto.signGoodsQty
	 * 				 签收件数
	 * 			contrabandInfoDto.stockGoodsQty 
	 * 				库存件数
	 * 			contrabandInfoDto.signNote 
	 * 				签收备注
	 * 			contrabandInfoDto.signTime 
	 * 				签收时间
	 * 			contrabandInfoDto.orgCode
	 * 				 部门编码
	 * 			contrabandInfoDto.waybillNo 
	 * 				运单号
	 * 			contrabandInfoDto.expType 
	 * 				异常类型 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService
	 * #addContrabandInfo(com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo, 
	 * com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto, java.lang.String)
	 */
	@Override
	
	public SendElectronicInvoiceSystemDto addContrabandInfo(CurrentInfo currentInfo,ContrabandInfoDto contrabandInfoDto,Boolean flag) {
		if(!DateCompareUtil.isToday(contrabandInfoDto.getSignTime())){
			throw new SignException(SignException.SIGNTIME_ISNOT_SYSTEMDATE,new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())});//当前电脑时间有误，请调整日期为{0}
		}
		/**
		 * 1）	若运单为“专线”运单，若当前登录人所在部门，即违禁品所在库存部门 = t_srv_actual_freight
		 * 		.end_stock_org_code，且该运单已存在active=’Y’，destroyed=’N’,status=’已生成’的到达联。
		 *		若到达联件数>签收件数，则更新到达联件数-=签收件数。更新t_srv_actualfreight，
		 *		已排单件数-=签收件数；已生成到达联件数-=签收件数；到达件数-=签收件数；
		 *		若到达联件数<=签收件数，则作废到达联。更新t_srv_actualfreight，已排单件数-=到达联件数；
		 *		已生成到达联件数-=到达联件数；到达件数-=到达联件数；
		 *		2）	由于是违禁品，手工生成到达联，到达联件数=签收出库件数，到达联的状态为“已签收”，到达联active=’Y’，
		 *		destroyed=’N’,是否PDA签收=“否”；
		 *		3）	若运单为“专线”运单，Insert签收明细；
		 *		4）	更新t_srv_waybill_sign_result。
		 *		5）	更新t_srv_actualfreight，到达件数+=签收件数；已排单件数+=签收件数；已生成到达联件数+=签收件数；
		 *		6）	将签收的流水号出库（中转提供接口），偏线/空运货也需要做此操作；
		 */
		//如果异常类型为--违禁品
		if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS, contrabandInfoDto.getExpType())){
			LOGGER.info("--提交违禁品签收信息 开始");//记录日志
		}else{
			LOGGER.info("--提交丢货签收信息 开始");//记录日志
		}
		ActualFreightEntity actual = actualFreightService.queryByWaybillNo(contrabandInfoDto.getWaybillNo());
		LOGGER.info("actualFreightEntity"+ ReflectionToStringBuilder.toString(actual));//记录日志
		if(null == actual){
			throw new SignException(SignException.ACTUAL_FREIGHT_NULL);//根据运单号查询actualFreight没有数据！
		}
		if( WaybillConstants.OBSOLETE.equals(actual.getStatus())){// 已作废
			throw new SignException(AirAgencySignException.WAYBILL_IS_OBSOLETE);//该运单状态已作废，不能进行签收
		}else if( WaybillConstants.ABORTED.equals(actual.getStatus())){// 已中止
			throw new SignException(AirAgencySignException.WAYBILL_IS_ABORTED);//该运单状态已中止，不能进行签收
		}
		//根据运单号查询 查询运单货物总件数
		WaybillEntity entity = waybillManagerService.queryPartWaybillByNo(contrabandInfoDto.getWaybillNo());
		if(null == entity){
			LOGGER.error("--该运单号不存在");//记录日志
			throw new SignException(SignException.WAYBILLNO_NULL);
		}else{
			//add by 353654 CUBC签收情况为必传值。   借助于entity新增字段  将值传入CUBC
			if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS, contrabandInfoDto.getExpType())){
				entity.setSignSituation("UNNORMAL_CONTRABAND");//违禁品
			}else if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS, contrabandInfoDto.getExpType())){
				entity.setSignSituation("UNNORMAL_LOSTCARGO");//丢货
			}else{
				entity.setSignSituation("UNNORMAL_ABANDONGOODS");//弃货
			}
		}
		if(StringUtils.isNotBlank(entity.getSignSituation())){
			LOGGER.info("签收情况："+ entity.getSignSituation());		
		}
		boolean isSendInvoiceInfo=waybillSignResultService.isNeedSendInvoiceInfo(entity,actual);//是否将发票信息传给发票系统
		//如果运输性质为汽运偏线 或精准空运
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(contrabandInfoDto.getProductCode())||
				ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(contrabandInfoDto.getProductCode())){
			//这里不作处理
			LOGGER.info("--该运单"+contrabandInfoDto.getWaybillNo()+"不为专线");//记录日志
		}else {
			//张新  限制签收
			//张新 2015-2-3 判断当前运单是否为快递
			if(WaybillConstants.directDetermineIsExpressByProductCode(contrabandInfoDto.getProductCode())){//是
				List<PrintAgentWaybillRecordEntity> sdExternalBillRecords = printAgentWaybillService.queryRecordByWaybillNo(contrabandInfoDto.getWaybillNo(),SignConstants.LIMIT_SIGN);
				if(CollectionUtils.isNotEmpty(sdExternalBillRecords)) 
				{
					//有效的营业部外发
					LOGGER.error("--该单已外发，如需人工签收，请于“营业部外发单号绑定”中解除绑定");//记录日志
					throw new SignException(SignException.SIGN_LIMIT);//签收限制
				}
			}

			ArriveSheetEntity arriveSheet = new ArriveSheetEntity();
			arriveSheet.setWaybillNo(contrabandInfoDto.getWaybillNo());//运单号
			arriveSheet.setActive(FossConstants.ACTIVE);//有效
			arriveSheet.setDestroyed(FossConstants.NO);//未作废
			LOGGER.info("--该运单"+contrabandInfoDto.getWaybillNo()+"为专线");//记录日志
			//若当前登录人所在部门，即违禁品所在库存部门 = t_srv_actual_freight.end_stock_org_code
			if(StringUtils.equals(currentInfo.getCurrentDeptCode(), actual.getEndStockOrgCode())){
				arriveSheet.setStatus(ArriveSheetConstants.STATUS_GENERATE);//到达联状态为生成
				List<ArriveSheetEntity> arriveList = arriveSheetManngerService.queryArriveSheetByWaybillNo(arriveSheet);
				if(!CollectionUtils.isEmpty(arriveList)){
					ArriveSheetEntity arr = new ArriveSheetEntity();
					arr.setId(arriveList.get(0).getId());//得到到达联id	
					//若到达联件数>签收件数，则更新到达联件数-=签收件数。更新t_srv_actualfreight，
					//已排单件数-=签收件数；已生成到达联件数-=签收件数；到达件数-=签收件数；
					if(arriveList.get(0).getArriveSheetGoodsQty() > contrabandInfoDto.getSignGoodsQty()){
						//到达联件数-=签收件数
						arr.setArriveSheetGoodsQty(arriveList.get(0).getArriveSheetGoodsQty() - contrabandInfoDto.getSignGoodsQty());
						arriveSheetManngerService.updateByPrimaryKeySelective(arr);
						//已生成到达联件数-=签收件数
						actual.setGenerateGoodsQty(actual.getGenerateGoodsQty() - contrabandInfoDto.getSignGoodsQty());//生成到达联件数
						//到达件数-=签收件数
						actual.setArriveGoodsQty(actual.getArriveGoodsQty() - contrabandInfoDto.getSignGoodsQty());//到达件数
						//已排单件数-=签收件数
						actual.setArrangeGoodsQty(actual.getArrangeGoodsQty() - contrabandInfoDto.getSignGoodsQty());//已排单件数
						
					}else {//若到达联件数<=签收件数，则作废到达联。更新t_srv_actualfreight，已排单件数-=到达联件数；已生成到达联件数-=到达联件数；到达件数-=到达联件数；
						// 作废
						arr.setActive(FossConstants.INACTIVE);
						//时效时间为当前时间
						arr.setModifyTime(new Date());
						arriveSheetManngerService.updateByPrimaryKeySelective(arr);
						//已生成到达联件数-=到达联件数
						actual.setGenerateGoodsQty(actual.getGenerateGoodsQty() - arriveList.get(0).getArriveSheetGoodsQty());//生成到达联件数
						//到达件数-=到达联件数
						actual.setArriveGoodsQty(actual.getArriveGoodsQty() - arriveList.get(0).getArriveSheetGoodsQty());//到达件数
						//已排单件数-=到达联件数
						actual.setArrangeGoodsQty(actual.getArrangeGoodsQty() - arriveList.get(0).getArriveSheetGoodsQty());//已排单件数
					}
					LOGGER.info("arriveSheetEntity"+ ReflectionToStringBuilder.toString(arr));//记录日志
					LOGGER.info("actualFreightEntity"+ ReflectionToStringBuilder.toString(actual));//记录日志
				}
			}
			this.initArriveSheet(arriveSheet,currentInfo,contrabandInfoDto);
			for (SignDetailEntity signDetailEntity : contrabandInfoDto.getSignDetailList()) {// 批量添加签收明细。
				signDetailEntity.setArrivesheetNo(arriveSheet.getArrivesheetNo());//到达联编号
				signDetailService.addSignDetailInfo(signDetailEntity);
			}
		}
		
		this.initWaybillSignResult(contrabandInfoDto, currentInfo,entity);//添加签收信息
		//丢货、违禁品签收，推送轨迹 --add by 231438，快递100，轨迹推送需求RFOSS2015031706 
		WaybillTrackDto trackDto = new WaybillTrackDto(); // 货物轨迹推送Dto
		//运单号
		trackDto.setWaybillNo(entity.getWaybillNo());
		//丢货签收，不需要描述信息;不需要签收人
		trackDto.setOperateType(WaybillTrackingsConstants.OPERATE_TYPE_EXCEPTION_SIGN);
		//登录信息：操作部门（编码、民称）
		trackDto.setCurrentInfo(currentInfo);
		//调用轨迹接口，推送轨迹
		sendWaybillTrackService.sendTrackings(trackDto); //-- --add by 231438
		
		//更新t_srv_actualfreight
		ActualFreightDto actualFreightDto = new ActualFreightDto();
		actualFreightDto.setWaybillNo(contrabandInfoDto.getWaybillNo());
		actualFreightDto.setOldGenerateGoodsQty(contrabandInfoDto.getGenerateGoodsQty());//签收前的生成件数
		//已生成到达联件数+=签收件数
		actualFreightDto.setGenerateGoodsQty(actual.getGenerateGoodsQty() + contrabandInfoDto.getSignGoodsQty());
		//到达件数+=签收件数
		actualFreightDto.setArriveGoodsQty(actual.getArriveGoodsQty() + contrabandInfoDto.getSignGoodsQty());
		//已排单件数+=签收件数
		actualFreightDto.setArrangeGoodsQty(actual.getArrangeGoodsQty() + contrabandInfoDto.getSignGoodsQty());
		if(actualFreightService.updatePartGoodsQtyControlByWaybillNo(actualFreightDto)<=0){
			LOGGER.error("签收失败，请重新查询一下!"+ReflectionToStringBuilder.toString(actualFreightDto));//记录日志
			throw new SignException(SignException.SIGN_FAILED);//抛出异常
		}
		LOGGER.info("--更新actualFreight信息完成"+ ReflectionToStringBuilder.toString(actualFreightDto));//记录日志
		//如果签收的流水号集合大于指定数据    将签收的流水号录入 t_srv_sign_stock，偏线/空运货也需要做此操作
		if(contrabandInfoDto.getSignDetailList().size()>SignConstants.DEFAULT_SERIALNOS_LIMIT_COUNT){
			for (SignDetailEntity signDetail : contrabandInfoDto.getSignDetailList()){
				SignStockEntity signStock = new SignStockEntity();
				// 运单号
				signStock.setWaybillNo(contrabandInfoDto.getWaybillNo());
				// 流水号
				signStock.setSerialNo(signDetail.getSerialNo());
				// 部门编码
				signStock.setStockOrgCode(currentInfo.getCurrentDeptCode());
				signStock.setStockOrgName(currentInfo.getCurrentDeptName());//部门名称 
				// 操作人姓名
				signStock.setOperator(currentInfo.getEmpName());
				// 操作人工号
				signStock.setOperatorCode(currentInfo.getEmpCode());
				// 出入库类型 签收出库
				signStock.setInoutType(StockConstants.SIGN_OUT_STOCK_TYPE);
				try {
					signStockJobService.addSelective(signStock);
				} catch (BusinessException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new SignException(e.getErrorCode(),e);//抛出异常
				}
			}
			LOGGER.info("-- 添加sign_stock完成,sign_stock 的数据走异步");//记录日志
		}else {
			
			// 系统调用中转接口（SUC-238）出库货物
			for (SignDetailEntity signDetailEntity : contrabandInfoDto.getSignDetailList()) {
				InOutStockEntity inOutStock = new InOutStockEntity();
				// 运单号
				inOutStock.setWaybillNO(contrabandInfoDto.getWaybillNo());
				// 流水号
				inOutStock.setSerialNO(signDetailEntity.getSerialNo());
				// 部门编码
				inOutStock.setOrgCode(currentInfo.getCurrentDeptCode());
				// 操作人姓名
				inOutStock.setOperatorName(currentInfo.getEmpName());
				// 操作人工号
				inOutStock.setOperatorCode(currentInfo.getEmpCode());
				// 出入库类型 签收出库
				inOutStock.setInOutStockType(StockConstants.SIGN_OUT_STOCK_TYPE);
				// 进行出库操作
				try {
					if(flag){
						stockService.outStockPC(inOutStock);
					}else{
						stockService.outStockDelivery(inOutStock);
					}
				} catch (StockException e) {//捕获异常
					LOGGER.error(e.getMessage(), e);//记录日志
					throw new SignException(e.getErrorCode(),e);
				}
			}
		}
		
		// 合伙人丢货签收-通知PTP干掉流水日志-239284-异步接口---start---------------------------------------------------------------
		if (null != actual ) {
			//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
			String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
			if (StringUtils.isNotBlank(configString)) {
			    	try {
			    		SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
						long intTime = sdf.parse(configString.trim()).getTime();
						long destTime = entity.getBillTime().getTime();
						LOGGER.info("合伙人丢货/违禁品签收===运单号：" +actual.getWaybillNo() + "开单日期：" + sdf.format(entity.getBillTime()) +  "，切割日期:" + configString.trim());
						//如果开单日期大于初始化日期，则走合伙人结清流程
						if (destTime >= intTime) {
							
							// 合伙人丢货签收-通知PTP干掉流水日志-239284-异步接口---start
							// 不要拿登录部门，丢货/弃货签收都是零担丢货服务组签收；拿运单的最终到达部门code查
							SaleDepartmentEntity saleDept = null;
							LOGGER.info("合伙人丢货/违禁品签收参数===运单号：" +actual.getWaybillNo() + "，类型-" + contrabandInfoDto.getExceptionStatus() + "，出发部门:" + entity.getReceiveOrgCode() + ", 到达部门:" + entity.getLastLoadOrgCode() +
									"，当前部门:" + currentInfo.getCurrentDeptCode());
							if (PickupWaybillConstants.CONTRABAND_GOODS.equals(contrabandInfoDto.getExceptionStatus())) {
								saleDept =  saleDepartmentService.querySaleDepartmentInfoByCode(currentInfo.getCurrentDeptCode());
							} else {
								saleDept =  saleDepartmentService.querySaleDepartmentInfoByCode(entity.getLastLoadOrgCode());
							}
							SaleDepartmentEntity  startSaleDept = saleDepartmentService.querySaleDepartmentInfoByCode(entity.getReceiveOrgCode());
							validContraExtracted(contrabandInfoDto, actual,
									saleDept, startSaleDept);							
						}
					} catch (ParseException e) {
						throw new RepaymentException("合伙人结清初始化日期开关解析错误！");
					}
			} 
			
		}
		// 合伙人丢货签收-通知PTP干掉流水日志-239284-异步接口---end---------------------------------------------------------------
		
		
		//--------子母件签收判断------------
		//如果子母件全部丢货或全部违禁品，则系统红冲到达应收、代收货款应收、代收货款应付(调用结算红冲接口)；
		//add by 353654  传递签收件数到后台
		entity.setSignGoodsQty(contrabandInfoDto.getSignGoodsQty());
		//-----add by 2015-09-01 guopengzhong 
		this.handleAllExceptionGoods(entity, contrabandInfoDto.getExpType(),
				currentInfo);
		
		//如果异常类型为--违禁品
		if(StringUtils.equals(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS, contrabandInfoDto.getExpType())){
			LOGGER.info("--提交违禁品签收信息 完成");//记录日志
		}else{
			LOGGER.info("--提交丢货签收信息 完成");//记录日志
		}
		SendElectronicInvoiceSystemDto sendElectronicInvoiceSystemDto =new SendElectronicInvoiceSystemDto();
		sendElectronicInvoiceSystemDto.setIsSendInvoiceInfo(isSendInvoiceInfo);
		sendElectronicInvoiceSystemDto.setEntity(entity);
		sendElectronicInvoiceSystemDto.setActual(actual);
		return sendElectronicInvoiceSystemDto;
	}

	private void validContraExtracted(ContrabandInfoDto contrabandInfoDto,
			ActualFreightEntity actual, SaleDepartmentEntity saleDept,
			SaleDepartmentEntity startSaleDept) {
		if (null != saleDept && null != startSaleDept) {
			if (FossConstants.YES.equals(saleDept.getIsLeagueSaleDept()) || FossConstants.YES.equals(startSaleDept.getIsLeagueSaleDept())) {
				// PTP作废流水-红冲金额-调结算红冲-异步接口-黄伟
				waybillService.syncLostGoodsToPtp(actual.getWaybillNo(), contrabandInfoDto.getExceptionStatus());
			}
		} /*else {
			throw new RepaymentException("合伙人出发或到达部门营业部信息不存在！");
		}*/
		//modify by 353654
	}
	/**
	 * 更新运单签收结果
	 * @author foss-meiying
	 * @date 2013-1-29 下午3:11:14
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	private void initWaybillSignResult(ContrabandInfoDto dto,CurrentInfo currentInfo,WaybillEntity entity){
		// 传入参数(运单号,当前运单号生效)
		WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(dto.getWaybillNo(), FossConstants.ACTIVE);
		// 根据运单号 查询运单签收结果
		WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(wayEntity);
		// 得到当前时间
		Date modifyTime = new Date();
		// 如果当前运单不是第一次添加
		if (waybillSign != null) {
			// 运单签收结果里作废当前运单号
			waybillSignResultService.invalidWaybillSignResult(waybillSign.getId(), modifyTime);
			// 运单签收结果的签收件数=运单签收件数+原先运单签收的件数
			wayEntity.setSignGoodsQty(dto.getSignGoodsQty()+ waybillSign.getSignGoodsQty());
			
		} else {// 如果当前运单是第一次添加
			// 运单签收结果的签收件数 = 签收件数
			wayEntity.setSignGoodsQty(dto.getSignGoodsQty());
		}
		//如果运输性质为汽运偏线 或精准空运
		if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(dto.getProductCode())||
				ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(dto.getProductCode())){
			// 签收情况
			wayEntity.setSignSituation(dto.getSituation());
			//这里不作处理
			LOGGER.info("--该运单"+dto.getWaybillNo()+"不为专线");//记录日志
		}else {
			// 签收情况
			wayEntity.setSignSituation(SignConstants.UNNORMAL_SIGN);
		}
		wayEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());//签收部门编码
		wayEntity.setCreateOrgName(currentInfo.getCurrentDeptName());//签收部门名称
		wayEntity.setCreator(currentInfo.getEmpName());//操作人
		wayEntity.setCreatorCode(currentInfo.getEmpCode());//操作人编码
		// 生效时间为当前时间
		wayEntity.setCreateTime(modifyTime);
		// 时效时间为空，添加时采用默认值
		wayEntity.setModifyTime(null);
		// 签收时间
		wayEntity.setSignTime(dto.getSignTime());
		// 得到签收备注
		wayEntity.setSignNote(dto.getSignNote());
		// 签收状态--全部签收/部分签收
		wayEntity.setSignStatus((wayEntity.getSignGoodsQty() >= entity.getGoodsQtyTotal()) ? SignConstants.SIGN_STATUS_ALL
						: SignConstants.SIGN_STATUS_PARTIAL);
		//如果签收状态为全部签收
		if(SignConstants.SIGN_STATUS_ALL.equals(wayEntity.getSignStatus())){
			//标识业务完结
			WaybillTransactionEntity waybillTransactionEntity = new WaybillTransactionEntity();
			//运单号
			waybillTransactionEntity.setWaybillNo(wayEntity.getWaybillNo());
			waybillTransactionService.updateBusinessOver(waybillTransactionEntity);
			if(FossConstants.NO.equals(entity.getIsWholeVehicle())){//如果该运单不是整车运单
				// 调中转的走货路径接口
				try {
					calculateTransportPathService.signIn(dto.getWaybillNo(), TransportPathConstants.TRANSPORTPATH_STATUS_SIGNIN);
				} catch (TfrBusinessException e) {//捕获异常
					LOGGER.error("调中转的走货路径接口有异常", e);//异常记录
					throw new SignException(e.getErrorCode(), e);
				}
			}else {
				//这里不作处理
			}
		}
		wayEntity.setIsPdaSign(FossConstants.NO);//是否PDA签收=“否”
		waybillSignResultService.addWaybillSignResult(wayEntity);
		//菜鸟轨迹 add by 231438
		//封装的轨迹方法 赋值，判断是否符合条件 调用中转的轨迹接口
		sendWaybillTrackService.rookieTrackingsForSign(wayEntity);
		LOGGER.info("--操作运单签收结果完成"+ ReflectionToStringBuilder.toString(wayEntity));//记录日志
	}
	/**
	 * 生成到达联
	 * @author foss-meiying
	 * @date 2013-1-30 上午11:03:26
	 * @see
	 */
	private void initArriveSheet(ArriveSheetEntity arriveSheet,CurrentInfo currentInfo,ContrabandInfoDto contrabandInfoDto){
		Date date = new Date();
		//手工生成到达联由于是违禁品，手工生成到达联，到达联件数=签收出库件数，到达联的状态为“已签收”，到达联active=’Y’，destroyed=’N’,是否PDA签收=“否”
		arriveSheet.setStatus(ArriveSheetConstants.STATUS_SIGN);//到达联的状态为“已签收”
		arriveSheet.setIsPdaSign(FossConstants.NO); //是否PDA签收=“否”
		arriveSheet.setCreateUserName(currentInfo.getEmpName());//创建人名称
		arriveSheet.setCreateUserCode(currentInfo.getEmpCode());//创建人编码
		arriveSheet.setCreateOrgName(currentInfo.getCurrentDeptName());//创建部门名称
		arriveSheet.setCreateOrgCode(currentInfo.getCurrentDeptCode());//创建部门编码
		arriveSheet.setOperateOrgCode(currentInfo.getCurrentDeptCode());//操作部门编码
		arriveSheet.setOperateOrgName(currentInfo.getCurrentDeptName());////操作部门名称
		arriveSheet.setOperator(currentInfo.getEmpName());//操作人名称
		arriveSheet.setOperatorCode(currentInfo.getEmpCode());//操作人编码
		arriveSheet.setSituation(contrabandInfoDto.getSituation());//签收情况
		arriveSheet.setOperateTime(date);//操作日期 
		arriveSheet.setCreateTime(date);//创建日期
		//到达联件数=签收件数
		arriveSheet.setArriveSheetGoodsQty(contrabandInfoDto.getSignGoodsQty());
		//签收件数=签收件数
		arriveSheet.setSignGoodsQty(contrabandInfoDto.getSignGoodsQty());
		arriveSheet.setSignTime(contrabandInfoDto.getSignTime());//签收时间
		arriveSheet.setSignNote(contrabandInfoDto.getSignNote());//签收备注
		//生成到达联编号
		arriveSheet.setArrivesheetNo(arriveSheetManngerService.generateArriveSheetId(contrabandInfoDto.getWaybillNo()));
		arriveSheetManngerService.addArriveSheetData(arriveSheet);
		LOGGER.info("--生成到达联完成"+ ReflectionToStringBuilder.toString(arriveSheet));//记录日志
	}
	/**
	 * 根据传入的运单号,入库时间起止查询满足条件的运单信息
	 * @author foss-meiying
	 * @date 2013-2-3 下午3:35:43
	 * @param dto
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService#queryLostCargoInfoByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto, int, int)
	 */
	@Override
	public List<LostCargoInfoDto> queryLostCargoInfoByCondition(LostCargoInfoDto dto, int start, int limit) {
		dto.setActive(FossConstants.ACTIVE);//有效
		List<LostCargoInfoDto> lostCargoInfoDtoDtoList=waybillSignResultService.queryLostCargoInfoByCondition(dto, start, limit);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(lostCargoInfoDtoDtoList!=null){
			for(int i=0;i<lostCargoInfoDtoDtoList.size();i++){
				LostCargoInfoDto tmp=lostCargoInfoDtoDtoList.get(i);
				tmp.setServiceTime(sdf.format(new Date()));//将服务器现在时间传到页面显示
			}
		}
		return lostCargoInfoDtoDtoList;
	}
	/**
	 *  根据传入的运单号,入库时间起止查询满足条件的运单信息总数
	 * @author foss-meiying
	 * @date 2013-2-3 下午3:35:47
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService#queryLostCargoCountByCondition(com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto, int, int)
	 */
	@Override
	public Long queryLostCargoCountByCondition(LostCargoInfoDto dto) {
		dto.setActive(FossConstants.ACTIVE);//有效
		String confValue = configurationParamsService.queryConfValueByCode("LOST_CARGO_ORG_CODE");
		if(StringUtils.isNotBlank(confValue)&& confValue.equals(dto.getOrgCode())){
			return waybillSignResultService.queryLostCargoCountByCondition(dto);
		}else {
			return Long.valueOf(0);
		}
		
	}
	/**
	 * 根据传入的运单号，查询该运单是否有丢货、弃货和违禁品签收
	 * @author foss-meiying
	 * @date 2015-2-03 下午4:36:19
	 * @param waybillNo
	 * @return boolean true：有以上异常签收 false：无以上异常签收
	 */
	@Override
	public boolean queryExcepSignResultByWaybillNo(String waybillNo){
		LOGGER.info("查询该运单是否有丢货、弃货和违禁品签收接口入口：");
		WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
		resultEntity.setWaybillNo(waybillNo);
		resultEntity.setActive(FossConstants.ACTIVE);
		// 根据运单号 查询运单签收结果
	    WaybillSignResultEntity waybillSign = waybillSignResultService.queryWaybillSignResultByWaybillNo(resultEntity);
	    if(null==waybillSign){
	    	LOGGER.info("该运单无签收记录");
	    	return false;
	    }
	    if(ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(
	    		waybillSign.getSignSituation())){
	    	LOGGER.info("查询到该运单有弃货签收（签收结果）");
	    	return true;
	    }
	    //到达联-违禁品、丢货签收
	    ArriveSheetEntity arriveEntity = new ArriveSheetEntity();
	    arriveEntity.setWaybillNo(waybillNo);
	    arriveEntity.setActive(FossConstants.ACTIVE);
	    arriveEntity.setDestroyed(FossConstants.NO);
	    arriveEntity.setStatus(ArriveSheetConstants.STATUS_SIGN); //签收情况-已签收 
	    List<ArriveSheetEntity> sheetEntitys = arriveSheetManngerService.queryArriveSheetByWaybillNo(arriveEntity);
	   
	    if(sheetEntitys!=null && sheetEntitys.size() >0){  //运单有已签收到达联，再判断违禁品、丢货签收
	    	for(ArriveSheetEntity entity:sheetEntitys){  
	    		//丢货
	    		if(ArriveSheetConstants.SITUATION_UNNORMAL_LOSTCARGO.equals(entity.getSituation())){
	    			LOGGER.info("查询到该运单有丢货签收（到达联）");
	    			return true;
	    		}
	    		//违禁品
	    		if(ArriveSheetConstants.SITUATION_UNNORMAL_CONTRABAND.equals(entity.getSituation())){
	    			LOGGER.info("查询到该运单有违禁品签收（到达联）");
	    			return true;
	    		}
	    	}
	    }
	    LOGGER.info("查询该运单无丢货、弃货和违禁品签收，查询完毕");
		return false;
	}
	
	/**
	 * 根据传入的运单和异常出库类型<br/>
	 * 1 判断子母件是否全部丢货或全部弃货或全部违禁品,如果全部丢货或全部弃货或全部违禁品则调用结算红冲接口<br/>
	 * 2 子件有正常签收，最后一件是母件且母件丢货、弃货或者违禁品签收,则调用确认签收接口
	 * @param entity 运单entity
	 * @param expType 异常出库类型<br/>
	 * 		<code>SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS</code> 弃货<br/>
	 * 		<code>SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS</code> 丢货<br/>
	 * 		<code>SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS</code> 违禁品
	 * @param currentInfo 当前登录人信息
	 * 
	 */
	public void handleAllExceptionGoods(WaybillEntity entity, String expType,
			CurrentInfo currentInfo) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("waybillNo", entity.getWaybillNo());//运单号
		params.put("active", FossConstants.ACTIVE);
		//根据运单号，active，运单类型查询子母件信息
		TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
		//不是子母件
		if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.NO)){
			try {
				//CUBC签收   灰度改造    353654 ---------------------------start
				String vestSystemCode1 = null;
				try {
	              	List<String> arrayList = new ArrayList<String>();
	              	arrayList.add(entity.getWaybillNo());
	              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".handleAllExceptionGoods",
	              			SettlementConstants.TYPE_FOSS);
	              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	              	List<VestBatchResult> list1 = response.getVestBatchResult();
	              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
		  			} catch (Exception e) {
		  				LOGGER.info("灰度分流失败,"+"运单号："+entity.getWaybillNo());
						throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
		  			}
				if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
					outWarehouseExceptionService.outWarehouseException(entity.getWaybillNo(), expType, currentInfo);
				}
				if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
//					//查询运单总件数
//					WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(entity.getWaybillNo());
//					//查询签收结果表
//			        WaybillSignResultEntity wayEntity = new WaybillSignResultEntity(entity.getWaybillNo(),FossConstants.ACTIVE);
//			        WaybillSignResultEntity waybillSignResultEntity = waybillSignResultDao.queryWaybillSignResult(wayEntity);
//			        //获取已签收件数+此次签收件数与运单总件数比较判断全部和部分签收
//			        int signCount = (waybillSignResultEntity == null ? 0 : waybillSignResultEntity.getSignGoodsQty()) + entity.getSignGoodsQty();
//					String signStatus = signCount >= entity.getGoodsQtyTotal() ? SignConstants.SIGN_STATUS_ALL
//							: SignConstants.SIGN_STATUS_PARTIAL;
//					LOGGER.info("运单号:"+entity.getWaybillNo()+",ContrabandSignService.handleAllExceptionGoods传递签收件数为:"+ entity.getSignGoodsQty());
//					if(SignConstants.SIGN_STATUS_ALL.equals(signStatus)){
						LineSignDto dto = new LineSignDto();
						dto.setWaybillNo(entity.getWaybillNo());
						dto.setSignSituation(entity.getSignSituation());
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						reqDto.setExpSignType(expType);
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new SignException(resultDto1.getMeg());	
							}else{
								throw new SignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}			
//					}else{
//						LOGGER.info("ContrabandSignService.handleAllExceptionGoods(913行)："+entity.getWaybillNo()+",部分签收,不调CUBC签收接口");
//					}		
				}
				//CUBC签收   灰度改造    353654 ---------------------------end
			} catch (SettlementException e) {
				LOGGER.error("--调用结算异常接口抛出异常",e);//记录日志
				throw new SignException(e.getErrorCode(),e);
			} catch (CUBCGrayException e) {
				throw new SignException("系统繁忙,灰度分流失败,请稍后重试！");
			}
			
		//是子母件
		}else if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.YES)){
			int result = isAllLostOrAbandonOrContraband(entity.getWaybillNo(),twoInOneWaybillDto);
			//判断子母件已全部签收（签收情况为丢货或弃货或违禁品签收）
			if(result == 1){
				try {
					//CUBC签收   灰度改造    353654 ---------------------------start
					String vestSystemCode1 = null;
					try {
		              	List<String> arrayList = new ArrayList<String>();
		              	arrayList.add(entity.getWaybillNo());
		              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
		              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".handleAllExceptionGoods",
		              			SettlementConstants.TYPE_FOSS);
		              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
		              	List<VestBatchResult> list1 = response.getVestBatchResult();
		              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
			  			} catch (Exception e) {
			  				LOGGER.info("灰度分流失败,"+"运单号："+entity.getWaybillNo());
							throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			  			}
					if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
						outWarehouseExceptionService.outWarehouseException(entity.getWaybillNo(), expType, currentInfo);
					}
					if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
						LineSignDto dto = new LineSignDto();
						dto.setWaybillNo(entity.getWaybillNo());
						dto.setSignSituation(entity.getSignSituation());			
						FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
						reqDto.setCurrentInfo(currentInfo);
						reqDto.setLineSignDto(dto);
						reqDto.setExpSignType(expType);
						LOGGER.info("--调用CUBC“签收接口”开始传入参数：----"+ReflectionToStringBuilder.toString(reqDto));//记录日志
						CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
						if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
							if(StringUtils.isNotBlank(resultDto1.getMeg())){
								LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
								throw new SignException(resultDto1.getMeg());
							}else{
								throw new SignException("调用CUBC签收接口失败但未获取到异常信息");
							}
						}
					}
					//CUBC签收   灰度改造    353654 ---------------------------end
				} catch (SettlementException e) {
					LOGGER.error("--调用结算异常接口抛出异常",e);//记录日志
					throw new SignException(e.getErrorCode(),e);
				} catch (CUBCGrayException e) {
					throw new SignException("系统繁忙,灰度分流失败,请稍后重试！");
				}
				
			//子件有正常签收，最后一件是丢货、弃货或者违禁品签收
			}else if(result == 2 || result == SettlementReportNumber.THREE){
				LineSignDto dto = new LineSignDto();
				if(null != currentInfo){
					// 操作人名称
					dto.setOperatorName(currentInfo.getEmpName());
					// 操作人编码
					dto.setOperatorCode(currentInfo.getEmpCode());
					// 签收部门名称
					dto.setSignOrgName(currentInfo.getCurrentDeptName());
					// 签收部门编码
					dto.setSignOrgCode(currentInfo.getCurrentDeptCode());
				}
				// 运单号
				dto.setWaybillNo(entity.getWaybillNo());
				// 签收时间
				dto.setSignDate(new Date());
				// 签收类型:专线签收
				dto.setSignType(SettlementConstants.LINE_SIGN);
				// 运输性质
				dto.setProductType(entity.getProductCode());
				// 是否整车
				dto.setIsWholeVehicle(entity.getIsWholeVehicle());
				// 不是PDA签收
				dto.setIsPdaSign(FossConstants.NO);
				//签收情况
				dto.setSignSituation(entity.getSignSituation());
				try {
					if(result == 2){ //最后一件是母件，确认当前件收入
						//CUBC签收   灰度改造    353654 ---------------------------start
						String vestSystemCode1 = null;
						try {
			              	List<String> arrayList = new ArrayList<String>();
			              	arrayList.add(dto.getWaybillNo());
			              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
			              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".handleAllExceptionGoods",
			              			SettlementConstants.TYPE_FOSS);
			              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
			              	List<VestBatchResult> list1 = response.getVestBatchResult();
			              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
				  			} catch (Exception e) {
				  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				  			}
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
							lineSignService.confirmTaking(dto, currentInfo);
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
							FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
							reqDto.setCurrentInfo(currentInfo);
							reqDto.setLineSignDto(dto);
							CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
									throw new SignException(resultDto1.getMeg());	
								}else{
									throw new SignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}	
						}
						//CUBC签收   灰度改造    353654 ---------------------------end
					}else{//最后一件是子件，确认子件收入，同时确认母件收入
						//子件确认收入
						//CUBC签收   灰度改造    353654 ---------------------------start
						String vestSystemCode1 = null;
						try {
			              	List<String> arrayList = new ArrayList<String>();
			              	arrayList.add(dto.getWaybillNo());
			              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
			              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".handleAllExceptionGoods",
			              			SettlementConstants.TYPE_FOSS);
			              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
			              	List<VestBatchResult> list1 = response.getVestBatchResult();
			              	vestSystemCode1 = list1.get(0).getVestSystemCode();	
				  			} catch (Exception e) {
				  				LOGGER.info("灰度分流失败,"+"运单号："+dto.getWaybillNo());
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				  			}
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode1)){
							lineSignService.confirmTaking(dto, currentInfo);
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode1)){
							FOSSSignOrRevSignRequestDto reqDto = new FOSSSignOrRevSignRequestDto();
							reqDto.setCurrentInfo(currentInfo);
							reqDto.setLineSignDto(dto);
							CUBCSignOrRevSignResultDto resultDto1 = cUBCSignService.sendSignReqToCUBC(reqDto);
							if(StringUtils.equals(resultDto1.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto1.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto1.getMeg());
									throw new SignException(resultDto1.getMeg());	
								}else{
									throw new SignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}	
						}
						//CUBC签收   灰度改造    353654 ---------------------------end
						LineSignDto newDto = new LineSignDto();
						BeanUtils.copyProperties(dto, newDto);//复制参数信息
						newDto.setWaybillNo(twoInOneWaybillDto.getMainWaybillNo());//设置运单号为母件单号
//						newDto.setSignSituation(entity.getSignSituation());
						//母件确认收入
						//CUBC签收   灰度改造    353654 ---------------------------start
						String vestSystemCode = null;
						try {
			              	List<String> arrayList = new ArrayList<String>();
			              	arrayList.add(newDto.getWaybillNo());
			              	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
			              			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".handleAllExceptionGoods",
			              			SettlementConstants.TYPE_FOSS);
			              	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
			              	List<VestBatchResult> list1 = response.getVestBatchResult();
			              	vestSystemCode = list1.get(0).getVestSystemCode();	
				  			} catch (Exception e) {
				  				LOGGER.info("灰度分流失败,"+"运单号："+newDto.getWaybillNo());
								throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				  			}
						if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
							lineSignService.confirmTaking(newDto, currentInfo);
						}
						if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
							FOSSSignOrRevSignRequestDto reqDto1 = new FOSSSignOrRevSignRequestDto();
							reqDto1.setCurrentInfo(currentInfo);
							reqDto1.setLineSignDto(newDto);
							CUBCSignOrRevSignResultDto resultDto2 = cUBCSignService.sendSignReqToCUBC(reqDto1);
							if(StringUtils.equals(resultDto2.getResultMark(), FossConstants.NO)){
								if(StringUtils.isNotBlank(resultDto2.getMeg())){
									LOGGER.error("调用CUBC签收接口异常信息如下："+resultDto2.getMeg());
									throw new SignException(resultDto2.getMeg());	
								}else{
									throw new SignException("调用CUBC签收接口失败但未获取到异常信息");
								}
							}
						}
						//CUBC签收   灰度改造    353654 ---------------------------end
					}
					
				} catch (SettlementException e) {
					LOGGER.error("--调用确认签收接口抛出异常", e);// 记录日志
					throw new SignException(e.getErrorCode(), e);
				} catch (CUBCGrayException e) {
					throw new SignException("系统繁忙,灰度分流失败,请稍后重试！");
				}
			}
		}
		
	}
	
	/**
	 * 1:子母件是否全部丢货，或全部弃货，或全部违禁品;<br/>
	 * 2:子件有正常签收，最后一件是母件且母件丢货、弃货或者违禁品签收
	 * @param waybillNo 运单号
	 * @param twoInOneWaybillDto 子母件dto
	 * @param situation 签收情况
	 * @return 1全部丢货，或全部弃货，或全部违禁品; 2子件有正常签收，最后一件是母件且母件丢货、弃货或者违禁品签收，3最后一件是子件，其他情况返回0
	 */
	private int isAllLostOrAbandonOrContraband(String waybillNo,TwoInOneWaybillDto twoInOneWaybillDto){
		// 所有子母件签收结果集合
		List<WaybillSignResultEntity> signResultList = new ArrayList<WaybillSignResultEntity>();
		// 获取对应子母件的所有子单号集合
		List<String> waybillNoList = twoInOneWaybillDto.getWaybillNoList();
		if (CollectionUtils.isNotEmpty(waybillNoList)) {
			// 循环运单集合判断每一个运单是否都丢货、弃货、违禁品
			for (String billNo : waybillNoList) {
				WaybillSignResultEntity resultEntity = new WaybillSignResultEntity();
				resultEntity.setWaybillNo(billNo);
				resultEntity.setActive(FossConstants.ACTIVE);
				// 根据运单号 查询运单签收结果
				WaybillSignResultEntity signEntity = waybillSignResultService
						.queryWaybillSignResultByWaybillNo(resultEntity);
				if (signEntity != null) {// 签收结果记录不为空
					if (SignConstants.SIGN_STATUS_ALL.equals(signEntity.getSignStatus())) {
						// 弃货（弃货只有签收结果记录）
						if (ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(signEntity.getSignSituation())) {
							signResultList.add(signEntity);
						} else {// 非弃货的情况再进行处理筛选
							validBillNoExtracted(billNo, signEntity);
							signResultList.add(signEntity);
						}
					} else {// 签收结果记录为空
						return 0;// 有部分签收
					}
				} else {// 签收结果记录为空
					return 0;// 有未签收
				}
			}
		}
		
		//弃货，丢货，违禁品
		Object[] obj = { ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS,
				ArriveSheetConstants.SITUATION_UNNORMAL_LOSTCARGO,
				ArriveSheetConstants.SITUATION_UNNORMAL_CONTRABAND };
		
		//判断子件有正常签收标识
		boolean hasNormalSign = false;
		//判断全部丢货，或全部弃货，或全部违禁品的标识
		int count = 0;
		if(CollectionUtils.isNotEmpty(signResultList)){
			for(WaybillSignResultEntity entity : signResultList){
				//全部签收并且都是丢货签收，或弃货，或违禁品签收
				if (SignConstants.SIGN_STATUS_ALL.equals(entity.getSignStatus())){
					if(ArrayUtils.contains(obj, entity.getSignSituation())){
						count++;
					}else{
						//说明有子件是正常签收的
						hasNormalSign = true;
						break;//终止循环
					}
				}
			}
		}
		if(waybillNoList.size() == count){
			return 1;
		}
		
		// 如果子母件运单号集合数量和签收结果集合数量相同，则说明当前签收的是子母件里面的最后一件，且是丢货或弃货或违禁品
		if (hasNormalSign && (waybillNoList.size() == signResultList.size())) {
			if (waybillNo.equals(twoInOneWaybillDto.getMainWaybillNo())) {
				return 2;// 最后一件是母件
			} else {
				return SettlementReportNumber.THREE;// 最后一件是子件
			}
		}
		return 0;
	}

	private void validBillNoExtracted(String billNo,
			WaybillSignResultEntity signEntity) {
		if (SignConstants.UNNORMAL_SIGN.equals(signEntity.getSignSituation())) {// 异常签收
			// 到达联-违禁品、丢货签收
			ArriveSheetEntity arriveEntity = new ArriveSheetEntity();
			arriveEntity.setWaybillNo(billNo);// 运单号
			arriveEntity.setActive(FossConstants.ACTIVE);// 有效
			arriveEntity.setDestroyed(FossConstants.NO);// 未作废
			arriveEntity.setStatus(ArriveSheetConstants.STATUS_SIGN); // 签收情况-已签收
			arriveEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);// 全部签收
			// 按条件查询 waybillNo，active = 'Y'，STATUS = 'SIGN'，SIGN_STATUS = 'SIGN_STATUS_ALL'
			List<ArriveSheetEntity> sheetEntitys = arriveSheetManngerService
					.queryArriveSheetByWaybillNo(arriveEntity);
			if (CollectionUtils.isNotEmpty(sheetEntitys)) {
				// 子母件只有一件，只能有一条有效的签收的全部签收记录
				ArriveSheetEntity arrEntity = sheetEntitys.get(0);
				if (ArriveSheetConstants.SITUATION_UNNORMAL_LOSTCARGO.equals(arrEntity.getSituation())
						|| ArriveSheetConstants.SITUATION_UNNORMAL_CONTRABAND.equals(arrEntity.getSituation())) {// 丢货、违禁品
					// 签收结果表没存具体的异常情况，丢货和违禁品签收都是异常签收，这里将具体的异常-丢货和异常-违禁品的签收情况赋值给当前单号查询出的签收结果记录，用于下面的处理
					signEntity.setSignSituation(arrEntity.getSituation());
				}
			}
		}
	}
	
	/**
	 * Sets the 到达联Service.
	 *
	 * @param arriveSheetManngerService the new 到达联Service
	 */
	public void setArriveSheetManngerService(IArriveSheetManngerService arriveSheetManngerService) {
		this.arriveSheetManngerService = arriveSheetManngerService;
	}
	
	/**
	 * Sets the 运单签收结果Service.
	 *
	 * @param waybillSignResultService the new 运单签收结果Service
	 */
	public void setWaybillSignResultService(IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	
	/**
	 * Sets the 运单状态服务接口.
	 *
	 * @param actualFreightService the new 运单状态服务接口
	 */
	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}
	
	/**
	 * Sets the 中转出库接口.
	 *
	 * @param stockService the new 中转出库接口
	 */
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	
	/**
	 * Sets the 签收明细service.
	 *
	 * @param signDetailService the new 签收明细service
	 */
	public void setSignDetailService(ISignDetailService signDetailService) {
		this.signDetailService = signDetailService;
	}
	
	/**
	 * Sets the 运单service.
	 *
	 * @param waybillManagerService the new 运单service
	 */
	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	/**
	 * Sets the 异常出库service.
	 *
	 * @param outWarehouseExceptionService the new 异常出库service
	 */
	public void setOutWarehouseExceptionService(IOutWarehouseExceptionService outWarehouseExceptionService) {
		this.outWarehouseExceptionService = outWarehouseExceptionService;
	}
	
	/**
	 * Sets the 运单完结状态操作Service.
	 *
	 * @param waybillTransactionService the new 运单完结状态操作Service
	 */
	public void setWaybillTransactionService(IWaybillTransactionService waybillTransactionService) {
		this.waybillTransactionService = waybillTransactionService;
	}
	
	/**
	 * Sets the 运单完结状态service.
	 *
	 * @param contrabandGoodsService the new 运单完结状态service
	 */
	public void setContrabandGoodsService(IContrabandGoodsService contrabandGoodsService) {
		this.contrabandGoodsService = contrabandGoodsService;
	}
	/**
	 * 查询中转库存流水号
	 * @author foss-meiying
	 * @date 2013-2-18 下午4:15:08
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService#queryOptStockSerinalNo(com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto)
	 */
	@Override
	public List<SignDetailEntity> queryOptStockSerinalNo(String waybillNo,String currentDeptcode) {
		ContrabandInfoDto dto = new ContrabandInfoDto();
		dto.setOrgCode(currentDeptcode); //当前登录部门
		dto.setWaybillNo(waybillNo);
		//查询中转库存流水号
		List<SignDetailEntity> signDetailEntitys = new ArrayList<SignDetailEntity>();
		signDetailEntitys = waybillSignResultService.queryOptStockSerinalNo(dto);
		return signDetailEntitys;
	}
	
	/**
	 * 返货需求--丢货违禁品
	 * @author foss-WeiXing
	 * @date 2015-2-6 下午4:15:08
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.IContrabandSignService#queryOptStockSerinalNo(com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto)
	 */
	@Override
	@Transactional
	public void addContrabandInfoForReturnOrder(CurrentInfo currentInfo,ContrabandInfoDto contrabandInfoDto){
		SendElectronicInvoiceSystemDto SendSystmp1 = new SendElectronicInvoiceSystemDto();
		SendElectronicInvoiceSystemDto SendSystmp2 = new SendElectronicInvoiceSystemDto();
		SendElectronicInvoiceSystemDto SendSystmp3 = new SendElectronicInvoiceSystemDto();
		if (waybillExpressService.onlineDetermineIsExpressByProductCode(contrabandInfoDto.getProductCode(),new Date())){//快递
			WaybillExpressEntity waybillExpressEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(contrabandInfoDto.getWaybillNo(),WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if(waybillExpressEntity!=null){
				throw new SignException("该单号为原单号!");//该运单号不存在
			}else{
				List<WaybillExpressEntity> waybillExpressEntityList = waybillExpressService.queryWaybillListByWaybillNo(contrabandInfoDto.getWaybillNo());
				WaybillExpressEntity newWaybillNoEntity = CollectionUtils.isNotEmpty(waybillExpressEntityList) ? waybillExpressEntityList.get(0) : null;
				if(newWaybillNoEntity!=null){//是新单号
					SendSystmp1= addContrabandInfo(currentInfo, contrabandInfoDto,true);
					
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("waybillNo", newWaybillNoEntity.getOriginalWaybillNo());//返货原运单号
					params.put("active", FossConstants.ACTIVE);
					//根据运单号，active查询返货原单是否是子母件
					TwoInOneWaybillDto twoInOneWaybillDto = waybillSignResultService.queryWaybillRelateByWaybillOrOrderNo(params);
					//不是子母件
					if(StringUtils.equals(twoInOneWaybillDto.getIsTwoInOne(), FossConstants.NO)){
						//调用中转接口获取流水号
						List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(newWaybillNoEntity.getOriginalWaybillNo());
						SendSystmp2 = validSystemDtoExtracted(currentInfo,
								contrabandInfoDto, SendSystmp2,
								newWaybillNoEntity, labeledGoodEntityList);
						
					}else{//是子母件
						//遍历签收所有的返货原单
						for(WaybillExpressEntity entity : waybillExpressEntityList){
							//调用中转接口获取流水号
							List<LabeledGoodEntity> labeledGoodEntityList = labeledGoodService.queryAllSerialByWaybillNo(entity.getOriginalWaybillNo());
							//如果流水号集合不为空
							SendSystmp2 = validSystemDtoExtracted(currentInfo,
									contrabandInfoDto, SendSystmp2, entity,
									labeledGoodEntityList);
						}
					}
					
				}else{ //为无返货开单运单
					SendSystmp3 = addContrabandInfo(currentInfo, contrabandInfoDto,false);
				}
			}
		}else{//零担
			SendSystmp3 = addContrabandInfo(currentInfo, contrabandInfoDto,true);
		}
		//如果运单类型为电子发票类型且预付金额大于0   将发票信息传输至发票系统 
		if(SendSystmp1.getIsSendInvoiceInfo()!=null){
			if(SendSystmp1.getIsSendInvoiceInfo()){
				waybillSignResultService.sendInvoiceInfo(SendSystmp1.getEntity(),SendSystmp1.getActual());
			}
		}
		if(SendSystmp2.getIsSendInvoiceInfo()!=null){
			if(SendSystmp2.getIsSendInvoiceInfo()){
				waybillSignResultService.sendInvoiceInfo(SendSystmp2.getEntity(),SendSystmp2.getActual());
			}
		}
		if(SendSystmp3.getIsSendInvoiceInfo()!=null){
			if(SendSystmp3.getIsSendInvoiceInfo()){
				waybillSignResultService.sendInvoiceInfo(SendSystmp3.getEntity(),SendSystmp3.getActual());
			}
		}
	}

	private SendElectronicInvoiceSystemDto validSystemDtoExtracted(
			CurrentInfo currentInfo, ContrabandInfoDto contrabandInfoDto,
			SendElectronicInvoiceSystemDto SendSystmp2,
			WaybillExpressEntity entity,
			List<LabeledGoodEntity> labeledGoodEntityList) {
		if(!CollectionUtils.isEmpty(labeledGoodEntityList)){
			List<SignDetailEntity> signDetailEntityList =new ArrayList<SignDetailEntity>();
			for (LabeledGoodEntity labeledGoodEntity : labeledGoodEntityList) {
				SignDetailEntity signDetail = new SignDetailEntity();
				signDetail.setSerialNo(labeledGoodEntity.getSerialNo());//流水号
				signDetailEntityList.add(signDetail);
			}
			contrabandInfoDto.setSignDetailList(signDetailEntityList);
			contrabandInfoDto.setWaybillNo(entity.getOriginalWaybillNo());
			WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(contrabandInfoDto.getWaybillNo());
			contrabandInfoDto.setSignGoodsQty(waybillEntity.getGoodsQtyTotal());
			SendSystmp2 = addContrabandInfo(currentInfo, contrabandInfoDto,false);
		}
		return SendSystmp2;
	}

	/**
	 * Sets the 计算&调整走货路径类.
	 *
	 * @param calculateTransportPathService the new 计算&调整走货路径类
	 */
	public void setCalculateTransportPathService(ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * Sets the 签收反签收同步改异步接口.
	 *
	 * @param signStockJobService the new 签收反签收同步改异步接口
	 */
	public void setSignStockJobService(ISignStockJobService signStockJobService) {
		this.signStockJobService = signStockJobService;
	}

	public void setHandleQueryOutfieldService(IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setWaybillService(IWaybillService waybillService) {
		this.waybillService = waybillService;
	}
	
}