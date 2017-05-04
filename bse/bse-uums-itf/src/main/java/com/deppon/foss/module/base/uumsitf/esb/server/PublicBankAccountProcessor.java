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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/PublicBankAccountProcessor.java
 * 
 * FILE NAME        	: PublicBankAccountProcessor.java
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

import com.alibaba.fastjson.JSON;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.finance.BankAccInfoType;
import com.deppon.esb.inteface.domain.finance.SyncPubBankAccProcessType;
import com.deppon.esb.inteface.domain.finance.SyncPubBankAccRequestType;
import com.deppon.esb.inteface.domain.finance.SyncPubBankAccResponseType;
import com.deppon.esb.pojo.transformer.jaxb.SyncPubBankAccRequestTypeTrans;
import com.deppon.esb.pojo.transformer.jaxb.SyncPubBankAccResponseTypeTrans;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PublicBankAccountException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.uumsitf.esb.util.DataRuleMessageConstant;

/**
 * 用来从UUMS同步"对公银行帐号"到FOSS数据库：无SUC
 * 
 * \01 项目管理\100 项目内基线\接口管理\接口规格\接口设计文档\财务自助\FOSS\jms
 * 
 * FOSS_QUERY_ACCOUNT.xsd
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-9 上午11:00:57
 */
public class PublicBankAccountProcessor implements IProcess {
	/**
	 * 声明Log日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PublicBankAccountProcessor.class);
	/**
	 * 同步"职等信息"接口结果操作Service
	 */
	private IPublicBankAccountService publicBankAccountService;
	// 业务锁
	private IBusinessLockService businessLockService;
	
	// 初始化成功和失败次数
	int successCount = 0, failCount = 0;

