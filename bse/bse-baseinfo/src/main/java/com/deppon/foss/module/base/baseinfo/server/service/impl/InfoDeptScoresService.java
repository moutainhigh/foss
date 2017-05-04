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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/InfoDeptScoresService.java
 * 
 * FILE NAME        	: InfoDeptScoresService.java
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptScoresException;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.InfoDeptScoresDao;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“信息部得分”的数据库对应数据访问Service接口实现类：SUC-222
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-17 下午3:44:20</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-17 下午3:44:20
 * @since
 * @version
 */
public class InfoDeptScoresService implements IInfoDeptScoresService {

    //"信息部得分"Dao
    private InfoDeptScoresDao infoDeptScoresDao;
    
    /**
     * <p>新增一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:43
     * @param infoDeptScores “信息部得分”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws InfoDeptScoresException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresService#addInfoDeptScores(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity, java.lang.String, boolean)
     */
    @Override
    public int addInfoDeptScores(InfoDeptScoresEntity infoDeptScores,
	    String createUser, boolean ignoreNull) throws InfoDeptScoresException {
	if (null == infoDeptScores) {
	    throw new InfoDeptScoresException("", "信息部得分为空");
	}
	if (StringUtils.isBlank(infoDeptScores.getInfodeptId())) {
	    throw new InfoDeptScoresException("", "信息部为空");
	}
	if (ignoreNull) {
	    infoDeptScoresDao.addInfoDeptScoresBySelective(infoDeptScores);
	}else{
	    infoDeptScoresDao.addInfoDeptScores(infoDeptScores);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“信息部得分”实体入库（忽略实体中是否存在空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:48
     * @param infoDeptScores “信息部得分”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws InfoDeptScoresException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresService#updateInfoDeptScores(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity, java.lang.String, boolean)
     */
    @Override
    public int updateInfoDeptScores(InfoDeptScoresEntity infoDeptScores, String modifyUser,
	    boolean ignoreNull) throws InfoDeptScoresException {
	
	if (null == infoDeptScores) {
	    throw new InfoDeptException("", "信息部为空");
	}
	if (StringUtils.isBlank(infoDeptScores.getId())) {
	    throw new InfoDeptException("", "ID为空");
	}
	if (StringUtils.isBlank(infoDeptScores.getInfodeptId())) {
	    throw new InfoDeptScoresException("", "信息部为空");
	}

	//验证对应身份证号"信息部"是否已经存在
	InfoDeptScoresEntity oldInfoDeptScores, tempInfoDeptScores = new InfoDeptScoresEntity();
	tempInfoDeptScores.setId(infoDeptScores.getId());
	oldInfoDeptScores = infoDeptScoresDao.queryInfoDeptScoresBySelective(tempInfoDeptScores);
	
	if (null == oldInfoDeptScores) {
	    throw new InfoDeptException("", "信息部不存在");
	}else{
	    infoDeptScores.setModifyUser(modifyUser);
	    if (ignoreNull) {
		infoDeptScoresDao.updateInfoDeptScoresBySelective(infoDeptScores);
	    }else{
		infoDeptScoresDao.updateInfoDeptScores(infoDeptScores);
	    }
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“信息部”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午11:02:46
     * @param ownTruck 以“信息部”实体承载的条件参数实体
     * @return 分页的Action和Service通讯封装对象 
     * @throws InfoDeptScoresException 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresService#queryInfoDeptScoresListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity)
     */
    @Override
    public List<InfoDeptScoresEntity> queryInfoDeptScoresListBySelectiveCondition(
	    InfoDeptScoresEntity infoDeptScores) throws InfoDeptScoresException {
	if (null == infoDeptScores) {
	    throw new InfoDeptException("", "信息部为空");
	}
	return infoDeptScoresDao.queryInfoDeptScoresListBySelective(infoDeptScores);
    }
    
    /**
     * @param infoDeptScoresDao the infoDeptScoresDao to set
     */
    public void setInfoDeptScoresDao(InfoDeptScoresDao infoDeptScoresDao) {
        this.infoDeptScoresDao = infoDeptScoresDao;
    }
}
