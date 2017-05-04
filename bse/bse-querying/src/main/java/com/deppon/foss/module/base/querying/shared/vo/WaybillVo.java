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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/shared/vo/WaybillVo.java
 * 
 * FILE NAME        	: WaybillVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.shared
 * FILE    NAME: WaybillVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.querying.shared.vo;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.querying.shared.domain.ArriveChanSignVo;
import com.deppon.foss.module.base.querying.shared.domain.HandlingCharges;
import com.deppon.foss.module.base.querying.shared.domain.Invoice;
import com.deppon.foss.module.base.querying.shared.domain.ReturnShipping;
import com.deppon.foss.module.base.querying.shared.domain.ServiceRemedy;
import com.deppon.foss.module.base.querying.shared.domain.WaybillEntity;
import com.deppon.foss.module.base.querying.shared.domain.WaybillSearchCondition;
import com.deppon.foss.module.base.querying.shared.dto.DeliveryInformationDto;
import com.deppon.foss.module.base.querying.shared.dto.QueryAgentInformationResultDto;
import com.deppon.foss.module.base.querying.shared.dto.QueryClaimbillResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReturnBillProcessEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverySituationDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentArriveDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillOtherForBseDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillStatusForBseDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LeaveChangeByWaybillNoResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;

/**
 * 运单前台视图.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-24 上午9:24:49
 */
public class WaybillVo {
	// 查询条件
	/** The condition. */
	private WaybillSearchCondition condition;
	/**
	 * CRM理赔信息
	 */
	private QueryClaimbillResultDto queryClaimbillResultDto; 

	/** 运单号 **/
	private String wayBillNo;
	
	/** 根据运单找到的出发部门 **/
	private String leaveDeptCode;
	



	/** 根据运单找到的到达部门 **/
	private String arriveDeptCode;
	
	/** 根据运单找到的开单时间 **/
	private Date billTime;
	

	/**特殊增值服务信息**/
	private List<DeliveryInformationDto> deliveryInformationDtos;
	
	/**合伙人派送弹窗提醒**/
	private String  ballotNum; //件数
	private String  packageNum;//票数

	public List<DeliveryInformationDto> getDeliveryInformationDtos() {
		return deliveryInformationDtos;
	}

	public void setDeliveryInformationDtos(
			List<DeliveryInformationDto> deliveryInformationDtos) {
		this.deliveryInformationDtos = deliveryInformationDtos;
	}


	/**系统的捕获的异常信息 **/
	private String ExceptionInfo;

	public String getExceptionInfo() {
		return ExceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		ExceptionInfo = exceptionInfo;
	}



	/**
	 * 获取 根据运单找到的开单时间 *.
	 *
	 * @return the 根据运单找到的开单时间 *
	 */
	public Date getBillTime() {
	    return billTime;
	}


	
	/**
	 * 设置 根据运单找到的开单时间 *.
	 *
	 * @param billTime the new 根据运单找到的开单时间 *
	 */
	public void setBillTime(Date billTime) {
	    this.billTime = billTime;
	}


	// 运单号集合
	/** The waybills. */
	private List<WaybillEntity> waybills;

	
	/**
	 * 获取 运单号 *.
	 *
	 * @return the 运单号 *
	 */
	public String getWayBillNo() {
	    return wayBillNo;
	}

	
	/**
	 * 设置 运单号 *.
	 *
	 * @param wayBillNo the new 运单号 *
	 */
	public void setWayBillNo(String wayBillNo) {
	    this.wayBillNo = wayBillNo;
	}

	
	/**
	 * 获取 根据运单找到的出发部门 *.
	 *
	 * @return the 根据运单找到的出发部门 *
	 */
	public String getLeaveDeptCode() {
	    return leaveDeptCode;
	}

	
	/**
	 * 设置 根据运单找到的出发部门 *.
	 *
	 * @param leaveDeptCode the new 根据运单找到的出发部门 *
	 */
	public void setLeaveDeptCode(String leaveDeptCode) {
	    this.leaveDeptCode = leaveDeptCode;
	}

	
	/**
	 * 获取 根据运单找到的到达部门 *.
	 *
	 * @return the 根据运单找到的到达部门 *
	 */
	public String getArriveDeptCode() {
	    return arriveDeptCode;
	}

	
	/**
	 * 设置 根据运单找到的到达部门 *.
	 *
	 * @param arriveDeptCode the new 根据运单找到的到达部门 *
	 */
	public void setArriveDeptCode(String arriveDeptCode) {
	    this.arriveDeptCode = arriveDeptCode;
	}


