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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IWaybillRfcHessianRemoting.java
 * 
 * FILE NAME        	: IWaybillRfcHessianRemoting.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IHessianService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LostRepDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryVirtualResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TodoConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcImportDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcSubmitDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;

/**
 * 
 * 更改单Hessian服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:102246-foss-shaohongliang,date:2012-10-25 上午10:59:10, </p>
 * @author 102246-foss-shaohongliang
 * @date 2012-10-25 上午10:59:10
 * @since
 * @version
 */
public interface IWaybillRfcHessianRemoting extends IHessianService {
	
	/**
	 * 是否已经结清货款（部分结清同样是true）
	 * @param waybillNo
	 * @return
	 */
	public boolean isExistRepayment(String waybillNo);

	/**
	 * 
	 * <p>根据运单号导入运单</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-25 上午11:00:07
	 * @param waybillNo
	 * @return
	 * @throws BusinessException
	 * @see
	 */
	WaybillRfcImportDto importWaybillByNumber(String waybillNo);

	/**
	 * 
	 * 查询交接单明细
	 * @author 102246-foss-shaohongliang
	 * @date 2012-11-9 下午4:10:30
	 */
	List<HandOverAndUnloadDto> queryHandoverDetail(String waybillNo);


	/**
	     * 更新在线更改单打印次数 +1
	     * 
	     * @author foss-jiangfei
	     * @date 2012-11-15 下午11:48:25
	     * @param waybillNo
	     * @return
	     * @see
	     */	
	int updateWaybillRfcPrintTimes(String waybillId) ;
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
	 * @date 2012-11-19 下午2:10:24
	 */
	WaybillRfcEntity commitWaybillRfc(WaybillRfcSubmitDto submitDto);

	/**
	 * 
	 * 差错查询
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午6:10:37
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
     int insertSelective(PrintInfoEntity record) ;
    
    
    
    /**
     * 在线查询更改单打印次数  
     * 
     * @author foss-jiangfei
     * @date 2012-12-5 下午7:48:25
     * @param waybillNo
     * @return
     * @see
     */
     int queryPrintTimesByWaybillId(String waybillId,String waybillNo);

     /**
      * 
      * 根据Code查询价格DTO
      * @author 102246-foss-shaohongliang
      * @date 2013-1-22 下午2:27:16
      * @see com.deppon.foss.module.pickup.changing.client.service.IWaybillRfcRemotingService#queryValueAddPriceByCode(java.lang.String)
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
 	 * 根据waybillID 查询waybillNO 
 	 * @param waybillID
 	 * @return
 	 */
 	String  queryWaybillNOByID(String waybillID);
 	
 	/**
 	 * 查询代办数量
 	 * @param todoConditionDto
 	 * @return
 	 */
 	Long queryGoodsInfoCount(TodoConditionDto todoConditionDto);
 	
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
	
	/**
	 * 通过营业部编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午8:45:47
	 */
	List<ProductEntity> queryBySalesDept(String salesDeptId,String productLevel);
			
	/**
	 * 根据到达部门编码获取运输性质
	 * @author WangQianJin
	 * @date 2013-7-22 下午9:18:41
	 */
	List<ProductEntity> queryByArriveDept(String salesDeptId,String productLevel);
			
	/**
	 * 
	 * 根据运单号，快递代理理公司代码查询外发单(用于结算查询是否存在有效外发单)
	 * 
	 * @author 025000-foss-helong
	 * @date 2013-7-24
	 */
	boolean checkIfExistValidLdpExternalBillForStl(String waybillNo,String agentCompanyCode);

	/**
	 * 根据运单号查询运单是否已交接快递代理公司
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午12:35:20
	 */
	boolean isLdpHandOveredOutBranch(String waybillNo);

	/**
	 * 根据运单号查询快递运单信息
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-30 下午3:33:36
	 */
	WaybillExpressEntity queryWaybillExpressByNo(String waybillNo);
	
	/**
	 * 是否打过包装
	  * Description: 
	  * @author deppon-157229-zxy
	  * @version 1.0 2014-1-21 上午9:52:06
	  * @param waybillNo
	  * @return
	 */
	public boolean isHasPackaged(String waybillNo);

	/**
	 * 查询到达部门产品
	 * 
	 * @author 076234-FOSS-pgy
	 * @date 2014-3-5
	 */
	public List<ProductEntity> searchByArriveDept(String salesDeptCode,
			String productLevel);
	
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
	 * 查询更改单中最新的一条的状态
	 */
	List<WaybillRfcEntity> queryRecentRfc(String waybillNo);
	
	/**
	 * 
	 * qms差错查询
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-1 下午6:10:37
	 */
	LostRepDto queryQmsErrorHandlingResult(String handingID, String waybillNo);

	/**
	 * 
	 * <p>
	 * 根据运单号导入运单
	 * </p>
	 * 
	 * @author 265475-foss-邹胜利
	 * @date 2016-1-18 上午11:00:07
	 */
	WaybillRfcImportDto importWaybillBy(String waybillNo);

	/**
     * 根据更改单id,查询运单变更信息
     * @param waybillRfcId
     * @return
     * @author 311417 wangfeng
     * @date 2016-4-12
     */
    public String queryRfcChangeItemsByWaybillRfcId(String waybillRfcId);
    
    /**
     * 
     * <p>校验运单号是否存在</p> 
     * @author Foss-351326-xingjun 
     * @date 2016-8-5 上午8:27:23
     * @param waybillNo
     * @param operator 操作人
     * @return
     * @see
     */
    public boolean valuateFoss2EcsWaybillNo(String waybillNo,String operator);
    /**
     * 
     * <p>查询传递ECS开关</p> 
     * @author Foss-351326-xingjun 
     * @date 2016-8-5 上午8:27:33
     * @return
     * @see
     */
    public boolean queryPkpSwitch4Ecs();
    
    /**
     * 查询是否可开装卸费
     * @author 354805
     * @date 2016-12-6 16:29:35
     * @param code 装卸费开单部门
     * @param billTime 开单时间
     * @return 是否可开装卸费
     */
    public String queryCanPayServiceFeeByCodeAndTime(String code, Date billTime );
    
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
    public boolean IsWaybillPutInStorage(String waybillNo, List<String> inStockTypeList);

	public CustomerCircleNewDto getByCustCode(String provinceCode, Date date,
			String active);


}