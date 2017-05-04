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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/RoleResourceService.java
 * 
 * FILE NAME        	: RoleResourceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-17 	新增	李俊	V0.1
		2012-07-10	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V0.2
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		2012-07-10 	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V1.1
		1.	SUC-38-修改_查询角色权限
		1.1	相关业务用例
		无
		1.2	用例描述
		该用例可修改，查询角色权限。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	角色信息完备。
		权限信息完备。	SUC-647-同步角色信息接口
		SUC-39-新增_修改_作废_查询权限
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统维护人员	    系统维护人员修改，查询角色权限。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一：角色主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	修改按钮：选中列表中的一行记录，点击修改按钮，进入分配权限界面，参见【图二：分配权限界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。参见【图一：角色主界面】。
		3)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【角色列表信息】。
		3.	字段输入区域
		1)	角色名称：角色的名称。
		1.5.4	界面原型-分配权限界面
		图二：分配权限界面
		1.5.5	界面描述-分配权限界面
		1.	信息显示区域
		1)	角色名称：角色的名称
		2)	描述：角色的描述
		2.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复角色所具有的权限的初始状态。
		3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		3)4)	查询按钮：输入权限名称，点击查询按钮，在权限树中展开并定位到符合查询条件的第一个结点。
		3.	树形显示区域
		1)	树形显示区域展示权限名称的树形结构。
		2)	点击树形结构的“展开标识+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-折叠标识”，则收起该权限下一级的所有权限。		
		1.6	操作步骤
		1.6.1	分配权限操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色主界面。		
		2	查出需要修改的角色。		
		3	点击修改按钮。	【角色信息】	进入分配权限界面。
		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。		弹出修改确认对话框。
		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
		6	点击提示框中的确定按钮。		回到角色管理主界面。
		分配权限异常操作步骤
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮，退出当前界面，返回主界面。		
		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		1.6.2	查询角色操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色管理主界面。		
		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】		
		1.7	业务规则
		序号	描述
		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
		1.8	数据元素
		1.8.1	角色信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	文本	50	是	
		角色名称	角色的名称	文本	64	是	
		描述	角色的描述	文本	255	否	
		角色权限	角色所包含的权限的名称	文本	255	否	
		1.8.2	角色列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	N/A	N/A	N/A	
		角色名称	角色的名称	N/A	N/A	N/A	
		角色权限	角色所包含的权限的名称	N/A	N/A	N/A	
		描述	角色的描述	N/A	N/A	N/A	
		1.8.3	角色查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色名称	角色的名称	文本	64	否	
		1.8.4	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称	文本	64	否	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段		
		1.10	接口描述：
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统
		 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-17 	新增	李俊	V0.1
		2012-07-10	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V0.2
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		2012-07-10 	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V1.1
		1.	SUC-38-修改_查询角色权限
		1.1	相关业务用例
		无
		1.2	用例描述
		该用例可修改，查询角色权限。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	角色信息完备。
		权限信息完备。	SUC-647-同步角色信息接口
		SUC-39-新增_修改_作废_查询权限
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统维护人员	    系统维护人员修改，查询角色权限。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一：角色主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	修改按钮：选中列表中的一行记录，点击修改按钮，进入分配权限界面，参见【图二：分配权限界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。参见【图一：角色主界面】。
		3)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【角色列表信息】。
		3.	字段输入区域
		1)	角色名称：角色的名称。
		1.5.4	界面原型-分配权限界面
		图二：分配权限界面
		1.5.5	界面描述-分配权限界面
		1.	信息显示区域
		1)	角色名称：角色的名称
		2)	描述：角色的描述
		2.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复角色所具有的权限的初始状态。
		3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		3)4)	查询按钮：输入权限名称，点击查询按钮，在权限树中展开并定位到符合查询条件的第一个结点。
		3.	树形显示区域
		1)	树形显示区域展示权限名称的树形结构。
		2)	点击树形结构的“展开标识+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-折叠标识”，则收起该权限下一级的所有权限。		
		1.6	操作步骤
		1.6.1	分配权限操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色主界面。		
		2	查出需要修改的角色。		
		3	点击修改按钮。	【角色信息】	进入分配权限界面。
		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。		弹出修改确认对话框。
		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
		6	点击提示框中的确定按钮。		回到角色管理主界面。
		分配权限异常操作步骤
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮，退出当前界面，返回主界面。		
		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		1.6.2	查询角色操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色管理主界面。		
		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】		
		1.7	业务规则
		序号	描述
		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
		1.8	数据元素
		1.8.1	角色信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	文本	50	是	
		角色名称	角色的名称	文本	64	是	
		描述	角色的描述	文本	255	否	
		角色权限	角色所包含的权限的名称	文本	255	否	
		1.8.2	角色列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	N/A	N/A	N/A	
		角色名称	角色的名称	N/A	N/A	N/A	
		角色权限	角色所包含的权限的名称	N/A	N/A	N/A	
		描述	角色的描述	N/A	N/A	N/A	
		1.8.3	角色查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色名称	角色的名称	文本	64	否	
		1.8.4	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称	文本	64	否	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段		
		1.10	接口描述：
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统
		 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-17 	新增	李俊	V0.1
		2012-07-10	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V0.2
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		2012-07-10 	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V1.1
		1.	SUC-38-修改_查询角色权限
		1.1	相关业务用例
		无
		1.2	用例描述
		该用例可修改，查询角色权限。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	角色信息完备。
		权限信息完备。	SUC-647-同步角色信息接口
		SUC-39-新增_修改_作废_查询权限
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统维护人员	    系统维护人员修改，查询角色权限。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一：角色主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	修改按钮：选中列表中的一行记录，点击修改按钮，进入分配权限界面，参见【图二：分配权限界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。参见【图一：角色主界面】。
		3)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认分页显示所有查询结果，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【角色列表信息】。
		3.	字段输入区域
		1)	角色名称：角色的名称。
		1.5.4	界面原型-分配权限界面
		图二：分配权限界面
		1.5.5	界面描述-分配权限界面
		1.	信息显示区域
		1)	角色名称：角色的名称
		2)	描述：角色的描述
		2.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复角色所具有的权限的初始状态。
		3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		3)4)	查询按钮：输入权限名称，点击查询按钮，在权限树中展开并定位到符合查询条件的第一个结点。
		3.	树形显示区域
		1)	树形显示区域展示权限名称的树形结构。
		2)	点击树形结构的“展开标识+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-折叠标识”，则收起该权限下一级的所有权限。		
		1.6	操作步骤
		1.6.1	分配权限操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色主界面。		
		2	查出需要修改的角色。		
		3	点击修改按钮。	【角色信息】	进入分配权限界面。
		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。		弹出修改确认对话框。
		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
		6	点击提示框中的确定按钮。		回到角色管理主界面。
		分配权限异常操作步骤
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮，退出当前界面，返回主界面。		
		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		1.6.2	查询角色操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入角色管理主界面。		
		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】		
		1.7	业务规则
		序号	描述
		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
		1.8	数据元素
		1.8.1	角色信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	文本	50	是	
		角色名称	角色的名称	文本	64	是	
		描述	角色的描述	文本	255	否	
		角色权限	角色所包含的权限的名称	文本	255	否	
		1.8.2	角色列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色编号	角色的编号	N/A	N/A	N/A	
		角色名称	角色的名称	N/A	N/A	N/A	
		角色权限	角色所包含的权限的名称	N/A	N/A	N/A	
		描述	角色的描述	N/A	N/A	N/A	
		1.8.3	角色查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		角色名称	角色的名称	文本	64	否	
		1.8.4	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称	文本	64	否	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段		
		1.10	接口描述：
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统


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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RoleException;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;
import com.deppon.foss.util.common.FossTTLCache;

