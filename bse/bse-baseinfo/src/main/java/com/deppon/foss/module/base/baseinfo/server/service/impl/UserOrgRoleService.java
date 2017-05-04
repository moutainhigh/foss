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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/UserOrgRoleService.java
 * 
 * FILE NAME        	: UserOrgRoleService.java
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
 ***相关SUC
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
			*********************************************************************************
			* *修订记录 
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
			
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserDeptDataService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserOrgRoleException;
import com.deppon.foss.module.base.baseinfo.server.action.actionutil.GainEmployee;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互“用户组织角色信息”的数据库对应数据访问Service接口实现类：SUC-41
 * <p>
 * 需要版本控制：已实现
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:32:16
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午7:32:16
 * @since
 * @version
 */
public class UserOrgRoleService implements IUserOrgRoleService {
	// "用户组织角色信息"Service接口
	private IUserOrgRoleDao userOrgRoleDao;
	
	private ISyncResourceService syncResourceService;
	
	public void setSyncResourceService(ISyncResourceService syncResourceService) {
		this.syncResourceService = syncResourceService;
	}
	
	/**
	 * 同属用户部门角色去其他系统
	 * @author 310854
	 * @date 2016-4-8
	 */
	private void syncResourceToOther(List<?> entitys){
		syncResourceService.syncResource(entitys);
	}
	/**
	 * <p>
	 * 新增一个“用户组织角色信息”实体入库(注意该方法没有事务，被外部事务调用)
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-16 上午10:18:55
	 * 
	 * @param userOrgRole
	 * 
	 *            “用户组织角色信息”实体
	 *            
	 * @param createUser
	 * 
	 *            创建人
	 *            
	 * @param ignoreNull
	 * 
	 *            true：忽略空值，false：包含空值
	 *            
	 * @return 1：成功；-1：失败
	 * 
	 * @throws UserOrgRoleException
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleEntityService#addUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity,
	 *      java.lang.String, boolean)
	 */
	@Override
	public int addUserOrgRole(UserOrgRoleEntity userOrgRole, String createUser,
			boolean ignoreNull) throws UserOrgRoleException {
        //判断用户组织角色信息是否为空
		if (null == userOrgRole) {
			throw new UserOrgRoleException("", "用户组织角色信息为空");
		}
		//判断员工号是否为空
		if (StringUtils.isBlank(userOrgRole.getEmpCode())) {
			throw new UserOrgRoleException("", "用户工号为空");
		}
		//判断组织为空
		if (StringUtils.isBlank(userOrgRole.getOrgUnifiedCode())) {
			throw new UserOrgRoleException("", "用户组织为空");
		}
		//判断角色信息是否为空
		if (StringUtils.isBlank(userOrgRole.getRoleCode())) {
			throw new UserOrgRoleException("", "用户角色信息为空");
		}
		//判断创建人是否为空
		if (StringUtils.isBlank(createUser)) {
			throw new UserOrgRoleException("", "创建人为空");
		}
		// 验证数据
		UserOrgRoleEntity userOrgRoleParameter = new UserOrgRoleEntity();
		//设置员工编码
		userOrgRoleParameter.setEmpCode(userOrgRole.getEmpCode());
		//设置部门标杆编码
		userOrgRoleParameter.setOrgUnifiedCode(userOrgRole.getOrgUnifiedCode());
		//设置角色编码
		userOrgRoleParameter.setRoleCode(userOrgRole.getRoleCode());
		//查询角色信息
		UserOrgRoleEntity tempUserOrgRole = userOrgRoleDao
				.queryUserOrgRoleBySelective(userOrgRoleParameter);
        //判断角色信息是否为空
		if (null == tempUserOrgRole) {
			// 087584-foss-lijun 根据“部门标杆编码”给用户部门角色加上“部门编码”
			this.attachOrgWhenUpdate(userOrgRole);

			userOrgRole.setCreateUser(createUser);
			if (ignoreNull) {
				userOrgRoleDao.addUserOrgRoleBySelective(userOrgRole);
			} else {
				userOrgRoleDao.addUserOrgRole(userOrgRole);
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 根据“用户组织角色信息”记录唯一标识作废（逻辑删除）一条“用户组织角色信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-16 上午10:18:54
	 * 
	 * @param modifyUser
	 * 
	 *            修改人
	 * @param id
	 * 
	 *            记录唯一标识
	 *            
	 * @return 1：成功；-1：失败
	 * @throws UserOrgRoleException
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleEntityService#deleteUserOrgRole(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteUserOrgRole(String id, String modifyUser)
			throws UserOrgRoleException {
        //判断ID
		if (StringUtils.isBlank(id)) {
			throw new UserOrgRoleException("", "ID 为空");
		}
		//判断修改人信息
		if (StringUtils.isBlank(modifyUser)) {
			throw new UserOrgRoleException("", "修改人为空");
		}
		// 验证数据
		UserOrgRoleEntity oldUserOrgRole, tempUserOrgRole = new UserOrgRoleEntity();
		tempUserOrgRole.setId(id);
		//查询角色信息
		oldUserOrgRole = userOrgRoleDao
				.queryUserOrgRoleBySelective(tempUserOrgRole);
        //如果用户组织角色信息为空，则抛出异常信息
		if (null == oldUserOrgRole) {
			throw new UserOrgRoleException("", "用户组织角色信息不存在");
		} else {
			// 逻辑删除，直接“停用”
			oldUserOrgRole.setModifyUser(modifyUser);
			oldUserOrgRole.setActive(FossConstants.INACTIVE);
			//在无效表中增加这条数据。在有效表中物理删除
			userOrgRoleDao.addUnactiveUserOrgRoleBySelective(oldUserOrgRole);
			userOrgRoleDao.updateUserOrgRoleBySelective(oldUserOrgRole);
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 修改一个“用户组织角色信息”实体入库
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-16 上午10:19:00
	 * 
	 * @param userOrgRole
	 * 
	 * 
	 *            “用户组织角色信息”实体
	 *            
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；-1：失败
	 * 
	 * @throws UserOrgRoleException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService#updateUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public int updateUserOrgRole(UserOrgRoleEntity userOrgRole,
			String modifyUser) throws UserOrgRoleException {
		if (null == userOrgRole) {
			throw new UserOrgRoleException("", "用户组织角色信息为空");
		}
		if (StringUtils.isBlank(userOrgRole.getId())) {
			throw new UserOrgRoleException("", "ID 为空");
		}
		if (StringUtils.isBlank(modifyUser)) {
			throw new UserOrgRoleException("", "修改人为空");
		}
		// 验证数据
		UserOrgRoleEntity oldUserOrgRole, tempUserOrgRole = new UserOrgRoleEntity();
		tempUserOrgRole.setEmpCode(userOrgRole.getEmpCode());
		tempUserOrgRole.setOrgUnifiedCode(userOrgRole.getOrgUnifiedCode());
		tempUserOrgRole.setRoleCode(userOrgRole.getRoleCode());
		oldUserOrgRole = userOrgRoleDao
				.queryUserOrgRoleBySelective(tempUserOrgRole);
		if (null == oldUserOrgRole) {
			throw new UserOrgRoleException("", "用户组织角色信息不存在");
		} else {
			// 1、禁用当前实体记录
			oldUserOrgRole.setModifyUser(modifyUser);
			oldUserOrgRole.setActive(FossConstants.INACTIVE);
			userOrgRoleDao.updateUserOrgRoleBySelective(oldUserOrgRole);
			// 2、产生一条最新启用记录
			userOrgRole.setModifyUser(modifyUser);

			// 087584-foss-lijun 根据“部门标杆编码”给用户部门角色加上“部门编码”
			this.attachOrgWhenUpdate(userOrgRole);
			userOrgRoleDao.addUserOrgRole(userOrgRole);
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 根据“用户组织角色信息”记录唯一标识查询出一条“用户组织角色信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-16 上午10:18:57
	 * @param id
	 *            记录唯一标识
	 * @return “用户组织角色信息”实体
	 * @throws UserOrgRoleException
	 * @see com.deppon.foss.module.base.baseinfo.api.
	 * server.service.IUserOrgRoleEntityService#queryUserOrgRole(java.lang.String)
	 *  1	进入角色主界面。		
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
	@Override
	@Transactional(readOnly = true)
	public UserOrgRoleEntity queryUserOrgRole(String id)
			throws UserOrgRoleException {
		if (StringUtils.isBlank(id)) {
			throw new LeasedVehicleException("", "ID 为空");
		}
		UserOrgRoleEntity userOrgRole = new UserOrgRoleEntity();
		userOrgRole.setId(id);
		return userOrgRoleDao.queryUserOrgRoleBySelective(userOrgRole);
	}
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-12 下午2:30:49
	 * @param userOrgRole
	 * 
	 *            以“用户组织角色信息”实体承载的条件参数实体
	 *            
	 * @return “用户组织角色信息”实体
	 * @throws UserOrgRoleException
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.
	 * 
	 * service.IUserOrgRoleEntityService#queryUserOrgRoleBySelective
	 * 
	 * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
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
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，
	*则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。
	*	
	 */
	@Override
	public UserOrgRoleEntity queryUserOrgRoleBySelective(
			UserOrgRoleEntity userOrgRole) throws UserOrgRoleException {
		if (null == userOrgRole) {
			throw new LeasedVehicleException("", "用户组织角色信息为空");
		}
		return userOrgRoleDao.queryUserOrgRoleBySelective(userOrgRole);
	}

	/**
	 * 
	 * <p>判断一个用户是否有超级管理员权限</p> 
	 * @author foss-zhujunyong
	 * @date Apr 18, 2013 5:43:34 PM
	 * @param employeeCode
	 * @return
	 * @see
	 */
	@Override
	public boolean checkSystemAdmin(String employeeCode) {
	    UserOrgRoleEntity c = new UserOrgRoleEntity();
	    c.setEmpCode(employeeCode);
	    c.setRoleCode(FossConstants.ROLE_FOSS_SYSTEM_ADMIN);
	    UserOrgRoleEntity result = queryUserOrgRoleBySelective(c);
	    return result != null;
	}
	
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-12 下午2:30:49
	 * 
	 * @param userOrgRole
	 *            以“用户组织角色信息”实体承载的条件参数实体
	 *            
	 * @return “用户组织角色信息”实体
	 * @throws UserOrgRoleException
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.
	 * 
	 * server.service.IUserOrgRoleService#queryUserOrgRoleListBySelective
	 * 
	 * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
	 * 
	 *  1	进入角色主界面。		
	*
	**		2	查出需要修改的角色。		
	*
	**		3	点击修改按钮。	【角色信息】	进入分配权限界面。
	*
	*		4	从权限树中选择角色所具有的权限，也可通过查询快速找到需要分配的权限。点击保存按钮。		弹出修改确认对话框。
	*		5	点击对话框中的确定按钮。		更新当前信息至数据库。弹出修改成功的提示框。
	*
	**		6	点击提示框中的确定按钮。		回到角色管理主界面。
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
	 **/
	@Override
	public List<UserOrgRoleEntity> queryUserOrgRoleListBySelective(
			UserOrgRoleEntity userOrgRole) throws UserOrgRoleException {
		if (null == userOrgRole) {
			userOrgRole = new UserOrgRoleEntity();
		}
		return userOrgRoleDao.queryUserOrgRoleListBySelective(userOrgRole);
	}
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * 
	 * @date 2012-10-16 上午10:18:58
	 * 
	 * @param airlinesAgent
	 *            以“用户组织角色信息”实体承载的条件参数实体
	 *            
	 * @param offset
	 * 
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 *            
	 * @return 符合条件的“用户组织角色信息”实体列表
	 * 
	 * @throws UserOrgRoleException
	 * 
	 * @throws UserOrgRoleException
	 * @see com.deppon.foss.module.base.ba
	 * seinfo.api.server.
	 * service.IUserOrgRoleEntityService#queryUserOrgRoleListBySelectiveCondition
	 * 
	 * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity,
	 *      int, int)
	 *       1	进入角色主界面。		
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
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UserOrgRoleEntity> queryUserOrgRoleListBySelectiveCondition(
			UserOrgRoleEntity userOrgRole, int offset, int limit)
			throws UserOrgRoleException {
		if (limit < 0 || offset < 0) {
			return new ArrayList<UserOrgRoleEntity>();
		}
		if (null == userOrgRole) {
			userOrgRole = new UserOrgRoleEntity();
		}
		List<UserOrgRoleEntity> userOrgRoleList = null;
		userOrgRoleList = userOrgRoleDao
				.queryUserOrgRoleListBySelectiveCondition(userOrgRole, offset,
						limit);
		return userOrgRoleList;
	}
	/**
	 * 下面是 087584-foss-lijun 添加
	 */

	/**
	 * 插入
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午10:54:42
	 */
	@Override
	@Transactional
	public UserOrgRoleEntity addUserOrgRoleByEntity(UserOrgRoleEntity entity, List<UserOrgRoleEntity> entitys)
			throws UserOrgRoleException {
		if (StringUtils.isBlank(entity.getOrgUnifiedCode())
				|| StringUtils.isBlank(entity.getEmpCode())
				|| StringUtils.isBlank(entity.getRoleCode())) {
			return null;
		}
		// 087584-foss-lijun 根据“部门标杆编码”给用户部门角色加上“部门编码”
		this.attachOrgWhenUpdate(entity);
		entity = userOrgRoleDao.addUserOrgRoleByEntity(entity);
		entitys.add(entity);
		
		// 强制刷新用户缓存
		//this.refreshUserCache(entity);
		return entity;
	}
	/**
	 * 根据用户编码USER_CODE，部门编码ORG_CODE 作废数据
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-21 下午9:8:10
	 * *       1	进入角色主界面。		
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
	 */
	@Override
	public UserOrgRoleEntity deleteUserOrgRole(UserOrgRoleEntity entity)
			throws UserOrgRoleException {
		if (entity == null) {
			return null;
		}
		if (StringUtils.isBlank(entity.getEmpCode())) {
			String msg = "根据用户编码USER_CODE作废数据,empCode不能为空";
			throw new UserOrgRoleException(msg, msg);
		}
		//获得当前登录人的工号
//		String empcode =FossUserContext.getCurrentUser().getUserName();
		UserOrgRoleEntity queryentity = new UserOrgRoleEntity();
		queryentity.setEmpCode(entity.getEmpCode());
		List<UserOrgRoleEntity> userOrgRoleEntitys = 
				queryUserOrgRoleListBySelectiveCondition(queryentity,NumberConstants.NUMERAL_ZORE,NumberConstants.NUMERAL_10001);
		if(CollectionUtils.isNotEmpty(userOrgRoleEntitys)){
			for(UserOrgRoleEntity userOrgRoleEntity : userOrgRoleEntitys){
				Date now = new Date();
				userOrgRoleEntity.setModifyDate(now);
				userOrgRoleEntity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
				userOrgRoleEntity.setVersion(now.getTime());
				//在无效表中新增，在有效表中物理删除
				userOrgRoleDao.addUnactiveUserOrgRoleBySelective(userOrgRoleEntity);
			}
		}else{
				userOrgRoleDao.addUnactiveUserOrgRoleBySelective(entity);
		}
		entity = userOrgRoleDao.deleteUserOrgRole(entity);
		// 强制刷新用户缓存
		//this.refreshUserCache(entity);
		return entity;
	}
	/**
	 * 根据用户编码USER_CODE，部门编码ORG_CODE，角色编码ORG_CODE数组 批量作废数据
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-21 下午9:8:10
	 * *       1	进入角色主界面。		
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
	 */
	@Override
	@Transactional
	public UserOrgRoleEntity deleteUserOrgRoleMore(UserOrgRoleEntity entity,
			String[] roleCodes) {
		if (ArrayUtils.isEmpty(roleCodes)) {
			return null;
		}
		if (StringUtils.isBlank(entity.getEmpCode())
				|| StringUtils.isBlank(entity.getOrgUnifiedCode())) {
			String msg = "根据用户编码USER_CODE，部门编码ORG_CODE 作废数据,empCode,OrgUnifiedCode不能为空";
			throw new UserOrgRoleException(msg, msg);
		}
		
//		//获得当前登录人的工号
//		String empcode =FossUserContext.getCurrentUser().getUserName();
		if(StringUtils.isBlank(entity.getModifyUser())){
			entity.setModifyUser(null);
		}
		if(StringUtils.isNotBlank(entity.getCreateUser())){
			entity.setCreateUser(null);
		}
		List<UserOrgRoleEntity> userOrgRoleEntitys = 
				queryUserOrgRoleListBySelectiveCondition(entity,NumberConstants.NUMERAL_ZORE,NumberConstants.NUMERAL_10001);
		
		//在无效表中新增，在有效表中物理删除
		List<UserOrgRoleEntity> entitys = new ArrayList<UserOrgRoleEntity>();
		for(UserOrgRoleEntity userOrgRoleEntity:userOrgRoleEntitys){
			for(String roleCode:roleCodes){
				if(StringUtils.equals(userOrgRoleEntity.getRoleCode(), roleCode)){
					Date now = new Date();
					userOrgRoleEntity.setModifyDate(now);
					userOrgRoleEntity.setModifyUser(ComnConst.EXTERNAL_SYSTEM_UUMS_USER_ACCOUNT);
					userOrgRoleEntity.setVersion(now.getTime());
					int result = userOrgRoleDao.addUnactiveUserOrgRoleBySelective(userOrgRoleEntity);
					
					if(0 < result){
						userOrgRoleEntity.setActive(FossConstants.INACTIVE);
						entitys.add(userOrgRoleEntity);
					}
				}
			}
		}
		//  把用户组织角色信息同步到其他
		if(!entitys.isEmpty()){
			syncResourceToOther(entitys);
		}
		
		entity = userOrgRoleDao.deleteUserOrgRoleMore(entity, roleCodes);

		entity.setRoleCode(roleCodes[NumberConstants.ZERO]);
		// 强制刷新用户缓存
		//this.refreshUserCache(entity);
		return entity;
	}
	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-19 上午11:11:15
	 * *       1	进入角色主界面。		
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
	@Override
	public List<UserOrgRoleEntity> queryUserOrgRoleExactByEntity(
			UserOrgRoleEntity entity, int start, int limit) {
		return userOrgRoleDao.queryUserOrgRoleExactByEntity(entity, start,
				limit);
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-10-19 上午11:09:53
	 */
	@Override
	public long queryUserOrgRoleExactByEntityCount(UserOrgRoleEntity entity) {
		return userOrgRoleDao.queryUserOrgRoleExactByEntityCount(entity);
	}

	/**
	 * 需求更改后使用的查询方式
	 */

	/**
	 * 模糊查询 查询用户部门角色的列表信息
	 * 
	 * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
	 * 
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 *  1	进入角色主界面。	
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
	*		1.6.2	查询角色操作步骤
	*
	*		序号	基本步骤	相关数据	补充步骤
	*
	*		1	进入角色管理主界面。		
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
	@Override
	public List<UserOrgRoleDto> queryUserOrgRoleDto(
			UserOrgRoleEntity userOrgRole, EmployeeEntity employee,
			RoleEntity role, int start, int limit) {
		List<UserOrgRoleDto> dtos = userOrgRoleDao.queryUserOrgRoleDto(
				userOrgRole, employee, role, start, limit);
		dtos = this.attachRole(dtos);
		return dtos;
	}
	/**
	 * 模糊查询-查询总条数，用于分页 查询用户部门角色的列表信息
	 * 
	 * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
	 * 
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.
	 * dao.IValuablesDao#queryValuablesExactByEntityCount
	 * (com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
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
	 **/
	@Override
	public long queryUserOrgRoleDtoCount(UserOrgRoleEntity userOrgRole,
			EmployeeEntity employee, RoleEntity role) {
		return userOrgRoleDao.queryUserOrgRoleDtoCount(userOrgRole, employee,
				role);
	}

	/**
	 * 当操作部门，角色名称为空时的查询方式
	 */

	/**
	 * 模糊查询 查询用户部门角色的列表信息
	 * 
	 * 根据employee工号，employee人员姓名 模糊 查询,
	 * 
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 *  1	进入角色主界面。		
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
			SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
			SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
			SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
			SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	 */
	@Override
	public List<UserOrgRoleDto> queryUserOrgRoleDtoByEmployee(
			EmployeeEntity employee, int start, int limit) {
		EmployeeEntity queryEmployee = employee == null ? new EmployeeEntity()
				: employee;

		List<UserOrgRoleDto> list= userOrgRoleDao.queryUserOrgRoleDtoByEmployee(queryEmployee,
				start, limit);
		return list;
	}
	/**
	 * 模糊查询-查询总条数，用于分页 查询用户部门角色的列表信息
	 * 
	 * 根据employee工号，employee人员姓名 模糊 查询,
	 * 
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 *  1	进入角色主界面。		
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
			SR-1	如果手动选择权限树中的一个节点,则该节点以上的所有父节点都自动选中.
			SR-2	如果手动取消选择权限树上的一个节点,上级结点的是否选中的状态不变.
			SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，并提示用户此权限与已经添加的XXX权限互斥。
			SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	 */
	@Override
	public long queryUserOrgRoleDtoByEmployeeCount(EmployeeEntity employee) {
		EmployeeEntity queryEmployee = employee == null ? new EmployeeEntity()
				: employee;
		return userOrgRoleDao.queryUserOrgRoleDtoByEmployeeCount(queryEmployee);
	}
	/**
	 * 下面是一些工具方法
	 */

	/**
	 * 给用户部门角色加上“角色列表”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-26 下午4:35:13
	 */
	@Override
	public List<UserOrgRoleDto> attachRole(List<UserOrgRoleDto> dtos) {
		if (CollectionUtils.isEmpty(dtos)) {
			return dtos;
		}
		for (UserOrgRoleDto dto : dtos) {
			this.attachRole(dto);
		}
		return dtos;
	}
	/**
	 * 将角色信息添加到“用户部门角色”的角色列表中
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午7:43:53
	 */
	@Override
	public UserOrgRoleDto attachRole(UserOrgRoleDto dto) {
		if (dto == null) {
			return dto;
		}
		String empCode = dto.getEmpCode();
		String operateOrgUnifiedCode = dto.getOperateOrgUnifiedCode();
		if (StringUtils.isEmpty(empCode)
				|| StringUtils.isEmpty(operateOrgUnifiedCode)) {
			return dto;
		}

		// 查出对应的用户部门角色：
		UserOrgRoleEntity entityCondition = new UserOrgRoleEntity();
		entityCondition.setEmpCode(empCode);
		entityCondition.setOrgUnifiedCode(operateOrgUnifiedCode);
		List<UserOrgRoleEntity> userOrgRoleList = this
				.queryUserOrgRoleExactByEntity(entityCondition,
						NumberConstants.ZERO, Integer.MAX_VALUE);
		int leng = userOrgRoleList.size();
		String[] roleCodes = new String[leng];
		for (int i = 0; i < leng; i++) {
			roleCodes[i] = userOrgRoleList.get(i).getRoleCode();
		}
		// 根据角色编码数组，批量查询角色信息
		List<RoleEntity> roleList = roleService.queryRoleBatchByCodeNoSetResource(roleCodes);
		dto.setRoleList(roleList);
		return dto;
	}
	/**
	 * 添加“用户部门角色”时，根据部门标杆编码查出部门，将部门编码添加进去，
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午7:43:53
	 */
	@Override
	public UserOrgRoleEntity attachOrgWhenUpdate(UserOrgRoleEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getOrgUnifiedCode())) {
			return entity;
		}

		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(entity
						.getOrgUnifiedCode());
		if (org != null) {
			entity.setOrgCode(org.getCode());
		}
		return entity;
	}

	/**
	 * 添加“用户部门角色”时，根据部门标杆编码查出部门，将部门编码添加进去，
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-22 下午7:43:53
	 */
	@Override
	public List<UserOrgRoleEntity> attachOrgWhenUpdate(
			List<UserOrgRoleEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}
		for (UserOrgRoleEntity entity : entitys) {
			this.attachOrgWhenUpdate(entity);
		}
		return entitys;
	}
	/**
	 * 下面是工具方法
	 */

	/**
	 * 强制更新用户，刷新用户的“用户部门角色”缓存
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 下午6:08:33
	 * @param code
	 *            人员工号
	 */
	public void refreshUserCache(UserOrgRoleEntity entity) {
		if (entity != null) {
			UserEntity user = userService.queryUserByEmpCode(entity.getEmpCode());
			@SuppressWarnings("unchecked")
			ICache<String, Object> refreshableCache = CacheManager
					.getInstance().getCache(FossTTLCache.USER_CACHE_UUID);
			@SuppressWarnings("unchecked")
			ICache<String, Object> userOrgRoleResCache = CacheManager
					.getInstance().getCache(FossTTLCache.USER_ORG_ROLE_RES_CACHE_UUID);
			if (refreshableCache != null) {
				// 作废此记录
				refreshableCache.invalid(user.getId());
				userOrgRoleResCache.invalid(entity.getEmpCode()+"#"+entity.getOrgCode());
			}
		}
	} 
	/**
	 * 根据部门编码获取该部门下所有员工编码
	 * @author 101911-foss-zhouChunlai
	 * @date 2013-3-15 下午5:38:56
	 */
	@Override
	public List<UserOrgRoleEntity> queryUserOrgRoleListByUnifieldCode(String unifieldCode) {
		//校验标杆编码是否输入
		if (StringUtils.isEmpty(unifieldCode)) {
			return null;
		}
		return userOrgRoleDao.queryUserOrgRoleListByUnifieldCode(unifieldCode);
	}
	/**
	 * @param userOrgRoleDao
	 *            the userOrgRoleDao to set
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	private IRoleService roleService;
	private IUserService userService;
	private IUserDeptDataService userDeptDataService;

	
	/**
	 * @set
	 * @param userDeptDataService
	 */
	public void setUserDeptDataService(IUserDeptDataService userDeptDataService) {
		/*
		 *@set
		 *@this.userDeptDataService = userDeptDataService
		 */
		this.userDeptDataService = userDeptDataService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	public void setUserOrgRoleDao(IUserOrgRoleDao userOrgRoleDao) {
		this.userOrgRoleDao = userOrgRoleDao;
	}
	
	/** 
	 * 更新 用户部门角色
	 * @author foss-qiaolifeng
	 * @date 2013-7-12 下午2:05:47
	 * @param userOrgRoleEntity
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService#updateUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
	 */
	@Override
	@Transactional
	public void updateUserOrgRole(UserOrgRoleEntity entity) {

		String empCodeCondition = entity.getEmpCode();
	    String orgUnifiedCodeCondition = entity.getOrgUnifiedCode();
	    String roleCodeCondition = entity.getRoleCode();
	    
	    // 先根据用户工号，部门标杆编码查询角色
	    UserOrgRoleEntity entityCondition = new UserOrgRoleEntity();
	    entityCondition.setEmpCode(empCodeCondition);
	    entityCondition.setOrgUnifiedCode(orgUnifiedCodeCondition);	    
	    List<UserOrgRoleEntity> userOrgRoleList = this.queryUserOrgRoleExactByEntity(entityCondition, 0,Integer.MAX_VALUE);
	    
	    // 将查询到的角色编码放到set中
	    Set<String> deleteRoleCodeSet= new HashSet<String>();
	    for(UserOrgRoleEntity userOrgRole : userOrgRoleList){
	    	deleteRoleCodeSet.add(userOrgRole.getRoleCode());
	    }
	    
	    // 将请求的用户部门的角色放到set中
	    Set<String> addRoleCodeSet = new HashSet<String>();
	    String[] roleCodeArray = roleCodeCondition.split(
		    SymbolConstants.EN_COMMA);
	    for(String roleCode : roleCodeArray){
	    	addRoleCodeSet.add(roleCode);
	    }
	    
	    // 去掉不变的角色
	    for(String roleCode : roleCodeArray){
		if(deleteRoleCodeSet.contains(roleCode)){
		    deleteRoleCodeSet.remove(roleCode);
		    addRoleCodeSet.remove(roleCode);
		}
	    }

	    /**
	     *  将要删除的角色拼装成字符串数组deleteRoleCodeStrs
	     */
	    int index = 0;
	    String[] deleteRoleCodeStrs=new String[deleteRoleCodeSet.size()];
	    for(String roleCode :deleteRoleCodeSet){
	    	deleteRoleCodeStrs[index]=roleCode;
	    	++index;
	    }
	    UserOrgRoleEntity userOrgRoleEntity = new UserOrgRoleEntity();
	    userOrgRoleEntity.setEmpCode(empCodeCondition);
	    userOrgRoleEntity.setOrgUnifiedCode(orgUnifiedCodeCondition);
	    // 设置操作用户的用户编码
	    String operUserCode = GainEmployee.getOperUserCode();
	    userOrgRoleEntity.setCreateUser(operUserCode);
	    userOrgRoleEntity.setModifyUser(operUserCode);
	    this.deleteUserOrgRoleMore(userOrgRoleEntity, deleteRoleCodeStrs);
	   
	    /**
	     * 新增用户部门角色
	     */
	    List<UserOrgRoleEntity> entitys = new ArrayList<UserOrgRoleEntity>();
	    for(String roleCode : addRoleCodeSet){
	    	userOrgRoleEntity.setRoleCode(roleCode);
	    	entity = this.addUserOrgRoleByEntity(userOrgRoleEntity, entitys);
	    }
	    //  把用户组织角色信息同步到其他
	    if(!CollectionUtils.isEmpty(entitys)){
			syncResourceToOther(entitys);
    	}
	    /*
	     * 刷新用户部门数据权限
	     */
	    if(deleteRoleCodeStrs.length>0||addRoleCodeSet.size()>0){
	    	
			refreshUserCache(entity);
	    	userDeptDataService.refreshUserDeptData(empCodeCondition);	
	    }
		
	}
	
	/** 
	 * UUMS同步用户时，采用修改方式，新增用户部门角色信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-19 下午5:06:34
	 * @param userOrgRole
	 * @param createUser
	 * @param ignoreNull
	 * @return
	 * @throws UserOrgRoleException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserOrgRoleService#addUserOrgRoleForSycUumsUpdate(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity, java.lang.String, boolean)
	 */
	@Override
	public int addUserOrgRoleForSycUumsUpdate(UserOrgRoleEntity userOrgRole,
			String createUser, boolean ignoreNull) throws UserOrgRoleException {
        //判断用户组织角色信息是否为空
		if (null == userOrgRole) {
			throw new UserOrgRoleException("", "用户组织角色信息为空");
		}
		//判断员工号是否为空
		if (StringUtils.isBlank(userOrgRole.getEmpCode())) {
			throw new UserOrgRoleException("", "用户工号为空");
		}
		//判断组织为空
		if (StringUtils.isBlank(userOrgRole.getOrgUnifiedCode())) {
			throw new UserOrgRoleException("", "用户组织为空");
		}
		//判断角色信息是否为空
		if (StringUtils.isBlank(userOrgRole.getRoleCode())) {
			throw new UserOrgRoleException("", "用户角色信息为空");
		}
		//判断创建人是否为空
		if (StringUtils.isBlank(createUser)) {
			throw new UserOrgRoleException("", "创建人为空");
		}
		// 验证数据
		UserOrgRoleEntity userOrgRoleParameter = new UserOrgRoleEntity();
		//设置员工编码
		userOrgRoleParameter.setEmpCode(userOrgRole.getEmpCode());
		//设置部门标杆编码
		userOrgRoleParameter.setOrgUnifiedCode(userOrgRole.getOrgUnifiedCode());
		//设置角色编码
		userOrgRoleParameter.setRoleCode(userOrgRole.getRoleCode());
		//查询角色信息
		UserOrgRoleEntity tempUserOrgRole = userOrgRoleDao
				.queryUserOrgRoleBySelective(userOrgRoleParameter);
        //判断角色信息是否为空
		if (null == tempUserOrgRole) {
			
			this.attachOrgWhenUpdate(userOrgRole);

			userOrgRole.setCreateUser(createUser);
			if (ignoreNull) {
				userOrgRoleDao.addUserOrgRoleBySelective(userOrgRole);
			} else {
				userOrgRoleDao.addUserOrgRole(userOrgRole);
			}
		}
		return FossConstants.SUCCESS;
	}
}