	// 运单出发更改记录
	/** The start change sign rfc dtos. */
	List<LeaveChangeByWaybillNoResultDto> startChangeSignRfcDtos;

	// 运单到达更改记录
	/** The arrive change sign rfc dtos. */
	private List<ArriveChanSignVo> arriveChangeSignRfcDtos;

	// 签收单
	/** The bill process entities. */
	private List<ReturnBillProcessEntity> billProcessEntities;

	// 财务信息
	/** The waybill settlement info dto. */
	private WaybillSettlementInfoDto waybillSettlementInfoDto;
	
	//财务情况
	private WaybillFinanceInfoDto waybillFinanceInfoDto;


	// 标签打印记录
	/** The goods label print dtos. */
	private List<GoodsLabelPrintDto> goodsLabelPrintDtos;
	
	// 派送情况    接送货  meiying  提供接口
	private DeliverySituationDto deliverySituationDto;
	
	//代理信息
	private List<QueryAgentInformationResultDto> queryAgentInformationResultDtos;
	
	//返货处理
	private List<ReturnGoodsRequestEntity> returnGoodsRequestEntitys;
	public List<ReturnGoodsRequestEntity> getReturnGoodsRequestEntitys() {
		return returnGoodsRequestEntitys;
	}

	public void setReturnGoodsRequestEntitys(
			List<ReturnGoodsRequestEntity> returnGoodsRequestEntitys) {
		this.returnGoodsRequestEntitys = returnGoodsRequestEntitys;
	}


	/** The general query dtos. */
	List<GeneralQueryDto> generalQueryDtos;


	/** The way bill other for bse dto. */
	private WayBillOtherForBseDto wayBillOtherForBseDto;

	/** The way bill status for bse dtos. */
	private List<WayBillStatusForBseDto> wayBillStatusForBseDtos;
	
	/**
	 * 
	 */
	private List<RepaymentArriveDto> repaymentArriveDtos;
	
	/**
	 * 
	 */
	private WaybillInfoByWaybillNoReultDto waybillInfoByWaybillNoReultDto;
	/************* 运单执行轨迹（中转） ****************/
	private List<WayBillNoLocusDTO> wayBIllNoLocusList;
	/************* 运单 跟踪记录(接送货) ****************/
	private List<WayBillNoLocusDTO> wayBillNoLocusDTOList;
	/************* 运单 流水号 ****************/
	private List<LabeledGoodEntity>  labeledGoodList;
	/************* 运单 跟踪记录 ****************/
	private List<TrackRecordEntity>  trackRecordList;
	
	/**
	 * 他人收件人信息
	 */
	private SearchWaybillSignByOtherDto dto;
	
	/**
	 * 装卸费基类
	 */
	private HandlingCharges handlingCharges;
	
	/**
	 * 服务补救费基类
	 */
	private ServiceRemedy serviceRemedy;
	
	/**
	 * 退运费基类
	 */
	private ReturnShipping returnShipping;
	
	/**
	 * 发票基类
	 */
	private Invoice invoice;
	/**
	 * 子母件相关信息（包括子母件关联单、是否为子母件）
	 */
	private TwoInOneWaybillDto twoInOneWaybillDto;

	
	
	public TwoInOneWaybillDto getTwoInOneWaybillDto() {
		return twoInOneWaybillDto;
	}

	public void setTwoInOneWaybillDto(TwoInOneWaybillDto twoInOneWaybillDto) {
		this.twoInOneWaybillDto = twoInOneWaybillDto;
	}

	/**
	 * getter.
	 * 
	 * @return the WaybillSignByOtherDto
	 */
	public SearchWaybillSignByOtherDto getDto() {
		return dto;
	}
	
	/**
	 * setter.
	 * 
	 * @param WaybillSignByOtherDto
	 *            the  new way bill sign other dto
	 */
	public void setDto(SearchWaybillSignByOtherDto dto) {
		this.dto = dto;
	}

	/**
	 * getter.
	 * 
	 * @return the waybills
	 */
	public List<WaybillEntity> getWaybills() {
		return waybills;
	}

	/**
	 * setter.
	 * 
	 * @param waybills
	 *            the new waybills
	 */
	public void setWaybills(List<WaybillEntity> waybills) {
		this.waybills = waybills;
	}

	/**
	 * getter.
	 * 
	 * @return the condition
	 */
	public WaybillSearchCondition getCondition() {
		return condition;
	}

	/**
	 * setter.
	 * 
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(WaybillSearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * getter.
	 * 
	 * @return the start change sign rfc dtos
	 */
	public List<LeaveChangeByWaybillNoResultDto> getStartChangeSignRfcDtos() {
		return startChangeSignRfcDtos;
	}

