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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/crm/itf/server/service/impl/SynCustomerInfoService.java
 * 
 * FILE NAME        	: SynCustomerInfoService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 同步客户信息接口
        FOSS接送货子系统中的开单用例、FOSS结算子系统中的退代收货款等用例都需要客户及客户关联信息，该信息存储在CRM系统中，为满足FOSS系统各功能模块对于该部分信息的需求，需将客户及客户关联信息同步至FOSS系统
        FOSS系统	接口调用者	CRM系统
        前置条件：
        1、	FOSS系统正常运行；
        2、	CRM系统能正常调用本接口；
        
        接口场景：
        1、	CRM系统中客户或客户关联信息发生更改（新增、修改、删除），则调用此接口，传入客户及客户关联信息和更改类型（新增、修改、删除）；
        
        后置动作：
        1、	CRM系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由CRM系统统一处理异常；
        传入参数信息：
        字段	是否必填	是否列表	备注
        客户信息	是	否	参见【客户信息】
        
        客户信息
        字段	是否必填	是否列表	备注
        客户基本信息	是	否	参见【客户基本信息】
        客户开户银行	是	是	参见【客户开户银行】
        客户联系人信息	是	是	参见【客户联系人信息】
        客户合同信息	是	是	参见【客户合同信息】
        客户接送货地址	是	是	参见【客户接送货地址】
        合同适用部门	是	是	参见【合同适用部门】
        优惠信息	是	是	参见【优惠信息】
        联系人接送货地址	是	是	参见【联系人接送货地址】
        操作类别(新增、修改、删除)	是	否	传入整数，1、新增；2、修改；3、删除；
        
        
        客户基本信息
        字段	是否必填	是否列表	备注
        客户编码	是	否	
        地址	是	否	
        客户属性	是	否	整数类型，1、出发和到达客户；2、到达客户；3、出发客户
        客户类型	是	否	整数类型，1、企业；2、个人；
        信用额度	是	否	
        客户名称	是	否	
        客户等级	是	否	整数类型，1、已降级固定客户；2、普通客户；3、铂金客户；4、黄金客户；5、钻石客户；
        营业执照号	是	否	
        客户所属部门标杆编码	是	否	
        客户是否有效	是	否	整数类型，0、无效；1、有效；
        CRM客户ID	是	否	
        月结客户总欠款	是	否	
        创建时间	是	否	
        最后修改时间	是	否	
        
        客户开户银行
        字段	是否必填	是否列
        	备注
        其他支行名称（手动输入）	是	否	
        开户帐号	是	否	
        开户人姓名	是	否	
        开户行所在城市编码	是	否	
        开户行省份编码	是	否	
        开户行编码	是	否	
        手机号码	是	否	
        帐号与客户关系	是	否	
        支行编码	是	否	
        备注	是	否	
        是否默认帐号	是	否	整数类型，0、否；1、是
        
        客户接送货地址
        字段	是否必填	是否列表	备注
        详细地址	是	否	
        邮编	是	否	
        省份	是	否	
        城市	是	否	
        区县	是	否	
        地址类型	是	否	整数类型，1、发货地址；2、接货地址；3、接送货地址；
        状态	是	否	整数类型，1、正常；2、审批中；3、无效；
        创建时间	是	否	
        最后修改时间	是	否	
        
        
        客户联系人信息
        字段	是否必填	是否列表	备注
        性别	是	否	整数类型，1、男；2、女；
        办公电话	是	否	
        移动电话	是	否	
        传真	是	否	
        联系人地址	是	否	
        E_Mail	是	否	
        邮编	是	否	
        生日	是	否	
        身份证号	是	否	
        个人爱好	是	否	
        是否接收邮件	是	否	整数类型，0、否；1、是；
        是否接收短信	是	否	整数类型，0、否；1、是；
        是否接收信件	是	否	整数类型，0、否；1、是；
        获知公司途径	是	否	
        民族	是	否	
        籍贯	是	否	
        职务	是	否	
        任职部门	是	否	
        联系人名称	是	否	
        描述	是	否	
        创建时间	是	否	
        最后修改时间	是	否	
        
        客户合同信息
        字段	是否必填	是否列表	备注
        付款方式	是	否	整数类型，1、月结；2、无；
        申请欠款额度	是	否	
        合同月结天数	是	否	
        协议联系人姓名	是	否	
        联系人固定电话	是	否	
        联系人手机	是	否	
        联系人详细地址	是	否	
        对账日期	是	否	
        开发票日期	是	否	
        结款日期	是	否	
        合同起始日期	是	否	
        合同到期日期	是	否	
        申请事由	是	否	
        所属部门标杆编码	是	否	
        合同适用部门	是	是	参见【合同适用部门】
        是否折扣	是	否	整数类型，0、否；1、是；
        合同状态	是	否	整数类型，1、审批中；2、生效；3、失效；
        合同主体	是	否	
        注册资金	是	否	
        原合同编号	是	否	
        合同编号	是	否	
        走货名称	是	否	
        客户全称	是	否	
        协议联系人	是	否	参见【客户联系人信息】
        优惠类型	是	否	整数类型，1、月发月送；2、价格折扣；
        优惠信息	是	否	参见【优惠信息】
        创建时间	是	否	
        最后修改时间	是	否	
        
        合同适用部门
        字段	是否必填	是否列表	备注
        对应部门标杆编码	是	否	
        开始时间	是	否	
        结束时间	是	否	
        工作流号	是	否	
        OA审批状态	是	否	
        审批人	是	否	
        工作流类型	是	否	整数类型，1、新增合同；2、改签合同；3、作废合同；4、归属变更；5、解除绑定；6、绑定合同；
        状态	是	否	数字类型，0、无效；1、有效；
        是否归属部门	是	否	数字类型，0、否；1、是；
        创建时间	是	否	
        最后修改时间	是	否	
        
        优惠信息
        字段	是否必填	是否列表	备注
        运费折扣费率	是	否	
        代收货款费率	是	否	
        保价费率	是	否	
        接货费率	是	否	
        送货费率	是	否	
        创建时间	是	否	
        最后修改时间	是	否	
        
        联系人接送货地址
        字段	是否必填	是否列表	备注
        接送货地址	是	否	参见【客户接送货地址】
        联系人	是	否	参见【客户联系人信息】
        地址类型	是	否	整数类型，1、发货地址；2、接货地址；3、接送货地址；
        地址	是	否	
        偏好起始时间	是	否	
        偏好结束时间	是	否	
        发票要求	是	否	整数类型，1、不要发票；2、定额发票；3、增值税普通发票；4、增值税专用发票；
        停车费用	是	否	
        付款方式	是	否	整数类型，1、现金；2、月结；3、到付；4、在线支付；
        是否送货上楼	是	否	整数类型，0、否；1、是；
        其它要求	是	否	
        是否主地址	是	否	整数类型，0、否；1、是；
        状态	是	否	整数类型，1、正常；2、审批中；3、无效；
        创建时间	是	否	
        最后修改时间	是	否	
        
        返回参数：
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        CRM客户ID	是	否	
        成功或失败的标识	是	否	整数类型，0、失败；1、成功
        失败原因	否	否	如果处理失败，此字段为必填
        
        调用时效：立即
        交互模式：单向/回调
        是否需要顺序执行：否
        是否批量处理：否
        是否允许重复调用：否
        调用时段和调用量：00：00~24：00，一天调用8000次。
        高峰时段：08:00-12:00，13:30-17:30
        消息大小 ：一次1条
        时间响应要求：N/A
        安全性要求：无

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

