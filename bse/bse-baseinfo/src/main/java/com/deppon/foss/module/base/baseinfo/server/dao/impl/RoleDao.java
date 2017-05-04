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
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/RoleDao.java
 * 
 * FILE NAME        	: RoleDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 修订记录 
*		日期 	修订内容 	修订用户 	版本号 
*
*		2012-04-27 	新增	李俊	V0.1
*
*
*		2012-07-04	1. 修改前置条件； 
*
*
*		2. 查询条件增加“所在部门”，使用“放大镜”控件； 
*
*
*
*		3. “用户角色用户角色列表”中增加“启用日期”； 
*
*
*		4. “用户详情”去掉“更新时间”，“生效日期”和“失效日期”；	李俊	V0.2
*
*
*		2012-07-04	关联系统用例BUC_FOSS_5.10.20_065排班(短途班车)。	李俊	V0.3
*
*
*
*		2012-07-12	1.	查询条件中增加“角色”；
*
*		2.	列表中增加“部门”，“角色”和“是否默认”列。
*
*		3.	添加“添加角色”，“作废角色”和“编辑角色”的按钮入口。	李俊	V0.4
*
*		2012-12-04		1，将修改用户部门角色的流程改为从查询界面直接找出用户部门，点击修改按钮后，直接进行用户部门角色的修改界面	李俊	V1.1
*		1.	SUC-41-修改_查询用户角色用户部门角色
*
**		1.1	相关业务用例
*
*		BUC_FOSS_5.10.20_065排班(短途班车)。
*
*		
*		1.2	用例描述
*
*		该用例可对用户角色用户部门角色进行修改，查询。
*
*		
*		1.3	用例条件
*
*
*		条件类型	描述	引用系统用例
*
*		前置条件	1.	用户信息完备。
*
*		2.	角色信息完备。
*		3.	UUMS已将用户信息同步至FOSS。	SUC-226查询用户
*
*		SUC-646同步角色信息接口
*		后置条件		
*
*		1.4	操作用户角色用户部门角色
*
*		操作用户	描述
*		系统管理员	系统管理员对用户角色用户部门角色进行修改，查询操作。
*
*		1.5	界面要求
*
*		1.5.1	表现方式
*
*		Web页面
*		1.5.2	界面原型-主界面
*
*		  
***		图一：主界面
*		1.5.3	界面描述-主界面
*
*		1.	功能按钮区域
*		1)	重置按钮：点击重置按钮，清空查询条件。
*
*		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
*
*		3)	修改按钮：点击修改按钮，进入用户角色用户部门角色详情界面，参见【图二：用户角色用户部门角色详情界面】。
*
*		4)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
*
*		5)	分页按钮：支持分页功能。
*
*		2.	列表显示区域
*
*		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。参见数据元素【用户列表信息】。
*
*		3.	字段输入区域
*		1)	参见数据元素【用户查询条件】。
*
*		1.5.4	界面原型-用户角色详情界面
*
*		 
*
*		图二：用户角色详情界面
*
*		1.5.5	界面描述-用户角色详情界面
*
*		1.	功能按钮区域
*
*		1)	返回按钮：点击返回按钮，退出当前界面，返回上一级界面。
*
*		2)	新增按钮：点击新增按钮，进入用户角色新增界面。参见【图三：用户角色新增和修改界面】。
*
*		3)	修改按钮：点击一条记录的修改按钮，进入用户角色修改界面。参见【图三：用户角色新增和修改界面】。
*
*		4)	作废按钮：点击各行的作废按钮，作废此行记录；需要弹出确认提示框。
*
*		2.	列表显示区域
*		1)	列表区域显示这个用户在不同的部门所具有的角色。参见数据元素【用户角色列表】。
*
*		3.	信息显示区域
*
*		1)	参见数据元素【用户信息】。
*
*		
*		1.5.61.5.4	界面原型-用户角色用户部门角色新增和修改界面
*
*		  
		图三：用户角色用户部门角色新增和修改界面
		
