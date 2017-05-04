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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/goodswaybill/GoodsWayBillPrtDataSource.java
 * 
 * FILE NAME        	: GoodsWayBillPrtDataSource.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.prt.goodswaybill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.querying.server.service.IIntegrativeQueryService;
import com.deppon.foss.module.base.querying.shared.vo.WaybillVo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;
/**
 * 打印运单数据预览
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2013-1-29 下午2:13:02</p>
 * @author 100847-foss-GaoPeng
 * @date 2013-1-29 下午2:13:02
 * @since
 * @version
 */
public class GoodsWayBillPrtDataSource implements JasperDataSource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsWayBillPrtDataSource.class);
    private static final String DATA_BLANK = "  ";
    private static final String DATA_POINT = ",";
    private static final String DATA_SLASH = "/";
    private static final String DATA_LINE = "-";
  private static final String BUSINESS = "DEAP";
  private static final String STANDARD = "特准快件";//DN201605280007需求要求打印白单时将商务专递修改为特准快件，原数据不动
     
    
    @Override
    public Map<String, Object> createParameterDataSource(JasperContext jasperContext) throws Exception {
	//"综合查询"Service
	ApplicationContext context = jasperContext.getApplicationContext();
	IEmployeeService employeeService = (IEmployeeService) context.getBean("employeeService");
	IIntegrativeQueryService integrativeQueryService = (IIntegrativeQueryService) context.getBean("integrativeQueryService");
	IAdministrativeRegionsService regionsService = (IAdministrativeRegionsService) context.getBean("administrativeRegionsService");
	IProductService productService = (IProductService) context.getBean("productService");
	
	//数据提取
	String goodsWayBillNumber = jasperContext.get("goodsWayBillNumber").toString();
	WaybillVo waybill = integrativeQueryService.searchWaybillInfoByWaybillNo(goodsWayBillNumber);
	//声明打印值MAP
	Map<String, Object> parameters = new HashMap<String, Object>();
	LOGGER.info(" ******************** PRINT : Into the air waybill print data ******************** ");
	if(null != waybill){
	    WaybillInfoByWaybillNoReultDto waybillInfo = waybill.getWaybillInfoByWaybillNoReultDto();
	    if(null != waybillInfo){
		parameters.put("goodsWayBillNumber", goodsWayBillNumber+DATA_BLANK);
		parameters.put("startingStation", regionsService.queryAdministrativeRegionsNameByCode(waybillInfo.getReceiveOrgCity()));
		ProductEntity product = productService.getProductByCache(waybillInfo.getProductCode(), new Date());
		if(null != product){
			if(StringUtil.isNotEmpty(product.getCode()) && BUSINESS.equals(product.getCode())){
				parameters.put("transportProperties", STANDARD);
			}else{
				parameters.put("transportProperties", product.getName());
			}
	    }
		parameters.put("destinationStation", waybillInfo.getTargetOrgCode());
		parameters.put("consignor", waybillInfo.getDeliveryCustomerContact());
		parameters.put("consignee", waybillInfo.getReceiveCustomerContact());
		StringBuffer telephone = new StringBuffer();
		// BUG-23915
		String deliveryCustomerPhone = waybillInfo.getReceiveCustomerPhone(); //  waybillInfo.getDeliveryCustomerPhone();
		String deliveryCustomerMobilephone = waybillInfo.getReceiveCustomerMobilephone(); // waybillInfo.getDeliveryCustomerMobilephone();
		if(StringUtils.isNotBlank(deliveryCustomerPhone)){
		    telephone.append(deliveryCustomerPhone).append(DATA_POINT);
		}
		if(StringUtils.isNotBlank(deliveryCustomerMobilephone)){
		    telephone.append(deliveryCustomerMobilephone);
		}
		parameters.put("telephone", telephone.length() == NumberConstants.NUMERAL_ZORE ? null : telephone.toString());
		parameters.put("address", waybillInfo.getReceiveCustomerAddress());//  waybillInfo.getDeliveryCustomerAddress());
		parameters.put("goodsName", waybillInfo.getGoodsName());
		parameters.put("packaging", waybillInfo.getGoodsPackage());
		parameters.put("totalPiece", String.valueOf(waybillInfo.getGoodsQtyTotal()));
		parameters.put("totalWeight", String.valueOf(waybillInfo.getGoodsWeightTotal()+DATA_BLANK));
		parameters.put("totalVolume", String.valueOf(waybillInfo.getGoodsVolumeTotal()+DATA_BLANK));
		parameters.put("insuranceValue", String.valueOf(waybillInfo.getInsuranceAmount()));
		//BUG-20913 添加的包装费用
		parameters.put("packageFee", String.valueOf(waybillInfo.getPackageFee()));
		//代收货款手续费
		parameters.put("codFee", String.valueOf(waybillInfo.getCodFee()));
		//接货费
		parameters.put("pickUpFee", String.valueOf(waybillInfo.getPickUpFee()));
		StringBuffer size = new StringBuffer();
		if(StringUtils.isNotEmpty(waybillInfo.getGoodsSize())){
		    size.append(waybillInfo.getGoodsSize()).append(DATA_BLANK).append(DATA_BLANK);//添加空格
		    parameters.put("size", size.toString()+DATA_BLANK);
		}
		String flightNumberType = waybillInfo.getFlightNumberType();
		Date flightShift = waybillInfo.getFlightShift();
		StringBuffer flightDate = new StringBuffer();
		if(StringUtils.isNotBlank(flightNumberType)){
		    flightDate.append(flightNumberType).append(DATA_SLASH);
		} else {
		    flightDate.append(DATA_LINE).append(DATA_SLASH);
		}
		if(null != flightShift){
		    flightDate.append(DateUtils.convert(flightShift, DateUtils.DATE_FORMAT));
		} else {
		    flightDate.append(DATA_LINE);
		}
		parameters.put("flightDate", flightDate.toString() + DATA_BLANK);
		Date preCustomerPickupTime = waybillInfo.getPreCustomerPickupTime();
		if(null != preCustomerPickupTime){
		    parameters.put("estimatedArrivalTime", DateUtils.convert(preCustomerPickupTime, DateUtils.DATE_FORMAT) + DATA_BLANK);
		} else {
		    parameters.put("estimatedArrivalTime", DATA_LINE);
		}
		parameters.put("chargeMode", waybillInfo.getBillingType());
		parameters.put("freightRates", String.valueOf(waybillInfo.getTransportFee())+DATA_BLANK);
		parameters.put("freightCost", String.valueOf(waybillInfo.getTotalFee()));
		parameters.put("deliveryExpense", String.valueOf(waybillInfo.getDeliveryGoodsFee()));
		parameters.put("insuranceCharges", String.valueOf(waybillInfo.getInsuranceFee()) + DATA_BLANK);
		parameters.put("otherCharges", String.valueOf(waybillInfo.getOtherFee()) + DATA_BLANK);
		parameters.put("collectionOnDelivery", String.valueOf(waybillInfo.getCodAmount()));
		parameters.put("otherChargesAndItems", waybillInfo.getTransportationRemark());
		parameters.put("deliveryTypes", waybillInfo.getReceiveMethod());
		parameters.put("paymentTypes", waybillInfo.getPaidMethod());
		parameters.put("totalCashPayments", String.valueOf(waybillInfo.getPrePayAmount()));
		String originalWaybillNoTotalFee = DATA_BLANK , oriAndReturntotalFee = DATA_BLANK ;
		if(null != waybillInfo.getOriginalWaybillNoTotalFee() && StringUtils.isNotBlank(String.valueOf(waybillInfo.getOriginalWaybillNoTotalFee())))
			originalWaybillNoTotalFee = String.valueOf(waybillInfo.getOriginalWaybillNoTotalFee());
		else
			oriAndReturntotalFee = String.valueOf(waybillInfo.getToPayAmount());
		parameters.put("originalWaybillNoTotalFee", originalWaybillNoTotalFee);
		if(null != waybillInfo.getOriAndReturntotalFee())
			oriAndReturntotalFee = String.valueOf(waybillInfo.getOriAndReturntotalFee());
		parameters.put("oriAndReturntotalFee", oriAndReturntotalFee);
		parameters.put("arriveTotalPayments", String.valueOf(waybillInfo.getToPayAmount()));
		parameters.put("signForPeople", DATA_BLANK);
		parameters.put("startingStationName", waybillInfo.getCustomerPickupOrgName());
		parameters.put("startingStationAddress", waybillInfo.getCustomerPickupOrgAddress());
		parameters.put("startingStationTelphone", waybillInfo.getCustomerPickupOrgPhone()+DATA_BLANK);
		parameters.put("originatingDepartment", waybillInfo.getReceiveOrgName());
		EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(waybillInfo.getCreateUserCode());
		if(null != employee){
		    parameters.put("keyBoarder", employee.getEmpName());
		}
		parameters.put("billingDate", DateUtils.convert(waybillInfo.getBillTime(), DateUtils.DATE_TIME_FORMAT) + DATA_BLANK);
		StringBuffer printerWithDate = new StringBuffer(); 
		Object printer = jasperContext.get("printerWithDate");
		if(null != printerWithDate && StringUtils.isNotBlank(printer.toString())){
		    printerWithDate.append(printer).append(DATA_SLASH);
		} else {
		    printerWithDate.append(DATA_BLANK).append(DATA_SLASH);
		}
		printerWithDate.append(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT) + DATA_BLANK);
		parameters.put("printerWithDate", printerWithDate.toString());
	    }
	}
	LOGGER.info(" ********************** PRINT : End air waybill print data ******************** ");
	return parameters;
    }

    @Override
    public List<Map<String, Object>> createDetailDataSource(JasperContext jasperContext) throws Exception {
	Map<String, Object> m = new HashMap<String, Object>();
	List<Map<String,Object>> detailData = new ArrayList<Map<String,Object>>();
	detailData.add(m);
	return detailData;
    }

}
