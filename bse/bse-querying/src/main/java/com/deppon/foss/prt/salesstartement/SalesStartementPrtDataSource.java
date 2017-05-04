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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/salesstartement/SalesStartementPrtDataSource.java
 * 
 * FILE NAME        	: SalesStartementPrtDataSource.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.salesstartement;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.querying.server.service.ISalesstatementService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.dto.CalcTotalFeeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesStatementByComplexCondationResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillListByComplexCondationDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
/**
 * 营业报表数据预览
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2013-2-20 上午8:55:22</p>
 * @author 100847-foss-GaoPeng
 * @date 2013-2-20 上午8:55:22
 * @since
 * @version
 */
public class SalesStartementPrtDataSource implements JasperDataSource {

	
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesStartementPrtDataSource.class);
    private static final String SALES_STATEMENT_SERVICE_BEAN = "salesstatementService";
    private static final String DICTIONARY_VALUE_SERVICE_BEAN = "dataDictionaryValueService";
    private static final String PRINT_PARAME_ERROR_CHAR = "T";
    private static final String DATA_BLANK = " ";
    
	/**
	 * <p>
	 * 报表的参数收集处理
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2013-2-27 下午2:46:42
	 * @param jasperContext
	 * @return
	 * @throws Exception
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		LOGGER.info(" *************** PRINT : Into the salesstartment parameter print data. *************** ");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currentSalesman",
				blankWhenNullToString(jasperContext, "currentSalesman"));
		parameters.put("salesDepartment",
				blankWhenNullToString(jasperContext, "salesDepartment"));
		Date currentDate = null;
		WaybillListByComplexCondationDto condations = getConditions(jasperContext);
		if (null != condations.getStartBillTime()) {
			currentDate = condations.getStartBillTime();
		} else if (null != condations.getBeginDeliverTime()) {
			currentDate = condations.getBeginDeliverTime();
		} else {
			currentDate = new Date();
		}
		
		parameters.put("receptionTime",
				blankWhenNullToString(null, currentDate));
		Map<String, Object> queryMap = getQuerySalesStartements(jasperContext);
		Object totals = queryMap.get(QueryingConstant.TOTAL_FEE_DTO);
		if (null != totals) {
			CalcTotalFeeDto totalFee = (CalcTotalFeeDto) totals;
			
			/**
			 * 2013-06-06 22:08 zengJunFan
			 * 
			 * 【 注】： 经过需求人员杨巍确认,每天的  到付总额 是 代收货款+ 到付 ， 
			 * 	而经调查：接送货开发组，传过来的【到付金额】实际就是【到付总额】
			 * 
			 * 	而经过杨巍确认： 每天的收入总额 ==到付款+预付金额  == (到付总额-代收货款)+预付金额
			 * 	而且，接送货传过来的amountTotal实际是把代收货款总额+到付款+预付金额的
			 */
			
			//到付额 =到付总额-代收款
			BigDecimal toPay =subtract( totalFee.getToPayAmountTotal(),totalFee.getCodAmountTotal());
			
			//到付总额   
			parameters.put("summary",
							blankWhenNullToString(null,
									totalFee.getToPayAmountTotal()) + "  ");
			parameters.put("agency",
					blankWhenNullToString(null, totalFee.getCodAmountTotal())+" ");
			//总票数
			parameters.put("pageCount", blankWhenNullToString(null, totalFee.getPageCount()+" "));
			//总件数
			parameters.put("goodsQtyTotal", blankWhenNullToString(null, totalFee.getGoodsQtyTotal()+" "));
			//总重量
			parameters.put("goodsWeightTotal", blankWhenNullToString(null, totalFee.getGoodsWeightTotal()+"   "));
			
			//预付金额
			parameters.put("prePayAmountTotal", blankWhenNullToString(null, totalFee.getPrePayAmountTotal()+" "));
			//到付额
			parameters.put("toPayAmountTotal", blankWhenNullToString(null, toPay+" "));
			//代收总款
			parameters.put("codAmountTotal", blankWhenNullToString(null, totalFee.getCodAmountTotal()+" "));
			//收入总额
			parameters.put("amountTotal", blankWhenNullToString(null, NumberUtils.sum(totalFee.getPrePayAmountTotal(),toPay)+" "));
			//定义现金 ，月结，支票，欠款，到付的款数
			parameters.put("cash", blankWhenNullToString(null,totalFee.getCashAmountTotal()+" "));
			//临时欠款
			parameters.put("arrearage", blankWhenNullToString(null,totalFee.getArrearAmountTotal()+" "));
			//月结总额
			parameters.put("balance", blankWhenNullToString(null,totalFee.getMonthlyAmountTotal()+" "));
			//其他金额
			parameters.put("others", blankWhenNullToString(null,totalFee.getOtherAmountTotal()+" "));
			//开单金额(收入额)
			parameters.put("billing", blankWhenNullToString(null,NumberUtils.sum(totalFee.getPrePayAmountTotal(),toPay))+" ");
			
		}
		
		
		
		LOGGER.info(" *************** PRINT : End salesstartment parameter print data. *************** ");
		return parameters;
	}

    /**
     * <p>报表详细信息的数据转化处理</p> 
     * @author 100847-foss-GaoPeng
     * @date 2013-2-27 下午2:47:00
     * @param jasperContext
     * @return
     * @throws Exception 
     * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
     */
    @Override
    @SuppressWarnings({ "unchecked" })
    public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext){
	List<Map<String, Object>> detailDatas = new ArrayList<Map<String, Object>>();
	List<SalesStatementByComplexCondationResultDto> salesStatementDtos = null;
	LOGGER.info(" *************** PRINT : Into the salesstartment detail print data. *************** ");
	Map<String, Object> queryMap = getQuerySalesStartements(jasperContext);
	Object details = queryMap.get(QueryingConstant.RESULT);
	if (null != details) {
	    salesStatementDtos = (List<SalesStatementByComplexCondationResultDto>) details;
	    for (SalesStatementByComplexCondationResultDto salesStatement : salesStatementDtos) {
		Map<String, Object> detailsObj = new HashMap<String, Object>();
		detailsObj.put("oddNumbers", blankWhenNullToString(null, salesStatement.getWaybillNo()));
		detailsObj.put("destinationStation", blankWhenNullToString(null, salesStatement.getTargetOrgName()));
		detailsObj.put("numberOfCases", blankWhenNullToZore(salesStatement.getGoodsQtyTotal(), false));
		detailsObj.put("grossWeight", blankWhenNullToZore(salesStatement.getGoodsWeightTotal(), true));
		detailsObj.put("preceWeight", blankWhenNullToZore(salesStatement.getBillWeight(), true));
		detailsObj.put("amountAdvanced",blankWhenNullToZore(salesStatement.getPrePayAmount(), true));
		// 默认值
		BigDecimal toPayAmount = (BigDecimal) blankWhenNullToZore(salesStatement.getToPayAmount(), true);
		BigDecimal payment = (BigDecimal) blankWhenNullToZore(salesStatement.getCodAmount(), true);
		//到付额 =到付总额-代收货款
		detailsObj.put("freightToCollect", subtract(toPayAmount, payment));
		//代收款
		detailsObj.put("payment", payment);
		//到付总额
		detailsObj.put("totalCollect", toPayAmount + " ");
		// 转换值
		String paidMethod = salesStatement.getPaidMethod();
		if (null != paidMethod) {
		    detailsObj.put("modeOfPayment", blankWhenNullToString(null, getDictionaryValue(jasperContext, paidMethod)));
		    if (WaybillConstants.MONTH_PAYMENT.equals(paidMethod) || WaybillConstants.CASH_PAYMENT.equals(paidMethod)) {
			detailsObj.put("settlementObject", blankWhenNullToString(null, salesStatement.getReceiveCustomerName()));
		    }
		    if (WaybillConstants.ARRIVE_PAYMENT.equals(paidMethod)) {
			detailsObj.put("settlementObject", blankWhenNullToString(null, salesStatement.getDeliverDeptName()));
		    }
		}
		detailDatas.add(detailsObj);
	    }
	}
	LOGGER.info(" *************** PRINT : End salesstartment detail print data. *************** ");
	return detailDatas;
    }
    /**
     * 减法 
     * @param d1 大的数
     * @param d2 小的数
     * @return
     */
    protected BigDecimal subtract(BigDecimal big, BigDecimal small) {
//		BigDecimal sub =BigDecimal.ZERO;
		return big.subtract(small);
   	}

    protected WaybillListByComplexCondationDto getConditions(JasperContext jasperContext) {
	WaybillListByComplexCondationDto condation = new WaybillListByComplexCondationDto();
	for (String param : jasperContext.getParamkeys()) {
	    Field field = ReflectionUtils.findField(WaybillListByComplexCondationDto.class, param);
	    if (null != field) {
		Object tempObj = null;
		ReflectionUtils.makeAccessible(field);
		if (field.getType().isAssignableFrom(Date.class)) {
		    tempObj = blankWhenNullToDate(jasperContext, param);
		} else {
		    tempObj = blankWhenNullToString(jasperContext, param);
		}
		ReflectionUtils.setField(field, condation, tempObj);
	    }else{
		continue;
	    }
	}
	
	return condation;
    }

    protected Date blankWhenNullToDate(JasperContext jasperContext, String key) {
	String tempStr = blankWhenNullToString(jasperContext, key);
	if (StringUtils.isNotBlank(tempStr)) {
	    return DateUtils.strToDate(StringUtils.replaceOnce(tempStr, PRINT_PARAME_ERROR_CHAR, DATA_BLANK));
	}
	return null;
    }
    
    protected Object blankWhenNullToZore(Object tempVal, boolean isBigData) {
	String tempObj = null;
	if (null == tempVal || StringUtils.isBlank(tempVal.toString())) {
	    tempObj = NumberConstants.NUMERAL_S_ZORE;
	} else {
	    tempObj = tempVal.toString();
	}
	if(isBigData){
	    return NumberUtils.createBigDecimal(tempObj);
	} else {
	    return Integer.valueOf(tempObj);
	}
    }
    
    protected String blankWhenNullToString(JasperContext jasperContext, Object tempVal) {
	Object tempObj = null;
	if (null != tempVal) {
	    if (null == jasperContext) {
		if (tempVal instanceof Date) {
		    return DateUtils.convert((Date) tempVal);
		} else {
		    tempObj = tempVal;
		}
	    } else {
		tempObj = jasperContext.get(tempVal.toString());
	    }
	}
	if (null != tempObj) {
	    String valStr = String.valueOf(tempObj);
	    if (StringUtils.isNotBlank(valStr)) {
		return valStr;
	    }
	}
	return null;
    }
    
    public Map<String, Object> getQuerySalesStartements(JasperContext jasperContext) {
//	Map<String, Object> queryMap = new HashMap<String, Object>();
        ApplicationContext context = jasperContext.getApplicationContext();
        ISalesstatementService salesstatementService = (ISalesstatementService) context.getBean(SALES_STATEMENT_SERVICE_BEAN);
        int start = NumberConstants.NUMERAL_ZORE, limit = Integer.MAX_VALUE;
        Map<String, Object> queryMap = salesstatementService.querySalesStatementByComplexCondation(getConditions(jasperContext), start, limit);
        return queryMap;
    }
    
    public String getDictionaryValue(JasperContext jasperContext, String key) {
	DataDictionaryValueEntity dictionaryValue = null;
	ApplicationContext context = jasperContext.getApplicationContext();
        IDataDictionaryValueService dictionaryValueService = (IDataDictionaryValueService) context.getBean(DICTIONARY_VALUE_SERVICE_BEAN);
        dictionaryValue = dictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE, key);
        if(null != dictionaryValue){
            return dictionaryValue.getValueName();
        } else {
            return null;
        }
    }
}
