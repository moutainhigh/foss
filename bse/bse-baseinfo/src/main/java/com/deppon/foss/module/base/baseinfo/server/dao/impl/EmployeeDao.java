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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/EmployeeDao.java
 * 
 * FILE NAME        	: EmployeeDao.java
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
 *      修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-06-29 	1，增加“状态”(在职/离职/全部)查询条件；默认查询所有在职的人员；
		2，列表中增加“状态”列； 	李俊	V0.2
		2012-07-05 	1，添加了相关业务用例。	李俊	V0.3
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		
		1.	SUC-35-查询人员
		1.1	相关业务用例
		BUC_FOSS_5.10.20_065排班(短途班车)。
		1.2	用例描述
		该用例可对人员进行查询。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	1，行政组织信息完备。	SUC-647同步行政组织接口
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对人员进行查询操作。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		 
		图一:人员管理主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
		1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		同步人员信息	UUMS系统	将修改的人员信息同步到FOSS系统
		 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-06-29 	1，增加“状态”(在职/离职/全部)查询条件；默认查询所有在职的人员；
		2，列表中增加“状态”列； 	李俊	V0.2
		2012-07-05 	1，添加了相关业务用例。	李俊	V0.3
		2012-08-02	1. 通过业务部门审核签字版本升级到V0.9	李俊	V0.9
		
		1.	SUC-35-查询人员
		1.1	相关业务用例
		BUC_FOSS_5.10.20_065排班(短途班车)。
		1.2	用例描述
		该用例可对人员进行查询。
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件	1，行政组织信息完备。	SUC-647同步行政组织接口
		后置条件		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对人员进行查询操作。
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		1.5.2	界面原型-主界面
		 
		图一:人员管理主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		同步人员信息	UUMS系统	将修改的人员信息同步到FOSS系统
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 人员 DAO
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:55:34
 */