import java.io.IOException;

import javax.xml.ws.Holder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.module.client.sync.domain.dto.CrmSyncRequest;
import com.deppon.crm.module.client.sync.domain.dto.CustCustbasedata;
import com.deppon.esb.header.ESBHeader;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.esb.util.ESBHeaderUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosFromCrmService;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.foss.ws.syncdata.ISyncDataService;
import com.deppon.foss.ws.syncdata.domain.Sync;
import com.deppon.foss.ws.syncdata.domain.SyncResponse;

/**
 * 同步CRM客户相关信息服务 WEB SERVICE服务端
 * 
 * 同步客户信息接口
        FOSS接送货子系统中的开单用例、FOSS结算子系统中的退代收货款等用例都需要客户及客户关联信息，该信息存储在CRM系统中，为满足FOSS系统各功能模块对于该部分信息的需求，需将客户及客户关联信息同步至FOSS系统
        FOSS系统	接口调用者	CRM系统
        前置条件：
        1、	FOSS系统正常运行；
        2、	CRM系统能正常调用本接口；
        
        接口场景：
        1、	CRM系统中客户或客户关联信息发生更改（新增、修改、删除），则调用此接口，传入客户及客户关联信息和更改类型（新增、修改、删除）；
        
        后置动作：
        1、	CRM系统记录接口调用日志；
        2、	FOSS系统记录接口被调用日志；
        
        接口异常处理：
        接口调用发生异常需要抛出，由CRM系统统一处理异常；
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-23
 * 上午11:34:21
 * </p>
 * 
 * @author 094463-foes-xieyantao
 * @date 2012-11-23 上午11:34:21
 * @since
 * @version
 */
