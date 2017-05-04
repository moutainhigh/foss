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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/BorrowVehicleService.java
 * 
 *  FILE NAME     :BorrowVehicleService.java
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
 * FILE    NAME: BorrowVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleTypeException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.BorrowVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.define.OrderVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IBorrowVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IBorrowVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassBorrowApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.BorrowVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassBorrowApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.BorrowVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.BorrowVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * 借车申请service
 * @author 104306-foss-wangLong
 * @date 2012-12-03 下午1:06:57
 */
public class BorrowVehicleService implements IBorrowVehicleService {
	
	private IBorrowVehicleDao borrowVehicleDao;
	
	private ITfrCommonService tfrCommonService;
	
	/** 借车受理  */
	private IPassBorrowApplyService passBorrowApplyService;
	
	/** 借车受理log */
	private IAuditBorrowApplyService auditBorrowApplyService;
	
	/** 车辆信息Service */
	private IVehicleService vehicleService;
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity) {
		String borrowNo = tfrCommonService.generateSerialNumber(TfrSerialNumberRuleEnum.JC);
		borrowVehicleEntity.setBorrowNo(borrowNo);
		borrowVehicleEntity.setApplyTime(new Date());
		
		UserEntity user = FossUserContext.getCurrentUser();
		// 工号
		String userCode = user.getEmployee().getEmpCode();
		if (StringUtil.isBlank(borrowVehicleEntity.getApplyEmpCode())) {
			borrowVehicleEntity.setApplyEmpCode(userCode);
		}
		return borrowVehicleDao.addBorrowVehicle(borrowVehicleEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int updateBorrowVehicle(BorrowVehicleEntity borrowVehicleEntity) {
		
		BorrowVehicleEntity oldBorrowVehicleEntity=this.queryBorrowVehicle(borrowVehicleEntity.getId());
		List<String> list=new ArrayList<String>();
		//暂存
		list.add("STAGING");
		//未审核
		list.add("UNAPPROVED");
		//退回
		list.add("RETURN");
		if(!list.contains(oldBorrowVehicleEntity.getStatus())){
			throw new TfrBusinessException("该状态不支持编辑！");
		}
		
		return borrowVehicleDao.updateBorrowVehicle(borrowVehicleEntity);
	}

	/**
	 * 查询单个对象（按主键查询）
	 * <br>------------------------------<br>
	 * 存在 返回该对象 不存在返回null
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param id  主键id
	 * @return com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassOrderApplyEntity
	 */
	@Transactional(readOnly = true)
	public BorrowVehicleEntity queryBorrowVehicle(String id) {
		return borrowVehicleDao.queryBorrowVehicle(id);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<BorrowVehicleEntity> queryBorrowVehicleList(BorrowVehicleDto borrowVehicleDto) {
		return borrowVehicleDao.queryBorrowVehicleList(borrowVehicleDto);
	}
	
	/**
	 * 根据借车单号List查询对象</br>
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowNoList
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<BorrowVehicleEntity> queryBorrowVehicleListByBorrowNoList(List<String> borrowNoList) {
		if (CollectionUtils.isEmpty(borrowNoList)) {
			return new ArrayList<BorrowVehicleEntity>();
		}
		BorrowVehicleDto borrowVehicleDto = new BorrowVehicleDto();
		borrowVehicleDto.setBorrowNoList(borrowNoList);
		return queryBorrowVehicleList(borrowVehicleDto);
	}
	
	/**
	 * 根据借车单号List查询对象</br>
	 * 用于借车申请到借车审核页面
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowNoList
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<BorrowVehicleEntity> queryNeedAcceptBorrowVehicleApply(List<String> borrowNoList) {
		List<BorrowVehicleEntity> list = queryBorrowVehicleListByBorrowNoList(borrowNoList);
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<BorrowVehicleEntity>();
		}
		// 状态验证
		for (BorrowVehicleEntity borrowVehicleEntity : list) {
			String status = borrowVehicleEntity.getStatus();
			if (StringUtil.isBlank(status)) {
				throw new BorrowVehicleException("状态错误,借车单号:" + borrowVehicleEntity.getBorrowNo());
			}
			if (!(StringUtil.equals(status, BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED) 
					|| StringUtil.equals(status, BorrowVehicleConstants.BORROWVEHICLE_STATUS_UNAPPROVED))) {
				throw new BorrowVehicleException("状态有改变,借车单号:" + borrowVehicleEntity.getBorrowNo() + "，请刷新页面!");
			} 
		}
		return list;
	}
	
	/**
	 * 根据借车单号查询对象
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return BorrowVehicleEntity
	 */
	@Transactional(readOnly = true)
	public BorrowVehicleEntity queryBorrowVehicleByBorrowNo(String borrowNo) {
		BorrowVehicleDto borrowVehicleDto = new BorrowVehicleDto();
		borrowVehicleDto.setBorrowNo(borrowNo);
		List<BorrowVehicleEntity> list = queryBorrowVehicleList(borrowVehicleDto);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<BorrowVehicleDto> queryBorrowVehicleForPage(BorrowVehicleDto borrowVehicleDto, int start, int pageSize) {
		String applyOrgCode = borrowVehicleDto.getApplyOrgCode();
		String auditOrgCode = borrowVehicleDto.getAuditOrgCode();
		if(StringUtils.isBlank(applyOrgCode) && StringUtils.isBlank(auditOrgCode)){
			if(FossUserContext.getCurrentDept().checkDispatchTeam()){
				borrowVehicleDto.setApplyOrgCode("");
				borrowVehicleDto.setAuditOrgCode("");
				borrowVehicleDto.setQueryType("IsNull");
			}else{
				borrowVehicleDto.setApplyOrgCode(FossUserContext.getCurrentDeptCode());
				borrowVehicleDto.setAuditOrgCode(FossUserContext.getCurrentDeptCode());
				borrowVehicleDto.setQueryType("IsOr");
			}
		}
		if(StringUtils.isNotBlank(applyOrgCode) && StringUtils.isNotBlank(auditOrgCode)){
			borrowVehicleDto.setQueryType("IsAnd");
		}
		if(StringUtils.isBlank(applyOrgCode) && StringUtils.isNotBlank(auditOrgCode)){
			borrowVehicleDto.setQueryType("IsOne");
		}
		if(StringUtils.isBlank(auditOrgCode) && StringUtils.isNotBlank(applyOrgCode)){
			borrowVehicleDto.setQueryType("IsOne");
		}
		return borrowVehicleDao.queryBorrowVehicleForPage(borrowVehicleDto, start, pageSize);
	}
	
	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleDto
	 * @return 
	 */
	@Transactional(readOnly = true)
	public Long queryCount(BorrowVehicleDto borrowVehicleDto) {
		return borrowVehicleDao.queryCount(borrowVehicleDto);
	}
	
	/**
	 * 保存操作
	 * <p>根据主键id,来区分是新增操作 还是修改操作
	 * @author 104306-foss-wangLong
	 * @date 2012-12-03 下午1:06:57
	 * @param borrowVehicleEntity
	 * @throws ParameterException
	 */
	@Transactional
	public void saveBorrowVehicleApply(BorrowVehicleEntity borrowVehicleEntity) {
		//参数验证
		if (borrowVehicleEntity == null) {
			throw new ParameterException(OrderVehicleConstants.ORDERVEHICLE_PARAMETERERROR_MESSAGE);
		}
		//如果id为空   新增操作
		if (StringUtil.isBlank(borrowVehicleEntity.getId())) {
			addBorrowVehicle(borrowVehicleEntity);
		} else {
			updateBorrowVehicle(borrowVehicleEntity);
		}
	}
	
	/**
	 * 撤销借车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @param borrowNoList 借车编号 list
	 */
	@Transactional
	public void doUndoBorrowVehicleApply(List<String> borrowNoList) {
		updateBorrowVehicleApplyStatus(borrowNoList, BorrowVehicleConstants.BORROWVEHICLE_STATUS_UNDO);
	}

	/**
	 * 确认到达
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:04:06
	 * @param borrowNoList 借车编号 list
	 */
	@Transactional
	public void doBorrowVehicleConfirmTo(List<String> borrowNoList) {
		updateBorrowVehicleApplyStatus(borrowNoList, BorrowVehicleConstants.BORROWVEHICLE_STATUS_VERIFY_ARRIVE);
	}
	
	/**
	 * 车辆归还
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:06:18
	 * @param borrowNoList 借车编号 list
	 */
	@Transactional
	public void doBorrowVehicleGiveBack(List<String> borrowNoList) {
		updateBorrowVehicleApplyStatus(borrowNoList, BorrowVehicleConstants.BORROWVEHICLE_STATUS_GIVE_BACK);
	}
	
	/**
	 * 更新到退回状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:48:54
	 * @param borrowNo 借车编号
	 */
	@Transactional
	public void updateBorrowVehicleApplyForReturnStatus(String borrowNo) {
		updateBorrowVehicleApplyStatus(borrowNo, BorrowVehicleConstants.BORROWVEHICLE_STATUS_RETURN);
	}
	
	/**
	 * 更新到拒绝状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-7 下午5:48:54
	 * @param borrowNo 借车编号
	 */
	@Transactional
	public void updateBorrowVehicleApplyForDismissStatus(String borrowNo) {
		updateBorrowVehicleApplyStatus(borrowNo, BorrowVehicleConstants.BORROWVEHICLE_STATUS_DISMISS);
	}
	
	/**
	 * 更新到已受理(通过)状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-10 下午6:55:35
	 * @param borrowNo 借车编号
	 */
	@Transactional
	public void updateBorrowVehicleApplyForAcceptedStatus(String borrowNo) {
		updateBorrowVehicleApplyStatus(borrowNo, BorrowVehicleConstants.BORROWVEHICLE_STATUS_ACCEPTED);
	}
	
	/**
	 * 查询借车申请明细
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午3:46:48
	 * @param borrowNo 借车单号
	 */
	@Transactional(readOnly = true)
	public BorrowVehicleDto queryBorrowVehicleApplyDetail(String borrowNo) {
		if (StringUtil.isBlank(borrowNo)) {
			throw new BorrowVehicleException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, "借车单号为空.");
		}
		BorrowVehicleEntity borrowVehicleEntity = queryBorrowVehicleByBorrowNo(borrowNo);
		if (borrowVehicleEntity == null) {
			throw new BorrowVehicleException(BorrowVehicleConstants.BORROWVEHICLE_PARAMETERERROR_MESSAGE, "借车申请不存在，借车单号:" + borrowNo);
		}
		BorrowVehicleDto borrowVehicleDto = new BorrowVehicleDto();
		BeanUtils.copyProperties(borrowVehicleEntity, borrowVehicleDto);
		// 匹配车辆信息
		PassBorrowApplyEntity passBorrowApplyEntity = passBorrowApplyService.queryPassBorrowApplyByBorrowNo(borrowNo);
		if (passBorrowApplyEntity == null) {
			// 没有审核通过的信息
			passBorrowApplyEntity = new PassBorrowApplyEntity();
		}
		// 车辆信息
		VehicleAssociationDto vehicleAssociationDto = null;
		if (StringUtil.isNotBlank(passBorrowApplyEntity.getVehicleNo())) {
			try {
				vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(passBorrowApplyEntity.getVehicleNo());
			} catch (LeasedVehicleTypeException e) {
				throw new BorrowVehicleException(e.getMessage());
			}
		}
		// 车牌号
		borrowVehicleDto.setVehicleNo(passBorrowApplyEntity.getVehicleNo());
		if (vehicleAssociationDto != null) {
			// 车型
			borrowVehicleDto.setOrderVehicleModelName(vehicleAssociationDto.getVehicleLengthName());
			// 车辆所属
			borrowVehicleDto.setVehicleOrganizationName(vehicleAssociationDto.getVehicleOrganizationName());
		}
		// 受理log
		List<AuditBorrowApplyEntity> auditBorrowApplyList = auditBorrowApplyService.queryPassBorrowApplyAndAuditBorrowApplyLog(borrowNo);
		borrowVehicleDto.setAuditBorrowApplyList(auditBorrowApplyList);
		return borrowVehicleDto;
	}
	
	/**
	 * 更新状态,
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:27:21
	 * @param borrowNoList 借车编号list
	 * @param status 状态
	 */
	private int updateBorrowVehicleApplyStatus(List<String> borrowNoList, String status) {
		return borrowVehicleDao.updateBorrowVehicleApplyStatus(borrowNoList, status);
	}
	
	/**
	 * 更新状态,
	 * @author 104306-foss-wangLong
	 * @date 2012-12-5 下午2:27:21
	 * @param borrowNo 借车单号
	 * @param status 状态
	 */
	private int updateBorrowVehicleApplyStatus(String borrowNo, String status) {
		List<String> borrowNoList = new ArrayList<String>();
		borrowNoList.add(borrowNo);
		return updateBorrowVehicleApplyStatus(borrowNoList, status);
	}
	
	/**
	 * 设置borrowVehicleDao
	 * @param borrowVehicleDao the borrowVehicleDao to set
	 */
	public void setBorrowVehicleDao(IBorrowVehicleDao borrowVehicleDao) {
		this.borrowVehicleDao = borrowVehicleDao;
	}
	
	/**
	 * 设置tfrCommonService
	 * @param tfrCommonService the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 设置passBorrowApplyService
	 * @param passBorrowApplyService the passBorrowApplyService to set
	 */
	public void setPassBorrowApplyService(
			IPassBorrowApplyService passBorrowApplyService) {
		this.passBorrowApplyService = passBorrowApplyService;
	}

	/**
	 * 设置vehicleService
	 * @param vehicleService the vehicleService to set
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
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