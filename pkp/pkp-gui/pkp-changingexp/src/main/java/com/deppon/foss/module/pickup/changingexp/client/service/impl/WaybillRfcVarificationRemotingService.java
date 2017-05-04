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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/service/impl/WaybillRfcVarificationRemotingService.java
 * 
 * FILE NAME        	: WaybillRfcVarificationRemotingService.java
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
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting;

/**
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:kevin,date:2012-11-27 下午5:40:31</p>
 * @author foss-gengzhe
 * @date 2012-11-27 下午5:40:31
 * @since
 * @version
 */
public class WaybillRfcVarificationRemotingService implements IWaybillRfcVarificationRemotingService{

	private IWaybillRfcVarificationHessianRemoting waybillRfcVarificationHessianRemoting ;
   
	/**
     * 
     * @author foss-gengzhe
     * @date 2012-11-27 下午9:10:01
     * @param condition
     * @return
     * @see
     */
	@Override
    public List<WaybillRfcChangeDto> queryWaybillRfcVarificationDto(WaybillRfcCondition condition) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
    	return waybillRfcVarificationHessianRemoting.queryWaybillRfcVarificationDto(condition);
    }

	/**
     * 
     * <p>同意运单更改审核</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:22:25
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
	@Override
	public boolean agreeWaybillRfcCheck(String waybillRfcId, String notes) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.agreeWaybillRfcCheck(waybillRfcId, notes);
	}

	 /**
     * 
     * <p>拒绝运单更改审核</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:23:07
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
	@Override
	public boolean refuseWaybillRfcCheck(String waybillRfcId, String notes) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.refuseWaybillRfcCheck(waybillRfcId, notes);
	}

	 /**
     * 
     * <p>同意运单更改受理</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:24:44
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
	@Override
	public boolean agreeWaybillRfcOpinion(String waybillRfcId, String notes) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.agreeWaybillRfcOpinion(waybillRfcId, notes);
	}

	 /**
     * 
     * <p>拒绝运单更改受理</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:25:34
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
	@Override
	public boolean refuseWaybillRfcOpinion(String waybillRfcId, String notes) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.refuseWaybillRfcOpinion(waybillRfcId, notes);
	}

	 /**
     * 
     * <p>根据更改单Id查询附件列表</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:27:07
     * @param waybillRfcId
     * @return
     * @see
     */
	@Override
	public List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryWayBillRfcProofByRfcId(waybillRfcId);
	}

	 /**
     * 
     * <p>根据图片路径下载图片</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:27:45
     * @param filePath
     * @return
     * @see
     */
	@Override
	public String queryWaybillRfcProofByFilePath(String filePath) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryWaybillRfcProofByFilePath(filePath);
	}

	/**
	 * 
	 * 新增更改单代理审核
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:05
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#addWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void addWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		waybillRfcVarificationHessianRemoting.addWayBillRfcAgent(entity);
	}

	/**
	 * 
	 * 根据查询条件查询审核代理信息
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:11
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#queryWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto)
	 */
	@Override
	public List<WaybillRfcAgentEntity> queryWaybillRfcAgent(
			WaybillRfcQueryAgentConDto condition) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryWaybillRfcAgent(condition);
	}

	/**
	 * 
	 * 修改审核代理权限
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:18
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#updateWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void updateWaybillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		waybillRfcVarificationHessianRemoting.updateWaybillRfcAgent(entity);
	}

	/**
	 * 
	 * 根据部门标杆编码查找部门人员 
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:24
	 * @param unifieldCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#queryEmployeeByEntity(java.lang.String)
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeByEntity(String unifieldCode) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryEmployeeByEntity(unifieldCode);
	}

	/**
	 * 
	 * 删除审核代理数据
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:28
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#deleteWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void deleteWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		waybillRfcVarificationHessianRemoting.deleteWayBillRfcAgent(entity);
	}

	/**
	 * 
	 * 中止审核代理
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:49:32
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#stopWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void stopWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		waybillRfcVarificationHessianRemoting.stopWayBillRfcAgent(entity);
	}

	/**
	 * 
	 * 审核和受理状态查询
	 * @author foss-gengzhe
	 * @date 2012-12-27 上午11:20:35
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#queryWaybillRfcChkAndPro(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition)
	 */
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(
			WaybillRfcCondition condition) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryWaybillRfcChkAndPro(condition);
	}

	/**
	 * 
	 * 根据运单ID查询运单详细信息
	 * @author foss-gengzhe
	 * @date 2013-1-6 下午12:14:13
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService#queryWaybillBasicByNo(java.lang.String)
	 */
	@Override
	public WaybillEntity queryWaybillById(String waybillId) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryWaybillById(waybillId);
	}
	
	/**
	 * 根据更改单ID查询变更明细
	 * @author foss-shaohongliang
	 * @date 2013-6-14 下午12:06:25
	 * @param rfcId
	 * @return
	 */
	@Override
	public List<WaybillRfcChangeDetailEntity> queryRfcChangeDetailList(String rfcId){
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		return waybillRfcVarificationHessianRemoting.queryRfcChangeDetailList(rfcId);
	}

	@Override
	public void addWayBillRfcAgentExp(WaybillRfcAgentEntity bean) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		
		waybillRfcVarificationHessianRemoting.addWayBillRfcAgentExp(bean);
	}

	@Override
	public void updateWaybillRfcAgentExp(WaybillRfcAgentEntity bean) {
		waybillRfcVarificationHessianRemoting = DefaultRemoteServiceFactory.getService(IWaybillRfcVarificationHessianRemoting.class);
		
		waybillRfcVarificationHessianRemoting.updateWaybillRfcAgentExp(bean);
		
	}
    
}