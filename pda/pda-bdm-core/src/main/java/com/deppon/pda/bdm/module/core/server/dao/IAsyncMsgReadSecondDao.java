package com.deppon.pda.bdm.module.core.server.dao;

import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;

/**
 * 异步消息读取DAO
 * @author 245955
 *
 */
public interface IAsyncMsgReadSecondDao {
	/**
	 * 读取异步消息
	 * @param count 读取条数
	 * @param queueId 
	 * @return
	 */
	public List<AsyncMsg> readAsyncMsg(int count, String queueId);
	
	/**
	 * 清理异常状态数据
	 * @param queueId
	 */
	public void doInitAsyncDataStatus(String queueId);
}
