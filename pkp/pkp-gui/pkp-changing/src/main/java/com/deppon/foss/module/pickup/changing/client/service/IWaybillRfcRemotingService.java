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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/service/IWaybillRfcRemotingService.java
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
package com.deppon.foss.module.pickup.changing.client.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
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
	 * 是否已经结清货款（部分结清同样是true）
	 * @param waybillNo
	 * @return
	 */
	public boolean isExistRepayment(String waybillNo);
	
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
	LostRepDto queryErrorHandlingResult(String handingID, String waybillNo);

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
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	List<ProductEntity> queryTransType(String salesDeptId);
	
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	List<ProductEntity> queryByArriveDeptProduct(String arriveDept);
	
	/* 是否打过包装
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-21 上午9:44:17
	  * @param waybillNo
	  * @return
	 */
	public boolean isHasPackaged(String waybillNo);
	
	/**
	 * 查询运单是否有交接记录
	 * 
	 */
	public List<HandOverBillEntity> queryHandOveredRecordsByWaybillNo(String waybillNo);

	/**
	 * 查询运单是否在开单部门有卸车记录
	 *  
	 */
	public boolean queryUnloadTaskReport(String waybillNo);

	public void canDebit(String deliveryCustomerCode, String receiveOrgCode);
	
	/**
     * 根据更改单id,查询更改单变更信息
     * @param waybillRfcId
     * @return
     * @author 311417 wangfeng
     * @date 2016-4-22
     */
	public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId);

	/**
	 * 依据运单类型查询servicetype
	 * @param orderno
	 * @return
	 */
	public String queryServiceType(String orderno);
	
	/**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:37:29
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime );
   
	/**
	 * 根据运单号查询运单是否存在单票入库记录
	 * @Title:        IsWaybillPutInStorage 
	 * @param:        @param WaybillNo
	 * @param:        @return    
	 * @return:       boolean    
	 * @author        355673 chenpeng
	 * @Date          2016-11-26 上午11:12:19 
	 * @throws
	 */
	public boolean IsWaybillPutInStorage(String WaybillNo);

	public CustomerCircleNewDto queryCustomerByCusCodegetByCustCode(
			String provinceCode, Date date, String active);

}