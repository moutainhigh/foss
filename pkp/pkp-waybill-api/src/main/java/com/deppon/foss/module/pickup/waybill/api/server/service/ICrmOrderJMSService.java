/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/ICrmOrderJMSService.java
 * 
 * FILE NAME        	: ICrmOrderJMSService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaAppInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmModifyInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * CRM订单数据JMS服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-22 下午5:23:50, </p>
 * @author foss-sunrui
 * @date 2012-11-22 下午5:23:50
 * @since
 * @version
 */
public interface ICrmOrderJMSService extends IService {

    /**
     * 
     * <p>更新订单状态</p> 
     * @author foss-sunrui
     * @date 2012-11-22 下午5:24:31
     * @param request
     * @return
     * @see
     */
    ResultDto sendModifyOrder(CrmModifyInfoDto request);
    
    /**
     * 零担电子运单推送消息获取组装信息(虽然返回类为CRM，但实际上信息已经改为推送OMS了)
     * @param orderNo
     * @param WaybillNo
     * @param exception (Y/N)
     * @param exceptionCode 异常编码
     * @return 推送消息实体
     */ 
    void pushOmsOrderInfoStatust(String orderNo,String waybillNo,String logType,PdaAppInfoEntity pdaAppInfo,String waybillStatus,String driverCode,String exceptionCode,String exceptionMessage);
    
}