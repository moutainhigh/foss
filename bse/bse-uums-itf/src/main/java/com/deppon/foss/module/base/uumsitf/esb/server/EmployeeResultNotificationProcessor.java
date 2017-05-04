/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-uums-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/EmployeeResultNotificationProcessor.java
 * 
 * FILE NAME        	: EmployeeResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.jaxb.SendEmployeeRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendEmployeeResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.EmployeeException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.uums.inteface.domain.usermanagement.EmployeeInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendEmployeeProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendEmployeeRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendEmployeeResponse;

/**
 * 用来从UUMS同步公司“人员信息”到FOSS数据库：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-23 上午11:12:40
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-23 上午11:12:40
 * @since
 * @version
 */
public class EmployeeResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeeResultNotificationProcessor.class);

	// 同步"人员信息"接口结果操作Service
	private IEmployeeService employeeService;

	// 同步"部门信息"接口结果操作Service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	// 业务锁
	private IBusinessLockService businessLockService;
	
	//定义成功失败次数
	int  successCount = 0, failCount = 0;

	@Override
	public Object process(Object req) throws ESBBusinessException {

		LOGGER.info(" ***************************** Begin to record data ***************************** ");

		SendEmployeeRequest sendEmployeeRequest = (SendEmployeeRequest) req;
		SendEmployeeResponse sendEmployeeResponse = new SendEmployeeResponse();

		LOGGER.info(new SendEmployeeRequestTrans()
				.fromMessage(sendEmployeeRequest));

		if (null != sendEmployeeRequest) {
			List<SendEmployeeProcessReult> detailList = sendEmployeeResponse
					.getProcessResult();
			for (EmployeeInfo employeeInfo : sendEmployeeRequest
					.getEmployeeInfo()) {
				//313353 sonar优化
//				if (addSendEmployeeProcessReult(detailList, employeeInfo)) {
//
//				} else {
//					continue;
//				}
				if (!addSendEmployeeProcessReult(detailList, employeeInfo)) {
					continue;
				}
				
			}
			sendEmployeeResponse.setSuccessCount(successCount);
			sendEmployeeResponse.setFailedCount(failCount);

			LOGGER.info(new SendEmployeeResponseTrans()
					.fromMessage(sendEmployeeResponse));
		}

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return sendEmployeeResponse;
	}

	private boolean addSendEmployeeProcessReult(
			List<SendEmployeeProcessReult> detailList, EmployeeInfo employeeInfo) {
		// wp_078816_0520
		if (null == employeeInfo) {
			return false;
		}
		// FOSS"人员信息"信息初始化
		EmployeeEntity entity = null;
		try {
			// 业务锁
			MutexElement mutex = new MutexElement(
					employeeInfo.getEmployeeCode(), "UUMS_EMP_CODE",
					MutexElementType.UUMS_EMP_CODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.NUMBER_10);
			if (result) {
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				entity = this.vaidationUUMSDataObjectIntoFOSS(employeeInfo);
				// wp_078816_0520
				// 如果操作类型为新增或者返聘
				/*
				 * if (NumberConstants.NUMERAL_S_ONE
				 * .equalsIgnoreCase(employeeInfo.getChangeType()) ||
				 * NumberConstants.NUMERAL_S_FOUR .equalsIgnoreCase(employeeInfo
				 * .getChangeType())) { if (null == employeeEntity) { } else
				 * {//wp_078816_0520 throw new EmployeeException( "",
				 * DataRuleMessageConstant
				 * .DATA_RULE_REASON_OBJECTISNOTNULL_ERROR); } } else { if (null
				 * == employeeEntity) { throw new EmployeeException( "",
				 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
				 * } else { // 这里不做处理 } }
				 */

				// 数据转换与封装
				EmployeeEntity employeeEntity = new EmployeeEntity();
				employeeEntity
						.setCreateUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				employeeEntity
						.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				employeeEntity.setEmpName(employeeInfo.getEmployeeName());
				if (StringUtils.isNotBlank(employeeInfo.getEmployeeCode())) {
					// 清空空格
					employeeEntity.setEmpCode(employeeInfo.getEmployeeCode()
							.trim());
				} else {
					employeeEntity.setEmpCode(employeeInfo.getEmployeeCode());
				}
				employeeEntity.setGender(employeeInfo.getGender());
				employeeEntity.setTitle(employeeInfo.getOfficeZipCode());
				employeeEntity.setDegree(employeeInfo.getDegree());
				employeeEntity.setBirthdate(employeeInfo.getBirthday());
				employeeEntity.setStatus(employeeInfo.getStatus());
				employeeEntity.setEntryDate(employeeInfo.getEntryDate());
				employeeEntity.setLeaveDate(employeeInfo.getDepartureDate());
				employeeEntity.setPhone(employeeInfo.getOfficeTel());
				employeeEntity.setIdentityCard(employeeInfo.getDocNumber());
				employeeEntity.setMobilePhone(employeeInfo.getMobile());
				employeeEntity.setEmail(employeeInfo.getOfficeEmail());

				// 转换“部门标杆编码”到“部门编码”
				String tempOrgCode = null;
				tempOrgCode = employeeInfo.getDeptBenchmarkCode();
				/*
				 * if (StringUtils.isNotBlank(employeeInfo
				 * .getDeptBenchmarkCode())) { tempOrgCode =
				 * employeeInfo.getDeptBenchmarkCode(); } else {
				 * //wp_078816_0520 tempOrgCode =
				 * employeeInfo.getDeptBenchmarkCode(); }
				 */
				if (StringUtils.isNotBlank(tempOrgCode)) {
					OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCode(employeeInfo
									.getDeptBenchmarkCode());
					if (null != orgAdministrativeInfo) {
						employeeEntity.setOrgCode(orgAdministrativeInfo
								.getCode());
						employeeEntity.setUnifieldCode(orgAdministrativeInfo
								.getUnifiedCode());
					}
				} else {// wp_078816_0520 TODO 标杆编码异常要不要抛出
					throw new EmployeeException("UUMS同步过来的组织标杆编码为空");
				}

				/*
				 * if (NumberConstants.NUMERAL_S_ONE
				 * .equalsIgnoreCase(employeeInfo.getChangeType()) ||
				 * NumberConstants.NUMERAL_S_FOUR .equalsIgnoreCase(employeeInfo
				 * .getChangeType())) { // 根据同步的动作执行对应的"新增"或"返聘"
				 * //wp_078816_0520 if(null == entity){
				 * employeeService.addEmployee(employeeEntity); }else{
				 * employeeService.updateEmployee(employeeEntity); }
				 * 
				 * } else
				 */
				if (NumberConstants.NUMERAL_S_THREE
						.equalsIgnoreCase(employeeInfo.getChangeType())) {
					// 根据同步的动作执行对应的"删除"
					employeeService.deleteEmployee(employeeEntity);
				} /*
				 * else if (NumberConstants.NUMERAL_S_TWO
				 * .equalsIgnoreCase(employeeInfo.getChangeType())) { //
				 * 根据同步的动作执行对应的"修改" //wp_078816_0520 if(null == entity){
				 * employeeService.addEmployee(employeeEntity); }else{
				 * employeeService.updateEmployee(employeeEntity); } }
				 */else {
					// throw new EmployeeException(
					// DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
					// wp_078816_0520
					if (null == entity) {
						employeeService.addEmployee(employeeEntity);
					} else {
						employeeService.updateEmployee(employeeEntity);
					}
				}
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
			} else {
				failCount++;
				detailList.add(this.recordSynchronizedRequestDataError(
						employeeInfo, false, "UUMS0102:出现了高并发操作！"));
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				return false;
			}
			successCount++;
			detailList.add(this.recordSynchronizedRequestDataError(
					employeeInfo, true, null));
		} catch (Exception e) {
			failCount++;
			detailList.add(this.recordSynchronizedRequestDataError(
					employeeInfo, false, e.getMessage()));
			LOGGER.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>
	 * 验证当前的UUMS"人员信息"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午6:50:00
	 * @param tractor
	 *            UUMS"人员信息"信息参数实体
	 * @return FOSS"人员信息"信息实体
	 * @see
	 */
	private EmployeeEntity vaidationUUMSDataObjectIntoFOSS(
			EmployeeInfo employeeInfo) {
		if (null == employeeInfo
				|| StringUtils.isBlank(employeeInfo.getEmployeeCode())) {
			return null;
		}
		return employeeService.queryEmployeeByEmpCodeNoCache(employeeInfo
				.getEmployeeCode());
	}

	/**
	 * <p>
	 * 对同步的UUMS对象的做成功和失败的记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午3:42:13
	 * @param tractor
	 *            请求的"人员信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SendEmployeeProcessReult recordSynchronizedRequestDataError(
			EmployeeInfo employeeInfo, boolean result, String reason) {
		SendEmployeeProcessReult sendEmployeeProcessReult = new SendEmployeeProcessReult();
		sendEmployeeProcessReult.setEmployeeChangeId(employeeInfo
				.getEmployeeChangeId());
		sendEmployeeProcessReult.setResult(result);
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			sendEmployeeProcessReult.setReason(reason);
		}
		sendEmployeeProcessReult
				.setEmployeeCode(employeeInfo.getEmployeeCode());
		sendEmployeeProcessReult.setChangeType(employeeInfo.getChangeType());
		return sendEmployeeProcessReult;
	}

	/**
	 * @param employeeService
	 *            the employeeService to set
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * @param orgAdministrativeInfoService
	 *            the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