*		1.5.71.5.5	界面描述-用户角色用户部门角色新增和修改界面
*
*		1.	功能按钮区域
*
*		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
*
*		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
*
*		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
*
*		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
*
*		5)	保存按钮：点击保存按钮，右框中的数据被保存为用户的角色。
*
*		6)	重置按钮：点击重置按钮，恢复用户的可分配角色，已分配角色。
*
*		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
*
*		2.	信息显示区域
*		1)	用户：用户的工号和姓名。
*
*		2)	组织：用户所属的部门。
*
*		3)	可分配角色：用户未被分配的角色。
*
*		4)	已分配角色：用户已被分配的角色。
*
*		5)	角色权限列表：已分配角色所具有的所有权限的列表。
*
*		
*		1.6	操作步骤
*		1.6.1	查询用户操作步骤
*
*		序号	基本步骤	相关数据	补充步骤
*
*		1	进入用户角色用户部门角色主界面。		
*
*
*
**		2	输入查询条件。点击查询按钮。	【用户查询条件】
*
*		【用户列表信息】	系统返回查询结果。
*		
*		3	点击分页按钮，查看各页的查询结果。		
*		4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
*		
*		5	点击返回按钮。		返回主界面。
*		
*		
***		1.6.2	用户角色用户部门角色修改操作步骤
*		
*		序号	基本步骤	相关数据	补充步骤
*		
*		
*		1	进入用户角色用户部门角色主界面。		
*		2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
*		
**		3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
*		
*		4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
*		
*		5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
***		
*		用户角色用户部门角色修改异常操作步骤
*		
**		序号	扩展事件	相关数据	备注
*		5a	点击取消按钮，退出当前界面，返回主界面。		
*		
*		5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
*		
*		1.7	业务规则
*		序号	描述
		SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
*		SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
*		
*		SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
*		
*		1.8	数据元素
*		1.8.1	用户信息
*		
**		字段名称 	说明 	输入限制	长度	是否必填	备注
*		
*		工号	用户的工号	N/A	N/A	N/A	
*		
*		用户名	用户登录帐号，默认为工号	N/A	N/A	N/A	
*		
*		姓名	用户的姓名	N/A	N/A	N/A	
*		
*		手机号	用户的手机号	N/A	N/A	N/A	
*		
**		1.8.2	用户查询条件
*		字段名称 	说明 	输入限制	长度	是否必填	备注
*		
*		工号	用户的工号	文本	50	否	
*		
*		姓名	用户的姓名	文本	50	否	
*		
*		所在操作部门	用户所在的的操作部门	选择框	50	否	
*		
*		角色	用户的角色	文本	50	否	
*
*
*
*
*
*
*		
*		1.8.3	用户列表信息
*		
*		字段名称 	说明 	输入限制	长度	是否必填	备注
*		
*		工号	用户的工号	N/A	N/A	N/A	
*		
*
*
*
*
*		姓名	用户姓名	N/A	N/A	N/A	
*		部门	用户的当前所属部门	N/A	N/A	N/A	
*		
*		手机号	用户的手机号	N/A	N/A	N/A	
*		
*		所在操作部门	用户所属操作的部门	N/A	N/A	N/A	一个用户可能属于多个部门，在不同的部门有不同的角色。
*		
*		是否默认	所在部门是不是用户的默认所属部门	N/A	N/A	N/A
*			
*		角色	用户在各个部门的角色	N/A	N/A	N/A	
*		
*		1.8.4	用户角色用户部门角色列表
*		
*		字段名称 	说明 	输入限制	长度	是否必填	备注
*		
*		所在部门	用户所在的部门	N/A	N/A	N/A	
*		
*		启用日期	启用日期	N/A	N/A	N/A	
*		
*		是否默认	是否是用户的默认的部门	N/A	N/A	N/A	
*		
*		角色	用户在这个部门所具有的角色的名称，多个角色用英文逗号（,）分隔。	N/A	N/A	N/A	
*		
*		
*		1.9	非功能性需求
*		使用量	
*		
*		2012年全网估计用户数	
*		响应要求（如果与全系统要求 不一致的话）	
*		使用时间段	
*		高峰使用时间段	
*		
*		1.10	接口描述
*		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
*		用户信息接口	UUMS系统	将修改的用户信息同步到FOSS系统。
 *******************************************************************************/
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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserOrgRoleEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 角色数据访问 DAO
 * 
 * 2012-08-30 钟庭杰 新增
 */
