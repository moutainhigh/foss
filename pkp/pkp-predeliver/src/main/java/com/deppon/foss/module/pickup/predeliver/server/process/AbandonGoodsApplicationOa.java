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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/process/AbandonGoodsApplicationOa.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationOa.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.process;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.wfs.ThrowfreightResponse;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.AbandGoodsApplicationConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;

/**
 * JMS远端服务请求回调处理
 * 
 * @date 2012-11-22 下午5:07:42
 */
public class AbandonGoodsApplicationOa implements ICallBackProcess {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(AbandonGoodsApplicationOa.class.getName());

	/**
	 * 弃货处理dao
	 */
	private IAbandonGoodsApplicationDao abandonGoodsApplicationDao;
	/**
	 * 弃货service
	 */
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;

	/**
	 * esb回调foss oa审批结果接口
	 */
	@Transactional
	public void callback(Object response) throws ESBException {
		// 保存操作历史记录
		UserEntity user = new UserEntity();
		user.setUserName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_CODE);
		employee.setEmpName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		user.setEmployee(employee);
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("--");
		dept.setName("--");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);

		//如果RESULT调用接口失败,需要将运单更新状态审批失败 processId processReason
		ThrowfreightResponse res = (ThrowfreightResponse) response;
		long processinstid = res.getProcessinstid();
		//工作流id
		String reason = res.getReason();
		//处理结果
		//运单号
		String waybillNo = res.getWaybillNumber();
		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		abandonentity.setWaybillNo(waybillNo);
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(response));
		abandonentity.setStatus("callback");
		abandonGoodsApplicationService.insertAgActionHistoryEntity(abandonentity, currentInfo);
		logger.info("ThrowfreightResponse" + ReflectionToStringBuilder.toString(res));

		//如果RESULT调用接口失败,需要将运单更新状态审批失败 processId processReason
		abandonentity = new AbandonGoodsApplicationEntity();
		//调用接口失败
		if (res.isResult()) {
			//SUCCESS
			abandonentity.setWaybillNo(waybillNo);
			//运单号
			String workflowNo = StringUtils.substringBefore(String.valueOf(processinstid), ".");
			abandonentity.setProcessId(workflowNo);
			//工作流id
		} else {
			abandonentity.setWaybillNo(waybillNo);
			//运单号
			//将状态改为没有进入流程中
			abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
			abandonentity.setProcessReason(reason);
			//处理结果
		}
		AbandonGoodsResultDto dto = null;
		String serialNumber = res.getSerialNumber();
		// 如果不是无标签转弃货的记录，再继续原有的流程 ISSUE-4377
		dto = abandonGoodsApplicationService.queryNoTagAbandonedGoodsByWaybillNoAndSerialNo(waybillNo, serialNumber);
		if(dto == null){
			dto = abandonGoodsApplicationService.queryAbandonedGoodsForWaybillNo(waybillNo);
		}
		if (dto == null) {
			return;
		}
		abandonentity.setId(dto.getId());
		//更新数据库
		abandonGoodsApplicationDao.updateByKey(abandonentity);

		// 设置操作内容
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(res));
		abandonentity.setStatus(abandonentity.getStatus() == null ? AbandGoodsApplicationConstants.ABANDGOODS_STATUS_APPROVAL : abandonentity.getStatus());
		abandonGoodsApplicationService.insertAgActionHistoryEntity(abandonentity, currentInfo);
	}

	//调用接口失败
	@Transactional
	public void errorHandler(Object response) throws ESBException {
		ThrowfreightResponse res = (ThrowfreightResponse) response;
		//get response

		long processinstid = res.getProcessinstid();
		//process id
		String reason = res.getReason();
		//处理结果
		String waybillNo = res.getWaybillNumber();
		//运单号
		//create AbandonGoodsApplicationEntity
		AbandonGoodsApplicationEntity abandonentity = new AbandonGoodsApplicationEntity();
		abandonentity.setWaybillNo(waybillNo);
		AbandonGoodsResultDto dto = null;
		String serialNumber = res.getSerialNumber();
		// 如果不是无标签转弃货的记录，再继续原有的流程 ISSUE-4377
		dto = abandonGoodsApplicationService.queryNoTagAbandonedGoodsByWaybillNoAndSerialNo(waybillNo, serialNumber);
		if(dto == null){
			dto = abandonGoodsApplicationService.queryAbandonedGoodsForWaybillNo(waybillNo);
		}
		if (dto == null) {
			return;
		}
		abandonentity.setId(dto.getId());
		//运单号
		abandonentity.setStatus(AbandGoodsApplicationConstants.ABANDGOODS_STATUS_NEW);
		//将状态改为没有进入流程中
		abandonentity.setProcessReason(reason);
		//处理结果
		String workflowNo = StringUtils.substringBefore(String.valueOf(processinstid), ".");
		abandonentity.setProcessId(workflowNo);
		//process id
		//更新数据库
		abandonGoodsApplicationDao.updateByKey(abandonentity);
		// 保存操作历史记录
		UserEntity user = new UserEntity();
		user.setUserName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_CODE);
		employee.setEmpName(AbandGoodsApplicationConstants.DEFAULT_CREATE_USER_NAME);
		user.setEmployee(employee);
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("--");
		dept.setName("--");
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 设置操作内容
		abandonentity.setOperateContent(ReflectionToStringBuilder.toString(res));
		abandonGoodsApplicationService.insertAgActionHistoryEntity(abandonentity, currentInfo);
	}

	/**
	 * getAbandonGoodsApplicationDao 获得对象
	 * 
	 * @return
	 */
	public IAbandonGoodsApplicationDao getAbandonGoodsApplicationDao() {
		return abandonGoodsApplicationDao;
	}

	/**
	 * set对象
	 */
	public void setAbandonGoodsApplicationDao(IAbandonGoodsApplicationDao abandonGoodsApplicationDao) {
		this.abandonGoodsApplicationDao = abandonGoodsApplicationDao;
	}

	public void setAbandonGoodsApplicationService(IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

}