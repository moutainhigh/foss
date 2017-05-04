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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/InfoDeptAction.java
 * 
 * FILE NAME        	: InfoDeptAction.java
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
 * 
 * 1.5.3 界面描述-主界面
 * 
 *  1. 功能按钮区域
 * 
 *  1) 新增按钮：
 *  
 *  点击新增按钮进入新增界面，
 *  
 *  参见【图二：
 *  
 *  信息部基础资料新增和修改界面】。
 *  
 *   2)
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
 *  3) 重置按钮：
 *  
 *  点击重置按钮，
 *  
 *  重置查询条件。 4)
 *  
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
 * 
 * 修改按钮：
 * 
 * 点击各行的修改按钮，
 * 
 * 进入修改界面，
 * 
 * 参见【图二：信息部基础资料新增和修改界面】。 
 * 
 * 6) 分页按钮：
 * 
 * 实现分页功能。 
 * 
 * 2. 列表区域 1)
 * 
 * 
 * 列表区域默认不显示，
 * 
 * 点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 *  2)
 * 列表中显示：
 * 
 * 信息部名称、业务联系人
 * 
 * 、联系电话、手机号码、传真号码、
 * 
 * 信息部性质、总分、审核人、日期。 
 * 
 * 3. 字段输入区域 1)
 * 
 * 
 * 查询条件包括信息部名称、
 * 
 * 业务联系人、
 * 
 * 手机号码、信息部性质。 
 * 
 * 1.1 信息部名称：文本，
 * 
 * 支持模糊查询 
 * 
 * 1.2 业务联系人：文本，
 * 
 * 支持模糊查询 1.3
 * 
 * 
 * 手机号码：数字，
 * 
 * 支持模糊查询 
 * 
 * 1.4 信息部性质：下拉框，
 * 
 * 包含企业公司
 * 
 * 、个体户、其他、全部，
 * 
 * 默认为全部
 * 
 *  1.5.5 界面描述-新增和修改界面 1.
 *  
 *  
 * 字段输入区域
 * 
 *  1) 信息部名称：
 * 
 *  必填，文本 
 *  
 *  2) 业务联系人：必填，文本 
 *  
 *  3) 信息部性质：必填，下拉框，
 *  
 *  包含企业公司、个体户、其他
 *  
 *   4)
 *  
 *  
 * 手机号码：必填，数字，
 * 
 * 遵循手机号码规则，
 * 
 * 只能为11位 
 * 
 * 5) 传真号码：选填，数字 
 * 
 * 6) 联系电话：选填，数字 
 * 
 * 7) 注册资本：选填，数字
 * 
 *  8)
 * 法人代表：选填，文本 
 * 
 * 9) 联系地址：必填，文本
 * 
 *  10) 标准得分1：必填，数字 
 *  
 *  11) 标准得分2：必填，数字
 *  
 *   12) 标准得分3：必填，数字
 *   
 *   
 * 13) 标准得分4：必填，数字
 * 
 *  14) 标准得分5：必填，数字 
 *  
 *  15) 标准得分6：必填，数字 
 *  
 *  16) 备注1：选填
 *  
 *   17) 备注2：选填 
 *   
 *   18)
 * 备注3：选填 
 * 
 * 19) 备注4：选填 
 * 
 * 20) 备注5：选填 
 * 
 * 21) 备注6：选填 
 * 
 * 22) 总体评述（15分）：必填 
 * 
 * 23)
 * 总分：必填，数字，自动生成 
 * 
 * 24) 采用意见：选填
 * 
 *  25) 信息部老板身份证正面（图片）：必填
 *  
 *   26) 身份证反面（图片）：必填 
 *   
 *   27)
 * 营业执照复印件（图片）：选填
 * 
 *  28) 信息部照片：选填 2.
 *  
 *   功能按钮区域 1)
 *   
 * 本地照片按钮：
 * 
 * 点击本地照片按钮，
 * 
 * 弹出文件选择框，选
 * 
 * 择要上传的图片。 
 * 
 * 2)
 * 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 需要提示用户是否保存成功，
 * 
 * 若保存成功，关闭当前页面
 * 
 * 
 * ，返回到主界面；若保存失败
 * 
 * ，提示用户保存失败以及失败原因，
 * 
 * 不关闭当前界面。。
 * 
 *  3) 重置按钮：点击重置按钮，
 *  
 *  回到当前界面的初始状态。 
 *  
 *  4)
 * 取消按钮：点击取消按钮，
 * 
 * 退出当前界面，返回主界面。
 * 
 *  SR-1 总分等于各项选择标准得分之和，
 *  
 *  为自动计算生成，不支持手动编辑； 
 *  
 *  SR-2
 * 信息部老板身份证正面、
 * 
 * 身份证反面、营业执照复印件、
 * 
 * 信息部照片上传只支持图片格式；
 * 
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptScoresStdService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IInfoDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InfoDeptScoresEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.InfoDeptScoresException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.InfoDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 信息部action
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-18 16:40:10
 * @since
 * @version 0.01
 */
