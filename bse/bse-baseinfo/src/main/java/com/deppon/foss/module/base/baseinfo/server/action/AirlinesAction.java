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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirlinesAction.java
 * 
 * FILE NAME        	: AirlinesAction.java
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

/**
 * 
 * 1.5.3 
 * 
 * 界面描述-主界面 
 * 
 * 1. 功能按钮区域 
 * 
 * 1) 新增按钮：
 * 
 * 点击新增按钮进入新增界面，
 * 
 * 参见【图二：航空公司新增和修改界面】。
 * 
 *  2)
 *  
 * 查询按钮：
 * 
 * 输入查询条件，
 * 
 * 点击查询按钮，
 * 
 * 系统返回查询结果，
 * 
 * 刷新查询列表。 
 * 
 * 3) 重置按钮：
 * 
 * 点击重置按钮，
 * 
 * 重置查询条件。 4)
 * 
 * 作废按钮：
 * 
 * 选中列表中一行或多行记录，
 * 
 * 点击作废按钮，
 * 
 * 选中的记录被作废；
 * 
 * 或点击各行的作废按钮，
 * 
 * 作废各行记录；
 * 
 * 需要弹出确认提示框。 5)
 * 
 * 修改按钮
 * 
 * ：点击各行的修改按钮，
 * 
 * 进入修改界面，
 * 
 * 参见【图二：航空公司新增和修改界面】。
 * 
 *  6) 分页按钮：
 *  
 *  实现分页功能。 
 *  
 *  2. 列表区域 1)
 *  
 * 列表区域默认不显示，
 * 
 * 点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 *  2) 列表中显示：
 *  
 *  航空公司、航空公司代码、
 *  
 *  航空公司简称、航空公司数字前缀。
 *  
 *   3.
 * 字段输入区域 
 * 
 * 1) 录入查询条件，
 * 
 * 点击查询按钮后在列表区显示满足条件的查询结果。
 * 
 * 1.5.5 
 * 
 * 界面描述-新增和修改界面 
 * 
 * 1. 字段输入区域 
 * 
 * 1) 航空公司： 必填 
 * 
 * 2) 航空公司代码：必填 
 * 
 * 3) 航空公司简称：必填
 * 
 *  4)
 * 航空公司数字前缀：选填
 * 
 *  5) 航空公司LOGO：选填
 *  
 *   4) 描述：选填 
 *   
 *   2. 功能按钮区域 
 *   1
 *   )
 *   
 * 保存按钮：
 * 
 * 点击保存按钮，
 * 
 * 若保存成功，
 * 
 * 弹出保存成功提示框，
 * 
 * 返回主界面，
 * 
 * 否则，提示保存失败，
 * 
 * 返回新增/修改界面。
 * 
 *  2)
 * 重置按钮：
 * 
 * 点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：
 *  
 *  点击取消按钮，
 *  
 *  退出当前界面，
 *  返
 *  回主界面。 SR-1
 *  
 * 新增界面中航空公司、
 * 
 * 航空公司代码、
 * 
 * 航空公司简称均为唯一的信息，
 * 
 * 不能重复 SR-2 
 * 
 * 航空公司查询支持模糊查询
 * 
 * 
 * 进入航空公司管理主界面 
 * 
 * 点击新增按钮，
 * 
 * 进入新增和修改界面 
 * 
 * 输入航空公司详细信息，
 * 
 * 点击保存。 
 * 
 * 参见业务规则SR-1 
 * 
 * 返回航空公司管理主界面
 * 
 * 
 * 取消按钮，
 * 
 * 退出当前界面，
 * 
 * 返回主界面 若保存失败，
 * 
 * 弹出对话框，
 * 
 * 提示用户保存失败以及失败原因，
 * 
 * 继续停留在新增界面
 * 
 * 进入航空公司管理
 * 
 * 主界面 点击修改按钮，进入新增和修改界面 修改航空公司详细信息,点击保存 参见业务规则SR-1 返回航空公司管理主界面
 * 取消按钮，退出当前界面，返回主界面 若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面 进入航空公司管理主界面
 * 点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。 点击确定按钮。 取消按钮，关闭对话框，返回主界面
 * 确认作废后，若作废失败，弹出对话框，提示用户作废失败以及失败原因 进入航空公司管理主界面 输入查询条件，点击查询按钮。参见业务规则SR-2
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AirlinesVo;

/**
 * 月台ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class AirlinesAction extends AbstractAction {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 2883644272419312426L;

    /**
     * vo
     */
    private AirlinesVo airlinesVo = new AirlinesVo();

    /**
     * 
     * @date Mar 14, 2013 4:59:31 PM
     * @return
     * @see
     */
    public AirlinesVo getAirlinesVo() {
	return airlinesVo;
    }

    /**
     * 
     * @date Mar 14, 2013 4:59:36 PM
     * @param airlinesVo
     * @see
     */
    public void setAirlinesVo(AirlinesVo airlinesVo) {
	this.airlinesVo = airlinesVo;
    }

    /**
     * 航空公司service
     */
    private IAirlinesService airlinesService;

    /**
     * 
     * @date Mar 14, 2013 4:59:48 PM
     * @param airlinesService
     * @see
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
	this.airlinesService = airlinesService;
    }
    
    /**
     * .
     * <p>
     * 查询所有的航空公司根据条件<br/>
     * 方法名：queryAirlines
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-12-03
     * @since JDK1.6
     */
    @JSON
    public String queryAirlines() {
	try {
	    List<AirlinesEntity> airlinesEntityList = airlinesService
		    .queryAirlines(airlinesVo.getAirlinesEntity(), limit, start);
	    airlinesVo.setAirlinesEntityList(airlinesEntityList);
	    this.setTotalCount(airlinesService.queryRecordCount(airlinesVo
		    .getAirlinesEntity()));
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 删除航空公司根据条件<br/>
     * 方法名：deleteAirlinesByCode
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-12-03
     * @since JDK1.6
     */
    @JSON
    public String deleteAirlinesByCode() {
	try {
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    airlinesService.deleteAirlinesByCode(airlinesVo.getIdsStr(),
		    userCode);
	    return returnSuccess(MessageType.DELETE_AIRLINES_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 新增航空公司根据条件<br/>
     * 方法名：addAirlines
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-12-03
     * @since JDK1.6
     */
    @JSON
    public String addAirlines() {
	try {
	    airlinesService.addAirlines(airlinesVo.getAirlinesEntity());
	    return returnSuccess(MessageType.SAVE_AIRLINES_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 新增航空公司根据条件<br/>
     * 方法名：addAirlines
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-12-03
     * @since JDK1.6
     */
    @JSON
    public String updateAirlines() {
	try {
	    airlinesService.updateAirlines(airlinesVo.getAirlinesEntity());
	    return returnSuccess(MessageType.UPDATE_AIRLINES_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 根据CODE查询航空公司<br/>
     * 方法名：queryAirlineByCode
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-12-03
     * @since JDK1.6
     */
    @JSON
    public String queryAirlineByCode() {
	try {
	    AirlinesEntity airlinesEntity = airlinesService
		    .queryAirlineByCode(airlinesVo.getAirlinesEntity()
			    .getCode());
	    airlinesVo.setAirlinesEntity(airlinesEntity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 根据"简称"查询航空公司
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2013-1-4 下午8:15:55
     * @return
     * @see
     */
    @JSON
    public String queryAirlineBySimpleName() {
	try {
	    AirlinesEntity airlinesEntity = airlinesService
		    .queryAirlineBySimpleName(airlinesVo.getAirlinesEntity()
			    .getSimpleName());
	    airlinesVo.setAirlinesEntity(airlinesEntity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 根据"名称"查询航空公司
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2013-1-5 上午8:32:36
     * @return
     * @see
     */
    @JSON
    public String queryAirlineByName() {
	try {
	    AirlinesEntity airlinesEntity = airlinesService
		    .queryAirlineByName(airlinesVo.getAirlinesEntity()
			    .getName());
	    airlinesVo.setAirlinesEntity(airlinesEntity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }
}
