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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/WorkdayAction.java
 * 
 * FILE NAME        	: WorkdayAction.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IWorkdayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkdayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.WorkdayException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.WorkdayVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;


/**
 * 工作日控制类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-12-24 下午3:21:40 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-12-24 下午3:21:40
 * @since
 * @version
 */
public class WorkdayAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkdayAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6242572624192876293L;
    
    /**
     * 工作日 Service接口.
     */
    private IWorkdayService workdayService;
    
    /**
     * 工作日信息VO.
     */
    private WorkdayVo workdayVo = new WorkdayVo();
    
    /**
     * <p>保存或修改工作日</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午3:24:23
     * @see
     */
    @JSON
    public String saveOrUpdateWorkday(){
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    WorkdayEntity entity = workdayVo.getEntity();
	    entity.setCreateUser(userCode);

	    // 保存成功返回一个对象
	    entity = workdayService.saveOrUpdate(entity);
	    workdayVo.setEntity(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (WorkdayException e) {
	    LOGGER.error(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>查询工作日</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午3:24:23
     * @see
     */
    @JSON
    public String queryWorkday(){
	try {
	    WorkdayEntity workday = workdayService.queryWorkdayByWorkMonth(workdayVo.getWorkMonth());
	    workdayVo.setEntity(workday);
	    return returnSuccess();
	} catch (WorkdayException e) {
	    LOGGER.error(e.getMessage(), e);
	    return returnError(e);
	}
    }

    
    /**
     * 设置 工作日 Service接口.
     *
     * @param workdayService the new 工作日 Service接口
     */
    public void setWorkdayService(IWorkdayService workdayService) {
        this.workdayService = workdayService;
    }

    
    /**
     * 获取 工作日信息VO.
     *
     * @return the 工作日信息VO
     */
    public WorkdayVo getWorkdayVo() {
        return workdayVo;
    }

    
    /**
     * 设置 工作日信息VO.
     *
     * @param workdayVo the new 工作日信息VO
     */
    public void setWorkdayVo(WorkdayVo workdayVo) {
        this.workdayVo = workdayVo;
    }
    
    

}
