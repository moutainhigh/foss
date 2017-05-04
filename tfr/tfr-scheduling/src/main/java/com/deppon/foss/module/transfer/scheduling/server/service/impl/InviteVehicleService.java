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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/InviteVehicleService.java
 * 
 *  FILE NAME     :InviteVehicleService.java
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
 * FILE    NAME: InviteVehicleService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesExpenseMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IinviteFossVehicleCostService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesExpenseMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.server.service.IFOSSToTPSService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrSerialNumberRuleEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.OrderVehicleRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.StartArriveRequestEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.StowageBillEntity;

import com.deppon.foss.module.transfer.scheduling.api.define.InviteVehicleConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAuditInviteApplyService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AuditInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.VehicleDriverWithDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.InviteVehicleException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外请车Service
 * @author 104306-foss-wangLong
 * @date 2012-12-4 下午5:25:01
 */
public class InviteVehicleService implements IInviteVehicleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InviteVehicleService.class);
	
	private IInviteVehicleDao inviteVehicleDao;
	
	private ITfrCommonService tfrCommonService;
	
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private IFOSSToTPSService fossToTPSService;
	
	/** 外请车约车受理log  */
	private IAuditInviteApplyService auditInviteApplyService;
	
	/** 车辆信息  */
	private IVehicleService vehicleService;
	
	/** 人员信息  */
	private IEmployeeService employeeService;
	/** 外请车信息Service */
	
	/** 外请车信息Service */
	private ILeasedVehicleService leasedVehicleService;
	
	/** 外请车线路信息  */
	private ILineService lineService;
	
	/** 车队Service  */
	private IMotorcadeService motorcadeService;
	
	/** 查询费用承担部门  */
	private ISalesExpenseMappingService salesExpenseMappingService;
	
	/** 外请车单票费用service */
	private IinviteFossVehicleCostService inviteFossVehicleCostService; 
	
	
	

	/**
	 * 根据外请车编号查询 请车价格 </br>
	 * 约车申请不存在 抛出异常{@link InviteVehicleException}
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午2:32:17
	 * @param inviteNo 外请车编号
	 * @return 外请车价格
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	@Transactional(readOnly = true)
	public BigDecimal queryInviteCostByInviteNo(String inviteNo) 
			throws InviteVehicleException {
		if (StringUtil.isBlank(inviteNo)) {
			throw new ParameterException(InviteVehicleConstants.INVITEVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"参数错误."});
		}
		InviteVehicleDto inviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		if (inviteVehicleDto == null) {
			throw new InviteVehicleException("约车申请不存在.编号:" + inviteNo);
		}
		return inviteVehicleDto.getInviteCost();
	}

	/**
	 * 检查是否有此外请车编号</br>
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午4:48:45
	 * @param inviteNo	外请车编号
	 * @param deptCode	部门编码
	 * @return 外请车申请状态
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	@Transactional(readOnly = true)
	public String checkInviteNoIsExists(String inviteNo, String deptCode) 
			throws InviteVehicleException {
		if (StringUtil.isBlank(inviteNo)) {
			throw new ParameterException(InviteVehicleConstants.INVITEVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"参数错误."});
		}
		InviteVehicleDto inviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		// 单号不存在
		if (inviteVehicleDto == null) {
			throw new InviteVehicleException("无此约车编号!");
		}
		// 未受理
		if (StringUtil.equals(inviteVehicleDto.getStatus(), InviteVehicleConstants.INVITEVEHICLE_STATUS_UNCOMMITTED)) {
			throw new InviteVehicleException("约车申请未受理!");
		}
		// 约车拒绝
		if (StringUtil.equals(inviteVehicleDto.getStatus(), InviteVehicleConstants.INVITEVEHICLE_STATUS_REJECT)) {
			List<AuditInviteApplyEntity> auditInviteList = auditInviteApplyService.queryAuditInviteApplyListByInviteNo(inviteNo);
			String failMessage = StringUtil.EMPTY_STRING;
			if (!CollectionUtils.isEmpty(auditInviteList)) {
				failMessage = auditInviteList.get(auditInviteList.size() - 1).getNotes();
			}
			throw new InviteVehicleException("约车失败,失败原因:" + failMessage);
		}
		// 是否完成开单
		String isFinishBill = inviteVehicleDto.getIsFinishBill();
		if (StringUtil.equals(FossConstants.YES, isFinishBill)) {
			throw new InviteVehicleException("约车编号已经导入过");
		}
		// 用车部门
		String applyOrgCode = inviteVehicleDto.getApplyOrgCode();
		if (!StringUtil.equals(deptCode, applyOrgCode)) {
			throw new InviteVehicleException("不能导入其他部门整车约车编号");
		}
		return inviteVehicleDto.getStatus();
	}

	/**
	 * 运单开单完成之后，更新状态</br>
	 * 标识此约车已经完成开单，重复更新抛出异常
	 * @author 104306-foss-wangLong
	 * @date 2012-12-4 下午5:23:02
	 * @param inviteNo	      外请车编号
	 * @param billStatus  开单状态
	 * @throws InviteVehicleException
	 * @throws ParameterException
	 */
	@Transactional
	public void updateInviteVehicleForFinishBill(String inviteNo) 
			throws InviteVehicleException {
		if (StringUtil.isBlank(inviteNo)) {
			throw new ParameterException(InviteVehicleConstants.INVITEVEHICLE_PARAMETERERROR_MESSAGE, new String[]{"参数错误."});
		}
		InviteVehicleDto inviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		// 单号不存在
		if (inviteVehicleDto == null) {
			throw new InviteVehicleException("无此约车编号!");
		}
		if (StringUtil.equals(inviteVehicleDto.getIsFinishBill(), FossConstants.YES)) {
			throw new InviteVehicleException("不能重复导入!");
		}
		inviteVehicleDao.updateInviteVehicleForFinishBill(inviteNo, FossConstants.YES);
	}
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return 受影响的行数 
	 */
	@Transactional
	public int addInviteVehicle(InviteVehicleEntity inviteVehicleEntity) {
		inviteVehicleEntity.setApplyTime(new Date());
		inviteVehicleEntity.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
		checkParameters(inviteVehicleEntity);
		String inviteNo = tfrCommonService.generateSerialNumberRequireNew(TfrSerialNumberRuleEnum.WQCYC, "");
		inviteVehicleEntity.setInviteNo(inviteNo);
		
		/**
		 * 调用TPS接口同步外请车约车信息
		 */
		/*
		 * 269701--2016/01/26--begin
		 * FOSS端只进行同城约车，干线约车和整车约车移至TPS系统进行；故FOSS约车无需同步给TPS
		 * 
		 * LOGGER.error("调用TPS接口同步外请车约车编号："+inviteNo+"信息开始！");
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
			//同步约车到TPS
		 	OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		 	order.setOperateType(inviteVehicleEntity.getStatus());
		 	order.setOrderNumber(inviteVehicleEntity.getInviteNo());
		 	order.setOrderType(inviteVehicleEntity.getUsePurpose());
		 	//269701--foss--lln 2015-09-10 begin
		 	//用车部门code
			order.setApplyOrgCode(inviteVehicleEntity.getApplyOrgCode());
			//用车部门name
			order.setApplyOrgName(inviteVehicleEntity.getApplyOrgName());
		 	//269701--foss--lln 2015-09-10 end
		 	requestParameter.setRequestEntity(order);
	 
		try {
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("调用TPS接口同步外请车约车编号："+inviteNo+"信息结束！");
		} catch (Exception e) {
			LOGGER.error("调用TPS接口同步外请车约车编号："+inviteNo+"信息失败："+e.getStackTrace().toString());
			e.printStackTrace();
		}*/
		
		return inviteVehicleDao.addInviteVehicle(inviteVehicleEntity);
	}

	/**
	 * 参数检查
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 上午8:49:28
	 * @param inviteVehicleEntity 
	 */
	private void checkParameters(InviteVehicleEntity inviteVehicleEntity) {
		if (inviteVehicleEntity == null) {
			throw new InviteVehicleException("参数错误.");
		}
		Date predictUseTime  = inviteVehicleEntity.getPredictUseTime();
		Date applyTime  = inviteVehicleEntity.getApplyTime();
		int compareToResult = applyTime.compareTo(predictUseTime);
		if (compareToResult >= 0) {
			throw new InviteVehicleException("错误：用车时间不能小于等于创建时间.","");
		}
		// 转换秒
		long seconds = (predictUseTime.getTime() - applyTime.getTime()) / ConstantsNumberSonar.SONAR_NUMBER_1000;
		// 分
		long differMinute = seconds / ConstantsNumberSonar.SONAR_NUMBER_60;
		int minute = ConstantsNumberSonar.SONAR_NUMBER_45;
		if (differMinute < minute) {
			throw new InviteVehicleException("错误：申请时间与用车时间必须间隔45分钟及以上.","");
		}
		
		/** 同城外租车 费用承担部门校验 -332219 **/
		if(inviteVehicleEntity.getUsePurpose()!=null || inviteVehicleEntity.getUsePurpose()!=""){
			//使用类型为同城外租车
			if(StringUtils.equals(inviteVehicleEntity.getUsePurpose(), InviteVehicleConstants.RENTALMARK_SAMECITYORDER)){
				//费用承担部门
				String bearFeesDeptName = inviteVehicleEntity.getBearFeesDeptName();
				if(bearFeesDeptName == null || bearFeesDeptName == ""){
					throw new InviteVehicleException("错误：费用承担部门不能为空.","");
				}
			}
		}
	}
	
	/**
	 * 根据部门查询外请车单票费用
	 * @author 332219-foss
	 * @date 2016-03-25 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return 费用 
	 */
	public double queryVehicleCost(String orgCode){
		if (orgCode == null || orgCode == "") {
			throw new InviteVehicleException("参数错误.");
		}
		//大区code
		String bigCode = null;
		//根据当前部门code查询当前部门
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
		
		//若为枢纽中心或车队则不需查询大区
		if(StringUtils.equals(orgAdministrativeInfoEntity.getDivision(), FossConstants.YES) || StringUtils.equals(orgAdministrativeInfoEntity.getBigRegion(), FossConstants.YES) ||
		   StringUtils.equals(orgAdministrativeInfoEntity.getTransDepartment(), FossConstants.YES) || StringUtils.equals(orgAdministrativeInfoEntity.getTransTeam(), FossConstants.YES)){
			//则赋值当前部门
			bigCode = orgCode;
		}else{
			//找出大区
			for(int i=0 ; i<6 ; i++){
				//是否为营业大区
				String bigRegion = orgAdministrativeInfoEntity.getBigRegion();
				//组织标杆编码
				String unifiedCode = orgAdministrativeInfoEntity.getParentOrgUnifiedCode();
				if(StringUtils.equals(FossConstants.YES, bigRegion)){
					//当前部门是大区
					bigCode = orgAdministrativeInfoEntity.getCode();
					//如果有值，则跳出循环
					break;
				}else{
					//当前部门不是大区
					orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(unifiedCode);
				}
			}
		}
		//根据大区编码查询单票费用
		if(bigCode != null){
			InviteFossVehicleCostEntity vehicleCostEntity = inviteFossVehicleCostService.queryVehicleDrivingByRegionalCode(bigCode); 
			if(vehicleCostEntity != null){
				//获取单票费用
				return vehicleCostEntity.getVehicleCostMax();
			}
		}
		return 0;
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return 受影响的行数 
	 */
	public int updateInviteVehicle(InviteVehicleEntity inviteVehicleEntity) {
		checkParameters(inviteVehicleEntity);
		
		/**
		 * 调用TPS接口同步外请车约车信息
		 */
		/*
		 * 269701--2016/01/26--begin
		 * FOSS端只进行 同城外租车约车 干线约车和整车约车移至到TPS系统
		 * 
		 * LOGGER.error("调用TPS接口同步外请车约车编号："+inviteVehicleEntity.getInviteNo()+"信息开始！");
		RequestParameterEntity requestParameter =  new RequestParameterEntity();
		
		 	//同步约车到TPS
		 	OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
		 	order.setOperateType(inviteVehicleEntity.getStatus());
		 	order.setOrderNumber(inviteVehicleEntity.getInviteNo());
		 	order.setOrderType(inviteVehicleEntity.getUsePurpose());
		 	//269701--foss--lln 2015-09-10 begin
		 	//用车部门code
			order.setApplyOrgCode(inviteVehicleEntity.getApplyOrgCode());
			//用车部门name
			order.setApplyOrgName(inviteVehicleEntity.getApplyOrgName());
		 	//269701--foss--lln 2015-09-10 end
		 	requestParameter.setRequestEntity(order);
		 
		
		
		try {
			fossToTPSService.updateOrderVehicleInfo(requestParameter);
			LOGGER.error("调用TPS接口同步外请车约车编号："+inviteVehicleEntity.getInviteNo()+"信息结束！");
		} catch (Exception e) {
			LOGGER.error("调用TPS接口同步外请车约车编号："+inviteVehicleEntity.getInviteNo()+"信息失败："+e.getStackTrace().toString());
			e.printStackTrace();
			//throw new TfrBusinessException("foss更新外请车约车信息同步TPS失败！");
		}
		*/
		return inviteVehicleDao.updateInviteVehicle(inviteVehicleEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<InviteVehicleDto> queryInviteVehicleList(InviteVehicleDto inviteVehicleDto) {
		return inviteVehicleDao.queryInviteVehicleList(inviteVehicleDto);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @param start   
	 * @param pageSize
	 * @return java.util.List
	 */
	@Transactional(readOnly = true)
	public List<InviteVehicleDto> queryInviteVehicleForPage(InviteVehicleDto inviteVehicleDto, int start, int pageSize) {
		
//		将登陆人作为申请人
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		if (currentDept == null) {
			currentDept = new OrgAdministrativeInfoEntity();
		}
		
		//尝试找登陆部门所属的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currentDept.getCode());
		if(null == topFleet){
			inviteVehicleDto.setTopFleetCode("");
		}else{
			inviteVehicleDto.setTopFleetCode(topFleet.getCode());
		}

		// 设置查询参数
		List<String> bizTypesList = new ArrayList<String>();
		// 外场类型
		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		// 营业部（派送部）
		bizTypesList.add(BizTypeConstants.ORG_SALES_DEPARTMENT);
		// 查询上级部门或返回当前用户的部门(营业部返回当前部门)
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentDept.getCode(), bizTypesList);
		if(null !=orgAdministrativeInfoEntity){
			inviteVehicleDto.setCurrentTransferCenterCode(orgAdministrativeInfoEntity.getCode());
		}else{
			inviteVehicleDto.setCurrentTransferCenterCode("");
		}
		inviteVehicleDto.setCurrentDeptCode(currentDept.getCode());
		
		List<InviteVehicleDto> ivList= inviteVehicleDao.queryInviteVehicleForPage(inviteVehicleDto, start, pageSize);
		bizTypesList.clear();
		// 查询车队类型(顶级车队)
		bizTypesList.add(BizTypeConstants.ORG_TRANS_DEPARTMENT);
		//遍历查出来的
		for(InviteVehicleDto iDto : ivList ){
			//判定两个顶级车队的CODE是否相同如果相同则不用查询直接赋值如果不相同则查询
			if(null != topFleet && StringUtils.equals(topFleet.getCode(), iDto.getTopFleetCode())){
				iDto.setTopFleetName(topFleet.getName());
			}else{
				orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(iDto.getTopFleetCode(), bizTypesList);
				if(null !=orgAdministrativeInfoEntity){
					iDto.setTopFleetName(orgAdministrativeInfoEntity.getName());
				}
			}
			
			//直接赋值状态
			if(StringUtils.isNotBlank(iDto.getUseStatus())){
				//String[] 
				String[] useStatusArray = iDto.getUseStatus().split(",");
				for(String useStatus : useStatusArray){
					if(StringUtils.equals(useStatus, InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED)){
						iDto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED_NAME);
						break;
					}
					iDto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_USING_NAME);
				}
			}
		}
		
		return ivList;
	}
	
	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @return 
	 */
	@Transactional(readOnly = true)
	public Long queryCount(InviteVehicleDto inviteVehicleDto) {
//		将登陆人作为申请人
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		if (currentDept == null) {
			currentDept = new OrgAdministrativeInfoEntity();
		}
		
		inviteVehicleDto.setCurrentDeptCode(currentDept.getCode());
		//尝试找登陆部门所属的派车车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currentDept.getCode());
		if(null == topFleet){
			inviteVehicleDto.setTopFleetCode("");
		}else{
			inviteVehicleDto.setTopFleetCode(topFleet.getCode());
		}
		
		return inviteVehicleDao.queryCount(inviteVehicleDto);
	}
	
	/**
	 * 保存外请约车申请
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午2:24:20
	 * @param inviteVehicleDto 
	 */
	@Transactional
	public InviteVehicleDto saveInviteVehicleApply(InviteVehicleDto inviteVehicleDto) {
	   if (inviteVehicleDto == null) {
		  throw new InviteVehicleException("参数错误.");
	   }
		
	   if(null == inviteVehicleDto.getBearFeesDeptName()||"".equals(inviteVehicleDto.getBearFeesDeptName())){
		   inviteVehicleDto.setBearFeesDeptName(inviteVehicleDto.getBearFeesDeptName1());
		   inviteVehicleDto.setBearFeesDeptName1(null);
	   }
	   
	   if(null == inviteVehicleDto.getBearFeesDeptName()||"".equals(inviteVehicleDto.getBearFeesDeptName())){
		   inviteVehicleDto.setBearFeesDeptName(inviteVehicleDto.getBearFeesDeptName2());
		   inviteVehicleDto.setBearFeesDeptName2(null);
	   }
	
		//尝试找所选择的派车车队的顶级车队，如果能找到
		OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(inviteVehicleDto.getDispatchTransDept());
		if(null == topFleet){
			throw new InviteVehicleException("您选择的派车车队未配置其对应的顶级车队，请配置后再尝试！");
		}else{
			inviteVehicleDto.setTopFleetCode(topFleet.getCode());
		}
		
		InviteVehicleEntity inviteVehicleEntity = new InviteVehicleEntity();
		BeanUtils.copyProperties(inviteVehicleDto, inviteVehicleEntity);
		
		/**为接送货是才  校验外请车费用  -332219*/
		if(StringUtils.equals(inviteVehicleEntity.getBusinessType(), InviteVehicleConstants.RENTALMARK_PICKDELIVERY)){
			//外请车费用
			BigDecimal applyFees = inviteVehicleEntity.getApplyFees();
			//部门的外请车费用标准
			double vehicleCost = queryVehicleCost(inviteVehicleEntity.getApplyOrgCode());
			//转换类型
			BigDecimal cost = new BigDecimal(vehicleCost);
			//保留一位小数
			BigDecimal mumber = cost.setScale(1,BigDecimal.ROUND_HALF_EVEN);
			//比较大小
			if(applyFees.compareTo(mumber) == 1){
				inviteVehicleDto.setVehicleCost(""+mumber);
			}
		}
		
		//269701--foss--lln 2015-08-13 begin
		//如果到达地址是省市区联动或者详细地址 不为空，说明是整车约车
		if(StringUtils.isNotEmpty(inviteVehicleEntity.getDetailAdress())){
			inviteVehicleEntity.setArrivedAddress(inviteVehicleEntity.getProvinceName()+inviteVehicleEntity.getCityName()+
					inviteVehicleEntity.getAreaName()+inviteVehicleEntity.getDetailAdress());
		}
		// 设置申请人 所在部门属性
		EmployeeEntity employeeEntity = null;
		if (StringUtil.isNotBlank(inviteVehicleEntity.getApplyEmpCode())) {
			employeeEntity = employeeService.queryEmployeeByEmpCode(inviteVehicleEntity.getApplyEmpCode());
		}
		if (employeeEntity != null && employeeEntity.getDepartment() != null) {
			inviteVehicleEntity.setApplyEmpOrgName(employeeEntity.getDepartment().getName());
		}
		//顶级车队名称
		inviteVehicleEntity.setTopFleetName(topFleet.getName());
		//269701--foss--lln 2015-08-13 end
		// 参数检查
		if (StringUtil.isBlank(inviteVehicleEntity.getId())) {
			addInviteVehicle(inviteVehicleEntity);
		} else {
			updateInviteVehicle(inviteVehicleEntity);
		}
		
		BeanUtils.copyProperties(inviteVehicleEntity, inviteVehicleDto);
		
		return inviteVehicleDto;
	}
	
	/**
	 * 查询外请约车明细
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:43:29
	 * @param inviteNo 外请约车编号
	 * @return {@link InviteVehicleDto}
	 */
	@Transactional(readOnly = true)
	public InviteVehicleDto queryInviteVehicleApplyDetail(String inviteNo) {
		InviteVehicleDto queryInviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		if (queryInviteVehicleDto == null) {
			return null;
		}
		// 申请人 所在部门属性
		EmployeeEntity employeeEntity = null;
		if (StringUtil.isNotBlank(queryInviteVehicleDto.getApplyEmpCode())) {
			employeeEntity = employeeService.queryEmployeeByEmpCode(queryInviteVehicleDto.getApplyEmpCode());
		}
		if (employeeEntity != null && employeeEntity.getDepartment() != null) {
			queryInviteVehicleDto.setApplyEmpOrgName(employeeEntity.getDepartment().getName());
		}
		// 受理log
		List<AuditInviteApplyEntity> auditInviteVehicleList = auditInviteApplyService.queryAuditInviteApplyListByInviteNo(inviteNo);
		queryInviteVehicleDto.setAuditInviteVehicleList(auditInviteVehicleList);
		// 查询车辆和 司机信息
		VehicleDriverWithDto vehicleDriverWithDto = queryVehicleDriverWithInfo(queryInviteVehicleDto.getVehicleNo());
		// 外请车价格
		if (vehicleDriverWithDto != null) {
			vehicleDriverWithDto.setInviteCost(queryInviteVehicleDto.getInviteCost());
			vehicleDriverWithDto.setPredictArriveTime(queryInviteVehicleDto.getPredictArriveTime());
		}
		queryInviteVehicleDto.setVehicleDriverWithDto(vehicleDriverWithDto);
		
		//如果该约车已经被使用则显示使用它的配载单号
		if(StringUtils.equals(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_USING, queryInviteVehicleDto.getUseStatus())){
			String vehicleassembleNo = inviteVehicleDao.queryVehicleassemblebillByInviteNo(inviteNo);
			queryInviteVehicleDto.setVehicleassembleNo(vehicleassembleNo);
		}
		
		/**业务类型赋值 - 332219*/
		if(queryInviteVehicleDto.getBusinessType()!=null || queryInviteVehicleDto.getBusinessType() != ""){
			//业务类型为接送货
			if(StringUtils.equals(queryInviteVehicleDto.getBusinessType(), InviteVehicleConstants.RENTALMARK_PICKDELIVERY)){
				queryInviteVehicleDto.setBusinessType( InviteVehicleConstants.RENTALMARK_pickdelivery);
			}
			//业务类型为转货
			if(StringUtils.equals(queryInviteVehicleDto.getBusinessType(), InviteVehicleConstants.RENTALMARK_TRANSFER)){
				queryInviteVehicleDto.setBusinessType( InviteVehicleConstants.RENTALMARK_transfer);
			}
			//业务类型为大客户接货
			if(StringUtils.equals(queryInviteVehicleDto.getBusinessType(), InviteVehicleConstants.RENTALMARK_BIGCUSTOMER)){
				queryInviteVehicleDto.setBusinessType( InviteVehicleConstants.RENTALMARK_bigcustomer);
			}
			//业务类型快递
			if(StringUtils.equals(queryInviteVehicleDto.getBusinessType(), InviteVehicleConstants.RENTALMARK_EXPRESSAGE)){
				queryInviteVehicleDto.setBusinessType( InviteVehicleConstants.RENTALMARK_expressage);
			}
		}
		
		//直接赋值状态
		if(StringUtils.isNotBlank(queryInviteVehicleDto.getUseStatus())){
			//String[] 
			String[] useStatusArray = queryInviteVehicleDto.getUseStatus().split(",");
			for(String useStatus : useStatusArray){
				if(StringUtils.equals(useStatus, InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED)){
					queryInviteVehicleDto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED_NAME);
					break;
				}
				queryInviteVehicleDto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_USING_NAME);
			}
		}
		
		
		return queryInviteVehicleDto;
	}

	/**
	 * 根据外请车编号List查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午3:35:15
	 * @param inviteNoList 外请车编号list
	 * @return {@link java.util.List<InviteVehicleDto>}
	 */
	@Transactional(readOnly = true)
	public List<InviteVehicleDto> queryInviteVehicleByInviteNoList(List<String> inviteNoList) {
		if (CollectionUtils.isEmpty(inviteNoList)) {
			return null;
		}
		InviteVehicleDto inviteVehicleDto = new InviteVehicleDto();
		inviteVehicleDto.setInviteNoList(inviteNoList);
		
		return inviteVehicleDao.queryAuditInviteVehicleList(inviteVehicleDto);
	}
	
	/**
	 * 根据外请车编号查询对象
	 * @author 104306-foss-wangLong
	 * @date 2012-12-17 下午3:35:15
	 * @param inviteNo 外请车编号
	 * @return InviteVehicleDto
	 */
	@Transactional(readOnly = true)
	public InviteVehicleDto queryInviteVehicleByInviteNo(String inviteNo) {
		if (StringUtil.isBlank(inviteNo)) {
			throw new InviteVehicleException("外请车约车单号为空.");
		}
		List<String> inviteNoList = new ArrayList<String>();
		inviteNoList.add(inviteNo);
		List<InviteVehicleDto> resultList = queryInviteVehicleByInviteNoList(inviteNoList);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		return resultList.get(0);
	}
	
	/**
	 * 查询营业部或外场地址
	 * @author 104306-foss-wangLong
	 * @date 2012-12-18 下午1:14:34
	 * @param useType		用车类型
	 * @param applyOrgCode	部门编码
	 * @return 地址
	 */
	@Transactional(readOnly = true)
	public String querySalesDepartmentAddressOrTransitAddress(String useType, String applyOrgCode) {
		if (StringUtil.isBlank(useType)) {
			return StringUtil.EMPTY_STRING;
		}
		if (StringUtil.isBlank(applyOrgCode)) {
			applyOrgCode = FossUserContext.getCurrentDeptCode();
		}
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
		// 到营业部
		if (InviteVehicleConstants.INVITEVEHICLE_USEVEHICLE_TYPE_TO_SALES_DEPARTMENT.equals(useType)) {
			orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(applyOrgCode);
		}
		// 到中转场
		if (InviteVehicleConstants.INVITEVEHICLE_USEVEHICLE_TYPE_TO_TRANSIT.equals(useType)) {
			orgAdministrativeInfoEntity = getTransferCenter(applyOrgCode);
		}
		if (orgAdministrativeInfoEntity != null) {
			return orgAdministrativeInfoEntity.getAddress();
		}
		return StringUtil.EMPTY_STRING;
	}
	
	/**
	 * 获取当前部门对应的外场
	 * @author 104306-foss-wangLong
	 * @date 2012-11-28 下午6:30:53
	 */
	@Transactional(readOnly = true)
	private OrgAdministrativeInfoEntity getTransferCenter(String deptCode) {
		List<String> bizTypes = new ArrayList<String>();
		// 外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = null;
		try {
			orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(deptCode, bizTypes);
		} catch (Exception e) {
			LOGGER.info("获取部门对应的外场出错，错误信息:" + e.getMessage());
			throw new InviteVehicleException("调用综合接口获取部门对应的外场出错 , 错误信息：" + e.getMessage());
		}
		if (orgAdministrativeInfoEntity == null ) {
			LOGGER.info("获取部门对应的外场出错，返回空, 部门编码 ：" + deptCode);
			return null;
		}
		LOGGER.info("获取部门对应的外场. 部门编码: " + deptCode + ",综合接口返回  外场名称:" + orgAdministrativeInfoEntity.getName() + ", 外场编码:" + orgAdministrativeInfoEntity.getCode());
		return orgAdministrativeInfoEntity;
	}

	
	/**
	 * 撤销外请约车
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteNoList 约车编号list
	 */
	@Transactional
	public void doUndoInviteVehicleApply(final List<String> inviteNoList) {
		checkInviteVehicleApplyStatus(inviteNoList, new CheckStatusCallback<Object>() {
			public Object doCheck(List<InviteVehicleDto> list) {
				for (InviteVehicleDto inviteVehicleDto : list) {
					String status = inviteVehicleDto.getStatus();
					// 退回 ， 未审核，暂存
					if (StringUtil.isBlank(status) || 
							!(StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_RETURN)||
							 StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_UNCOMMITTED)||
							 StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_STAGING))) {
						throw new InviteVehicleException("状态错误.未审核,暂存,退回状态的才能撤销,外请车编号:" + inviteVehicleDto.getInviteNo());
					}
				}
				return null;
			}
		});
		updateInviteVehicleApplyStatus(inviteNoList, InviteVehicleConstants.INVITEVEHICLE_STATUS_UNDO);
		for(String inviteNo : inviteNoList){
			RequestParameterEntity requestParameter =  new RequestParameterEntity();
			OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
			order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_UNDO);
			order.setOrderNumber(inviteNo);
			requestParameter.setRequestEntity(order);
			try {
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为撤销--调用TPS同步外请车约车信息接口开始");
				fossToTPSService.updateOrderVehicleInfo(requestParameter);
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为撤销--调用TPS同步外请车约车信息接口结束！");
			} catch (Exception e) {
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为撤销--调用TPS同步外请车约车信息接口失败："+e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 报到
	 * TPS约车审数据 进行报到
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteVehicleDto
	 */
	public void doVerifyArriveInviteVehicleApply(InviteVehicleDto inviteVehicleDto) {
		if (inviteVehicleDto == null || StringUtil.isEmpty(inviteVehicleDto.getInviteNo())) {
			throw new InviteVehicleException("参数错误");
		}
		InviteVehicleDto inviteVehicle = queryInviteVehicleByInviteNo(inviteVehicleDto.getInviteNo());
		if (inviteVehicle == null) {
			throw new InviteVehicleException("外请约车申请不存在, 外请车编号:" + inviteVehicleDto.getInviteNo());
		}
		String status = inviteVehicle.getStatus();
		// 已受理
		if (StringUtil.isBlank(status) || 
				!(StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED))) {
			throw new InviteVehicleException("没有达到已受理状态,无法报到,外请车编号:" + inviteVehicleDto.getInviteNo());
		}
		//269701--2016-03-22 begin
		//FOSS干线约车和整车约车以及受理转移到TPS进行 并且,TPS车辆信息资料与FOSS车辆信息库不一致 此处需要加判断限制
		//此处追加以下判断校验：
		//该车牌号是否存在 不存在抛出信息
		//如果是干线约车 和整车约车 进行以下判断处理，否则不做处理
		if(!(InviteVehicleConstants.INVITEVEHICLE_USEPURPOSE.equals(inviteVehicleDto.getUsePurpose()))){
			// 根据车牌号查询车辆信息
			VehicleAssociationDto  dto = vehicleService.queryVehicleAssociationDtoByVehicleNo(inviteVehicleDto.getVehicleNo());
			// 通过是否存在直属部门来判断是否外请车
			if(dto==null){
					throw new InviteVehicleException("该车在FOSS中不存在，请在FOSS中录入车辆基础资料;车牌号："+inviteVehicleDto.getVehicleNo()+"; 约车编号：" + inviteVehicleDto.getInviteNo());
			 }else{
					if(StringUtils.isEmpty(dto.getVehicleLengthCode())){
						 throw new InviteVehicleException("请在FOSS中录入该车辆(车牌号："+inviteVehicleDto.getVehicleNo()+")基础资料中对应的车型编码！"+" 约车编号：" + inviteVehicleDto.getInviteNo());
					 }
				//269701-2016-03-04 修改车型
				//由于TPS车型同FOSS车型不一致,如果改该车牌号存在于FOSS基础车辆中，那么修改TPS约车以及审核的车型，按照FOSS车型；
				inviteVehicle.setOrderVehicleModel(dto.getVehicleLengthCode());
				}
		}
		//269701--2016-03-22 end
		
		// 判断此外请车的
		
		inviteVehicle.setIsReturn(inviteVehicleDto.getIsReturn());
		inviteVehicle.setReturnCost(inviteVehicleDto.getReturnCost());
		inviteVehicle.setIsContractVehicle(inviteVehicleDto.getIsContractVehicle());
		inviteVehicle.setContractLineCode(inviteVehicleDto.getContractLineCode());
		inviteVehicle.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);
		inviteVehicle.setArrivalTime(new Date());
		
		updateInviteVehicle(inviteVehicle);
		
		
		//TPS同步已受理约车信息的 报到状态同步操作
		RequestParameterEntity requestParameter = new RequestParameterEntity();
		List<StartArriveRequestEntity> requestEntityList = new ArrayList<StartArriveRequestEntity>();
		StartArriveRequestEntity requestEntity = new StartArriveRequestEntity();
		requestEntity.setOperateType("report");//tps 的报到是 start
		requestEntity.setFossAboutCode(inviteVehicle.getInviteNo());
		List<StowageBillEntity> stowageBills = new ArrayList<StowageBillEntity>();
		StowageBillEntity stowageBill = new StowageBillEntity();
		stowageBill.setVehicleNo(inviteVehicle.getVehicleNo());
		//添加实体
		stowageBills.add(stowageBill);
		requestEntity.setStowageBillList(stowageBills);
		
		requestEntityList.add(requestEntity);
		
		requestParameter.setRequestEntity(requestEntityList);
		try {
			LOGGER.info("FOSS同步已受理约车信息到TPS，约车状态为报到--调用TPS同步外请车约车信息接口开始");
			/*ResponseParameterEntity resultEntity = */fossToTPSService.updateDepartArriveinfo(requestParameter);
			LOGGER.info("FOSS步已受理约车信息到TPS，约车状态为报到--调用TPS同步外请车约车信息接口结束");
		} catch (Exception e) {
			LOGGER.error("FOSS步已受理约车信息到TPS，约车状态为报到，调用TPS接口同步外请车约车信息失败："+e.getStackTrace().toString());
			e.printStackTrace();
		}
					
	}
	
	/**
	 * 报到
	 * FOSS恢复整车约车 数据不同步给TPS
	 * @author 269701-foss-lln
	 * @date 2016-03-15 上午17:50:34
	 * @param inviteVehicleDto
	 */
	public void doVerifyArriveInviteVehicleApplyForWholecar(InviteVehicleDto inviteVehicleDto) {
		if (inviteVehicleDto == null || StringUtil.isEmpty(inviteVehicleDto.getInviteNo())) {
			throw new InviteVehicleException("参数错误");
		}
		InviteVehicleDto inviteVehicle = queryInviteVehicleByInviteNo(inviteVehicleDto.getInviteNo());
		if (inviteVehicle == null) {
			throw new InviteVehicleException("外请约车申请不存在, 外请车编号:" + inviteVehicleDto.getInviteNo());
		}
		
		String status = inviteVehicle.getStatus();
		// 已受理
		if (StringUtil.isBlank(status) || 
				!(StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED))) {
			throw new InviteVehicleException("没有达到已受理状态,无法报到,外请车编号:" + inviteVehicleDto.getInviteNo());
		}
		// 判断此外请车的
		inviteVehicle.setIsReturn(inviteVehicleDto.getIsReturn());
		inviteVehicle.setReturnCost(inviteVehicleDto.getReturnCost());
		inviteVehicle.setIsContractVehicle(inviteVehicleDto.getIsContractVehicle());
		inviteVehicle.setContractLineCode(inviteVehicleDto.getContractLineCode());
		inviteVehicle.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);
		inviteVehicle.setArrivalTime(new Date());
		updateInviteVehicle(inviteVehicle);
	}
	
	/**
	 * 释放
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:06:34
	 * @param inviteNoList 约车编号list
	 */
	@Transactional
	public void doReleaseInviteVehicleApply(List<String> inviteNoList) {
		checkInviteVehicleApplyStatus(inviteNoList, new CheckStatusCallback<Object>() {
			public Object doCheck(List<InviteVehicleDto> list) {
				for (InviteVehicleDto inviteVehicleDto : list) {
					String status = inviteVehicleDto.getStatus();
					
					String[] useStatusArray=inviteVehicleDto.getUseStatus().split(",");
					boolean useStatusBool=false;
					//判定约车里面是否尤为适用的状态如果有则直接释放如果没有则不能释放
					for(String useStatus : useStatusArray){
						if(StringUtil.equals(useStatus, InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED)){
							useStatusBool=true;
							break;
						}
					}
					
					if (StringUtil.equals(status, InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE) && useStatusBool){
						//判定约车里面是否尤为适用的状态如果有则直接释放如果没有则不能释放
						continue;
					}
					//如果上面条件都没有满足则直接抛出异常提示不能释放
					throw new InviteVehicleException("只有已报到且未使用状态的外请车约车可以执行释放操作, 此记录状态有误，编号: " + inviteVehicleDto.getInviteNo());
				}
				return null;
			}
		});
		updateInviteVehicleApplyStatus(inviteNoList, InviteVehicleConstants.INVITEVEHICLE_STATUS_RELEASE);
		//同步约车信息到TPS
		for(String inviteNo : inviteNoList){
			RequestParameterEntity requestParameter =  new RequestParameterEntity();
			OrderVehicleRequestEntity order = new OrderVehicleRequestEntity();
			order.setOperateType(InviteVehicleConstants.INVITEVEHICLE_STATUS_RELEASE);
			order.setOrderNumber(inviteNo);
			requestParameter.setRequestEntity(order);
			
			try {
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为释放--调用TPS同步外请车约车信息接口开始");
				fossToTPSService.updateOrderVehicleInfo(requestParameter);
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为释放--调用TPS同步外请车约车信息接口结束");
			} catch (Exception e) {
				LOGGER.error("FOSS同步约车信息到TPS，约车状态为释放，调用TPS接口同步外请车约车信息失败："+e.getStackTrace().toString());
				e.printStackTrace();
				//throw new TfrBusinessException("foss更新外请车约车信息同步TPS失败！");
			}
		}
		
		
		List<InviteVehicleDto> list = queryInviteVehicleByInviteNoList(inviteNoList);
		for (InviteVehicleDto inviteVehicleDto : list) {
		//TPS同步已受理约车信息的 释放状态同步操作
		//for(String inviteNo : inviteNoList){
			RequestParameterEntity requestParameter = new RequestParameterEntity();
			List<StartArriveRequestEntity> requestEntityList = new ArrayList<StartArriveRequestEntity>();
			StartArriveRequestEntity requestEntity = new StartArriveRequestEntity();
			requestEntity.setOperateType("release");//tps 的释放是 start
			requestEntity.setFossAboutCode(inviteVehicleDto.getInviteNo());
			List<StowageBillEntity> stowageBills = new ArrayList<StowageBillEntity>();
			StowageBillEntity stowageBill = new StowageBillEntity();
			stowageBill.setVehicleNo(inviteVehicleDto.getVehicleNo());
			//添加实体
			stowageBills.add(stowageBill);
			requestEntity.setStowageBillList(stowageBills);
			requestEntityList.add(requestEntity);
			requestParameter.setRequestEntity(requestEntityList);
			try {
				LOGGER.info("FOSS同步已受理约车信息到TPS，约车状态为释放--调用TPS同步外请车约车信息接口开始");
				/*ResponseParameterEntity resultEntity = */fossToTPSService.updateDepartArriveinfo(requestParameter);
				LOGGER.info("FOSS步已受理约车信息到TPS，约车状态为释放--调用TPS同步外请车约车信息接口结束");
			} catch (Exception e) {
				LOGGER.error("FOSS步已受理约车信息到TPS，约车状态为释放，调用TPS接口同步外请车约车信息失败："+e.getStackTrace().toString());
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 更新审核外请约车操作状态
	 * @param inviteNo 外请车约车单号
	 * @param status 操作状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-25 下午1:57:49
	 */
	@Override
	@Deprecated
	public void updatePassInviteVehicleStatus(String inviteNo, String status) {
		List<String> list = new ArrayList<String>();
		list.add(inviteNo);
		updateInviteVehicleApplyStatus(list,status);
	}
	
	/***
	 * 更新外请约车状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:08:20
	 * @param inviteNoList
	 * @param status
	 */
	private void updateInviteVehicleApplyStatus(List<String> inviteNoList, String status) {
		inviteVehicleDao.updateInviteVehicleApplyStatus(inviteNoList, status);
	}
	
	/**
	 * 查询合同线路
	 * @author 104306-foss-wangLong
	 * @date 2012-12-28 上午11:31:39
	 * @param inviteNo 外请车编号
	 * @return LineEntity
	 */
	public LineEntity queryBargainLine(String inviteNo) {
		LOGGER.info("外请车编号:{}", inviteNo);
		InviteVehicleDto inviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		if(inviteVehicleDto == null) {
			return null;
		}
		LeasedTruckEntity leasedTruckEntity = new LeasedTruckEntity();
		leasedTruckEntity.setVehicleNo(inviteVehicleDto.getVehicleNo());
		LeasedTruckEntity resulTruckEntity = leasedVehicleService.queryLeasedVehicleBySelectiveCondition(leasedTruckEntity);
		if (resulTruckEntity == null || StringUtil.isEmpty(resulTruckEntity.getBargainRoute())) {
			return null;
		}
		/** 外请车线路信息  */
		return lineService.queryLineBySimpleCode(resulTruckEntity.getBargainRoute());
	}
	
	/**
	 * 更新外请约车使用状态   - 已使用
	 * @author 104306-foss-wangLong
	 * @date 2013-1-28 上午10:37:00
	 * @param inviteNo 外请车单号
	 * @throws InviteVehicleException
	 */
	@Override
	public void updateUseStatusForUsing(String inviteNo,String vehicleNo) 
			throws InviteVehicleException {
		updateUseStatus(inviteNo,vehicleNo, InviteVehicleConstants.INVITEVEHICLE_USESTATUS_USING);
	}
	
	/**
	 * 更新外请约车使用状态   - 未使用
	 * @author 104306-foss-wangLong
	 * @date 2013-1-28 上午10:37:00
	 * @param inviteNo 外请车单号
	 * @throws InviteVehicleException
	 */
	@Override
	public void updateUseStatusForUnused(String inviteNo,String vehicleNo)
			throws InviteVehicleException {
		updateUseStatus(inviteNo,vehicleNo, InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
	}
	
	/**
	 * 更新使用状态
	 * @author 104306-foss-wangLong
	 * @date 2013-1-7 上午9:00:17
	 * @param inviteNo 外请车单号
	 * @return 受影响的行数
	 */
	private int updateUseStatus(String inviteNo,String vehicleNo, String status) {
		LOGGER.info("外请车单号:{}", inviteNo);
		InviteVehicleDto inviteVehicleDto = queryInviteVehicleByInviteNo(inviteNo);
		if (inviteVehicleDto == null) {
			LOGGER.info("外请约车申请不存在,外请车单号:{}", inviteNo);
			throw new InviteVehicleException("外请约车申请不存在,外请车单号: " + inviteNo);
		}
		
		return inviteVehicleDao.updateUseStatus(inviteNo, vehicleNo, status);
	}
	
	/***
	 * 验证状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午12:04:35
	 */
	private <T> void checkInviteVehicleApplyStatus(List<String> inviteNoList, CheckStatusCallback<T> checkStatusCallback) {
		if (CollectionUtils.isEmpty(inviteNoList)) {
			throw new InviteVehicleException("参数错误");
		}
		List<InviteVehicleDto> list = queryInviteVehicleByInviteNoList(inviteNoList);
		checkStatusCallback.doCheck(list);
	}
	
	/**
	 * 查询外请车 & 司机信息
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午2:51:57
	 * @param vehicleNo 车牌号
	 */
	private VehicleDriverWithDto queryVehicleDriverWithInfo(String vehicleNo) {
		LeasedVehicleWithDriverDto driverAssociationDto = null;
		if (StringUtil.isBlank(vehicleNo)) {
			return null;
		}
		
		//这个是为了应对整车约车可以月多个约车的时候,这边一逗号分割
		String[] vehicleNos = vehicleNo.split(",");
		
		//车牌号
		StringBuffer vehicleNoSB=new StringBuffer();
		//车型
		StringBuffer vehcleLengthNameSB=new StringBuffer();
		//司机
		StringBuffer driverNameSB=new StringBuffer();
		//司机电话
		StringBuffer driverPhoneSB=new StringBuffer();
		
		for(int i=0;i<vehicleNos.length;i++){
			
			String vehicleNoTemp = vehicleNos[i];
			// 车辆信息
			VehicleAssociationDto vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(vehicleNoTemp);
			// 司机信息 //BUG-15138
			List<LeasedVehicleWithDriverDto> driverAssociationDtoList = leasedVehicleService.queryLeasedVehicleWithDriverDtosByCondition(vehicleNoTemp,"","","",true,"",0,ConstantsNumberSonar.SONAR_NUMBER_5);
			if (CollectionUtils.isEmpty(driverAssociationDtoList)) {
				 driverAssociationDtoList = leasedVehicleService.queryLeasedVehicleWithDriverDtosByCondition(vehicleNoTemp,"","","",false,"",0,ConstantsNumberSonar.SONAR_NUMBER_5);
			}
			if (!CollectionUtils.isEmpty(driverAssociationDtoList)) {
				driverAssociationDto = driverAssociationDtoList.get(0);
			}
			if (vehicleAssociationDto == null) {
				vehicleAssociationDto = new VehicleAssociationDto();
			}
			if (driverAssociationDto == null) {
				driverAssociationDto = new LeasedVehicleWithDriverDto();
			}
			
			//拼接
			vehicleNoSB.append(vehicleNoTemp);
			vehcleLengthNameSB.append(vehicleAssociationDto.getVehicleLengthName());
			if(null != driverAssociationDto.getLeasedDriver()){
				driverNameSB.append(driverAssociationDto.getLeasedDriver().getDriverName());
				driverPhoneSB.append(driverAssociationDto.getLeasedDriver().getDriverPhone());
			}
			//最后一个车牌号后面不用加逗号作为分割符
			if(i<vehicleNos.length-1){
				//如果上面的字符为空的这边还是拼接一个逗号,以作这个地方没有查出值
				vehicleNoSB.append(",");
				vehcleLengthNameSB.append(",");
				driverNameSB.append(",");
				driverPhoneSB.append(",");
			}
		}
		
		VehicleDriverWithDto vehicleDriverWithDto = new VehicleDriverWithDto();
		vehicleDriverWithDto.setVehicleNo(vehicleNoSB.toString());
		vehicleDriverWithDto.setVehcleLengthName(vehcleLengthNameSB.toString());
		vehicleDriverWithDto.setDriverName(driverNameSB.toString());
		vehicleDriverWithDto.setDriverPhone(driverPhoneSB.toString());
			
		return vehicleDriverWithDto;
	}
	
	/**
	 * 验证状态callback
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午12:04:42
	 */
	interface CheckStatusCallback<T> {
		
		T doCheck(List<InviteVehicleDto> list);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return java.util.List
	 */
	@Override
	public List<InviteVehicleDto> queryInviteVehicleListByNeedPassRecord(boolean isLoadAll,List<String> inviteNoList, InviteVehicleDto inviteVehicleDto) {
		if(isLoadAll){
			inviteVehicleDto.setInviteNoList(inviteNoList);
		}
		
		String currentDeptCode = FossUserContext.getCurrentDeptCode();
		
		inviteVehicleDto.setTopFleetCode(currentDeptCode);
		
		MotorcadeEntity org = motorcadeService.queryMotorcadeByCodeClean(currentDeptCode);
		if(null == org || (!StringUtils.equals(FossConstants.YES, org.getIsTopFleet()) && ! StringUtils.equals(FossConstants.YES, org.getDispatchTeam()))){
			throw new InviteVehicleException("您没有顶级车队或者车队调度组权限，无法审核受理！", "");
		}else{
			//尝试找登陆部门所属的顶级车队，如果能找到
			OrgAdministrativeInfoEntity topFleet = orgAdministrativeInfoComplexService.getTopFleetByCode(currentDeptCode);
			if(null == topFleet){
				throw new InviteVehicleException("此登陆部门未找到对应的顶级车队，无法审核受理！");
			}else{
				inviteVehicleDto.setTopFleetCode(topFleet.getCode());
			}
		}
		
		return inviteVehicleDao.queryInviteVehicleListByNeedPassRecord(inviteVehicleDto);
	}
	
	/**
	 * 设置inviteVehicleDao
	 * @param inviteVehicleDao the inviteVehicleDao to set
	 */
	public void setInviteVehicleDao(IInviteVehicleDao inviteVehicleDao) {
		this.inviteVehicleDao = inviteVehicleDao;
	}

	/**
	 * 设置tfrCommonService
	 * @param tfrCommonService the tfrCommonService to set
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}

	/**
	 * 设置orgAdministrativeInfoService
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置orgAdministrativeInfoComplexService
	 * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
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
	 * 设置vehicleService
	 * @param vehicleService the vehicleService to set
	 */
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 设置employeeService
	 * @param employeeService the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 设置leasedVehicleService
	 * @param leasedVehicleService the leasedVehicleService to set
	 */
	public void setLeasedVehicleService(ILeasedVehicleService leasedVehicleService) {
		this.leasedVehicleService = leasedVehicleService;
	}

	/**
	 * 设置lineService
	 * @param lineService the lineService to set
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	/**
	 * 查询费用承担部门310248
	 * @param salesExpenseMappingService
	 */
	public void setSalesExpenseMappingService(
			ISalesExpenseMappingService salesExpenseMappingService) {
		this.salesExpenseMappingService = salesExpenseMappingService;
	}
	
	/**
	 * 设置setInviteFossVehicleCostService
	 * @param setInviteFossVehicleCostService the setInviteFossVehicleCostService to set
	 */
	public void setInviteFossVehicleCostService(
			IinviteFossVehicleCostService inviteFossVehicleCostService) {
		this.inviteFossVehicleCostService = inviteFossVehicleCostService;
	}

	/**
	 * 设置fossToTPSService
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-5
	 * @param fossToTPSService
	 */
	public void setFossToTPSService(IFOSSToTPSService fossToTPSService) {
		this.fossToTPSService = fossToTPSService;
	}

	@Override
	public void checkVehicleArriveRule(String inviteNo) {
		//通过外请车编号获取车牌号及状态、使用状态
		List<InviteVehicleDto> dtoList = inviteVehicleDao.queryInviteVehicleByNo(inviteNo);
		for(InviteVehicleDto dto: dtoList){
			
			if(StringUtils.isEmpty(dto.getVehicleNo())){
				throw new InviteVehicleException("车牌号不能为空");
			}
			
			if(!StringUtils.equals(InviteVehicleConstants.INVITEVEHICLE_STATUS_COMMITTED, dto.getStatus())){
				throw new InviteVehicleException("此外请车约车未受理");
			}
	/*		
			if(StringUtils.equals(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_USING, dto.getUseStatus())){
				throw new InviteVehicleException("此外请车约车的车辆正在使用中");
			}
	*/		
			//查询非本次约车编号对应的状态为"已报道"、"未使用"的外请车约车记录是否存在
			dto.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);
			dto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
			List<InviteVehicleDto> inviteVehicleEntityList = inviteVehicleDao.queryCheckVehicleArriveRuleList(dto);
			//如果存在，抛出异常，提示用户做释放动作
			if(!CollectionUtils.isEmpty(inviteVehicleEntityList)){
				String tip = "车辆 - " + dto.getVehicleNo() + " 已绑定以下外请车约车记录，状态为已报到但未使用，请先对这些记录进行释放处理:R";
				for(InviteVehicleDto entity: inviteVehicleEntityList){
					tip += "编号: " + entity.getInviteNo() + 
						   ", 用车部门: " + entity.getApplyOrgName() + " ( " + entity.getApplyOrgCode() + " ) R";
				}
				
				throw new InviteVehicleException(tip);
			}
		}
	}

	public void setMotorcadeService(IMotorcadeService motorcadeService) {
		this.motorcadeService = motorcadeService;
	}

	/**
	 * @author niuly
	 * @date 2014-1-20下午4:40:31
	 * @function 根据约车编号查询约车信息（配载单调用接口）
	 * @param entity
	 * @return
	 */
	@Override
	public List<String> queryInviteVehicleInfo(String inviteNo) {
		/*InviteVehicleDto dto = new InviteVehicleDto();
		dto.setInviteNo(inviteNo);
		dto.setUseStatus(InviteVehicleConstants.INVITEVEHICLE_USESTATUS_UNUSED);
		dto.setStatus(InviteVehicleConstants.INVITEVEHICLE_STATUS_VERIFY_ARRIVE);*/
		return inviteVehicleDao.queryInviteVehicleInfo(inviteNo);
	}

	@Override
	public List<OrgEntity> queryBearFeesDept(String applyOrgCode , String dispatchTransDept) {
		List<OrgEntity> list = new ArrayList<OrgEntity>();
		List<SalesExpenseMappingEntity> deptlist = salesExpenseMappingService.queryByBusinessDepart(applyOrgCode);
		if(deptlist != null && deptlist.size()>0){
			//用于去重复
			Set<OrgEntity> set = new HashSet<OrgEntity>();
			for (SalesExpenseMappingEntity salesExpenseMappingEntity : deptlist) {
				 //获取费用承担部门
				 String budgetDepartmentCode = salesExpenseMappingEntity.getBudgetDepartmentCode();
				 //根据预算承担部门编码查询车队
				 MotorcadeEntity entity = motorcadeService.queryTransMotorcadeByCode(budgetDepartmentCode);
				 OrgEntity orgEntity = new OrgEntity();
				 orgEntity.setApplyPath(salesExpenseMappingEntity.getBudgetDepartmentName());
				 orgEntity.setMinistryinformationCode(budgetDepartmentCode);
				 if(entity != null){
					 if(StringUtils.equals(dispatchTransDept , entity.getCode())){
						 set.add(orgEntity);
					 }
				 }else{
					 set.add(orgEntity);
				 }
			}
			//加入当前派车车队
			MotorcadeEntity motorcadeEntity = motorcadeService.queryTransMotorcadeByCode(dispatchTransDept);
			if(motorcadeEntity != null){
				OrgEntity orgEntity = new OrgEntity();
				orgEntity.setApplyPath(motorcadeEntity.getName());
				orgEntity.setMinistryinformationCode(motorcadeEntity.getCode());
				set.add(orgEntity);
			}
			//set集合中的值放入list集合中
			list.addAll(set);
			return list;
		}		
		return null;
	}
}