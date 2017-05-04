package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.common.api.server.service.IPushTrackSendDopService;
import com.deppon.foss.module.transfer.common.api.shared.dto.TrackingsRequestCommDto;


/**
* @description 推送给dop
* @version 1.0
* @author 14022-foss-songjie
* @update 2015年5月28日 下午8:53:52
*/
public class PushTrackForDopJob extends ThreadPoolcaller {
	
	private IPushTrackSendDopService pushTrackSendDopService;
	
	
	
	public void setPushTrackSendDopService(
			IPushTrackSendDopService pushTrackSendDopService) {
		this.pushTrackSendDopService = pushTrackSendDopService;
	}

	@Override
	public int serviceCaller() {
		String jobId = pushTrackSendDopService.updateAndGetJobId();
		List<TrackingsRequestCommDto> taobaoTrackingsDtos = pushTrackSendDopService.queryTrackingsInfobyJobId(jobId);
		if(CollectionUtils.isNotEmpty(taobaoTrackingsDtos)&&taobaoTrackingsDtos.size()>0){
			pushTrackSendDopService.ThreadsPool(taobaoTrackingsDtos);
			return taobaoTrackingsDtos.size();
		}
		return 0;
	}

	@Override
	public long getSleepTime() {
		//设置线程执行休眠时间1s
		long sleepTime =1000l;
		return sleepTime;
	}

	@Override
	public void reSetDate() {
		pushTrackSendDopService.reSetTrackingsMsgbyJobId();
		
	}

	@Override
	public long getNoDataSleepTime() {
		//设置线程执行休眠时间1min
		long sleepTime =60000l;
		return sleepTime;
	}

}
