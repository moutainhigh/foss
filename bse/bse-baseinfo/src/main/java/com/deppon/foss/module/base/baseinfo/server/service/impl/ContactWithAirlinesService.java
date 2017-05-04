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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ContactWithAirlinesService.java
 * 
 * FILE NAME        	: ContactWithAirlinesService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 修订记录 日期 修订内容 修订人员 版本号 2012-04-25 新增 周海根 V0.1 2012-6-6
 * 添加相关业务用例 谢艳涛 V0.2 2012-08-02 通过业务部门审核签字版本升级到V0.9 周春来 V0.9
 * 2012-12-26 SUC画了个导入按钮，对于导入的功能没有任何描述，也没有导入模板，
 * 正单交货人一共只有不超过50条数据，而且不会经常变动。 赵鹏 V1.01
 * 
 * 1. SUC-37-新增_修改_作废_查询正单交货人 1.1 相关业务用例
 * BUC_FOSS_5.70.30_050空运-制作空运交接单 1.2 用例描述
 * 新增_修改_作废_查询正单交货人系统用例用于管理正单交货人基础资料
 * ，包括机场交接部经理编号、名称以及航空公司给德邦定义单位名称。 该用例可对正单交货人进行新增、修改、作废、查询。 1.3
 * 用例条件 条件类型 描述 引用系统用例 前置条件 后置条件 1、 为生成航空正单交接单系统用例提供正单交货人查询
 * SUC-607 DP-FOSS-中转系统用例-空运-生成航空正单交接单 1.4 操作用户角色 操作用户 描述 系统管理员
 * 系统管理员对“正单交货人”进行新增，修改，作废，查询操作。 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2
 * 界面原型-主界面
 * 
 * 图一：正单交货人主界面 1.5.3 界面描述-主界面 1. 功能按钮区域 1)
 * 新增按钮：点击新增按钮跳入新增界面，参见【图二：正单交货人新增和 修改界面】。 2)
 * 作废按钮：选中列表中的一行记录，点击作废按钮。 3)
 * 查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。 4) 重置按钮：重置查询条件。 5)
 * 查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。 6)
 * 分页按钮：可以跳转到首页，上页，下页和末页。指定每页的数量。或者直接跳转到指定页。 2. 列表显示区域 1)
 * 列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件（单位名称和交货人）显示列表数据。 2)
 * 列表中显示编号、单位名称和交货人。
 * 
 * 1.5.4 界面原型-新增和修改界面
 * 
 * 
 * 图二：正单交货人新增和修改界面 1.5.5 界面描述-新增和修改界面 1. 字段输入区域 1) 编号： 必填 2)
 * 单位名称：必填 3) 交货人：必填 2. 功能按钮区域 1)
 * 保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，
 * 关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前页面。 2)
 * 重置按钮：点击重置按钮，回到当前界面的初始状态。 3) 取消按钮：点击取消按钮，退出当前界面，返回主界面。 1.6
 * 操作步骤 1.6.1 正单交货人新增操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入正单交货人管理主界面
 * 【正单交货人列表信息】 2 点击新增按钮，进入新增和修改界面 3 输入正单交货人详细信息，点击保存。
 * 参见业务规则SR-1和SR-2 【正单交货人信息】 4 返回正单交货人管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面 3b
 * 若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面
 * 
 * 1.6.2 正单交货人修改操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入正单交货人管理主界面
 * 【正单交货人列表信息】 2 点击修改按钮，进入新增和修改界面 【正单交货人信息】 3
 * 输入正单交货人详细信息，修改正单交货人详细信息,点击保存 参见业务规则SR-1和SR-2 【正单交货人信息】 4
 * 返回正单交货人管理主界面
 * 
 * 序号 扩展事件 相关数据 备注 3a 点击取消按钮，退出当前界面，返回主界面 3b
 * 若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面
 * 
 * 1.6.3 正单交货人作废操作步骤 序号 基本步骤 相关数据 补充步骤 1 进入正单交货人管理主界面
 * 【正单交货人列表信息】 2 选择一行或者多行记录，点击作废按钮。 弹出确认对话框。 3 点击确定按钮。
 * 
 * 序号 扩展事件 相关数据 备注 2a 点击取消按钮，退出当前界面，返回主界面 2b
 * 若作废失败，需提示用户作废失败以及失败原因 1.6.4 正单交货人查询操作步骤
 * 
 * 序号 基本步骤 相关数据 补充步骤 1 进入正单交货人管理主界面 【正单交货人列表信息】 2
 * 输入查询条件，点击查询按钮。参见业务规则SR-3 【正单交货人查询条件】 系统返回查询结果
 * 
 * 1.7 业务规则 序号 描述 SR-1 机场交接部经理编号为主键 SR-2 单位名称和交货人的组合不能重复 SR-3
 * 查询支持模糊查询
 * 
 * 1.8 数据元素 1.8.1 正单交货人信息 字段名称 说明 输入限制 输入项提示文本 长度 是否必填 备注 编号
 * 机场交接部经理编号 文本 八位数字编号 8 是 单位名称 航空公司给德邦定义的单位名称 文本 航空公司给德邦定义的单位名称
 * 20 是 交货人 正单交货人名称,一般为机场交接部经理 文本 正单交货人名称 20 是 1.8.2 正单交货人列表信息
 * 字段名称 说明 输入限制 长度 是否必填 备注 编号 机场交接部经理编号 N/A 8 N/A 单位名称
 * 航空公司给德邦定义的单位名称 N/A 20 N/A 交货人 正单交货人名称,一般为机场交接部经理 N/A 20 N/A
 * 1.8.3 正单交货人查询条件 字段名称 说明 输入限制 长度 是否必填 备注 编号 机场交接部经理编号 文本 8 否
 * 单位名称 航空公司给德邦定义的单位名称 文本 20 否 交货人 正单交货人名称,一般为机场交接部经理 文本 20 否
 * 
 * 1.9 非功能性需求 使用量 2012年全网估计用户数 响应要求（如果与全系统要求 不一致的话） 使用时间段
 * 高峰使用时间段
 * 
 * 1.10 接口描述 接口名称 对方系统（外部系统或内部其他模块） 接口描述
 * 
 * 
 * 
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IContactWithAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ContactWithAirlinesException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“正单交货人”的数据库对应数据访问Service接口实现类：SUC-37
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午10:12:28
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * 
 * @date 2012-11-3 上午10:12:28
 * 
 * @since
 * 
 * @version
 */
