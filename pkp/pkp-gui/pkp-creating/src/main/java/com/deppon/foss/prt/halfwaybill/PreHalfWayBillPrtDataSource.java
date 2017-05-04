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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/halfwaybill/PreHalfWayBillPrtDataSource.java
 * 
 * FILE NAME        	: PreHalfWayBillPrtDataSource.java
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
package com.deppon.foss.prt.halfwaybill;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.util.ClassPathResourceUtil;

/**
 * (半预览界面数据填充)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:jiangfei,date:2012-10-17 下午10:56:33,
 * </p>
 * 
 * @author jiangfei
 * @date 2012-10-17 下午10:56:33
 * @since
 * @version
 */

public class PreHalfWayBillPrtDataSource implements JasperDataSource {

	/**
	 * logger
	 */
	private static final Log LOG = LogFactory
			.getLog(PreHalfWayBillPrtDataSource.class);

	/**
	 * 创建map data source
	 */
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) {
		Map<String, Object> parameter = new HashMap<String, Object>();


		parameter.put("waybillNo", jasperContext.get("waybillNo"));
		ClassPathResourceUtil util = new ClassPathResourceUtil(jasperContext.getClassLoader());

		InputStream imgin = util
				.getInputStream("com/deppon/foss/prt/halfwaybill/images/waybill.jpg");
		InputStream img = util
				.getInputStream("com/deppon/foss/prt/waybillhongkong/images/waybillhongkong.jpg");
		parameter.put("waybillbkg", imgin);
		parameter.put("waybillhongkongbkg", img);

		return parameter;
	}

	public List<Map<String, Object>> createDetailDataSource(JasperContext jctx) {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();

		try {
			Map<String, Object> m = new HashMap<String, Object>();

			// 半打全打分界线...........................
			m.put("otherfee", jctx.get("otherfee"));
			m.put("stroeTransport", jctx.get("stroeTransport"));// "$F储运注意事项"

			m.put("onlineOrderNo", jctx.get("onlineOrderNo"));// "$F网上订单号"
			m.put("commodityName", jctx.get("commodityName"));// "$F货物品名"
			m.put("packaging", jctx.get("packaging"));// "$F包装"
			m.put("product", jctx.get("product")); //运输性质
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
			m.put("insuranceCharge1", jctx.get("insuranceCharge1"));// "$F保价费");
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
			m.put("benefitActivity", jctx.get("benefitActivity"));//出发城市打印广告专用
			lst.add(m);
		} catch (Exception e) {
			LOG.error("打印部分:半预览异常",e);
		}
		return lst;
	}

}