/**
 * 角色权限 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午4:24:42
 */
public class RoleResourceService implements IRoleResourceService {
	
	/**
	 * 
	 * <p>修改角色权限信息</p> 
	 * @author ztjie
	 * @date 2013-4-18 下午2:42:06
	 * @return
	 * @see
	 */
	@Transactional
	@Override
    public void updateRoleResource(String roleCode, Set<String> addResourceCodes,
			Set<String> deleteResourceCodes) {
		// 如果角色的编码为空，则直接返回
		if(StringUtils.isEmpty(roleCode)){
			throw new RoleException(RoleException.ROLE_CODE_NULL);
		}
		// 设置操作用户的用户编码
		String operUserCode = GainEmployee.getOperUserCode();
		
		//通过用户的角色编码获取 角色权限对象
		RoleResourceEntity roleResourceEntity =new RoleResourceEntity();
		roleResourceEntity.setRoleCode(roleCode);
		
		
		for(String addResourceCode : addResourceCodes){
			List<RoleResourceEntity> entitys = new ArrayList<RoleResourceEntity>();
			
			//判断保存时是否存在该权限,若该用户存在该权限的话
			RoleResourceEntity entity=this.isExistResourceCode(addResourceCode,roleResourceEntity);
			//返回的对象不为空值，就更新角色权限
			if(entity!=null){
				//更新角色角色权限
				entity.setModifyUser(operUserCode);
			//	roleResourceDao.updateRoleResource(entity);
				entitys.add(roleResourceDao.updateRoleResource(entity));
				syncResourceToOther(entitys);
			}else{
				//否则添加一个新的权限
				RoleResourceEntity addEntity=new RoleResourceEntity();
			    addEntity.setRoleCode(roleCode);
			    addEntity.setResourceCode(addResourceCode);
			    addEntity.setCreateUser(operUserCode);
			    addEntity.setModifyUser(operUserCode);
			 //   roleResourceDao.addRoleResource(addEntity);
			    entitys.add(roleResourceDao.addRoleResource(addEntity));
			    syncResourceToOther(entitys);
			}  
		}
		
		// 作废权限到数据库：
		for(String deleteResourceCode : deleteResourceCodes){
			List<RoleResourceEntity> entitys = new ArrayList<RoleResourceEntity>();
			
		    RoleResourceEntity deleteEntity=new RoleResourceEntity();
		    deleteEntity.setRoleCode(roleCode);
		    deleteEntity.setResourceCode(deleteResourceCode);
		    deleteEntity.setModifyUser(operUserCode);    
		//    roleResourceDao.deleteRoleResourceByRoleResource(deleteEntity);
		    entitys.add(roleResourceDao.deleteRoleResourceByRoleResource(deleteEntity));
		    syncResourceToOther(entitys);
		}
		
		// 如果更新成功，刷新缓存
		this.refreshCache(roleCode);
    }
	/**
	 * 
	 *<p>判断该角色权限是否是否存在</P>
	 * @author 130566-foss-ZengJunfan
	 * @date 2013-5-8下午4:06:02
	 *@param addResourceCode
	 *@return 
	 */
	private RoleResourceEntity isExistResourceCode(String addResourceCode,RoleResourceEntity roleEntity) {
		//得到用户的所有角色权限
		List<RoleResourceEntity> roleResourceList =this.queryRoleResourceExactByEntity(roleEntity);
		//如权限集合不为空时
		if(CollectionUtils.isNotEmpty(roleResourceList)){
			for (RoleResourceEntity roleResourceEntity : roleResourceList) {
				//如果该权限等于得到的传进来权限，说明该角色存在该权限
				if(roleResourceEntity.getResourceCode().equals(addResourceCode)){
					return roleResourceEntity;
				}
			}
		}
		return null;
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 精确查询 通过 VIRTUAL_CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午4:24:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceService#queryRoleResourceByCode(java.lang.String)
	 */
	public RoleResourceEntity queryRoleResourceByVirtualCode(String code) {
		if (null == code) {
			return null;
		}
		return roleResourceDao.queryRoleResourceByVirtualCode(code);
	}
	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceService#queryRoleResourceByCode(java.lang.String)
	 */
	public List<RoleResourceEntity> queryRoleResourceBatchByVirtualCode(
			String[] codes) {
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		return roleResourceDao.queryRoleResourceBatchByVirtualCode(codes);
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceService#queryRoleResourceExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity,
	 *      int, int)
	 */
	public List<RoleResourceEntity> queryRoleResourceExactByEntity(
			RoleResourceEntity entity, int start, int limit) {
		return roleResourceDao.queryRoleResourceExactByEntity(entity, start,
				limit);
	}
	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceService#queryRoleResourceExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity)
	 */
	public long queryRoleResourceExactByEntityCount(RoleResourceEntity entity) {
		return roleResourceDao.queryRoleResourceExactByEntityCount(entity);
	}
	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午4:24:42
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceService#deleteRoleResourceMore(java.lang.String[])
	 */
	public List<RoleResourceEntity> queryRoleResourceByEntity(
			RoleResourceEntity entity, int start, int limit) {
		return roleResourceDao.queryRoleResourceByEntity(entity, start, limit);
	}
	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午4:24:42
	 * @see com.deppon.foss.module.baseinfo.server.service.IRoleResourceService#queryRoleResourceCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.RoleResourceEntity)
	 */
	public long queryRoleResourceByEntityCount(RoleResourceEntity entity) {
		return roleResourceDao.queryRoleResourceByEntityCount(entity);
	}

	/**
	 * 特殊查询，精确查询 根据多个标识和列名ROLE_CODE批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleResourceDao#queryRoleResourceBatchBy(java.lang.String[])
	 */
	public List<RoleResourceEntity> queryRoleResourceMoreByRoleCode(
			String[] codes) {
		return roleResourceDao.queryRoleResourceMoreByColumnName(codes,
				ColumnConstants.ROLE_RESOURCE_OF_ROLE_CODE);
	}
	/**
	 * 下面是工具方法
	 */

	/**
	 * 刷新角色信息的缓存
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 下午6:08:33
	 */
	@SuppressWarnings("unchecked")
	public void refreshCache(String roleCode) {
		ICache<String, IUser> userCache = CacheManager
				.getInstance().getCache(FossTTLCache.USER_CACHE_UUID);
		List<String> userIds = userService.queryUserIdsByRoleCode(roleCode);
		userCache.invalidMulti(userIds.toArray(new String[userIds.size()]));
		ICache<String, List<Set<String>>> userOrgRoleResCache = CacheManager
				.getInstance().getCache(FossTTLCache.USER_ORG_ROLE_RES_CACHE_UUID);
		List<String> userOrgCodes = userService.queryUserAndOrgCodesByRoleCodeForCache(roleCode);
		userOrgRoleResCache.invalidMulti(userOrgCodes.toArray(new String[userOrgCodes.size()]));
	}
	/**
	 * 下面是dao对象的声明及get,set方法：
	 */
	private IRoleResourceDao roleResourceDao;

	public void setRoleResourceDao(IRoleResourceDao roleResourceDao) {
		this.roleResourceDao = roleResourceDao;
	}
	private IUserService userService;
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	private ISyncResourceService syncResourceService;
	 public void setSyncResourceService(ISyncResourceService syncResourceService) {
		this.syncResourceService = syncResourceService;
	}
	 
	 private void syncResourceToOther(List<?> entitys){
		 syncResourceService.syncResource(entitys);
	 }
	 
	/**
     * 
     * <p>查询用户拥有的所有权限</p> 
     * @author 何波
     * @date 2013-2-26 下午4:03:41
     * @param roleResource
     * @return
     * @see
     */
	@Override
	public List<RoleResourceEntity> queryRoleResourceExactByEntity(
			RoleResourceEntity roleResource) {
		if(roleResource == null || StringUtils.isEmpty(roleResource.getRoleCode())){
			return null;
		}
		//查询出用户拥有的角色权限
		 List<RoleResourceEntity> roleResourceList = roleResourceDao.queryRoleResourceExactByEntity(roleResource);
		
		 if(CollectionUtils.isEmpty(roleResourceList)){
			 return null;
		 } 
		return roleResourceList;
	}
}
