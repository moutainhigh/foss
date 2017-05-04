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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillRfcVarificationHessianRemoting.java
 * 
 * FILE NAME        	: WaybillRfcVarificationHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.List;

import javax.annotation.Resource;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillRfcChangeException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting;

/**
 * 
 * <p>
 * 更改单remoting服务<br />
 * </p>
 * @title WaybillRfcVarificationHessianRemoting.java
 * @package com.deppon.foss.module.pickup.waybill.server.hessian 
 * @author suyujun
 * @version 0.1 2012-11-27
 */
@Remote()
public class WaybillRfcVarificationHessianRemoting implements IWaybillRfcVarificationHessianRemoting {
	@Resource
	private IWaybillRfcVarificationService waybillRfcVarificationService;
	
	@Resource
	private IWaybillManagerService waybillManagerService;
	

	/**
	 * 业务互斥锁服务
	 *  提供业务互斥锁服务接口
	 */
	@Resource
	private IBusinessLockService businessLockService;
	/**
	 * 业务锁定超时自动释放时间:15秒
	 */
	private static final int LOCK_TIMEOUT = 0;
	
	/**
	 * 
	 */
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcVarificationDto(
			WaybillRfcCondition condition) {
		//获得当前部门编号
		String deptCode = FossUserContext.getCurrentDeptCode();
		condition.setDeptCode(deptCode);
		//更改单信息查询
		return waybillRfcVarificationService.queryWaybillRfcVarifyDto(condition);
	}

