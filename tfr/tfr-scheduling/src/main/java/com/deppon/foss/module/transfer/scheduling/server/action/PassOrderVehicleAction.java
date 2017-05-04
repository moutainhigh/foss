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
 *  DESCRIPTION   : 调度、约车记录、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/PassOrderVehicleAction.java
 * 
 *  FILE NAME     :PassOrderVehicleAction.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.action
 * FILE    NAME: PassOrderVehicleApply.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.List;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IOrderVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassOrderApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditOrderApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.OrderVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PassOrderApplyDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;

import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PassOrderVehicleVo;

/**
 * 受理约车
 * 
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午4:41:34
 */
public class PassOrderVehicleAction extends AbstractAction {

	private static final long serialVersionUID = 5857717226743045669L;

	/** 受理约车Vo */
	private PassOrderVehicleVo passOrderVehicleVo = new PassOrderVehicleVo();

	/** 约车申请Service */
	private IOrderVehicleService orderVehicleService;

	/** 受理约车Service */
	private IPassOrderApplyService passOrderApplyService;

	/** 约车审核logService */
	private IAuditOrderApplyService auditOrderApplyService;

	/** 组织结构 Service */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 业务锁Service
	 */
	//未使用-352203
//	private IBusinessLockService businessLockService;

/*	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}*/

	/**
	 * 加载当前登录用户所在部门约车申请信息
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午7:45:30
	 * @return
	 */
	@JSON
	public String queryOrderVehicleApply() {
		try {
			List<OrderVehicleEntity> orderVehicleResultList = orderVehicleService.queryOrderVehicleListByCanAcceptedStatus(passOrderVehicleVo.getOrderIdList(), passOrderVehicleVo.isLoadAll());

			passOrderVehicleVo.setOrderVehicleResultList(orderVehicleResultList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 根据约车单号 加载审核受理记录
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-22 下午7:45:30
	 * @return
	 */
	@JSON
	public String queryAuditOrderApplyLog() {
		OrderVehicleEntity orderVehicleEntity = passOrderVehicleVo.getOrderVehicleEntity();
		String orderNo = orderVehicleEntity.getOrderNo();
		PassOrderApplyDto passOrderApplyDto = passOrderApplyService.queryPassOrderApplyAndAuditOrderApplyLog(orderNo);
		passOrderVehicleVo.setPassOrderApplyDto(passOrderApplyDto);
		return returnSuccess();
	}

	/**
	 * 退回约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午3:00:56
	 * @return
	 */
	@JSON
	public String doReturnOrderVehicleApply() {
		try {
			AuditOrderApplyEntity auditOrderApplyEntity = passOrderVehicleVo.getAuditOrderApplyEntity();
			// 保存更新
			auditOrderApplyService.doReturnOrderVehicleApply(auditOrderApplyEntity, passOrderVehicleVo.getOrderId());
			// 解锁
			return returnSuccess();
		} catch (TfrBusinessException e) {
			// 记录异常错误
			return returnError(e);
		}
	}

	/**
	 * 受理通过
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午3:00:56
	 * @return
	 */
	@JSON
	public String doAcceptedOrderVehicleApply() {
		try {
			// 保存更新
			passOrderApplyService.doAcceptedOrderVehicleApply(passOrderVehicleVo.getPassOrderApplyDto(), passOrderVehicleVo.getOrderId());
			return returnSuccess();
		} catch (TfrBusinessException e) {
			// 记录异常错误
			return returnError(e);
		}
	}

	/**
	 * 拒绝约车申请
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-11-23 下午10:33:19
	 * @return
	 */
	@JSON
	public String doDismissOrderVehicleApply() {
		try {
			AuditOrderApplyEntity auditOrderApplyEntity = passOrderVehicleVo.getAuditOrderApplyEntity();
			// 保存更新
			auditOrderApplyService.doDismissOrderVehicleApply(auditOrderApplyEntity, passOrderVehicleVo.getOrderId());
			return returnSuccess();
		} catch (TfrBusinessException e) {
			// 记录异常错误
			return returnError(e);
		}
	}

	/**
	 * 查询车队小组
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-6 上午9:19:50
	 */
	@JSON
	public String queryTransTeamList() {
		List<OrgAdministrativeInfoEntity> transTeamList = null;
		String transDepartment = passOrderVehicleVo.getTransDepartment();
		// 尝试找登陆部门所属的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(transDepartment);
		if (null == topFleet) {
			return returnError("无法找到登陆部门的顶级车队!");
		} else {
			transDepartment = topFleet.getCode();
		}
		try {
			transTeamList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoSubByBizType(transDepartment, BizTypeConstants.ORG_TRANS_TEAM);
		} catch (BusinessException e) {
			return returnError(e);
		}
		passOrderVehicleVo.setTransTeamList(transTeamList);
		return returnSuccess();
	}

	/**
	 * 查询公司车 & 借来的车
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 上午9:29:04
	 */
	@JSON
	public String queryCompanyVehicleAndBorrowVehicle() {
		VehicleDriverWithDto vehicleDriverWithDto = passOrderVehicleVo.getVehicleDriverWithDto();
		if (vehicleDriverWithDto == null) {
			vehicleDriverWithDto = new VehicleDriverWithDto();
		}
		// 接口只判断了null 没判断empty
		if (StringUtil.isEmpty(vehicleDriverWithDto.getVehicleNo())) {
			vehicleDriverWithDto.setVehicleNo(null);
		}
		List<VehicleDriverWithDto> vehicleDriverWithDtoList = null;
		try {
			vehicleDriverWithDtoList = passOrderApplyService.queryCompanyVehicleAndBorrowVehicle(vehicleDriverWithDto);
		} catch (BusinessException e) {
			return returnError(e);
		}
		passOrderVehicleVo.setVehicleDriverWithDtoList(vehicleDriverWithDtoList);
		return returnSuccess();
	}

	/**
	 * 获取锁条件
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-4-22 上午10:14:03
	 */
	private MutexElement fetchMutexElement(String orderId) {
		// 锁定条件
		MutexElement mutex = new MutexElement(orderId, "约车", MutexElementType.TRUCK_SCHEDULING);
		return mutex;
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
	 * 设置passOrderApplyService
	 * 
	 * @param passOrderApplyService
	 *            the passOrderApplyService to set
	 */
	public void setPassOrderApplyService(IPassOrderApplyService passOrderApplyService) {
		this.passOrderApplyService = passOrderApplyService;
	}

	/**
	 * 设置passOrderVehicleVo
	 * 
	 * @param passOrderVehicleVo
	 *            the passOrderVehicleVo to set
	 */
	public void setPassOrderVehicleVo(PassOrderVehicleVo passOrderVehicleVo) {
		this.passOrderVehicleVo = passOrderVehicleVo;
	}

	/**
	 * 设置auditOrderApplyService
	 * 
	 * @param auditOrderApplyService
	 *            the auditOrderApplyService to set
	 */
	public void setAuditOrderApplyService(IAuditOrderApplyService auditOrderApplyService) {
		this.auditOrderApplyService = auditOrderApplyService;
	}

	/**
	 * 获得passOrderVehicleVo
	 * 
	 * @return the passOrderVehicleVo
	 */
	public PassOrderVehicleVo getPassOrderVehicleVo() {
		return passOrderVehicleVo;
	}

	/**
	 * 设置orgAdministrativeInfoComplexService
	 * 
	 * @param orgAdministrativeInfoComplexService
	 *            the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}