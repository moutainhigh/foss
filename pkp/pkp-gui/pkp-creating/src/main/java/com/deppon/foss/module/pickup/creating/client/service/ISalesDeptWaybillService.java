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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/ISalesDeptWaybillService.java
 * 
 * FILE NAME        	: ISalesDeptWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOfflineDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * 
 * (管理部出发运单所有的业务操作)
 * <p style="display:none">
 * modifyRecord
 * 
 * @author 105089-FOSS-yangtong
 * @date 2012-10-16 下午04:13:46
 * @since
 * @version
 */
public interface ISalesDeptWaybillService {

	/**
	 * <p>
	 * (查询运输性质)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	List<ProductEntity> queryTransType(String salesDeptId);

	/**
	 * <p>
	 * (查询运单待处理表)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);
	
	List<WaybillPendingEntity> queryPendingExpress(WaybillPendingDto waybillPendingDto);

	/**
	 * <p>
	 * (按id查询运单待处理表)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	WaybillPendingEntity queryPendingById(String id);

	/**
	 * <p>
	 * (查询运单状态PENDING)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryPendingType();

	/**
	 * <p>
	 * (查询运单状态OFFLINE)
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	List<DataDictionaryValueEntity> queryOfflineActive();

	/**
	 * <p>
	 * 更改运单状态PENDING
	 * </p>
	 * 
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	void updatePendingActive(String id);

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在PENDING
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	boolean isPendingExsits(String mixNo);
	
	

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在PENDING
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	boolean isPendingExsitsAndOrderNo(String mixNo, String orderNo);

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在OFFLINE
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	boolean isOfflineExsits(String mixNo);

	/**
	 * 
	 * <p>
	 * 查询未提交的OFFLINE运单数量
	 * </p>
	 * 
	 * @author niujian
	 * @return
	 * @see
	 */
	Integer countOfflineActiveWayBill();
	
	
	/**
	 * 
	 * <p>
	 * 根据orgcode查询未提交的OFFLINE运单数量
	 * </p>
	 * 
	 * @author niujian
	 * @return
	 * @see
	 */
	Integer countOfflineActiveWayBill(String orgCode);

	/**
	 * 查询离线运单表
	 * 
	 * @param waybillPendingDto
	 * @return
	 */
	List<WaybillOfflineEntity> queryWaybillOffline(
			WaybillOfflineDto waybillOfflineDto);

	/**
	 * 查询离线待处理单据
	 * 
	 * @param id
	 * @return
	 */
	WaybillPendingEntity queryWaybillOfflineByPrimaryKey(String id);
	
    /**
     * 
     * 通过 标识编码查询
     * @author 026113-foss-linwensheng
     * @date 2012-11-28 上午10:30:33
     */
	SaleDepartmentEntity querySaleDepartmentByCode(String code);

	/**
	 * 通过code查询组织实体
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-20 下午9:23:24
	 */
	OrgAdministrativeInfoEntity queryOrgByCode(String deptCode);

	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String code, Date billTime);

	/**
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午4:09:29
	 */
	List<OrgAdministrativeInfoEntity> queryBillingGroupsBySaleDeptCode(String salesCode);

	/**
	 * 根据集中开单组部门编码查询相关的营业部列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午4:27:48
	 */
	List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(String billingGroupCode);

	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param ewaybillConditionDto
	 * @param type 
	 * @return
	 */
	List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto, String type);

	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto);
	WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo);
}