	/**
	 * setter.
	 * 
	 * @param startChangeSignRfcDtos
	 *            the new start change sign rfc dtos
	 */
	public void setStartChangeSignRfcDtos(
			List<LeaveChangeByWaybillNoResultDto> startChangeSignRfcDtos) {
		this.startChangeSignRfcDtos = startChangeSignRfcDtos;
	}


	/**
	 * getter.
	 * 
	 * @return the bill process entities
	 */
	public List<ReturnBillProcessEntity> getBillProcessEntities() {
		return billProcessEntities;
	}

	/**
	 * setter.
	 * 
	 * @param billProcessEntities
	 *            the new bill process entities
	 */
	public void setBillProcessEntities(
			List<ReturnBillProcessEntity> billProcessEntities) {
		this.billProcessEntities = billProcessEntities;
	}

	/**
	 * getter.
	 * 
	 * @return the waybill settlement info dto
	 */
	public WaybillSettlementInfoDto getWaybillSettlementInfoDto() {
		return waybillSettlementInfoDto;
	}

	/**
	 * setter.
	 * 
	 * @param waybillSettlementInfoDto
	 *            the new waybill settlement info dto
	 */
	public void setWaybillSettlementInfoDto(
			WaybillSettlementInfoDto waybillSettlementInfoDto) {
		this.waybillSettlementInfoDto = waybillSettlementInfoDto;
	}

	/**
	 * getter.
	 * 
	 * @return the goods label print dtos
	 */
	public List<GoodsLabelPrintDto> getGoodsLabelPrintDtos() {
		return goodsLabelPrintDtos;
	}

	/**
	 * setter.
	 * 
	 * @param goodsLabelPrintDtos
	 *            the new goods label print dtos
	 */
	public void setGoodsLabelPrintDtos(
			List<GoodsLabelPrintDto> goodsLabelPrintDtos) {
		this.goodsLabelPrintDtos = goodsLabelPrintDtos;
	}

	/**
	 * getter.
	 * 
	 * @return the general query dtos
	 */
	public List<GeneralQueryDto> getGeneralQueryDtos() {
		return generalQueryDtos;
	}

	/**
	 * setter.
	 * 
	 * @param generalQueryDtos
	 *            the new general query dtos
	 */
	public void setGeneralQueryDtos(List<GeneralQueryDto> generalQueryDtos) {
		this.generalQueryDtos = generalQueryDtos;
	}

	/**
	 * getter.
	 * 
	 * @return the way bill other for bse dto
	 */
	public WayBillOtherForBseDto getWayBillOtherForBseDto() {
		return wayBillOtherForBseDto;
	}

	/**
	 * setter.
	 * 
	 * @param wayBillOtherForBseDto
	 *            the new way bill other for bse dto
	 */
	public void setWayBillOtherForBseDto(
			WayBillOtherForBseDto wayBillOtherForBseDto) {
		this.wayBillOtherForBseDto = wayBillOtherForBseDto;
	}

	/**
	 * getter.
	 * 
	 * @return the way bill status for bse dtos
	 */
	public List<WayBillStatusForBseDto> getWayBillStatusForBseDtos() {
		return wayBillStatusForBseDtos;
	}

	/**
	 * setter.
	 * 
	 * @param wayBillStatusForBseDtos
	 *            the new way bill status for bse dtos
	 */
	public void setWayBillStatusForBseDtos(
			List<WayBillStatusForBseDto> wayBillStatusForBseDtos) {
		this.wayBillStatusForBseDtos = wayBillStatusForBseDtos;
	}

	/**
	 *getter
	 */
	public List<RepaymentArriveDto> getRepaymentArriveDtos() {
		return repaymentArriveDtos;
	}

	/**
	 *setter
	 */
	public void setRepaymentArriveDtos(List<RepaymentArriveDto> repaymentArriveDtos) {
		this.repaymentArriveDtos = repaymentArriveDtos;
	}

	/**
	 *getter
	 */
	public WaybillInfoByWaybillNoReultDto getWaybillInfoByWaybillNoReultDto() {
		return waybillInfoByWaybillNoReultDto;
	}

	/**
	 *setter
	 */
	public void setWaybillInfoByWaybillNoReultDto(
			WaybillInfoByWaybillNoReultDto waybillInfoByWaybillNoReultDto) {
		this.waybillInfoByWaybillNoReultDto = waybillInfoByWaybillNoReultDto;
	}

	/**
	 *getter
	 */
	public List<ArriveChanSignVo> getArriveChangeSignRfcDtos() {
		return arriveChangeSignRfcDtos;
	}