@SuppressWarnings("unchecked")
public class RoleDao extends SqlSessionDaoSupport implements IRoleDao {

    /**
     * 
     * <p>提供缓存加载所有的角色对象与角色所对应的功能ID</p> 
     * 
     * @author ztjie
     * 
     * @date 2013-1-30 下午3:52:46
     * 
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#getRoleAndResIds()
     */
    @Override
    public List<RoleEntity> getRoleAndResIds() {
		List<RoleEntity> list = null;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("roleActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		list = getSqlSession().selectList(NAMESPACE + "getRoleAndResIds",map);
		return list;
    }
    /**
     * 
     * <p>缓存根据角色编码加载角色与功能Code</p> 
     * @author ztjie
     * 
     * @date 2013-1-30 下午3:45:58
     * 
     * @param roleCode
     * 
     * @return
     * @see
     */
    @Override
    public List<RoleEntity> getRoleAndResCodesByCode(String roleCode) {
		List<RoleEntity> list = null;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("roleCode", roleCode);
		map.put("roleActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		list = getSqlSession().selectList(NAMESPACE + "getRoleAndResCodesByCode",map);
		return list;
    }
    /**
     * 
     * <p>缓存根据更新版本加载角色与功能Code</p> 
     * @author ztjie
     * 
     * @date 2013-1-30 下午3:45:58
     * 
     * @param lastModyfyTime
     * 
     * @return
     * @see
     */
    @Override
    public List<RoleEntity> getByLastModifyRole(Date lastModyfyTime) {
		List<RoleEntity> list = null;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("versionNo", lastModyfyTime.getTime());
		map.put("roleActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		list = getSqlSession().selectList(NAMESPACE + "getByLastModifyRole",map);
		return list;
    }
    /**
     * 
     * <p>根据 CODE 批量精确查询角色与功能Code</p> 
     * @author ztjie
     * 
     * @date 2013-1-30 下午3:45:58
     * 
     * @param codes
     * 
     * @return
     * @see
     */
    @Override
    public List<RoleEntity> getRoleBatchByCode(String[] codes) {
		List<RoleEntity> list = null;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("roleActive", FossConstants.ACTIVE);
		map.put("roleResActive", FossConstants.ACTIVE);
		list = getSqlSession().selectList(NAMESPACE + "getRoleBatchByCode",map);
		return list;
    }
    /**
     * 查询最后更新时间
     * 
     * @return
     */
    @Override
    public Date getLastModifyTime() {
	long versionNo = (Long) getSqlSession().selectOne(NAMESPACE + "getLastModifyTime");
	return new Date(versionNo);
    }
    /**
     * 下面是基本的DAO操作
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".role.";
    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#addRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity)
     */
    @Override
    public RoleEntity addRole(RoleEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addRole", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#deleteRole(java.lang.String)
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public RoleEntity deleteRole(RoleEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteRole", map);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 通过CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#deleteRoleMore(java.lang.String[], java.lang.String)
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public RoleEntity deleteRoleMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	RoleEntity entity = new RoleEntity();

	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	entity.setModifyUser(deleteUser);
	entity.setActive(FossConstants.INACTIVE);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteRoleMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE标识更新
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-2 下午4:6:5
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#updateRole(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity)
     */
    @Override
    public RoleEntity updateRole(RoleEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return entity;
	}
	// 更新要先删除旧的数据：
	RoleEntity result = this.deleteRole(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// 版本号始终取当前时间
	entity.setVersionNo(System.currentTimeMillis());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());

	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addRole", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#queryRoleByCode(java.lang.String)
     */
    @Override
    public RoleEntity queryRoleByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	RoleEntity entity=new RoleEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	
	List<RoleEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryRoleByCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#queryRoleBatchBy(java.lang.String[])
     * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。

     */
    @Override
    public List<RoleEntity> queryRoleBatchByCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	return getSqlSession().selectList(
		NAMESPACE + "queryRoleBatchByCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#queryRoleExactByEntity
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity, int, int)
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public List<RoleEntity> queryRoleExactByEntity(
	    RoleEntity entity, int start, int limit) {
	RoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleEntity();
	}else{
	    queryEntity = entity;
	}
	
	
	
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryRoleExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IRoleDao#queryRoleExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity)
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public long queryRoleExactByEntityCount(RoleEntity entity) {
	RoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryRoleExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IRoleDao#queryRoleMore(java.lang.String[])
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public List<RoleEntity> queryRoleByEntity(
	    RoleEntity entity, int start, int limit) {
	RoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	
	queryEntity = this.transQueryCondition(queryEntity);
	
	return getSqlSession().selectList(NAMESPACE + "queryRoleByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午4:6:5
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IRoleDao#queryRoleByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.RoleEntity)
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public long queryRoleByEntityCount(RoleEntity entity) {
	RoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);

	queryEntity = this.transQueryCondition(queryEntity);
	
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryRoleByEntityCount", queryEntity);
    }

	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午8:49:5
     *  * 1	进入用户角色用户部门角色主界面。		
	 *	2	输入查询条件。点击查询按钮。	【用户查询条件】
	 *	【用户列表信息】	系统返回查询结果。
		*3	点击分页按钮，查看各页的查询结果。		
	*	4	找出要查看的用户双击。	【用户信息】，【用户角色用户部门角色列表】	进入用户详情界面。
	*	5	点击返回按钮。		返回主界面。
	*	
	*	1.6.2	用户角色用户部门角色修改操作步骤
	*	序号	基本步骤	相关数据	补充步骤
	*	1	进入用户角色用户部门角色主界面。		
	*	2	输入查询条件，点击查询按钮。	【用户查询条件】, 【用户列表信息】	系统返回查询结果。
	*	3	找出需要修改的用户角色用户部门角色，点击修改按钮。		进入用户角色用户部门角色修改界面。
	*	4	在用户角色用户部门角色修改界面中，从左框中选择角色，或者从右框中移除角色。		
	*	5	点击保存按钮。	【用户信息】	成功保存至数据库，返回用户角色用户部门角色主界面。
	*	用户角色用户部门角色修改异常操作步骤
	*	序号	扩展事件	相关数据	备注
	*	5a	点击取消按钮，退出当前界面，返回主界面。		
	*	5b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
	*	
	*	1.7	业务规则
	*	序号	描述
	*	SR-1	不能为用户在两个相同的组织中分配角色。当用户新增部门角色时，如果所选的部门已被添加，则提示用户部门角色已添加。查询条件中的部门从公共选择框中选择
	*	SR-2	当修改用户角色时，在修改界面中，角色权限列表中的权限随着角色的修改而变化。
	*	SR-3	在添加角色时，如果当前添加的角色的权限与已经添加的角色中的权限有互斥关系，则不允许添加，并提示用户此角色的XXX权限与已经添加的XXX角色的XXX权限互斥。
     */
    @Override
    public List<RoleEntity> queryRoleForDownload(RoleEntity entity){
	RoleEntity queryEntity;
	if (null == entity) {
	    queryEntity = new RoleEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<RoleEntity>) getSqlSession().selectList(NAMESPACE + "queryRoleForDownload", queryEntity);
    }
    
    /**
     * 下面是工具方法
     */
    
    /**
     * 转义查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2013-1-5 下午3:04:30
     */
    public RoleEntity transQueryCondition(RoleEntity entity){
	if(entity == null){
	    return entity;
	}
	if(StringUtils.isNotBlank(entity.getName())){
	    entity.setName(entity.getName().trim());
	    entity.setName(entity.getName().replace("%","\\%"));
	}
	
	return entity;
    }
    
    /**
     * 查询部门 未分配的用户角色
     * 
     * @author 313353-foss-邱鹏
     * @date 2016-11-2 下午8:49:21
     */
    public List<RoleEntity> queryRoleByUserOrgRole(UserOrgRoleEntity entity){
    	if (null == entity) {
    		entity = new UserOrgRoleEntity();
    	}
    	entity.setActive(FossConstants.ACTIVE);
    	return getSqlSession().selectList(NAMESPACE + "queryRoleByUserOrgRole", entity);
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedWarrantyItemsDao.class);

}
