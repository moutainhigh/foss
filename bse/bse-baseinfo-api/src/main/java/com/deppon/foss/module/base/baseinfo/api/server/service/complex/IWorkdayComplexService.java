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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/complex/IWorkdayComplexService.java
 * 
 * FILE NAME        	: IWorkdayComplexService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service.complex;

import java.util.Date;


public interface IWorkdayComplexService {

    /**
     * 判断给定的日期是否是工作
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午5:19:26
     */
    boolean isWorkday(Date date) ;
    
    
    /**
     * 查询给定的两个日期之间的工作日天数
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-28 下午9:40:53
     */
    int countWorkday(Date start, Date end);
}
