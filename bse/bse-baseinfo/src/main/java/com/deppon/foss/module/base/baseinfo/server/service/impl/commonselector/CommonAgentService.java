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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/CommonAgentService.java
 * 
 * FILE NAME        	: CommonAgentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
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
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonAgentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;
/**
 * 用来操作交互“航空公司代理人”的数据库对应数据访问Dao调用的Service接口实现类：SUC-61 
 * 
 * 
 * 
 * 
 * 该用例可对航空公司代理人基础资料进行新增、修改、作废、查询操作等，该基础资料主要记录航空公司代理人的基础信息。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	航空公司基础资料齐备
        2、	行政组织基础资料齐备	SUC-42 新增_修改_删除_查询航空公司
        SUC-85 修改_查询行政组织业务属性
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供航空公司代理人基础资料查询	SUC-247-录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司代理人”进行新增，修改，作废，查询操作。
 * 
 *  SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
    SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
    SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
    SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取

 * 
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-16 上午8:38:20</p>
 * 
 * 
 * @author 100847-foss-lifanghong
 * 
 * 
 * @date 2013-4-25
 * 
 * @since
 * 
 * 
 * @version
 */
public class CommonAgentService implements ICommonAgentService {
    /**
     * 日志.
     */
    //private static final Logger LOGGER = LoggerFactory.getLogger(CommonAgentService.class);
    /**
     * "航空公司代理人"Dao.
     */
    private ICommonAgentDao commomAgentDao;
    
  
  
    @Override
    @Transactional(readOnly = true)
    public List<AgentEntity> queryAgentListBySelectiveCondition(AgentEntity agent, int offset, int limit) {
    		return commomAgentDao.queryAirlinesAgentListBySelectiveCondition(agent, offset, limit);

    }
    
    @Override
    @Transactional(readOnly = true)
    public long queryAgentRecordCountBySelectiveCondition(AgentEntity agent) {
    		return commomAgentDao.queryAirlinesAgentRecordCountBySelectiveCondition(agent);
    }

	public ICommonAgentDao getCommomAgentDao() {
		return commomAgentDao;
	}

	public void setCommomAgentDao(ICommonAgentDao commomAgentDao) {
		this.commomAgentDao = commomAgentDao;
	}
    
    
}
