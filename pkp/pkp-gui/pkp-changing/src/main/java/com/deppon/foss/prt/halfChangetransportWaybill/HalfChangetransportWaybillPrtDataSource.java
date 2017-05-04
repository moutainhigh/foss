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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/changetransportWaybill/ChangetransportWaybillPrtDataSource.java
 * 
 * FILE NAME        	: ChangetransportWaybillPrtDataSource.java
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
package com.deppon.foss.prt.halfChangetransportWaybill;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.print.util.ClassPathResourceUtil;

/**
 * (组装转运单打印需要的数据)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-17 下午10:38:31,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-17 下午10:38:31
 * @since
 * @version
 */
public class HalfChangetransportWaybillPrtDataSource implements JasperDataSource {

	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 多出来三项 与其他不同的
		paramMap.put("changedeststation", jasperContext.get("changedeststation"));// 目的站
		paramMap.put("newpickuporg", jasperContext.get("newpickuporg"));// 提货网点
		paramMap.put("changetype", jasperContext.get("changetype"));// 运输性质

		paramMap.put("waybillNo", jasperContext.get("waybillNo"));// 单号
		paramMap.put("applicant", jasperContext.get("applicant"));// 申请人
		paramMap.put("applydepartment", jasperContext.get("applydepartment"));// 申请部门
		paramMap.put("changereason", jasperContext.get("changereason"));// 更改原因
		paramMap.put("applytime", jasperContext.get("applytime"));// 申请时间

		paramMap.put("printtimes",jasperContext.get("printtimes"));// 更改次数???

		paramMap.put("goodsname", jasperContext.get("goodsname"));// 货物名称
		paramMap.put("package", jasperContext.get("package"));// 货物体积
		paramMap.put("goodsnum", jasperContext.get("goodsnum"));// 货物数量

		paramMap.put("weight", jasperContext.get("weight"));// 货物重量
		paramMap.put("volume", jasperContext.get("volume"));// 货物体积
		paramMap.put("size", jasperContext.get("size"));// 货物尺寸

		// 总费用差额
		paramMap.put("costvariance", jasperContext.get("costvariance"));
		ClassPathResourceUtil util = new ClassPathResourceUtil(jasperContext.getClassLoader());
		InputStream imgin = util
				.getInputStream("com/deppon/foss/prt/images/halfLine.jpg");
		paramMap.put("halfLine", imgin);
		return paramMap;
	}

	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < Integer.parseInt(jasperContext.get(
				"WaybillRfcChangeDetailDtoSize").toString()); i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			String temp = jasperContext.get("WaybillRfcChangeDetailDto" + i)
					.toString();
			m.put("changeitem", temp.substring(0, temp.lastIndexOf("&")));
			String oldStr = temp.substring(temp.lastIndexOf("&") + 1,
					temp.lastIndexOf("~"));
			m.put("changebeforeinfo", oldStr);

			String newStr = temp.substring(temp.lastIndexOf("~") + 1);

			m.put("changeafterinfo", newStr);

			lst.add(m);
		}

		return lst;
	}


}