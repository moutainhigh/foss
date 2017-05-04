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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/UserService.java
 * 
 * FILE NAME        	: UserService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 修订记录 
		日期 	修订内容 	修订用户 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-04	1. 修改前置条件； 
		2. 查询条件增加“所在部门”，使用“放大镜”控件； 
		3. “用户角色列表”中增加“启用日期”； 
		4. “用户详情”去掉“更新时间”，“生效日期”和“失效日期”；	李俊	V0.2
		2012-07-04	关联系统用例BUC_FOSS_5.10.20_065排班(短途班车)。	李俊	V0.3
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		1.	SUC-226-查询用户
		1.1	相关业务用例
		BUC_FOSS_5.10.20_065排班(短途班车)。
		1.2	用例描述
		该用例可对用户进行查询。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	1. 人员信息完备。
		2. 角色信息完备。
		3. UUMS已将用户信息同步至FOSS。	SUC-35查询人员
		SUC-41 修改_查询用户角色
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对用户进行查询操作。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一:用户管理主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：用户详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。参见数据元素【用户列表信息】。
		3.	字段输入区域
		1)	参见数据元素【用户查询条件】。
		1.5.4	界面原型-用户详情界面
		图一:用户详情界面
		1.5.5	界面描述-用户详情界面
		4.	功能按钮区域
		1)	返回按钮：点击返回按钮，退出当前界面，返回上一级界面。
		5.	列表显示区域
		1)	列表区域显示这个用户在不同的部门所具有的角色。参见数据元素【用户角色列表】。
		6.	信息显示区域
		1)	参见数据元素【用户信息】。
				1.6	操作步骤
		1.6.1	查询用户操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入主界面。		
		2	输入查询条件。点击查询按钮。	【用户查询条件】
		【用户列表信息】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。		
		4	找出要查看的用户双击。	【用户信息】，【用户角色列表】	进入用户详情界面。
		5	点击返回按钮。		返回主界面。
		1.7	业务规则
		序号	描述
		1.8	数据元素
		1.8.1	用户信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		用户名	用户登录帐号，默认为工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.2	用户查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	文本	50	否	
		姓名	用户的姓名	文本	50	否	
		所在部门	用户所在的部门	选择框	50	否	
		1.8.3	用户列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		所在部门	用户的所在部门	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.4	用户角色列表
		字段名称 	说明 	输入限制	长度	是否必填	备注
		部门	用户的部门	N/A	N/A	N/A	
		启用日期	启用日期	N/A	N/A	N/A	
		是否默认	是否是用户的默认的部门	N/A	N/A	N/A	
		角色	用户在这个部门所具有的角色的名称，多个角色用英文逗号（,）分隔。	N/A	N/A	N/A	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		用户信息接口	UUMS系统	将修改的用户信息同步到FOSS系统。

		日期 	修订内容 	修订用户 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-04	1. 修改前置条件； 
		2. 查询条件增加“所在部门”，使用“放大镜”控件； 
		3. “用户角色列表”中增加“启用日期”； 
		4. “用户详情”去掉“更新时间”，“生效日期”和“失效日期”；	李俊	V0.2
		2012-07-04	关联系统用例BUC_FOSS_5.10.20_065排班(短途班车)。	李俊	V0.3
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		1.	SUC-226-查询用户
		1.1	相关业务用例
		BUC_FOSS_5.10.20_065排班(短途班车)。
		1.2	用例描述
		该用例可对用户进行查询。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	1. 人员信息完备。
		2. 角色信息完备。
		3. UUMS已将用户信息同步至FOSS。	SUC-35查询人员
		SUC-41 修改_查询用户角色
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对用户进行查询操作。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一:用户管理主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：用户详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。参见数据元素【用户列表信息】。
		3.	字段输入区域
		1)	参见数据元素【用户查询条件】。
		1.5.4	界面原型-用户详情界面
		图一:用户详情界面
		1.5.5	界面描述-用户详情界面
		4.	功能按钮区域
		1)	返回按钮：点击返回按钮，退出当前界面，返回上一级界面。
		5.	列表显示区域
		1)	列表区域显示这个用户在不同的部门所具有的角色。参见数据元素【用户角色列表】。
		6.	信息显示区域
		1)	参见数据元素【用户信息】。
				1.6	操作步骤
		1.6.1	查询用户操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入主界面。		
		2	输入查询条件。点击查询按钮。	【用户查询条件】
		【用户列表信息】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。		
		4	找出要查看的用户双击。	【用户信息】，【用户角色列表】	进入用户详情界面。
		5	点击返回按钮。		返回主界面。
		1.7	业务规则
		序号	描述
		1.8	数据元素
		1.8.1	用户信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		用户名	用户登录帐号，默认为工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.2	用户查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	文本	50	否	
		姓名	用户的姓名	文本	50	否	
		所在部门	用户所在的部门	选择框	50	否	
		1.8.3	用户列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		所在部门	用户的所在部门	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.4	用户角色列表
		字段名称 	说明 	输入限制	长度	是否必填	备注
		部门	用户的部门	N/A	N/A	N/A	
		启用日期	启用日期	N/A	N/A	N/A	
		是否默认	是否是用户的默认的部门	N/A	N/A	N/A	
		角色	用户在这个部门所具有的角色的名称，多个角色用英文逗号（,）分隔。	N/A	N/A	N/A	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		用户信息接口	UUMS系统	将修改的用户信息同步到FOSS系统。
				日期 	修订内容 	修订用户 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-04	1. 修改前置条件； 
		2. 查询条件增加“所在部门”，使用“放大镜”控件； 
		3. “用户角色列表”中增加“启用日期”； 
		4. “用户详情”去掉“更新时间”，“生效日期”和“失效日期”；	李俊	V0.2
		2012-07-04	关联系统用例BUC_FOSS_5.10.20_065排班(短途班车)。	李俊	V0.3
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		1.	SUC-226-查询用户
		1.1	相关业务用例
		BUC_FOSS_5.10.20_065排班(短途班车)。
		1.2	用例描述
		该用例可对用户进行查询。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	1. 人员信息完备。
		2. 角色信息完备。
		3. UUMS已将用户信息同步至FOSS。	SUC-35查询人员
		SUC-41 修改_查询用户角色
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对用户进行查询操作。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		图一:用户管理主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：用户详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。参见数据元素【用户列表信息】。
		3.	字段输入区域
		1)	参见数据元素【用户查询条件】。
		1.5.4	界面原型-用户详情界面
		图一:用户详情界面
		1.5.5	界面描述-用户详情界面
		4.	功能按钮区域
		1)	返回按钮：点击返回按钮，退出当前界面，返回上一级界面。
		5.	列表显示区域
		1)	列表区域显示这个用户在不同的部门所具有的角色。参见数据元素【用户角色列表】。
		6.	信息显示区域
		1)	参见数据元素【用户信息】。
				1.6	操作步骤
		1.6.1	查询用户操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入主界面。		
		2	输入查询条件。点击查询按钮。	【用户查询条件】
		【用户列表信息】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。		
		4	找出要查看的用户双击。	【用户信息】，【用户角色列表】	进入用户详情界面。
		5	点击返回按钮。		返回主界面。
		1.7	业务规则
		序号	描述
		1.8	数据元素
		1.8.1	用户信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		用户名	用户登录帐号，默认为工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.2	用户查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	文本	50	否	
		姓名	用户的姓名	文本	50	否	
		所在部门	用户所在的部门	选择框	50	否	
		1.8.3	用户列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	用户的工号	N/A	N/A	N/A	
		姓名	用户姓名	N/A	N/A	N/A	
		所在部门	用户的所在部门	N/A	N/A	N/A	
		手机号	用户的手机号	N/A	N/A	N/A	
		1.8.4	用户角色列表
		字段名称 	说明 	输入限制	长度	是否必填	备注
		部门	用户的部门	N/A	N/A	N/A	
		启用日期	启用日期	N/A	N/A	N/A	
		是否默认	是否是用户的默认的部门	N/A	N/A	N/A	
		角色	用户在这个部门所具有的角色的名称，多个角色用英文逗号（,）分隔。	N/A	N/A	N/A	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		用户信息接口	UUMS系统	将修改的用户信息同步到FOSS系统。
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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.entity.IUser;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IUserDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.server.util.CryptoUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 用来操作交互"用户信息"的数据库对应数据访问Service接口实现类：SUC-226
 * <p>
 * 需要版本控制：已实现
 * </p>
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:100847-foss-GaoPeng,date:2012-11-7 下午6:49:01
 * </p>
 * 
 * @author 100847-foss-GaoPeng
 * @date 2012-11-7 下午6:49:01
 * @since
 * @version
 */
