package com.deppon.foss.module.transfer.agent.server.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CarCancelRequestDO;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.CarCancelResponseDO;

public interface ICancelHireCarTagService{

	/**
	 * 配合CUBC传递租车编码，取消临时租车标记
	 * @author liping
	 * 
	 * @date 2017-04-06 下午23:24:06
	 */
	@RequestMapping(value = "/aSynCancelHireCarTagService", method = RequestMethod.POST)
	CarCancelResponseDO aSynCancelHireCarTagService(CarCancelRequestDO carCancelRequestDO);
}
