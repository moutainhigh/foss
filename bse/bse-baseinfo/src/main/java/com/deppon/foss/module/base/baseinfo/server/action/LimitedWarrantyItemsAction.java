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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/limitedwarrantygoodsAction.java
 * 
 * FILE NAME        	: limitedwarrantygoodsAction.java
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
import com.deppon.foss.module.base.baseinfo.api.server.service.ILimitedWarrantyItemsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LimitedwarrantygoodsVo;

/**
 * 限保物品action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class LimitedWarrantyItemsAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 限保物品service接口
    private ILimitedWarrantyItemsService limitedWarrantyItemsService;

    // 限保物品 action使用VO
    private LimitedwarrantygoodsVo objectVo = new LimitedwarrantygoodsVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(LimitedWarrantyItemsAction.class);

    /**
     * <p>
     * 查询限保物品
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String queryLimitedWarrantyItemsExactByEntity() {
	LimitedWarrantyItemsEntity entityCondition = objectVo
		.getLimitedWarrantyItemsEntity();
	// 返回的结果显示在表格中：
	objectVo.setLimitedWarrantyItemsEntityList(limitedWarrantyItemsService
		.queryLimitedWarrantyItemsExactByEntity(entityCondition, start,
			limit));
	totalCount = limitedWarrantyItemsService
		.queryLimitedWarrantyItemsExactByEntityCount(entityCondition);
	return returnSuccess();
    }

    /**
     * 作废限保物品
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteLimitedWarrantyItems() {
	try {
	    objectVo.setLimitedWarrantyItemsEntity(limitedWarrantyItemsService
		    .deleteLimitedWarrantyItems(objectVo
			    .getLimitedWarrantyItemsEntity()));
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 批量作废限保物品
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-27 下午19:10:10
     * @return String
     * @see
     */
    public String deleteLimitedWarrantyItemsMore() {
	try {
	    objectVo.setLimitedWarrantyItemsEntity(limitedWarrantyItemsService
		    .deleteLimitedWarrantyItemsMore(objectVo.getCodeStr(), "1"));
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改限保物品
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String updateLimitedWarrantyItems() {
	try {
	    objectVo.setLimitedWarrantyItemsEntity(limitedWarrantyItemsService
		    .updateLimitedWarrantyItems(objectVo
			    .getLimitedWarrantyItemsEntity()));
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增限保物品
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-11-28 13:50:10
     * @return String
     * @see
     */
    public String addLimitedWarrantyItems() {
	try {
	    objectVo.setLimitedWarrantyItemsEntity(limitedWarrantyItemsService
		    .addLimitedWarrantyItems(objectVo
			    .getLimitedWarrantyItemsEntity()));
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 限保物品 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String limitedwarrantygoodsIsExist() {
	try {
	    objectVo.setLimitedWarrantyItemsEntityList(limitedWarrantyItemsService
		    .queryLimitedWarrantyItemsExactByEntity(
			    objectVo.getLimitedWarrantyItemsEntity(), 0, 1));
	    List<LimitedWarrantyItemsEntity> list = objectVo
		    .getLimitedWarrantyItemsEntityList();
	    objectVo.setLimitedWarrantyItemsEntity((list.size() > 0) ? list
		    .get(0) : null);
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
    public LimitedwarrantygoodsVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(LimitedwarrantygoodsVo objectVo) {
	this.objectVo = objectVo;
    }

    public void setLimitedWarrantyItemsService(
	    ILimitedWarrantyItemsService limitedWarrantyItemsService) {
	this.limitedWarrantyItemsService = limitedWarrantyItemsService;
    }

}
