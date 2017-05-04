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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IEmployeeDao.java
 * 
 * FILE NAME        	: IEmployeeDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
/**
 * 人员 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 上午13:18:4
 */
public interface IEmployeeDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    EmployeeEntity addEmployee(EmployeeEntity entity);

    /**
     * 根据EMP_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    EmployeeEntity deleteEmployee(EmployeeEntity entity);

    /**
     * 根据EMP_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    EmployeeEntity deleteEmployeeMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    EmployeeEntity updateEmployee(EmployeeEntity entity);
    
    /**
     * 
     * <p>配合主数据项目修改人员信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-22 上午9:08:59
     * @param entity
     * @return
     * @see
     */
    EmployeeEntity updateEmployeeOfUU(EmployeeEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据EMP_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    EmployeeEntity queryEmployeeByEmpCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个EMP_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     * @see com.deppon.foss.module.base.dict.api.server.dao.IEmployeeDao#queryEmployeeByCode(java.lang.String)
     */
    List<EmployeeEntity> queryEmployeeBatchByEmpCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *关联了人员部门
     * @author 078823-panGuangjun
     * @date 2012-11-2 上午13:18:4
     */
    List<EmployeeEntity> queryEmployeeExactByEntity4Selector(
    		EmployeeEntity entity, int start, int limit) ;
    /**
     * 模糊查询--查询符合条件的总数
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *关联了人员部门
     * @author 078823-panGuangjun
     * @date 2012-11-2 上午13:18:4
     */
    long countEmployeeExactByEntity4Selector(
    		EmployeeEntity entity);
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    List<EmployeeEntity> queryEmployeeExactByEntity(
	    EmployeeEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    long queryEmployeeExactByEntityCount(EmployeeEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * 部门的标杆编码如果不为空，则为精确查询的查询条件 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    List<EmployeeEntity> queryEmployeeByEntity(EmployeeEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryEmployeeByEntity返回的记录总数,用于分页
     * 
     * 部门的标杆编码如果不为空，则为精确查询的查询条件 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午13:18:4
     */
    long queryEmployeeByEntityCount(EmployeeEntity entity);
    
    /**
     * 用户部门功能中查询员工信息（该员工必须有用户信息）
     * @author 088933-foss-zhangjiheng
     * @date 2013-2-28 下午6:44:33
     */
    List<EmployeeEntity> queryEmployeeAndUserByEntity(EmployeeEntity entity,int start,int limit);
    
    /**
     * 用户部门功能中查询员工信息求和（该员工必须有用户信息）
     * @author 088933-foss-zhangjiheng
     * @date 2013-2-28 下午6:44:33
     */
    long queryEmployeeAndUserCountByEntity(EmployeeEntity entity);
		
    /**
     * 根据员工号查询员工姓名
     * 
     * @author foss-zhangxiaohui
     * @date 2013-5-6 下午5:47:06
     */
    String queryEmpNameByEmpCode(String empCode);
    
    /**
   	 * 内存溢出，性能优化
   	 * @return
   	 * add WangQianJin by 2010-10-17
   	 */
     List<EmployeeEntity> queryEmployeeNameAndCodeByEmpCode(List<String> codes);
     /**
      * 
      * <p>配合主数据项目，根据人员编码和修改时间查询人员信息</p> 
      * @author 187862-dujunhui 
      * @date 2015-4-23 下午3:09:19
      * @param entity
      * @return
      * @see
      */
     List<EmployeeEntity> queryEmployeeByCodeAndModifyDate(EmployeeEntity entity);
     /**
      * 
      * <p>配合主数据项目新增</p> 
      * @author 187862-dujunhui 
      * @date 2015-4-23 下午3:29:14
      * @param entity
      * @return
      * @see
      */
     EmployeeEntity addEmployeeOfUU(EmployeeEntity entity);
     /**
      * 
      * <p>配合主数据项目查询临时人员</p> 
      * @author 187862-dujunhui 
      * @date 2015-5-14 下午9:07:15
      * @param entity
      * @return
      * @see
      */
     EmployeeEntity queryTempEmployeeByEmpCode(String code);
     
     /**
      * 提供给接送货接口：根据员工编号查询其是否为营业部经理
      * @author 187862-dujunhui
      * @date 2015年10月30日 下午4:45:27
      */
     boolean queryEmpIsSaleDeptOfficer(String empCode);
     
     /**
      * 提供给中转：根据组织编码查询收银员姓名和部门电话
      * @author 310430-wangyuanyuan
      * @date 2016年4月26日
      */
	 List<com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity> queryCashierNameAndDepTelephone(
			String orgCode);
	 /**
      * 
      * <p>根据部门编号来查询该部门下面的所有员工</p> 
      * @author wangpengtao 323098 
      * @date 2016年7月25日20:11:13
      * @param entity
      * @return
      * @see
      */
     List<EmployeeEntity> queryEmployeeByOrgCode(EmployeeEntity entity);
}
