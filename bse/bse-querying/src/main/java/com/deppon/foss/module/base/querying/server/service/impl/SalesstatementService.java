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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/service/impl/SalesstatementService.java
 * 
 * FILE NAME        	: SalesstatementService.java
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
package com.deppon.foss.module.base.querying.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.base.querying.server.service.ISalesstatementService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ProductDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营运报表清单Service.
 * 
 * @author 073586-FOSS-LIXuexing
 * @date 2012-12-26 13:00:33
 */
@SuppressWarnings("unchecked")
public class SalesstatementService implements ISalesstatementService {

    private IWaybillQueryService waybillQueryService;
    /**
     * 部门
     */
    private ISaleDepartmentService saleDepartmentService;
    /**
     * 注入productDao
     * @author 218392-FOSS-zhangyongxue
     */
    private ProductDao productDao;
   

    
	@Override
    public Map<String, Object> querySalesStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition, int start, int limit) {
		//校验营业报表清单在查询时收货部门和派送部门不能同时为空
		if(StringUtils.isEmpty(condition.getReceiveOrgCode())
				&& StringUtils.isEmpty(condition.getDeliverDept())
				&& StringUtils.isEmpty(condition.getCreateOrgCode())){
			return null;
		}else{
			return waybillQueryService.querySalesStatementByComplexCondation(
	    			condition, start, limit);
		}
    	
    }

    @Override
    public ExportResource exportStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition,List<String> codeArr) {
	Map<String, Object> map = waybillQueryService
		.querySalesStatementByComplexCondation(condition, 0,
			Integer.MAX_VALUE);
	ArrayList<SalesStatementByComplexCondationResultDto> list = (ArrayList<SalesStatementByComplexCondationResultDto>) map
		.get(QueryingConstant.RESULT);
	ArrayList<SalesStatementByComplexCondationResultDto> operateList = (ArrayList<SalesStatementByComplexCondationResultDto>) list.clone();
//	CalcTotalFeeDto totalFeeDto = new CalcTotalFeeDto();
	if (CollectionUtils.isNotEmpty(codeArr)
		&& CollectionUtils.isNotEmpty(list)) {
	    for (SalesStatementByComplexCondationResultDto dto : list) {
		//将页面移除掉的数据移除
	    	if(codeArr.contains(dto.getWaybillNo())){
		    operateList.remove(dto);
		}
	    }
	}
	list = operateList;
	CalcTotalFeeDto totalFeeDto = getTotalFeeDto(operateList);
	if (CollectionUtils.isEmpty(list)) {
	    list = new ArrayList<SalesStatementByComplexCondationResultDto>();
	}
	List<List<String>> resultList = new ArrayList<List<String>>();
	// 封装全部数据
	for (SalesStatementByComplexCondationResultDto entity : list) {
	    List<String> result = null;
	    // 每行数据 展示   将数据set到  导出实体内
	    result = exportSalesStatement(entity);
	    if (CollectionUtils.isEmpty(result)) {
		continue;
	    }
	    resultList.add(result);
	}
	//合计 行
	List<String> calcTotalFeeHeadList = exportSalesStatementTotal();
	resultList.add(calcTotalFeeHeadList);
	
	List<String> calcTotalFeeList = exportSalesStatementTotal(totalFeeDto);
	if (CollectionUtils.isNotEmpty(calcTotalFeeList)) {
	    resultList.add(calcTotalFeeList);
	}
	ExportResource sheet = new ExportResource();
	// 表头
	sheet.setHeads(QueryingConstant.SALES_STATEMENT_TITLE);
	// 待更换
	sheet.setHeads(QueryingConstant.SALES_STATEMENT_ENTITY_TITLE);
	sheet.setRowList(resultList);
	
	return sheet;
    }
    /**
     * 
     * <p>导出时 汇总（复制接送货代码）</p> 
     * com.deppon.foss.module.pickup.waybill.server.service.impl.WaybillQueryService.querySalesStatementByComplexCondation(WaybillListByComplexCondationDto, int, int)
     * @author LIXUEXING-073586
     * @date 2013-3-19 上午10:31:11
     * @param totalFeeDto
     * @param dto
     * @param count
     * @return
     * @see
     */
    private CalcTotalFeeDto getTotalFeeDto(ArrayList<SalesStatementByComplexCondationResultDto> list) {
	CalcTotalFeeDto totalFeeDto = new CalcTotalFeeDto();
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
	/**
	 * 2013-07-25 10:08 shenweihua
	 * 
	 * 【 注】： 经过需求人员杨巍确认,每天的 到付总额 是 代收货款+ 到付 ， 
	 * 	而经调查：接送货开发组，传过来的【到付金额】额实际就是【到付总额】
	 * 	而经过杨巍确认： 每天的                 收入总额 =  到付款+预付金额   = (到付总额-代收货款)+预付金额
	 * 	而且，接送货传过来的amountTotal实际是把代收货款总额+到付款+预付金额的
	 * 退款金额是装卸费+代收货款
	 */
	for(SalesStatementByComplexCondationResultDto dto : list){
		if(dto!=null&&FossConstants.INACTIVE.equals(dto.getIsInvalid())){
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
	    //新增统计项目
	    if(!dto.getPaidMethod().equals("FC")){
	    	if(dto.getPaidMethod().equals("CH")){
	    	    cashAmountTotal = cashAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
	    	}
	    	if(dto.getPaidMethod().equals("DT")){
	    	    arrearAmountTotal = arrearAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
	    	}
	    	if(dto.getPaidMethod().equals("CT")){
	    	    monthlyAmountTotal = monthlyAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
	    	}
	    	if(!dto.getPaidMethod().equals("CH") && !dto.getPaidMethod().equals("DT") && !dto.getPaidMethod().equals("CT")){
	    	    otherAmountTotal = otherAmountTotal.add((dto.getPrePayAmount()!=null ? dto.getPrePayAmount():BigDecimal.ZERO));
	    	}	    	    	    	    	    	
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
	
	return totalFeeDto;
    }
    /**
     * 营运报表清单 最后 合计行
     * 
     * @author 073586-FOSS-LIXuexing
     * @date 2012-12-26 13:00:33
     */
    private List<String> exportSalesStatementTotal(CalcTotalFeeDto entity) {
	List<String> columnList = new ArrayList<String>();
	// "总件数", "总重量", "总预付金额", "总体积", "到付金额", "总代收货款", "总包装费", "收入总额" 
	columnList.add(QueryingConstant.STRING_EMPTY);
	columnList.add(String.valueOf(entity.getPageCount()));
	columnList.add(String.valueOf(entity.getGoodsQtyTotal()));
	columnList.add(String.valueOf(entity.getGoodsWeightTotal()));
	columnList.add(String.valueOf(entity.getPrePayAmountTotal()));

	columnList.add(String.valueOf(entity.getGoodsVolumeTotal()));
	columnList.add(String.valueOf(entity.getToPayAmountTotal()));

	columnList.add(String.valueOf(entity.getCodAmountTotal()));
	columnList.add(String.valueOf(entity.getPackageFeeTotal()));
	//接送货传过来的总收入【注】：其中包括代收款项
	BigDecimal total =entity.getAmountTotal();

	columnList.add(String.valueOf(total.subtract(entity.getCodAmountTotal())));
	return columnList;
    }
    /**
     * 营运报表清单 最后 合计行 head
     * 
     * @author 073586-FOSS-LIXuexing
     * @date 2012-12-26 13:00:33
     */
    private List<String> exportSalesStatementTotal() {
	List<String> columnList = new ArrayList<String>();
	// "总件数", "总重量", "总预付金额", "总体积", "到付金额", "总代收货款", "总包装费", "收入总额" 
	columnList.add(QueryingConstant.TOTAL_STATEMENT);
	for (String str : QueryingConstant.SALES_STATEMENT_TOTALHEAD_TITLE) {
	    columnList.add(str);
	}
	return columnList;
    }
    /**
     * 营运报表清单 列（组装单行数据）
     * 
     * @author 073586-FOSS-LIXuexing
     * @date 2012-12-26 13:00:33
     */
    private List<String> exportSalesStatement(
	    SalesStatementByComplexCondationResultDto entity) {
	List<String> columnList = new ArrayList<String>();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	if(FossConstants.NO.equals(entity.getIsPdaCreate())){
		columnList.add(null);
	}else{
		columnList.add(/** Date */
				String.valueOf(	sf.format(entity.getBillTime())));
	}
	if((entity.getCreateTime())==null){
		columnList.add(null);
	}else{
		columnList.add(/** Date */
				String.valueOf(	sf.format(entity.getCreateTime())));
	}
	if(FossConstants.YES.equals(entity.getIsPdaCreate())){
		columnList.add(QueryingConstant.YES);
	}else{
		columnList.add(QueryingConstant.NO);
	}
	columnList.add(entity.getWaybillNo());
	columnList.add(entity.getTargetOrgName());
	columnList.add(/** Integer */
	String.valueOf(entity.getGoodsQtyTotal()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getGoodsWeightTotal()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getPrePayAmount()));
	columnList.add(entity.getGoodsSize());
	columnList.add(entity.getDeliveryCustomerContact());
	/**
	 * @author 218392 zhangyongxue
	 * @date 2015-04-08- 13:57:50
	 * 发货人手机和电话
	 */
	columnList.add(entity.getDeliveryCustomerMobilephone());
	columnList.add(entity.getDeliveryCustomerPhone());
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getBillWeight()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getGoodsVolumeTotal()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getPackageFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getToPayAmount()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getCodAmount()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getCodFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getRefundAmount()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getDeliveryGoodsFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getArriveDeliGoodsFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getTotalFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getService_fee()));
	columnList.add(entity.getReceiveOrgName());
	columnList.add(entity.getCheckAccountOrgName());
	columnList.add(entity.getCreateOrgName());
	columnList.add(entity.getGoodsName());
	columnList.add(entity.getGoodsPackage());
	columnList.add(entity.getReceiveCustomerName());
	/**
	 * @author 218392 zhangyongxue
	 * @date 2015-04-08 14:00:00
	 * 接货人手机和电话
	 */
	columnList.add(entity.getReceiveCustomerMobilephone());
	columnList.add(entity.getReceiverCustomerTel());
	columnList.add(entity.getReceiveCustomerAddress());
	columnList.add(entity.getSigner());
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getInsuranceFee()));
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getInsuranceAmount()));
	columnList.add(DictUtil.rendererSubmitToDisplay(entity.getPaidMethod(),"SETTLEMENT__PAYMENT_TYPE"));
	columnList.add(changeProductCodeToName(entity.getTransportType()));
	columnList.add(entity.getCreateUserName());
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getUnitPrice()));
	//增加提货方式修改逻辑，update by huangwei 262036  20151015
	if(!entity.getReceiveMethod().equals(DictUtil.rendererSubmitToDisplay(entity.getReceiveMethod(),"PICKUPGOODSHIGHWAYS"))){
		columnList.add(DictUtil.rendererSubmitToDisplay(entity.getReceiveMethod(),"PICKUPGOODSHIGHWAYS"));
	}else if(!entity.getReceiveMethod().equals(DictUtil.rendererSubmitToDisplay(entity.getReceiveMethod(),"PICKUPGOODSAIR"))){
		columnList.add(DictUtil.rendererSubmitToDisplay(entity.getReceiveMethod(),"PICKUPGOODSAIR"));
	}else{
		columnList.add(DictUtil.rendererSubmitToDisplay(entity.getReceiveMethod(),"PICKUPGOODSAIRSPECIALDELIVERYTYPE"));
	}
	columnList.add(entity.getTransportationRemark());
	columnList.add(/** BigDecimal */
	String.valueOf(entity.getTransportFee()));
	//司机工号
	columnList.add(/** BigDecimal */
			String.valueOf(entity.getEmp_code()));
	//司机姓名
	columnList.add(/** BigDecimal */
			String.valueOf(entity.getEmp_name()));
	
	return columnList;
    }

    /**
	 * 运输类型编码转化
	 * 
	 * @author foss-zhangxiaohui
	 * @param
	 * @date 2013-03-11 下午7:40:42
	 * @return
	 */
	private String changeProductCodeToName(String productCode) {
		if(StringUtils.isEmpty(productCode)){
			throw new BusinessException("导出时查询条件运输性质的编码为空！");
		}else{
			List<ProductEntity> productEntities = productDao.queryProductsByCode(productCode);
			if(CollectionUtils.isNotEmpty(productEntities) && StringUtils.isNotBlank(productEntities.get(0).getName())){
				 return productEntities.get(0).getName();
			}else{
				return "未知的运输类型";
			}
		}
			
	}
    
    @Override
    public SaleDepartmentEntity querySaleDepartmentByCode(String codeStr) {
	return saleDepartmentService.querySaleDepartmentByCode(codeStr);
    }
    @Override
    public Map<String, Object> printSalesStatementByComplexCondation(
	    WaybillListByComplexCondationDto condition) {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @param waybillQueryService
     *            the waybillQueryService to set
     */
    public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
	this.waybillQueryService = waybillQueryService;
    }
    
    /**
     * @param saleDepartmentService the saleDepartmentService to set
     */
    public void setSaleDepartmentService(
    	ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }
    
    public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

}
