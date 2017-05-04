package com.deppon.foss.module.transfer.agent.server.service;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.CommonInterTrackingRequest;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.CommonInterTrackingResponse;
import com.deppon.foss.service.waybilltrackservice.CommonException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IInternationalTrackingService {
	/**
	 * 日本国际间轨迹
	 * @author 352203
	 * @param commonInterTrackingRequest
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(value = "/addInternationalTracking", method = RequestMethod.POST)
	CommonInterTrackingResponse addInternationalTracking(CommonInterTrackingRequest commonInterTrackingRequest) throws CommonException;
}
