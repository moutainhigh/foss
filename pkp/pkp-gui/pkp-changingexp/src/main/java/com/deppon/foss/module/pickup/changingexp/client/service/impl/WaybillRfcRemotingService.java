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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/service/impl/WaybillRfcRemotingService.java
 * 
 * FILE NAME        	: WaybillRfcRemotingService.java
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
package com.deppon.foss.module.pickup.changingexp.client.service.impl;

import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcRemotingService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 远程调用服务实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Bobby,date:2012-10-16 下午5:41:47,content
 * </p>
 * 
 * @author Bobby
 * @date 2012-10-16 下午5:41:47
 * @since
 * @version
 */
/**
 * 更改单远程服务类
 * @author 102246-foss-shaohongliang
 * @date 2012-12-18 下午8:08:58
 */
public class WaybillRfcRemotingService implements IWaybillRfcRemotingService {

	/**
	 * 
	 * 功能：queryWaybillByNumber
	 * 
	 * @param:
	 * @return WaybillEntity
	 * @since:1.6
	 */
	@Override
	public WaybillEntity queryWaybillByNumber(String number) {
		IWaybillHessianRemoting waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		return waybillHessianRemoting.queryWaybillBasicByNo(number);
	}
	

	/**
	 * 
	 * <p>
	 * 运单号码效验
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 下午5:42:20
	 * @param waybillNo
	 * @param orgCode
	 * @return
	 * @see com.deppon.foss.module.pickup.creating.client.service.IWaybillRemotingService#checkWaybillNumber(java.lang.String)
	 */
	@Override
	public boolean checkWaybillNumber(String waybillNo) {
		IWaybillHessianRemoting waybillHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillHessianRemoting.class);
		return waybillHessianRemoting.checkWaybillNo(waybillNo);
	}

	/**
	 * 
	 * 运单导入
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-18 下午8:04:20
	 */
	@Override
	public WaybillRfcImportDto importWaybillByNumber(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.importWaybillByNumber(waybillNo);
	}
	
	  /**
	    * 
	    * 导入运单
	    * 
	    * @author 265475-foss-邹胜利
	    * @date 2016-1-18 下午1:42:28
	    */
	@Override
	public WaybillRfcImportDto importWaybillBy(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.importWaybillBy(waybillNo);
	}
	
	/**
	 * 
	 * 查询交接单明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-9 下午4:09:28
	 */
	@Override
	public List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryHandoverDetail(waybillNo);
	}

    /**
     * 更新在线更改单打印次数 +1
     * 
     * @author foss-jiangfei
     * @date 2012-11-15 下午11:48:25
     * @param waybillNo
     * @return
     * @see
     */
    public int updateWaybillRfcPrintTimes(String waybillId) {
    	IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory
		.getService(IWaybillRfcHessianRemoting.class);
	return waybillRfcHessianRemoting.updateWaybillRfcPrintTimes(waybillId);
    }
    
    /**
	 * 根据更改单ID 查询更改单实体
	 * @author foss-jiangfei
	 * @date 2012-11-18 下午2:49:01
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	public WaybillRfcEntity queryRfcEntityByWaybillId(String waybillRfcId) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory
				.getService(IWaybillRfcHessianRemoting.class);
	 return waybillRfcHessianRemoting.queryRfcEntityByWaybillId(waybillRfcId);		
	}

	/**
	 * 
	 * 提交更改单
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-19 下午2:09:06
	 */
	@Override
	public WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.commitWaybillRfc(submitDto);
	}
	
	/**
	 * 
	 * 处理差错结果
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午5:56:20
	 */
	@Override
	public QueryVirtualResultDto queryErrorHandlingResult(String handingID) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryErrorHandlingResult(handingID);
	}
	
    /**
     * 插入一条更改单打印记录
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    public int insertSelective(PrintInfoEntity record){
	IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
	return waybillRfcHessianRemoting.insertSelective(record);
    }

    /**
     * 在线查询更改单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
    public int queryPrintTimesByWaybillId(String waybillId, String waybillNo){
	IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
	return waybillRfcHessianRemoting.queryPrintTimesByWaybillId(waybillId, waybillNo);
    }

    /**
     * 
     * 根据Code查询价格DTO
     * @author 102246-foss-shaohongliang
     * @date 2013-1-22 下午2:27:16
     * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
     */
	@Override
	public PriceEntity queryValueAddPriceByCode(String pricingCodeZz) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryValueAddPriceByCode(pricingCodeZz);
	}
	
	  /**
     * 
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 102246-foss-shaohongliang
     * @date 2013-4-12 下午4:52:38
     */
	@Override
	public OuterBranchEntity queryAgencyBranchCompanyInfo(
			String agencyBranchCode) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryAgencyBranchCompanyInfo(agencyBranchCode);
	}
	
	/**
 	 * 
 	 *  通过 标识编码查询
 	 * @author 102246-foss-shaohongliang
 	 * @date 2013-4-12 下午4:56:44
 	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(
			String customerPickupOrgCode) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.querySaleDepartmentByCode(customerPickupOrgCode);
	}

	
	/**
	 * 根据virtual code查询走货路径
	 * @param virtualCode
	 * @return
	 */
	public FreightRouteEntity queryFreightRouteByVirtualCode(String virtualCode) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryFreightRouteByVirtualCode(virtualCode);

	}

	/**
	 * 通过运单ID获取运单号
	 */
	@Override
	public String queryWaybillNOByID(String waybillID) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryWaybillNOByID(waybillID);
	}

	@Override
	public boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,
			String agentCompanyCode) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.checkIfExistValidLdpExternalBillForStl(waybillNo,null);
	}
	
	/**
	 * 根据运单号查询运单是否已交接给快递代理理理公司
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午12:42:13
	 */
	@Override
	public boolean isLdpHandOveredOutBranch(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.isLdpHandOveredOutBranch(waybillNo);
	}
	
	/**
	 * 根据运单号查询快递运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:33:36
	 */
	@Override
	public WaybillExpressEntity queryWaybillExpressByNo(String waybillNo){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryWaybillExpressByNo(waybillNo);
	}
	
}