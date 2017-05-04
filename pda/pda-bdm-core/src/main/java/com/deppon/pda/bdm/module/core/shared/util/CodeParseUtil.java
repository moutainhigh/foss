package com.deppon.pda.bdm.module.core.shared.util;

import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.pda.bdm.module.core.server.cache.DeptCache;
import com.deppon.pda.bdm.module.core.server.cache.UserCache;
import com.deppon.pda.bdm.module.core.server.cache.VehicleCache;
import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;
import com.deppon.pda.bdm.module.core.shared.domain.VehicleEntity;

public class CodeParseUtil {
	public static DeptCache deptCache;

	public static UserCache userCache;
	
	public static VehicleCache vehicleCache;

	static {
		deptCache = (DeptCache) BeanFactory.getBean("deptCache");
		userCache = (UserCache) BeanFactory.getBean("pdaUserCache");
		vehicleCache = (VehicleCache) BeanFactory.getBean("vehicleCache");
	}

	public static String getDeptCode(String deptId,String empCode,String userType) {
		DeptEntity dept = deptCache.getDept(deptId);
		if (dept != null) {
			//如果是快递员,则要找快递部门
			if(userType != null && userType.equals(PdaSignStatusConstants.USER_TYPE_COURIER)){
				//根据用户工号,查找快递员所属营业部
				VehicleEntity vehicle = vehicleCache.getDept(empCode);
				if(vehicle != null){
					return vehicle.getOrgCode();
				}else{
					return null;
				}
			}
			return dept.getDeptCode();
		} else {
			return null;
		}
	}

	public static String getDeptId(String userCode) {
		UserEntity user = userCache.getUser(userCode);
		if (user != null) {
			return user.getDeptId();
		} else {
			return null;
		}
	}
}
