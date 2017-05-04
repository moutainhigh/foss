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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/ExpressOriginatingLineAction.java
 * 
 * FILE NAME        	: ExpressOriginatingLineAction.java
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
 * 1.5.3 界面描述-主界面 
 * 
 * 1. 功能按钮区域
 * 
 *  1) 新增按钮：
 *  
 *  点击新增按钮进入新增界面，
 *  
 *  参见【图二：新增/修改始发线路】。
 *  
 *   2)
 * 导出按钮：点击导出按钮，
 * 
 * 可以导出始发线路的所有数据至Excel表中 
 * 
 * 3) 查询按钮：输入查询条件，点击查询按钮
 * 
 * ，系统返回查询结果，刷新查询列表。
 * 
 * 
 * 4) 重置按钮：点击重置按钮，清空查询条件。 5)
 * 
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废图标，作废各行记录
 * 
 * 
 * ，作废时连同作废该线路对应的发车标准
 * 
 * ，需要弹出确认提示框。 6)
 * 
 * 查看详细信息：鼠标移动到列表中某一条记录，
 * 
 * 双击该行记录，弹出一个窗口，
 * 
 * 可以查看该记录的详细信息。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，参见【图二：新增/修改始发线路】。
 * 
 *  8) 分页按钮：实现分页功能。 
 *  
 *  2. 列表区域 1)
 *  
 *  
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 *  2) 列表中显示：线路名称、
 *  
 *  始发站、到达站、始发城市、
 *  
 *  运输类型、是否默认始发线路。
 *  
 *  
 * 3. 字段输入区域
 * 
 *  1) 查询条件包括线路名称、
 *  
 *  始发站、到达站、始发城市、
 *  
 *  运输类型、管理车队、线路简码。
 *  
 *   1.1 线路名称：文本，支持模糊查询
 *   
 *   
 * 1.2 始发站：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（营业部）
 * 
 * 基础资料中选取 1.3
 * 
 * 
 * 到达站：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（外场、空运总调）
 * 
 * 基础资料中选取 1.4
 * 
 * 
 * 始发城市：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政区域（城市）
 * 
 * 基础资料中选取
 * 
 *  1.5 运输类型：下拉框，
 *  
 *  默认为全部，
 *  
 *  包括：全部、汽运、空运
 *  
 *  
 * 1.6 管理车队：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（车队）
 * 
 * 基础资料中选取
 * 
 *  1.7 线路简码：文本，支持模糊查询
 * 
 * 1.5.5 界面描述-新增和修改始发线路
 * 
 *  1. 字段输入区域 1) 线路名称：
 *  
 *  
 * 自动生成，生成规则：
 * 
 * 根据所选“始发站”和“到达站”动态生成,
 * 
 * 如：生成线路名称为XXX营业部-XXX外场 2)
 * 
 * 
 * 线路简码：必填，文本，线路简码必须唯一 
 * 
 * 3) 管理车队：必填，选择框，
 * 
 * 从行政组织（车队）基础资料中选取 4)
 * 
 * 
 * 运输类型：必填，下拉框，包含汽运、
 * 
 * 空运 5) 始发站：必填，选择框，
 * 
 * 从行政组织（营业部）基础资料中选取 
 * 
 * 6) 始发城市：与“始发站”联动获取，只读
 * 
 * 
 * 7) 到达站：必填，选择框，
 * 
 * 从行政组织（外场、空运总调）
 * 
 * 基础资料中选取 8) 是否默认始发线路：
 * 
 * 必填，单选按钮，是或否 9)
 * 
 * 
 * 线路距离（公里）：必填，数字 10)
 * 
 *  备注：选填，文本 2． 发车标准列表区域 1)
 *  
 *  
 * 列表中显示：班次、准点出发时间、
 * 
 * 准点到达时间、时效类型、备注。
 * 
 *  3. 功能按钮区域 1)
 *  
 *  
 * 保存按钮：点击保存按钮，
 * 
 * 需要提示用户是否保存成功，
 * 
 * 若保存成功，关闭当前界面，
 * 
 * 返回主界面；若保存失败，
 * 
 * 提示用户保存失败以及失败原因，
 * 
 * 不关闭当前界面。
 * 
 * 2) 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回主界面。
 *  
 *   4)
 * 新增发车标准按钮：点击新增发车标准按钮
 * 
 * ，先判断始发线路信息是否已经保存，
 * 
 * 如果信息还未保存，弹出提醒框，
 * 
 * 提醒需要保存线路信息才能新增发车标准；
 * 
 * 如果信息已经保存成功
 * 
 * ，则弹出【图三：新增和修改发车标准】界面，
 * 
 * 进行新增操作，详细步骤参见【新增发车标准操作步骤】。 
 * 
 * 5)
 * 修改按钮：点击修改按钮，
 * 
 * 先判断线路信息是否已做修改
 * 
 * 
 * ，如果线路信息已做修改，
 * 
 * 弹出提醒框，提醒先保存线路信息才能修改发车标准，
 * 
 * 详细步骤参见【修改发车标准操作步骤】； 6)
 * 
 * 作废按钮：点击作废按钮，
 * 
 * 先判断线路信息是否已做修改
 * 
 * ，如果线路信息已做修改，弹出提醒框，
 * 
 * 提醒先保存线路信息才能作废发车标准，
 * 
 * 详细步骤参见【作废发车标准操作步骤】。 7)
 * 
 * 查看详细信息：鼠标移动到列表中某一条记录，
 * 
 * 双击该行记录，弹出一个窗口，
 * 
 * 可以查看该记录的详细信息。
 * 
 * 1.5.7 界面描述-新增/修改发车标准 
 * 
 * 1、字段输入区域 1) 班次：
 * 
 * 数字，用户自行维护，
 * 
 * 自然数自动生成，
 * 
 * 同一条线路上班次从1开始，
 * 
 * 按照准点出发时间顺序排序。
 * 
 *  2)
 * 准点出发时间：必填，
 * 
 * 时间格式：08:00,09:30等
 * 
 *  3) 始发站：自动带出 4)
 *  
 * 准点到达时间：必填，
 * 
 * 采用T+1格式，T是指时间，如：00:30
 * 
 * ；+1天是指当天时间加1天，如：00:30+1，
 * 
 * 表示第二天的00:30；XX天用下拉框表示
 * 
 * ，如果为0天，默认为当天时间，
 * 
 * 如：22:00+0天，用22:00表示。 
 * 
 * 5) 到达站：自动带出 5)6)
 * 
 * 时效类型：
 * 
 * 下拉框，包括：普车、卡车两种时效类型；
 * 
 *  6)7) 备注：选填
 *  
 *   2、功能按钮区域 1)
 *   
 * 保存按钮：
 * 
 * 点击保存按钮，
 * 
 * 需要提示用户是否保存成功，
 * 
 * 若保存成功
 * 
 * ，关闭弹出窗口，
 * 
 * 返回到【图二：新增/修改始发线路】；
 * 
 * 若保存失败，
 * 
 * 提示用户保存失败以及失败原因，
 * 
 * 不关闭弹出窗口。 2)
 * 
 * 
 * 重置按钮：
 * 
 * 点击重置按钮，
 * 
 * 回到当前界面的初始状态。 
 * 
 * 3) 取消按钮：点击取消按钮，
 * 
 * 关闭当前界面，
 * 
 * 返回到【图二：新增/修改始发线路】界面。
 * 
 * 
 * 
 * * 1.5.3 界面描述-主界面 
 * 
 * 1. 功能按钮区域
 * 
 *  1) 新增按钮：
 *  
 *  点击新增按钮进入新增界面，
 *  
 *  参见【图二：新增/修改始发线路】。
 *  
 *   2)
 * 导出按钮：点击导出按钮，
 * 
 * 可以导出始发线路的所有数据至Excel表中 
 * 
 * 3) 查询按钮：输入查询条件，点击查询按钮
 * 
 * ，系统返回查询结果，刷新查询列表。
 * 
 * 
 * 4) 重置按钮：点击重置按钮，清空查询条件。 5)
 * 
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废图标，作废各行记录
 * 
 * 
 * ，作废时连同作废该线路对应的发车标准
 * 
 * ，需要弹出确认提示框。 6)
 * 
 * 查看详细信息：鼠标移动到列表中某一条记录，
 * 
 * 双击该行记录，弹出一个窗口，
 * 
 * 可以查看该记录的详细信息。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，参见【图二：新增/修改始发线路】。
 * 
 *  8) 分页按钮：实现分页功能。 
 *  
 *  2. 列表区域 1)
 *  
 *  
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 *  2) 列表中显示：线路名称、
 *  
 *  始发站、到达站、始发城市、
 *  
 *  运输类型、是否默认始发线路。
 *  
 *  
 * 3. 字段输入区域
 * 
 *  1) 查询条件包括线路名称、
 *  
 *  始发站、到达站、始发城市、
 *  
 *  运输类型、管理车队、线路简码。
 *  
 *   1.1 线路名称：文本，支持模糊查询
 *   
 *   
 * 1.2 始发站：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（营业部）
 * 
 * 基础资料中选取 1.3
 * 
 * 
 * 到达站：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（外场、空运总调）
 * 
 * 基础资料中选取 1.4
 * 
 * 
 * 始发城市：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政区域（城市）
 * 
 * 基础资料中选取
 * 
 *  1.5 运输类型：下拉框，
 *  
 *  默认为全部，
 *  
 *  包括：全部、汽运、空运
 *  
 *  
 * 1.6 管理车队：选择框，
 * 
 * 支持手动输入模糊查询，
 * 
 * 也支持从行政组织（车队）
 * 
 * 基础资料中选取
 * 

 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LineException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressOriginatingLineVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 快递始发线路控制类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-9
 * 下午6:12:14
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-9 下午6:12:14
 * @since
 * @version
 */
