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
 * PROJECT NAME	: bse-costcontrol-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/costcontrolitf/server/esb/BeneficiaryResultProcessor.java
 * 
 * FILE NAME        	: BeneficiaryResultProcessor.java
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
package com.deppon.foss.module.base.costcontrolitf.server.esb;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBBusinessException;
import com.deppon.dpap.esb.mqc.core.process.IProcess;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.inteface.domain.payment.PayeeInfo;
import com.deppon.foss.esb.inteface.domain.payment.PayeeInfoNtfRequest;
import com.deppon.foss.esb.inteface.domain.payment.PayeeInfoNtfResponse;
import com.deppon.foss.esb.inteface.domain.payment.PayeeInfoProcessResult;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPayeeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PayeeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PayeeInfoException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
//import com.deppon.esb.core.exception.ESBBusinessException;
//import com.deppon.esb.core.process.IProcess;

/**
 * 报账系统接口：同步收款方信息接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-23 下午2:01:22 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-23 下午2:01:22
 * @since
 * @version
 */
public class BeneficiaryFsscResultProcessor implements IProcess {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryFsscResultProcessor.class);
    
    /**
     * 付款方信息Service接口
     */
    private IPayeeInfoService payeeInfoService;
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;

    /**
     * 设置 付款方信息Service接口.
     *
     * @param payeeInfoService the new 付款方信息Service接口
     */
    public void setPayeeInfoService(IPayeeInfoService payeeInfoService) {
	this.payeeInfoService = payeeInfoService;
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
     * <p>异常处理</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-23 下午2:02:32
     * @param req
     * @return
     * @throws ESBBusinessException 
     * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.debug("从报账系统同步付款方信息异常：" + req);
	return null;
    }

    /**
     * <p>接收报账系统同步收款方信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-23 下午2:03:16
     * @param req
     * @return
     * @throws ESBBusinessException 
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
    @Override
	public Object process(Object req) throws ESBBusinessException {
		LOGGER.info("同步收款方信息开始........................");
		PayeeInfoNtfRequest request = (PayeeInfoNtfRequest) req;
		PayeeInfoNtfResponse response = new PayeeInfoNtfResponse();
		// 报账系统
		String user = "FSSC";

		if (null == request) {
			return null;
		}
		// 同步信息处理结果集合
		List<PayeeInfoProcessResult> processResultList = new ArrayList<PayeeInfoProcessResult>();
		// 同步成功总数
		int successCount = 0;
		// 同步失败总数
		int failedCount = 0;
		// 获取收款方信息列表
		List<PayeeInfo> payeeInfoList = request.getPayeeInfo();
		if (CollectionUtils.isEmpty(payeeInfoList)) {
			return null;
		}
		for (PayeeInfo info : payeeInfoList) {
			// 获取转化后的收款方信息实体
			PayeeInfoEntity entity = convertInfo(info);
			entity.setCreateUser(user);
			entity.setModifyUser(user);

			PayeeInfoProcessResult processResult = new PayeeInfoProcessResult();
			if (StringUtil.isNotBlank(info.getPayeeNo())) {
				// 收款方编码
				processResult.setPayeeNo(info.getPayeeNo().trim());
			}

			// 操作类别, 1-新增; 2-修改; 3-禁用
			processResult.setOperateCode(info.getOperateCode());

			// 业务锁
			MutexElement mutex = new MutexElement(info.getPayeeNo(),
					"PAYEEINFO_PAYEENO", MutexElementType.PAYEEINFO_PAYEENO);
			LOGGER.info("开始加锁：" + mutex.getBusinessNo());
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);

			if (!result) {
				LOGGER.info("失败加锁：" + mutex.getBusinessNo());
				// 保存失败原因
				processResult.setReason("出现了高并发操作！");
				failedCount++;
				continue;
			}
			LOGGER.info("成功加锁：" + mutex.getBusinessNo());
			// 王鹏—078816-20140424
			// 获取收款方编码
			String payeeCode = info.getPayeeNo().trim();
			try {
				if (StringUtils.isNotEmpty(payeeCode)) {
					int m = 0;
					// 根据收款方编码查询收款信息
					PayeeInfoEntity infoEntity = payeeInfoService
							.queryPayeeInfoBypayeeNo(payeeCode);
					if (null == infoEntity) {
						m = payeeInfoService.addPayeeInfo(entity);
					} else {
						m = payeeInfoService.updatePayeeInfo(entity);
					}
					if (m > 0) {
						processResult.setResult(true);
						// 成功数
						successCount++;
					} else {
						processResult.setResult(false);
						// 失败数
						failedCount++;
					}
				} else {
					processResult.setReason("同步收款方失败,收款方编码为空！");
					processResult.setResult(false);
					// 失败数
					failedCount++;
				}
			} catch (PayeeInfoException e) {
				LOGGER.debug(e.getMessage(), e);
				// 保存失败原因
				processResult.setReason(e.getMessage());
				processResult.setResult(false);
				// 失败数
				failedCount++;
			}

			if (StringUtil.equals(NumberConstants.NUMERAL_S_THREE,
					info.getOperateCode())) {// 删除信息
				try {
					int deleteResult = payeeInfoService
							.deletePayeeInfoByPayeeNo(entity.getPayeeNo(), user);
					if (deleteResult > 0) {
						processResult.setResult(true);
						// 成功数
						successCount++;
					} else {
						processResult.setResult(false);
						// 失败数
						failedCount++;
					}

				} catch (PayeeInfoException e) {
					LOGGER.debug(e.getMessage(), e);
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
			processResultList.add(processResult);
		}
		// 失败总记录数
		response.setFailedCount(failedCount);
		// 成功总记录数
		response.setSuccessCount(successCount);
		response.getProcessResult().addAll(processResultList);

		LOGGER.info("同步收款方信息结束........................");
		return response;

	}

    /**
     * <p>
     * 把费控系统同步过来的收款方信息转化成Foss 信息实体
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-12-15 上午9:10:39
     * @param info
     * @return
     * @see
     */
    private PayeeInfoEntity convertInfo(PayeeInfo info) {

	PayeeInfoEntity entity = new PayeeInfoEntity();
	if (null != info) {
	    if(StringUtil.isNotBlank(info.getPayeeNo())){
		 // 收款方编码
		 entity.setPayeeNo(info.getPayeeNo().trim());
	    }else {
		 // 收款方编码
		 entity.setPayeeNo(info.getPayeeNo());
	    }
	   
	    // 录入人工号
	    entity.setOperatorId(info.getOperatorId());
	    // 账户性质, 1-德邦子公司账户; 2-收银员卡; 3-内部员工账户; 4-公司外部账户
	    entity.setAccountType(info.getAccountType());
	   
	    // 开户人姓名， 可能是单位，可能是个人
	    entity.setBeneficiaryName(info.getBeneficiaryName());
	    if(StringUtil.isNotBlank(info.getAccountNo())){
		// 银行帐号
		entity.setAccountNo(info.getAccountNo().trim());
	    }else {
		// 银行帐号
		entity.setAccountNo(info.getAccountNo());
	    }
	    
	    if(StringUtil.isNotBlank(info.getAccountBank())){
		// 开户行编码，国家统一规定的
		entity.setAccountbankCode(info.getAccountBank().trim());
	    }else {
		// 开户行编码，国家统一规定的
		entity.setAccountbankCode(info.getAccountBank());
	    }
	    
	    if(StringUtil.isNotBlank(info.getAccountBranchBank())){
		// 开户支行编码
		entity.setAccountbranchbankCode(info.getAccountBranchBank().trim());
	    }else {
		// 开户支行编码
		entity.setAccountbranchbankCode(info.getAccountBranchBank());
	    }
	    
	    if(StringUtil.isNotBlank(info.getAccountBankCityId())){
		// 开户行所在城市编码，国家统一规定的
		entity.setAccountbankcityCode(info.getAccountBankCityId().trim());
	    }else {
		// 开户行所在城市编码，国家统一规定的
		entity.setAccountbankcityCode(info.getAccountBankCityId());
	    }
	    
	    if(StringUtil.isNotBlank(info.getAccountBankStateId())){
		// 开户行所在省份编码，国家统一规定的
		entity.setAccountbankstateCode(info.getAccountBankStateId().trim());
	    }else {
		// 开户行所在省份编码，国家统一规定的
		entity.setAccountbankstateCode(info.getAccountBankStateId());
	    }
	    if(StringUtil.isNotBlank(info.getAccSort())){
	    //账户类别（保理 贷款 其他）
		entity.setAccSort(info.getAccSort().trim());
	    }else {
		//账户类别（保理 贷款 其他）
	    entity.setAccSort(info.getAccSort());
	    }
	}
	
	return entity;
    }

}
