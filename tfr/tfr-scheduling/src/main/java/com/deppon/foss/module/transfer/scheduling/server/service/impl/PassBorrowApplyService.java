/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/PassBorrowApplyService.java
 * 
 *  FILE NAME     :PassBorrowApplyService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.scheduling.api.define.BorrowVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassBorrowApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IBorrowVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.BorrowVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * PassBorrowApplyService
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:15
 */
public class PassBorrowApplyService implements IPassBorrowApplyService {
	
	private IPassBorrowApplyDao passBorrowApplyDao;
	
	private IBorrowVehicleService borrowVehicleService;
	
	private IAuditBorrowApplyService auditBorrowApplyService;
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addPassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity) {
		return passBorrowApplyDao.addPassBorrowApply(passBorrowApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int updatePassBorrowApply(PassBorrowApplyEntity passBorrowApplyEntity) {
		return passBorrowApplyDao.updatePassBorrowApply(passBorrowApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<PassBorrowApplyEntity> queryPassBorrowApplyList(PassBorrowApplyEntity passBorrowApplyEntity) {
		return passBorrowApplyDao.queryPassBorrowApplyList(passBorrowApplyEntity);
	}
	
	/**
	 * 根据借车编号查询
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param borrowNo
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public PassBorrowApplyEntity queryPassBorrowApplyByBorrowNo(String borrowNo) {
		PassBorrowApplyEntity passBorrowApplyEntity = new PassBorrowApplyEntity();
		passBorrowApplyEntity.setBorrowNo(borrowNo);
		List<PassBorrowApplyEntity> list = queryPassBorrowApplyList(passBorrowApplyEntity);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<PassBorrowApplyEntity> queryPassBorrowApplyForPage(PassBorrowApplyEntity passBorrowApplyEntity, int start, int pageSize) {
		return passBorrowApplyDao.queryPassBorrowApplyForPage(passBorrowApplyEntity, start, pageSize);
	}
	
	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:15
	 * @param passBorrowApplyEntity
	 * @return 
	 */
	@Transactional(readOnly = true)
	public Long queryCount(PassBorrowApplyEntity passBorrowApplyEntity) {
		return passBorrowApplyDao.queryCount(passBorrowApplyEntity);
	}

	/**
	 * 借车审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:47:43
	 * @param passBorrowApplyEntity
	 */
	@Transactional
	public void doAcceptedBorrowVehicleApply(PassBorrowApplyEntity passBorrowApplyEntity, String notes) {
		if (passBorrowApplyEntity == null) {
			// 异常 参数错误.
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE);
		}
		// 借车单号
		String borrowNo = passBorrowApplyEntity.getBorrowNo();
		if (StringUtil.isEmpty(borrowNo)) {
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"借车单号为空"});
		}
		// 车牌号
		String vehicleNo = passBorrowApplyEntity.getVehicleNo();
		if (StringUtil.isEmpty(vehicleNo)) {
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"车牌号不能为空"});
		}
		BorrowVehicleEntity borrowVehicleEntity = borrowVehicleService.queryBorrowVehicleByBorrowNo(borrowNo);
		String status = borrowVehicleEntity.getStatus();
		if (borrowVehicleEntity == null || StringUtil.equals(BorrowVehicleConstants.BORROWVEHICLE_STATUS_VERIFY_ARRIVE, status) || StringUtil.equals(BorrowVehicleConstants.BORROWVEHICLE_STATUS_GIVE_BACK, status)) {
			throw new BorrowVehicleException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"状态错误."});
		}
		// 保存受理信息
		savePassBorrowApplyForAccepted(passBorrowApplyEntity);
		
		// 1.保存借车log信息
		// 2.更新借车申请状态
		AuditBorrowApplyEntity auditBorrowApplyEntity = new AuditBorrowApplyEntity();
		auditBorrowApplyEntity.setBorrowNo(borrowNo);
		auditBorrowApplyEntity.setNotes(notes);
		auditBorrowApplyService.doAcceptedBorrowVehicleApply(auditBorrowApplyEntity);
	}
	
	/**
	 *  借来的车辆, 约车使用
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午12:38:30
	 * @param vehicleDriverWithDto
	 */
	public Map<String, VehicleDriverWithDto> queryBorrowVehicle(VehicleDriverWithDto vehicleDriverWithDto) {
		return passBorrowApplyDao.queryBorrowVehicle(vehicleDriverWithDto);
	}

	/**
	 * 保存受理约车信息
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午8:43:59
	 * @param passBorrowApplyEntity
	 */
	private void savePassBorrowApplyForAccepted(PassBorrowApplyEntity passBorrowApplyEntity) {
		passBorrowApplyEntity.setPassStatus(BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED);
		passBorrowApplyEntity.setPassTime(new Date());
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 网点编码
		String orgCode =  FossUserContext.getCurrentDeptCode();
		String orgName =  FossUserContext.getCurrentDept().getName();
		passBorrowApplyEntity.setAcceptOrgName(orgName);
		passBorrowApplyEntity.setAcceptOrgCode(orgCode);
		passBorrowApplyEntity.setAcceptEmpName(userName);
		passBorrowApplyEntity.setAcceptEmpCode(userCode);
		
		PassBorrowApplyEntity oldPassBorrowApplyEntity = queryPassBorrowApplyByBorrowNo(passBorrowApplyEntity.getBorrowNo());
		if (oldPassBorrowApplyEntity == null) {
			addPassBorrowApply(passBorrowApplyEntity);
		} else {
			passBorrowApplyEntity.setId(oldPassBorrowApplyEntity.getId());
			updatePassBorrowApply(passBorrowApplyEntity);
		}
	}
	
	/**
	 * 设置passBorrowApplyDao
	 * @param passBorrowApplyDao the passBorrowApplyDao to set
	 */
	public void setPassBorrowApplyDao(IPassBorrowApplyDao passBorrowApplyDao) {
		this.passBorrowApplyDao = passBorrowApplyDao;
	}

	/**
	 * 设置borrowVehicleService
	 * @param borrowVehicleService the borrowVehicleService to set
	 */
	public void setBorrowVehicleService(IBorrowVehicleService borrowVehicleService) {
		this.borrowVehicleService = borrowVehicleService;
	}

	/**
	 * 设置auditBorrowApplyService
	 * @param auditBorrowApplyService the auditBorrowApplyService to set
	 */
	public void setAuditBorrowApplyService(
			IAuditBorrowApplyService auditBorrowApplyService) {
		this.auditBorrowApplyService = auditBorrowApplyService;
	}
}