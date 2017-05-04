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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/action/SMSTempleteAction.java
 * 
 * FILE NAME        	: SMSTempleteAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteForDeptService;
import com.deppon.foss.module.base.common.api.server.service.ISMSTempleteService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSTemplateEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteException;
import com.deppon.foss.module.base.common.api.shared.exception.SMSTempleteForDeptException;
import com.deppon.foss.module.base.common.api.shared.vo.SMSTemplateVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 短信模板action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-17 14:10:10
 * @since
 * @version 0.01
 */
public class SMSTempleteAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 短信模板service接口
    private ISMSTempleteService sMSTempleteService;

    // 部门短信模板service接口
    private ISMSTempleteForDeptService sMSTempleteForDeptService;

    // 短信模板 使用VO
    private SMSTemplateVo objectVo = new SMSTemplateVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SMSTempleteAction.class);

    /**
     * <p>
     * 查询短信模板
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     */
    public String querySMSTemplateEntityByEntity() {
	SMSTemplateEntity entityCondition = objectVo.getSmsTemplateEntity();
	// 返回的结果显示在表格中：
	objectVo.setSmsTemplateEntityList(sMSTempleteService.querySMSTempletes(
		entityCondition, limit, start));
	totalCount = sMSTempleteService.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    // 部门
    public String queryTemplateAppOrgEntityByEntity() {
	// 返回的结果显示在表格中：
	objectVo.setTemplateAppOrgEntityList(sMSTempleteForDeptService
		.querySMSTempleteForDepts(objectVo.getTemplateAppOrgEntity()));
	return returnSuccess();
    }

    /**
     * 作废短信模板
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String deleteSMSTemplateEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteService.deleteSMSTempleteByCode(
		    objectVo.getCodeStr(), FossUserContext.getCurrentInfo()
			    .getEmpCode()));
	    return returnSuccess();
	} catch (SMSTempleteException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String deleteTemplateAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteForDeptService
		    .deleteSMSTempleteForDeptByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode()));
	    return returnSuccess();
	} catch (SMSTempleteForDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改短信模板
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String updateSMSTemplateEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteService.updateSMSTemplete(objectVo
		    .getSmsTemplateEntity()));
	    return returnSuccess();
	} catch (SMSTempleteException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String updateTemplateAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteForDeptService
		    .updateSMSTempleteForDept(objectVo
			    .getTemplateAppOrgEntity()));
	    return returnSuccess();
	} catch (SMSTempleteForDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增短信模板
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String addSMSTemplateEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteService.addSMSTemplete(objectVo
		    .getSmsTemplateEntity()));
	    return returnSuccess();
	} catch (SMSTempleteException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String addTemplateAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSTempleteForDeptService
		    .addSMSTempleteForDept(objectVo.getTemplateAppOrgEntity()));
	    return returnSuccess();
	} catch (SMSTempleteForDeptException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 短信模板 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     */
    public String sMSTempleteEntityIsExist() {
	// 短信模板 是否重复
	return returnSuccess();
    }

    // 部门
    public String templateAppOrgEntityIsExist() {
	// 短信模板 是否重复
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public void setsMSTempleteService(ISMSTempleteService sMSTempleteService) {
	this.sMSTempleteService = sMSTempleteService;
    }

    public SMSTemplateVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(SMSTemplateVo objectVo) {
	this.objectVo = objectVo;
    }

    public void setsMSTempleteForDeptService(
	    ISMSTempleteForDeptService sMSTempleteForDeptService) {
	this.sMSTempleteForDeptService = sMSTempleteForDeptService;
    }

}
