package com.deppon.foss.module.settlement.common.api.shared.util;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

public class ECSCurrentInfoUtil {

	/**
	 * 获取当前信息
	 * @author 243921-FOSS-zhangtingting
 	 * @date 2016-4-15 下午4:22:02
	 */
	public static CurrentInfo getCurrentInfo(String currentEmpCode,String currentEmpName,String currentDeptCode,String currentDeptName){
		
		UserEntity user = new UserEntity();
		//员工信息
		EmployeeEntity	emp = new EmployeeEntity();
		emp.setEmpName(currentEmpName);//员工名称
		emp.setEmpCode(currentEmpCode);//员工编码
		user.setEmployee(emp);//得到员工信息
		
		//部门信息
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(currentDeptCode);
		dept.setName(currentDeptName);
		
		//返回信息
		return new CurrentInfo(user, dept);
	}
	
}