	/**
	 *setter
	 */
	public void setArriveChangeSignRfcDtos(
			List<ArriveChanSignVo> arriveChangeSignRfcDtos) {
		this.arriveChangeSignRfcDtos = arriveChangeSignRfcDtos;
	}

	
	/**
	 * @return  the wayBIllNoLocusList
	 */
	public List<WayBillNoLocusDTO> getWayBIllNoLocusList() {
	    return wayBIllNoLocusList;
	}

	
	/**
	 * @param wayBIllNoLocusList the wayBIllNoLocusList to set
	 */
	public void setWayBIllNoLocusList(List<WayBillNoLocusDTO> wayBIllNoLocusList) {
	    this.wayBIllNoLocusList = wayBIllNoLocusList;
	}

	
	/**
	 * @return  the labeledGoodList
	 */
	public List<LabeledGoodEntity> getLabeledGoodList() {
	    return labeledGoodList;
	}

	
	/**
	 * @param labeledGoodList the labeledGoodList to set
	 */
	public void setLabeledGoodList(List<LabeledGoodEntity> labeledGoodList) {
	    this.labeledGoodList = labeledGoodList;
	}

	
	/**
	 * @return  the trackRecordList
	 */
	public List<TrackRecordEntity> getTrackRecordList() {
	    return trackRecordList;
	}

	
	/**
	 * @param trackRecordList the trackRecordList to set
	 */
	public void setTrackRecordList(List<TrackRecordEntity> trackRecordList) {
	    this.trackRecordList = trackRecordList;
	}

	
	/**
	 * @return  the wayBillNoLocusDTOList
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocusDTOList() {
	    return wayBillNoLocusDTOList;
	}

	
	
	public QueryClaimbillResultDto getQueryClaimbillResultDto() {
		return queryClaimbillResultDto;
	}



	
	public void setQueryClaimbillResultDto(
			QueryClaimbillResultDto queryClaimbillResultDto) {
		this.queryClaimbillResultDto = queryClaimbillResultDto;
	}



	/**
	 * @param wayBillNoLocusDTOList the wayBillNoLocusDTOList to set
	 */
	public void setWayBillNoLocusDTOList(
		List<WayBillNoLocusDTO> wayBillNoLocusDTOList) {
	    this.wayBillNoLocusDTOList = wayBillNoLocusDTOList;
	}
	
	/**获得派送情况信息*/
	public DeliverySituationDto getDeliverySituationDto() {
		return deliverySituationDto;
	}

	/**设置派送情况信息*/
	public void setDeliverySituationDto(DeliverySituationDto deliverySituationDto) {
		this.deliverySituationDto = deliverySituationDto;
	}
	
	/**获得财务情况信息*/
	public WaybillFinanceInfoDto getWaybillFinanceInfoDto() {
		return waybillFinanceInfoDto;
	}
	
	/**设置财务情况信息*/
	public void setWaybillFinanceInfoDto(WaybillFinanceInfoDto waybillFinanceInfoDto) {
		this.waybillFinanceInfoDto = waybillFinanceInfoDto;
	}
	
	/**获得装卸费信息*/
	public HandlingCharges getHandlingCharges() {
		return handlingCharges;
	}

	/**设置装卸费信息*/
	public void setHandlingCharges(HandlingCharges handlingCharges) {
		this.handlingCharges = handlingCharges;
	}

	/**获得服务补救信息*/
	public ServiceRemedy getServiceRemedy() {
		return serviceRemedy;
	}

	/**设置服务补救信息*/
	public void setServiceRemedy(ServiceRemedy serviceRemedy) {
		this.serviceRemedy = serviceRemedy;
	}

	/**获得退运费信息*/
	public ReturnShipping getReturnShipping() {
		return returnShipping;
	}

	/**设置退运费信息*/
	public void setReturnShipping(ReturnShipping returnShipping) {
		this.returnShipping = returnShipping;
	}
	
	/**获得发票信息*/
	public Invoice getInvoice() {
		return invoice;
	}
	
	/**设置发票信息*/
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<QueryAgentInformationResultDto> getQueryAgentInformationResultDtos() {
		return queryAgentInformationResultDtos;
	}

	public void setQueryAgentInformationResultDtos(
			List<QueryAgentInformationResultDto> queryAgentInformationResultDtos) {
		this.queryAgentInformationResultDtos = queryAgentInformationResultDtos;
	}

	public String getBallotNum() {
		return ballotNum;
	}

	public void setBallotNum(String ballotNum) {
		this.ballotNum = ballotNum;
	}

	public String getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}


}
