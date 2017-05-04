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
 * PROJECT NAME	: bse-crm-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/crm/itf/server/service/impl/NonfixedCustomerResultProcessor.java
 * 
 * FILE NAME        	: NonfixedCustomerResultProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * FOSS结算子系统中退代收货款等用例需要使用临欠散客及其银行账户信息，此数据由CRM系统维护，故需将该部分数据从CRM系统同步至FOSS系统； 
 * 
 * 前置条件：
 * 1、	FOSS系统正常运行；
 * 2、	CRM系统能正常调用本接口；
 * 
 * 接口场景：
 * 1、CRM系统中的临欠客户或者其银行账户发生更改（新增、修改、删除），则调用此接口，传入散客信息及其银行账户信息和更改类型（新增、修改、删除）；
 * 2、	若调用成功，FOSS系统返回调用成功的标识；
 * 
 * 后置动作：
 * 1、	CRM系统记录接口调用日志；
 * 2、	FOSS系统记录接口被调用日志；
 * 
 * 接口异常处理：
 * 接口调用发生异常需要抛出，由CRM系统统一处理异常；
 * 
 * 非功能需求
 * 调用时效：立即
 * 交互模式：请求/回调
 * 是否需要顺序执行：否
 * 是否批量处理：否
 * 是否允许重复调用：否
 * 调用时段和调用量：00：00~24：00，一天调用3000次。
 * 高峰时段：08:00-12:00，13:30-17:30
 * 消息大小 ：一次1条
 * 时间响应要求：N/A
 * 安全性要求：无
 * 
 * 传入参数信息：
        字段	是否必填	是否列表	备注
        散客信息	是	否	参见【散客信息】
        
        散客信息
        字段	是否必填	是否列表	备注
        散客ID	是	否	
        散客类型	是	否	
        临欠额度	是	否	
        客户开户银行	否	是	参见【客户开户银行】
        操作类别(新增、修改、删除)	是	否	传入整数，1、新增；2、修改；3、删除；
        客户名称	是	否	
        客户编码	是	否	
        会员号	否	否	如果有则必填
        联系人名称	是	否	
        手机	是	否	
        
        电话	否	否	
        联系人地址	是	否	
        散客状态	是	否	0-有效，2-无效
        最后更新时间	是	否	当前数据的最后更新时间可当做版本号
        临客所属部门标杆编码	是	否	
        
        客户开户银行
        字段	是否必填	是否列表	备注
        账户ID	是	否	
        其他支行名称（手动输入）	是	否	
        开户帐号	是	否	
        开户人姓名	是	否	
        开户行所在城市编码	是	否	
        开户行所在城市名称	是	否	
        开户行省份编码	是	否	
        开户行省份名称			
        开户行编码	是	否	
        开户行名称			
        手机号码	是	否	
        帐号与客户关系	是	否	
        支行编码	是	否	
        支行名称			
        备注	是	否	
        是否默认帐号	是	否	整数类型，0、否；1、是
        散客账号状态	是	否	标识当前散客的此开户银行账号是否有效。0-有效，2-无效
        最后更新时间	是	否	当前数据的最后更新时间，可当做版本号
        
        返回参数：
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        散客ID	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填

 * 
 * 
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
package com.deppon.foss.module.base.crm.itf.server.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.crm.OrigCustSyncRequest;
import com.deppon.esb.inteface.domain.crm.OrigCustSyncResponse;
import com.deppon.esb.inteface.domain.crm.ScatterCustInfo;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;

/**
 * 同步临欠散客信息接口
 * 
 * 1、CRM系统中的临欠客户或者其银行账户发生更改（新增、修改、删除），则调用此接口，传入散客信息及其银行账户
 *    信息和更改类型（新增、修改、删除）；
 * 2、	若调用成功，FOSS系统返回调用成功的标识；
 * * 前置条件：
 * 1、	FOSS系统正常运行；
 * 2、	CRM系统能正常调用本接口；
 * 
 * 接口场景：
 * 1、CRM系统中的临欠客户或者其银行账户发生更改（新增、修改、删除），则调用此接口，传入散客信息及其银行账户信息和更改类型（新增、修改、删除）；
 * 2、	若调用成功，FOSS系统返回调用成功的标识；
 * 
 * 后置动作：
 * 1、	CRM系统记录接口调用日志；
 * 2、	FOSS系统记录接口被调用日志；
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-27 下午6:57:15
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-27 下午6:57:15
 * @since
 * @version
 */
