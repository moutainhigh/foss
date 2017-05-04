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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ResourceConflictService.java
 * 
 * FILE NAME        	: ResourceConflictService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		 *
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		*  
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		*  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		*
		1.5.8	界面原型-权限添加角色界面
		 *
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		*
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		*
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		*
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		*
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		*
		1.7	业务规则
		 *
		图四：权限类型所属关系图
		*
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		*
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		*
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		*
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		**************************************************
		*		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		*
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		 *
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		  
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		
		1.5.8	界面原型-权限添加角色界面
		 
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		
		1.7	业务规则
		 
		图四：权限类型所属关系图
		
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		
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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SysCtrlConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceConflictService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceConflictException;

public class ResourceConflictService implements
	IResourceConflictService {
    /**
     * 权限互斥 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:36:27
     */
    @Override
    @Transactional
    public ResourceConflictEntity addResourceConflict(ResourceConflictEntity entity) {
	//检查参数的合法性
	if(null == entity || StringUtils.isBlank(entity.getFirstCode())
		|| StringUtils.isBlank(entity.getSecondCode())){
	    throw new ResourceConflictException("","新增时，两个权限编码不能为空");
	}
	List<ResourceConflictEntity> entitys = resourceConflictDao
		.queryResourceConflictExactByEntity(entity,
			NumberConstants.NUMERAL_ONE, Integer.MAX_VALUE);
	if(CollectionUtils.isNotEmpty(entitys)){
	    throw new ResourceConflictException("","两个互斥的权限已存在");
	}
	return resourceConflictDao.addResourceConflict(entity);
    }
    /**
     * 通过virtualCode标识来删除
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-2 下午3:36:27
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#deleteResourceConflict(java.lang.String)
     */
    @Override
    @Transactional
    public ResourceConflictEntity deleteResourceConflict(ResourceConflictEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	return resourceConflictDao.deleteResourceConflict(entity);
    }
    /**
     * 通过code标识来批量删除
     * 
     * 
     * @author 087584-foss-lijun
     * 
     * 
     * @date 2012-11-2 下午3:36:27
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#deleteResourceConflictMore(java.lang.String[])
     */
    @Override
    @Transactional
    public ResourceConflictEntity deleteResourceConflictMore(String[] codes , String deleteUser) {
	return resourceConflictDao.deleteResourceConflictMore(codes, deleteUser);
    }
    
    
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询 
     * 通过 VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午3:36:27
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictService#queryResourceConflictByCode(java.lang.String)
     */
    @Override
    public ResourceConflictEntity queryResourceConflictByVirtualCode(String code) {
	if (null == code) {
	    return null;
	}
	return resourceConflictDao.queryResourceConflictByVirtualCode(code);
    }
    /**
     * 精确查询 
     * 
     * 根据多个编码批量查询
     * 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-18 下午4:1:47
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictService#queryResourceConflictByCode(java.lang.String)
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictBatchByVirtualCode(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	List<ResourceConflictEntity> retnEntitys = resourceConflictDao
		.queryResourceConflictBatchByVirtualCode(codes);
	retnEntitys = this.attachResourceName(retnEntitys);
	return retnEntitys;
    }
    /** 
     * 精确查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-19 上午11:11:15
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictService#queryResourceConflictExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity, int, int)
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictExactByEntity(
	    ResourceConflictEntity entity, int start, int limit) {
	List<ResourceConflictEntity> retnEntitys = resourceConflictDao.queryResourceConflictExactByEntity(
			entity, start, limit);
	retnEntitys = this.attachResourceName(retnEntitys);
	return retnEntitys;
    }
    /**
     * 精确查询-查询总条数，用于分页
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-19 上午11:09:53
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictService#queryResourceConflictExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceConflictEntity)
     */
    @Override
    public long queryResourceConflictExactByEntityCount(ResourceConflictEntity entity) {
	return resourceConflictDao.queryResourceConflictExactByEntityCount(entity);
    }
    /**
     * 下面为特殊查询
     */
    /**
     * 根据 两批权限CODE 查询哪些是互斥的：
     * 
     * 不支持两个codes都为空，如果两个codes都为空，则调queryResourceConflictExactByEntity
     * 
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-26 下午4:36:7
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceConflictDao#queryResourceConflictByCodes(java.lang.String[], java.lang.String[])
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictByCodes(
	    String[] codes1, String[] codes2) {
	if (ArrayUtils.isEmpty(codes1) && ArrayUtils.isEmpty(codes2)) {
	    return null;
	}
	List<ResourceConflictEntity> retnEntitys = resourceConflictDao.queryResourceConflictByCodes(codes1,codes2);
	retnEntitys = this.attachResourceName(retnEntitys);
	return retnEntitys;
    }
    /**
     * 根据权限CODE查询这些CODE中哪些是互斥的：
     * 
     * 如果CODE为空，则应调queryResourceConflictExactByEntity,调用此方法返回空
     * 
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-26 下午4:36:7
     */
    @Override
    public List<ResourceConflictEntity> queryResourceConflictByCodes(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	List<ResourceConflictEntity> retnEntitys = resourceConflictDao.queryResourceConflictByCodes(codes);
	retnEntitys = this.attachResourceName(retnEntitys);
	return retnEntitys;
    }
    /**
     * 查询一批权限列表数组（List<List<String>>）中哪些是互斥的,只要查到就返回，用于分配角色时，判断哪些角色是互斥的
     * 
     * 建议后面改为调一个存储过程
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-26 下午4:36:7
     */
    //@Override
    public List<ResourceConflictEntity> queryResourceConflictByBatchList(
	    List<List<String>> codesList) {
	if (CollectionUtils.isEmpty(codesList)) {
	    return null;
	}
	// 声明目标准权限编码二维数组
	List<List<String>> lists=new LinkedList<List<String>>();
	
	// 将数组中，长度超过限制的打散成多个，使oracle的sql语句in可使用
	for(List<String> list : lists){
	    if(CollectionUtils.isNotEmpty(list)){
		if(list.size()> SysCtrlConstants.RESOURCE_CONFLICT_JUDGE_BATCH){
		    int leng = list.size();
		    int batchNum = leng/SysCtrlConstants.RESOURCE_CONFLICT_JUDGE_BATCH;
		    for(int i=0;i<batchNum;i++){
			List<String> atomList = new ArrayList<String>();
			int startIndex = i*SysCtrlConstants.RESOURCE_CONFLICT_JUDGE_BATCH;
			atomList.addAll(list.subList(startIndex, startIndex + SysCtrlConstants.RESOURCE_CONFLICT_JUDGE_BATCH));
			lists.add(atomList);
		    }
		    // 将最后一批权限添加进来
		    List<String> atomList = new ArrayList<String>();
		    atomList.addAll(list.subList(batchNum*SysCtrlConstants.RESOURCE_CONFLICT_JUDGE_BATCH, leng));
		    lists.add(atomList);
		}else{
		    lists.add(list);
		}
		
	    }
	}
	// 让权限数组相互判断是否互斥
	List<List<String>> traversal = new ArrayList<List<String>>();
	traversal.addAll(lists);
	for(List<String> list : traversal){
	    String[] codes1 = list.toArray(new String[0]);
	    List<ResourceConflictEntity> retnEntitys = this.queryResourceConflictByCodes(codes1);
	    if (CollectionUtils.isNotEmpty(retnEntitys)) {
		return retnEntitys;
	    }
	    lists.remove(list);
	    if(lists.isEmpty()){
		return null;
	    }
	    for(List<String> list2 : lists){
		String[] codes = list2.toArray(new String[0]);
		retnEntitys = this.queryResourceConflictByCodes(codes1, codes);
		if(CollectionUtils.isNotEmpty(retnEntitys)){
		    return retnEntitys;
		}
	    }
	}
	return null;
    }
    /**
     * 给互斥的权限加上名称,两个互斥的权限编码不能为空
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-12-1 上午8:34:24
     */
    public ResourceConflictEntity attachResourceName(ResourceConflictEntity entity){
	if(entity == null || StringUtils.isBlank(entity.getFirstCode())
		|| StringUtils.isBlank(entity.getSecondCode())){
	    return entity;
	}
	ResourceEntity resource = resourceService.queryResourceByCodeNoCache(entity.getFirstCode());
	if(resource!=null){
	    entity.setFirstCodeName(resource.getName());
	}
	resource = resourceService.queryResourceByCodeNoCache(entity.getSecondCode());
	if(resource!=null){
	    entity.setSecondCodeName(resource.getName());
	}
	return entity;
    }
    /**
     * 给互斥的权限加上名称,两个互斥的权限编码不能为空
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-12-1 上午8:34:24
     */
    public List<ResourceConflictEntity> attachResourceName(List<ResourceConflictEntity> entitys){
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}
	for(ResourceConflictEntity entity : entitys){
	    this.attachResourceName(entity);
	}
	return entitys;
    }
    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private IResourceConflictDao resourceConflictDao;
    private IResourceService resourceService;

    public void setResourceConflictDao(IResourceConflictDao resourceConflictDao) {
	this.resourceConflictDao = resourceConflictDao;
    }
    public void setResourceService(IResourceService resourceService) {
        this.resourceService = resourceService;
    }
    /**
     * 
     * <p>验证用户已有的权限与新增的权限是否互斥</p> 
     * 
     * @author 何波
     * 
     * @date 2013-2-26 上午9:06:00
     * 
     * @param codeList 用户已有权限编码
     * 
     * @param code  新增给用户的编码
     * 
     * @return
     * 
     * @see
     */
	@Override
	public List<ResourceConflictEntity> checkUserResourceIsConflict(List<String> codeList, List<String> code) throws ResourceConflictException {
		//空验证
		if(CollectionUtils.isEmpty(codeList) || CollectionUtils.isEmpty(code) ){
			return  null;
		}
		String[] codes = new String[codeList.size()];
		for (int i = 0; i < codeList.size(); i++) {
			codes[i] = codeList.get(i);
		}
		List<ResourceConflictEntity> resourceConflictList = queryResourceConflictByCodes(codes,null);
	    //如果为空则用户拥有的权限没有互斥
		if(CollectionUtils.isEmpty(resourceConflictList)){
			return  null;
		}
		//返回权限互斥列表
		List<ResourceConflictEntity>  resourceConflictResult = new ArrayList<ResourceConflictEntity>();
		for (int i = 0; i < resourceConflictList.size(); i++) {
			ResourceConflictEntity conflictEntity = resourceConflictList.get(i);
			if(code.contains(conflictEntity.getSecondCode()) ||
					code.contains(conflictEntity.getFirstCode())){
			//如果等于说明权限存在互斥
			if(code.contains(conflictEntity.getSecondCode())){
				//加上名称
				attachResourceName(conflictEntity);
				resourceConflictResult.add(conflictEntity);
			}
			if(code.contains(conflictEntity.getFirstCode())){
				//加上名称
				attachResourceName(conflictEntity);
				resourceConflictResult.add(conflictEntity);
			}
		}
		}
		return resourceConflictResult;
	}
    
    /** 
     * 根据权限编删除权限互斥信息
     * @author 101911-foss-zhouChunlai
     * @date 2013-5-29 下午5:15:44
     */
    @Override
    @Transactional
    public int deleteResourceConflictByCode(String code , String modifyUserCode) {
	return resourceConflictDao.deleteResourceConflictByCode(code, modifyUserCode);
    }
	
}
