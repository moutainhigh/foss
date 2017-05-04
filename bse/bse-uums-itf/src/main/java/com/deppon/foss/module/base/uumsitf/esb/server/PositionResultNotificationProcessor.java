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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/uumsitf/esb/server/PositionResultNotificationProcessor.java
 * 
 * FILE NAME        	: PositionResultNotificationProcessor.java
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
import com.deppon.esb.pojo.transformer.jaxb.SendPositionRequestTrans;
import com.deppon.esb.pojo.transformer.jaxb.SendPositionResponseTrans;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.uums.inteface.domain.usermanagement.PositionInfo;
import com.deppon.uums.inteface.domain.usermanagement.SendPositionProcessReult;
import com.deppon.uums.inteface.domain.usermanagement.SendPositionRequest;
import com.deppon.uums.inteface.domain.usermanagement.SendPositionResponse;

/**
 * 用来从UUMS同步公司“职位信息”到FOSS数据库数据字典：无SUC
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
public class PositionResultNotificationProcessor implements IProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(PositionResultNotificationProcessor.class);

	// 同步"职位信息"接口结果操作Service
	private IDataDegreePostionService dataDegreePostionService;
	
	/**
	 * 业务互斥锁服务.
	 */
	private IBusinessLockService businessLockService;
	
	//统计成功失败记录
	int successCount = 0, failCount = 0;
	

	@Override
	public Object process(Object req) throws ESBBusinessException {

		LOGGER.info(" ***************************** Began to record data ***************************** ");

		SendPositionRequest sendPositionRequest = (SendPositionRequest) req;
		SendPositionResponse sendPositionResponse = new SendPositionResponse();

		LOGGER.info(new SendPositionRequestTrans().fromMessage(sendPositionRequest));

		if (null != sendPositionRequest) {
			List<SendPositionProcessReult> detailList = sendPositionResponse.getProcessResult();
			for (PositionInfo positionInfo : sendPositionRequest.getPositionInfo()) {
				String changeType = null;
				// 注意，
				// FOSS"职位信息"信息初始化
				DataDegreePostionValueEntity dataDegreePostionValueEntity = null;
				try {
					// 业务锁
					MutexElement mutex = new MutexElement(positionInfo.getPositionCode(),"UUMS_POSITION_CODE",MutexElementType.UUMS_POSITION_CODE);
					LOGGER.info("开始加锁：" + mutex.getBusinessNo());
					boolean result = businessLockService.lock(mutex,NumberConstants.NUMBER_10);
					if (result) {
						changeType = getChangeType(positionInfo, mutex,changeType,dataDegreePostionValueEntity);
					} else {
						failCount++;
						positionInfo.setStatus(changeType);
						detailList.add(this.recordSynchronizedRequestDataError(positionInfo, false, "出现了高并发操作！"));
						LOGGER.info("失败加锁：" + mutex.getBusinessNo());
						continue;
					}
					successCount++;
					positionInfo.setStatus(changeType);
					detailList.add(this.recordSynchronizedRequestDataError(positionInfo, true, null));
				} catch (Exception e) {
					failCount++;
					positionInfo.setStatus(changeType);
					detailList.add(this.recordSynchronizedRequestDataError(positionInfo, false, e.getMessage()));
					LOGGER.error(e.getMessage(), e);
					continue;
				}
			}
			sendPositionResponse.setSuccessCount(successCount);
			sendPositionResponse.setFailedCount(failCount);

			LOGGER.info(new SendPositionResponseTrans()
					.fromMessage(sendPositionResponse));
		}

		LOGGER.info(" ***************************** End to record data ***************************** ");
		return sendPositionResponse;
	}

	private String getChangeType(PositionInfo positionInfo, MutexElement mutex,String changeType,DataDegreePostionValueEntity dataDegreePostionValueEntity) {
		boolean isInDataSystem=false;
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
		dataDegreePostionValueEntity = this.vaidationUUMSDataObjectIntoFOSS(positionInfo);
		if (null == dataDegreePostionValueEntity) {
			isInDataSystem = false;
			dataDegreePostionValueEntity = new DataDegreePostionValueEntity();
		} else {
			isInDataSystem = true;
		}
		// 数据转换与封装
		dataDegreePostionValueEntity.setCreateUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
		dataDegreePostionValueEntity.setType(DictionaryConstants.UUMS_POSITION_TERMSCODE);
		if (StringUtils.isNotBlank(positionInfo.getPositionCode())) {
			// 清除空格
			dataDegreePostionValueEntity.setValueCode(positionInfo.getPositionCode().trim());
		} else {
			dataDegreePostionValueEntity.setValueCode(positionInfo.getPositionCode());
		}

		dataDegreePostionValueEntity.setValueName(positionInfo.getPositionName());
//						dataDegreePostionValueEntity.setValueSeq(String.valueOf(Long.valueOf(System.currentTimeMillis()).shortValue()+ ++index));
		if(isInDataSystem){
			if(StringUtils.equals(NumberConstants.NUMERAL_S_ONE,positionInfo.getStatus())){
				changeType = NumberConstants.NUMERAL_S_THREE;
				dataDegreePostionService.deleteDegreePostionValue(dataDegreePostionValueEntity);
			}else{
				changeType = NumberConstants.NUMERAL_S_TWO;
				dataDegreePostionService.updateDegreePostionValue(dataDegreePostionValueEntity);
			}
		 }else{
			 changeType = NumberConstants.NUMERAL_S_ONE;
			 dataDegreePostionService.addDegreePostionValue(dataDegreePostionValueEntity);
		 }
		
		
		/*if (StringUtils.equals(NumberConstants.NUMERAL_S_ONE,positionInfo.getStatus())) {
			// 设置操作类型，3为删除
			changeType = NumberConstants.NUMERAL_S_THREE;
			if (isInDataSystem) {
				dataDegreePostionService.deleteDataDictionaryValue(dataDegreePostionValueEntity);
			} else {
				throw new DataDictionaryValueException(
						DataRuleMessageConstant.DATA_RULE_REASON_OBJECTISNULL_ERROR);
			}
		} else if (StringUtils.equals(
				NumberConstants.NUMERAL_S_TWO,
				positionInfo.getStatus())) {
			if (isInDataSystem) {
				// 设置操作类型，2为修改
				changeType = NumberConstants.NUMERAL_S_TWO;
				dataDegreePostionService.updateDataDictionaryValue(dataDegreePostionValueEntity);
			} else {
				// 设置操作类型，1为新增
				changeType = NumberConstants.NUMERAL_S_ONE;

				dataDegreePostionService.addDataDictionaryValue(dataDegreePostionValueEntity);
			}
		} else {
			throw new DataDictionaryValueException(
					DataRuleMessageConstant.DATA_RULE_OPERATE_ERROR);
		}*/
		LOGGER.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		LOGGER.info("完成解锁：" + mutex.getBusinessNo());
		return changeType;
	}

	@Override
	public Object errorHandler(Object req) throws ESBBusinessException {
		LOGGER.error("ESB处理错误");
		return null;
	}

	/**
	 * <p>
	 * 验证当前的UUMS"职位信息"信息是否已经存在FOSS中
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-21 下午6:50:00
	 * @param tractor
	 *            UUMS"职位信息"信息参数实体
	 * @return FOSS"职位信息"信息实体
	 * @see
	 */
	private DataDegreePostionValueEntity vaidationUUMSDataObjectIntoFOSS(
			PositionInfo positionInfo) {
		if (null == positionInfo
				|| StringUtils.isBlank(positionInfo.getPositionCode())) {
			return null;
		}
		String termsCode = DictionaryConstants.UUMS_POSITION_TERMSCODE;
		String valueCode = positionInfo.getPositionCode();
		DataDegreePostionValueEntity dataDegreePostionValueEntity = 
				dataDegreePostionService.queryDegreePostionValueByTermsCodeValueCodeNoCache(termsCode,valueCode);
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
	 *            请求的"职位信息"序列化对象
	 * @param result
	 *            0表示失败，1表示成功
	 * @param reason
	 *            失败的原因
	 * @return
	 * @see
	 */
	private SendPositionProcessReult recordSynchronizedRequestDataError(PositionInfo positionInfo, boolean result, String reason) {
		SendPositionProcessReult sendPositionProcessReult = new SendPositionProcessReult();
		sendPositionProcessReult.setPositionChangeId(positionInfo.getPositionChangeId());
		sendPositionProcessReult.setResult(result);
		// 判断操作是否失败，失败需要强制增加原因
		if (!result) {
			try {
				sendPositionProcessReult.setReason(new String(reason.getBytes(), "UTF8"));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getMessage(), e);
				sendPositionProcessReult.setReason(reason);
			}
		}
		sendPositionProcessReult.setPositionName(positionInfo.getPositionName());
		sendPositionProcessReult.setPositionCode(positionInfo.getPositionCode());
		sendPositionProcessReult.setChangeType(positionInfo.getStatus());
		return sendPositionProcessReult;
	}

	/**
	 * @param dataDictionaryValueService
	 *            the dataDictionaryValueService to set
	 */
	 public void setDataDegreePostionService(
			IDataDegreePostionService dataDegreePostionService) {
		this.dataDegreePostionService = dataDegreePostionService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

}
