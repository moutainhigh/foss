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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/LeasedDriverWhitelistAction.java
 * 
 * FILE NAME        	: LeasedDriverWhitelistAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *      * 修订记录 日期 修订内容 修订人员 版本号 2012-06-22 新增 李俊 V0.1 2012-07-02
 * 添加了详情界面中的“驾驶证”号。修改了界面布局 李俊 V0.5 2012-07-06 根据关键用例评审，做出修改 高鹏 V0.6
 * 2012-08-02 通过业务部门评审,并签字确认内容修改 高鹏 V0.9
 * 
 * 1. SUC-750-新增_修改_作废_查询外请司机白名单 1.1 相关业务用例 BUC_FOSS_6.20.20_042维护车辆司机白名单
 * 1.2 用例描述 该用例用于对外请司机进行查询、白名单入库申请、调整可用/不可用属性申请、审核申请操作。 1.3 用例条件 条件类型 描述
 * 引用系统用例 前置条件 1， 外请司机信息完备。 SUC-211新增_修改_作废_查询外请车司机 后置条件 1.4 操作用户角色 操作用户 描述
 * 请车员 请车员可对外请司机白名单进行入库申请、调整可用/不可用属性申请以及查询操作。 外请司机白名单审核员
 * 外请司机白名单审核员可对外请司机白名单的入库申请、调整可用/不可用属性申请进行审核操作。 1.5 界面要求 1.5.1 表现方式 Web页面
 * 1.5.2 界面原型-外请司机白名单管理主界面
 * 
 * 图一：外请司机白名单管理主界面 1.5.3 界面描述-外请司机白名单管理主界面 1. 功能按钮区域 1)
 * 申请入库按钮：点击申请入库按钮进入申请入库界面，参见【图二：外请司机白名单申请入库界面】。 2)
 * 查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图三：外请司机白名单查看详情界面】。 3)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 4) 重置按钮：点击重置按钮，清空查询条件。 5)
 * 撤回按钮：请车员点击撤回按钮，弹出撤回确认对话框，点击确定按钮，若撤回成功，弹出撤回成功提示框，返回主界面，否则，提示撤回失败。 6)
 * 申请不可用按钮：请车员点击申请不可用按钮，进入外请司机白名单申请可用/申请不可用界面，参见【图六：外请司机白名单申请可用/申请不可用界面】。 7)
 * 申请可用按钮：请车员点击申请可用按钮，进入外请司机白名单申请可用/申请不可用界面，参见【图六：外请司机白名单申请可用/申请不可用界面】。 8)
 * 分页按钮：实现分页功能。 2. 列表显示区域 1) 列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。 2)
 * 参见数据元素【外请司机白名单列表信息】。 3. 字段输入区域 录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
 * 
 * 1.5.4 界面原型-外请司机白名单申请入库界面
 * 
 * 图二：外请司机白名单申请入库界面 1.5.5 界面描述-外请司机白名单申请入库界面 1. 功能按钮区域 1)
 * 保存按钮：点击保存按钮，参见【业务规则SR
 * -9】，点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
 * 2) 重置按钮：点击重置按钮，清空外请司机白名单信息。 3) 取消按钮：点击取消按钮，退出当前界面，返回主界面。 2. 字段输入区域
 * 参见数据元素【外请司机白名单信息】。 1.5.6 界面原型-外请司机白名单查看详情界面
 * 
 * 图三：外请司机白名单查看详情界面 1.5.7 界面描述-外请司机白名单查看详情界面 1. 功能按钮区域 1)
 * 返回按钮：点击返回按钮，返回上一级界面。 1.5.8 界面原型-外请司机白名单审核主界面
 * 
 * 图四：外请司机白名单审核主界面 1.5.9 界面描述-外请司机白名单审核主界面 1. 功能按钮区域 1)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 2) 重置按钮：点击重置按钮，清空查询条件。 3)
 * 处理按钮：外请司机白名单审核员点击处理按钮，进入外请司机白名单审核界面。参见【图五：外请司机白名单审核界面】。 4)
 * 批量同意按钮：外请司机白名单审核员选择多个外请司机白名单
 * ，点击批量同意按钮，弹出审核同意确认对话框，点击对话框中的确定按钮，批量同意外请司机白名单的申请。 5)
 * 批量拒绝按钮：外请司机白名单审核员选择多个外请司机白名单
 * ，点击批量拒绝按钮，弹出审核拒绝确认对话框，点击对话框中的确定按钮，批量拒绝外请司机白名单的申请。 6) 分页按钮：实现分页功能。 2.
 * 列表显示区域 1) 列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件显示列表数据。 2) 参见数据元素【外请司机白名单列表信息】。
 * 3. 字段输入区域 录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
 * 
 * 
 * 1.5.10 界面原型-外请司机白名单审核界面
 * 
 * 图五：外请司机白名单审核界面 1.5.11 界面描述-外请司机白名单审核界面 1. 功能按钮区域 1)
 * 同意按钮：添加备注信息，点击同意按钮，成功保存至数据库，返回上一级界面。 2)
 * 拒绝按钮：添加备注信息，点击拒绝按钮，成功保存至数据库，返回上一级界面。 3) 取消按钮：点击取消按钮，退出当前界面，返回上一级界面。 2.
 * 字段输入区域 参见数据元素【外请司机白名单信息】。 1.5.12 界面原型-外请司机白名单申请可用/申请不可用界面
 * 
 * 图六：外请司机白名单申请可用/申请不可用界面 1.5.13 界面描述-外请司机白名单申请可用/申请不可用界面 1. 功能按钮区域 1)
 * 保存按钮：点击保存按钮，若保存失败，提示用户保存失败，返回上一级界面。 2) 取消按钮：点击取消按钮，退出当前界面，返回上一级界面。 2.
 * 字段输入区域 参见数据元素【外请司机白名单信息】。 1.6 操作步骤 1.6.1 外请司机白名单申请入库操作步骤 序号 基本步骤 相关数据
 * 补充步骤 1 进入【图一：外请司机白名单管理主界面】 2 点击申请入库按钮。 参见【业务规则SR-9】 进入外请司机白名单申请入库界面。 3
 * 输入外请司机白名单信息，点击保存按钮。 【外请司机白名单信息】 成功保存至数据库。返回上一级界面。 4 返回【图一】
 * 外请司机白名单申请入库操作步骤-异常操作 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面。 3b
 * 若申请失败，弹出对话框，提示用户申请失败以及失败原因，继续停留在申请入库界面。
 * 
 * 1.6.2 外请司机白名单撤回操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请司机白名单管理主界面。 2 输入查询条件，点击查询按钮，
 * 【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。 3 找出需要撤回的外请司机白名单，点击撤回按钮。 弹出撤回确认对话框。
 * 4 点击确定按钮 成功保存至数据库。返回上一级界面。 外请司机白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮,
 * 退出当前界面，返回上一级界面。 4b 若撤回失败，弹出对话框，提示用户撤回失败以及失败原因。
 * 
 * 1.6.3 外请司机白名单申请不可用操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请司机白名单管理主界面。 2
 * 输入查询条件，点击查询按钮， 【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。 3
 * 找出需要申请不可用的外请司机白名单，点击申请不可用按钮。 进入外请司机白名单申请不可用界面。 4 点击保存按钮。
 * 成功保存至数据库。返回上一级界面。 外请司机白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮,
 * 退出当前界面，返回上一级界面。 4b 若申请不可用失败，弹出对话框，提示用户申请不可用失败以及失败原因。
 * 
 * 1.6.4 外请司机白名单申请可用操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请司机白名单管理主界面。 2
 * 输入查询条件，点击查询按钮， 【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。 3
 * 找出需要申请可用的外请司机白名单，点击申请可用按钮。 进入外请司机白名单申请可用界面。 4 点击保存按钮。 成功保存至数据库。返回上一级界面。
 * 外请司机白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮, 退出当前界面，返回上一级界面。 4b
 * 若申请可用失败，弹出对话框，提示用户申请可用操作失败以及失败原因。
 * 
 * 1.6.5 外请司机白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请司机白名单审核主界面 2 输入查询条件，点击查询按钮。
 * 【外请司机白名单待办列表查询条件】，【外请司机白名单列表信息】 系统返回查询结果。 3 找出需要审核的外请司机白名单。点击处理按钮。
 * 进入外请司机白名单审核界面。 4 修改外请司机白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。
 * 外请司机白名单审核操作步骤-异常操作 序号 扩展事件 相关数据 备注 4a 点击取消按钮, 退出当前界面，返回上一级界面。 4b
 * 点击拒绝按钮，成功保存审核数据至数据库。返回外请司机白名单审核主界面。 4c
 * 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
 * 
 * 1.6.6 外请司机白名单查询操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入外请司机白名单管理主界面或者外请司机白名单审核主界面 2
 * 输入查询条件，点击查询按钮。 【外请司机白名单查询条件】，【外请司机白名单待办列表查询条件】，【外请司机白名单列表信息】 系统返回查询结果。 3
 * 点击分页按钮，查看各页的查询结果。 4 选择要查看的外请司机白名单双击。 【外请司机白名单信息】 进入外请司机白名单查看详情界面。 5
 * 点击返回按钮。 返回上一级界面。
 * 
 * 1.7 业务规则 序号 描述 SR-1
 * 请车员的查询条件中的白名单状态包括全部，未入库，可用，不可用。当前申请包括全部，无，申请入库，申请可用，申请不可用。
 * 外请司机白名单审核员的查询条件中的白名单状态包括全部，未入库，可用，不可用。当前申请包括全部，申请入库，申请可用，申请不可用。 SR-2
 * 外请司机白名单申请入库时，白名单状态为未入库，当前申请为申请入库。 SR-3
 * 如果当前申请为空，当白名单状态为“可用”，请车员只有“申请不可用”的操作，当白名单状态为“不可用”，请车员只有“申请可用”的操作。
 * 如果当前申请不为空，请车员只有“撤回”的操作。 SR-4
 * 外请司机白名单审核员进入外请司机白名单审核主界面，只能查询出待办列表。如果当前申请为“申请可用
 * ”，同意后外请司机白名单状态为“可用”，拒绝后外请司机白名单状态为
 * “不可用”，并且当前申请为空；如果当前申请为“申请不可用”，同意后外请司机白名单状态为
 * “不可用”，拒绝后外请司机白名单状态为“可用”，并且当前申请为空
 * ；如果当前申请为“申请入库”，同意后外请司机白名单状态为“可用”，拒绝后外请司机白名单状态为“不可用”，并且当前申请为空。 SR-5
 * 当处理单个申请时，进入外请司机白名单审核界面，可修改备注。当批量处理申请时，只弹出处理的确认对话框。 SR-6
 * 在申请入库的时候，司机身份证号可手动输入
 * ，也可从外请车司机中自动获取并选择，而后司机的姓名、电话、从业资格证和驾驶证号由外请车司机基础资料自动带出；不支持手动录入身份证号。 SR-7
 * 在外请司机白名单详情界面，如果当前申请不为空，则不显示审核人和审核时间。 SR-8 外请司机白名单审核主界面中，查询结果是按照申请时间倒序排列。
 * SR-9 输入外请司机身份证号，如果身份号在白名单中已经存在，则提示出“白名单状态”和“当前申请状态”
 * 
 * 1.8 数据元素 1.8.1 外请司机白名单信息 字段名称 说明 输入限制 长度 是否必填 备注 司机姓名 司机姓名 N/A N/A N/A
 * 根据“司机身份证号”，从外请司机基础信息中带出 司机电话 常用联系方式 N/A N/A N/A 根据“司机身份证号”，从外请司机基础信息中带出
 * 司机身份证号 司机身份证号码 N/A 50 是 司机身份证号不支持输入，是从外请车司机信息中检索选择出来，参见【业务规则SR-6】 从业资格证
 * 从业资格证 N/A N/A N/A 根据“司机身份证号”，从外请司机基础信息中带出 驾驶证 驾驶证号 N/A N/A N/A
 * 根据“司机身份证号”，从外请司机基础信息中带出 白名单状态 外请司机白名单的状态（未入库，可用，不可用） N/A N/A N/A 当前申请
 * 请车员的申请类型（申请入库，申请可用，申请不可用） N/A N/A N/A 备注 请车员及外请司机白名单审核员的备注 文本 255 否 申请人
 * 请车员,系统自动填写 N/A N/A N/A 申请时间 系统自动填写申请时间 N/A N/A N/A 审核人 外请司机白名单审核员，系统自动填写
 * N/A N/A N/A 审核时间 系统自动填写审核时间 N/A N/A N/A
 * 
 * 1.8.2 外请司机白名单列表信息 字段名称 说明 输入限制 长度 是否必填 备注 司机身份证号 司机身份证号码 N/A N/A N/A 司机姓名
 * 司机姓名 N/A N/A N/A 白名单状态 外请司机白名单的状态（未入库，可用，不可用） N/A N/A N/A 当前申请
 * 请车员的申请类型（申请入库，申请可用，申请不可用） N/A N/A N/A 备注 请车员及外请司机白名单审核员的备注 N/A N/A N/A
 * 申请人 请车员,系统自动填写 N/A N/A N/A 申请时间 系统自动填写申请时间 N/A N/A N/A
 * 
 * 1.8.3 外请司机白名单查询条件 字段名称 说明 输入限制 长度 是否必填 备注 司机身份证号 司机身份证号 文本 50 否 司机姓名 司机姓名
 * 文本 50 否 白名单状态 外请司机白名单的状态（未入库，可用，不可用） 下拉框 N/A N/A 当前申请
 * 请车员的申请类型（申请入库，申请可用，申请不可用） 下拉框 N/A N/A
 * 
 * 1.8.4 外请司机白名单待办列表查询条件 字段名称 说明 输入限制 长度 是否必填 备注 司机身份证号 司机身份证号 文本 50 否 司机姓名
 * 司机姓名 文本 50 否 当前申请 请车员的申请类型（申请入库，申请可用，申请不可用） 下拉框 N/A N/A 申请人 申请人 文本 50 否
 * 
 * 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 使用时间段 高峰使用时间段
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

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistAuditService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILeasedDriverWhitelistService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverWhitelistAuditException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverWhitelistException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LeasedWhitelistVo;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 用来响应“外请司机白名单”申请（入库、可用、不可用、撤回）Action类：SUC-750
 * <p style="display:none">
 * 
 * modifyRecord
 * 
 * </p>
 * 
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
public class LeasedDriverWhitelistAction extends AbstractAction {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -5161749331134987000L;