public class EmployeeDao extends SqlSessionDaoSupport implements
	IEmployeeDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".employee.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-2 上午13:16:23
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IEmployeeDao#addEmployee(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     * 1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
     */
    @Override
    @Transactional
    public EmployeeEntity addEmployee(EmployeeEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(now);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addEmployee", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过EMP_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#deleteEmployee(java.lang.String)
     * 1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
     */
    @Override
    public EmployeeEntity deleteEmployee(EmployeeEntity entity) {
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
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteEmployee", map);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 通过EMP_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#
     * deleteEmployeeMore(java.lang.String[], java.lang.String)
     * 1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
     */
    @Override
    public EmployeeEntity deleteEmployeeMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	EmployeeEntity entity = new EmployeeEntity();
	entity.setActive(FossConstants.INACTIVE);
	entity.setModifyDate(now);
	entity.setModifyUser(deleteUser);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteEmployeeMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 通过EMP_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IEmployeeDao#updateEmployee(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     * 1.8	数据元素
		1.8.1	人员信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		性别	人员的性别	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		职等	人员的职等（文职、经理、主管、助理、经理、高级经理）	N/A	N/A	N/A	
		出生日期	人员的出生日期	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		身份证号	身份证号码	N/A	N/A	N/A	
		入职日期	入职日期	N/A	N/A	N/A	
		离职日期	办理离职的日期	N/A	N/A	N/A	
		1.8.2	人员查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	文本	20	否	
		职员姓名	人员的姓名	文本	50	否	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	文本	50	否	
		电话	电话号码（同步OA电话信息）	文本	50	否	
		部门名称	人员的部门名称	选择框	50	否	
		状态	人员的状态（在职、离职，全部）	下拉框	50	是	
		1.8.3	人员列表信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		工号	人员的工号	N/A	N/A	N/A	
		职员姓名	人员的姓名	N/A	N/A	N/A	
		职位	人员的职位（人事专员、人事部经理、会计部经理）	N/A	N/A	N/A	
		电话	电话号码（同步OA电话信息）	N/A	N/A	N/A	
		部门名称	人员的部门名称	N/A	N/A	N/A	
		状态	人员的状态（在职、离职）	N/A	N/A	N/A	
     */
    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getEmpCode())) {
	    return entity;
	}
	// 更新要先删除旧的数据：
	EmployeeEntity result = this.deleteEmployee(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(new Date());
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addEmployee", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 
     * <p>配合主数据项目修改人员信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-22 上午9:06:03
     * @param entity
     * @return
     * @see
     */
    @Override
    public EmployeeEntity updateEmployeeOfUU(EmployeeEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getEmpCode())) {
	    return entity;
	}
	int resultNum=0;
	if(StringUtils.equals(entity.getIsTempEmp(), FossConstants.YES)){//临时人员修改
		resultNum = getSqlSession().update(NAMESPACE + "updateTempEmployeeOfUU", entity);
	}else{
		resultNum = getSqlSession().update(NAMESPACE + "updateEmployeeOfUU", entity);
	}
	return resultNum > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 以下全为查询：
     */  
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#queryEmployeeByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public EmployeeEntity queryEmployeeByEmpCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}	
	// 构造查询条件：
	EmployeeEntity entity=new EmployeeEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setEmpCode(code);
	List<EmployeeEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryEmployeeByEmpCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(0);
	}
    } 
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IEmployeeDao#queryEmployeeBatchBy(java.lang.String[])
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeEntity> queryEmployeeBatchByEmpCode(
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
		NAMESPACE + "queryEmployeeBatchByEmpCode", map);
    }
    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IEmployeeDao#queryEmployeeExactByEntity
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeEntity> queryEmployeeExactByEntity(
	    EmployeeEntity entity, int start, int limit) {
	EmployeeEntity queryEntity;
	if (null == entity) {
	    queryEntity = new EmployeeEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryEmployeeExactByEntity",
			queryEntity,
			rowBounds);
    }
    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 关联了人员部门
     * @author 078823-panGuangjun
     * @date 2012-11-30 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IEmployeeDao#queryEmployeeExactByEntity(com.deppon.foss.
     * module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeEntity> queryEmployeeExactByEntity4Selector(
    		EmployeeEntity entity, int start, int limit) {
    	EmployeeEntity queryEntity;
    	if (null == entity) {
    		queryEntity = new EmployeeEntity();
    	}else{
    		queryEntity = entity;
    	}
    	queryEntity.setActive(FossConstants.ACTIVE);
    	RowBounds rowBounds = new RowBounds(start, limit);
    	return getSqlSession()
    			.selectList(NAMESPACE + "queryEmployeeExactByEntity4Selector",
    					queryEntity,
    					rowBounds);
    }
    /** 
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 关联了人员部门
     * @author 078823-panGuangjun
     * @date 2012-11-30 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.
     * dao.IEmployeeDao#countEmployeeExactByEntity4Selector(
     * com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity, int, int)
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @Override
    public long countEmployeeExactByEntity4Selector(
    		EmployeeEntity entity) {
    	EmployeeEntity queryEntity;
    	if (null == entity) {
    		queryEntity = new EmployeeEntity();
    	}else{
    		queryEntity = entity;
    	}
    	queryEntity.setActive(FossConstants.ACTIVE);
    	return  (Long)getSqlSession()
    			.selectOne(NAMESPACE + "countEmployeeExactByEntity4Selector",
    					queryEntity);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.
     * server.dao.IEmployeeDao#queryEmployeeExactByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @Override
    public long queryEmployeeExactByEntityCount(EmployeeEntity entity) {
	EmployeeEntity queryEntity;
	if (null == entity) {
	    queryEntity = new EmployeeEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryEmployeeExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 部门的标杆编码如果不为空，则为精确查询的查询条件 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.
     * dao.IEmployeeDao#queryEmployeeMore(java.lang.String[])
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeEntity> queryEmployeeByEntity(
	    EmployeeEntity entity, int start, int limit) {
	EmployeeEntity queryEntity;
	if (null == entity) {
	    queryEntity = new EmployeeEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryEmployeeByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 部门的标杆编码如果不为空，则为精确查询的查询条件 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:16:23
     * @see com.deppon.foss.module.base.baseinfo.server.
     * dao.IEmployeeDao#queryEmployeeByEntityCount
     * (com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
    @Override
    public long queryEmployeeByEntityCount(EmployeeEntity entity) {
	EmployeeEntity queryEntity;
	if (null == entity) {
	    queryEntity = new EmployeeEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryEmployeeByEntityCount", queryEntity);
    }
    
    /**
     * 用户部门功能中查询员工信息（该员工必须有用户信息）
     * @author 088933-foss-zhangjiheng
     * @date 2013-2-28 下午6:44:33
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeEntity> queryEmployeeAndUserByEntity(
			EmployeeEntity entity, int start, int limit) {
		//只查询有效的员工信息
		entity.setActive(FossConstants.ACTIVE);
		//分页查询
		RowBounds rowBounds=new RowBounds(start, limit);
	    //执行查询语句
		return getSqlSession().selectList(NAMESPACE+"queryEmployeeAndUserByEntity", entity,rowBounds);
	}
	
	/**
     * 用户部门功能中查询员工信息求和（该员工必须有用户信息）
     * @author 088933-foss-zhangjiheng
     * @date 2013-2-28 下午6:44:33
     * 1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	重置按钮：点击重置按钮，清空查询条件。
		2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
		3)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。参见【图二：人员详情界面】。
		4)	分页按钮：支持分页功能。
		2.	列表显示区域
		1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
		2)	列表数据参见数据元素【人员列表信息】。
		3.	字段输入区域
		1)	参见数据元素【人员查询条件】。
		1.5.4	界面原型-人员详情界面
		 
		图一: 人员详情界面
		
		1.5.5	界面描述-人员详情界面
		1.	功能按钮区域
		1)	关闭按钮：点击关闭按钮，返回上一级界面。
		
		1.6	操作步骤
		1.6.1	查询人员信息操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入人员管理主界面。		
		2	输入查询条件。点击查询按钮。	【人员查询条件】	系统返回查询结果。
		3	点击分页按钮，查看各页的查询结果。	【人员列表信息】	
		4	找出要查看的人员双击。	【人员信息】	进入人员详情界面。
		5	点击返回按钮。		返回主界面。
		
		1.7	业务规则
		序号	描述
		SR-1	查询条件中的“状态”默认为在职。
		
     */
	@Override
	public long queryEmployeeAndUserCountByEntity(EmployeeEntity entity) {
		//只查询有效的员工信息
	    entity.setActive(FossConstants.ACTIVE);
		return  (Long) getSqlSession().selectOne(NAMESPACE+"queryEmployeeAndUserByEntityCount", entity);
	}

	/**
     * 根据员工号查询员工姓名
     * 
     * @author foss-zhangxiaohui
     * @date 2013-5-6 下午5:49:06
     */
	public String queryEmpNameByEmpCode(String empCode){
		//声明传参数的map
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("empCode", empCode);
		EmployeeEntity entity = (EmployeeEntity)getSqlSession().selectOne(NAMESPACE+"queryEmpNameByEmpCode", empCode);
		if(null != entity && StringUtils.isNotEmpty(entity.getEmpName())){
			return entity.getEmpName();
		}
		return  null;
	}
	
	 /**
   	 * 内存溢出，性能优化
   	 * @return
   	 * add WangQianJin by 2010-10-17
   	 */
     public List<EmployeeEntity> queryEmployeeNameAndCodeByEmpCode(List<String> codes){
    	// 构造查询条件
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("codes", codes);
		map.put("active", FossConstants.ACTIVE);		
		return getSqlSession().selectList(NAMESPACE + "queryEmployeeNameAndCodeByEmpCode", map);
			
     }
     
     /**
      * 
      * <p>根据编码和修改时间查询人员信息</p> 
      * @author 187862-dujunhui 
      * @date 2015-4-23 下午3:02:06
      * @return
      * @see
      */
     @SuppressWarnings("unchecked")
     @Override
	public List<EmployeeEntity> queryEmployeeByCodeAndModifyDate(EmployeeEntity entity){
		if(entity==null){
			return null;
		}
    	 return getSqlSession().selectList(NAMESPACE+"queryEmployeeByCodeAndModifyDate", entity);
    	 
     }
     /**
      * 
      * <p>配合主数据项目新增人员信息</p> 
      * @author 187862-dujunhui 
      * @date 2015-4-23 下午3:27:34
      * @param entity
      * @return 
      * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#addEmployee(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
      */
     @Override
     public EmployeeEntity addEmployeeOfUU(EmployeeEntity entity) {
    		// 请求合法性验证：
    		if (null == entity) {
    		    return entity;
    		}
    		entity.setId(UUIDUtils.getUUID());
    		int result = getSqlSession().insert(NAMESPACE + "addEmployeeOfUU", entity);
    		return result > NumberConstants.ZERO ? entity : null;
    }
     
     /**
      * 通过 标识编码查询
      * @author 187862-dujunhui
      * @date 2015-5-14 下午9:09:24
      */
     @SuppressWarnings("unchecked")
     @Override
    public EmployeeEntity queryTempEmployeeByEmpCode(String code) {
	 	if (StringUtils.isBlank(code)) {
	 	    return null;
	 	}	
	 	// 构造查询条件：
	 	EmployeeEntity entity=new EmployeeEntity();
	 	entity.setEmpCode(code);
	 	List<EmployeeEntity> entitys = this.getSqlSession().selectList(
	 		NAMESPACE + "queryTempEmployeeByEmpCode", entity);
	 	if (CollectionUtils.isEmpty(entitys)) {
	 	    return null;
	 	} else {
	 	    return entitys.get(0);
	 	}
    }

     /**
      * 提供给接送货接口：根据员工编号查询其是否为营业部经理
      * @author 187862-dujunhui
      * @date 2015年10月30日 下午4:43:48
      */
	@Override
	public boolean queryEmpIsSaleDeptOfficer(String empCode) {
		Map<String,String> map = new HashMap<String , String>();
		map.put("empCode", empCode);
		map.put("titleCode", "00010443");//营业部经理岗位编码
		map.put("active", FossConstants.ACTIVE);	
		int i=(Integer) getSqlSession().selectOne(NAMESPACE+"queryEmpIsSaleDeptOfficer", map);
		return (Integer)i==null? false:i==0?false:true;
	} 

	/**
     * 提供给中转 ：根据组织编码查询收银员姓名和部门电话
     * @author 310430-wangyuanyuan
     * @date 2016年4月26日
     */
	@SuppressWarnings("unchecked")
	public List<EmployeeEntity> queryCashierNameAndDepTelephone(String orgCode){
		EmployeeEntity entity=new EmployeeEntity();
	 	entity.setOrgCode(orgCode);
		List<EmployeeEntity> entitys = this.getSqlSession().selectList(
	 		NAMESPACE + "queryCashierNameAndDepTelephone", entity);
	 	if (CollectionUtils.isEmpty(entitys)) {
	 	    return null;
	 	} else {
	 	    return entitys;
	 	}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeEntity> queryEmployeeByOrgCode(EmployeeEntity entity) {
		if(entity==null){
			return null;
		}
		if(StringUtils.isEmpty(entity.getOrgCode())){
			return null;
		}
    	 return getSqlSession().selectList(NAMESPACE+"queryEmployeeByOrgCode", entity);
    	 
     }
}
