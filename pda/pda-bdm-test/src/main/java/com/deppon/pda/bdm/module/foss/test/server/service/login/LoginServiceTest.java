package com.deppon.pda.bdm.module.foss.test.server.service.login;

import java.util.Date;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;

public class LoginServiceTest implements IPdaSigninLogoutService {
	
	public void signIn(String deviceNo, String driverCode, String vehicleNo,
			Date signTime, String userType) throws BusinessException {
		System.out.println("deviceNo:" + deviceNo + ",driveCode:" + driverCode);
		throw new TestException("asdasd");
	}

	@Override
	public void signIn(PdaSigninDto pdaSigninDto) {
		// TODO Auto-generated method stub
	}

	@Override
	public void loginOut(String deviceNo, String driverCode, String vehicleNo,
			Date unbundleTime) {
		// TODO Auto-generated method stub
		
	}

}