public class ContactWithAirlinesService implements IContactWithAirlinesService {

    // "正单交货人"DAO
    private IContactWithAirlinesDao contactWithAirlinesDao;

    /**
     * <p>
     * 新增一个“正单交货人”实体入库（忽略实体中是否存在空值）
     * </p>
     * 
     * 1.6.1 正单交货人新增操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入正单交货人管理主界面 【正单交货人列表信息】
     * 
     * 2　点击新增按钮，进入新增和修改界面 3 输入正单交货人详细信息，点击保存。 参见业务规则SR-1和SR-2 【正单交货人信息】
     * 
     * 4　返回正单交货人管理主界面
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 3a 点击取消按钮，退出当前界面，返回主界面
     * 
     * 3b 若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面
     * 
     * SR-1
     * 
     * 机场交接部经理编号为主键
     * 
     * SR-2
     * 
     * 单位名称和交货人的组合不能重复
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午11:53:16
     * 
     * @param contactAirlines
     *            “正单交货人”实体
     * 
     * @param createUser
     *            创建人
     * 
     * @param ignoreNull
     *            true：忽略空值，false：包含空值
     * 
     * @return 1：成功；0：失败
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#addContactWithAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addContactWithAirlines(ContactAirlinesEntity contactAirlines,
	    String createUser, boolean ignoreNull)
	    throws ContactWithAirlinesException {
	if (null == contactAirlines) {
	    throw new ContactWithAirlinesException("", "正单交货人信息为空");
	}
	if (StringUtils.isBlank(contactAirlines.getContactCode())) {
	    throw new ContactWithAirlinesException("", "正单交货人编码为空");
	}
	if (StringUtils.isBlank(createUser)) {
	    throw new ContactWithAirlinesException("", "创建人信息为空");
	}

	// 验证数据
	ContactAirlinesEntity contactAirlinesParameter = new ContactAirlinesEntity();
	contactAirlinesParameter.setContactCode(contactAirlines
		.getContactCode());
	ContactAirlinesEntity tempContactAirlines = contactWithAirlinesDao
		.queryContactWithAirlinesBySelective(contactAirlinesParameter);
	if (null != tempContactAirlines) {
	    throw new ContactWithAirlinesException("", "正单交货人信息已经存在");
	} else {
	    contactAirlines.setCreateUser(createUser);
	    if (ignoreNull) {
		contactWithAirlinesDao
			.addContactWithAirlinesBySelective(contactAirlines);
	    } else {
		contactWithAirlinesDao.addContactWithAirlines(contactAirlines);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 根据“正单交货人”记录唯一标识作废（物理删除）一条“正单交货人”记录
     * </p>
     * 
     * 1.6.3 正单交货人作废操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入正单交货人管理主界面 【正单交货人列表信息】
     * 
     * 2　选择一行或者多行记录，点击作废按钮。 弹出确认对话框。
     * 
     * 3 点击确定按钮。
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 2a 点击取消按钮，退出当前界面，返回主界面
     * 
     * 2b 若作废失败，需提示用户作废失败以及失败原因
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午11:53:02
     * 
     * @param ids
     *            记录唯一标识集合
     * 
     * @param modifyUser
     *            修改人
     * 
     * @return 1：成功；0：失败
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#deleteContactWithAirlines(java.util.List,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public int deleteContactWithAirlines(List<String> ids, String modifyUser)
	    throws ContactWithAirlinesException {
	if (CollectionUtils.isEmpty(ids)) {
	    throw new ContactWithAirlinesException("", "ID 为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new ContactWithAirlinesException("", "修改人信息为空");
	}

	for (String id : ids) {
	    // 验证数据
	    ContactAirlinesEntity oglContactAirlines = queryContactWithAirlinesBySelective(id);
	    if (null == oglContactAirlines) {
		throw new ContactWithAirlinesException("", "正单交货人信息不经存在");
	    } else {
		oglContactAirlines.setModifyUser(modifyUser);
		oglContactAirlines.setActive(FossConstants.INACTIVE);
		contactWithAirlinesDao
			.updateContactWithAirlinesBySelective(oglContactAirlines);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 修改一个“正单交货人”实体入库（忽略实体中是否存在空值）
     * </p>
     * 
     * 1.6.2 正单交货人修改操作步骤 序号 基本步骤 相关数据 补充步骤
     * 
     * 1 进入正单交货人管理主界面 【正单交货人列表信息】
     * 
     * 2　点击修改按钮，进入新增和修改界面 【正单交货人信息】
     * 
     * 3 输入正单交货人详细信息，修改正单交货人详细信息,点击保存 参见业务规则SR-1和SR-2 【正单交货人信息】
     * 
     * 4 返回正单交货人管理主界面
     * 
     * 序号 扩展事件 相关数据 备注
     * 
     * 3a 点击取消按钮，退出当前界面，返回主界面
     * 
     * 3b 若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面
     * 
     * SR-1
     * 
     * 机场交接部经理编号为主键
     * 
     * SR-2
     * 
     * 单位名称和交货人的组合不能重复
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午11:53:07
     * 
     * @param contactAirlines
     *            “正单交货人”实体
     * 
     * @param modifyUser
     *            修改人
     * 
     * @param ignoreNull
     *            true：忽略空值，false：包含空值
     * 
     * @return 1：成功；0：失败
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#updateContactWithAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity,
     *      java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateContactWithAirlines(ContactAirlinesEntity contactAirlines,
	    String modifyUser, boolean ignoreNull)
	    throws ContactWithAirlinesException {

	if (null == contactAirlines) {
	    throw new ContactWithAirlinesException("", "正单交货人信息为空");
	}
	if (StringUtils.isBlank(contactAirlines.getId())) {
	    throw new ContactWithAirlinesException("", "ID 为空");
	}
	if (StringUtils.isBlank(contactAirlines.getContactCode())) {
	    throw new ContactWithAirlinesException("", "正单交货人编码为空");
	}
	if (StringUtils.isBlank(modifyUser)) {
	    throw new ContactWithAirlinesException("", "修改人信息为空");
	}

	// 验证数据
	ContactAirlinesEntity oldContactAirlines, tempContactAirlines = new ContactAirlinesEntity();
	tempContactAirlines.setContactCode(contactAirlines.getContactCode());
	oldContactAirlines = contactWithAirlinesDao
		.queryContactWithAirlinesBySelective(tempContactAirlines);
	if (null == oldContactAirlines) {
	    throw new ContactWithAirlinesException("", "正单交货人信息不经存在");
	} else {
	    contactAirlines.setModifyUser(modifyUser);
	    if (ignoreNull) {
		contactWithAirlinesDao
			.updateContactWithAirlinesBySelective(contactAirlines);
	    } else {
		contactWithAirlinesDao
			.updateContactWithAirlines(contactAirlines);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 根据“正单交货人”记录唯一标识查询出一条“正单交货人”记录
     * </p>
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午11:53:11
     * 
     * @param id
     *            记录唯一标识
     * 
     * @return “正单交货人”实体
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#queryContactWithAirlines(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public ContactAirlinesEntity queryContactWithAirlinesBySelective(String id)
	    throws ContactWithAirlinesException {
	if (StringUtils.isBlank(id)) {
	    throw new ContactWithAirlinesException("", "ID 为空");
	}
	ContactAirlinesEntity contactAirlines = new ContactAirlinesEntity();
	contactAirlines.setId(id);
	return contactWithAirlinesDao
		.queryContactWithAirlinesBySelective(contactAirlines);
    }

    /**
     * <p>
     * 根据条件有选择的检索出符合条件一个的“正单交货人”实体（条件做自动判断，只选择实体中非空值）
     * </p>
     * 
     * SR-3
     * 
     * 查询支持模糊查询
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-14 上午11:23:53
     * 
     * @param airport
     *            以“正单交货人”实体承载的条件参数实体
     * 
     * @return 符合条件的“正单交货人”实体
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#queryContactWithAirlinesBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity)
     */
    @Override
    public ContactAirlinesEntity queryContactWithAirlinesBySelective(
	    ContactAirlinesEntity contactAirlines)
	    throws ContactWithAirlinesException {
	if (null == contactAirlines) {
	    throw new ContactWithAirlinesException("", "正单交货人参数信息为空");
	}
	return contactWithAirlinesDao
		.queryContactWithAirlinesBySelective(contactAirlines);
    }

