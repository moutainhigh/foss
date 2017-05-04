/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-common
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/common/test/util/CommonTestUtil.java
 * 
 * FILE NAME        	: CommonTestUtil.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.common.test.util;

import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;


public class CommonTestUtil {
	/**
	 * 临时获取用户对象
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-15 下午7:57:59
	 * @return
	 */
	public static CurrentInfo getCurrentInfo() {
		UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode("000123");
		employee.setEmpName("张三");
		user.setEmployee(employee);
		user.setUserName("zhangsan");

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode("1");
		dept.setName("德邦物流");

		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		return currentInfo;
	}
	
	/**
	 * 获取随机运单号
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-23 上午7:59:21
	 * @return
	 */
	public static String getWaybillNO(){
		return  "YD"+Math.round(Math.random() * 1000000000) ;
	}
	
	/**
	 * 获取对账单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午6:54:50
	 * @return
	 */
	public static String getStatementBillNo(){
		return "DZDH"+new Date().getTime();
	}
	
	/**
	 * 获取应收单号
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-4 下午7:54:37
	 * @return
	 */
	public static String getReceivableNo(){
		return "YSDH"+new Date().getTime();
	}
}
