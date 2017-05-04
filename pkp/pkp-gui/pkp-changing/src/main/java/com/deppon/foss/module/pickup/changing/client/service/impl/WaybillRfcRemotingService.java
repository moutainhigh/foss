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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/service/impl/WaybillRfcRemotingService.java
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
package com.deppon.foss.module.pickup.changing.client.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.ICrmOrderHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillHessianRemoting;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcHessianRemoting;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
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

	      
/*	private ICrmOrderService crmOrderService ;
	
	

	public ICrmOrderService getCrmOrderService() {
		return crmOrderService;
	}

	public void setCrmOrderService(ICrmOrderService crmOrderService) {
		this.crmOrderService = crmOrderService;
	}*/
	
/*	ICustomerCircleRelationNewService customerCircleRelationNewService;
	
	public void setCustomerCircleRelationNewService(
			ICustomerCircleRelationNewService customerCircleRelationNewService) {
		this.customerCircleRelationNewService = customerCircleRelationNewService;
	}
*/
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
	 * 是否已经结清货款（部分结清同样是true）
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean isExistRepayment(String waybillNo){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.isExistRepayment(waybillNo);
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
	public LostRepDto queryErrorHandlingResult(String handingID, String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryQmsErrorHandlingResult(handingID, waybillNo);
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
     * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
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
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	@Override
	public List<ProductEntity> queryTransType(String salesDeptId){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryTransType(salesDeptId);
	}
	
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	public List<ProductEntity> queryByArriveDeptProduct(String arriveDept){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryByArriveDeptProduct(arriveDept);
	}

	@Override
	public boolean isHasPackaged(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.isHasPackaged(waybillNo);
	}
	
	/**
	 * 查询运单是否有交接记录
	 * 
	 */
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryHandOveredRecordsByWaybillNo(waybillNo);
	}
	
	/**
	 * 查询运单是否在开单部门有卸车记录
	 *  
	 */
	public boolean queryUnloadTaskReport(String waybillNo){
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.queryUnloadTaskReport(waybillNo);
	}

	@Override
	public void canDebit(String deliveryCustomerCode, String receiveOrgCode) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		waybillRfcHessianRemoting.canDebit(deliveryCustomerCode, receiveOrgCode);
	}
	
	/**
     * 根据更改单id,查询更改单变更信息
     * @author 311417 wangfeng
     * @date 2016-4-12
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId) {
        IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
        return waybillRfcHessianRemoting.queryRfcChangeItemsByWaybillRfcId(waybillRfcId);      
    }
    
    /**
     * 根据更改单id,查询更改单变更信息
     * @author 367726
     * @date 2016-4-12
     */
    public String queryServiceType(String orderNo) {
    	 ICrmOrderHessianRemoting crmOrderHessianRemoting = DefaultRemoteServiceFactory.getService(ICrmOrderHessianRemoting.class);
        return crmOrderHessianRemoting.queryServiceType(orderNo);
    }
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:32:44
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime ){
    	IWaybillRfcHessianRemoting waybillRfcHessionTemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
    	return waybillRfcHessionTemoting.queryCanPayServiceFeeByCodeAndTime(code, billTime);
    }

    /**
     * 根据运单号查询运单是否存在单票入库记录（入库类型：少货找到，派送拉回，偏线拉回）
     * @Title:        IsWaybillPutInStorage 
     * @param:        @param waybillNo
     * @param:        @return    
     * @return:       boolean    
     * @author        355673 chenpeng
     * @Date          2016-11-26 下午2:30:41 
     * @throws
     */
	@Override
	public boolean IsWaybillPutInStorage(String waybillNo) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		List<String> inStockTypeList=new ArrayList<String>();
		inStockTypeList.add(StockConstants.LOSE_GOODS_FOUND_IN_STOCK_TYPE);
		inStockTypeList.add(StockConstants.PARTIALLINE_RETURN_IN_STOCK_TYPE);
		inStockTypeList.add(StockConstants.SEND_RETURN_IN_STOCK_TYPE);
		return waybillRfcHessianRemoting.IsWaybillPutInStorage(waybillNo,inStockTypeList);
	}

	
	@Override
	public CustomerCircleNewDto queryCustomerByCusCodegetByCustCode(
			String provinceCode, Date date, String active) {
		IWaybillRfcHessianRemoting waybillRfcHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcHessianRemoting.class);
		return waybillRfcHessianRemoting.getByCustCode(provinceCode, date, active);
	}
}