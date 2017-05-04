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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IContactWithAirlinesService.java
 * 
 * FILE NAME        	: IContactWithAirlinesService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactWithAirlinesException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
/**
 * 用来操作交互“正单交货人”的数据库对应数据访问Service接口：SUC-37
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午9:31:45</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午9:31:45
 * @since
 * @version
 */
public interface IContactWithAirlinesService extends IService {

    /**
     * <p>新增一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:16
     * @param contactAirlines “正单交货人”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int addContactWithAirlines(ContactAirlinesEntity contactAirlines, String createUser, boolean ignoreNull) throws ContactWithAirlinesException;

    /**
     * <p>根据“正单交货人”记录唯一标识作废（物理删除）一条“正单交货人”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:02
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @see
     */
     int deleteContactWithAirlines(List<String> ids, String modifyUser) throws ContactWithAirlinesException;

    /**
     * <p>修改一个“正单交货人”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:07
     * @param contactAirlines “正单交货人”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @see
     */
     int updateContactWithAirlines(ContactAirlinesEntity contactAirlines, String modifyUser,  boolean ignoreNull) throws ContactWithAirlinesException;
    

    /**
     * <p>根据“正单交货人”记录唯一标识查询出一条“正单交货人”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午11:53:11
     * @param id 记录唯一标识
     * @return “正单交货人”实体
     * @see
     */
     ContactAirlinesEntity queryContactWithAirlinesBySelective(String id) throws ContactWithAirlinesException;
    
    /**
     * <p>根据条件有选择的检索出符合条件一个的“正单交货人”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-14 上午11:23:53
     * @param airport 以“正单交货人”实体承载的条件参数实体
     * @return 符合条件的“正单交货人”实体
     * @throws LeasedDriverException
     * @see
     */
     ContactAirlinesEntity queryContactWithAirlinesBySelective(ContactAirlinesEntity contactAirlines) throws ContactWithAirlinesException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param contactAirlines 以“机型信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象 
     * @see
     */
     PaginationDto queryContactAirlinesListBySelectiveCondition(ContactAirlinesEntity contactAirlines, int offset, int limit) throws ContactWithAirlinesException;
}
