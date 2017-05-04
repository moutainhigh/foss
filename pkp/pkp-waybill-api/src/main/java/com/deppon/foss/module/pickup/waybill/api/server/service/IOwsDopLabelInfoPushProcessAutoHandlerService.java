package com.deppon.foss.module.pickup.waybill.api.server.service;

/**
 * OWS/DOP标签推送任务处理线程
 * @author 329834
 *
 */
public interface IOwsDopLabelInfoPushProcessAutoHandlerService {
	public void process(String jobId);
}
