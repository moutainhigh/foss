/**
 * 
 */
package com.deppon.foss.module.pickup.order.service;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.pickup.order.BaseTestCase;
import com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto;

/**
 * @author wangfei
 *
 */
public class SignInAndLogOutExpressServiceTest extends BaseTestCase {
	@Autowired
	private ISignInAndLogOutExpressService signInAndLogOutExpressService;
	
	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#querySignedInfo(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto)}.
	 */
	@Test
	public void testQuerySignedInfo() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.querySignedInfo(dto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#querySignedInfoByPage(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto, int, int)}.
	 */
	@Test
	public void testQuerySignedInfoByPage() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.querySignedInfoByPage(dto, 0, 10);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#querySignedInfoCount(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto)}.
	 */
	@Test
	public void testQuerySignedInfoCount() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.querySignedInfoCount(dto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#handResolveBind(com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity)}.
	 */
	@Test
	public void testHandResolveBind() {
		PdaSignEntity entity = new PdaSignEntity();
		// 将PDA签到信息状态改为"已解绑"
		entity.setStatus(PdaSignStatusConstants.UNBUNDLE);
		entity.setUnbundleTime(new Date());
		signInAndLogOutExpressService.handResolveBind(entity);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#autoUnbundle()}.
	 */
	@Test
	public void testAutoUnbundle() {
		signInAndLogOutExpressService.autoUnbundle();
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#refreshPdaSignDto(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto)}.
	 */
	@Test
	public void testRefreshPdaSignDto() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.refreshPdaSignDto(dto);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#queryExpressSignedInfoByPage(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto, int, int)}.
	 */
	@Test
	public void testQueryExpressSignedInfoByPage() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.queryExpressSignedInfoByPage(dto, 0, 10);
	}

	/**
	 * Test method for {@link com.deppon.foss.module.pickup.order.api.server.service.ISignInAndLogOutExpressService#queryExpressSignedInfoCount(com.deppon.foss.module.pickup.order.api.shared.dto.PdaSignDto)}.
	 */
	@Test
	public void testQueryExpressSignedInfoCount() {
		PdaSignDto dto = new PdaSignDto();
		dto.setActive("Y");
		dto.setDeviceNo("123213");
		signInAndLogOutExpressService.queryExpressSignedInfoCount(dto);
	}

}
