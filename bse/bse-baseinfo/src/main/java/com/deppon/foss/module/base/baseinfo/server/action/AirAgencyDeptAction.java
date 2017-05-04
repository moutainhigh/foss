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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AirAgencyDeptAction.java
 * 
 * FILE NAME        	: AirAgencyDeptAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 修订记录 
 * 
 * 日期 修订内容 修订人员 版本号 2012-06-27 始创 石巍 V0.1 2012-07-04 增加承运业务 石巍 V0.2 2012-07-05
 * 根据ITA王偕旭评审意见修改 石巍 V0.3 2012-07-05
 * 更名为空运代理网点；增加管理部门属性；关联分离后的空运代理网点公司；增加操作用户：空运代理网点维护员 石巍 V0.4 2012-07-11
 * 根据石巍的提示把空运代理网点编码由系统自动生成变为操作员手工输入 周春来 V0.5 2012-08-02 通过业务部门审核签字版本升级到V0.9 周春来
 * V0.9 2012-09-08 根据JIRA rc-35修改 新增/修改页面图示显示字段内容和功能描述及数据元素字段不一致 周春来 V1.0 1.
 * SUC-720-新增_修改_作废_查询空运代理网点 1.1 相关业务用例 BUC_FOSS_5.20.30_510确认承运信息
 * BUC_FOSS_5.70.30_060制作航空正单 1.2 用例描述 系统管理员或空运代理维护员使用该用例新增、修改、作废、查询空运代理网点信息 1.3
 * 用例条件 条件类型 描述 引用系统用例 前置条件 1、 行政区域基础资料完备； 2、 空运代理公司基础资料完备；
 * SUC-33新增_修改_作废_查询行政区域 SUC-786新增_修改_作废_查询空运代理公司 后置条件 1、
 * 录入运单信息中的提货网点时能正常获取所需的空运代理网点； 2、 录入外发航空正单信息中的外发代理时能正常获取所需的空运代理网点；
 * SUC-496录入运输信息 SUC-247录入航空正单明细 1.4 操作用户角色 操作用户 描述 系统管理员
 * 系统管理员负责对空运代理网点基础资料进行新增、修改、作废、查询操作 空运代理维护员 空运代理维护员负责对空运代理网点基础资料进行新增、修改、作废、查询操作
 * 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2 界面原型-空运代理网点查询界面
 * 
 * 图 1：空运代理网点查询界面 1.5.3 界面描述-空运代理网点查询界面 该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区； 1.
 * 查询条件区域：查询条件包括空运代理网点编码、空运代理网点、所属空运代理公司、城市、增值业务； 1.1 空运代理网点：共用选择框，内容读取自本基础资料；
 * 1.2 所属空运代理公司：共用选择框，内容读取空运代理公司基础资料； 1.3 城市：共用选择框，读取行政区域基础资料中的城市信息； 1.4
 * 增值服务：下拉框，包括全部、返回签单、货到付款、代收货款； 1.5 承运业务：下拉框，包括全部、出发、到达、中转； 1.6
 * 管理部门：共用选择框，读取行政组织； 2.
 * 查询结果列表：数据元素参见【空运代理网点查询结果列表】，“空运代理网点编码”字段添加超链接，点击超链可打开空运代理网点详情界面； 3.
 * 功能按钮区：按钮包括新增、修改、作废、查询、重置；  新增：此按钮位于系统悬浮工具条中，点击可打开空运代理网点新增界面(图2)； 
 * 修改：图标按钮，位于【空运代理网点查询结果列表】最前面的操作列，点击可打开空运代理网点修改界面(图2)； 
 * 作废：【空运代理网点查询结果列表】顶部和下部各有一个
 * “作废”按钮，点击可作废选中的空运代理网点信息；另外存在“作废”的图标按钮，位于【空运代理网点查询结果列表】最前面的操作列，点击可作废该行空运代理网点；
 *  查询：点击此按钮查询符合条件的空运代理网点；  重置：重新初始化【空运代理网点查询条件】； 4.
 * 提供的相关用例链接或提示信息：作废空运代理网点信息成功后
 * ，提示操作成功，同时刷新【空运代理网点查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。 1.5.4
 * 界面原型-空运代理网点新增、修改界面
 * 
 * 图 2：空运代理网点新增、修改界面 1.5.5 界面描述-空运代理网点新增、修改界面 该界面分为两个部分：空运代理网点信息输入区域，功能按钮区； 1.
 * 空运代理网点信息输入区域
 * ：字段包括空运代理网点编码、空运代理网点名称、管理部门、空运代理公司、省份、城市、区/县、空运代理网点地址、联系电话、正单联系电话、
 * 正单开单名称、备注、标准服务、增值服务、承运业务、自提区域描述、派送区域描述； 1.1
 * 空运代理网点编码：可作为空运代理网点的唯一标识，不可重复，参见业务规则SR-1； 1.2 空运代理网点名称：不可重复，参见业务规则SR-1； 1.3
 * 管理部门：参见业务规则SR-2； 1.4 空运代理公司：该空运代理网点所属的空运代理公司，共用选择框，读取空运代理公司基础资料； 1.5
 * 省份、城市、区/县：共用选择框，读取行政区域基础资料，参见业务规则SR-2； 1.6 标准服务：包括自提、送货上门复选框，参见业务规则SR-3； 1.7
 * 增值服务：包括返回签单、代收货款、货到付款复选框，参见业务规则SR-3； 1.8 承运业务：包括出发、到达、中转复选框，参见业务规则SR-3； 2.
 * 功能按钮区：按钮包括保存、取消、重置；  保存：点击此按钮保存空运代理网点信息；  取消：退出当前界面； 
 * 重置：点击此按钮重新初始化输入区域各控件； 3.
 * 提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。 1.6 操作步骤 1.6.1
 * 查询空运代理网点 序号 基本步骤 相关数据 补充步骤 1 进入空运代理网点查询界面 2 输入查询条件，点击“查询”按钮 【空运代理网点查询条件】
 * 【空运代理网点查询结果列表】 后台执行查询，并把查询结果返回到前台
 * 
 * 1.6.2 新增空运代理网点 序号 基本步骤 相关数据 补充步骤 1 点击悬浮工具条中的“新增”按钮 打开新增界面(图2) 2
 * 输入空运代理网点信息，点击“保存”按钮 校验通过后，保存空运代理网点信息
 * 
 * 序号 扩展事件 相关数据 备注 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新初始化各控件的值 2b 步骤2中，点击“取消”按钮
 * 退出当前界面
 * 
 * 1.6.3 修改空运代理网点 序号 基本步骤 相关数据 补充步骤 1 点击查询结果列表中的操作列中的“修改”按钮 打开空运代理网点修改界面(图2) 2
 * 输入【空运代理网点信息】，点击修改界面的“保存”按钮 校验通过后，更新空运代理网点信息
 * 
 * 序号 扩展事件 相关数据 备注 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新填充各控件的值为修改前的值 2b 步骤2中，点击“取消”按钮
 * 退出当前界面
 * 
 * 1.6.4 作废空运代理网点 序号 基本步骤 相关数据 补充步骤 1 点击查询结果列表中的操作列中的“作废”按钮
 * 校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该空运代理网点信息吗？” 2 在弹出的“确认/取消”对话框中，点击“确认”按钮
 * 更新该空运代理网点信息状态为“已作废”，同时刷新【空运代理网点查询结果列表】
 * 
 * 序号 扩展事件 相关数据 备注 1a 除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮 【空运代理网点查询结果列表】 2a
 * 步骤2中，点击“取消”按钮，则回到查询界面
 * 
 * 1.7 业务规则 序号 描述 SR-1 “空运代理网点编码”由操作员根据定义好的编码规则进行输入，不可重复；“空运代理网点名称”不可重复； SR-2
 * 新增或修改空运代理网点时： 1、
 * 新增、修改时，空运代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； 2、
 * 必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市； 3、
 * 必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县； SR-3 新增或修改空运代理网点时： 1、
 * 只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入； 2、 只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入； 3、
 * 只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”； SR-4
 * 只有新增空运代理网点时的管理部门或者系统管理员才有权限修改、作废空运代理网点；
 * 
 * 1.8 数据元素 1.8.1 空运代理网点查询条件 字段名称 说明 输入限制 输入提示文本 长度 是否必填 备注 空运代理网点编码
 * 系统自动生成的空运代理网点编码 数字 7 否 空运代理网点 空运代理网点的名称 共用选择框 40 否 读取本基础资料 所属空运代理公司
 * 空运代理网点所属的空运代理公司 共用选择框 40 否 读取空运代理公司基础资料 城市 空运代理网点所在城市 共用选择框 20 否 读取行政区域中的城市信息
 * 增值服务 空运代理网点所能提供的增值服务 下拉框 1 否 包括全部、返回签单、代收货款、货到付款 承运业务 空运代理网点所承运业务的种类 下拉框 1 否
 * 包括全部、出发、到达、中转 管理部门 新增空运代理网点时的管理部门 共用选择框 读取行政组织 1.8.2 空运代理网点查询结果列表 字段名称 说明
 * 输入限制 输入提示文本 长度 是否必填 备注 空运代理网点编码 空运代理网点编码 N/A N/A N/A N/A 空运代理网点 空运代理网点的名称 N/A
 * N/A N/A N/A 所属空运代理公司 空运代理网点所属的空运代理公司 N/A N/A N/A N/A 管理部门 新增空运代理网点时的管理部门 N/A
 * N/A N/A N/A 省份 空运代理网点所在省份 N/A N/A N/A N/A 城市 空运代理网点所在城市 N/A N/A N/A N/A 区/县
 * 空运代理网点所在区县 N/A N/A N/A N/A
 * 
 * 1.8.3 空运代理网点信息 字段名称 说明 输入限制 输入提示文本 长度 是否必填 备注 空运代理网点编码 空运代理网点编码 N/A N/A N/A
 * 空运代理网点名称 空运代理网点的名称 文本 40 是 空运代理公司 空运代理网点所属的空运代理公司 共用选择框 N/A 是 读取空运代理公司 省份
 * 空运代理网点所在省份 共用选择框 N/A 是 读取行政区域中的省份信息 管理部门 代理网点的管理部门 共用选择框 N/A 是 参见业务规则SR-2 城市
 * 空运代理网点所在城市 共用选择框 N/A 是 参见业务规则SR-2 区/县 空运代理网点所在区县 共用选择框 N/A 是 参见业务规则SR-2 联系电话
 * 提供给收货客户的联系电话 文本 80 是 正单联系电话 提供给发货客户的联系电话 文本 80 是 正单开单名称 文本 80 是 备注 备注信息 文本 80
 * 空运代理网点地址 空运代理网点地址 文本 150 是 自提 空运代理网点提供的自提服务，自动勾选，不可反选 N/A N/A N/A 送货上门
 * 代理可否送货上门 复选框 1 N/A 增值服务 货到付款、返回签单、代收货款 复选框 N/A N/A 承运业务 出发、到达、中转 复选框 N/A N/A
 * 自提区域描述 对自提区域的描述 文本 800 是 派送区域描述 对派送区域的描述 文本 800 是
 * 
 * 1.9 非功能性需求（可选） 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 查询响应与全系统要求一致 使用时间段 全天
 * 高峰使用时段
 * 
 * 1.10 接口描述： 接口名称 对方系统 接口描述
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirAgencyCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AgencyCompanyOrDeptVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运代理网点action
 * 
 * 新增_修改_作废_查询空运代理网点
 * 
 * @author 073586-FOSS-LIXUEXING
 * 
 * @date 2012-11-28 19:10:10
 * 
 * @since
 * 
 * @version 0.01
 */
