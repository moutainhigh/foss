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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/service/IWaybillRfcRemotingService.java
 * 
 * FILE NAME        	: IWaybillRfcRemotingService.java
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
package com.deppon.foss.module.pickup.changingexp.client.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 更改单远程服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:102246-foss-shaohongliang,date:2012-10-12
 * 下午5:23:40
 * </p>
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-12 下午5:23:40
 * @since
 * @version
 */
public interface IWaybillRfcRemotingService extends IService {

	/**
	 * 
	 * 功能：queryWaybillByNumber
	 * 
	 * @param:
	 * @return WaybillEntity
	 * @since:1.6
	 */
	WaybillEntity queryWaybillByNumber(String number);

	/**
	 * 
	 * <p>
	 * 运单号码效验
	 * </p>
	 * 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-16 下午5:42:49
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see
	 */
	boolean checkWaybillNumber(String waybillNo);

	
	/**
	 * 
	 * 运单导入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午8:04:20
	 */
	WaybillRfcImportDto importWaybillByNumber(String waybillNo);
	
	  /**
	    * 
	    * 导入运单
	    * 
	    * @author 265475-foss-邹胜利
	    * @date 2016-1-18 下午1:42:28
	    */
	WaybillRfcImportDto importWaybillBy(String waybillNo);
	
	/**
	 * 
	 * 查询交接单明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-9 下午4:09:28
	 */
	List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo);
	
    /**
     * 更新在线运单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-11-15 下午11:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int updateWaybillRfcPrintTimes(String waybillNo);
    /**
	 * 根据更改单ID 查询更改单实体
	 * @author foss-jiangfei
	 * @date 2012-11-18 下午2:49:01
	 * @param id
	 * @return
	 * @see
	 */
	WaybillRfcEntity queryRfcEntityByWaybillId(String id) ;

	/**
	 * 
	 * 提交更改单
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:09:06
	 */
	WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto);

	/**
	 * 
	 * 处理差错结果
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午5:56:20
	 */
	QueryVirtualResultDto queryErrorHandlingResult(String handingID);

    /**
     * 插入一条更改单打印记录
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int insertSelective(PrintInfoEntity record);

    /**
     * 在线查询更改单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int queryPrintTimesByWaybillId(String waybillId, String waybillNo);

    /**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:26:37
     */
    PriceEntity queryValueAddPriceByCode(String pricingCodeZz);

    /**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode);
	
	/**
	 * 
	 *  通过 标识编码查询
	 * @author 102246-foss-shaohongliang
	 * @date 2013-4-12 下午4:56:44
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String customerPickupOrgCode);
	
	/**
	 * 根据virtual code查询走货路径
	 * @param virtualCode
	 * @return
	 */
	FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode);
	
	
	/**
	 * 获取运单ＩＤ
	 * @param waybillID
	 * @return
	 */
	String queryWaybillNOByID(String waybillID);
	



	/**
	 * 
	 * 根据运单号，快递代理理理公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-24
	 */
	boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,String agentCompanyCode);

	/**
	 * 根据运单号查询运单是否已交快递代理公司
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午12:42:13
	 */
	boolean isLdpHandOveredOutBranch(String waybillNo);

	/**
	 * 根据运单号查询快递运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:33:36
	 */
	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);

	
}