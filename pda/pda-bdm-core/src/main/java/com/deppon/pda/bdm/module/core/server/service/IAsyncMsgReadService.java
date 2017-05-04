package com.deppon.pda.bdm.module.core.server.service;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**
 * 异步消息读取服务接口
 * @author wanghongling
 *
 */
public interface IAsyncMsgReadService {
	/**
	 * 读取异步消息
	 * @param count 读取条数
	 * @param queueId 
	 * @return
	 */
	public List<ScanDataQueueMsg> readAsyncMsg(int count, String queueId) throws PdaBusiException;
}
