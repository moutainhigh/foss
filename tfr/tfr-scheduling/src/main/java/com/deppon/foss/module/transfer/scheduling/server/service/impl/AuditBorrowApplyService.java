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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/AuditBorrowApplyService.java
 * 
 *  FILE NAME     :AuditBorrowApplyService.java
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
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.scheduling.api.define.BorrowVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditBorrowApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IBorrowVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * 借车审核log service
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:39
 */
public class AuditBorrowApplyService implements IAuditBorrowApplyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditBorrowApplyService.class);
	
	private IAuditBorrowApplyDao auditBorrowApplyDao;
	
	private IBorrowVehicleService borrowVehicleService;
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		return auditBorrowApplyDao.addAuditBorrowApply(auditBorrowApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int updateAuditBorrowApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		return auditBorrowApplyDao.updateAuditBorrowApply(auditBorrowApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<AuditBorrowApplyEntity> queryAuditBorrowApplyList(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		return auditBorrowApplyDao.queryAuditBorrowApplyList(auditBorrowApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<AuditBorrowApplyEntity> queryAuditBorrowApplyForPage(AuditBorrowApplyEntity auditBorrowApplyEntity, int start, int pageSize) {
		return auditBorrowApplyDao.queryAuditBorrowApplyForPage(auditBorrowApplyEntity, start, pageSize);
	}
	
	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-06 上午8:44:39
	 * @param auditBorrowApplyEntity
	 * @return {@link java.lang.Long} 
	 */
	@Transactional(readOnly = true)
	public Long queryCount(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		return auditBorrowApplyDao.queryCount(auditBorrowApplyEntity);
	}
	
	/**
	 * 查询受理序号
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:22:11
	 * @param borrowNo 借车单号
	 * @return {@link java.lang.Integer}
	 */
	@Transactional(readOnly = true)
	public int queryAuditNo(String borrowNo) {
		return auditBorrowApplyDao.queryAuditNo(borrowNo);
	}
	
	/**
	 * 获取下一个受理序号
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:22:11
	 * @param borrowNo 借车编号
	 */
	private int getNextAuditNo(String borrowNo) {
		int no = queryAuditNo(borrowNo);
		AtomicInteger atomicNo = new AtomicInteger(no);
		return atomicNo.incrementAndGet();
	}
	
	/**
	 * 退回借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyEntity
	 */
	@Transactional
	public void doReturnBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		if (auditBorrowApplyEntity == null) {
			// 异常
			LOGGER.info("参数错误.");
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"参数错误."});
		}
		String borrowNo = auditBorrowApplyEntity.getBorrowNo();
		if (borrowNo == null) {
			// 异常
			LOGGER.info("借车编号为空.");
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"借车编号为空."});
		}
		// 备注信息
		if (StringUtil.isBlank(auditBorrowApplyEntity.getNotes())) {
			// 异常
			LOGGER.info("备注信息为空.");
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"备注信息不能为空."});
		}
		// 更新借车申请状态到退回
		borrowVehicleService.updateBorrowVehicleApplyForReturnStatus(borrowNo);
		// 保存借车审核log
		settingAndAddAuditOrderApply(auditBorrowApplyEntity, BorrowVehicleConstants.BORROWVEHICLE_STATUS_RETURN);
	}
	
	/**
	 * 退回借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyList
	 */
	@Transactional
	public void doReturnBorrowVehicleApply(List<AuditBorrowApplyEntity> auditBorrowApplyList) {
		if (CollectionUtils.isEmpty(auditBorrowApplyList)) {
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"参数错误."});
		}
		for (AuditBorrowApplyEntity auditBorrowApplyEntity : auditBorrowApplyList) {
			doReturnBorrowVehicleApply(auditBorrowApplyEntity);
		}
	}
	
	/**
	 * 拒绝借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyEntity
	 */
	@Transactional
	public void doDismissBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		if (auditBorrowApplyEntity == null) {
			// 异常
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"参数错误."});
		}
		String borrowNo = auditBorrowApplyEntity.getBorrowNo();
		if (borrowNo == null) {
			// 异常
			LOGGER.info("借车编号为空.");
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"借车编号为空."});
		}
		// 备注信息
		if (StringUtil.isBlank(auditBorrowApplyEntity.getNotes())) {
			// 异常
			LOGGER.info("备注信息为空.");
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"备注信息不能为空."});
		}
		// 更新借车申请状态到退回
		borrowVehicleService.updateBorrowVehicleApplyForDismissStatus(borrowNo);
		// 保存借车审核log
		settingAndAddAuditOrderApply(auditBorrowApplyEntity, BorrowVehicleConstants.BORROWVEHICLE_STATUS_DISMISS);
	}
	
	/**
	 * 拒绝借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:39:44
	 * @param auditBorrowApplyList
	 */
	@Transactional
	public void doDismissBorrowVehicleApply(List<AuditBorrowApplyEntity> auditBorrowApplyList) {
		if (CollectionUtils.isEmpty(auditBorrowApplyList)) {
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"参数错误."});
		}
		for (AuditBorrowApplyEntity auditBorrowApplyEntity : auditBorrowApplyList) {
			doDismissBorrowVehicleApply(auditBorrowApplyEntity);
		}
	}
	
	/**
	 * 审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:59:35
	 * @param auditBorrowApplyEntity
	 * @return 
	 */
	@Transactional
	public void doAcceptedBorrowVehicleApply(AuditBorrowApplyEntity auditBorrowApplyEntity) {
		if (auditBorrowApplyEntity == null) {
			// 异常
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"参数错误."});
		}
		String borrowNo = auditBorrowApplyEntity.getBorrowNo();
		if (borrowNo == null) {
			// 异常
			throw new ParameterException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, new Object[]{"借车编号为空."});
		}
		// 更新借车申请状态
		borrowVehicleService.updateBorrowVehicleApplyForAcceptedStatus(borrowNo);
		// 保存借车审核log
		settingAndAddAuditOrderApply(auditBorrowApplyEntity, BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED);
	}
	
	/**
	 * 保存借车审核log
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:50:22
	 * @param auditBorrowApplyEntity
	 * @param status
	 */
	private void settingAndAddAuditOrderApply(AuditBorrowApplyEntity auditBorrowApplyEntity, String status) {
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 网点编码
		String orgCode =  FossUserContext.getCurrentDeptCode();
		String orgName =  FossUserContext.getCurrentDept().getName();
		LOGGER.info("当前登录人编码: {}, 名称:{}, 网点编码{}, 网点名称:{}", new Object[]{userCode, userName, orgCode, orgName});
		// 受理时间
		auditBorrowApplyEntity.setAuditTime(new Date());
		// 受理序号
		Integer auditNo = getNextAuditNo(auditBorrowApplyEntity.getBorrowNo());
		LOGGER.info("受理编号: {}", auditNo);
		auditBorrowApplyEntity.setAuditNo(auditNo);
		// 审核车队名称
		auditBorrowApplyEntity.setAuditOrgName(orgName);
		// 审核车队编码
		auditBorrowApplyEntity.setAuditOrgCode(orgCode);
		// 审核人员名称
		auditBorrowApplyEntity.setAuditEmpName(userName);
		// 审核人员编码
		auditBorrowApplyEntity.setAuditEmpCode(userCode);
		// 状态
		auditBorrowApplyEntity.setStatus(status);
		// 保存
		addAuditBorrowApply(auditBorrowApplyEntity);
	}
	
	/**
	 * 借车审核log
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午12:09:20
	 * @param borrowNo 借车编号 
	 * @return {@link java.util.List}
	 */
	public List<AuditBorrowApplyEntity> queryPassBorrowApplyAndAuditBorrowApplyLog(String borrowNo) {
		AuditBorrowApplyEntity auditBorrowApplyEntity = new AuditBorrowApplyEntity();
		auditBorrowApplyEntity.setBorrowNo(borrowNo);
		return auditBorrowApplyDao.queryAuditBorrowApplyList(auditBorrowApplyEntity);
	}

	/**
	 * 设置auditBorrowApplyDao
	 * @param auditBorrowApplyDao the auditBorrowApplyDao to set
	 */
	public void setAuditBorrowApplyDao(IAuditBorrowApplyDao auditBorrowApplyDao) {
		this.auditBorrowApplyDao = auditBorrowApplyDao;
	}

	/**
	 * 设置borrowVehicleService
	 * @param borrowVehicleService the borrowVehicleService to set
	 */
	public void setBorrowVehicleService(IBorrowVehicleService borrowVehicleService) {
		this.borrowVehicleService = borrowVehicleService;
	}
	
}