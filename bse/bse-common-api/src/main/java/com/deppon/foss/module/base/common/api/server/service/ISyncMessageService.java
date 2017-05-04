package com.deppon.foss.module.base.common.api.server.service;

import java.util.List;

import com.deppon.esb.inteface.domain.msg.SyncMsgResponseBody;

/**
 * 同步待办消息给其他系统  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-4-12
 */
public interface ISyncMessageService {

	/**
	 * 待办消息
	 * @author 310854
	 * @date 2016-4-12
	 */
	SyncMsgResponseBody syncMessage(List<?> entitys);
}