public class SynCustomerInfoService implements ISyncDataService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SynCustomerInfoService.class);
    
    /**
     * 从CRM系统同步客户主数据业务处理接口
     */
    private ISyncInfosFromCrmService syncInfosFromCrmService;
    
    /**
     * 业务互斥锁服务.
     */
    private IBusinessLockService businessLockService;
    
   
    /**
     * @param syncInfosFromCrmService the syncInfosFromCrmService to set
     */
    public void setSyncInfosFromCrmService(
    	ISyncInfosFromCrmService syncInfosFromCrmService) {
        this.syncInfosFromCrmService = syncInfosFromCrmService;
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
     * <p>同步客户信息主数据</p>.
     * 
     * 同步客户信息接口
        FOSS接送货子系统中的开单用例、FOSS结算子系统中的退代收货款等用例都需要客户及客户关联信息，该信息存储在CRM系统中，为满足FOSS系统各功能模块对于该部分信息的需求，需将客户及客户关联信息同步至FOSS系统
        FOSS系统	接口调用者	CRM系统
                        前置条件：
                        1、	FOSS系统正常运行；
                        2、	CRM系统能正常调用本接口；
                        
                        接口场景：
                        1、	CRM系统中客户或客户关联信息发生更改（新增、修改、删除），则调用此接口，传入客户及客户关联信息和更改类型（新增、修改、删除）；
                        
                        后置动作：
                        1、	CRM系统记录接口调用日志；
                        2、	FOSS系统记录接口被调用日志；
                        
                        接口异常处理：
                        接口调用发生异常需要抛出，由CRM系统统一处理异常；
     *
     * @param syncRequest 
     * @param esbHeader 
     * @return 
     * @throws CommonException 
     * @author 094463-foss-xieyantao
     * @date 2012-12-25 上午10:34:24
     * @see com.deppon.foss.ws.syncdata.ISyncDataService#sync(com.deppon.foss.ws.syncdata.domain.Sync, javax.xml.ws.Holder)
     */
    @Override
    public SyncResponse sync(Sync syncRequest, Holder<ESBHeader> esbHeader)
	    throws CommonException {
	// 信息头
	ESBHeaderUtil.processEsbHeaderForResponse(esbHeader);
	
	// 获取json Request字符串
	String jsonRequest = syncRequest.getJsonRequest();
	ObjectMapper mapper = new ObjectMapper();
	// 把json字符串转换成CrmSyncRequest对象
	CrmSyncRequest crmSyncRequest;
	CustCustbasedata custbasedata = null;
	try {
	    crmSyncRequest = mapper.readValue(jsonRequest,CrmSyncRequest.class);
	    // 获取客户基本信息
	    custbasedata = crmSyncRequest.gettCustCustbasedata();
	    
	} catch (JsonParseException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw new CommonException(e.getMessage(), e);
	} catch (JsonMappingException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw new CommonException(e.getMessage(), e);
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	    throw new CommonException(e.getMessage(), e);
	}
	SyncResponse response = new SyncResponse();
	if(null != custbasedata){
	    // 业务锁
	    MutexElement mutex = new MutexElement(String.valueOf(custbasedata
		    .getFid()), "CUSTOMER_CRMID",
		    MutexElementType.CUSTOMER_CRMID);
	    LOGGER.info("开始加锁：" + mutex.getBusinessNo());
	    boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
	    if (result) {
		LOGGER.info("成功加锁：" + mutex.getBusinessNo());
	    } else {
		LOGGER.info("失败加锁：" + mutex.getBusinessNo());
		response.setReturn(false);
		return response;
	    }

	    // 客户同步主数据处理
	    response = syncInfosFromCrmService.syncInfo(crmSyncRequest);
	    
	    LOGGER.info("开始解锁：" + mutex.getBusinessNo());
	    // 解业务锁
	    businessLockService.unlock(mutex);
	    LOGGER.info("完成解锁：" + mutex.getBusinessNo());

	    return response;
	}else {
	    LOGGER.info("客户主数据为空！.......");
	    response.setReturn(false);
	    return response;
	}
	
    }
    
}
