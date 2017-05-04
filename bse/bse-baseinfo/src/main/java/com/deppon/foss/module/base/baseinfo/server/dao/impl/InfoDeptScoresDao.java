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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/InfoDeptScoresDao.java
 * 
 * FILE NAME        	: InfoDeptScoresDao.java
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

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“信息部得分”的数据库对应数据访问DAO接口实现类：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 上午10:23:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 上午10:23:53
 * @since
 * @version
 */
public class InfoDeptScoresDao extends SqlSessionDaoSupport implements IInfoDeptScoresDao {
    
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".infodeptscores";

    /**
     * <p>新增一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:53
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#addInfoDeptScores(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    public int addInfoDeptScores(InfoDeptScoresEntity infoDeptScores) {
	infoDeptScores.setId(UUIDUtils.getUUID());
	infoDeptScores.setCreateDate(new Date());
	infoDeptScores.setActive(FossConstants.ACTIVE);
	infoDeptScores.setModifyUser(infoDeptScores.getCreateUser());
	infoDeptScores.setModifyDate(infoDeptScores.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addInfoDeptScores", infoDeptScores);
    }

    /**
     * <p>新增一个“信息部得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:51
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#addInfoDeptScoresBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    public int addInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores) {
	infoDeptScores.setId(UUIDUtils.getUUID());
	infoDeptScores.setCreateDate(new Date());
	infoDeptScores.setActive(FossConstants.ACTIVE);
	infoDeptScores.setModifyUser(infoDeptScores.getCreateUser());
	infoDeptScores.setModifyDate(infoDeptScores.getCreateDate());
	return getSqlSession().insert(NAMESPACE + ".addInfoDeptScoresBySelective", infoDeptScores);
    }

    /**
     * <p>根据“信息部得分”记录唯一标识删除（物理删除）一条“信息部得分”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:42
     * @param id 记录唯一标识
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#deleteInfoDeptScores(java.lang.String)
     */
    @Override
    public int deleteInfoDeptScores(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteInfoDeptScores", id);
    }

    /**
     * <p>修改一个“信息部得分”实体入库 （只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:47
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#updateInfoDeptScoresBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    public int updateInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores) {
	infoDeptScores.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateInfoDeptScoresBySelective", infoDeptScores);
    }

    /**
     * <p>修改一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:45
     * @param infoDeptScores “信息部得分”实体
     * @return 影响记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#updateInfoDeptScores(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    public int updateInfoDeptScores(InfoDeptScoresEntity infoDeptScores) {
	infoDeptScores.setModifyDate(new Date());
	return getSqlSession().update(NAMESPACE + ".updateInfoDeptScores", infoDeptScores);
    }

    /**
     * <p>根据条件有选择的查询“信息部得分”唯一激活可用状态实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-30 下午8:32:32
     * @param infoDeptScores 以“信息部得分”实体承载的条件参数实体
     * @return 符合条件的“信息部得分”实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#queryInfoDeptScoresBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public InfoDeptScoresEntity queryInfoDeptScoresBySelective(InfoDeptScoresEntity infoDeptScores) {
	if(StringUtils.isBlank(infoDeptScores.getId())){
	    //强制设置为只查询“启用”的记录
	    infoDeptScores.setActive(FossConstants.ACTIVE);
	}
	//ID存在，则记录肯定唯一，忽略其他参数
	
	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	String statement = NAMESPACE + ".queryInfoDeptScoresListBySelective";
	List<InfoDeptScoresEntity> infoDeptScoresList = getSqlSession().selectList(statement, infoDeptScores, rowBounds);
	if (CollectionUtils.isEmpty(infoDeptScoresList)) {
	    return null;
	}
        return infoDeptScoresList.get(NumberConstants.NUMERAL_ZORE);
    }

    /**
     * <p>根据条件有选择的统计出符合条件的“信息部得分”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-25 上午10:15:40
     * @param infoDeptScores 以“信息部得分”实体承载的条件参数实体
     * @return 符合条件的“信息部得分”实体记录条数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IInfoDeptScoresDao#queryInfoDeptScoresListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<InfoDeptScoresEntity> queryInfoDeptScoresListBySelective(
	    InfoDeptScoresEntity infoDeptScores) {
	//强制设置为只查询“启用”的记录
	infoDeptScores.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryInfoDeptScoresListBySelective", infoDeptScores);
    }
}
