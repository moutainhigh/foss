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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ILabelPrintInfoService.java
 * 
 * FILE NAME        	: ILabelPrintInfoService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabelPrintEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteCommonsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.FreightRouteParamCommonsDto;

/**
 * (得到标签打印的信息)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-13 下午4:03:57,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-13 下午4:03:57
 * @since
 * @version
 */
public interface ILabelPrintInfoService extends IService{

    /**
     * 得到标签打印的信息
     * 
     * @author foss-jiangfei
     * @date 2012-11-13 下午4:24:34
     * @param waybillNo
     *            运单号
     * @param serialNos
     *            流水号
     * @param  waybillStatus 运单处理状态          
     * @return
     * @see
     */
    BarcodePrintLabelDto getLabelPrintInfos(String waybillNo,
	    List<String> serialNos,String waybillStatus);
    

    /**
     * 得到标签打印的信息 分批配载时候调用
     * 
     * @author foss-jiangfei
     * @date 2012-11-13 下午4:24:34
     * @param waybillNo
     *            运单号
     * @param serialNos
     *            流水号
     * @return
     * @see
     */
    List<BarcodePrintLabelDto> getLabelPrintInfoForDepart(String waybillNo,
	    List<String> serialNos);

    /**
     * @author 105888-foss-zhangxingwang
     * @date 2013-7-10 17:53:45
     * @param waybillEntity
     * @param serialNos
     * @return
     */
	String isExsitWaybillRfcDestinationAndHandlerNotTodo(
			WaybillEntity waybillEntity, List<String> serialNos);


	List<BarcodePrintLabelDto> getLabelPrintInfoReceAndStockAndArri(
			String waybillNo, List<String> serialNos);


	List<FreightRouteCommonsDto> getFreightRouteCommonsList(
			List<FreightRouteParamCommonsDto> freightRouteDtoList);


	boolean queryIsExpressBill(String waybillNo);
	/**
	 * <p>查询对应的城市广告信息</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-28 15:25:51
	 * @param receiverOgCode
	 */
	String getWaybillDocAd(String receiverOgCode, String cityPattern);

	/**
	 * 加入走货路径需要进行相关数据的封装
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-6-30 20:03:30
	 * @param eWaybillBillDto
	 */
	List<EWaybillPrintDto> printEWaybillInfos(String waybillNo, List<String> serialNoList);

	/**
	 * 判定是否电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-27 20:39:30
	 */
	boolean isEWaybillInfoByWaybillNo(String waybillNo);
	BarcodePrintLabelDto getWaybillInfo(String waybillNo,String waybillStatus);
/**
	 * 中转场打印快递标签信息封装
     * @author 220125  yangxiaolong
     * @date 2015-02-22 
     * @param waybillEntity
     * @param serialNos
     * @return
     */

	List<BarcodePrintLabelDto> getLabelPrintInfoExpress(String waybillNo,
			List<String> serialNos);

	/**
	 * 获取快递标签打印优化信息远程接口 获得LIST
	 * @param waybillNo
	 * @param waybillstatus
	 * @author foss-218438
	 * @param waybillstatus 
	 * @return
	 */
	BarcodePrintLabelDto getCommonLabelPrintInfoExpress(String waybillNo, List<String> serialNos, String waybillstatus);

	/**
	 * 快递标签信息封装供中转使用
	 */
	List<BarcodePrintLabelDto> getLabelPrintInfoForDepartExpress(String waybillNo, List<String> serialNos);


	/**
     * 得到标签打印的信息
     * 
     * @author fanbangyu
     * @date 2015-09-17 下午4:24:34
     * @param waybillNo
     *            运单号
     * @param serialNos
     *            流水号
     * @param  waybillStatus 运单处理状态          
     * @return
     * @see
     */
  

	LabelPrintEntity findGxgLabelPrint(WaybillPendingEntity pend);
	
}