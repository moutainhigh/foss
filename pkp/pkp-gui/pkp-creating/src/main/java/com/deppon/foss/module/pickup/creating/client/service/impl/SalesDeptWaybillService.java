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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/service/impl/SalesDeptWaybillService.java
 * 
 * FILE NAME        	: SalesDeptWaybillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.service.impl;

import java.util.Date;
import java.util.List;
import com.deppon.foss.framework.client.component.remote.DefaultRemoteServiceFactory;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillOfflineDao;
import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.creating.client.service.ISalesDeptWaybillService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOfflineDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting;
import com.google.inject.Inject;



/**
 * 
 * 运单 dept service
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2012-10-17 上午11:16:43,
 * </p>
 * @author 105089-FOSS-yangtong
 * @date 2012-10-17 上午11:16:43
 * @since
 * @version
 */
public class SalesDeptWaybillService implements ISalesDeptWaybillService {
	
	private IBaseDataService baseDataService;
	
	private IDataDictionaryValueService dictionaryValueSerivce;
	
	
	// 获得远程HessianRemoting接口
	private IWaybillPendingHessianRemoting waybillPendingRemotingService;
	
	// 待处理运单
	private IWaybillPendingDao waybillPendingDao;
	
	//离线运单

	private IWaybillOfflineDao waybillOfflineDao;

	
	public SalesDeptWaybillService(){
		waybillPendingRemotingService = DefaultRemoteServiceFactory.getService(IWaybillPendingHessianRemoting.class);
	}
	
	@Inject
    public void setWaybillOfflineDao(IWaybillPendingDao waybillPendingDao) {
		this.waybillPendingDao = waybillPendingDao;
    }
	
	
	

	@Inject
    public void setBaseDataService(IBaseDataService baseDataService) {
		this.baseDataService = baseDataService;
    }
	
	@Inject
    public void setDictionaryValueSerivce(IDataDictionaryValueService dictionaryValueSerivce) {
		this.dictionaryValueSerivce = dictionaryValueSerivce;
    }
	
	@Inject 
	public void setWaybillOfflineDao(IWaybillOfflineDao waybillOfflineDao) {
		this.waybillOfflineDao = waybillOfflineDao;
	}

	/**
     * <p>
     * (查询运输性质)
     * </p>
     * @author 025000-FOSS-helong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public List<ProductEntity> queryTransType(String salesDeptId) {
		return this.baseDataService.queryTransType(salesDeptId);
	}

	/**
     * <p>
     * (查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto) {
		return waybillPendingRemotingService.queryPending(waybillPendingDto);
	}
	@Override
	public List<WaybillPendingEntity> queryPendingExpress(WaybillPendingDto waybillPendingDto) {
		return waybillPendingRemotingService.queryPendingExpress(waybillPendingDto);
	}
	
	/**
     * <p>
     * (查询运单状态PENDING)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public List<DataDictionaryValueEntity> queryPendingType() {
		 
		return dictionaryValueSerivce.queryPendingType();
	}
	
	/**
     * <p>
     * (查询运单状态Offline)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public List<DataDictionaryValueEntity> queryOfflineActive() {
		return dictionaryValueSerivce.queryOfflineActive();
	}
	
	/**
	 * 
	 * 获取离线运单
	 * @date 2012-10-16 下午5:29:27
	 * @return
	 * @see
	 */
	@Override
	public List<WaybillOfflineEntity> queryWaybillOffline(
			WaybillOfflineDto waybillOfflineDto) {
		return waybillOfflineDao.queryWaybillOffline(waybillOfflineDto);
	}
	
	
	
	/**
	 * queryWaybillOfflineByPrimaryKey
	 * @param id
	 * @return
	 */
	@Override
	public WaybillPendingEntity queryWaybillOfflineByPrimaryKey(String id )
	{
		return waybillPendingDao.queryByPrimaryKey(id);
	}
	

	
	/**
     * <p>
     * 更改运单状态PENDING
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public void updatePendingActive(String id) {
		waybillPendingDao.updatePendingActive(id);
	}
	
	/**
     * <p>
     * (按id查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public WaybillPendingEntity queryPendingById(String id) {
		 
		return waybillPendingRemotingService.queryPendingById(id);
	}
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public boolean isPendingExsits(String mixNo) {
		return waybillPendingRemotingService.isPendingExsits(mixNo);
	}
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public boolean isPendingExsitsAndOrderNo(String mixNo, String orderNo) {
		return waybillPendingRemotingService.isPendingExsitsAndOrderNo(mixNo,orderNo);
	}
	
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在OFFLINE
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public boolean isOfflineExsits(String mixNo) {
		WaybillPendingEntity offineEntity = waybillPendingDao.queryByWaybillNumber(StringUtil.defaultIfNull(mixNo));
		return (offineEntity!=null);//sornar 简化布尔返回 
	}
	
	@Override
	public Integer countOfflineActiveWayBill() {
		return waybillPendingDao.countOfflineActiveWayBill();
	}
	
	@Override
	public Integer countOfflineActiveWayBill(String orgCode) {
		return waybillPendingDao.countOfflineActiveWayBill(orgCode);
	}
	
	/**
	 * 
	 * <p>通过部门编码查营业网点</p> 
	 * @author foss-sunrui
	 * @date 2012-12-25 下午1:00:18
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.pickup.common.client.service.IBaseDataService#querySaleDepartmentByCode(java.lang.String)
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code){
		return baseDataService.querySaleDepartmentByCode(code);
	}
	
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
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code,Date billTime){
		return baseDataService.querySaleDepartmentByCode(code,billTime);
	}
	
	
	
	/**
	 * 通过code查询组织实体
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-20 下午9:23:24
	 */
	@Override
	public OrgAdministrativeInfoEntity queryOrgByCode(String deptCode){
		return baseDataService.queryOrgAdministrativeInfoEntityByCode(deptCode);
	}
	
	
	/**
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午4:09:29
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryBillingGroupsBySaleDeptCode(String salesCode){
		return baseDataService.queryBillingGroupListBySalesCode(salesCode);
	}
	
	/**
	 * 根据集中开单组部门编码查询相关的营业部列表
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午4:27:48
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDeptsByBillingGroupCode(String billingGroupCode){
		return baseDataService.querySalesListByBillingGroupCode(billingGroupCode);
	}

	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param pendingDto
	 * @return
	 */
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto, String type) {
		return waybillPendingRemotingService.queryEWaybillSalesDepart(ewaybillConditionDto, type);
	}
	
	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	@Override
	public WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto) {
		return waybillPendingRemotingService.queryBasicWaybillPendingData(waybillPendingDto);
	}
	@Override
	public WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo){
		return waybillPendingRemotingService.queryWaybillByWaybillNo(waybillNo);
	}
}