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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/INetGroupDao.java
 * 
 * FILE NAME        	: INetGroupDao.java
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
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity;

/**
 * 
 * 网点组数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午7:54:25, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午7:54:25
 * @since
 * @version
 */
public interface INetGroupDao {

    /**
     * 
     * <p>(方法详细描述说明、方法参数的具体涵义)通过主键获取网点组</p> 
     * @author foss-sunrui
     * @date 2012-10-18 下午7:23:27
     * @param id
     * @return
     * @see
     */
    NetGroupEntity queryByPrimaryKey(String id);

    /**
     * 
     * <p>通过营业部编码和网点组类型获取网点组</p> 
     * @author foss-sunrui
     * @date 2012-10-18 下午7:23:07
     * @param map
     * @return
     * @see
     */
    NetGroupEntity queryBySaleDept(Map<String,String> map);
    /**
     * 
     * <p>根据出发营业部和到达营业部找出一条唯一的走货路径</p> 
     * @author foss-jiangfei
     * @date Nov 2, 2012 11:52:05 AM
     * @param sourceSaleCode 出发营业部部门编码
     * @param targetSaleCode 到达营业部部门编码
     * @return 走货路径列表
     * @see
     */
    List<String> queryFreightRouteCode(String sourceSaleCode, String targetSaleCode);
    
    /** 
     * <p>根据出发营业部和到达营业部查询网点组记录</p> 
     * @author foss-jiangfei
     * @date Nov 4, 2012 4:41:50 PM
     * @param netGroup
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao#queryNetGroupBySourceTarget(com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupEntity)
     */

    List<NetGroupEntity> queryNetGroupBySourceTarget(
	    NetGroupEntity netGroup);
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addNetGroup(NetGroupEntity netGroup);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateNetGroup(NetGroupEntity netGroup);

	/**
	 * @param netGroupEntity
	 */
	void delete(NetGroupEntity netGroupEntity);
}