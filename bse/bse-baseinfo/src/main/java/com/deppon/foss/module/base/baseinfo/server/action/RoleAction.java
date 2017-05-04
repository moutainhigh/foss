/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * 
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * 
 * 
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 
 * 
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/RoleAction.java
 * 
 * FILE NAME        	: RoleAction.java
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
 * 
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * 
 * not internal will pay legal responsibility.
 * 
 *
 * You may learn more information about Deppon from
 *
 *
 *      http://www.deppon.com
 *相关SUC
			 *修订记录 
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
		*	
		*	
		*	
			1.5.4	界面原型-分配权限界面
		*	 
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
		*	
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
			*
			1.6.2	查询角色操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入角色管理主界面。		
			2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
			3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
			*
			1.7	业务规则
			序号	描述
			SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
			SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
			SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
			SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
			*
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
			*
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			*
			1.10	接口描述：
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
			同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统
			*相关SUC
			 *修订记录 
			日期 	修订内容 	修订人员 	版本号 
			2012-04-17 	新增	李俊	V0.1
			2012-07-10	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V0.2
			2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
			2012-07-10 	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V1.1
			*  	  	  	 
			*
			1.	SUC-38-修改_查询角色权限
			1.1	相关业务用例
			无
			*
			1.2	用例描述
			该用例可修改，查询角色权限。
			*
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件	角色信息完备。
			权限信息完备。	SUC-647-同步角色信息接口
			SUC-39-新增_修改_作废_查询权限
			后置条件		
			*
			1.4	操作用户角色
			操作用户	描述
			系统维护人员	    系统维护人员修改，查询角色权限。
			*
			1.5	界面要求
			1.5.1	表现方式
			Web页面
			*
			1.5.2	界面原型-主界面
			*
			*
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
			*
			*
			*
			1.5.4	界面原型-分配权限界面
			* 
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
			*
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
			*
			1.6.2	查询角色操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入角色管理主界面。		
			2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
			3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
			*
			1.7	业务规则
			序号	描述
			SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
			SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
			SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
			SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
			*
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
			*
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			*
			1.10	接口描述：
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
			同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统
			日期 	修订内容 	修订人员 	版本号 
			2012-04-17 	新增	李俊	V0.1
			2012-07-10	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V0.2
			2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
			2012-07-10 	修改了交互方式，修改了业务规则，修改了角色编号的元素类型。	李俊	V1.1
			* 	  	  	 
			*
			1.	SUC-38-修改_查询角色权限
			1.1	相关业务用例
			无
			*
			1.2	用例描述
			该用例可修改，查询角色权限。
			*
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件	角色信息完备。
			权限信息完备。	SUC-647-同步角色信息接口
			SUC-39-新增_修改_作废_查询权限
			后置条件		
			*
			1.4	操作用户角色
			操作用户	描述
			系统维护人员	    系统维护人员修改，查询角色权限。
			*
			1.5	界面要求
			1.5.1	表现方式
			Web页面
			*
			1.5.2	界面原型-主界面
			*
			 *
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
			*
			*
			*
			1.5.4	界面原型-分配权限界面
			 *
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
			*
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
			*
			1.6.2	查询角色操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入角色管理主界面。		
			2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
			3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
			*
			1.7	业务规则
			序号	描述
			SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中,该节点以下的所有子节点也都自动选中.
			SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变则该节点以下的所有子节点也都自动取消.
			SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
			SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
			*
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
			*
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			*
			1.10	接口描述：
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
			同步角色信息接口	UUMS系统	由FOSS系统提供角色信息接口，由UUMS调用，通过此接口传递角色更新信息到FOSS系统		
*/ 

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceConflictService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.RoleVo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 角色 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:49:38
 */
public class RoleAction extends AbstractAction {

    private static final long serialVersionUID = 1782509318456860274L;
    
    @JSON
    public String addRole(){
    	try{
    		RoleEntity role = roleVo.getRoleEntityDetail();
    		roleService.addRole(role, false);
    		return returnSuccess("foss.baseinfo.saveSuccessful");
    	}catch (BusinessException e) {
			return returnError(e);
		}
    }
    
