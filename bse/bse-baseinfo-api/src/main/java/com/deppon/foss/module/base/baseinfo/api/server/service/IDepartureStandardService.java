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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IDepartureStandardService.java
 * 
 * FILE NAME        	: IDepartureStandardService.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;


/**
 * 发车标准服务类
 * @author foss-zhujunyong
 * @date Oct 26, 2012 3:14:20 PM
 * @version 1.0
 */
public interface IDepartureStandardService extends IService{
    /**
     * 
     * <p>添加发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:01 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity addDepartureStandard(DepartureStandardEntity departureStandard);
    
    /**
     * 
     * <p>作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:13 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity deleteDepartureStandard(DepartureStandardEntity departureStandard);

    /**
     * 
     * <p>更新发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:28 PM
     * @param departureStandard
     * @return
     * @see
     */
    DepartureStandardEntity updateDepartureStandard(DepartureStandardEntity departureStandard);

    /**
     * 
     * <p>查询发车标准详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:19:45 PM
     * @param id
     * @return
     * @see
     */
    DepartureStandardEntity queryDepartureStandardById(String id);

    /**
     * 
     * <p>查询特定线路下的发车标准列表</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:20:00 PM
     * @param lineVirtualCode
     * @return
     * @see
     */
    List<DepartureStandardEntity> queryDepartureStandardListByLineVirtualCode(String lineVirtualCode);

    /**
     * 
     * <p>查询特定线路下指定班次的发车标准实体</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 2:37:03 PM
     * @param lineVirtualCode 线路虚拟编码
     * @param sequence 发车班次
     * @return
     * @see
     */
    DepartureStandardEntity queryDepartureStandardByLineVirtualCodeAndSequence(String lineVirtualCode, int sequence);
 
    /**
     * 
     * <p>根据线路作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 3:32:49 PM
     * @param departureStandard
     * @return
     * @see
     */
    int deleteDepartureStandardByLine(String lineVirtualCode, String modifyUser);
    
    
    /**
     * 
     * <p>找出指定线路下离传入时间（只取时分）最近的一个发车标准</p>
     * 该发车标准要求具备 
     * @author foss-zhujunyong
     * @date Nov 14, 2012 5:34:22 PM
     * @param line 线路实体
     * @param time 只取时分
     * @return
     * @see
     */
    DepartureStandardEntity queryDepartureStandardByLineAndTime(LineEntity line, Date time);
    
    /**
     *     DMANA-2870
     *     新增始发线路、到达线路时生成默认发车标准
     *     author:187862-dujunhui
     *     date:2014-08-20 下午1:53
     * 
     * **/
    public DepartureStandardEntity addDefaultDepartureStandard(
    	    DepartureStandardEntity departureStandard);
    
    
}
