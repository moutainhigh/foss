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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/action/IntegrativeQueryAction.java
 * 
 * FILE NAME        	: IntegrativeQueryAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-querying
 * PACKAGE NAME: com.deppon.foss.module.base.querying.server.action
 * FILE    NAME: IntegrativeQueryAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.querying.server.action;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TrackRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserDefinedQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.IntegrativeQueryVo;
import com.deppon.foss.module.base.querying.server.service.IIntegrativeQueryService;
import com.deppon.foss.module.base.querying.server.service.IQueryCrmClaimbillService;
import com.deppon.foss.module.base.querying.server.service.ISpecialValueAddedService;
import com.deppon.foss.module.base.querying.server.service.IWayBillRelevanceQueryService;
import com.deppon.foss.module.base.querying.shared.domain.HandlingCharges;
import com.deppon.foss.module.base.querying.shared.domain.Invoice;
import com.deppon.foss.module.base.querying.shared.domain.ReturnShipping;
import com.deppon.foss.module.base.querying.shared.domain.ServiceRemedy;
import com.deppon.foss.module.base.querying.shared.domain.VestResponse;
import com.deppon.foss.module.base.querying.shared.domain.WaybillSearchCondition;
import com.deppon.foss.module.base.querying.shared.dto.DeliveryInformationDto;
import com.deppon.foss.module.base.querying.shared.dto.FinanceInfoDto;
import com.deppon.foss.module.base.querying.shared.dto.QueryAgentInformationResultDto;
import com.deppon.foss.module.base.querying.shared.dto.QueryClaimbillResultDto;
import com.deppon.foss.module.base.querying.shared.exception.QueryingBussinessException;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignByOtherService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SearchWaybillSignByOtherDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrigFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.IPrintAgentWaybillService;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.PrintAgentWaybillRecordEntity;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 综合查询ACTION.
 * 
 * @author 078823-foss-panGuangJun
 * @date 2012-12-21 上午8:43:33
 */
public class IntegrativeQueryAction extends AbstractAction {

    /** serialVersionUID. */
    private static final long serialVersionUID = 6301406070816329268L;
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(IntegrativeQueryAction.class);
    
