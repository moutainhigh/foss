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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/action/SMSAdvertisingSloganAction.java
 * 
 * FILE NAME        	: SMSAdvertisingSloganAction.java
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
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganForDeptService;
import com.deppon.foss.module.base.common.api.server.service.ISMSAdvertisingSloganService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSloganEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SloganAppOrgEntity;
import com.deppon.foss.module.base.common.api.shared.exception.SMSAdVertisingSloganException;
import com.deppon.foss.module.base.common.api.shared.exception.SloganAppOrgException;
import com.deppon.foss.module.base.common.api.shared.vo.SMSSloganVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 短信广告语action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-17 14:10:10
 * @since
 * @version 0.01
 */
public class SMSAdvertisingSloganAction extends AbstractAction {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6309782781506250316L;

    // 短信广告语service接口
    private ISMSAdvertisingSloganService sMSAdvertisingSloganService;

    // 部门短信广告语service接口
    private ISMSAdvertisingSloganForDeptService sMSAdvertisingSloganForDeptService;

    // 短信广告语 使用VO
    private SMSSloganVo objectVo = new SMSSloganVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SMSAdvertisingSloganAction.class);

    /**
     * <p>
     * 查询短信广告语
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     */
    public String querySMSSloganEntityByEntity() {
	SMSSloganEntity entityCondition = objectVo.getSmssloganEntity();
	// 返回的结果显示在表格中：
	objectVo.setSmssloganEntityList(sMSAdvertisingSloganService
		.querySMSAdvertisingSlogans(entityCondition, limit, start));
	totalCount = sMSAdvertisingSloganService
		.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    // 部门
    public String querySloganAppOrgEntityByEntity() {
	SloganAppOrgEntity entityCondition = objectVo.getSloganAppOrgEntity();
	// 返回的结果显示在表格中：
	objectVo.setSloganAppOrgEntityList(sMSAdvertisingSloganForDeptService
		.querySMSAdvertisingSloganForDepts(entityCondition, Integer.MAX_VALUE,
			start));
	totalCount = sMSAdvertisingSloganForDeptService
		.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * 作废短信广告语
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String deleteSMSSloganEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganService
		    .deleteSMSAdvertisingSloganByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode()));
	    return returnSuccess();
	} catch (SMSAdVertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String deleteSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganForDeptService
		    .deleteSMSAdvertisingSloganForDeptByCode(objectVo
			    .getCodeStr(), FossUserContext.getCurrentInfo()
			    .getEmpCode()));
	    return returnSuccess();
	} catch (SloganAppOrgException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改短信广告语
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String updateSMSSloganEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganService
		    .updateSMSAdvertisingSlogan(objectVo.getSmssloganEntity()));
	    return returnSuccess();
	} catch (SMSAdVertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String updateSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganForDeptService
		    .updateSMSAdvertisingSloganForDept(objectVo
			    .getSloganAppOrgEntity()));
	    return returnSuccess();
	} catch (SloganAppOrgException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增短信广告语
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     * @see
     */
    public String addSMSSloganEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganService
		    .addSMSAdvertisingSlogan(objectVo.getSmssloganEntity()));
	    return returnSuccess();
	} catch (SMSAdVertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    // 部门
    public String addSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(sMSAdvertisingSloganForDeptService
		    .addSMSAdvertisingSloganForDept(objectVo
			    .getSloganAppOrgEntity()));
	    return returnSuccess();
	} catch (SloganAppOrgException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 短信广告语 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-17 14:10:10
     * @return String
     */
    public String sMSSloganEntityIsExist() {
	// TODO 短信广告语 是否重复
	return returnSuccess();
    }

    // 部门
    public String sloganAppOrgEntityIsExist() {
	// TODO 部门短信广告语 是否重复
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public void setObjectVo(SMSSloganVo objectVo) {
	this.objectVo = objectVo;
    }

    public SMSSloganVo getObjectVo() {
	return objectVo;
    }

    public void setsMSAdvertisingSloganService(
	    ISMSAdvertisingSloganService sMSAdvertisingSloganService) {
	this.sMSAdvertisingSloganService = sMSAdvertisingSloganService;
    }

    public void setsMSAdvertisingSloganForDeptService(
	    ISMSAdvertisingSloganForDeptService sMSAdvertisingSloganForDeptService) {
	this.sMSAdvertisingSloganForDeptService = sMSAdvertisingSloganForDeptService;
    }
}
