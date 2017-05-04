package com.deppon.pda.bdm.module.core.server.service.impl.second;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.service.job.AbstractClusterAsyncDataReadSecondService;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgReadSecondDao;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
/**
 * 
 * TODO(异步消息读取service)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2013-3-21 上午9:38:43,content:TODO </p>
 * @author 245955
 * @date 2015-10-21
 * @since
 * @version
 */
public class AsyncMsgReadSecondService extends AbstractClusterAsyncDataReadSecondService<AsyncMsg> {
	private IAsyncMsgReadSecondDao asyncMsgReadSecondDao;
	/**
	 * 
	 * <p>TODO(读取异步消息)</p> 
	 * @author 245955
	 * @date 2015-10-21
	 * @param count
	 * @param queueId
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.AbstractClusterAsyncDataReadService#doReadAsyncData(int, java.lang.String)
	 */
	@Transactional
	@Override
	protected List<AsyncMsg> doReadAsyncData(int count, String queueId) {
		return asyncMsgReadSecondDao.readAsyncMsg(count, queueId);
	}
	/**
	 * 
	 * <p>TODO(清理异常数据)</p> 
	 * @author 245955
	 * @date 2015-10-21
	 * @param queueId 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.AbstractClusterAsyncDataReadService#doInitAsyncDataStatus(java.lang.String)
	 */
	@Transactional
	@Override
	protected void doInitAsyncDataStatus(String queueId) {
		asyncMsgReadSecondDao.doInitAsyncDataStatus(queueId);
	}
	
	
	public IAsyncMsgReadSecondDao getAsyncMsgReadSecondDao() {
		return asyncMsgReadSecondDao;
	}
	public void setAsyncMsgReadSecondDao(
			IAsyncMsgReadSecondDao asyncMsgReadSecondDao) {
		this.asyncMsgReadSecondDao = asyncMsgReadSecondDao;
	}
	

}
