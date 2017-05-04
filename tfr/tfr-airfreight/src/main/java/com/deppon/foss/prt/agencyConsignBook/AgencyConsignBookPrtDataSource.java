/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/prt/agencyConsignBook/AgencyConsignBookPrtDataSource.java
 *  
 *  FILE NAME          :AgencyConsignBookPrtDataSource.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: foss-print
 * PACKAGE NAME: com.deppon.foss.prt.zhoudejun
 * FILE    NAME: ZhouDeJunPrtDataSource.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.prt.agencyConsignBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;

/**
 * TODO（描述类的职责）
 * @author 104306-foss-zhoudejun
 * @date 2012-11-3 上午10:26:20
 */
public class AgencyConsignBookPrtDataSource implements JasperDataSource {

	//给转换json对象赋值
	private Map<?,?> maps;

	public void getMaps(String record){
		ObjectMapper mapper = new ObjectMapper();
		try {
			//执行json转换操作
			maps= mapper.readValue(record, Map.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 104306-foss-zhoudejun
	 * @date 2012-11-3 上午10:26:28
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings("rawtypes")
	public Map createParameterDataSource(JasperContext jasperContext) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		//获取前台json
		String record = (String)jasperContext.get("record");
		//调用json转换
		getMaps(record);
		//设置始发站
		parameter.put("deptRegionName", maps.get("deptRegionName"));
		//设置目的站
		parameter.put("arrvRegionName", maps.get("arrvRegionName"));
		//设置托运人单位/姓名
		parameter.put("shipperName", maps.get("shipperName"));
		//设置托运人电话/手机
		parameter.put("shipperContactPhone", maps.get("shipperContactPhone"));
		//设置 收货人单位/姓名
		parameter.put("receiverName", maps.get("receiverName"));
		//设置收货人电话/手机
		parameter.put("receiverContactPhone", maps.get("receiverContactPhone"));
		//设置收货人地址
		parameter.put("receiverAddress", maps.get("receiverAddress"));
		//设置储运事项
		parameter.put("pickupType", maps.get("pickupType"));
		//设置提货方式
		parameter.put("storageItem", maps.get("storageItem"));
		//设置货物名称
		parameter.put("goodsName", maps.get("goodsName"));
		//设置运价
		parameter.put("fee", maps.get("fee"));
		//设置总运费
		parameter.put("feeTotal", maps.get("feeTotal"));
		//设置件数
		parameter.put("goodsQty", maps.get("goodsQty"));
		//设置重量
		parameter.put("billingWeight", maps.get("grossWeight"));
		//设置 外发代理
		parameter.put("agencyName", maps.get("agencyName"));
		//设置 时间
		parameter.put("flightDate", maps.get("createTime"));
		//设置制单人
		parameter.put("createUserName", maps.get("createUserName"));
		return parameter;
	}

	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 104306-foss-zhoudejun
	 * @date 2012-11-3 上午10:26:28
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List createDetailDataSource(JasperContext jasperContext) {
		List lst = new ArrayList();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("deptRegionName", "SEW");
		lst.add(dataMap);
		return lst;
	}
}