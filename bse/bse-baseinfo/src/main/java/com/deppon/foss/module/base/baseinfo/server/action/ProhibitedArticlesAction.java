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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ProhibitedArticlesAction.java
 * 
 * FILE NAME        	: ProhibitedArticlesAction.java
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
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProhibitedArticlesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProhibitedArticlesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ProhibitedArticlesVo;
/**
 * 禁运物品action
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class ProhibitedArticlesAction extends AbstractAction {
	/**
	 * 下面是声明的属性
	 */
	private static final long serialVersionUID = -802246567875971335L;
	//禁运物品service接口
    private IProhibitedArticlesService prohibitedArticlesService;
	//禁运物品 action使用VO
	private ProhibitedArticlesVo objectVo = new ProhibitedArticlesVo();
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProhibitedArticlesAction.class);
	/**
     * <p>查询禁运物品</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
	public String queryProhibitedArticlesExactByEntity(){
		ProhibitedArticlesEntity entityCondition = objectVo.getProhibitedArticlesEntity();
		// 返回的结果显示在表格中：模糊查询 queryProhibitedArticlesByEntity queryProhibitedArticlesByEntityCount
    	objectVo.setProhibitedArticlesEntityList( prohibitedArticlesService.queryProhibitedArticlesExactByEntity(entityCondition,start, limit));
    	totalCount = prohibitedArticlesService.queryProhibitedArticlesExactByEntityCount(entityCondition);
    	return returnSuccess();
	}
    /**
     * 作废禁运物品 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteProhibitedArticles() {
    	try {
        	objectVo.setProhibitedArticlesEntity(prohibitedArticlesService.deleteProhibitedArticles(objectVo.getProhibitedArticlesEntity()));
            return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 批量作废禁运物品 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteProhibitedArticlesMore() {
    	try {
        	objectVo.setProhibitedArticlesEntity(prohibitedArticlesService.deleteProhibitedArticlesMore(objectVo.getCodeStr(), "1"));
            return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 修改禁运物品 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String updateProhibitedArticles() {
    	try {
        	objectVo.setProhibitedArticlesEntity(prohibitedArticlesService.updateProhibitedArticles(objectVo.getProhibitedArticlesEntity()));
            return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 新增禁运物品 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String addProhibitedArticles() {
    	try {
        	objectVo.setProhibitedArticlesEntity(prohibitedArticlesService.addProhibitedArticles(objectVo.getProhibitedArticlesEntity()));
            return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }

	/**
     * <p>禁运物品  是否重复</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String prohibitedArticlesIsExist() {
    	try {
        	objectVo.setProhibitedArticlesEntityList( prohibitedArticlesService.queryProhibitedArticlesExactByEntity(objectVo.getProhibitedArticlesEntity(),0, 1));
        	List<ProhibitedArticlesEntity> list = objectVo.getProhibitedArticlesEntityList();
        	objectVo.setProhibitedArticlesEntity((list.size()>0)?list.get(0):null);
        	return returnSuccess();
    	} catch (AirAgencyCompanyException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /*
     * =================================================================
     * 下面是get,set方法：
     */
	public ProhibitedArticlesVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(ProhibitedArticlesVo objectVo) {
		this.objectVo = objectVo;
	}
	public void setProhibitedArticlesService(
			IProhibitedArticlesService prohibitedArticlesService) {
		this.prohibitedArticlesService = prohibitedArticlesService;
	}

}
