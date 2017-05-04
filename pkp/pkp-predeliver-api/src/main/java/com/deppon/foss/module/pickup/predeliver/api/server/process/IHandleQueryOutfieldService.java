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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/process/IHandleQueryOutfieldService.java
 * 
 * FILE NAME        	: IHandleQueryOutfieldService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.process;

import java.util.List;

/**
 * 
 * 外场相关共通接口
 * 
 * @author ibm-wangfei
 * @date Feb 26, 2013 4:51:50 PM
 */
public interface IHandleQueryOutfieldService {
	/**
	 * 
	 * 获取最终库存部门Code和库区编码 参照WaybillStockService.getEndStockCodeAndAreaCode方法
	 * 
	 * @param orgCode 部门CODE
	 * @return List<String>list.get(0) = '最终库存部门Code';list.get(1)='库区编码'
	 * @author ibm-wangfei
	 * @date Feb 26, 2013 3:33:05 PM
	 */
	List<String> getEndStockCodeAndAreaCode(String orgCode);

	/**
	 * 
	 * 根据传入参数，获取完整的地址信息
	 * 
	 * @param provCode 省code
	 * @param cityCode 市code
	 * @param distCode 区code
	 * @param address 地址
	 * @return 完整地址
	 * @author ibm-wangfei
	 * @date Mar 7, 2013 9:53:28 AM
	 */
	String getCompleteAddress(String provCode, String cityCode, String distCode, String address);
	
	/** 
	 * @Title: getCompleteAddress 
	 * @Description: 根据传入参数，获取完整的地址信息
	 * @param @param provCode 省code
	 * @param @param cityCode 市code
	 * @param @param distCode 区code
	 * @param @param villageCode 乡镇code
	 * @param @param address 地址
	 * @param @return  完整地址
	 * @author fangwenjun 237982
	 * @date 2015年9月10日 下午4:58:38 
	 * @return String
	 */
	String getCompleteAddress(String provCode, String cityCode, String distCode, String villageCode, String address);

	/**
	 * 
	 * 根据传入参数，获取完整的地址信息
	 * 
	 * @param provCode 省code
	 * @param cityCode 市code
	 * @param distCode 区code
	 * @param address 地址
	 * @param receiveCustomerAddress 地址备注
	 * @return 完整地址
	 * @author ibm-wangfei
	 * @date Mar 7, 2013 9:53:28 AM
	 */
	String getCompleteAddressAttachAddrNote(String receiveCustomerProvCode,String receiveCustomerCityCode, String receiveCustomerDistCode,
			String receiveCustomerAddress, String receiveCustomerAddressNote);
	
	/** 
	 * @Title: getCompleteAddressAttachAddrNote 
	 * @Description: 根据传入参数，获取完整的地址信息
	 * @param @param receiveCustomerProvCode 省code
	 * @param @param receiveCustomerCityCode  市code
	 * @param @param receiveCustomerDistCode 区code
	 * @param @param receiveCustomerVillageCode 乡镇Code
	 * @param @param receiveCustomerAddress 地址
	 * @param @param receiveCustomerAddressNote 地址备注
	 * @param @return    完整地址 
	 * @author fangwenjun 237982
	 * @date 2015年9月10日 下午5:10:08 
	 * @return String
	 */
	String getCompleteAddressAttachAddrNote(String receiveCustomerProvCode,String receiveCustomerCityCode, String receiveCustomerDistCode,
			String receiveCustomerVillageCode, String receiveCustomerAddress, String receiveCustomerAddressNote);
	/*
	 * 获取最终库存部门Code和库区编码
	 * @param orgCode 部门CODE
	 * @return List<String>list.get(0) = '最终库存部门Code';list.get(1)='库区编码'
	 */
	public List<String> getEndStockCodeAndExpressAreaCode(String orgCode);
}