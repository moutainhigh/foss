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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/service/impl/WaybillQueryService.java
 * 
 * FILE NAME        	: WaybillQueryService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.esb.inteface.domain.ccwaybillservice.WaybillCountDto;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.process.IHandleQueryOutfieldService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillExpressDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRelateDetailEntityDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcDao;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillRfcVarificationDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanResultDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.AdjustPlanSearcherDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;
import com.deppon.foss.module.pickup.waybill.server.utils.Constants;
import com.deppon.foss.module.pickup.waybill.server.utils.HttpClientUtil;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.RequestDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.TradeDO;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestBatchResult;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestResponse;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.service.IGrayScaleService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcActionHistoryEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EarliestCreateTimeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportBillingDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ReportComponentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SignDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOneYearDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryCityProvinceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryOneYearDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillQueryException;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillQueryExceptionType;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcException;
import com.deppon.foss.module.pickup.waybill.shared.vo.AppWayBillDetaillVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.WaybillSignDetailVo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPathDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;


public class WaybillQueryService implements IWaybillQueryService {
	/**
	 * 状态
	 */
	private static final String STATUS = "status";
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillQueryService.class);
	
	private static final int NUMBER_10000 = 10000;
	
	/**
	 * 运单Dao
	 */
	private IWaybillDao waybillDao;
	/**
	 * 更改单Dao
	 */
	private IWaybillRfcDao waybillRfcDao;
	/**
	 * 行政区域 Service
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	/**
	 * 组织信息service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 数据字典service
	 */
	private IDataDictionaryValueService dataDictionaryValueService;
	/**
	 * 外场相关共通接口
	 */
	private IHandleQueryOutfieldService handleQueryOutfieldService;
	
	/**
	 * 运单签收结果
	 */
	private IWaybillSignResultService waybillSignResultService;

	/** 
	 * 部门查询起始. 
	 */
	private static final int BEGIN_NUM = 0;
	
	/** 
	 * 查询页面大小. 
	 */
	private static final int PAGE_SIZE = 1;
	
	/**
	 * 偏线代理网点查询
	 */
	private ICommonAgencyDeptDao commonAgencyDeptDao;
	
	//查询更改单审核受理
	private IWaybillRfcVarificationDao waybillRfcVarificationDao;
	
	private IWaybillManagerService waybillManagerService;
	
	
	private IWaybillExpressDao waybillExpressDao;
	
	private IWaybillExpressService waybillExpressService;
	
	private IPathDetailDao pathDetailDao;
	/**
	 * CUBC灰度接口
	 */
	private IGrayScaleService grayScaleService;
	
	public void setGrayScaleService(IGrayScaleService grayScaleService) {
		this.grayScaleService = grayScaleService;
	}

	/**
	 * 应付单服务.
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 子母件数据持久层
	 */
	private IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao;

	public void setWaybillRelateDetailEntityDao(
			IWaybillRelateDetailEntityDao waybillRelateDetailEntityDao) {
		this.waybillRelateDetailEntityDao = waybillRelateDetailEntityDao;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setWaybillExpressDao(IWaybillExpressDao waybillExpressDao) {
		this.waybillExpressDao = waybillExpressDao;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	public void setWaybillRfcVarificationDao(
			IWaybillRfcVarificationDao waybillRfcVarificationDao) {
		this.waybillRfcVarificationDao = waybillRfcVarificationDao;
	}

	public void setCommonAgencyDeptDao(ICommonAgencyDeptDao commonAgencyDeptDao) {
		this.commonAgencyDeptDao = commonAgencyDeptDao;
	}

	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	public void setHandleQueryOutfieldService(
			IHandleQueryOutfieldService handleQueryOutfieldService) {
		this.handleQueryOutfieldService = handleQueryOutfieldService;
	}

	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/**
	 * @param waybillDao the waybillDao to set
	 */
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}

	/**
	 * @param waybillRfcDao the waybillRfcDao to set
	 */
	public void setWaybillRfcDao(IWaybillRfcDao waybillRfcDao) {
		this.waybillRfcDao = waybillRfcDao;
	}

	/**
	 * @param administrativeRegionsService the administrativeRegionsService to set
	 */
	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param dataDictionaryValueService the dataDictionaryValueService to set
	 */
	public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * 根据不同的查询条件返回运单单号集合 ： 1、 输入参数中“运单号”优先级最高，如果起始运单号和结束运单号不为空，则其他条件可以忽略
	 * 2、输入参数如果为Null，则不作为查询条件 3、 列表最大返回行数3000行
	 * 
	 * @param waybillListByComplexCondationDto
	 * @return waybillNos
	 */
	public List<String> queryWaybillListByComplexCondation(
			WaybillListByComplexCondationDto waybillListByComplexCondationDto) {

		/**
		 * 判断传入对象是否为空
		 */
		if (null == waybillListByComplexCondationDto) {
			throw new WaybillQueryException(WaybillQueryException.WAYBILLLIST_NULL);
		}

		/**
		 * 起始单号不为空，但是结束单号为空
		 */
		if (!StringUtils.isBlank(waybillListByComplexCondationDto.getStartWaybillNo()) 
				&& StringUtils.isBlank(waybillListByComplexCondationDto.getEndWaybillNo()) ) {
			throw new WaybillQueryException(WaybillQueryException.ENDWAYBILLNO_NULL);
		}
		/**
		 * 结束单号不为空，但是起始单号为空
		 */
		if (!StringUtils.isBlank(waybillListByComplexCondationDto.getEndWaybillNo()) 
				&& StringUtils.isBlank(waybillListByComplexCondationDto.getStartWaybillNo())) {
			throw new WaybillQueryException(WaybillQueryException.STARTWAYBILLNO_NULL);
		}
		/**
		 * 起始时间不为空，结束时间为空
		 */
		if(null!=waybillListByComplexCondationDto.getStartBillTime() && null==waybillListByComplexCondationDto.getEndBilltime()){
			throw new WaybillQueryException(WaybillQueryException.ENDBILLTIME_NULL);
		}
		/**
		 * 结束时间不为空，起始时间为空
		 */
		if(null!=waybillListByComplexCondationDto.getEndBilltime() && null==waybillListByComplexCondationDto.getStartBillTime()){
			throw new WaybillQueryException(WaybillQueryException.STARTBILLTIME_NULL);
		}
		
		return waybillDao.queryWaybillListByComplexCondation(waybillListByComplexCondationDto);
	}

	/**
	 * 根据运单号查询运单出发更改单信息
	 * 
	 * @param waybillNo
	 * @return
	 */
	public List<LeaveChangeByWaybillNoResultDto> queryLeaveChangeByWaybillNo(
			String waybillNo) {

		if (StringUtils.isEmpty(waybillNo)) {

			throw new WaybillQueryException(
					WaybillQueryExceptionType.QUERY_NUMBER_NULL_ERROR_CODE);
		}
		
		 List<LeaveChangeByWaybillNoResultDto>  leaveChangeByWaybillNoResultDtos=waybillRfcDao.queryLeaveChangeByWaybillNo(waybillNo);
		 /**
		  * 如果没受理，受理部门和受理时间为空，如果已受理受理你们为最终配载部门
		  */
		 for(LeaveChangeByWaybillNoResultDto leaveChangeByWaybillNoResultDto:leaveChangeByWaybillNoResultDtos){
			 
			 List<WaybillRfcActionHistoryEntity> rfcAction = getWaybillRfcActionHistoryEntity(leaveChangeByWaybillNoResultDto.getId());
			 if(rfcAction != null && !rfcAction.isEmpty()) {
				 //一条更改单审核的信息只存在一条
				 WaybillRfcActionHistoryEntity actionHistory = rfcAction.get(0);
				 //审核人
				 leaveChangeByWaybillNoResultDto.setAcceptor(actionHistory.getOperator());
				 //审核时间
				 String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(actionHistory.getOperateTime());
				 leaveChangeByWaybillNoResultDto.setAcceptTime(time);
				 //审核状态
				 leaveChangeByWaybillNoResultDto.setAcceptStatus(actionHistory.getStatus());
				 //审核备注
				 leaveChangeByWaybillNoResultDto.setAcceptNotes(actionHistory.getNotes());
				 
			 }
			 
			 /**
			  * 根据受理状态来处理是否需要赋值
			  * BUG-44895【诡异】单号146144746在系统中出现紊乱
			  */
			 if(!WaybillRfcConstants.ACCECPT.equals(leaveChangeByWaybillNoResultDto.getStatus())
					 && !WaybillRfcConstants.ACCECPT_DENY.equals(leaveChangeByWaybillNoResultDto.getStatus())) {
				/**
				 * 未受理的状态
				 */
				 leaveChangeByWaybillNoResultDto.setOperateOrgName("");
				 leaveChangeByWaybillNoResultDto.setAuditor("");
				 leaveChangeByWaybillNoResultDto.setOperateTime(null);
				 leaveChangeByWaybillNoResultDto.setNotes("");
				 
				 
			 }
			 
		 	/**
			 * 设置需要受理部门
			 */
			 if(FossConstants.ACTIVE.equals(leaveChangeByWaybillNoResultDto.getIsLabourHandle()))
			 {
				 
				 leaveChangeByWaybillNoResultDto.setNeedAcceptOrg("-");//如果是自动受理，需要受理人是空
			 }
		 }

		return leaveChangeByWaybillNoResultDtos;
	}
	
	/**
	 * 根据运单号查询运单更改单审核信息
	 * 
	 * @param waybillNo
	 * @return
	 */
	private List<WaybillRfcActionHistoryEntity> getWaybillRfcActionHistoryEntity(String waybillRfcId)
	{
		return waybillRfcVarificationDao.queryWayBillRfcActionHistory(waybillRfcId);
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService#querySalesStatementByComplexCondation(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto)
	 */
	public Map<String,Object> querySalesStatementByComplexCondation(
			WaybillListByComplexCondationDto condition,int start,int limit) {
		if(condition == null){
			throw new WaybillQueryException("方法querySalesStatementByComplexCondation传入参数WaybillListByComplexCondationDto为空！");
		}
		List<SalesStatementByComplexCondationResultDto> result = new ArrayList<SalesStatementByComplexCondationResultDto>();
		//切割传入的参数Dmana-4340
		condition.setProductCodes(splitString(condition.getProductCode()));
		condition.setProductCode("");
		int count = 0;
		/**
		 * 如果业务时间和派送时间都为空时，抛出异常
		 */
		if (!((condition.getStartBillTime() != null && condition.getEndBilltime() != null)
				|| (condition.getBeginDeliverTime() != null && condition
						.getEndDeliverTime() != null))) {
			throw new WaybillQueryException(WaybillQueryException.TIME_NULL);
		}
		
		
		CalcTotalFeeDto totalFeeDto = new CalcTotalFeeDto();
		if(condition != null){
			
			Integer pageCount = Integer.valueOf(0);
			Integer goodsQtyTotal = Integer.valueOf(0);
			BigDecimal goodsWeightTotal = BigDecimal.ZERO;
			BigDecimal goodsVolumeTotal = BigDecimal.ZERO;
			BigDecimal prePayAmountTotal =BigDecimal.ZERO;
			BigDecimal toPayAmountTotal = BigDecimal.ZERO;
			BigDecimal codAmountTotal = BigDecimal.ZERO;
			BigDecimal packageFeeTotal = BigDecimal.ZERO;
			BigDecimal amountTotal = BigDecimal.ZERO;
			BigDecimal cashAmountTotal = BigDecimal.ZERO;
			BigDecimal arrearAmountTotal = BigDecimal.ZERO;
			BigDecimal monthlyAmountTotal = BigDecimal.ZERO;
			BigDecimal otherAmountTotal =BigDecimal.ZERO;
			BigDecimal toPaybillAmountTotal = BigDecimal.ZERO;
			BigDecimal billAmountTotal = BigDecimal.ZERO;			
			//保价费
			BigDecimal insuranceFee = BigDecimal.ZERO;
			//公布价运费
			BigDecimal transportFee = BigDecimal.ZERO;
			//增值服务费
			BigDecimal valueAddFee = BigDecimal.ZERO;
			//优惠费用
			BigDecimal promotionsFee = BigDecimal.ZERO;
			
			count = waybillDao.countQuerySalesStatementByComplexCondation(condition);
			//限制查询条数不能大于10000
			if(count>NUMBER_10000){
				throw  new WaybillQueryException("查询数据量过大,请缩短查询时间！");
			}
			if(count > 0){
				result = revertPdaWaybillCreateTime(waybillDao.querySalesStatementByComplexCondation(condition,start,limit));
				for(SalesStatementByComplexCondationResultDto dto : result){
					if(dto!=null&&FossConstants.INACTIVE.equals(dto.getIsInvalid()))
					{
						continue;
					}
					
					pageCount ++;
					goodsQtyTotal +=dto.getGoodsQtyTotal();
					goodsWeightTotal = goodsWeightTotal.add((dto.getGoodsWeightTotal() != null ? dto.getGoodsWeightTotal() : BigDecimal.ZERO));
					goodsVolumeTotal = goodsVolumeTotal.add( (dto.getGoodsVolumeTotal()!=null ? dto.getGoodsVolumeTotal():BigDecimal.ZERO));
					prePayAmountTotal = prePayAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount(): BigDecimal.ZERO));
					toPayAmountTotal = toPayAmountTotal.add((dto.getToPayAmount()!=null ? dto.getToPayAmount():BigDecimal.ZERO));
					codAmountTotal = codAmountTotal.add((dto.getCodAmount()!=null ? dto.getCodAmount():BigDecimal.ZERO));
					packageFeeTotal = packageFeeTotal.add( (dto.getPackageFee()!=null ? dto.getPackageFee():BigDecimal.ZERO));
					amountTotal = amountTotal.add((dto.getTotalFee()!=null ? dto.getTotalFee(): BigDecimal.ZERO));
					//根据乡镇编码查询
					AdministrativeRegionsEntity entity = null;
					if(StringUtil.isNotBlank(dto.getReceiverCustomerVillageCode())){
						entity = administrativeRegionsService.queryAdministrativeRegionsByCode(dto.getReceiverCustomerVillageCode());
					}
					if(entity != null && StringUtil.isNotBlank(entity.getName())){
						dto.setReceiverCustomerVillage(entity.getName());
					}
					//收货地址拼接
					if(dto.getReceiveCustomerAddress()!=null || dto.getReceiverCustomerProv()!=null || dto.getReceiverCustomerCity()!=null ||dto.getReceiverCustomerDist()!=null){
					   dto.setReceiveCustomerAddress((dto.getReceiverCustomerProv()!=null ? dto.getReceiverCustomerProv():"")
							                        +(dto.getReceiverCustomerCity()!=null ? dto.getReceiverCustomerCity():"")
							                        +(dto.getReceiverCustomerDist()!=null ? dto.getReceiverCustomerDist():"")
							                        +(dto.getReceiverCustomerVillage()!=null ? dto.getReceiverCustomerVillage():"")
							                        +(dto.getReceiveCustomerAddress()!=null ? dto.getReceiveCustomerAddress():""));
					}
					//新增统计项目
					if(!dto.getPaidMethod().equals(WaybillConstants.ARRIVE_PAYMENT)){
						if(dto.getPaidMethod().equals(WaybillConstants.CASH_PAYMENT)){
							cashAmountTotal = cashAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
						}
						if(dto.getPaidMethod().equals(WaybillConstants.TEMPORARY_DEBT)){
							arrearAmountTotal = arrearAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
						}
						if(dto.getPaidMethod().equals(WaybillConstants.MONTH_PAYMENT)){
							monthlyAmountTotal = monthlyAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
						}
						if(!dto.getPaidMethod().equals(WaybillConstants.CASH_PAYMENT) && !dto.getPaidMethod().equals(WaybillConstants.TEMPORARY_DEBT) && !dto.getPaidMethod().equals(WaybillConstants.MONTH_PAYMENT)){
							otherAmountTotal = otherAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
						}											
					}
					//保价费
					insuranceFee = insuranceFee.add((dto.getInsuranceFee()!=null ? dto.getInsuranceFee(): BigDecimal.ZERO));
					//公布价运费
					transportFee = transportFee.add((dto.getTransportFee()!=null ? dto.getTransportFee(): BigDecimal.ZERO));
					//增值服务费
					valueAddFee = valueAddFee.add((dto.getValueAddFee()!=null ? dto.getValueAddFee(): BigDecimal.ZERO));
					//优惠费用
					promotionsFee = promotionsFee.add((dto.getPromotionsFee()!=null ? dto.getPromotionsFee(): BigDecimal.ZERO));
				}
			}
			toPaybillAmountTotal = toPayAmountTotal.add(codAmountTotal);
			billAmountTotal = toPayAmountTotal.add(prePayAmountTotal);
				
			totalFeeDto.setAmountTotal(amountTotal);
			totalFeeDto.setCodAmountTotal(codAmountTotal);
			totalFeeDto.setGoodsQtyTotal(goodsQtyTotal);
			totalFeeDto.setGoodsVolumeTotal(goodsVolumeTotal);
			totalFeeDto.setGoodsWeightTotal(goodsWeightTotal);
			totalFeeDto.setPackageFeeTotal(packageFeeTotal);
			totalFeeDto.setPageCount(pageCount);
			totalFeeDto.setPrePayAmountTotal(prePayAmountTotal);
			totalFeeDto.setToPayAmountTotal(toPayAmountTotal);
			totalFeeDto.setCashAmountTotal(cashAmountTotal);
			totalFeeDto.setArrearAmountTotal(arrearAmountTotal);
			totalFeeDto.setMonthlyAmountTotal(monthlyAmountTotal);
			totalFeeDto.setOtherAmountTotal(otherAmountTotal);
			totalFeeDto.setToPaybillAmountTotal(toPaybillAmountTotal);
			totalFeeDto.setBillAmountTotal(billAmountTotal);
			//保价费
			totalFeeDto.setInsuranceFee(insuranceFee);
			//公布价运费
			totalFeeDto.setTransportFee(transportFee);			
			//增值服务费
			totalFeeDto.setValueAddFee(valueAddFee);
			//优惠费用
			totalFeeDto.setPromotionsFee(promotionsFee);
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		resultMap.put("count", count);
		resultMap.put("totalFeeDto", totalFeeDto);
		return resultMap;
	}

	/**
	 * @功能 转换PDA制单时间，取其最早的时间
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-12-25 9:07:18
	 * @param result
	 * @return
	 */
	private List<SalesStatementByComplexCondationResultDto> revertPdaWaybillCreateTime(
			List<SalesStatementByComplexCondationResultDto> result) {
		//当查询结果不为空
		if(result != null && result.size() > 0){
			List<SalesStatementByComplexCondationResultDto> pdaResult = new ArrayList<SalesStatementByComplexCondationResultDto>();
			List<String> waybillNoList = new ArrayList<String>();
			//先取出是PDA制单的单号,移除对应行，后面加进去
			for(int i = result.size()-1; i >= 0; i--){
				if(FossConstants.YES.equals(result.get(i).getIsPdaCreate())){
					pdaResult.add(result.get(i));
					waybillNoList.add(result.get(i).getWaybillNo());
					result.remove(i);
				}
			}
//			List<EarliestCreateTimeDto> createTimeList = waybillDao.selectPdaWaybillCreateTime(waybillNoList);
			List<EarliestCreateTimeDto> createTimeList = new ArrayList<EarliestCreateTimeDto>();
			//Oracle因为做了限制，in()条件数量不能超过1000条，所以在这里限制一下，500条查询一次
		    if(waybillNoList.size()>NumberConstants.NUMBER_1000){
		      int num = waybillNoList.size()/NumberConstants.NUMBER_1000+1;
		      for(int i=0;i<num;i++){
		        if(i == num-1){
		          createTimeList.addAll(waybillDao.selectPdaWaybillCreateTime(waybillNoList.subList(i*NumberConstants.NUMBER_1000, waybillNoList.size())));
		        }else{
		          createTimeList.addAll(waybillDao.selectPdaWaybillCreateTime(waybillNoList.subList(i*NumberConstants.NUMBER_1000, (i+1)*NumberConstants.NUMBER_1000)));
		        }
		        
		      }
		    }else{
		      createTimeList = waybillDao.selectPdaWaybillCreateTime(waybillNoList);
		    }
			//如果查询出来的结果跟这个有出入还是返回原来的结果
			if(createTimeList.size() == 0){
				result.addAll(pdaResult);
			}else{//将数据逐一赋值到对应的Dto中
				for(int i=createTimeList.size()-1;i>=0;i--){
					for(int j=pdaResult.size()-1;j>=0;j--){
						//当单号相等时候才赋值
						if(createTimeList.get(i).getWaybillNo().equals(pdaResult.get(j).getWaybillNo())){
							pdaResult.get(j).setCreateTime(createTimeList.get(i).getCreateTime());
							result.add(pdaResult.get(j));
						}
					}
				}
			}
			return result;
		}
		return null;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param condition
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService#queryBillListByComplexCondation(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto)
	 */
	@Deprecated
	public List<SalesStatementByComplexCondationResultDto> queryBillListByComplexCondation(
			WaybillListByComplexCondationDto condition) {
		List<SalesStatementByComplexCondationResultDto> result = new ArrayList<SalesStatementByComplexCondationResultDto>();
		if(condition != null){
			result = waybillDao.queryBillListByComplexCondation(condition);
		}
		return result;
	}

	/** 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService#queryWaybillInfoByWaybillNo(java.lang.String)
	 */
	public WaybillInfoByWaybillNoReultDto queryWaybillInfoByWaybillNo(
			String waybillNO) {
		if(StringUtils.isEmpty(waybillNO)){
			throw new WaybillQueryException(
					WaybillQueryExceptionType.QUERY_NUMBER_NULL_ERROR_CODE);
		}
		//转换航班类型
		WaybillInfoByWaybillNoReultDto result = waybillDao.queryWaybillInfoByWaybillNo(waybillNO);
		
		/**
		 * 根据 单号来判断是否为字母件
		 * 如果为字母件则货物总重量和总体积为字母件的总重量和体积
		 */
		if(result !=null){			
			List<String> list=new ArrayList<String>();
			list.add(waybillNO);
			List<WaybillRelateDetailEntity> entity=waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNos(list,0,0,false);
			if(CollectionUtils.isNotEmpty(entity)){
				WaybillRelateDetailEntity dateilEntity=entity.get(0);
				result.setCzmGoodsVolumeTotal(dateilEntity.getGoodsVolumeTotal());
				result.setCzmGoodsWeightTotal(dateilEntity.getGoodsWeightTotal());
			}
		}
		
		if(result!=null){
			if(StringUtils.isEmpty(result.getCustomerPickupOrgName())){
				String orgcode = result.getCustomerPickupOrgCode();
				String productCode =result.getProductCode();
				String  customerPickupOrgName= waybillDao.queryCustomerPickupOrgNameByWayno(orgcode,productCode);
				result.setCustomerPickupOrgName(customerPickupOrgName);
			}
			if(StringUtils.isEmpty(result.getReceiveOrgName())){
				String orgcode = result.getReceiveOrgCode();
				String  receiveOrgName= waybillDao.queryReceiveOrgNameByWayno(orgcode);
				result.setReceiveOrgName(receiveOrgName);
			}
			//收货地址备注
			if(StringUtils.isNotEmpty(result.getReceiveCustomerAddressNote())){
				String receiveCustomerAddress = result.getReceiveCustomerAddress()+"("+result.getReceiveCustomerAddressNote()+")";
				result.setReceiveCustomerAddress(receiveCustomerAddress);
			}
			//发货地址备注
			if(StringUtils.isNotEmpty(result.getDeliveryCustomerAddressNote())){
				String deliveryCustomerAddress = result.getDeliveryCustomerAddress()+"("+result.getDeliveryCustomerAddressNote()+")";
				result.setDeliveryCustomerAddress(deliveryCustomerAddress);
			}
			/**
			 * DMANA-8928 FOSS开单品名自动匹配货源品类需求
			 * 货源品类编码转换成名称
			 * @author Foss-206860
			 */
			DataDictionaryValueEntity  categoryEntity = new DataDictionaryValueEntity();
			if(StringUtils.isNotEmpty(result.getCategory())){
				categoryEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.BSE_SOURCE_CATEGORY, result.getCategory());
				// 设置
				if(categoryEntity == null){
					result.setCategory("");
				}else{
					result.setCategory(categoryEntity.getValueName());
				}
			}
		}
			 
		 
		DataDictionaryValueEntity  resultFlightType = new DataDictionaryValueEntity();
		// 获取航班的中文描述
		if(result != null){
			resultFlightType = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.AIR_FLIGHT_TYPE, result.getFlightNumberType());
			//设置出发部门
			result.setStartOrgCode(getStartOrgCode(waybillNO));						
		}else{
		    return null;
		}
		// 设置
		if(resultFlightType == null){
			result.setFlightNumberType("");
		}else{
			result.setFlightNumberType(resultFlightType.getValueName());
		}
		return result;
	}
	
	/**
	 * 根据运单号获取出发部门
	 * @author WangQianJin
	 * @date 2013-7-30 上午11:03:20
	 */
	private String getStartOrgCode(String waybillNO){
		WaybillEntity waybillEntity=waybillDao.queryWaybillByNo(waybillNO);
		if(waybillEntity!=null){
			/**
			 * 判断是否是集中开单
			 */
			if (FossConstants.YES.equals(waybillEntity.getPickupCentralized())) {
				//集中开单查开单组所属中转场的默认出发部门
				SaleDepartmentEntity saleDepartment = waybillManagerService.queryPickupCentralizedDeptCode(waybillEntity.getCreateOrgCode());
				if (saleDepartment != null && saleDepartment.getCode()!=null) {
					return saleDepartment.getCode();		
				}else{
					return null;
				}
			} else {
				//非集中开单收货部门为出发部门
				return waybillEntity.getReceiveOrgCode();
			}
		}else{
			return null;
		}
	}
	
	/** 
	 * 分页查询，官网查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param args
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService#queryWaybillInfos(java.util.Map)
	 */
	public Map<String, Object> queryWaybillInfos(Map<String, Object> args) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<CrmWaybillServiceDto> list = new ArrayList<CrmWaybillServiceDto>();
		int count = 0;
		//官网查询，需要对传入的物流状态进行分页，如果用运单状态来查询，需要进行内存分页
		if(args != null){
			args.put("active", FossConstants.ACTIVE);
			//使用内存分页
			if(args.get(STATUS)!=null && !"".equals(args.get(STATUS)) ){
				int start = (Integer) args.get("pageNum");
				int limit = (Integer) args.get("pageSize");
				if(limit > NumberConstants.NUMBER_2000){
					limit = NumberConstants.NUMBER_2000 ;
				}
				//不分页，采用内存分页
				list = waybillDao.queryWaybillInfoByCondition(args,false);
				//设置运单状态
				setWaybillStatus(list);

				if(args.get(STATUS)!=null && !"".equals(args.get(STATUS)) ){
					if(!StringUtils.isEmpty(args.get(STATUS).toString())){
						queryWaybillByStatus(list,result,args.get(STATUS).toString(),start,limit);
					}
				}
				
			}else{
				list = waybillDao.queryWaybillInfoByCondition(args,true);
				count = waybillDao.countQueryWaybillInfoByCondition(args);
				setWaybillStatus(list);//设置运单状态
				result.put("count", count);
				result.put("list", list);
			}
		}
		
		

		return result;
	}
	
	/** 
	 * 分页查询，官网查询已激活的电子运单
	 * @author 136334-foss-BaiLei
	 * @date 2014-9-3
	 */
	public Map<String, Object> queryActiveEWaybillInfos(Map<String, Object> args) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<WaybillEntity> list = new ArrayList<WaybillEntity>();
		int count = 0;
		//官网查询，需要对传入的物流状态进行分页，如果用运单状态来查询，需要进行内存分页
		if(args != null){
			args.put("active", FossConstants.ACTIVE);
			args.put("waybillType", WaybillConstants.EWAYBILL);
			
			list = waybillDao.queryActiveEWaybillInfoByCondition(args,true);
			count = waybillDao.countQueryActiveEWaybillInfoByCondition(args);
			if(list != null && list.size() > 0){
				result.put("count", count);
				result.put("list", list);
			}else{
				result.put("count", 0);
				result.put("list", null);
			}

		}
		return result;
	}	
	
	/**
	 * APP分页查询Foss的发货/收货信息,包含子母件或正常件标示
	 * @param params 请求参数
	 * @return map键值对 count：记录数；list:AppWayBillDetaillVo结果集
	 * @author 272311
	 * 2015.09.06
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryAppWaybillInfosExp(Map<String, Object> params){
		LOGGER.info("进入 queryAppWaybillInfos_exp");
		long begin = System.currentTimeMillis();
		
		//发货/收货信息
		Map<String, Object> map = queryAppWaybillInfos(params);
		if((Integer)map.get("count") > 0){
			List<String> waybillNos = new ArrayList<String>();
			List<CrmWaybillServiceDto> list = (List<CrmWaybillServiceDto>) map.get("list");
			for(CrmWaybillServiceDto crmWaybillServiceDto : list){
				waybillNos.add(crmWaybillServiceDto.getWaybillNo());
			}//for end
			LOGGER.info("----------->根据运单集合查询子母件信息");
			//根据运单集合查询子母件信息
			List<WaybillRelateDetailEntity> lists = waybillRelateDetailEntityDao.queryWaybillRelateDetailByWaybillNos(waybillNos,0,0,false);
			Map<String, String> hashMap = new HashMap<String, String>();
			for(WaybillRelateDetailEntity waybillRelateDetailEntity : lists){
				LOGGER.info("母件运单号："+waybillRelateDetailEntity.getParentWaybillNo()+"; 子运单号："+waybillRelateDetailEntity.getWaybillNo()+";"+waybillRelateDetailEntity.getIsPicPackage()) ;
				hashMap.put(waybillRelateDetailEntity.getWaybillNo(), waybillRelateDetailEntity.getIsPicPackage());//子件母件判定:Y:母件;N:子件
			}
			String keywords = (String) params.get("keyWords");
//			map.put("waybillCZMMap", hashMap);
//			BeanUtils.copyProperties(dest, orig);
			//把CrmWaybillServiceDto bean 转换为AppWayBillDetaillVo bean
			List<AppWayBillDetaillVo> wayBillDetaillVos = new ArrayList<AppWayBillDetaillVo>();
			List<AppWayBillDetaillVo> wayBillDetaillVoList = convertToAppWayBillDetaillVo(list);
			LOGGER.info("----------->给运单对象AppWayBillDetaillVo增加子母件标示");
			//增加子母件标示
			for(AppWayBillDetaillVo wayBillDetaillVo : wayBillDetaillVoList){
				if(hashMap.containsKey(wayBillDetaillVo.getWaybillNum())){
					//子母件集合中没有该运单信息 则再次判断是子单或母单
					if("Y".equals(hashMap.get(wayBillDetaillVo.getWaybillNum()))){
						wayBillDetaillVo.setWaybillType(WaybillConstants.WAYBILL_PARENT);
						wayBillDetaillVos.add(wayBillDetaillVo);
					}else{
						if(StringUtils.isNotEmpty(keywords)){
							wayBillDetaillVo.setWaybillType(WaybillConstants.WAYBILL_CHILD);
							wayBillDetaillVos.add(wayBillDetaillVo);
						}else{
							LOGGER.info("keywords 关键字 为空，移除子单信息");
//							wayBillDetaillVos.remove(wayBillDetaillVo);
						}
					}
				}else{//子母件集合中没有该运单信息 则是正常件
					wayBillDetaillVo.setWaybillType(WaybillConstants.WAYBILL_NORMAL);
					wayBillDetaillVos.add(wayBillDetaillVo);
				}
			}//for end
			
			int start = (Integer) params.get("pageNum");
			int limit = (Integer) params.get("pageSize");
			int pageCount = 0;
			pageCount = (wayBillDetaillVos.size()-1) / limit + 1 ;
			// 跨越总页数置为最后一页
			if (start > pageCount) {
				start = pageCount;
			}
			// 策画须要显示的成果数据
			List<AppWayBillDetaillVo> pageList = new ArrayList<AppWayBillDetaillVo>();
			//start从0开始计数
			for(int i = start * limit; i < wayBillDetaillVos.size() && i < (start+1) * limit ; i++){
				pageList.add(wayBillDetaillVos.get(i));
			}
			map.put("count", wayBillDetaillVos.size());			
			map.put("list", pageList);//支持内存分页
			
		}
		LOGGER.info("----------->queryAppWaybillInfos_exp操作结束 耗时："+(System.currentTimeMillis()-begin));
		return map ;
		
	}
	
	
	/**
	 * APP分页根据手机号分页查询我的发货/收货信息
	 * @author WangQianJin
	 * @date 2014-07-19
	 * @param args
	 * @return
	 */
	public Map<String, Object> queryAppWaybillInfos(Map<String, Object> args){
		Map<String,Object> result = new HashMap<String, Object>();
		List<CrmWaybillServiceDto> list = new ArrayList<CrmWaybillServiceDto>();
		int count = 0;
		//官网查询，需要对传入的物流状态进行分页，如果用运单状态来查询，需要进行内存分页
		if(args != null){
			args.put("active", FossConstants.ACTIVE);
			if(args.get("type")!=null){
				String type=(String)args.get("type");
				//判断客户类型，获取客户信息
				if(WaybillConstants.APP_DELIVER.equals(type)){
					args.put("deliverMobile", args.get("mobilePhone"));
				}else if(WaybillConstants.APP_RECEIVE.equals(type)){
					args.put("receiveMobile", args.get("mobilePhone"));
				}else{
					args.put("deliverMobile", args.get("mobilePhone"));
					args.put("receiveMobile", args.get("mobilePhone"));
				}
			}else{
				args.put("deliverMobile", args.get("mobilePhone"));
				args.put("receiveMobile", args.get("mobilePhone"));
			}			
			//使用内存分页
			if(args.get(STATUS)!=null && !"".equals(args.get(STATUS))){
				int start = (Integer) args.get("pageNum");
				int limit = (Integer) args.get("pageSize");
				//不分页，采用内存分页
				list = waybillDao.queryAppWaybillInfoByCondition(args,false);
				//设置运单状态
				setWaybillStatus(list);
				if(args.get(STATUS)!=null && !"".equals(args.get(STATUS)) ){
					if(!StringUtils.isEmpty(args.get(STATUS).toString())){
						queryWaybillByStatus(list,result,args.get(STATUS).toString(),start,limit);
					}
				}				
			}else{
				//此处也采用内存分页
				list = waybillDao.queryAppWaybillInfoByCondition(args,false);
				count = waybillDao.countQueryAppWaybillInfoByCondition(args);
				setWaybillStatus(list);//设置运单状态
				result.put("count", count);
				result.put("list", list);
			}
		}
		return result;
	}
	
	private void setWaybillStatus(List<CrmWaybillServiceDto> list){
		for(CrmWaybillServiceDto dto : list){
			//TODO 若弃货，且未理赔，要将签收时间设为空，运单状态设未签收上一级
			//update by foss-231434-bieyexiong 非异常弃货，直接发送签收信息
			//查询签收结果信息
			WaybillSignResultEntity signEntity = new WaybillSignResultEntity();
			signEntity.setWaybillNo(dto.getWaybillNo());
			signEntity.setActive(FossConstants.ACTIVE);
			// 根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity newEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(signEntity);
			//(需求DN201603140026只针对零担)当非快递，签收结果不等于null，并且签收情况为弃货时，判断是否申请过理赔
			if(!waybillExpressService.onlineDetermineIsExpressByProductCode(dto.getTransProperties(),null) && newEntity != null && ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(newEntity.getSignSituation())){
				RequestDO requestDO = new RequestDO();
				requestDO.setServiceCode(WaybillRfcVarificationService.class.getName()+".addWSCWayBillData");
				requestDO.setOrigin(Constants.GRAY_VESTSYSTEM_CODE_FOSS_JSH);
				requestDO.setSourceBillType(Constants.GRAY_SOURCE_BILLTYPE_W);
				requestDO.setSourceBillNos(newEntity.getWaybillNo());
				VestResponse response=null;
				// 获取理赔应付单 --根据运单号集合和单据类型集合查询应付单信息
				List<BillPayableEntity> billPayables = null;
				try {
				    response = grayScaleService.vestAscription(requestDO);//如果客户端出现异常，或者连接超时，就默认走foss
				} catch (Exception e) {
					throw new WaybillRfcException(Constants.GRAY_SERVICE_EXCEPTION);
				}
				if(null!=response && !CollectionUtils.isEmpty(response.getVestBatchResult())){
					List<VestBatchResult> batchResults = response.getVestBatchResult();
					List<String> waybillNosFOSS=null;
					List<String> wyabillNosCUBC=null;
					for (int i = 0; i < batchResults.size(); i++) {
						if(Constants.GRAY_VESTSYSTEM_CODE_FOSS.equals(batchResults.get(i).getVestSystemCode())){
							waybillNosFOSS=batchResults.get(i).getVestObject();
						}else if(Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())){
							wyabillNosCUBC=batchResults.get(i).getVestObject();
						}
					}
					for (int i = 0; i < batchResults.size(); i++) {
						if (batchResults.get(i).getVestSystemCode().equals(Constants.GRAY_VESTSYSTEM_CODE_FOSS)) {
							billPayables = billPayableService.queryByWaybillNosAndByBillTypes(waybillNosFOSS,
									Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM));
						}else if (Constants.GRAY_VESTSYSTEM_CODE_CUBC.equals(batchResults.get(i).getVestSystemCode())) {
							List<TradeDO> tradeDOList = queryBySourceBillNOsToCUBC(wyabillNosCUBC);
							for (int j = 0; j < tradeDOList.size(); j++) {
								BillPayableEntity billReceivableEntity = converFromTradeDo(tradeDOList.get(j));
			        			billPayables.add(billReceivableEntity);
			        		}
							
						}
					}
				}
				//异常弃货，且有上报理赔，才发送签收信息
				if(CollectionUtils.isNotEmpty(billPayables)){
					dto.setWaybillStatus(waybillDao.queryWaybillStatus(dto.getWaybillNo()));
				}else{
					//异常弃货，未上报理赔时，把签收时间设为null
					dto.setSignedDate(null);
				}
			}else{
				//当签收结果等于null，或者签收情况不为弃货时，直接设置运单状态
				dto.setWaybillStatus(waybillDao.queryWaybillStatus(dto.getWaybillNo()));
			}
		}
	}
	private List<TradeDO> queryBySourceBillNOsToCUBC(List<String> sourceBillNosList){
	    Map<String, Object> toCubcMap=new HashMap<String, Object>();
	    toCubcMap.put("sourceBillNosList", sourceBillNosList);
	    toCubcMap.put("sourceBillType", "W");
	    toCubcMap.put("active", FossConstants.YES);
	    String requestparams=JSON.toJSONString(toCubcMap);
	    LOGGER.info("cubc的访问接口queryBySourceBillNOsToCUBC方法的json信息"+requestparams);
	    try {
			return grayScaleService.grayQueryOrderByList(requestparams);
		} catch (Exception e) {
			LOGGER.info("cubc的访问接口queryBySourceBillNOsToCUBC方法时异常");
			return null;
		}
	}
	private BillPayableEntity converFromTradeDo(TradeDO tradeDO){
		BillPayableEntity billPayableEntity = new BillPayableEntity();
		billPayableEntity.setPayableNo(tradeDO.getOrderNo());// 物流交易号(应收单号)
		billPayableEntity.setWaybillNo(tradeDO.getWaybillNo());// 运单号
		billPayableEntity.setWaybillId(tradeDO.getSourceBillNoId());//运单ID
		billPayableEntity.setPaymentNo(tradeDO.getPaymentNo());//付款单号（付款反写）
		billPayableEntity.setCreateType(tradeDO.getSourceSystem());//系统生成方式
		billPayableEntity.setBillType(tradeDO.getOrderSubType());//单据子类型
		billPayableEntity.setSourceBillNo(tradeDO.getSourceBillNo());//来源单据单号
		billPayableEntity.setSourceBillType(tradeDO.getSourceBillType());// 来源单据类型(来源单据类型)
//		billPayableEntity.setCodType(codType);//代收货款类型
//		billPayableEntity.setPayableOrgCode(payableOrgCode);//应付部门编码
//		billPayableEntity.setPayableOrgName(payableOrgName);//应付部门名称
//		billPayableEntity.setPayableComCode(payableComCode);//应付子公司编码
//		billPayableEntity.setPayableComName(payableComName);//应付子公司名称
		billPayableEntity.setOrigOrgName(tradeDO.getTradeAttributeDO().getOrigOrgName());// 始发部门名称(出发部门名称)
		billPayableEntity.setOrigOrgCode(tradeDO.getTradeAttributeDO().getOrigOrgCode());// 始发部门编码(出发部门编码)
		billPayableEntity.setDestOrgCode(tradeDO.getTradeAttributeDO().getDescOrgCode());// 到达部门编码(到达部门编码)
		billPayableEntity.setDestOrgName(tradeDO.getTradeAttributeDO().getDescOrgName());// 到达部门名称(到达部门名称)
		billPayableEntity.setCustomerCode(tradeDO.getMasterCustomerCode());// 主账户客户编码
		billPayableEntity.setCustomerName(tradeDO.getMasterCustomerName()); // 主账户客户名称
		billPayableEntity.setAmount(tradeDO.getAmount());//应付金额
		billPayableEntity.setVerifyAmount(tradeDO.getVerifyAmount());//已核销金额
		billPayableEntity.setUnverifyAmount(tradeDO.getUnverifyAmount());//未核销金额
		billPayableEntity.setCurrencyCode(tradeDO.getCurrencyType());//币种
//		billPayableEntity.setProductCode(productCode);//运输性质
//		billPayableEntity.setProductName(productName);//运输名称
		billPayableEntity.setProductId(tradeDO.getProductId());//产品ID
		billPayableEntity.setAccountDate(tradeDO.getStatementDate());//记账日期
		billPayableEntity.setBusinessDate(tradeDO.getBusinessDate());//业务日期
//		billPayableEntity.setSignDate(signDate);//签收日期
//		billPayableEntity.setEffectiveDate(effectiveDate);//生效日期
		billPayableEntity.setCreateUserCode(tradeDO.getCreateUserCode());// 创建人工号 （制单人编码）
		billPayableEntity.setCreateUserName(tradeDO.getCreateUserName());;// 创建人名称（制单人名称）
		billPayableEntity.setCreateOrgCode(tradeDO.getCreateOrgCode());// 创建部门编码 （制单部门编码）
		billPayableEntity.setCreateOrgName(tradeDO.getCreateOrgName());// 创建部门名称（制单部门名称）
//		billPayableEntity.setActive(active);//是否有效
//		billPayableEntity.setIsRedBack(isRedBack);//是否红单
//		billPayableEntity.setIsInit(isInit);//是否初始化
		billPayableEntity.setVehicleNo(tradeDO.getVersionNo().toString());//版本号
//		billPayableEntity.setEffectiveStatus(effectiveStatus);//生效状态
//		billPayableEntity.setEffectiveUserCode(effectiveUserCode);//生效人名称
//		billPayableEntity.setEffectiveUserName(effectiveUserName);//生效人编码
		billPayableEntity.setFrozenStatus(tradeDO.getFrozenStatus());//冻结状态
		billPayableEntity.setFrozenTime(tradeDO.getFrozenDate());//冻结时间
//		billPayableEntity.setFrozenUserCode(frozenUserCode);//冻结人名称
//		billPayableEntity.setFrozenUserName(frozenUserName);//冻结人编码
		billPayableEntity.setPayStatus(tradeDO.getPayStatus());//支付状态
		billPayableEntity.setStatementBillNo(tradeDO.getStatementBillNo());//对账单号
//		billPayableEntity.setCustomerContact(customerContact);//客户联系人编码
//		billPayableEntity.setCustomerContactName(customerContactName);//客户联系人姓名
//		billPayableEntity.setCustomerPhone(customerPhone);//客户联系电话
		billPayableEntity.setCreateTime(tradeDO.getCreateDate());//创建时间
		billPayableEntity.setModifyDate(tradeDO.getModifyDate());// 修改时间
		billPayableEntity.setModifyUserCode(tradeDO.getModifyUserCode());// 修改人工号（修改人编码）
		billPayableEntity.setModifyUserName(tradeDO.getModifyUserName());// 修改人姓名(修改人名称)
//		billPayableEntity.setWorkflowNo(workflowNo);//工作流号
//		billPayableEntity.setLgdriverName(lgdriverName);//外请车司机名称
//		billPayableEntity.setLgdriverCode(lgdriverCode);//外请车司机编码
//		billPayableEntity.setPayerType(payerType);//付款方
//		billPayableEntity.setPayableType(payableType);//应付类型
		billPayableEntity.setDeliverFee(tradeDO.getTradeAttributeDO().getDeliveryFee());//送货费
//		billPayableEntity.setOutgoingFee(outgoingFee);//外发运费
		billPayableEntity.setCodAgencyFee(tradeDO.getTradeAttributeDO().getCollectionChargesPoundage());//代收货款手续费
//		billPayableEntity.setExternalInsuranceFee(externalInsuranceFee);//外发保价费
//		billPayableEntity.setAuditUserCode(auditUserCode);//审核人编码
//		billPayableEntity.setAuditUserName(auditUserName);//审核人名称
//		billPayableEntity.setAuditDate(auditDate);//审校日期
//		billPayableEntity.setApproveStatus(approveStatus);//审批状态
		billPayableEntity.setNotes(tradeDO.getRemark());//备注
		billPayableEntity.setClaimWay(tradeDO.getTradeAttributeDO().getClaimWay());//理培类型
//		billPayableEntity.setPayeeName(payeeName);//开户人姓名
//		billPayableEntity.setBankHqCode(bankHqCode);//开户行编码
//		billPayableEntity.setBankHqName(bankHqName);//开户行名称
//		billPayableEntity.setAccountNo(accountNo);//银行账号
//		billPayableEntity.setProvinceCode(provinceCode);//省份编码
//		billPayableEntity.setProvinceName(provinceName);//身份名称
//		billPayableEntity.setCityCode(cityCode);//城市编码
//		billPayableEntity.setCityName(cityName);//城市名称
//		billPayableEntity.setBankBranchCode(bankBranchCode);//支行编码（行号）
//		billPayableEntity.setBankBranchName(bankBranchName);//支行名称
//		billPayableEntity.setPaymentNotes(paymentNotes);//付款备注
//		billPayableEntity.setPaymentAmount(paymentAmount);//付款金额
//		billPayableEntity.setLastPaymentTime(lastPaymentTime);//最后付款时间
//		billPayableEntity.setPaymentCategories(paymentCategories);//支付类别
//		billPayableEntity.setIsWriteoff(isWriteoff);//是否核销
//		billPayableEntity.setAccountType(accountType);//账户类型
//		billPayableEntity.setExpressOrigOrgCode(expressOrigOrgCode);//出发部门映射快递点部编码
//		billPayableEntity.setExpressOrigOrgName(expressOrigOrgName);//出发部门映射快递点部名称
//		billPayableEntity.setExpressDestOrgCode(expressDestOrgCode);//到达部门映射快递点部编码
//		billPayableEntity.setExpressDestOrgName(expressDestOrgName);//到达部门映射快递点部名称
		billPayableEntity.setInvoiceMark(tradeDO.getInvoiceMark());// 发票标记(发票标记)
//		billPayableEntity.setCostDeptCode(costDeptCode);//费用承担部门编码
//		billPayableEntity.setCostDeptName(costDeptName);//费用承担部门名称
//		billPayableEntity.setCostType(costType);//费用承担部门名称
//		billPayableEntity.setVatInvoice(vatInvoice);//发票号
//		billPayableEntity.setCostDate(costDate);//费用发生日期
		billPayableEntity.setVehicleNo(tradeDO.getTradeAttributeDO().getVehicleNo());//车牌号
		billPayableEntity.setRentCarUseType(tradeDO.getTradeAttributeDO().getRentCarUseType());//租车用途
//		billPayableEntity.setDriverName(driverName);//司机姓名
		billPayableEntity.setDriverPhone(tradeDO.getTradeAttributeDO().getDriverPhone());//司机联系方式
//		billPayableEntity.setTaxAmount(taxAmount);//税金
//		billPayableEntity.setTax(tax);//税后金额
		billPayableEntity.setBusinessDate(tradeDO.getBusinessDate());//临时租车业务发生日期
//		billPayableEntity.setAccruedState(accruedState);//预提状态
//		billPayableEntity.setWithholdingStatus(withholdingStatus);//预提状态
		billPayableEntity.setUnifiedSettlement(tradeDO.getUnifiedSettlement());// 统一结算(是否统一结算)
		billPayableEntity.setContractOrgCode(tradeDO.getContractOrgCode());// 合同部门编码
		billPayableEntity.setContractOrgName(tradeDO.getContractOrgName()); // 合同部门名称
//		billPayableEntity.setTaxpayerNumber(taxpayerNumber);//纳税人识别号
//		billPayableEntity.setPaymentWorkflowNo(paymentWorkflowNo);//付款工作流
//		billPayableEntity.setPaymentTransfer(paymentTransfer);//转报销工作流
//		billPayableEntity.setExpenseBearCode(expenseBearCode);//应付单费用承担部门
//		billPayableEntity.setFactoring(factoring);//是否为保理  默认为'N'
//		billPayableEntity.setFactorBeginTime(factorBeginTime);//保理开始日期
//		billPayableEntity.setFactorEndTime(factorEndTime);//保理结束日期
//		billPayableEntity.setFactorAccount(factorAccount);//保理回款账号
//		billPayableEntity.setCusCode(cusCode);//贷款客户编码
		
	    return billPayableEntity;
	}
	/**
	 * 根据状态进行查询
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	
	private void queryWaybillByStatus(List<CrmWaybillServiceDto> list,
			Map<String, Object> result, String status, int start, int limit) {
		List<CrmWaybillServiceDto> waybillStatusList = new ArrayList<CrmWaybillServiceDto>();

		if (list != null && result != null) {
			for (CrmWaybillServiceDto dto : list) {
				String waybillStatus = dto.getWaybillStatus();
				// 返回的运单状态不能为空。且必须与传入的
				if (!StringUtils.isEmpty(dto.getWaybillStatus())) {
					/**
					 * 在途运输时，包含状态： STATION_OUT";// 营业部已出发 TFR_IN";// 已到达中转场
					 * TFR_STOCK";// 中转库存 TFR_OUT";// 中转运输 STATION_IN";// 已到达营业部
					 * STATION_STOCK";// 营业部库存 DELIVERING";// 送货中
					 * 
					 */
					if (WaybillConstants.ACCOUNT_IN_TRANSIT.equals(status)) {

						if (WaybillConstants.ACCOUNT_STATION_OUT
								.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_TFR_IN
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_TFR_STOCK
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_TFR_OUT
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_STATION_IN
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_STATION_STOCK
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_EFFECTIVE
										.equals(waybillStatus)
								|| WaybillConstants.ACCOUNT_DELIVERING
										.equals(waybillStatus)) {
							waybillStatusList.add(dto);

						}
						// 正常签收
					} else if (WaybillConstants.ACCOUNT_NORMAL_SIGN.equals(status)) {
						if (WaybillConstants.ACCOUNT_NORMAL_SIGN.equals(waybillStatus)) {
							waybillStatusList.add(dto);
						}
						// 异常签收
					} else if (WaybillConstants.ACCOUNT_UNNORMAL_SIGN
							.equals(status)) {
						if (WaybillConstants.ACCOUNT_UNNORMAL_SIGN
								.equals(waybillStatus)) {
							waybillStatusList.add(dto);
						}

					}

				}

			}
			
			
			

			int pageCount = 0;
			if (waybillStatusList.size() % limit == 0) {
				pageCount = waybillStatusList.size() / limit;
			} else {
				pageCount = (waybillStatusList.size() / limit) + 1;
			}
			// 跨越总页数置为最后一页
			if (start > pageCount) {
				start = pageCount;
			}

			// 策画须要显示的成果数据
			List<CrmWaybillServiceDto> pageList = new ArrayList<CrmWaybillServiceDto>();
			/*//start从1开始计数
			for (int i = ((start - 1) * limit); i < waybillStatusList.size()
					&& i < ((start) * limit) && start > 0; i++) {
				pageList.add(waybillStatusList.get(i));
			}
			*/
			//start从0开始计数
			for(int i = start * limit; i < (start+1) * limit && i < waybillStatusList.size() ; i++){
				pageList.add(waybillStatusList.get(i));
			}
			result.put("count", waybillStatusList.size());			
			result.put("list", pageList);//支持内存分页
			
		}

	}
	
	
	
	/**
	 * 根据查询条件查询执行计划
	 * @author 043260-foss-suyujun
	 * @date 2012-12-25
	 * @param waybillNO
	 * @return WaybillInfoByWaybillNoReultDto
	 */
	@Override
	public List<AdjustPlanResultDto> queryAdjustPlan(
			AdjustPlanSearcherDto adjustPlanSearcherDto,int start,int limit) {
		// 打印页面传入参数
		LOGGER.info(ReflectionToStringBuilder.toString(adjustPlanSearcherDto));
		List<AdjustPlanResultDto> dtoList = waybillDao.queryAdjustPlan(adjustPlanSearcherDto, start, limit);
		if (CollectionUtils.isNotEmpty(dtoList)) {
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
			for (AdjustPlanResultDto dto : dtoList) {
				// 设置出发部门名称
				orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getReceiveOrgCode());
				dto.setReceiveOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				// 设置原到达部门名称
				orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getOcustomerPickupOrgCode());
				dto.setOcustomerPickupOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				// 设置新到达部门名称
				if (StringUtils.equals(dto.getOcustomerPickupOrgCode(), dto.getDcustomerPickupOrgCode())) {
					dto.setDcustomerPickupOrgName(dto.getOcustomerPickupOrgName());
				} else {
					orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(dto.getDcustomerPickupOrgCode());
					dto.setDcustomerPickupOrgName(orgAdministrativeInfoEntity == null ? "" : orgAdministrativeInfoEntity.getName());
				}
			}
		} else {
			LOGGER.info("没有记录");
		}
		return dtoList;
	}

	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-1-6 下午5:21:02
	 */
	public List<WaybillInfoDto> queryWaybillInfo(List<String> waybillList){
		if(CollectionUtils.isNotEmpty(waybillList)){
			WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();//创建对象
			waybillQueryInfoDto.setState(FossConstants.ACTIVE);
			waybillQueryInfoDto.setWaybillList(waybillList);
			List<WaybillInfoDto> waybillInfoDtoList = waybillDao.queryWaybillInfo(waybillQueryInfoDto);
			List<WaybillInfoDto> waybillInfoDtoListNew = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDtoNew = null;
			for (WaybillInfoDto waybillInfoDto : waybillInfoDtoList){	
				waybillInfoDtoNew = new WaybillInfoDto();
				try {
					PropertyUtils.copyProperties(waybillInfoDtoNew,waybillInfoDto);
				} catch (IllegalAccessException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (InvocationTargetException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (NoSuchMethodException e) {
					//出现异常
					LOGGER.error("error", e);
				}
				
				//小件=------------------- ----
				//出发网点点部code
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity0 = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getStartExpressOrgCode());
				if(orgAdministrativeInfoEntity0!=null){
					waybillInfoDtoNew.setStartExpressUnfiedCode(orgAdministrativeInfoEntity0.getUnifiedCode());//标杆编码
					
					List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
						waybillInfoDtoNew.getStartExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
					
					if(list!=null && list.size()>0){
						OrgAdministrativeInfoEntity entity = list.get(0);
						waybillInfoDtoNew.setDistrictCode(entity.getCode());
						waybillInfoDtoNew.setDistrictName(entity.getName());
						waybillInfoDtoNew.setDistrictUnifiedCode(entity.getUnifiedCode());	
					}
					
				}
				
				
				ExpressPartSalesDeptResultDto dtoExp =null;
				SignDto dto = waybillDao.getWaybillSignInfo(waybillInfoDto.getWaybillNo());
				if(dto!=null){
					if(StringUtils.isNotEmpty(dto.getEmpCode())){
					
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getEmpCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getEmpName());
					}else{
						
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getCreateUserCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getCreateUserName());
					}
					
					if(StringUtils.isNotEmpty(dto.getOrgCode())){
						waybillInfoDtoNew.setEndExpressOrgCode(dto.getOrgCode());
						waybillInfoDtoNew.setEndExpressOrgName(dto.getOrgName());
					}else{
						
						dtoExp= expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
								(dto.getCreateOrgCode(), new Date());
						if(dtoExp!=null){
							waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
					}
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){
						
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressUnfiedCode(tmp.getUnifiedCode());
						}
					}
					
					
					
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){
							List<OrgAdministrativeInfoEntity> list = 
									orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
											waybillInfoDtoNew.getEndExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
							
							if(list!=null && list.size()>0){
								OrgAdministrativeInfoEntity entity = list.get(0);
								waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
								waybillInfoDtoNew.setEndDistrictName(entity.getName());
								waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
							}
					}
					
					
				}else{
					//在没有签收的情况下 返回快递提货网点的快递点部信息
					String customerPickUpOrgCode = waybillInfoDtoNew.getCustomerPickupOrgCode();
					dtoExp = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
							(customerPickUpOrgCode, new Date());
					
					
					if(dtoExp!=null){
						
						waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
						
						if(StringUtils.isNotEmpty(dtoExp.getPartCode())){
						
							List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
									dtoExp.getPartCode(), BizTypeConstants.EXPRESS_BIG_REGION);
								
								if(list!=null && list.size()>0){
									OrgAdministrativeInfoEntity entity = list.get(0);
									
									waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
									waybillInfoDtoNew.setEndDistrictName(entity.getName());
									waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
									
								}
							}
						}
				}
				
				//小件=-------------------11111111
				
				//根据编码查询出发部门
