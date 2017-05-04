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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/crm/itf/jms/SendBillingPermissionTest.java
 * 
 * FILE NAME        	: SendBillingPermissionTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.crm.itf.jms;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.deppon.crm.inteface.foss.domain.OrderRightInfo;
import com.deppon.crm.inteface.foss.domain.OrderRightRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.api.util.ESBInitUtil;
import com.deppon.foss.util.UUIDUtils;

/**
 * 开单权限同步接口单元测试
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-28 上午11:12:06 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-28 上午11:12:06
 * @since
 * @version
 */
public class SendBillingPermissionTest {

    @Before
    public void init() throws Exception {
	ESBInitUtil util = new ESBInitUtil();
	util.process();
    }

    /**
     * <p>
     * 发送开单权限
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-28 上午10:45:19
     * @throws Exception
     * @see
     */
    @Test
    public void sendBillingPermission() throws Exception {

	OrderRightRequest request = new OrderRightRequest();
	String businessId = UUIDUtils.getUUID();
	OrderRightInfo info = new OrderRightInfo();
	info.setDepartment("0000122");
	info.setOperateTime(new Date());
	info.setOperateType("delete");
	info.setOrderTeam("000001222");
	
        request.setOrderRightInfo(info);
	AccessHeader header = buildHeader(businessId);

	ESBJMSAccessor.asynReqeust(header, request);
    }

    /**
     * <p>
     * 返回ESB请求头消息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-28 上午10:50:58
     * @param businessId
     * @return
     * @see
     */
    private AccessHeader buildHeader(String businessId) {

	AccessHeader header = new AccessHeader();
	header.setBusinessId(businessId);
	header.setEsbServiceCode("ESB_FOSS2ESB_ORDER_RIGHT");
	header.setVersion("1.0");
	header.setBusinessDesc1("开单权限信息");

	return header;

    }
}
