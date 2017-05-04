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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/UserOrgRoleAction.java
 * 
 * FILE NAME        	: UserOrgRoleAction.java
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
 **相关SUC
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
			**相关SUC
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
			
			
*/ 

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.UserOrgRoleVo;

/**
 * 用户部门角色 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:49:56
 * 
 */
public class UserOrgRoleAction extends AbstractAction {
    
    
    /**
     * 更新 用户部门角色
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午6:15:25
     *  1	进入角色主界面。		
     *  
	*		2	查出需要修改的角色。		
	*
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*		弹出修改确认对话框。
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
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	*
     */
    @JSON
    public String updateUserOrgRole() {
	if (userOrgRoleVo == null || userOrgRoleVo.getUserOrgRoleEntity() ==null
		||StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getEmpCode())
		|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getOrgUnifiedCode())) {
	    return returnSuccess();
	}
	
	try{
	    //调用修改用户部门角色方法
		userOrgRoleService.updateUserOrgRole(userOrgRoleVo.getUserOrgRoleEntity());
		
	    return returnSuccess();
	}catch (BusinessException e) {
	    LOGGER.error("更新用户部门角色失败", e);
	    return returnError("更新用户部门角色失败");
	}catch (Exception e) {
	    LOGGER.error("更新用户部门角色失败", e);
	    return returnError("未知错误");
	}
    }
    /**
     * 模糊查询
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-17 下午6:15:25
     *  1	进入角色主界面。		
     *  
	*	2	查出需要修改的角色。		
	*
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。	
	*	弹出修改确认对话框。
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	**		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*	分配权限异常操作步骤
	*
	*		序号	扩展事件	相关数据	备注
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。		
	*
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。
	*		
	*		
	**		1.6.2	查询角色操作步骤
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
		*
     */
    @JSON
    public String queryUserOrgRoleDto() {
	if (userOrgRoleVo == null) {
	    userOrgRoleVo = new UserOrgRoleVo();
	}
	try{
	    // 设置查询条件
	    EmployeeEntity employee = new EmployeeEntity();
	    UserOrgRoleEntity userOrgRole = new UserOrgRoleEntity();
	    RoleEntity role = new RoleEntity();
	    UserOrgRoleDto userOrgRoleDto = userOrgRoleVo.getUserOrgRoleDto();
	    
	    // 如果部门，角色为空，则从人员的角度去查询
	    if(userOrgRoleDto == null ||
		    ( StringUtils.isBlank(userOrgRoleDto.getOperateOrgUnifiedCode())
		    && StringUtils.isBlank(userOrgRoleDto.getRoleName()))){
		if (userOrgRoleDto != null) {
		    employee.setEmpCode(userOrgRoleDto.getEmpCode());
		    employee.setEmpName(userOrgRoleDto.getEmpName());
		}
		// 设置查询结果
		List<UserOrgRoleDto> userOrgRoleDtoList = userOrgRoleService
			.queryUserOrgRoleDtoByEmployee(employee, start, limit);
		if (!CollectionUtils.isEmpty(userOrgRoleDtoList)) {
		    userOrgRoleDtoList = userOrgRoleService.attachRole(userOrgRoleDtoList);
		    userOrgRoleVo.setUserOrgRoleDtoList(userOrgRoleDtoList);
		}
		totalCount = userOrgRoleService.queryUserOrgRoleDtoByEmployeeCount(employee);
		return returnSuccess();
	    }
	    if (userOrgRoleVo.getUserOrgRoleDto() != null) {
		userOrgRole.setEmpCode(userOrgRoleDto.getEmpCode());
		employee.setEmpName(userOrgRoleDto.getEmpName());
		userOrgRole.setOrgUnifiedCode(userOrgRoleDto
			.getOperateOrgUnifiedCode());
		role.setName(userOrgRoleDto.getRoleName());
	    }
	    // 设置查询结果
	    List<UserOrgRoleDto> userOrgRoleDtoList = userOrgRoleService
		    .queryUserOrgRoleDto(userOrgRole, employee, role, start,
			    limit);
	    if (!CollectionUtils.isEmpty(userOrgRoleDtoList)) {
		userOrgRoleVo.setUserOrgRoleDtoList(userOrgRoleDtoList);
	    }
	    totalCount = userOrgRoleService.queryUserOrgRoleDtoCount(
		    userOrgRole, employee, role);
	    return returnSuccess();
	}catch (BusinessException e) {
	    return returnError(e);
	}
    }
    /**
     * 根据用户部门编码，分页查询角色
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     *  1	进入角色主界面。		
     *  
	*		2	查出需要修改的角色。		
	*
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*		弹出修改确认对话框。
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	**		6	点击提示框中的确定按钮。		回到角色管理主界面。
	*
	*		分配权限异常操作步骤
	*		序号	扩展事件	相关数据	备注
	*
	*
	*		5a	点击取消按钮，退出当前界面，返回主界面。		
	*
	*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。	
	*	
	*		
	*		1.6.2	查询角色操作步骤
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
	*
	*		2	输入查询条件。点击查询按钮。	【角色查询条件】	系统返回查询结果。
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】
	*
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	*		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	*
     */
    @JSON
    public String queryRoleByUserOrg() {
	if (userOrgRoleVo == null || userOrgRoleVo.getUserOrgRoleEntity() == null
		|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getEmpCode())
		|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getOrgUnifiedCode())) {
	    return returnSuccess();
	}
	try{
	    List<RoleEntity> roleList = this.queryRoleList(userOrgRoleVo, start, limit);
	    if (!CollectionUtils.isEmpty(roleList)) {
		userOrgRoleVo.setRoleList(roleList);
	    }
	    UserOrgRoleEntity userOrgRoleEntity = userOrgRoleVo.getUserOrgRoleEntity();
	    totalCount = userOrgRoleService.queryUserOrgRoleExactByEntityCount(userOrgRoleEntity);
	    return returnSuccess();
	}catch (BusinessException e) {
	    return returnError(e);
	}
    }
    /**
     * 根据用户部门编码，查询全部角色
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 上午9:06:41
     *  1	进入角色主界面。		
     *  
	*		2	查出需要修改的角色。		
	*
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*		弹出修改确认对话框。
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
	**		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	*
     */
    @JSON
    public String queryRoleByUserOrgAll() {
	if (userOrgRoleVo == null || userOrgRoleVo.getUserOrgRoleEntity() == null
		|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getEmpCode())
		) {
	    return returnSuccess();
	}	
	try{
	    List<RoleEntity> roleList = this.queryRoleList(userOrgRoleVo, 0, Integer.MAX_VALUE);
	    if (!CollectionUtils.isEmpty(roleList)) {
		userOrgRoleVo.setRoleList(roleList);
	    }
	    return returnSuccess();
	}catch (BusinessException e) {
	    return returnError(e);
	}
    }
    /**
     * 根据用户部门编码，查询未分配角色
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-21 上午9:06:41
     * 
     *  1	进入角色主界面。		
     *  
	 *		2	查出需要修改的角色。		
	 *
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*		弹出修改确认对话框。
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
	*
	*		3	点击分页按钮，查看各页的查询结果。	【角色列表信息】
	*
	*		SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
	*
	**		SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
	*
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
     */
    @JSON
    public String queryRoleByNoAllot() {
	if (userOrgRoleVo == null || userOrgRoleVo.getUserOrgRoleEntity() == null
		|| StringUtils.isBlank(userOrgRoleVo.getUserOrgRoleEntity().getEmpCode())
		) {
	    return returnSuccess();
	}	
	try{
	    List<RoleEntity> noAllotRoleList = roleService.queryRoleByUserOrgRole(userOrgRoleVo.getUserOrgRoleEntity());
		userOrgRoleVo.setRoleList(noAllotRoleList);
	    return returnSuccess();
	}catch (BusinessException e) {
	    return returnError(e);
	}
    }   
    /**
     * 根据用户部门角色中的用户编码，操作部门编码查询角色
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午9:31:50
     *  1	进入角色主界面。		
     *  
	 *		2	查出需要修改的角色。		
	 *
	*		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。
	*
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
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
	*	
     */
    private List<RoleEntity> queryRoleList(UserOrgRoleVo userOrgRoleVo, int start, int limit)
	    throws BusinessException {
	// 设置查询条件
	UserOrgRoleEntity userOrgRoleEntity = userOrgRoleVo
		.getUserOrgRoleEntity();
	// 设置查询结果
	List<UserOrgRoleEntity> userOrgRoleList = userOrgRoleService
		.queryUserOrgRoleExactByEntity(userOrgRoleEntity, start, limit);

	if (!CollectionUtils.isEmpty(userOrgRoleList)) {
	    // 组装查询条件
	    int leng = userOrgRoleList.size();
	    String[] codes = new String[leng];
	    for (int i = 0; i < leng; i++) {
		codes[i] = userOrgRoleList.get(i).getRoleCode();
	    }
	    return roleService.queryRoleBatchByCode(codes);
	}
	return null;
    }
    /**
     * 
     * 下面是变量的声明
     * 
     */
    private static final long serialVersionUID = -4387627988772020011L;
    private IUserOrgRoleService userOrgRoleService;
    private IRoleService roleService;
    private UserOrgRoleVo userOrgRoleVo;
   
    private static final Logger LOGGER = LoggerFactory.getLogger(UserOrgRoleAction.class);
    /**
     * =================================================================
     * 
     * 下面是get,set方法：
     * 
     */ 
    public UserOrgRoleVo getUserOrgRoleVo() {
        return userOrgRoleVo;
    }
    /**
     * =================================================================
     * 
     * 下面是get,set方法：
     * 
     */ 
    public void setUserOrgRoleVo(UserOrgRoleVo userOrgRoleVo) {
        this.userOrgRoleVo = userOrgRoleVo;
    }
    /**
     * =================================================================
     * 
     * 下面是get,set方法：
     * 
     */ 
    public void setUserOrgRoleService(IUserOrgRoleService userOrgRoleService) {
        this.userOrgRoleService = userOrgRoleService;
    }  
    /**
     * =================================================================
     * 
     * 下面是get,set方法：
     * 
     */ 
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    
}
