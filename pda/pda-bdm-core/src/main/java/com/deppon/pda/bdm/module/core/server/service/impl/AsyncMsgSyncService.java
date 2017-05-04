package com.deppon.pda.bdm.module.core.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService;
import com.deppon.pda.bdm.module.core.server.dao.IAsyncMsgSyncDao;
import com.deppon.pda.bdm.module.core.server.httpService.OperationFactory;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

/**
 * 异步消息同步服务
 * @author wanghongling
 *
 */
public class AsyncMsgSyncService implements IAsyncDataSyncService<AsyncMsg>{
	private IAsyncMsgSyncDao asyncMsgSyncDao;
	/**
	 * 
	 * <p>TODO(异步消息同步)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:39:52
	 * @param asyncMsg
	 * @throws Exception 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService#syncData(com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage)
	 */
	@Transactional
	@Override
	public void syncData(AsyncMsg asyncMsg) throws Exception{
		//检验消息是否已存在
		if(!asyncMsgSyncDao.checkIsExist(asyncMsg.getId())){
			//调用异步消息各个模块service
			OperationFactory.asyncService(asyncMsg);
		}
		asyncMsgSyncDao.deleteAsyncMsg(asyncMsg.getId());
	}
	/**
	 * 
	 * <p>TODO(异常数据处理状态)</p> 
	 * @author Administrator
	 * @date 2013-3-21 上午9:41:12
	 * @param asyncMsg
	 * @param e
	 * @throws Exception 
	 * @see com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService#dealException(com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage, java.lang.Exception)
	 */
	@Transactional
	@Override
	public void dealException(AsyncMsg asyncMsg, Exception e) throws Exception {
		asyncMsgSyncDao.updateSyncStatus(asyncMsg.getId(), Constant.SYNC_STATUS_FAILED);
		//同步次数小于1时，第一次失败
		if(asyncMsg.getSyncCount()<1){
			asyncMsgSyncDao.saveSyncExcInfo(asyncMsg.getId(), LogUtil.logFormat(e));
		}
		
	}
	public IAsyncMsgSyncDao getAsyncMsgSyncDao() {
		return asyncMsgSyncDao;
	}
	public void setAsyncMsgSyncDao(IAsyncMsgSyncDao asyncMsgSyncDao) {
		this.asyncMsgSyncDao = asyncMsgSyncDao;
	}
	@Transactional
	@Override
	public void dealBussinessException(AsyncMsg asyncMsg, Exception e)
			throws Exception {
		asyncMsgSyncDao.updateSyncStatus(asyncMsg.getId(), Constant.SYNC_STATUS_BUSSINESS_FAILED);
		asyncMsgSyncDao.saveSyncExcInfo(asyncMsg.getId(), LogUtil.logFormat(e));
	}
	
}
