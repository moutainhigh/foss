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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/PassBorrowApplyAction.java
 * 
 *  FILE NAME     :PassBorrowApplyAction.java
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
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IBorrowVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.BorrowVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PassBorrowApplyVo;

/**
 * 借车审核Action
 * @author 104306-foss-wangLong
 * @date 2012-12-3 下午12:22:37
 */
public class PassBorrowApplyAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3445615162646921450L;

	/**
	 * 
	 */
	private PassBorrowApplyVo passBorrowApplyVo = new PassBorrowApplyVo();

	/** 受理借车Service  */
	private IPassBorrowApplyService passBorrowApplyService;

	/** 借车申请Service */
	private IBorrowVehicleService borrowVehicleService;
	
	/** 借车受理LogService */
	private IAuditBorrowApplyService auditBorrowApplyService;
	
	/** 车辆信息 Service */
	private IOwnVehicleService ownVehicleService;
	
	/** 组织结构 Service */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	/**
	 * 查询车队小组
	 * 
	 * @author 104306-foss-wangLong
	 * @date 2012-12-6 上午9:19:50
	 */
	@JSON
	public String queryTransTeamListForBorrow() {
		List<OrgAdministrativeInfoEntity> transTeamList = null;
		String transDepartment = passBorrowApplyVo.getTransDepartment();
		if(StringUtils.isBlank(transDepartment)){
			transDepartment = FossUserContext.getCurrentDeptCode();
		}
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
		passBorrowApplyVo.setTransTeamList(transTeamList);
		return returnSuccess();
	}
	
	/**
	 * 根据借车单号list 查询借车信息列表
	 * @author 104306-foss-wangLong
	 * @date 2012-12-6 上午11:30:29
	 * @return {@link java.lang.String}
	 * @see IBorrowVehicleService#queryBorrowVehicleListByBorrowNoList(List)
	 */
	@JSON
	public String queryNeedAcceptBorrowVehicleApply() {
		List<String> borrowNoList = passBorrowApplyVo.getBorrowNoList();
		try {
			List<BorrowVehicleEntity> borrowVehicleList = borrowVehicleService.queryNeedAcceptBorrowVehicleApply(borrowNoList);
			passBorrowApplyVo.setBorrowVehicleList(borrowVehicleList);
		} catch (BorrowVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 审核通过
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:03:50
	 * @return {@link java.lang.String}
	 * @see IPassBorrowApplyService#doAcceptedBorrowVehicleApply(PassBorrowApplyEntity, String)
	 * @see IAuditBorrowApplyService#doAcceptedBorrowVehicleApply(AuditBorrowApplyEntity)
	 */
	@JSON
	public String doAcceptedBorrowVehicleApply() {
		PassBorrowApplyEntity passBorrowApplyEntity = passBorrowApplyVo.getPassBorrowApplyEntity();
		AuditBorrowApplyEntity auditBorrowApplyEntity = passBorrowApplyVo.getAuditBorrowApplyEntity();
		try {
			passBorrowApplyService.doAcceptedBorrowVehicleApply(passBorrowApplyEntity, auditBorrowApplyEntity.getNotes());
		} catch (BorrowVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 拒绝
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:03:50
	 * @return {@link java.lang.String}
	 * @see IAuditBorrowApplyService#doDismissBorrowVehicleApply(List)
	 */
	@JSON
	public String doDismissBorrowVehicleApply() {
		List<AuditBorrowApplyEntity> auditBorrowApplyList = passBorrowApplyVo.getAuditBorrowApplyList();
		try {
			auditBorrowApplyService.doDismissBorrowVehicleApply(auditBorrowApplyList);
		} catch (BorrowVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 退回
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:03:50
	 * @return {@link java.lang.String}
	 * @see IAuditBorrowApplyService#doReturnBorrowVehicleApply(List)
	 */
	@JSON
	public String doReturnBorrowVehicleApply() {
		List<AuditBorrowApplyEntity> auditBorrowApplyList = passBorrowApplyVo.getAuditBorrowApplyList();
		try {
			auditBorrowApplyService.doReturnBorrowVehicleApply(auditBorrowApplyList);
		} catch (BorrowVehicleException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 借车审核log查询
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午12:13:18
	 * @return {@link java.lang.String}
	 * @see IAuditBorrowApplyService#queryPassBorrowApplyAndAuditBorrowApplyLog(String)
	 */
	@JSON
	public String queryAuditBorrowApplyLog() {
		PassBorrowApplyEntity passBorrowApplyEntity = passBorrowApplyVo.getPassBorrowApplyEntity();
		String borrowNo = passBorrowApplyEntity.getBorrowNo();
		List<AuditBorrowApplyEntity> auditBorrowApplyList = auditBorrowApplyService.queryPassBorrowApplyAndAuditBorrowApplyLog(borrowNo);
		passBorrowApplyVo.setAuditBorrowApplyList(auditBorrowApplyList);
		return returnSuccess();
	}
	
	/**
	 * 查询公司车
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 上午9:29:04
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryCompanyVehicle() {
		OwnTruckEntity ownTruck = passBorrowApplyVo.getOwnTruckEntity();
		if (ownTruck == null) {
			ownTruck = new OwnTruckEntity();
		}
		// 车牌号
		List<String> vehicleNos = new ArrayList<String>();
		if (!StringUtil.isEmpty(ownTruck.getVehicleNo())) {
			vehicleNos.add(ownTruck.getVehicleNo());
		}
		PaginationDto paginationDto = null;
		try {
			paginationDto = ownVehicleService.queryVehicleAssociationDtoListPaginationByCondition(ownTruck.getOrgId(), vehicleNos, ownTruck.getVehcleLengthCode(), null, 0, Integer.MAX_VALUE, null);
		} catch (BusinessException e) {
			return returnError(e);
		}
		if (paginationDto == null) {
			return returnSuccess(); 
		}
		passBorrowApplyVo.setOwnTruckList(paginationDto.getPaginationDtos());
		return returnSuccess(); 
	}
	
	/**
	 * 获得passBorrowApplyVo
	 * @return the passBorrowApplyVo
	 */
	public PassBorrowApplyVo getPassBorrowApplyVo() {
		return passBorrowApplyVo;
	}
	
	/**
	 * 设置passBorrowApplyVo
	 * @param passBorrowApplyVo the passBorrowApplyVo to set
	 */
	public void setPassBorrowApplyVo(PassBorrowApplyVo passBorrowApplyVo) {
		this.passBorrowApplyVo = passBorrowApplyVo;
	}
	
	/**
	 * 设置passBorrowApplyService
	 * @param passBorrowApplyService the passBorrowApplyService to set
	 */
	public void setPassBorrowApplyService(IPassBorrowApplyService passBorrowApplyService) {
		this.passBorrowApplyService = passBorrowApplyService;
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

	/**
	 * 设置ownVehicleService
	 * @param ownVehicleService the ownVehicleService to set
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	/**
	 * 设置 组织结构 Service.
	 *
	 * @param orgAdministrativeInfoComplexService the new 组织结构 Service
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
}