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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/frameworkimpl/shared/domain/CurrentInfo.java
 * 
 * FILE NAME        	: CurrentInfo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.frameworkimpl.shared.domain;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:系统当前信息</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 2012-11-12 钟庭杰    新增
* </div>  
********************************************
 */
public class CurrentInfo implements Serializable{
	
	/**
	 * 序列化版本
	 */
	private static final long serialVersionUID = 1L;
	//350909   郭倩云    添加一个无参构造函数
	public CurrentInfo() {
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}

	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setDept(OrgAdministrativeInfoEntity dept) {
		this.dept = dept;
	}

	//不改变逻辑  此字段只给CUBC反签收调反结清使用
	private String cubcID;
	
	public String getCubcID() {
		return cubcID;
	}

	public void setCubcID(String cubcID) {
		this.cubcID = cubcID;
	}

	//用户名
	private String userName;
	
	//员工工号
	private String empCode;
	
	//员工姓名
	private String empName;
	
	//当前登录部门编码
	private String currentDeptCode;
	
	//当前登录部门名称
	private String currentDeptName;
	
	//当前登录用户
	private UserEntity user;
	
	//当前部门
	private OrgAdministrativeInfoEntity dept;
	
	public CurrentInfo(UserEntity user, OrgAdministrativeInfoEntity dept) {
		this.user = user;
		this.dept = dept;
		if(user!=null){
			this.userName = user.getUserName();
			if(user.getEmployee()!=null){
				this.empCode = user.getEmployee().getEmpCode();
				this.empName = user.getEmployee().getEmpName();
			}						
		}
		if(dept!=null){
			this.currentDeptCode = dept.getCode();
			this.currentDeptName = dept.getName();			
		}
	}
	
	public CurrentInfo(UserEntity user) {
		this.user = user;
		if(user!=null){
			this.userName = user.getUserName();
			this.empCode = user.getEmployee().getEmpCode();
			this.empName = user.getEmployee().getEmpName();			
		}
		this.currentDeptCode = FossUserContext.getCurrentDeptCode();
		this.currentDeptName = FossUserContext.getCurrentDeptName();		
	}

	public String getUserName() {
		return userName;
	}

	public String getEmpCode() {
		return empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public String getCurrentDeptCode() {
		return currentDeptCode;
	}

	public String getCurrentDeptName() {
		return currentDeptName;
	}

	public UserEntity getUser() {
		return user;
	}

	public OrgAdministrativeInfoEntity getDept() {
		if(dept==null){
			return FossUserContext.getCurrentDept();			
		}else{
			return dept;
		}
	}
}
