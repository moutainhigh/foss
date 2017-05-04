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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/DegreeResultNotificationProcessor.java
 * 
 * FILE NAME        	: DegreeResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.uumsitf.esb.server;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.pojo.transformer.jaxb.SendDegreeRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendDegreeResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.uums.inteface.domain.usermanagement.DegreeInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendDegreeProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendDegreeRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendDegreeResponse;

/**
 * 用来从UUMS同步公司“职等信息”到FOSS数据库数据字典：无SUC
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-12-9 上午9:28:51
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-12-9 上午9:28:51
 * @since
 * @version
 */
public class DegreeResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(DegreeResultNotificationProcessor.class);

	// 同步"职等信息"接口结果操作Service
	//private IDataDictionaryValueService dataDictionaryValueService;

	private IDataDegreePostionService dataDegreePostionService;
	 
	public void setDataDegreePostionService(
			IDataDegreePostionService dataDegreePostionService) {
		this.dataDegreePostionService = dataDegreePostionService;
	}
	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;

	@Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info(" ***************************** Began to record data ***************************** ");

		SendDegreeRequest sendDegreeRequest = (SendDegreeRequest) req;
		SendDegreeResponse sendDegreeResponse = new SendDegreeResponse();

		LOGGER.info(new SendDegreeRequestTrans().fromMessage(sendDegreeRequest));

		if (null == sendDegreeRequest) {
			LOGGER.info("SendDegreeRequest数据为空:" + req);
			return sendDegreeResponse;
		}
		int successCount = 0, failCount = 0;
		List<SendDegreeProcessReult> detailList = sendDegreeResponse
				.getProcessResult();
		List<DegreeInfo> degreeInfos = sendDegreeRequest.getDegreeInfo();
		for (DegreeInfo degreeInfo : degreeInfos) {
			String changeType = null;
			// FOSS"职等信息"信息初始化
			DataDegreePostionValueEntity dataDegreePostionValueEntity = null;
			// 是否已经存在数据库中
			boolean isInDataSystem;
			try {
				// 业务锁
				MutexElement mutex = new MutexElement(
						degreeInfo.getDegreeCode(), "UUMS_DEGREE_CODE",
						MutexElementType.UUMS_DEGREE_CODE);
				LOGGER.info("开始加锁：" + mutex.getBusinessNo());
				boolean result = businessLockService.lock(mutex,
						NumberConstants.ZERO);
				if (!result) {
					failCount++;
					degreeInfo.setStatus(changeType);
					detailList.add(this.recordSynchronizedRequestDataError(
							degreeInfo, false, "出现了高并发操作！"));
					LOGGER.info("失败加锁：" + mutex.getBusinessNo());
					continue;
				}
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				dataDegreePostionValueEntity = this
						.vaidationUUMSDataObjectIntoFOSS(degreeInfo);
				if (null == dataDegreePostionValueEntity) {
					isInDataSystem = false;
					dataDegreePostionValueEntity = new DataDegreePostionValueEntity();
				} else {
					isInDataSystem = true;
				}
				// 数据转换与封装
				dataDegreePostionValueEntity
						.setType(DictionaryConstants.UUMS_DEGREE_TERMSCODE);
				if (StringUtils.isNotBlank(degreeInfo.getDegreeCode())) {
					// 清除空格
					dataDegreePostionValueEntity.setValueCode(degreeInfo
							.getDegreeCode().trim());
				} else {
					dataDegreePostionValueEntity.setValueCode(degreeInfo
							.getDegreeCode());
				}

				dataDegreePostionValueEntity.setValueName(degreeInfo
						.getDegreeName());
				// dataDictionaryValue.setValueSeq(String.valueOf(Long
				// .valueOf(System.currentTimeMillis())
				// .shortValue()
				// + ++index));
				dataDegreePostionValueEntity
						.setCreateUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);

				if (isInDataSystem) {
					if (StringUtils.equals(NumberConstants.NUMERAL_S_ONE,
							degreeInfo.getStatus())) {
						changeType = NumberConstants.NUMERAL_S_THREE;
						dataDegreePostionService
								.deleteDegreePostionValue(dataDegreePostionValueEntity);
					} else {
						changeType = NumberConstants.NUMERAL_S_TWO;
						dataDegreePostionService
								.updateDegreePostionValue(dataDegreePostionValueEntity);
					}
				} else {
					changeType = NumberConstants.NUMERAL_S_ONE;
					dataDegreePostionService
							.addDegreePostionValue(dataDegreePostionValueEntity);
				}

				/*
				 * if
				 * (StringUtils.equals(NumberConstants.NUMERAL_S_ONE,degreeInfo
				 * .getStatus())) { // 设置操作类型，3代表删除 changeType =
				 * NumberConstants.NUMERAL_S_THREE; if (isInDataSystem) {
				 * dataDegreePostionService
				 * .deleteDataDictionaryValue(dataDegreePostionValueEntity); }
				 * else { throw new DataDictionaryValueException(
				 * DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
				 * } } else if (StringUtils.equals(
				 * NumberConstants.NUMERAL_S_TWO, degreeInfo.getStatus())) { if
				 * (isInDataSystem) { // 设置操作类型，2代表修改 changeType =
				 * NumberConstants.NUMERAL_S_TWO;
				 * dataDegreePostionService.updateDataDictionaryValue
				 * (dataDegreePostionValueEntity); } else { // 设置操作类型，1代表新增
				 * changeType = NumberConstants.NUMERAL_S_ONE;
				 * dataDegreePostionService
				 * .addDataDictionaryValue(dataDegreePostionValueEntity); } }
				 * else { throw new
				 * DataDictionaryValueException(DataRuleMessageConstant
				 * .DATA_RULE_OPERATE_ERROR); }
				 */
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());
				successCount++;
				degreeInfo.setStatus(changeType);
				detailList.add(this.recordSynchronizedRequestDataError(
						degreeInfo, true, null));
			} catch (Exception e) {
				failCount++;
				degreeInfo.setStatus(changeType);
				detailList.add(this.recordSynchronizedRequestDataError(
						degreeInfo, false, e.getMessage()));
				LOGGER.error(e.getMessage(), e);
				continue;
			}
		}
		sendDegreeResponse.setSuccessCount(successCount);
		sendDegreeResponse.setFailedCount(failCount);

		LOGGER.info(new SendDegreeResponseTrans()
				.fromMessage(sendDegreeResponse));

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return sendDegreeResponse;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	 /**
	  * 验证当前的UUMS"职等信息"信息是否已经存在FOSS中
	  * @author 130134
	  * @date 2014-3-11 下午3:15:49
	  * @param degreeInfo
	  * @return
	  * @see
	  */
	private DataDegreePostionValueEntity vaidationUUMSDataObjectIntoFOSS(DegreeInfo degreeInfo) {
		if (null == degreeInfo|| StringUtils.isBlank(degreeInfo.getDegreeCode())|| StringUtils.isBlank(degreeInfo.getDegreeName())) {
			return null;
		}
		String termsCode = DictionaryConstants.UUMS_DEGREE_TERMSCODE;
		String valueCode = degreeInfo.getDegreeCode();
		DataDegreePostionValueEntity dataDegreePostionValueEntity = 
				dataDegreePostionService.queryDegreePostionValueByTermsCodeValueCodeNoCache(termsCode, valueCode);
		return dataDegreePostionValueEntity;
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
	private SendDegreeProcessReult recordSynchronizedRequestDataError(DegreeInfo degreeInfo, boolean result, String reason) {
		SendDegreeProcessReult sendDegreeProcessReult = new SendDegreeProcessReult();
		sendDegreeProcessReult.setDegreeChangeId(degreeInfo.getDegreeChangeId());
		sendDegreeProcessReult.setResult(result);
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			try {
				sendDegreeProcessReult.setReason(new String(reason.getBytes(),
						"UTF8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				sendDegreeProcessReult.setReason(reason);
			}
		}
		sendDegreeProcessReult.setDegreeName(degreeInfo.getDegreeName());
		sendDegreeProcessReult.setDegreeCode(degreeInfo.getDegreeCode());
		sendDegreeProcessReult.setChangeType(degreeInfo.getStatus());
		return sendDegreeProcessReult;
	}


	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
