package com.deppon.pda.bdm.module.foss.test.server.service.login;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaSigninLogoutService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaSigninDto;

public class PdaSigninLogoutService implements IPdaSigninLogoutService {
	private static final Log LOG = LogFactory.getLog(PdaSigninLogoutService.class);
	
	public void signIn(String deviceNo, String driverCode, String vehicleNo,
			Date signTime, String userType) {
		LOG.info("success foss interface: "+"signIn");
	}

	@Override
	public void loginOut(String deviceNo, String driverCode, String vehicleNo,
			Date unbundleTime) {
		LOG.info("success foss interface ："+"loginout");
	}

	@Override
	public void signIn(PdaSigninDto pdaSigninDto) {
		// TODO Auto-generated method stub
		
	}

}
