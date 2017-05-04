package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.esb.api.domain.AccessHeader;

/** 
 * @author  313353-qiupeng
 * @date 创建时间：2016-8-3 上午10:20:50 
 * @version 1.0 
 **/
public interface ISyncESBSendService {
	
	/**
	 * 真正发送消息的方法
	 * @param header 消息头
	 * @param request 消息请求
	 */
	public void sendESBMessage(AccessHeader header, Object request);
	
	/**
	 * 创建线程发送消息
	 * @param syncESBSendService 发送esb消息的服务
	 * @param header 消息头
	 * @param request 消息请求
	 */
	public void createThreadToSendESB(ISyncESBSendService syncESBSendService, AccessHeader header, Object request);
}
