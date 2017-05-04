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
 * PROJECT NAME	: bse-bank-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/bankitf/server/esb/BlankInfoResultProcessor.java
 * 
 * FILE NAME        	: BlankInfoResultProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.bankitf.server.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.payment.BankInfo;
import com.deppon.esb.inteface.domain.payment.BankInfoNotificationRequest;
import com.deppon.esb.inteface.domain.payment.BankInfoNotificationResponse;
import com.deppon.esb.inteface.domain.payment.BankInfoProcessResult;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 同步银行信息接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-19 上午10:32:05 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-19 上午10:32:05
 * @since
 * @version
 */
public class BlankInfoResultProcessor implements IProcess {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BlankInfoResultProcessor.class);
    
    /**
     * 银行Service接口.
     */
    private IBankService bankService;
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 设置 银行Service接口.
     *
     * @param bankService the bankService to set
     */
    public void setBankService(IBankService bankService) {
        this.bankService = bankService;
    }
    
    /**
     * 设置 业务互斥锁服务.
     *
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    /**
     * <p>异常处理</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 上午10:32:05
     * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	
	LOGGER.error("同步银企系统银行信息异常："+ req);
	return null;
    }

    /**
     * <p>接收银企系统同步过来的银行信息</p>.
     *
     * @param req 
     * @return 
     * @throws ESBBusinessException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-19 上午10:32:05
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info("同步银行信息开始");
	BankInfoNotificationRequest request = (BankInfoNotificationRequest)req;
	BankInfoNotificationResponse response = new BankInfoNotificationResponse();
	//定义一个操作用户
	String userCode = "094463";
	
	if(request != null){
	    //同步信息处理结果集合
	  
			// 获取同步过来的数据
			List<BankInfo> list = request.getBankInfo();

			if (CollectionUtils.isNotEmpty(list)) {
				getProcessResulttList(list,userCode,response);
				// 成功总数
				
			}
	}
	
	LOGGER.info("同步银行信息结束");
	return response;
    }
    /**
     * <p>
     * 同步银行信息情况
     * </p>
     * @author 273311
     * @date 2016-4-26 上午9:50:21
     * @param 
     * @return
     * @see
     */
	private void getProcessResulttList(List<BankInfo> list, String userCode,
			BankInfoNotificationResponse response) {
		// 同步成功总数
		int successCount = 0;
		// 同步失败总数
		int failedCount = 0;
		List<BankInfoProcessResult> processResulttList = new ArrayList<BankInfoProcessResult>();
		for (BankInfo bankInfo : list) {
			BankEntity entity = this.convertInfo(bankInfo);
			// 定义一个同步处理结果对象
			BankInfoProcessResult processResult = new BankInfoProcessResult();
			// 银行编码
			processResult.setBankId(bankInfo.getBankId());
			processResult.setOperateCode(bankInfo.getOperateCode());

			// 业务锁
			MutexElement mutex = new MutexElement(bankInfo.getBankId(),
					"BANK_CODE", MutexElementType.BANK_CODE);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			if(!result){
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				// 保存失败原因
				processResult.setReason("出现了高并发操作！");
				failedCount++;
				continue;
			}
				LOGGER.info("成功加锁：" + mutex.getBusinessNo());
				if (StringUtil.equals(NumberConstants.NUMERAL_S_ONE,
						bankInfo.getOperateCode())) {// 新增信息
					try {

						int addResult = 0;
						addResult = bankService.addBank(entity);
						if (addResult > 0) {// 保存成功
							processResult.setResult(true);
							processResult.setReason("FOSS:success");
							// 成功数
							successCount++;
						} else {
							processResult.setResult(false);
							processResult.setReason("FOSS: failed");
							// 失败数
							failedCount++;
						}
					} catch (BankException e) {
						LOGGER.error(e.getMessage(), e);
						// 保存异常原因
						processResult.setReason(e.getMessage());
						processResult.setResult(false);
						// 失败数
						failedCount++;
					}

				}
				if (StringUtil.equals(NumberConstants.NUMERAL_S_TWO,
						bankInfo.getOperateCode())) {// 修改信息
					try {
						int updateResult = bankService.updateBank(entity);
						if (updateResult > 0) {
							// 保存成功
							processResult.setResult(true);
							processResult.setReason("FOSS:success");
							// 成功数
							successCount++;
						} else {
							// 保存失败
							processResult.setResult(false);
							processResult.setReason("FOSS: failed");
							// 失败数
							failedCount++;
						}

					} catch (BankException e) {
						// 记录日志
						LOGGER.error(e.getMessage(), e);
						// 保存失败原因
						processResult.setReason(e.getMessage());
						processResult.setResult(false);
						// 失败数
						failedCount++;
					}

				}
				if (StringUtil.equals(NumberConstants.NUMERAL_S_THREE,
						bankInfo.getOperateCode())) {// 删除信息
					try {
						int deleteResult = bankService.deleteBank(
								entity.getCode(), userCode);
						if (deleteResult > 0) {
							processResult.setResult(true);
							processResult.setReason("FOSS:success");
							// 成功数
							successCount++;
						} else {
							processResult.setResult(false);
							processResult.setReason("FOSS: failed");
							// 失败数
							failedCount++;
						}

					} catch (BankException e) {
						// 记录日志
						LOGGER.error(e.getMessage(), e);
						// 保存失败原因
						processResult.setReason(e.getMessage());
						processResult.setResult(false);
						// 失败数
						failedCount++;
					}
				}
				LOGGER.info("开始解锁：" + mutex.getBusinessNo());
				// 解业务锁
				businessLockService.unlock(mutex);
				LOGGER.info("完成解锁：" + mutex.getBusinessNo());

			// 把处理结果放在集合里面
			processResulttList.add(processResult);

		}
		response.setSuccessCount(successCount);
		response.getProcessResult().addAll(processResulttList);
		// 失败总数
		response.setFailedCount(failedCount);

	}
    /**
     * <p>
     * 把同步过来的信息转化成Foss信息实体
     * </p>
     * @author 273311
     * @date 2016-4-26 上午9:10:39
     * @param bankInfo
     * @return
     * @see
     */
  private BankEntity convertInfo(BankInfo bankInfo) {
	//定义银行信息实体
	    BankEntity entity = new BankEntity();
	    if(StringUtil.isNotBlank(bankInfo.getBankId())){
		//银行编码
		entity.setCode(bankInfo.getBankId().trim());
	    }else {
		//银行编码
		entity.setCode(bankInfo.getBankId());
	    }
	    //银行名称
	    entity.setName(bankInfo.getBankName());
	    if(StringUtil.isNotBlank(bankInfo.getSuperiorBankId())){
		//上级银行
		entity.setParentBank(bankInfo.getSuperiorBankId().trim());
	    }else {
		//上级银行
		entity.setParentBank(bankInfo.getSuperiorBankId());
	    }
	    if(StringUtil.isNotBlank(bankInfo.getProvenceId())){
		//省份编码
		entity.setProvId(bankInfo.getProvenceId().trim());
	    }else {
		//省份编码
		entity.setProvId(bankInfo.getProvenceId());
	    }
	    if(StringUtil.isNotBlank(bankInfo.getCityId())){
		//城市编码
		entity.setCityCode(bankInfo.getCityId().trim());
	    }else {
		//城市编码
		entity.setCityCode(bankInfo.getCityId());
	    }
	 return entity;   
}
}
