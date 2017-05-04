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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/printWaybill/PrintWayBillPrtDataSource.java
 * 
 * FILE NAME        	: PrintWayBillPrtDataSource.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.prt.printWaybill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;

/**
 * 
(全打印界面数据填充)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-18 上午9:20:49,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-18 上午9:20:49
 * @since
 * @version
 */
public class PrintWayBillPrtDataSource implements JasperDataSource {

	private static final Log LOG = LogFactory
			.getLog(PrintWayBillPrtDataSource.class);


	/**
	 * 创建map data source
	 */
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) {
		Map<String, Object> parameter = new HashMap<String, Object>();

		parameter.put("waybillNo", jasperContext.get("waybillNo"));
	

		return parameter;
	}


	/**
	 * 创建map data  DETAIL source
	 */
	public List<Map<String, Object>> createDetailDataSource(JasperContext jctx) {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			/*****托运人信息 *******/
			m.put("consignor", jctx.get("consignor")); //"托运人公司"
			m.put("field1", jctx.get("field1")); //"联系方式"
			m.put("consignorNo", jctx.get("consignorNo"));// 会员卡 "$F发货人编码"
			m.put("consignorAddr", jctx.get("consignorAddr"));// "$F发货人地址"
			m.put("receiveCustomerContact", jctx.get("receiveCustomerContact"));// 收货客户联系人
			
			/*****收货人信息 ********/
			m.put("consignee", jctx.get("consignee"));// "收货人公司" "$F收货人"
			m.put("field2", jctx.get("field2"));// "$F收货人联系方式"
			m.put("addr", jctx.get("addr"));// "$F收货人地址"
			m.put("deliveryCustomerContact",jctx.get("deliveryCustomerContact"));
			m.put("receiveCustomerCode",jctx.get("receiveCustomerCode"));
			
			
			/*****增值业务信息 ********/
			m.put("insurance", jctx.get("insurance"));// "$F保险声明价值");
			m.put("proxyAmount", jctx.get("proxyAmount"));// "$F代收货款");
			m.put("accountName", jctx.get("accountName"));// "$F户名");
			m.put("openAccountBank", jctx.get("openAccountBank"));// "$F开户银行");
			m.put("payAccount", jctx.get("payAccount"));// "$F开户行账号");
			m.put("exchangeRate", jctx.get("exchangeRate"));// 港币汇率
			
			
			
			/*****服务 ********/
			m.put("product", jctx.get("product"));// "$F运输方式"
			m.put("deliveryType", jctx.get("deliveryType"));// "$F交货方式");
			m.put("payType", jctx.get("payType"));// "$F付款方式");
			m.put("signBill", jctx.get("signBill"));// "$F签收单");


			// 半打全打分界线...........................
			m.put("otherfee", jctx.get("otherfee"));
			m.put("stroeTransport", jctx.get("stroeTransport"));// "$F储运注意事项"

			m.put("onlineOrderNo", jctx.get("onlineOrderNo"));// "$F网上订单号"
			m.put("commodityName", jctx.get("commodityName"));// "$F货物品名"
			m.put("packaging", jctx.get("packaging"));// "$F包装"

			m.put("startStop", jctx.get("startStop"));// "$F始发站"
			m.put("endStop", jctx.get("endStop"));// "$F目的站"
			m.put("count", jctx.get("count"));// "$F件数");
			m.put("volume", jctx.get("volume"));// "$F体积");
			m.put("weight", jctx.get("weight"));// "$F重量");
			m.put("isDoor", jctx.get("isDoor"));// "$F上门接货");
			m.put("chargeWeight", jctx.get("chargeWeight"));// "$F计费重量");
			m.put("rate", jctx.get("rate"));// "$F费率/价格");
			m.put("freight", jctx.get("freight"));// "$F运费");
			
			m.put("insuranceCharge", jctx.get("insuranceCharge"));// "$F保价费");
			m.put("promotionsFee", jctx.get("promotionsFee"));// "$F已优惠金额");
			
			m.put("noPayAmount", jctx.get("noPayAmount"));// "$F现付费用");
			m.put("toPayAmount", jctx.get("toPayAmount"));// "$F到付费用");
			m.put("makeBillInfo", jctx.get("makeBillInfo"));// "$F制单人/制单时间");

			m.put("consigorNetInfo", jctx.get("consigorNetInfo"));// "$F发货网点/发货网点地址/电话");
			m.put("deliveryInfo", jctx.get("deliveryInfo"));// "$F提货网点/提货网点地址/电话");
			
			m.put("exchangeRate", jctx.get("exchangeRate"));// "$F人民币对港币汇率");
			m.put("noPayAmountHK", jctx.get("noPayAmountHK"));// "$F预付金额 * 人民币对港币汇率");
			m.put("toPayAmountHK", jctx.get("toPayAmountHK"));// "$F到付金额 * 人民币对港币汇率");
			m.put("insuranceHK", jctx.get("insuranceHK"));// "$F保价申明价值 * 人民币对港币汇率");
			m.put("insuranceChargeHK", jctx.get("insuranceChargeHK"));// "$F保价费 * 人民币对港币汇率");
			m.put("refundTypeName", jctx.get("refundTypeName"));// "$F退款类型名称");
			m.put("benefitActivity", jctx.get("benefitActivity"));//出发城市打印广告专用
			lst.add(m);
		
		} catch (Exception e) {
			LOG.error("打印异常",e);
		}
		return lst;
	}
}