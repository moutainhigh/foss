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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/EmployeeService.java
 * 
 * FILE NAME        	: EmployeeService.java
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
 ***************************************************
 *
 * 修订记录 
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
		

 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeDtoToSTL;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.EmployeeException;
import com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;
import com.google.inject.Inject;

/**
 * 人员 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 上午13:29:37
 */
public class EmployeeService implements IEmployeeService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmployeeService.class);
	/**
	 *职位职等Service 
	 */
	private IDataDegreePostionService dataDegreePostionService;
	
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	
	
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}

	public void setDataDegreePostionService(
			IDataDegreePostionService dataDegreePostionService) {
		this.dataDegreePostionService = dataDegreePostionService;
	}

	/**
	 * 人员 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService#addEmployee(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
	 * 
	 *      1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	@Transactional
	public EmployeeEntity addEmployee(EmployeeEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		return employeeDao.addEmployee(entity);
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#deleteEmployee(java.lang.String)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述 SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	@Transactional
	public EmployeeEntity deleteEmployee(EmployeeEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getEmpCode())) {
			return null;
		}
		// 清空缓存
		invalidEntity(entity.getEmpCode());
		return employeeDao.deleteEmployee(entity);
	}

	/**
	 * 通过code标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#deleteEmployeeMore(java.lang.String[])
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 * 
	 *      1.7 业务规则 序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	@Transactional
	public EmployeeEntity deleteEmployeeMore(String[] codes, String deleteUser) {
		// 批量删除数据缓存
		invalidEntitys(codes);
		return employeeDao.deleteEmployeeMore(codes, deleteUser);
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeDao#updateEmployee(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	@Transactional
	public EmployeeEntity updateEmployee(EmployeeEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getEmpCode())) {
			return null;
		}
		// 清空缓存
		invalidEntity(entity.getEmpCode());
		return employeeDao.updateEmployee(entity);
	}

	/**
	 * 以下全为查询
	 */

	/**
	 * 精确查询 通过 EMP_CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeService#queryEmployeeByCode(java.lang.String)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	public EmployeeEntity queryEmployeeByEmpCode(String code) {
		// 判断传入的员工编码是否为空
		if (null == code) {
			return null;
		}
		EmployeeEntity employee = querySimpleEmployeeByEmpCode(code);
		//加非空判断，根据DEFECT-3450进行修改
		if(employee!=null){
			//根据职位编号查询职位
			if(!StringUtils.isBlank(employee.getTitle())){//修改查询方法-187862
				DataDegreePostionValueEntity titleEntity=dataDegreePostionService.queryDegreePostionValueByTermsCodeValueCodeNoCache(DictionaryConstants.
						UUMS_POSITION_TERMSCODE, employee.getTitle());
				if(null!=titleEntity){
					employee.setTitleName(titleEntity.getValueName());
				}
			}
			// 调用私有方法，为员工实体添加组织信息
			employee = this.attachOrg(employee);
		}		
		return employee;
	}

	/**
	 * 
	 * 根据员工编码查询员工信息，不走缓存，供综合内部维护基础数据使用
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-5-29 下午5:05:13
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService#queryEmployeeByEmpCode(java.lang.String)
	 */
	@Override
	public EmployeeEntity queryEmployeeByEmpCodeNoCache(String code) {

		// 判断传入的员工编码是否为空
		if (StringUtils.isBlank(code)) {
			return null;
		}
		EmployeeEntity employee = null;
		employee = employeeDao.queryEmployeeByEmpCode(code);
		// 调用私有方法，为员工实体添加组织信息
		employee = this.attachOrg(employee);
		return employee;
	}

	/**
	 * 
	 * <p>
	 * 通过员工编码查询员工实体
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Apr 16, 2013 4:01:31 PM
	 * @param code
	 * @return
	 * @see
	 */
	@Override
	public EmployeeEntity querySimpleEmployeeByEmpCode(String code) {
		// 判断传入的员工编码是否为空
		if (StringUtils.isBlank(code)) {
			return null;
		}
		EmployeeEntity employee = null;
		// 先从缓存中获取员工信息
		if (SqlUtil.loadCache) {
			employee = queryEmployeeByCache(code);
			// 如果缓存中不存在，则从数据库中查询
		} else {
			employee = employeeDao.queryEmployeeByEmpCode(code);
		}
		return employee;
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeService#queryEmployeeByCode(java.lang.String)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeBatchByEmpCode(String[] codes) {
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		// 执行查询操作
		List<EmployeeEntity> employeeList = employeeDao
				.queryEmployeeBatchByEmpCode(codes);
		// 调用私有方法，为员工实体添加组织信息
		employeeList = this.attachOrg(employeeList);
		return employeeList;
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeService#queryEmployeeExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity,
	 *      int, int) * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeExactByEntity(
			EmployeeEntity entity, int start, int limit) {
		// 执行查询操作
		List<EmployeeEntity> employeeList = employeeDao
				.queryEmployeeExactByEntity(entity, start, limit);
		// 调用私有方法，为员工实体添加组织信息
		employeeList = this.attachOrg(employeeList);
		return employeeList;
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeService#queryEmployeeExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果 。 3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 * 
	 */
	@Override
	public long queryEmployeeExactByEntityCount(EmployeeEntity entity) {
		return employeeDao.queryEmployeeExactByEntityCount(entity);
	}

	/**
	 * 模糊查询
	 * 
	 * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * 部门的标杆编码如果不为空，则为精确查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-2 上午13:29:37
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IEmployeeService#deleteEmployeeMore(java.lang.String[])
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述
	 * 
	 *      SR-1 查询条件中的“状态”默认为在职。
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeByEntity(EmployeeEntity entity,
			int start, int limit) {
		List<EmployeeEntity> entitys = employeeDao.queryEmployeeByEntity(
				entity, start, limit);
		// 调用私有方法，为员工实体添加组织信息
		entitys = this.attachOrg(entitys);
		return entitys;
	}

	/**
	 * 动态的查询条件-查询总条数。
	 * 
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * 部门的标杆编码如果不为空，则为精确查询的查询条件
	 * 
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 上午13:29:37
	 * @see com.deppon.foss.module.baseinfo.server.service.IEmployeeService#queryEmployeeCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.EmployeeEntity)
	 *      * 1 进入人员管理主界面。
	 * 
	 *      2 输入查询条件。点击查询按钮。 【人员查询条件】 系统返回查询结果。
	 * 
	 *      3 点击分页按钮，查看各页的查询结果。 【人员列表信息】
	 * 
	 *      4 找出要查看的人员双击。 【人员信息】 进入人员详情界面。
	 * 
	 *      5 点击返回按钮。 返回主界面。
	 * 
	 * 
	 *      1.7 业务规则
	 * 
	 *      序号 描述 SR-1 查询条件中的“状态”默认为在职。
	 */
	@Override
	public long queryEmployeeByEntityCount(EmployeeEntity entity) {
		return employeeDao.queryEmployeeByEntityCount(entity);
	}

	/**
	 * 给用户加上部门信息-批量
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-30 下午8:24:10
	 */
	private List<EmployeeEntity> attachOrg(List<EmployeeEntity> entitys) {
		for (EmployeeEntity entity : entitys) {
			this.attachOrg(entity);
		}
		return entitys;
	}

	/**
	 * 给用户加上部门信息
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-30 下午8:24:45
	 */
	private EmployeeEntity attachOrg(EmployeeEntity entity) {
		if (entity == null) {
			return entity;
		}
		String unifield = entity.getUnifieldCode();
		if (StringUtils.isNotBlank(unifield)) {
			OrgAdministrativeInfoEntity entityCondition = new OrgAdministrativeInfoEntity();
			entityCondition.setUnifiedCode(unifield);
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByUnifiedCode(unifield);
			// List<OrgAdministrativeInfoEntity> results =
			// orgAdministrativeInfoService.queryOrgAdministrativeInfoExactByEntity(entityCondition,
			// NumberConstants.ZERO, Integer.MAX_VALUE);
			// if (CollectionUtils.isNotEmpty(results)) {
			// entity.setDepartment(results.get(NumberConstants.ZERO));
			// return entity;
			// }
			if (org != null) {
				entity.setDepartment(org);
			}
		}
		return entity;
	}

	/**
	 * 
	 * 在員工信息修改、删除时去清空单条数据缓存
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-2-22 下午6:09:34
	 */
	@SuppressWarnings({ "unchecked" })
	private void invalidEntity(String key) {
		((ICache<String, EmployeeEntity>) CacheManager.getInstance().getCache(
				FossTTLCache.EMPLOYEE_ENTITY_CACHE_UUID)).invalid(key);
	}

	/**
	 * 
	 * 在員工信息修改、删除时去清空批量数据缓存
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-2-22 下午6:09:34
	 */
	@SuppressWarnings("unchecked")
	private void invalidEntitys(String[] keys) {
		for (int i = 0; i < keys.length; i++) {
			((ICache<String, EmployeeEntity>) CacheManager.getInstance()
					.getCache(FossTTLCache.EMPLOYEE_ENTITY_CACHE_UUID))
					.invalid(keys[i]);
		}
	}

	/**
	 * 从缓存中读取数据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-2-22 下午6:15:52
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private EmployeeEntity queryEmployeeByCache(String key) {

		EmployeeEntity result = null;
		try {
			// 获取cache实例
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			// 通过员工CACHE id获取员工cache
			ICache cache = cacheManager
					.getCache(FossTTLCache.EMPLOYEE_ENTITY_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (EmployeeEntity) cache.get(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return result;

	}

	/**
	 * 用户部门功能中查询员工信息（该员工必须有用户信息）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * 
	 * @date 2013-2-28 下午6:44:33
	 */
	@Override
	public List<EmployeeEntity> queryEmployeeAndUserByEntity(
			EmployeeEntity entity, int start, int limit) {
		// 判断查询参数对象是否为空
		if (entity == null) {
			throw new BusinessException("", "查询参数对象为空，不能执行查询操作！");
		}
		List<EmployeeEntity> employeeEntityList = employeeDao
				.queryEmployeeAndUserByEntity(entity, start, limit);
		employeeEntityList = this.attachOrg(employeeEntityList);
		return employeeEntityList;
	}

	/**
	 * 用户部门功能中查询员工信息求和（该员工必须有用户信息）
	 * 
	 * @author 088933-foss-zhangjiheng
	 * 
	 * @date 2013-2-28 下午6:44:33
	 */
	@Override
	public long queryEmployeeAndUserCountByEntity(EmployeeEntity entity) {
		// 判断查询参数对象是否为空
		if (entity == null) {
			throw new BusinessException("", "查询参数对象为空，不能执行查询操作！");
		}
		return employeeDao.queryEmployeeAndUserCountByEntity(entity);
	}

	/**
	 * 下面是dao对象的声明及get,set方法：
	 * 
	 */
	@Inject
	private IEmployeeDao employeeDao;
	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	//private ICommonPositionService commonPositionService;
	
	
	/*public void setCommonPositionService(
			ICommonPositionService commonPositionService) {
		this.commonPositionService = commonPositionService;
	}*/

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * 根据员工号查询员工姓名
	 * 
	 * @author foss-zhangxiaohui
	 * @date 2013-5-6 下午5:45:06
	 */
	public String queryEmpNameByEmpCode(String empCode) {
		// 非空验证
		if (StringUtils.isEmpty(empCode)) {
			// 输入参数为空则打印信息
			LOGGER.info("queryEmpNameByEmpCode通过员工号查询员工姓名时参数为空");
			// 返回null值
			return null;
		}
		// 返回查询结果
		return employeeDao.queryEmpNameByEmpCode(empCode);
	}
	
	/**
	 * 内存溢出，性能优化
	 * @return
	 * add WangQianJin by 2010-10-17
	 */
    public List<EmployeeEntity> queryEmployeeNameAndCodeByEmpCode(List<String> codes){
    	//集合非空判断
		if(CollectionUtils.isEmpty(codes)){
			return null;
		}	
		//根据员工工号查询
		return employeeDao.queryEmployeeNameAndCodeByEmpCode(codes);
    }
    /**
     * 
     * <p>配合主数据项目修改人员信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-22 上午9:02:02
     * @param entity
     * @return
     * @see
     */
    @Override
	@Transactional
	public EmployeeEntity updateEmployeeOfUU(EmployeeEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getEmpCode())) {
			return null;
		}
		// 清空缓存
		invalidEntity(entity.getEmpCode());
		//对临时人员的操作:只修改
		if(StringUtil.equal(entity.getIsTempEmp(), FossConstants.YES)){
			//临时人员先查询有没有，有则更新，无则新增
			EmployeeEntity tempEntity=employeeDao.queryTempEmployeeByEmpCode(entity.getEmpCode());
			if(tempEntity==null){//无则新增
				EmployeeEntity addEntity=null;
				try{//无则新增
					addEntity=employeeDao.addEmployeeOfUU(entity);
				}catch(Exception e){
					throw new EmployeeException(e.getMessage());
				}
				return addEntity;
			}else{//有则更新
				return employeeDao.updateEmployeeOfUU(entity);
			}
		}else{
			//根据工号和修改时间判断修改还是新增
			List<EmployeeEntity> list=this.queryEmployeeByCodeAndModifyDate(entity);
			if(CollectionUtils.isEmpty(list)){//无则新增
				EmployeeEntity addEntity=null;
				try{
					addEntity=employeeDao.addEmployeeOfUU(entity);
				}catch(Exception e){
					throw new EmployeeException(e.getMessage());
				}
				return addEntity;
			}else{//有则直接修改
				return employeeDao.updateEmployeeOfUU(entity);
			}
		}
	}

    /**
     * 
     * <p>根据员工工号和修改时间查询员工信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-22 上午11:19:12
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService#queryEmployeeByCodeAndModifyDate(com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity)
     */
	@Override
	public List<EmployeeEntity> queryEmployeeByCodeAndModifyDate(
			EmployeeEntity entity) {
		if(StringUtil.isEmpty(entity.getEmpCode())||StringUtil.isEmpty(entity.getCreateTime())){
			return null;
		}
		return employeeDao.queryEmployeeByCodeAndModifyDate(entity);
	}
	
	/**
	 * 
	 * <p>配合主数据项目新增人员</p> 
	 * @author 187862-dujunhui 
	 * @date 2015-4-24 下午7:10:58
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	@Transactional
	public EmployeeEntity addEmployeeOfUU(EmployeeEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		return employeeDao.addEmployeeOfUU(entity);
	}

	/**
	 * 提供给接送货接口：根据员工编号查询其是否为营业部经理
	 * @author 187862-dujunhui
     * @date 2015年10月30日 下午4:44:36
	 */
	@Override
	public boolean queryEmpIsSaleDeptOfficer(String empCode) {
		return employeeDao.queryEmpIsSaleDeptOfficer(empCode);
	}
	
	
	/**
	 * 提供给结算： 通过部门编码，查询该部门下的所有收银员、以及部门电话
	 * @author wangyuanyuan
     * @date 2016年4月25日 下午4:44:36
	 */
	@Override
	public EmployeeDtoToSTL queryCashierNameAndDepTelephone(String orgCode) {
		
		//查询收银员名称，返回List
		List<EmployeeEntity> employeeList = employeeDao.queryCashierNameAndDepTelephone(orgCode);
		
		//再查部门电话
		OrgAdministrativeInfoEntity entityResult = orgAdministrativeInfoDao
				.queryOrgAdministrativeInfoByCode(orgCode);
		
		//拼成一个Dto，返回
		EmployeeDtoToSTL dto=new EmployeeDtoToSTL();
		dto.setCashierList(employeeList);
		dto.setDepTelephone(entityResult.getDepTelephone());
		return dto;
	}
	
}
