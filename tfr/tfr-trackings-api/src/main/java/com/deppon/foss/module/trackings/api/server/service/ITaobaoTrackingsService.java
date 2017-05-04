package com.deppon.foss.module.trackings.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;

public interface ITaobaoTrackingsService extends IService {

	public String updateAndGetJobId();
	public List<TaobaoTrackingsRequestDto> queryTrackingsInfobyJobId(String jobId);
	public void ThreadsPool(Object obj);
	public void reSetTrackingsMsgbyJobId();
}