public class ExpressOriginatingLineAction extends AbstractAction {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressOriginatingLineAction.class);

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7284101423203887931L;

    /**
     * 始发线路服务类.
     */
    private IExpressLineService expresslineService;
    
    /**
     * 发车标准service.
     */
    private IExpressDepartureStandardService expressdepartureStandardService;

    /**
     * 前台注入参数.
     */
    private ExpressOriginatingLineVo lineVo = new ExpressOriginatingLineVo();
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    
    /**
     * 导出Excel 文件名.
     */
    private String downloadFileName;
    
    /**
     * 获取 导出Excel 文件名.
     *
     * @return the 导出Excel 文件名
     */
    public String getDownloadFileName() {
        return downloadFileName;
    }
    
    /**
     * 设置 导出Excel 文件名.
     *
     * @param downloadFileName the new 导出Excel 文件名
     */
    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
    /**
     * 设置 行政区域接口.
     *
     * @param administrativeRegionsService the administrativeRegionsService to set
     */
    public void setAdministrativeRegionsService(
    	IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }
    
    /**
     * 获取 导出Excel 文件流.
     *
     * @return the 导出Excel 文件流
     */
    public InputStream getInputStream() {
        return inputStream;
    }
    
    /**
     * 设置 导出Excel 文件流.
     *
     * @param inputStream the new 导出Excel 文件流
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 导出Excel 文件流.
     */
    private InputStream inputStream;

    /**
     * <p>
     * 新增始发线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String addOriginatingLine() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExpressLineEntity entity = lineVo.getEntity();
	    entity.setCreateUser(userCode);
	    entity.setModifyUser(userCode);
	    // 设置线路类型为：始发线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);

	    // 保存成功返回一个对象
	    entity = expresslineService.addLine(entity);
	    lineVo.setEntity(entity);
	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 作废始发线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String deleteOriginatingLine() {
	try {
	    UserEntity user = FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
//	    lineService.deleteLines(lineVo.getEntity().getIds(), userCode);
	    System.out.println(lineVo.getCodeList()+"=======================code");
	    expresslineService.deleteLineList(lineVo.getCodeList(), userCode);
	    return returnSuccess(MessageType.DELETE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 修改始发线路
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-9 下午6:12:14
     * @see
     */
    @JSON
    public String updateOriginatingLine() {

	try {
	    UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    // 获取Form信息
	    ExpressLineEntity entity = lineVo.getEntity();
	    entity.setModifyUser(userCode);
	    // 设置线路类型为：始发线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);

	    // 更新成功返回一个对象
	    entity = expresslineService.updateLine(entity);
	    lineVo.setEntity(entity);

	    return returnSuccess(MessageType.SAVE_SUCCESS);
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}

    }

    /**
     * <p>
     * 分页查询始发线路所有信息
     * </p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午1:49:44
     * @see
     */
    @JSON
    public String queryOriginatingLine() {
	try {
		if(null == lineVo.getEntity()){
			 return returnError("数据未成功提交,请重试");
		}			
	    ExpressLineEntity expressLineEntity = lineVo.getEntity();
	    // 设置线路类型为：始发线路
	    expressLineEntity
		    .setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
	    System.out.println(this.getStart()+"------------"+this.getLimit());
	    List<ExpressLineEntity> lineList = expresslineService.queryLineListByCondition(
		    expressLineEntity, this.getStart(), this.getLimit());
	    lineVo.setLineEntities(lineList);

	    // 查询总记录数
	    Long totalCount = expresslineService.countLineListByCondition(lineVo
		    .getEntity());
	    // 设置totalCount
	    this.setTotalCount(totalCount);

	    return returnSuccess();
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>根据虚拟编码查询线路信息和发车标准</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-13 上午11:25:38
     * @see
     */
    public String queryLineAndStandardByCode(){
	try {
	    String lineVirtualCode = lineVo.getLineVirtualCode();
	    //通过虚拟编码查询线路信息
	    ExpressLineEntity expressLineEntity = expresslineService.queryLineByVirtualCode(lineVirtualCode);
	    //发车标准
	    List<ExpressDepartureStandardEntity> departureStandardEntityList = expressdepartureStandardService
			.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
	    lineVo.setEntity(expressLineEntity);
	    lineVo.setDepartureStandardEntityList(convertList(departureStandardEntityList));
	    return returnSuccess();
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>导出始发线路数据至EXCEl</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-24 下午2:00:06
     * @see
     */
    public String exportOriginatingLines(){
	try {
	    //导出文件名称
	    //downloadFileName = URLEncoder.encode(ColumnConstants.EXPROT_ORIGINATING_LINE_NAME, "UTF-8");
	    downloadFileName =new String(ColumnConstants.EXPROT_EXPRESS_ORIGINATING_LINE_NAME.getBytes("UTF-8"),"iso-8859-1");
		//获取查询参数
	    ExpressLineEntity entity = lineVo.getEntity();
	    if(null == entity){
		entity = new ExpressLineEntity();
	    }
	    //设置线路类型为始发线路
	    entity.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
	    
	    //导出始发线路
	    ExportResource exportResource =expresslineService.exportLineList(entity);
	    ExportSetting exportSetting = new ExportSetting();
	    //设置名称
	    exportSetting.setSheetName(ColumnConstants.EXPROT_EXPRESS_ORIGINATING_LINE_NAME);
	    //设置下载最大条数
//	    exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
	    
	    return returnSuccess();
	} catch (BusinessException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError("UnsupportedEncodingException", e);
	}
    }
    
    /**
     * <p>根据城市编码查询城市名称</p>.
     *
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-1-7 下午6:49:30
     * @see
     */
    @JSON
    public String queryCityName(){
	try {
	    ExpressLineEntity entity = lineVo.getEntity();
	    //获取出发城市编码
	    String orginalCityCode = entity.getOrginalCityCode();
	    //获取到达城市编码
	    String destinationCityCode = entity.getDestinationCityCode();
	    if(StringUtil.isNotBlank(orginalCityCode)){
		String orginalCityName = administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(orginalCityCode);
		entity.setOrginalCityName(orginalCityName);
	    }else {
		entity.setOrginalCityName(null);
	    }
	    //根据编码查询城市名称
	    if(StringUtil.isNotBlank(destinationCityCode)){
		String destinationCityName = administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(destinationCityCode);
		entity.setDestinationCityName(destinationCityName);
	    }else {
		entity.setDestinationCityName(null);
	    }
	    
	    lineVo.setEntity(entity);
	    return returnSuccess();
	} catch (LineException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }
    
    /**
     * <p>批量为准点发车时间、准点到达时间添加冒号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午3:17:03
     * @param list
     * @return
     * @see
     */
    private List<ExpressDepartureStandardEntity> convertList(List<ExpressDepartureStandardEntity> list){
	if(!CollectionUtils.isEmpty(list)){
	    List<ExpressDepartureStandardEntity> entityList = new ArrayList<ExpressDepartureStandardEntity>();
	    for(ExpressDepartureStandardEntity entity : list){
		entityList.add(convertInfos(entity));
	    }
	    
	    return entityList;
	}else {
	    return list;
	}
    }
    
    /**
     * <p>为准点发车时间、准点到达时间添加冒号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:59:56
     * @param entity
     * @return
     * @see
     */
    private ExpressDepartureStandardEntity convertInfos(ExpressDepartureStandardEntity entity){
	if(null != entity){
	    //准点发车时间
	    entity.setLeaveTime(addColon(entity.getLeaveTime()));
	    // 准点到达时间
	    entity.setArriveTime(addColon(entity.getArriveTime()));
	    
	    return entity;
	}else {
	    return entity;
	}
    }
    
    /**
     * <p>添加冒号字符串</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-16 下午2:55:40
     * @param colonStr 
     * @return
     * @see
     */
    private String addColon(String colonStr){
	if(StringUtil.isNotBlank(colonStr)){
	    StringBuffer buffer = new StringBuffer();
	    //添加冒号
	    return buffer.append(colonStr.substring(NumberConstants.STRING_LOCATION_0, NumberConstants.STRING_LOCATION_2)).append(":").append(colonStr.substring(NumberConstants.STRING_LOCATION_2, NumberConstants.STRING_LOCATION_4)).toString();
	}else {
	    return colonStr;
	}
    }
    
    /**
     * 获取 前台注入参数.
     *
     * @return the 前台注入参数
     */
    public ExpressOriginatingLineVo getLineVo() {
	return lineVo;
    }

    /**
     * 设置 前台注入参数.
     *
     * @param lineVo the new 前台注入参数
     */
    public void setLineVo(ExpressOriginatingLineVo lineVo) {
	this.lineVo = lineVo;
    }

    /**
     * 设置 始发线路服务类.
     *
     * @param lineService the new 始发线路服务类
     */
    public void setExpresslineService(IExpressLineService expresslineService) {
	this.expresslineService = expresslineService;
    }

	
    
    /**
     * 设置 发车标准service.
     *
     * @param expressdepartureStandardService the new 发车标准service
     */
    public void setExpressdepartureStandardService(
			IExpressDepartureStandardService expressdepartureStandardService) {
		this.expressdepartureStandardService = expressdepartureStandardService;
	}
    

}