public class UserService implements IUserService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserService.class);
	// "用户信息"Service接口
	private IUserDao userDao;
	
	private IEmployeeDao employeeDao;
	
	private ISyncResourceService syncResourceService;
	
	/**
     * 业务锁
     */
 	private IBusinessLockService businessLockService;
	 
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	
	public void setSyncResourceService(ISyncResourceService syncResourceService) {
		this.syncResourceService = syncResourceService;
	}
	
	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	/**
	 * 通过用户ID从缓存中得到用户信息 findByUserId
	 * 
	 * @param id
	 * @return
	 * @return UserEntity
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserEntity findByUserId(String id) {
		return (UserEntity) CacheManager.getInstance()
				.getCache(FossTTLCache.USER_CACHE_UUID).get(id);
	}
	/**
	 * <p>
	 * 新增一个“用户信息”实体入库（忽略实体中是否存在空值）(注意该方法没有事务，被外部事务调用)
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:43
	 * @param user
	 *            “用户信息”实体
	 * @param createUser
	 *            创建人
	 * @param ignoreNull
	 *            true：忽略空值，false：包含空值
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#addUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.lang.String, boolean)
	 */
	@Override
	@Transactional
	public int addUser(UserEntity user, String createUser, boolean ignoreNull)
			throws UserException {
		// 判断用户是否为空
		if (null == user) {
			throw new UserException("", "用户为空");
		}
		// 判断员工号是否为空
		if (StringUtils.isBlank(user.getEmpCode())) {
			throw new UserException("", "用户工号为空");
		}
		// 判断用户信息是否为空
		if (StringUtils.isBlank(user.getUserName())) {
			throw new UserException("", "用户名信息为空");
		}
		// 验证对应"用户"是否已经存在
		UserEntity oldUser = null, tempUser = new UserEntity();
		tempUser.setEmpCode(user.getEmpCode());
		tempUser.setUserName(user.getUserName());
		// 执行查询操作
		oldUser = userDao.queryUserBySelective(tempUser);
		// 如果用户不为空
		if (null != oldUser) {
			throw new UserException("", "用户已经存在");
		} else {
			user.setCreateUser(createUser);
			if (ignoreNull) {
				userDao.addUserBySelective(user);
			} else {
				userDao.addUser(user);
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 根据“用户信息”记录标识集合删除（物理删除）多条“用户信息”记录（注意该方法无事务）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:42
	 * @param ids
	 *            记录唯一标识集合
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#deleteUser(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public int deleteUser(List<String> ids, String modifyUser)
			throws UserException {
		// 判断用户信息是否为空
		if (StringUtils.isBlank(modifyUser)) {
			throw new UserException("", "修改人信息为空");
		}
		// 待优化实现
		for (String id : ids) {
			if (StringUtils.isBlank(id)) {
				throw new UserException("", "UUMS0105:ID为空");
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("调试：作废车辆品牌的ID检测通过");
				}
			}
			// 验证对应"身份证号用户"是否已经存在
			UserEntity user = queryUserListBySelectiveCondition(id);
			if (null == user) {
				throw new UserException("", "用户不存在");
			} else {
				Date now = new Date();
				user.setModifyDate(now);
				user.setModifyUser(modifyUser);
				user.setVersion(now.getTime());
				user.setActive(FossConstants.INACTIVE);
				int userRecord = userDao.updateUserBySelective(user);
				if (NumberConstants.NUMERAL_ONE != userRecord) {
					throw new UserException("", "执行结果不符合预期");
				}
			}
		}
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 修改一个“用户信息”实体入库（忽略实体中是否存在空值）(注意该方法没有事务，被外部事务调用)
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-10-20 上午11:02:48
	 * @param user
	 *            “用户信息”实体
	 * @param modifyUser
	 *            修改人
	 * @return 1：成功；0：失败
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#updateUser(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public int updateUser(UserEntity user, String modifyUser)
			throws UserException {
        //判断用户信息
		if (null == user) {
			throw new UserException("", "用户为空");
		}
		//判断ID
		if (StringUtils.isBlank(user.getId())) {
			throw new UserException("", "ID 为空");
		}
		//判断员工号
		if (StringUtils.isBlank(user.getEmpCode())) {
			throw new UserException("", "用户工号为空");
		}
		//判断用户信息
		if (StringUtils.isBlank(user.getUserName())) {
			throw new UserException("", "用户名信息为空");
		}
		//判断修改人信息
		if (StringUtils.isBlank(modifyUser)) {
			throw new UserException("", "修改人信息为空");
		}
		// 验证对应"用户"是否已经存在
		UserEntity queryUserEntity = new UserEntity();
		queryUserEntity.setEmpCode(user.getEmpCode());
	
		List<UserEntity> userList = userDao.queryUserListBySelectiveCondition(queryUserEntity, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isNotEmpty(userList)){
			for(UserEntity oldUser:userList){
				// 1、禁用当前实体记录
				Date now = new Date();
				oldUser.setModifyDate(now);
				oldUser.setModifyUser(modifyUser);
				oldUser.setVersion(now.getTime());
				oldUser.setActive(FossConstants.INACTIVE);
				userDao.updateUserBySelective(oldUser);
			}
			// 2、产生一条最新启用记录
			user.setModifyUser(modifyUser);
			userDao.addUser(user);
			try{
				//   修改密码后，用户信息传递ECS
				List<UserEntity> userEntitys = new ArrayList<UserEntity>();
				userEntitys.add(user);
				syncResourceService.syncResource(userEntitys);
			}catch(BusinessException e){
				LOGGER.error("修改密码后，用户信息传递ECS异常"+e.getMessage());
			}
			
		}else{
			throw new UserException("", "用户不存在");
		} 
		return FossConstants.SUCCESS;
	}
	/**
	 * <p>
	 * 根据“用户信息”记录唯一标识查询出一条“用户信息”记录
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:31:47
	 * @param id
	 *            记录唯一标识
	 * @return “用户信息”实体
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUser(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public UserEntity queryUserListBySelectiveCondition(String id)
			throws UserException {
		//判断ID是否为空
		if (StringUtils.isBlank(id)) {
			throw new UserException("", "用户ID为空");
		}
		UserEntity user = new UserEntity();
		user.setId(id);
		return userDao.queryUserBySelective(user);
	}
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户信息”唯一实体（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:31:47
	 * @param user
	 *            以“用户信息”实体承载的条件参数实体
	 * @return “用户信息”实体
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUserListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public UserEntity queryUserListBySelectiveCondition(UserEntity user)
			throws UserException {
		//判断用户信息是否为空
		if (null == user) {
			throw new UserException("", "用户为空");
		}
		return userDao.queryUserBySelective(user);
	}
	/**
	 * <p>
	 * 根据条件有选择的检索出符合条件的“用户信息”实体列表（条件做自动判断，只选择实体中非空值）
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午5:28:21
	 * @param leasedDriver
	 *            以“用户信息”实体承载的条件参数实体
	 * @param offset
	 *            开始记录数
	 * @param limit
	 *            限制记录数
	 * @return 符合条件的“用户信息”实体列表
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUserListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity,
	 *      int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UserEntity> queryUserListBySelectiveCondition(UserEntity user,
			int offset, int limit) throws UserException {
		if (offset < 0 || limit < 0) {
			return new ArrayList<UserEntity>();
		}
		if (null == user) {
			user = new UserEntity();
		}
		return userDao.queryUserListBySelectiveCondition(user, offset, limit);
	}
	/**
	 * <p>
	 * 提供给"结算"来验证用户编号和密码的正确性
	 * </p>
	 * 
	 * @author 100847-foss-GaoPeng
	 * @date 2012-11-7 下午8:40:42
	 * @param empCode
	 *            用户编号
	 * @param password
	 *            密码
	 * @return
	 * @throws UserException
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUserForValidationCorrectness(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean queryUserForValidationCorrectness(String empCode,
			String password) throws UserException {
		if (StringUtils.isEmpty(empCode)) {
			throw new UserException("", "用户编号为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new UserException("", "密码为空");
		}
		UserEntity user = new UserEntity();
		user.setEmpCode(empCode);
		user.setPassword(CryptoUtil.digestByMD5(password));
		if (null == userDao.queryUserBySelective(user)) {
			return false;
		}
		return true;
	}
	/**
	 * 通过用户名查询用户
	 * 
	 * @throws UserException
	 * @param loginName
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public UserEntity findByLoginName(String loginName) throws UserException {
		if (StringUtils.isBlank(loginName)) {
			throw new UserException(UserException.LOGINNAME_NULL);
		}
		return userDao.getByUserName(loginName);
	}
	@Override
	@Transactional
	public int updateLastLoginDate(String userId) throws UserException {
		if (StringUtils.isBlank(userId)) {
			throw new UserException(UserException.USER_ID_NULL);
		}
		return userDao.updateLastLoginDate(userId);
	}
	/**
	 * 以下全为查询
	 */
	/**
	 * 精确查询 通过 EMP_CODE 查询 用户
	 * 
	 * @author 087584-foss-lijun
	 * @date 2013-1-8 下午10:5:55
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IUserService#queryUserByCode(java.lang.String)
	 */
	@Override
	public UserEntity queryUserByEmpCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return userDao.queryUserByEmpCode(code);
	}
	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	/**
	 * 
	 * <p>
	 * 通过资源code，查询分配了该资源的所有用户
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-2-2 下午3:50:00
	 * @param resourceCode
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUserCodeByResourceCode(java.lang.String)
	 */
	@Override
	public List<String> queryUserIdsByResourceCode(String resourceCode) {
		if (StringUtil.isEmpty(resourceCode)) {
			return new ArrayList<String>();
		}
		return userDao.getUserIdsByResourceCode(resourceCode);
	}
	/**
	 * 
	 * <p>
	 * 通过角色code，查询分配了该角色的所有用户
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-2-2 下午4:32:21
	 * @param roleCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#queryUserIdsByRoleCode(java.lang.String)
	 */
	@Override
	public List<String> queryUserIdsByRoleCode(String roleCode) {
		if (StringUtil.isEmpty(roleCode)) {
			return new ArrayList<String>();
		}
		return userDao.getUserIdsByRoleCode(roleCode);
	}
	 /**
	  * 
	  * @Description: 修改密码
	  * @author FOSSDP-sz
	  * @date 2013-3-29 上午11:27:47
	  * @param newPwd
	  * @param userName
	  * @return 加密后的密码字符串
	  * @version V1.0
	  */
	
	@Override
	@Transactional
	public String modifyUserPwd(String userName, String newPwd) {
		MutexElement mutex =null;
		String pwd = null;
		try{
			
			if (StringUtils.isBlank(userName)) {
				throw new UserException(UserException.LOGINNAME_NULL);
			}
			if (StringUtils.isBlank(newPwd)) {
				throw new UserException(UserException.PW_NULL);
			}
			UserEntity user = this.findByLoginName(userName);
			if (user == null) {
				throw new UserException(UserException.USER_IS_NULL);
			}
			
			String regex="^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{6,16}$";
			if(!newPwd.matches(regex)){
				throw new UserException(MessageType.PW_SAMECHAR_ERROR);
			}
			
			pwd = CryptoUtil.digestByMD5(newPwd);
			if(StringUtil.isNotEmpty(user.getPassword()) && user.getPassword().equals(pwd)){
				throw new UserException(MessageType.PW_NOT_SAME);
			}
			//获取当前登录人
			EmployeeEntity empEntity=FossUserContext.getCurrentUser().getEmployee();
			UserEntity userEntity = new UserEntity();
			//封装用户属性-187862收银员修改密码需求20151117
			userEntity.setId(UUIDUtils.getUUID());
			userEntity.setPassword(pwd);
			userEntity.setEmpCode(user.getEmpCode());
			userEntity.setUserName(userName);
			userEntity.setEmpName(user.getEmpName());
			userEntity.setLastLogin(user.getLastLogin());
			userEntity.setTitle(user.getTitle());
			userEntity.setBeginDate(user.getBeginDate());
			userEntity.setEndDate(user.getEndDate());
			userEntity.setCreateUser(empEntity.getEmpCode());
			//封装完后先作废有效数据，再新增
			Date now = new Date();
			user.setModifyDate(now);
			user.setVersion(now.getTime());
			user.setModifyUser(empEntity.getEmpCode());
			user.setActive(FossConstants.INACTIVE);
			
			mutex = new MutexElement(userName,
					"UUMS_USER_CODE", MutexElementType.UUMS_USER_CODE);
			
			boolean result = businessLockService.lock(mutex,
					NumberConstants.ZERO);
			
			UserEntity addEntity = null;
			int i = 0;
			
			//加锁成功
			if (result) {
				i =userDao.updateUserBySelective(user);
				addEntity=userDao.addUserWithoutID(userEntity);
			} else {
				throw new UserException("修改密码正在被操作，请稍等。");
			}
			
			if(i<0||addEntity==null){
				throw new UserException(UserException.UPDATE_PSW_FAIL);
			}
			
			try{
				//   修改密码后，用户信息传递ECS
				List<UserEntity> userEntitys = new ArrayList<UserEntity>();
				userEntitys.add(addEntity);
				syncResourceService.syncResource(userEntitys);
			}catch(BusinessException e){
				LOGGER.error("修改密码后，用户信息传递ECS异常"+e.getMessage());
			}
			
			//解锁
			businessLockService.unlock(mutex);
			
			//刷新用户缓存
			this.refreshCache(addEntity.getId());
		} catch (BusinessException e) {
			LOGGER.error("修改密码异常：    "+e.getMessage());
		}
		
		return pwd;
	}
	
	@SuppressWarnings("unchecked")
	private void refreshCache(String userId) {
		ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
				FossTTLCache.USER_CACHE_UUID);
		if (userId != null) {
			userCache.invalid(userId);
		}
	}
	/**
	 *<p>根据条件，查询符合条件的所有记录数</P>
	 * @author 130566-foss-ZengJunfan
	 * @date 2013-4-25上午10:37:44
	 *@param userEntity
	 *@return
	 */
	@Override
	public Long queryUserCountBySelectiveCondition(UserEntity userEntity) {
		if(userEntity==null){
			userEntity= new UserEntity();
		}
		return userDao.queryUserRecordCountBySelectiveCondition(userEntity);
	}
	/**
	 * 
	 * <p>通过角色code，查询分配了该角色的所有用户及组织，并把用户与组织按"用户编码#组织编码"的方式进行返回</p> 
	 * @author ztjie
	 * @date 2013-8-19 上午8:45:01
	 * @param roleCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IUserService#getUserIdsAndOrgCodesByRoleCodeForCacheId(java.lang.String)
	 */
	@Override
	public List<String> queryUserAndOrgCodesByRoleCodeForCache(
			String roleCode) {
		if (StringUtil.isEmpty(roleCode)) {
			return new ArrayList<String>();
		}
		return userDao.getUserAndOrgCodesByRoleCodeForCache(roleCode);
	}
	@Override
	public UserEntity queryEmpByUserCode(String empCode) {
		
		UserEntity entity=new  UserEntity();
		entity.setId(empCode);
		UserEntity userEntity=userDao.queryUserBySelective(entity);
		EmployeeEntity employeeEntity=employeeDao.queryEmployeeByEmpCode(userEntity.getEmpCode());
		userEntity.setEmployee(employeeEntity);
		return userEntity;
	}
	/**
	 * 判断当前用户密码使用期限是否到三个月
	 * @author 187862-dujunhui
	 * @date 2015-10-27 下午2:51:05
	 */
	@Override
	public int queryLeftDaysOfPsw(String userName,String psw) {
		return userDao.queryLeftDaysOfPsw(userName,psw);
	}
	
}
