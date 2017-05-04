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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/AuditInviteApplyService.java
 * 
 *  FILE NAME     :AuditInviteApplyService.java
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
 * FILE    NAME: AuditInviteApplyService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */

package com.deppon.foss.module.transfer.scheduling.server.service.impl;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTPSService;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.common.api.shared.domain.OrderVehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.TpsAboutVehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.VehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAuditInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IPassInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;

import com.deppon.foss.module.transfer.scheduling.api.shared.vo.PassInviteApplyVo;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**

 * 外请约车审核log service

 * @author 104306-foss-wangLong

 * @date 2012-12-15 下午12:47:47

 */

public class AuditInviteApplyService implements IAuditInviteApplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InviteVehicleService.class);
	/**
	 * 外请约车审核
	 */
	private IAuditInviteApplyDao auditInviteApplyDao;

	/**
	 * 外请约车服务
	 */

	private IInviteVehicleService inviteVehicleService;

	/**
	 * 外请车受理服务
	 */
	private IPassInviteApplyService passInviteApplyService;
	
	/**
	 * 外请车约车
	 */
	private IInviteVehicleDao inviteVehicleDao;

	/**
	 * 注入消息服务
	 */
	private IMessageService messageService;
	
	private IMotorcadeService motorcadeService;

	private IFOSSToTPSService fossToTPSService;
	
	/**
	 * 注入查询车型和净空的接口
	 * */
	private IVehicleService vehicleService;
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	/**
	 * 注入查询车型和净空的接口
	 * */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	* @fields fossToTPSService
	* @author 105869-foss-heyongdong
	* @update 2015年4月9日 上午10:16:52
	* @version V1.0
	*/
	public void setFossToTPSService(IFOSSToTPSService fossToTPSService) {
		this.fossToTPSService = fossToTPSService;
	}

	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity) {
		return auditInviteApplyDao.addAuditInviteApply(auditInviteApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int updateAuditInviteApply(AuditInviteApplyEntity auditInviteApplyEntity) {
		return auditInviteApplyDao.updateAuditInviteApply(auditInviteApplyEntity);
	}

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<AuditInviteApplyEntity> queryAuditInviteApplyList(AuditInviteApplyEntity auditInviteApplyEntity) {
		return auditInviteApplyDao.queryAuditInviteApplyList(auditInviteApplyEntity);
	}

	/**
	 * 根据外请车编号查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param inviteNo 外请车编号
	 * @return java.util.List
	 */
	public List<AuditInviteApplyEntity> queryAuditInviteApplyListByInviteNo(String inviteNo) {
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		auditInviteApplyEntity.setInviteNo(inviteNo);
		return queryAuditInviteApplyList(auditInviteApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<AuditInviteApplyEntity> queryAuditInviteApplyForPage(AuditInviteApplyEntity auditInviteApplyEntity, int start, int pageSize) {
		return auditInviteApplyDao.queryAuditInviteApplyForPage(auditInviteApplyEntity, start, pageSize);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:47:47
	 * @param auditInviteApplyEntity
	 * @return 
	 */
	@Transactional(readOnly = true)
	public Long queryCount(AuditInviteApplyEntity auditInviteApplyEntity) {
		return auditInviteApplyDao.queryCount(auditInviteApplyEntity);
	}
	
	
	/**
	* @description 通过约车编号更新
	* @param auditInviteApplyEntity
	* @return	int
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年3月19日 上午10:52:33
	*/
	public int updateAuditInviteApplyByInvite(AuditInviteApplyEntity auditInviteApplyEntity){
		if(auditInviteApplyEntity==null||
				(auditInviteApplyEntity!=null&&StringUtils.isEmpty(auditInviteApplyEntity.getInviteNo()))){
			return 0;
		}else{
			return auditInviteApplyDao.updateAuditInviteApplyByInvite(auditInviteApplyEntity);
		}
		
	}

	/**
	 * 拒绝审核外请车约车
	 * @param notes 审核结果备注
	 * @param inviteNo 外请车单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午2:34:30
	 */
	public void refuseInviteInviteApplyApply(String inviteNo ,String notes){
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//生成受理序号
		int newInviteNo = getNextInviteInvite(inviteNo);
		//更新审核状态
		inviteVehicleService.updatePassInviteVehicleStatus(inviteNo,InviteVehicleConstants.INVITEVEHICLE_STATUS_REJECT);
		auditInviteApplyEntity.setId(UUIDUtils.getUUID());
		auditInviteApplyEntity.setInviteNo(inviteNo);
		auditInviteApplyEntity.setAuditTime(new Date());
		auditInviteApplyEntity.setAuditNo(newInviteNo);
		auditInviteApplyEntity.setNotes(notes);
		auditInviteApplyEntity.setAuditOrgName(currentInfo.getCurrentDeptName());
		auditInviteApplyEntity.setAuditOrgCode(currentInfo.getCurrentDeptCode());
		auditInviteApplyEntity.setAuditEmpName(currentInfo.getEmpName());
		auditInviteApplyEntity.setAuditEmpCode(currentInfo.getEmpCode());
		auditInviteApplyEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_REJECT);
		auditInviteApplyDao.addAuditInviteApply(auditInviteApplyEntity);
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
		OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_REJECT);
		order.setOrderNumber(inviteNo);
		requestParameter.setRequestEntity(order);
		try {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为拒绝--调用TPS同步外请车约车信息接口开始");
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为拒绝--调用TPS同步外请车约车信息接口结束");
		} catch (Exception e) {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为拒绝，调用TPS接口同步外请车约车信息失败："+e.getStackTrace().toString());
			e.printStackTrace();
		}

	}

	/**
	 * 查询审核外请车约车编号 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午2:07:20
	 */

	@Transactional(readOnly = true)
	public int queryInviteInviteNo(String inviteNo) {
		return auditInviteApplyDao.queryInviteInviteAuditNo(inviteNo);
	}

	/**
	 * 获取下一个受理序号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-7 下午5:22:11
	 * @param borrowNo 审核外请车约车编号
	 */
	private int getNextInviteInvite(String inviteNo) {
		int no = queryInviteInviteNo(inviteNo);
		AtomicInteger atomicNo = new AtomicInteger(no);
		return atomicNo.incrementAndGet();
	}

	/**
	 * 通过审核外请车约车
	 * @param notes 审核备注
	 * @param inviteNo 外请车单号
	 * @param vehicleNo 外请车车牌号
	 * @param perdictArriveTime 预计到达时间
	 * @param inviteCost 请车价格
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午3:50:36
	 */
	@Override
	public void passInviteInviteApplyApply(PassInviteApplyVo passInviteApplyVo) {
		
		//TODO
		BigDecimal inviteCostTotal=BigDecimal.ZERO;
		
		String inviteNo=passInviteApplyVo.getInviteNo();
		String notes=passInviteApplyVo.getNotes();
		String isSaleDepartmentCompany=passInviteApplyVo.getIsSaleDepartmentCompany();
		String carpoolingType=passInviteApplyVo.getCarpoolingType();
		List<PassInviteApplyEntity> passInviteApplyList = passInviteApplyVo.getPassInviteApplyList();
		
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		//尝试找登陆部门所属的派车车队，如果能找到
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(currentDeptCode);
		if(null == org || (!StringUtils.equals(FossConstants.YES, org.getIsTopFleet()) && ! StringUtils.equals(FossConstants.YES, org.getDispatchTeam()))){
			throw new TfrBusinessException("您没有顶级车队或者车队调度组权限，无法审核受理！");
		}
		
		PassInviteApplyEntity passInviteApplyEntity=new PassInviteApplyEntity();
		passInviteApplyEntity.setInviteNo(inviteNo);
		List<PassInviteApplyEntity> oldpassInviteApplyEntity = passInviteApplyService.queryPassInviteApplyList(passInviteApplyEntity);
	
		Date currentDate = new Date();
		int auditNo = getNextInviteInvite(inviteNo);
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String currentDeptName = currentInfo.getCurrentDeptName();
		String empName = currentInfo.getEmpName();
		String empCode = currentInfo.getEmpCode();
		if (CollectionUtils.isEmpty(oldpassInviteApplyEntity)) {
				for(PassInviteApplyEntity passInviteApply : passInviteApplyList){
	
					inviteCostTotal = inviteCostTotal.add(passInviteApply.getInviteCost());
					
					//受理外请车entity
					passInviteApplyEntity = new PassInviteApplyEntity();
					passInviteApplyEntity.setId(UUIDUtils.getUUID());
					passInviteApplyEntity.setInviteNo(inviteNo);
					passInviteApplyEntity.setPerdictArriveTime(passInviteApply.getPerdictArriveTime());
					passInviteApplyEntity.setInviteCost(passInviteApply.getInviteCost());
					passInviteApplyEntity.setVehicleNo(passInviteApply.getVehicleNo());
					passInviteApplyEntity.setAcceptOrgName(currentDeptName);
					passInviteApplyEntity.setAcceptOrgCode(currentDeptCode);
					passInviteApplyEntity.setAcceptEmpName(empName);
					passInviteApplyEntity.setAcceptEmpCode(empCode);
					passInviteApplyEntity.setPassStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
					passInviteApplyEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
					passInviteApplyEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					passInviteApplyEntity.setPassTime(currentDate);
					passInviteApplyEntity.setMinistryinformation(passInviteApply.getMinistryinformation());
					//新增受理外请车信息
					passInviteApplyService.addPassInviteApply(passInviteApplyEntity);
				}
		}else{
			throw new TfrBusinessException("已受理的约车编号,无法再次审核受理！");
			//oldpassInviteApplyEntity.setInviteCost(inviteCost);
			//oldpassInviteApplyEntity.setVehicleNo(vehicleNo);
			//oldpassInviteApplyEntity.setPerdictArriveTime(perdictArriveTime);
			//更新
			//passInviteApplyService.updatePassInviteApply(oldpassInviteApplyEntity);
		}

		//审核外请车entity
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		auditInviteApplyEntity.setId(UUIDUtils.getUUID());
		auditInviteApplyEntity.setInviteNo(inviteNo);
		auditInviteApplyEntity.setAuditTime(currentDate);
		auditInviteApplyEntity.setAuditNo(auditNo);
		auditInviteApplyEntity.setNotes(notes);
		auditInviteApplyEntity.setIsSaleDepartmentCompany(isSaleDepartmentCompany);
		auditInviteApplyEntity.setCarpoolingType(carpoolingType);
		auditInviteApplyEntity.setAuditOrgName(currentDeptName);
		auditInviteApplyEntity.setAuditOrgCode(currentDeptCode);
		auditInviteApplyEntity.setAuditEmpName(empName);
		auditInviteApplyEntity.setAuditEmpCode(empCode);
		auditInviteApplyEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		//新增审核外请车信息
		auditInviteApplyDao.addAuditInviteApply(auditInviteApplyEntity);
		//更新请车状态
		inviteVehicleDao.updateInviteVehicleForTotalInviteCost(inviteNo,inviteCostTotal);
		inviteVehicleService.updatePassInviteVehicleStatus(inviteNo,InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		//反写 约车状态
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
		OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		order.setOrderNumber(inviteNo);
		requestParameter.setRequestEntity(order);
		try {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口开始");
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口结束！");
		} catch (Exception e) {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口失败："+e.getMessage());
			e.printStackTrace();
			//throw new TfrBusinessException("TPS同步约车信息到FOSS成功后反写约车状态为已受理--调用TPS同步外请车约车信息接口失败！"+e.getMessage());
		}
	}

	
	/**
	 * 通过TPS审核外请车约车
	 * @param notes 审核备注
	 * @param inviteNo 外请车单号
	 * @param vehicleNo 外请车车牌号
	 * @param perdictArriveTime 预计到达时间
	 * @param inviteCost 请车价格
	 * @author foss-heyongdong
	 * @date 2015年1月9日 15:49:33
	 */
	@Override
	public void passInviteInviteTPSApply(List<VehicleRequestEntity> vehicleRequestEntitys) {
		
		//TODO
		BigDecimal inviteCostTotal=BigDecimal.ZERO;
		if(CollectionUtils.isEmpty(vehicleRequestEntitys)){
			throw new TfrBusinessException("传递的信息为空！");
		}
		VehicleRequestEntity entity=vehicleRequestEntitys.get(0);
		if(entity==null){
			throw new TfrBusinessException("传递的信息为空！");
		}
		String inviteNo=entity.getInviteNo();
		String notes=null;
		
		PassInviteApplyEntity passInviteApplyEntity=new PassInviteApplyEntity();
		passInviteApplyEntity.setInviteNo(inviteNo);
		List<PassInviteApplyEntity> oldpassInviteApplyEntity = passInviteApplyService.queryPassInviteApplyList(passInviteApplyEntity);
		
		Date currentDate = new Date();
		int auditNo = getNextInviteInvite(inviteNo);
		//审核部门
		String currentDeptName = entity.getAppvoeDeptName();
		//审核部门编码
		String currentDeptCode = entity.getAppvoeDeptCode();
		//审核人
		String empName = entity.getAppvoeUserName();
		//审核人编码
		String empCode = entity.getAppvoeUserCode();
		if (CollectionUtils.isEmpty(oldpassInviteApplyEntity)) {
				for(VehicleRequestEntity passInviteApply : vehicleRequestEntitys){
					
					inviteCostTotal = inviteCostTotal.add(passInviteApply.getInviteCost());
					
					//受理外请车entity
					passInviteApplyEntity = new PassInviteApplyEntity();
					passInviteApplyEntity.setId(UUIDUtils.getUUID());
					passInviteApplyEntity.setInviteNo(inviteNo);
					passInviteApplyEntity.setPerdictArriveTime(passInviteApply.getPlanArriveTime());
					passInviteApplyEntity.setInviteCost(passInviteApply.getInviteCost());
					passInviteApplyEntity.setVehicleNo(passInviteApply.getVehicleNo());
					passInviteApplyEntity.setAcceptOrgName(passInviteApply.getAppvoeDeptName());
					passInviteApplyEntity.setAcceptOrgCode(passInviteApply.getAppvoeDeptCode());
					passInviteApplyEntity.setAcceptEmpName(passInviteApply.getAppvoeUserName());
					passInviteApplyEntity.setAcceptEmpCode(passInviteApply.getAppvoeUserCode());
					passInviteApplyEntity.setPassStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
					passInviteApplyEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
					passInviteApplyEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
					passInviteApplyEntity.setPassTime(currentDate);
					passInviteApplyEntity.setMinistryinformation(TransferConstants.TPS_INFODEPT_NAME);
					//新增受理外请车信息
					passInviteApplyService.addPassInviteApply(passInviteApplyEntity);
				}
			
		}else{
			
			throw new TfrBusinessException("已受理的约车编号,无法再次审核受理！");
			
		}
		//审核外请车entity
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		auditInviteApplyEntity.setId(UUIDUtils.getUUID());
		auditInviteApplyEntity.setInviteNo(inviteNo);
		auditInviteApplyEntity.setAuditTime(currentDate);
		auditInviteApplyEntity.setAuditNo(auditNo);
		auditInviteApplyEntity.setNotes(notes);
		auditInviteApplyEntity.setAuditOrgName(currentDeptName);
		auditInviteApplyEntity.setAuditOrgCode(currentDeptCode);
		auditInviteApplyEntity.setAuditEmpName(empName);
		auditInviteApplyEntity.setAuditEmpCode(empCode);
		auditInviteApplyEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		//新增审核外请车信息
		auditInviteApplyDao.addAuditInviteApply(auditInviteApplyEntity);
		//更新请车状态
		inviteVehicleDao.updateInviteVehicleForTotalInviteCost(inviteNo,inviteCostTotal);
		inviteVehicleService.updatePassInviteVehicleStatus(inviteNo,InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		
		//反写 约车状态
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
		OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		order.setOrderNumber(inviteNo);
		requestParameter.setRequestEntity(order);
		try {
			LOGGER.error("TPS同步约车信息到FOSS成功后反写约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口开始");
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("TPS同步约车信息到FOSS成功后反写约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口结束！");
		} catch (Exception e) {
			LOGGER.error("TPS同步约车信息到FOSS成功后反写约车编号："+inviteNo+"状态为已受理--调用TPS同步外请车约车信息接口失败："+e.getMessage());
			e.printStackTrace();
			//throw new TfrBusinessException("TPS同步约车信息到FOSS成功后反写约车状态为已受理--调用TPS同步外请车约车信息接口失败！"+e.getMessage());
		}
	}

	/**
	 * 退回审核外请车约车申请
	 * @param notes 备注
	 * @param inviteNo 外车单号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午6:25:23
	 */
	@Override
	public void backInviteInviteApplyApply(String notes, String inviteNo,String applyOrgCode) {
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		String currentDeptName = currentInfo.getCurrentDeptName();
		String currentDeptCode = currentInfo.getCurrentDeptCode();
		String empName = currentInfo.getEmpName();
		String empCode = currentInfo.getEmpCode();
		int auditNo = getNextInviteInvite(inviteNo);
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		auditInviteApplyEntity.setId(UUIDUtils.getUUID());
		auditInviteApplyEntity.setInviteNo(inviteNo);
		auditInviteApplyEntity.setAuditTime(new Date());
		auditInviteApplyEntity.setAuditNo(auditNo);
		auditInviteApplyEntity.setNotes(notes);
		auditInviteApplyEntity.setAuditOrgName(currentDeptName);
		auditInviteApplyEntity.setAuditOrgCode(currentDeptCode);
		auditInviteApplyEntity.setAuditEmpName(empName);
		auditInviteApplyEntity.setAuditEmpCode(empCode);
		auditInviteApplyEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_RETURN);
		//新增审核外请车信息
		auditInviteApplyDao.addAuditInviteApply(auditInviteApplyEntity);
		//更新请车状态
		inviteVehicleService.updatePassInviteVehicleStatus(inviteNo,InviteVehicleConstants.INVITEVEHICLE_STATUS_RETURN);
		//反写 约车状态
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
		OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_RETURN);//操作类型
		order.setOrderNumber(inviteNo);//约车编号
		requestParameter.setRequestEntity(order);
		try {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为退回--调用TPS同步外请车约车信息接口开始");
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为退回--调用TPS同步外请车约车信息接口结束！");
		} catch (Exception e) {
			LOGGER.error("FOSS同步约车信息到TPS，约车编号："+inviteNo+"状态为退回--调用TPS同步外请车约车信息接口失败："+e.getMessage());
			e.printStackTrace();
		}
		InstationJobMsgEntity msg = createMsg(applyOrgCode, inviteNo);
		messageService.createBatchInstationMsg(msg);
	}

	/**
	 * 创建消息通知
	 * @author 104306-foss-wangLong
	 * @date 2012-12-21 下午5:04:24
	 */
	private InstationJobMsgEntity createMsg(String orgCode, String orderNo) {

		InstationJobMsgEntity instationMsgEntity = new InstationJobMsgEntity();
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		instationMsgEntity.setId(UUIDUtils.getUUID());
		instationMsgEntity.setSenderCode(currentInfo.getEmpCode());
		instationMsgEntity.setSenderName(currentInfo.getEmpName());
		instationMsgEntity.setSenderOrgCode(currentInfo.getCurrentDeptCode());
		instationMsgEntity.setSenderOrgName(currentInfo.getCurrentDeptName());
		instationMsgEntity.setReceiveOrgCode(orgCode);
		// 消息内容
		String message = "外请车约车申请被退回.约车单号:" + orderNo;
		instationMsgEntity.setContext(message);
		// 消息创建方式
		instationMsgEntity.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
		// 站内消息类型
		instationMsgEntity.setMsgType(DictionaryValueConstants.MSG_TYPE__NORMAL);
		instationMsgEntity.setPostDate(new Date());

		return instationMsgEntity;

	}
	
	/**
	 * 设置auditInviteApplyDao
	 * @param auditInviteApplyDao the auditInviteApplyDao to set
	 */
	public void setAuditInviteApplyDao(IAuditInviteApplyDao auditInviteApplyDao) {
		this.auditInviteApplyDao = auditInviteApplyDao;
	}

	/**
	 * 设置passInviteApplyService
	 * @param passInviteApplyService the passInviteApplyService to set
	 */
	public void setInviteVehicleService(IInviteVehicleService inviteVehicleService) {
		this.inviteVehicleService = inviteVehicleService;
	}

	/**
	 * 设置 外请车受理服务.
	 *
	 * @param passInviteApplyService the new 外请车受理服务
	 */
	public void setPassInviteApplyService(
			IPassInviteApplyService passInviteApplyService) {
		this.passInviteApplyService = passInviteApplyService;
	}

	/**
	 * 设置 注入消息服务.
	 *
	 * @param messageService the new 注入消息服务
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * @return the inviteVehicleDao
	 */
	public IInviteVehicleDao getInviteVehicleDao() {
		return inviteVehicleDao;
	}

	/**
	 * @param inviteVehicleDao the inviteVehicleDao to set
	 */
	public void setInviteVehicleDao(IInviteVehicleDao inviteVehicleDao) {
		this.inviteVehicleDao = inviteVehicleDao;
	}

	@Override
	@Transactional//确保多表数据完整性
	public void tpsAboutVehicleToFoss(
			TpsAboutVehicleRequestEntity entity) {
		if(entity==null){
			throw new TfrBusinessException("传递的信息为空!");
		}
		//审核部门
		String currentDeptName = entity.getApplyOrgName();
		//审核部门编码
		String currentDeptCode = entity.getApplyOrgCode();
		//审核人
		String empName = entity.getApplyEmpName();
		//审核人编码
		String empCode = entity.getApplyEmpCode();
		
		/**必要参数的非空校验
		 * vehicleNo DISPATCH_TRANS_DEPT APPLY_ORG_NAME APPLY_ORG_CODE APPLY_EMP_NAME APPLY_EMP_CODE
		 * INVITE_NO USE_PURPOSE USE_TYPE ORDER_VEHICLE_MODEL PREDICT_USE_TIME USE_ADDRESS
		 * */
		if(StringUtils.isEmpty(entity.getVehicleNo())||StringUtils.isEmpty(entity.getInviteNo())||StringUtils.isEmpty(entity.getDispatchTransDept())||
				StringUtils.isEmpty(entity.getApplyEmpCode())||StringUtils.isEmpty(entity.getApplyEmpName())
				||StringUtils.isEmpty(entity.getUsePurpose())||entity.getPredictUseTime()==null){
			throw new TfrBusinessException("必要参数为空,无法成功生成约车信息！");
		}
		List<InviteVehicleDto> list=inviteVehicleDao.queryInviteVehicleByNo(entity.getInviteNo());
		//确保前后版本兼容(新添加是否更新字段)
		String isUpdateStr=StringUtils.isEmpty(entity.getIsUpdate())?"N":entity.getIsUpdate();
		
		/**接口是否更新标识*/
		boolean isUpdate=true;
		/**不支持更新约车操作直接返回错误信息*/
		if(list!=null&&list.size()>0){
			//支持更新，状态已变更
			if("Y".equalsIgnoreCase(isUpdateStr)&&
					!InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED.equals(list.get(0).getStatus())){
				throw new TfrBusinessException("该约车编号已经在foss系统发生状态变更，不支持重新约车操作！");
			}
			//不支持变更
			if("N".equalsIgnoreCase(isUpdateStr)){
				throw new TfrBusinessException("该约车编号已经在foss系统发生过申请约车！");
			}
		}else{
			//初始插入操作
			isUpdate=false;
		}
		
		InviteVehicleEntity inviteVehicleEntity = new InviteVehicleEntity();
		BeanUtils.copyProperties(entity, inviteVehicleEntity);
		inviteVehicleEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
		inviteVehicleEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		/**
		 * 系统间部分类型参数转化
		 * TPS:4 干线（外请车） 5整车（整车）       
		 * FOSS:外请 INVITE_ORDER  整车 WHOLECAR_ORDER 
		 * 
		 * 到营业部 TO_SALES_DEPARTMENT  到客户处 TO_CLIENT  到中转场 TO_TRANSIT
		 * 
		 * 
		 * */ 
		if("4".equals(entity.getUsePurpose())){
			inviteVehicleEntity.setUsePurpose("INVITE_ORDER");
		}else if("5".equals(entity.getUsePurpose())){
			inviteVehicleEntity.setUsePurpose("WHOLECAR_ORDER");
		}else{
			throw new TfrBusinessException("非法的参数类型:usePurpose!");
		}
		//依据tps传递车牌号 查询车型
		VehicleAssociationDto vehicleAssociation = vehicleService.queryVehicleAssociationDtoByVehicleNo(entity.getVehicleNo());
		if(vehicleAssociation!=null){
			String orderVehicleModel=vehicleAssociation.getVehicleLengthCode();
			inviteVehicleEntity.setOrderVehicleModel(orderVehicleModel!=null?orderVehicleModel:"CX0033");
		}else{
			//对查询不到车型进行默认赋值
			inviteVehicleEntity.setOrderVehicleModel("CX0033");
		}
		//尝试找登陆部门所属的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(entity.getDispatchTransDept());
		if(topFleet!=null){
			inviteVehicleEntity.setTopFleetCode(topFleet.getCode());
			inviteVehicleEntity.setTopFleetName(topFleet.getName());
		}
		
		Date currentDate = new Date();
		int auditNo = isUpdate?0
				:getNextInviteInvite(inviteVehicleEntity.getInviteNo());
		/**
		 * 新增受理外请车信息
		 * */
		//受理外请车entity
		PassInviteApplyEntity passInviteApplyEntity=new PassInviteApplyEntity();
		//针对更新操作不涉及主键初始化问题
		passInviteApplyEntity.setId(isUpdate?null:UUIDUtils.getUUID());
		passInviteApplyEntity.setInviteNo(entity.getInviteNo());
		passInviteApplyEntity.setPerdictArriveTime(entity.getPerdictArriveTime());
		passInviteApplyEntity.setInviteCost(entity.getInviteCost());
		passInviteApplyEntity.setVehicleNo(entity.getVehicleNo());
		passInviteApplyEntity.setAcceptOrgName(currentDeptName);
		passInviteApplyEntity.setAcceptOrgCode(currentDeptCode);
		passInviteApplyEntity.setAcceptEmpName(empName);
		passInviteApplyEntity.setAcceptEmpCode(empCode);
		passInviteApplyEntity.setPassStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		passInviteApplyEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
		passInviteApplyEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		passInviteApplyEntity.setPassTime(currentDate);
		passInviteApplyEntity.setMinistryinformation(TransferConstants.TPS_INFODEPT_NAME);//310248
		passInviteApplyEntity.setApplyPath(entity.getMinistryinformation());
		if("000000".equals(entity.getMinistryinformationcode())){
		passInviteApplyEntity.setMinistryinformationCode("");
		}else{
		passInviteApplyEntity.setMinistryinformationCode(entity.getMinistryinformationcode());
		}//310248
		/**
		 * 新增审核外请车信息
		 * */
		AuditInviteApplyEntity auditInviteApplyEntity = new AuditInviteApplyEntity();
		//针对更新操作不涉及主键初始化问题
		auditInviteApplyEntity.setId(isUpdate?null:UUIDUtils.getUUID());
		auditInviteApplyEntity.setInviteNo(entity.getInviteNo());
		auditInviteApplyEntity.setAuditTime(currentDate);
		auditInviteApplyEntity.setAuditNo(auditNo);
		auditInviteApplyEntity.setNotes(entity.getNotes());
		auditInviteApplyEntity.setIsSaleDepartmentCompany(entity.getIsSaleDepartmentCompany());
		auditInviteApplyEntity.setCarpoolingType(entity.getCarpoolingType());
		auditInviteApplyEntity.setAuditOrgName(currentDeptName);
		auditInviteApplyEntity.setAuditOrgCode(currentDeptCode);
		auditInviteApplyEntity.setAuditEmpName(empName);
		auditInviteApplyEntity.setAuditEmpCode(empCode);
		auditInviteApplyEntity.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED);
		if(isUpdate){
			//根据主键更新(主键来自于查询结果)
			inviteVehicleEntity.setId(list.get(0).getId());
			inviteVehicleDao.updateInviteVehicle(inviteVehicleEntity);
			//新增受理外请车信息
			passInviteApplyService.updatePassInviteApplyByInviteNo(passInviteApplyEntity);
			//新增审核外请车信息
			updateAuditInviteApplyByInvite(auditInviteApplyEntity);
		}else{
			inviteVehicleDao.addInviteVehicle(inviteVehicleEntity);
			//新增受理外请车信息
			passInviteApplyService.addPassInviteApply(passInviteApplyEntity);
			//新增审核外请车信息
			addAuditInviteApply(auditInviteApplyEntity);
		}
	}


}