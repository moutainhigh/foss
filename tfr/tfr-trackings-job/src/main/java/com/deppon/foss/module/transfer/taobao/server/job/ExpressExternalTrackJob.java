package com.deppon.foss.module.transfer.taobao.server.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.trackings.api.server.service.ITaobaoTrackingsService;
import com.deppon.foss.module.trackings.api.shared.dto.TaobaoTrackingsRequestDto;
import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;

public class ExpressExternalTrackJob extends ThreadPoolcaller {

	private ITaobaoTrackingsService taobaoTrackingsService;
	
	
	public void setTaobaoTrackingsService(
			ITaobaoTrackingsService taobaoTrackingsService) {
		this.taobaoTrackingsService = taobaoTrackingsService;
	}


	/**
	 * 
	 * <p>执行方法，获取数据</p> 
	 * @author alfred
	 * @date 2015-4-28 上午10:21:23
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#serviceCaller()
	 */
	@Override
	public int serviceCaller() {
		String jobId = taobaoTrackingsService.updateAndGetJobId();
		List<TaobaoTrackingsRequestDto> taobaoTrackingsDtos = taobaoTrackingsService.queryTrackingsInfobyJobId(jobId);
		if(CollectionUtils.isNotEmpty(taobaoTrackingsDtos)&&taobaoTrackingsDtos.size()>0){
			taobaoTrackingsService.ThreadsPool(taobaoTrackingsDtos);
			return taobaoTrackingsDtos.size();
		}
		return 0;
	}
	

	/**
	 * 
	 * <p>重新设置线程执行休眠时间</p> 
	 * @author alfred
	 * @date 2015-4-29 上午10:23:22
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getSleepTime()
	 */
	@Override
	public long getSleepTime() {
		//设置线程执行休眠时间1s
		long sleepTime =1000l;
		return sleepTime;
	}

	/**
	 * 
	 * <p>重新设置异常数据占位符</p> 
	 * @author alfred
	 * @date 2015-4-29 上午10:26:27 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#reSetDate()
	 */
	@Override
	public void reSetDate() {
		taobaoTrackingsService.reSetTrackingsMsgbyJobId();
	}


	/**
	 * 
	 * <p>重新设置无数据是线程休眠时间</p> 
	 * @author alfred
	 * @date 2015-4-29 下午2:13:15
	 * @return 
	 * @see com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller#getNoDataSleepTime()
	 */
	@Override
	public long getNoDataSleepTime() {
		//设置线程执行休眠时间5min
		long sleepTime =300000l;
		return sleepTime;
	}

}