public class InfoDeptAction extends AbstractAction {
	/**
	 * 下面是声明的属性
	 */
	private static final long serialVersionUID = -802246567875971335L;
	//信息部service接口
    private IInfoDeptService infoDeptService;
    //信息部标准得分service接口
    private IInfoDeptScoresService infoDeptScoresService;
    //信息部得分标准service接口
    private IInfoDeptScoresStdService infoDeptScoresStdService;
    //信息部 使用VO
	private InfoDeptVo objectVo = new InfoDeptVo();
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoDeptAction.class);
	/**
     * <p>查询信息部</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     */
	@SuppressWarnings("unchecked")
	public String queryInfoDeptEntityByEntity(){
		InfoDeptEntity entityCondition = objectVo.getInfoDeptEntity();
		// 返回的结果显示在表格中：
		PaginationDto paginationDto = infoDeptService.queryInfoDeptListBySelectiveCondition(entityCondition, start, limit);
    	objectVo.setInfoDeptEntityList(paginationDto.getPaginationDtos());
    	totalCount = paginationDto.getTotalCount();
    	return returnSuccess();
	}
	/**
     * <p>标准得分</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     */
	public String queryInfoDeptScoresEntityByEntity(){
		InfoDeptScoresEntity entityCondition = objectVo.getInfoDeptScoresEntity();
		// 返回的结果显示在表格中：
    	objectVo.setInfoDeptScoresEntityList( infoDeptScoresService.queryInfoDeptScoresListBySelectiveCondition(entityCondition));
    	return returnSuccess();
	}
	/**
     * <p>得分标准</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     */
	public String queryInfoDeptScoresStdList(){
    	objectVo.setInfoDeptScoresStdList( infoDeptScoresStdService.queryInfoDeptScoresStdListBySelective());
    	return returnSuccess();
	}
    /**
     * 作废信息部 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    public String deleteInfoDeptEntity() {
    	try {
        	objectVo.setReturnInt(infoDeptService.deleteInfoDept(objectVo.getCodeStr(),FossUserContext.getCurrentInfo().getEmpCode()));
            return returnSuccess();
    	} catch (InfoDeptException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
	//标准得分
    public String deleteInfoDeptScoresEntity() {
//    	objectVo.setReturnInt(infoDeptScoresService.deleteSMSTempleteForDeptByCode(objectVo.getCodeStr(),FossUserContext.getCurrentInfo().getEmpCode()));
        return returnSuccess();
    }
    /**
     * 修改信息部 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    public String updateInfoDeptEntity() {
    	try {
    		objectVo.getInfoDeptEntity().setInfoDeptScoresList(CollectionUtils.isNotEmpty(objectVo.getInfoDeptScoresEntityList())?objectVo.getInfoDeptScoresEntityList():null);
        	objectVo.setReturnInt(infoDeptService.updateInfoDept(objectVo.getInfoDeptEntity(),FossUserContext.getCurrentInfo().getEmpCode(),false));
           return returnSuccess();
    	} catch (InfoDeptException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 修改信息部 标准得分
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    public String updateInfoDeptScoresEntity() {
    	try {
        	objectVo.setReturnInt(updateInfoDeptScores(objectVo.getInfoDeptScoresEntity()));
            return returnSuccess();
    	} catch (InfoDeptException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 修改信息部 标准得分
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    private int updateInfoDeptScores(InfoDeptScoresEntity info){
    	return infoDeptScoresService.updateInfoDeptScores(info,FossUserContext.getCurrentInfo().getEmpCode(),false);
    }
    /**
     * 新增信息部 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    public String addInfoDeptEntity() {
    	try {
    		objectVo.getInfoDeptEntity().setInfoDeptScoresList(CollectionUtils.isNotEmpty(objectVo.getInfoDeptScoresEntityList())?objectVo.getInfoDeptScoresEntityList():null);
        	objectVo.setReturnInt(infoDeptService.addInfoDept(objectVo.getInfoDeptEntity(),FossUserContext.getCurrentInfo().getEmpCode(),false));
           return returnSuccess();
    	} catch (InfoDeptException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 新增信息部 标准得分
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    public String addInfoDeptScoresEntity() {
    	try {
        	objectVo.setReturnInt(addInfoDeptScores(objectVo.getInfoDeptScoresEntity()));
            return returnSuccess();
    	} catch (InfoDeptScoresException e) {
    	    LOGGER.debug(e.getMessage(), e);
    	    return returnError(e);
    	}
    }
    /**
     * 新增信息部 标准得分
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     * @see
     */
    private int addInfoDeptScores(InfoDeptScoresEntity info) {
       return infoDeptScoresService.addInfoDeptScores(info,FossUserContext.getCurrentInfo().getEmpCode(),false);
    }
	/**
     * <p>信息部  是否重复</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-18 16:40:10
     * @return String
     */
    public String infoDeptIsExist() {
    	//TODO 信息部  是否重复
    	return returnSuccess();
    }
	//标准得分
    public String infoDeptScoresEntityIsExist() {
    	return returnSuccess();
    }
    /*
     * =================================================================
     * 下面是get,set方法：
     */
	public void setInfoDeptService(IInfoDeptService infoDeptService) {
		this.infoDeptService = infoDeptService;
	}
	public InfoDeptVo getObjectVo() {
		return objectVo;
	}
	public void setObjectVo(InfoDeptVo objectVo) {
		this.objectVo = objectVo;
	}
	public void setInfoDeptScoresService(
			IInfoDeptScoresService infoDeptScoresService) {
		this.infoDeptScoresService = infoDeptScoresService;
	}
	public void setInfoDeptScoresStdService(
			IInfoDeptScoresStdService infoDeptScoresStdService) {
		this.infoDeptScoresStdService = infoDeptScoresStdService;
	}

}