//				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getCreateOrgCode());
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if(orgAdministrativeInfoEntity != null){
					//出发部门名称
					waybillInfoDtoNew.setDepartureDeptName(orgAdministrativeInfoEntity.getName());
					//出发部门标杆编码
					waybillInfoDtoNew.setDepartureDeptNumber(orgAdministrativeInfoEntity.getUnifiedCode());
					//出发部门地址
					waybillInfoDtoNew.setDepartureDeptAddr(orgAdministrativeInfoEntity.getAddress());
					//出发部门电话
					waybillInfoDtoNew.setDepartureDeptPhone(orgAdministrativeInfoEntity.getDepTelephone());
					//出发部门传真
					waybillInfoDtoNew.setDepartureDeptFax(orgAdministrativeInfoEntity.getDepFax());
				}
				
				/**
				 * 根据BUGKD-1616 修复 运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
				 */
				//根据编码查询收货部门
				OrgAdministrativeInfoEntity org = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if(org != null){
					//收货部门名称
					waybillInfoDtoNew.setReceiveOrgName(org.getName());
					//收货部门标杆编码
					waybillInfoDtoNew.setReceiveOrgNumber(org.getUnifiedCode());
					
				}
					
				//根据编码查询提货网点
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCustomerPickupOrgCode());	
				if(orgAdministrativeInfoEntity1 != null){
					//到达网点名称
					waybillInfoDtoNew.setLadingStationName(orgAdministrativeInfoEntity1.getName());
					//到达网点标杆编码
					waybillInfoDtoNew.setLadingStationNumber(orgAdministrativeInfoEntity1.getUnifiedCode());
					//到达网点地址
					waybillInfoDtoNew.setLadingStationAddr(orgAdministrativeInfoEntity1.getAddress());
					//到达网点电话
					waybillInfoDtoNew.setLadingStationPhone(orgAdministrativeInfoEntity1.getDepTelephone());
					//到达网点传真
					waybillInfoDtoNew.setLadingStationFax(orgAdministrativeInfoEntity1.getDepFax());
				}else{
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					outerBranchEntity.setAgentDeptCode(waybillInfoDtoNew.getCustomerPickupOrgCode());
					outerBranchEntity.setActive(FossConstants.ACTIVE);
					List<OuterBranchEntity> outList = commonAgencyDeptDao.queryAgencyDeptsByCondition(outerBranchEntity, PAGE_SIZE, BEGIN_NUM);
					//若outList不为空的话
					if (!CollectionUtils.isEmpty(outList))
					{
						OuterBranchEntity outerBranchNew = outList.get(0);
						if(outerBranchNew != null)
						{
							//到达网点名称
							waybillInfoDtoNew.setLadingStationName(outerBranchNew.getAgentDeptName());
							//到达网点标杆编码
							waybillInfoDtoNew.setLadingStationNumber(outerBranchNew.getAgentDeptCode());
							//到达网点地址
							waybillInfoDtoNew.setLadingStationAddr(outerBranchNew.getAddress());
							//到达网点电话
							waybillInfoDtoNew.setLadingStationPhone(outerBranchNew.getContactPhone());
						}
					}
				}
				if(waybillInfoDtoNew.getSignSituation() != null){
					if(SignConstants.NORMAL_SIGN.equals(waybillInfoDtoNew.getSignSituation()) && 
							!ArriveSheetConstants.SITUATION_UNNORMAL_ABANDONGOODS.equals(waybillInfoDtoNew.getSignSituation())){
						//是否正常签收
						waybillInfoDtoNew.setNormalSigned(true);
					}else{
						//是否正常签收
						waybillInfoDtoNew.setNormalSigned(false);
					}
					//是否签收
					waybillInfoDtoNew.setSigned(true);
				}else{
					//是否签收
					waybillInfoDtoNew.setSigned(false);
				}
				
				
				//拼接得到客户地址 省-市-区县-具体地址
				
				
				
				String deliveryCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getDeliveryCustomerProvCode(),
						waybillInfoDtoNew.getDeliveryCustomerCityCode(), waybillInfoDtoNew.getDeliveryCustomerDistCode(),
						waybillInfoDtoNew.getDeliveryCustomerAddress());
				waybillInfoDtoNew.setDeliveryCustomerAddress(deliveryCustomerAddress);
				
				
				
				//拼接得到客户地址 省-市-区县-具体地址
				
				
				String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getReceiveCustomerProvCode(),
						waybillInfoDtoNew.getReceiveCustomerCityCode(), waybillInfoDtoNew.getReceiveCustomerDistCode(), 
						waybillInfoDtoNew.getReceiveCustomerAddress());
				waybillInfoDtoNew.setReceiveCustomerAddress(receiveCustomerAddress);
		
					WaybillQueryCityProvinceDto provDto = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCreateOrgCode());
					
					if(provDto!=null){
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityName1(provDto.getCityName());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvName(provDto.getProvName());
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityCode1(provDto.getCityCode());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvCode(provDto.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto.getCountyCode())){
							waybillInfoDtoNew.setDeliveryCustomerDistCode(provDto.getCountyCode());
						}
					}
					
					WaybillQueryCityProvinceDto provDto2 = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCustomerPickupOrgCode());
					
					if(provDto2!=null){
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityName(provDto2.getCityName());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvName(provDto2.getProvName());
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityCode(provDto2.getCityCode());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvCode(provDto2.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto2.getCountyCode())){
							waybillInfoDtoNew.setReceiveCustomerDistCode(provDto2.getCountyCode());
						}
					}
					
				//}
				
				//第一次签收时间
				waybillInfoDtoNew.setFirstSignTime(waybillSignResultService.queryFirstSignTimeByWaybillNo(waybillInfoDtoNew.getWaybillNo()));
				//是否上门接货
				if(FossConstants.YES.equals(waybillInfoDtoNew.getPickupToDoor())){
					waybillInfoDtoNew.setPickup(true);
				}else{
					waybillInfoDtoNew.setPickup(false);
				}
				//运单状态
				waybillInfoDtoNew.setActive(waybillDao.queryWaybillStatus(waybillInfoDtoNew.getWaybillNo()));
				
				//根据编码查询配载部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getLoadOrgCode());
				if(orgAdministrativeInfoEntity2 != null){
					waybillInfoDtoNew.setLoadOrgNumber(orgAdministrativeInfoEntity2.getUnifiedCode());
				}
				
				//根据编码查询始发站
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity3 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getDeliveryCustomerCityCode());
				waybillInfoDtoNew.setDeliveryCustomerCityName(orgAdministrativeInfoEntity3.getCityName());
				
				waybillInfoDtoListNew.add(waybillInfoDtoNew);
			}
			return waybillInfoDtoListNew;
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * 根据运单集合返回运单详细信息
	 * @author 234773-xulixin
	 * @date 2015-07-02 10:14:03
	 */
	public List<WaybillInfoDto> queryWaybillInfoForQms(List<String> waybillList){
		if(CollectionUtils.isNotEmpty(waybillList)){
			WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();//创建对象
			waybillQueryInfoDto.setState(FossConstants.ACTIVE);
			waybillQueryInfoDto.setWaybillList(waybillList);
			List<WaybillInfoDto> waybillInfoDtoList = waybillDao.queryWaybillInfoForQms(waybillQueryInfoDto);
			List<WaybillInfoDto> waybillInfoDtoListNew = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDtoNew = null;
			for (WaybillInfoDto waybillInfoDto : waybillInfoDtoList){	
				waybillInfoDtoNew = new WaybillInfoDto();
				try {
					PropertyUtils.copyProperties(waybillInfoDtoNew,waybillInfoDto);
				} catch (IllegalAccessException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (InvocationTargetException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (NoSuchMethodException e) {
					//出现异常
					LOGGER.error("error", e);
				}
				
				//小件=------------------- ----
				//出发网点点部code
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity0 = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getStartExpressOrgCode());
				if(orgAdministrativeInfoEntity0!=null){
					waybillInfoDtoNew.setStartExpressUnfiedCode(orgAdministrativeInfoEntity0.getUnifiedCode());//标杆编码
					
					List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
						waybillInfoDtoNew.getStartExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
					
					if(list!=null && list.size()>0){
						OrgAdministrativeInfoEntity entity = list.get(0);
						waybillInfoDtoNew.setDistrictCode(entity.getCode());
						waybillInfoDtoNew.setDistrictName(entity.getName());
						waybillInfoDtoNew.setDistrictUnifiedCode(entity.getUnifiedCode());	
					}
					
				}
				
				
				ExpressPartSalesDeptResultDto dtoExp =null;
				SignDto dto = waybillDao.getWaybillSignInfo(waybillInfoDto.getWaybillNo());
				if(dto!=null){
					boolean isFromCreate = false;
					if(StringUtils.isNotEmpty(dto.getEmpCode())){
					
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getEmpCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getEmpName());
					}else{
						
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getCreateUserCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getCreateUserName());
					}
					
					if(StringUtils.isNotEmpty(dto.getOrgCode())){
						waybillInfoDtoNew.setEndExpressOrgCode(dto.getOrgCode());
						waybillInfoDtoNew.setEndExpressOrgName(dto.getOrgName());
					}else{
						isFromCreate = true;
						
						dtoExp= expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
								(dto.getCreateOrgCode(), new Date());
						if(dtoExp!=null){
							waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
					}
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){
						
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressUnfiedCode(tmp.getUnifiedCode());
						}
					}
					
					
					
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){
							//快递到达大区
							List<OrgAdministrativeInfoEntity> list = 
									orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
											waybillInfoDtoNew.getEndExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
							
							if(list!=null && list.size()>0){
								OrgAdministrativeInfoEntity entity = list.get(0);
								waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
								waybillInfoDtoNew.setEndDistrictName(entity.getName());
								waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
							}
					}
				}else{
					//在没有签收的情况下 返回快递提货网点的快递点部信息
					String customerPickUpOrgCode = waybillInfoDtoNew.getCustomerPickupOrgCode();
					dtoExp = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
							(customerPickUpOrgCode, new Date());
					
					
					if(dtoExp!=null){
						
						waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
						
						if(StringUtils.isNotEmpty(dtoExp.getPartCode())){
						
							List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
									dtoExp.getPartCode(), BizTypeConstants.EXPRESS_BIG_REGION);
								
								if(list!=null && list.size()>0){
									OrgAdministrativeInfoEntity entity = list.get(0);
									
									waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
									waybillInfoDtoNew.setEndDistrictName(entity.getName());
									waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
									
								}
							}
						}
					
					
				}
				
				//根据编码查询出发部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getCreateOrgCode());
				if(orgAdministrativeInfoEntity != null){
					//出发部门名称
					waybillInfoDtoNew.setDepartureDeptName(orgAdministrativeInfoEntity.getName());
					//出发部门标杆编码
					waybillInfoDtoNew.setDepartureDeptNumber(orgAdministrativeInfoEntity.getUnifiedCode());
					//出发部门地址
					waybillInfoDtoNew.setDepartureDeptAddr(orgAdministrativeInfoEntity.getAddress());
					//出发部门电话
					waybillInfoDtoNew.setDepartureDeptPhone(orgAdministrativeInfoEntity.getDepTelephone());
					//出发部门传真
					waybillInfoDtoNew.setDepartureDeptFax(orgAdministrativeInfoEntity.getDepFax());
				}
				
				/**
				 * 根据BUGKD-1616 修复 运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
				 */
				//根据编码查询收货部门
				OrgAdministrativeInfoEntity org = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getReceiveOrgCode());
				if(org != null){
					//收货部门名称
					waybillInfoDtoNew.setReceiveOrgName(org.getName());
					//收货部门标杆编码
					waybillInfoDtoNew.setReceiveOrgNumber(org.getUnifiedCode());
				}
				
				//根据编码查询提货网点
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getCustomerPickupOrgCode());	
				if(orgAdministrativeInfoEntity1 != null){
					//到达网点名称
					waybillInfoDtoNew.setLadingStationName(orgAdministrativeInfoEntity1.getName());
					//到达网点标杆编码
					waybillInfoDtoNew.setLadingStationNumber(orgAdministrativeInfoEntity1.getUnifiedCode());
					//到达网点地址
					waybillInfoDtoNew.setLadingStationAddr(orgAdministrativeInfoEntity1.getAddress());
					//到达网点电话
					waybillInfoDtoNew.setLadingStationPhone(orgAdministrativeInfoEntity1.getDepTelephone());
					//到达网点传真
					waybillInfoDtoNew.setLadingStationFax(orgAdministrativeInfoEntity1.getDepFax());
				}else{
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					outerBranchEntity.setAgentDeptCode(waybillInfoDtoNew.getCustomerPickupOrgCode());
					outerBranchEntity.setActive(FossConstants.ACTIVE);
					List<OuterBranchEntity> outList = commonAgencyDeptDao.queryAgencyDeptsByCondition(outerBranchEntity, PAGE_SIZE, BEGIN_NUM);
					//若outList不为空的话
					if (!CollectionUtils.isEmpty(outList))
					{
						OuterBranchEntity outerBranchNew = outList.get(0);
						if(outerBranchNew != null)
						{
							//到达网点名称
							waybillInfoDtoNew.setLadingStationName(outerBranchNew.getAgentDeptName());
							//到达网点标杆编码
							waybillInfoDtoNew.setLadingStationNumber(outerBranchNew.getAgentDeptCode());
							//到达网点地址
							waybillInfoDtoNew.setLadingStationAddr(outerBranchNew.getAddress());
							//到达网点电话
							waybillInfoDtoNew.setLadingStationPhone(outerBranchNew.getContactPhone());
						}
					}
				}
				if(waybillInfoDtoNew.getSignSituation() != null){
					if(SignConstants.NORMAL_SIGN.equals(waybillInfoDtoNew.getSignSituation())){
						//是否正常签收
						waybillInfoDtoNew.setNormalSigned(true);
					}else{
						//是否正常签收
						waybillInfoDtoNew.setNormalSigned(false);
					}
					//是否签收
					waybillInfoDtoNew.setSigned(true);
				}else{
					//是否签收
					waybillInfoDtoNew.setSigned(false);
				}
				
				//拼接得到客户地址 省-市-区县-具体地址
				String deliveryCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getDeliveryCustomerProvCode(),
						waybillInfoDtoNew.getDeliveryCustomerCityCode(), waybillInfoDtoNew.getDeliveryCustomerDistCode(),
						waybillInfoDtoNew.getDeliveryCustomerAddress());
				waybillInfoDtoNew.setDeliveryCustomerAddress(deliveryCustomerAddress);
				//拼接得到客户地址 省-市-区县-具体地址
				
				String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getReceiveCustomerProvCode(),
						waybillInfoDtoNew.getReceiveCustomerCityCode(), waybillInfoDtoNew.getReceiveCustomerDistCode(), 
						waybillInfoDtoNew.getReceiveCustomerAddress());
				waybillInfoDtoNew.setReceiveCustomerAddress(receiveCustomerAddress);
		
					WaybillQueryCityProvinceDto provDto = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCreateOrgCode());
					
					if(provDto!=null){
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityName1(provDto.getCityName());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvName(provDto.getProvName());
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityCode1(provDto.getCityCode());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvCode(provDto.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto.getCountyCode())){
							waybillInfoDtoNew.setDeliveryCustomerDistCode(provDto.getCountyCode());
						}
					}
					
					
					/**
				if(!StringUtils.isEmpty(waybillInfoDtoNew.getReceiveCustomerCityCode())
						&& !StringUtils.isEmpty(waybillInfoDtoNew.getReceiveCustomerProvCode()) ){
					
					//收货人城市名称
					waybillInfoDtoNew.setReceiveCustomerCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(waybillInfoDtoNew.getReceiveCustomerCityCode()));
					//收货人省份名称
					waybillInfoDtoNew.setReceiveCustomerProvName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(waybillInfoDtoNew.getReceiveCustomerProvCode()));
					
				}else{*/
					WaybillQueryCityProvinceDto provDto2 = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCustomerPickupOrgCode());
					
					if(provDto2!=null){
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityName(provDto2.getCityName());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvName(provDto2.getProvName());
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityCode(provDto2.getCityCode());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvCode(provDto2.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto2.getCountyCode())){
							waybillInfoDtoNew.setReceiveCustomerDistCode(provDto2.getCountyCode());
						}
					}
					
				//}
				
				//第一次签收时间
				waybillInfoDtoNew.setFirstSignTime(waybillSignResultService.queryFirstSignTimeByWaybillNo(waybillInfoDtoNew.getWaybillNo()));
				//是否上门接货
				if(FossConstants.YES.equals(waybillInfoDtoNew.getPickupToDoor())){
					waybillInfoDtoNew.setPickup(true);
				}else{
					waybillInfoDtoNew.setPickup(false);
				}
				//运单状态
				waybillInfoDtoNew.setActive(waybillDao.queryWaybillStatus(waybillInfoDtoNew.getWaybillNo()));
				
				//根据编码查询配载部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getLoadOrgCode());
				if(orgAdministrativeInfoEntity2 != null){
					waybillInfoDtoNew.setLoadOrgNumber(orgAdministrativeInfoEntity2.getUnifiedCode());
				}
				
				//根据编码查询始发站
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity3 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeNoCache(waybillInfoDtoNew.getDeliveryCustomerCityCode());
				waybillInfoDtoNew.setDeliveryCustomerCityName(orgAdministrativeInfoEntity3.getCityName());
				
				waybillInfoDtoListNew.add(waybillInfoDtoNew);
			}
			return waybillInfoDtoListNew;
		}
		return null;
	}
	
	/**
	 * 
	 * 根据运单号查看该运单是否快递
	 * @author 234773-qms-xulixin
	 * @date 2015-07-11 17:14:03
	 */
	@Override
	public String getIsExpressByWayBillNo(String waybillNo) {
		return this.waybillDao.getIsExpressByWayBillNo(waybillNo);
	}
	
	/**
	 * 根据运单号或运单号流水号查询走货路径明细
	 * @author 234773-xulixin
	 * @date 2015-08-20 9:49:20
	 * @param waybill
	 * @return
	 */
	@Override
	public String queryPathDetailByNos(String waybillNo) {
		List<PathDetailEntity> l = this.pathDetailDao.queryPathDetailByNos(waybillNo,null);
		return JSONObject.toJSON(l).toString();
	}
	/**
	 * 
	 * <p>
	 * 查询运单状态<br />
	 * </p>
	 * 
	 * @author 苏玉军
	 * @version 0.1 2013-1-7
	 * @param waybillNo
	 * @return String
	 */
	public String queryWaybillStatus(String waybillNo) {
		String status = "";
		if(!StringUtils.isEmpty(waybillNo)){
			//调用存储过程问题未测试通过
			status =  waybillDao.queryWaybillStatus(waybillNo);
		}
		return status;// "在途中"

	}
	
	/**
	 * 
	 * 查询记录数
	 * 
	 * @param adjustPlanSearcherDto
	 * @return
	 * @author ibm-wangfei
	 * @date Jan 16, 2013 2:16:30 PM
	 */
	public Long queryAdjustPlanCount(AdjustPlanSearcherDto adjustPlanSearcherDto) {
		return waybillDao.queryAdjustPlanCount(adjustPlanSearcherDto);
	}
	
	/**
	 * 自定义查询
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-1 上午9:49:24
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService#queryWayBillListByUserDefinedQuery(java.lang.String, int)
	 */
	public List<SimpleWaybillDto> queryWayBillListByUserDefinedQuery(String where, int count) {
		SimpleWaybillDto simpleWaybillDto = new SimpleWaybillDto();
		simpleWaybillDto.setWhereSql(where);
		simpleWaybillDto.setActive(FossConstants.ACTIVE);
		return waybillDao.queryWayBillListByUserDefinedQuery(simpleWaybillDto, count);
	}
	
	public OrgAdministrativeInfoEntity queryHisOrgInfoByCode(String code, Date billTime){
		return orgAdministrativeInfoService.queryOrgInfoByCodeAndTime(code, billTime);
	}
	
	private IExpressPartSalesDeptService expressPartSalesDeptService;



	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}
	
	/**
	 * 根据客户编码，开始和结束时间查询运单详细信息
	 * @author 叶涛
	 */
	@Override
	public List<WaybillOneYearDto> queryWaybillInfoOneYear(
			String deliveryCustomerCode, Date startTime,
			Date endTime) {
		WaybillQueryOneYearDto waybillQueryOneYearDto = new WaybillQueryOneYearDto();//创建对象
		waybillQueryOneYearDto.setState(FossConstants.ACTIVE);
		waybillQueryOneYearDto.setDeliveryCustomerCode(deliveryCustomerCode);
		waybillQueryOneYearDto.setStartTime(startTime);
		waybillQueryOneYearDto.setEndTime(endTime);
		List<WaybillOneYearDto> waybillOneYearDtoList= waybillDao.queryWaybillOneYear(waybillQueryOneYearDto);
		List<WaybillOneYearDto> waybillOneYearDtoListNew=new ArrayList<WaybillOneYearDto> ();
		for(WaybillOneYearDto waybillOneYearDto : waybillOneYearDtoList){
		WaybillQueryCityProvinceDto provDto = waybillExpressDao.queryCityProvince(waybillOneYearDto.getCreateOrgCode());
		WaybillQueryCityProvinceDto provDto2 = waybillExpressDao.queryCityProvince(waybillOneYearDto.getCustomerPickupOrgCode());
		if(provDto!=null){
			waybillOneYearDto.setDeliveryCustomerCityName(provDto.getCityName());
		}
		if(provDto2!=null){
			waybillOneYearDto.setReceiveCustomerCityName(provDto2.getCityName());
		}
		
		waybillOneYearDtoListNew.add(waybillOneYearDto);
		}
		
		return waybillOneYearDtoListNew;
	}
	
	/**
	 * 切割字符串 DMANA-4340
	 * @author 董思伟
	 * 这个方法是为了实现营业报表清单实现多选查询。
	 * 从productCode中获取的类似“all” 、“AF,”字符串的运输性质进行分割
	 */
	public List<String> splitString(String productCode){
		List<String> list=new ArrayList<String>();
		if(productCode.endsWith(",")){
			productCode="all";
		}
		String [] productCodesArr=productCode.split(",");
		for(int i=0;i<productCodesArr.length;i++){
			if(productCodesArr[i].equals("")){
				productCodesArr[i]="all";
			}
		}
		for(int i=0;i<productCodesArr.length;i++){
			if(productCodesArr[i].equals("all")){
				list=null;
				break;
			}else{
				list.add(productCodesArr[i]);
			}
		}
		return list;

	}
	
	private List<AppWayBillDetaillVo> convertToAppWayBillDetaillVo(List<CrmWaybillServiceDto> list){
		LOGGER.info("开始自动对象转换：CrmWaybillServiceDto--->AppWayBillDetaillVo") ;
		List<AppWayBillDetaillVo> targetList = new ArrayList<AppWayBillDetaillVo>();
		try {
			for(CrmWaybillServiceDto dto : list){
				AppWayBillDetaillVo info = new AppWayBillDetaillVo();
				info.setConsigneeAddress(dto.getConsigneeAddress());
				info.setConsigneeMobile(dto.getConsigneeMobile());
				info.setConsigneeName(dto.getConsignee());
				info.setConsigneetel(dto.getConsigneePhone());
				info.setCubage(dto.getCubage());
				info.setDeliveryCharge(dto.getDeliveryCharge());
				info.setDeliveryMode(dto.getDeliveryType());
				info.setDeparture(dto.getDeparture());
				info.setDepartureAddress(dto.getDepartureDeptAddr());
				info.setDepartureName(dto.getDepartureDeptName());
				info.setDeparturetel(dto.getDepartureDeptPhone());
				info.setDestination(dto.getDestination());
				info.setGoodsName(dto.getGoodName());
				info.setInsurance(dto.getInsurance());
				info.setInsuranceFee(dto.getInsuranceValue());
				info.setOrderNum(dto.getOrderNumber());
				info.setOtherCharge(dto.getOtherFee());
				info.setPacking(dto.getPacking());
				info.setPackingCharge(dto.getPackingFee());
				info.setPayWay(dto.getPayment());
				info.setPieces(dto.getPieces());
				info.setReceiveCharge(dto.getPickCharge());
				info.setRefund(dto.getRefund());
				info.setRefundFee(dto.getRefundFee());
				info.setRefundType(dto.getRefundType());
				info.setReturnBillType(dto.getSignBackType());
				info.setSendDate(dto.getSendTime());
				info.setShipperAddress(dto.getDepartureDeptAddr());
				info.setShipperMobile(dto.getSenderMobile());
				info.setShipperName(dto.getSender());
				info.setShippertel(dto.getSenderPhone());
				info.setSignedDate(dto.getSignedDate());
				info.setStationaddress(dto.getLadingStationAddr());
				info.setStationtel(dto.getLadingStationPhone());
				info.setStationName(dto.getLadingStationName());
				info.setWayBillState(dto.getWaybillStatus());
				info.setWaybillNum(dto.getWaybillNo());
				info.setPayWay(dto.getPaidMethod());
				info.setTotalCharge(dto.getTotalCharge());
				info.setTransProperties(dto.getTransProperties());
				info.setTranCharge(dto.getTransportFee());
				if(dto.getWeight()!=null){
					info.setWeight(dto.getWeight());
				}
				info.setDepartureFax(dto.getDepartureDeptFax());
				info.setDestinationFax(dto.getLadingStationFax());
				info.setDepartureCityName(dto.getDepartureCityName());
				info.setDestinationCityName(dto.getDestinationCityName());
				info.setPredictArriveTime(dto.getPredictArriveTime());
				targetList.add(info);
			}
		} catch (Exception e) {
			LOGGER.error("CrmWaybillServiceDto对象自动转换为AppWayBillDetaillVo对象失败！");
			throw new WaybillQueryException("对象自动转换失败！");
		}
		LOGGER.info("自动对象转换结束：CrmWaybillServiceDto--->AppWayBillDetaillVo") ;
		return targetList;
	}
	

	/**
	* 官网查询子母件信息接口
	* 
	* @Method: queryWaybillInfoForOfficialCP 
	* @param waybillList
	* @return 
	* @author WangZengming
	* @date 2015-9-1 下午2:58:36
	* @see
	*/
	@Override
	public List<WaybillInfoDto> queryWaybillInfoForOfficialCP(
			List<String> waybillList) {
		if(CollectionUtils.isNotEmpty(waybillList)){
			List<WaybillInfoDto> waybillInfoDtoList = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDto = null;
			for(String waybillNo : waybillList){
				waybillInfoDto = new WaybillInfoDto();
				//判断是否是子母件
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo",waybillNo);
				params.put("active",FossConstants.ACTIVE);
				//根据运单号查出子母件信息
				List<WaybillRelateDetailEntity> waybillRelateDetailList = waybillRelateDetailEntityDao.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
				//是子母件
				if(CollectionUtils.isNotEmpty(waybillRelateDetailList)){
					//是否子母件
					waybillInfoDto.setParentChildWaybill(true);
					//母件单号
					waybillInfoDto.setParentWaybillNo(waybillRelateDetailList.get(0).getParentWaybillNo());
					//子单号集合
					List<String> childWaybillNoList = waybillRelateDetailEntityDao.queryWaybillNoListByParentWaybillNo(waybillInfoDto.getParentWaybillNo());
					waybillInfoDto.setChildWaybillNoList(childWaybillNoList);
				}else{
					waybillInfoDto.setParentChildWaybill(false);
				}
				//运单号
				waybillInfoDto.setWaybillNo(waybillNo);
				waybillInfoDtoList.add(waybillInfoDto);
			}
			return waybillInfoDtoList;
		}
		return null;
	}
	
	
	/**
	* 根据运单集合返回运单详细信息
	* 
	* @Method: queryWaybillInfoForSOC 
	* @param waybillList
	* @return List<WaybillInfoDto>
	* @author WangZengming
	* @date 2015-8-31 下午2:43:51
	* @see
	*/
	
	public List<WaybillInfoDto> queryWaybillInfoForSOC(List<String> waybillList){
		if(CollectionUtils.isNotEmpty(waybillList)){
			WaybillQueryInfoDto waybillQueryInfoDto = new WaybillQueryInfoDto();//创建对象
			waybillQueryInfoDto.setState(FossConstants.ACTIVE);
			waybillQueryInfoDto.setWaybillList(waybillList);
			List<WaybillInfoDto> waybillInfoDtoList = waybillDao.queryWaybillInfoForSOC(waybillQueryInfoDto);
			List<WaybillInfoDto> waybillInfoDtoListNew = new ArrayList<WaybillInfoDto>();
			WaybillInfoDto waybillInfoDtoNew = null;
			for (WaybillInfoDto waybillInfoDto : waybillInfoDtoList){	
				waybillInfoDtoNew = new WaybillInfoDto();
				try {
					PropertyUtils.copyProperties(waybillInfoDtoNew,waybillInfoDto);
				} catch (IllegalAccessException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (InvocationTargetException e) {
					//出现异常
					LOGGER.error("error", e);
				} catch (NoSuchMethodException e) {
					//出现异常
					LOGGER.error("error", e);
				}
				
				//小件=------------------- ----
				//出发网点点部code
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity0 = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getStartExpressOrgCode());
				if(orgAdministrativeInfoEntity0!=null){
					waybillInfoDtoNew.setStartExpressUnfiedCode(orgAdministrativeInfoEntity0.getUnifiedCode());//标杆编码
					
					List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
						waybillInfoDtoNew.getStartExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
					
					if(list!=null && list.size()>0){
						OrgAdministrativeInfoEntity entity = list.get(0);
						waybillInfoDtoNew.setDistrictCode(entity.getCode());
						waybillInfoDtoNew.setDistrictName(entity.getName());
						waybillInfoDtoNew.setDistrictUnifiedCode(entity.getUnifiedCode());	
					}
					
				}
				
				
				ExpressPartSalesDeptResultDto dtoExp =null;
				SignDto dto = waybillDao.getWaybillSignInfo(waybillInfoDto.getWaybillNo());
				if(dto!=null){
					if(StringUtils.isNotEmpty(dto.getEmpCode())){
					
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getEmpCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getEmpName());
					}else{
						
						waybillInfoDtoNew.setEndExpressEmpCode(dto.getCreateUserCode());
						waybillInfoDtoNew.setEndExpressEmpName(dto.getCreateUserName());
					}
					
					if(StringUtils.isNotEmpty(dto.getOrgCode())){
						waybillInfoDtoNew.setEndExpressOrgCode(dto.getOrgCode());
						waybillInfoDtoNew.setEndExpressOrgName(dto.getOrgName());
					}else{
						dtoExp= expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
								(dto.getCreateOrgCode(), new Date());
						if(dtoExp!=null){
							waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
					}
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){
						
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressUnfiedCode(tmp.getUnifiedCode());
						}
					}
					
					
					
					if(StringUtils.isNotEmpty(waybillInfoDtoNew.getEndExpressOrgCode())){

							List<OrgAdministrativeInfoEntity> list = 
									orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
											waybillInfoDtoNew.getEndExpressOrgCode(), BizTypeConstants.EXPRESS_BIG_REGION);
							
							if(list!=null && list.size()>0){
								OrgAdministrativeInfoEntity entity = list.get(0);
								waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
								waybillInfoDtoNew.setEndDistrictName(entity.getName());
								waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
							}
					}
					
					
				}else{
					//在没有签收的情况下 返回快递提货网点的快递点部信息
					String customerPickUpOrgCode = waybillInfoDtoNew.getCustomerPickupOrgCode();
					dtoExp = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime
							(customerPickUpOrgCode, new Date());
					
					
					if(dtoExp!=null){
						
						waybillInfoDtoNew.setEndExpressOrgCode(dtoExp.getPartCode());
						OrgAdministrativeInfoEntity tmp = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getEndExpressOrgCode());
						if(tmp!=null){
							waybillInfoDtoNew.setEndExpressOrgName(dtoExp.getPartName());
						}
						
						if(StringUtils.isNotEmpty(dtoExp.getPartCode())){
						
							List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoUpByBizType(
									dtoExp.getPartCode(), BizTypeConstants.EXPRESS_BIG_REGION);
								
								if(list!=null && list.size()>0){
									OrgAdministrativeInfoEntity entity = list.get(0);
									
									waybillInfoDtoNew.setEndDistrictCode(entity.getCode());
									waybillInfoDtoNew.setEndDistrictName(entity.getName());
									waybillInfoDtoNew.setEndDistrictUnifiedCode(entity.getUnifiedCode());
									
								}
							}
						}
					
					
				}
				
				//小件=-------------------11111111
				
				//根据编码查询出发部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCreateOrgCode());
				if(orgAdministrativeInfoEntity != null){
					//出发部门名称
					waybillInfoDtoNew.setDepartureDeptName(orgAdministrativeInfoEntity.getName());
					//出发部门标杆编码
					waybillInfoDtoNew.setDepartureDeptNumber(orgAdministrativeInfoEntity.getUnifiedCode());
					//出发部门地址
					waybillInfoDtoNew.setDepartureDeptAddr(orgAdministrativeInfoEntity.getAddress());
					//出发部门电话
					waybillInfoDtoNew.setDepartureDeptPhone(orgAdministrativeInfoEntity.getDepTelephone());
					//出发部门传真
					waybillInfoDtoNew.setDepartureDeptFax(orgAdministrativeInfoEntity.getDepFax());
				}
				
				/**
				 * 根据BUGKD-1616 修复 运单查询明细接口：收货部门对应值取值错误，应该取收入部门（即收货部门）对应的值。
				 */
				//根据编码查询收货部门
				OrgAdministrativeInfoEntity org = 
						orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getReceiveOrgCode());
				if(org != null){
					//收货部门名称
					waybillInfoDtoNew.setReceiveOrgName(org.getName());
					//收货部门标杆编码
					waybillInfoDtoNew.setReceiveOrgNumber(org.getUnifiedCode());
				}
				
				//根据编码查询提货网点
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity1 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getCustomerPickupOrgCode());	
				if(orgAdministrativeInfoEntity1 != null){
					//到达网点名称
					waybillInfoDtoNew.setLadingStationName(orgAdministrativeInfoEntity1.getName());
					//到达网点标杆编码
					waybillInfoDtoNew.setLadingStationNumber(orgAdministrativeInfoEntity1.getUnifiedCode());
					//到达网点地址
					waybillInfoDtoNew.setLadingStationAddr(orgAdministrativeInfoEntity1.getAddress());
					//到达网点电话
					waybillInfoDtoNew.setLadingStationPhone(orgAdministrativeInfoEntity1.getDepTelephone());
					//到达网点传真
					waybillInfoDtoNew.setLadingStationFax(orgAdministrativeInfoEntity1.getDepFax());
				}else{
					OuterBranchEntity outerBranchEntity = new OuterBranchEntity();
					outerBranchEntity.setAgentDeptCode(waybillInfoDtoNew.getCustomerPickupOrgCode());
					outerBranchEntity.setActive(FossConstants.ACTIVE);
					List<OuterBranchEntity> outList = commonAgencyDeptDao.queryAgencyDeptsByCondition(outerBranchEntity, PAGE_SIZE, BEGIN_NUM);
					//若outList不为空的话
					if (!CollectionUtils.isEmpty(outList))
					{
						OuterBranchEntity outerBranchNew = outList.get(0);
						if(outerBranchNew != null)
						{
							//到达网点名称
							waybillInfoDtoNew.setLadingStationName(outerBranchNew.getAgentDeptName());
							//到达网点标杆编码
							waybillInfoDtoNew.setLadingStationNumber(outerBranchNew.getAgentDeptCode());
							//到达网点地址
							waybillInfoDtoNew.setLadingStationAddr(outerBranchNew.getAddress());
							//到达网点电话
							waybillInfoDtoNew.setLadingStationPhone(outerBranchNew.getContactPhone());
						}
					}
				}
				
				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("waybillNo",waybillInfoDtoNew.getWaybillNo());
				params.put("active",FossConstants.ACTIVE);
				//根据运单号查出子母件信息
				List<WaybillRelateDetailEntity> waybillRelateDetailList = waybillRelateDetailEntityDao.queryWaybillRelateDetailListByOrderOrWaybillNo(params);
				//是子母件
				if(CollectionUtils.isNotEmpty(waybillRelateDetailList)){
					//是否子母件
					waybillInfoDtoNew.setParentChildWaybill(true);
					//母件单号
					waybillInfoDtoNew.setParentWaybillNo(waybillRelateDetailList.get(0).getParentWaybillNo());
				}else{
					waybillInfoDtoNew.setParentChildWaybill(false);
				}
				
				//验证签收情况
				List<WaybillSignDetailVo> waybillSignList = null;
				List<String> waybillNoList = null;
				if(StringUtils.isNotEmpty(waybillInfoDtoNew.getParentWaybillNo()))
					waybillNoList = waybillRelateDetailEntityDao.queryWaybillNoListByParentWaybillNo(waybillInfoDtoNew.getParentWaybillNo());
				if(waybillInfoDtoNew.getSignSituation() != null){
					waybillInfoDtoNew.setNormalSigned(false);
					waybillInfoDtoNew.setAbnormalSigned(false);
					waybillInfoDtoNew.setAbandonGoodsSigned(false);
					
					//当单号为母件的情况
					if(CollectionUtils.isNotEmpty(waybillRelateDetailList)&&waybillInfoDtoNew.getWaybillNo().equals(waybillInfoDtoNew.getParentWaybillNo())){
						waybillSignList = waybillDao.getWaybillSignList(waybillNoList);
					}
					
					if(SignConstants.NORMAL_SIGN.equals(waybillInfoDtoNew.getSignSituation())){
						//非子母件是否正常签收
						if(CollectionUtils.isEmpty(waybillSignList)){
							waybillInfoDtoNew.setNormalSigned(true);
						}else{
							//子母件是否全部正常签收
							if(validateSignStatus(waybillSignList,SignConstants.NORMAL_SIGN)){
								waybillInfoDtoNew.setNormalSigned(true);
							}
						}
					}
					//是否存在异常签收--理赔申请判断的依据
					waybillInfoDtoNew.setAbnormalSigned(!waybillInfoDtoNew.isNormalSigned());
					//返单、子单签收、母件全部签收
					if(CollectionUtils.isEmpty(waybillNoList)
						||(CollectionUtils.isNotEmpty(waybillNoList)&&!waybillInfoDtoNew.getWaybillNo().equals(waybillInfoDtoNew.getParentWaybillNo()))
							||(CollectionUtils.isNotEmpty(waybillNoList)&&CollectionUtils.isNotEmpty(waybillSignList)&&waybillSignList.size() >= waybillNoList.size())){
						waybillInfoDtoNew.setSigned(true);
					}
				}else{
					waybillInfoDtoNew.setSigned(false);
				}
				//去除除母单号
				if(CollectionUtils.isNotEmpty(waybillNoList))
					waybillNoList.remove(waybillInfoDtoNew.getParentWaybillNo());
				//子单号集合
				waybillInfoDtoNew.setChildWaybillNoList(waybillNoList);
				//查询返货单号集合
				WaybillExpressEntity expressEntity = new WaybillExpressEntity();
				expressEntity.setWaybillNo(waybillInfoDtoNew.getWaybillNo());
				List<WaybillExpressEntity> waybillExpressEntityList = waybillExpressDao.queryWaybillReturnByWaybillNo(expressEntity);
				List<String> returnWaybillNoList = null;
				if(CollectionUtils.isNotEmpty(waybillExpressEntityList)){
					returnWaybillNoList = new ArrayList<String>();
					for(WaybillExpressEntity entity : waybillExpressEntityList){
						returnWaybillNoList.add(entity.getOriginalWaybillNo());
					}
				}
				//返货单号集合
				waybillInfoDtoNew.setReturnWaybillNoList(returnWaybillNoList);
				
				
				//拼接得到客户地址 省-市-区县-具体地址
				String deliveryCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getDeliveryCustomerProvCode(),
						waybillInfoDtoNew.getDeliveryCustomerCityCode(), waybillInfoDtoNew.getDeliveryCustomerDistCode(),
						waybillInfoDtoNew.getDeliveryCustomerAddress());
				waybillInfoDtoNew.setDeliveryCustomerAddress(deliveryCustomerAddress);
				//拼接得到客户地址 省-市-区县-具体地址
				
				
				String receiveCustomerAddress = handleQueryOutfieldService.getCompleteAddress(waybillInfoDtoNew.getReceiveCustomerProvCode(),
						waybillInfoDtoNew.getReceiveCustomerCityCode(), waybillInfoDtoNew.getReceiveCustomerDistCode(), 
						waybillInfoDtoNew.getReceiveCustomerAddress());
				waybillInfoDtoNew.setReceiveCustomerAddress(receiveCustomerAddress);

					WaybillQueryCityProvinceDto provDto = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCreateOrgCode());
					
					if(provDto!=null){
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityName1(provDto.getCityName());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvName(provDto.getProvName());
						//发货人城市名称
						waybillInfoDtoNew.setDeliveryCustomerCityCode1(provDto.getCityCode());
						//发货人省份名称
						waybillInfoDtoNew.setDeliveryCustomerProvCode(provDto.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto.getCountyCode())){
							waybillInfoDtoNew.setDeliveryCustomerDistCode(provDto.getCountyCode());
						}
					}

					WaybillQueryCityProvinceDto provDto2 = waybillExpressDao.queryCityProvince(waybillInfoDtoNew.getCustomerPickupOrgCode());
					
					if(provDto2!=null){
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityName(provDto2.getCityName());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvName(provDto2.getProvName());
						//收货人城市名称
						waybillInfoDtoNew.setReceiveCustomerCityCode(provDto2.getCityCode());
						//收货人省份名称
						waybillInfoDtoNew.setReceiveCustomerProvCode(provDto2.getProvCode());
						
						if(StringUtils.isNotEmpty(provDto2.getCountyCode())){
							waybillInfoDtoNew.setReceiveCustomerDistCode(provDto2.getCountyCode());
						}
					}
					
				//}
				
				//第一次签收时间
				waybillInfoDtoNew.setFirstSignTime(waybillSignResultService.queryFirstSignTimeByWaybillNo(waybillInfoDtoNew.getWaybillNo()));
				//是否上门接货
				if(FossConstants.YES.equals(waybillInfoDtoNew.getPickupToDoor())){
					waybillInfoDtoNew.setPickup(true);
				}else{
					waybillInfoDtoNew.setPickup(false);
				}
				
				//运单状态
				waybillInfoDtoNew.setActive(waybillDao.queryWaybillStatus(waybillInfoDtoNew.getWaybillNo()));
				
				//根据编码查询配载部门
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity2 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getLoadOrgCode());
				if(orgAdministrativeInfoEntity2 != null){
					waybillInfoDtoNew.setLoadOrgNumber(orgAdministrativeInfoEntity2.getUnifiedCode());
				}
				
				//根据编码查询始发站
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity3 = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(waybillInfoDtoNew.getDeliveryCustomerCityCode());
				waybillInfoDtoNew.setDeliveryCustomerCityName(orgAdministrativeInfoEntity3.getCityName());
				
				waybillInfoDtoListNew.add(waybillInfoDtoNew);
			}
			return waybillInfoDtoListNew;
		}
		return null;
	}
	

	/**
	 * 验证母件签收情况
	 * @param waybillSignList
	 * @return
	 * @author WangZengming
	 */
	private boolean validateSignStatus(
			List<WaybillSignDetailVo> waybillSignList,String signSituation) {
		boolean signFlag = false;
		if(CollectionUtils.isNotEmpty(waybillSignList)){
			boolean unNormalFlag = false;
			for(WaybillSignDetailVo vo : waybillSignList){
				String sign = vo.getSignStatus();
				if(!SignConstants.NORMAL_SIGN.equals(sign)){
					unNormalFlag = true;
					signFlag = false;
					break;
				}
			}
			if(!unNormalFlag)
				signFlag = true;
		}
		return signFlag;
	}

	/**
	 * @param codition: 封装的Map对象，其中key 分别为：startDate 开始时间，endDate 结束时间，
	 * phones 拼装好的客户电话字符串，mobilePhones 拼装好的客户手机字符串，isAssociatedConsignee 是否关联发货人
	 * @author liyongfei
	 * @date 2014-07-31
	 */
	@Override
	public List<WaybillCountDto> queryWaybillByPhone(Map<String,Object> condition){
		// TODO Auto-generated method stub
		return waybillDao.queryWaybillByPhone(condition);
	}

	@Override
	public ComponentDto queryComponentDetail(
			ReportComponentDto reportComponentDto) {
		// TODO Auto-generated method stub
		return waybillDao.queryComponentDetail(reportComponentDto);
	}

	@Override
	public List<BillingDto> queryBillingDetail(
			ReportBillingDto reportBillingDto) {
		return waybillDao.queryBillingDetail(reportBillingDto);
	}

	@Override
	public String getWoodenPackageOrgCode(String waybillNo) {
		return waybillDao.getWoodenPackageOrgCode(waybillNo);
	}

	public IPathDetailDao getPathDetailDao() {
		return pathDetailDao;
	}

	public void setPathDetailDao(IPathDetailDao pathDetailDao) {
		this.pathDetailDao = pathDetailDao;
	}
}