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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PlatformDao.java
 * 
 * FILE NAME        	: PlatformDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonPlatformDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 月台dao
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zhujunyong,date:Oct 12, 2012 11:22:00 AM</p>
 * @author zhujunyong
 * @date Oct 12, 2012 11:22:00 AM
 * @since
 * @version
 */
public class CommonPlatformDao extends SqlSessionDaoSupport implements ICommonPlatformDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".platforminfo.";
    
  
   
    /** 
     * <p>按条件查找月台列表</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#queryPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PlatformEntity> queryPlatformListByCondition(
	    PlatformEntity platform, int start, int limit) {
	platform.setActive(FossConstants.ACTIVE);
	return (List<PlatformEntity>)getSqlSession().selectList(NAMESPACE + "queryPlatformListByCondition", platform, new RowBounds(start, limit));
    }

    /** 
     * <p>按条件查找月台数量</p> 
     * @author zhujunyong
     * @date Oct 12, 2012 11:22:01 AM
     * @param platform
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPlatformDao#countPlatformListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity)
     */
    @Override
    public long countPlatformListByCondition(PlatformEntity platform) {
	platform.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "countPlatformListByCondition", platform);
    }

   
}
