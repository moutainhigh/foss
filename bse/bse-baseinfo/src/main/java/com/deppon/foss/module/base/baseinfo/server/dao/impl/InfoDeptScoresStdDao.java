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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/InfoDeptScoresStdDao.java
 * 
 * FILE NAME        	: InfoDeptScoresStdDao.java
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresStdDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresStdEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“信息部基础标准”的数据库对应数据访问DAO接口实现类：SUC-222 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-25 下午3:29:19</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-25 下午3:29:19
 * @since
 * @version
 */
public class InfoDeptScoresStdDao extends SqlSessionDaoSupport implements IInfoDeptScoresStdDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".infodeptscoresstd";
    
    /**
     * <p>根据条件有选择的统计出符合条件的“信息部基础标准”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDeptScores 以“信息部基础标准”实体承载的条件参数实体
     * @return 符合条件的“信息部基础标准”实体记录条数
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresStdDao#queryInfoDeptScoresStdListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresStdEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<InfoDeptScoresStdEntity> queryInfoDeptScoresStdListBySelective(
	    InfoDeptScoresStdEntity infoDeptScoresStd) {
	//强制设置为只查询“启用”的记录
	infoDeptScoresStd.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryInfoDeptScoresStdListBySelective", infoDeptScoresStd);
    }

}
