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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/FinancialOrganizationsProcessor.java
 * 
 * FILE NAME        	: FinancialOrganizationsProcessor.java
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
import com.deppon.esb.pojo.transformer.jaxb.SendFinancialOrgRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendFinancialOrgResponseTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFinancialOrganizationsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FinancialOrganizationsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FinancialOrganizationsException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.uumsitf.esb.util.DataRuleMessageConstant;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.uums.inteface.domain.usermanagement.FinancialOrgInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendFinancialOrgProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendFinancialOrgRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendFinancialOrgResponse;

/**
 * 用来从UUMS同步"财务组织"到FOSS数据库：无SUC
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-9 上午11:00:57
 */
public class FinancialOrganizationsProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FinancialOrganizationsProcessor.class);

	// 同步"职等信息"接口结果操作Service
	private IFinancialOrganizationsService financialOrganizationsService;

	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	
	//统计成功，失败记录
	int successCount = 0, failCount = 0;

	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info(" ***************************** Began to record data ***************************** ");

		SendFinancialOrgRequest sendFinancialOrgRequest = (SendFinancialOrgRequest) req;
		SendFinancialOrgResponse sendFinancialOrgResponse = new SendFinancialOrgResponse();

		LOGGER.info("UUMS invoke FOSS network interface,send t_bas_fin_org info,request msg UUMS调用FOSS同步财务组织接口，请求报文:\n"
				+ new SendFinancialOrgRequestTrans()
						.fromMessage(sendFinancialOrgRequest));

		if (null != sendFinancialOrgRequest) {
			List<SendFinancialOrgProcessReult> detailList = sendFinancialOrgResponse
					.getProcessResult();

			// 操作类别
			String operateType = null;

			for (FinancialOrgInfo financialOrgInfo : sendFinancialOrgRequest
					.getFinancialOrgInfo()) {
				try {
					if(null==financialOrgInfo){
						throw new FinancialOrganizationsException("同步数据为空");
					}
					// 业务锁
					MutexElement mutex = new MutexElement(
							financialOrgInfo.getFinOrgCode(),
							"UUMS_FIN_ORG_CODE",
							MutexElementType.UUMS_FIN_ORG_CODE);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,
							NumberConstants.ZERO);
					if (result) {
						operateType = getOperateType(operateType,
								financialOrgInfo, mutex);
					} else {
						failCount++;
						detailList.add(this.recordSynchronizedRequestDataError(
								financialOrgInfo, false, "出现了高并发操作！",
								operateType));
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						continue;
					}
					successCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							financialOrgInfo, true, null, operateType));
				} catch (Exception e) {
					failCount++;
					detailList.add(this.recordSynchronizedRequestDataError(
							financialOrgInfo, false, e.getMessage(),
							operateType));
					LOGGER.error(e.getMessage(), e);
					continue;
				}
			}

			sendFinancialOrgResponse.setSuccessCount(successCount);
			sendFinancialOrgResponse.setFailedCount(failCount);

			LOGGER.info("UUMS invoke FOSS network interface,send t_bas_fin_org info,return msg UUMS调用FOSS同步行财务织接口，返回报文:\n"
					+ new SendFinancialOrgResponseTrans()
							.fromMessage(sendFinancialOrgResponse));

		}
		LOGGER.info(" ***************************** End to record data ***************************** ");
		return sendFinancialOrgResponse;
	}

	private String getOperateType(String operateType,
			FinancialOrgInfo financialOrgInfo, MutexElement mutex) {
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		if (financialOrgInfo == null
				|| StringUtils.isBlank(financialOrgInfo
						.getFinOrgCode())) {
			throw new FinancialOrganizationsException(
					DataRuleMessageConstant.DATA_RULE_REASON_IDENTIFY_IS_NULL_ERROR_CODE,
					DataRuleMessageConstant.DATA_RULE_REASON_IDENTIFY_IS_NULL_ERROR);
		}

		// 数据标识（财务组织编码）
		String code = financialOrgInfo.getFinOrgCode();

		FinancialOrganizationsEntity entityResult = financialOrganizationsService
				.queryFinancialOrganizationsByCodeNoCache(code);
		FinancialOrganizationsEntity entity = new FinancialOrganizationsEntity();

		// 设置操作类型
		if (entityResult == null) {
			// 如果财务组织不存在，但封存（作废），则为异常
			if (financialOrgInfo.isIsMothball()) {
				throw new FinancialOrganizationsException(
						DataRuleMessageConstant.DATA_RULE_REASON_MOTHBALL_NO_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_MOTHBALL_NO_EXIST_ERROR);
			} else {
				// 如果财务组织不存在，但不封存，则为新增
				operateType = NumberConstants.NUMERAL_S_ONE;
			}

		} else {
			// 如果财务组织存在，但封存，则为作废
			if (financialOrgInfo.isIsMothball()) {
				operateType = NumberConstants.NUMERAL_S_THREE;
			} else {
				// 如果财务组织存在，但不是封存，则为更新
				operateType = NumberConstants.NUMERAL_S_TWO;
			}

		}

		if (StringUtils.equals(NumberConstants.NUMERAL_S_ONE,
				operateType)) {
			// 新增操作

			// 如果新增的对象已存在：
			if (entityResult != null) {
				throw new FinancialOrganizationsException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_EXIST_ERROR);
			}

			entity = this.changeEsbToFoss(financialOrgInfo,
					entity);

			// 根据同步的动作执行对应的"新增"
			financialOrganizationsService
					.addFinancialOrganizations(entity);

		} else if (StringUtils.equals(
				NumberConstants.NUMERAL_S_THREE, operateType)) {
			// 作废操作

			// 如果作废的对象不存在：
			if (entityResult == null) {
				throw new FinancialOrganizationsException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR);
			}

			entity = this.changeEsbToFoss(financialOrgInfo,
					entityResult);
			// 根据同步的动作执行对应的"作废"
			financialOrganizationsService
					.deleteFinancialOrganizations(entity);
		} else if (StringUtils.equals(
				NumberConstants.NUMERAL_S_TWO, operateType)) {
			// 修改操作

			// 如果修改的对象不存在：
			if (entityResult == null) {
				throw new FinancialOrganizationsException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR);
			}

			entity = this.changeEsbToFoss(financialOrgInfo,
					entityResult);
			// 根据同步的动作执行对应的"修改"
			financialOrganizationsService
					.updateFinancialOrganizations(entity);
		} else {
			throw new FinancialOrganizationsException(
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		}
		LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		return operateType;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		return null;
	}

	private FinancialOrganizationsEntity changeEsbToFoss(
			FinancialOrgInfo esbInfo, FinancialOrganizationsEntity fossEntity) {
		// esbInfo对象非空验证
		if (esbInfo == null) {
			// 返回为空
			return null;
		}
		// 三目运算符判断参数是否为空，为空则声明新对象
		FinancialOrganizationsEntity entity = fossEntity == null ? new FinancialOrganizationsEntity()
				: fossEntity;
		// 财务组织编码的全路径
		entity.setFullPath(esbInfo.getFullPath());
		// 财务组织编码
		entity.setCode(esbInfo.getFinOrgCode());
		// 上级财务组织编码
		entity.setParentOrgCode(esbInfo.getParentFinOrgCode());
		// 是否叶子节点
		entity.setIsLeaf(this.boolToStr(esbInfo.isIsLeaf()));
		// 财务组织名称
		entity.setName(esbInfo.getFinOrgName());
		// 是否是成本中心
		entity.setCostCenter(this.boolToStr(esbInfo.isIsCostCenter()));
		// 子公司名称
		entity.setSubCompanyName(esbInfo.getSubCompanyName());
		// 子公司编码
		entity.setSubCompanyCode(esbInfo.getSubCompanyCode());
		// 子公司名称
		entity.setFullName(esbInfo.getFinOrgFullName());
		// 备注（描述）
		entity.setNotes(esbInfo.getDescription());
		// 财务组织标杆编码
		entity.setFinOrgBenchmarkCode(esbInfo.getFinOrgBenchmarkCode());
		// 上级财务组织标杆编码
		entity.setParentFinOrgBenchmarkCode(esbInfo
				.getParentFinOrgBenchmarkCode());
		// 缺少是否子公司的属性
		// fossEntity.setSubSidiary(subSidiary)
		// 返回结果
		return entity;
	}

	/**
	 * <p>
	 * 对同步的UUMS对象的做成功和失败的记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午3:42:13
	 * @param tractor
	 *            请求的"职等信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SendFinancialOrgProcessReult recordSynchronizedRequestDataError(
			FinancialOrgInfo financialOrgInfo, boolean result, String reason,
			String operateType) {
		SendFinancialOrgProcessReult sendFinancialOrgProcessReult = new SendFinancialOrgProcessReult();
		sendFinancialOrgProcessReult.setFinOrgChangeId(financialOrgInfo
				.getFinOrgChangeId());
		sendFinancialOrgProcessReult.setResult(result);

		sendFinancialOrgProcessReult.setChangeType(operateType);
		sendFinancialOrgProcessReult.setFinOrgName(financialOrgInfo
				.getFinOrgName());
		sendFinancialOrgProcessReult.setFinOrgCode(financialOrgInfo
				.getFinOrgCode());

		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			sendFinancialOrgProcessReult.setReason(reason);
		}
		return sendFinancialOrgProcessReult;
	}

	/**
	 * @param entityService
	 *            the entityService to set
	 */
	public void setFinancialOrganizationsService(
			IFinancialOrganizationsService financialOrganizationsService) {
		this.financialOrganizationsService = financialOrganizationsService;
	}

	/**
	 * 设置 业务互斥锁服务.
	 * 
	 * @param businessLockService
	 *            the businessLockService to set
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 下面是工具方法
	 */
	public String boolToStr(boolean bool) {
		return bool ? FossConstants.YES : FossConstants.NO;
	}
}