	/**
	 * 更改单受理同意操作
	 */
	@Override
	public boolean agreeWaybillRfcOpinion(String waybillRfcId, String notes) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		boolean flag=false;
		WaybillRfcEntity waybillRfcEntity = waybillRfcVarificationService.selectByPrimaryKey(waybillRfcId);		
		/**
		 * 处理并发控制
		 */
  		MutexElement mutexElement = new MutexElement(waybillRfcEntity.getWaybillNo(), WaybillConstants.WAYBILL_ACCEPT_AGREE, MutexElementType.RFC_ACCEPT);//创建对象
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if(!isLocked){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTING);
		}
		try{
			//执行受理
			flag=waybillRfcVarificationService.agreeWaybillRfcOpinion(waybillRfcEntity, notes, currentInfo);
		}finally{
			//释放锁
			businessLockService.unlock(mutexElement);
		}			
		return flag;
	}

	/**
	 * 更改单受理拒绝
	 * @author 043260-foss-suyujun
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#refuseWaybillRfcOpinion(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean refuseWaybillRfcOpinion(String waybillRfcId, String notes) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		boolean flag=false;
		WaybillRfcEntity waybillRfcEntity = waybillRfcVarificationService.selectByPrimaryKey(waybillRfcId);		
		/**
		 * 处理并发控制
		 */
  		MutexElement mutexElement = new MutexElement(waybillRfcEntity.getWaybillNo(), WaybillConstants.WAYBILL_ACCEPT_REJECT, MutexElementType.RFC_ACCEPT);//创建对象
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if(!isLocked){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_ACCECPTING);
		}
		try{
			//执行受理
			flag=waybillRfcVarificationService.refuseWaybillRfcOpinion(waybillRfcEntity, notes, currentInfo);
		}finally{
			//释放锁
			businessLockService.unlock(mutexElement);
		}
		return flag;
	}

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-4
	 * @param waybillRfcId
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWayBillRfcProofByRfcId(java.lang.String)
	 */
	@Override
	public List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId) {
		return waybillRfcVarificationService.queryWayBillRfcProofByRfcId(waybillRfcId);
	}

	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-4
	 * @param filePath
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWaybillRfcProofByFilePath(java.lang.String)
	 */
	@Override
	public String queryWaybillRfcProofByFilePath(String filePath) {
		return waybillRfcVarificationService.queryWaybillRfcProofByFilePath(filePath);
	}

	/**
	 * 
	 * <p>同意运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-5 下午2:17:25
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#agreeWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean agreeWaybillRfcCheck(String waybillRfcId, String notes) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		boolean flag=false;
		WaybillRfcEntity waybillRfcEntity = waybillRfcVarificationService.selectByPrimaryKey(waybillRfcId);		
		/**
		 * 处理并发控制
		 */
  		MutexElement mutexElement = new MutexElement(waybillRfcEntity.getWaybillNo(), WaybillConstants.WAYBILL_AUDIT_AGREE, MutexElementType.RFC_CHECK);//创建对象
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if(!isLocked){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_APPROVING);
		}
		try{
			//执行审核
			flag=waybillRfcVarificationService.agreeWaybillRfcCheck(waybillRfcEntity, notes, currentInfo);
		}finally{
			//释放锁
			businessLockService.unlock(mutexElement);
		}
		return flag;
	}

	/**
	 * 
	 * <p>拒绝运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-5 下午2:18:31
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#refuseWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean refuseWaybillRfcCheck(String waybillRfcId, String notes) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		boolean flag=false;
		WaybillRfcEntity waybillRfcEntity = waybillRfcVarificationService.selectByPrimaryKey(waybillRfcId);		
		/**
		 * 处理并发控制
		 */
  		MutexElement mutexElement = new MutexElement(waybillRfcEntity.getWaybillNo(), WaybillConstants.WAYBILL_AUDIT_REJECT, MutexElementType.RFC_CHECK);//创建对象
		//互斥锁定
		boolean isLocked = businessLockService.lock(mutexElement, LOCK_TIMEOUT);
		if(!isLocked){
			throw new WaybillRfcChangeException(WaybillRfcChangeException.CHANGE_WAYBILL_APPROVING);
		}	
		try{
			//执行审核
			flag=waybillRfcVarificationService.refuseWaybillRfcCheck(waybillRfcEntity, notes, currentInfo);
		}finally{
			//释放锁
			businessLockService.unlock(mutexElement);
		}
		return flag;
	}

	/**
	 * 
	 *
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:50:48
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWaybillRfcChkAndProList(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition)
	 */
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcChkAndProList(
			WaybillRfcCondition condition) {
		return waybillRfcVarificationService.queryWaybillRfcChkAndPro(condition);
	}

	/**
	 * 
	 * 新增更改单代理审核
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:50:53
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#addWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void addWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		waybillRfcVarificationService.addWayBillRfcAgent(entity, currentInfo);
	}

	/**
	 * 
	 * 根据查询条件查询审核代理信息
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:50:57
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto)
	 */
	@Override
	public List<WaybillRfcAgentEntity> queryWaybillRfcAgent(
			WaybillRfcQueryAgentConDto condition) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		return waybillRfcVarificationService.queryWaybillRfcAgent(condition, currentInfo);
	}

	/**
	 * 
	 * 修改审核代理权限 
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:51:01
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#updateWaybillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void updateWaybillRfcAgent(WaybillRfcAgentEntity entity) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		waybillRfcVarificationService.updateWaybillRfcAgent(entity, currentInfo);
	}

	/**
	 * 
	 * 根据部门标杆编码查找部门人员
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:51:06
	 * @param unifieldCode
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryEmployeeByEntity(java.lang.String)
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeByEntity(String unifieldCode) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		return waybillRfcVarificationService.queryEmployeeByEntity(unifieldCode, currentInfo);
	}

	/**
	 * 
	 * 删除审核代理数据
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:51:11
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#deleteWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void deleteWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationService.deleteWayBillRfcAgent(entity);
	}

	/**
	 * 
	 * 中止审核代理 
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:51:15
	 * @param entity 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#stopWayBillRfcAgent(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity)
	 */
	@Override
	public void stopWayBillRfcAgent(WaybillRfcAgentEntity entity) {
		waybillRfcVarificationService.stopWayBillRfcAgent(entity);
	}

	/**
	 * 
	 * 审核和受理状态查询 
	 * @author foss-gengzhe
	 * @date 2012-12-27 上午11:18:21
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWaybillRfcChkAndPro(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition)
	 */
	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(WaybillRfcCondition condition) {
		condition.setDeptCode(FossUserContext.getCurrentDeptCode());
		return waybillRfcVarificationService.queryWaybillRfcChkAndPro(condition);
	}

	/**
	 * 
	 * 根据运单ID查询运单详细信息
	 * @author foss-gengzhe
	 * @date 2013-1-6 下午12:08:59
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillRfcVarificationHessianRemoting#queryWaybillBasicByNo(java.lang.String)
	 */
	@Override
	public WaybillEntity queryWaybillById(String waybillId) {
		return waybillManagerService.queryWaybillById(waybillId);
	}
	/**
	 * 
	 * 是否打印过到达联
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-25 上午11:00:51
	 */
	@Override
	public boolean isPrintArrivesheet(String waybillNO) {
		
		return waybillRfcVarificationService.isPrintArrivesheet(waybillNO);
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
		return waybillRfcVarificationService.queryRfcChangeDetailList(rfcId);
	}

	@Override
	public List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition, String cargoPay) {
		return waybillRfcVarificationService.queryWaybillRfcCargoVarificationDto(waybillRfcCondition,cargoPay);
	}

	@Override
	public void addWayBillRfcAgentExp(WaybillRfcAgentEntity bean) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		waybillRfcVarificationService.addWayBillRfcAgentExpress(bean, currentInfo);
		
	}

	@Override
	public void updateWaybillRfcAgentExp(WaybillRfcAgentEntity entity) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		waybillRfcVarificationService.updateWaybillRfcAgentExpress(entity, currentInfo);
		
	}
	

}