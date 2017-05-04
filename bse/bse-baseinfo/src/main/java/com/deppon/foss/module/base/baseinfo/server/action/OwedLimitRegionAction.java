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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/OwedLimitRegionAction.java
 * 
 * FILE NAME        	: OwedLimitRegionAction.java
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

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.OwedLimitRegionVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 临欠额度区间范围控制类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-2-26 下午2:14:21
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-2-26 下午2:14:21
 * @since
 * @version
 */
public class OwedLimitRegionAction extends AbstractAction {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OwedLimitRegionAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 9119827262086969058L;

    /**
     * 临欠额度区间范围信息Service接口.
     */
    private IOwedLimitRegionService owedLimitRegionService;

    /**
     * 临欠额度区间范围信息VO.
     */
    private OwedLimitRegionVo vo = new OwedLimitRegionVo();
    
    /**
     * <p>添加临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午2:53:02
     * @return
     * @see
     */
    @JSON
    public String addOwedLimitRegion() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    OwedLimitRegionEntity entity = vo.getEntity();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);

	    // 保存成功返回一个对象
	    owedLimitRegionService.addInfo(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>作废临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午2:55:13
     * @return
     * @see
     */
    @JSON
    public String deleteOwedLimitRegion() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    
	    owedLimitRegionService.deleteInfos(vo.getIds(), userCode);

	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>修改临欠额度区间范围信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午2:57:50
     * @return
     * @see
     */
    @JSON
    public String updateOwedLimitRegion() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    OwedLimitRegionEntity entity = vo.getEntity();
	    entity.setModifyUser(userCode);

	    // 保存成功返回一个对象
	    owedLimitRegionService.updateInfo(entity);

	    return returnSuccess(MessageType.UPDATE_SUCCESS);
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>分页查询临欠额度区间信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午3:07:26
     * @return
     * @see
     */
    @JSON
    public String queryOwedLimitRegions() {
	try {

	    List<OwedLimitRegionEntity> regionList = owedLimitRegionService.queryAllInfos(vo.getEntity(), this.getLimit(), this.getStart());
	    vo.setRegionList(regionList);

	    // 查询总记录数
	    Long totalCount = owedLimitRegionService.queryRecordCount(vo.getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 设置 临欠额度区间范围信息Service接口.
     * 
     * @param owedLimitRegionService
     *            the owedLimitRegionService to set
     */
    public void setOwedLimitRegionService(
	    IOwedLimitRegionService owedLimitRegionService) {
	this.owedLimitRegionService = owedLimitRegionService;
    }

    /**
     * 获取 临欠额度区间范围信息VO.
     * 
     * @return the vo
     */
    public OwedLimitRegionVo getVo() {
	return vo;
    }

    /**
     * 设置 临欠额度区间范围信息VO.
     * 
     * @param vo
     *            the vo to set
     */
    public void setVo(OwedLimitRegionVo vo) {
	this.vo = vo;
    }

}
