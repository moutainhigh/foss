/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
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
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/UserOrgRoleDao.java
 * 
 * FILE NAME        	: UserOrgRoleDao.java
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
 *      ***相关SUC
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
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.UserOrgRoleDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“用户组织角色信息”的数据库对应数据访问DAO接口实现类：SUC-41 
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午7:12:04</p>
 * 
 * @author 100847-foss-GaoPeng
 * 
 * @date 2012-11-7 下午7:12:04
 * 
 * @since
 * 
 * @version
 * 
 */
public class UserOrgRoleDao extends SqlSessionDaoSupport implements
	IUserOrgRoleDao {
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".userorgrole";
    /**
     * <p>新增一个“用户组织角色信息”实体入库（忽略实体中是否存在空值）</p> 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-10-12 下午2:30:53
     * 
     * 
     * @param userOrgRole “用户组织角色信息”实体
     * 
     * 
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#addUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public int addUserOrgRole(UserOrgRoleEntity userOrgRole) {
	userOrgRole.setId(UUIDUtils.getUUID());
	userOrgRole.setCreateDate(new Date());
	userOrgRole.setActive(FossConstants.ACTIVE);
	userOrgRole.setModifyUser(userOrgRole.getCreateUser());
	userOrgRole.setModifyDate(new Date(NumberConstants.ENDTIME));
	userOrgRole.setVersion(userOrgRole.getCreateDate().getTime());
	userOrgRole.setVirtualCode(UUIDUtils.getUUID());
	return getSqlSession().insert(NAMESPACE + ".addUserOrgRole", userOrgRole);
    }
    /**
     * <p>新增一个“用户组织角色信息”实体入库 （只选择实体中非空值）</p> 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 下午2:30:51
     * 
     * @param userOrgRole “用户组织角色信息”实体
     * 
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#addUserOrgRoleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public int addUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) {
	userOrgRole.setId(UUIDUtils.getUUID());
	userOrgRole.setCreateDate(new Date());
	userOrgRole.setActive(FossConstants.ACTIVE);
	userOrgRole.setModifyUser(userOrgRole.getCreateUser());
	userOrgRole.setModifyDate(new Date(NumberConstants.ENDTIME));
	userOrgRole.setVersion(userOrgRole.getCreateDate().getTime());
	userOrgRole.setVirtualCode(UUIDUtils.getUUID());
	return getSqlSession().insert(NAMESPACE + ".addUserOrgRoleBySelective", userOrgRole);
    }

    /**
     * <p>根据“用户组织角色信息”记录唯一标识删除（物理删除）一条“用户组织角色信息”记录</p> 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 下午2:30:42
     * 
     * @param id 记录唯一标识
     * 
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#deleteUserOrgRole(java.lang.String)
     */
    @Override
    public int deleteUserOrgRole(String id) {
	return getSqlSession().delete(NAMESPACE + ".deleteUserOrgRole", id);
    }
    /**
     * <p>修改一个“用户组织角色信息”实体入库 （只选择实体中非空值）</p> 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 下午2:30:47
     * 
     * @param userOrgRole “用户组织角色信息”实体
     * 
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#updateUserOrgRoleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public int updateUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) {
	Date now = new Date();
	userOrgRole.setModifyDate(now);
	userOrgRole.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + ".updateUserOrgRoleBySelective", userOrgRole);
    }
    /**
     * <p>新增一个“用户组织角色信息”实体入无效库 （只选择实体中非空值）</p> 
     * 
     * @author 130346-foss-lifanghong
     * 
     * @date 2014-3-27 下午2:30:47
     * 
     * @param userOrgRole “用户组织角色信息”实体
     * 
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#updateUserOrgRoleBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public int addUnactiveUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) {
    userOrgRole.setId(UUIDUtils.getUUID());
	Date now = new Date();
	userOrgRole.setModifyDate(now);
	userOrgRole.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + ".addUnactiveUserOrgRoleBySelective", userOrgRole);
    }
    /**
     * <p>修改一个“用户组织角色信息”实体入库（忽略实体中是否存在空值）</p>
     *  
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 下午2:30:45
     * 
     * @param userOrgRole “用户组织角色信息”实体
     * 
     *
     * @return 影响记录数
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#updateUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public int updateUserOrgRole(UserOrgRoleEntity userOrgRole) {
	Date now = new Date();
	userOrgRole.setModifyDate(now);
	userOrgRole.setVersion(now.getTime());
	return getSqlSession().update(NAMESPACE + ".updateUserOrgRole", userOrgRole);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”唯一实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 下午2:30:49
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return queryUserOrgRoleBySelective “用户组织角色信息”实体
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IUserOrgRoleDao#queryUserOrgRoleBySelective
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
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
    @SuppressWarnings("unchecked")
    public UserOrgRoleEntity queryUserOrgRoleBySelective(UserOrgRoleEntity userOrgRole) {
	if(StringUtils.isBlank(userOrgRole.getId())){
	    //强制设置为只查询“启用”的记录
	    userOrgRole.setActive(FossConstants.ACTIVE);
	}

	RowBounds rowBounds = new RowBounds(NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
	List<UserOrgRoleEntity> userOrgRoleList = getSqlSession().selectList(NAMESPACE + ".queryUserOrgRoleListBySelective", userOrgRole, rowBounds);
	if(CollectionUtils.isEmpty(userOrgRoleList)){
	    return null;
	}
        return userOrgRoleList.get(NumberConstants.NUMERAL_ZORE);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @return 符合条件的“用户组织角色信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IUserOrgRoleDao#queryUserOrgRoleListBySelective
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
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
    @SuppressWarnings("unchecked")
    public List<UserOrgRoleEntity> queryUserOrgRoleListBySelective(UserOrgRoleEntity userOrgRole) {
	//强制设置为只查询“启用”的记录
	userOrgRole.setActive(FossConstants.ACTIVE);
        return getSqlSession().selectList(NAMESPACE + ".queryUserOrgRoleListBySelective", userOrgRole);
    }
    /**
     * <p>根据条件（分页模糊）有选择的检索出符合条件的“用户组织角色信息”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-20 上午10:51:05
     * @param userOrgRole 以“用户组织角色信息”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“用户组织角色信息”实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IUserOrgRoleDao#queryUserOrgRoleListBySelectiveCondition
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity, int, int)
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
    @SuppressWarnings("unchecked")
    public List<UserOrgRoleEntity> queryUserOrgRoleListBySelectiveCondition(
	    UserOrgRoleEntity userOrgRole, int offset, int limit) {
	//强制设置为只查询“启用”的记录
	userOrgRole.setActive(FossConstants.ACTIVE);
	return getSqlSession().selectList(NAMESPACE + ".queryUserOrgRoleListBySelectiveCondition", userOrgRole, new RowBounds(offset, limit));
    }
    /**
     * <p>提供给"GUI"下载"用户组织角色信息"的数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-7 下午8:06:28
     * @param userOrgRole “用户组织角色信息”实体参数
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IUserOrgRoleDao#queryUserOrgRoleListForDownload
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
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
    @SuppressWarnings("unchecked")
    public List<UserOrgRoleEntity> queryUserOrgRoleListForDownload(
            UserOrgRoleEntity userOrgRole) {
	return getSqlSession().selectList(NAMESPACE + ".queryUserOrgRoleListForDownload", userOrgRole);
    }
    /**
     * 下面是 087584-foss-lijun 添加
     */
    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午11:1:51
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#addUserOrgRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     */
    @Override
    public UserOrgRoleEntity addUserOrgRoleByEntity(UserOrgRoleEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if(StringUtils.isBlank(entity.getVirtualCode())){
	    entity.setVirtualCode(entity.getId());
	}
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersion(now.getTime());
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + ".addUserOrgRoleByEntity", entity);
	return result > 0 ? entity : null;
    }
    /**
     * 根据用户编码USER_CODE 作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:8:10
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#deleteUserOrgRole(UserOrgRoleEntity)
     */
    @Override
    public UserOrgRoleEntity deleteUserOrgRole(UserOrgRoleEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getEmpCode())) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setVersion(now.getTime());
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + ".deleteUserOrgRoleByEmpCode", map);
	return result > 0 ? entity : null;
    }
    /**
     * 根据用户编码USER_CODE，部门编码ORG_CODE 作废数据
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-21 下午9:8:10
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserOrgRoleDao#deleteUserOrgRole(UserOrgRoleEntity)
     */
    @Override
    public UserOrgRoleEntity deleteUserOrgRoleMore(UserOrgRoleEntity entity, String[] roleCodes) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getEmpCode()) 
		|| StringUtils.isBlank(entity.getOrgUnifiedCode())) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setVersion(now.getTime());
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	map.put("codes", roleCodes);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + ".deleteUserOrgRoleMore", map);
	return result > 0 ? entity : null;
    }
    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
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
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrgRoleEntity> queryUserOrgRoleExactByEntity(
	    UserOrgRoleEntity entity, int start, int limit) {
	UserOrgRoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new UserOrgRoleEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return (List<UserOrgRoleEntity> )getSqlSession()
		.selectList(NAMESPACE + ".queryUserOrgRoleExactByEntity",
			queryEntity,
			rowBounds);
    }
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.
     * dao.IValuablesDao#queryValuablesExactByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
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
    public long queryUserOrgRoleExactByEntityCount(UserOrgRoleEntity entity) {
	UserOrgRoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new UserOrgRoleEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + ".queryUserOrgRoleExactByEntityCount",
		queryEntity);
    }
    /**
     * 下面的查询是为了显示到前台页面
     */
    /**
     * 需求更改后使用的查询方式
     */ 
    /**
     * 模糊查询
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
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
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrgRoleDto> queryUserOrgRoleDto(
	    UserOrgRoleEntity userOrgRole, EmployeeEntity employee,
	    RoleEntity role, int start, int limit) {
	UserOrgRoleEntity queryUserOrgRole = userOrgRole ==null?new UserOrgRoleEntity():userOrgRole;
	EmployeeEntity queryEmployee = employee == null?new EmployeeEntity():employee;
	RoleEntity queryRole =role == null?new RoleEntity():role; 
	
	queryUserOrgRole.setActive(FossConstants.ACTIVE);
	queryEmployee.setActive(FossConstants.ACTIVE);
	queryRole.setActive(FossConstants.ACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("userOrgRole", userOrgRole);
	map.put("employee", employee);
	map.put("role", role);
	// 只查询active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return (List<UserOrgRoleDto>)getSqlSession().selectList(
		NAMESPACE + ".queryUserOrgRoleDto",
		map,
		rowBounds);
    }  
    /**
     * 模糊查询-查询总条数，用于分页
     * 查询用户部门角色的列表信息
     * 
     * 根据用户部门角色的userOrgRole用户工号，employee人员姓名，userOrgRole部门标杆编码（精确），role角色名称 查询
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IValuablesDao#queryValuablesExactByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
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
    public long queryUserOrgRoleDtoCount(UserOrgRoleEntity userOrgRole, EmployeeEntity employee, RoleEntity role) {
	UserOrgRoleEntity queryUserOrgRole = userOrgRole ==null?new UserOrgRoleEntity():userOrgRole;
	EmployeeEntity queryEmployee = employee == null?new EmployeeEntity():employee;
	RoleEntity queryRole =role == null?new RoleEntity():role; 
	queryUserOrgRole.setActive(FossConstants.ACTIVE);
	queryEmployee.setActive(FossConstants.ACTIVE);
	queryRole.setActive(FossConstants.ACTIVE);
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("userOrgRole", userOrgRole);
	map.put("employee", employee);
	map.put("role", role);
	// 只查询active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + ".queryUserOrgRoleDtoCount",
		map);
    }   
    /**
     * 当操作部门，角色名称为空时的查询方式
     */   
    /**
     * 模糊查询
     * 查询用户部门角色的列表信息
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
    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrgRoleDto> queryUserOrgRoleDtoByEmployee(
	    EmployeeEntity employee, int start, int limit) {
	EmployeeEntity queryEmployee = employee == null?new EmployeeEntity():employee;
	
	queryEmployee.setActive(FossConstants.ACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("employee", employee);
	// 只查询active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	List<UserOrgRoleDto> list= (List<UserOrgRoleDto>)getSqlSession().selectList(
		NAMESPACE + ".queryUserOrgRoleDtoByEmployee",
		map,
		rowBounds);
	return list;
    } 
    /**
     * 模糊查询-查询总条数，用于分页
     * 查询用户部门角色的列表信息
     * 
     * 根据employee工号，employee人员姓名 模糊 查询,
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.
     * api.server.dao.IValuablesDao#queryValuablesExactByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.ValuablesEntity)
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
	**		5a	点击取消按钮，退出当前界面，返回主界面。		
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
	*		SR-3	在添加权限时，如果当前添加的权限与已经添加的权限有互斥关系，则不允许添加，
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
     */
    @Override
    public long queryUserOrgRoleDtoByEmployeeCount(EmployeeEntity employee) {
	EmployeeEntity queryEmployee = employee == null?new EmployeeEntity():employee;
	queryEmployee.setActive(FossConstants.ACTIVE);
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("employee", employee);
	// 只查询active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + ".queryUserOrgRoleDtoByEmployeeCount",
		map);
    }    
    /** 
     * 根据部门编码获取该部门下所有员工编码
     * @author 101911-foss-zhouChunlai
     * @date 2013-3-15 下午4:55:49
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IUserOrgRoleDao#queryUserOrgRoleListByUnifieldCode
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity)
     *  1	进入角色主界面。		
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
	**		分配权限异常操作步骤
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
	*并提示用户此权限与已经添加的XXX权限互斥。
	*
	*		SR-4	在“分配权限界面”，查询时，权限树定位到第一个符合条件的结点。	
	*
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<UserOrgRoleEntity> queryUserOrgRoleListByUnifieldCode(String unifieldCode) {
    	Map<String, Object> map=new HashMap<String, Object>();
    	//设置部门标杆编码
    	map.put("orgUnifiedCode", unifieldCode);
    	// 只查询active为有效的：
    	map.put("active", FossConstants.ACTIVE);
      return getSqlSession().selectList(NAMESPACE + ".queryUserOrgRoleListByUnifieldCode", map);
    }
}
