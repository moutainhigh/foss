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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IPartbussPriceCaculateService.java
 * 
 * FILE NAME        	: IPartbussPriceCaculateService.java
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
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPartBusinessPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPartBusinessPriceResultDto;


/**
 * 快递代理理公司运价计算Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-zhangdongping,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-zhangdongping
 * @date 2013-7-24 下午2:55:11
 * @since
 * @version
 */
public interface IPartbussPriceCaculateService extends IService{
    
    /**
     *快递代理价格计算接口，包括运费和增值服务 
     * @see
     */
    List<QueryPartBusinessPriceResultDto> caculateFee(List<QueryPartBusinessPriceDto> dto);
    
 
    
    
     
   
}