    /**
     * 日志
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(LeasedDriverWhitelistAction.class);
    
    /**
     * 前段界面常量All
     */
    private static final String CURRENT_APPLICATION_ALL = "all";

    /**
     * 前段界面常量null
     */
    private static final String CURRENT_APPLICATION_NULL = "null";

    /**
     * "外请司机白名单"参数和结果对象
     */
    private LeasedWhitelistVo leasedWhitelistVo;

    /**
     * "外请司机"白名单申请服务
     */
    private ILeasedDriverWhitelistService leasedDriverWhitelistService;

    /**
     * "外请司机"白名单审核服务
     */
    private ILeasedDriverWhitelistAuditService leasedDriverWhitelistAuditService;
    
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
     * 外请司机白名单审核员的查询条件中的白名单状态包括全部，未入库，可用，不可用。
     * 
     * 当前申请包括全部，申请入库，申请可用，申请不可用。
     * 
     * SR-3
     * 
     * 如果当前申请为空，当白名单状态为“可用”，请车员只有“申请不可用”的操作，
     * 
     * 当白名单状态为“不可用”，请车员只有“申请可用”的操作。
     * 
     * 如果当前申请不为空，请车员只有“撤回”的操作。
     * 
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
    public String queryLeasedDriverWhitelistsList() {
	WhitelistAuditEntity whitelistAudit = leasedWhitelistVo
		.getWhitelistAudit();
	PaginationDto pagination = null;
	try {
	    // 处理前端一些数据规则
	    if (StringUtils.equals(CURRENT_APPLICATION_ALL,
		    whitelistAudit.getCurrentApplication())) {
		whitelistAudit.setCurrentApplication(null);
	    } else if (StringUtils.equals(CURRENT_APPLICATION_NULL,
		    whitelistAudit.getCurrentApplication())) {
		whitelistAudit.setCurrentApplication(StringUtils.EMPTY);
	    }

	    pagination = leasedDriverWhitelistService
		    .queryLeasedDriverWhitelistsListBySelectiveCondition(
			    whitelistAudit, start, limit);
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
			  //查询结果为空
			  LOGGER.info("queryLeasedDriverWhitelistsList查询结果为空");
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
	} catch (LeasedDriverWhitelistException e) {
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
     * 外请司机白名单审核员进入外请司机白名单审核主界面，只能查询出待办列表。
     * 
     * 如果当前申请为“申请可用”，同意后外请司机白名单状态为“可用”，拒绝后外请司机白名单状态为“不可用”，并且当前申请为空；
     * 
     * 如果当前申请为“申请不可用”，同意后外请司机白名单状态为“不可用”，拒绝后外请司机白名单状态为“可用”，并且当前申请为空；
     * 
     * 如果当前申请为“申请入库”，同意后外请司机白名单状态为“可用”，拒绝后外请司机白名单状态为“不可用”，并且当前申请为空。
     * 
     * SR-8
     * 
     * 外请司机白名单审核主界面中，查询结果是按照申请时间倒序排列。
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
    public String queryUnauditLeasedDriverWhitelistsList() {
	WhitelistAuditEntity whitelistAudit = leasedWhitelistVo
		.getWhitelistAudit();
	PaginationDto pagination = null;
	try {
	    pagination = leasedDriverWhitelistAuditService
		    .queryUnauditedLeasedDriverWhitelistsBySelectiveCondition(
			    whitelistAudit, start, limit);
	    if(null == pagination || CollectionUtils.isEmpty(pagination.getPaginationDtos())){
			  //查询结果为空
			  LOGGER.info("queryUnauditLeasedDriverWhitelistsList查询结果为空");
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
	} catch (LeasedDriverWhitelistAuditException e) {
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
     * 在外请司机白名单详情界面，如果当前申请不为空，则不显示审核人和审核时间。
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
    public String queryLeasedDriverWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	try {
	    whitelistAudit = leasedDriverWhitelistService
		    .queryLeasedDriverWhitelistsBySelectiveCondition(whitelistAudit);
	    getLeasedWhitelistVo().setWhitelistAudit(whitelistAudit);
	} catch (LeasedDriverWhitelistException e) {
	    return returnError(e);
	}
	return returnSuccess();
    }

    /**
     * <p>
     * 审核同意白名单的当前申请
     * </p>
     * 
     * 1.6.5 外请司机白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请司机白名单审核主界面
     * 
     * 2 输入查询条件，点击查询按钮。【外请司机白名单待办列表查询条件】，【外请司机白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要审核的外请司机白名单。点击处理按钮。进入外请司机白名单审核界面。
     * 
     * 4 修改外请司机白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。外请司机白名单审
     * 
     * 核操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 点击拒绝按钮，成功保存审核数据至数据库。返回外请司机白名单审核主界面。
     * 
     * 4c 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午4:51:10
     * @return
     * @see
     */
    @JSON
    public String auditArgeeLeasedDriverWhitelists() {
	List<String> ids = getLeasedWhitelistVo().getBatchIds();
	String auditNotes = getLeasedWhitelistVo().getComment();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try{
		leasedDriverWhitelistAuditService.auditArgeeLeasedDriverWhitelists(
			ids, auditNotes, modifyUser);
		return returnSuccess(LeasedDriverWhitelistAuditException.AUDIT_SUCCESS_CODE);
	} catch (LeasedDriverWhitelistAuditException e) {
	    return returnError(e);
	} catch (BusinessException e) {
		return returnError(e);
	}
    }

    /**
     * <p>
     * 审核拒绝白名单的当前申请
     * </p>
     * 
     * 1.6.5 外请司机白名单审核操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请司机白名单审核主界面
     * 
     * 2 输入查询条件，点击查询按钮。【外请司机白名单待办列表查询条件】，【外请司机白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要审核的外请司机白名单。点击处理按钮。进入外请司机白名单审核界面。
     * 
     * 4 修改外请司机白名单的备注信息，点击同意按钮。 成功保存审核数据至数据库。返回上一级界面。外请司机白名单审
     * 
     * 核操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 点击拒绝按钮，成功保存审核数据至数据库。返回外请司机白名单审核主界面。
     * 
     * 4c 若操作失败，弹出对话框，提示用户操作失败以及失败原因，继续停留在当前界面。
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-8 下午4:51:16
     * 
     * @return
     * 
     * @see
     */
    @JSON
    public String auditDeclineLeasedDriverWhitelists() {
	List<String> ids = getLeasedWhitelistVo().getBatchIds();
	String auditNotes = getLeasedWhitelistVo().getComment();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	try {
		leasedDriverWhitelistAuditService
			.auditDeclineLeasedDriverWhitelists(ids, auditNotes,
					modifyUser);
		return returnSuccess(LeasedDriverWhitelistAuditException.AUDIT_SUCCESS_CODE);
	} catch (LeasedDriverWhitelistAuditException e) {
	    return returnError(e);
	} catch (BusinessException e) {
		return returnError(e);
	}
    }

    /**
     * <p>
     * 申请入库白名单信息
     * </p>
     * 
     * 1.6.1 外请司机白名单申请入库操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入【图一：外请司机白名单管理主界面】
     * 
     * 2 点击申请入库按钮。参见【业务规则SR-9】 进入外请司机白名单申请入库界面。
     * 
     * 3 输入外请司机白名单信息，点击保存按钮。 【外请司机白名单信息】成功保存至数据库。返回上一级界面。
     * 
     * 4 返回【图一】 外请司机白名单申请入库
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 3a 点击取消按钮，退出当前界面，返回主界面。
     * 
     * 3b 若申请失败，弹出对话框，提示用户申请失败以及失败原因，继续停留在申请入库界面。
     * 
     * SR-2
     * 
     * 外请司机白名单申请入库时，白名单状态为未入库，当前申请为申请入库。
     * 
     * SR-5
     * 
     * 当处理单个申请时，进入外请司机白名单审核界面，可修改备注。当批量处理申请时，只弹出处理的确认对话框。
     * 
     * SR-6
     * 
     * 在申请入库的时候，司机身份证号可手动输入，也可从外请车司机中自动获取并选择，
     * 
     * 而后司机的姓名、电话、从业资格证和驾驶证号由外请车司机基础资料自动带出；
     * 
     * 不支持手动录入身份证号。
     * 
     * SR-9
     * 
     * 输入外请司机身份证号，如果身份号在白名单中已经存在，则提示出“白名单状态”和“当前申请状态”
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
    public String applyLeasedDriverWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353。
		mutex = new MutexElement(getLeasedWhitelistVo().
			getWhitelistAudit().getDriverIdCard(), 
			"LEASED_DRIVER_IDCARD",MutexElementType.
			LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedDriverWhitelistService.applyLeasedDriverWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedDriverWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverWhitelistException e) {
	    return returnError(e);
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
     * 1.6.4 外请司机白名单申请可用操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请司机白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮， 【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要申请可用的外请司机白名单，点击申请可用按钮。 进入外请司机白名单申请可用界面。
     * 
     * 4 点击保存按钮。 成功保存至数据库。返回上一级界面。外请司机白名单作废
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮, 退出当前界面，返回上一级界面。
     * 
     * 4b 申请可用失败，弹出对话框，提示用户申请可用操作失败以及失败原因。
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午4:59:11
     * @return
     * @see
     */
    @JSON
    public String availableLeasedDriverWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353。
		mutex = new MutexElement(getLeasedWhitelistVo().
			getWhitelistAudit().getDriverIdCard(), 
			"LEASED_DRIVER_IDCARD",MutexElementType.
			LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedDriverWhitelistService.availableLeasedDriverWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedDriverWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverWhitelistException e) {
	    return returnError(e);
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
     * 1.6.3 外请司机白名单申请不可用操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请司机白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮， 【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要申请不可用的外请司机白名单，点击申请不可用按钮。 进入外请司机白名单申请不可用界面。
     * 
     * 4 点击保存按钮。成功保存至数据库。返回上一级界面。 外请司机白名单作废
     * 
     * 操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮,退出当前界面，返回上一级界面。
     * 
     * 4b 若申请不可用失败，弹出对话框，提示用户申请不可用失败以及失败原因。
     * 
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午4:59:40
     * @return
     * @see
     */
    @JSON
    public String unAvailableLeasedDriverWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	String applyNotes = getLeasedWhitelistVo().getComment();
	MutexElement mutex =null;
	try {
		//优化加锁—313353。
		mutex = new MutexElement(getLeasedWhitelistVo().
			getWhitelistAudit().getDriverIdCard(), 
			"LEASED_DRIVER_IDCARD",MutexElementType.
			LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedDriverWhitelistService.unAvailableLeasedDriverWhitelists(
					whitelistAudit, applyNotes, modifyUser);
			return returnSuccess(LeasedDriverWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverWhitelistException e) {
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
     * 1.6.2 外请司机白名单撤回操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入外请司机白名单管理主界面。
     * 
     * 2 输入查询条件，点击查询按钮，【外请司机白名单查询条件】，【外请司机白名单列表信息】 系统返回查询结果。
     * 
     * 3 找出需要撤回的外请司机白名单，点击撤回按钮。 弹出撤回确认对话框。
     * 
     * 4 点击确定按钮 成功保存至数据库。返回上一级界面。
     * 
     * 外请司机白名单作废操作步骤-异常操作 序号 扩展事件 相关数据 备注
     * 
     * 4a 点击取消按钮,退出当前界面，返回上一级界面。
     * 
     * 4b 若撤回失败，弹出对话框，提示用户撤回失败以及失败原因。
     * 
     * 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-8 下午5:00:39
     * @return
     * @see
     */
    @JSON
    public String withdrawLeasedDriverWhitelists() {
	WhitelistAuditEntity whitelistAudit = getLeasedWhitelistVo()
		.getWhitelistAudit();
	String modifyUser = getCurrentUser().getEmployee().getEmpCode();
	MutexElement mutex =null;
	try {
		//优化加锁—313353。
		mutex = new MutexElement(getLeasedWhitelistVo().
			getWhitelistAudit().getDriverIdCard(), 
			"LEASED_DRIVER_IDCARD",MutexElementType.
			LEASED_DRIVER_IDCARD);
		boolean result = businessLockService.lock(mutex,
				NumberConstants.ZERO);
		if (result) {
			leasedDriverWhitelistService.withdrawLeasedDriverWhitelists(
					whitelistAudit, modifyUser);
			return returnSuccess(LeasedDriverWhitelistException.APPLY_SUCCESS_CODE);
		} else {
			return returnError("该司机数据正在更新中，请稍后再操作。");
		}
	} catch (LeasedDriverWhitelistException e) {
	    return returnError(e);
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
    private UserEntity getCurrentUser() {
	UserEntity user = FossUserContext.getCurrentUser();
	return null == user ? new UserEntity() : user;
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
     * @param leasedDriverWhitelistService
     *            the leasedDriverWhitelistService to set
     */
    public void setLeasedDriverWhitelistService(
	    ILeasedDriverWhitelistService leasedDriverWhitelistService) {
	this.leasedDriverWhitelistService = leasedDriverWhitelistService;
    }

    /**
     * @param leasedDriverWhitelistAuditService
     *            the leasedDriverWhitelistAuditService to set
     */
    public void setLeasedDriverWhitelistAuditService(
	    ILeasedDriverWhitelistAuditService leasedDriverWhitelistAuditService) {
	this.leasedDriverWhitelistAuditService = leasedDriverWhitelistAuditService;
    }
    
    /**
     * @param employeeService
     *            the employeeService to set
     */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}
