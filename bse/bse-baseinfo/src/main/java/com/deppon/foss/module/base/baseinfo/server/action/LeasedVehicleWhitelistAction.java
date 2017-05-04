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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LeasedVehicleWhitelistAction.java
 * 
 * FILE NAME        	: LeasedVehicleWhitelistAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 *     
 * 修订记录 日期 修订内容 修订人员 版本号 2012-06-22 新增 李俊 V0.1 2012-06-27
 * 按照评审意见做一些修改，实现了简单的工作流。 李俊 V0.2 2012-06-27 添加了车辆的车架号 李俊 V0.3 2012-07-02
 * 添加了“行驶证、营运证，保险卡”，去掉了“车架号”。 李俊 V0.5 2012-07-06 根据评审意见,做出修改 高鹏 V0.6
 * 2012-07-16 根据评审意见,修改界面原型 高鹏 V0.65 2012-08-02 通过业务部门评审,并签字确认内容修改 高鹏 V0.9
 * 
 * 1. SUC-104-新增_修改_作废_查询外请车白名单 1.1 相关业务用例 BUC_FOSS_6.20.20_010外请车：约车。 1.2
 * 用例描述 该用例用于对外请车进行查询、白名单入库申请、调整可用/不可用属性申请、审核申请操作。 1.3 用例条件 条件类型 描述 引用系统用例
 * 前置条件 1， 外请拖头信息完备。 2， 外请厢式车信息完备。 3， 外请挂车信息完备。 SUC-608新增_修改_作废_查询外请拖头。
 * SUC-44新增_修改_作废_查询外请厢式车。 SUC-103新增_修改_作废_查询外请挂车。 后置条件 1.4 操作用户角色 操作用户 描述
 * 请车员 请车员可对外请车白名单进行入库申请、调整可用/不可用属性申请以及查询操作。 外请车白名单审核员
 * 外请车白名单审核员可对外请车白名单的入库申请、调整可用/不可用属性申请进行审核操作。 1.5 界面要求 1.5.1 表现方式 Web 页面
 * 1.5.2 界面原型-外请车白名单管理主界面
 * 
 * 图一：外请车白名单管理主界面 1.5.3 界面描述-外请车白名单管理主界面 1. 功能按钮区域 1)
 * 新增按钮：点击新增按钮进入新增界面，参见【图二：新增外请车白名单界面】。 2)
 * 查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图三：外请车白名单查看详情界面】。 3)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 4) 重置按钮：点击重置按钮，清空查询条件。 5)
 * 撤回按钮：请车员点击撤回按钮，弹出撤回确认对话框，点击确定按钮，若撤回成功，弹出撤回成功提示框，返回主界面，否则，提示撤回失败。 6)
 * 申请不可用按钮：请车员点击申请不可用按钮，进入外请车白名单申请可用/申请不可用界面，参见【图六：外请车白名单申请可用/申请不可用界面】。 7)
 * 申请可用按钮：请车员点击申请可用按钮，进入外请车白名单申请可用/申请不可用界面，参见【图六：外请车白名单申请可用/申请不可用界面】。 8)
 * 分页按钮：实现分页功能。 2. 列表显示区域 1) 列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。 2)
 * 参见数据元素【外请车白名单列表信息】。 3. 字段输入区域 录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
 * 
 * 1.5.4 界面原型-新增外请车白名单界面
 * 
 * 图二：新增外请车白名单界面 1.5.5 界面描述-新增外请车白名单界面 1. 功能按钮区域 1)
 * 保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
 * 2) 重置按钮：点击重置按钮，清空外请车白名单信息。 3) 取消按钮：点击取消按钮，退出当前界面，返回主界面。 2. 字段输入区域
 * 参见数据元素【外请车白名单信息】。 1.5.6 界面原型-外请车白名单查看详情界面
 * 
 * 图三：外请车白名单查看详情界面 1.5.7 界面描述-外请车白名单查看详情界面 1. 功能按钮区域 1) 返回按钮：点击返回按钮，返回上一级界面。
 * 1.5.8 界面原型-外请车白名单审核主界面
 * 
 * 图四：外请车白名单审核主界面 1.5.9 界面描述-外请车白名单审核主界面 1. 功能按钮区域 1)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 2) 重置按钮：点击重置按钮，清空查询条件。 3)
 * 处理按钮：外请车白名单审核员点击处理按钮，进入外请车白名单审核界面。参见【图五：外请车白名单审核界面】。 4)
 * 批量同意按钮：外请车白名单审核员选择一个或多个外请车白名单
 * ，点击批量同意按钮，弹出审核同意确认对话框，点击对话框中的确定按钮，批量同意外请车白名单的申请。 5)
 * 批量拒绝按钮：外请车白名单审核员选择多个外请车白名单
 * ，点击批量拒绝按钮，弹出审核拒绝确认对话框，点击对话框中的确定按钮，批量拒绝外请车白名单的申请。 6) 分页按钮：实现分页功能。 2.
 * 列表显示区域 1) 列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件显示列表数据。 2) 参见数据元素【外请车白名单列表信息】。 3.
 * 字段输入区域 录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。 1.5.10 界面原型-外请车白名单审核界面
 * 
 * 图五：外请车白名单审核界面 1.5.11 界面描述-外请车白名单审核界面 1. 功能按钮区域 1)
 * 同意按钮：添加备注信息，点击同意按钮，成功保存至数据库，返回上一级界面。 2)
 * 拒绝按钮：添加备注信息，点击拒绝按钮，成功保存至数据库，返回上一级界面。 3) 取消按钮：点击取消按钮，退出当前界面，返回上一级界面。 2.
 * 字段输入区域 参见数据元素【外请车白名单信息】。 1.5.12 界面原型-外请车白名单申请可用/申请不可用界面
 * 
 * 图六：外请车白名单申请可用/申请不可用界面 1.5.13 界面描述-外请车白名单申请可用/申请不可用界面 1. 功能按钮区域 1)
 * 保存按钮：点击保存按钮，若保存失败，提示用户保存失败，返回上一级界面。 2) 取消按钮：点击取消按钮，退出当前界面，返回上一级界面。 2.
 * 字段输入区域 参见数据元素【外请车白名单信息】。 1.6 操作步骤 1.6.1 外请车新增白名单操作步骤 序号 基本步骤 相关数据 补充步骤 1
 * 进入外请车白名单管理主界面。 2 点击新增按钮。 进入新增外请车白名单界面。 3 输入外请车白名单信息，点击保存按钮。
 * 参见【业务规则SR-9,SR-10,SR-11】 【外请车白名单信息】 成功保存至数据库。返回上一级界面。 外请车白名单新增操作步骤-异常操作
 * 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面。 3b
 * 若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。
 * 
 * 1.6.2 外请车白名单撤回操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请车白名单管理主界面。 2 输入查询条件，点击查询按钮，
 * 【外请车白名单列表信息】 系统返回查询结果。 3 找出需要撤回的外请车白名单，点击撤回按钮。 弹出撤回确认对话框。 4 点击确定按钮
 * 成功保存至数据库。返回上一级界面。 外请车白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮,
 * 退出当前界面，返回上一级界面。 4b 若撤回失败，弹出对话框，提示用户撤回失败以及失败原因。
 * 
 * 1.6.3 外请车白名单申请不可用操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请车白名单管理主界面。 2 输入查询条件，点击查询按钮，
 * 【外请车白名单列表信息】 系统返回查询结果。 3 找出需要申请不可用的外请车白名单，点击申请不可用按钮。 参见【业务规则SR-9,SR-10】
 * 进入外请车白名单申请不可用界面。 4 点击保存按钮。 成功保存至数据库。返回上一级界面。 外请车白名单作废操作步骤-异常操作 序号 扩展事件
 * 相关数据 备注 4a 点击取消按钮, 退出当前界面，返回上一级界面。 4b 若申请不可用失败，弹出对话框，提示用户申请不可用失败以及失败原因。
 * 
 * 1.6.4 外请车白名单申请可用操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请车白名单管理主界面。 2 输入查询条件，点击查询按钮，
 * 【外请车白名单列表信息】 系统返回查询结果。 3 找出需要申请可用的外请车白名单，点击申请可用按钮。 参见【业务规则SR-9,SR-10】
 * 进入外请车白名单申请可用界面。 4 点击保存按钮。 成功保存至数据库。返回上一级界面。 外请车白名单作废操作步骤-异常操作 序号 扩展事件
 * 相关数据 备注 4a 点击取消按钮, 退出当前界面，返回上一级界面。 4b 若申请可用失败，弹出对话框，提示用户申请可用操作失败以及失败原因。
 * 
 * 1.6.5 外请车白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请车白名单审核主界面 2 输入查询条件，点击查询按钮。
 * 【外请车白名单查询条件】 系统返回查询结果。 3 找出需要审核的外请车白名单。点击处理按钮。 参见【业务规则SR-9,SR-10】
 * 进入外请车白名单审核界面。 4 修改外请车白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。
 * 外请车白名单审核操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮, 退出当前界面，返回上一级界面。 4b
 * 点击拒绝按钮，成功保存审核数据至数据库。返回外请车白名单审核主界面。 4c
 * 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
 * 
 * 1.6.6 外请车白名单查询操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请车白名单管理主界面或者外请车白名单审核主界面 2
 * 输入查询条件，点击查询按钮。 【外请车白名单查询条件】，【外请车白名单列表信息】 系统返回查询结果。 3 点击分页按钮，查看各页的查询结果。 4
 * 选择要查看的外请车白名单双击。 参见【业务规则SR-9,SR-10】 【外请车白名单信息】 进入外请车白名单查看详情界面。 5 点击返回按钮。
 * 返回上一级界面。
 * 
 * 1.7 业务规则 序号 描述 SR-1
 * 请车员的查询条件中的白名单状态包括全部，未入库，可用，不可用。当前申请包括全部，无，申请入库，申请可用，申请不可用。
 * 外请车白名单审核员的查询条件中的白名单状态包括全部，未入库，可用，不可用。当前申请包括全部，申请入库，申请可用，申请不可用。 SR-2
 * 外请车白名单申请入库时，白名单状态为未入库，当前申请为申请入库。 SR-3
 * 如果当前申请为空，当白名单状态为“可用”，请车员只有“申请不可用”的操作，当白名单状态为“不可用”，请车员只有“申请可用”的操作。
 * 如果当前申请不为空，请车员只有“撤回”的操作。 SR-4
 * 外请车白名单审核员进入外请车白名单审核主界面，只能查询出待办列表。如果当前申请为“申请可用
 * ”，同意后外请车白名单状态为“可用”，拒绝后外请车白名单状态为
 * “不可用”，并且当前申请为空；如果当前申请为“申请不可用”，同意后外请车白名单状态为“
 * 不可用”，拒绝后外请车白名单状态为“可用”，并且当前申请为空；
 * 如果当前申请为“申请入库”，同意后外请车白名单状态为“可用”，拒绝后外请车白名单状态为“不可用”，当前申请为空。 SR-5
 * 当处理单个申请时，进入外请车白名单审核界面，可修改备注。当批量处理申请时，只弹出处理的确认对话框。 SR-6
 * 可从外请车信息中检索选择出来，也可手动输入
 * ,并同时自动带出车辆的车辆类型，车架号，车主姓名，车主联系方式由外请车基础资料。如果车牌号已经录入，则提示车牌号已录入，不能重复录入。 SR-7
 * 在外请车白名单详情界面，如果当前申请不为空，则不显示审核人和审核时间。 SR-8 外请车白名单审核主界面中，查询结果是按照申请时间倒序排列。
 * SR-9 营运证到期日期根据输入的车牌号自动从FOSS外请车基础资料里面获取过来。 SR-10
 * 行驶证证到期日期根据输入的车牌号自动从FOSS外请车基础资料里面获取过来。 SR-11
 * 输入车牌号,如果车牌号在白名单中已经存在则提示该车牌号的“白名单状态”及“当前申请”。
 * 
 * 1.8 数据元素 1.8.1 外请车白名单信息 字段名称 说明 输入限制 长度 是否必填 备注 车牌号 车牌号 文本 50 是
 * 参见【业务规则SR-6,SR-11】 白名单状态 外请车白名单的状态（未入库，可用，不可用） N/A N/A N/A 当前申请
 * 请车员的申请类型（申请入库，申请可用，申请不可用） N/A N/A N/A 车辆类型 包括拖头，挂车，厢式车 N/A N/A N/A
 * 根据“车牌号”，从外请车基础信息中带出 行驶证 行驶证号 N/A N/A N/A 根据“车牌号”，从外请车基础信息中带出 营运证 营运证号 N/A
 * N/A N/A 根据“车牌号”，从外请车基础信息中带出 保险卡 保险卡号 N/A N/A N/A 根据“车牌号”，从外请车基础信息中带出 车主姓名
 * 车主的姓名 N/A N/A N/A 根据“车牌号”，从外请车基础信息中带出 车主联系方式 车主手机号码 N/A N/A N/A
 * 根据“车牌号”，从外请车基础信息中带出 备注 请车员及外请车白名单审核员的备注 文本 255 否 申请人 请车员,系统自动填写 N/A N/A
 * N/A 申请时间 系统自动填写申请时间 N/A N/A N/A 审核人 外请车白名单审核员，系统自动填写 N/A N/A N/A 审核时间
 * 系统自动填写审核时间 N/A N/A N/A 营运证到期日期 此车营运证到期日期 N/A N/A 参见【业务规则SR-9】 行驶证到期日期
 * 此车行驶证到期日期 N/A N/A 参见【业务规则SR-10】
 * 
 * 1.8.2 外请车白名单列表信息 字段名称 说明 输入限制 长度 是否必填 备注 车牌号 车牌号 N/A N/A 白名单状态
 * 外请车白名单的状态（未入库，可用，不可用） N/A N/A 当前申请 请车员的申请类型（申请入库，申请可用，申请不可用） N/A N/A 车辆类型
 * 包括拖头，挂车，厢式车 N/A N/A 备注 请车员及外请车白名单审核员的备注 N/A N/A 申请人 请车员,系统自动填写 N/A N/A
 * 申请时间 系统自动填写申请时间 N/A N/A 营运证到期日期 此车营运证到期日期 N/A N/A 参见【业务规则SR-9】 行驶证到期日期
 * 此车行驶证到期日期 N/A N/A 参见【业务规则SR-10】
 * 
 * 1.8.3 外请车白名单查询条件 字段名称 说明 输入限制 长度 是否必填 备注 车牌号 车牌号 文本 50 否 白名单状态
 * 外请车白名单的状态（全部，未入库，可用，不可用） 下拉框 50 否 当前申请 请车员的申请类型（全部，申请入库，申请可用，申请不可用） 下拉框
 * 50 否 申请人 申请人 文本 50 否 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话）
 * 使用时间段 高峰使用时间段
 * 
 * 1.10 接口描述 接口名称 对方系统（外部系统或内部其他模块） 接口描述
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

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistAuditService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedVehicleWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistAuditException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleWhitelistException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedWhitelistVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.DateUtils;

