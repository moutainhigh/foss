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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/ILabelPrintInfoHessianRemoting.java
 * 
 * FILE NAME        	: ILabelPrintInfoHessianRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;

/**
 * 获取标签打印信息远程接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-11-14 下午6:42:00,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-11-14 下午6:42:00
 * @since
 * @version
 */
public interface ILabelPrintInfoHessianRemoting extends IHessianService {

    /**
     * 获取标签打印信
     * 
     * @author foss-jiangfei
     * @date 2012-11-14 下午6:44:25
     * @param waybillNo
     * @return
     * @see
     */
    BarcodePrintLabelDto getLabelPrintInfos(String waybillNo,String waybillStatus);

    /**
     * 获取标签打印信
     * 
     * @author foss-jiangfei
     * @date 2012-11-14 下午6:44:25
     * @param waybillNo
     * @return
     * @see
     */
    List<BarcodePrintLabelDto> getLabelPrintInfo(String waybillNo,
	    List<String> serialNos);
    
    /**
     * 获取是否该单存在更改单记录
     * 
     * @author foss-zhangxingwang
     * @date 2013-7-9 14:19:59
     * @param waybillEntity
     * @param serialNos
     * @return
     * @see
     */
    String isExistDestinationAndTodoRfc(WaybillEntity waybillEntity, List<String> serialNos);
    
    /**
     * @功能 通过运单号，流水号查询运单所有走货路径信息
     * @author 105888-foss-zhangxingwang
     * @date 2013-7-12 12:56:11
     * @param waybillNo
     * @param serialNos
     * @return
     */
    List<BarcodePrintLabelDto> getLabelPrintInfoReceAndStockAndArri(String waybillNo, List<String> serialNos);

	boolean queryIsExpressBill(String waybillNo);
	String getWaybillDocAd(String receiverOgCode, String cityPattern);
	
	/**
	 * 打印电子运单Service
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-27 20:09:54
	 * @param waybillNo
	 * @param serialNoList
	 * @return
	 */
	List<EWaybillPrintDto> printEWaybillInfos(String waybillNo, List<String> serialNoList);
	/**
	 * 判定是否电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-27 20:39:30
	 */
	boolean isEWaybillInfoByWaybillNo(String waybillNo);
	
	/**
	 * 获取快递标签打印优化信息远程接口 获得LIST
	 * @param waybillNo
	 * @param waybillstatus
	 * @author foss-218438
	 * @return
	 */
	BarcodePrintLabelDto getCommonLabelPrintInfoExpress(String waybillNo,String waybillstatus);
}