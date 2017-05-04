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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ContactWithAirlinesAction.java
 * 
 * FILE NAME        	: ContactWithAirlinesAction.java
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

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactWithAirlinesException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ContactWithAirlinesVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 用来响应“正单交货人”的Action类：SUC-37 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-12-19 下午3:10:59</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-12-19 下午3:10:59
 * @since
 * @version
 */
public class ContactWithAirlinesAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -8833259877290512876L;
    
    //"正单交货人"参数和结果对象
    private ContactWithAirlinesVo contactWithAirlinesVo;
    
    //"正单交货人"服务
    private IContactWithAirlinesService contactWithAirlinesService;

    /**
     * <p>通过"正单交货人"来检索一个"正单交货人"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:22
     * @return
     * @see
     */
    @JSON
    public String queryContactWithAirlines(){
	ContactAirlinesEntity contactAirlines = getContactWithAirlinesVo().getContactWithAirlines();
	try {
	    contactAirlines = contactWithAirlinesService.queryContactWithAirlinesBySelective(contactAirlines);
	    getContactWithAirlinesVo().setContactWithAirlines(contactAirlines);
	} catch (ContactWithAirlinesException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>无条件检索所有"正单交货人"列表</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-12-11 上午10:38:34
     * @return
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryContactWithAirlinesList(){
	ContactAirlinesEntity contactAirlines = getContactWithAirlinesVo().getContactWithAirlines();
	try {
	    PaginationDto paginationDto = contactWithAirlinesService.queryContactAirlinesListBySelectiveCondition(contactAirlines, start, limit);
	    getContactWithAirlinesVo().setContactWithAirlinesList(paginationDto.getPaginationDtos());
	    setTotalCount(paginationDto.getTotalCount());
	} catch (ContactWithAirlinesException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>新增一个外请"正单交货人"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:04
     * @return
     * @see
     */
    @JSON
    public String addContactWithAirlines(){
	ContactAirlinesEntity contactAirlines = getContactWithAirlinesVo().getContactWithAirlines();
	String createUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    contactWithAirlinesService.addContactWithAirlines(contactAirlines, createUser, false);
	    return returnSuccess(ContactWithAirlinesException.CONTACTWITHAIRLINES_ADD_SUCCESS);
	} catch (ContactWithAirlinesException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>删除一个外请"正单交货人"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午2:00:02
     * @return
     * @see
     */
    @JSON
    public String deleteContactWithAirlines(){
	List<String> ids = getContactWithAirlinesVo().getBatchIds();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    contactWithAirlinesService.deleteContactWithAirlines(ids, modifyUser);
	    return returnSuccess(ContactWithAirlinesException.CONTACTWITHAIRLINES_DEL_SUCCESS);
	} catch (ContactWithAirlinesException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>修改一个外请"正单交货人"</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-29 下午1:59:59
     * @return
     * @see
     */
    @JSON
    public String updateContactWithAirlines(){
	ContactAirlinesEntity contactAirlines = getContactWithAirlinesVo().getContactWithAirlines();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
	    contactWithAirlinesService.updateContactWithAirlines(contactAirlines, modifyUser, true);
	    return returnSuccess(ContactWithAirlinesException.CONTACTWITHAIRLINES_UPD_SUCCESS);
	} catch (ContactWithAirlinesException e) {
	    returnError(e);
	}
	return returnSuccess();
    }
    
    /**
     * <p>获取当前的登录用户</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:34:46
     * @return
     * @see
     */
    private UserEntity getCurrentUser(){
	UserEntity user = FossUserContext.getCurrentUser();
	return null == user ? new UserEntity() : user;
    }
    
    /**
     * @return  the contactWithAirlinesVo
     */
    public ContactWithAirlinesVo getContactWithAirlinesVo() {
	if (null == contactWithAirlinesVo) {
	    this.contactWithAirlinesVo = new ContactWithAirlinesVo();
	}
        return contactWithAirlinesVo;
    }

    /**
     * @param contactWithAirlinesVo the contactWithAirlinesVo to set
     */
    public void setContactWithAirlinesVo(ContactWithAirlinesVo contactWithAirlinesVo) {
        this.contactWithAirlinesVo = contactWithAirlinesVo;
    }

    /**
     * @param contactWithAirlinesService the contactWithAirlinesService to set
     */
    public void setContactWithAirlinesService(
    	IContactWithAirlinesService contactWithAirlinesService) {
        this.contactWithAirlinesService = contactWithAirlinesService;
    }
}
