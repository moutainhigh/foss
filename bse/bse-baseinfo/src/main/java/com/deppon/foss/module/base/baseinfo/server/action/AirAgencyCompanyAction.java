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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirAgencyCompanyAction.java
 * 
 * FILE NAME        	: AirAgencyCompanyAction.java
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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 空运代理公司action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-11-27 14:10:10
 * @since
 * @version 0.01
 */
public class AirAgencyCompanyAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 空运代理公司service接口
    private IAirAgencyCompanyService airAgencyCompanyService;

    // 空运代理公司 action使用VO
    private AgencyCompanyOrDeptVo objectVo = new AgencyCompanyOrDeptVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AirAgencyCompanyAction.class);

    /**
     * <p>
     * 查询空运代理公司
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String queryAirAgencyCompany() {
	BusinessPartnerEntity entityCondition = objectVo
		.getBusinessPartnerEntity();
	// 返回的结果显示在表格中：
	objectVo.setBusinessPartnerEntityList(airAgencyCompanyService
		.queryAirAgencyCompanys(entityCondition, limit, start));
	totalCount = airAgencyCompanyService.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * <p>
     * 空运代理公司 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     */
    public String airAgencyCompanyIsExist() {
	try {
	    objectVo.setBusinessPartnerEntityList(airAgencyCompanyService
		    .queryAirAgencyCompanys(
			    objectVo.getBusinessPartnerEntity(), 1, 0));
	    List<BusinessPartnerEntity> list = objectVo
		    .getBusinessPartnerEntityList();
	    objectVo.setBusinessPartnerEntity((CollectionUtils.isNotEmpty(list)) ? list
		    .get(0) : null);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 根据code作废空运代理公司
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     * @see
     */
    public String deleteAirAgencyCompanyByCode() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyCompanyService
		    .deleteAirAgencyCompanyByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改空运代理公司
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     * @see
     */
    public String updateAirAgencyCompany() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyCompanyService
		    .updateAirAgencyCompany(objectVo.getBusinessPartnerEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增空运代理公司
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-03 19:50:10
     * @return String
     * @see
     */
    public String addAirAgencyCompany() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyCompanyService
		    .addAirAgencyCompany(objectVo.getBusinessPartnerEntity());
	    objectVo.setReturnInt(returnInt);
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
    public void setAirAgencyCompanyService(
	    IAirAgencyCompanyService airAgencyCompanyService) {
	this.airAgencyCompanyService = airAgencyCompanyService;
    }

    public AgencyCompanyOrDeptVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(AgencyCompanyOrDeptVo objectVo) {
	this.objectVo = objectVo;
    }
}