    /**
     * 修改角色权限
     * 
     * 值放在roleVo.roleEntityList
     * 
     * @author 087584
     * @date 2012-9-8 上午11:16:53
     * 
     * @return
     * @see
	 *      1	进入角色主界面。		
	 *      
	*		2	查出需要修改的角色。	
	*	
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*	
	*	弹出修改确认对话框。
	*
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	*
	*		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。	
	*	
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。	
	*	
	*		
	*		1.6.2	查询角色操作步骤
	*
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
	*
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
    *
     */
    @JSON
    public String updateRoleResource() {
		try {
			// 如果角色的编码为空，则直接返回
			if(roleVo == null||roleVo.getRoleEntityDetail() == null
				|| StringUtils.isEmpty(roleVo.getRoleEntityDetail().getCode())){
			    LOGGER.info("没有要修改的角色权限");
			    return returnSuccess();
			}
			String roleCode=roleVo.getRoleEntityDetail().getCode();
			String addResourceCodesString= roleVo.getResourceCodes();
			String deleteResourceCodesString= roleVo.getDeleteResourceCodes();
			addResourceCodesString = addResourceCodesString==null?"":addResourceCodesString;
			deleteResourceCodesString = deleteResourceCodesString==null?"":deleteResourceCodesString;
			// 前台传过来的角色的新增的权限编码，多个，用英文逗号","分隔
			String[] addResCodes=addResourceCodesString.split(SymbolConstants.EN_COMMA);
			// 前台传过来的角色的作废的权限编码，多个，用英文逗号","分隔
			String[] deleteResCodes=deleteResourceCodesString.split(SymbolConstants.EN_COMMA);
			// 将resourceCode转成set，便于修改
			Set<String> addResourceCodes=new HashSet<String>();
			addResourceCodes.addAll(Arrays.asList(addResCodes));
			Set<String> deleteResourceCodes = new HashSet<String>();
			deleteResourceCodes.addAll(Arrays.asList(deleteResCodes));
			addResourceCodes.remove(null);
			addResourceCodes.remove("");
			deleteResourceCodes.remove(null);
			deleteResourceCodes.remove("");
			roleResourceService.updateRoleResource(roleCode,addResourceCodes,deleteResourceCodes);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e.getMessage());
		}
    }

    /**
     * 根据权限名称查询权限列表 值放在roleVo.roleEntityList
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-8 下午5:58:41
     * 
     * *      1	进入角色主界面。	
     * 	
	*		2	查出需要修改的角色。	
	*	
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。	
	*	弹出修改确认对话框。
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	*		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。		
	*
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。	
	*	
	*		
	*		1.6.2	查询角色操作步骤
	*
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
	*
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
     */
    @JSON
    public String queryRoleListByName() {
	RoleEntity roleEntityConditon = null;
	RoleEntity roleEntityView = null;
	if (roleVo == null) {
	    roleVo = new RoleVo();
	}
	roleEntityView = roleVo.getRoleEntityDetail();
	if (roleEntityView == null) {
	    roleEntityConditon = new RoleEntity();
	} else {
	    roleEntityConditon = roleEntityView;
	}
	if(StringUtils.isBlank(roleEntityConditon.getName())){
	    roleEntityConditon.setName(null);
	}
	// 返回的结果显示在表格中：
	List<RoleEntity> roleEntityList = roleService.queryRoleByEntity(
		roleEntityConditon, start, limit);
	roleVo.setRoleEntityList(roleEntityList);

	totalCount = roleService.queryRoleByEntityCount(roleEntityConditon);
	return returnSuccess();
    }

    /**
     * 根据权限编码查询权限详情
     * 
     * 值放在roleVo.roleEntityDetail
     * 
     * @author 087584
     * @date 2012-9-8 上午11:16:53
     * @return
     * @see
     * *      1	进入角色主界面。		
     * 
	*		2	查出需要修改的角色。		
	*
		*	3	点击修改按钮。	【角色信息】	进入分配权限界面。
		*
		*	4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
		*
		*		弹出修改确认对话框。
		*
		*	5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
		*
		*	6	点击提示框中的确定按钮。		回到角色管理主界面。
		*
		*	分配权限异常操作步骤
		*
		*	序号	扩展事件	相关数据	备注
		*
		*	5a	点击取消按钮，退出当前界面，返回主界面。		
		*
		*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。	
		*	
		*	
		*	1.6.2	查询角色操作步骤
		*
		*	序号	基本步骤	相关数据	补充步骤
		*
		*	1	进入角色管理主界面。		
		*
		*	2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
		*
		*	3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
		*
		*	SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
		*
		*	SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
		*
		*	SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
		*
		*并提示用户此权限与已经添加的XXX权限互斥。
		*
		*	SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
     */
    @JSON
    public String queryRoleByCode() {
	RoleEntity roleEntityConditon = null;
	RoleEntity roleEntityView = null;
	if (roleVo == null) {
	    roleVo = new RoleVo();
	}
	roleEntityView = roleVo.getRoleEntityDetail();

	if (roleEntityView == null) {
	    return returnSuccess();
	} else {
	    roleEntityConditon = roleEntityView;
	}

	// 返回的结果显示在表格中：
	RoleEntity roleEntityDetail = roleService
		.queryRoleByCode(roleEntityConditon.getCode());
	roleVo.setRoleEntityDetail(roleEntityDetail);

	return returnSuccess();
    }

    /**
     * 根据角色编码查询包含的权限
     * 
     * 值放在roleVo.roleEntityDetail
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-14 下午7:12:28
     * 
     * *      1	进入角色主界面。		
     * 
	*		2	查出需要修改的角色。
	*		
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*		弹出修改确认对话框。
	*
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	*		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。	
	*	
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*		
	*		1.6.2	查询角色操作步骤
	*		
	*		序号	基本步骤	相关数据	补充步骤
	*		
	*		1	进入角色管理主界面。
	*				
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*		
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
	*		
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*		
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*		
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*		并提示用户此权限与已经添加的XXX权限互斥。
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
     */
    @JSON
    public String queryRoleResourceByCodeOfRole() {
	RoleEntity roleEntityConditon = null;
	RoleEntity roleEntityView = null;
	if (roleVo == null) {
	    roleVo = new RoleVo();
	}
	roleEntityView = roleVo.getRoleEntityDetail();

	if (roleEntityView == null) {
	    return returnSuccess();
	} else {
	    roleEntityConditon = roleEntityView;
	}

	/**
	 * 构造查询条件：
	 */
	RoleResourceEntity roleResourceEntityCondition = new RoleResourceEntity();
	// 根据权限编码查询：
	roleResourceEntityCondition.setRoleCode(roleEntityConditon.getCode());
	
	List<RoleResourceEntity> roleResourceEntityList = roleResourceService
		.queryRoleResourceExactByEntity(roleResourceEntityCondition, 0,
			Integer.MAX_VALUE);
	roleVo.setRoleResourceEntityList(roleResourceEntityList);
	return returnSuccess();
    }
    
    /**
     * 查询所有的角色
     * 值放在roleVo.roleEntityList
     * 
     * @author 087584
     * 
     * @date 2012-9-8 上午11:16:53
     * 
     * @return
     * @see
     * *      1	进入角色主界面。	
     * 	
	*		2	查出需要修改的角色。	

	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*
	*		弹出修改确认对话框。
	*
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	*
	*		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。		
	*
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因
	*
	*，继续停留在修改界面。		
	*		
	*		1.6.2	查询角色操作步骤
	*
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
	*
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加
	*，
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
	*
     */
    @JSON
    public String queryRoleAll() {
	List<RoleEntity> roleEntityList = roleService
		.queryRoleExactByEntity(new RoleEntity(), 0,
			Integer.MAX_VALUE);
	roleVo.setRoleEntityList(roleEntityList);
	return returnSuccess();
    }

    /**
     * 
     * <p>检查赋给用户的权限是否相互互斥</p> 
     * @author 何波
     * @date 2013-2-26 下午2:31:05
     * @return
     * @see
     * *      1	进入角色主界面。		
     * 
     * 
	*		2	查出需要修改的角色。		
	*
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。
	*
	*        点击保存按钮。		弹出修改确认对话框。
	*        
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	*		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。		
	*
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。
	*		
	*		
	*		1.6.2	查询角色操作步骤
	*
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
	*
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】	
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*         并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
*
     */
    @JSON
	public String checkUserAuthorityIsConflict() {
	
		if (roleVo == null) {
			roleVo = new RoleVo();
		}
		if (StringUtil.isEmpty(newResourceCode)) {
			roleVo.setValidate(true);
			return returnSuccess();
		}
		ResourceConflictEntity entity =new ResourceConflictEntity();
		//entity.setRoleCode(roleVo.getCurrRoleCode());
		//查询出全部的权限互斥
		List<ResourceConflictEntity> resourceConflictEntitys1 = resourceConflictService.queryResourceConflictExactByEntity(entity,0,Integer.MAX_VALUE);
		//不存在权限互斥
		if(CollectionUtils.isEmpty(resourceConflictEntitys1)){
			roleVo.setValidate(true);
			return returnSuccess();
		}
		//List<List<ResourceEntity>>  resourceLists=  new ArrayList<List<ResourceEntity>>();
		List<String>  resourceLists=  new ArrayList<String>();
		Set<String> tempResourceCode1 = new HashSet<String>();
		
		//sonar 313353
		this.sonarOne(tempResourceCode1, resourceLists);
		List<String>  removeResourceLists=  new ArrayList<String>();
		Set<String> tempRemoveResourceCode = new HashSet<String>();
		//sonar 313353
		this.sonarTwo(removeResourceLists, tempRemoveResourceCode);
		
		//只判断当前所选权限编码不级联该权限下子权限
		//List<ResourceEntity>  resourceList = resourceService.queryResourceByCodeAllChildNode(newResourceCode);
		//验证添加权限之间是否存在互斥
		List<String> resourceCodeLsit = new ArrayList<String>();
		Set<String> resourceCodes1= new HashSet<String>();
		Set<String>  resourceCodes2= new HashSet<String>();
		if(CollectionUtils.isNotEmpty(resourceLists)){
		//临时添加给角色的权限编码
		for (int i = 0; i < resourceLists.size(); i++) {
			/*List<ResourceEntity> resources=  resourceLists.get(i);
			for (int j = 0; j < resources.size(); j++) {
				ResourceEntity resource = resources.get(j);
				resourceCodeLsit.add(resource.getCode());
			}*/
			resourceCodeLsit.add(resourceLists.get(i));
		}
		//将将添加的权限放入set中
		resourceCodes1.addAll(resourceCodeLsit);
		}
		/*for (int i = 0; i < resourceList.size(); i++) {
			ResourceEntity resource = resourceList.get(i);
			resourceCodes2.add(resource.getCode());
		}*/
		resourceCodes2.add(newResourceCode);
		
	   //合并角色临时添加的所有权限编码
		Set<String>  resourceCodes= new HashSet<String>();
		/**
		 * 根据角色编码查询用户权限
		 */
		RoleResourceEntity roleResource = new RoleResourceEntity();
		roleResource.setRoleCode(roleVo.getCurrRoleCode());
		roleResource.setBelongSystemType(MessageConstants.MSG__URL_TYPE__WEB);
		roleResource.setResType(NumberConstants.NUMERAL_S_FOUR);
		List<RoleResourceEntity> roleResourceEntityList = roleResourceService.queryRoleResourceExactByEntity(roleResource);
		if(CollectionUtils.isEmpty(roleResourceEntityList)){
			roleVo.setValidate(true);
			return  returnSuccess();
		}
		List<String>  codeList = new ArrayList<String>();
		//将角色拥有的权限编码的放入list中
		for (int i = 0; i < roleResourceEntityList.size(); i++) {
			codeList.add(roleResourceEntityList.get(i).getResourceCode());
		}
		resourceCodes.addAll(codeList);
		//sonar 313353
		Set<String> resourceCodeLoopSet = this.sonarThree(resourceCodes, removeResourceLists);
		
		resourceCodeLoopSet.addAll(resourceCodes1);
		resourceCodeLoopSet.addAll(resourceCodes2);
		//互斥权限列表
		List<ResourceConflictEntity>  conflictEntitys  = new ArrayList<ResourceConflictEntity>();
		//根据已有的互斥，校验当前用户是否有互斥存在
		for (int i = 0; i < resourceConflictEntitys1.size(); i++) {
			ResourceConflictEntity resourceConflictEntity = resourceConflictEntitys1.get(i);
			if(resourceCodeLoopSet.contains(resourceConflictEntity.getFirstCode()) && 
					resourceCodeLoopSet.contains(resourceConflictEntity.getSecondCode())){
					conflictEntitys.add(resourceConflictEntity);
			}
		}
		
	   //sonar 313353 start
	   this.sonarResult(conflictEntitys);
       return returnSuccess();
	}
    
    /**
     * sonar优化拆分
     * @author 313353
     * @date 2016/09/12
     */
    private void sonarOne(Set<String> tempResourceCode1, List<String>  resourceLists){
    	if(CollectionUtils.isNotEmpty(tempResourceCode)){
			//去掉null和""
			tempResourceCode1.removeAll(Collections.singleton(null));
			tempResourceCode1.removeAll(Collections.singleton(""));
			for (int i = 0; i < tempResourceCode.size(); i++) {
				String  tempStr= tempResourceCode.get(i);
				tempResourceCode1.add(tempStr);
			}
			Iterator<String> addResourceCode= tempResourceCode1.iterator();
			while(addResourceCode.hasNext()){
			    String code = (String)addResourceCode.next();
			   /* List<ResourceEntity> resourceList = resourceService
						.queryResourceByCodeAllChildNode(code);
				resourceLists.add(resourceList);*/
				resourceLists.add(code);
			}
		}
    }
    
    /**
     * sonar优化拆分
     * @author 313353
     * @date 2016/09/12
     */
    private void sonarTwo(List<String>  removeResourceLists, Set<String> tempRemoveResourceCode){
    	if(CollectionUtils.isNotEmpty(tempRemoveResourceCodes)){
			//去掉null和""
			tempRemoveResourceCode.removeAll(Collections.singleton(null));
			tempRemoveResourceCode.removeAll(Collections.singleton(""));
			for (int i = 0; i < tempRemoveResourceCodes.size(); i++) {
				String  tempStr= tempRemoveResourceCodes.get(i);
				tempRemoveResourceCode.add(tempStr);
			}
			Iterator<String> addResourceCode= tempRemoveResourceCode.iterator();
			while(addResourceCode.hasNext()){
			    String code = (String)addResourceCode.next();
			   /* List<ResourceEntity> resourceList = resourceService
						.queryResourceByCodeAllChildNode(code);
				resourceLists.add(resourceList);*/
			    removeResourceLists.add(code);
			}
		}
    }
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 * @date 2016/09/12
	 */
	@SuppressWarnings("unchecked")
	private Set<String> sonarThree(Set<String> resourceCodes,
			List<String> removeResourceLists) {
		Set<String> resourceCodeLoopSet = null;
		if (CollectionUtils.isNotEmpty(removeResourceLists)) {

			List<String> tmpResourceCodes = new ArrayList<String>();
			tmpResourceCodes.addAll(resourceCodes);
			// 先算差集
			List<String> diffList = (List<String>) CollectionUtils.subtract(
					removeResourceLists, tmpResourceCodes);
			// 去除掉该角色不存在的权限编码
			removeResourceLists.removeAll(diffList);
			// 取联集
			List<String> resourceCodeLoopList = (List<String>) CollectionUtils
					.disjunction(tmpResourceCodes, removeResourceLists);
			resourceCodeLoopSet = new HashSet<String>();
			resourceCodeLoopSet.addAll(resourceCodeLoopList);
		} else {
			resourceCodeLoopSet = resourceCodes;
		}
		return resourceCodeLoopSet;
	}
    
    /**
     * sonar优化拆分
     * @author 313353
     * @date 2016/09/12
     */
    private void sonarResult(List<ResourceConflictEntity>  conflictEntitys){
    	if(CollectionUtils.isNotEmpty(conflictEntitys)){
     	   StringBuffer  conflictInfo =  new StringBuffer();
 			for (int i = 0; i < conflictEntitys.size(); i++) {
 				ResourceConflictEntity  resourceConflictEntity =  conflictEntitys.get(i);
 				conflictInfo.append(resourceConflictEntity.getFirstCodeName() +"与"+
 						resourceConflictEntity.getSecondCodeName()+"\r\n");
 			}
 			roleVo.setValidate(false);
 			roleVo.setUserResourceConflictInfo(conflictInfo.toString());
        }else{
     	    roleVo.setValidate(true);
        }
    }
    
    /**
     * 下面是一些工具方法
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleAction.class);
    /**
     * 下面是属性的get,set方法：
     */
    private RoleVo roleVo = new RoleVo();

    private IRoleService roleService;

    private IRoleResourceService roleResourceService;
    //权限互斥service
    private  IResourceConflictService resourceConflictService;
    //给用户新增的临时权限编列表
    private List<String> tempResourceCode;
    //用户删除的临时权限编列表
    private List<String> tempRemoveResourceCodes; 
    //给用户新增的权限编码
    private  String  newResourceCode;
    //提供查询一个节点下的所有节点服务
    //private IResourceService resourceService;
    /**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
    public RoleVo getRoleVo() {
        return roleVo;
    }
    /**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
    
    public void setRoleVo(RoleVo roleVo) {
        this.roleVo = roleVo;
    }

    /**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */    
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    /**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */ 
    public void setRoleResourceService(IRoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }
    /**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	public void setResourceConflictService(
			IResourceConflictService resourceConflictService) {
		this.resourceConflictService = resourceConflictService;
	}
	/**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	public List<String> getTempResourceCode() {
		return tempResourceCode;
	}
	/**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	public void setTempResourceCode(List<String> tempResourceCode) {
		this.tempResourceCode = tempResourceCode;
	}
	/**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	public String getNewResourceCode() {
		return newResourceCode;
	}
	/**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	public void setNewResourceCode(String newResourceCode) {
		this.newResourceCode = newResourceCode;
	}
    
	/**
	 * 
	 * 实现get、set方法
	 * 
	 * @date 2013-3-19 下午1:49:50
	 */
	/*public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}*/

	
	public List<String> getTempRemoveResourceCodes() {
		return tempRemoveResourceCodes;
	}

	
	public void setTempRemoveResourceCodes(List<String> tempRemoveResourceCodes) {
		this.tempRemoveResourceCodes = tempRemoveResourceCodes;
	}
	
	
    
}