public class AirAgencyDeptAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -3372339694836134644L;

    // 空运代理网点service接口
    private IAirAgencyDeptService airAgencyDeptService;

    // 空运代理网点 action使用VO
    private AgencyCompanyOrDeptVo objectVo = new AgencyCompanyOrDeptVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AirAgencyDeptAction.class);

    /**
     * <p>
     * 查询空运代理网点
     * </p>
     * 
     * 1.6.1 查询空运代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入空运代理网点查询界面
     * 
     * 2 输入查询条件，点击“查询”按钮
     * 
     * 【空运代理网点查询条件】 【空运代理网点查询结果列表】 后台执行查询，并把查询结果返回到前台
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-27 下午14:10:10
     * 
     * @return String
     */
    public String queryAirAgencyDept() {
	objectVo = dealCarrierBusiness(dealValueAddedServices(objectVo));
	OuterBranchEntity entityCondition = objectVo.getOuterBranchEntity();
	// 返回的结果显示在表格中：
	objectVo.setOuterBranchEntityList(airAgencyDeptService
		.queryAirAgencyDepts(entityCondition, limit, start));
	totalCount = airAgencyDeptService.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * <p>
     * 空运代理网点 是否重复
     * </p>
     * 
     * SR-1
     * 
     * “空运代理网点编码”由操作员根据定义好的编码规则进行输入，不可重复；
     * 
     * “空运代理网点名称”不可重复；
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-27 下午14:10:10
     * 
     * @return String
     */
    public String airAgencyDeptIsExist() {
	try {
	    objectVo.setOuterBranchEntityList(airAgencyDeptService
		    .queryAirAgencyDepts(objectVo.getOuterBranchEntity(), 1, 0));
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 根据代理公司虚拟编码查询该公司的所有代理网点
     * 
     * 1.6.1 查询空运代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入空运代理网点查询界面
     * 
     * 2 输入查询条件，点击“查询”按钮【空运代理网点查询条件】 【空运代理网点查询结果列表】 后台执行查询，并把查询结果返回到前台
     * 
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-30 13:50:10
     * 
     * @return String
     * 
     * @see
     */
    public String queryOuterBranchListByComCode() {
	// 返回的结果显示在表格中：
	return returnSuccess();
    }

    /**
     * 根据code作废空运代理网点
     * 
     * 1.6.4 作废空运代理网点 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 点击查询结果列表中的操作列中的“作废”按钮
     * 
     * 校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该空运代理网点信息吗？”
     * 
     * 2 在弹出的“确认/取消”对话框中，点击“确认”按钮 更新该空运代理网点信息状态为“已作废”，同时刷新【空运代理网点查询结果列表】
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 1a 除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮 【空运代理网点查询结果列表】
     * 
     * 2a 步骤2中，点击“取消”按钮，则回到查询界面
     * 
     * 
     * SR-4
     * 
     * 只有新增空运代理网点时的管理部门或者系统管理员才有权限修改、作废空运代理网点；
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-27 下午19:10:10
     * 
     * @return String
     * 
     * @see
     */
    public String deleteAirAgencyDeptByCode() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyDeptService.deleteAirAgencyDeptByCode(
		    objectVo.getCodeStr(), FossUserContext.getCurrentInfo()
			    .getEmpCode());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改空运代理网点
     * 
     * 1.6.3 修改空运代理网点 序号 基本步骤 相关数据 补充步骤 1 点击查询结果列表中的操作列中的“修改”按钮 打开空运代理网点修改界面(图2)
     * 
     * 2 输入【空运代理网点信息】，点击修改界面的“保存”按钮 校验通过后，更新空运代理网点信息
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新填充各控件的值为修改前的值
     * 
     * 2b 步骤2中，点击“取消”按钮 退出当前界面
     * 
     * SR-4
     * 
     * 只有新增空运代理网点时的管理部门或者系统管理员才有权限修改、作废空运代理网点；
     * 
     * 步骤2中，点击“取消”按钮 退出当前界面
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-28 13:50:10
     * 
     * @return String
     * 
     * @see
     */
    public String updateAirAgencyDept() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyDeptService.updateAirAgencyDept(objectVo
		    .getOuterBranchEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增空运代理网点
     * 
     * 1.6.2 新增空运代理网点 序号 基本步骤 相关数据 补充步骤 1 点击悬浮工具条中的“新增”按钮 打开新增界面(图2) 2
     * 
     * 输入空运代理网点信息，点击“保存”按钮 校验通过后，保存空运代理网点信息
     * 
     * SR-2
     * 
     * 新增或修改空运代理网点时：
     * 
     * 1、新增、修改时，空运代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
     * 
     * 2、必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
     * 
     * 3、必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
     * 
     * SR-4
     * 
     * 只有新增空运代理网点时的管理部门或者系统管理员才有权限修改、作废空运代理网点；
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新初始化各控件的值
     * 
     * 2b 步骤2中，点击“取消”按钮退出当前界面
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-28 13:50:10
     * 
     * @return String
     * 
     * @see
     */
    public String addAirAgencyDept() {
	try {
	    // 1：成功；-1：失败
	    int returnInt = airAgencyDeptService.addAirAgencyDept(objectVo
		    .getOuterBranchEntity());
	    objectVo.setReturnInt(returnInt);
	    return returnSuccess();
	} catch (AirAgencyCompanyException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 处理增值服务，前台传数据字典转换到后台查询
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-29 11:30:42
     */
    private AgencyCompanyOrDeptVo dealValueAddedServices(
	    AgencyCompanyOrDeptVo agencyCompanyOrDeptVo) {
	OuterBranchEntity entityCondition = agencyCompanyOrDeptVo
		.getOuterBranchEntity();
	if (DictionaryValueConstants.PAY_COLLECTION
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setHelpCharge(FossConstants.YES);
	} else if (DictionaryValueConstants.CASH_DELIVERY
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setArriveCharge(FossConstants.YES);
	} else if (DictionaryValueConstants.BACK_SIGN
		.equals(agencyCompanyOrDeptVo.getValueAddedServices())) {
	    entityCondition.setReturnBill(FossConstants.YES);
	}
	agencyCompanyOrDeptVo.setOuterBranchEntity(entityCondition);
	return agencyCompanyOrDeptVo;
    }

    /**
     * 处理承运业务，前台传数据字典转换到后台查询
     * 
     * @author 073586-FOSS-LIXUEXING
     * 
     * @date 2012-11-29 11:30:42
     */
    private AgencyCompanyOrDeptVo dealCarrierBusiness(
	    AgencyCompanyOrDeptVo agencyCompanyOrDeptVo) {
	OuterBranchEntity entityCondition = agencyCompanyOrDeptVo
		.getOuterBranchEntity();
	if (DictionaryValueConstants.DEPARTURE.equals(agencyCompanyOrDeptVo
		.getCarrierBusiness())) {
	    entityCondition.setLeave(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.ARRIVE.equals(agencyCompanyOrDeptVo
		.getCarrierBusiness())) {
	    entityCondition.setArrive(FossConstants.ACTIVE);
	} else if (DictionaryValueConstants.TRANSFER
		.equals(agencyCompanyOrDeptVo.getCarrierBusiness())) {
	    entityCondition.setTransfer(FossConstants.ACTIVE);
	}
	agencyCompanyOrDeptVo.setOuterBranchEntity(entityCondition);
	return agencyCompanyOrDeptVo;
    }

    /**
     * =================================================================
     * 下面是get,set方法：
     */
    public void setAirAgencyDeptService(
	    IAirAgencyDeptService airAgencyDeptService) {
	this.airAgencyDeptService = airAgencyDeptService;
    }

    public AgencyCompanyOrDeptVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(AgencyCompanyOrDeptVo objectVo) {
	this.objectVo = objectVo;
    }
}
