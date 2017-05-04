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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IBankDao.java
 * 
 * FILE NAME        	: IBankDao.java
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

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;


/**
 * 快递代理理公增值服務Service接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-zdp,date:2013-7-18 下午2:55:11 </p>
 * @author 094463-foss-zdp
 * @date 2013-7-18 下午2:55:11
 * @since
 * @version
 */
public interface IPartbussValueAddService extends IService{
    
    
    /**
     * 根快递代理公司code 和 開單日期 查詢 增值服務信息，計算幾個使用  
     * @author zhangdongping
     * @date Jul 25, 2013 2:38:28 PM
     * @param expressPartbussCode
     * @param billdate
     * @param districtCode 行政區域code
     * @return
     * @see
     */
    PartbussValueAddEntity queryInfosByPartCode(String  expressPartbussCode,Date billdate,String districtCode);
    
    
    
   
}
