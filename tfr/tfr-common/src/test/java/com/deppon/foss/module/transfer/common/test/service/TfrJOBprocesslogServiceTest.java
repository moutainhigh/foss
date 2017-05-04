package com.deppon.foss.module.transfer.common.test.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.common.api.server.service.ITfrJobProcessLogService;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.vo.TfrJobProcessLogVo;
import com.deppon.foss.module.transfer.common.test.BaseTestCase;

public class TfrJOBprocesslogServiceTest extends BaseTestCase {

	@Autowired
	private ITfrJobProcessLogService tfrJobProcessLogService;

	private List<TfrJobProcessLogEntity> list;

	@Test
	public void tt1() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		TfrJobProcessLogVo vo = new TfrJobProcessLogVo();

		list = tfrJobProcessLogService.getJobProcessLogsList(vo, 0, 5);

		String ty = "";

		logger.debug("=======================>");

		for (TfrJobProcessLogEntity source : list) {
			ty = "";
			Method[] sourceMethods = source.getClass().getMethods();
			for (int i = 0; i < sourceMethods.length; i++) {
				if (sourceMethods[i].getName().startsWith("get")) {
					String lsName = sourceMethods[i].getName().substring(3); // 属性Name
					Object loValue = sourceMethods[i].invoke(source, null); // 值
					// String lsSourceType = sourceMethods[i].getReturnType()
					// .getName(); // 类型

					ty = ty + lsName + " : " + loValue + ",";

				}
			}

			logger.debug(ty);
		}

		logger.debug("=======================>");

	}

	@Test
	public void ttty2() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		logger.debug("=======================>");

		String ty = "";
		
		TfrJobProcessLogVo vo = new TfrJobProcessLogVo();

		list = tfrJobProcessLogService.getJobProcessLogsList(vo, 0, 5);

		for (TfrJobProcessLogEntity source : list) {
			vo.setId(source.getId());
			TfrJobProcessLogEntity trrr = tfrJobProcessLogService
					.getJobProcessLogByID(vo);

			ty = "";

			Method[] sourceMethods = trrr.getClass().getMethods();
			for (int i = 0; i < sourceMethods.length; i++) {
				if (sourceMethods[i].getName().startsWith("get")) {
					String lsName = sourceMethods[i].getName().substring(3); // 属性Name
					Object loValue = sourceMethods[i].invoke(source, null); // 值
					// String lsSourceType = sourceMethods[i].getReturnType()
					// .getName(); // 类型

					ty = ty + lsName + " : " + loValue + ",";

				}
			}
			logger.debug(ty);

		}

		logger.debug("=======================>");
	}

}