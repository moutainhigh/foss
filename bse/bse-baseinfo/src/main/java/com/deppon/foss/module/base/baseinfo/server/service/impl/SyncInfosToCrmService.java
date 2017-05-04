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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SyncInfosToCrmService.java
 * 
 * FILE NAME        	: SyncInfosToCrmService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.inteface.foss.domain.OrderRightInfo;
import com.deppon.crm.inteface.foss.domain.OrderRightRequest;
import com.deppon.esb.api.ESBJMSAccessor;
import com.deppon.esb.api.domain.AccessHeader;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.pojo.transformer.jaxb.OrderRightRequestTrans;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosToCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SyncInfosException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SynchronousExternalSystemException;
/**
 * CRM同步开单权限信息服务接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-29
 * 下午4:46:57
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-29 下午4:46:57
 * @since
 * @version
 */
public class SyncInfosToCrmService implements ISyncInfosToCrmService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncInfosToCrmService.class);

    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * <p>
     * 发送开单权限信息服务
     * </p>.
     *
     * @param entity 
     * @param operateType 操作类型：只有新增（insert）、删除（delete）两种状态
     * @throws Exception 
     * @author 094463-foss-xieyantao
     * @date 2012-11-29 下午4:46:57
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosToCrmService#sendWebsitInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity,
     * int)
     */
    @Override
    public void sendBillingPermtInfos(String salesCode, String billingGroupCode, 
	    String operateType){
	LOGGER.info("send info start.............");
	if (StringUtils.isBlank(salesCode) || StringUtils.isBlank(billingGroupCode) || StringUtils.isBlank(operateType)) {
	    throw new SyncInfosException("传入的参数不允许为空！");
	}
	    // 部门标杆编码
	    String deptUnifiedCode = getUnifiedCode(salesCode);
	    // 开单组标杆编码
	    String groupUnifiedCode = getUnifiedCode(billingGroupCode);
	    OrderRightRequest request = new OrderRightRequest();
	    //把版本号作为businessId
	    Date now = new Date();
	    String businessId = String.valueOf(now.getTime());
	    OrderRightInfo info = new OrderRightInfo();
	    // 营业部门（部门标杆编码）
	    info.setDepartment(deptUnifiedCode);
	    info.setOperateTime(now);
	    
	    // 操作类型
	    info.setOperateType(operateType);
	    // 开单组（部门标杆编码）
	    info.setOrderTeam(groupUnifiedCode);

	    request.setOrderRightInfo(info);
	    AccessHeader header = buildHeader(businessId,
		    "ESB_FOSS2ESB_ORDER_RIGHT", "开单权限信息");
      try {
    	// 087584-foss-lijun
  	    LOGGER.info("发送营业部信息（开单权限）到CRM，请求报文：\n"
  		    + new OrderRightRequestTrans().fromMessage(request));
  	    ESBJMSAccessor.asynReqeust(header, request);
  	    LOGGER.info("send info end.............");
	} catch (ESBException e) {
		throw new SynchronousExternalSystemException(e.getMessage());
	}
	    
    }

    /**
     * <p>
     * 返回ESB请求头消息
     * </p>.
     *
     * @param businessId 
     * @param ServiceCode 
     * @param desc 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-29 下午3:16:29
     * @see
     */
    private AccessHeader buildHeader(String businessId, String serviceCode,
	    String desc) {
	AccessHeader header = new AccessHeader();
	header.setBusinessId(businessId);
	header.setEsbServiceCode(serviceCode);
	header.setVersion("1.0");
	header.setBusinessDesc1(desc);

	return header;
    }

    /**
     * <p>
     * 根据部门编码查询部门标杆编码
     * </p>.
     *
     * @param deptCode 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-29 下午4:14:23
     * @see
     */
    private String getUnifiedCode(String deptCode) {
	if (StringUtils.isNotBlank(deptCode)) {
	    // 通过部门编码获得该部门的标杆编码
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCodeClean(deptCode);
	    if (null != org) {
		return org.getUnifiedCode();
	    } else {
		return null;
	    }
	} else {
	    return null;
	}
    }

}
