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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/PassInviteApplyAction.java
 * 
 *  FILE NAME     :PassInviteApplyAction.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: PassInviteApplyAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PassInviteApplyVo;

/**
 * PassInviteApplyAction
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public class PassInviteApplyAction extends AbstractAction {

	private static final long serialVersionUID = 44313259977825024L;
	
	private PassInviteApplyVo passInviteApplyVo = new PassInviteApplyVo();

	private IPassInviteApplyService passInviteApplyService;

	private IInviteVehicleService inviteVehicleService;

	private IAuditInviteApplyService auditInviteApplyService;
	
	private ILeasedVehicleWhitelistService leasedVehicleWhitelistService;
	
	/**
	 * 查询约车信息
	 * @param null
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 上午9:11:37
	 */
	@JSON
	public String queryPassInviteVehicleInfo(){
		try {
			List<String> stautsList = new ArrayList<String>();
			InviteVehicleDto inviteVehicleDto = new InviteVehicleDto();
			List<String> inviteNoList = passInviteApplyVo.getInviteNoList();
			boolean isLoadAll = passInviteApplyVo.isInviteVehicleIsLoadAll();
			
			stautsList.add(InviteVehicleConstants.INVITEVEHICLE_STATUS_UNCOMMITTED);
			//-3为可配置项
			inviteVehicleDto.setPredictUseTime(org.apache.commons.lang.time.DateUtils.addDays(new Date(), -3));
			inviteVehicleDto.setInviteVehicleStatusList(stautsList);
			
			List<InviteVehicleDto> queryInviteVehicleList = inviteVehicleService.queryInviteVehicleListByNeedPassRecord(isLoadAll,inviteNoList,inviteVehicleDto);
			
			passInviteApplyVo.setInviteVehicleDtoList(queryInviteVehicleList);
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
		
	}

	/**
	 * 根据外请车单号查询(审核受理记录、车辆信息)
	 * @param inviteNo 外请车单号
	 * @return InviteVehicleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午12:20:14
	 */
	@JSON
	public String queryAuditInviteLogListAndVehicleList(){
		String inviteNo = passInviteApplyVo.getInviteNo();
		InviteVehicleDto inviteVehicleDto = passInviteApplyService.queryAuditInviteLogListAndVehicleList(inviteNo);
		passInviteApplyVo.setInviteVehicleDto(inviteVehicleDto);
		return super.returnSuccess();
	}
	
	/**
	 * 退回审核外请车约车申请 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午6:20:35
	 */
	@JSON
	public String backInviteInviteApplyApply(){
		String notes = passInviteApplyVo.getNotes();
		String inviteNo = passInviteApplyVo.getInviteNo();
		String applyOrgCode = passInviteApplyVo.getApplyOrgCode();
		auditInviteApplyService.backInviteInviteApplyApply(notes,inviteNo,applyOrgCode);
		return super.returnSuccess();
	}
	
	/**
	 * 审核外请车约车通过
	 * @param 外请车单号 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午3:18:03
	 */
	@JSON
	public String passInviteInviteApplyApply(){
	try{
		
		auditInviteApplyService.passInviteInviteApplyApply(passInviteApplyVo);
	} catch (TfrBusinessException e) {
		return returnError(e);
	}
	return returnSuccess();
	}
	
	/**
	 * 查询外请车车辆信息
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-11 下午3:14:42
	 */
	@JSON
	public String previewPassInviteVehicleDetail(){
		String vehicleNo = passInviteApplyVo.getVehicleNo();
		String vehicleType = passInviteApplyVo.getVehcleLengthName();
		String driverName = passInviteApplyVo.getDriverName();
		String driverPhone = passInviteApplyVo.getDriverPhone();
		boolean isOpenVehicle = false;
		if("true".equals(passInviteApplyVo.getIsOpenVehicle())){
			isOpenVehicle = true;
		}
		List<VehicleDriverWithDto> vehicleDriverWithDtoList = passInviteApplyService
				.queryPreviewPassInviteVehicleDetail(vehicleNo,vehicleType,driverName,driverPhone,isOpenVehicle);
		passInviteApplyVo.setVehicleDriverWithDtoList(vehicleDriverWithDtoList);
		return super.returnSuccess();
	}
	
	/**
	 * 根据车牌号查询外请车使用状态
	 * @param vehicleNo
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-12 下午4:32:26
	 */
	public String queryInviteUseStatus(){
		String vehicleNo = passInviteApplyVo.getVehicleNo();
		List<InviteVehicleEntity> inviteVehicleList = passInviteApplyService.queryInviteUseStatus(vehicleNo);
		passInviteApplyVo.setInviteVehicleList(inviteVehicleList);
		return super.returnSuccess();
	}
	
	/**
	 * 拒绝约车申请 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午1:52:28
	 */
	public String refusePassInviteVehicleApply(){
		String inviteNo = passInviteApplyVo.getInviteNo();
		String notes = passInviteApplyVo.getNotes();
		auditInviteApplyService.refuseInviteInviteApplyApply(inviteNo,notes);
		return super.returnSuccess();
	}
	
	/**
	 * 根据车牌号、车型、是否开蓬、司机姓名、司机手机、请车价格查询外请车信息
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 上午9:15:06
	 */
	@SuppressWarnings("unchecked")
	@JSON
	public String queryVehicleApplyInfo(){
		passInviteApplyVo.getWhitelistAuditEntity();
		
		WhitelistAuditQueryDto whitelistAuditQueryDto=new WhitelistAuditQueryDto();
		BeanUtils.copyProperties(passInviteApplyVo.getWhitelistAuditEntity(), whitelistAuditQueryDto);
		
		PaginationDto paginationDto = leasedVehicleWhitelistService.queryLeasedVehicleWhitelistsListBySelectiveCondition(whitelistAuditQueryDto,0,ConstantsNumberSonar.SONAR_NUMBER_100);
		List<WhitelistAuditEntity> whitelistAuditList = paginationDto.getPaginationDtos();
		passInviteApplyVo.setWhitelistAuditList(whitelistAuditList);
		return super.returnSuccess();
	}
	
	/**
	 * 获得passInviteApplyVo
	 * @return the passInviteApplyVo
	 */
	public PassInviteApplyVo getPassInviteApplyVo() {
		return passInviteApplyVo;
	}
	
	/**
	 * 设置passInviteApplyVo
	 * @param passInviteApplyVo the passInviteApplyVo to set
	 */
	public void setPassInviteApplyVo(PassInviteApplyVo passInviteApplyVo) {
		this.passInviteApplyVo = passInviteApplyVo;
	}
	
	/**
	 * 设置passInviteApplyService
	 * @param passInviteApplyService the passInviteApplyService to set
	 */
	public void setPassInviteApplyService(IPassInviteApplyService passInviteApplyService) {
		this.passInviteApplyService = passInviteApplyService;
	}

	/**
	 * 设置inviteVehicleService
	 * @param inviteVehicleService the inviteVehicleService to set
	 */
	public void setInviteVehicleService(IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}

	/**
	 * 设置auditInviteApplyService
	 * @param auditInviteApplyService the auditInviteApplyService to set
	 */
	public void setAuditInviteApplyService(
			IAuditInviteApplyService auditInviteApplyService) {
		this.auditInviteApplyService = auditInviteApplyService;
	}

	/**
	 * 设置leasedVehicleWhitelistService
	 * @param leasedVehicleWhitelistService the leasedVehicleWhitelistService to set
	 */
	public void setLeasedVehicleWhitelistService(
			ILeasedVehicleWhitelistService leasedVehicleWhitelistService) {
		this.leasedVehicleWhitelistService = leasedVehicleWhitelistService;
	}
}