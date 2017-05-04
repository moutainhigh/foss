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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/PassInviteApplyService.java
 * 
 *  FILE NAME     :PassInviteApplyService.java
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
 * FILE    NAME: PassInviteApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleBaseDto;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外请约车审核Server
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public class PassInviteApplyService implements IPassInviteApplyService {
	
	//日志
	private static final Logger logger = LogManager.getLogger(PassInviteApplyService.class);
	
	private IPassInviteApplyDao passInviteApplyDao;
	
	private IAuditInviteApplyService auditInviteApplyService;
	
	private ILeasedVehicleTypeService leasedVehicleTypeService;
	
	private ILeasedVehicleService leasedVehicleService;
	
	private IInviteVehicleDao inviteVehicleDao;
	
	/**
	 * 设置passInviteApplyDao
	 * @param passInviteApplyDao the passInviteApplyDao to set
	 */
	public void setPassInviteApplyDao(IPassInviteApplyDao passInviteApplyDao) {
		this.passInviteApplyDao = passInviteApplyDao;
	}
	
	/**
	 * 设置auditInviteApplyService
	 * @param auditInviteApplyService the auditInviteApplyService to set
	 */
	public void setAuditInviteApplyService(IAuditInviteApplyService auditInviteApplyService) {
		this.auditInviteApplyService = auditInviteApplyService;
	}

	public void setLeasedVehicleTypeService(ILeasedVehicleTypeService leasedVehicleTypeService) {
		this.leasedVehicleTypeService = leasedVehicleTypeService;
	}

	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}
	public void setInviteVehicleDao(IInviteVehicleDao inviteVehicleDao) {
		this.inviteVehicleDao = inviteVehicleDao;
	}
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addPassInviteApply(PassInviteApplyEntity passInviteApplyEntity) {
		return passInviteApplyDao.addPassInviteApply(passInviteApplyEntity);
	}

	/**
	 * 没有地方在用这个接口使用请通知
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 受影响的行数 
	 */
	@Deprecated
	@Transactional
	public int updatePassInviteApply(PassInviteApplyEntity passInviteApplyEntity) {
		return passInviteApplyDao.updatePassInviteApply(passInviteApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return java.util.List
	 * TODO
	 */
	@Transactional(readOnly = true)
	public List<PassInviteApplyEntity> queryPassInviteApplyList(PassInviteApplyEntity passInviteApplyEntity) {
		return passInviteApplyDao.queryPassInviteApplyList(passInviteApplyEntity);
	}
	
	/**
	 * 没有地方在用这个接口使用请通知
	 * @update_author 134019-yuyongxiang
	 * @update_date 2013年7月16日 14:27:15
	 * 
	 * 根据外请车编号查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午2:02:20
	 * @param inviteNo 外请车编号
	 * @return PassInviteApplyEntity
	 */
	@Deprecated
	public PassInviteApplyEntity queryPassInviteApplyListByInviteNo(String inviteNo) {
		PassInviteApplyEntity passInviteApplyEntity = new PassInviteApplyEntity();
		passInviteApplyEntity.setInviteNo(inviteNo);
		List<PassInviteApplyEntity> resultList = queryPassInviteApplyList(passInviteApplyEntity);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		return resultList.get(0);
	}
	
	/**
	 * 未使用的接口要使用请通知
	 * 
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Deprecated
	@Transactional(readOnly = true)
	public List<PassInviteApplyEntity> queryPassInviteApplyForPage(PassInviteApplyEntity passInviteApplyEntity, int start, int pageSize) {
		return passInviteApplyDao.queryPassInviteApplyForPage(passInviteApplyEntity, start, pageSize);
	}
	
	/**
	 * 未使用的接口要使用请通知
	 * 
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 
	 */
	@Deprecated
	@Transactional(readOnly = true)
	public Long queryCount(PassInviteApplyEntity passInviteApplyEntity) {
		return passInviteApplyDao.queryCount(passInviteApplyEntity);
	}

	/**
	 * 根据外请车单号查询(审核受理记录、车辆信息)
	 * @param inviteNo 外请车单号
	 * @return InviteVehicleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午12:20:14
	 */
	@Override
	public InviteVehicleDto queryAuditInviteLogListAndVehicleList(String inviteNo) {
		InviteVehicleDto inviteVehicleDto = new InviteVehicleDto();
		List<AuditInviteApplyEntity> auditInviteVehicleList = auditInviteApplyService.queryAuditInviteApplyListByInviteNo(inviteNo);
		inviteVehicleDto.setAuditInviteVehicleList(auditInviteVehicleList);
		BigDecimal inviteCost = passInviteApplyDao.queryInviteCost(inviteNo);
		inviteVehicleDto.setInviteCost(inviteCost);
		return inviteVehicleDto;
	}
	
	/**
	 * 根据外请车车牌号、用车部门code查询请车价格
	 *
	 * PS:这个地方使用车牌号查询价格所以查出来的是车辆的价格 外请车约车表里面存着一个总价钱
	 * @update_date 2013年7月16日 14:36:33
	 * 
	 * 提供给配载单的接口
	 * 
	 * @return inviteCost
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 上午8:17:25
	 */
	@Override
	public PassInviteApplyEntity queryPassInvitePrice(String vehicleNo, String applyOrgCode) {
		String status = InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE;
		String useStatus = InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED;
		List<PassInviteApplyEntity> passInviteApplyList = passInviteApplyDao.queryPassInvitePrice(vehicleNo, applyOrgCode,status,useStatus);
		if(!CollectionUtils.isEmpty(passInviteApplyList)){
			return passInviteApplyList.get(0);
		}
		return null;
	}
	
	/**
	 * 查询外请车辆信息
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-11 下午4:57:33
	 */
	@Override
	public List<VehicleDriverWithDto> queryPreviewPassInviteVehicleDetail(
			String vehicleNo, String vehicleType, String driverName,
			String driverPhone, boolean isOpenVehicle) {
		List<LeasedVehicleWithDriverDto> resultList = leasedVehicleService.queryLeasedVehicleWithDriverDtosByCondition(vehicleNo, vehicleType, 
				driverName, driverPhone, isOpenVehicle, FossConstants.YES);
		List<VehicleDriverWithDto> driverWithDtoList = new ArrayList<VehicleDriverWithDto>();
		for(int i = 0; i<resultList.size(); i++ ){
			VehicleDriverWithDto vehicleDriverWithDto =  new VehicleDriverWithDto();
			DriverBaseDto driverBaseDto = resultList.get(i).getLeasedDriver();
			VehicleBaseDto vehicleBaseDto = resultList.get(i).getLeasedVehicle();
			vehicleDriverWithDto.setDriverName(driverBaseDto.getDriverName());
			vehicleDriverWithDto.setDriverPhone(driverBaseDto.getDriverPhone());
			vehicleDriverWithDto.setVehicleNo(vehicleBaseDto.getVehicleNo());
			vehicleDriverWithDto.setVehcleLengthCode(vehicleBaseDto.getVehicleLengthCode());
			vehicleDriverWithDto.setVehicleScrapDate(vehicleBaseDto.getEndTime());
			String vehicleLengthName = queryVehicleTypeEntity(vehicleBaseDto.getVehicleLengthCode());
			vehicleDriverWithDto.setVehcleLengthName(vehicleLengthName);
			vehicleDriverWithDto.setDriverCode(driverBaseDto.getDriverIdCard());
			
			driverWithDtoList.add(vehicleDriverWithDto);
		}
		return driverWithDtoList;
	}
	
	
	/**
	 * 根据外请车code查询外请车名称
	 */
	private String queryVehicleTypeEntity(String vehicleLengthCode){
		VehicleTypeEntity vehicleTypeEntity = null;
		try {
			vehicleTypeEntity = leasedVehicleTypeService.queryLeasedVehicleTypeByVehicleLengthCode(vehicleLengthCode);
		} catch (BusinessException e) {
			//352203-sonar
			logger.info("PassInviteApplyService.queryVehicleTypeEntity 报错:" + StringUtils.substring(e.toString(), 0, ConstantsNumberSonar.SONAR_NUMBER_100));
		}
		if(vehicleTypeEntity == null){
			return null;
		}
		return vehicleTypeEntity.getVehicleLengthName();
	}
	
	/**
	 * 根据车牌号查询外请车使用状态
	 * @param vehicleNo
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-12 下午4:32:26
	 */
	@Override
	public List<InviteVehicleEntity> queryInviteUseStatus(String vehicleNo) {
		return inviteVehicleDao.queryInviteUseStatus(vehicleNo);
	}
	
	@Override
	public List<InviteVehicleDto> queryInviteVehicleListByPage(InviteVehicleDto inviteVehicleDto, int start, int limit) {
		
		return passInviteApplyDao.queryInviteVehicleListByPage(inviteVehicleDto, start, limit);
	}

	@Override
	public Long queryInviteVehicleCountByPage(InviteVehicleDto inviteVehicleDto) {
		
		return passInviteApplyDao.queryInviteVehicleCountByPage(inviteVehicleDto);
	}
	@Override
	public int updatePassInviteApplyByInviteNo(PassInviteApplyEntity passInviteApplyEntity){
		if(passInviteApplyEntity==null||
				(passInviteApplyEntity!=null&&StringUtils.isEmpty(passInviteApplyEntity.getInviteNo()))){
			return 0;
		}else{
			return passInviteApplyDao.updatePassInviteApplyByInviteNo(passInviteApplyEntity);
		}
	}
    /**
     * 根据与外请车编号查询信息部名称和编码
     * @author 310248
     */
	@Override
	public OrgEntity queryOrgDtoByApplyByInviteNo(String inviteNo) {
		
		return passInviteApplyDao.queryOrgDtoByApplyByInviteNo(inviteNo);
	}
	
}