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
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/crm/itf/jms/SendWebsitsTest.java
 * 
 * FILE NAME        	: SendWebsitsTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.crm.itf.jms;

import org.junit.Before;
import org.junit.Test;
import com.deppon.crm.inteface.foss.domain.SiteReceiveRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.api.util.ESBInitUtil;

/**
 * 发送网点信息接口单元测试
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-28
 * 上午10:43:24
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-28 上午10:43:24
 * @since
 * @version
 */
public class SendWebsitsTest {

    @Before
    public void init() throws Exception {
	ESBInitUtil util = new ESBInitUtil();
	util.process();
    }

    /**
     * <p>
     * 发送网点信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2012-11-28 上午10:45:19
     * @throws Exception
     * @see
     */
    @Test
    public void sendWebsitInfos() throws Exception {

	SiteReceiveRequest request = new SiteReceiveRequest();
	String deptCode = "0000122";
	request.setDeptCode(deptCode);
	request.setDeptName("青浦区徐泾营业部");
	request.setDeptProvince("上海市");
	request.setDeptCity("上海市");
	request.setIsAB(false);
	request.setDeptArea("青浦区徐泾镇");
	request.setDeptAddress("上海市青浦区徐泾镇明珠路1108号");
	request.setDescript("");
	request.setContactWay("");
	request.setLeaveOutDept("上海外场");
	request.setLeaveMiddleChange("");
	request.setIsUsed(true);
	request.setSuperiorArea("青浦区徐泾镇");
	request.setIsOpen(true);
	request.setOrganisationId("0998838883");
	request.setIsArrived(true);
	request.setIsLeave(true);
	request.setIsSendToHome(true);
	request.setIsGetBySelf(true);
	request.setIsOutSend(true);
	request.setIsCarTeam(false);
	request.setStandardCode("9988090303");
	request.setHandType("insert");
	

	AccessHeader header = buildHeader(deptCode);

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
	header.setEsbServiceCode("ESB_FOSS2ESB_RECEIVE_SITE");
	header.setVersion("1.0");
	header.setBusinessDesc1("网点信息");

	return header;

    }
}