    /**
     * <p>
     * 根据条件有选择的检索出符合条件的“机型信息”实体列表（条件做自动判断，只选择实体中非空值）
     * </p>
     * 
     * 1.6.4 正单交货人查询操作步骤
     * 
     * 序号 基本步骤 相关数据 补充步骤 1 进入正单交货人管理主界面 【正单交货人列表信息】
     * 
     * 2 输入查询条件，点击查询按钮。参见业务规则
     * 
     * SR-3 【正单交货人查询条件】 系统返回查询结果
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-20 上午11:02:46
     * 
     * @param contactAirlines
     *            以“机型信息”实体承载的条件参数实体
     * 
     * @param offset
     *            开始记录数
     * 
     * @param limit
     *            限制记录数
     * 
     * @return 分页的Action和Service通讯封装对象
     * 
     * @throws ContactWithAirlinesException
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IContactWithAirlinesService#queryContactAirlinesListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ContactAirlinesEntity,
     *      int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryContactAirlinesListBySelectiveCondition(
	    ContactAirlinesEntity contactAirlines, int offset, int limit)
	    throws ContactWithAirlinesException {
	PaginationDto paginationDto = new PaginationDto();
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if (null == contactAirlines) {
	    contactAirlines = new ContactAirlinesEntity();
	}
	List<ContactAirlinesEntity> contactAirlinesList = null;
	contactAirlinesList = contactWithAirlinesDao
		.queryContactAirlinesListBySelectiveCondition(contactAirlines,
			offset, limit);
	if (CollectionUtils.isNotEmpty(contactAirlinesList)) {
	    Long totalCount = contactWithAirlinesDao
		    .queryContactAirlinesCountBySelectiveCondition(contactAirlines);
	    paginationDto.setPaginationDtos(contactAirlinesList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }

    /**
     * @param contactWithAirlinesDao
     *            the contactWithAirlinesDao to set
     */
    public void setContactWithAirlinesDao(
	    IContactWithAirlinesDao contactWithAirlinesDao) {
	this.contactWithAirlinesDao = contactWithAirlinesDao;
    }
}
