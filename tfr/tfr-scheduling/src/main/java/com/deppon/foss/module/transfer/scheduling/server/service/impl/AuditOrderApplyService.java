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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/AuditOrderApplyService.java
 * 
 *  FILE NAME     :AuditOrderApplyService.java
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
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.service.impl
 * FILE    NAME: AuditOrderApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditOrderApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.OrderVehicleException;

import com.deppon.foss.util.UUIDUtils;

/**
 * 审核约车log服务
 * 
 * @author 104306-foss-wangLong
 * @date 2012-11-22 下午3:32:32
 */
public class AuditOrderApplyService implements IAuditOrderApplyService {

	private IAuditOrderApplyDao auditOrderApplyDao;

	private IOrderVehicleService orderVehicleService;

	private IMessageService messageService;

	/**
	 * 新增
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数
	 */
	@Override
	@Transactional
	public int addAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		return auditOrderApplyDao.addAuditOrderApply(auditOrderApplyEntity);
	}

	/**
	 * 修改
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return 受影响的行数
	 */
	@Override
	@Transactional
	public int updateAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		return auditOrderApplyDao.updateAuditOrderApply(auditOrderApplyEntity);
	}

	/**
	 * 查询集合
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return java.util.List
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AuditOrderApplyEntity> queryAuditOrderApplyList(AuditOrderApplyEntity auditOrderApplyEntity) {
		return auditOrderApplyDao.queryAuditOrderApplyList(auditOrderApplyEntity);
	}

	/**
	 * 根据约车单号查询 审核记录
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param orderNo
	 *            约车单号
	 * @return java.util.List
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AuditOrderApplyEntity> queryAuditOrderApplyListByOrderNo(String orderNo) {
		AuditOrderApplyEntity auditOrderApplyEntity = new AuditOrderApplyEntity();
		auditOrderApplyEntity.setOrderNo(orderNo);
		return queryAuditOrderApplyList(auditOrderApplyEntity);
	}

	/**
	 * 查询集合 分页
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AuditOrderApplyEntity> queryAuditOrderApplyForPage(AuditOrderApplyEntity auditOrderApplyEntity, int start, int pageSize) {
		return auditOrderApplyDao.queryAuditOrderApplyForPage(auditOrderApplyEntity, start, pageSize);
	}

	/**
	 * 统计记录数
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午3:32:32
	 * @param auditOrderApplyEntity
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Long queryCount(AuditOrderApplyEntity auditOrderApplyEntity) {
		return auditOrderApplyDao.queryCount(auditOrderApplyEntity);
	}

	/**
	 * 退回约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午4:49:19
	 * @param auditOrderApplyEntity
	 */
	@Override
	@Transactional
	public void doReturnOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity, String orderId) {
		if (auditOrderApplyEntity == null) {
			// 异常
			throw new OrderVehicleException("参数错误");
		}
		String orderNo = auditOrderApplyEntity.getOrderNo();
		if (orderNo == null) {
			// 异常
			throw new OrderVehicleException("约车单号为空");
		}
		OrderVehicleEntity orderVehicleEntity = orderVehicleService.queryOrderVehicleByOrderNo(orderNo);
		if (orderVehicleEntity == null) {
			throw new OrderVehicleException("约车申请不存在!");
		}
		// 备注信息
		if (StringUtil.isBlank(auditOrderApplyEntity.getNotes())) {
			// 异常
			throw new OrderVehicleException("退回约车申请，必须有备注信息.!");
		}
		// 判断是否未审核 只有未审核的才能审核受理
		if (!StringUtils.equals(orderVehicleEntity.getStatus(), OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED)) {
			// 状态错误 .
			throw new OrderVehicleException("只有未审核的约车申请才能进行受理!");
		}
		// 更新约车申请状态到退回
		orderVehicleService.updateOrderVehicleApplyForReturnStatus(orderId);
		// 保存约车审核log
		settingAndAddAuditOrderApply(auditOrderApplyEntity, OrderVehicleConstants.ORDERVEHICLE_STATUS_RETURN);

		// 生成通知消息
		InstationJobMsgEntity instationMsgEntity = createInstationMsg(orderVehicleEntity.getApplyEmpCode(), orderVehicleEntity.getApplyEmpName(), orderNo);

		messageService.createBatchInstationMsg(instationMsgEntity);
	}

	/**
	 * 创建消息通知
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-21 下午5:04:24
	 */
	private InstationJobMsgEntity createInstationMsg(String orgCode, String receiveUserName, String orderNo) {
		InstationJobMsgEntity instationMsgEntity = new InstationJobMsgEntity();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		instationMsgEntity.setId(UUIDUtils.getUUID());

		instationMsgEntity.setSenderCode(currentInfo.getEmpCode());
		instationMsgEntity.setSenderName(currentInfo.getEmpName());

		instationMsgEntity.setSenderOrgCode(currentInfo.getCurrentDeptCode());
		instationMsgEntity.setSenderOrgName(currentInfo.getCurrentDeptName());

		instationMsgEntity.setReceiveOrgCode(orgCode);
		// 消息内容
		String message = "约车申请被退回.约车单号:" + orderNo;
		instationMsgEntity.setContext(message);
		// 消息创建方式
		instationMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		// 站内消息类型
		instationMsgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
		instationMsgEntity.setPostDate(new Date());

		return instationMsgEntity;
	}

	/**
	 * 新增约车审核log
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午12:01:27
	 */
	private void settingAndAddAuditOrderApply(AuditOrderApplyEntity auditOrderApplyEntity, String status) {
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		// 名称
		String userName = user.getEmployee().getEmpName();
		// 网点编码
		String orgCode = FossUserContext.getCurrentDeptCode();
		String orgName = FossUserContext.getCurrentDept().getName();
		// 受理时间
		auditOrderApplyEntity.setAuditTime(new Date());
		// 受理序号
		Integer auditNo = getNextAuditNo(auditOrderApplyEntity.getOrderNo());
		auditOrderApplyEntity.setAuditNo(auditNo);
		// 审核车队名称
		auditOrderApplyEntity.setAuditOrgName(orgName);
		// 审核车队编码
		auditOrderApplyEntity.setAuditOrgCode(orgCode);
		// 审核人员名称
		auditOrderApplyEntity.setAuditEmpName(userName);
		// 审核人员编码
		auditOrderApplyEntity.setAuditEmpCode(userCode);
		// 状态
		auditOrderApplyEntity.setStatus(status);
		// 保存
		addAuditOrderApply(auditOrderApplyEntity);
	}

	/**
	 * 获取下一个受理序号
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午9:48:36
	 */
	private int getNextAuditNo(String orderNo) {
		int no = queryAuditNo(orderNo);
		AtomicInteger atomicNo = new AtomicInteger(no);
		return atomicNo.incrementAndGet();
	}

	/**
	 * 查询受理序号 每次+1
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午8:06:55
	 */
	@Override
	public Integer queryAuditNo(String orderNo) {
		return auditOrderApplyDao.queryAuditNo(orderNo);
	}

	/**
	 * 拒绝约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午10:38:24
	 */
	@Override
	public void doDismissOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity, String orderId) {
		if (auditOrderApplyEntity == null) {
			// 异常
			throw new OrderVehicleException("参数错误");
		}
		String orderNo = auditOrderApplyEntity.getOrderNo();
		if (orderNo == null) {
			// 异常
			throw new OrderVehicleException("约车单号为空");
		}
		OrderVehicleEntity orderVehicleEntity = orderVehicleService.queryOrderVehicleByOrderNo(orderNo);
		if (orderVehicleEntity == null) {
			throw new OrderVehicleException("约车申请不存在!");
		}
		// 备注信息
		if (StringUtil.isBlank(auditOrderApplyEntity.getNotes())) {
			// 异常
			throw new OrderVehicleException("退回约车申请，必须有备注信息.!");
		}
		// 判断是否未审核 只有未审核的才能审核受理
		if (!StringUtils.equals(orderVehicleEntity.getStatus(), OrderVehicleConstants.ORDERVEHICLE_STATUS_UNAPPROVED)) {
			// 状态错误 .
			throw new OrderVehicleException("只有未审核的约车申请才能进行受理!");
		}
		// 更新约车申请状态到拒绝
		orderVehicleService.updateOrderVehicleApplyForDismissStatus(orderId);
		// 保存约车审核log
		settingAndAddAuditOrderApply(auditOrderApplyEntity, OrderVehicleConstants.ORDERVEHICLE_STATUS_DISMISS);
	}

	/**
	 * 受理约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-24 上午2:06:55
	 * @param auditOrderApplyEntity
	 */
	@Override
	public void doAcceptedOrderVehicleApply(AuditOrderApplyEntity auditOrderApplyEntity) {
		// 保存约车审核log
		settingAndAddAuditOrderApply(auditOrderApplyEntity, OrderVehicleConstants.ORDERVEHICLE_STATUS_ACCEPTED);
	}

	/**
	 * 设置auditOrderApplyDao
	 * 
	 * @param auditOrderApplyDao
	 *            the auditOrderApplyDao to set
	 */
	public void setAuditOrderApplyDao(IAuditOrderApplyDao auditOrderApplyDao) {
		this.auditOrderApplyDao = auditOrderApplyDao;
	}

	/**
	 * 设置orderVehicleService
	 * 
	 * @param orderVehicleService
	 *            the orderVehicleService to set
	 */
	public void setOrderVehicleService(IOrderVehicleService orderVehicleService) {
		this.orderVehicleService = orderVehicleService;
	}

	/**
	 * 设置messageService
	 * 
	 * @param messageService
	 *            the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}