public class NonfixedCustomerResultProcessor implements IProcess {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(NonfixedCustomerResultProcessor.class);
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
    /**
     * 从CRM系统同步散客信息业务处理接口.
     */
//    private ISyncInfosFromCrmService syncInfosFromCrmService;
    
    /**
     * 设置 业务互斥锁服务.
     *
     * @param businessLockService the businessLockService to set
     */
    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }
    
    /**
     * 设置 从CRM系统同步散客信息业务处理接口.
     *
     * @param syncInfosFromCrmService the syncInfosFromCrmService to set
     */
//    public void setSyncInfosFromCrmService(
//    	ISyncInfosFromCrmService syncInfosFromCrmService) {
//        this.syncInfosFromCrmService = syncInfosFromCrmService;
//    }


    /**
     * <p>
     * 接口异常处理：
     * 接口调用发生异常需要抛出，由CRM系统统一处理异常；
     * 
     * * 前置条件：
     * 1、	FOSS系统正常运行；
     * 2、	CRM系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、CRM系统中的临欠客户或者其银行账户发生更改（新增、修改、删除），则调用此接口，传入散客信息及其银行账户信息和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	CRM系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * </p>
     * .
     * 
     * @param req
     * @return
     * @throws ESBBusinessException
     * @author 094463-foss-xieyantao
     * @date 2012-11-27 下午8:33:09
     * @see com.deppon.esb.core.process.IProcess#errorHandler(java.lang.Object)
     */
    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("同步散客信息和临欠散客开户银行账户信息异常：" + req);
	return null;
    }

    /**
     * <p>
     * 同步散客信息和临欠散客开户银行账户信息
     * * 前置条件：
     * 1、	FOSS系统正常运行；
     * 2、	CRM系统能正常调用本接口；
     * 
     * 接口场景：
     * 1、CRM系统中的临欠客户或者其银行账户发生更改（新增、修改、删除），则调用此接口，传入散客信息及其银行账户信息和更改类型（新增、修改、删除）；
     * 2、	若调用成功，FOSS系统返回调用成功的标识；
     * 
     * 后置动作：
     * 1、	CRM系统记录接口调用日志；
     * 2、	FOSS系统记录接口被调用日志；
     * </p>
     * .
     * 
     * @param req
     * @return
     * @throws ESBBusinessException
     * @author 094463-foss-xieyantao
     * @date 2012-11-27 下午8:05:07
     * @see com.deppon.esb.core.process.IProcess#process(java.lang.Object)
     */
    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info("同步散客信息和临欠散客开户银行账户信息开始");
	// 同步散客信息和临欠散客开户银行账户信息对象
	OrigCustSyncRequest request = (OrigCustSyncRequest) req;
	// 获取同步过来的数据
	ScatterCustInfo custInfo = request.getScatterCustInfo();
	OrigCustSyncResponse response = new OrigCustSyncResponse();
	if(custInfo != null){
	    // 业务锁
	    MutexElement mutex = new MutexElement(String.valueOf(custInfo
		    .getFscatterid()), "NONCUS_CRMID",
		    MutexElementType.NONCUS_CRMID);
	    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
	    boolean result = businessLockService.lock(mutex,
		    NumberConstants.ZERO);
	    if (result) {
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
	    } else {
		LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		return response;
	    }
	    //同步散客和散客账号信息
//	    response = syncInfosFromCrmService.syncNonfixedCustomer(custInfo);

	    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
	    // 解业务锁
	    businessLockService.unlock(mutex);
	    LOGGER.info("完成解锁：" + mutex.getBusinessNo());
	    
	    return response;
	}
	return response;
	
    }
    
    
}
