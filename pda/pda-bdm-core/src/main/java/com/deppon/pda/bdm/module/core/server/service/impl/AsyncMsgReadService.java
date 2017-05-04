package com.deppon.pda.bdm.module.core.server.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.service.AbstractClusterAsyncDataReadService;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgReadDao;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
/**
 * 
 * TODO(异步消息读取service)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-21 上午9:38:43,content:TODO </p>
 * @author Administrator
 * @date 2013-3-21 上午9:38:43
 * @since
 * @version
 */
public class AsyncMsgReadService extends AbstractClusterAsyncDataReadService<AsyncMsg> {
	private IAsyncMsgReadDao asyncMsgReadDao;
	/**
	 * 
	 * <p>TODO(读取异步消息)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:39:11
	 * @param count
	 * @param queueId
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.AbstractClusterAsyncDataReadService#doReadAsyncData(int, java.lang.String)
	 */
	@Transactional
	@Override
	protected List<AsyncMsg> doReadAsyncData(int count, String queueId) {
		return asyncMsgReadDao.readAsyncMsg(count, queueId);
	}
	/**
	 * 
	 * <p>TODO(清理异常数据)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:39:33
	 * @param queueId 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.AbstractClusterAsyncDataReadService#doInitAsyncDataStatus(java.lang.String)
	 */
	@Transactional
	@Override
	protected void doInitAsyncDataStatus(String queueId) {
		asyncMsgReadDao.doInitAsyncDataStatus(queueId);
	}

	public IAsyncMsgReadDao getAsyncMsgReadDao() {
		return asyncMsgReadDao;
	}

	public void setAsyncMsgReadDao(IAsyncMsgReadDao asyncMsgReadDao) {
		this.asyncMsgReadDao = asyncMsgReadDao;
	}

}