/**
 * 用来响应“外请车白名单”申请（入库、可用、不可用、撤回）Action类：SUC-104
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-8 下午2:48:45
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * 
 * @date 2012-11-8 下午2:48:45
 * 
 * @since
 * 
 * @version
 */
public class LeasedVehicleWhitelistAction extends AbstractAction {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5161749331134987000L;

    /**
     * 日志.
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(LeasedVehicleWhitelistAction.class);
	
    /**
     * 前段界面常量
     */
    private static final String CURRENT_APPLICATION_ALL = "all";

    /**
     * 常量空值
     */
    private static final String CURRENT_APPLICATION_NULL = "null";
    
    /**
     * 参数和结果对象
     */
    private LeasedWhitelistVo leasedWhitelistVo;

    /**
     * "车辆"白名单申请服务
     */
    private ILeasedVehicleWhitelistService leasedVehicleWhitelistService;

    /**
     * "车辆"白名单审核服务
     */
    private ILeasedVehicleWhitelistAuditService leasedVehicleWhitelistAuditService;

    /**
     * 员工Service
     */
    private IEmployeeService employeeService;
	
	/**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
        
    /**
     * <p>
     * 查询所有司机白名单的列表
     * </p>
     * 
     * SR-1
     * 
     * 请车员的查询条件中的白名单状态包括全部，未入库，可用，不可用。
     * 
     * 当前申请包括全部，无，申请入库，申请可用，申请不可用。
     * 
     * 外请车白名单审核员的查询条件中的白名单状态包括全部，未入库，可用，不可用。
     * 
     * 当前申请包括全部，申请入库，申请可用，申请不可用。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:56:05
     * 
     * @return
     * 
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryLeasedVehicleWhitelistsList() {
    WhitelistAuditQueryDto whitelistAuditdto = getLeasedWhitelistVo().getWhitelistAuditQueryDto();
	PaginationDto pagination = null;
	try {
	    // 处理前端一些数据规则
	    if (StringUtils.equals(CURRENT_APPLICATION_ALL,
	    		whitelistAuditdto.getCurrentApplication())) {
	    	whitelistAuditdto.setCurrentApplication(null);
	    } else if (StringUtils.equals(CURRENT_APPLICATION_NULL,
	    		whitelistAuditdto.getCurrentApplication())) {
	    	whitelistAuditdto.setCurrentApplication(StringUtils.EMPTY);
	    }

	    //时间处理,结束日期加1天
	    if(whitelistAuditdto.getEndDate()!=null){
	    	//设置执行时使用小于结束日期+1天
			String dateEndTemp = DateUtils.addDay(whitelistAuditdto.getEndDate(), 1);
			//转换时间为格式
			Date dateEnd = DateUtils.convert(dateEndTemp);
			whitelistAuditdto.setEndDate(dateEnd);
	    }
	    /**
	     * 优化需求 RFOSS2015020408(DN201501230012)
	     * 在“车辆白名单”界面，当输入“车牌号”信息查询时，系统忽略其他查询条件
	     *  @author 189284-ZhangXu
         * @Date 2015-3-5  上午10:29:09
	     */
	    if(StringUtils.isNotEmpty(whitelistAuditdto.getVehicleNo())){
	    	String  vehicleNo=whitelistAuditdto.getVehicleNo();
	    	whitelistAuditdto =new WhitelistAuditQueryDto();
	    	whitelistAuditdto.setVehicleNo(vehicleNo);
	    }
	    pagination = leasedVehicleWhitelistService
		    .queryLeasedVehicleWhitelistsListBySelectiveCondition(
		    		whitelistAuditdto, start, limit);
	    getLeasedWhitelistVo().setWhitelistAuditList(
		    pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (BusinessException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }

    /**
     * <p>
     * 查询待审核的司机白名单列表
     * </p>
     * 
     * SR-4
     * 
     * 外请车白名单审核员进入外请车白名单审核主界面，只能查询出待办列表。
     * 
     * 如果当前申请为“申请可用”，同意后外请车白名单状态为“可用”，拒绝后外请车白名单状态为“不可用”，并且当前申请为空；
     * 
     * 如果当前申请为“申请不可用”，同意后外请车白名单状态为“不可用”，拒绝后外请车白名单状态为“可用”，并且当前申请为空；
     * 
     * 如果当前申请为“申请入库”，同意后外请车白名单状态为“可用”，拒绝后外请车白名单状态为“不可用”，当前申请为空。
     * 
     * SR-8
     * 
     * 外请车白名单审核主界面中，查询结果是按照申请时间倒序排列。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:53:42
     * 
     * @return
     * 
     * @see
     */
    @JSON
    @SuppressWarnings("unchecked")
    public String queryUnauditLeasedVehicleWhitelistsList() {
	WhitelistAuditEntity whitelistAudit = leasedWhitelistVo
		.getWhitelistAudit();
	PaginationDto pagination = null;
	try {
	    pagination = leasedVehicleWhitelistAuditService
		    .queryUnauditedLeasedVehicleWhitelistsBySelectiveCondition(
			    whitelistAudit, start, limit);
	   if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
		  //查询结果为空
		  LOGGER.info("queryUnauditLeasedVehicleWhitelistsList查询结果为空");
			// 返回结果
			return returnSuccess();
	   }
	   //声明员工工号
	   String empName = "";
	   for(int i = 0; i< pagination.getPaginationDtos().size();i++){
		   WhitelistAuditEntity entity =(WhitelistAuditEntity) pagination.getPaginationDtos().get(i);
		   empName = employeeService.queryEmpNameByEmpCode(entity.getApplyUser());
		   if(StringUtils.isNotEmpty(empName)){
			   entity.setApplyUser(empName);
		   }
	   }
	    getLeasedWhitelistVo().setWhitelistAuditList(
		    pagination.getPaginationDtos());
	    setTotalCount(pagination.getTotalCount());
	} catch (BusinessException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }

    /**
     * <p>
     * 根据ID来查询一个白名单的信息
     * </p>
     * 
     * SR-7
     * 
     * 在外请车白名单详情界面，如果当前申请不为空，则不显示审核人和审核时间。
     * 
     * SR-9
     * 
     * 营运证到期日期根据输入的车牌号自动从FOSS外请车基础资料里面获取过来。
     * 
     * SR-10
     * 
     * 行驶证证到期日期根据输入的车牌号自动从FOSS外请车基础资料里面获取过来。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:57:20
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String queryLeasedVehicleWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	try {
	    whitelistAudit = leasedVehicleWhitelistService
		    .queryLeasedVehicleWhitelistsBySelectiveCondition(whitelistAudit);
	    getLeasedWhitelistVo().setWhitelistAudit(whitelistAudit);
	} catch (BusinessException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }

    /**
     * <p>
     * 审核同意白名单的当前申请
     * </p>
     * 
     * 1.6.5 外请车白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单审核主界面
     * 
     * 2 输入查询条件，点击查询按钮。【外请车白名单查询条件】 系统返回查询结果。
     * 
     * 3 找出需要审核的外请车白名单。点击处理按钮。 参见【业务规则SR-9,SR-10】 进入外请车白名单审核界面。
     * 
     * 4 修改外请车白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。 外请车白名单审核
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 点击拒绝按钮，成功保存审核数据至数据库。返回外请车白名单审核主界面。
     * 
     * 4c 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午4:51:10
     * @return
     * @see
     */
    @JSON
    public String auditArgeeLeasedVehicleWhitelists() {
	List<String> ids = getLeasedWhitelistVo().getBatchIds();
	String auditNotes = getLeasedWhitelistVo().getComment();
	//String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
		
			leasedVehicleWhitelistAuditService
			.auditArgeeLeasedVehicleWhitelists(ids, auditNotes,
					getCurrentUser());
			return returnSuccess(LeasedVehicleWhitelistAuditException.AUDIT_SUCCESS_CODE);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 审核拒绝白名单的当前申请
     * </p>
     * 
     * 1.6.5 外请车白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单审核主界面
     * 
     * 2 输入查询条件，点击查询按钮。【外请车白名单查询条件】 系统返回查询结果。
     * 
     * 3 找出需要审核的外请车白名单。点击处理按钮。 参见【业务规则SR-9,SR-10】 进入外请车白名单审核界面。
     * 
     * 4 修改外请车白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。 外请车白名单审核
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 点击拒绝按钮，成功保存审核数据至数据库。返回外请车白名单审核主界面。
     * 
     * 4c 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午4:51:16
     * @return
     * @see
     */
    @JSON
    public String auditDeclineLeasedVehicleWhitelists() {
	List<String> ids = getLeasedWhitelistVo().getBatchIds();
	String auditNotes = getLeasedWhitelistVo().getComment();
	//String modifyUser = getCurrentUser().getEmpCode();
	try {
		leasedVehicleWhitelistAuditService
			.auditDeclineLeasedVehicleWhitelists(ids, auditNotes,getCurrentUser());
		return returnSuccess(LeasedVehicleWhitelistAuditException.AUDIT_SUCCESS_CODE);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 申请入库白名单信息
     * </p>
     * 
     * 1.6.1 外请车新增白名单操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单管理主界面。
     * 
     * 2 点击新增按钮。 进入新增外请车白名单界面。
     * 
     * 3 输入外请车白名单信息，点击保存按钮。 参见【业务规则SR-9,SR-10,SR-11】 【外请车白名单信息】
     * 成功保存至数据库。返回上一级界面。 外请车白名单新增
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 3a 点击取消按钮，退出当前界面，返回主界面。
     * 
     * 3b 若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。
     * 
     * 
     * SR-2
     * 
     * 外请车白名单申请入库时，白名单状态为未入库，当前申请为申请入库。
     * 
     * SR-3
     * 
     * 如果当前申请为空，当白名单状态为“可用”，请车员只有“申请不可用”的操作，
     * 
     * 当白名单状态为“不可用”，请车员只有“申请可用”的操作。
     * 
     * 如果当前申请不为空，请车员只有“撤回”的操作。
     * 
     * SR-5
     * 
     * 当处理单个申请时，进入外请车白名单审核界面，可修改备注。
     * 
     * 当批量处理申请时，只弹出处理的确认对话框。
     * 
     * SR-6
     * 
     * 可从外请车信息中检索选择出来，也可手动输入,并同时自动带出车辆的车辆类型，车架号，车主姓名，车主联系方式由外请车基础资料。
     * 
     * 如果车牌号已经录入，则提示车牌号已录入，不能重复录入。
     * 
     * SR-11
     * 
     * 输入车牌号,如果车牌号在白名单中已经存在则提示该车牌号的“白名单状态”及“当前申请”。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:58:56
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String applyLeasedVehicleWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353并发优化，车牌号加锁。
		mutex = new MutexElement(getLeasedWhitelistVo().
				getWhitelistAudit().getVehicleNo(), 
				"LEASED_VEHICLE_NO",MutexElementType.
				LEASED_VEHICLE_NO);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedVehicleWhitelistService.applyLeasedVehicleWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedVehicleWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("改车辆正在被操作，请稍等。");
		}
	} catch (BusinessException e) {
	    return returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
    }

    /**
     * <p>
     * 申请白名单为可用状态
     * </p>
     * 
     * 1.6.4 外请车白名单申请可用操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮， 【外请车白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要申请可用的外请车白名单，点击申请可用按钮。 参见【业务规则SR-9,SR-10】 进入外请车白名单申请可用界面。
     * 
     * 4 点击保存按钮。 成功保存至数据库。返回上一级界面。 外请车白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * b 若申请可用失败，弹出对话框，提示用户申请可用操作失败以及失败原因。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:59:11
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String availableLeasedVehicleWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353并发优化，车牌号加锁。
		mutex = new MutexElement(getLeasedWhitelistVo().
				getWhitelistAudit().getVehicleNo(), 
				"LEASED_VEHICLE_NO",MutexElementType.
				LEASED_VEHICLE_NO);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedVehicleWhitelistService.availableLeasedVehicleWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedVehicleWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("改车辆正在被操作，请稍等。");
		}
	} catch (BusinessException e) {
	    return returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
    }

    /**
     * <p>
     * 申请白名单为不可用状态
     * </p>
     * 
     * 1.6.3 外请车白名单申请不可用操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮， 【外请车白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要申请不可用的外请车白名单，点击申请不可用按钮。 参见【业务规则SR-9,SR-10】 进入外请车白名单申请不可用界面。
     * 
     * 4 点击保存按钮。 成功保存至数据库。返回上一级界面。 外请车白名单作废
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 若申请不可用失败，弹出对话框，提示用户申请不可用失败以及失败原因。
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:59:40
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String unAvailableLeasedVehicleWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353并发优化，车牌号加锁。
		mutex = new MutexElement(getLeasedWhitelistVo().
				getWhitelistAudit().getVehicleNo(), 
				"LEASED_VEHICLE_NO",MutexElementType.
				LEASED_VEHICLE_NO);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedVehicleWhitelistService.unavailableLeasedVehicleWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedVehicleWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("改车辆正在被操作，请稍等。");
		}
	} catch (BusinessException e) {
	    return returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
    }

    /**
     * <p>
     * 撤回白名单的申请
     * </p>
     * 
     * 1.6.2 外请车白名单撤回操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请车白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮， 【外请车白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要撤回的外请车白名单，点击撤回按钮。 弹出撤回确认对话框。
     * 
     * 4 点击确定按钮 成功保存至数据库。返回上一级界面。 外请车白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 若撤回失败，弹出对话框，提示用户撤回失败以及失败原因。
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午5:00:39
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String withdrawLeasedVehicleWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmpCode();
	MutexElement mutex =null;
	try {
		//优化加锁—313353并发优化，车牌号加锁。
		mutex = new MutexElement(getLeasedWhitelistVo().
				getWhitelistAudit().getVehicleNo(), 
				"LEASED_VEHICLE_NO",MutexElementType.
				LEASED_VEHICLE_NO);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedVehicleWhitelistService.withdrawLeasedVehicleWhitelists(
					whitelistAudit, modifyUser);
			return returnSuccess(LeasedVehicleWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("改车辆正在被操作，请稍等。");
		}
	} catch (BusinessException e) {
	    return returnError(e);
	} finally {
		if(mutex!=null){
			businessLockService.unlock(mutex);				
		}
	}
    }

    /**
     * <p>
     * 获取当前的登录用户
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:34:46
     * @return
     * @see
     */
    private CurrentInfo getCurrentUser() {
	CurrentInfo user = FossUserContext.getCurrentInfo(); 
	return user;
    }

    /**
     * @return the leasedWhitelistVo
     */
    public LeasedWhitelistVo getLeasedWhitelistVo() {
	if (null == leasedWhitelistVo) {
	    leasedWhitelistVo = new LeasedWhitelistVo();
	}
	return leasedWhitelistVo;
    }

    /**
     * @param leasedWhitelistVo
     *            the leasedWhitelistVo to set
     */
    public void setLeasedWhitelistVo(LeasedWhitelistVo leasedWhitelistVo) {
	this.leasedWhitelistVo = leasedWhitelistVo;
    }

    /**
     * @param leasedVehicleWhitelistService
     *            the leasedVehicleWhitelistService to set
     */
    public void setLeasedVehicleWhitelistService(
	    ILeasedVehicleWhitelistService leasedVehicleWhitelistService) {
	this.leasedVehicleWhitelistService = leasedVehicleWhitelistService;
    }

    /**
     * @param leasedVehicleWhitelistAuditService
     *            the leasedVehicleWhitelistAuditService to set
     */
    public void setLeasedVehicleWhitelistAuditService(
	    ILeasedVehicleWhitelistAuditService leasedVehicleWhitelistAuditService) {
	this.leasedVehicleWhitelistAuditService = leasedVehicleWhitelistAuditService;
    }

    /**
     * @param employeeService
     *            the employeeService to set
     */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}	
}