	private String queryFinanceConditionByWayBillNoByCUBCUrl;	
	public void setQueryFinanceConditionByWayBillNoByCUBCUrl(
			String queryFinanceConditionByWayBillNoByCUBCUrl) {
		this.queryFinanceConditionByWayBillNoByCUBCUrl = queryFinanceConditionByWayBillNoByCUBCUrl;
	}

	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}

    /** 结算查询service. */
    // IWaybillSettlementInfoQueryService--
    private IIntegrativeQueryService integrativeQueryService;
    
    @Autowired
    private ISaleDepartmentService saleDepartmentService;
    

    private IStockService stockService;
    
    @Autowired
    private ILeasedVehicleService leasedVehicleService;
    
     public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}



	/**
     * 用于运单和更改单查询.  接送货提供接口
     */
    private IWaybillQueryService waybillQueryService;
    private ISpecialValueAddedService specialValueAddedService;
    public ISpecialValueAddedService getSpecialValueAddedService() {
		return specialValueAddedService;
	}

	public void setSpecialValueAddedService(
			ISpecialValueAddedService specialValueAddedService) {
		this.specialValueAddedService = specialValueAddedService;
	}



	/**
     * 客户信息Service接口.
     */
    private ICustomerService customerService;
    /**
     * 理赔信息接口
     */
    private IQueryCrmClaimbillService queryCrmClaimbillService;
    /**
     * 综合查询-运单相关查询*.
     */
    private IWayBillRelevanceQueryService wayBillRelevanceQueryService;
    
    /**
     * 综合查询-代理信息*.
     */
    private IPrintAgentWaybillService printAgentWaybillService;
    
    /**
     * 设置 综合查询-运单相关查询*.
     *
     * @param wayBillRelevanceQueryService the new 综合查询-运单相关查询*
     */
    public void setWayBillRelevanceQueryService(
    	IWayBillRelevanceQueryService wayBillRelevanceQueryService) {
        this.wayBillRelevanceQueryService = wayBillRelevanceQueryService;
    }
    
    /**
     * 设置 综合查询-代理信息*.
     *
     * @param printAgentWaybillService the new 综合查询-代理信息*
     */
    public void setPrintAgentWaybillService(
			IPrintAgentWaybillService printAgentWaybillService) {
		this.printAgentWaybillService = printAgentWaybillService;
	}



	/** 查询结果和参数定义. */
    private WaybillVo waybillVo = new WaybillVo();

    /**
     * 综合查询 VO.
     */
    private IntegrativeQueryVo objectVo = new IntegrativeQueryVo();
    
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;

	/**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;
    
    /**
     * 处理他人收件 Service接口
     */
    private ISignByOtherService signByOtherService;
    
    @Autowired
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;


    
	
	
	/**
     * 根据条件查询运单list.
     * 
     * @return String
     * @author 078823-foss-panGuangJun
     * @date 2012-12-24 下午2:23:18
     */
    public String queryWayBillList() {
	try {
	    waybillVo.setWaybills(integrativeQueryService
		    .searchWaybillByCondtion(waybillVo.getCondition()));
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }
    /**
     * 根据运单和流水号 查询 货物轨迹.
     * 
     * @return String
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-24 下午2:23:18
     */
    public String queryWayBillNoLocus() {
	try {
	    WaybillSearchCondition con = waybillVo.getCondition();
	    waybillVo = integrativeQueryService
		    .searchWaybillInfoByWaybillNo(con.getWaybillNo(),con.getSerialNo());
	    //313353 综合查询外请车图片上传人优化
		this.convertWayBillNoLocusDTO(waybillVo);
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }
    /**
     * Query waybill info.
     * 
     * @return the string
     */
    public String queryWaybillInfo() {
	if (null != waybillVo && null != waybillVo.getCondition()
		&& !StringUtil.isEmpty(waybillVo.getCondition().getWaybillNo())) {
	    try {
		waybillVo = integrativeQueryService
			.searchWaybillInfoByWaybillNo(waybillVo.getCondition()
				.getWaybillNo());
		//DP-FOSS-综合查询收货人信息隐藏需求DN201601060002 
		OrgAdministrativeInfoEntity  org = FossUserContext.getCurrentDept();
		WaybillInfoByWaybillNoReultDto  waybillResult = waybillVo.getWaybillInfoByWaybillNoReultDto();
	    if(waybillResult == null) {
		    return returnSuccess();
	    }
	    String receiveOrgCode =  waybillResult.getReceiveOrgCode();
		String customerPickupOrgCode = waybillResult.getCustomerPickupOrgCode();
		//313353 空指针异常修复
		if(null == org){
			org = new OrgAdministrativeInfoEntity();
		}
		String orgCode = org.getCode();
		if(!StringUtil.isEmpty(orgCode) && !orgCode.equals(receiveOrgCode)&& !orgCode.equals(customerPickupOrgCode)){
			if(org.checkExpressPart()||org.checkSaleDepartment()){
				waybillResult.setCustomerPickupOrgCode(null);
				waybillResult.setReceiveCustomerMobilephone(null);
				waybillResult.setReceiveCustomerPhone(null);
				waybillResult.setReceiveCustomerAddress(null);
				waybillResult.setReceiveCustomerCode(null);
			}
		}
		//DP-营销-FOSS-运费信息保密需求DN201605030003-当运单开单勾选“预付费保密”时，不是 出发部门、财务本部、市场营销本部的都无法查看运费相关信息 -wangyuanyuan(310430)
				List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService
						.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
				List<String> orgStrings = new ArrayList<String>();
				for(OrgAdministrativeInfoEntity entity :orgList){
					orgStrings.add(entity.getCode());
				}
				String SecretPrepaid = waybillVo.getWaybillInfoByWaybillNoReultDto().getSecretPrepaid();
				if (null!=SecretPrepaid&&SecretPrepaid.equals("Y")
						&& !orgCode.equals(receiveOrgCode)
						&& !orgStrings.contains("W0103")
						&& !orgStrings.contains("W0122")) {
					waybillResult.setTransportFee(null);// 费率
					waybillResult.setTotalFee(null);// 运费
					waybillResult.setPrePayAmount(null);// 预付金额
					waybillResult.setInsuranceFee(null);// 保险费
					waybillResult.setCodRate(null);// 代收货款费率
					waybillResult.setPickUpFee(null);// 接货费
					waybillResult.setDeliveryGoodsFee(null);// 送货费
					waybillResult.setPackageFee(null);// 包装费
					waybillResult.setServiceFee(null);// 装卸费
					waybillResult.setOtherFee(null);// 其他费用
					waybillResult.setToPayAmountDiscount(null);// 到付折扣金额
					waybillResult.setPrePayAmountDiscount(null);// 预付折扣金额
				}
			//313353 综合查询外请车图片上传人优化
			this.convertWayBillNoLocusDTO(waybillVo);
	    } catch (QueryingBussinessException e) {
		return returnError(e);
	    }
	}
	return returnSuccess();
    }

    /**
     * 【操作类型】，【操作人】显示为“外请车司机”。
     * 【操作部门】显示为外请车车牌绑定人所在的顶级车队
     * 
     * @author 313353
     * @param waybillVo
     */
    private void convertWayBillNoLocusDTO(WaybillVo waybillVo){
    	List<WayBillNoLocusDTO> dtoList = waybillVo.getWayBIllNoLocusList();
    	if(null == dtoList){
    		return;
    	}
    	//过滤快递单号
    	if(waybillVo.getWayBillNo()!= null && 
    			(waybillVo.getWayBillNo().length() == 10 || waybillVo.getWayBillNo().length() == 14)){
    		return;
    	}
    	for (WayBillNoLocusDTO wayBillNoLocusDTO : dtoList) {
			if(null != wayBillNoLocusDTO){
				if(("总裁".equals(wayBillNoLocusDTO.getOperateOrgName()) || null == wayBillNoLocusDTO.getOperateName())
						&& "图片上传".equals(wayBillNoLocusDTO.getOperateTypeName())){
				//根据外请车牌照，查询顶级车队，设置操作部门
				if(!StringUtils.isEmpty(wayBillNoLocusDTO.getVehicleNo())){
					String operateOrgName = leasedVehicleService.queryOrgNameByVehicleNo(wayBillNoLocusDTO.getVehicleNo());
					if(null != operateOrgName && !operateOrgName.trim().isEmpty()){
						//设置操作部门
						wayBillNoLocusDTO.setOperateOrgName(operateOrgName);
						//设置操作人
						wayBillNoLocusDTO.setOperateName("外请车司机");
					}
				}
				}
			}
		}
    }
    
    /**
     * 根据 自定义查询条件查询运单list.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String queryWayBillListByUserDefinedQueryDto() {
	UserDefinedQueryDto entity = objectVo.getUserDefinedQuery();
	entity.setUserCode(FossUserContext.getCurrentInfo().getEmpCode());
	waybillVo.setWaybills(integrativeQueryService
		.queryWayBillListByUserDefinedQueryDto(entity));
	return returnSuccess();
    }

    /**
     * 根据传入对象查询符合条件所有自定义查询方案和条件.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String queryUserDefinedQueryDtos() {
	UserDefinedQueryDto entity = objectVo.getUserDefinedQuery();
	entity.setUserCode(FossUserContext.getCurrentInfo().getEmpCode());
	objectVo.setUserDefinedQueryList(integrativeQueryService
		.queryUserDefinedQueryDtos(entity));
	return returnSuccess();
    }

   
    /**
     * 根据传入对象查询符合条件所有跟踪记录.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String queryTrackRecord() {
	TrackRecordEntity entity = objectVo.getTrackRecord();
	objectVo.setTrackRecordList(integrativeQueryService
		.queryTrackRecords(entity));
	return returnSuccess();
    }
    /**
     * 综合查询-运单相关信息-特殊增值服务信息
     */
    @JSON
    public String querySpecialValueAddedServiceInfo(){
    	if (null != waybillVo && null != waybillVo.getCondition()
    			&& !StringUtil.isEmpty(waybillVo.getCondition().getWaybillNo())){
    	try {
    		List<DeliveryInformationDto> ls = specialValueAddedService.queryDeliveryInfo(waybillVo.getCondition().getWaybillNo());
    	   waybillVo.setDeliveryInformationDtos(ls);
    	} catch (BusinessException e) {
			return returnError(e);
		} 
    	}
    	return returnSuccess();
    }
    /**
     * <p>根据运单号查询发货客户的详细信息</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-20 上午10:00:52
     * @see
     */
    @JSON
    public String queryCustomerInfo(){
	try {
	    //根据运单号查询运单信息
	    WaybillInfoByWaybillNoReultDto dto = waybillQueryService
		    .queryWaybillInfoByWaybillNo(waybillVo.getWayBillNo());
	    if (null != dto) {
		// 获取发货客户的编码
		String custCode = dto.getDeliveryCustomerCode();
		// 通过传入一个客户编码查询出对应的客户信息 包括：客户基本信息、联系人信息集合、客户银行账户、客户合同信息集合
		CustomerDto custDto = customerService
			.queryCustInfoByCode(custCode);
		objectVo.setCustDto(custDto);
	    }
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
	
    }
    
    /**
     * 根据传入对象查询符合条件所有运单标记紧急状态.
     *
     * @return List<UserDefinedQueryDto>
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-01-21 20:33
     * @see
     */
    public String queryWaybillMark() {
	WaybillMarkEntity entity = objectVo.getWaybillMark();
	entity.setCreateUserCode(FossUserContext.getCurrentInfo().getEmpCode());
//	objectVo.setWaybillMark(integrativeQueryService
//		.queryWaybillMarks(entity));
	return returnSuccess();
    }

    
    
    /**
     * 
     * <p>综合查询-运单相关信息-跟踪记录</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryWayBilllTrackRecords(){
	try {
	    String waybillNo = waybillVo.getCondition().getWaybillNo();
	    TrackRecordEntity trackRecordEntity = new TrackRecordEntity();
	    trackRecordEntity.setWaybillNo(waybillNo);
	    waybillVo = wayBillRelevanceQueryService.queryTrackRecords(trackRecordEntity);
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
    
    /**
     * 
     * <p>综合查询-运单相关信息-运单更改</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryWayBilllChangeByWaybillNo(){
	try {
	    waybillVo = wayBillRelevanceQueryService.queryWayBilllChangeByWaybillNo(waybillVo.getCondition().getWaybillNo());
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
    
    /**
     * FOSS
     * <p>综合查询-运单相关信息-财务情况-</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryFinanceConditionByWayBillNoByFOSS(){
	try {
		Invoice invoice = new Invoice();
	    String waybillNo = waybillVo.getWayBillNo();
	    String leaveDeptCode = waybillVo.getLeaveDeptCode();
	    
	   // String arriveDeptCode = waybillVo.getArriveDeptCode();
	    waybillVo = wayBillRelevanceQueryService.queryWaybillFinanceInfo(waybillNo);
	    List<InvoiceFeeInfo> invoiceFeeInfos = waybillVo.getWaybillFinanceInfoDto().getInvoiceFeeInfos();
	   // waybillVo = wayBillRelevanceQueryService.queryFinanceConditionByWayBillNo(waybillNo, billTime, leaveDeptCode,arriveDeptCode);
	    if(CollectionUtils.isNotEmpty(invoiceFeeInfos)){
	    	for(InvoiceFeeInfo invoiceFeeInfo : invoiceFeeInfos){
	    		if(invoiceFeeInfo.getOrgCode().equals(leaveDeptCode)){
	    			invoice.setLeaveAmount(invoiceFeeInfo.getAmount());
	    		}else{
	    			invoice.setArriveAmount(invoiceFeeInfo.getAmount());
	    		}
	    	}
	    }
	    waybillVo.setInvoice(invoice);
	  
	  //DP-营销-FOSS-运费信息保密需求DN201605030003-当运单开单勾选“预付费保密”时，不是 出发部门、财务本部、市场营销本部的都无法查看运费相关信息 -wangyuanyuan(310430)
		WaybillInfoByWaybillNoReultDto resultByWaybillNoReultDto = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
	    if(resultByWaybillNoReultDto == null) {
		    return returnSuccess();
	    }
	    OrgAdministrativeInfoEntity  org = FossUserContext.getCurrentDept();
	    String receiveOrgCode =  resultByWaybillNoReultDto.getReceiveOrgCode();
	    String orgCode = org.getCode();
	    List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(orgCode);
	    List<String> orgStrings = new ArrayList<String>();
		for(OrgAdministrativeInfoEntity entity :orgList){
			orgStrings.add(entity.getCode());
		}
		OrigFeeInfo origFeeInfo = waybillVo.getWaybillFinanceInfoDto().getOrigFeeInfo();
		ServiceRemedy serviceRemedy = waybillVo.getServiceRemedy();
		ReturnShipping returnShipping = waybillVo.getReturnShipping();
		HandlingCharges handlingCharges = waybillVo.getHandlingCharges();
		String secretPrepaid = resultByWaybillNoReultDto.getSecretPrepaid();
			if (null!=secretPrepaid&&secretPrepaid.equals("Y")
					&& !orgCode.equals(receiveOrgCode)
					&& !orgStrings.contains("W0103")
					&& !orgStrings.contains("W0122")) {
				if (null!=origFeeInfo) {
					origFeeInfo.setReceivedAmount(null);// 实收始发运费
				}
				if(null!=serviceRemedy){
				    serviceRemedy.setAmount(null);//减免金额
				}
				if(null!=returnShipping){
					returnShipping.setAmount(null);// 退运费金额
					returnShipping.setReturnableAmount(null);// 应退金额
					returnShipping.setVerifyRcvAmount(null);// 冲应收金额
				}
				if(null!=handlingCharges){
					handlingCharges.setAmount(null);//装卸费金额
				}
				
			}

	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
    
    
    /**
     * CUBC
     * <p>综合查询-运单相关信息-财务情况-</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
     public String queryFinanceConditionByWayBillNoByCUBC() {
        try{
    	// post方式
        //String url = "http://10.224.173.93:8080/trade-web/webservice/fossGetMsgByWayBillNoServiceImpl/getMsgByWBNAndDeptCode";
        //String url = "http://10.230.29.19:8180/esb2/rs/ESB_FOSS2ESB_CUBC_INFO_SELECT";
        
	    String waybillNo = waybillVo.getWayBillNo();
	    String leaveDeptCode = waybillVo.getLeaveDeptCode();
	    String arriveDeptCode = waybillVo.getArriveDeptCode();
	    
	    JSONObject JsonWaybillVo = new JSONObject();
	    JsonWaybillVo.put("arriveDeptCode", arriveDeptCode);
	    JsonWaybillVo.put("leaveDeptCode", leaveDeptCode);
	    JsonWaybillVo.put("waybillNo", waybillNo);
	    HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(JsonWaybillVo);

	    RestTemplate restTemplate = new RestTemplate(); 
	    FinanceInfoDto response = restTemplate.exchange(queryFinanceConditionByWayBillNoByCUBCUrl, HttpMethod.POST, entity, FinanceInfoDto.class).getBody();
	    
	    WaybillFinanceInfoDto waybillFinanceInfoDto = new WaybillFinanceInfoDto();

 	    if(null != response.getCodFeeInfo()) { 
	    	  String[] strArray = null;
	    	  String str ="";
	    	  if(null != response.getCodFeeInfo().getPaymentStatus()){
	          strArray = response.getCodFeeInfo().getPaymentStatus().split(",");
	          for(int i = 0; i < strArray.length; i++){
	              if(!StringUtil.isEmpty(strArray[i])){
	            	  if(strArray[i].equalsIgnoreCase(FossConstants.CUBC_CREATE))
	            		  strArray[i] = "未退款";
	            	  if(strArray[i].equalsIgnoreCase(FossConstants.CUBC_PAYING))
	            		  strArray[i] = "退款中";
	            	  if(strArray[i].equalsIgnoreCase(FossConstants.CUBC_DONE))
	            		  strArray[i] = "已退款";  
	              }
	              else
	            	  str = ",";
	              str = str + strArray[i]+",";
	          }
	         str = str.substring(0,str.length()-1);
	    	}	        
	          CODFeeInfo codeFeeInfo = response.getCodFeeInfo();
	          codeFeeInfo.setPaymentStatus(str);
	          waybillFinanceInfoDto.setCodFeeInfo(codeFeeInfo);
	    	}
 	    
	    if(null != response.getDestFeeInfo())
	    	waybillFinanceInfoDto.setDestFeeInfo(response.getDestFeeInfo());    	    
	    if(null != response.getInvoiceFeeInfo()){
	    	List<InvoiceFeeInfo> list = new ArrayList<InvoiceFeeInfo>();
	    	list.add(response.getInvoiceFeeInfo());
	    	waybillFinanceInfoDto.setInvoiceFeeInfos(list);
	    }
	    if(null != response.getOrigFeeInfo())
	    	waybillFinanceInfoDto.setOrigFeeInfo(response.getOrigFeeInfo());
	    if(null != response.getOtherFeeInfos()) 
	    	waybillFinanceInfoDto.setOtherFeeInfos(response.getOtherFeeInfos());
	    
    	waybillVo.setWaybillFinanceInfoDto(waybillFinanceInfoDto);
        
	    return returnSuccess();
        }
	    catch (QueryingBussinessException e) {
		    return returnError(e);
		}
    }
    
    /**
     * 
     * <p>综合查询-运单相关信息-财务情况-</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryFinanceConditionByWayBillNo(){
    	try{
        	// post方式
            //String url = "http://10.230.28.137:8145/ashy-web/webservice/v1/ashy/vestService/vestAscription";
    	    String[] waybillNo = {waybillVo.getWayBillNo()};
            
            //{"origin":"FOSS-ZH","serviceCode":"XXXX","sourceBillNos":["5165696775"],"sourceBillType":"W"}
            JSONObject JsonWaybillVo = new JSONObject();
    	    JsonWaybillVo.put("origin", "FOSS-ZH");
    	    JsonWaybillVo.put("serviceCode", "com.deppon.foss.module.base.querying.server.action.IntegrativeQueryAction.queryFinanceConditionByWayBillNo");
    	    JsonWaybillVo.put("sourceBillNos", waybillNo);
    	    JsonWaybillVo.put("sourceBillType", "W");
    	    
    	    HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(JsonWaybillVo);
    	    
    	    RestTemplate restTemplate = new RestTemplate(); 
    	    VestResponse response = restTemplate.exchange(grayByWaybillNoUrl, HttpMethod.POST, entity, VestResponse.class).getBody();
    	    if(null != response.getVestBatchResult().get(0).getVestSystemCode()){
    	    String sysCode = response.getVestBatchResult().get(0).getVestSystemCode();  		
    	    if(sysCode.equalsIgnoreCase(FossConstants.CUBC))
    	    	queryFinanceConditionByWayBillNoByCUBC();
    	    if(sysCode.equalsIgnoreCase(FossConstants.FOSS))
    	    	queryFinanceConditionByWayBillNoByFOSS();
    	    
    	    return returnSuccess();
    	    }
    	    else
    	    	return returnError("灰度层返回异常");
    	    	
    	}
	    catch (QueryingBussinessException e) {
		    return returnError(e);
		}
    	
    }
     /**
     * <p>综合查询-运单相关信息-签收单-</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String querySignedBillByWaybillNo(){
	try {
	    String waybillNo = waybillVo.getCondition().getWaybillNo();
	    waybillVo = wayBillRelevanceQueryService.querySignedBillByWaybillNo(waybillNo);
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
   
    /**
     * 
     * <p>综合查询-运单相关信息-标签打印记录-</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-13 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryLabelPrintByWaybillNo(){
	try {
	    String waybillNo = waybillVo.getCondition().getWaybillNo();
	    waybillVo = wayBillRelevanceQueryService.queryLabelPrintByWaybillNo(waybillNo);
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
   
    /**
     * <p>综合查询-运单相关信息-理赔信息-</p> 
     * @author 101911-foss-zhouChunlai
     * @date 2013-4-24 下午6:30:20
     */
    @JSON
    public String queryClaimbillByWaybillNo(){
	try {
	    String waybillNo = waybillVo.getCondition().getWaybillNo();
	    QueryClaimbillResultDto claimbillResultDto = queryCrmClaimbillService.queryCrmClaimbillByWaybillNo(waybillNo);
	    /**
	     * 查询理赔其他其他信息
	     *
	     */
	    claimbillResultDto =integrativeQueryService.queryOtherClaimbillByWaybillNo(claimbillResultDto,waybillNo);
		 //查询理赔其他其他信息
	    claimbillResultDto =integrativeQueryService.queryOtherClaimbillByWaybillNo(claimbillResultDto,waybillNo);

	    waybillVo.setQueryClaimbillResultDto(claimbillResultDto);
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
    
    /**
     * <p>综合查询-运单相关信息-处理他人收件人信息</p>.
     *
     * @return the 查询结果和参数定义
     * @author 132599-FOSS-shenweihua
     * @date 2013-09-3 10:23
     */
    @JSON
    public String querySignByOtherDto(){
    	try{
    		String waybillNo = waybillVo.getCondition().getWaybillNo();
    		SearchWaybillSignByOtherDto searchWaybillSignByOtherDto = signByOtherService.querySignByOtherDto(waybillNo);
    		waybillVo.setDto(searchWaybillSignByOtherDto);
    		return returnSuccess();
    	}catch( QueryingBussinessException e){
    		return returnError(e);
    	}
    }
    
    /**
     * 
     * <p>派送情况</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-7-04 下午2:16:47
     * @param waybillNo
     * @return
     * @see
     */
    @JSON
    public String queryDeliverySituationByWaybillNo(){
    try {
    	waybillVo = wayBillRelevanceQueryService.queryLabelDeliverySituationByWaybillNo(waybillVo.getCondition().getWaybillNo());
    	return returnSuccess();
    } catch (QueryingBussinessException e) {
    	return returnError(e);
    }
    }
    
    /**
     * 
     * <p>导出标签打印记录</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-7-06 下午3:53:47
     * @param waybillNo
     * @return
     * @see
     */
    public String queryLablePrinting() {
    	try {
    	    // 导出文件名称
    	    downloadFileName = URLEncoder.encode(
    		    QueryingConstant.LABEL_PRINTING_NAME, "UTF-8");
    	    // 导出成文件
    	    inputStream =  wayBillRelevanceQueryService.queryLablePrinting(waybillVo.getCondition().getWaybillNo());
 
    	    ExportSetting exportSetting = new ExportSetting();
    	    // 设置名称
    	    exportSetting.setSheetName(QueryingConstant.LABEL_PRINTING_NAME);
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	} catch (UnsupportedEncodingException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError("UnsupportedEncodingException", e);
    	}
        }
    
    /**
     * 
     * <p>导出跟踪记录</p> 
     * @author DP-Foss-shenweihua
     * @date 2013-8-01 下午2:03:47
     * @param waybillNo
     * @return
     * @see
     */
    public String queryExportTrackRecords() {
    	try {
    	    // 导出文件名称
    	    downloadFileName = URLEncoder.encode(
    		    QueryingConstant.TRACK_RECORDS_NAME, "UTF-8");
    	    // 导出成文件
    	    inputStream =  wayBillRelevanceQueryService.queryExportTrackRecords(waybillVo.getCondition().getWaybillNo());
 
    	    ExportSetting exportSetting = new ExportSetting();
    	    // 设置名称
    	    exportSetting.setSheetName(QueryingConstant.TRACK_RECORDS_NAME);
    	    return returnSuccess();
    	} catch (BusinessException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	} catch (UnsupportedEncodingException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError("UnsupportedEncodingException", e);
    	}
        }
    
    
    /**
     * 
     * <p>综合查询-运单相关信息-代理信息-</p> 
     * @author WeiXing
     * @date 2014-11-14 下午1:19:32
     * @return
     * @see
     */
    @JSON
    public String queryAgentInformationByWaybillNo(){
	try {
	    String waybillNo = waybillVo.getCondition().getWaybillNo();
	    PrintAgentWaybillRecordEntity printAgentWaybillRecordEntity=new PrintAgentWaybillRecordEntity();
	    printAgentWaybillRecordEntity.setWaybillNo(waybillNo);
	    List<PrintAgentWaybillRecordEntity> entities= printAgentWaybillService.queryWaybillsRecord(printAgentWaybillRecordEntity, start, limit);
	    List<QueryAgentInformationResultDto> dtos=new ArrayList<QueryAgentInformationResultDto>();
	    for(int i=0;i<entities.size();i++){
	    	QueryAgentInformationResultDto tmp=new QueryAgentInformationResultDto();
	    	PrintAgentWaybillRecordEntity entity=entities.get(i);
	    	tmp.setSerialNo(entity.getSerialNo());
	    	tmp.setOperatorName(entity.getOperatorName());
	    	tmp.setOperatTime(entity.getOperatTime());
	    	tmp.setTraceContent(entity.getAgentWaybillNo());
	    	tmp.setStatus(entity.getStatus());
	    	tmp.setDepTelephone(entity.getDepTelephone());
	    	tmp.setOrgName(entity.getOrgName());
	    	dtos.add(tmp);
	    }
	    waybillVo.setQueryAgentInformationResultDtos(dtos);
	    long totalCount = dtos.size(); //由于只可能有一条数据，所以才会计算总记录数
		this.setTotalCount(totalCount);
	    return returnSuccess();
	} catch (QueryingBussinessException e) {
	    return returnError(e);
	}
    }
    /**
     * 合伙人派送弹窗提醒
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-1-27 上午10:03:28
     * @return
     * @see
     */
    @JSON
     public String queryDeliveryNum(){
    	//获取当前登录部门
    	SaleDepartmentEntity  en = saleDepartmentService.querySaleDepartmentByCode(FossUserContext.getCurrentDeptCode());
    	//判断登录部门是否为合伙人营业部
    	if(en!=null&&en.getIsLeagueSaleDept()!=null&&en.getIsLeagueSaleDept().equals("Y")){
    		WaybillStockStatisticsDto  dto = stockService.queryStockGoodsQtyAndWaybillQty(en.getCode());
    		//调用中转接口，查询合伙人营业部的派送件数和派送票数
    		waybillVo.setBallotNum(""+dto.getWaybillQty());
       	   waybillVo.setPackageNum(""+dto.getStockGoodsQty());
    	}
    	return returnSuccess();
    }
    
    
    
    /**
     * 获取 综合查询 VO.
     *
     * @return the objectVo
     */
    public IntegrativeQueryVo getObjectVo() {
	return objectVo;
    }

    /**
     * 设置 综合查询 VO.
     *
     * @param objectVo the objectVo to set
     */
    public void setObjectVo(IntegrativeQueryVo objectVo) {
	this.objectVo = objectVo;
    }
    
    /**
     * 设置 用于运单和更改单查询.
     *
     * @param waybillQueryService the waybillQueryService to set
     */
    public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
        this.waybillQueryService = waybillQueryService;
    }
    
    /**
     * 设置 客户信息Service接口.
     *
     * @param customerService the customerService to set
     */
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }
	
	public void setQueryCrmClaimbillService(
			IQueryCrmClaimbillService queryCrmClaimbillService) {
		this.queryCrmClaimbillService = queryCrmClaimbillService;
	}
	
	/**
     * 得到导出Excel 文件名
     *
     * @param customerService the customerService to set
     */
	public String getDownloadFileName() {
		return downloadFileName;
	}
	
	/**
     * 设置导出Excel 文件名
     *
     * @param customerService the customerService to set
     */
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	/**
     * 得到导出Excel 文件流
     *
     * @param customerService the customerService to set
     */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
     * 设置导出Excel 文件流
     *
     * @param customerService the customerService to set
     */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	   /**
     * getter.
     * 
     * @return the waybill vo
     */
    public WaybillVo getWaybillVo() {
	return waybillVo;
    }

    /**
     * setter.
     * 
     * @param waybillVo
     *            the new waybill vo
     */
    public void setWaybillVo(WaybillVo waybillVo) {
	this.waybillVo = waybillVo;
    }

    /**
     * setter.
     * 
     * @param integrativeQueryService
     *            the new integrative query service
     */
    public void setIntegrativeQueryService(
	    IIntegrativeQueryService integrativeQueryService) {
	this.integrativeQueryService = integrativeQueryService;
    }
    
    /**
     * 获取处理他人收件人信息
     * @param signByOtherService
     */
    public void setSignByOtherService(ISignByOtherService signByOtherService) {
		this.signByOtherService = signByOtherService;
	}
}