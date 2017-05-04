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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/ILabeledGoodService.java
 * 
 * FILE NAME        	: ILabeledGoodService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * 货签服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:33:40, </p>
 * @author foss-sunrui
 * @date 2012-10-30 下午3:33:40
 * @since
 * @version
 */
public interface ILabeledGoodService extends IService {

 
    /**
     * <p>(插入货签信息)</p> 
     * @author foss-jiangfei
     * @date 2012-11-7 下午1:50:31
     * @param record
     * @return
     * @see
     */
    ResultDto insertSerialNo(LabeledGoodEntity labeledGoods, List<String> serialNoList);
    
    /**
     * 
     * <p>通过运单号查询最大的序列号</p> 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo);
   
    
}