	/**
	 * 用来从UUMS同步"对公银行帐号"到FOSS数据库
	 * 
	 * 
	 * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
	 */
	@Override
	public Object process(Object req) throws ESBBusinessException {
		// 打印log日志
		LOGGER.info(" ***************************** Began to record data ***************************** ");
		// 声明syncPubBankAccRequest对象
		SyncPubBankAccRequestType syncPubBankAccRequest = (SyncPubBankAccRequestType) req;
		// 声明syncPubBankAccResponse对象
		SyncPubBankAccResponseType syncPubBankAccResponse = new SyncPubBankAccResponseType();
		// 打印Log日志
		LOGGER.info("financeSelf invoke FOSS network interface,send t_bas_public_bank_account info,request msg UUMS调用FOSS同步行财务织接口，请求报文:\n"
				+ new SyncPubBankAccRequestTypeTrans()
						.fromMessage(syncPubBankAccRequest));
		// 对Request进行非空判断
		if (null != syncPubBankAccRequest) {
			// 获得respon结果
			List<SyncPubBankAccProcessType> detailList = syncPubBankAccResponse
					.getProcessDetails();
			// 遍历Request的BankAccInfoType实体
			for (BankAccInfoType bankAccInfoType : syncPubBankAccRequest
					.getBankAccInfo()) {
				try {
					// 业务锁
					MutexElement mutex = new MutexElement(bankAccInfoType.getFid(),
							"FIN_PUBLIC_BANK_FID",
							MutexElementType.FIN_PUBLIC_BANK_FID);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,
							NumberConstants.NUMBER_10);
					if (result) {
						saveOrUpdateBankAccInfoType(
								bankAccInfoType, mutex);
					} else {

						// 失败次数加1
						failCount++;
						// 转换成json数据格式
						String jsonobj = JSON.toJSONString(bankAccInfoType);
						// 添加详细错误信息
						detailList.add(this.recordSynchronizedRequestDataError(
								bankAccInfoType, false, "出现了高并发操作！" + "#"
										+ jsonobj));
						// 打印错误日志
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						// 继续执行
						continue;
					}
					// 成功次数加1
					successCount++;
					// 添加详细错误信息
					detailList.add(this.recordSynchronizedRequestDataError(
							bankAccInfoType, true, null));
				}
				// 捕获异常
				catch (PublicBankAccountException e) {
					// 失败次数加1
					failCount++;
					// 转换成json数据格式
					String jsonobj = JSON.toJSONString(bankAccInfoType);
					// 添加详细错误信息
					detailList.add(this.recordSynchronizedRequestDataError(
							bankAccInfoType, false, e.getMessage() + "#"
									+ jsonobj));
					// 打印错误日志
					LOGGER.error(e.getMessage(), e);
					// 继续执行
					continue;
				}
			}
			// 把成功的次数设置到response对象返回
			syncPubBankAccResponse.setSuccessCount(successCount);
			// 把失败的次数设置到response对象返回
			syncPubBankAccResponse.setFailedCount(failCount);
			// 打印日志信息
			LOGGER.info("financeSelf invoke FOSS network interface,send t_bas_public_bank_account info,return msg UUMS调用FOSS同步 对公银行账号 接口，返回报文:\n"
					+ new SyncPubBankAccResponseTypeTrans()
							.fromMessage(syncPubBankAccResponse));
		}
		// 打印日志信息
		LOGGER.info(" *****************************同步 对公银行账号 End to record data ***************************** ");
		// 返回Response对象
		return syncPubBankAccResponse;
	}

	private void saveOrUpdateBankAccInfoType(BankAccInfoType bankAccInfoType,
			MutexElement mutex) {
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		// 操作类别
		String operateType = String.valueOf(bankAccInfoType.getOperateType());
		// 数据标识
		String code = bankAccInfoType.getFid();
		// 执行查询
		PublicBankAccountEntity entityResult = publicBankAccountService
				.queryPublicBankAccountOfActiveByFid(code);
		// 声明对公银行帐号实体类
		PublicBankAccountEntity entity = new PublicBankAccountEntity();
		// 判断数据合法性
		if (StringUtils.isBlank(code)) {
			// 抛出异常
			throw new PublicBankAccountException(
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR_CODE,
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR);
		}
		// 判断 部门标杆编码 银行编码 银行名称 支行编码 支行名称 银行开户名 省份编码 省份名称 城市编码 城市名称
		// 银行账号 账号ID 操作类型 账号状态不能为空
		String checkInfo = this.checkRequData(bankAccInfoType);
		// 非空判断查询结果
		if (StringUtils.isNotBlank(checkInfo)) {
			// 抛出异常
			throw new PublicBankAccountException(
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR_CODE,
					checkInfo);
		}

		// 判断操作类型是否为1
		if (StringUtils.endsWith(NumberConstants.NUMERAL_S_ONE, operateType)) {
			// 如果新增的对象已存在：
			if (entityResult != null) {
				// 抛出异常信息
				throw new PublicBankAccountException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_EXIST_ERROR);
			}
			// 执行对象类型转换
			entity = this.changeEsbToFoss(bankAccInfoType, entity);

			// 根据同步的动作执行对应的"新增"
			publicBankAccountService.addPublicBankAccount(entity);

		}
		// 判断操作类型是否是删除
		else if (NumberConstants.NUMERAL_S_THREE.equalsIgnoreCase(operateType)) {
			// 如果刪除的对象不存在：
			if (entityResult == null) {
				// 抛出异常
				throw new PublicBankAccountException(
				// 对象类型编码不存在错误
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
						// 对象类型不存在错误
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR);
			}
			// 执行对象类型转换
			entity = this.changeEsbToFoss(bankAccInfoType, entityResult);
			// 根据同步的动作执行对应的"删除"
			publicBankAccountService.deletePublicBankAccount(entity);
		}
		// 判断操作类型
		else if (NumberConstants.NUMERAL_S_TWO.equalsIgnoreCase(operateType)) {
			// 如果修改的对象不存在：
			if (entityResult == null) {
				// 抛出异常
				throw new PublicBankAccountException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR_CODE,
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NO_EXIST_ERROR);
			}
			// 对象类型转换
			entity = this.changeEsbToFoss(bankAccInfoType, entityResult);
			// 根据同步的动作执行对应的"修改"
			publicBankAccountService.updatePublicBankAccount(entity);
		} else {
			// 抛出异常信息
			throw new PublicBankAccountException(
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR_CODE,
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		}
		LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("完成解锁：" + mutex.getBusinessNo());
	}

	/**
	 * 
	 * 声明错误处理函数
	 * 
	 * @date Mar 20, 2013 10:46:30 AM
	 * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		// 返回为空
		return null;
	}

	/**
	 * 将ESB请求的 对象转换成FOSS的对象
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-9 下午4:13:50
	 */
	private PublicBankAccountEntity changeEsbToFoss(BankAccInfoType esbInfo,
			PublicBankAccountEntity fossEntity) {
		// esbInfo非空判断
		if (esbInfo == null) {
			// 返回空
			return null;
		}
		// 三目运算符判断参数是否为空，为空则声明新对象
		PublicBankAccountEntity entity = fossEntity == null ? new PublicBankAccountEntity()
				: fossEntity;
		// 银行账号
		entity.setBankAcc(esbInfo.getBankAcc());
		// 银行开户名
		entity.setBankAccName(esbInfo.getBankAccName());
		// 部门标杆编码
		entity.setDeptCd(esbInfo.getDeptCd());
		// 银行编码
		entity.setBankCd(esbInfo.getBankCd());
		// 银行名称
		entity.setBankName(esbInfo.getBankName());
		// 支行编码
		entity.setSubbranchCd(esbInfo.getSubbranchCd());
		// 支行名称
		entity.setSubbranchName(esbInfo.getSubbranchName());
		// 省份编码
		entity.setProvCd(esbInfo.getProvCd());
		// 省份名称
		entity.setProvName(esbInfo.getProvName());
		// 城市编码
		entity.setCityCd(esbInfo.getCityCd());
		// 城市名称
		entity.setCityName(esbInfo.getCityName());
		// 账号状态：正常/销户
		entity.setAccountStatus(esbInfo.getAccountStatus());
		// 财务自助平台ID
		entity.setFid(esbInfo.getFid());
		//保理类账号类别
		entity.setAccSort(esbInfo.getAccSort());
		// 返回结果
		return entity;
	}

	/**
	 * 对同步的UUMS对象的做成功和失败的记录
	 * 
	 * @author 087584-foss-lijun
	 * @date 2013-1-26 下午6:31:07
	 * @param tractor
	 *            请求的"职等信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SyncPubBankAccProcessType recordSynchronizedRequestDataError(
			BankAccInfoType bankAccInfoType, boolean result, String reason) {
		// SyncPubBankAccProcessType类为处理明细的对象
		SyncPubBankAccProcessType syncPubBankAccProcessType = new SyncPubBankAccProcessType();
		// 设置处理的对象标识
		syncPubBankAccProcessType.setDeptCd(bankAccInfoType.getDeptCd());
		// 设置处理是否成功
		syncPubBankAccProcessType.setIsSuccess(result);
		// 设置DeptCd的值
		syncPubBankAccProcessType.setDeptCd(bankAccInfoType.getDeptCd());
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			// 如果失败了返回失败原因
			syncPubBankAccProcessType.setFailedReason(reason);
		}
		// 返回结果
		return syncPubBankAccProcessType;
	}

	/**
	 * 验证请求数据的合法性
	 * 
	 * @author 087584-foss-lijun
	 * @date 2013-1-29 上午10:21:58
	 * @param
	 * 
	 */
	public String checkRequData(BankAccInfoType bankAccInfoType) {
		// 声明StringBuilder对象
		StringBuilder nullProp = new StringBuilder();
		// 判断 部门标杆编码 银行编码 银行名称 支行编码 支行名称 银行开户名 省份编码 省份名称 城市编码 城市名称 银行账号 账号ID
		// 操作类型 账号状态不能为空
		if (StringUtils.isBlank(bankAccInfoType.getDeptCd())) {
			// 添加到StringBuffer
			nullProp.append("部门标杆编码");
		}
		// 对银行名称非空验证
		if (StringUtils.isBlank(bankAccInfoType.getBankName())) {
			// 添加到StringBuffer
			nullProp.append("银行名称");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getSubbranchCd())) {
			// 添加到StringBuffer
			nullProp.append("支行编码");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getSubbranchName())) {
			// 添加到StringBuilder对象
			nullProp.append("支行名称");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getBankAccName())) {
			// 添加到StringBuilder对象
			nullProp.append("银行开户名");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getProvCd())) {
			// 添加到StringBuilder对象
			nullProp.append("省份编码");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getProvName())) {
			// 添加到StringBuilder对象
			nullProp.append("省份名称");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getCityCd())) {
			// 添加到StringBuilder对象
			nullProp.append("城市编码");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getCityName())) {
			// 添加到StringBuilder对象
			nullProp.append("城市名称");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getBankAcc())) {
			// 添加到StringBuilder对象
			nullProp.append("银行账号");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getFid())) {
			// 添加到StringBuilder对象
			nullProp.append("账号ID");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getAccountStatus())) {
			// 添加到StringBuilder对象
			nullProp.append("账号状态：正常/销户");
		}
		// 非空验证
		if (StringUtils.isBlank(bankAccInfoType.getBankAcc())) {
			// 添加到StringBuilder对象
			nullProp.append("最后更新时间");
		}
		// 非空验证
		if (StringUtils.isNotBlank(nullProp.toString())) {
			// 声明StringBuilder对象
			StringBuilder msg = new StringBuilder();
			// 返回错误信息
			msg.append(
					DataRuleMessageConstant.DATA_RULE_REASON_OBJECT_NULL_ERROR)
					.append(",fid:").append(bankAccInfoType.getFid())
					.append(",必填字段：").append(nullProp);
			// 把消息类型转换成String
			return msg.toString();
		}
		// 返回为空
		return null;
	}

	/**
	 * @param entityService
	 *            the entityService to set
	 */
	public void setPublicBankAccountService(
			IPublicBankAccountService publicBankAccountService) {
		// 赋值Service
		this.publicBankAccountService = publicBankAccountService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}