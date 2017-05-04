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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISyncInfosToCrmService.java
 * 
 * FILE NAME        	: ISyncInfosToCrmService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.crm.module.client.sync.domain.dto.CrmSyncRequest;
import com.deppon.esb.inteface.domain.crm.OrigCustSyncResponse;
import com.deppon.esb.inteface.domain.crm.ScatterCustInfo;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.ws.syncdata.CommonException;
import com.deppon.foss.ws.syncdata.domain.SyncResponse;

/**
 * 从CRM系统同步客户主数据业务处理接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-5-16 下午3:19:01 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-5-16 下午3:19:01
 * @since
 * @version
 */
public interface ISyncInfosFromCrmService extends IService {
    
    /**
     * <p>同步客户主数据信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-6 下午10:18:14
     * @param crmSyncRequest
     * @return
     * @throws CommonException
     * @see
     */
    public SyncResponse syncInfo(CrmSyncRequest crmSyncRequest) throws CommonException;
    
    /**
     * <p>同步散客和散客账号信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-6 下午10:17:39
     * @param custInfo
     * @return
     * @see
     */
    public OrigCustSyncResponse syncNonfixedCustomer(ScatterCustInfo custInfo); 

}
